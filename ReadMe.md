
# Food Image Classifier – DJL + Spring Boot

Dieses Projekt implementiert eine Webservice-Anwendung zur **Klassifikation von Lebensmitteln anhand von Bildern**. Es nutzt die **Deep Java Library (DJL)** zur Ausführung eines vortrainierten CNN-Modells (ResNet50) und wird mit **Spring Boot** als REST-API bereitgestellt.

---

## Features

- Klassifikation von Lebensmitteln aus Bilddateien (JPG/PNG)
- REST-API für Healthcheck, verfügbare Modelle, Labels und Inferenz
- Integration eines DJL-Modells mit PyTorch-Backend
- Minimaler Setup-Aufwand dank Spring Boot Starter
- Möglichkeit zum eigenen Modelltraining mit DJL

---

## Verwendete Technologien

- **Java 11+**
- **Spring Boot**
- **Deep Java Library (DJL)**
  - `djl-api`, `djl-model-zoo`, `djl-pytorch-engine`, `djl-basicmodelzoo`
- **Maven** zur Abhängigkeitsverwaltung

---

## Projektstruktur

```
Projekt2MDM/
├── food/
│   ├── src/main/java/ch/zhaw/akguelar/food/
│   │   ├── FoodApplication.java         # Spring Boot Startpunkt
│   │   ├── ClassificationController.java # REST-Endpunkte
│   │   ├── Inference.java               # Klassifikationslogik mit DJL
│   │   ├── Models.java                  # Modellinitialisierung
│   │   ├── Training.java                # Optionales Modelltraining
│   │   ├── dto/
│   │   │   ├── LabelInfo.java           # DTO für Labels
│   │   │   └── ClassificationResult.java # DTO für Ergebnisse
│   ├── models/                          # Enthält foodclassifier.params + │   │    ├── synset.txt
│   │    └── foodclassifier-0002.params
│   ├── pom.xml                          # Maven Build-Konfiguration
```

---

## Setup-Anleitung

### Voraussetzungen

- Java 11 oder höher installiert
- Maven installiert
- Internetverbindung (zum Laden des Modells)

### Lokale Ausführung

1. Projekt klonen:
   ```bash
   git clone https://github.com/laraAkg/foodClassifier.git
   cd Projekt2MDM/food
   ```

2. Abhängigkeiten installieren:
   ```bash
   mvn clean install
   ```

3. Anwendung starten:
   ```bash
   mvn spring-boot:run
   ```

---

## 🧪 API-Endpunkte

| Methode | Pfad         | Beschreibung                                |
|--------:|--------------|---------------------------------------------|
| GET     | `/health`    | Gibt Server-Status, Version & Timestamp zurück |
| GET     | `/models`    | Listet verfügbare Modelle im `models/`-Ordner |
| GET     | `/labels`    | Gibt alle verfügbaren Klassifikationslabels |
| POST    | `/analyze`   | Klassifiziert hochgeladenes Bild            |

---


## Modelltraining (optional)

Du kannst dein eigenes Modell mit der Klasse `Training.java` trainieren:

```bash
mvn exec:java -Dexec.mainClass="ch.zhaw.akguelar.food.Training"
```

Dabei wird der Datensatz aus dem Ordner `food-101-tiny/` verwendet.  
➡️ **Wichtig**: Lade den Datensatz vorher von [dieser Kaggle-Seite](https://www.kaggle.com/datasets/msarmi9/food101tiny?utm_source=chatgpt.com) herunter und speichere ihn im Projektverzeichnis.

Nach dem Training wird ein neues Modell im Ordner `models/` gespeichert.


---

## 👨‍💻 Autor

Lara Akgün – ZHAW Projekt im Modul *Model Deployment & Maintenance*

---

