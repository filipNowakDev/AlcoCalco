package com.example.filip.alcocalco;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PromileCalco extends Activity
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

        AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {

                //Intent intent = new Intent(getApplicationContext(), AlcoEditDialogActivity.class);
                //startActivity(intent);
                final Dialog editDialog = new Dialog(PromileCalco.this);
                editDialog.setContentView(R.layout.alco_edit_dialog);

                Button cancelButton = (Button) findViewById(R.id.dialogCancel);
                cancelButton.setOnClickListener(new View.OnClickListener() //TODO REPAIR IT
                {
                    @Override
                    public void onClick(View view)
                    {
                        editDialog.dismiss();
                    }
                });

                Button okButton = (Button) findViewById(R.id.dialogOk);

                editDialog.show();
            }
        };
        list.setOnItemClickListener(clickListener);
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
