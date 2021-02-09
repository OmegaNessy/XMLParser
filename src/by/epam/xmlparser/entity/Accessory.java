package by.epam.xmlparser.entity;

import java.time.Year;
import java.util.Objects;

public abstract class Accessory {
    protected String id;
    protected String name;
    protected String origin;
    protected float price;
    protected String category;
    protected Type type = new Type();
    protected Year yearOfIssue;

    protected Accessory() {
    }

    protected Accessory(String id, String origin, String name, //TODO:SolarLint tip protected. Quest: WHY? сделать протектед и на след уроке попросить объяснить
                     float price, String category,
                     Type type, Year yearOfIssue) {
        this.id = id;
        this.origin = origin;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Accessory)) return false;
        Accessory accessory = (Accessory) o;
        return Float.compare(accessory.price, price) == 0 &&
                Objects.equals(id, accessory.id) &&
                Objects.equals(name, accessory.name) &&
                Objects.equals(origin, accessory.origin) &&
                Objects.equals(category, accessory.category) &&
                type.equals(accessory.type) &&
                Objects.equals(yearOfIssue, accessory.yearOfIssue);
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME*result +(id!=null?id.hashCode():0);
        result =PRIME*result+(name!=null?name.hashCode():0);
        result =PRIME*result+(origin!=null?origin.hashCode():0);
        result =PRIME*result+ Float.floatToIntBits(price);
        result =PRIME*result+(category!=null?category.hashCode():0);
        result =PRIME*result+(yearOfIssue!=null?yearOfIssue.hashCode():0);
        result =PRIME*result+(type!=null?type.hashCode():0);
        return result;
    }

}
