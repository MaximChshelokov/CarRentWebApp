package com.mv.schelokov.carent.model.db.dao;

import com.mv.schelokov.carent.model.db.dao.factories.EntityFromResultSetFactory;
import com.mv.schelokov.carent.model.db.dao.interfaces.AbstractSqlDao;
import com.mv.schelokov.carent.model.db.dao.interfaces.Criteria;
import com.mv.schelokov.carent.model.db.dao.interfaces.SqlCriteria;
import com.mv.schelokov.carent.model.entity.RejectionReason;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class RejectionReasonDao extends AbstractSqlDao<RejectionReason> {
    
    public interface ReadCriteria extends SqlCriteria {}

    public interface DeleteCriteria extends SqlCriteria {}
    
    public static final Criteria SELECT_ALL = new SelectAllCriteria();

    public static class SelectAllCriteria implements ReadCriteria {

        private static final String QUERY = "SELECT reason_id,reason FROM "
                + "rejection_reasons";

        @Override
        public String toSqlQuery() {
            return QUERY;
        }

        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {}
    }
    
    public static class FindByIdCriteria extends SelectAllCriteria {

        private static final String QUERY = " WHERE reason_id=?";
        private static final int REASON_ID_INDEX = 1;        
        
        private final int reasonId;
        
        public FindByIdCriteria(int reasonId) {
            this.reasonId = reasonId;
        }

        @Override
        public String toSqlQuery() {
            return super.toSqlQuery().concat(QUERY);
        }

        @Override
        public void setStatement(PreparedStatement ps) throws SQLException {
            ps.setInt(REASON_ID_INDEX, this.reasonId);
        }
    }

    private static final String CREATE_QUERY = "INSERT INTO rejection_reasons "
            + "(reason_id,reason) VALUES (?,?)";
    private static final String REMOVE_QUERY = "DELETE FROM rejection_reasons "
            + "WHERE  reason_id=?";
    private static final String UPDATE_QUERY = "UPDATE rejection_reasons SET "
            + "reason=? WHERE reason_id=?";

    /**
     * The Field enum has column names for read methods and number of column for
     * the update method and the add method (in the NUMBER attribute)
     */
    enum Fields {
        REASON_ID(1, 2), REASON(2, 1);

        int INSERT;
        int UPDATE;

        Fields(int insert, int update) {
            this.INSERT = insert;
            this.UPDATE = update;
        }
    }

    public RejectionReasonDao(Connection connection) {
        super(connection);
    }

    @Override
    protected String getCreateQuery() {
        return CREATE_QUERY;
    }

    @Override
    protected String getRemoveQuery() {
        return REMOVE_QUERY;
    }

    @Override
    protected String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected RejectionReason createItem(ResultSet rs) throws SQLException {
        return new EntityFromResultSetFactory(rs).createRejectionReason();
    }

    @Override
    protected void setStatement(PreparedStatement ps, RejectionReason item,
            boolean isUpdateStatement) throws SQLException {
        if (isUpdateStatement) {
            ps.setInt(Fields.REASON_ID.UPDATE, item.getId());
            ps.setString(Fields.REASON.UPDATE, item.getReason());
        } else {
            ps.setInt(Fields.REASON_ID.INSERT, item.getId());
            ps.setString(Fields.REASON.INSERT, item.getReason());
        }
    }

    @Override
    protected boolean isReadCriteriaInstance(Criteria criteria) {
        return criteria instanceof ReadCriteria;
    }

    @Override
    protected boolean isDeleteCriteriaInstance(Criteria criteria) {
        return criteria instanceof DeleteCriteria;
    }
}
