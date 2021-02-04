package by.epam.xmlparser.entity;

import java.time.Year;

public class Cpu extends Accessory{
    private String socket;
    private int frequency;
    private int coreNum;

    public Cpu() {
    }

    public Cpu(String id, String origin, float price,
               String category, Type type, Year yearOfIssue, String socket,
               int frequency, int coreNum) {
        super(id,origin,price,category,type,yearOfIssue);
        this.frequency = frequency;
        this.coreNum = coreNum;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getCoreNum() {
        return coreNum;
    }

    public void setCoreNum(int coreNum) {
        this.coreNum = coreNum;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("Cpu{" + "id='");
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
        string.append(", socket=");
        string.append(socket);
        string.append(", frequency=");
        string.append(frequency);
        string.append(", coreNum='");
        string.append(coreNum);
        string.append('}');
        return string.toString();
    }
}
