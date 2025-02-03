package com.example.tabelataco;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList <String> foodlist;
    ArrayAdapter <String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        foodlist = new ArrayList<>();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.initializeDatabase();
        SQLiteDatabase db = dbHelper.openDatabase();

        Cursor cursor = db.rawQuery("SELECT DISTINCT nome_alimento FROM alimentos_macros_100g"
                , null);
        while(cursor.moveToNext()){
            foodlist.add(cursor.getString(0));
        }
        cursor.close();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foodlist);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) -> {
            String selectedFood = foodlist.get(position);
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            intent.putExtra("food_name", selectedFood);
            startActivity(intent);
        });






    }
}