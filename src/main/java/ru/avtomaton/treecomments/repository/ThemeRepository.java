package ru.avtomaton.treecomments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.avtomaton.treecomments.entity.Theme;

/**
 * @author Anton Akkuzin
 */
public interface ThemeRepository extends JpaRepository<Theme, Long> {
}
