package br.com.chain.workflow_processor.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Getter
@Setter
public class CommonData {

    private Address address;
    private Profile profile;
    private Occupation occupation;

    private CommonData() {
    }

    private static final Map<Class<?>, Consumer<CommonData>> INFO_SETTERS = new HashMap<>();

    static {
        INFO_SETTERS.put(Address.class, commonData -> commonData.setAddress(commonData.getAddress()));
        INFO_SETTERS.put(Profile.class, commonData -> commonData.setProfile(commonData.getProfile()));
        INFO_SETTERS.put(Occupation.class, commonData -> commonData.setOccupation(commonData.getOccupation()));
        // Add more types here as needed
    }

    public static CommonData fromInformation(List<?> information) {
        CommonData commonData = new CommonData();
        information.forEach(info -> {
            Consumer<CommonData> setter = INFO_SETTERS.get(info.getClass());
            if (setter != null) {
                setter.accept(commonData);
            }
        });
        return commonData;
    }

}
