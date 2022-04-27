package ru.avtomaton.treecomments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.avtomaton.treecomments.entity.Role;
import ru.avtomaton.treecomments.entity.User;
import ru.avtomaton.treecomments.repository.UserRepository;

import java.util.Collections;
import java.util.List;

/**
 * @author Anton Akkuzin
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public User getUserByName(String name) {
        return userRepository.findByUsername(name);
    }

    public boolean isTrustedUser(User user) {
        for (Role role : user.getRoles()) {
            if (role.getName().equals("ROLE_MODERATOR") || role.getName().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role(1L, Role.RoleName.ROLE_MODERATOR.name())));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return true;
    }
}
