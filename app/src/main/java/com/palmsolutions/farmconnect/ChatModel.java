package com.palmsolutions.farmconnect;

import java.util.ArrayList;

public class ChatModel {
    String Sender;
    String Reciever;
    ArrayList<ChatMessage> Messages;

    public ChatModel(){

    }

    public ChatModel(String sender, String reciever, ArrayList<ChatMessage> messages) {
        Sender = sender;
        Reciever = reciever;
        Messages = messages;
    }

    public ChatModel(String sender, String reciever){
        Sender = sender;
        Reciever = reciever;
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

    public ArrayList<ChatMessage> getMessages() {
        return Messages;
    }

    public void setMessages(ArrayList<ChatMessage> messages) {
        Messages = messages;
    }
}
