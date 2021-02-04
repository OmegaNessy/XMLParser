package by.epam.xmlparser.entity;

import java.util.ArrayList;
import java.util.List;

public class Type {
    private boolean isPeriphery;
    private boolean isRequiredForLaunch;
    private boolean hasCooling;
    private float energyConsumption;
    private List<String> ports = new ArrayList<>();

    public Type() {
    }

    public Type(boolean isPeriphery, boolean isRequiredForLaunch,
                boolean hasCooling, float energyConsumption, List<String> ports) {
        this.isPeriphery = isPeriphery;
        this.isRequiredForLaunch = isRequiredForLaunch;
        this.hasCooling = hasCooling;
        this.energyConsumption = energyConsumption;
        this.ports = ports;
    }

    public boolean isPeriphery() {
        return isPeriphery;
    }

    public void setPeriphery(boolean periphery) {
        isPeriphery = periphery;
    }

    public boolean isRequiredForLaunch() {
        return isRequiredForLaunch;
    }

    public void setRequiredForLaunch(boolean requiredForLaunch) {
        isRequiredForLaunch = requiredForLaunch;
    }

    public boolean isHasCooling() {
        return hasCooling;
    }

    public void setHasCooling(boolean hasCooling) {
        this.hasCooling = hasCooling;
    }

    public float getEnergyConsumption() {
        return energyConsumption;
    }

    public void setEnergyConsumption(float energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    public List<String> getPort() {
        return List.copyOf(this.ports);
    }

    public void addPort (String port) {this.ports.add(port);}

    public void deletePortById (int id) {this.ports.remove(id);}

    public void setPorts (List<String> ports) {this.ports = ports;}


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Type{" + "isPeriphery=");
        builder.append(isPeriphery);
        builder.append(", isRequiredForLaunch=");
        builder.append(isRequiredForLaunch);
        builder.append(", hasCooling=");
        builder.append(hasCooling);
        builder.append(", energyConsumption=");
        builder.append(energyConsumption);
        builder.append(", ports=");
        for (String port:ports){
            builder.append(port);
            builder.append(',');
        }
        builder.append('}');
        return builder.toString();
    }

    //TODO: Спросить про гет метод у Листа

}
