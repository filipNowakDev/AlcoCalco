package com.example.filip.alcocalco;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PromileCalco extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promile_calco);
    }

    private List drinkList = new ArrayList<AlcoType>();


    public void addDrink()
    {
        AlcoType tempAlco = new AlcoType();
        EditText volumeEdit = (EditText) findViewById(R.id.volumeEdit);
        EditText percentsEdit = (EditText) findViewById(R.id.percentsEdit);
        NumberConverter converter = new NumberConverter();
        String buffer;
        double vol = 0;
        double perc = 0;
        boolean err = false;
        while (!err)
        {
            try
            {
                err = false;
                converter.setBuffer(volumeEdit.getText().toString());
                vol = Double.parseDouble(converter.getBuffer());

                converter.setBuffer(percentsEdit.getText().toString());
                perc = Double.parseDouble(converter.getBuffer());


            }
            catch(Exception ex)
            {
                Toast.makeText(getApplicationContext(),"Invalid Input.", Toast.LENGTH_SHORT).show();
                err = true;
            }
        }
        tempAlco.setGrams(vol, perc);
        drinkList.add(tempAlco);
    }


}
