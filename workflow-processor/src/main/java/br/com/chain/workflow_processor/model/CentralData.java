package br.com.chain.workflow_processor.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Optional;

@Getter
public class CentralData {

    private Profile profile;
    private Address address;
    private Occupation occupation;

    @JsonIgnore
    private final Sinks.Many<CentralData> changeSink = Sinks.many().multicast().directBestEffort();

    public void setProfile(Profile profileData) {
        this.profile = profileData;
        changeSink.tryEmitNext(this);
    }

    public void setAddress(Address addressData) {
        this.address = addressData;
        changeSink.tryEmitNext(this);
    }

    public void setOccupation(Occupation occupationData) {
        this.occupation = occupationData;
        changeSink.tryEmitNext(this);
    }

    public Flux<CentralData> listenerChange() {
        return changeSink.asFlux();
    }

    public boolean isCompleted() {
        return this.profile != null &&
                this.address != null &&
                this.occupation != null;
    }
}