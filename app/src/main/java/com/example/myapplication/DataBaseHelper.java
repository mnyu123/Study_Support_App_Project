package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper{
    private final static String TAG = "DataBaseHelper"; // Logcat에 출력할 태그이름
    // database 의 파일 경로
    private static String DB_PATH = "";
    private static String DB_NAME = "student_db.db";
    private SQLiteDatabase mDataBase;
    private Context mContext;

    public static  final String TABLE_NAME = "student";

    public static final String Col1 = "Time";

    public static final String Col2 = "Cs";



    public DataBaseHelper(Context context) {
        super(context,DB_NAME,null,1);


        if(android.os.Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }


        this.mContext = context;

    }

    public void createDataBase() throws IOException
    {
        //데이터베이스가 없으면 asset폴더에서 복사해온다.
        boolean mDataBaseExist = checkDataBase();
        if(!mDataBaseExist)
        {
            this.getReadableDatabase();
            this.close();
            try
            {
                //Copy the database from assests
                copyDataBase();
                Log.e(TAG, "createDatabase database created");
            }
            catch (IOException mIOException)
            {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    ///data/data/your package/databases/Da Name <-이 경로에서 데이터베이스가 존재하는지 확인한다
    private boolean checkDataBase()
    {
        File dbFile = new File(DB_PATH + DB_NAME);
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();
    }

    //assets폴더에서 데이터베이스를 복사한다.
    private void copyDataBase() throws IOException
    {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer))>0)
        {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    //데이터베이스를 열어서 쿼리를 쓸수있게만든다.
    public boolean openDataBase() throws SQLException
    {
        String mPath = DB_PATH + DB_NAME;
        //Log.v("mPath", mPath);
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return mDataBase != null;
    }

    @Override
    public synchronized void close()
    {
        if(mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

//    public void insertData(String time, String cscheck){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(Col1,time);
//        contentValues.put(Col2,cscheck);
//        long result = db.insert(TABLE_NAME,"Time", contentValues);
//        if(result == -1)
//            Toast.makeText(mContext,"출석값 입력 실패",Toast.LENGTH_LONG).show();
//       else
//            Toast.makeText(mContext,"출석값 입력 완료",Toast.LENGTH_LONG).show();
//
//    }
//    public void insertTimeData(String time, String Username){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(Col1,time);
//        db.update(TABLE_NAME,contentValues,"Username=?",new String[]{Username});
//
//    }
//    public void insertCSData(String CsCheck, String Username){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(Col2,CsCheck);
//        db.update(TABLE_NAME,contentValues,"Username=?",new String[]{Username});
//
//    }
    public void insertData(String time,String cscheck, String Username){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col1,time);
        contentValues.put(Col2,cscheck);
        db.update(TABLE_NAME,contentValues,"Username=?",new String[]{Username});

    }

    public void DoCopyDB()throws IOException{
        this.getReadableDatabase();
        this.close();
        try
        {
            //Copy the database from assests
            copyDataBase();
            Log.e(TAG, "createDatabase database created");
        }
        catch (IOException mIOException)
        {
            throw new Error("ErrorCopyingDataBase");
        }
    }


//    public void UpdateData(String time, String cscheck){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(Col1,time);
//        contentValues.put(Col2,cscheck);
//        long result = db.insert(TABLE_NAME,null, contentValues);
//        if(result == -1)
//            Toast.makeText(mContext,"출석값 입력 실패",Toast.LENGTH_LONG).show();
//        else
//            Toast.makeText(mContext,"출석값 입력 완료",Toast.LENGTH_LONG).show();
//    }




}

