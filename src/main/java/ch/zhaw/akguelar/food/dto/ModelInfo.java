package ch.zhaw.akguelar.food.dto;


public class ModelInfo {
    private String name;
    private Double accuracy;
    private Integer epochs;

    // Konstruktor
    public ModelInfo(String name, Double accuracy, Integer epochs) {
        this.name = name;
        this.accuracy = accuracy;
        this.epochs = epochs;
    }

    // Getter (für JSON serialisierbar mit Spring Boot)
    public String getName() { return name; }
    public Double getAccuracy() { return accuracy; }
    public Integer getEpochs() { return epochs; }
}
