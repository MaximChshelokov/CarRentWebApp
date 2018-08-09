package com.mv.schelokov.carent.model.db.dao.factories;

import com.mv.schelokov.carent.model.db.dao.CarDao;
import com.mv.schelokov.carent.model.db.dao.InvoiceLineDao;
import com.mv.schelokov.carent.model.db.dao.InvoiceDao;
import com.mv.schelokov.carent.model.db.dao.MakeDao;
import com.mv.schelokov.carent.model.db.dao.ModelDao;
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
    
    public static Criteria getUserFindLoginPasswordCriteria(User user) {
        return new UserDao.FindLoginPasswordCriteria(user);
    }
    
    public static Criteria getUserFindLoginCriteria(String login) {
        return new UserDao.FindLoginCriteria(login);
    }
    
    public static Criteria getUserFindLoginPasswordCriteria(String login, 
            String password) {
        return new UserDao.FindLoginPasswordCriteria(login, password);
    }
    
    public static Criteria getUserByIdCriteria(int id) {
        return new UserDao.FindByIdCriteria(id);
    }
    
    public static Criteria getAllUsersCriteria() {
        return UserDao.SELECT_ALL_CRITERIA;
    }
    
    public static Criteria getAllUsersDataCriteria() {
        return UserDataDao.SELECT_ALL_CRITERIA;
    }
    
    public static Criteria getUserDataByIdCriteria(int id) {
        return new UserDataDao.FindByUserCriteria(id);
    }
    
    public static Criteria getAllRolesCriteria() {
        return RoleDao.SELECT_ALL_CRITERIA;
    }
    
    public static Criteria getAllCarsCriteria() {
        return CarDao.SELECT_ALL_CRITERIA;
    }
    
    public static Criteria getAvailableCarsCriteria() {
        return CarDao.SELECT_AVAILABLE_CRITERIA;
    }
    
    public static Criteria getCarByIdCriteria(int id) {
        return new CarDao.FindByIdCriteria(id);
    }
    
    public static Criteria findModelCriteria(CarModel model) {
        return new ModelDao.FindModelCriteria(model);
    }
    
    public static Criteria getMakeByNameCriteria(String name) {
        return new MakeDao.FindNameCriteria(name);
    }
    
    public static Criteria getAllRentOrdersCriteria() {
        return RentOrderDao.SELECT_ALL_CRITERIA;
    }
    
    public static Criteria getAllOrdersOrderByApprovedCriteria() {
        return RentOrderDao.ORDER_BY_APPROVED_CRITERIA;
    }
    
    public static Criteria getAllOpenedOrdersCriteria() {
        return RentOrderDao.OPENED_ORDERS_CRITERIA;
    }
    
    public static Criteria findOrderByIdCriteria(int id) {
        return new RentOrderDao.FindByIdCriteria(id);
    }
    
    public static Criteria findOrderByUserIdCriteria(int id) {
        return new RentOrderDao.FindByUserIdCriteria(id);
    }
    
    public static Criteria findInvoiceByIdCriteria(int id) {
        return new InvoiceDao.FindByIdCriteria(id);
    }
    
    public static Criteria findInvoiceLinesByInvoiceIdCriteria(int id) {
        return new InvoiceLineDao.FindByInvoiceIdCriteria(id);
    }
    
}
