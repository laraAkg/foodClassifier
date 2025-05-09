package ch.zhaw.akguelar.food;

import ai.djl.Model;
import ai.djl.ModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.transform.Resize;
import ai.djl.modality.cv.transform.ToTensor;
import ai.djl.modality.cv.translator.ImageClassificationTranslator;
import ai.djl.translate.TranslateException;
import ai.djl.translate.Translator;
import ch.zhaw.akguelar.food.dto.LabelInfo;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.imageio.ImageIO;

/**
 * Service-Klasse für Bildklassifikation mittels DJL.
 */
public class Inference {

    Predictor<Image, Classifications> predictor;

    // Verzeichnis, in dem sich die Modell-Dateien (.params) und synset.txt befinden
    private static final Path MODEL_DIR = Paths.get("models");

    /**
     * Initialisiert den DJL-Model-Loader mit Translator für Pre-/Post-Processing.
     * Wirft IllegalStateException, wenn Laden fehlschlägt.
     */
    public Inference() {
        try {
            Model model = Models.getModel();
            Path modelDir = Paths.get("models");
            model.load(modelDir, Models.MODEL_NAME);

            // define a translator for pre and post processing
            Translator<Image, Classifications> translator = ImageClassificationTranslator.builder()
                    .addTransform(new Resize(Models.IMAGE_WIDTH, Models.IMAGE_HEIGHT))
                    .addTransform(new ToTensor())
                    .optApplySoftmax(true)
                    .build();
            predictor = model.newPredictor(translator);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Klassifiziert ein Bild aus einem Byte-Array.
     *
     * @param imageData Rohdaten des Bildes
     * @return Klassifikations-Ergebnis mit Labels und Wahrscheinlichkeiten
     * @throws TranslateException falls die Übersetzung fehlschlägt
     */
    public Classifications predict(byte[] image) throws ModelException, TranslateException, IOException {
        InputStream is = new ByteArrayInputStream(image);
        BufferedImage bi = ImageIO.read(is);
        Image img = ImageFactory.getInstance().fromImage(bi);

        Classifications predictResult = this.predictor.predict(img);
        return predictResult;
    }

    /**
     * Listet alle im Modellverzeichnis verfügbaren Modelle auf (Dateien mit
     * .params).
     *
     * @return Liste von Modellnamen ohne Dateiendung
     */
    public List<String> availableModels() {
        try (Stream<Path> files = Files.list(MODEL_DIR)) {
            return files
                    .filter(p -> p.toString().endsWith(".params"))
                    .map(p -> p.getFileName().toString().replaceFirst("\\.params$", ""))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Auslesen der Modelle", e);
        }
    }

    /**
     * Lädt die Datei synset.txt und mappt jede Zeile auf ein LabelInfo-DTO.
     *
     * @return Liste von LabelInfo, Beschreibung momentan leer
     */
    public List<LabelInfo> getLabelInfos() {
        Path synset = MODEL_DIR.resolve("synset.txt");
        try {
            return Files.readAllLines(synset).stream()
                    .map(label -> new LabelInfo(label, "")) // hier kannst du später Beschreibungen ergänzen
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Laden der Labels", e);
        }
    }
}
