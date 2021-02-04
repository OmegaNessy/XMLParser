package by.epam.xmlparser.parser;

import by.epam.xmlparser.exception.CustomException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AccessoriesBuilderFactory {
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
                    return new AccessoriesDomBuilder();
                case STAX:
                    return new AccessoriesStaxBuilder();
                case SAX:
                    return new AccessoriesSaxBuilder();
                default:
                    throw new EnumConstantNotPresentException(type
                            .getDeclaringClass(), type.name());
            }
        } catch (IllegalArgumentException | EnumConstantNotPresentException e) {
            logger.error("Такого типа парсера не сущестует");
        }

        throw new CustomException("Тип парсера выбран некорректно");
    }
}
