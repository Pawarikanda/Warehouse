package controllers;
import be.objectify.deadbolt.java.actions.SubjectPresent;
import play.mvc.Http;
import play.mvc.Security;


/**
 * Created by exalt on 1/11/17.
 */

@SubjectPresent
public class SecuredController extends Security.Authenticator {

    @Override
    @SubjectPresent
    public String getUsername(Http.Context ctx){
        return ctx.session().get("username");

    }


}
