package com.example.reminder;


import androidx.fragment.app.Fragment;

public class IsciListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new IsciListFragment();
    }
}
