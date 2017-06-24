package com.example.filip.alcocalco;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void calculate(View view)
    {
        EditText price1 = (EditText) findViewById(R.id.Price1);
        EditText price2 = (EditText) findViewById(R.id.Price2);
        EditText volume1 = (EditText) findViewById(R.id.Volume1);
        EditText volume2 = (EditText) findViewById(R.id.Volume2);

        AlcoType alc1 = new AlcoType(Double.parseDouble(price1.getText().toString()), Double.parseDouble(volume1.getText().toString()));
        AlcoType alc2 = new AlcoType(Double.parseDouble(price2.getText().toString()), Double.parseDouble(volume2.getText().toString()));
        TextView result = (TextView) findViewById(R.id.Answer);
        String answer;
        if(alc1.getPpv() < alc2.getPpv())
            result.setText("Second is better!");
        else if(alc2.getPpv() < alc1.getPpv())
            result.setText("First is better!");
        else
            result.setText("Equal");


    }
}

