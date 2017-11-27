package com.example.igiagante.thegarden.creation.plants.usecase;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.managers.AttributeRepositoryManager;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * @author Ignacio Giagante, on 3/6/16.
 */
@PerActivity
public class GetAttributesUseCase extends UseCase<Attribute, Void> {

    /**e
     * Repository Manager which delegates the actions to the correct repository
     */
    private final AttributeRepositoryManager attributeRepositoryManager;

    @Inject
    public GetAttributesUseCase(@NonNull AttributeRepositoryManager attributeRepositoryManager, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.attributeRepositoryManager = attributeRepositoryManager;
    }

    @Override
    protected Observable buildUseCaseObservable(Void aVoid) {
        return attributeRepositoryManager.getAll();
    }
}
