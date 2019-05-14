package de.kejanu.drinkinggame.Activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.kejanu.drinkinggame.Fragments.SettingsFragment;
import de.kejanu.drinkinggame.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.settings_container, new SettingsFragment())
                .commit();
    }
}
