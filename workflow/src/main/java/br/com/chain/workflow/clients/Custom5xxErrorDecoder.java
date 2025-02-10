package br.com.chain.workflow.clients;

import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Custom5xxErrorDecoder implements ErrorDecoder {

    private static final Logger logger = LoggerFactory.getLogger(Custom5xxErrorDecoder.class.getName());

    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException exception = feign.FeignException.errorStatus(methodKey, response);
        logger.error("error trying to call service: " + exception.getMessage());
        int status = response.status();
        if (status >= 500) {
            return new RetryableException(
                    response.status(),
                    exception.getMessage(),
                    response.request().httpMethod(),
                    exception,
                    50L, // The retry interval
                    response.request());
        }
        return exception;
    }
}