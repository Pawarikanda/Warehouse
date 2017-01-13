package modules;

import be.objectify.deadbolt.java.AbstractDeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import be.objectify.deadbolt.java.ExecutionContextProvider;
import be.objectify.deadbolt.java.models.Subject;
import models.User;
import play.api.libs.ws.WSClient;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

import static java.util.concurrent.CompletableFuture.completedFuture;

/**
 * Created by exalt on 1/12/17.
 */
public class MyDeadboltHandler extends AbstractDeadboltHandler {
    WSClient ws;

    @Inject
    public MyDeadboltHandler(ExecutionContextProvider ecProvider, WSClient ws) {
        super(ecProvider);
        this.ws = ws;
    }
    @Override
    public CompletionStage<Optional<Result>> beforeAuthCheck(Http.Context context){
        return completedFuture(Optional.empty());
    }

    @Override
    public CompletionStage<Optional<? extends Subject>> getSubject(Http.Context context){
        return CompletableFuture.supplyAsync(() -> Optional.ofNullable(
                User.findByUserName(context.session().get("username"))), (Executor) executionContextProvider.get());
    }

    @Override
    public CompletionStage<Result> onAuthFailure(Http.Context context, Optional<String> content){
        return completedFuture(forbidden("Auth denied"));
    }

    @Override
    public CompletionStage<Optional<DynamicResourceHandler>> getDynamicResourceHandler(Http.Context context){
        return CompletableFuture.completedFuture(Optional.of(new MyDynamicResourceHandler()));
    }
}

