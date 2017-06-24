package com.example.filip.alcocalco;

/**
 * Created by filip on 6/23/17.
 */

public class AlcoType
{
    private double pricePerVol;

    public AlcoType(double volume, double price)
    {
        pricePerVol = price / volume;
    }

    public double getPpv()
    {
        return pricePerVol;
    }

}
