package by.epam.xmlparser.parser;

import by.epam.xmlparser.exception.CustomException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AccessoryBuilderFactory {
    static final Logger logger = LogManager.getLogger();
    private enum TypeParser {
        SAX,
        STAX,
        DOM
    }

    public AbstractAccessoryBuilder createAccessoryBuilder(String typeParser) throws CustomException {
        try {
            TypeParser type = TypeParser.valueOf(typeParser.toUpperCase());
            switch (type) {
                case DOM:
                    return new AccessoryDomBuilder();
                case STAX:
                    return new AccessoryStaxBuilder();
                case SAX:
                    return new AccessorySaxBuilder();
                default:
                    throw new EnumConstantNotPresentException(type
                            .getDeclaringClass(), type.name());
            }
        } catch (IllegalArgumentException | EnumConstantNotPresentException e) {
            logger.error("Такого типа парсера не сущестует");
            throw new CustomException("Выбранного парсера не существует");
        }
    }
}
