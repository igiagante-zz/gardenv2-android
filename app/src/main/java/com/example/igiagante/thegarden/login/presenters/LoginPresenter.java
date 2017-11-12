package com.example.igiagante.thegarden.login.presenters;

import android.util.Log;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultObserver;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.login.view.LoginView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ignacio Giagante, on 2/8/16.
 */
@PerActivity
public class LoginPresenter extends AbstractPresenter<LoginView> {

    private final static String TAG = LoginPresenter.class.getSimpleName();

    private final UseCase loginUserUseCase;

    private final UseCase refreshTokenUseCase;

    private final UseCase existsUserUseCase;

    private final UseCase saveUserUseCase;

    private final UseCase updateUserUseCase;

    @Inject
    public LoginPresenter(@Named("loginUser") UseCase loginUserUseCase,
                          @Named("refreshToken") UseCase refreshTokenUseCase,
                          @Named("existsUser") UseCase existsUserUseCase,
                          @Named("saveUser") UseCase saveUserUseCase,
                          @Named("updateUser") UseCase updateUserUseCase) {

        this.loginUserUseCase = loginUserUseCase;
        this.refreshTokenUseCase = refreshTokenUseCase;
        this.existsUserUseCase = existsUserUseCase;
        this.saveUserUseCase = saveUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
    }

    public void destroy() {
        this.loginUserUseCase.dispose();
        this.refreshTokenUseCase.dispose();
        this.existsUserUseCase.dispose();
        this.saveUserUseCase.dispose();
        this.updateUserUseCase.dispose();
        this.view = null;
    }

    public void loginUser(User user) {
        this.loginUserUseCase.execute(user, new LoginUserObserver());
    }

    public void refreshToken() {
        this.refreshTokenUseCase.execute(null, new RrefreshTokenObserver());
    }

    public void existsUser(String userId) {
        this.existsUserUseCase.execute(userId, new UserExistsObserver());
    }

    public void saveUser(User user) {
        this.saveUserUseCase.execute(user, new SaveUserObserver());
    }

    private void notifyUserLogin(String result) {
        getView().notifyUserLogin(result);
    }

    private void sendNewToken(String token) {
        getView().sendNewToken(token);
    }

    private void userExists(Boolean exists) {
        getView().userExists(exists);
    }

    private void notifyUserWasPersisted() {
        getView().notifyUserWasPersisted();
    }

    private final class LoginUserObserver extends DefaultObserver<String> {

        @Override
        public void onComplete() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            //PlantListPresenter.this.hideViewLoading();
            //PlantListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            //PlantListPresenter.this.showViewRetry();
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onNext(String result) {
            LoginPresenter.this.notifyUserLogin(result);
        }
    }

    private final class RrefreshTokenObserver extends DefaultObserver<String> {

        @Override
        public void onComplete() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            //PlantListPresenter.this.hideViewLoading();
            //PlantListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            //PlantListPresenter.this.showViewRetry();
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onNext(String token) {
            LoginPresenter.this.sendNewToken(token);
        }
    }

    private final class UserExistsObserver extends DefaultObserver<Boolean> {

        @Override
        public void onComplete() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
        }

        @Override
        public void onNext(Boolean exists) {
            LoginPresenter.this.userExists(exists);
        }
    }

    private final class SaveUserObserver extends DefaultObserver<User> {

        @Override
        public void onComplete() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
        }

        @Override
        public void onNext(User user) {
            LoginPresenter.this.notifyUserWasPersisted();
        }
    }
}
