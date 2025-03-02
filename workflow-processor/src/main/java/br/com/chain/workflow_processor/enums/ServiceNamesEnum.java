package br.com.chain.workflow_processor.enums;

public enum ServiceNamesEnum {
    ADDRESS,
    OCCUPATION;

    public static ServiceNamesEnum fromString(String name) {
        for (ServiceNamesEnum enumValue : ServiceNamesEnum.values()) {
            if (enumValue.name().equalsIgnoreCase(name)) { // Case-insensitive comparison
                return enumValue;
            }
        }
        return null; // Or throw an exception if you prefer
    }
}
