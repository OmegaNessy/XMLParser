package by.epam.xmlparser.validator;

import by.epam.xmlparser.exception.CustomException;
import by.epam.xmlparser.handler.AccessoryErrorHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class DefaultValidator {
    final static Logger logger = LogManager.getLogger();
    public boolean validate(String fileName, String schemaName) throws CustomException {
        boolean isValid = false;
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory schemaFactory = SchemaFactory.newInstance(language);
        File schemaLocation = new File(schemaName);
        try{
            Schema schema = schemaFactory.newSchema(schemaLocation);
// creating a schema-based validator
            Validator validator = schema.newValidator();
            Source source = new StreamSource(fileName);
// document check
            validator.setErrorHandler(new AccessoryErrorHandler());
            validator.validate(source);
            isValid = true;
        } catch (SAXException | IOException e) {
            logger.error("{} is not correct or valid",fileName);
            throw new CustomException(e);
        }
        return isValid;
    }
}
