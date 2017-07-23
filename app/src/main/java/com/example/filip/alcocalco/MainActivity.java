package com.example.filip.alcocalco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//main menu


public class MainActivity extends AppCompatActivity
{

    ListView list;
    private String[] options = {"AlcoCalco", "PromileCalco", "AlcoMap", "Credits", "Quit"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options); //simple array adapter for menu list
        list = (ListView) findViewById(R.id.menu);
        list.setAdapter(adapter);                                                                               //sets the adapter in list
        AdapterView.OnItemClickListener clickHandler = new AdapterView.OnItemClickListener()                    //listens to which item is clicked
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                if (position == 0)
                {
                    Intent intent = new Intent(MainActivity.this, BetterActivity.class);
                    startActivity(intent);
                }
                if (position == 1)
                {
                    Intent intent = new Intent(MainActivity.this, PromileCalco.class);
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

