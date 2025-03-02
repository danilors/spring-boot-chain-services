package br.com.chain.workflow.model;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Component
@RequestScope
public class DataWorkflow {

    private final Profile profile;
    private Address address;
    private Occupation occupation;

    public DataWorkflow(Profile profile) {
        this.profile = profile;
    }

    public synchronized void setAddress(Address address) {
        this.address = address;
    }

    public synchronized void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }
}
