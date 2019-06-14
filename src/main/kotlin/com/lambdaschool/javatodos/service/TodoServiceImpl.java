package com.lambdaschool.javatodos.service;

import com.lambdaschool.javatodos.model.Todo;
import com.lambdaschool.javatodos.model.User;
import com.lambdaschool.javatodos.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "todoService")
public class TodoServiceImpl implements TodoService
{
    @Autowired
    private TodoRepository todorepos;

    @Override
    public List<Todo> findAll()
    {
        List<Todo> list = new ArrayList<>();
        todorepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Todo findTodoById(long id)
    {
        return todorepos.findById(id).orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));
    }

    @Override
    public void delete(long id)
    {
        if (todorepos.findById(id).isPresent())
        {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (todorepos.findById(id).get().getUser().getUsername().equalsIgnoreCase(authentication.getName()))
            {
                todorepos.deleteById(id);
            } else
            {
                throw new EntityNotFoundException(Long.toString(id) + " " + authentication.getName());
            }
        } else
        {
            throw new EntityNotFoundException(Long.toString(id));
        }
    }

    @Transactional
    @Override
    public Todo save(Todo todo)
    {
        return todorepos.save(todo);
    }

    @Override
    public List<Todo> findByUserName(String username)
    {
        List<Todo> list = new ArrayList<>();
        todorepos.findAll().iterator().forEachRemaining(list::add);

        list.removeIf(t -> !t.getUser().getUsername().equalsIgnoreCase(username));
        return list;
    }


    @Transactional
    @Override
    public Todo update(Todo receivedTodo, long todoid)
    {
        Todo existingTodo = todorepos.findById(todoid).orElseThrow(() -> new EntityNotFoundException("Todo " + todoid + " was not found!"));

        if (receivedTodo.getDescription() != null)
        {
            existingTodo.setDescription(receivedTodo.getDescription());
        }

        if (receivedTodo.getDatestarted() != null)
        {
            existingTodo.setDatestarted(receivedTodo.getDatestarted());
        }

        if (receivedTodo.isCompleted())
        {
            existingTodo.setCompleted(receivedTodo.isCompleted());
        }

        if (receivedTodo.getUser() != null)
        {
            User userToUpdate = receivedTodo.getUser();

            if (userToUpdate.getUsername() != null)
            {
                existingTodo.getUser().setUsername(userToUpdate.getUsername());
            }

            if (userToUpdate.getPassword() != null)
            {
                existingTodo.getUser().setPasswordNoEncrypt(userToUpdate.getPassword());
            }

            if (userToUpdate.getUserRoles().size() > 0)
            {
                existingTodo.getUser().setUserRoles(userToUpdate.getUserRoles());
            }
        }

        return todorepos.save(existingTodo);
    }
}
