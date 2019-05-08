package de.kejanu.drinkinggame;

public class Task {
    private int id;
    private int requiredNames;
    private String text;
    private boolean needsGenderCheck;

    public Task(int id, int requiredNames, String text, boolean needsGenderCheck) {
        this.id = id;
        this.requiredNames = requiredNames;
        this.text = text;
        this.needsGenderCheck = needsGenderCheck;
    }

    public boolean isNeedsGenderCheck() {
        return needsGenderCheck;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRequiredNames() {
        return requiredNames;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
