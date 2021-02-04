package by.epam.xmlparser.handler;

public enum ComputersXmlTag {
    DEVICES("devices"),
    CPU("CPU"),
    GPU("GPU"),
    MEMORY("Memory"),
    ID("id"),
    NAME("name"),
    ORIGIN("origin"),
    PRICE("price"),
    CATEGORY("category"),
    TYPE("type"),
    PERIPHERY("periphery"),
    REQUIRED_FOR_LAUNCH("required_for_launch"),
    COOLING("cooling"),
    ENERGY_CONSUMPTION("energy_consumption"),
    YEAR_OF_ISSUE("year_of_issue"),
    SOCKET("socket"),
    FREQUENCY("frequency"),
    CORES_NUM("cores_num"),
    MEMORY_CAPACITY("memory_capacity"),
    MEMORY_TYPE("memory_type"),
    PORTS("ports");

    private String value;

    ComputersXmlTag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
