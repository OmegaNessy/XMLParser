package by.epam.xmlparser.parser;

import by.epam.xmlparser.exception.CustomException;
import by.epam.xmlparser.handler.AccessoryHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class AccessorySaxBuilder extends AbstractAccessoryBuilder{
    private static final Logger logger = LogManager.getLogger();
    private SAXParser parser;
    private AccessoryHandler accessoryHandler;

    public AccessorySaxBuilder() throws CustomException {
        try {
            parser = SAXParserFactory.newInstance().newSAXParser();
            accessoryHandler = new AccessoryHandler(getAccessories());
        } catch (ParserConfigurationException e) {
            logger.error("Ошибка в конфигурации парсера");
            throw new CustomException("Ошибка конфигурации парсера", e);
        } catch (SAXException e) {
            logger.error("Ошибка парсинга");
            throw new CustomException("Ошибка парсинга");
        }
    }

    @Override
    public void buildSetAccessory(String fileName) throws CustomException {
        try {
            parser.parse(new File(fileName), accessoryHandler);
            logger.info("Парсинг SAX успешно завершен");
        } catch (SAXException e) {
            logger.error("Ошибка парсинга");
            throw new CustomException("Ошибка парсинга", e);
        } catch (IOException e) {
            logger.error("Ошибка файла.");
            throw new CustomException("Ошибка чтения файла", e);
        }
    }
}
