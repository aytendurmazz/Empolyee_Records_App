package com.example.reminder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.UUID;

public class IsciPagerActivity extends AppCompatActivity {

    private static final String EXTRA_ISCI_ID =
            "com.bignerdranch.android.criminalintent.crime_id";

    private ViewPager mViewPager;
    private List<Isci> mIsciler;

    public static Intent newIntent(Context packageContext, UUID reminderId) {
        Intent intent = new Intent(packageContext, IsciPagerActivity.class);
        intent.putExtra(EXTRA_ISCI_ID, reminderId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_pager);

        UUID reminderId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_ISCI_ID);

        mViewPager = (ViewPager) findViewById(R.id.reminder_view_pager);

        mIsciler = IsciLab.get(this).getIsciler();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Isci isciId = mIsciler.get(position);
                return IsciFragment.newInstance(isciId.getId());
            }

            @Override
            public int getCount() {
                return mIsciler.size();
            }
        });

        for (int i = 0; i < mIsciler.size(); i++) {
            if (mIsciler.get(i).getId().equals(reminderId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
