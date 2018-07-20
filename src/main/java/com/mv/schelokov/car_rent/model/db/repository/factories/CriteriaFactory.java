package com.mv.schelokov.car_rent.model.db.repository.factories;

import com.mv.schelokov.car_rent.model.db.repository.CarRepository;
import com.mv.schelokov.car_rent.model.db.repository.MakeRepository;
import com.mv.schelokov.car_rent.model.db.repository.ModelRepository;
import com.mv.schelokov.car_rent.model.db.repository.RentOrderRepository;
import com.mv.schelokov.car_rent.model.db.repository.RoleRepository;
import com.mv.schelokov.car_rent.model.db.repository.UserDataRepository;
import com.mv.schelokov.car_rent.model.db.repository.UserRepository;
import com.mv.schelokov.car_rent.model.db.repository.interfaces.Criteria;
import com.mv.schelokov.car_rent.model.entities.Model;
import com.mv.schelokov.car_rent.model.entities.User;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class CriteriaFactory {
    
    public static Criteria getUserFindLoginPassword(User user) {
        return new UserRepository.FindLoginPassword(user);
    }
    
    public static Criteria getUserFindLogin(String login) {
        return new UserRepository.FindLogin(login);
    }
    
    public static Criteria getUserFindLoginPassword(String login, 
            String password) {
        return new UserRepository.FindLoginPassword(login, password);
    }
    
    public static Criteria getAllUsers() {
        return UserRepository.SELECT_ALL;
    }
    
    public static Criteria getAllUsersData() {
        return UserDataRepository.SELECT_ALL;
    }
    
    public static Criteria getUserDataById(int id) {
        return new UserDataRepository.FindByUser(id);
    }
    
    public static Criteria getAllRoles() {
        return RoleRepository.SELECT_ALL;
    }
    
    public static Criteria getAllCars() {
        return CarRepository.SELECT_ALL;
    }
    
    public static Criteria getAvailableCars() {
        return CarRepository.SELECT_AVAILABLE;
    }
    
    public static Criteria getCarById(int id) {
        return new CarRepository.FindById(id);
    }
    
    public static Criteria findModel(Model model) {
        return new ModelRepository.FindModel(model);
    }
    
    public static Criteria getMakeByName(String name) {
        return new MakeRepository.FindName(name);
    }
    
    public static Criteria getAllRentOrders() {
        return RentOrderRepository.SELECT_ALL;
    }
    
    public static Criteria getAllOrdersOrderByApproved() {
        return RentOrderRepository.ORDER_BY_APPROVED;
    }
    
    public static Criteria findOrderById(int id) {
        return new RentOrderRepository.FindById(id);
    }
    
}
