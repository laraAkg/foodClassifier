package ch.zhaw.akguelar.food;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ch.zhaw.akguelar.food.dto.LabelInfo;
import ch.zhaw.akguelar.food.dto.ModelInfo;

/**
 * REST-Controller für Bildklassifikation:
 * - Healthcheck
 * - Auflisten verfügbarer Modelle und Labels
 * - Inferenz (Bild-Upload & Klassifikation)
 */
@RestController
public class ClassificationController {

    private final Inference inference = new Inference();

    /**
     * Gibt Metadaten zu allen Labels zurück (Name + Beschreibung).
     *
     * @return Liste von LabelInfo-DTOs
     */
    @GetMapping("/labels")
    public List<LabelInfo> getLabels() {
        // Rückgabe z.B. aller Klassennamen + Beschreibung
        return inference.getLabelInfos();
    }

    /**
     * Einfacher Healthcheck.
     *
     * @return Status, Version und Server-Zeitpunkt
     */
    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
                "status", "UP",
                "version", "1.0.0",
                "timestamp", Instant.now().toString());
    }


    /**
     * Verarbeitet GET-Anfragen an den Endpunkt "/models" und gibt eine Liste der verfügbaren Modelle zurück.
     * Jedes Modell wird durch ein {@link ModelInfo}-Objekt repräsentiert, das seinen Namen, die Genauigkeit 
     * und die Version enthält. Die Methode ordnet spezifischen Modellnamen vordefinierte Genauigkeits- und 
     * Versionswerte zu, während für unbekannte Modelle ein Standard-Fallback bereitgestellt wird.
     *
     * @return eine Liste von {@link ModelInfo}-Objekten, die die verfügbaren Modelle repräsentieren.
     */
    @GetMapping("/models")
    public List<ModelInfo> listModels() {
        return inference.availableModels().stream()
                .map(name -> {
                    return switch (name) {
                        case "foodclassifier-0005" -> new ModelInfo(name, 0.91, 10);
                        case "foodclassifier-0002" -> new ModelInfo(name, 0.88, 8);
                        default -> new ModelInfo(name, null, null); // Default-Fallback
                    };
                })
                .collect(Collectors.toList());
    }

    /**
     * Nimmt ein Bild entgegen, führt die Klassifikation durch
     * und gibt eine sortierte Liste von Ergebnissen zurück.
     *
     * @param image MultipartFile mit dem Bild (JPG/PNG o.ä.)
     * @return Liste von ClassificationResult, absteigend nach Wahrscheinlichkeit
     */
    @PostMapping(path = "/analyze", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ClassificationResult> predict(@RequestParam("image") MultipartFile image) throws Exception {

        // Validierung
        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("Kein Bild übermittelt.");
        }

        // Logging
        System.out.println("[INFO] Bild empfangen: " + image.getOriginalFilename() +
                " (" + image.getSize() + " Bytes)");

        // Inferenz und Aufbereitung als JSON-kompatible Liste
        return inference.predict(image.getBytes())
                .items().stream()
                .map(item -> new ClassificationResult(item.getClassName(), item.getProbability()))
                .sorted((a, b) -> Double.compare(b.getProbability(), a.getProbability()))
                .collect(Collectors.toList());
    }
}
