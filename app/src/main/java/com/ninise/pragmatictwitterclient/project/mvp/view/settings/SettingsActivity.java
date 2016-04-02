package com.ninise.pragmatictwitterclient.project.mvp.view.settings;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.ninise.pragmatictwitterclient.R;
import com.ninise.pragmatictwitterclient.project.mvp.presenter.settings.ISettingsView;
import com.ninise.pragmatictwitterclient.project.mvp.presenter.settings.SettingsPresenter;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity implements ISettingsView {

    @Bind(R.id.settingsSaveButton) Button mSaveButton;
    @Bind(R.id.settingsToolBar) Toolbar mToolbar;
    @Bind(R.id.toolbarIcon) CircularImageView mToolbarIconCircularImageView;
    @Bind(R.id.toolbarTitle) TextView mToolbarTitleTextView;
    @Bind(R.id.settingsUpdatesSwitch) SwitchCompat mGithubUpdatesSwitchCompat;
    @Bind(R.id.settingsUpdatesCountSpinner) AppCompatSpinner mCountOfUpdatesCompatSpinner;
    @Bind(R.id.settingsCountOfTweetsSpinner) AppCompatSpinner mCountOfTweetsCompatSpinner;
    @BindString(R.string.settings_title) String mToolBarTitleString;
    @BindString(R.string.settings_changes_saved) String mChangesSaved;
    @BindString(R.string.settings_changes_not_saved) String mChangesSaveFailed;
    @BindDrawable(R.drawable.app_logo) Drawable mIconDrawable;

    private SettingsPresenter mPresenter;

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

        ArrayAdapter<?> adapterTweets =
                ArrayAdapter.createFromResource(
                        this,
                        R.array.tweets_count,
                        android.R.layout.simple_spinner_item);

        adapterTweets.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<?> adapterPosts =
                ArrayAdapter.createFromResource(
                        this,
                        R.array.posts_count,
                        android.R.layout.simple_spinner_item);

        adapterPosts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mCountOfTweetsCompatSpinner.setAdapter(adapterTweets);
        mCountOfUpdatesCompatSpinner.setAdapter(adapterPosts);

        RxView.clicks(mSaveButton).subscribe(aVoid -> {
            mPresenter.saveChanges(
                    this,
                    mGithubUpdatesSwitchCompat.isChecked(),
                    mCountOfUpdatesCompatSpinner.getSelectedItemPosition(),
                    mCountOfTweetsCompatSpinner.getSelectedItemPosition()
            );
            leaveFromActivity();
        });

        mPresenter = new SettingsPresenter(this);
        mPresenter.getUpdatesSwitchState(this);
        mPresenter.getCountsSpinnersState(this);
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

    @Override
    public void onSuccess() {
        Toast.makeText(this, mChangesSaved, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailed() {
        Toast.makeText(this, mChangesSaveFailed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setGitHubSwitchState(boolean state) {
        mGithubUpdatesSwitchCompat.setChecked(state);
    }

    @Override
    public void setCountsSpinners(int countOfPosts, int countOfTweets) {
        mCountOfUpdatesCompatSpinner.setSelection(countOfPosts);
        mCountOfTweetsCompatSpinner.setSelection(countOfTweets);
    }
}
