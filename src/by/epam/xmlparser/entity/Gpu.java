package by.epam.xmlparser.entity;

import java.time.Year;

public class Gpu extends Accessory{
    private int memoryCapacity;
    private String memoryType;

    public Gpu() {
    }

    public Gpu(String id, String origin,String name, float price,
               String category, Type type, Year yearOfIssue,
               int memoryCapacity, String memoryType) {
        super(id,origin,name,price,category,type,yearOfIssue);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gpu)) return false;
        if (!super.equals(o)) return false;
        Gpu gpu = (Gpu) o;
        return memoryCapacity == gpu.memoryCapacity &&
                memoryType == gpu.memoryType || (memoryType != null && memoryType.equals(gpu.memoryType));
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = PRIME * result + memoryCapacity;
        result = PRIME * result + (memoryType!=null?memoryType.hashCode():0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("Gpu{" + "id=");
        string.append(id);
        string.append("\n");
        string.append(", origin=");
        string.append(origin);
        string.append("\n");
        string.append(", price=");
        string.append(price);
        string.append("\n");
        string.append(", category=");
        string.append(category);
        string.append("\n");
        string.append(", type=");
        string.append(type.toString());
        string.append("\n");
        string.append(", yearOfIssue=");
        string.append(yearOfIssue);
        string.append("\n");
        string.append(", memoryCapacity=");
        string.append(memoryCapacity);
        string.append("\n");
        string.append(", memoryType=");
        string.append(memoryType);
        string.append('}');
        string.append("\n");
        string.append("\n");
        return string.toString();
    }
}
