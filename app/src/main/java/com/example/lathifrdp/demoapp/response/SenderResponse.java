package com.example.lathifrdp.demoapp.response;

import com.example.lathifrdp.demoapp.model.Message;
import com.example.lathifrdp.demoapp.model.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SenderResponse {

    @SerializedName("sender")
    private User sender;

    @SerializedName("messages")
    private List<Message> messages;

    public SenderResponse(User sender, List<Message> messages){
        this.sender = sender;
        this.messages = messages;
    }

    public User getSender() {
        return sender;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
