package by.epam.xmlparser.parser;

import by.epam.xmlparser.entity.*;
import by.epam.xmlparser.exception.CustomException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AccessoryDomBuilder {
    Set<Accessory> accessorySet;
    @BeforeClass
    public void setUp(){
        ArrayList<String> ports = new ArrayList<>();
        accessorySet = new HashSet<>();
        accessorySet.add(new Cpu("ID-101","DE","r5 2600",362f,"CPU",
                         new Type(false,true,true,65,null),
                         Year.parse("2018"),"AM4",3900,6));
        accessorySet.add(new Memory("ID-201","Patriot Memory SL","RU",60.07f,"Memory",
                         new Type(false,true,false,1.5f,null),
                         Year.parse("2017"),4,"DDR3"));
        accessorySet.add(new Gpu("ID-301","US","Patriot Memory SL",824.21f,"GPU",new Type(false,false,true,300f)));
    }
    @Test
    public void buildSetAccessoryTest(){
        Set<Accessory> expected =
    }
}
