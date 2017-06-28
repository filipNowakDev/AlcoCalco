package com.example.filip.alcocalco;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import static android.R.attr.id;
import static android.R.id.list;

//main menu

public class MainActivity extends AppCompatActivity
{

    private String[] options = {"AlcoCalco", "PromileCalco", "AlcoMap", "Credits", "Quit"};


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options);
        ListView list = (ListView) findViewById(R.id.menu);
        list.setAdapter(adapter);
        AdapterView.OnItemClickListener clickHandler = new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                if (position == 0)
                {
                    Intent intent = new Intent(MainActivity.this, BetterActivity.class);
                    startActivity(intent);
                }

                if (position == 4)
                {
                    finish();
                    System.exit(0);
                }
            }
        };
        list.setOnItemClickListener(clickHandler);


    }


}

