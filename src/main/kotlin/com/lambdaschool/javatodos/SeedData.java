package com.lambdaschool.javatodos;

import com.lambdaschool.javatodos.model.Role;
import com.lambdaschool.javatodos.model.Todo;
import com.lambdaschool.javatodos.model.User;
import com.lambdaschool.javatodos.model.UserRoles;
import com.lambdaschool.javatodos.repository.RoleRepository;
import com.lambdaschool.javatodos.repository.TodoRepository;
import com.lambdaschool.javatodos.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{
    RoleRepository rolerepos;
    UserRepository userrepos;
    TodoRepository todorepos;

    public SeedData(RoleRepository rolerepos, UserRepository userrepos, TodoRepository todorepos)
    {
        this.rolerepos = rolerepos;
        this.userrepos = userrepos;
        this.todorepos = todorepos;
    }

    @Override
    public void run(String[] args) throws Exception
    {
        Role r1 = new Role("admin");
        Role r2 = new Role("user");

        ArrayList<UserRoles> admins = new ArrayList<>();
        admins.add(new UserRoles(new User(), r1));
        admins.add(new UserRoles(new User(), r2));

        ArrayList<UserRoles> users = new ArrayList<>();
        //users.add(new UserRoles(new User(), r2));

        rolerepos.save(r1);
        rolerepos.save(r2);

        User u1 = new User("admin", "1234", admins);
        User u2 = new User("tree", "2345", users);
        User u3 = new User("ant", "3456", users);
        User u4 = new User("table", "4567", users);

        u1.getTodos().add(new Todo("Bake a potato", "2019-06-14 10:30:55", u1));
        u1.getTodos().add(new Todo("Concoct a stew", "2019-06-14 11:30:55", u1));

        u2.getTodos().add(new Todo("Take a hike", "2019-05-19 16:45:22", u2));

        u4.getTodos().add(new Todo("Finish java-orders-swagger", "2019-01-13 04:04:04", u4));
        u4.getTodos().add(new Todo("Feed the turtles", "2019-03-01 04:04:04", u4));
        u4.getTodos().add(new Todo("Complete the sprint challenge", "2019-02-22 04:04:04", u4));

        userrepos.save(u1);
        userrepos.save(u2);
        userrepos.save(u3);
        userrepos.save(u4);
    }
}
