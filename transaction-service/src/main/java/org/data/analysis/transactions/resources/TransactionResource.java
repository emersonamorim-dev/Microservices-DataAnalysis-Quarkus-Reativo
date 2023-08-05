package org.data.analysis.transactions.resources;


import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.data.analysis.transactions.model.Transaction;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import com.oracle.svm.core.annotate.Inject;
import java.util.List;


@Path("/transactions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class TransactionResource {

    @Inject
    @Channel("transactions")
    Emitter<Transaction> transactionEmitter;

    @POST
    @Transactional
    public Uni<Void> create(Transaction transaction) {
        transaction.persist();
        transactionEmitter.send(transaction);
        return Uni.createFrom().voidItem();
    }

    @GET
    public List<Transaction> getAll() {
        return Transaction.listAll();
    }

    @GET
    @Path("/{id}")
    public Uni<Transaction> getById(@PathParam("id") Long id) {
        return Uni.createFrom().item(Transaction.findById(id));
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Uni<Void> update(@PathParam("id") Long id, Transaction transaction) {
        Transaction t = Transaction.findById(id);
        if (t != null) {
            t.accountId = transaction.accountId;
            t.amount = transaction.amount;
            t.timestamp = transaction.timestamp;
            transactionEmitter.send(t);
        }
        return Uni.createFrom().voidItem();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Uni<Void> delete(@PathParam("id") Long id) {
        Transaction t = Transaction.findById(id);
        if (t != null) {
            t.delete();
        }
        return Uni.createFrom().voidItem();
    }
}
