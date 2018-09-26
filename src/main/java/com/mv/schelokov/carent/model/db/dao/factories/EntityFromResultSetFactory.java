package com.mv.schelokov.carent.model.db.dao.factories;

import com.mv.schelokov.carent.model.entity.Car;
import com.mv.schelokov.carent.model.entity.CarMake;
import com.mv.schelokov.carent.model.entity.CarModel;
import com.mv.schelokov.carent.model.entity.Invoice;
import com.mv.schelokov.carent.model.entity.InvoiceLine;
import com.mv.schelokov.carent.model.entity.RejectionReason;
import com.mv.schelokov.carent.model.entity.RentOrder;
import com.mv.schelokov.carent.model.entity.Role;
import com.mv.schelokov.carent.model.entity.User;
import com.mv.schelokov.carent.model.entity.UserData;
import com.mv.schelokov.carent.model.entity.builders.CarBuilder;
import com.mv.schelokov.carent.model.entity.builders.CarMakeBuilder;
import com.mv.schelokov.carent.model.entity.builders.CarModelBuilder;
import com.mv.schelokov.carent.model.entity.builders.InvoiceBuilder;
import com.mv.schelokov.carent.model.entity.builders.InvoiceLineBuilder;
import com.mv.schelokov.carent.model.entity.builders.RejectionReasonBuilder;
import com.mv.schelokov.carent.model.entity.builders.RentOrderBuilder;
import com.mv.schelokov.carent.model.entity.builders.RoleBuilder;
import com.mv.schelokov.carent.model.entity.builders.UserBuilder;
import com.mv.schelokov.carent.model.entity.builders.UserDataBuilder;
import com.mv.schelokov.carent.model.entity.interfaces.EntityFactory;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class EntityFromResultSetFactory implements EntityFactory {
    
    private static final String CAR_MAKE_ID = "make_id";
    private static final String CAR_MAKE_NAME = "make_name";
    
    private static final String CAR_MODEL_ID = "model_id";
    private static final String CAR_MODEL_NAME = "model_name";
    
    private static final String CAR_ID = "car_id";
    private static final String CAR_LICENSE_PLATE = "license_plate";
    private static final String CAR_YEAR_OF_MAKE = "year_of_make";
    private static final String CAR_PRICE = "price";
    private static final String CAR_AVIALABLE = "available";

    private static final String INVOICE_ID = "invoice_id";
    private static final String INVOICE_DATE = "date";
    private static final String INVOICE_PAID = "paid";
    private static final String INVOICE_TOTAL = "total";
    
    private static final String INVOICE_LINE_ID = "line_id";
    private static final String INVOICE_LINE_DETAILS = "details";
    private static final String INVOICE_LINE_AMOUNT = "amount";

    private static final String REJECTION_REASON_ID = "reason_id";
    private static final String REJECTION_REASON_REASON = "reason";
    
    private static final String RENT_ORDER_ID = "rent_id";
    private static final String RENT_ORDER_START_DATE = "start_date";
    private static final String RENT_ORDER_END_DATE = "end_date";
    private static final String RENT_ORDER_APPROVED_BY = "approved_by";
    private static final String RENT_ORDER_APPROVER_LOGIN = "approver_login";
    
    private static final String ROLE_ID = "role_id";
    private static final String ROLE_NAME = "role_name";

    private static final String USER_ID = "user_id";
    private static final String USER_LOGIN = "login";
    private static final String USER_PASSWORD = "password";
    
    private static final String USERDATA_ID = "userdata_id";
    private static final String USERDATA_NAME = "name";
    private static final String USERDATA_ADDRESS = "address";
    private static final String USERDATA_PHONE = "phone";
    
    private final ResultSet resultSet;
    
    public EntityFromResultSetFactory(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public Car createCar() throws SQLException {
        return new CarBuilder()
                .withId(resultSet.getInt(CAR_ID))
                .withLicensePlate(resultSet.getString(CAR_LICENSE_PLATE))
                .inYearOfMake(resultSet.getInt(CAR_YEAR_OF_MAKE))
                .withPrice(resultSet.getInt(CAR_PRICE))
                .withAvailability(resultSet.getBoolean(CAR_AVIALABLE))
                .withModel(createCarModel())
                .getCar();
    }

    @Override
    public CarMake createCarMake() throws SQLException {
        return new CarMakeBuilder()
                .withId(resultSet.getInt(CAR_MAKE_ID))
                .withName(resultSet.getString(CAR_MAKE_NAME))
                .getCarMake();
    }

    @Override
    public CarModel createCarModel() throws SQLException {
        return new CarModelBuilder()
                .withId(resultSet.getInt(CAR_MODEL_ID))
                .withName(resultSet.getString(CAR_MODEL_NAME))
                .withCarMake(createCarMake())
                .getCarModel();
    }

    @Override
    public Invoice createInvoice() throws SQLException {
        return new InvoiceBuilder()
                .withId(resultSet.getInt(INVOICE_ID))
                .creationDate(resultSet.getDate(INVOICE_DATE))
                .initialPayment(resultSet.getInt(INVOICE_PAID))
                .estimatedTotal(resultSet.getInt(INVOICE_TOTAL))
                .getInvoice(); 
    }

    @Override
    public InvoiceLine createInvoiceLine() throws SQLException {
        return new InvoiceLineBuilder()
                .withId(resultSet.getInt(INVOICE_LINE_ID))
                .withInvoiceId(resultSet.getInt(INVOICE_ID))
                .paymentDetails(resultSet.getString(INVOICE_LINE_DETAILS))
                .paymentAmount(resultSet.getInt(INVOICE_LINE_AMOUNT))
                .getInvoiceLine();
    }

    @Override
    public RejectionReason createRejectionReason() throws SQLException {
        return new RejectionReasonBuilder()
                .withId(resultSet.getInt(REJECTION_REASON_ID))
                .dueReason(resultSet.getString(REJECTION_REASON_REASON))
                .getRejectionReason();
    }

    @Override
    public RentOrder createRentOrder() throws SQLException {
        return new RentOrderBuilder()
                .withId(resultSet.getInt(RENT_ORDER_ID))
                .startsAtDate(resultSet.getDate(RENT_ORDER_START_DATE))
                .endsByDate(resultSet.getDate(RENT_ORDER_END_DATE))
                .selectedCar(createCar())
                .byUser(new UserBuilder()
                        .withId(resultSet.getInt(USER_ID))
                        .withLogin(resultSet.getString(USER_LOGIN))
                        .getUser())
                .approvedBy(new UserBuilder()
                        .withId(resultSet.getInt(RENT_ORDER_APPROVED_BY))
                        .withLogin(resultSet.getString(RENT_ORDER_APPROVER_LOGIN))
                        .getUser())
                .rejectedDueReason(createRejectionReason())
                .getRentOrder();
    }

    @Override
    public Role createRole() throws SQLException {
        return new RoleBuilder()
                .withId(resultSet.getInt(ROLE_ID))
                .withRoleName(resultSet.getString(ROLE_NAME))
                .getRole();
    }

    @Override
    public User createUser() throws SQLException {
        return new UserBuilder()
                .withId(resultSet.getInt(USER_ID))
                .withLogin(resultSet.getString(USER_LOGIN))
                .withPassword(resultSet.getString(USER_PASSWORD))
                .withRole(createRole())
                .getUser();
    }

    @Override
    public UserData createUserData() throws SQLException {
        return new UserDataBuilder()
                .withId(resultSet.getInt(USERDATA_ID))
                .withName(resultSet.getString(USERDATA_NAME))
                .residentAtAddress(resultSet.getString(USERDATA_ADDRESS))
                .withPhone(resultSet.getString(USERDATA_PHONE))
                .withUser(createUser())
                .getUserData();
    }
}
