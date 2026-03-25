package com.example.myo_2026_hafta06_01;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    SQLiteDatabase db;
    ArrayList<Employee> liste= new ArrayList<>();
    ListView lv;
    EditText etId,etAd,etSoyad,etEmail;



    public void dataBaseAC(){
        db = openOrCreateDatabase("mydb.db", MODE_PRIVATE, null);
    }
    public void dataBaseKAPA(){
        db.close();
    }
    public void ListViewGuncelle() {
        dataBaseAC();
        liste.clear();

        db.execSQL("Create Table if not exists " +
                "Employee( id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "name VARCHAR, " +
                "surname VARCHAR, " +
                "email VARCHAR)");

        Cursor c = db.rawQuery("Select * from Employee", null);

        c.moveToFirst();
        if (c != null) {

            do {
                int id = c.getInt(0);
                String name = c.getString(1);
                String surname = c.getString(2);
                String email = c.getString(3);

                Log.d("Result", id + " " + name + " " + surname + " " + email);
                Employee epl = new Employee(id, name, surname, email);
                liste.add(epl);

            } while (c.moveToNext());

            ArrayAdapter<Employee> adapter = new ArrayAdapter<Employee>(this,
                    android.R.layout.simple_list_item_1,
                    liste);
            lv.setAdapter(adapter);
dataBaseKAPA();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dataBaseAC();
        lv=(ListView)findViewById(R.id.mylistView);
        etAd=(EditText)findViewById(R.id.etAd);
        etSoyad=(EditText)findViewById(R.id.etSoyad);
        etEmail=(EditText)findViewById(R.id.etEmail);
        etId=(EditText)findViewById(R.id.etId);


            ListViewGuncelle();
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dataBaseAC();
                    Employee e=(Employee) parent.getItemAtPosition(position);
                    Cursor c = db.rawQuery("Select * from Employee where id = " + e.getId() , null);
                    if(c!=null) {
                        c.moveToFirst();
                        etId.setText(""+c.getInt(0));
                        etAd.setText(""+c.getString(1));
                        etSoyad.setText(""+c.getString(2));
                        etEmail.setText(""+c.getString(3));
                    }
                    Toast.makeText(getApplicationContext(),"" + e.getId(),Toast.LENGTH_LONG).show();
                    dataBaseKAPA();
                }
            });
        dataBaseKAPA();
        }


     public void Ekle(View v){
     dataBaseAC();

         db.execSQL("Insert Into Employee(name,surname,email) " +
                 "values('" + etAd.getText().toString() + "','" +
                     etSoyad.getText().toString() + "','" +
                    etEmail.getText().toString() + "')");
         ListViewGuncelle();
dataBaseKAPA();

     }

     public void Sil(View v){

    }

     public void Guncelle(View v){

         dataBaseAC();
         db.execSQL("Update Employee set " +
                 "name ='" + etAd.getText().toString() + "'," +
                 "surname ='" + etSoyad.getText().toString() + "'," +
                 "email ='" + etEmail.getText().toString() + "'"+
                 "where id =" + etId.getText().toString());
         ListViewGuncelle();
         dataBaseKAPA();

     }


    }
