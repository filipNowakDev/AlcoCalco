package com.example.filip.alcocalco;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PromileCalco extends AppCompatActivity
{


    ListView list;
    AlcoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promile_calco);

        list = (ListView) findViewById(R.id.drinkListView);
        adapter = new AlcoAdapter(getApplicationContext(), R.layout.row_layout);
        list.setAdapter(adapter);
    }

    private List drinkList = new ArrayList<AlcoType>();
    private double gramsSum = 0;

    public void addDrink(View view)
    {
        AlcoType tempAlco = new AlcoType();
        EditText volumeEdit = (EditText) findViewById(R.id.volumeEdit);
        EditText percentsEdit = (EditText) findViewById(R.id.percentsEdit);
        NumberConverter converter = new NumberConverter();
        String buffer;
        double vol = 0;
        double perc = 0;
        boolean err = false;
        try
        {
            err = false;
            converter.setBuffer(volumeEdit.getText().toString());
            vol = Double.parseDouble(converter.getBuffer());

            converter.setBuffer(percentsEdit.getText().toString());
            perc = Double.parseDouble(converter.getBuffer());


        } catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), "Invalid Input!", Toast.LENGTH_SHORT).show();
            err = true;
        }
        if (!err)
        {
            tempAlco.setGrams(vol, perc);
            drinkList.add(tempAlco);
            adapter.add(tempAlco);
            gramsSum += tempAlco.getGrams();

        }
    }


}
