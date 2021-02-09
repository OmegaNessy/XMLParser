package by.epam.xmlparser.parser;

import by.epam.xmlparser.entity.Cpu;
import by.epam.xmlparser.entity.Gpu;
import by.epam.xmlparser.entity.Memory;
import by.epam.xmlparser.handler.ComputersXmlTag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.Year;

public class AccessoryDomBuilder extends AbstractAccessoryBuilder{
    static final Logger logger = LogManager.getLogger();
    static final String ATTRIBUTE_RESPONSE="yes";
    private DocumentBuilder docBuilder;
    public AccessoryDomBuilder() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.error("Ошибка конфигурации парсера.");
        }
    }

    @Override
    public void buildSetAccessory(String fileName) {
        Document document;
        try {
            document = docBuilder.parse(new File(fileName));
            Element element = document.getDocumentElement();
            NodeList cpus = element.getElementsByTagName(ComputersXmlTag.CPU.getValue());
            NodeList memories = element.getElementsByTagName(ComputersXmlTag.MEMORY.getValue());
            NodeList gpus = element.getElementsByTagName(ComputersXmlTag.GPU.getValue());
            for (int i = 0; i < cpus.getLength(); i++) {
                Element node = (Element)cpus.item(i);
                addAccessory(buildCpu(node));
                //так как поле abstract класс private, то обращение к нему заблочено,
                //но так как это унаследованный класс и его конструктор имеет модификатор protected
                //то воспользоваться и изменить его поля могут только наследованные от него классы
            }
                for (int i = 0; i < gpus.getLength(); i++) {
                    Element node = (Element)gpus.item(i);
                    addAccessory(buildGpu(node));
                }
            for (int i = 0; i < memories.getLength(); i++) {
                Element node = (Element)memories.item(i);
                addAccessory(buildMemory(node));
            }
            logger.info("Dom парсинг прошел успешно");
        } catch (IOException e) {
            logger.error("Ошибка чтения файла.");
        } catch (SAXException e) {
            logger.error("Ошибка парсинга");
        }
    }

    private Cpu buildCpu(Element cpuElement){
        Cpu cpu = new Cpu();
        Element elementType = (Element) cpuElement.getElementsByTagName("type")
                .item(0);
        cpu.setId(cpuElement.getAttribute(ComputersXmlTag.ID.getValue()));
        cpu.setName(getElementTextContent(cpuElement,ComputersXmlTag.NAME.getValue()));
        cpu.setOrigin(getElementTextContent(cpuElement,ComputersXmlTag.ORIGIN.getValue()));
        cpu.setPrice(Float.parseFloat(getElementTextContent(cpuElement,ComputersXmlTag.PRICE.getValue())));
        cpu.setCategory(getElementTextContent(cpuElement,ComputersXmlTag.CATEGORY.getValue()));
        cpu.setCoreNum(Integer.parseInt(getElementTextContent(cpuElement,ComputersXmlTag.CORES_NUM.getValue())));
        cpu.setFrequency(Integer.parseInt(getElementTextContent(cpuElement,ComputersXmlTag.FREQUENCY.getValue())));
        cpu.setSocket(getElementTextContent(cpuElement,ComputersXmlTag.SOCKET.getValue()));
        cpu.setYearOfIssue(Year.parse(getElementTextContent(cpuElement,ComputersXmlTag.YEAR_OF_ISSUE.getValue())));
        cpu.getType().setEnergyConsumption(Float.parseFloat(getElementTextContent(cpuElement,ComputersXmlTag.ENERGY_CONSUMPTION.getValue())));
        cpu.getType().setPeriphery(attributeToBoolean(elementType.getAttribute("periphery")));
        cpu.getType().setHasCooling(attributeToBoolean(elementType.getAttribute("cooling")));
        cpu.getType().setRequiredForLaunch(attributeToBoolean(elementType.getAttribute("required_for_launch")));
        return cpu;
    }

    private Gpu buildGpu(Element gpuElement){
        Gpu gpu = new Gpu();
        Element elementType = (Element) gpuElement.getElementsByTagName("type")
                .item(0);
        gpu.setId(gpuElement.getAttribute(ComputersXmlTag.ID.getValue()));
        gpu.setName(getElementTextContent(gpuElement,ComputersXmlTag.NAME.getValue()));
        gpu.setOrigin(getElementTextContent(gpuElement,ComputersXmlTag.ORIGIN.getValue()));
        gpu.setPrice(Float.parseFloat(getElementTextContent(gpuElement,ComputersXmlTag.PRICE.getValue())));
        gpu.setCategory(getElementTextContent(gpuElement,ComputersXmlTag.CATEGORY.getValue()));
        gpu.setMemoryCapacity(Integer.parseInt(getElementTextContent(gpuElement,ComputersXmlTag.MEMORY_CAPACITY.getValue())));
        gpu.setMemoryType(getElementTextContent(gpuElement,ComputersXmlTag.MEMORY_TYPE.getValue()));
        for (int i = 0; i<=gpuElement.getElementsByTagName(ComputersXmlTag.PORTS.getValue()).getLength()-1;i++) {
            gpu.getType().addPort(gpuElement.getElementsByTagName(ComputersXmlTag.PORTS.getValue()).item(i).getTextContent());
        }
        gpu.setYearOfIssue(Year.parse(getElementTextContent(gpuElement,ComputersXmlTag.YEAR_OF_ISSUE.getValue())));
        gpu.getType().setEnergyConsumption(Float.parseFloat(getElementTextContent(gpuElement,ComputersXmlTag.ENERGY_CONSUMPTION.getValue())));
        gpu.getType().setPeriphery(attributeToBoolean(elementType.getAttribute("periphery")));
        gpu.getType().setHasCooling(attributeToBoolean(elementType.getAttribute("cooling")));
        gpu.getType().setRequiredForLaunch(attributeToBoolean(elementType.getAttribute("required_for_launch")));
        return gpu;
    }

    private Memory buildMemory(Element memoryElement){
        Memory memory = new Memory();
        Element elementType = (Element) memoryElement.getElementsByTagName("type")
                .item(0);
        memory.setId(memoryElement.getAttribute(ComputersXmlTag.ID.getValue()));
        memory.setName(getElementTextContent(memoryElement,ComputersXmlTag.NAME.getValue()));
        memory.setOrigin(getElementTextContent(memoryElement,ComputersXmlTag.ORIGIN.getValue()));
        memory.setPrice(Float.parseFloat(getElementTextContent(memoryElement,ComputersXmlTag.PRICE.getValue())));
        memory.setCategory(getElementTextContent(memoryElement,ComputersXmlTag.CATEGORY.getValue()));
        memory.setMemoryCapacity(Integer.parseInt(getElementTextContent(memoryElement,ComputersXmlTag.MEMORY_CAPACITY.getValue())));
        memory.setMemoryType(getElementTextContent(memoryElement,ComputersXmlTag.MEMORY_TYPE.getValue()));
        memory.setYearOfIssue(Year.parse(getElementTextContent(memoryElement,ComputersXmlTag.YEAR_OF_ISSUE.getValue())));
        memory.getType().setEnergyConsumption(Float.parseFloat(getElementTextContent(memoryElement,ComputersXmlTag.ENERGY_CONSUMPTION.getValue())));
        memory.getType().setPeriphery(attributeToBoolean(elementType.getAttribute("periphery")));
        memory.getType().setHasCooling(attributeToBoolean(elementType.getAttribute("cooling")));
        memory.getType().setRequiredForLaunch(attributeToBoolean(elementType.getAttribute("required_for_launch")));
        return memory;
    }

    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        return node.getTextContent();
    }

    private static boolean attributeToBoolean(String data){
        return data.equals(ATTRIBUTE_RESPONSE);
    }
}
