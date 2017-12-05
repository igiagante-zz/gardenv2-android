package com.example.igiagante.thegarden.core.repository.realm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.repository.MapToModel;
import com.example.igiagante.thegarden.core.repository.MapToRealm;
import com.example.igiagante.thegarden.core.repository.realm.mapper.UserRealmToUser;
import com.example.igiagante.thegarden.core.repository.realm.mapper.UserToUserRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.UserRealm;

import io.reactivex.Observable;
import io.realm.Realm;

/**
 * @author Ignacio Giagante, on 5/8/16.
 */
public class UserRealmRepository extends RealmRepository<User, UserRealm> {

    public UserRealmRepository(@NonNull Context context) {
        super(context);
    }


    // Mapper<GardenRealm, Garden>
    MapToRealm<User, UserRealm> initModelToRealmMapper(Realm realm) {
        return new UserToUserRealm(realm);
    }

    // Mapper<Garden, GardenRealm>
    MapToModel<UserRealm, User> initRealmToModelMapper() {
        return new UserRealmToUser();
    }

    @Override
    void setRealmClass() {
        this.realmClass = UserRealm.class;
    }

}
