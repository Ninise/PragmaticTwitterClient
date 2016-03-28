package com.ninise.pragmatictwitterclient.project.mvp.presenter.about;

public class AboutPresenter implements IAboutPresenter {

    private IAboutView mView;

    public AboutPresenter(IAboutView view) {
        this.mView = view;
    }

    @Override
    public void backToHome() {
        mView.onBackToHome();
    }
}
