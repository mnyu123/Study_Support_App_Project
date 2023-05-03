package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    TextView mTextView;
    Button mRefreshBtn;

    Button button1;
    Button csstart;

    private Button loginButton;


    TextView longitudevie;
    TextView latitudevie;
    TextView Con;
    TextView nlongitudevie;
    TextView nlatitudevie;
    TextView name;

    static TextView navheader;


    RadioGroup radioGroup;

    RadioButton GSRadioButton;
    RadioButton STRadioButton;
    double latitude;
    double longitude;
    char csCheck;

    private GpsTracker gpsTracker;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    String firstUser = User.getUsername();

import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;




public class MainActivity extends AppCompatActivity {
    Button Tocs;
    private Context cscontext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initNavigationMenu();

//        setHeader();

        final Bundle bundle = new Bundle();
        Button login_btn = findViewById(R.id.login_btn);    //로그인버튼 선언
        login_btn.setOnClickListener(this);


        radibtn();
        loginButton = findViewById(R.id.login_btn);
        loginButton.setOnClickListener(new View.OnClickListener() {     //로그인 페이지 넘김
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
    }

    private void initNavigationMenu() {
        //사이드 메뉴바
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBar.toolbar);
        binding.appBar.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawerLayout = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.menu_item2, R.id.menu_item3)
                .setOpenableLayout(drawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    public MenuItem item;
                    public boolean onNavigationItemSelected(MenuItem item) {
                        this.item = item;
                        int id = item.getItemId();

                        switch (id) {
                            case R.id.nav_home:
                                // nav_home Fragment로 이동
                                Log.d("MainActivity", "Home menu item clicked");
                                navController.navigate(R.id.nav_home);
                                break;
                            case R.id.menu_item2:
                                // menu_item2 Fragment로 이동
                                Log.d("MainActivity", "Gallery menu item clicked");
                                navController.navigate(R.id.menu_item2);
                                break;
                            case R.id.menu_item3:
                                // menu_item3 Fragment로 이동
                                navController.navigate(R.id.menu_item3);
                                break;
                            case R.id.menu_item4:
                                // menu_item4 Fragment로 이동
                                navController.navigate(R.id.menu_item4);
                                break;
                        }

                        // Navigation Drawer 닫기
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                }
        );
        View headerView = navigationView.getHeaderView(0);
        TextView headerTextView = headerView.findViewById(R.id.header_text);
        headerTextView.setText(firstUser);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
   /* public void timer(){
        timeCountSettingLV.setVisibility(View.GONE);
        timeCountLV.setVisibility(View.VISIBLE);

        hourTV.setText(hourET.getText().toString());
        minuteTV.setText(minuteET.getText().toString());
        secondTV.setText(secondET.getText().toString());

        hour = Integer.parseInt(hourET.getText().toString());
        minute = Integer.parseInt(minuteET.getText().toString());
        second = Integer.parseInt(secondET.getText().toString());

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                // 반복실행할 구문

                // 0초 이상이면
                if(second != 0) {
                    //1초씩 감소
                    second--;

                    // 0분 이상이면
                } else if(minute != 0) {
                    // 1분 = 60초
                    second = 60;
                    second--;
                    minute--;

                    // 0시간 이상이면
                } else if(hour != 0) {
                    // 1시간 = 60분
                    second = 60;
                    minute = 60;
                    second--;
                    minute--;
                    hour--;
                }

                //시, 분, 초가 10이하(한자리수) 라면
                // 숫자 앞에 0을 붙인다 ( 8 -> 08 )
                if(second <= 9){
                    secondTV.setText("0" + second);
                } else {
                    secondTV.setText(Integer.toString(second));
                }

                if(minute <= 9){
                    minuteTV.setText("0" + minute);
                } else {
                    minuteTV.setText(Integer.toString(minute));
                }

                if(hour <= 9){
                    hourTV.setText("0" + hour);
                } else {
                    hourTV.setText(Integer.toString(hour));
                }

                // 시분초가 다 0이라면 toast를 띄우고 타이머를 종료한다..
                if(hour == 0 && minute == 0 && second == 0) {
                    timer.cancel();//타이머 종료
                    finishTV.setText("타이머가 종료되었습니다.");
                }
            }
        };
    }*/

    // 마찬가지 삭제 가능한 주석이면 삭제 부탁드립니다 가독성이 떨어집니다
    
