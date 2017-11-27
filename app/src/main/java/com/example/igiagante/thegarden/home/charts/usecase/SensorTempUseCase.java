package com.example.igiagante.thegarden.home.charts.usecase;

import com.example.igiagante.thegarden.core.domain.entity.SensorTemp;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.managers.SensorTempRepositoryManager;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 19/8/16.
 */
public class SensorTempUseCase extends UseCase<List<SensorTemp>, Void> {

    private final SensorTempRepositoryManager sensorTempRepositoryManager;

    @Inject
    public SensorTempUseCase(SensorTempRepositoryManager sensorTempRepositoryManager,
                             ThreadExecutor threadExecutor,
                             PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.sensorTempRepositoryManager = sensorTempRepositoryManager;
    }

    @Override
    protected Observable<List<SensorTemp>> buildUseCaseObservable(Void aVoid) {
        return sensorTempRepositoryManager.getAll();
    }
}
