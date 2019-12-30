package com.survey.surveyapp.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.survey.surveyapp.BaseActivity;
import com.survey.surveyapp.R;
import com.survey.surveyapp.databinding.ActivityCreateAccountBinding;
import com.survey.surveyapp.viewmodels.CreateAccountViewModel;

public class ActivityCreateAccount extends BaseActivity {

    ActivityCreateAccountBinding mActivityCreateAccountBinding;

    CreateAccountViewModel mCreateAccountViewModel;

    String[] mStringProfession;
    String[] mStringAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityCreateAccountBinding = (ActivityCreateAccountBinding) putContentView(R.layout.activity_create_account);

        initToolbar();
        mToolbarMain.setVisibility(View.GONE);
        mStringProfession = getResources().getStringArray(R.array.array_profession);

        mCreateAccountViewModel = new CreateAccountViewModel(ActivityCreateAccount.this, mMyService);
        mActivityCreateAccountBinding.setCreateAccountViewModel(mCreateAccountViewModel);
        mActivityCreateAccountBinding.setLifecycleOwner(this);

        AutoCompleteAdapter mAutoCompleteAdapterProfession = new AutoCompleteAdapter(ActivityCreateAccount.this, R.layout.raw_spinner_item, mStringProfession);
        mActivityCreateAccountBinding.activityLoginEmailEmailEdittextProfession.setAdapter(mAutoCompleteAdapterProfession);
        mActivityCreateAccountBinding.activityLoginEmailEmailEdittextProfession.setCursorVisible(false);

        mActivityCreateAccountBinding.activityLoginEmailEmailEdittextProfession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivityCreateAccountBinding.activityLoginEmailEmailEdittextProfession.showDropDown();
            }
        });

        mActivityCreateAccountBinding.activityLoginEmailEmailEdittextProfession.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mActivityCreateAccountBinding.activityLoginEmailEmailEdittextProfession.showDropDown();
                mCreateAccountViewModel.mStringProfession.setValue((String) parent.getItemAtPosition(position));
            }
        });

        mStringAge = new String[50];

        for (int i = 0; i < 50; i++) {
            mStringAge[i] = Integer.toString(i + 18);
        }

        AutoCompleteAdapter mAutoCompleteAdapterAge = new AutoCompleteAdapter(ActivityCreateAccount.this, R.layout.raw_spinner_item, mStringAge);
        mActivityCreateAccountBinding.activityLoginEmailEmailEdittextAge.setAdapter(mAutoCompleteAdapterAge);
        mActivityCreateAccountBinding.activityLoginEmailEmailEdittextAge.setCursorVisible(false);

        mActivityCreateAccountBinding.activityLoginEmailEmailEdittextAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivityCreateAccountBinding.activityLoginEmailEmailEdittextAge.showDropDown();
            }
        });

        mActivityCreateAccountBinding.activityLoginEmailEmailEdittextAge.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mActivityCreateAccountBinding.activityLoginEmailEmailEdittextAge.showDropDown();
                mCreateAccountViewModel.mStringAge.setValue((String) parent.getItemAtPosition(position));
            }
        });

    }

    public class AutoCompleteAdapter extends ArrayAdapter<String> {

        Context mContext;
        String[] mStringItem;

        public AutoCompleteAdapter(Context context, int resource, String[] mStringItem) {
            super(context, resource, mStringItem);
            this.mContext = context;
            this.mStringItem = mStringItem;
        }

        @Override
        public int getCount() {
            return mStringItem.length;
        }

        @Override
        public String getItem(int position) {
            return mStringItem[position];
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            LayoutInflater mLayoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert mLayoutInflater != null;
            @SuppressLint("ViewHolder") View rowView = mLayoutInflater.inflate(R.layout.raw_spinner_item, parent, false);

            TextView mTextViewItem = rowView.findViewById(R.id.raw_spinner_item_textview);
            mTextViewItem.setText(mStringItem[position]);

            return rowView;
        }
    }



}
