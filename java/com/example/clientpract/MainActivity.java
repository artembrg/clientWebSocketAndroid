package com.example.clientpract;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.net.URI;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class MainActivity extends AppCompatActivity {

    TextView counter;
    Button incButton;
    Button addButton;
    EditText addNum;
    Button setButton;
    EditText setNum;
    Button rndButton;
    EditText rndA;
    EditText rndB;
    WebSocketClient socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counter = (TextView) findViewById(R.id.counter);
        incButton = (Button) findViewById(R.id.inc);
        addButton = (Button) findViewById(R.id.add);
        addNum = (EditText) findViewById(R.id.add_num);
        setButton = (Button) findViewById(R.id.set);
        setNum = (EditText) findViewById(R.id.set_num);
        rndButton = (Button) findViewById(R.id.rnd);
        rndA = (EditText) findViewById(R.id.rnd_a);
        rndB = (EditText) findViewById(R.id.rnd_b);
        connectWebSocket();


        incButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String instr = "INC";
                try {
                    socket.send(instr);
                } catch (Exception e) {
                    counter.setText("Нет соединения");
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!addNum.getText().toString().equals("")) {
                    String instr = "ADD_" + addNum.getText();
                    try {
                        socket.send(instr);
                    } catch (Exception e) {
                        counter.setText("Нет соединения");
                    }
                }
            }
        });

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!setNum.getText().toString().equals("")) {
                    String instr = "SET_" + setNum.getText();
                    try {
                        socket.send(instr);
                    } catch (Exception e) {
                        counter.setText("Нет соединения");
                    }
                }
            }
        });

        rndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!rndA.getText().toString().equals("") && !rndB.getText().toString().equals("")) {
                    String instr = "RND_" + rndA.getText() + "_" + rndB.getText();
                    try {
                        socket.send(instr);
                    } catch (Exception e) {
                        counter.setText("Нет соединения");
                    }
                }
            }
        });
    }

    private void connectWebSocket() {
        URI uri;
        try {
            uri = new URI("ws://192.168.43.172:9000");
        } catch (Exception e) {
            return;
        }

        socket = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) { }

            @Override
            public void onMessage(String s) {
                if (!s.equals("Error")) {
                    counter.setText(s);
                }
            }

            @Override
            public void onClose(int i, String s, boolean b) { }

            @Override
            public void onError(Exception e) { }
        };
        socket.connect();
    }
}