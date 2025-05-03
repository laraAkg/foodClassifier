package ch.zhaw.akguelar.food.dto;

/**
 * Repräsentiert das Ergebnis einer Bildklassifikation.
 * Dieses DTO ist unveränderlich (immutable) und validiert Eingaben im
 * Konstruktor.
 *
 * @param className   Der technische Name des Labels (z. B. "apple_pie").
 * @param probability Die Wahrscheinlichkeit für dieses Label (0.0 bis 1.0).
 */
public class ClassificationResult {
    private String className;
    private double probability;

    public ClassificationResult() {
    }

    public ClassificationResult(String className, double probability) {
        this.className = className;
        this.probability = probability;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
}
