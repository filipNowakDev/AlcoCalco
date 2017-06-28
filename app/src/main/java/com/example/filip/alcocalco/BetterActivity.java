package com.example.filip.alcocalco;

import android.content.Intent;
import android.icu.text.NumberFormat;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

//alcohol comparison

public class BetterActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_better);
        Intent intent = getIntent();
    }

    public void calculate(View view)
    {
        EditText price1 = (EditText) findViewById(R.id.Price1);
        EditText price2 = (EditText) findViewById(R.id.Price2);
        EditText volume1 = (EditText) findViewById(R.id.Volume1);
        EditText volume2 = (EditText) findViewById(R.id.Volume2);
        EditText alcv1 = (EditText) findViewById(R.id.Alc1);
        EditText alcv2 = (EditText) findViewById(R.id.Alc2);
        TextView result = (TextView) findViewById(R.id.Answer);
        double prc1 = 0;
        double prc2 = 0;
        double vl1 = 0;
        double vl2 = 0;
        double alcov1 = 0;
        double alcov2 = 0;
        boolean err = false;
        String buf;
        NumberConverter conv = new NumberConverter("", 0);
        try
        {
            buf = price1.getText().toString();
            conv.setBuffer(buf);
            buf = conv.getBuffer();
            prc1 = Double.parseDouble(buf);

            buf = price2.getText().toString();
            conv.setBuffer(buf);
            buf = conv.getBuffer();
            prc2 = Double.parseDouble(buf);

            buf = volume1.getText().toString();
            conv.setBuffer(buf);
            buf = conv.getBuffer();
            vl1 = Double.parseDouble(buf);

            buf = volume2.getText().toString();
            conv.setBuffer(buf);
            buf = conv.getBuffer();
            vl2 = Double.parseDouble(buf);

            buf = alcv1.getText().toString();
            conv.setBuffer(buf);
            buf = conv.getBuffer();
            alcov1 = Double.parseDouble(buf);

            buf = alcv2.getText().toString();
            conv.setBuffer(buf);
            buf = conv.getBuffer();
            alcov2 = Double.parseDouble(buf);

        }
        catch(Exception ex)
        {
            //Toast.makeText(getApplicationContext(), "Invalid input!", Toast.LENGTH_SHORT).show();
            result.setText(R.string.invalid_input);
            err = true;
        }
        AlcoType alc1 = new AlcoType(prc1, vl1, alcov1);
        AlcoType alc2 = new AlcoType(prc2, vl2, alcov2);

        if(!err)
        {
            if(alc1.getPpv() > alc2.getPpv())
                result.setText(R.string.second_choice);
            else if(alc2.getPpv() > alc1.getPpv())
                result.setText(R.string.first_choice);
            else
                result.setText(R.string.equal);
        }


    }
}
