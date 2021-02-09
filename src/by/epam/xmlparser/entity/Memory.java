package by.epam.xmlparser.entity;

import java.time.Year;
import java.util.Objects;

public class Memory extends Accessory {
    private int memoryCapacity;
    private String memoryType;

    public Memory() {
    }

    public Memory(String id, String origin,String name, float price,
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
        if (!(o instanceof Memory)) return false;
        if (!super.equals(o)) return false;
        Memory memory = (Memory) o;
        return memoryCapacity == memory.memoryCapacity &&
                Objects.equals(memoryType, memory.memoryType);
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
        string.append("Memory{" + "id=");
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
