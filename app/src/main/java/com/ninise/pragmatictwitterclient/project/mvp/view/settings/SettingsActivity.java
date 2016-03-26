package com.ninise.pragmatictwitterclient.project.mvp.view.settings;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.ninise.pragmatictwitterclient.R;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity {

    @Bind(R.id.settingsSaveButton) Button mSaveButton;
    @Bind(R.id.settingsToolBar) Toolbar mToolbar;
    @Bind(R.id.toolbarIcon) CircularImageView mToolbarIconCircularImageView;
    @Bind(R.id.toolbarTitle) TextView mToolbarTitleTextView;
    @BindString(R.string.settings_title) String mToolBarTitleString;
    @BindDrawable(R.drawable.app_logo) Drawable mIconDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mToolbar.setNavigationIcon(R.drawable.ic_action_hardware_keyboard_backspace);
        mToolbar.setNavigationOnClickListener(v -> leaveFromActivity());

        mToolbarIconCircularImageView.setImageDrawable(mIconDrawable);
        mToolbarTitleTextView.setText(mToolBarTitleString);

        RxView.clicks(mSaveButton).subscribe(aVoid -> leaveFromActivity());
    }

    private void leaveFromActivity() {
        onBackPressed();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
