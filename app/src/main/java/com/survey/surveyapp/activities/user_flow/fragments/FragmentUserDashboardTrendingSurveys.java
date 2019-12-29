package com.survey.surveyapp.activities.user_flow.fragments;

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

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentUserDashboardTrendingSurveys extends Fragment {

    private View createView;
    boolean _isLoaded = false;

    @BindView(R.id.fragment_user_dashboard_trending_surveys_recyclerview)
    RecyclerView mRecyclerViewTrendingSurveys;

    //Adapter
    TrendingSurveysAdapter mTrendingSurveysAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createView = inflater.inflate(R.layout.fragment_user_dashboard_trending_surveys, container, false);
        _isLoaded = true;
        ButterKnife.bind(this, createView);

//        if (mTrendingSurveysAdapter != null) {
//            mTrendingSurveysAdapter.notifyDataSetChanged();
//        } else {
            mTrendingSurveysAdapter = new TrendingSurveysAdapter();
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            mRecyclerViewTrendingSurveys.setLayoutManager(mLinearLayoutManager);
            mRecyclerViewTrendingSurveys.setAdapter(mTrendingSurveysAdapter);
//        }

        return createView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && _isLoaded) {
//            if (mTrendingSurveysAdapter != null) {
//                mTrendingSurveysAdapter.notifyDataSetChanged();
//            } else {
                mTrendingSurveysAdapter = new TrendingSurveysAdapter();
                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                mRecyclerViewTrendingSurveys.setLayoutManager(mLinearLayoutManager);
                mRecyclerViewTrendingSurveys.setAdapter(mTrendingSurveysAdapter);
//            }
        }
    }

    public class TrendingSurveysAdapter extends RecyclerView.Adapter<TrendingSurveysAdapter.ViewHolder> {

        @androidx.annotation.NonNull
        @Override
        public TrendingSurveysAdapter.ViewHolder onCreateViewHolder(@androidx.annotation.NonNull @io.reactivex.annotations.NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_survey_list_item, parent, false);
            return new TrendingSurveysAdapter.ViewHolder(itemView);
        }

        @SuppressLint("CheckResult")
        @Override
        public void onBindViewHolder(@androidx.annotation.NonNull final TrendingSurveysAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

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
