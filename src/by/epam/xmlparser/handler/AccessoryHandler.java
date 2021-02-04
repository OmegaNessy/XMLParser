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

    private static final String ATTRIBUTE_PERIPHERY = "periphery";
    private static final String ATTRIBUTE_COOLING = "cooling";
    private static final String ATTRIBUTE_REQUIRED = "required_for_launch";
    private static final String ELEMENT_CPU = "CPU";
    private static final String ELEMENT_GPU = "GPU";
    private static final String ELEMENT_MEMORY = "Memory";


    public AccessoryHandler(Set<Accessory> accessorySet) {
        accessories = accessorySet;
        withText = EnumSet.range(ComputersXmlTag.NAME,ComputersXmlTag.PORTS);
    }

    public Set<Accessory> getAccessories() {
        return accessories;
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
        if (qName.equals(ELEMENT_CPU)||qName.equals(ELEMENT_GPU)||qName.equals(ELEMENT_MEMORY)) {
            objectCreation(attributes,qName);
        }
        if (qName.equals("type")) {
            if (isCpu) {
                cpu.getType().setPeriphery(responseToBoolean(attributes.getValue(ATTRIBUTE_PERIPHERY)));
                cpu.getType().setHasCooling(responseToBoolean(attributes.getValue(ATTRIBUTE_COOLING)));
                cpu.getType().setRequiredForLaunch(responseToBoolean(attributes.getValue(ATTRIBUTE_REQUIRED)));
            }
            if (isGpu) {
                gpu.getType().setPeriphery(responseToBoolean(attributes.getValue(ATTRIBUTE_PERIPHERY)));
                gpu.getType().setHasCooling(responseToBoolean(attributes.getValue(ATTRIBUTE_COOLING)));
                gpu.getType().setRequiredForLaunch(responseToBoolean(attributes.getValue(ATTRIBUTE_REQUIRED)));
            }
            if(isMemory) {
                memory.getType().setPeriphery(responseToBoolean(attributes.getValue(ATTRIBUTE_PERIPHERY)));
                memory.getType().setHasCooling(responseToBoolean(attributes.getValue(ATTRIBUTE_COOLING)));
                memory.getType().setRequiredForLaunch(responseToBoolean(attributes.getValue(ATTRIBUTE_REQUIRED)));
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
        if (qName.equals(ELEMENT_CPU)) {
            accessories.add(cpu);
            isCpu=false;
        }
        if (qName.equals(ELEMENT_GPU)) {
            accessories.add(gpu);
            isGpu=false;
        }
        if (qName.equals(ELEMENT_MEMORY)){
            accessories.add(memory);
            isGpu=false;
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
        if (ELEMENT_CPU.equals(qName)) {
            cpu = new Cpu();
            cpu.setId(attributes.getValue("id"));
            isCpu = true;
        }
        if (qName.equals(ELEMENT_GPU)) {
            gpu = new Gpu();
            gpu.setId(attributes.getValue("id"));
            isGpu = true;
        }
        if(qName.equals(ELEMENT_MEMORY)){
            memory = new Memory();
            memory.setId(attributes.getValue("id"));
            isMemory = true;
        }
    }

    private boolean responseToBoolean(String data) {
        return data.equals("yes");
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
            memory.setName(data);
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
//TODO: Почему дэфолтное значение не подставляется

}
