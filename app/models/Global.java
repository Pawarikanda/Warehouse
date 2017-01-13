package models;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import play.Application;
import play.GlobalSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Global extends GlobalSettings
{
    public static Model.Finder<Long,User> finder=new Model.Finder<>(User.class);

    @Override
    public void onStart(Application application)
    {
        if (SecurityRole.find.findRowCount() == 0)
        {
            for (String name : Arrays.asList("foo", "bar", "hurdy", "gurdy"))
            {
                SecurityRole role = new SecurityRole();
                role.name = name;
                role.save();
            }
        }

        if (UserPermission.find.findRowCount() == 0)
        {
            UserPermission permission = new UserPermission();
            permission.value = "edit";
            permission.save();
        }
        
        if (User.find.findRowCount() == 0)
        {
            User user = new User();


            user.username = "exalt";
            user.password="exalt";
            user.roles = new ArrayList<>();
            user.roles.add(SecurityRole.findByName("foo"));
            user.roles.add(SecurityRole.findByName("bar"));
            user.permissions = new ArrayList<>();
            user.permissions.add(UserPermission.findByValue("edit"));

            user.save();
            Ebean.saveManyToManyAssociations(user,"roles");
            Ebean.saveManyToManyAssociations(user,"permissions");
        }
    }

    public static boolean checkPermissions(User user){
        boolean check=false;
        List<UserPermission> AllowedPermissions=user.permissions;
        for (UserPermission perm:AllowedPermissions) {
            if (perm.toString()=="printers.edit") check=true;
            else continue;
        }
        return check;

    }

    public static boolean checkName(User user){
        User user1=finder.where().eq("username",user.username).eq("password",user.password).findUnique();
        String name=user1.username.toString();
        String pass=user1.password.toString();
        if((name.equals("exalt")&& (pass.equals("exalt"))))
            return true;
        else return false;
    }

}
