package modules;

import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import models.Global;
import models.User;
import play.mvc.Http;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by exalt on 1/12/17.
 */
public class MyDynamicResourceHandler implements DynamicResourceHandler
{
    private static final Map<String, Optional<DynamicResourceHandler>> handlers = new HashMap<>();
        @Override
        public CompletionStage<Boolean> isAllowed(String name, Optional<String> meta, DeadboltHandler deadboltHandler, Http.Context context)
        {
            return checkPermission(name,meta, deadboltHandler, context);
        }

        @Override
        public CompletionStage<Boolean> checkPermission(String value, Optional<String> meta, DeadboltHandler deadboltHandler, Http.Context context)
        {
            return CompletableFuture.completedFuture(Global.checkName(User.findByUserName(context.session().get("username"))));
        }
}
