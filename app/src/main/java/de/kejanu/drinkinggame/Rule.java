package de.kejanu.drinkinggame;

public class Rule extends Task {

    private int lifeSpan;

    // String text is basically startText
    private String endText;

    public Rule(int id, int lifeSpan, int requiredNames, String text, String endText, boolean needsGenderCheck) {
        super(id, requiredNames, text, needsGenderCheck);
        this.lifeSpan = lifeSpan;
        this.endText = endText;
    }

    public int getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(int lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public String getEndText() {
        return endText;
    }

    public void setEndText(String endText) {
        this.endText = endText;
    }
}
