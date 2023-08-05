package org.data.analysis.transactions.services;


import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import org.data.analysis.transactions.model.Transaction;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import com.oracle.svm.core.annotate.Inject;

@ApplicationScoped
public class TransactionProcessor {

    @Inject
    @Channel("transactions")
    Emitter<Transaction> transactionEmitter;

    @Incoming("transactions")
    @Transactional
    public Uni<Void> process(Transaction transaction) {
        return Uni.createFrom().item(transaction)
                .onItem().invoke(t -> {
                    transactionEmitter.send(t);
                })
                .onItem().ignore().andContinueWithNull();
    }
}

