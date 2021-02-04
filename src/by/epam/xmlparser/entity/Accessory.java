package by.epam.xmlparser.entity;

import java.time.Year;

public abstract class Accessory {
    protected String id;
    protected String name;
    protected String origin;
    protected float price;
    protected String category;
    protected Type type = new Type();
    protected Year yearOfIssue;

    public Accessory() {
    }

    public Accessory(String id, String origin, //SolarLint tip protected. Quest: WHY?
                     float price, String category,
                     Type type, Year yearOfIssue) {
        this.id = id;
        this.origin = origin;
        this.price = price;
        this.category = category;
        this.type = type;
        this.yearOfIssue = yearOfIssue;
    }

    public String getId() {
        return id;
    }

    public String getOrigin() {
        return origin;
    }

    public float getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public Type getType() {
        return type;
    }

    public Year getYearOfIssue() {
        return yearOfIssue;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setYearOfIssue(Year yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
