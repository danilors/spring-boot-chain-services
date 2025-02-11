package br.com.chain.workflow_processor.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Setter
@Getter
@Component
public class WorkFlowData {
    private Profile profile;
    private Address address;
    private Occupation occupation;

    private final Sinks.Many<WorkFlowData> dataSink = Sinks.many().multicast().directAllOrNothing();

    public Flux<WorkFlowData> dataChanges() {
        return dataSink.asFlux();
    }

    public void update(WorkFlowData newData){
        this.profile = newData.profile;
        this.address = newData.address;
        this.occupation = newData.occupation;
        dataSink.tryEmitNext(this);
    }

}


