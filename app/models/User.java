package models;

import be.objectify.deadbolt.java.models.Permission;
import be.objectify.deadbolt.java.models.Role;
import be.objectify.deadbolt.java.models.Subject;
import com.avaje.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import play.data.validation.Constraints;

import java.util.List;

import static play.mvc.Results.ok;

/**
 * Created by exalt on 1/11/17.
 */
@Entity

public class User extends Model implements Subject{
@Id
@Column(unique = true)
    public Long id;


@Constraints.Required
    public String password;

public User(){}
public  User(String username,String password){
    this.username=username;
    this.password=password;
}


public static User authenticate(String username, String password){
    User roles=finder.where().eq("username",username).eq("password",password).findUnique();
    Global.checkPermissions(roles);


   return finder.where().eq("username",username).eq("password",password).findUnique();

}


public static Finder<Long,User>finder=new Finder<Long, User>(User.class);
    @Constraints.Required
    public String username;

    @ManyToMany
    public List<SecurityRole> roles;

    @ManyToMany
    public List<UserPermission> permissions;

    public static final Finder<Long, User> find = new Finder<>(Long.class,
            User.class);

    @Override
    public List<? extends Role> getRoles()
    {
        return roles;
    }

    @Override
    public List<? extends Permission> getPermissions()
    {
        return permissions;
    }

    @Override
    public String getIdentifier()
    {
        return username;
    }

    public static User findByUserName(String userName)
    {
        return find.where()
                .eq("username",
                        userName)
                .findUnique();
    }
}
