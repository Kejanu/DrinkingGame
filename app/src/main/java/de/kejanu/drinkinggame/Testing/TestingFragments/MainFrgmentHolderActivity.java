package de.kejanu.drinkinggame.Testing.TestingFragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.kejanu.drinkinggame.R;

public class MainFrgmentHolderActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment_holder);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new FragmentNameSelect())
                .commit();
    }
}