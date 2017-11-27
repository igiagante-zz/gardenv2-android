package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.RealmRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.realm.RealmObject;

/**
 * @author Ignacio Giagante, on 21/8/16.
 */
public class BaseRepositoryManager<Entity, RealmEntity extends RealmObject,
        DB extends RealmRepository<Entity, RealmEntity> ,
        API extends Repository<Entity>> {

    private Context context;
    protected DB db;
    protected API api;

    public BaseRepositoryManager(Context context, DB db, API api ) {
        this.context = context;
        this.db = db;
        this.api = api;
    }

    public DB getDB() {
        return this.db;
    }

    public API getApi() {
        return this.api;
    }



    public Observable<Entity> add(@NonNull Entity entity, Specification specification) {

        //Entity queryEntity = db.exits(entity.id)

        List<Entity> entities = db.query(specification).blockingFirst();

        Observable<Entity> dbResults = entities.isEmpty() ? Observable.empty()
                : Observable.just(entities.get(0));

        Observable<Entity> apiResults = api.save(entity);

        return !checkInternet() ? dbResults : Observable.concat(dbResults, apiResults).first(entity).toObservable();
    }

    public Observable<Entity> update(@NonNull Entity entity) {
        return !checkInternet() ? Observable.just(entity) : api.save(entity);
    }

    public Observable<Integer> delete(@NonNull String id) {

        if (!checkInternet()) {
            return Observable.just(-1);
        }

        // delete plant from api
        Observable<Integer> resultFromApi = api.remove(id);

        //Create a list of Integer in order to avoid calling Realm from other Thread
        Integer result = resultFromApi.subscribeOn(Schedulers.io()).blockingFirst();

        // delete plant from DB
        if (result!= -1) {
            Observable<Integer> resultFromDB = db.remove(id);
            result = resultFromDB.blockingFirst();
        }

        return result == -1 ? Observable.just(-1) : Observable.just(Integer.parseInt(id));
    }

    public Observable<List<Entity>> getAll() {

        Observable<List<Entity>> query = db.getAll();
        Observable<List<Entity>> apiResult = api.getAll();

        return !checkInternet() ? query : Observable.concat(query, apiResult).firstElement().toObservable();
    }

    public Observable<List<Entity>> query(Specification specification) {

        Observable<List<Entity>> query = db.query(specification);
        Observable<List<Entity>> apiResult = api.query(null);

        Observable<List<Entity>> obsCombined = query.switchIfEmpty(apiResult).flatMap(list -> {
            db.add(list);
            return Observable.just(list);
        });

        return !checkInternet() ? query : obsCombined;
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
