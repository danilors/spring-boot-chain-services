package br.com.chain.workflow.clients;

import br.com.chain.workflow.model.Profile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "profileClient", url = "http://localhost:33501")
public interface ProfileClient {

    @RequestMapping(value = "/api/profiles/{profileId}", method = RequestMethod.GET)
    Profile getProfileById(@PathVariable("profileId") int profileId);
}

