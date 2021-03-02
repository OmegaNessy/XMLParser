package by.epam.xmlparser.handler;

import by.epam.xmlparser.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import java.time.Year;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class AccessoryHandler extends DefaultHandler {
    static Logger logger = LogManager.getLogger();
    private Set<Accessory> accessories;
    private Cpu cpu;
    private Gpu gpu;
    private Memory memory;
    private boolean isCpu;
    private boolean isGpu;
    private boolean isMemory;
    private ComputersXmlTag currentTag;
    private final EnumSet<ComputersXmlTag> withText;
    private static final String ELEMENT_POSITIVE = "yes";

    public AccessoryHandler(Set<Accessory> accessorySet) {
        accessories = accessorySet;
        withText = EnumSet.range(ComputersXmlTag.NAME,ComputersXmlTag.PORTS);
    }

    public AccessoryHandler(){
        this.accessories = new HashSet<>();
        withText = EnumSet.range(ComputersXmlTag.NAME,ComputersXmlTag.PORTS);
    }

    public Set<Accessory> getAccessories() {
        return Set.copyOf(accessories);
    }

    public void setAccessories(Set<Accessory> accessories) {
        this.accessories = accessories;
    }

    @Override
    public void startDocument(){
        logger.info("Begin parsing...");
    }

    @Override
    public void endDocument(){

        logger.info("End parsing...");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equals(ComputersXmlTag.CPU.getValue())||qName.equals(ComputersXmlTag.GPU.getValue())||qName.equals(ComputersXmlTag.MEMORY.getValue())) {
            objectCreation(attributes,qName);
        }
        if (qName.equals("type")) {
            if (isCpu) {
                cpu.getType().setPeriphery(responseToBoolean(attributes.getValue(ComputersXmlTag.PERIPHERY.getValue())));
                cpu.getType().setHasCooling(responseToBoolean(attributes.getValue(ComputersXmlTag.COOLING.getValue())));
                cpu.getType().setRequiredForLaunch(responseToBoolean(attributes.getValue(ComputersXmlTag.REQUIRED_FOR_LAUNCH.getValue())));
            }
            if (isGpu) {
                gpu.getType().setPeriphery(responseToBoolean(attributes.getValue(ComputersXmlTag.PERIPHERY.getValue())));
                gpu.getType().setHasCooling(responseToBoolean(attributes.getValue(ComputersXmlTag.COOLING.getValue())));
                gpu.getType().setRequiredForLaunch(responseToBoolean(attributes.getValue(ComputersXmlTag.REQUIRED_FOR_LAUNCH.getValue())));
            }
            if(isMemory) {
                memory.getType().setPeriphery(responseToBoolean(attributes.getValue(ComputersXmlTag.PERIPHERY.getValue())));
                memory.getType().setHasCooling(responseToBoolean(attributes.getValue(ComputersXmlTag.COOLING.getValue())));
                memory.getType().setRequiredForLaunch(responseToBoolean(attributes.getValue(ComputersXmlTag.REQUIRED_FOR_LAUNCH.getValue())));
            }
        } else {
            ComputersXmlTag temp = ComputersXmlTag.valueOf(qName.toUpperCase());
            if (withText.contains(temp)) {
                currentTag = temp;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName){
        if (qName.equals(ComputersXmlTag.CPU.getValue())) {
            accessories.add(cpu);
            isCpu=false;
        }
        if (qName.equals(ComputersXmlTag.GPU.getValue())) {
            accessories.add(gpu);
            isGpu=false;
        }
        if (qName.equals(ComputersXmlTag.MEMORY.getValue())){
            accessories.add(memory);
            isMemory=false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length){
        String data = new String(ch, start, length).strip();
        if (currentTag!= null) {
            switch (currentTag) {
                case NAME -> setName(data);
                case ORIGIN -> setOrigin(data);
                case PRICE -> setPrice(Float.parseFloat(data));
                case CATEGORY -> setCategory(data);
                case ENERGY_CONSUMPTION -> setEnergyConsumption(Float.parseFloat(data));
                case PORTS -> addPort(data);
                case YEAR_OF_ISSUE -> setYearOfIssue(Year.parse(data));
                case SOCKET -> cpu.setSocket(data);
                case FREQUENCY -> cpu.setFrequency(Integer.parseInt(data));
                case CORES_NUM -> cpu.setCoreNum(Integer.parseInt(data));
                case MEMORY_CAPACITY -> setMemoryCapacity(Integer.parseInt(data));
                case MEMORY_TYPE -> setMemoryType(data);
                default -> throw new EnumConstantNotPresentException(
                        currentTag.getDeclaringClass(), currentTag.name());
            }
        }
        currentTag = null;
    }
    private void objectCreation(Attributes attributes, String qName) {
        if (ComputersXmlTag.CPU.getValue().equals(qName)) {
            cpu = new Cpu();
            cpu.setId(attributes.getValue(ComputersXmlTag.ID.getValue()));
            isCpu = true;
        }
        if (qName.equals(ComputersXmlTag.GPU.getValue())) {
            gpu = new Gpu();
            gpu.setId(attributes.getValue(ComputersXmlTag.ID.getValue()));
            isGpu = true;
        }
        if(qName.equals(ComputersXmlTag.MEMORY.getValue())){
            memory = new Memory();
            memory.setId(attributes.getValue(ComputersXmlTag.ID.getValue()));
            isMemory = true;
        }
    }

    private boolean responseToBoolean(String data) {
        return data.equals(ELEMENT_POSITIVE);
    }

    private void addPort(String data) {
        Type type = gpu.getType();
        type.addPort(data);
    }

    private void setEnergyConsumption(float data) {
        Type type = null;
        if (isCpu) {
            type = cpu.getType();
        }
        if(isGpu) {
            type = gpu.getType();
        }
        if(isMemory){
            type = memory.getType();
        }
        type.setEnergyConsumption(data);
    }

    private void setMemoryType(String data) {
        if(isGpu) {
            gpu.setMemoryType(data);
        }
        if(isMemory){
            memory.setMemoryType(data);
        }
    }

    private void setMemoryCapacity(int data) {
        if(isGpu) {
            gpu.setMemoryCapacity(data);
        }
        if(isMemory){
            memory.setMemoryCapacity(data);
        }
    }

    private void setYearOfIssue(Year data) {
        if (isCpu) {
            cpu.setYearOfIssue(data);
        }
        if(isGpu) {
            gpu.setYearOfIssue(data);
        }
        if(isMemory){
            memory.setYearOfIssue(data);
        }
    }

    private void setCategory(String data) {
        if (isCpu) {
            cpu.setCategory(data);
        }
        if(isGpu){
            gpu.setCategory(data);
        }
        if(isMemory){
            memory.setCategory(data);
        }
    }

    private void setName(String data) {
        if (isCpu) {
            cpu.setName(data);
        }
        if (isGpu) {
            gpu.setName(data);
        }
        if(isMemory){
            memory.setName(data);
        }
    }

    private void setOrigin(String data) {
        if (isCpu) {
            cpu.setOrigin(data);
        }
        if (isGpu) {
            gpu.setOrigin(data);
        }
        if(isMemory){
            memory.setOrigin(data);
        }
    }

    private void setPrice(float data) {
        if (isCpu) {
            cpu.setPrice(data);
        }
        if (isGpu) {
            gpu.setPrice(data);
        }
        if(isMemory){
            memory.setPrice(data);
        }
    }
}
