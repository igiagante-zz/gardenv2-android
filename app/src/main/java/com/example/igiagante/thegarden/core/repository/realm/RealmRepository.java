package com.example.igiagante.thegarden.core.repository.realm;


import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.executor.JobExecutor;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.MapToRealm;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.MapperTest;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import java.util.Collection;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.functions.Func0;

/**
 * Created by igiagante on 14/11/17.
 */

public abstract class RealmRepository<Entity, RealmEntity extends RealmObject & RealmModel>  {

    protected Realm realm;
    protected final RealmConfiguration realmConfiguration;

    private MapperTest<RealmEntity, Entity> realmToModel;
    private MapToRealm<Entity, RealmEntity> modelToRealm;

    protected Class realmClass;

    abstract void setRealmClass();

    abstract MapToRealm<Entity, RealmEntity> initModelToRealmMapper(Realm realm);

    abstract MapperTest<RealmEntity, Entity> initRealmToModelMapper(Context context);

    private final ThreadExecutor executor;

    public RealmRepository(@NonNull Context context) {

        executor = new JobExecutor();

        // throw exception in case child class has not implemented setRealmClass()
        setRealmClass();

        Realm.init(context);
        this.realmConfiguration = new RealmConfiguration.Builder()
                .name(Repository.DATABASE_NAME_DEV)
                .deleteRealmIfMigrationNeeded()
                .build();

        this.realm = Realm.getInstance(realmConfiguration);

        modelToRealm = initModelToRealmMapper(this.realm);
        realmToModel = initRealmToModelMapper(context);
    }

    /*
    private void checkIfClassAttributeIsInitialized() {

        if(this.clazz == null) {
            throw new Exception("class property was not initialized");
        }
    }*/

    /**
     * Return a resource using the id
     *
     * @param id Object id
     * @return Observable<T>
     */
    public Observable getById(String id) {

        return Observable.defer(new Func0<Observable<Entity>>() {
            @Override
            public Observable<Entity> call() {

                Realm realm = Realm.getInstance(realmConfiguration);

                realm.beginTransaction();

                RealmEntity realmEntity = (RealmEntity) realm.where(realmClass)
                        .equalTo(Table.ID, id)
                        .findFirst();

                realm.commitTransaction();

                Entity entity = null;

                if(realmEntity != null) {
                    entity = realmToModel.map(realmEntity);
                    return  Observable.just(entity)
                            .subscribeOn(Schedulers.from(executor));
                } else {
                    return  Completable.complete().toObservable();
                }
            }
        });
    }

    /**
     * Return a resource using the name
     *
     * @param name Name of the resource
     * @return Observable<T>
     */
    public Observable getByName(String name){

        return Observable.defer(new Func0<Observable<Entity>>() {
            @Override
            public Observable<Entity> call() {

                Realm realm = Realm.getInstance(realmConfiguration);
                realm.beginTransaction();

                RealmEntity realmEntity = (RealmEntity) realm.where(realmClass)
                        .equalTo(Table.NAME, name)
                        .findFirst();

                realm.commitTransaction();

                Entity entity = null;

                if(realmEntity != null) {
                    entity = realmToModel.map(realmEntity);
                    return  Observable.just(entity)
                            .subscribeOn(Schedulers.from(executor));
                } else {
                    return  Completable.complete().toObservable();
                }
            }
        });
    }

    /**
     * Return an Object's id which was added
     *
     * @param item Object to be inserted into the repository
     * @return Observable<Entity> The Observable contains an object
     */
    @SuppressWarnings("unchecked")
    public Observable<Entity> add(final Entity item) {

        return Observable.defer(new Func0<Observable<Entity>>() {
            @Override
            public Observable<Entity> call() {

                Realm realm = Realm.getInstance(realmConfiguration);
                realm.beginTransaction();

                Observable<RealmEntity> result = (Observable<RealmEntity>) realm.copyToRealmOrUpdate(modelToRealm.map(item, realm))
                        .asFlowable().toObservable()
                        .subscribeOn(Schedulers.from(executor));

                realm.commitTransaction();

                return result.switchMap(realmEntity -> Observable.just(realmToModel.map(realmEntity)))
                        .subscribeOn(Schedulers.from(executor));
            }
        });
    }

