package com.example.igiagante.thegarden.home.plants.usecase;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.managers.PlantRepositoryManager;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 2/5/16.
 */
public class GetPlantsUseCase extends UseCase<Plant, Void> {

    /**
     * Repository Manager which delegates the actions to the correct repository
     */
    private final PlantRepositoryManager plantRepositoryManager;

    @Inject
    public GetPlantsUseCase(@NonNull PlantRepositoryManager plantRepositoryManager, @NonNull ThreadExecutor threadExecutor, @NonNull PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.plantRepositoryManager = plantRepositoryManager;
    }

    @Override
    protected Observable buildUseCaseObservable(Void aVoid) {
        return plantRepositoryManager.getAll();
    }
}
