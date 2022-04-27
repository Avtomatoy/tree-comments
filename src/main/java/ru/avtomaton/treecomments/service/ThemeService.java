package ru.avtomaton.treecomments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avtomaton.treecomments.entity.Theme;
import ru.avtomaton.treecomments.repository.ThemeRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Anton Akkuzin
 */
@Service
public class ThemeService {
    @Autowired
    private ThemeRepository themeRepository;

    public Theme save(Theme theme) {
        return themeRepository.save(theme);
    }

    public List<Theme> allThemes() {
        return themeRepository.findAll();
    }

    public Optional<Theme> findById(Long id) {
        return themeRepository.findById(id);
    }

    public void delete(Long themeId) {
        themeRepository.deleteById(themeId);
    }
}
