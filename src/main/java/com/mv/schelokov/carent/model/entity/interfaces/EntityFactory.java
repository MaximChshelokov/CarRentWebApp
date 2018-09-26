package com.mv.schelokov.carent.model.entity.interfaces;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public interface EntityFactory {
    
    Entity createCar() throws Exception;
    
    Entity createCarMake() throws Exception;
    
    Entity createCarModel() throws Exception;
    
    Entity createInvoice() throws Exception;
    
    Entity createInvoiceLine() throws Exception;
    
    Entity createRejectionReason() throws Exception;
    
    Entity createRentOrder() throws Exception;
    
    Entity createRole() throws Exception;
    
    Entity createUser() throws Exception;
    
    Entity createUserData() throws Exception;
}
