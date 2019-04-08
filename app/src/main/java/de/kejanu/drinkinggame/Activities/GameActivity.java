package de.kejanu.drinkinggame.Activities;

import android.graphics.Color;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import de.kejanu.drinkinggame.Game;
import de.kejanu.drinkinggame.Gender;
import de.kejanu.drinkinggame.Joker;
import de.kejanu.drinkinggame.Order;
import de.kejanu.drinkinggame.Person;
import de.kejanu.drinkinggame.R;
import de.kejanu.drinkinggame.Butler;
import de.kejanu.drinkinggame.Rule;
import de.kejanu.drinkinggame.Task;
import de.kejanu.drinkinggame.TaskType;
import de.kejanu.drinkinggame.Testing.JokerDialogFragment;

public class GameActivity extends AppCompatActivity implements JokerDialogFragment.JokerDialogListener {

    // Color Strings
    String ruleBgColor = "#0c69ff";
    String orderBgColor = "#0da300";
    String gameBgColor = "#ddf925";

    // Gender Stuff
    // {n0g0} => name0 + genderStuff0
    String[] genderTextsMale = {"ihm", "seine"};
    String[] genderTextsFemale = {"ihr", "ihre"};

    // {seine0}

    float scale;

    ArrayList<Person> pList;

    ArrayList<Order> orderList = new ArrayList<>();
    ArrayList<Rule> ruleList = new ArrayList<>();
    ArrayList<Game> gameList = new ArrayList<>();
    ArrayList<Task> taskList = new ArrayList<>();

    ArrayList<Rule> activeRules = new ArrayList<>();

    private Button btnDisplayJokers;
    private Button btnDisplayRules;

    Task currentTask;

    ConstraintLayout layout;
    TextView twDisplayTask;
    LinearLayout lljokers;

    boolean currentIsMulti;
    long lastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        twDisplayTask = findViewById(R.id.tw_display_task);
        twDisplayTask.setTextColor(Color.parseColor("#000000"));
        layout = findViewById(R.id.game_activity_layout);
        this.scale = getResources().getDisplayMetrics().density;


//        new Butler(this).addToActiveRules(this.activeRules);

        // Buttons
        this.btnDisplayJokers = findViewById(R.id.btn_joker_ingame);
        this.btnDisplayRules = findViewById(R.id.btn_rules_ingame);

        createOnClickListenersForButtons();

        this.pList = getIntent().getParcelableArrayListExtra(getResources().getString(R.string.personlist_with_jokers));

        //new Butler(this).logList(this.pList);

//        if (!fillListsFromSource("orders.json", "games.json", "rules.json")) {
//            this.twDisplayTask.setText(getResources().getString(R.string.error_reading_json));
//            return;
//        }

        if (!fillListsFromSourceTest("orders_test.json")) {
            this.twDisplayTask.setText(getResources().getString(R.string.error_reading_json));
            return;
        }

        removeTasksWhichNeedMoreNames();
        setRandomLifeSpanForRules();
        populateTasksWithNames();

        this.taskList.addAll(this.orderList);
        this.taskList.addAll(this.ruleList);
        this.taskList.addAll(this.gameList);

        //logListSizes();

        Collections.shuffle(this.taskList);

        setLayoutOnClickListener();
        runNextTask();

