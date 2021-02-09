package by.epam.xmlparser.validator;

import by.epam.xmlparser.exception.CustomException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

public class DefaultValidatorTest {
    static final Logger logger = LogManager.getLogger();
    final private String FILE_PATH = "./resources/data/Computers.xml";
    final private String SCHEMA_PATH = "./resources/data/Computers.xsd";

    @Test
    public void validatorTest() throws SAXNotSupportedException, CustomException, SAXNotRecognizedException {
        boolean expected = DefaultValidator.validate(FILE_PATH, SCHEMA_PATH);
        Assert.assertTrue(expected,"Ошибка валидации");
    }
}
