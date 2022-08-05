package com.example.springrest.controller;

import com.example.springrest.dao.UserPojoDao;
import com.example.springrest.exceptions.UserNotFoundException;
import com.example.springrest.model.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

    private final UserPojoDao userPojoDao;

    @Autowired
    public UserController(UserPojoDao userPojoDao) {
        this.userPojoDao = userPojoDao;
    }

    @GetMapping(path = "/users")
    public List<UserPojo> retrieveAllUsers() {
        return userPojoDao.findAll();
    }

    @PostMapping(path = "/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserPojo userPojo) {

        UserPojo savedUser = userPojoDao.save(userPojo);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/users/{id}")
    public EntityModel<UserPojo> retrieveUser(@PathVariable int id) {
        UserPojo userPojo = userPojoDao.findOne(id);
        if(userPojo == null) {
            throw new UserNotFoundException("id-" + id);
        }

        // HATEOAS
        Link link = linkTo(methodOn(this.getClass()).retrieveAllUsers()).withRel("all-users");
        EntityModel<UserPojo> entityUser = EntityModel.of(userPojo, link);

        return entityUser;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        UserPojo userPojo = userPojoDao.deleteById(id);

        if(userPojo == null) {
            throw new UserNotFoundException("id-" + id);
        }
    }

}
