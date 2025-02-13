package br.com.chain.workflow_processor.service.common;

import reactor.core.publisher.Mono;

public interface CommonService {
    Mono<?> getData();
}
