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

@RestController
public class ClassificationController {

    private final Inference inference = new Inference(); // oder @Autowired

    /** 6. Verfügbare Klassen / Label-Metadaten **/
    @GetMapping("/labels")
    public List<LabelInfo> getLabels() {
        // Rückgabe z.B. aller Klassennamen + Beschreibung
        return inference.getLabelInfos();
    }

    /** 1. Healthcheck / Status **/
    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
                "status", "UP",
                "version", "1.0.0",
                "timestamp", Instant.now().toString());
    }

    /** 2. Modelle auflisten **/
    @GetMapping("/models")
    public List<String> listModels() {
        // z.B. aus deinem Inference-Service: inference.availableModels()
        return inference.availableModels();
    }

    @PostMapping(path = "/analyze", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ClassificationResult> predict(@RequestParam("image") MultipartFile image) throws Exception {

        // 🛡 Validierung
        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("Kein Bild übermittelt.");
        }

        // 🧾 Logging
        System.out.println("[INFO] Bild empfangen: " + image.getOriginalFilename() +
                " (" + image.getSize() + " Bytes)");

        // 🧠 Inferenz und Aufbereitung als JSON-kompatible Liste
        return inference.predict(image.getBytes())
                .items().stream()
                .map(item -> new ClassificationResult(item.getClassName(), item.getProbability()))
                .sorted((a, b) -> Double.compare(b.getProbability(), a.getProbability()))
                .collect(Collectors.toList());
    }
}
