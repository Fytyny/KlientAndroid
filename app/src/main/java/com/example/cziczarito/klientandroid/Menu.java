package com.example.cziczarito.klientandroid;

import android.Manifest;
import android.content.Intent;
import android.net.LocalSocketAddress;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.net.LocalSocket;

import org.w3c.dom.Text;

import java.net.*;
import java.io.*;
public class Menu extends AppCompatActivity {
    public EditText ipad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //final TextView mainWindow = (TextView) this.findViewById(R.id.Receiver);


    }

    public void przyciskOnClick(View view){
        ipad = (EditText)this.findViewById(R.id.IPinput);
        EditText portad = (EditText)this.findViewById(R.id.PortInput);
        TextView message = (TextView)this.findViewById(R.id.rec);
        String ip = ipad.getText().toString();

        try {
            Integer port = Integer.parseInt(portad.getText().toString());
            if (port.toString().length() != 4) throw new PortException("Invalid port");
            Intent intentBundle = new Intent(Menu.this, Chat.class);
            Bundle pass = new Bundle();
            pass.putString("ip",ip);
            pass.putInt("port",port);
            intentBundle.putExtras(pass);
            startActivity(intentBundle);
        }catch(Exception e){

           message.setText(e.getMessage());

        }

    }




}

class PortException extends Exception{
    public PortException(){
        super();
    }
    public PortException (String message){
        super(message);
    }
    public PortException (String message, Throwable cause){
        super(message, cause);
    }
    public PortException (Throwable cause){
        super(cause);
    }

}



