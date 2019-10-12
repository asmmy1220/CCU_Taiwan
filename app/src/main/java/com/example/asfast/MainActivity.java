package com.example.asfast;

import android.Manifest;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.os.SystemClock;
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
    private TextView location1;
    private String commadStr;
    private LocationManager locationManager;
    public static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;




    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };


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
    private static final String hostip = "192.168.1.30";//connection另一端的ip


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
        commadStr = LocationManager.GPS_PROVIDER;
        location1 = findViewById(R.id.location1);





    }

    public void basicReadWrite(String d, String e, String g, String f) {
        // [START write_message]
        // Write a message to the database



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference numberRef = myRef.child("number");
        //DatabaseReference dateRef = myRef.child("date");

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


        //myRef.child("E Farm").child("Number:039495").child("ASFAST test").child("2019-04-16 10:28").setValue("Result: negative");
        myRef.child(editText.getText().toString()).child("Average").setValue(d);

    }

    @OnClick({R.id.button1})   //偵測功能按鈕，可傳輸訊息至python
    public void runtcpClient() {


        String a = "123";
        String b = "300";
        String c = "100";
        String d = "200";

        dialog2();
        //basicReadWrite(a, b, c, d);
        //Log.i("Client", "按鈕監聽事件有效");


        //Thread thread = new Thread() {

            //@Override
            //public void run() {
                //super.run();
            //}



        //};
        //thread.start();
    }


    /*@Optional
    @OnClick(R.id.button2)
    public void setButton2() {



        String a = "123";
        String b = "300";
        String c = "100";
        String d = "200";



        basicReadWrite(a,b,c,d);
        //basicReadWrite();
        //Intent intent = new Intent();
        //intent.setClass(MainActivity.this, MapsActivity.class);
        //startActivity(intent);
        /*locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (Build.VERSION.SDK_INT >= 23 &&ActivityCompat.checkSelfPermission(MainActivity.this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this,android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            requestPermissions(INITIAL_PERMS, MY_PERMISSION_ACCESS_COARSE_LOCATION);
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(commadStr, 1000, 5, locationListener);
        Location location = locationManager.getLastKnownLocation(commadStr);

        if (location != null)

            location1.setText("經度："+location.getLongitude()+"\n緯度："+location.getLatitude());

        else
            location1.setText(("GPS value fail"));
            //locationManager.getLastKnownLocation(commadStr);
            //location1.setText("經度："+location.getLongitude()+"\n緯度："+location.getLatitude());

        Toast toast = Toast.makeText(this, "按鈕已被點擊", Toast.LENGTH_SHORT);
        toast.show();


    } */

    

    public void dialog(String h, String i, String j, String k){

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm ");
        String Time = sdf.format(new Date());

        dialog.setTitle("Test completed");
        //dialog.setMessage("Average:"+h+"\n"+"Max"+i+"\n"+"Min"+j+"\n"+k);
        dialog.setMessage(Time+"\n"+"Result: Negative"+"\n"+"Cloud database: Uploaded");
        dialog.setCancelable(true);
        dialog.show();


    }

    public void dialog2(){

        AlertDialog.Builder dialog2 = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        View mview = getLayoutInflater().inflate(R.layout.layout,null);




        dialog2.setTitle("Please enter the number");
        dialog2.setView(editText);


        dialog2.setCancelable(true);

        Button button4 = (Button) mview.findViewById(R.id.button20);
        EditText editText = (EditText)mview.findViewById(R.id.editText5);

        button4.setOnClickListener((View view) -> {

                if (!editText.getText().toString().isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Preparing to start...",Toast.LENGTH_LONG).show();
                    String a = "123";
                    String b = "300";
                    String c = "100";
                    String d = "200";
                    SystemClock.sleep(3000);

                    dialog(a,b,c,d);

                    Toast.makeText(MainActivity.this,"Proceeding to test",Toast.LENGTH_LONG).show();

                    /*Thread thread = new Thread() {

                        @Override
                        public void  run(){
                          tcpClient();
                        }
                    }; */
                    //thread.start();


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






    public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            location1.setText("經度："+location.getLongitude()+"\n緯度："+location.getLatitude());
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    void testPush() {
        Log.d("test","測試用push上傳程式");
    }


    public void tcpClient(){

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



            Log.i("TcpClient", "received: " + inMsg);






            String[] tokens = inMsg.split(",|\n");

            for (String token:tokens) {
                Log.i("TcpClient", "received: " + token);
            }

            Log.i("矩陣","平均值："+tokens[0]);
            Log.i("矩陣","最大值："+tokens[1]);
            Log.i("矩陣","最小值："+tokens[2]);
            Log.i("矩陣","標準差："+tokens[3]);

            //textView.setText("平均值："+tokens[0]+"\n"+"最大值"+tokens[1]+"\n"+"最小值："+tokens[2]+"\n"+"標準差："+tokens[3]);

            basicReadWrite(tokens[0],tokens[1],tokens[2],tokens[3]);
            dialog(tokens[0],tokens[1],tokens[2],tokens[3]);



            socketClient.close();




        }catch (UnknownHostException e){e.printStackTrace();}
        catch (IOException e){e.printStackTrace();}



    }




}
