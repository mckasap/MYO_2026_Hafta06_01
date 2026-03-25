package com.example.myo_2026_hafta06_01;

import android.database.Cursor;
import android.database.sqlite.*;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    ArrayList<Employee> liste= new ArrayList<>();
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        db = openOrCreateDatabase("mydb.db", MODE_PRIVATE, null);

        lv=(ListView)findViewById(R.id.listView);
        liste.clear();


        db.execSQL("Create Table if not exists " +
                "Employee( id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "name VARCHAR, " +
                "surname VARCHAR, " +
                "email VARCHAR)");

        db.execSQL("Insert into Employee(name,surname,email) " +
                "values('Mustafa','Kasapbasi','test@test.com')");


        Cursor c = db.rawQuery("Select * from Employee", null);

        c.moveToFirst();
        if (c != null) {

            do{
                int id = c.getInt(0);
                String name = c.getString(1);
                String surname = c.getString(2);
                String email = c.getString(3);

                Log.d("Result", id + " " + name + " " + surname + " " + email);
                Employee epl = new Employee(id,name,surname,email);
                liste.add(epl);

            }while(c.moveToNext());
            db.close();
            ArrayAdapter<Employee>  adapter = new ArrayAdapter<Employee>(this,
                    android.R.layout.simple_list_item_1,
                    liste);
            lv.setAdapter(adapter);
 lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
     @Override
     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         Employee e=(Employee) parent.getItemAtPosition(position);
         Toast.makeText(getApplicationContext(),"" + e.getId(),Toast.LENGTH_LONG).show();
     }
 });

        }
    }

}