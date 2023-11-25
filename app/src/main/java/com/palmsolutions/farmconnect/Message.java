package com.palmsolutions.farmconnect;

import com.google.firebase.database.ServerValue;

import java.util.Date;

public class Message {

    private String Message_ID;
    private String Sender_ID;
    private String Reciever_ID;
    private String Content;
    private Object timeStamp;
    public Message(){

    }
    public Message(String message_ID, String sender_ID, String reciever_ID, String content) {
        Message_ID = message_ID;
        Sender_ID = sender_ID;
        Reciever_ID = reciever_ID;
        Content = content;
        this.timeStamp = ServerValue.TIMESTAMP;
    }

    public String getMessage_ID() {
        return Message_ID;
    }

    public void setMessage_ID(String message_ID) {
        Message_ID = message_ID;
    }

    public String getSender_ID() {
        return Sender_ID;
    }

    public void setSender_ID(String sender_ID) {
        Sender_ID = sender_ID;
    }

    public String getReciever_ID() {
        return Reciever_ID;
    }

    public void setReciever_ID(String reciever_ID) {
        Reciever_ID = reciever_ID;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
