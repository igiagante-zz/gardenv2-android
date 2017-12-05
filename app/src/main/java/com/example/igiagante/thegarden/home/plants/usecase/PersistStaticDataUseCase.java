package com.example.igiagante.thegarden.home.plants.usecase;

import android.content.Context;
import android.util.Log;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.domain.entity.Plague;
import com.example.igiagante.thegarden.core.domain.entity.SensorTemp;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.managers.AttributeRepositoryManager;
import com.example.igiagante.thegarden.core.repository.managers.FlavorRepositoryManager;
import com.example.igiagante.thegarden.core.repository.managers.PlagueRepositoryManager;
import com.example.igiagante.thegarden.core.repository.managers.SensorTempRepositoryManager;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.functions.Function4;


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
    private final FlavorRepositoryManager flavorRepositoryManager;

    @Inject
    public PersistStaticDataUseCase(Context context, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);

        this.attributeRepositoryManager = new AttributeRepositoryManager(context);
        this.plagueRepositoryManager = new PlagueRepositoryManager(context);
        this.sensorTempRepositoryManager = new SensorTempRepositoryManager(context);
        this.flavorRepositoryManager = new FlavorRepositoryManager(context);
    }

    @Override
    protected Observable<String> buildUseCaseObservable(Void aVoid) {

        // ask if attributes are already persisted
        Observable<List<Attribute>> attributes = attributeRepositoryManager.getAll();

        // ask if flavors are already persisted
        Observable<List<Flavor>> flavors = flavorRepositoryManager.getAll();

        // ask if plagues are already persisted
        Observable<List<Plague>> plagues = plagueRepositoryManager.getAll();

        // check temp and humidity data
        Observable<List<SensorTemp>> sensorTemps = sensorTempRepositoryManager.getAll();

        return Observable.zip(attributes, flavors, plagues, sensorTemps,
                new Function4<List<Attribute>, List<Flavor>, List<Plague>, List<SensorTemp>, Integer>() {
            @Override
            public Integer apply(List<Attribute> t1, List<Flavor> t2,
                                 List<Plague> t3, List<SensorTemp> t4) throws Exception {

                Log.d(TAG, "  ROWS " + t1.size() + " attributes were inserted into Realm DB");
                Log.d(TAG, "  ROWS " + t2.size() + " flavors were inserted into Realm DB");
                Log.d(TAG, "  ROWS " + t3.size() + " plagues were inserted into Realm DB");
                Log.d(TAG, "  ROWS " + t4.size() + " sensorTemps were inserted into Realm DB");

                return t1.size() + t2.size() + t3.size();
            }
        }).map(rows -> "The DB was updated successfully");
    }
}
