package org.catalog.model;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

public class Bottle {
    private int ID;
    private String AlcoholType;
    private String Alcohol;
    private String Content;
    private int Age;
    private String Shell;
    private String Name;
    private String Shape;
    private String Color;
    private String Material;
    private String Manufacturer;
    private String City;
    private String Country;
    private String Continent;
    private String Note;
    private URI PostUrl;

    public Bottle() {
        this.ID = 0;
        this.AlcoholType = "";
        this.Alcohol = "";
        this.Content = "";
        this.Age = 0;
        this.Shell = "";
        this.Name = "";
        this.Shape = "";
        this.Color = "";
        this.Material = "";
        this.Manufacturer = "";
        this.City = "";
        this.Country = "";
        this.Continent = "";
        this.Note = "";
    }

    @SuppressWarnings("unused")
    @Override
    public String toString() {
        return "ID: " + this.ID + "\n" + "Alcohol Type: " + this.AlcoholType + "\n"
                + "Alcohol" + this.Alcohol + "\n" + "Content" + this.Content + "\n"
                + "Age" + this.Age + "\n" + "Shell" + this.Shell + "\n" + "Name"
                + this.Name + "\n" + "Shape" + this.Shape + "\n" + "Color" + this.Color
                + "\n" + "Material" + this.Material + "\n" + "Manufacturer"
                + this.Manufacturer + "\n" + "City" + this.City + "\n" + "Country"
                + this.Country + "\n" + "Continent" + this.Continent + "\n" + "Note"
                + this.Note + "\n";
    }

    public static String Serialize(Bottle b) {
        // Delimiter
        char dm = '#';
        // To Be Replaced
        char tbr = '#';
        // Replacing Symbol - Space
        char spc = ' ';
        return Integer.toString(b.ID).replace(tbr, spc) + dm
                + b.AlcoholType.replace(tbr, spc) + dm + b.Alcohol.replace(tbr, spc)
                + dm + b.Content.replace(tbr, spc) + dm
                + Integer.toString(b.Age).replace(tbr, spc) + dm
                + b.Shell.replace(tbr, spc) + dm + b.Name.replace(tbr, spc) + dm
                + b.Shape.replace(tbr, spc) + dm + b.Color.replace(tbr, spc) + dm
                + b.Material.replace(tbr, spc) + dm + b.Manufacturer.replace(tbr, spc)
                + dm + b.City.replace(tbr, spc) + dm + b.Country.replace(tbr, spc) + dm
                + b.Continent.replace(tbr, spc) + dm + b.Note.replace(tbr, spc) + dm;
    }

    public static Bottle Deserialize(String serialized)
            throws NumberFormatException, IndexOutOfBoundsException {
        ArrayList<String> split = new ArrayList<String>(Arrays.asList(serialized.split("#")));

        //Padding the ArrayList
        if (split.size() < 16) {
            for (int i = split.size(); i < 16; i++) {
                split.add("");
            }
        }

        Bottle b = new Bottle();
        b.ID = Integer.parseInt(split.get(0));
        b.AlcoholType = split.get(1);
        b.Alcohol = split.get(2);
        b.Content = split.get(3);
        b.Age = Integer.parseInt(split.get(4));
        b.Shell = split.get(5);
        b.Name = split.get(6);
        b.Shape = split.get(7);
        b.Color = split.get(8);
        b.Material = split.get(9);
        b.Manufacturer = split.get(10);
        b.City = split.get(11);
        b.Country = split.get(12);
        b.Continent = split.get(13);
        b.Note = split.get(15);
        return b;
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public String getAlcoholType() {
        return AlcoholType;
    }

    public void setAlcoholType(String alcoholType) {
        AlcoholType = alcoholType;
    }

    public String getAlcohol() {
        return Alcohol;
    }

    public void setAlcohol(String alcohol) {
        Alcohol = alcohol;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getShell() {
        return Shell;
    }

    public void setShell(String shell) {
        Shell = shell;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getShape() {
        return Shape;
    }

    public void setShape(String shape) {
        Shape = shape;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getContinent() {
        return Continent;
    }

    public void setContinent(String continent) {
        Continent = continent;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public URI getPostUrl() {
        return PostUrl;
    }

    public void setPostUrl(URI postUrl) {
        PostUrl = postUrl;
    }
}
