package com.survey.surveyapp.activities.vendor_flow;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.survey.surveyapp.R;
import com.survey.surveyapp.databinding.ActivityVendorReceivedResponseBinding;
import com.survey.surveyapp.helper.DividerItemDecorationNavigation;

import butterknife.ButterKnife;

public class ActivityVendorReceivedResponses extends VendorBaseActivity {

    ActivityVendorReceivedResponseBinding mActivityVendorReceivedResponseBinding;

    ReceivedResponsesAdapter mReceivedResponsesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityVendorReceivedResponseBinding = (ActivityVendorReceivedResponseBinding) putContentView(R.layout.activity_vendor_received_response);

        initToolbar();
        mTextViewTitle.setText("Received Responses");

        mReceivedResponsesAdapter = new ReceivedResponsesAdapter();
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(ActivityVendorReceivedResponses.this, RecyclerView.VERTICAL, false);
        mActivityVendorReceivedResponseBinding.activityVendorReceivedResponseRecyclerview.addItemDecoration(new DividerItemDecorationNavigation(getApplicationContext(), DividerItemDecorationNavigation.VERTICAL_LIST));
        mActivityVendorReceivedResponseBinding.activityVendorReceivedResponseRecyclerview.setLayoutManager(mLinearLayoutManager);
        mActivityVendorReceivedResponseBinding.activityVendorReceivedResponseRecyclerview.setAdapter(mReceivedResponsesAdapter);

    }

    public class ReceivedResponsesAdapter extends RecyclerView.Adapter<ReceivedResponsesAdapter.ViewHolder> {

        @NonNull
        @Override
        public ReceivedResponsesAdapter.ViewHolder onCreateViewHolder(@NonNull @io.reactivex.annotations.NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_vendor_received_response_item, parent, false);
            return new ReceivedResponsesAdapter.ViewHolder(itemView);
        }

        @SuppressLint("CheckResult")
        @Override
        public void onBindViewHolder(@NonNull final ReceivedResponsesAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

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
