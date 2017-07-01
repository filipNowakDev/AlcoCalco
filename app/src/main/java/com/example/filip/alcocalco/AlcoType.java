package com.example.filip.alcocalco;

/**
 * Created by filip on 6/23/17.
 */

public class AlcoType
{
    private double pricePerVol = 0;
    private double grams = 0;
    private double volume = 0;
    private double percents = 0;
    private final double density = 0.79; //g/cm3

    public AlcoType(){}
    public AlcoType(double price, double vol, double alc)
    {
        volume = vol;
        percents = alc/100;
        grams = volume * percents * density;
        pricePerVol = price / (vol * alc);
    }

    public double getPpv()
    {
        return pricePerVol;
    }

    public void setGrams(double Volume, double Percents)
    {
        percents = Percents/100;
        volume = Volume;
        grams = volume * percents * density;
    }
    public double getGrams()
    {
        return grams;
    }
}
