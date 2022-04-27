package ru.avtomaton.treecomments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avtomaton.treecomments.entity.Message;
import ru.avtomaton.treecomments.repository.MessageRepository;
import ru.avtomaton.treecomments.util.MessageTree;

import java.util.List;
import java.util.Optional;

/**
 * @author Anton Akkuzin
 */
@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Message save(Message message) {
        return messageRepository.save(message);
    }

    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }

    public List<Message> findByParentId(Long id) {
        return messageRepository.findByParentMessageId(id);
    }

    public Message findByThemeId(Long themeId) {
        return messageRepository.findByThemeId(themeId);
    }

    public List<Message> findAllByThemeId(Long themeId) {
        return messageRepository.findAllByThemeId(themeId);
    }

    public void deleteAll(List<Message> messages) {
        messageRepository.deleteAll(messages);
    }

    public void deleteById(Long id) {
        messageRepository.deleteById(id);
    }

    public MessageTree getMessageTree(Message root) {
        MessageTree messageTree = new MessageTree(root);

        for (Message message : findByParentId(root.getId())) {
            messageTree.children.add(getMessageTree(message));
        }

        return messageTree;
    }

    public String formMessageTreeJspTemplate(MessageTree tree, boolean moderator) {
        String template = "<div>\n";

        template += process(tree, 0, moderator);

        template += "</div>";
        return template;
    }
    private String process(MessageTree tree, int level, boolean moderator) {
//        if (tree.children.isEmpty()) {
//            return template;
//        }

        String template = "<div class=lvl-" + level + ">\n"
                            + tree.message.getName() + "<br>\n"
                            + tree.message.getText() + "\n"
                        + "<form method=\"POST\" action=\"/themes/" + tree.message.getThemeId() +"/reply\" modelAttribute=\"reply\">" + "\n"
                            + "<input type=\"hidden\" name=\"themeId\" path=\"themeId\" value=\"" + tree.message.getThemeId() + "\">" + "\n"
                            + "<input type=\"hidden\" name=\"parentMessageId\" path=\"parentMessageId\" value=\"" + tree.message.getId() + "\">" + "\n"
                            + "Ваше имя:<input type=\"text\" name=\"name\" path=\"name\" required=\"true\">" + "\n"
                            + "Ваш ответ:<input type=\"text\" name=\"text\" path=\"text\" required=\"true\">" + "\n"
                            + "<button>Ответить</button>" + "\n"
                        + "</form>" + "\n";

        if (moderator) {
            template += "<form method=\"POST\" action=\"/themes/" + tree.message.getThemeId() +"/delete\" modelAttribute=\"delete\">" + "\n"
                            + "<input type=\"hidden\" name=\"messageId\" path=\"messageId\" value=\"" + tree.message.getId() + "\">" + "\n"
                            + "<button>Удалить</button>" + "\n"
                        + "</form>" + "\n";
        }

        for (MessageTree messageTree : tree.children) {
            template += process(messageTree, level + 1, moderator);
        }
        template += "</div>";

        return template;
    }
}
