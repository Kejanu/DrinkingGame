package de.kejanu.drinkinggame.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import de.kejanu.drinkinggame.Person;
import de.kejanu.drinkinggame.R;

public class NameSelectAdapter extends ArrayAdapter<Person> {

    public NameSelectAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Person> pList) {
        super(context, resource, pList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_name_select_person, container, false);
        }
        ((EditText) convertView.findViewById(R.id.edittext_name)).setHint((position + 1) + ". Person");
        return convertView;
    }


}
