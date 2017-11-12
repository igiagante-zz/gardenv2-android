package com.example.igiagante.thegarden.home.plants.deletePlant;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.managers.PlantRepositoryManager;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 13/6/16.
 */
@PerActivity
public class DeletePlantDataUseCase extends UseCase<Plant, String> {

    /**
     * Repository Manager which delegates the actions to the correct repository
     */
    private final PlantRepositoryManager plantRepositoryManager;

    @Inject
    public DeletePlantDataUseCase(@NonNull PlantRepositoryManager plantRepositoryManager,
                                  ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.plantRepositoryManager = plantRepositoryManager;
    }

    @Override
    protected Observable buildUseCaseObservable(String plantId) {
        return plantRepositoryManager.delete(plantId);
    }
}
