package com.example.igiagante.thegarden.creation.plants.usecase;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.managers.PlagueRepositoryManager;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 6/4/16.
 */
public class GetPlaguesUseCase extends UseCase<Void> {

    /**
     * Repository Manager which delegates the actions to the correct repository
     */
    private final PlagueRepositoryManager plagueRepositoryManager;

    @Inject
    public GetPlaguesUseCase(@NonNull PlagueRepositoryManager plagueRepositoryManager, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.plagueRepositoryManager = plagueRepositoryManager;
        // set repositories order
        this.plagueRepositoryManager.setRepositoriesOrder(getRepositoryOrder());
    }

    @Override
    protected Observable buildUseCaseObservable(Void aVoid) {
        return plagueRepositoryManager.query(new Specification() {});
    }

    @Override
    protected void setRepositoryOrder() {
        repositoryOrder.add(LOCAL_REPOSITORY, REMOTE_REPOSITORY);
    }
}
