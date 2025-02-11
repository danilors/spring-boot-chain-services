package br.com.chain.workflow.model;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Component
@RequestScope
public class DataWorkflow {

    private Optional<List<Profile>> profile;
    private Optional<List<Address>> address;
    private Optional<List<Occupation>> occupation;

    private final BehaviorSubject<DataWorkflow> dataSubject = BehaviorSubject.create();

    public DataWorkflow() {}

     public void setProfile(List<Profile> profile) {
        this.profile = Optional.ofNullable(profile);
        dataSubject.onNext(this);
    }


    public void setAddress(List<Address> address) {
        this.address = Optional.ofNullable(address);
        dataSubject.onNext(this);
    }


    public void setOccupation(List<Occupation> occupation) {
        this.occupation = Optional.ofNullable(occupation);
        dataSubject.onNext(this);
    }

    public Observable<DataWorkflow> observeChanges() {
        return dataSubject.hide();
    }

    public boolean isComplete() {
        return profile.isPresent() && address.isPresent() && occupation.isPresent();
    }

    public void complete(){
        this.dataSubject.onComplete();
    }
}
