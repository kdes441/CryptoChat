package com.cryptochat.theguys.cryptochat.Model;


import com.cryptochat.theguys.cryptochat.Controller.ChatActivity;
import com.cryptochat.theguys.cryptochat.Utils.ChatClient;
import com.cryptochat.theguys.cryptochat.Utils.Utils;
import com.cryptochat.theguys.cryptochat.Utils.WSClient;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Stack;

/**
 * Created by kyle on 3/11/17.
 */

public class ChatModel {

    private String chatParticipant;
    private int position = 1;

    //Constructor
    public ChatModel(String chatParticipant){
        this.chatParticipant = chatParticipant;
    }

    //Adds message to hashMap
    public void sendMessage(String sender, String message){
        WSClient.sendMessage(chatParticipant,message,sender);
    }

    //Gets chat participant
    public String getChatParticipant(){
        return this.chatParticipant;
    }

    //TODO Maybe I should encrypt the hashmap first or w.e
    //Store the users conversation
//    public void storeConversation(){
//        Utils.SAVED_CHATS.put(chatParticipant,conversation);
//        conversation.clear();
//    }

    //Gets the users conversation with someone
    public Stack retrieveConversations(){
        //Checks if there is an open chat with the particular
        //If they do than retrieve the convo else do nothing
        if(Utils.SAVED_CHATS.containsKey(chatParticipant))
            return Utils.SAVED_CHATS.get(chatParticipant);
        else{
            return new Stack();
        }

    }

    //TODO please find another way
    //Helper shit
    public HashMap<String,String> getMapSet(String message){
        HashMap<String,String> set = new HashMap<>();
        set.put(Utils.USERNAME+position,message);
        return set;
    }

}
