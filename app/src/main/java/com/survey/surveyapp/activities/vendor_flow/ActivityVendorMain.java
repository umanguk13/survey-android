package com.survey.surveyapp.activities.vendor_flow;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import com.survey.surveyapp.R;
import com.survey.surveyapp.activities.user_flow.UserBaseActivity;
import com.survey.surveyapp.activities.vendor_flow.fragment.FragmentVendorDashboard;
import com.survey.surveyapp.activities.vendor_flow.fragment.FragmentVendorDashboardCreateNewSurvey;
import com.survey.surveyapp.activities.vendor_flow.fragment.FragmentVendorDashboardCurrentSurveys;
import com.survey.surveyapp.activities.vendor_flow.fragment.FragmentVendorMenu;
import com.survey.surveyapp.activities.vendor_flow.fragment.FragmentVendorProfile;
import com.survey.surveyapp.databinding.ActivityVendorMainBinding;
import com.survey.surveyapp.viewmodels.VendorMainViewModel;
import com.survey.surveyapp.vo.VoResponseCreateNewSurvey;
import com.survey.surveyapp.vo.VoResponseCurrentSurvey;
import com.survey.surveyapp.vo.VoResponsePreviousSurvey;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

public class ActivityVendorMain extends UserBaseActivity {

    ActivityVendorMainBinding mActivityVendorMainBinding;

    public VendorMainViewModel mVendorMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityVendorMainBinding = (ActivityVendorMainBinding) putContentView(R.layout.activity_vendor_main);

        mVendorMainViewModel = new VendorMainViewModel(this, mMyService);
        mActivityVendorMainBinding.setVendorMainViewModel(mVendorMainViewModel);
        mActivityVendorMainBinding.setLifecycleOwner(this);

        initToolbar();
        initViewPager();

        mVendorMainViewModel.mLiveDataCreateNewSurvey.observe(this, voResponseCreateNewSurvey -> {
            if (voResponseCreateNewSurvey != null) {
                Fragment mFragmentCurrent = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.activity_vendor_main_viewpager + ":" +
                        mActivityVendorMainBinding.activityVendorMainViewpager.getCurrentItem());

                if (mFragmentCurrent instanceof FragmentVendorDashboardCreateNewSurvey) {
                    ((FragmentVendorDashboardCreateNewSurvey) mFragmentCurrent).gotServiceResponse(voResponseCreateNewSurvey);
                }
            }
        });

        mVendorMainViewModel.mLiveDataCurrentSurvey.observe(this, voResponseCurrentSurvey -> {
            if (voResponseCurrentSurvey != null) {

                Fragment mFragmentCurrent = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.activity_vendor_main_viewpager + ":" +
                        mActivityVendorMainBinding.activityVendorMainViewpager.getCurrentItem());

                if (mFragmentCurrent instanceof FragmentVendorDashboard) {
                    System.out.println("Darshan... Current Survey : " + voResponseCurrentSurvey.getSurvey_list().size());
                    ((FragmentVendorDashboard) mFragmentCurrent).gotServiceResponse(voResponseCurrentSurvey);
                }
            }
        });

        mVendorMainViewModel.mLiveDataPreviousSurvey.observe(this, voResponsePreviousSurvey -> {
            if (voResponsePreviousSurvey != null) {

            }
        });

    }

    public void initViewPager() {

        mActivityVendorMainBinding.activityVendorMainViewpager.setAdapter(new DashboardAdapter(getSupportFragmentManager()));
        mActivityVendorMainBinding.activityVendorMainViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(final int position) {
                if (mActivityVendorMainBinding.activityVendorMainBottomNavigation.getSelectedIndex() != position) {
                    mActivityVendorMainBinding.activityVendorMainBottomNavigation.setSelectedIndex(position, false);
                }
            }

            @Override
            public void onPageScrollStateChanged(final int state) {
            }
        });

        mActivityVendorMainBinding.activityVendorMainBottomNavigation.setMenuItemSelectionListener(new BottomNavigation.OnMenuItemSelectionListener() {
            @Override
            public void onMenuItemSelect(int i, int i1, boolean b) {
                if (mActivityVendorMainBinding.activityVendorMainViewpager.getCurrentItem() != i1) {
                    mActivityVendorMainBinding.activityVendorMainViewpager.setCurrentItem(i1);
                }
            }

            @Override
            public void onMenuItemReselect(int i, int i1, boolean b) {

            }
        });

    }

    private class DashboardAdapter extends FragmentPagerAdapter {

        public DashboardAdapter(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FragmentVendorDashboard();
                case 1:
                    return new FragmentVendorDashboardCreateNewSurvey();
                case 2:
                    return new FragmentVendorProfile();
                case 3:
                    return new FragmentVendorMenu();
                default:
                    return new FragmentVendorDashboard();
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    public void moveToPostedSurveys() {
        mActivityVendorMainBinding.activityVendorMainViewpager.setCurrentItem(0);
    }

}
