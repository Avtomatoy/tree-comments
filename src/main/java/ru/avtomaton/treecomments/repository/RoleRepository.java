package ru.avtomaton.treecomments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.avtomaton.treecomments.entity.Role;

/**
 * @author Anton Akkuzin
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}
