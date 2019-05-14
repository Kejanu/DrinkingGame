package de.kejanu.drinkinggame.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import de.kejanu.drinkinggame.R;

public class CategoryAdapter extends ArrayAdapter<Category> {

    public CategoryAdapter(@NonNull Context context, int textViewResourceId, @NonNull ArrayList<Category> categories) {
        super(context, textViewResourceId, categories);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category, container, false);
        }

        String name = "Big Uff";
        String summary = "Sunt castores pugna dexter, placidus sectames." +
                "Confucius says: in separate places PLURALappear solitary history. Ho-ho-ho! horror of halitosis.";

        ((TextView) convertView.findViewById(R.id.category_name)).setText(getItem(position).getName());
        ((TextView) convertView.findViewById(R.id.category_summary)).setText(getItem(position).getSummary());
        ((ImageView) convertView.findViewById(R.id.category_image)).setImageResource(getItem(position).getImageID());
        return convertView;
    }
}
