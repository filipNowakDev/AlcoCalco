package com.example.filip.alcocalco;

/**
 * Created by filip on 6/23/17.
 */

public class AlcoType
{
    private double pricePerVol;

    public AlcoType(double price, double vol, double alc)
    {
        pricePerVol = price / (vol * alc);
    }

    public double getPpv()
    {
        return pricePerVol;
    }

}
