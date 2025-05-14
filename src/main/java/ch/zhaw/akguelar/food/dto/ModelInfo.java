package ch.zhaw.akguelar.food.dto;


/**
 * Repräsentiert Informationen zu einem Machine-Learning-Modell.
 * <p>
 * Dieses DTO enthält Metadaten wie den Namen des Modells, die Genauigkeit 
 * (Accuracy) und die Anzahl der Trainings-Epochen.
 *
 * @param name     Der Name des Modells.
 * @param accuracy Die Genauigkeit des Modells als Dezimalwert (z. B. 0.95 für 95%).
 * @param epochs   Die Anzahl der Trainings-Epochen, die für das Modell verwendet wurden.
 */
public class ModelInfo {
    private String name;
    private Double accuracy;
    private Integer epochs;

    public ModelInfo(String name, Double accuracy, Integer epochs) {
        this.name = name;
        this.accuracy = accuracy;
        this.epochs = epochs;
    }

    public String getName() { return name; }
    public Double getAccuracy() { return accuracy; }
    public Integer getEpochs() { return epochs; }
}
