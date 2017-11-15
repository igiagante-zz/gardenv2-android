package com.example.igiagante.thegarden.core.repository.realm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.MapperTest;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.ImageRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlantRealm;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * @author Ignacio Giagante, on 5/5/16.
 */
public class PlantRealmRepository extends RealmRepository<Plant, PlantRealm> {


    @Override
    Mapper<Plant, PlantRealm> initModelToRealmMapper(Realm realm) {
        return null;
    }

    @Override
    MapperTest<PlantRealm, Plant> initRealmToModelMapper(Context context) {
        return null;
    }

    public PlantRealmRepository(@NonNull Context context) {

       super(context);
    }

    @Override
    void removeAll() {

    }

    /*

    @Override
    public Observable<Plant> getById(@NonNull String id) {
        return query(new PlantByIdSpecification(id)).flatMap(Observable::fromIterable);
    }

    public Observable<Plant> getByName(@NonNull String name) {
        return query(new PlantByNameSpecification(name)).flatMap(Observable::fromIterable);
    }

    @Override
    public Observable<Plant> add(@NonNull final Plant plant) {

        realm = Realm.getInstance(realmConfiguration);
        realm.executeTransaction(realmParam ->
                realmParam.copyToRealmOrUpdate(toPlantRealm.map(plant)));

        realm.close();

        return Observable.just(plant);
    }

    @Override
    public Observable<Integer> add(@NonNull final Iterable<Plant> plants) {

        int size = 0;
        realm = Realm.getInstance(realmConfiguration);

        realm.executeTransaction(realmParam -> {
            for (Plant plant : plants) {
                realmParam.copyToRealmOrUpdate(toPlantRealm.map(plant));
            }
        });

        realm.close();

        if (plants instanceof Collection<?>) {
            size = ((Collection<?>) plants).size();
        }

        return Observable.just(size);
    }

    @Override
    public Observable<Plant> update(@NonNull final Plant plant) {

        realm = Realm.getInstance(realmConfiguration);

        PlantRealm plantRealm = realm.where(PlantRealm.class).equalTo(PlantTable.ID, plant.getId()).findFirst();

        realm.executeTransaction(realmParam -> {
            toPlantRealm.copy(plant, plantRealm);
        });

        realm.close();

        // delete images
        List<ImageRealm> imagesToBeDeleted = getImagesToBeDeleted(plant.getResourcesIds());
        if (!imagesToBeDeleted.isEmpty()) {
            for (ImageRealm imageRealm : imagesToBeDeleted) {
                realm.executeTransaction(realmParam -> imageRealm.deleteFromRealm());
                realm.close();
            }
        }

        return Observable.just(plant);
    } */


    /**
     * Filter those images which should be deleted from Realm
     *
     * @param resourcesIds list of resources ids images
     * @return List of ImageRealm Objects
     */
    /*
    private List<ImageRealm> getImagesToBeDeleted(List<String> resourcesIds) {

        RealmResults<ImageRealm> all = realm.where(ImageRealm.class).findAll();

        // Filter resources ids
        ArrayList<String> resourcesIdsTemp = new ArrayList<>();

        for (ImageRealm imageRealm : all) {
            resourcesIdsTemp.add(imageRealm.getId());
        }

        if (resourcesIds != null && !resourcesIds.isEmpty()) {
            resourcesIdsTemp.removeAll(resourcesIds);
        }

        // Create array with Images Realm which should be deleted
        List<ImageRealm> imagesToBeDeleted = new ArrayList<>();

        for (ImageRealm imageRealm : all) {
            if (resourcesIdsTemp.contains(imageRealm.getId())) {
                imagesToBeDeleted.add(imageRealm);
            }
        }

        return imagesToBeDeleted;
    } */

    /*

    @Override
    public Observable<Integer> remove(@NonNull String plantId) {

        realm = Realm.getInstance(realmConfiguration);

        PlantRealm plantRealm = realm.where(PlantRealm.class).equalTo(PlantTable.ID, plantId).findFirst();
        if (plantRealm != null) {
            realm.executeTransaction(realmParam -> plantRealm.deleteFromRealm());
        }

        realm.close();

        // if plantRealm.isValid() is false, it is because the realm object was deleted
        return Observable.just((plantRealm != null && plantRealm.isValid()) ? -1 : 1);
    }

    @Override
    public void removeAll() {
        realm = Realm.getInstance(realmConfiguration);

        realm.executeTransaction(realmParam -> {
            RealmResults<PlantRealm> result = realm.where(PlantRealm.class).findAll();
            result.deleteAllFromRealm();
        });
        realm.close();
    }

    @Override
    public Observable<List<Plant>> query(@NonNull final Specification specification) {

        final RealmSpecification realmSpecification = (RealmSpecification) specification;

        final Realm realm = Realm.getInstance(realmConfiguration);
        final Flowable<RealmResults<PlantRealm>> realmResults = realmSpecification.toFlowable(realm);

        // convert Flowable<RealmResults<PlantRealm>> into Observable<List<Plant>>
        return realmResults
                .filter(plants -> plants.isLoaded())
                .switchMap(plants ->
                        Flowable.fromIterable(plants)
                                .map(plantRealm -> toPlant.map(plantRealm))
                )
                .toList()
                .toObservable();
    } */
}
