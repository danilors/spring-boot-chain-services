package br.com.chain.workflow_processor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.List;


@Getter
@Setter
@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonData {

    private Address address;
    private Profile profile;
    private Occupation occupation;

    private CommonData() {
    }

    public static CommonData fromInformation(List<?> information) {
        return parseInformationData(information);
    }


    private static CommonData parseInformationData(List<?> information) {
        log.info("parsing information: {}", information);
        var commonData = new CommonData();
        information.forEach(item -> {
            Class<?> wrapperClass = commonData.getClass();
            Class<?> itemClass = item.getClass();
            String setterName = "set" + itemClass.getSimpleName();
            try {
                Method setterMethod = wrapperClass.getMethod(setterName, itemClass);
                setterMethod.invoke(commonData, item);
            } catch (NoSuchMethodException |
                     InvocationTargetException |
                     IllegalAccessException e) {
                // Handle the case where the setter method doesn't exist.
                // This might happen if the PersonWrapper doesn't have a setter for
                // the given item type.  You could log a warning, throw an exception,
                // or simply ignore it.  Here's an example of logging a warning:
                log.error("Warning: No setter found for type {}", itemClass.getSimpleName());
            }

        });
        return commonData;
    }
}
