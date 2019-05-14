package de.kejanu.drinkinggame.Activities;

import android.graphics.Color;
import android.os.SystemClock;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import de.kejanu.drinkinggame.Game;
import de.kejanu.drinkinggame.GameHelper;
import de.kejanu.drinkinggame.Joker;
import de.kejanu.drinkinggame.Order;
import de.kejanu.drinkinggame.Person;
import de.kejanu.drinkinggame.R;
import de.kejanu.drinkinggame.Butler;
import de.kejanu.drinkinggame.Rule;
import de.kejanu.drinkinggame.RuleHelper;
import de.kejanu.drinkinggame.Task;
import de.kejanu.drinkinggame.TaskHelper;
import de.kejanu.drinkinggame.Testing.JokerDialogFragment;

public class GameActivity extends AppCompatActivity implements JokerDialogFragment.JokerDialogListener {

    // Color Strings
    String ruleBgColor = "#0c69ff";
    String orderBgColor = "#0da300";
    String gameBgColor = "#ddf925";

    float scale;
    private int difficulty;

    ArrayList<Person> pList;

    ArrayList<Order> orderList = new ArrayList<>();
    ArrayList<Rule> ruleList = new ArrayList<>();
    ArrayList<Game> gameList = new ArrayList<>();
    ArrayList<Task> taskList = new ArrayList<>();

    ArrayList<Rule> activeRules = new ArrayList<>();

    private Button displayGameRulesBtn;
    private Button btnDisplayJokers;
    private Button btnDisplayRules;

    Task currentTask;

    ConstraintLayout constraintLayout;
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
        constraintLayout = findViewById(R.id.game_activity_layout);
        btnDisplayJokers = findViewById(R.id.btn_joker_ingame);
        btnDisplayRules = findViewById(R.id.btn_rules_ingame);
        scale = getResources().getDisplayMetrics().density;
        displayGameRulesBtn = findViewById(R.id.btn_display_game_rules);
        difficulty = 10;

        if (!getIntent().getBooleanExtra(getResources().getString(R.string.jokers_checked), true)) {
            btnDisplayJokers.setVisibility(View.INVISIBLE);
        }
        createDisplayJokersOnClickListener();

        this.pList = getIntent().getParcelableArrayListExtra(getResources().getString(R.string.selected_person_list));

        if (!fillListsFromSource("orders_test.json")) {
            this.twDisplayTask.setText(getResources().getString(R.string.error_reading_json));
            return;
        }

        RuleHelper ruleHelper = new RuleHelper(getApplicationContext());
        ruleHelper.setDisplayRuleListener(btnDisplayRules, ruleList.isEmpty(), constraintLayout, activeRules);
        ruleHelper.setRandomLifeSpanForRules(ruleList);

        if (!getIntent().getBooleanExtra(getResources().getString(R.string.cards_checked), false)) {
            GameHelper gameHelper = new GameHelper();
            gameHelper.removeCardGames(this.gameList);
        }

        TaskHelper taskHelper = new TaskHelper();
        this.taskList.addAll(this.orderList);
        this.taskList.addAll(this.ruleList);
        this.taskList.addAll(this.gameList);

        taskHelper.removeTasksWhichNeedMoreNames(taskList, pList.size());
        taskHelper.populateTasksWithNames(taskList, pList);
        taskHelper.populateDrinkingAmount(taskList, difficulty);

        Collections.shuffle(this.taskList);

        // Create artifical input lag, so you can't skip tasks accidentally
        // The listener runs the next Task
        setLayoutOnClickListener();

        // Run inital Task
        runNextTask();

