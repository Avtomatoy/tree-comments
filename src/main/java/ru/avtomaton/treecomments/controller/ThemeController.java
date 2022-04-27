package ru.avtomaton.treecomments.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.avtomaton.treecomments.entity.Message;
import ru.avtomaton.treecomments.entity.Theme;
import ru.avtomaton.treecomments.service.MessageService;
import ru.avtomaton.treecomments.service.ThemeService;
import ru.avtomaton.treecomments.util.Delete;
import ru.avtomaton.treecomments.util.MessageTree;

import java.security.Principal;
import java.util.Date;
import java.util.Optional;

/**
 * @author Anton Akkuzin
 */
@Controller
@RequestMapping("/themes")
public class ThemeController {

    @Autowired
    private ThemeService themeService;
    @Autowired
    private MessageService messageService;

    @GetMapping
    public String get(Model model) {
        model.addAttribute("allThemes", themeService.allThemes());
        model.addAttribute("themeForm", new Theme());

        return "themes";
    }

    @PostMapping
    public String makeTheme(@ModelAttribute("themeForm") Theme theme) {
        theme.setCreationDate(new Date());
        Message message = new Message();
        message.setThemeId(themeService.save(theme).getId());
        message.setParentMessageId(null);
        message.setName(theme.getUsername());
        message.setText(theme.getMessageText());
        message.setCreationDate(new Date());
        messageService.save(message);

        return "redirect:/themes";
    }

    @GetMapping("/{themeId}")
    public String getTheme(@PathVariable("themeId") Long id, Model model, Principal principal) {

        Optional<Theme> theme = themeService.findById(id);
        if (theme.isPresent()) {
            model.addAttribute("theme", theme.get());

            Long themeId = theme.get().getId();
            Message root = messageService.findByThemeId(themeId);
            MessageTree messageTree = messageService.getMessageTree(root);

            model.addAttribute("htmlContent", messageService.formMessageTreeJspTemplate(messageTree, principal != null));
        }

        return "theme";
    }

    @PostMapping("/{themeId}/reply")
    public String reply(@PathVariable("themeId") Long themeId, @ModelAttribute("reply") Message message) {
        message.setCreationDate(new Date());
        messageService.save(message);

        return "redirect:/themes/" + themeId;
    }

    @PostMapping("/{themeId}/delete")
    public String delete(@ModelAttribute("delete") Delete delete) {
        Optional<Message> optionalMessage = messageService.findById(delete.getMessageId());

        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            if (message.getParentMessageId() == null) {
                messageService.deleteAll(messageService.findAllByThemeId(message.getThemeId()));
                themeService.delete(message.getThemeId());

                return "redirect:/themes";
            } else {

                messageService.deleteAll(messageService.findByParentId(message.getId()));
                messageService.deleteById(message.getId());

                return "redirect:/themes/" + message.getThemeId();
            }
        }
        throw new RuntimeException();
    }
}
