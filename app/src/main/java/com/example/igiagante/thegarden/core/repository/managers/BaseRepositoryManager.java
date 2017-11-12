package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Ignacio Giagante, on 21/8/16.
 */
public class BaseRepositoryManager<T, DB extends Repository<T>, API extends Repository<T>> {

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

    public Observable<T> add(@NonNull T entity, Specification specification) {

        // there should be only one entity with this name
        Observable<T> query = db.query(specification)
                .flatMap(list -> Observable.just(list.get(0)));

        Observable<T> apiResults = api.add(entity);

        return !checkInternet() ? query : Observable.concat(query, apiResults).first(entity).toObservable();
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


    public Observable<T> update(@NonNull T entity) {
        return !checkInternet() ? Observable.just(entity) : api.update(entity);
    }

    public Observable<List<T>> query(Specification specification) {

        Observable<List<T>> query = db.query(specification);
        Observable<List<T>> apiResult = api.query(null);

        return !checkInternet() ? query :
                Observable.concat(query, apiResult).first(new ArrayList<>()).toObservable();

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
