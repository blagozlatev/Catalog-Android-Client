package org.catalog.model;

import android.net.Uri;

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
	private Uri PostUrl;

	public String ToString() {
		return "ID: " + this.ID + "\n" + "Alcohol Type: " + this.AlcoholType
				+ "\n" + "Alcohol" + this.Alcohol + "\n" + "Content"
				+ this.Content + "\n" + "Age" + this.Age + "\n" + "Shell"
				+ this.Shell + "\n" + "Name" + this.Name + "\n" + "Shape"
				+ this.Shape + "\n" + "Color" + this.Color + "\n" + "Material"
				+ this.Material + "\n" + "Manufacturer" + this.Manufacturer
				+ "\n" + "City" + this.City + "\n" + "Country" + this.Country
				+ "\n" + "Continent" + this.Continent + "\n" + "Note"
				+ this.Note + "\n";
	}

	public static String Serialize(Bottle b) {
		return Integer.toString(b.ID).replace('#', ' ') + "#"
				+ b.AlcoholType.replace('#', ' ') + "#"
				+ b.Alcohol.replace('#', ' ') + "#"
				+ b.Content.replace('#', ' ') + "#"
				+ Integer.toString(b.Age).replace('#', ' ') + "#"
				+ b.Shell.replace('#', ' ') + "#" + b.Name.replace('#', ' ')
				+ "#" + b.Shape.replace('#', ' ') + "#"
				+ b.Color.replace('#', ' ') + "#"
				+ b.Material.replace('#', ' ') + "#"
				+ b.Manufacturer.replace('#', ' ') + "#"
				+ b.City.replace('#', ' ') + "#" + b.Country.replace('#', ' ')
				+ "#" + b.Continent.replace('#', ' ') + "#"
				+ b.Note.replace('#', ' ') + "#";
	}

	public static Bottle Deserialize(String serialized) {
		String[] split = serialized.split("#");
		for (int i = 0; i < split.length; i++) {
			if (split[i] != null) {
				split[i] = "";
			}
		}
		Bottle b = new Bottle();
		try {
			b.ID = Integer.parseInt(split[0]);
			b.AlcoholType = split[1];
			b.Alcohol = split[2];
			b.Content = split[3];
			b.Age = Integer.parseInt(split[4]);
			b.Shell = split[5];
			b.Name = split[6];
			b.Shape = split[7];
			b.Color = split[8];
			b.Material = split[9];
			b.Manufacturer = split[10];
			b.City = split[11];
			b.Country = split[12];
			b.Continent = split[13];
			b.Note = split[14];
		} catch (NumberFormatException e) {
			System.out.println("CAN'T PARSE INT!!!");
		} catch (IndexOutOfBoundsException ex) {
			return null;
		}
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

	public Uri getPostUrl() {
		return PostUrl;
	}

	public void setPostUrl(Uri postUrl) {
		PostUrl = postUrl;
	}
}
