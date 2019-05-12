package de.kejanu.drinkinggame;

import android.content.Context;

import androidx.constraintlayout.widget.ConstraintLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class RuleHelper {

    private Context context;
    private View popupView;
    private LinearLayout llInPopup;

    public RuleHelper(Context context) {
        this.context = context;
    }

    public void setDisplayRuleListener(Button displayRuleBtn, final boolean rulesEmtpy, final ConstraintLayout constraintLayout, final ArrayList<Rule> activeRules) {
        popupView = LayoutInflater.from(context).inflate(R.layout.display_rules_popup, constraintLayout, false);
        llInPopup = popupView.findViewById(R.id.ll_popup_rules);

        displayRuleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rulesEmtpy) {
                    Toast.makeText(context, "Es gibt keine aktiven Regeln", Toast.LENGTH_SHORT).show();
                    return;
                }

                llInPopup.removeAllViews();

                for (Rule activeRule : activeRules) {
                    TextView twInPopup = (TextView) LayoutInflater.from(context).inflate(R.layout.display_rules_popup_tw, llInPopup, false);
                    twInPopup.setText(activeRule.getText());
                    llInPopup.addView(twInPopup);
                }

                PopupWindow pw = new PopupWindow(popupView, ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT, true);
                pw.setTouchable(true);
                pw.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);
            }
        });
    }

    public void setRandomLifeSpanForRules(ArrayList<Rule> ruleList) {
        Random r = new Random();
        int lb = 10;
        int up = 15;

        for (Rule rule : ruleList) {
            rule.setLifeSpan(r.nextInt(up - lb) + lb);
        }
    }
}
