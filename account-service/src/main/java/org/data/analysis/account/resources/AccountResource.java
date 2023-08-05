package org.data.analysis.account.resources;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.transaction.Transactional;

import org.data.analysis.account.model.Account;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import com.oracle.svm.core.annotate.Inject;

import java.math.BigDecimal;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource {

    @Inject
    @Channel("accounts")
    Emitter<Account> accountEmitter;

    @POST
    @Transactional
    public Uni<Void> create(Account account) {
        return Uni.createFrom().item(account)
                .onItem().transformToUni(a -> {
                    a.persist();
                    accountEmitter.send(a);
                    return Uni.createFrom().voidItem();
                });
    }

    @GET
    @Path("/{id}")
    public Uni<Account> getById(@PathParam("id") Long id) {
        return Uni.createFrom().item(() -> Account.findById(id));
    }

    @PUT
    @Path("/{id}/balance")
    @Transactional
    public Uni<Void> updateBalance(@PathParam("id") Long id, BigDecimal amount) {
        return Uni.createFrom().item(() -> Account.findById(id))
                .onItem().transformToUni(a -> {
                    ((Account) a).setBalance(((Account) a).getBalance().add(amount));
                    ((PanacheEntityBase) a).persist();
                    accountEmitter.send((Account) a);
                    return Uni.createFrom().voidItem();
                });
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Uni<Void> delete(@PathParam("id") Long id) {
        return Uni.createFrom().item(() -> Account.findById(id))
                .onItem().transformToUni(a -> {
                    ((PanacheEntityBase) a).delete();
                    return Uni.createFrom().voidItem();
                });
    }
}
