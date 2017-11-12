package com.example.igiagante.thegarden.creation.plants.usecase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.igiagante.thegarden.core.domain.entity.Plague;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.managers.PlagueRepositoryManager;
import com.example.igiagante.thegarden.core.repository.realm.specification.plague.PlagueSpecification;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * @author Ignacio Giagante, on 6/4/16.
 */
public class GetPlaguesUseCase extends UseCase<Plague, Void> {

    /**
     * Repository Manager which delegates the actions to the correct repository
     */
    private final PlagueRepositoryManager plagueRepositoryManager;

    @Inject
    public GetPlaguesUseCase(@NonNull PlagueRepositoryManager plagueRepositoryManager, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.plagueRepositoryManager = plagueRepositoryManager;
    }

    @Override
    protected Observable buildUseCaseObservable(Void aVoid) {
        Log.i("Thread", "   GetPlaguesUseCase    " + Thread.currentThread().getName());
        return plagueRepositoryManager.query(new PlagueSpecification());
    }
}
