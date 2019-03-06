package com.example.lathifrdp.demoapp.response;

import com.example.lathifrdp.demoapp.model.Message;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MessageResponse {

    @SerializedName("result")
    private List<Message> messages;

    public MessageResponse(List<Message> messages){
        this.messages = messages;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
