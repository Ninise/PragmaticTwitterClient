package com.ninise.pragmatictwitterclient.project.mvp.view.about;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding.support.v7.widget.RxToolbar;
import com.jakewharton.rxbinding.view.RxView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.ninise.pragmatictwitterclient.R;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity {

    @Bind(R.id.aboutToolbar) Toolbar mAboutToolbar;
    @Bind(R.id.toolbarTitle) TextView mToolbarTitleTextView;
    @Bind(R.id.aboutOkButton) Button mOkButton;
    @BindString(R.string.about) String mToolbarTitleString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        setSupportActionBar(mAboutToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mAboutToolbar.setNavigationIcon(R.drawable.ic_action_hardware_keyboard_backspace);
        mAboutToolbar.setNavigationOnClickListener(v -> leaveFromActivity());

        mToolbarTitleTextView.setText(mToolbarTitleString);

        RxView.clicks(mOkButton).subscribe(v -> {

            leaveFromActivity();
        });
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
