package com.cryptochat.theguys.cryptochat.Controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.cryptochat.theguys.cryptochat.R;
import com.cryptochat.theguys.cryptochat.Utils.Utils;
import com.cryptochat.theguys.cryptochat.Utils.WSClient;

/**
 * Created by kyle on 4/10/17.
 */

public class SettingsActivity extends AppCompatActivity {

    private Context c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        c = this;
    }

    public void logoutAction(View view) {
        //TODO check if can using the onClose message on server or I have to send a personal close message
//        WSClient.sendMessage("Server","Close",Utils.USERNAME);
        WSClient.webSocketClient.close();
        Utils.SAVED_CHATS = null;
        Utils.USERNAME = null;
        Utils.USER_ID = 0;
        Utils.CURRENT_CHAT = null;
        Utils.handler = null;
        Intent intent = new Intent(c,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void updateAvatarAction(View view) {
    }

    public void updatePasswordAction(View view) {
    }

    public void addFriendAction(View view) {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(c);

        //Creates the visuals for the dialog
        builder.setTitle("Add Contacts");

        LayoutInflater inflater = getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_add_contact, null))
                // Add action buttons
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        LoginDialogFragment.this.getDialog().cancel();
                    }
                });

        //Creates the dialog and displays it
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
