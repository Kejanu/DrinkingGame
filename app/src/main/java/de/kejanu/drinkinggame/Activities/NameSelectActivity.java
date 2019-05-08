package de.kejanu.drinkinggame.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Random;

import de.kejanu.drinkinggame.Gender;
import de.kejanu.drinkinggame.Person;
import de.kejanu.drinkinggame.PersonWrapper;
import de.kejanu.drinkinggame.R;

public class NameSelectActivity extends AppCompatActivity {

    int personNumber;
    float scale;

    Button startGameBtn;
    Button addPersonBtn;
    CheckBox activateJokersCb;
    CheckBox hasCardsAvailableCb;
    LinearLayout layout;
    Random randomColor;

    ArrayList<PersonWrapper> personWrapperList;


    //Implemented Kartendeck available
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_select);

        this.startGameBtn = findViewById(R.id.start_game_btn);
        this.addPersonBtn = findViewById(R.id.add_person_btn);
        this.layout = findViewById(R.id.ll_in_scrollview);
        this.scale = getResources().getDisplayMetrics().density;
        this.activateJokersCb = findViewById(R.id.activate_jokers_cb);
        this.hasCardsAvailableCb = findViewById(R.id.nsa_has_cards_available_cb);
        this.randomColor = new Random();
        this.personNumber = 0;
        this.personWrapperList = new ArrayList<>();

        this.addPersonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person("Person " + personNumber, null, null);
                PersonWrapper pWrapper = new PersonWrapper(null, null, person);
                addEdittextWithSpinnerToLayout(pWrapper);

                personWrapperList.add(pWrapper);
            }
        });

        this.startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(goToJokerOrGame(activateJokersCb.isChecked()));
            }
        });

        for (int i = 0; i < 3; ++i) {
            fillWithSample();
        }
    }

    private Intent goToJokerOrGame(boolean jockerChecked) {
        Intent intent;
        if (jockerChecked) {
            intent = new Intent(NameSelectActivity.this, JokerSelectActivity.class);
        }
        else {
            intent = new Intent(NameSelectActivity.this, GameActivity.class);
            intent.putExtra(getResources().getString(R.string.jokers_checked), false);
        }

        intent.putExtra(getResources().getString(R.string.cards_checked), hasCardsAvailableCb.isChecked());
        intent.putParcelableArrayListExtra(getResources().getString(R.string.selected_person_list), createParcelableArrayListExtra());
        return intent;
    }

    private void fillWithSample() {
        Person person = new Person("", null, null);
        PersonWrapper pWrapper = new PersonWrapper(null, null, person);
        addEdittextWithSpinnerToLayout(pWrapper);
        pWrapper.getEditText().setText("Person " + personNumber);

        personWrapperList.add(pWrapper);
    }

    private ArrayList<Person> createParcelableArrayListExtra() {
        ArrayList<Person> pList = new ArrayList<>();
        for (PersonWrapper pWrapper : this.personWrapperList) {
            if (!pWrapper.getEditText().getText().toString().isEmpty()) {
                Person person = pWrapper.getPerson();
                person.setName(pWrapper.getEditText().getText().toString());
                pList.add(person);
            }
        }
        return pList;
    }

    private void addEdittextWithSpinnerToLayout(PersonWrapper pWrapper) {
        View generatedView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.edittext_with_spinner, layout, false);
        EditText et = generatedView.findViewById(R.id.person_et);
        et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        et.setHint(++personNumber + ". Name");
        et.getBackground().setColorFilter(Color.argb(255, randomColor.nextInt(256), randomColor.nextInt(256), randomColor.nextInt(256)), PorterDuff.Mode.SRC_ATOP);

        final Spinner sp = generatedView.findViewById(R.id.person_sp);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 for (PersonWrapper pWrapper : personWrapperList) {
                     if (pWrapper.getSpinner().getId() == sp.getId()) {
                         pWrapper.getPerson().setGender(id == 0 ? Gender.MALE : Gender.FEMALE);
                     }
                 }
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {
                 Log.e("NameSelectActivity", "Spinner: OnNothingSelected Triggered");
             }
        });

        pWrapper.setEditText(et);
        pWrapper.setSpinner(sp);

        layout.addView(generatedView);
    }
}
