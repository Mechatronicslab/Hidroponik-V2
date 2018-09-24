package com.example.omgimbot.hidroponik.features.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omgimbot.hidroponik.features.auth.model.LoginResponse;
import com.example.omgimbot.hidroponik.ui.CustomDrawable;
import com.example.omgimbot.hidroponik.ui.TopSnakbar;
import com.example.omgimbot.hidroponik.utils.Utils;
import com.example.omgimbot.hidroponik.R;
import com.google.gson.Gson;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.shuhart.stepview.StepView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthActivity extends AppCompatActivity implements IAuthView,View.OnClickListener {
    private boolean isSigninScreen = true;
    private final String TAG = "Auth Activity";
    private int currentStep = 0;
    private int signupType = 1;
    @BindView(R.id.signup_invoker_text)
    TextView mSignpInvokerText;
    @BindView(R.id.signup_text)
    TextView mSignupText;
    @BindView(R.id.signup_layout)
    LinearLayout mSignupLayout;
    @BindView(R.id.signup_button)
    Button mSignupButton;
    @BindView(R.id.signin_layout)
    LinearLayout mSigninLayout;
    @BindView(R.id.signin_invoker_text)
    TextView mSignInInvokerText;
    @BindView(R.id.signin_button)
    Button mSignInButton;
    @BindView(R.id.user_login_edit)
    TextView mUserLoginEdit;
    @BindView(R.id.password_login_edit)
    TextView mPasswordLoginEdit;
    @BindView(R.id.loading_layout)
    RelativeLayout mLoadingIndicator;
    @BindView(R.id.step_view)
    StepView mStepView;
    @BindView(R.id.next_button)
    ImageButton mNextButton;
    @BindView(R.id.back_button)
    ImageButton mPrevButton;
    @BindView(R.id.name_signup_edit)
    EditText mNameSignupEdit;
    @BindView(R.id.username_signup_edit)
    EditText mUsernameSignupEdit;
    @BindView(R.id.name_layout)
    LinearLayout mNameLayout;
    @BindView(R.id.password_signup_edit)
    EditText mPasswordSignupEdit;
    @BindView(R.id.passwordrepeat_signup_edit)
    EditText mPasswordRepeatSignupEdit;
    @BindView(R.id.password_layout)
    LinearLayout mPasswordLayout;
    @BindView(R.id.verification_layout)
    LinearLayout mVerificationLayout;
    @BindView(R.id.email_signup_edit)
    EditText mEmailSignupEdit;
    @BindView(R.id.phone_signup_edit)
    EditText mPhoneSignupEdit;
    @BindView(R.id.verification_switch)
    SwitchCompat mVerificationSwitch;
    @BindView(R.id.more_image)
    ImageView mMoreImage;

    AuthPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        presenter = new AuthPresenter(this);
        /*if (presenter.isLoggedIn())
            this.goToDashboard();
        else this.initViews();*/
        initViews();
    }

    @Override
    public void initViews(){
        String tmp = "Belum punya akun? <b>DAFTAR</b>";
        mSignupText.setText(Html.fromHtml(tmp));
        mSignupText.setOnClickListener(this);
        mVerificationSwitch.setEnabled(false);
        mSigninLayout.setOnClickListener(this);
        mSignupLayout.setOnClickListener(this);
        mSignInInvokerText.setOnClickListener(this);
        mSignpInvokerText.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        mPrevButton.setOnClickListener(this);
        mSignupButton.setOnClickListener(this);
        mSignInButton.setOnClickListener(this);
        mMoreImage.setOnClickListener(this);
        mPrevButton.setImageDrawable(CustomDrawable.googleMaterialDrawable(
                this, R.color.colorPrimary, 30,
                GoogleMaterial.Icon.gmd_keyboard_arrow_left
        ));
        mNextButton.setImageDrawable(CustomDrawable.googleMaterialDrawable(
                this, R.color.colorPrimary, 30,
                GoogleMaterial.Icon.gmd_keyboard_arrow_right
        ));
        mMoreImage.setImageDrawable(CustomDrawable.googleMaterialDrawable(
                this, R.color.colorPrimary, 20,
                GoogleMaterial.Icon.gmd_more_horiz
        ));

        mVerificationSwitch.setOnClickListener(view -> TopSnakbar.showWarning(AuthActivity.this,
                "Maaf, untuk sementara pendaftaran menggunakan alamat email belum tersedia"));

        mVerificationSwitch.setOnCheckedChangeListener((compoundButton, checked) -> {
            if (checked) {
                mPhoneSignupEdit.setVisibility(View.VISIBLE);
                mEmailSignupEdit.setVisibility(View.GONE);
                signupType = 1;
            } else {
                mEmailSignupEdit.setVisibility(View.VISIBLE);
                mPhoneSignupEdit.setVisibility(View.GONE);
                signupType = 2;

                mVerificationSwitch.setChecked(true);
            }
        });

        mVerificationSwitch.setChecked(true);
        this.showSigninForm();
        this.setNameLayoutVisible(true);

    }

    @Override
    public void showSignupForm() {
        ((LinearLayout.LayoutParams) mSigninLayout.getLayoutParams()).weight = 0.15f;
        mSigninLayout.requestLayout();
        ((LinearLayout.LayoutParams) mSignupLayout.getLayoutParams()).weight = 0.85f;
        mSignupLayout.requestLayout();
        mSignpInvokerText.setVisibility(View.GONE);
        mMoreImage.setVisibility(View.GONE);
        mSignInInvokerText.setVisibility(View.VISIBLE);
        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_right_to_left);
        mSignupLayout.startAnimation(translate);
        Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_left);
        mSignupButton.startAnimation(clockwise);

    }

    @Override
    public void showSigninForm() {
        ((LinearLayout.LayoutParams) mSigninLayout.getLayoutParams()).weight = 0.85f;
        mSigninLayout.requestLayout();
        ((LinearLayout.LayoutParams) mSignupLayout.getLayoutParams()).weight = 0.15f;
        mSignupLayout.requestLayout();
        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_left_to_right);
        mSigninLayout.startAnimation(translate);
        mSignpInvokerText.setVisibility(View.VISIBLE);
        mMoreImage.setVisibility(View.VISIBLE);
        mSignInInvokerText.setVisibility(View.GONE);
        Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_left_to_right);
        mSignInButton.startAnimation(clockwise);
    }

    @Override
    public void showLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public boolean isNameLayoutReady() {
        boolean valid = false;
        String name = mNameSignupEdit.getText().toString();
        String username = mUsernameSignupEdit.getText().toString();
        if (!name.equals("") || !username.equals("")) {
            if (name.length() >= 8 && username.length() >= 6) {
                if (!Utils.isContainUpperCase(username)) {
                    valid = true;
                } else TopSnakbar.showWarning(this,
                        "username hanya boleh huruf kecil tanpa karakter spesial");
            } else TopSnakbar.showWarning(this,
                    "Nama atau username terlalu pendek. Nama lengkap minimal 8 karakter, " +
                            "username minimal 6 karakter");
        } else TopSnakbar.showWarning(this,
                "Harap isi semua kolom terlebih dahulu");

        return valid;
    }

    @Override
    public void setNameLayoutVisible(boolean visible) {
        if (visible) {
            mNameLayout.setVisibility(View.VISIBLE);
            this.setVerificationLayoutVisible(false);
            this.setPasswordLayoutVisible(false);
        } else mNameLayout.setVisibility(View.GONE);
    }

    @Override
    public boolean isVerificationLayoutReady() {
        boolean valid = false;
        String phone = mPhoneSignupEdit.getText().toString();
        String email = mEmailSignupEdit.getText().toString();
        boolean isPhone = mVerificationSwitch.isChecked();
        if (isPhone) {
            if (phone.length() >= 9) {
                valid = true;
            } else TopSnakbar.showWarning(this, "Nomor telepon minimal 10 karakter");
        } else {
            if (email.length() >= 6) {
                valid = true;
            } else TopSnakbar.showWarning(this, "Email minimal 6 karakter");
        }
        return valid;
    }

    @Override
    public void setVerificationLayoutVisible(boolean visible) {
        if (visible) {
            mVerificationLayout.setVisibility(View.VISIBLE);
            this.setNameLayoutVisible(false);
            this.setPasswordLayoutVisible(false);
        } else mVerificationLayout.setVisibility(View.GONE);
    }

    @Override
    public boolean isPasswordLayoutReady() {
        boolean valid = false;
        String password = mPasswordSignupEdit.getText().toString();
        String passwordRepeat = mPasswordRepeatSignupEdit.getText().toString();
        if (password.length() > 5 && passwordRepeat.length() > 5) {
            if (password.equals(passwordRepeat)) {
                valid = true;
            } else TopSnakbar.showWarning(this, "kata sandi tidak cocok");
        } else TopSnakbar.showWarning(this, "Password minimal 6 karakter");
        return valid;
    }

    @Override
    public void setPasswordLayoutVisible(boolean visible) {
        if (visible) {
            mPasswordLayout.setVisibility(View.VISIBLE);
            this.setNameLayoutVisible(false);
            this.setVerificationLayoutVisible(false);
        } else mPasswordLayout.setVisibility(View.GONE);
    }

    @Override
    public void showSignupButton() {
        mSignupButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void signIn() {
        presenter.login(
                mUserLoginEdit.getText().toString(),
                mPasswordLoginEdit.getText().toString()
        );

    }

    @Override
    public void signUp() {
        /*RegistrationModel model = new RegistrationModel();
        model.setName(mNameSignupEdit.getText().toString());
        model.setUsername(mUsernameSignupEdit.getText().toString());
        model.setEmail(mEmailSignupEdit.getText().toString());
        model.setPhone(mPhoneSignupEdit.getText().toString());
        model.setSignupType(signupType);
        model.setCityid(getApplication().getResources().getString(R.string.pt_id));
        model.setPassword(mPasswordSignupEdit.getText().toString());
        model.setRole(getApplication().getResources().getString(R.string.pt_role));
        presenter.signup(model);*/
    }

    @Override
    public void onSigninSuccess(LoginResponse response) {
        Log.i(TAG, "Login success");
        /*presenter.storeProfile(new Gson().toJson(response));
        presenter.storeAccessToken(response.getResult().getAccessToken());*/
        Toast.makeText(this, "Signin berhasil", Toast.LENGTH_SHORT).show();
        //this.goToDashboard();
    }

    @Override
    public void onSignupSuccess(/*SignupResponse response*/) {
        Log.i(TAG, "Signup success");
        /*SweetDialogs.commonSuccess(this, response.getRm(), string -> {
            this.showSigninForm();
        });*/

    }

    @Override
    public void onRequestFailed(String rm) {
        Log.e(TAG, rm);
        /*SweetDialogs.commonWarning(this, "Gagal memuat permintaan",
                rm, false);*/
        Toast.makeText(this, rm, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkFailed(String cause) {
        Log.e(TAG, cause);
        //SweetDialogs.endpointError(this);
    }

    @Override
    public void goToDashboard() {
       // startActivity(new Intent(this, TrackingActivity.class));
        finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin_invoker_text:
                isSigninScreen = true;
                this.showSigninForm();
                break;
            case R.id.signup_invoker_text:
                isSigninScreen = false;
                this.showSignupForm();
                break;
            case R.id.signup_button:
                if (this.isNameLayoutReady() && this.isVerificationLayoutReady() && this.isPasswordLayoutReady()) {
                    this.signUp();
                } else
                    TopSnakbar.showWarning(this, "Harap periksa kembali semua kolom pendaftaran Anda");
                break;
            case R.id.signin_button:
                if (mUserLoginEdit.getText().toString().equals("") || mPasswordLoginEdit.getText().toString().equals("")) {
                    TopSnakbar.showWarning(this, "Kolom tidak boleh kosong");
                } else this.signIn();
                break;
            case R.id.signin_layout | R.id.signup_layout:
                InputMethodManager methodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                methodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                break;
            case R.id.signup_text:
                isSigninScreen = false;
                this.showSignupForm();
                break;
            case R.id.next_button:
                if (currentStep < mStepView.getStepCount() - 1) {
                    switch (currentStep) {
                        case 0:
                            if (this.isNameLayoutReady()) {
                                currentStep++;
                                mStepView.go(currentStep, true);
                                this.setVerificationLayoutVisible(true);
                            }
                            break;
                        case 1:
                            if (this.isVerificationLayoutReady()) {
                                currentStep++;
                                mStepView.go(currentStep, true);
                                this.setPasswordLayoutVisible(true);
                            }
                            break;
                    }
                } else {
                    if (this.isPasswordLayoutReady()) {
                        mStepView.done(true);
                        this.showSignupButton();
                        TopSnakbar.showSuccess(this, "Kolom sudah semua terisi, silahkan melanjutkan pendaftaran");
                    }

                }
                break;
            case R.id.back_button:
                if (currentStep > 0) {
                    currentStep--;
                    if (currentStep == 0)
                        this.setNameLayoutVisible(true);
                    else if (currentStep == 1)
                        this.setVerificationLayoutVisible(true);
                    else if (currentStep == 2)
                        this.setPasswordLayoutVisible(true);
                }
                mStepView.done(false);
                mStepView.go(currentStep, true);
                break;
            /*case R.id.more_image:
                PopupMenu popup = new PopupMenu(this, mMoreImage);
                popup.inflate(R.menu.menu_auth);
                popup.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.menu_verifikasi:

                            break;
                        case R.id.menu_forgot_password:
                            startActivity(new Intent(getBaseContext(), ForgotPasswordActivity.class));
                            break;
                    }
                    return true;
                });
                popup.show();
                break;*/
        }

    }

}
