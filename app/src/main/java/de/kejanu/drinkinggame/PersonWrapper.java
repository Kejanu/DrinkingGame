package de.kejanu.drinkinggame;

import android.widget.EditText;
import android.widget.Spinner;

public class PersonWrapper {
    private EditText editText;
    private Spinner spinner;
    private Person person;

    public PersonWrapper(EditText editText, Spinner spinner, Person person) {
        this.editText = editText;
        this.spinner = spinner;
        this.person = person;
    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public Spinner getSpinner() {
        return spinner;
    }

    public void setSpinner(Spinner spinner) {
        this.spinner = spinner;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
