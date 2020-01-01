package com.survey.surveyapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.survey.surveyapp.BaseActivity;
import com.survey.surveyapp.R;
import com.survey.surveyapp.databinding.ActivityLoginBinding;
import com.survey.surveyapp.helper.TagValues;
import com.survey.surveyapp.viewmodels.LoginViewModel;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class ActivityLogin extends BaseActivity {

    private static final String CLASS_NAME = ActivityLogin.class.getSimpleName();

    ActivityLoginBinding mActivityLoginBinding;

    LoginViewModel mLoginViewModel;

    //Google Plus Signin
    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 111;
    private static final int PHONE_NUMBER_SIGN_IN = 222;

    //Facebook Signin
    CallbackManager callbackManager;
    String mStringFacebookId = "";
    String mStringSocialName = "";
    String mStringSocialFirstName = "";
    String mStringSocialLastName = "";
    String mStringSocialEmail = "";
    String mStringSocialBirthdate = "";
    String mStringSocialGender = "";

    //Twitter SignIn
    TwitterAuthClient mTwitterAuthClient;
    boolean isTwitterSignIn = false;

    //LinkedIn SignIn
    boolean isLinkedInSignIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityLoginBinding = (ActivityLoginBinding) putContentView(R.layout.activity_login);

        initToolbar();
        mToolbarMain.setVisibility(View.GONE);

        mTwitterAuthClient = new TwitterAuthClient();

        //region Google Plus Data
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions);
        //endregion

        mLoginViewModel = new LoginViewModel(ActivityLogin.this, mMyService);

        mActivityLoginBinding.activityLoginImageviewGooglePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityLogin.this, "Google Login!", Toast.LENGTH_SHORT).show();

                Toast.makeText(ActivityLogin.this, "Google SignIn", Toast.LENGTH_SHORT).show();
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        mActivityLoginBinding.activityLoginImageviewFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityLogin.this, "Facebook Login!", Toast.LENGTH_SHORT).show();

                LoginManager.getInstance().logInWithReadPermissions(ActivityLogin.this,
                        Arrays.asList("public_profile",
                                "user_friends",
                                "email",
                                "user_birthday",
                                "user_gender"));
            }
        });

        mActivityLoginBinding.activityLoginImageviewTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityLogin.this, "Twitter Login!", Toast.LENGTH_SHORT).show();
                isTwitterSignIn = true;

                mTwitterAuthClient.authorize(ActivityLogin.this, new com.twitter.sdk.android.core.Callback<TwitterSession>() {
                    @Override
                    public void success(Result<TwitterSession> twitterSessionResult) {
                        // Success
                        Toast.makeText(ActivityLogin.this, "Twitter Success!", Toast.LENGTH_SHORT).show();
                        // Do something with result, which provides a TwitterSession for making API calls
                        TwitterSession twitterSession = twitterSessionResult.data;

                        //call fetch email only when permission is granted
                        fetchTwitterEmail(twitterSession);

                    }

                    @Override
                    public void failure(TwitterException e) {
                        e.printStackTrace();
                        isTwitterSignIn = false;
                        Toast.makeText(ActivityLogin.this, "Twitter Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mActivityLoginBinding.activityLoginImageviewInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityLogin.this, "Instagram Login!", Toast.LENGTH_SHORT).show();
            }
        });

        mActivityLoginBinding.activityLoginImageviewLinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityLogin.this, "LinkedIn Login!", Toast.LENGTH_SHORT).show();
//                isLinkedInSignIn = true;
//                loginHandle();
            }
        });

        mActivityLoginBinding.activityLoginImageviewPhoneNumberAuthentication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntentPhoneNumber = new Intent(ActivityLogin.this, ActivityLoginPhoneNumber.class);
                startActivityForResult(mIntentPhoneNumber, PHONE_NUMBER_SIGN_IN);
            }
        });

        mActivityLoginBinding.activityLoginTextviewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntentLogin = new Intent(ActivityLogin.this, ActivityLoginEmail.class);
                startActivity(mIntentLogin);
            }
        });

        mActivityLoginBinding.activityLoginTextviewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntentCreateAccount = new Intent(ActivityLogin.this, ActivityCreateAccount.class);
                startActivity(mIntentCreateAccount);
            }
        });

        //Facebook Login Listener
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {

                        //region GRAPH REQUEST FOR PROFILE DETAIL
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {

//                                        mUtility.writeSharedPreferencesString(Constant.PREF_FACEBOOK_TOKEN, loginResult.getAccessToken().getToken());
                                        ShowLog(CLASS_NAME, "Facebook Login Data", object.toString());
                                        ShowLog(CLASS_NAME, "Facebook Access Token", loginResult.getAccessToken().getToken());

                                        mStringFacebookId = object.optString("id");
                                        mStringSocialName = object.optString("name");
                                        mStringSocialFirstName = object.optString("first_name");
                                        mStringSocialLastName = object.optString("last_name");
                                        mStringSocialEmail = object.optString("email");
                                        mStringSocialBirthdate = object.optString("birthday");
                                        mStringSocialGender = object.optString("gender");

                                        mLoginViewModel.doSocialLogin(mStringSocialFirstName, mStringSocialLastName, mStringFacebookId, TagValues.SOCIAL_FACEBOOK_ID, mStringSocialEmail, mUtility.getAppPrefString(TagValues.PREF_SELECTED_ROLE_ID), "");

                                        //TODO CALL SOCIAL LOGIN WEB-SERVICE

                                        //region GRAPH REQUEST FOR USER PHOTOS
                                        GraphRequest mGraphRequest = new GraphRequest(AccessToken.getCurrentAccessToken(),
                                                "/" + mStringFacebookId + "/photos?type=uploaded",
                                                null,
                                                HttpMethod.GET,
                                                new GraphRequest.Callback() {
                                                    @Override
                                                    public void onCompleted(GraphResponse response) {
                                                        ShowLog(CLASS_NAME, "Graph Response of Photos", response.toString());
                                                    }
                                                });
                                        mGraphRequest.executeAsync();
                                        //endregion

                                    }
                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id, name, first_name, last_name, email, birthday, gender");
                        request.setParameters(parameters);
                        request.executeAsync();
                        //endregion
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException exception) {
                        exception.printStackTrace();
                    }
                });

        GenerateKeyHash();

    }

