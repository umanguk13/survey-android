package com.survey.surveyapp.activities.user_flow;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.survey.surveyapp.R;
import com.survey.surveyapp.activities.user_flow.fragments.FragmentUserDashboard;
import com.survey.surveyapp.activities.user_flow.fragments.FragmentUserMenu;
import com.survey.surveyapp.activities.user_flow.fragments.FragmentUserProfile;
import com.survey.surveyapp.activities.user_flow.fragments.FragmentUserSearchSurveys;
import com.survey.surveyapp.databinding.ActivityUserMainBinding;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

public class ActivityUserMain extends UserBaseActivity {

    ActivityUserMainBinding mActivityUserMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityUserMainBinding = (ActivityUserMainBinding) putContentView(R.layout.activity_user_main);

        initToolbar();
        initViewPager();

    }

    public void initViewPager() {

        mActivityUserMainBinding.activityUserMainViewpager.setAdapter(new DashboardAdapter(getSupportFragmentManager()));
        mActivityUserMainBinding.activityUserMainViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(final int position) {
                if (mActivityUserMainBinding.activityUserMainBottomNavigation.getSelectedIndex() != position) {
                    mActivityUserMainBinding.activityUserMainBottomNavigation.setSelectedIndex(position, false);
                }
            }

            @Override
            public void onPageScrollStateChanged(final int state) {
            }
        });

        mActivityUserMainBinding.activityUserMainBottomNavigation.setMenuItemSelectionListener(new BottomNavigation.OnMenuItemSelectionListener() {
            @Override
            public void onMenuItemSelect(int i, int i1, boolean b) {
                if (mActivityUserMainBinding.activityUserMainViewpager.getCurrentItem() != i1) {
                    mActivityUserMainBinding.activityUserMainViewpager.setCurrentItem(i1);
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
                    return new FragmentUserDashboard();
                case 1:
                    return new FragmentUserSearchSurveys();
                case 2:
                    return new FragmentUserProfile();
                case 3:
                    return new FragmentUserMenu();
                default:
                    return new FragmentUserDashboard();
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
