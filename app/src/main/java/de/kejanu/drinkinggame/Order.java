package de.kejanu.drinkinggame;

public class Order extends Task {

    private boolean multiOrder;
    private String[] secondTextArray;

    public Order(int id, String text, boolean multiOrder, int requiredNames, String[] secondTextArray, boolean needsGenderCheck) {
        super(id, requiredNames, text, needsGenderCheck);
        this.multiOrder = multiOrder;
        this.secondTextArray = secondTextArray;
    }

    @Override
    public String toString() {
        String arrayValues = "";

        if (this.isMultiOrder()) {
            for (String str : this.secondTextArray)
                arrayValues += " " + str;
        }
        return "id : " + this.getId() +
                " text: " + this.getText() +
                " multiorder: " + this.multiOrder +
                " requiredNames: " + this.getRequiredNames() +
                " array: " + arrayValues;
    }

    public String[] getSecondTextArray() {
        return secondTextArray;
    }

    public boolean isMultiOrder() {
        return multiOrder;
    }
}
