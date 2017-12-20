package com.example.igiagante.thegarden.core.repository.restAPI.repositories;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.RealmRepository;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class BaseRestApiRepository<Entity> implements Repository<Entity> {

    private static final String TAG = BaseRestApiRepository.class.getSimpleName();

    protected Context mContext;

    public BaseRestApiRepository(){}

    /**
     * Go to the api and then continue with the DB
     *Ø
     * @param apiResult  after call api
     * @param repository DB
     * @param update     indicate if the transaction is about an `updating`
     */
    @SuppressWarnings("unchecked")
    protected Observable<Entity> execute(Observable<Entity> apiResult, Class repository, boolean update) {

        RealmRepository dataBase = null;

        try {
            Constructor<?> cons = repository.getConstructor(Context.class);
            dataBase = (RealmRepository) cons.newInstance(mContext);
        } catch (NoSuchMethodException | InvocationTargetException
                | IllegalAccessException | InstantiationException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        final RealmRepository db = dataBase;

        return apiResult.flatMap(element -> db.save((RealmRepository.Identifiable) element, update));
    }

    /**
     * Add images to the request body which is a {@link okhttp3.MultipartBody.Builder}
     *
     * @param builder MultipartBody.Builder
     * @param files   files to be added in builder
     * @return builder
     */
    protected MultipartBody.Builder addImagesToRequestBody(MultipartBody.Builder builder, ArrayList<File> files) {

        for (int i = 0, size = files.size(); i < size; i++) {
            String mediaType = "image/" + getMediaType(files.get(i));
            RequestBody image = RequestBody.create(MediaType.parse(mediaType), files.get(i));
            builder.addFormDataPart(files.get(i).getName(), files.get(i).getName(), image);
        }

        return builder;
    }

    /**
     * Get media type from file
     *
     * @param file File to be processed
     * @return type
     */
    private String getMediaType(File file) {

        String type = null;
        String[] chain = null;

        if (file != null) {
            chain = file.getAbsolutePath().split("\\.");
        }

        if (chain != null && chain.length > 0) {
            type = chain[1];
        }
        return type;
    }

    @Override
    public Observable<Entity> getById(String id) {
        return null;
    }

    @Override
    public Observable<Entity> getByName(String name) {
        return null;
    }

    @Override
    public Observable<Entity> save(Entity item, boolean update) {
        return null;
    }

    @Override
    public Observable<List<Entity>> add(Iterable<Entity> items) {
        return null;
    }

    @Override
    public Observable<Boolean> remove(String id) {
        return null;
    }

    @Override
    public void removeAll() {

    }

    @Override
    public Observable<List<Entity>> getAll() {
        return null;
    }

    @Override
    public Observable<List<Entity>> query(Specification specification) {
        return null;
    }
}
