package com.cryptochat.theguys.cryptochat.Utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Stack;


/**
 * Created by kyle on 3/15/17.
 */

public class Utils {

    //API & ChatModel urls and port number
    public static String API_URL = "http://10.0.2.2:3000/api/V3";
    //TODO Remember to change from dev ip
    public static String CHAT_URL = "10.0.2.2";
    public static String WS_URL = "ws://10.0.2.2:5665";
//    public static String CHAT_URL = "54.175.15.9";
    public static int CHAT_PORT = 5665;

    //TODO I think this unsafe but fuck it...fix it
    //User info
    public static String USERNAME;
    public static int USER_ID;

    //API routes
    public static String REGISTER_ROUTE = "/register";
    public static String LOGIN_ROUTE = "/login";
    public static String FRIENDS_LIST_ROUTE = "/friends";
    public static String UPDATE_PASSWORD_ROUTE = "/updatep";
    public static String UPDATE_PICTURE_ROUTE = "/updatea";
    public static String LOGOUT_ROUTE = "/logout";
    public static String CONTACTS_ROUTE = "/contacts";

    //Allows for debug message_row to be seen
    public static boolean ALLOW_DEBUG = true;

    //Stores current conversation
    public static HashMap<String,Stack> SAVED_CHATS = new HashMap<>();

    //Current Chat Name
    public static String CURRENT_CHAT = "";

    //Handler is to update
    public static Handler handler;

    //Outputs debug message if ALLOW_DEBUG is set to true
    public static void output(String msg){
        if(Utils.ALLOW_DEBUG == true)
            Log.d("Crypto Chat",msg);
    }

    //Creates popup message
    public static void toast(String message, Context context){
        Toast.makeText(context, message,Toast.LENGTH_SHORT).show();
    }

    //This method hides the keyboard
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        try{
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception ex){
            output("Tried to hide keyboard but it is already hidden");
        }
    }

    //Encodes strings to json messages meant for a conversation stack
    public static String messageToJSON(String name, String message){
        JSONObject jsonMessage = new JSONObject();
        try {
            jsonMessage.put("From", name);
            jsonMessage.put("Message", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonMessage.toString();
    }

    //Decodes json messages from a conversation stack
    public static String[] messageFromJSON(String message){
        JSONObject response;
        try{
            response = new JSONObject(message);
            String from = (String) response.get("From");
            message = (String) response.get("Message");
            return new String[]{from,message};
        } catch (JSONException e){
            e.printStackTrace();
        }
        return new String[]{"Fat ass error trying to parse json conversation message"};
    }


    //Receives incoming messages and figures out weather the should be display onf the screen or saved
    public static void receivedMessage(String from,String message){

        //Checks if chat is currently open
        if(CURRENT_CHAT.equals(from)){
            //There is a chat open
//            HashMap<String,String> hashMap = new HashMap<>();
//            hashMap.put(from,message);
            Message message1 = handler.obtainMessage();
//            message1.obj = hashMap;
            //TODO clean this up
            message1.obj = messageToJSON(from,message);
            handler.sendMessage(message1);
//            hashMap = null;
        } else {
            //If its not  open
            Stack map = SAVED_CHATS.get(from);
            //Checks if there a saved chat with the person
            if(map == null){
                //If there isn't a save chat with the peron, create a new HashMap<String,String>
                // Create a chat Hash
                Stack newConversation = new Stack();
                newConversation.add(messageToJSON(from,message));

                //Add new conversation to all the save chat storage
                SAVED_CHATS.put(from,newConversation);
            } else {
                //There is a save chat with person
                map.add(messageToJSON(from,message));
                SAVED_CHATS.put(from,map);
            }
            map = null;
        }
    }
}
