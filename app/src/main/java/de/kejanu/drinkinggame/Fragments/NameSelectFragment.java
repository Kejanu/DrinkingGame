package de.kejanu.drinkinggame.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import de.kejanu.drinkinggame.Adapters.NameSelectAdapter;
import de.kejanu.drinkinggame.Person;
import de.kejanu.drinkinggame.R;

public class NameSelectFragment extends Fragment {

    //Widgets
    private Button continueBtn, addPersonBtn;
    private ListView listView;

    //Variables
    private ArrayList<Person> pList;
    private Context context;
    private NameSelectAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pList = new ArrayList<>();
        context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_name_select, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.listview_persons);

        pList.add(new Person("PersonName"));

        adapter = new NameSelectAdapter(context, R.layout.fragment_name_select_person, pList);
        listView.setAdapter(adapter);

        continueBtn = view.findViewById(R.id.button_continue);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Continue clicked", Toast.LENGTH_SHORT).show();
            }
        });

        addPersonBtn = view.findViewById(R.id.button_add_person);
        addPersonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pList.add(new Person("Big Uff"));
                adapter.notifyDataSetChanged();
            }
        });
    }
}
