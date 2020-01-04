package com.survey.surveyapp.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

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
import com.survey.surveyapp.databinding.ActivityCreateAccountBinding;
import com.survey.surveyapp.helper.TagValues;
import com.survey.surveyapp.viewmodels.CreateAccountViewModel;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import org.json.JSONObject;

import java.util.Arrays;

public class ActivityCreateAccount extends BaseActivity {

    private static final String CLASS_NAME = ActivityCreateAccount.class.getSimpleName();

    ActivityCreateAccountBinding mActivityCreateAccountBinding;

    CreateAccountViewModel mCreateAccountViewModel;

    String[] mStringProfession;
    String[] mStringAge;

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
        mActivityCreateAccountBinding = (ActivityCreateAccountBinding) putContentView(R.layout.activity_create_account);

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

        mActivityCreateAccountBinding.activityCreateTextCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCreateAccountViewModel.isValid()) {
                    mCreateAccountViewModel.doNormalRegister();
                }
            }
        });


        mActivityCreateAccountBinding.activityLoginEmailImageviewGooglePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        mActivityCreateAccountBinding.activityLoginEmailImageviewFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginManager.getInstance().logInWithReadPermissions(ActivityCreateAccount.this,
                        Arrays.asList("public_profile",
                                "user_friends",
                                "email",
                                "user_birthday",
                                "user_gender"));
            }
        });

        mActivityCreateAccountBinding.activityLoginEmailImageviewTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTwitterSignIn = true;

                mTwitterAuthClient.authorize(ActivityCreateAccount.this, new com.twitter.sdk.android.core.Callback<TwitterSession>() {
                    @Override
                    public void success(Result<TwitterSession> twitterSessionResult) {
                        // Success
                        Toast.makeText(ActivityCreateAccount.this, "Twitter Success!", Toast.LENGTH_SHORT).show();
                        // Do something with result, which provides a TwitterSession for making API calls
                        TwitterSession twitterSession = twitterSessionResult.data;

                        //call fetch email only when permission is granted
                        fetchTwitterEmail(twitterSession);

                    }

                    @Override
                    public void failure(TwitterException e) {
                        e.printStackTrace();
                        isTwitterSignIn = false;
                        Toast.makeText(ActivityCreateAccount.this, "Twitter Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mActivityCreateAccountBinding.activityLoginEmailImageviewInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityCreateAccount.this, "Instagram Login!", Toast.LENGTH_SHORT).show();
            }
        });

        mActivityCreateAccountBinding.activityLoginEmailImageviewLinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityCreateAccount.this, "LinkedIn Login!", Toast.LENGTH_SHORT).show();
//                isLinkedInSignIn = true;
//                loginHandle();
            }
        });

        mActivityCreateAccountBinding.activityLoginEmailImageviewPhoneNumberAuthentication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntentPhoneNumber = new Intent(ActivityCreateAccount.this, ActivityLoginPhoneNumber.class);
                startActivityForResult(mIntentPhoneNumber, PHONE_NUMBER_SIGN_IN);
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

                                        mCreateAccountViewModel.doSocialLogin(mStringSocialFirstName, mStringSocialLastName, mStringFacebookId, TagValues.SOCIAL_FACEBOOK_ID, mStringSocialEmail, mUtility.getAppPrefString(TagValues.PREF_SELECTED_ROLE_ID), "");

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

        mActivityCreateAccountBinding.activityLoginEmailEmailEdittextChooseUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 2) {
                    mCreateAccountViewModel.checkUserExist(charSequence.toString());
                } else {
                    mActivityCreateAccountBinding.activityLoginEmailEmailEdittextChooseUsername.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mCreateAccountViewModel.mBooleanUserExist.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isUserExist) {
                if (!isUserExist) {
                    mActivityCreateAccountBinding.activityLoginEmailEmailEdittextChooseUsername.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_correct_email, 0);
                } else {
                    mActivityCreateAccountBinding.activityLoginEmailEmailEdittextChooseUsername.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
            }
        });
    }

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

            mCreateAccountViewModel.doSocialLogin(mStringSocialFirstName, mStringSocialLastName, account.getId(), TagValues.SOCIAL_GOOGLE_PLUS_ID, account.getEmail(), mUtility.getAppPrefString(TagValues.PREF_SELECTED_ROLE_ID), "");
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Darshan", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(mContext, "Sign In Failed!", Toast.LENGTH_SHORT).show();
        }
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

                mCreateAccountViewModel.doSocialLogin(mStringSocialFirstName, mStringSocialLastName, Long.toString(twitterSession.getUserId()), TagValues.SOCIAL_TWITTER_ID, result.data, mUtility.getAppPrefString(TagValues.PREF_SELECTED_ROLE_ID), "");
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(ActivityCreateAccount.this, "Failed to authenticate. Please try again.", Toast.LENGTH_SHORT).show();
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

            TextView mTextViewItem = rowView.findViewById(R.id.raw_spinner_item_end_gravity_textview);
            mTextViewItem.setText(mStringItem[position]);

            return rowView;
        }
    }

}
