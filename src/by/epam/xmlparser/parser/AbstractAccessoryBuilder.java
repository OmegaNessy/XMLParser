
package by.epam.xmlparser.parser;

import by.epam.xmlparser.entity.Accessory;
import by.epam.xmlparser.exception.CustomException;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractAccessoryBuilder {
    private Set<Accessory> accessories;

    protected AbstractAccessoryBuilder() {
        accessories = new HashSet<>();
    }

    public Set<Accessory> getAccessories() {
        return accessories;
    }

    public void setAccessories(Set<Accessory> accessories) {
        this.accessories = accessories;
    }

    public void addAccessory(Accessory accessory){
        this.accessories.add(accessory);
    }

    public abstract void buildSetAccessory(String fileName) throws CustomException;

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(" ");
        if (accessories != null) {
            for (Accessory accessory : accessories) {
                string.append(accessories.toString());
            }
        }
        return string.toString();
    }
}
