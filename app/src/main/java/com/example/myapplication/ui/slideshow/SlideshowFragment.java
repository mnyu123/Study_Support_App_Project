package com.example.myapplication.ui.slideshow;

import static android.content.Context.NOTIFICATION_SERVICE;

import com.example.myapplication.CheckSup;
import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

//import com.example.myapplication.CsCheck;
import com.example.myapplication.DataAdapter;
import com.example.myapplication.DataBaseHelper;
import com.example.myapplication.GpsTracker;
import com.example.myapplication.ListOpener;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.User;
import com.example.myapplication.databinding.FragmentSlideshowBinding;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


    Button mRefreshBtn;
    Button CSstart;

    static Button button1;
    Button csstart;

    TextView Confirm_Text;

    double val;

    private static final String PRIMAY_CHANNEL_ID = "primary_notification_channel";

    private NotificationManager mNotificationManager;

    private static final int NOTIFICATION_ID=0;


    int alram_val = 0;


    double platitude =0;
    double plongtitude = 0;

    String csCheck;


    String is_pro;

    SlideshowViewModel slideshowViewModel;


    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    String SName = User.getUsername();
    private Context cscontext;

    CheckSup ccs = new CheckSup();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        cscontext = context;

    }


    public void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        double prlat =platitude;
        double prlon = plongtitude;

        outState.putDouble("prlat",prlat);
        outState.putDouble("prlon",prlon);
    }




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        requestPermission();
        createNotificationChannel();
        radibtn();


        Button tolist = (Button) root.findViewById(R.id.toList);

        tolist.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(cscontext.getApplicationContext(), ListOpener.class);
                startActivity(intent);
            }
        });

        return root;
    }

    public void requestPermission(){
        int permissionCheck = ContextCompat.checkSelfPermission(cscontext, Manifest.permission.ACCESS_FINE_LOCATION);

        if(permissionCheck == PackageManager.PERMISSION_DENIED){ //포그라운드 위치 권한 확인

            //위치 권한 요청
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        int permissionCheck2 = ContextCompat.checkSelfPermission(cscontext, Manifest.permission.ACCESS_BACKGROUND_LOCATION);

        if(permissionCheck == PackageManager.PERMISSION_DENIED){ //백그라운드 위치 권한 확인
            //위치 권한 요청
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 0);
        }
    }

    public void createNotificationChannel(){
        mNotificationManager = (NotificationManager)
                requireContext().getSystemService(NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel nfcc = new NotificationChannel(PRIMAY_CHANNEL_ID,"Test",mNotificationManager.IMPORTANCE_HIGH);
            nfcc.enableLights(true);
            nfcc.setLightColor(Color.RED);
            nfcc.enableVibration(true);
            nfcc.setDescription("NOTIFICATION");

            mNotificationManager.createNotificationChannel(nfcc);

        }

    }
    private NotificationCompat.Builder getNotificationBuilder(){
        Intent notify = new Intent(cscontext.getApplicationContext(),ListOpener.class);
         notify.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent notifyPending = PendingIntent.getActivity
                (cscontext.getApplicationContext(),NOTIFICATION_ID,notify,PendingIntent.FLAG_MUTABLE|PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(cscontext.getApplicationContext(),PRIMAY_CHANNEL_ID)
                .setContentTitle("출석알람!")
                .setContentText("출석하세요!")
                .setSmallIcon(R.drawable.clock)
                .setContentIntent(notifyPending)
                .setAutoCancel(true);
        return notifyBuilder;
    }
    public void sendNotification(){
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        mNotificationManager.notify(NOTIFICATION_ID,notifyBuilder.build());
    }




    public void radibtn() {
        View root = binding.getRoot();


        button1 = (Button) root.findViewById(R.id.refreshBtn);
        csstart = (Button) root.findViewById(R.id.CSstart);

        is_pro = User.getpro();

        pgpsresult pgp = new pgpsresult();




        if(is_pro.equals("1")){
            csstart.setEnabled(true);
            gsbtns();

        }
        else{
            if(slideshowViewModel.getval()==1)
            {sendNotification();
            slideshowViewModel.setaval(0);}
            csstart.setEnabled(false);

            stbtns();

        }


    }



    public void gsbtns(){
        button1.setVisibility(View.INVISIBLE);
        csstart.setVisibility(View.VISIBLE);

        btnchange();
    }
    public void stbtns(){
        button1.setVisibility(View.VISIBLE);
        csstart.setVisibility(View.INVISIBLE);
        btns();
    }

    // 타이머 TimeRest에 전달된 시간만큼 타이머 작동
    //onTick은 초마다 실행할 명령어
    //onfinish로 출석체크 버튼 비활성화



    //학생 좌표받기
    public class gpsresult{
        GpsTracker gpsTracker = new GpsTracker(cscontext.getApplicationContext());
        double lat = gpsTracker.getLatitude();


        double lon = gpsTracker.getLongitude();



    }
    //교수 좌표받기
    public class pgpsresult{
        GpsTracker gpsTracker = new GpsTracker(cscontext.getApplicationContext());
        double lat = gpsTracker.getPRLatitude();



        double lon = gpsTracker.getPRLongitude();



    }





    //출석체크 버튼 클릭시 발생 이벤트 / 교수의 좌표를 받아 전달하고 타이머 5분 시작
    public void btnchange(){
        View root = binding.getRoot();

        pgpsresult pgp = new pgpsresult();
        csstart.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View arg0)
            {

                slideshowViewModel.setprla(pgp.lat);
                slideshowViewModel.setprlo(pgp.lon);

                button1.setEnabled(true);
                slideshowViewModel.Timersta();
                slideshowViewModel.setaval(1);



            }

        });


    }




    //User 리스트에 db값 전달

    public List<User> userList ;

    public void initLoadDB() {

        DataAdapter mDbHelper = new DataAdapter(cscontext.getApplicationContext());
        mDbHelper.createDatabase();
        mDbHelper.open();

        // db에 있는 값들을 model을 적용해서 넣는다.
        userList = mDbHelper.getTableData();

        // db 닫기
        mDbHelper.close();
    }




    public class dist{

        double dislat = 0;
        double dislon = 0;
    }
    public void btns(){
        View root = binding.getRoot();



        mRefreshBtn = (Button) root.findViewById(R.id.refreshBtn);

        //bind listener
        mRefreshBtn.setOnClickListener(this::onClick);



        if (!checkLocationServicesStatus()) {

            showDialogForLocationServiceSetting();
        }else {

            checkRunTimePermission();
        }


        dist di = new dist();
        gpsresult gp = new gpsresult();




        Button ShowLocationButton = (Button) root.findViewById(R.id.refreshBtn);
        ShowLocationButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                gpsresult gp = new gpsresult();

                double latitude=0;
                double longitude=0;


                latitude = gp.lat;
                longitude = gp.lon;

                if((Math.abs(latitude)>=Math.abs(slideshowViewModel.getprla())))
                    di.dislat = Math.abs(latitude) - Math.abs(slideshowViewModel.getprla());
                else
                    di.dislat = Math.abs(slideshowViewModel.getprla()) - Math.abs(latitude);

                if((Math.abs(longitude)>=Math.abs(slideshowViewModel.getprlo())))
                    di.dislon = Math.abs(longitude) - Math.abs(slideshowViewModel.getprlo());
                else
                    di.dislon = Math.abs(slideshowViewModel.getprlo()) - Math.abs(longitude);

                String dilon = String.format("%.7f",di.dislon);
                String dilat = String.format("%.7f", di.dislat);


                if(Math.abs(di.dislat) <= 0.0002 && Math.abs(di.dislon) <=0.002)
                    csCheck = "O";
                else
                    csCheck = "X";


                TextView Confirm_Text = (TextView)root.findViewById(R.id.Confirm_Text);

                if(csCheck.equals("O"))
                    Confirm_Text.setText("출석 체크 완료!");



                val = slideshowViewModel.getprla();


                getVal();










            }
        });
    }

    //데이터 베이스 값 읽기


    void getVal(){
        View root = binding.getRoot();

        //Dbhelper의 읽기모드 객체를 가져와 SQLiteDatabase에 담아 사용준비
        DataBaseHelper helper = new DataBaseHelper(cscontext.getApplicationContext());
        SQLiteDatabase database = helper.getReadableDatabase();



        //Cursor라는 그릇에 목록을 담아주기
        Cursor cursor = database.rawQuery("SELECT * FROM student" ,null);

        helper.insertData(getTime(),csCheck,SName);
//        helper.insertTimeData(getTime(),"김경호");
//        helper.insertCSData(csCheck,"김경호");
//        if(cursor != null && cursor.moveToFirst())
        //목록의 개수만큼 순회하여 adapter에 있는 list배열에 add
        while(cursor.moveToNext()){
            //num 행은 가장 첫번째에 있으니 0번이 되고, name은 1번

          //  val = cursor.getString(7);

            //val = slideshowViewModel.getprla();
//            if(isInserted==true)
//

        }



        cursor.close();
        helper.close();



    }





    String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return mFormat.format(mDate);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.refreshBtn:
                break;
            default:
                break;
        }
    }




    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults);
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if (check_result) {

                //위치 값을 가져올 수 있음
                ;
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (shouldShowRequestPermissionRationale(REQUIRED_PERMISSIONS[0])
                        || shouldShowRequestPermissionRationale(REQUIRED_PERMISSIONS[1])) {
                    Toast.makeText(requireContext(), "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                } else {
                    Toast.makeText(requireContext(), "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다.", Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(cscontext.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(cscontext.getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음



        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (shouldShowRequestPermissionRationale(REQUIRED_PERMISSIONS[0])) {
                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(requireContext(), "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                requestPermissions(REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                requestPermissions(REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }

        }

    }



    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);

            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }



}