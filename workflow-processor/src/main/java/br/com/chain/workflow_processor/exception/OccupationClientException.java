package br.com.chain.workflow_processor.exception;

public class OccupationClientException extends RuntimeException {
    public OccupationClientException(String message) {
        super(message);
    }

    public OccupationClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
