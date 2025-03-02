package br.com.chain.workflow_processor.client;

import org.springframework.web.reactive.function.client.WebClient;

public abstract  class AbstractClient {
    protected WebClient webClient;
    protected String path;
    protected Integer maxRetry;
    protected Integer maxRetryInterval;
}