        Log.i("Initialization", "Init completed");
    }

    private void setLayoutOnClickListener() {
        this.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastClickTime < 500){
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                runNextTask();
            }
        });
    }

    private void logListSizes() {
        Log.e("OrderListSize", this.orderList.size() + "");
        Log.e("RuleListSize", this.ruleList.size() + "");
        Log.e("GameListSize", this.gameList.size() + "");
    }

    private void createDialogFragment(View v) {
        JokerDialogFragment jdf = new JokerDialogFragment();
        jdf.setV(v);
        jdf.show(getSupportFragmentManager(), "JokerDialogFragment");
    }

    // The dialog fragment receives a reference to this activity through the
    // Fragment.onAttach() callback, wich it uses to call the following methods
    // defined by the JokerDialogFragment.JokerDialogListener interface

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, View v) {
        //Toast.makeText(this, "PC " + ((Joker) v.getTag()).getText(), Toast.LENGTH_LONG).show();
        ((Joker) v.getTag()).setChecked(false);
        lljokers.removeView(v);

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog, View v) {
        //Toast.makeText(this, "NC " + ((Joker) v.getTag()).getText(), Toast.LENGTH_LONG).show();
    }


    private int scaledToDP(int i) {
        return (int) (i * scale + 0.5f);
    }

    private void createOnClickListenersForButtons() {
        this.btnDisplayJokers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.display_jokers_popup, layout, false);
                LinearLayout llNames = popupView.findViewById(R.id.ll_popup_jokers_names);
                lljokers = popupView.findViewById(R.id.ll_popup_jokers_jokers);

                for (final Person p : pList) {
                    Log.e("Person", p.toString());
                    Button button = new Button(getApplicationContext());
                    button.setText(p.getName());
                    button.setTextSize(scaledToDP(6));

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            lljokers.removeAllViews();

                            for (final Joker j : p.getJokerList()) {

                                if (!j.isChecked())
                                    continue;

                                final View generatedView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.display_jokers_with_checkbox, layout, false);
                                final TextView tw = generatedView.findViewById(R.id.tw_joker_display_jokers);
                                tw.setText(j.getText());

                                generatedView.setTag(j);

                                generatedView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(getApplicationContext(), tw.getText().toString(), Toast.LENGTH_LONG).show();
                                        createDialogFragment(generatedView);
                                    }
                                });

                                lljokers.addView(generatedView);
                            }

                        }
                    });

                    llNames.addView(button);
                }

                PopupWindow popupWindow = new PopupWindow(popupView, ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT, true);
                popupWindow.setTouchable(true);

                popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
            }
        });


        this.btnDisplayRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (activeRules.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Es gibt keine aktiven Regeln", Toast.LENGTH_SHORT).show();
                    return;
                }

                LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.display_rules_popup, layout, false);

                LinearLayout llInPopup = popupView.findViewById(R.id.ll_popup_rules);

                for (Rule activeRule : activeRules) {
                    TextView tw = new TextView(getApplicationContext());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    lp.setMargins(scaledToDP(5), scaledToDP(5), scaledToDP(5), scaledToDP(5));
                    tw.setLayoutParams(lp);
                    tw.setPadding(scaledToDP(5),scaledToDP(5), scaledToDP(5), scaledToDP(5));
                    tw.setText(activeRule.getStartText());
                    tw.setTextSize(scaledToDP(8));
                    tw.setBackgroundColor(Color.parseColor("#ffffff"));
                    tw.setTextColor(Color.parseColor("#000000"));
                    llInPopup.addView(tw);
                }

                PopupWindow pw = new PopupWindow(popupView, ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT, true);
                pw.setTouchable(true);

                pw.showAtLocation(layout, Gravity.CENTER, 0, 0);

            }
        });
    }

    private String returnGenderTextsReplaced(Person p, String text, int position) {
        // {n0g0} => name0 + genderStuff0

        HashMap<String, String> genderMap = new HashMap<>();
        genderMap.put("ihm", "ihr");
        genderMap.put("seine", "ihre");

//        Gender gender = p.getGender() == Gender.MALE ? this.genderTextsMale : this.genderTextsFemale;
//        for (int i = 0; i < genderArr.length; ++i) {

            for (Map.Entry<String, String> entry : genderMap.entrySet()) {
                String toReplace = "{" + entry.getKey() + position + "}";

                if (text.contains(toReplace)) {
                    text = text.replace(toReplace, p.getGender() == Gender.MALE ? entry.getKey() : entry.getValue());
                }
            }


//            text = text.replace("{n" + position + "g" + i + "}", genderArr[i]);
//        }
        return text;
    }

    private void populateTasksWithNames() {
        for (Order order : this.orderList) {
            for (int i = 0; i < order.getRequiredNames(); ++i) {
                order.setText(order.getText().replace("{name" + i + "}", this.pList.get(i).getName()));

                if (order.isNeedsGenderCheck()) {
                    order.setText(returnGenderTextsReplaced(this.pList.get(i), order.getText(), i));
                }

                if (order.getSecondTextArray() != null) {
                    for (int j = 0; j < order.getSecondTextArray().length; ++j) {
                        order.getSecondTextArray()[j] = order.getSecondTextArray()[j].replace("{name" + i + "}", this.pList.get(i).getName());
                        if (order.isNeedsGenderCheck()) {
                            order.getSecondTextArray()[j] = returnGenderTextsReplaced(this.pList.get(i), order.getSecondTextArray()[j], i);
                        }
                    }
                }
            }
            Collections.shuffle(this.pList);
        }

        for (Rule rule : this.ruleList) {
            for (int i = 0; i < rule.getRequiredNames(); ++i) {
                rule.setStartText(rule.getStartText().replace("{name" + i + "}", this.pList.get(i).getName()));
                rule.setEndText(rule.getEndText().replace("{name" + i + "}", this.pList.get(i).getName()));

                if (rule.isNeedsGenderCheck()) {
                    rule.setStartText(returnGenderTextsReplaced(this.pList.get(i), rule.getStartText(), i));
                    rule.setEndText(returnGenderTextsReplaced(this.pList.get(i), rule.getEndText(), i));
                }
            }
            Collections.shuffle(this.pList);
        }

        for (Game game : this.gameList) {
            for (int i = 0; i < game.getRequiredNames(); ++i) {
                game.setText(game.getText().replace("{name" + i + "}", this.pList.get(i).getName()));
                if (game.isNeedsGenderCheck()) {
                    game.setText(returnGenderTextsReplaced(this.pList.get(i), game.getText(), i));
                }
            }
            Collections.shuffle(this.pList);
        }
    }

    private void setRandomLifeSpanForRules() {
        Random r = new Random();
        int lb = 10;
        int up = 15;

        for (Rule rule : this.ruleList) {
            rule.setLifeSpan(r.nextInt(up - lb) + lb);
        }
    }

    private void removeTasksWhichNeedMoreNames() {
        for (Iterator<Order> it = this.orderList.iterator(); it.hasNext();) {
            if (it.next().getRequiredNames() > this.pList.size()) {
                it.remove();
            }
        }

        for (Iterator<Rule> it = this.ruleList.iterator(); it.hasNext();) {
            if (it.next().getRequiredNames() > this.pList.size()) {
                it.remove();
            }
        }

        for (Iterator<Game> it = this.gameList.iterator(); it.hasNext();) {
            if (it.next().getRequiredNames() > this.pList.size()) {
                it.remove();
            }
        }
    }

    private boolean fillListsFromSourceTest(String... source) {
        Gson gson = new Gson();
        Butler readJSONHelper = new Butler(this);

        Type orderListType = new TypeToken<ArrayList<Order>>(){}.getType();
        this.orderList = gson.fromJson(readJSONHelper.loadJSONFromAsset(source[0]), orderListType);

        if (this.orderList == null) {
            return false;
        }

        return true;
    }

    private boolean fillListsFromSource(String... source) {
        Gson gson = new Gson();
        Butler readJSONHelper = new Butler(this);

        Type orderListType = new TypeToken<ArrayList<Order>>(){}.getType();
        this.orderList = gson.fromJson(readJSONHelper.loadJSONFromAsset(source[0]), orderListType);
//        Log.e("ORDERLIST", this.orderList.size() + "");
//        Log.e("PERSONLIST", this.pList.size() + "");

        Type gameListType = new TypeToken<ArrayList<Game>>(){}.getType();
        this.gameList = gson.fromJson(readJSONHelper.loadJSONFromAsset(source[1]), gameListType);

        Type ruleListType = new TypeToken<ArrayList<Rule>>(){}.getType();
        this.ruleList = gson.fromJson(readJSONHelper.loadJSONFromAsset(source[2]), ruleListType);

        if (this.orderList == null || this.gameList == null || this.ruleList == null) {
            return false;
        }

        return true;
    }

    private void endGame() {
        this.twDisplayTask.setText(getResources().getString(R.string.game_end));
        this.layout.setBackgroundColor(Color.parseColor(orderBgColor));
    }

    private boolean checkRules() {

        if (currentTask == null)
            return false;

        if (currentTask.getType() ==  TaskType.ORDER && ((Order) currentTask).isMultiOrder())
            return false;

        for (int i = 0; i < this.activeRules.size(); i++) {
            Rule activeRule = this.activeRules.get(i);
            activeRule.setLifeSpan(activeRule.getLifeSpan() - 1);
            if (activeRule.getLifeSpan() <= 0) {
                this.layout.setBackgroundColor(Color.parseColor(ruleBgColor));
                displayNextText(activeRule.getEndText());
                this.activeRules.remove(i);
                return true;
            }
        }
        return false;
    }

    private boolean executeOrder(Task currentTask) {
        // return true, if multiorder to return in outer method

        Order currentOrder = (Order) currentTask;
        if (currentOrder.isMultiOrder()) {
            if (!currentIsMulti) {
                this.currentIsMulti = true;
                this.layout.setBackgroundColor(Color.parseColor(orderBgColor));
                displayNextText(currentOrder.getText());
                return true;
            }
            else {
                Random r = new Random();
                int next = r.nextInt(currentOrder.getSecondTextArray().length);
                displayNextText(currentOrder.getSecondTextArray()[next]);
                this.currentIsMulti = false;
            }
        }
        else {
            displayNextText(currentOrder.getText());
        }
        this.layout.setBackgroundColor(Color.parseColor(orderBgColor));
        return false;
    }

    private void executeRule(Task currentTask) {
        Rule currentRule = (Rule) currentTask;
        displayNextText(currentRule.getStartText());

        this.layout.setBackgroundColor(Color.parseColor(ruleBgColor));
        this.activeRules.add(currentRule);
    }

    private void executeGame(Task currentTask) {
        Game currentGame = (Game) currentTask;

        this.layout.setBackgroundColor(Color.parseColor(gameBgColor));
        displayNextText(currentGame.getText());
    }

    private void runNextTask() {
        if (this.taskList.size() == 0) {
            endGame();
            return;
        }

        if(checkRules())
            return;

        currentTask = this.taskList.get(0);

        if (currentTask.getType() == TaskType.ORDER) {
            if (executeOrder(currentTask))
                return;
        }
        else if (currentTask.getType() == TaskType.RULE){
            executeRule(currentTask);
        }
        else if (currentTask.getType() == TaskType.GAME) {
            executeGame(currentTask);
        }
        else {
            Log.e("Tasklog", "Something is going horribly wrong.");
        }

        this.taskList.remove(0);
        Collections.shuffle(this.pList);
    }

    private void displayNextText(String currenText) {
        twDisplayTask.setText(currenText);
    }
}
