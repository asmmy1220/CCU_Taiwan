package com.example.asfast;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import android.os.Bundle;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;




    ProgressDialog barProgressDialog;
    Handler updateBarHandler;



    @BindView(R.id.button1)
    Button button1;



    @BindView(R.id.textView2)
    TextView textView;


    @Nullable
    @BindView(R.id.editText5)
    EditText editText;
    @Nullable
    @BindView(R.id.login)
    Button button3;





    private static final int port = 12567;//connection另一端的端口
    //hostip寫服務端的IP
    private static final String hostip = "192.168.1.32";//connection另一端的ip


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
        View mview = getLayoutInflater().inflate(R.layout.layout,null);
        EditText editText = (EditText)mview.findViewById(R.id.editText5);






    }

    public void basicReadWrite(String d,String e,String message) {
        // [START write_message]
        // Write a message to the database



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference numberRef = myRef.child("number");
        //DatabaseReference dateRef = myRef.child("date");
        View mview = getLayoutInflater().inflate(R.layout.layout,null);
        EditText editText = (EditText)mview.findViewById(R.id.editText5);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm ");
        String Time = sdf.format(new Date());

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String number = ds.child("number").getValue(String.class);
                    String date = ds.child("date").getValue(String.class);
                    String name = ds.child("name").getValue(String.class);
                    Log.d("TAG", number + " / " + date + "/" + name);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("amigo", "Failed to read value.", databaseError.toException());
            }
        };
        numberRef.addListenerForSingleValueEvent(eventListener);



        //editText.getText().toString().trim();

        Log.i("Client", "firebase");


        myRef.child("E Farm").child("Number:"+e).child("ASFAST test").child(Time).setValue(message);
        //myRef.child(editText.getText().toString()).child("Average").setValue(d);
        //myRef.child("E Farm").child(editText.getText().toString()).child("Result").setValue("Negative");


    }

    @OnClick({R.id.button1})   //偵測功能按鈕，可傳輸訊息至python
    public void runtcpClient() {

        View mview = getLayoutInflater().inflate(R.layout.layout,null);
        String a = "123";
        String b = "300";
        String c = "100";
        String d = "200";
        EditText editText = (EditText)mview.findViewById(R.id.editText5);
        //launchBarDialog(mview);
        dialog2();
        //basicReadWrite(a, b, c, d);
        //Log.i("Client", "按鈕監聽事件有效");



    }




    public void launchBarDialog(View view,String mmm) {
        updateBarHandler = new Handler();
        barProgressDialog = new ProgressDialog(MainActivity.this);
        View mview = getLayoutInflater().inflate(R.layout.layout,null);

        barProgressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                //Put your AlertDialog Here ....
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm ");
                String Time = sdf.format(new Date());

                alert.setTitle("Test completed");
                //dialog.setMessage("Average:"+h+"\n"+"Max"+i+"\n"+"Min"+j+"\n"+k);
                alert.setMessage(Time+"\n"+"Number: "+mmm+"\n"+"Cloud database: Uploaded");
                alert.show();


            }
        });
        barProgressDialog.setTitle("Processing,please wait ...");
        barProgressDialog.setMessage("Proceeding to test ...");
        barProgressDialog.setProgressStyle(barProgressDialog.STYLE_HORIZONTAL);
        barProgressDialog.setProgress(0);
        barProgressDialog.setMax(10);
        barProgressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Here you should write your time consuming task...
                    //tcpClient();

                    tcpClient(mmm);

                    while (barProgressDialog.getProgress() <= barProgressDialog
                            .getMax()) {

                        Thread.sleep(1000);
                        updateBarHandler.post(new Runnable() {
                            public void run() {
                                barProgressDialog.incrementProgressBy(2);



                                if (barProgressDialog.getProgress() == barProgressDialog
                                        .getMax()) {
                                    barProgressDialog.dismiss();

                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    Log.e("thiago", "error when trying to run the Thread==>"+e.getMessage());
                }
            }
        }).start();
    }

    public void dialog3(){

        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String a = "123";
        String b = "300";
        String c = "100";
        String d = "200";



        Thread t = new Thread(new Runnable() {

            @Override

            public void run() {

                try {

                    Thread.sleep(3000);//顯示3秒後，關閉dialog

                } catch (InterruptedException e) {

// TODO Auto-generated catch block

                    e.printStackTrace();

                }

               finally {
                    progressDialog.dismiss();
                }


            }


        });


        t.start();






    }


    public void dialog123(String h){

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm ");
        String Time = sdf.format(new Date());

        dialog.setTitle("Test completed");
        //dialog.setMessage("Average:"+h+"\n"+"Max"+i+"\n"+"Min"+j+"\n"+k);
        dialog.setMessage(Time+"\n"+"Result: Negative"+"\n"+"Cloud database: Uploaded"+"\n"+"Average:"+h);
        dialog.setCancelable(true);
        dialog.show();


    }

    public void dialog2(){

        AlertDialog.Builder dialog2 = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        View mview = getLayoutInflater().inflate(R.layout.layout,null);




        dialog2.setTitle("Please enter the number");



        dialog2.setCancelable(true);

        Button button4 = (Button) mview.findViewById(R.id.button20);


        button4.setOnClickListener((View view) -> {
            EditText editText = (EditText)mview.findViewById(R.id.editText5);
            String aaa = editText.getText().toString();
                if (!editText.getText().toString().isEmpty())
                {


                    Log.i("Client", "check:"+aaa);
                    launchBarDialog(mview,aaa);


                    //Toast.makeText(MainActivity.this,"Proceeding to test",Toast.LENGTH_LONG).show();

                   /* Thread thread = new Thread() {

                        @Override
                        public void  run(){
                          tcpClient();
                        }
                    };
                    thread.start(); */


                }

                else

                {
                    Toast.makeText(MainActivity.this,"Please fill any empty fields",
                            Toast.LENGTH_LONG).show();
                }


            }
        );


        dialog2.setView(mview);
        AlertDialog dialog = dialog2.create();
        dialog.show();

    }



    public void tcpClient(String num){

        String hello = "";


        try{

            Socket socketClient = new Socket(hostip, port);
            //Log.i("Client", "新建socket有效");


                BufferedReader in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
                //LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                //View mview = getLayoutInflater().inflate(R.layout.layout,null);
                //EditText editText = mview.findViewById(R.id.editText5);

                //String op = editText.getText().toString();

                String outMsg = "TCP connecting to " + port + System.getProperty("line.separator");//發出的數據





                    Log.i("Client", num);
                    Log.i("Client", "得到發出數據");



                        out.write(outMsg);//發送數據

                        out.write(num);

                        Log.i("Client", "發送數據有效");
                        out.flush();
                        Log.i("TcpClient", "sent: " + outMsg);
                        //Log.i("TcpClient", "sent: " + op);



            String inMsg = in.readLine() + System.getProperty("line.separator");//服務器返回的數據



            Log.i("TcpClient", "received: " + inMsg);






            String[] tokens = inMsg.split(",|\n");

            for (String token:tokens) {
                Log.i("TcpClient", "received: " + token);
            }

            Log.i("矩陣","Average："+tokens[0]);
            Log.i("矩陣","Result："+tokens[1]);
            //Log.i("矩陣","最小值："+tokens[2]);
            //Log.i("矩陣","標準差："+tokens[3]);

            //textView.setText("平均值："+tokens[0]+"\n"+"最大值"+tokens[1]+"\n"+"最小值："+tokens[2]+"\n"+"標準差："+tokens[3]);

            basicReadWrite(tokens[0],num,tokens[1]);



            socketClient.close();



        }catch (UnknownHostException e){e.printStackTrace();}
        catch (IOException e){e.printStackTrace();}


    }




}
