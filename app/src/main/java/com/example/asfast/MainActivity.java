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


public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    @BindView(R.id.button1)Button button1;
    @BindView(R.id.editText_name)EditText editText;
    @BindView(R.id.button2)Button button2;



    private static final int port=12567;//connection另一端的端口
    //hostip寫服務端的IP
    private static final String hostip="192.168.1.34";//connection另一端的ip


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_detect:
                    mTextMessage.setText("ASFAST test page");
                    return true;
                case R.id.navigation_record:
                    mTextMessage.setText("History page");
                    return true;
                case R.id.navigation_setting:
                    mTextMessage.setText("Setting page");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        ButterKnife.bind(this);



    }

    @OnClick(R.id.button1)
    public void runtcpClient(){
        Log.i("Client","按鈕監聽事件有效");
        Thread thread=new Thread(){
            @Override
            public void run(){ tcpClient();}
        };
        thread.start();
    }

    @OnClick(R.id.button2)
    public void setButton2(){
        Toast toast = Toast.makeText(this, "按鈕已被點擊", Toast.LENGTH_SHORT);
        toast.show();

    }

    void testPush() {
        Log.d("test","測試用push上傳程式");
    }

    private void tcpClient(){


        try{

            Socket socketClient = new Socket(hostip, port);
            Log.i("Client", "新建socket有效");


                BufferedReader in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));


                String outMsg = "TCP connecting to " + port + System.getProperty("line.separator");//發出的數據

                String op = editText.getText().toString();


                    Log.i("Client", "得到發出數據");


                        out.write(outMsg);//發送數據

                        out.write(op);

                        Log.i("Client", "發送數據有效");
                        out.flush();
                        Log.i("TcpClient", "sent: " + outMsg);
                        Log.i("TcpClient", "sent: " + op);



            String inMsg = in.readLine() + System.getProperty("line.separator");//服務器返回的數據
                Log.i("TcpClient", "received: " + inMsg);
                socketClient.close();

        }catch (UnknownHostException e){e.printStackTrace();}
        catch (IOException e){e.printStackTrace();}
    }




}
