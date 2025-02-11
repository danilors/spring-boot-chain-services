package br.com.chain.workflow.clients;

import br.com.chain.workflow.clients.fallback.ProfileClientFallbackFactory;
import br.com.chain.workflow.config.RetryConfiguration;
import br.com.chain.workflow.model.Profile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "profileClient", fallbackFactory = ProfileClientFallbackFactory.class, configuration = RetryConfiguration.class)
public interface ProfileClient {

    @RequestMapping(method = RequestMethod.GET)
    List<Profile> getAllProfiles();
}

