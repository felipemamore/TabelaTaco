package com.example.tabelataco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "taco_converted.sqlite";
    private static final int DATABASE_VERSION = 1;

    Context context;
    String databasePath;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.databasePath = context.getDatabasePath(DATABASE_NAME).getPath();

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void initializeDatabase(){
        if (!checkDatabase()){
            copyDatabase();
        }
    }
    private boolean checkDatabase() {
        File dbFile = new File(databasePath);
        return dbFile.exists();
    }

    private void copyDatabase() {
        try{
            InputStream inputStream = context.getAssets().open(DATABASE_NAME);
            OutputStream outputStream = new FileOutputStream(databasePath);

            byte[] buffer = new byte[1024];
            int lenght;

            while ((lenght = inputStream.read(buffer))>0){
                outputStream.write(buffer,0, lenght);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e){
            Log.e("DatabaseHelper", "Erro ao copiar banco de dados:"+e.getMessage());
        }

    }
    public SQLiteDatabase openDatabase(){
        return SQLiteDatabase.openDatabase(databasePath,null,
                SQLiteDatabase.OPEN_READWRITE);
    }


}
