package com.example.igiagante.thegarden.core.repository.restAPI.repositories;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.network.ServiceFactory;
import com.example.igiagante.thegarden.core.repository.realm.PlantRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.PlantTable;
import com.example.igiagante.thegarden.core.repository.restAPI.services.PlantRestAPI;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

/**
 * @author Ignacio Giagante, on 19/4/16.
 */
public class RestApiPlantRepository extends BaseRestApiRepository<Plant> implements Repository<Plant> {

    private final PlantRestAPI api;
    private Context mContext;

    @Inject
    public RestApiPlantRepository(Context context, Session session) {
        super(context, session);
        this.mContext = context;
        api = ServiceFactory.createRetrofitService(PlantRestAPI.class, session.getToken());
    }

    @Override
    public Observable<Plant> getById(String id) {
        return api.getPlant(id);
    }

    @Override
    public Observable<Plant> getByName(String name) {
        return null;
    }

    @Override
    public Observable<Plant> add(@NonNull final Plant plant) {

        MultipartBody.Builder builder = getMultipartBodyForPostOrPut(plant);
        Observable<Plant> apiResult = api.createPlant(builder.build());

        // get Data From api
        List<Plant> listOne = new ArrayList<>();
        apiResult.subscribe(listOne::add);

        // persist the garden into database
        PlantRealmRepository dataBase = new PlantRealmRepository(mContext);
        Observable<Plant> dbResult = dataBase.save(listOne.get(0));

        List<Plant> list = new ArrayList<>();
        dbResult.subscribe(list::add);

        Observable<Plant> observable = Observable.just(list.get(0));

        return observable;
    }

    @Override
    public Observable<Integer> add(@NonNull final Iterable<Plant> plants) {

        int size = 0;
        for (Plant plant : plants) {
            add(plant);
        }

        if (plants instanceof Collection<?>) {
            size = ((Collection<?>) plants).size();
        }

        return Observable.just(size);
    }

    @Override
    public Observable<Plant> update(@NonNull final Plant plant) {

        MultipartBody.Builder builder = getMultipartBodyForPostOrPut(plant);
        Observable<Plant> apiResult = api.updatePlant(plant.getId(), builder.build());

        // get Data From api
        Plant plantFromApi = apiResult.subscribeOn(Schedulers.io()).blockingFirst();

        // update the plant into database
        PlantRealmRepository dataBase = new PlantRealmRepository(mContext);
        Observable<Plant> dbResult = dataBase.save(plantFromApi);

        return Observable.just(dbResult.blockingFirst());
    }

    @Override
    public Observable<Integer> remove(@NonNull String plantId) {
        return api.deletePlant(plantId)
                .map(response -> response.isSuccessful() ? 1 : -1);
    }

    @Override
    public void removeAll() {

    }

    @Override
    public Observable<List<Plant>> query(Specification specification) {
        return api.getPlants();
    }

    /**
     * Get {@link okhttp3.MultipartBody.Builder} for method POST or PUT
     *
     * @param plant Plant to be added in form
     * @return builder
     */
    private MultipartBody.Builder getMultipartBodyForPostOrPut(@NonNull final Plant plant) {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder = addPlantToRequestBody(builder, plant);

        ArrayList<File> files = new ArrayList<>();

        for (Image image : plant.getImages()) {
            File file = image.getFile();
            if (file != null) {
                files.add(file);
            }
        }

        return addImagesToRequestBody(builder, files);
    }

    /**
     * Add plant to the request body which is a {@link okhttp3.MultipartBody.Builder}
     *
     * @param builder MultipartBody.Builder
     * @param plant   Plant
     * @return builder
     */
    @NonNull
    private MultipartBody.Builder addPlantToRequestBody(@NonNull final MultipartBody.Builder builder, @NonNull final Plant plant) {

        if (plant.getResourcesIds() != null && !plant.getResourcesIds().isEmpty()) {
            String resourcesIds = new Gson().toJson(plant.getResourcesIds());
            builder.addFormDataPart(PlantTable.RESOURCES_IDS, resourcesIds);
        }

        if (plant.getFlavors() != null && !plant.getFlavors().isEmpty()) {
            String flavors = new Gson().toJson(plant.getFlavors());
            builder.addFormDataPart(PlantTable.FLAVORS, flavors);
        }

        if (plant.getAttributes() != null && !plant.getAttributes().isEmpty()) {
            String attributes = new Gson().toJson(plant.getAttributes());
            builder.addFormDataPart(PlantTable.ATTRIBUTES, attributes);
        }

        if (plant.getPlagues() != null && !plant.getPlagues().isEmpty()) {
            String plagues = new Gson().toJson(plant.getPlagues());
            builder.addFormDataPart(PlantTable.PLAGUES, plagues);
        }

        Log.i("GARDEN ID", "plant.getGardenId() -- " + plant.getGardenId());

        return builder.addFormDataPart(PlantTable.NAME, plant.getName())
                .addFormDataPart(PlantTable.SIZE, String.valueOf(plant.getSize()))
                .addFormDataPart(PlantTable.PH_SOIL, String.valueOf(plant.getPhSoil()))
                .addFormDataPart(PlantTable.EC_SOIL, String.valueOf(plant.getEcSoil()))
                .addFormDataPart(PlantTable.FLOWERING_TIME, String.valueOf(plant.getFloweringTime()))
                .addFormDataPart(PlantTable.GENOTYPE, String.valueOf(plant.getGenotype()))
                .addFormDataPart(PlantTable.HARVEST, String.valueOf(plant.getHarvest()))
                .addFormDataPart(PlantTable.DESCRIPTION, String.valueOf(plant.getDescription()))
                .addFormDataPart(PlantTable.GARDEN_ID, plant.getGardenId());
    }
}
