package com.example.filip.alcocalco;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

//calculates the alcohol blood content

public class PromileCalco extends AppCompatActivity
{
    private final int MALE = 0;
    private final int FEMALE = 1;


    ListView list;
    AlcoAdapter adapter; //adapter containing drinks/alcos
    private AlcoDbAdapter db; //database
    private Cursor alcoCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promile_calco);


        list = (ListView) findViewById(R.id.drinkListView);
        fillListViewData();


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
                        db.deleteAlco(alcoId);
                        editDialog.dismiss();
                        onDestroy();//WHY?!?!
                        onCreate(null);
                        adapter.remove(pos);
                        db.deleteAlco(alcoId);
                    }
                });


                editDialog.show();
                return false;
            }
        };

        list.setOnItemClickListener(tapListener);
        list.setOnItemLongClickListener(clickListener);


    }

    public void clearAlcos(View view)
    {
        for (int i = 0; i < 2; i++) //no idea why
        {
            long id;
            AlcoType tmp;
            EditText time = (EditText) findViewById(R.id.timeEdit);
            TextView res = (TextView) findViewById(R.id.promileResult);
            TextView temp = (TextView) findViewById(R.id.volumeEdit);
            temp.setText("");
            temp = (TextView) findViewById(R.id.percentsEdit);
            temp.setText("");
            time.setText("");
            res.setText(R.string.result);
            while (adapter.getCount() > 0)
            {
                tmp = (AlcoType) adapter.getItem(0);
                id = tmp.getId();
                adapter.remove(0);
                db.deleteAlco(id);
            }
            onDestroy();
            onCreate(null);
        }
    }

    public void addDrink(View view) //adds drink to list
    {

        AlcoType tempAlco = new AlcoType();
        EditText volumeEdit = (EditText) findViewById(R.id.volumeEdit);
        EditText percentsEdit = (EditText) findViewById(R.id.percentsEdit);
        NumberConverter converter = new NumberConverter();
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
            adapter.add(tempAlco);
            db.insertAlco(tempAlco);

        }
    }

    public void calculate(View view)
    {
        double gramsSum = 0;
        int startTime = 0;
        int currentTime = 0;
        TextView resultView = (TextView) findViewById(R.id.promileResult);
        AlcoType temp = null;
        for (int i = 0; i < adapter.getCount(); i++)
        {
            temp = (AlcoType) adapter.getItem(i);
            gramsSum += temp.getGrams();
        }
        DecimalFormat format = new DecimalFormat("##.##");
        resultView.setText(format.format(calculatePromiles(gramsSum, MALE, 19, 87, 180, 60 * 0)) + "promile");
    }

    private double calculatePromiles(double grams, int gender, int age, double bodyweight, double height, double timeSpent) //age in years, bodyweight in kg, height in cm, time in minutes
    {
        double modifier = 0;
        double promiles = 0;
        if (gender == MALE)
        {
            modifier = 0.7;
            promiles = ((grams - (timeSpent / 60 * 0.1 * bodyweight)) / (modifier * bodyweight));
        } else if (gender == FEMALE)
        {
            modifier = 0.6;
            promiles = ((grams - (timeSpent / 60 * (0.1 * bodyweight - 1))) / (modifier * bodyweight));
        }
        if (promiles > 0)
            return promiles;
        else
            return 0;
    }


    //database methods

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
