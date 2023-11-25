package com.palmsolutions.farmconnect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatModel {
    String Sender;
    String Reciever;
    String chat_id;
    ArrayList<Message> messages ;
    public ChatModel(){
    }
    public ChatModel(String sender, String reciever, String chat_id) {
        Sender = sender;
        Reciever = reciever;
        this.chat_id = chat_id;
    }
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("sender", Sender);
        map.put("reciever", Reciever);
        map.put("chat_id", chat_id);

        return map;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getReciever() {
        return Reciever;
    }

    public void setReciever(String reciever) {
        Reciever = reciever;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
