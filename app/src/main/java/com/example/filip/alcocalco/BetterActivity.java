package com.example.filip.alcocalco;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

        try
        {
            prc1 = Double.parseDouble(price1.getText().toString());
            prc2 = Double.parseDouble(price2.getText().toString());
            vl1 = Double.parseDouble(volume1.getText().toString());
            vl2 = Double.parseDouble(volume2.getText().toString());
            alcov1 = Double.parseDouble(alcv1.getText().toString());
            alcov2 = Double.parseDouble(alcv2.getText().toString());

        }
        catch(Exception ex)
        {
            //Toast.makeText(getApplicationContext(), "Invalid input!", Toast.LENGTH_SHORT).show();
            result.setText("Invalid input!");
            err = true;
        }
        AlcoType alc1 = new AlcoType(prc1, vl1, alcov1);
        AlcoType alc2 = new AlcoType(prc2, vl2, alcov2);

        if(!err)
        {
            if(alc1.getPpv() > alc2.getPpv())
                result.setText("Second is better!");
            else if(alc2.getPpv() > alc1.getPpv())
                result.setText("First is better!");
            else
                result.setText("Equal");
        }


    }
}
