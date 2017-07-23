package com.example.filip.alcocalco;

/**
 * Created by filip on 6/23/17.
 */

//Class contains necessary data about alcohol type used in other activities
//as well as setters and getters

public class AlcoType
{
    private final double density = 0.79; //g/cm3
    private double pricePerVol = 0;
    private double grams = 0;
    private double volume = 0;
    private double percents = 0;
    private long id = 0;

    public AlcoType(){}
    public AlcoType(double price, double vol, double alc)
    {
        setGrams(vol, alc);
        pricePerVol = price / (vol * alc);
    }

    public AlcoType(long id, double price, double volume, double percents)
    {
        this.id = id;
        setGrams(volume, percents);
        pricePerVol = price / (volume * percents);
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
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

    public double getVolume()
    {
        return volume;
    }

    public double getPercents()
    {
        return percents;
    }
}
