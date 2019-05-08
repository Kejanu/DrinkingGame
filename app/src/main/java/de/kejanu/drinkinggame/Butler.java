package de.kejanu.drinkinggame;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

public class Butler {

    private Context context;

    public Butler(Context context) {
        this.context = context;
    }

    public void addToActiveRules(ArrayList<Rule> rList) {
        //rList.add(new Rule(1, 4, 2, "Magnus hat kleine Eier.", ""));
//        rList.add(new Rule(1, 4, 2, "Magnus hat kleine Eier und darf deshalb jedesmal sich einscheißen.Magnus hat kleine Eier und darf deshalb jedesmal sich einscheißen.", ""));
//        rList.add(new Rule(1, 4, 2, "Magnus hat kleine Eier und darf deshalb jedesmal sich einscheißen.Magnus hat kleine Eier und darf deshalb jedesmal sich einscheißen.", ""));
    }

    public void logList(ArrayList<Person> pList) {
        for (Person p : pList) {
            if (p != null) {
                Log.e("Parcelable", p.toString());
            }
        }
    }

    public String loadJSONFromAsset(String source) {
        String json;
        try {
            InputStream is = context.getAssets().open(source);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        //Log.e("JSON", json);
        return json;
    }

    public TextView createTextView(String content) {
        float scale = context.getResources().getDisplayMetrics().density;
        TextView tw = new TextView(context);

        tw.setId(View.generateViewId());
        tw.setTextSize(18);
        tw.setText(content);
        tw.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0,(int) (10 * scale + 0.5f));
        tw.setLayoutParams(lp);

        tw.setTextColor(Color.parseColor("#000000"));
        return tw;
    }
}
