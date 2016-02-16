package com.example.android.myanimalstestapp;

public class Animal {
//Instansvariables
    private int imageId;
    private String name;
    private String description;

    //Klassvariables
    //Konstruktorer
    public Animal(){
        //Kod för ett "standard" djur
    }
    public Animal(int imageId, String name, String description){
        //Spara som instansvariabler
        this.imageId = imageId;
        this.name = name;
        this.description = description;
    }
    //Instansmetoder
    public int getImageId(){
        return imageId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    //Klassmetoder
}
