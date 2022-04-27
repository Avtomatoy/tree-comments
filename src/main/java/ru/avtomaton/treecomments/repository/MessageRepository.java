package ru.avtomaton.treecomments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.avtomaton.treecomments.entity.Message;

import java.util.List;

/**
 * @author Anton Akkuzin
 */
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByParentMessageId(Long parentMessageId);

    @Query(value = "SELECT id, creation_date, name, parent_message_id, text, theme_id FROM public.message where parent_message_id is null and theme_id = ?1", nativeQuery = true)
    Message findByThemeId(Long themeId);

    @Query(value = "SELECT id, creation_date, name, parent_message_id, text, theme_id FROM public.message where theme_id = ?1", nativeQuery = true)
    List<Message> findAllByThemeId(Long themeId);


}
