package com.example.igiagante.thegarden.core.repository.restAPI.authentication;

import android.content.Context;
import android.util.Log;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.repository.network.ErrorResponse;
import com.example.igiagante.thegarden.core.repository.network.HttpStatus;
import com.example.igiagante.thegarden.core.repository.network.ServiceFactory;
import com.example.igiagante.thegarden.core.repository.realm.UserRealmRepository;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.BaseRestApiRepository;
import com.example.igiagante.thegarden.core.repository.restAPI.services.UserRestApi;
import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * @author Ignacio Giagante, on 2/8/16.
 */
public class RestAuthApi extends BaseRestApiRepository<User> {

    private static final String TAG = RestAuthApi.class.getSimpleName();

    private final UserRestApi api;
    private UserRealmRepository realmRepository;

    private HttpStatus httpStatus;
    private Session session;
    private Context context;

    public RestAuthApi(Context context, Session session) {
        this.api = ServiceFactory.createRetrofitService(UserRestApi.class);
        this.httpStatus = new HttpStatus();
        this.session = session;
        this.context = context;
    }

    private String getErrorMessage(Response response) {

        String httpStatusValue = "Something was wrong";

        try {
            String error = response.errorBody().string();
            ErrorResponse errorResponse = new Gson().fromJson(error, ErrorResponse.class);
            httpStatusValue = httpStatus.getMessage(errorResponse.getErrorType());
        } catch (IOException ie) {
            Log.d(TAG, ie.getMessage());
        }

        return httpStatusValue;
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
                realmRepository.save(session.getUser(), false);

            } else {
                httpStatusValue = getErrorMessage(response);
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

                httpStatusValue = getErrorMessage(response);
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

    /**
     * Return an observable with the object updated.
     *
     * @param user Object to be updated into the repository
     * @return Observable<Integer> The Observable contains the number of objects added.
     */
    // TODO - Implementar edición de cuenta
    public Observable<User> update(User user) {
        return Observable.just(new User());
    }

    /**
     * Return an observable with the integer, which indicates if the resource was deleted or not.
     *
     * @param id Id from Object to be deleted into the repository
     * @return Observable<Integer>
     */
    // TODO - Implementar borrado de cuenta
    public Observable<Boolean> remove(String id){
        return Observable.just(true);
    }


    public class InnerResponse {

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
