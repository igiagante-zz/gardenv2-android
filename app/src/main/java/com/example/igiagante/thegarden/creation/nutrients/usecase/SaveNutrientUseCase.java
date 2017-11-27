package com.example.igiagante.thegarden.creation.nutrients.usecase;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.managers.NutrientRepositoryManager;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

/**
 * @author Ignacio Giagante, on 12/7/16.
 */
public class SaveNutrientUseCase extends UseCase<Nutrient, Nutrient> {

    private final NutrientRepositoryManager nutrientRepositoryManager;

    @Inject
    public SaveNutrientUseCase(@NonNull NutrientRepositoryManager nutrientRepositoryManager,
                               ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.nutrientRepositoryManager = nutrientRepositoryManager;
    }

    @Override
    protected io.reactivex.Observable<Nutrient> buildUseCaseObservable(Nutrient nutrient) {
        return nutrientRepositoryManager.save(nutrient);
    }
}
