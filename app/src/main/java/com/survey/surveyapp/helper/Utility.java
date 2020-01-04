package com.survey.surveyapp.helper;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.IntDef;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.request.RequestOptions;
import com.survey.surveyapp.BaseActivity;
import com.survey.surveyapp.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public class Utility {

    private Activity mActivity;

    private Dialog mDialogProgress;

    Dialog mAlertDialog;

    Toast mToast;

    private final String[] ALL_PERMS = {Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.WRITE_CALENDAR,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    private static final int MULTIPLE_PERMISSIONS_RESPONSE_CODE = 1;

    private final int ALL_REQUEST = 35;

    public static final int GRANTED = 0;
    public static final int DENIED = 1;
    public static final int BLOCKED_OR_NEVER_ASKED = 2;

    int PERMISSIONCOUNT = 0;

    public Utility(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void writeSharedPreferencesString(String key, String value) {
        SharedPreferences settings = mActivity.getSharedPreferences(TagValues.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void clearAllPrefData() {
        SharedPreferences settings = mActivity.getSharedPreferences(TagValues.PREFS_NAME, 0);
        settings.edit().clear().commit();
    }

    public String getAppPrefString(String key) {
        try {
            SharedPreferences settings = mActivity.getSharedPreferences(TagValues.PREFS_NAME, 0);
            String value = settings.getString(key, "");
            return value;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public void errorDialogWithTitleBothClicks(String title, String message, DialogInterface.OnClickListener Positive, String PositveName, DialogInterface.OnClickListener Nagative, String Nagativename) {
        if (!mActivity.isFinishing() && !mActivity.isDestroyed()) {
            AlertDialog.Builder mAlertDialogBuilder = new AlertDialog.Builder(mActivity);
            mAlertDialogBuilder.setTitle(title)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(PositveName, Positive)
                    .setNegativeButton(Nagativename, Nagative);

            if (mAlertDialog != null) {
                mAlertDialog.dismiss();
                mAlertDialog = null;
            }
            mAlertDialog = mAlertDialogBuilder.create();
            mAlertDialog.show();
        }

    }

    public DisplayMetrics getDisplayMetrics() {
        Display display = mActivity.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        return metrics;
    }

    public void errorDialog(String message) {
        if (!mActivity.isFinishing() && !mActivity.isDestroyed()) {
            AlertDialog.Builder mAlertDialogBuilder = new AlertDialog.Builder(mActivity);

            mAlertDialogBuilder.setTitle(mActivity.getResources().getString(R.string.app_name));
            mAlertDialogBuilder.setMessage(message).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });

            if (mAlertDialog != null) {
                mAlertDialog.dismiss();
                mAlertDialog = null;
            }
            mAlertDialog = mAlertDialogBuilder.create();
            mAlertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            mAlertDialog.show();
        }
    }

    public void errorDialogWithTitle(String title, String message) {
        if (!mActivity.isFinishing() && !mActivity.isDestroyed()) {
            AlertDialog.Builder mAlertDialogBuilder = new AlertDialog.Builder(mActivity);
            mAlertDialogBuilder.setTitle(title)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

            if (mAlertDialog != null) {
                mAlertDialog.dismiss();
                mAlertDialog = null;
            }
            mAlertDialog = mAlertDialogBuilder.create();
            mAlertDialog.show();
        }

    }

    public boolean haveInternet() {
        NetworkInfo mNetworkInfo = (NetworkInfo) ((ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (mNetworkInfo == null || !mNetworkInfo.isConnected()) {
            return false;
        }
        if (mNetworkInfo.isRoaming()) {
            return true;
        }
        return true;
    }

    public void hideKeyboard() {
        View view = mActivity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void showAnimatedProgress(Activity activity) {
        if (!mActivity.isFinishing() && !mActivity.isDestroyed()) {
            if (mDialogProgress != null && mDialogProgress.isShowing()) {
                mDialogProgress.dismiss();
            }

            if (mDialogProgress == null) {
                mDialogProgress = new Dialog(mActivity);
                mDialogProgress.requestWindowFeature(Window.FEATURE_NO_TITLE);
                mDialogProgress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                mDialogProgress.setContentView(R.layout.raw_progress_layout);
                mDialogProgress.setCancelable(false);
                mDialogProgress.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            }

            LottieAnimationView mLottieAnimationView = mDialogProgress.findViewById(R.id.raw_progress_layout_animationview);
            mLottieAnimationView.setImageAssetsFolder("images/");
            mLottieAnimationView.setAnimation("loader_old.json");
            mLottieAnimationView.loop(true);
            mLottieAnimationView.playAnimation();

            if (!mDialogProgress.isShowing())
                mDialogProgress.show();
        }
    }

    public void hideAnimatedProgress() {
        try {
            if (!mActivity.isFinishing() && !mActivity.isDestroyed()) {
                if (mDialogProgress != null && mDialogProgress.isShowing()) {
                    mDialogProgress.dismiss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean verifyPermissions() {
        boolean isPermissionGranted = true;
        boolean isPermissionNeverAsk = false;

        if (Build.VERSION.SDK_INT >= 23) {
            PERMISSIONCOUNT = PERMISSIONCOUNT + 1;
            if (PERMISSIONCOUNT < 6) {
                if (!requestForPermission()) {
                    requestForPermission();
                }
            } else {
                for (int i = 0; i < ALL_PERMS.length; i++) {
                    int permisionCode = getPermissionStatus(mActivity, ALL_PERMS[i]);
                    if (permisionCode == GRANTED) {
                    } else if (permisionCode == DENIED) {
                        isPermissionGranted = false;
                    } else if (permisionCode == BLOCKED_OR_NEVER_ASKED) {
                        isPermissionNeverAsk = true;
                    }
                }
            }
        }

        return isPermissionNeverAsk;
    }

    public boolean verifyPermissions(Activity activity, String[] ALL_PERMS) {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : ALL_PERMS) {
            result = ActivityCompat.checkSelfPermission(activity, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    MULTIPLE_PERMISSIONS_RESPONSE_CODE);
            return false;
        }
        return true;
    }

    public boolean requestForPermission() {
        boolean isPermissionOn = true;
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            if (!canAccessAllPermission()) {
                isPermissionOn = false;
                mActivity.requestPermissions(ALL_PERMS, ALL_REQUEST);
            }
        }

        return isPermissionOn;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({GRANTED, DENIED, BLOCKED_OR_NEVER_ASKED})
    public @interface PermissionStatus {
    }

    @PermissionStatus
    public static int getPermissionStatus(Activity activity, String androidPermissionName) {
        if (ContextCompat.checkSelfPermission(activity, androidPermissionName) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, androidPermissionName)) {
                return BLOCKED_OR_NEVER_ASKED;
            }
            return DENIED;
        }
        return GRANTED;
    }

    public boolean canAccessAllPermission() {
        boolean isPermissionAllowed = true;
        for (int i = 0; i < ALL_PERMS.length; i++) {
            if (!hasPermission(ALL_PERMS[i])) {
                isPermissionAllowed = false;
            }
        }
        return isPermissionAllowed;
    }

    public boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(mActivity, perm));
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static RequestOptions getCommanRequerstOptions() {
        return new RequestOptions()
                .dontAnimate()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher);
    }

}