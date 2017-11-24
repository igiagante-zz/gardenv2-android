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
import io.reactivex.functions.Function3;


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

    @Inject
    public PersistStaticDataUseCase(Context context, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);

        this.attributeRepositoryManager = new AttributeRepositoryManager(context);
        this.plagueRepositoryManager = new PlagueRepositoryManager(context);
        this.sensorTempRepositoryManager = new SensorTempRepositoryManager(context);
    }

    @Override
    protected Observable<String> buildUseCaseObservable(Void aVoid) {

        // ask if attributes are already persisted
        AttributeSpecification attributeSpecification = new AttributeSpecification();
        Observable<List<Attribute>> attributes = attributeRepositoryManager.query(attributeSpecification);

        PlagueSpecification plagueSpecification = new PlagueSpecification();
        Observable<List<Plague>> plagues = plagueRepositoryManager.query(plagueSpecification);

        // check temp and humidity data
        SensorTempSpecification sensorTempSpecification = new SensorTempSpecification();
        Observable<List<SensorTemp>> sensorTemps = sensorTempRepositoryManager.query(sensorTempSpecification);

        return Observable.zip(attributes.count().toObservable(), plagues.count().toObservable(),
                sensorTemps.count().toObservable(),
                new Function3<Long, Long, Long, Long>() {
            @Override
            public Long apply(Long t1, Long t2, Long t3) throws Exception {

                Log.d(TAG, "  ROWS " + t1 + " attributes were inserted into Realm DB");
                Log.d(TAG, "  ROWS " + t2 + " plagues were inserted into Realm DB");
                Log.d(TAG, "  ROWS " + t3 + " sensorTemps were inserted into Realm DB");

                return t1 + t2 + t3;
            }
        }).map(rows -> "ok: " + rows);
    }
}
