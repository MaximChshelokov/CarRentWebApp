package com.mv.schelokov.carent.model.db.dao.factories;

import com.mv.schelokov.carent.model.db.dao.CarDao;
import com.mv.schelokov.carent.model.db.dao.InvoiceLineDao;
import com.mv.schelokov.carent.model.db.dao.InvoiceDao;
import com.mv.schelokov.carent.model.db.dao.CarMakeDao;
import com.mv.schelokov.carent.model.db.dao.CarModelDao;
import com.mv.schelokov.carent.model.db.dao.RejectionReasonDao;
import com.mv.schelokov.carent.model.db.dao.RentOrderDao;
import com.mv.schelokov.carent.model.db.dao.RoleDao;
import com.mv.schelokov.carent.model.db.dao.UserDataDao;
import com.mv.schelokov.carent.model.db.dao.UserDao;
import com.mv.schelokov.carent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.carent.model.entity.CarModel;
import com.mv.schelokov.carent.model.entity.User;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CriteriaFactory {
    
    public Criteria createUserFindLoginPasswordCriteria(User user) {
        return new UserDao.FindLoginPasswordCriteria(user);
    }
    
    public Criteria createUserFindLoginCriteria(String login) {
        return new UserDao.FindLoginCriteria(login);
    }
    
    public Criteria createUserFindLoginPasswordCriteria(String login, 
            String password) {
        return new UserDao.FindLoginPasswordCriteria(login, password);
    }
    
    public Criteria createUserByIdCriteria(int id) {
        return new UserDao.FindByIdCriteria(id);
    }
    
    public Criteria createAllUsersCriteria() {
        return UserDao.SELECT_ALL_CRITERIA;
    }
    
    public Criteria createAllUsersDataCriteria() {
        return UserDataDao.SELECT_ALL_CRITERIA;
    }
    
    public Criteria createUserDataByIdCriteria(int id) {
        return new UserDataDao.FindByUserCriteria(id);
    }
    
    public Criteria createAllRolesCriteria() {
        return RoleDao.SELECT_ALL_CRITERIA;
    }
    
    public Criteria createAllCarsCriteria() {
        return CarDao.SELECT_ALL_CRITERIA;
    }
    
    public Criteria createAvailableCarsCriteria() {
        return CarDao.SELECT_AVAILABLE_CRITERIA;
    }
    
    public Criteria createCarByIdCriteria(int id) {
        return new CarDao.FindByIdCriteria(id);
    }
    
    public Criteria createFindModelCriteria(CarModel model) {
        return new CarModelDao.FindModelCriteria(model);
    }
    
    public Criteria createMakeByNameCriteria(String name) {
        return new CarMakeDao.FindNameCriteria(name);
    }
    
    public Criteria createAllRentOrdersCriteria() {
        return RentOrderDao.SELECT_ALL_CRITERIA;
    }
    
    public Criteria createOrdersByApprovedCriteria() {
        return RentOrderDao.ORDER_BY_APPROVED_CRITERIA;
    }
    
    public Criteria createAllOpenedOrdersCriteria() {
        return RentOrderDao.OPENED_ORDERS_CRITERIA;
    }
    
    public Criteria createOrderByIdCriteria(int id) {
        return new RentOrderDao.FindByIdCriteria(id);
    }
    
    public Criteria createOrderByUserIdCriteria(int id) {
        return new RentOrderDao.FindByUserIdCriteria(id);
    }
    
    public Criteria createInvoiceByIdCriteria(int id) {
        return new InvoiceDao.FindByIdCriteria(id);
    }
    
    public Criteria createInvoiceLinesByInvoiceIdCriteria(int id) {
        return new InvoiceLineDao.FindByInvoiceIdCriteria(id);
    }
    
    public Criteria createRejectionReasonByIdCriteria(int id) {
        return new RejectionReasonDao.FindByIdCriteria(id);
    }
    
}
