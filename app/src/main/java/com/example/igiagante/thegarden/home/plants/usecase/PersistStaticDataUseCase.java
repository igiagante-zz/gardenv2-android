package com.example.igiagante.thegarden.home.plants.usecase;

import android.content.Context;
import android.util.Log;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.domain.entity.Plague;
import com.example.igiagante.thegarden.core.domain.entity.SensorTemp;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.managers.AttributeRepositoryManager;
import com.example.igiagante.thegarden.core.repository.managers.PlagueRepositoryManager;
import com.example.igiagante.thegarden.core.repository.managers.SensorTempRepositoryManager;
import com.example.igiagante.thegarden.core.repository.realm.specification.SensorTempSpecification;
import com.example.igiagante.thegarden.core.repository.realm.specification.attribute.AttributeSpecification;
import com.example.igiagante.thegarden.core.repository.realm.specification.plague.PlagueSpecification;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;


/**
 * @author Ignacio Giagante, on 15/6/16.
 *         <p>
 *         This usecase is used to retrieve data that almost never change. Attributes, Flavors and Plagues
 */
@Singleton
public class PersistStaticDataUseCase extends UseCase<String, Void> {

    private static final String TAG = PersistStaticDataUseCase.class.getSimpleName();

    /**
     * Repository Managers
     */
    private final AttributeRepositoryManager attributeRepositoryManager;
    private final PlagueRepositoryManager plagueRepositoryManager;
    private final SensorTempRepositoryManager sensorTempRepositoryManager;


    private Context context;

    @Inject
    public PersistStaticDataUseCase(Context context, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.context = context;

        this.attributeRepositoryManager = new AttributeRepositoryManager(context);
        this.plagueRepositoryManager = new PlagueRepositoryManager(context);
        this.sensorTempRepositoryManager = new SensorTempRepositoryManager(context);
    }

    @Override
    protected Observable<String> buildUseCaseObservable(Void aVoid) {

        // ask if attributes are already persisted
        AttributeSpecification attributeSpecification = new AttributeSpecification();
        Observable<List<Attribute>> attributes = attributeRepositoryManager.query(attributeSpecification);

        attributes.doOnNext(list -> Log.i(TAG, "  ROWS " + list.size() +
                " attributes were inserted into Realm DB"));

        // ask if plagues are already persisted
        PlagueSpecification plagueSpecification = new PlagueSpecification();
        Observable<List<Plague>> plagues = plagueRepositoryManager.query(plagueSpecification);

        plagues.doOnNext(list -> Log.i(TAG, "  ROWS " + list.size() +
                " plagues were inserted into Realm DB"));

        // check temp and humidity data
        SensorTempSpecification sensorTempSpecification = new SensorTempSpecification();
        Observable<List<SensorTemp>> sensorTemps = sensorTempRepositoryManager.query(sensorTempSpecification);

        sensorTemps.doOnNext(list -> Log.i(TAG, "  ROWS " + list.size() +
                " plagues were inserted into Realm DB"));

        return Observable.just("OK");
    }
}