        Log.i("Initialization", "Init completed");
    }

    private void setLayoutOnClickListener() {
        this.constraintLayout.setOnClickListener(new View.OnClickListener() {
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

    private void createDisplayJokersOnClickListener() {
        this.btnDisplayJokers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.display_jokers_popup, constraintLayout, false);
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

                                final View generatedView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.display_jokers_with_checkbox, constraintLayout, false);
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

                popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);
            }
        });
    }



    private boolean fillListsFromSource(String... source) {
        Gson gson = new Gson();
        Butler readJSONHelper = new Butler(this);

        try {
            for (String s : source) {
                if (s.contains("game")) {
                    Type gameListType = new TypeToken<ArrayList<Game>>(){}.getType();
                    this.gameList = gson.fromJson(readJSONHelper.loadJSONFromAsset(s), gameListType);
                }

                if (s.contains("rules")) {
                    Type ruleListType = new TypeToken<ArrayList<Rule>>(){}.getType();
                    this.ruleList = gson.fromJson(readJSONHelper.loadJSONFromAsset(s), ruleListType);
                }

                if (s.contains("order")) {
                    Type orderListType = new TypeToken<ArrayList<Order>>(){}.getType();
                    this.orderList = gson.fromJson(readJSONHelper.loadJSONFromAsset(s), orderListType);
                }
            }
        }
        catch (JsonParseException jpe) {
            return false;
        }
        return true;
    }

    private void endGame() {
        this.twDisplayTask.setText(getResources().getString(R.string.game_end));
        this.constraintLayout.setBackgroundColor(Color.parseColor(orderBgColor));
    }

    private boolean checkRules() {

        if (currentTask == null)
            return false;

        if (currentTask instanceof Order && ((Order) currentTask).isMultiOrder())
            return false;

        for (int i = 0; i < this.activeRules.size(); i++) {
            Rule activeRule = this.activeRules.get(i);
            activeRule.setLifeSpan(activeRule.getLifeSpan() - 1);
            if (activeRule.getLifeSpan() <= 0) {
                this.constraintLayout.setBackgroundColor(Color.parseColor(ruleBgColor));
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
                this.constraintLayout.setBackgroundColor(Color.parseColor(orderBgColor));
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
        this.constraintLayout.setBackgroundColor(Color.parseColor(orderBgColor));
        return false;
    }

    private void executeRule(Task currentTask) {
        Rule currentRule = (Rule) currentTask;
        displayNextText(currentRule.getText());

        this.constraintLayout.setBackgroundColor(Color.parseColor(ruleBgColor));
        this.activeRules.add(currentRule);
    }

    private void executeGame(Task currentTask) {
        final Game currentGame = (Game) currentTask;
        this.constraintLayout.setBackgroundColor(Color.parseColor(gameBgColor));

        if (currentGame.getRulesText() != null) {
            // Display Rules in Layout
            displayGameRulesBtn.setVisibility(View.VISIBLE);
            displayGameRulesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayGameRulesPopUp(currentGame.getRulesText());
                }
            });
        }

        displayNextText(currentGame.getText());
    }

    private void displayGameRulesPopUp(String rules) {
        View inflatedView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.game_rules_popup, constraintLayout, false);
        TextView tw = inflatedView.findViewById(R.id.tw_ingame_game_rules);
        tw.setText(rules);

        PopupWindow popupWindow = new PopupWindow(inflatedView, ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);

        popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);
    }

    private void runNextTask() {
        displayGameRulesBtn.setVisibility(View.INVISIBLE);

        if (this.taskList.size() == 0) {
            endGame();
            return;
        }

        if(checkRules())
            return;

        currentTask = this.taskList.get(0);

        if (currentTask instanceof Order) {
            if (executeOrder(currentTask))
                return;
        }
        else if (currentTask instanceof Rule){
            executeRule(currentTask);
        }
        else if (currentTask instanceof Game) {
            executeGame(currentTask);
        }
        else {
            Log.e("GameActivity", "CurrentTask is neither Order nor Rule nor Game");
        }

        this.taskList.remove(0);
        Collections.shuffle(this.pList);
    }

    private void displayNextText(String currenText) {
        twDisplayTask.setText(currenText);
    }
}
