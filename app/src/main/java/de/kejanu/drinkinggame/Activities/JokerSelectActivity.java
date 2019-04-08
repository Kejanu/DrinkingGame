package de.kejanu.drinkinggame.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

import javax.security.auth.login.LoginException;

import de.kejanu.drinkinggame.Joker;
import de.kejanu.drinkinggame.JokerAdapter;
import de.kejanu.drinkinggame.Person;
import de.kejanu.drinkinggame.R;
import de.kejanu.drinkinggame.Butler;

public class JokerSelectActivity extends AppCompatActivity {

    private TextView twDisplayName;
    private Button btnNextName;
    private int currentIndex;

    private Butler butler;
    private JokerAdapter jAdapter;

    private ArrayList<Person> pList;
    private ArrayList<Joker> jList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joker_select);

        this.twDisplayName = findViewById(R.id.tw_joker_display_name);
        this.btnNextName = findViewById(R.id.btn_joker_next_name);
        this.listView = findViewById(R.id.joker_listview);
        this.butler = new Butler(this);

        this.currentIndex = 0;

        pList = getIntent().getParcelableArrayListExtra(getResources().getString(R.string.selected_person_list));
        //logList(pList);

        if (!readJSON()) {
            this.twDisplayName.setText(getResources().getString(R.string.error_reading_json));
            return;
        }


        this.jAdapter = new JokerAdapter(JokerSelectActivity.this, this.jList);
        listView.setAdapter(jAdapter);

        displayNextName(currentIndex);

        this.btnNextName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentIndex < pList.size() - 1) {
                    saveJokersToPerson(currentIndex);
                    displayNextName(++currentIndex);

                    if (currentIndex == pList.size() - 1)
                        btnNextName.setText(getResources().getString(R.string.btn_joker_finish_select));
                }
                else {
                    saveJokersToPerson(currentIndex);
                    goToGameActivity();
                }
            }
        });
    }

    private void goToGameActivity() {
//        for (Person p : this.pList) {
//            Log.e("Joker", p.toString());
//        }

        Intent intent = new Intent(JokerSelectActivity.this, GameActivity.class);
        String intentKey = getResources().getString(R.string.selected_person_list);
        intent.putParcelableArrayListExtra(intentKey, this.pList);
        startActivity(intent);

    }

    private void saveJokersToPerson(int index) {
        Person person = this.pList.get(index);
        person.setJokerList(new ArrayList<Joker>());

        for (Iterator<Joker> it = this.jList.iterator(); it.hasNext();) {
            Joker joker = it.next();
            if (joker.isChecked()) {
                person.getJokerList().add(joker.checkedJoker());
                it.remove();
            }
        }
        this.jAdapter.notifyDataSetChanged();
    }

    private void displayNextName(int index) {
        String name = pList.get(index).getName();
        this.twDisplayName.setText(name);
    }

    private boolean readJSON() {
        Type jListType = new TypeToken<ArrayList<Joker>>(){}.getType();
        String content = butler.loadJSONFromAsset("jokers.json");

        if (content == null) {
            Log.e("Joker", "JSON couldnt be read properly. Exiting");
            return false;
        }

        this.jList = new Gson().fromJson(content, jListType);
        return true;
    }
}
