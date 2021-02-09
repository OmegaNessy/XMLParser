package by.epam.xmlparser.parser;

import by.epam.xmlparser.entity.Accessory;
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
import java.util.Set;

public class AccessoryDomBuilder extends AbstractAccessoryBuilder{
    private DocumentBuilder docBuilder;
    static final Logger logger = LogManager.getLogger();
    public AccessoryDomBuilder() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.error("Ошибка конфигурации парсера.");
        }
    }

    public AccessoryDomBuilder(Set<Accessory> accessories) {
        super(accessories);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.error("Ошибка конфигурации парсера");

        }
    }

    @Override
    public Set<Accessory> getAccessories() {
        return super.getAccessories();
    }

    @Override
    public void buildSetAccessory(String fileName) {
        Document document;
        try {
            document = docBuilder.parse(new File(fileName));
            Element element = document.getDocumentElement();
            NodeList cpus = element.getElementsByTagName(ComputersXmlTag.CPU.getValue());
            NodeList gpus = element.getElementsByTagName(ComputersXmlTag.GPU.getValue());
            NodeList memories = element.getElementsByTagName(ComputersXmlTag.MEMORY.getValue());
            for (int i = 0; i < cpus.getLength(); i++) {
                Element node = (Element)cpus.item(i);
                getAccessories().add(buildCpu(node));
            }
            for (int i = 0; i < gpus.getLength(); i++) {
                Element node = (Element)gpus.item(i);
                getAccessories().add(buildGpu(node));
            }
            for (int i = 0; i < memories.getLength(); i++) {
                Element node = (Element)memories.item(i);
                getAccessories().add(buildMemory(node));
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
        return cpu;
    }

    private Gpu buildGpu(Element gpuElement){
        Gpu gpu = new Gpu();
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
        return gpu;
    }

    private Memory buildMemory(Element memoryElement){
        Memory memory = new Memory();
        memory.setId(memoryElement.getAttribute(ComputersXmlTag.ID.getValue()));
        memory.setName(getElementTextContent(memoryElement,ComputersXmlTag.NAME.getValue()));
        memory.setOrigin(getElementTextContent(memoryElement,ComputersXmlTag.ORIGIN.getValue()));
        memory.setPrice(Float.parseFloat(getElementTextContent(memoryElement,ComputersXmlTag.PRICE.getValue())));
        memory.setCategory(getElementTextContent(memoryElement,ComputersXmlTag.CATEGORY.getValue()));
        memory.setMemoryCapacity(Integer.parseInt(getElementTextContent(memoryElement,ComputersXmlTag.MEMORY_CAPACITY.getValue())));
        memory.setMemoryType(getElementTextContent(memoryElement,ComputersXmlTag.MEMORY_TYPE.getValue()));
        memory.setYearOfIssue(Year.parse(getElementTextContent(memoryElement,ComputersXmlTag.YEAR_OF_ISSUE.getValue())));
        memory.getType().setEnergyConsumption(Float.parseFloat(getElementTextContent(memoryElement,ComputersXmlTag.ENERGY_CONSUMPTION.getValue())));
        return memory;
    }

    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        String text = node.getTextContent();
        return text;
    }
}
