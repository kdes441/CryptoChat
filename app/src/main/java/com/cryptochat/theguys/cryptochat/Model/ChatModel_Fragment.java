package com.cryptochat.theguys.cryptochat.Model;

import android.support.annotation.NonNull;

import com.cryptochat.theguys.cryptochat.Utils.Utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Created by kyle on 3/28/17.
 */

public class ChatModel_Fragment {

    //Has all of the open chats
    private Map<String,String> chats = new HashMap<>();

    //Constructor
    public ChatModel_Fragment(){

        //TEST
        ///////////////////////////////////////
        Stack testStack = new Stack();
        String msg1 = Utils.messageToJSON("Deems","Don't know");
        String msg2 = Utils.messageToJSON("Kyle","What don't you know");
        String msg3 = Utils.messageToJSON("Deems","A lot manne");
        testStack.add(msg1);
        testStack.add(msg2);
        testStack.add(msg3);
        Utils.SAVED_CHATS.put("Deems",testStack);

        Stack testStack2 = new Stack();
        String msg4 = Utils.messageToJSON("RandomMofo","Don't know");
        String msg5 = Utils.messageToJSON("Kyle","What don't you know");
        String msg6 = Utils.messageToJSON("RandomMofo","Kyle aye!!");
        testStack2.add(msg4);
        testStack2.add(msg5);
        testStack2.add(msg6);
        Utils.SAVED_CHATS.put("RandomMofo",testStack2);
        ///////////////////////////////////////

        //Iterates through the chats in the hash map
        for(Map.Entry<String, Stack> entry : Utils.SAVED_CHATS.entrySet()) {
            Stack chat = entry.getValue();
            String mostRecent = (String) chat.peek();
            String contact = entry.getKey();

            //Adds the contacts name and the most recent message in the conversation to a map of chats
            chats.put(contact, mostRecent);
        }
    }
    //Refreshes the chats most recent message
    public void refresh(){
        //TODO maybe update indival chats instead of all of them
        HashMap<String,Stack> conversations = Utils.SAVED_CHATS;

        //Iterates through the chats in the hash map
        for(Map.Entry<String, Stack> entry : conversations.entrySet()){
            Stack chat = entry.getValue();
            String mostRecent = (String) chat.peek();
            String contact = entry.getKey();
            //Adds the contacts name and the most recent message in the conversation to a map of chats
            chats.put(contact,mostRecent);
        }
    }

    //Gets the chats
    public Map<String,String> getChats(){ return this.chats; }


}
