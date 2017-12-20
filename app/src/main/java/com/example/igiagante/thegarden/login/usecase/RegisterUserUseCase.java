package com.example.igiagante.thegarden.login.usecase;

import android.content.Context;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.restAPI.authentication.RestAuthApi;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author Ignacio Giagante, on 2/8/16.
 */
public class RegisterUserUseCase extends UseCase<User, User> {

    private final RestAuthApi api;

    @Inject
    public RegisterUserUseCase(Context context, Session session,
                               ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.api = new RestAuthApi(context, session);
    }

    @Override
    protected Observable buildUseCaseObservable(User user) {
        return api.registerUser(user);
    }
}
