package de.kejanu.drinkinggame.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
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
    LinearLayout layout;
    Random randomColor;

    ArrayList<PersonWrapper> personWrapperList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_select);

        this.startGameBtn = findViewById(R.id.start_game_btn);
        this.addPersonBtn = findViewById(R.id.add_person_btn);
        this.layout = findViewById(R.id.ll_in_scrollview);
        this.scale = getResources().getDisplayMetrics().density;
        this.activateJokersCb = findViewById(R.id.activate_jokers_cb);
        randomColor = new Random();

        personNumber = 0;
        this.personWrapperList = new ArrayList<>();

        this.addPersonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person("Person " + personNumber, null, null);
                PersonWrapper pWrapper = new PersonWrapper(createEditText(), createSpinner(), person);

                personWrapperList.add(pWrapper);
                displayPersonOnActivity(pWrapper);
            }
        });

        this.startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (activateJokersCb.isChecked()) {
                    intent = new Intent(NameSelectActivity.this, JokerSelectActivity.class);
                }
                else {
                    intent = new Intent(NameSelectActivity.this, GameActivity.class);
                }

                String intentKey = getResources().getString(R.string.selected_person_list);
                intent.putParcelableArrayListExtra(intentKey, createParcelableArrayListExtra());
                startActivity(intent);
            }
        });

        for (int i = 0; i < 3; ++i) {
            fillWithSample();
        }
    }

    private void fillWithSample() {
        Person person = new Person("", null, null);
        PersonWrapper pWrapper = new PersonWrapper(createEditText(), createSpinner(), person);
        pWrapper.getEditText().setText("Person " + personNumber);

        personWrapperList.add(pWrapper);
        displayPersonOnActivity(pWrapper);
    }

    private void displayPersonOnActivity(PersonWrapper pWrapper) {
        LinearLayout ll = new LinearLayout(NameSelectActivity.this.getApplicationContext());
        ll.setGravity(Gravity.CENTER);
        ll.addView(pWrapper.getEditText());
        ll.addView(pWrapper.getSpinner());
        layout.addView(ll);
    }

    private Spinner createSpinner() {
        final Spinner sp = new Spinner(NameSelectActivity.this.getApplicationContext());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_spinner,
                                                                             android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        int id = View.generateViewId();
        sp.setId(id);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (PersonWrapper pWrapper : personWrapperList) {
                    if (pWrapper.getSpinner().getId() == sp.getId()) {
                        Log.e("Spinner", pWrapper.getPerson().getName());

                        pWrapper.getPerson().setGender(id == 0 ? Gender.MALE : Gender.FEMALE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("Spinner", "Something is wrong");
            }
        });

        return sp;
    }

    private EditText createEditText() {
        EditText editText = new EditText(NameSelectActivity.this.getApplicationContext());

        editText.setId(View.generateViewId());
        editText.setEms(10);
        editText.setMaxLines(1);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(14)});
        editText.setHint(++personNumber + ". Name");

        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0,(int) (10 * scale + 0.5f));
        editText.setLayoutParams(lp);

        editText.getBackground().setColorFilter(Color.argb(255, randomColor.nextInt(256),
                randomColor.nextInt(256), randomColor.nextInt(256)), PorterDuff.Mode.SRC_ATOP);

        editText.setTextColor(Color.parseColor("#000000"));
        editText.setHintTextColor(Color.parseColor("#808080"));

        editText.setPaddingRelative((int) (5 * scale + 0.5f), 0, 0, (int) (10 * scale + 0.5f));
        return editText;
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
}
