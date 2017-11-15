package com.example.igiagante.thegarden.core.repository.realm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.repository.MapToRealm;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.MapperTest;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.realm.mapper.GardenRealmToGarden;
import com.example.igiagante.thegarden.core.repository.realm.mapper.GardenToGardenRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.GardenRealm;
import com.example.igiagante.thegarden.core.repository.realm.specification.garden.GardenByIdSpecification;
import com.example.igiagante.thegarden.core.repository.realm.specification.garden.GardenByNameAndUserIdSpecification;
import com.example.igiagante.thegarden.core.repository.realm.specification.garden.GardenByNameSpecification;

import io.reactivex.Observable;
import io.realm.Realm;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class GardenRealmRepository extends RealmRepository<Garden, GardenRealm> {

    public GardenRealmRepository(@NonNull Context context) {
        super(context);
    }

    // Mapper<GardenRealm, Garden>
    MapToRealm<Garden, GardenRealm> initModelToRealmMapper(Realm realm) {
        return new GardenToGardenRealm(realm);
    }

    // Mapper<Garden, GardenRealm>
    MapperTest<GardenRealm, Garden> initRealmToModelMapper(Context context) {
        return new GardenRealmToGarden(context);
    }

    @Override
    void setRealmClass() {
        this.realmClass = GardenRealm.class;
    }

    /*
    @Override
    public Observable<Garden> getById(String id) {
        return query(new GardenByIdSpecification(id)).flatMap(Observable::fromIterable);
    }

    @Override
    public Observable<Garden> getByName(String name) {
        return query(new GardenByNameSpecification(name)).flatMap(Observable::fromIterable);
    } */

    public Observable<Garden> getByNameAndUserId(@NonNull String name, @NonNull String userId) {
        return query(new GardenByNameAndUserIdSpecification(name, userId)).flatMap(Observable::fromIterable);
    }
}
