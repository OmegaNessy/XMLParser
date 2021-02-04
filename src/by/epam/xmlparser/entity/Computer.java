package by.epam.xmlparser.entity;

import java.util.ArrayList;
import java.util.List;

public class Computer {
    private ArrayList<Accessory> accessories = new ArrayList<>();

    public Computer() {
    }

    public Computer(ArrayList<Accessory> accessories) {
        this.accessories = accessories;
    }

    public List<Accessory> getAccessories() {
        return List.copyOf(this.accessories);
    }

    public void setAccessories(ArrayList<Accessory> accessories) {
        this.accessories = accessories;
    }

    public void addAccessory(Accessory accessory){
        this.accessories.add(accessory);
    }

    public void deleteAccessoryById(int id){
        this.accessories.remove(id);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("Computer{" + "accessories=");
        string.append(accessories);
        string.append('}');
        return string.toString();
    }
}
