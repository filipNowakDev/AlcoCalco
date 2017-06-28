package com.example.filip.alcocalco;

/**
 * Created by filip on 6/28/17.
 */

public class NumberConverter
{
    private String buf;
    final private String [] locale_types = {"PL", "US"};
    private int locale_current;
    NumberConverter()
    {
        buf = "";
        locale_current = 0;
    }
    NumberConverter(String s, int loc)
    {
        locale_current = loc;
        buf = s;
    }

    private void convert(String s) //convert the string
    {
        char tochange = 0;
        char dest = 0;

        if (locale_types[locale_current] == "PL")
        {
            tochange = ',';
            dest = '.';
        }
        else if (locale_types[locale_current] == "US")
        {
            tochange = '.';
            dest = ',';
        }
        for(int i = 0; i < s.length(); i++)
        {
            if (s.charAt(i) == tochange)
            {
                buf = s.substring(0, i) + dest + s.substring(i+1);

            }
        }

    }

    public String getBuffer() //get saved string
    {
        convert(buf);
        return buf;
    }

    public String getLocale() //get current locale settings
    {
        return locale_types[locale_current];
    }

    public void setBuffer(String s) //
    {
        buf = s;
    }

    public void setLocale(int i)
    {
        locale_current = i;
    }


}
