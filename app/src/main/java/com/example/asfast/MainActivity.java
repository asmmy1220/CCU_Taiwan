package com.example.asfast;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;



    @BindView(R.id.button1)Button button1;
    @BindView(R.id.editText_name)EditText editText;
    @BindView(R.id.button2)Button button2;
    @BindView(R.id.textView2)TextView textView;




    private static final int port=12567;//connection另一端的端口
    //hostip寫服務端的IP
    private static final String hostip="192.168.1.28";//connection另一端的ip


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override   //底部導航欄切換會顯示不同文字。
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_detect:
                    mTextMessage.setText("ASFAST test page");

                    break;
                case R.id.navigation_record:
                    mTextMessage.setText("History page");
                    break;
                case R.id.navigation_setting:
                    mTextMessage.setText("Setting page");
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        ButterKnife.bind(this);



    }

    public void basicReadWrite() {
        // [START write_message]
        // Write a message to the database

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("Hola").setValue("Sammy");
        // [END write_message]
        // [START read_message]
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d("hola", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("amigo", "Failed to read value.", error.toException());
            }
        });


    }

    @OnClick(R.id.button1)   //偵測功能按鈕，可傳輸訊息至python
    public void runtcpClient(){
        Log.i("Client","按鈕監聽事件有效");

        Thread thread=new Thread(){
            @Override
            public void run()
            { tcpClient();

            }
        };


        thread.start();

    }

    @OnClick(R.id.button2)   //測試用按鈕，無任何功能。
    public void setButton2(){
        basicReadWrite();
        Toast toast = Toast.makeText(this, "按鈕已被點擊", Toast.LENGTH_SHORT);
        toast.show();

    }

    void testPush() {
        Log.d("test","測試用push上傳程式");
    }


    public String tcpClient(){

        String hello = "";

        try{

            Socket socketClient = new Socket(hostip, port);
            //Log.i("Client", "新建socket有效");


                BufferedReader in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));


                String outMsg = "TCP connecting to " + port + System.getProperty("line.separator");//發出的數據

                String op = editText.getText().toString();


                    //Log.i("Client", "得到發出數據");


                        out.write(outMsg);//發送數據

                        out.write(op);

                        //Log.i("Client", "發送數據有效");
                        out.flush();
                        Log.i("TcpClient", "sent: " + outMsg);
                        //Log.i("TcpClient", "sent: " + op);



            String inMsg = in.readLine() + System.getProperty("line.separator");//服務器返回的數據

            hello = inMsg;

            Log.i("TcpClient", "received: " + inMsg);



            //int Hii = Integer.parseInt(inMsg);


            String[] tokens = inMsg.split(",");

            for (String token:tokens) {
                Log.i("TcpClient", "received: " + token);
            }

            Log.i("矩陣","平均值："+tokens[0]);
            Log.i("矩陣","最大值："+tokens[1]);
            Log.i("矩陣","最小值："+tokens[2]);

            //textView.setText(String.format(getResources().getString(R.string.average),tokens[0],tokens[1],tokens[2]));

            socketClient.close();

              return hello;


        }catch (UnknownHostException e){e.printStackTrace();}
        catch (IOException e){e.printStackTrace();}

        return hello ;

    }




}