    public void radibtn() {
        GSRadioButton = (RadioButton) findViewById(R.id.GSradibtn);
        STRadioButton = (RadioButton) findViewById(R.id.STDradibtn);
       /* GSRadioButton.setOnClickListener(radioButtonClickListener);
        STRadioButton.setOnClickListener(radioButtonClickListener); */
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        button1 = (Button) findViewById(R.id.refreshBtn);
        csstart = (Button) findViewById(R.id.CSstart);


    }
   /* View.OnClickListener radioButtonClickListener = new RadioButton.OnClickListener() {
        @Override

        }
    }; */


    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if (i == R.id.STDradibtn) {
                stbtns();
            } else if (i == R.id.GSradibtn) {
                gsbtns();
            }
        }
    };


    public void gsbtns() {
        button1.setVisibility(View.INVISIBLE);
        csstart.setVisibility(View.VISIBLE);
        btnchange();
    }

    public void stbtns() {
        button1.setVisibility(View.VISIBLE);
        csstart.setVisibility(View.INVISIBLE);
        btns();
    }


    public void btnchange() {

        Button Btnena = (Button) findViewById(R.id.CSstart);
        Btnena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                button1.setEnabled(true);
            }

        });
    }

    public List<User> mUserList;

    private void initLoadDB() {

        DataAdapter mDbHelper = new DataAdapter(getApplicationContext());
        mDbHelper.createDatabase();
        mDbHelper.open();

        // db에 있는 값들을 model을 적용해서 넣는다.
        mUserList = mDbHelper.getTableData();

        // db 닫기
        mDbHelper.close();
    }


    int val;

    void getVal() {

        //Dbhelper의 읽기모드 객체를 가져와 SQLiteDatabase에 담아 사용준비
        DataBaseHelper helper = new DataBaseHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();


        //Cursor라는 그릇에 목록을 담아주기
        Cursor cursor = database.rawQuery("SELECT Student_id FROM student where Username = '김경호'", null);


//        if(cursor != null && cursor.moveToFirst())
        //목록의 개수만큼 순회하여 adapter에 있는 list배열에 add
        while (cursor.moveToNext()) {
            //num 행은 가장 첫번째에 있으니 0번이 되고, name은 1번
            val = cursor.getInt(0);
        }
        name = (TextView) findViewById(R.id.name);
        name.setText(String.valueOf(val));
        cursor.close();
        helper.close();


    }


    public class prime{

        double platitude =0;
        double plongtitude = 0;
    }

    public class dist {

        double dislat = 0;
        double dislon = 0;
    }

    public void btns() {
        mTextView = (TextView) findViewById(R.id.textView);

        mRefreshBtn = (Button) findViewById(R.id.refreshBtn);

        //bind listener
        mRefreshBtn.setOnClickListener(this::onClick);


        longitudevie = (TextView) findViewById(R.id.longitudevie);
        latitudevie = (TextView) findViewById(R.id.latitudevie);

        Con = (TextView) findViewById(R.id.Con);

        nlongitudevie = (TextView) findViewById(R.id.nlongitudevie);
        nlatitudevie = (TextView) findViewById(R.id.nlatitudevie);

        if (!checkLocationServicesStatus()) {

            showDialogForLocationServiceSetting();
        } else {

            checkRunTimePermission();
        }


        prime pr = new prime();
        dist di = new dist();


        pr.platitude = 35.9684;
        pr.plongtitude = 126.9581;


        TextView textview_latitude = (TextView) findViewById(R.id.latitudevie);
        TextView textview_longitude = (TextView) findViewById(R.id.longitudevie);


        Button ShowLocationButton = (Button) findViewById(R.id.refreshBtn);
        ShowLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {


                gpsTracker = new GpsTracker(MainActivity.this);

                latitude = gpsTracker.getLatitude();
                longitude = gpsTracker.getLongitude();

                if ((Math.abs(latitude) >= Math.abs(pr.platitude)))
                    di.dislat = Math.abs(latitude) - Math.abs(pr.platitude);
                else
                    di.dislat = Math.abs(pr.platitude) - Math.abs(latitude);

                if ((Math.abs(longitude) >= Math.abs(pr.plongtitude)))
                    di.dislon = Math.abs(longitude) - Math.abs(pr.plongtitude);
                else
                    di.dislon = Math.abs(pr.plongtitude) - Math.abs(longitude);

                String dilon = String.format("%.7f", di.dislon);
                String dilat = String.format("%.7f", di.dislat);


                mTextView.setText(getTime());
                textview_latitude.setText(String.valueOf(latitude));
                textview_longitude.setText(String.valueOf(longitude));


                nlongitudevie.setText(dilon);
                nlatitudevie.setText(dilat);

                if (Math.abs(di.dislat) <= 0.0002 && Math.abs(di.dislon) <= 0.002)
                    csCheck = 'O';
                else
                    csCheck = 'X';


                Con.setText(String.valueOf(csCheck));
                initLoadDB();
                getVal();

            }
        });
    }


    String getTime() {
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return mFormat.format(mDate);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refreshBtn:
                mTextView.setText(getTime());
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

            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();


                } else {

                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission() {

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }


    public String getCurrentAddress(double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString() + "\n";

    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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


        Tocs = (Button)findViewById(R.id.ToCs);

        Tocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CsCheck.class);
                startActivity(intent);
                //((CsCheck)cscontext).CsCheck();

            }
        });
        //bind view

    }




                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    //id pw입력후 메세지 입력
    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void setHeader() {
        navheader = (TextView)findViewById(R.id.header_text);
        navheader.setText("aaa");
    }

}








