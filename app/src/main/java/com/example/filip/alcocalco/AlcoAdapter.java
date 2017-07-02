package com.example.filip.alcocalco;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by filip on 7/2/17.
 */

public class AlcoAdapter extends ArrayAdapter
{

    List list = new ArrayList<>();
    public AlcoAdapter(@NonNull Context context, @LayoutRes int resource)
    {
        super(context, resource);
    }

    static class DataHandler
    {
        TextView volView;
        TextView percView;
        TextView gramsView;
    }

    @Override
    public void add(@Nullable Object object)
    {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount()
    {
        return this.list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position)
    {
        return this.list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View row = convertView;
        DataHandler handler;
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row_layout, parent, false);
            handler = new DataHandler();
            handler.volView = (TextView) row.findViewById(R.id.rowml);
            handler.percView = (TextView) row.findViewById(R.id.rowPercents);
            handler.gramsView = (TextView) row.findViewById(R.id.rowLabel);
            row.setTag(handler);
        }

        else
        {
            handler = (DataHandler) row.getTag();
        }

        AlcoType dataProvider = (AlcoType) this.getItem(position);
        handler.volView.setText(Double.toString(dataProvider.getVolume()));
        handler.percView.setText(Double.toString(dataProvider.getPercents()*100));
        handler.gramsView.setText(Double.toString(dataProvider.getGrams()));

        return row;
    }
}
