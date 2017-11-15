package com.example.igiagante.thegarden.login.usecase;

import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.managers.UserRepositoryManager;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 10/8/16.
 */
public class ExistsUserUseCase extends UseCase<User, String> {

    /**
     * Repository Manager which delegates the actions to the correct repository
     */
    private final UserRepositoryManager userRepositoryManager;

    @Inject
    public ExistsUserUseCase(UserRepositoryManager userRepositoryManager, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepositoryManager = userRepositoryManager;
    }

    @Override
    protected Observable buildUseCaseObservable(String userId) {
        return this.userRepositoryManager.checkIfUserExistsInDataBase(userId);
    }
}
