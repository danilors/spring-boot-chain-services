package br.com.chain.workflow.clients;

import br.com.chain.workflow.model.Occupation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "occupationsClient", url = "http://localhost:33503")
public interface OccupationsClient {

    @RequestMapping(value = "/api/occupations/{occupationId}", method = RequestMethod.GET)
    Occupation getOccupationId(@PathVariable("occupationId") int occupationId);
}
