package com.ninise.pragmatictwitterclient.project.mvp.presenter.login.activity;

public interface ILoginView {

    void navigateToHomeActivity();
    void loginFailedNetworkNotFound();
    void loginFailedPermissionDenied();

}
