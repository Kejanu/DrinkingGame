package de.kejanu.drinkinggame.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import de.kejanu.drinkinggame.Gender;
import de.kejanu.drinkinggame.Person;
import de.kejanu.drinkinggame.PersonWrapper;
import de.kejanu.drinkinggame.R;
import de.kejanu.drinkinggame.Testing.TestingFragments.MainFrgmentHolderActivity;

public class NameSelectActivity extends AppCompatActivity {

    private static final String TAG = "NameSelectActivity";

    // Memeber variables
    private int personNumber;
    private LinearLayout layout;

    // Widgets
    private CheckBox activateJokersCb;
    private CheckBox hasCardsAvailableCb;
    private ImageView settingsIcon;

    ArrayList<PersonWrapper> personWrapperList;


    //Implemented Kartendeck available
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_select);

        Button startGameBtn = findViewById(R.id.start_game_btn);
        Button addPersonBtn = findViewById(R.id.add_person_btn);
        layout = findViewById(R.id.ll_in_scrollview);
        activateJokersCb = findViewById(R.id.activate_jokers_cb);
        hasCardsAvailableCb = findViewById(R.id.nsa_has_cards_available_cb);
        settingsIcon = findViewById(R.id.settings_icon);
        personWrapperList = new ArrayList<>();

        personNumber = 0;
        addPersonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person("Person " + personNumber);
                PersonWrapper pWrapper = new PersonWrapper(null, null, person);
                addEdittextWithSpinnerToLayout(pWrapper);

                personWrapperList.add(pWrapper);
            }
        });

        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(NameSelectActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = createIntent();
//                startActivity(intent);
//                Intent intent = new Intent(NameSelectActivity.this, CategorySelectActivity.class);
//                startActivity(intent);

                Intent intent = new Intent(NameSelectActivity.this, MainFrgmentHolderActivity.class);
                startActivity(intent);
            }
        });

        for (int i = 0; i < 3; ++i) {
            fillWithSample();
        }
    }

    private Intent createIntent() {
        Intent intent;
        // To Joker or Game Activity
        if (activateJokersCb.isChecked()) {
            intent = new Intent(NameSelectActivity.this, JokerSelectActivity.class);
        }
        else {
            intent = new Intent(NameSelectActivity.this, GameActivity.class);
            intent.putExtra(getResources().getString(R.string.jokers_checked), false);
        }
        // Playing Cards available
        intent.putExtra(getResources().getString(R.string.cards_checked), hasCardsAvailableCb.isChecked());

        // Put the person list
        intent.putParcelableArrayListExtra(getResources().getString(R.string.selected_person_list), createPersonList());
        return intent;
    }

    private void fillWithSample() {
        Person person = new Person("");
        PersonWrapper pWrapper = new PersonWrapper(null, null, person);
        addEdittextWithSpinnerToLayout(pWrapper);
        pWrapper.getEditText().setText("Person " + personNumber);

        personWrapperList.add(pWrapper);
    }

    private ArrayList<Person> createPersonList() {
        ArrayList<Person> pList = new ArrayList<>();
        for (PersonWrapper pWrapper : this.personWrapperList) {
            if (!pWrapper.getEditText().getText().toString().isEmpty()) {
                // Name is not empty
                Person person = pWrapper.getPerson();
                person.setName(pWrapper.getEditText().getText().toString());
                person.setGender(pWrapper.getSpinner().getSelectedItemId() == 0 ? Gender.MALE : Gender.FEMALE);

                pList.add(person);
            }
        }
        Log.d(TAG, pList.toString());
        return pList;
    }

    private void addEdittextWithSpinnerToLayout(PersonWrapper pWrapper) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View addToLayoutView = inflater.inflate(R.layout.edittext_with_spinner, layout, false);

        // EditText code
        EditText et = addToLayoutView.findViewById(R.id.person_et);
        et.setHint(++personNumber + ". Name");
        et.getBackground().setColorFilter(getRandomColor(), PorterDuff.Mode.SRC_ATOP);

        // Spinner code
        Spinner sp = addToLayoutView.findViewById(R.id.person_sp);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        pWrapper.setEditText(et);
        pWrapper.setSpinner(sp);

        layout.addView(addToLayoutView);
    }

    private int getRandomColor() {
        return Color.rgb(
            ThreadLocalRandom.current().nextInt(256),
            ThreadLocalRandom.current().nextInt(256),
            ThreadLocalRandom.current().nextInt(256)
        );
    }
}
