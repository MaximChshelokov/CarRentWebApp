package com.mv.schelokov.car_rent.model.db.dao.factories;

import com.mv.schelokov.car_rent.model.db.dao.CarDao;
import com.mv.schelokov.car_rent.model.db.dao.InvoiceLineDao;
import com.mv.schelokov.car_rent.model.db.dao.InvoiceDao;
import com.mv.schelokov.car_rent.model.db.dao.MakeDao;
import com.mv.schelokov.car_rent.model.db.dao.ModelDao;
import com.mv.schelokov.car_rent.model.db.dao.RentOrderDao;
import com.mv.schelokov.car_rent.model.db.dao.RoleDao;
import com.mv.schelokov.car_rent.model.db.dao.UserDataDao;
import com.mv.schelokov.car_rent.model.db.dao.UserDao;
import com.mv.schelokov.car_rent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.car_rent.model.entity.CarModel;
import com.mv.schelokov.car_rent.model.entity.User;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CriteriaFactory {
    
    public static Criteria getUserFindLoginPassword(User user) {
        return new UserDao.FindLoginPassword(user);
    }
    
    public static Criteria getUserFindLogin(String login) {
        return new UserDao.FindLogin(login);
    }
    
    public static Criteria getUserFindLoginPassword(String login, 
            String password) {
        return new UserDao.FindLoginPassword(login, password);
    }
    
    public static Criteria getAllUsers() {
        return UserDao.SELECT_ALL;
    }
    
    public static Criteria getAllUsersData() {
        return UserDataDao.SELECT_ALL;
    }
    
    public static Criteria getUserDataById(int id) {
        return new UserDataDao.FindByUser(id);
    }
    
    public static Criteria getAllRoles() {
        return RoleDao.SELECT_ALL;
    }
    
    public static Criteria getAllCars() {
        return CarDao.SELECT_ALL;
    }
    
    public static Criteria getAvailableCars() {
        return CarDao.SELECT_AVAILABLE;
    }
    
    public static Criteria getCarById(int id) {
        return new CarDao.FindById(id);
    }
    
    public static Criteria findModel(CarModel model) {
        return new ModelDao.FindModel(model);
    }
    
    public static Criteria getMakeByName(String name) {
        return new MakeDao.FindName(name);
    }
    
    public static Criteria getAllRentOrders() {
        return RentOrderDao.SELECT_ALL;
    }
    
    public static Criteria getAllOrdersOrderByApproved() {
        return RentOrderDao.ORDER_BY_APPROVED;
    }
    
    public static Criteria getAllOpenedOrders() {
        return RentOrderDao.OPENED_ORDERS;
    }
    
    public static Criteria findOrderById(int id) {
        return new RentOrderDao.FindById(id);
    }
    
    public static Criteria findOrderByUserId(int id) {
        return new RentOrderDao.FindByUserId(id);
    }
    
    public static Criteria findInvoiceById(int id) {
        return new InvoiceDao.FindById(id);
    }
    
    public static Criteria findInvoiceLinesByInvoiceId(int id) {
        return new InvoiceLineDao.FindByInvoiceId(id);
    }
    
}
