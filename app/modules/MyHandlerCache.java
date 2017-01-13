package modules;
import be.objectify.deadbolt.java.ConfigKeys;
import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.ExecutionContextProvider;
import be.objectify.deadbolt.java.cache.HandlerCache;
import play.api.libs.ws.WSClient;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;

/**
 * Created by exalt on 1/12/17.
 */
public class MyHandlerCache implements HandlerCache
{
    private final DeadboltHandler defaultHandler;
    private final Map<String, DeadboltHandler> handlers = new HashMap<>();

    @Inject
    public MyHandlerCache(final ExecutionContextProvider ecProvider, WSClient ws){
        defaultHandler = new MyDeadboltHandler(ecProvider, ws);
        handlers.put(ConfigKeys.DEFAULT_HANDLER_KEY, defaultHandler);
        handlers.put("altHandler", defaultHandler);

    }

    @Override
    public DeadboltHandler apply(final String key)
    {
        return handlers.get(key);
    }

    @Override
    public DeadboltHandler get()
    {
        return defaultHandler;

    }


}