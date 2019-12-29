package com.survey.surveyapp.activities.vendor_flow.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.survey.surveyapp.R;
import com.survey.surveyapp.helper.DividerItemDecorationNavigation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentVendorDashboardCurrentSurveys extends Fragment {

    private View createView;
    boolean _isLoaded = false;

    @BindView(R.id.fragment_vendor_dashboard_current_surveys_recyclerview)
    RecyclerView mRecyclerViewHotSurveys;

    //Adapter
    CurrentSurveysAdapter mCurrentSurveysAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createView = inflater.inflate(R.layout.fragment_vendor_dashboard_current_surveys, container, false);
        ButterKnife.bind(this, createView);
        _isLoaded = true;

//        if (mCurrentSurveysAdapter != null) {
//            mCurrentSurveysAdapter.notifyDataSetChanged();
//        } else {
        mCurrentSurveysAdapter = new CurrentSurveysAdapter();
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mRecyclerViewHotSurveys.addItemDecoration(new DividerItemDecorationNavigation(getActivity(), DividerItemDecorationNavigation.VERTICAL_LIST));
        mRecyclerViewHotSurveys.setLayoutManager(mLinearLayoutManager);
        mRecyclerViewHotSurveys.setAdapter(mCurrentSurveysAdapter);
//        }

        return createView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && _isLoaded) {
//            if (mCurrentSurveysAdapter != null) {
//                mCurrentSurveysAdapter.notifyDataSetChanged();
//            } else {
            mCurrentSurveysAdapter = new CurrentSurveysAdapter();
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            mRecyclerViewHotSurveys.addItemDecoration(new DividerItemDecorationNavigation(getActivity(), DividerItemDecorationNavigation.VERTICAL_LIST));
            mRecyclerViewHotSurveys.setLayoutManager(mLinearLayoutManager);
            mRecyclerViewHotSurveys.setAdapter(mCurrentSurveysAdapter);
//            }
        }
    }

    public class CurrentSurveysAdapter extends RecyclerView.Adapter<CurrentSurveysAdapter.ViewHolder> {

        @NonNull
        @Override
        public CurrentSurveysAdapter.ViewHolder onCreateViewHolder(@NonNull @io.reactivex.annotations.NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_vendor_survey_list_item, parent, false);
            return new CurrentSurveysAdapter.ViewHolder(itemView);
        }

        @SuppressLint("CheckResult")
        @Override
        public void onBindViewHolder(@NonNull final CurrentSurveysAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        }

        @Override
        public int getItemCount() {
            return 4;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }

    }

}
