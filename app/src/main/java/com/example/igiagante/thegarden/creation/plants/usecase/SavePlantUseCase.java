package com.example.igiagante.thegarden.creation.plants.usecase;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.managers.PlantRepositoryManager;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 8/6/16.
 */
public class SavePlantUseCase extends UseCase<Plant, Plant> {

    /**
     * Repository Manager which delegates the actions to the correct repository
     */
    private final PlantRepositoryManager plantRepositoryManager;

    @Inject
    public SavePlantUseCase(@NonNull PlantRepositoryManager plantRepositoryManager,
                            ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.plantRepositoryManager = plantRepositoryManager;
    }

    @Override
    protected Observable<Plant> buildUseCaseObservable(Plant plant) {
        return plantRepositoryManager.save(plant);
    }
}
