package by.epam.xmlparser.parser;

import by.epam.xmlparser.entity.Accessory;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractAccessoryBuilder {
    private Set<Accessory> accessories;

    public AbstractAccessoryBuilder() {
        accessories = new HashSet<>();
    }

    public AbstractAccessoryBuilder(Set<Accessory> accessories) {
        this.accessories = accessories;
    }

    public Set<Accessory> getAccessories() {
        return accessories;
    }

    public void setAccessories(final Set<Accessory> accessories) {
        this.accessories = accessories;
    }

    public abstract void buildSetAccessory(String fileName);
}