//    public void loginHandle() {
//        LISessionManager.getInstance(getApplicationContext()).init(ActivityLogin.this, buildScope(), new AuthListener() {
//            @Override
//            public void onAuthSuccess() {
//                // Authentication was successful.  You can now do other calls with the SDK.
//                Toast.makeText(ActivityLogin.this, "Successfully Logged In!!!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAuthError(LIAuthError error) {
//                // Handle authentication errors
//                Toast.makeText(getApplicationContext(), "Login Error " + error.toString(), Toast.LENGTH_LONG).show();
//            }
//        }, true);
//    }

//    private static Scope buildScope() {
//        return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (isTwitterSignIn) {
            mTwitterAuthClient.onActivityResult(requestCode, resultCode, data);
        }
//        else if (isLinkedInSignIn) {
//            LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
//        }
        else {
            super.onActivityResult(requestCode, resultCode, data);

            // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                // The Task returned from this call is always completed, no need to attach
                // a listener.
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            } else {
                callbackManager.onActivityResult(requestCode, resultCode, data);
            }
        }

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            Toast.makeText(mContext, "Sign In Successful!", Toast.LENGTH_SHORT).show();

            mStringSocialFirstName = "";
            mStringSocialLastName = "-";

            if (account.getDisplayName().contains(" ")) {
                String userNames[] = account.getDisplayName().split(" ");
                mStringSocialFirstName = userNames[0];
                mStringSocialLastName = userNames[1];
            } else {
                mStringSocialFirstName = account.getDisplayName();
            }

            mLoginViewModel.doSocialLogin(mStringSocialFirstName, mStringSocialLastName, account.getId(), TagValues.SOCIAL_GOOGLE_PLUS_ID, account.getEmail(), mUtility.getAppPrefString(TagValues.PREF_SELECTED_ROLE_ID), "");
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Darshan", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(mContext, "Sign In Failed!", Toast.LENGTH_SHORT).show();
        }
    }

    public void GenerateKeyHash() {
        ShowLog(CLASS_NAME, "GenerateKeyHash()", getPackageName());
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                ShowLog(CLASS_NAME, "KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void alertDisplayer(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityLogin.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        // don't forget to change the line below with the names of your Activities
//                        Intent intent = new Intent(ActivityLogin.this, LogoutActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }

    public void fetchTwitterEmail(final TwitterSession twitterSession) {
        mTwitterAuthClient.requestEmail(twitterSession, new Callback<String>() {
            @Override
            public void success(Result<String> result) {
                //here it will give u only email and rest of other information u can get from TwitterSession
                ShowLog(CLASS_NAME, "Twitter ID", Long.toString(twitterSession.getUserId()));
                ShowLog(CLASS_NAME, "Twitter Name", twitterSession.getUserName());
                ShowLog(CLASS_NAME, "Twitter Email", result.data);

                mStringSocialFirstName = "";
                mStringSocialLastName = "-";

                if (twitterSession.getUserName().contains(" ")) {
                    String userNames[] = twitterSession.getUserName().split(" ");
                    mStringSocialFirstName = userNames[0];
                    mStringSocialLastName = userNames[1];
                } else {
                    mStringSocialFirstName = twitterSession.getUserName();
                }

                mLoginViewModel.doSocialLogin(result.data, "", Long.toString(twitterSession.getUserId()), TagValues.SOCIAL_TWITTER_ID, result.data, mUtility.getAppPrefString(TagValues.PREF_SELECTED_ROLE_ID), "");
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(ActivityLogin.this, "Failed to authenticate. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
