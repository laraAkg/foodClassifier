package ch.zhaw.akguelar.food;
public class ClassificationResult {
    private String className;
    private double probability;

    public ClassificationResult(String className, double probability) {
        this.className = className;
        this.probability = probability;
    }

    public String getClassName() { return className; }
    public double getProbability() { return probability; }
}
