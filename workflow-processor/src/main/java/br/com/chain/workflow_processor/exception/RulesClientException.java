package br.com.chain.workflow_processor.exception;

public class RulesClientException extends RuntimeException {
    public RulesClientException(String message) {
        super(message);
    }

    public RulesClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