    /**
     * Return an Object's id which was added
     *
     * @param item Object to be inserted into the repository
     * @return Observable<Entity> The Observable contains an object
     */
    @SuppressWarnings("unchecked")
    public Observable<Entity> update(final Entity item) {

        return Observable.defer(new Func0<Observable<Entity>>() {
            @Override
            public Observable<Entity> call() {

                Realm realm = Realm.getInstance(realmConfiguration);
                realm.beginTransaction();

                Observable<RealmEntity> result =
                        (Observable<RealmEntity>) realm.copyToRealmOrUpdate(modelToRealm.map(item, realm))
                        .asFlowable().toObservable()
                                .subscribeOn(Schedulers.from(executor));

                realm.commitTransaction();

                return result.switchMap(realmEntity -> Observable.just(realmToModel.map(realmEntity)))
                        .subscribeOn(Schedulers.from(executor));
            }
        });
    }

    /**
     * Return the number of objects which were added.
     *
     * @param items Objects to be inserted into the repository
     * @return Observable<Integer> The Observable contains the number of objects added
     */
    public Observable<Integer> add(Iterable<Entity> items){

        return Observable.defer(new Func0<Observable<Integer>>() {
            @Override
            public Observable<Integer> call() {

                Realm realm = Realm.getInstance(realmConfiguration);
                realm.executeTransaction(realmParam -> {
                    for (Entity item : items) {
                        realmParam.copyToRealmOrUpdate(modelToRealm.map(item, realm));
                    }
                });

                realm.close();

                int size = 0;

                if (items instanceof Collection<?>) {
                    size = ((Collection<?>) items).size();
                }

                return Observable.just(size).subscribeOn(Schedulers.from(executor));
            }
        });
    }

    /**
     * Return an observable with the integer, which indicates if the resource was deleted or not.
     *
     * @param id Id from Object to be deleted into the repository
     * @return Observable<Integer>
     */
    public Observable<Integer> remove(String id){

        return Observable.defer(new Func0<Observable<Integer>>() {
            @Override
            public Observable<Integer> call() {

                Realm realm = Realm.getInstance(realmConfiguration);

                RealmEntity realmEntity = (RealmEntity) realm.where(realmClass)
                        .equalTo(Table.ID, id)
                        .findFirst();

                realm.executeTransaction(realmParam -> {
                    if (realmEntity != null) {
                        realmEntity.deleteFromRealm();
                    }
                });

                realm.close();

                // if plantRealm.isValid() is false, it is because the realm object was deleted
                return Observable.just(realmEntity.isValid() ? -1 : 1)
                        .subscribeOn(Schedulers.from(executor));
            }
        });
    }

    /**
     * Return an observable a list of resources.
     *
     * @param specification {@link Specification}
     * @return Observable<List<T>>
     */
    @SuppressWarnings("unchecked")
    public Observable<List<Entity>> query(Specification specification){

        return Observable.defer(() -> {

        final Realm realm = Realm.getInstance(realmConfiguration);
        final Flowable<RealmResults<RealmEntity>> realmResults = specification.toFlowable(realm);

        // convert Flowable<RealmResults<RealmEntity>> into Observable<List<Entity>>
        return realmResults
                .filter(realms -> realms.isLoaded())
                .switchMap(realms ->
                        Flowable.fromIterable(realms)
                                .map(realmEntity -> realmToModel.map(realmEntity))
                )
                .toList()
                .toObservable()
                .subscribeOn(Schedulers.from(executor));
        });
    }

    public void removeAll() {

    }
}
