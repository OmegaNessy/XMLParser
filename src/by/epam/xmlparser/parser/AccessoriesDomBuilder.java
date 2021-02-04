package by.epam.xmlparser.parser;

import by.epam.xmlparser.entity.Accessory;
import by.epam.xmlparser.entity.Cpu;
import by.epam.xmlparser.entity.Gpu;
import by.epam.xmlparser.entity.Memory;
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

public class AccessoriesDomBuilder extends AbstractAccessoryBuilder{
    private DocumentBuilder docBuilder;
    static final Logger logger = LogManager.getLogger();
    public AccessoriesDomBuilder() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.error("Ошибка конфигурации парсера.");
        }
    }

    public AccessoriesDomBuilder(Set<Accessory> accessories) {
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
            NodeList cpus = element.getElementsByTagName("CPU");
            NodeList gpus = element.getElementsByTagName("GPU");
            NodeList memories = element.getElementsByTagName("Memories");

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
        cpu.setId(cpuElement.getAttribute("id"));
        cpu.setName(getElementTextContent(cpuElement,"name"));
        cpu.setOrigin(getElementTextContent(cpuElement,"origin"));
        cpu.setPrice(Float.parseFloat(getElementTextContent(cpuElement,"price")));
        cpu.setCategory(getElementTextContent(cpuElement,"category"));
        cpu.setCoreNum(Integer.parseInt(getElementTextContent(cpuElement,"cores_num")));
        cpu.setFrequency(Integer.parseInt(getElementTextContent(cpuElement,"frequency")));
        cpu.setSocket(getElementTextContent(cpuElement,"socket"));
        cpu.setYearOfIssue(Year.parse(getElementTextContent(cpuElement,"year_of_issue")));
        cpu.getType().setEnergyConsumption(Float.parseFloat(getElementTextContent(cpuElement,"energy_consumption")));
        return cpu;
    }

    private Gpu buildGpu(Element gpuElement){
        Gpu gpu = new Gpu();
        gpu.setId(gpuElement.getAttribute("id"));
        gpu.setName(getElementTextContent(gpuElement,"name"));
        gpu.setOrigin(getElementTextContent(gpuElement,"origin"));
        gpu.setPrice(Float.parseFloat(getElementTextContent(gpuElement,"price")));
        gpu.setCategory(getElementTextContent(gpuElement,"category"));
        gpu.setMemoryCapacity(Integer.parseInt(getElementTextContent(gpuElement,"memory_capacity")));
        gpu.setMemoryType(getElementTextContent(gpuElement,"memory_type"));
        for (int i = 0; i<=gpuElement.getElementsByTagName("ports").getLength()-1;i++) {
            gpu.getType().addPort(gpuElement.getElementsByTagName("ports").item(i).getTextContent());
        }
        gpu.setYearOfIssue(Year.parse(getElementTextContent(gpuElement,"year_of_issue")));
        gpu.getType().setEnergyConsumption(Float.parseFloat(getElementTextContent(gpuElement,"energy_consumption")));
        return gpu;
    }

    private Memory buildMemory(Element memoryElement){
        Memory memory = new Memory();
        memory.setId(memoryElement.getAttribute("id"));
        memory.setName(getElementTextContent(memoryElement,"name"));
        memory.setOrigin(getElementTextContent(memoryElement,"origin"));
        memory.setPrice(Float.parseFloat(getElementTextContent(memoryElement,"price")));
        memory.setCategory(getElementTextContent(memoryElement,"category"));
        memory.setMemoryCapacity(Integer.parseInt(getElementTextContent(memoryElement,"memory_capacity")));
        memory.setMemoryType(getElementTextContent(memoryElement,"memory_type"));
        memory.setYearOfIssue(Year.parse(getElementTextContent(memoryElement,"year_of_issue")));
        memory.getType().setEnergyConsumption(Float.parseFloat(getElementTextContent(memoryElement,"energy_consumtion")));
        return memory;
    }

    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        String text = node.getTextContent();
        return text;
    }
}
