package com.survey.surveyapp.activities.vendor_flow.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.survey.surveyapp.R;
import com.survey.surveyapp.vo.VoResponseCurrentSurvey;

public class FragmentVendorDashboard extends Fragment {

    private View createView;
    boolean _isLoaded = false;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createView = inflater.inflate(R.layout.fragment_vendor_dashboard, container, false);
        _isLoaded = true;

        mTabLayout = (TabLayout) createView.findViewById(R.id.fragment_vendor_dashboard_tab_layout);
        mViewPager = (ViewPager) createView.findViewById(R.id.fragment_vendor_dashboard_viewpager);

        mViewPager.setAdapter(new DashboardAdapter(getChildFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.setOffscreenPageLimit(2);

        return createView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && _isLoaded) {
//            if (mViewPager != null && mTabLayout != null) {
//                mViewPager.setAdapter(new DashboardAdapter(getChildFragmentManager()));
//                mTabLayout.setupWithViewPager(mViewPager);
//            }
        }
    }

    public void gotServiceResponse(VoResponseCurrentSurvey voResponseCurrentSurvey) {
        System.out.println("Darshan... Current Survey : " + voResponseCurrentSurvey.getSurvey_list().size());
        Fragment mFragmentCurrent = getChildFragmentManager().findFragmentByTag("android:switcher:" + R.id.fragment_vendor_dashboard_viewpager + ":" +
                mViewPager.getCurrentItem());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mFragmentCurrent instanceof FragmentVendorDashboardCurrentSurveys) {
                    System.out.println("Darshan... Current Survey : " + voResponseCurrentSurvey.getSurvey_list().size());
                    ((FragmentVendorDashboardCurrentSurveys) mFragmentCurrent).gotServiceResponse(voResponseCurrentSurvey);
                }
            }
        }, 1000);
        
    }

    private class DashboardAdapter extends FragmentStatePagerAdapter {

        private String[] tabTitles = new String[]{getResources().getString(R.string.text_vendor_dashboard_current_surveys),
                getResources().getString(R.string.text_vendor_dashboard_previous_surveys)};

        public DashboardAdapter(FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FragmentVendorDashboardCurrentSurveys();
                case 1:
                    return new FragmentVendorDashboardPreviousSurveys();
                default:
                    return new FragmentVendorDashboardCurrentSurveys();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
