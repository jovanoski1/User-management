package rs.raf.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rs.raf.demo.model.Role;
import rs.raf.demo.model.User;
import rs.raf.demo.requests.UpdateUserRequest;
import rs.raf.demo.services.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new Role("can_create_users"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(userService.create(user)); // this.userService.create(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new Role("can_read_users"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(userService.getAll());
    }

    @PutMapping("/update")
    public ResponseEntity<User> update(@Valid @RequestBody UpdateUserRequest user) {
        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new Role("can_update_users"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(userService.update(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new Role("can_delete_users"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public User me() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.userService.findByEmail(email);
    }
}
