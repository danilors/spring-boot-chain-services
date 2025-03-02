package br.com.chain.workflow.service;

import br.com.chain.workflow.model.DataWorkflow;
import br.com.chain.workflow.model.Profile;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Component
public class TaskExecutorService {

    private Executor taskExecutor;

    public TaskExecutorService(@Qualifier("taskExecutor") Executor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }


    public DataWorkflow processTask(Profile profile, List<CompletableService> services) {
        var dataWorkflow = new DataWorkflow(profile);
        List<CompletableFuture<Void>> futures = new ArrayList<>(services.size());
        services.forEach(service -> {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> service.run(profile, dataWorkflow), taskExecutor);
            futures.add(future);
        });
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        return dataWorkflow;
    }
}
