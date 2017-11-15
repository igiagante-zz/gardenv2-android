package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.repository.realm.UserRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.UserRealm;
import com.example.igiagante.thegarden.core.repository.restAPI.authentication.RestUserApi;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiGardenRepository;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiNutrientRepository;

import java.util.ArrayList;

import javax.inject.Inject;
import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 5/8/16.
 */
public class UserRepositoryManager
        extends BaseRepositoryManager<User, UserRealm, UserRealmRepository, RestUserApi>  {

    private UserRealmRepository realmRepository;
    private RestApiGardenRepository api;
    private RestApiNutrientRepository restApiNutrientRepository;

    @Inject
    public UserRepositoryManager(Context context, Session session) {
        super(context, new UserRealmRepository(context), new RestUserApi(context, session));
        realmRepository = new UserRealmRepository(context);
        api = new RestApiGardenRepository(context, session);
        restApiNutrientRepository = new RestApiNutrientRepository(context, session);
    }

    public Observable<Boolean> checkIfUserExistsInDataBase(@Nullable String userId) {
        return realmRepository.getById(userId).flatMap(user -> {
            if (user != null) {
                return Observable.just(true);
            }
            return Observable.just(false);
        });
    }

    public Observable<User> saveUser(@NonNull User user) {
        if (!checkInternet()) {
            return Observable.just(user);
        }
        return realmRepository.add(user);
    }

    public Observable<User> updateUser(@NonNull User user) {
        if (!checkInternet()) {
            return Observable.just(user);
        }
        return realmRepository.update(user);
    }

    /**
     * Return an observable a list of resources.
     *
     * @return Observable
     */
    public Observable query(@Nullable User user) {

        if (!checkInternet()) {
            return Observable.just(user);
        }

        //check if the user has gardens into the database
        Observable<User> query = realmRepository.getById(user.getId());

        return query.filter(userData ->
                        userData.getGardens() != null
                        && userData.getGardens().isEmpty()
                        && !TextUtils.isEmpty(userData.getUserName()))

                .flatMap(user1 -> api.getGardensByUser(user1.getUserName()),
                        (user1, gardensByUser) -> {

                            user1.setGardens((ArrayList<Garden>) gardensByUser);
                            return user1;
                        })

                .flatMap(user2 -> restApiNutrientRepository.getNutrientsByUser(user2.getUserName()),
                        (user2, nutrientsByUser) -> {
                            user2.setNutrients((ArrayList<Nutrient>) nutrientsByUser);
                            return user2;
                        })

                .flatMap(userUpdated -> realmRepository.update(userUpdated));
    }
}
