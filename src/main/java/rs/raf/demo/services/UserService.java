package rs.raf.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import rs.raf.demo.model.Role;
import rs.raf.demo.model.User;
import rs.raf.demo.repositories.UserRepository;
import rs.raf.demo.requests.UpdateUserRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;
    private TaskScheduler taskScheduler;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, TaskScheduler taskScheduler) {
        this.passwordEncoder = passwordEncoder;

        this.userRepository = userRepository;
        this.taskScheduler = taskScheduler;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User myUser = this.findByEmail(email);
        if(myUser == null) {
            throw new UsernameNotFoundException("Email "+email+" not found");
        }

        Collection<Role> authorities = new ArrayList<>();
        if (myUser.getAuthorities() != null){
            for(String s : myUser.getAuthorities().split(",")) {
                authorities.add(new Role(s));
            }
        }

        return new org.springframework.security.core.userdetails.User(myUser.getEmail(), myUser.getPassword(),
                authorities);
    }

    public User create(User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    public List<User> getAll() {
        return this.userRepository.findAll();
    }

    public User findByEmail(String email) {
        return this.userRepository.findUserByEmail(email);
    }

    public User update(UpdateUserRequest user) {
        User userToUpdate = this.userRepository.findUserByEmail(user.getEmail());
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setAuthorities(user.getAuthorities());
        return this.userRepository.save(userToUpdate);
    }
}
