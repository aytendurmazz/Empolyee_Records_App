package com.example.reminder;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IsciListFragment extends Fragment {

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
    private RecyclerView mReminderRecyclerView;
    private IsciAdapter mAdapter;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_isci_list, container, false);

        mReminderRecyclerView = (RecyclerView) view
                .findViewById(R.id.reminder_recycler_view);
        mReminderRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_reminder_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_reminder:
                Isci r1 = new Isci();
                IsciLab.get(getActivity()).addIsci(r1);
                Intent intent = IsciPagerActivity
                        .newIntent(getActivity(), r1.getId());
                startActivity(intent);
                return true;
            case R.id.show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle() {
        IsciLab reminderLab= IsciLab.get(getActivity());
        int reminderCount = reminderLab.getIsciler().size();
        String subtitle = getString(R.string.subtitle_format, reminderCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void updateUI() {
        IsciLab reminderLab = IsciLab.get(getActivity());
        List<Isci> isciler = reminderLab.getIsciler();

        if (mAdapter == null) {
            mAdapter = new IsciAdapter(isciler);
            mReminderRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    private class ReminderHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Isci mIsci;
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mCinsiyet;
        private TextView mAdres;
        private TextView mID;
        public ReminderHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_isci, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.reminder_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.reminder_date);
            mCinsiyet = (TextView) itemView.findViewById(R.id.textViewCinsiyet);
            mAdres=(TextView) itemView.findViewById(R.id.textViewAdres);
            mID=(TextView) itemView.findViewById(R.id.textViewID);
        }
        public void bind(final Isci isci) {
            mIsci = isci;
            mTitleTextView.setText("Ad Soyad:   "+mIsci.getTitle());
            mDateTextView.setText("İşe Giriş Tarihi:    "+mIsci.getDate().toString());
            mCinsiyet.setText("Cinsiyet:    "+mIsci.getCinsiyet());
            mAdres.setText("Adres:  "+mIsci.getAdres());
            mID.setText("ID:    "+mIsci.getmID());
            }

        @Override
        public void onClick(View v) {
            Intent intent = IsciPagerActivity.newIntent(getActivity(), mIsci.getId());
            startActivity(intent);
        }
    }


    private class IsciAdapter extends RecyclerView.Adapter<ReminderHolder> {

        private List<Isci> mReminders;

        public IsciAdapter(List<Isci> isciler) {
            mReminders = isciler;
        }

        @Override
        public ReminderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ReminderHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ReminderHolder holder, int position) {
            Isci r1 = mReminders.get(position);
            holder.bind(r1);
        }

        @Override
        public int getItemCount() {
            return mReminders.size();
        }
    }
}
