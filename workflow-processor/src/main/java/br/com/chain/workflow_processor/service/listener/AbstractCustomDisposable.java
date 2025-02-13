package br.com.chain.workflow_processor.service.listener;

import reactor.core.Disposable;

public abstract class AbstractCustomDisposable {

    protected Disposable disposableService;
    protected Disposable disposableListener;

    public void dispose() {
        if (disposableService != null && !disposableService.isDisposed()) {
            disposableService.dispose();
        }
        if (disposableListener != null && !disposableListener.isDisposed()) {
            disposableListener.dispose();
        }

    }
}
