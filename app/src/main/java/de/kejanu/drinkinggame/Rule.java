package de.kejanu.drinkinggame;

public class Rule extends Task {

    private static transient TaskType type = TaskType.RULE;
    private int id;
    private int lifeSpan;
    private int requiredNames;

    private String startText;
    private String endText;
    private boolean needsGenderCheck;

    public Rule(int id, int lifeSpan, int requiredNames, String startText, String endText) {
        this.id = id;
        this.lifeSpan = lifeSpan;
        this.requiredNames = requiredNames;
        this.startText = startText;
        this.endText = endText;
    }

    public Rule(int id, int lifeSpan, int requiredNames, String startText, String endText, boolean needsGenderCheck) {
        this.id = id;
        this.lifeSpan = lifeSpan;
        this.requiredNames = requiredNames;
        this.startText = startText;
        this.endText = endText;
        this.needsGenderCheck = needsGenderCheck;
    }

    public TaskType getType() {
        return type;
    }

    public int getRequiredNames() {
        return requiredNames;
    }

    public void setRequiredNames(int requiredNames) {
        this.requiredNames = requiredNames;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(int lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public String getStartText() {
        return startText;
    }

    public void setStartText(String startText) {
        this.startText = startText;
    }

    public String getEndText() {
        return endText;
    }

    public void setEndText(String endText) {
        this.endText = endText;
    }

    public boolean isNeedsGenderCheck() {
        return needsGenderCheck;
    }

    public void setNeedsGenderCheck(boolean needsGenderCheck) {
        this.needsGenderCheck = needsGenderCheck;
    }
}
