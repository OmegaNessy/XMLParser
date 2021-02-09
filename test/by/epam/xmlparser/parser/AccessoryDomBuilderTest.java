package by.epam.xmlparser.parser;

import by.epam.xmlparser.entity.*;
import by.epam.xmlparser.exception.CustomException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AccessoryDomBuilderTest {
    final Logger logger = LogManager.getLogger();
    private Set<Accessory> accessorySet;
    private AccessoryBuilderFactory builderFactory = new AccessoryBuilderFactory();
    static final String TEST_FILENAME = "./resources/data/ComputersForTesting.xml";

    @BeforeClass
    public void setUp(){
        ArrayList<String> portsGPU = new ArrayList<>();
        accessorySet = new HashSet<>();
        portsGPU.add("HDMI");
        portsGPU.add("DisplayPort");
        accessorySet.add(new Cpu("ID-101","DE","r5 2600",362f,"CPU",
                         new Type(false,true,true,65,null),
                         Year.parse("2018"),"AM4",3900,6));
        accessorySet.add(new Memory("ID-201","RU","Patriot Memory SL",60.07f,"Memory",
                new Type(false,true,false,1.5f,null),
                Year.parse("2017"),4,"DDR3"));
        accessorySet.add(new Gpu("ID-301","US","Patriot Memory SL",824.21f,"GPU",
                new Type(false,false,true,300f,portsGPU),
                Year.parse("2019"),4096,"GDDR5"));
    }

    @AfterClass
    public void tearDown(){
        accessorySet = null;
    }
    @Test
    public void buildSetAccessoryTest() throws CustomException {
        AbstractAccessoryBuilder builder = builderFactory.createAccessoryBuilder("dom");
        builder.buildSetAccessory(TEST_FILENAME);
        logger.info("{}",accessorySet);
        logger.info("{}",builder.getAccessories());
        Set<Accessory> expected = accessorySet;
        Set<Accessory> actual = builder.getAccessories();
        Assert.assertEquals(actual,expected);
    }
}
