package com.example.filip.alcocalco;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PromileCalco extends Activity
{


    ListView list;
    AlcoAdapter adapter;
    private List drinkList = new ArrayList<AlcoType>();
    private double gramsSum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promile_calco);

        list = (ListView) findViewById(R.id.drinkListView);
        adapter = new AlcoAdapter(getApplicationContext(), R.layout.row_layout);
        list.setAdapter(adapter);


        AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() //list click opens a dialog
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {

                DecimalFormat format = new DecimalFormat("##.##");
                final Dialog editDialog = new Dialog(PromileCalco.this);
                editDialog.setContentView(R.layout.alco_edit_dialog);
                final EditText mlField = editDialog.findViewById(R.id.dialogMlEdit);
                final EditText percField = editDialog.findViewById(R.id.dialogPercentsEdit);
                final int pos = position;
                final AlcoType temp = (AlcoType) adapter.getItem(position);
                mlField.setText(format.format(temp.getVolume()));
                percField.setText(format.format(temp.getPercents() * 100));

                final Button okButton = (Button) editDialog.findViewById(R.id.dialogOk);

                okButton.setOnClickListener(new View.OnClickListener()
                {
                    double newVolume;
                    double newPercents;
                    @Override
                    public void onClick(View view)
                    {
                        try
                        {
                            NumberConverter conv = new NumberConverter();
                            conv.setBuffer(mlField.getText().toString());
                            newVolume = Double.parseDouble(conv.getBuffer());

                            conv.setBuffer(percField.getText().toString());
                            newPercents = Double.parseDouble(conv.getBuffer());

                        } catch (Exception ex)
                        {
                            Toast.makeText(getApplicationContext(), R.string.invalid_input, Toast.LENGTH_LONG).show();
                        }

                        AlcoType newDrink = new AlcoType(0, newVolume, newPercents);
                        adapter.remove(pos);
                        adapter.add(pos, newDrink);
                        editDialog.dismiss();
                    }
                });


                editDialog.show();
            }
        };
        list.setOnItemClickListener(clickListener);

    }

    public void addDrink(View view) //adds drink to list
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
