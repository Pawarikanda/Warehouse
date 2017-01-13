package controllers;
import akka.NotUsed;
import akka.actor.Status;
import akka.stream.OverflowStrategy;
import akka.stream.javadsl.Source;
import akka.util.ByteString;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;
import views.html.index;
import views.html.*;
import javax.inject.Inject;

import static play.data.Form.form;

/**
 * Created by exalt on 1/10/17.
 */
public class ApplicationController extends Controller {

public ApplicationController(){

}
    private static FormFactory formFactory;
@Inject
    public ApplicationController(FormFactory formFactory){
        this.formFactory=formFactory;
    }

    public Result liveUpdates() {
        // Prepare a chunked text stream
       Source<ByteString, ?> source = Source.<ByteString>actorRef(256, OverflowStrategy.dropNew())
                .mapMaterializedValue(sourceActor -> {
                    sourceActor.tell(ByteString.fromString("kiki"), null);
                    sourceActor.tell(ByteString.fromString("foo"), null);
                    sourceActor.tell(ByteString.fromString("bar"), null);
                    sourceActor.tell(new Status.Success(NotUsed.getInstance()), null);
                    return null;
                });
        // Serves this stream with 200 OK
        return ok().chunked(source);
    }

    public LegacyWebSocket<String> socket() {
        return WebSocket.whenReady((in, out) -> {
            // For each event received on the socket,
            in.onMessage(System.out::println);

            // When the socket is closed.
            in.onClose(() -> System.out.println("Disconnected"));

            // Send a single 'Hello!' message
            out.write("Hello!");
        });
    }

    public Result index() {
        //final Source source = Source.from(Arrays.asList("kiki", "foo", "bar"));
        //return ok().chunked(source.via(Comet.string("parent.cometMessage"))).as(Http.MimeTypes.HTML);
        return ok(index.render("live streaming"));
    }
public Result login(){
        return ok(login.render(form(Login.class)));
}

public Result authenticate(){
    Form<Login> loginForm=form(Login.class).bindFromRequest();
    String username=loginForm.get().username;
    String password=loginForm.get().password;

    if (User.authenticate(username,password)==null){
        return forbidden("Invalid password");
    }
    session().clear();
    session("username",username);
    return redirect(routes.ProductsController.index());
}

public Result logout(){
    session().clear();
    return redirect(routes.ApplicationController.login());
}
    public static class Login{
        public String username;
        public String  password;
    }
}
