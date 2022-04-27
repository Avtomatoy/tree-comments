package ru.avtomaton.treecomments.mock;

import org.springframework.stereotype.Service;
import ru.avtomaton.treecomments.entity.Role;
import ru.avtomaton.treecomments.entity.User;
import ru.avtomaton.treecomments.repository.RoleRepository;
import ru.avtomaton.treecomments.service.UserService;

import java.util.Set;

/**
 * @author Anton Akkuzin
 */
@Service
public class MockService {
    private final UserService userService;
    private final RoleRepository roleRepository;

    public MockService(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;

        createAdmin();
    }

    private Set<Role> createRoles() {
        Role role1 = new Role();
        role1.setName(Role.RoleName.ROLE_MODERATOR.name());
        Set<Role> roles = Set.of(role1);
        roleRepository.saveAll(roles);

        return roles;
    }

    private void createAdmin() {
        User admin = new User();
        admin.setUsername("Admin");
        admin.setPassword("775577");
        admin.setRoles(createRoles());
        userService.saveUser(admin);
    }
}
