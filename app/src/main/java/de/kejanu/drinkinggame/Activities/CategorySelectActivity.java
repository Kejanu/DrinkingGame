package de.kejanu.drinkinggame.Activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.kejanu.drinkinggame.Adapters.Category;
import de.kejanu.drinkinggame.Adapters.CategoryAdapter;
import de.kejanu.drinkinggame.R;

public class CategorySelectActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_select);

        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category("Classic", "The classic game. Pretty basic tbh", R.drawable.ic_launcher_foreground));
        categories.add(new Category("Round Robin", "Fight against your neighbour", R.drawable.ic_launcher_background));
        categories.add(new Category("Test", "Decently sized UFF", R.drawable.baseline_settings_black_18dp));

        ListView listView = findViewById(R.id.listview_category);
        listView.setAdapter(new CategoryAdapter(this, R.layout.category, categories));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CategorySelectActivity.this, Long.toString(id), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
