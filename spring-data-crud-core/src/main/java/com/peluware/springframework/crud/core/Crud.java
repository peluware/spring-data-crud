package com.peluware.springframework.crud.core;

/**
 * Marker sealed interface that defines the base contract for all CRUD-related services.
 * <p>
 * This interface is extended by {@link WriteService}, {@link ReadService}, and {@link CrudService},
 * and can be used to apply common behaviors or processing logic across all of them.
 * </p>
 *
 * @see WriteService
 * @see ReadService
 * @see CrudService
 */
public sealed interface Crud permits WriteService, ReadService, CrudService {

    /**
     * Executes common pre-processing logic for a given CRUD service before performing
     * the actual CRUD operation.
     * <p>
     * If the given service implements {@link AuthorizedCrud}, this method will check
     * whether the current operation is permitted.
     * Additional pre-processing logic can be added here to be executed just before
     * any CRUD operation is carried out.
     * </p>
     *
     * @param crud      the CRUD service instance
     * @param operation the type of CRUD operation to be performed
     */
    static void preProccess(Crud crud, CrudOperation operation) {
        if (crud instanceof AuthorizedCrud authorizedCrud) {
            AuthorizedCrud.verifyAccess(authorizedCrud, operation);
        }
        // Additional operations to be invoked right before the CRUD operation will be added here.
    }
}
