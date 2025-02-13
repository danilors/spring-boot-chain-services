package br.com.chain.workflow_processor.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommonData {

    private  Address address;
    private  Profile profile;
    private  Occupation occupation;

    public CommonData() {
    }

    public static CommonData fromInformations(List<?> informations) {
        CommonData commonData = new CommonData();
        informations.forEach(info -> {
            if (info instanceof Address) {
                commonData.setAddress((Address) info);
            } else if (info instanceof Profile) {
                commonData.setProfile((Profile) info);
            } else if (info instanceof Occupation) {
                commonData.setOccupation((Occupation) info);
            }
        });
        return commonData;
    }
}
