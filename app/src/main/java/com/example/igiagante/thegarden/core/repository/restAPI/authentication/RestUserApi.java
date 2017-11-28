package com.example.igiagante.thegarden.core.repository.restAPI.authentication;

import android.content.Context;
import android.util.Log;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.network.HttpStatus;
import com.example.igiagante.thegarden.core.repository.network.ServiceFactory;
import com.example.igiagante.thegarden.core.repository.realm.UserRealmRepository;
import com.example.igiagante.thegarden.core.repository.restAPI.services.UserRestApi;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * @author Ignacio Giagante, on 2/8/16.
 */
public class RestUserApi implements Repository<User> {

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
                realmRepository.save(session.getUser(), false);
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

    /**
     * Return a resource using the id
     *
     * @param id Object id
     * @return Observable<T>
     */
    public Observable<User> getById(String id) {
        return Observable.just(new User());
    }

    /**
     * Return a resource using the name
     *
     * @param name Name of the resource
     * @return Observable<T>
     */
    public Observable<User> getByName(String name) {
        return Observable.just(new User());
    }

    @Override
    public Observable<User> save(User item, boolean update) {
        return null;
    }

    /**
     * Return an Object's id which was added
     *
     * @param user Object to be inserted into the repository
     * @return Observable<T> The Observable contains an object
     */

    public Observable<User> add(User user) {
        return Observable.just(new User());
    }

    /**
     * Return the number of objects which were added.
     *
     * @param items Objects to be inserted into the repository
     * @return Observable<Integer> The Observable contains the number of objects added
     */
    public Observable<Integer> add(Iterable<User> items) {
        return Observable.just(1);
    }

    /**
     * Return an observable with the object updated.
     *
     * @param user Object to be updated into the repository
     * @return Observable<Integer> The Observable contains the number of objects added.
     */
    public Observable<User> update(User user) {
        return Observable.just(new User());
    }

    /**
     * Return an observable with the integer, which indicates if the resource was deleted or not.
     *
     * @param id Id from Object to be deleted into the repository
     * @return Observable<Integer>
     */
    public Observable<Boolean> remove(String id){
        return Observable.just(true);
    }


    public void removeAll() {

    }

    @Override
    public Observable<List<User>> getAll() {
        return null;
    }

    /**
     * Return an observable a list of resources.
     *
     * @param specification {@link Specification}
     * @return Observable<List<T>>
     */
    public Observable<List<User>> query(Specification specification) {
        return Observable.just(new ArrayList<User>());
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
