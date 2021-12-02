/*
 * This file is generated by jOOQ.
 */
package generated.security.tables.records;


import generated.security.tables.UserRole;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserRoleRecord extends UpdatableRecordImpl<UserRoleRecord> implements Record2<Integer, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>security.user_role.user_id</code>.
     */
    public UserRoleRecord setUserId(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>security.user_role.user_id</code>.
     */
    public Integer getUserId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>security.user_role.role_id</code>.
     */
    public UserRoleRecord setRoleId(Integer value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>security.user_role.role_id</code>.
     */
    public Integer getRoleId() {
        return (Integer) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<Integer, Integer> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<Integer, Integer> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<Integer, Integer> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return UserRole.USER_ROLE.USER_ID;
    }

    @Override
    public Field<Integer> field2() {
        return UserRole.USER_ROLE.ROLE_ID;
    }

    @Override
    public Integer component1() {
        return getUserId();
    }

    @Override
    public Integer component2() {
        return getRoleId();
    }

    @Override
    public Integer value1() {
        return getUserId();
    }

    @Override
    public Integer value2() {
        return getRoleId();
    }

    @Override
    public UserRoleRecord value1(Integer value) {
        setUserId(value);
        return this;
    }

    @Override
    public UserRoleRecord value2(Integer value) {
        setRoleId(value);
        return this;
    }

    @Override
    public UserRoleRecord values(Integer value1, Integer value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserRoleRecord
     */
    public UserRoleRecord() {
        super(UserRole.USER_ROLE);
    }

    /**
     * Create a detached, initialised UserRoleRecord
     */
    public UserRoleRecord(Integer userId, Integer roleId) {
        super(UserRole.USER_ROLE);

        setUserId(userId);
        setRoleId(roleId);
    }
}