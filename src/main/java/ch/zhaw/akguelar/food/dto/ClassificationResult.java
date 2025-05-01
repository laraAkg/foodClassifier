package ch.zhaw.akguelar.food.dto;

public class ClassificationResult {
    private String className;
    private double probability;

    public ClassificationResult() {}
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
