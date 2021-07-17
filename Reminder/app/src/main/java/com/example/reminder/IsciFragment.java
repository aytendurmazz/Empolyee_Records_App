package com.example.reminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Date;
import java.util.UUID;

import static android.widget.CompoundButton.*;

public class IsciFragment extends Fragment {

    private static final String ARG_ISCI_ID = "reminder_ID";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;

    private Isci mIsci;
    private EditText mTitleField;
    private Button mDateButton;
    private RadioGroup radioGroup;
    private RadioButton rB;
    private EditText mAdresField;
    private EditText mIDField;
    private Button mDeleteButton;


    public static IsciFragment newInstance(UUID isciId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ISCI_ID, isciId);

        IsciFragment fragment = new IsciFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID isciId = (UUID) getArguments().getSerializable(ARG_ISCI_ID);
        mIsci = IsciLab.get(getActivity()).getIsci(isciId);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_isci, container, false);

        mTitleField = (EditText) v.findViewById(R.id.reminder_title);
        mTitleField.setText(mIsci.getTitle());
        mAdresField=(EditText) v.findViewById(R.id.textViewAdres);
        mAdresField.setText(mIsci.getAdres());
        mAdresField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mIsci.setAdres(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mIDField=(EditText) v.findViewById(R.id.textViewID);
        mIDField.setText(mIsci.getmID());
        mIDField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mIsci.setmID(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mIsci.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton = (Button) v.findViewById(R.id.reminder_date);
        updateDate();
        mDateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mIsci.getDate());
                dialog.setTargetFragment(IsciFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });
        RadioGroup rb = (RadioGroup) v.findViewById(R.id.radioGroup);
        rb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_kız:
                        mIsci.setmCinsiyet("Kız");
                        break;
                    case R.id.radio_erkek:
                        mIsci.setmCinsiyet("Erkek");
                        break;
                }
            }

        });
        mDeleteButton = (Button) v.findViewById(R.id.buttonDelete);
        mDeleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IsciLab.deleteIsci(mIsci.getId()) ){
                    Toast.makeText(getActivity(),"Deleted.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { //date seçince update eder
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mIsci.setDate(date);
            updateDate();
        }
    }

    private void updateDate() {
        mDateButton.setText(mIsci.getDate().toString());
    }

}

