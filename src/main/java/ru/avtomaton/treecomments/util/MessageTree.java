package ru.avtomaton.treecomments.util;

import ru.avtomaton.treecomments.entity.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anton Akkuzin
 */
public class MessageTree {

    public List<MessageTree> children = new ArrayList<>();

    public Message message;

    public MessageTree(Message message) {
        this.message = message;
    }
}
