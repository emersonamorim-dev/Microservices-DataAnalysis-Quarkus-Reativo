package org.data.analysis.account.services;


import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import org.data.analysis.account.model.Account;
import org.eclipse.microprofile.reactive.messaging.Incoming;



@ApplicationScoped
public class AccountProcessor {

    @Incoming("accounts")
    @Transactional
    public Uni<Void> process(Account account) {
        return Uni.createFrom().item(account)
                .onItem().transformToUni(a -> {
                    Account existingAccount = Account.findById(a.id);
                    if (existingAccount != null) {
                        existingAccount.balance = a.balance;
                        existingAccount.persist();
                    } else {
                        a.persist();
                    }
                    return Uni.createFrom().voidItem();
                });
    }
}

