package de.kejanu.drinkinggame;

public class Order extends Task {

    private static transient TaskType type = TaskType.ORDER;
    private int id;
    private String text;
    private boolean multiOrder;
    private int requiredNames;
    private String[] secondTextArray;
    private boolean needsGenderCheck;

    public Order(int id, String text, boolean multiOrder, int requiredNames) {
        this(id, text, multiOrder, requiredNames,null, false);
    }

    public Order(int id, String text, boolean multiOrder, int requiredNames, String[] secondTextArray, boolean needsGenderCheck) {
        this.id = id;
        this.text = text;
        this.multiOrder = multiOrder;
        this.requiredNames = requiredNames;
        this.secondTextArray = secondTextArray;
        this.needsGenderCheck = needsGenderCheck;
    }

    @Override
    public String toString() {
        String arrayValues = "";

        if (this.isMultiOrder()) {
            for (String str : this.secondTextArray)
                arrayValues += " " + str;
        }
        return "id : " + this.id + " text: " + this.text + " multiorder: " + this.multiOrder + " requiredNames: " + this.requiredNames + " array: " + arrayValues;
    }

    public TaskType getType() {
        return type;
    }

    public String[] getSecondTextArray() {
        return secondTextArray;
    }

    public void setSecondTextArray(String[] secondTextArray) {
        this.secondTextArray = secondTextArray;
    }

    public int getRequiredNames() {
        return requiredNames;
    }

    public void setRequiredNames(int requiredNames) {
        this.requiredNames = requiredNames;
    }

    public boolean isMultiOrder() {
        return multiOrder;
    }

    public void setMultiOrder(boolean multiOrder) {
        this.multiOrder = multiOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isNeedsGenderCheck() {
        return needsGenderCheck;
    }

    public void setNeedsGenderCheck(boolean needsGenderCheck) {
        this.needsGenderCheck = needsGenderCheck;
    }
}
