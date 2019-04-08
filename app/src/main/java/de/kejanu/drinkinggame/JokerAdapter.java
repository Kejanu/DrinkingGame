package de.kejanu.drinkinggame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class JokerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Joker> jList;

    public JokerAdapter(Context context, ArrayList<Joker> jList) {
        this.context = context;
        this.jList = jList;
    }

    private class ViewHolder {
        private TextView tvJokerText;
        private CheckBox cbJokerChecked;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // Holder of the views to be reused
        final ViewHolder viewHolder;

        // No previous views found
        if (convertView == null) {
            // Create the container ViewHolder
            viewHolder = new ViewHolder();

            // Inflate the views from layout for the new row
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.joker_adapter, parent, false);

            // Set the views to the ViewHolder
            viewHolder.tvJokerText = convertView.findViewById(R.id.tw_joker_adapter);
            viewHolder.cbJokerChecked = convertView.findViewById(R.id.cb_joker_adapter);

            // Save the ViewHolder to be reused later
            convertView.setTag(viewHolder);
        }
        else {
            // There is already a ViewHolder, reuse it
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Now we can populate the views via the ViewHolder
        viewHolder.tvJokerText.setText(jList.get(position).getText());
        viewHolder.cbJokerChecked.setChecked(jList.get(position).isChecked());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ticked = !viewHolder.cbJokerChecked.isChecked();
                viewHolder.cbJokerChecked.setChecked(ticked);
                jList.get(position).setChecked(ticked);

                //Toast.makeText(context, "Clicked whole row", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return jList.size();
    }

    @Override
    public String getItem(int position) {
        return jList.get(position).getText();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
