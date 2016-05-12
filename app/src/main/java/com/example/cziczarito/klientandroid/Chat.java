package com.example.cziczarito.klientandroid;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Chat extends AppCompatActivity {
    TextView mainWindow;
    EditText input;
    Connect sock;

    @Override
    protected  void onStop(){
        try {
            sock.gniazdo.close();
            super.onStop();
        }catch(Exception e){

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mainWindow = (TextView) this.findViewById(R.id.Receiver);
        input = (EditText)this.findViewById(R.id.Text);
        try {

            Intent intentExtras = getIntent();
            final String ip = intentExtras.getStringExtra("ip");
            final int port = intentExtras.getIntExtra("port",0000);
            final Handler mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    String text = (String)msg.obj;
                    mainWindow.append(text + "\n");
                }
            };
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    //mainWindow.setText("bla2");
                    sock = new Connect(ip,port,mainWindow,mHandler);
                    sock.inputLoop();

                }
            });



            thread.start();
        }catch(Exception e){
            mainWindow.setText(e.getMessage());
        }
    }

    public void buttonOnClick(View view){
            String sendMessage = input.getText().toString();
            try {
                OutputStream output = sock.gniazdo.getOutputStream();
                output.write(sendMessage.getBytes());
            }catch (Exception e){
                mainWindow.setText("output " + e.getMessage());
            }
    }


}

class Connect{
    public Socket gniazdo;
    String ip;
    int port;
    final TextView mainWindow;
    Handler mhandler;
    Connect(String ip, int port, TextView main, Handler mes){
        this.mainWindow = main;
        mhandler = mes;
        try {

            gniazdo = new Socket(ip, port);

        }catch (Exception e){
            Message in = new Message();
            in.obj = e.getMessage();
            mhandler.sendMessage(in);
        }


    }
    void inputLoop(){
        try {
            InputStream input = gniazdo.getInputStream();
            byte[] bres = new byte[1024];
            int l;
            while(true){
                l = input.read(bres);
               // if (l!= -1) mainWindow.append(new String(bres,0,l) + "\n");
                Message in = new Message();
                in.obj = new String(bres,0,l);
                mhandler.sendMessage(in);
            }

        }catch (Exception e){
            Message in = new Message();
            in.obj = e.getMessage();
            mhandler.sendMessage(in);
        }

    }

}



