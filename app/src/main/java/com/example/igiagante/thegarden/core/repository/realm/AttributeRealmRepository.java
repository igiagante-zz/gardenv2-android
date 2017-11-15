package com.example.igiagante.thegarden.core.repository.realm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.repository.MapToRealm;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.MapperTest;
import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.mapper.AttributeRealmToAttribute;
import com.example.igiagante.thegarden.core.repository.realm.mapper.AttributeToAttributeRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.AttributeRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.PlantTable;
import com.example.igiagante.thegarden.core.repository.realm.specification.attribute.AttributeByIdSpecification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


/**
 * @author Ignacio Giagante, on 6/5/16.
 */
public class AttributeRealmRepository extends RealmRepository<Attribute, AttributeRealm> {

    @Override
    MapToRealm<Attribute, AttributeRealm> initModelToRealmMapper(Realm realm) {
        return null;
    }

    @Override
    MapperTest<AttributeRealm, Attribute> initRealmToModelMapper(Context context) {
        return null;
    }

    public AttributeRealmRepository(@NonNull Context context) {

        super(context);
    }

    @Override
    void setRealmClass() {
        this.realmClass = AttributeRealm.class;
    }
/*
    @Override
    public Observable<Attribute> getById(String id) {
        return query(new AttributeByIdSpecification(id)).flatMap(Observable::fromIterable);
    }

    @Override
    public Observable<Attribute> getByName(String name) {
        return null;
    }

    @Override
    public Observable<Attribute> add(Attribute attribute) {
        final Realm realm = Realm.getInstance(realmConfiguration);
        realm.executeTransaction(realmParam -> realmParam.copyToRealmOrUpdate(toAttributeRealm.map(attribute)));
        realm.close();

        return Observable.just(attribute);
    }

    @Override
    public Observable<Integer> add(Iterable<Attribute> attributes) {

        int size = 0;
        realm = Realm.getInstance(realmConfiguration);

        realm.executeTransaction(realmParam -> {
            for (Attribute attribute : attributes) {
                realmParam.copyToRealmOrUpdate(toAttributeRealm.map(attribute));
            }
        });

        realm.close();

        if (attributes instanceof Collection<?>) {
            size = ((Collection<?>) attributes).size();
        }

        return Observable.just(size);
    }

    @Override
    public Observable<Attribute> update(Attribute attribute) {
        realm = Realm.getInstance(realmConfiguration);

        AttributeRealm attributeRealm = realm.where(AttributeRealm.class).equalTo(PlantTable.ID, attribute.getId()).findFirst();

        realm.executeTransaction(realmParam -> {
            realmParam.copyToRealmOrUpdate(toAttributeRealm.copy(attribute, attributeRealm));
        });

        realm.close();

        return Observable.just(attribute);
    }

    @Override
    public Observable<Integer> remove(@NonNull String attributeId) {
        realm = Realm.getInstance(realmConfiguration);

        AttributeRealm attributeRealm = realm.where(AttributeRealm.class).equalTo(PlantTable.ID, attributeId).findFirst();
        realm.executeTransaction(realmParam -> attributeRealm.deleteFromRealm());

        realm.close();

        // if attributeRealm.isValid() is false, it is because the realm object was deleted
        return Observable.just(attributeRealm.isValid() ? 0 : 1);
    }

    @Override
    public void removeAll() {
        realm = Realm.getInstance(realmConfiguration);

        realm.executeTransaction(realmParam -> {
            RealmResults<AttributeRealm> result = realm.where(AttributeRealm.class).findAll();
            result.deleteAllFromRealm();
        });
        realm.close();
    }

    @Override
    public Observable<List<Attribute>> query(Specification specification) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;

        final Realm realm = Realm.getInstance(realmConfiguration);
        final Flowable<RealmResults<AttributeRealm>> realmResults = realmSpecification.toFlowable(realm);

        // convert Flowable<RealmResults<AttributeRealm>> into Observable<List<Attribute>>
        return realmResults
                .filter(plants -> plants.isLoaded())
                .switchMap(plants ->
                        Flowable.fromIterable(plants)
                                .map(attributeRealm -> toAttribute.map(attributeRealm))
                )
                .toList()
                .toObservable();
    } */
}
