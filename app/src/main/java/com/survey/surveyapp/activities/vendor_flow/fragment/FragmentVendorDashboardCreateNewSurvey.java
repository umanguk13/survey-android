package com.survey.surveyapp.activities.vendor_flow.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.survey.surveyapp.R;
import com.survey.surveyapp.activities.vendor_flow.ActivityVendorAddSurveyQuestions;
import com.survey.surveyapp.activities.vendor_flow.ActivityVendorMain;
import com.survey.surveyapp.helper.TagValues;
import com.survey.surveyapp.service.MyService;
import com.survey.surveyapp.vo.VoResponseCreateNewSurvey;
import com.survey.surveyapp.vo.VoResponseFetchCategory;
import com.survey.surveyapp.vo.VoResponseFetchCategoryData;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.HttpException;

public class FragmentVendorDashboardCreateNewSurvey extends Fragment {

    ActivityVendorMain mActivityVendorMain;

    private View createView;
    boolean _isLoaded = false;

    @BindView(R.id.fragment_vendor_dashboard_create_new_spinner_category)
    Spinner mSpinnerCategory;

    @BindView(R.id.fragment_vendor_dashboard_create_survey_edittext_choose_survey_title)
    TextInputEditText mEditTextSurveyTitle;

    @BindView(R.id.fragment_vendor_dashboard_create_survey_edittext_posted_by)
    TextInputEditText mEditTextPostedBy;

    @BindView(R.id.fragment_vendor_dashboard_create_new_survey_textview_next)
    TextView mTextViewNext;

    //Required Responses
    @BindView(R.id.fragment_vendor_dashboard_create_new_radiobutton_required_responses)
    RadioButton mRadioButtonRequiredResponses;

    @BindView(R.id.fragment_vendor_dashboard_create_new_edittext_required_responses)
    AppCompatEditText mEditTextRequiredResponses;


    //Start Date & End Date
    @BindView(R.id.fragment_vendor_dashboard_create_new_radiobutton_date_range)
    RadioButton mRadioButtonDateRangeRequired;

    @BindView(R.id.fragment_vendor_dashboard_create_new_edittext_start_date)
    AppCompatEditText mEditTextStartDate;

    @BindView(R.id.fragment_vendor_dashboard_create_new_edittext_end_date)
    AppCompatEditText mEditTextEndDate;

    //Participate Info
    @BindView(R.id.fragment_vendor_dashboard_create_new_spinner_profession)
    Spinner mSpinnerProfession;

    @BindView(R.id.fragment_vendor_dashboard_create_new_spinner_start_age)
    Spinner mSpinnerStartAge;

    @BindView(R.id.fragment_vendor_dashboard_create_new_spinner_end_age)
    Spinner mSpinnerEndAge;

    @BindView(R.id.fragment_vendor_dashboard_create_new_spinner_group)
    Spinner mSpinnerGroup;

    @BindView(R.id.fragment_vendor_dashboard_create_new_spinner_location)
    Spinner mSpinnerLocation;


    //Data
    private String[] mStringProfession;
    private String[] mStringStartAge;
    private String[] mStringEndAge;
    private String[] mStringGroup;
    private String[] mStringLocation;

    //Selected Data
    private String mStringSelectedCategoryID = "";
    private String mStringSelectedProfession = "";
    private String mStringSelectedStartAge = "";
    private String mStringSelectedEndAge = "";
    private String mStringSelectedLocation = "";
    private String mStringSelectedGroup = "";

    //Selected Data
    private Calendar mCalendarStartDate = Calendar.getInstance();
    private Calendar mCalendarEndDate = Calendar.getInstance();

    //Array Data
    private ArrayList<VoResponseFetchCategoryData> mArrayListCategory = new ArrayList<>();

    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityVendorMain = (ActivityVendorMain) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createView = inflater.inflate(R.layout.fragment_vendor_dashboard_create_new_survey, container, false);
        ButterKnife.bind(this, createView);
        _isLoaded = true;

        mStringProfession = getResources().getStringArray(R.array.array_profession);
        mStringGroup = getResources().getStringArray(R.array.array_group);
        mStringLocation = getResources().getStringArray(R.array.array_location);

        mTextViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    if (mActivityVendorMain.mUtility.haveInternet()) {
                        mActivityVendorMain.mVendorMainViewModel.createNewSurvey(mStringSelectedCategoryID,
                                mEditTextSurveyTitle.getText().toString().trim(),
                                mEditTextRequiredResponses.getText().toString().trim(),
                                mEditTextStartDate.getText().toString().trim(),
                                mEditTextEndDate.getText().toString().trim(),
                                mStringSelectedProfession,
                                mStringSelectedStartAge,
                                mStringSelectedEndAge,
                                mStringSelectedLocation,
                                mStringSelectedGroup);
                    } else {
                        mActivityVendorMain.mUtility.errorDialog(mActivityVendorMain.getResources().getString(R.string.internet_error));
                    }
                }
            }
        });

        mRadioButtonRequiredResponses.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mEditTextRequiredResponses.setEnabled(true);
                    mEditTextRequiredResponses.setFocusable(true);
                    mEditTextRequiredResponses.setFocusableInTouchMode(true);
                    mEditTextRequiredResponses.setClickable(true);
                } else {
                    mEditTextRequiredResponses.setEnabled(false);
                    mEditTextRequiredResponses.setFocusable(false);
                    mEditTextRequiredResponses.setFocusableInTouchMode(false);
                    mEditTextRequiredResponses.setClickable(false);
                    mEditTextRequiredResponses.setText("");
                }
            }
        });

        mRadioButtonDateRangeRequired.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mEditTextStartDate.setEnabled(true);
                    mEditTextStartDate.setClickable(true);
                    mEditTextEndDate.setEnabled(true);
                    mEditTextEndDate.setClickable(true);
                } else {
                    mEditTextStartDate.setEnabled(false);
                    mEditTextStartDate.setClickable(false);
                    mEditTextEndDate.setEnabled(false);
                    mEditTextEndDate.setClickable(false);
                }
            }
        });

        mEditTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePickerDialog = DatePickerDialog.newInstance(mOnStartDateSetListener,
                        mCalendarStartDate.get(Calendar.YEAR),
                        mCalendarStartDate.get(Calendar.MONTH),
                        mCalendarStartDate.get(Calendar.DAY_OF_MONTH));

                mDatePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
                mDatePickerDialog.setThemeDark(false);
                mDatePickerDialog.setOkColor(ContextCompat.getColor(mActivityVendorMain, R.color.color_white));
                mDatePickerDialog.setCancelColor(ContextCompat.getColor(mActivityVendorMain, R.color.color_white));

                Calendar mCalendar = Calendar.getInstance();
                mDatePickerDialog.setMinDate(mCalendar);

                mCalendar.set(Calendar.MONTH, mCalendar.get(Calendar.MONTH) + 6);
                mDatePickerDialog.setMaxDate(mCalendar);

                mDatePickerDialog.show(getChildFragmentManager(), "StartDate");
            }
        });

        mEditTextEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditTextStartDate.getText() != null && mEditTextStartDate.getText().toString().trim().equalsIgnoreCase("")) {
                    mEditTextEndDate.setError("Please select start date first.");
                } else {
                    DatePickerDialog mDatePickerDialog = DatePickerDialog.newInstance(mOnEndDateSetListener,
                            mCalendarEndDate.get(Calendar.YEAR),
                            mCalendarEndDate.get(Calendar.MONTH),
                            mCalendarEndDate.get(Calendar.DAY_OF_MONTH));

                    mDatePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
                    mDatePickerDialog.setThemeDark(false);
                    mDatePickerDialog.setOkColor(ContextCompat.getColor(mActivityVendorMain, R.color.color_white));
                    mDatePickerDialog.setCancelColor(ContextCompat.getColor(mActivityVendorMain, R.color.color_white));

                    mDatePickerDialog.setMinDate(mCalendarStartDate);

                    Calendar mCalendar = Calendar.getInstance();
                    mCalendar.set(Calendar.MONTH, mCalendarStartDate.get(Calendar.MONTH) + 6);
                    mDatePickerDialog.setMaxDate(mCalendar);

                    mDatePickerDialog.show(getChildFragmentManager(), "EndDate");
                }
            }
        });

        //region Set Spinner Adapter
        mSpinnerProfession.setAdapter(new CustomSpinnerAdapter(mActivityVendorMain, R.layout.raw_spinner_item, mStringProfession));

        mStringStartAge = new String[50];

        for (int i = 0; i < 50; i++) {
            mStringStartAge[i] = Integer.toString(i + 18);
        }

        mSpinnerStartAge.setAdapter(new CustomSpinnerAdapter(mActivityVendorMain, R.layout.raw_spinner_item, mStringStartAge));

        mStringEndAge = new String[50];

        for (int i = 0; i < 50; i++) {
            mStringEndAge[i] = Integer.toString(i + 18);
        }

        mSpinnerEndAge.setAdapter(new CustomSpinnerAdapter(mActivityVendorMain, R.layout.raw_spinner_item, mStringEndAge));

        mSpinnerGroup.setAdapter(new CustomSpinnerAdapter(mActivityVendorMain, R.layout.raw_spinner_item, mStringGroup));

        mSpinnerLocation.setAdapter(new CustomSpinnerAdapter(mActivityVendorMain, R.layout.raw_spinner_item, mStringLocation));
        //endregion

        //region Spinner Listeners
        mSpinnerProfession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStringSelectedProfession = mStringProfession[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinnerStartAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStringSelectedStartAge = mStringStartAge[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinnerEndAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStringSelectedEndAge = mStringEndAge[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStringSelectedLocation = mStringLocation[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinnerGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStringSelectedGroup = mStringGroup[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mArrayListCategory.get(position) != null
                        && mArrayListCategory.get(position).getId() != null) {
                    mStringSelectedCategoryID = mArrayListCategory.get(position).getId();
                    System.out.println("Darshan... Selected Category ID : " + mStringSelectedCategoryID);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //endregion

        if (isAdded()) {
            fetchSurveyCategory();
        }

        return createView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && _isLoaded) {

        }
    }

    private DatePickerDialog.OnDateSetListener mOnStartDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            mCalendarStartDate.set(Calendar.YEAR, year);
            mCalendarStartDate.set(Calendar.MONTH, monthOfYear);
            mCalendarStartDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            mEditTextStartDate.setText(mSimpleDateFormat.format(mCalendarStartDate.getTime()));
        }
    };

    private DatePickerDialog.OnDateSetListener mOnEndDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            mCalendarEndDate.set(Calendar.YEAR, year);
            mCalendarEndDate.set(Calendar.MONTH, monthOfYear);
            mCalendarEndDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            mEditTextEndDate.setText(mSimpleDateFormat.format(mCalendarEndDate.getTime()));
        }
    };

    private boolean isValid() {
        if (mEditTextSurveyTitle.getText() != null &&
                mEditTextSurveyTitle.getText().toString().trim().equalsIgnoreCase("")) {
            mEditTextSurveyTitle.setError("Please enter survey title.");
            return false;
        }

        if (mEditTextPostedBy.getText() != null &&
                mEditTextPostedBy.getText().toString().trim().equalsIgnoreCase("")) {
            mEditTextPostedBy.setError("Please enter name of poster.");
            return false;
        }

        if (mRadioButtonRequiredResponses.isChecked()) {
            if (mEditTextRequiredResponses.getText() != null &&
                    mEditTextRequiredResponses.getText().toString().trim().equalsIgnoreCase("")) {
                mEditTextRequiredResponses.setError("Please enter number of required responses.");
                return false;
            }
        }

        if (mRadioButtonDateRangeRequired.isChecked()) {
            if (mEditTextStartDate.getText() != null &&
                    mEditTextStartDate.getText().toString().trim().equalsIgnoreCase("")) {
                mEditTextStartDate.setError("Please select start date of survey.");
                return false;
            }

            if (mEditTextEndDate.getText() != null &&
                    mEditTextEndDate.getText().toString().trim().equalsIgnoreCase("")) {
                mEditTextEndDate.setError("Please select end date of survey.");
                return false;
            }
        }

        if (mStringSelectedProfession.equalsIgnoreCase("")) {
            mActivityVendorMain.mUtility.errorDialog("Please select profession criteria.");
            return false;
        }

        if (mStringSelectedStartAge.equalsIgnoreCase("")) {
            mActivityVendorMain.mUtility.errorDialog("Please select start age.");
            return false;
        }

        if (mStringSelectedEndAge.equalsIgnoreCase("")) {
            mActivityVendorMain.mUtility.errorDialog("Please select end age.");
            return false;
        }

        if (mStringSelectedLocation.equalsIgnoreCase("")) {
            mActivityVendorMain.mUtility.errorDialog("Please select location criteria.");
            return false;
        }

        if (mStringSelectedGroup.equalsIgnoreCase("")) {
            mActivityVendorMain.mUtility.errorDialog("Please select group criteria.");
            return false;
        }

        return true;
    }

    public void gotServiceResponse(VoResponseCreateNewSurvey mVoResponseCreateNewSurvey) {
        //TODO Got Response, Now go to Add Questions
        Toast.makeText(mActivityVendorMain, "Got Response, Now go to Add Questions", Toast.LENGTH_SHORT).show();
        Intent mIntent = new Intent(getActivity(), ActivityVendorAddSurveyQuestions.class);
        mIntent.putExtra("mVoResponseCreateNewSurvey", mVoResponseCreateNewSurvey);
        startActivity(mIntent);
    }

    public class CustomSpinnerAdapter extends ArrayAdapter<String> {

        Context mContext;
        String[] mStringItem;

        CustomSpinnerAdapter(Context context, int resource, String[] mStringItem) {
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

            TextView mTextViewItem = rowView.findViewById(R.id.raw_spinner_item_end_gravity_textview);
            mTextViewItem.setText(mStringItem[position]);

            return rowView;
        }
    }

    public class CustomSpinnerCategoryAdapter extends ArrayAdapter<String> {

        Context mContext;

        public CustomSpinnerCategoryAdapter(Context context, int resource) {
            super(context, resource);
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return mArrayListCategory.size();
        }

        @Override
        public String getItem(int position) {
            return mArrayListCategory.get(position).getName();
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            LayoutInflater mLayoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert mLayoutInflater != null;
            @SuppressLint("ViewHolder") View rowView = mLayoutInflater.inflate(R.layout.raw_spinner_item, parent, false);

            TextView mTextViewItem = rowView.findViewById(R.id.raw_spinner_item_end_gravity_textview);
            mTextViewItem.setText(mArrayListCategory.get(position).getName());

            return rowView;
        }
    }

    private void fetchSurveyCategory() {
        mActivityVendorMain.mMyService.fetchSurveyCategory(mActivityVendorMain.mUtility.getAppPrefString(TagValues.PREF_USER_ACCESS_TOKEN),
                mActivityVendorMain.mUtility.getAppPrefString(TagValues.PREF_USER_ID),
                new MyService.ServiceCallback<VoResponseFetchCategory>() {
                    @Override
                    public void onSuccess(VoResponseFetchCategory data) {
                        if (data != null && data.getList_category() != null
                                && data.getList_category().size() > 0) {
                            mArrayListCategory.addAll(data.getList_category());
                            mSpinnerCategory.setAdapter(new CustomSpinnerCategoryAdapter(mActivityVendorMain, R.layout.raw_spinner_item));
                        }
                    }

                    @Override
                    public void onError(Throwable networkError) {
                        mActivityVendorMain.mUtility.hideAnimatedProgress();
                        networkError.printStackTrace();

                        if (networkError instanceof HttpException) {
                            try {
                                int statusCode = ((HttpException) networkError).code();
                                JSONObject mJsonObjectMsg = new JSONObject(((HttpException) networkError).response().errorBody().string());
                                mActivityVendorMain.mUtility.errorDialog(mJsonObjectMsg.optString("message"));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        } else if (networkError instanceof SocketTimeoutException) {
                            mActivityVendorMain.mUtility.errorDialog(mActivityVendorMain.getString(R.string.something_went_wrong_fix_soon));
                        } else {
                            mActivityVendorMain.mUtility.errorDialog(mActivityVendorMain.getString(R.string.something_went_wrong_fix_soon));
                        }
                    }
                });
    }

}
