package by.epam.xmlparser.parser;

import by.epam.xmlparser.entity.Accessory;
import by.epam.xmlparser.entity.Cpu;
import by.epam.xmlparser.entity.Gpu;
import by.epam.xmlparser.entity.Memory;
import by.epam.xmlparser.handler.ComputersXmlTag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Year;
import java.util.Set;

public class AccessoryStaxBuilder extends AbstractAccessoryBuilder {
    private XMLInputFactory inputFactory;
    private static final Logger logger = LogManager.getLogger();

    public AccessoryStaxBuilder() {
        super();
        inputFactory = XMLInputFactory.newInstance();
    }

    public AccessoryStaxBuilder(Set<Accessory> accessories) {
        super(accessories);
        inputFactory = XMLInputFactory.newInstance();
    }

    @Override
    public void buildSetAccessory(final String fileName) {
        XMLStreamReader reader;
        String name;
        try (FileInputStream inputStream = new FileInputStream(new File(fileName))) {
            reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    buildObject(name, reader);
                }
            }
            logger.info("Stax парсинг успешно завершен!");
        } catch (XMLStreamException ex) {
            logger.error("Ошибка парсинга StAX!");
        } catch (FileNotFoundException ex) {
            logger.error("Файл {} не найден!", fileName);
        } catch (IOException e) {
            logger.error("Ошибка файла.");
        }
    }

    private void buildObject(String name, XMLStreamReader reader) throws XMLStreamException {
        if (ComputersXmlTag.valueOf(name.toUpperCase()) == ComputersXmlTag.CPU) {
            Cpu cpu = buildCpu(reader);
            getAccessories().add(cpu);
        }
        if (ComputersXmlTag.valueOf(name.toUpperCase()) == ComputersXmlTag.GPU) {
            Gpu gpu = buildGpu(reader);
            getAccessories().add(gpu);
        }
        if (ComputersXmlTag.valueOf(name.toUpperCase()) == ComputersXmlTag.MEMORY) {
            Memory memory = buildMemory(reader);
            getAccessories().add(memory);
        }
    }

    private Cpu buildCpu(XMLStreamReader reader) throws XMLStreamException {
        Cpu cpu = new Cpu();
        cpu.setId(reader.getAttributeValue(null, ComputersXmlTag.ID.getValue()));
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (ComputersXmlTag.valueOf(name.toUpperCase())) {
                        case NAME -> cpu.setName(getXMLText(reader));
                        case ORIGIN -> cpu.setOrigin(getXMLText(reader));
                        case PRICE -> cpu.setPrice(Float.parseFloat(getXMLText(reader)));
                        case CATEGORY -> cpu.setCategory(getXMLText(reader));
                        case YEAR_OF_ISSUE -> cpu.setYearOfIssue(Year.parse(getXMLText(reader)));
                        case SOCKET -> cpu.setSocket(getXMLText(reader));
                        case FREQUENCY -> cpu.setFrequency(Integer.parseInt(getXMLText(reader)));
                        case CORES_NUM -> cpu.setCoreNum(Integer.parseInt(getXMLText(reader)));
                        case TYPE -> {
                            cpu.getType().setPeriphery(stringToBoolean(reader.getAttributeValue(null, ComputersXmlTag.PERIPHERY.getValue())));
                            cpu.getType().setRequiredForLaunch(stringToBoolean(reader.getAttributeValue(null, ComputersXmlTag.REQUIRED_FOR_LAUNCH.getValue())));
                            cpu.getType().setHasCooling(stringToBoolean(reader.getAttributeValue(null, ComputersXmlTag.COOLING.getValue())));
                        }
                        case ENERGY_CONSUMPTION -> cpu.getType().setEnergyConsumption(Float.parseFloat(getXMLText(reader)));
                    }
                case XMLStreamConstants.CHARACTERS: break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (ComputersXmlTag.valueOf(name.toUpperCase()) == ComputersXmlTag.CATEGORY.CPU) {
                        return cpu;
                    }
            }
        }
        throw new XMLStreamException("Незнакомый элемент в тэге <Memory>");
    }

    private Gpu buildGpu(XMLStreamReader reader) throws XMLStreamException {
        Gpu gpu = new Gpu();
        gpu.setId(reader.getAttributeValue(null, ComputersXmlTag.ID.getValue()));
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (ComputersXmlTag.valueOf(name.toUpperCase())) {
                        case NAME -> gpu.setName(getXMLText(reader));
                        case ORIGIN -> gpu.setOrigin(getXMLText(reader));
                        case PRICE -> gpu.setPrice(Float.parseFloat(getXMLText(reader)));
                        case CATEGORY -> gpu.setCategory(getXMLText(reader));
                        case YEAR_OF_ISSUE -> gpu.setYearOfIssue(Year.parse(getXMLText(reader)));
                        case MEMORY_CAPACITY -> gpu.setMemoryCapacity(Integer.parseInt(getXMLText(reader)));
                        case MEMORY_TYPE -> gpu.setMemoryType(getXMLText(reader));
                        case TYPE -> {
                            gpu.getType().setPeriphery(stringToBoolean(reader.getAttributeValue(null, ComputersXmlTag.PERIPHERY.getValue())));
                            gpu.getType().setRequiredForLaunch(stringToBoolean(reader.getAttributeValue(null, ComputersXmlTag.REQUIRED_FOR_LAUNCH.getValue())));
                            gpu.getType().setHasCooling(stringToBoolean(reader.getAttributeValue(null, ComputersXmlTag.COOLING.getValue())));
                        }
                        case ENERGY_CONSUMPTION -> gpu.getType().setEnergyConsumption(Float.parseFloat(getXMLText(reader)));
                        case PORTS -> gpu.getType().addPort(getXMLText(reader));
                    }
                case XMLStreamConstants.CHARACTERS: break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (ComputersXmlTag.valueOf(name.toUpperCase()) == ComputersXmlTag.CATEGORY.GPU) {
                        return gpu;
                    }
            }
        }
        throw new XMLStreamException("Незнакомый элемент в тэге <GPU>");
    }

    private Memory buildMemory(XMLStreamReader reader) throws XMLStreamException {
        Memory memory = new Memory();
        memory.setId(reader.getAttributeValue(null,ComputersXmlTag.ID.getValue()));
        String name;
        while(reader.hasNext()){
            int type = reader.next();
            switch (type){
                case XMLStreamConstants.START_ELEMENT:
                    name= reader.getLocalName();
                    switch (ComputersXmlTag.valueOf(name.toUpperCase())){
                        case NAME -> memory.setName(getXMLText(reader));
                        case ORIGIN -> memory.setOrigin(getXMLText(reader));
                        case PRICE -> memory.setPrice(Float.parseFloat(getXMLText(reader)));
                        case CATEGORY -> memory.setCategory(getXMLText(reader));
                        case YEAR_OF_ISSUE -> memory.setYearOfIssue(Year.parse(getXMLText(reader)));
                        case MEMORY_CAPACITY -> memory.setMemoryCapacity(Integer.parseInt(getXMLText(reader)));
                        case MEMORY_TYPE -> memory.setMemoryType(getXMLText(reader));
                        case TYPE -> setTypeMemory(memory,reader);
                        case ENERGY_CONSUMPTION -> memory.getType().setEnergyConsumption(Float.parseFloat(getXMLText(reader)));
                        case PORTS -> memory.getType().addPort(getXMLText(reader));
                    }
                    break;
                case XMLStreamConstants.CHARACTERS: break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (ComputersXmlTag.valueOf(name.toUpperCase()) == ComputersXmlTag.CATEGORY.MEMORY) {
                        return memory;
                    }
            }
        }
        throw new XMLStreamException("Незнакомый элемент в тэге <Memory>");
    }

    private void setTypeMemory(Memory memory, XMLStreamReader reader) {
        memory.getType().setPeriphery(stringToBoolean(reader.getAttributeValue(null,ComputersXmlTag.PERIPHERY.getValue())));
        memory.getType().setRequiredForLaunch(stringToBoolean(reader.getAttributeValue(null,ComputersXmlTag.REQUIRED_FOR_LAUNCH.getValue())));
        memory.getType().setHasCooling(stringToBoolean(reader.getAttributeValue(null,ComputersXmlTag.COOLING.getValue())));
    }

    private String getXMLText(final XMLStreamReader reader)
            throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
    private boolean stringToBoolean (String data){
        return data.equals("yes");
    }
}
