package com.palmsolutions.farmconnect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatModel implements Serializable {
    private String sender;
    private String receiver;
    private String chatId;
    private List<Message> messages;

    public ChatModel() {
    }

    public ChatModel(String sender, String receiver, String chatId) {
        this.sender = sender;
        this.receiver = receiver;
        this.chatId = chatId;
        this.messages = new ArrayList<>();
    }

    public ChatModel(String sender, String receiver, String chatId, List<Message> messages) {
        this.sender = sender;
        this.receiver = receiver;
        this.chatId = chatId;
        this.messages = messages;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("sender", sender);
        map.put("receiver", receiver);
        map.put("chatId", chatId);

        return map;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
