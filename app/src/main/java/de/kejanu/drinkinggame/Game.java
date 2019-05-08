package de.kejanu.drinkinggame;

public class Game extends Task {

    private boolean needsCards;
    private String rulesText;

    public Game(int id, int requiredNames, String text, boolean needsGenderCheck, String rulesText, boolean needsCards) {
        super(id, requiredNames, text, needsGenderCheck);
        this.rulesText = rulesText;
        this.needsCards = needsCards;
    }

    public String getRulesText() {
        return rulesText;
    }

    public boolean isNeedsCards() {
        return needsCards;
    }
}
