package com.peluware.springframework.crud.core.providers;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Interface that provides methods to obtain transaction management components.
 * <p>
 * Implementations can override to supply a {@link PlatformTransactionManager} which
 * will be used to create {@link TransactionOperations}. If no transaction manager
 * is provided, a no-transaction operations instance is returned.
 * </p>
 */
public interface TransactionOperationsProvider {

    /**
     * Returns the {@link PlatformTransactionManager} to be used for managing transactions.
     * <p>
     * The default implementation returns {@code null}, indicating no transaction
     * manager is available.
     * </p>
     *
     * @return the transaction manager, or {@code null} if none is available
     */
    default PlatformTransactionManager getTransactionManager() {
        return null;
    }

    /**
     * Returns an instance of {@link TransactionOperations} that can be used
     * to execute transactional code.
     * <p>
     * If a transaction manager is available (i.e., {@link #getTransactionManager()} returns non-null),
     * this method returns a {@link TransactionTemplate} bound to that manager.
     * Otherwise, it returns a {@link TransactionOperations} instance that performs
     * operations without actual transactions.
     * </p>
     *
     * @return a transaction operations instance, never {@code null}
     */
    default TransactionOperations getTransactionOperations() {
        var transactionManager = getTransactionManager();
        if (transactionManager == null) {
            return TransactionOperations.withoutTransaction();
        }
        return new TransactionTemplate(transactionManager);
    }
}
