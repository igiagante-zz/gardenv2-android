package com.example.igiagante.thegarden.core.repository.restAPI.authentication;

import android.content.Context;
import android.util.Log;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.repository.network.HttpStatus;
import com.example.igiagante.thegarden.core.repository.network.ServiceFactory;
import com.example.igiagante.thegarden.core.repository.realm.UserRealmRepository;
import com.example.igiagante.thegarden.core.repository.restAPI.services.UserRestApi;
import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * @author Ignacio Giagante, on 2/8/16.
 */
public class RestUserApi {

    private static final String TAG = RestUserApi.class.getSimpleName();

    private final UserRestApi api;
    private UserRealmRepository realmRepository;

    private HttpStatus httpStatus;
    private Session session;
    private Context context;

    public RestUserApi(Context context, Session session) {
        this.api = ServiceFactory.createRetrofitService(UserRestApi.class);
        this.httpStatus = new HttpStatus();
        this.session = session;
        this.context = context;
    }

    public Observable<String> registerUser(User user) {

        Observable<Response<InnerResponse>> result = api.createUser(user.getUserName(), user.getPassword());

        return result.flatMap(response -> {
            String httpStatusValue = "";
            if (response.isSuccessful()) {

                session.getUser().setUserName(user.getUserName());
                session.setToken(response.body().getToken());

                httpStatusValue = httpStatus.getHttpStatusValue(response.code());

                realmRepository = new UserRealmRepository(context);
                realmRepository.add(session.getUser());
            } else {
                try {
                    // TODO - java.lang.IllegalStateException: Expected BEGIN_OBJECT but was STRING at line 1 column 1 path $
                    // if the error has not the structure of the MessageError, Gson cannot parse it.
                    String error = response.errorBody().string();
                    MessageError messageErrorKey = new Gson().fromJson(error, MessageError.class);
                    httpStatusValue = httpStatus.getMessage(messageErrorKey.getMessage());
                } catch (IOException ie) {
                    Log.d(TAG, ie.getMessage());
                }
            }

            return Observable.just(httpStatusValue);
        });
    }

    public Observable<String> loginUser(User user) {

        Observable<Response<InnerResponse>> result = api.loginUser(user.getUserName(), user.getPassword());

        User sessionUser = session.getUser();

        return result.flatMap(response -> {

            String httpStatusValue = "";

            if (response.isSuccessful()) {

                sessionUser.setUserName(user.getUserName());
                session.setToken(response.body().getToken());

                httpStatusValue = httpStatus.getHttpStatusValue(response.code());
            } else {
                try {
                    String error = response.errorBody().string();
                    MessageError messageErrorKey = new Gson().fromJson(error, MessageError.class);
                    httpStatusValue = httpStatus.getMessage(messageErrorKey.getMessage());
                } catch (IOException ie) {
                    Log.d(TAG, ie.getMessage());
                }
            }
            return Observable.just(httpStatusValue);
        });
    }

    public Observable<String> refreshToken(String userId) {

        Observable<Response<InnerResponse>> result = api.refreshToken(userId);

        return result.flatMap(response -> {
            String httpStatusValue = "";
            if (response.isSuccessful()) {
                session.setToken(response.body().getToken());
                httpStatusValue = httpStatus.getHttpStatusValue(response.code());
            }
            return Observable.just(httpStatusValue);
        });
    }

    public class InnerResponse {

        private boolean success;
        private String token;
        private String message;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public class MessageError {

        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
