package com.example.filip.alcocalco;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
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

//calculates the alcohol blood content

public class PromileCalco extends Activity
{


    ListView list;
    AlcoAdapter adapter;
    private List drinkList; //list containing drinks
    private double gramsSum = 0;
    private AlcoDbAdapter db; //database
    private Cursor alcoCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promile_calco);


        list = (ListView) findViewById(R.id.drinkListView);
        fillListViewData();

        //adapter = new AlcoAdapter(getApplicationContext(), R.layout.row_layout);
        //list.setAdapter(adapter);
        //db = new AlcoDbAdapter(getApplicationContext());
        //db.open();
        //long count = 0;
        //while(db.getAlco(count) != null)
        //{
        //    drinkList.add(db.getAlco(count));
        //    count++;
        //}


        AdapterView.OnItemClickListener tapListener = new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Toast.makeText(getApplicationContext(), R.string.hold, Toast.LENGTH_SHORT).show();
            }
        };

        AdapterView.OnItemLongClickListener clickListener = new AdapterView.OnItemLongClickListener() //list click opens a dialog
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id)
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
                final Button deleteButton = (Button) editDialog.findViewById(R.id.dialogDelete);
                final long alcoId = temp.getId();
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
                        db.updateAlco(alcoId, newDrink);
                        editDialog.dismiss();
                    }
                });

                deleteButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        adapter.remove(pos);
                        drinkList.remove(pos);
                        db.deleteAlco(alcoId);
                        editDialog.dismiss();
                    }
                });


                editDialog.show();
                return false;
            }
        };

        list.setOnItemClickListener(tapListener);
        list.setOnItemLongClickListener(clickListener);


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
            db.insertAlco(tempAlco);

        }
    }

    private void fillListViewData()
    {
        db = new AlcoDbAdapter(getApplicationContext());
        db.open();
        adapter = new AlcoAdapter(this, R.layout.row_layout);
        list.setAdapter(adapter);
        getAllDrinks();
    }

    private void getAllDrinks()
    {
        drinkList = new ArrayList<AlcoType>();
        alcoCursor = getAllEntriesFromDb();
        updateAlcoList();
    }

    private Cursor getAllEntriesFromDb()
    {
        alcoCursor = db.getAllAlcos();
        if (alcoCursor != null)
        {
            startManagingCursor(alcoCursor);
            alcoCursor.moveToFirst();
        }
        return alcoCursor;
    }

    private void updateAlcoList()
    {
        if (alcoCursor != null && alcoCursor.moveToFirst())
        {
            do
            {
                long id = alcoCursor.getLong(db.ID_COLUMN);
                double volume = alcoCursor.getDouble(db.VOLUME_COLUMN);
                double percents = alcoCursor.getDouble(db.PERCENTS_COLUMN);
                drinkList.add(new AlcoType(id, 0, volume, percents * 100));
                adapter.add(new AlcoType(id, 0, volume, percents * 100));
            } while (alcoCursor.moveToNext());
        }
    }

    @Override
    protected void onDestroy()
    {
        if (db != null)
            db.close();
        super.onDestroy();
    }



}
