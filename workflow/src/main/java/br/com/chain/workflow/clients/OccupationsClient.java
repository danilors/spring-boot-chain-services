package br.com.chain.workflow.clients;

import br.com.chain.workflow.clients.fallback.OccupationClientFallbackFactory;
import br.com.chain.workflow.config.RetryConfiguration;
import br.com.chain.workflow.model.Occupation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "occupationsClient", fallbackFactory = OccupationClientFallbackFactory.class, configuration = RetryConfiguration.class)
public interface OccupationsClient {

    @RequestMapping(method = RequestMethod.GET)
    public List<Occupation> getAllOccupations();
}
