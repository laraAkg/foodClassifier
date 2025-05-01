package ch.zhaw.akguelar.footwear;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@RestController
public class ClassificationController {

    private final Inference inference = new Inference(); // oder @Autowired

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
