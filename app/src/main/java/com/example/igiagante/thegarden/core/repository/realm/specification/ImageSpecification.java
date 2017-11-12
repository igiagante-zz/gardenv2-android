package com.example.igiagante.thegarden.core.repository.realm.specification;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.ImageRealm;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 5/5/16.
 */
public class ImageSpecification implements RealmSpecification {

    @Override
    public Flowable<RealmResults<ImageRealm>> toFlowable(@NonNull Realm realm) {
        return realm.where(ImageRealm.class).findAll().asFlowable();
    }

    @Override
    public RealmResults<ImageRealm> toRealmResults(@NonNull Realm realm) {
        return realm.where(ImageRealm.class).findAll();
    }
}
