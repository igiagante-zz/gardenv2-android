package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.RealmRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.realm.RealmObject;

/**
 * @author Ignacio Giagante, on 21/8/16.
 */
public class BaseRepositoryManager<Entity extends RealmRepository.Identifiable, RealmEntity extends RealmObject,
        DB extends RealmRepository<Entity, RealmEntity>,
        API extends Repository<Entity>> {

    private Context context;
    protected DB db;
    protected API api;

    public BaseRepositoryManager(Context context, DB db, API api) {
        this.context = context;
        this.db = db;
        this.api = api;
    }

    public API getApi() {
        return this.api;
    }

    public Observable<Entity> getById(String id){
        return db.getById(id);
    }

    public Observable<Boolean> exists(@NonNull Entity entity) {
        return db.checkIfRealmObjectExists(entity);
    }

    public Observable<Entity> save(@NonNull Entity entity)
    {
        return db.checkIfRealmObjectExists(entity)
                .flatMap(entityExists -> api.save(entity, entityExists));
    }

    public Observable<Boolean> delete(@NonNull String id) {

        if (!checkInternet()) {
            return Observable.just(false);
        }

        return api.remove(id)
                .flatMap(entityDeletedFromAPI -> db.remove(id))
                .flatMap(entityDeletedFromDB -> Observable::just);
    }

    public Observable<List<Entity>> getAll() {

        Observable<List<Entity>> dbResult = db.getAll();
        Observable<List<Entity>> apiResult = api.getAll();

        Observable<List<Entity>> flatMap = apiResult.flatMap(entityList -> {
            Log.d("DB", "size of : " + entityList.getClass().getName() + " list " + entityList.size());
            return db.add(entityList);
        });

        return Observable.concat(flatMap, dbResult)
                 .doOnNext(entityList -> Log.d("DB", "size of list concat  --> " + entityList.size()))
                .filter(entityList -> !entityList.isEmpty())
                .first(new ArrayList<>())
                .toObservable();
    }

    public Observable<List<Entity>> query(@NonNull Specification specification) {

        Observable<List<Entity>> dbResult = db.query(specification);
        Observable<List<Entity>> apiResult = api.query(null);

        return Observable.concat(dbResult, apiResult).firstElement().toObservable();
    }

    protected boolean checkInternet() {

        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }
}
