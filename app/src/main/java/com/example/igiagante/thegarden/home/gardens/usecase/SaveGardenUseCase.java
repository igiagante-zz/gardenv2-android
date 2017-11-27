package com.example.igiagante.thegarden.home.gardens.usecase;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.managers.GardenRepositoryManager;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
public class SaveGardenUseCase extends UseCase<Garden, Garden> {

    /**
     * Repository Manager which delegates the actions to the correct repository
     */
    private final GardenRepositoryManager gardenRepositoryManager;

    @Inject
    public SaveGardenUseCase(GardenRepositoryManager gardenRepositoryManager,
                             ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.gardenRepositoryManager = gardenRepositoryManager;
    }

    @Override
    protected Observable<Garden> buildUseCaseObservable(Garden garden) {
        return gardenRepositoryManager.save(garden);
    }
}
