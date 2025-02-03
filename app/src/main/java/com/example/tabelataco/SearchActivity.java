package com.example.tabelataco;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {
    private TextView foodDetails;

    @SuppressLint("Range")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        foodDetails = findViewById(R.id.foodDetails);
        String foodName = getIntent().getStringExtra("food_name");

        Log.d("SearchActivity", "Received food name: " + foodName);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.initializeDatabase();
        SQLiteDatabase db = dbHelper.openDatabase();


        Cursor cursor = db.rawQuery(
                "SELECT * FROM alimentos_macros_100g WHERE nome_alimento LIKE ?", new String[]{"%" + foodName + "%"}
        );

        if (cursor != null) {
            Log.d("SearchActivity", "Number of rows returned: " + cursor.getCount());

            if (cursor.moveToFirst()) {

                int columnIndexCode = cursor.getColumnIndex("codigo");
                int columnIndexName = cursor.getColumnIndex("nome_alimento");
                int columnIndexPreparationCode = cursor.getColumnIndex("cod_preparo");
                int columnIndexPreparationMethod = cursor.getColumnIndex("forma_preparo");
                int columnIndexEnergy = cursor.getColumnIndex("enegiakcal");
                int columnIndexProtein = cursor.getColumnIndex("proteinasg");
                int columnIndexFat = cursor.getColumnIndex("lipidiosg");
                int columnIndexCarb = cursor.getColumnIndex("carboidratosg");
                int columnIndexFiber = cursor.getColumnIndex("fibrag");
                int columnIndexCategory = cursor.getColumnIndex("categoria");


                if (columnIndexCode != -1 && columnIndexName != -1 && columnIndexPreparationCode != -1 &&
                        columnIndexPreparationMethod != -1 && columnIndexEnergy != -1 && columnIndexProtein != -1 &&
                        columnIndexFat != -1 && columnIndexCarb != -1 && columnIndexFiber != -1 && columnIndexCategory != -1) {

                    StringBuilder details = new StringBuilder();

                    do {

                        details.append("Código: ").append(cursor.getString(columnIndexCode)).append("\n")
                                .append("Alimento: ").append(cursor.getString(columnIndexName)).append("\n")
                                .append("Código de Preparo: ").append(cursor.getString(columnIndexPreparationCode)).append("\n")
                                .append("Forma de Preparo: ").append(cursor.getString(columnIndexPreparationMethod)).append("\n")
                                .append("Energia (kcal): ").append(cursor.getDouble(columnIndexEnergy)).append("\n")
                                .append("Proteínas (g): ").append(cursor.getDouble(columnIndexProtein)).append("\n")
                                .append("Gorduras (g): ").append(cursor.getDouble(columnIndexFat)).append("\n")
                                .append("Carboidratos (g): ").append(cursor.getDouble(columnIndexCarb)).append("\n")
                                .append("Fibra (g): ").append(cursor.getDouble(columnIndexFiber)).append("\n")
                                .append("Categoria: ").append(cursor.getString(columnIndexCategory)).append("\n")
                                .append("\n");
                    } while (cursor.moveToNext());

                    foodDetails.setText(details.toString());
                } else {
                    Log.e("SearchActivity", "Uma ou mais colunas não encontradas no Cursor.");
                    foodDetails.setText("Erro ao buscar informações.");
                }
            } else {
                Log.d("SearchActivity", "No data found for food: " + foodName);
                foodDetails.setText("Nenhuma informação encontrada.");
            }

            cursor.close();
        } else {
            foodDetails.setText("Erro ao consultar banco de dados.");
        }
    }
}