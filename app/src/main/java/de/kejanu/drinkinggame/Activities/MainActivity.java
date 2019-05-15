package de.kejanu.drinkinggame.Activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import de.kejanu.drinkinggame.Fragments.NameSelectFragment;
import de.kejanu.drinkinggame.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        displayFragment(new NameSelectFragment(), getString(R.string.fragment_name_select), false);
    }

    private void displayFragment(Fragment fragment, String tag, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);

        if (addToBackStack)
            transaction.addToBackStack(tag);

        transaction.commit();
    }
}
