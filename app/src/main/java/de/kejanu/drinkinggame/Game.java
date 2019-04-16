package de.kejanu.drinkinggame;

public class Game extends Task {

    private static transient TaskType type = TaskType.GAME;
    private int id;
    private int requiredNames;

    private boolean needsGenderCheck;
    private boolean needsCards;

    private String text;
    private String rulesText;

    public Game(int id, int requiredNames, String text, boolean needsGenderCheck, String rulesText, boolean needsCards) {
        this.id = id;
        this.requiredNames = requiredNames;
        this.text = text;
        this.needsGenderCheck = needsGenderCheck;
        this.rulesText = rulesText;
        this.needsCards = needsCards;
    }

    public String getRulesText() {
        return rulesText;
    }

    public void setRulesText(String rulesText) {
        this.rulesText = rulesText;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isNeedsGenderCheck() {
        return needsGenderCheck;
    }

    public void setNeedsGenderCheck(boolean needsGenderCheck) {
        this.needsGenderCheck = needsGenderCheck;
    }

    public boolean isNeedsCards() {
        return needsCards;
    }

    public void setNeedsCards(boolean needsCards) {
        this.needsCards = needsCards;
    }
}
