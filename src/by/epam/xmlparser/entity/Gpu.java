package by.epam.xmlparser.entity;

import java.time.Year;

public class Gpu extends Accessory{
    private int memoryCapacity;
    private String memoryType;

    public Gpu() {
    }

    public Gpu(String id, String origin, float price,
               String category, Type type, Year yearOfIssue,
               int memoryCapacity, String memoryType) {
        super(id,origin,price,category,type,yearOfIssue);
        this.memoryCapacity = memoryCapacity;
        this.memoryType = memoryType;
    }

    public int getMemoryCapacity() {
        return memoryCapacity;
    }

    public void setMemoryCapacity(int memoryCapacity) {
        this.memoryCapacity = memoryCapacity;
    }

    public String getMemoryType() {
        return memoryType;
    }

    public void setMemoryType(String memoryType) {
        this.memoryType = memoryType;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("Gpu{" + "id='");
        string.append(id);
        string.append(", origin='");
        string.append(origin);
        string.append(", price=");
        string.append(price);
        string.append(", category='");
        string.append(category);
        string.append(", type=");
        string.append(type.toString());
        string.append(", yearOfIssue=");
        string.append(yearOfIssue);
        string.append(", memoryCapacity=");
        string.append(memoryCapacity);
        string.append(", memoryType='");
        string.append(memoryType);
        string.append('}');
        return string.toString();
    }
}
