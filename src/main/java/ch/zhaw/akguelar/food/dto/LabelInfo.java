package ch.zhaw.akguelar.food.dto;

public class LabelInfo {
    private String className;
    private String description;

    public LabelInfo() {}
    public LabelInfo(String className, String description) {
        this.className = className;
        this.description = description;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
