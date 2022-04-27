package ru.avtomaton.treecomments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.avtomaton.treecomments.entity.User;

/**
 * @author Anton Akkuzin
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
