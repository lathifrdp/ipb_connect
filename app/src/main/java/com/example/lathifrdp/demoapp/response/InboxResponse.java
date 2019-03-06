package com.example.lathifrdp.demoapp.response;

import com.example.lathifrdp.demoapp.model.Message;
import com.example.lathifrdp.demoapp.model.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InboxResponse {

    @SerializedName("result")
    private List<SenderResponse> senderResponses;

    public InboxResponse(List<SenderResponse> senderResponses){
        this.senderResponses = senderResponses;
    }

    public List<SenderResponse> getSenderResponses() {
        return senderResponses;
    }
}
