package br.com.chain.workflow_processor.exception;

public class AddressClientException extends RuntimeException {
    public AddressClientException(String message) {
        super(message);
    }

    public AddressClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
