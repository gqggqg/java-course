/*
 * This file is generated by jOOQ.
 */
package generated.tables.records;


import generated.tables.WaybillItem;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class WaybillItemRecord extends UpdatableRecordImpl<WaybillItemRecord> implements Record3<Long, Long, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.waybill_item.id</code>.
     */
    public WaybillItemRecord setId(Long value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.waybill_item.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.waybill_item.waybill_id</code>.
     */
    public WaybillItemRecord setWaybillId(Long value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.waybill_item.waybill_id</code>.
     */
    public Long getWaybillId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>public.waybill_item.item_id</code>.
     */
    public WaybillItemRecord setItemId(Integer value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.waybill_item.item_id</code>.
     */
    public Integer getItemId() {
        return (Integer) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<Long, Long, Integer> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Long, Long, Integer> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return WaybillItem.WAYBILL_ITEM.ID;
    }

    @Override
    public Field<Long> field2() {
        return WaybillItem.WAYBILL_ITEM.WAYBILL_ID;
    }

    @Override
    public Field<Integer> field3() {
        return WaybillItem.WAYBILL_ITEM.ITEM_ID;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public Long component2() {
        return getWaybillId();
    }

    @Override
    public Integer component3() {
        return getItemId();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public Long value2() {
        return getWaybillId();
    }

    @Override
    public Integer value3() {
        return getItemId();
    }

    @Override
    public WaybillItemRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public WaybillItemRecord value2(Long value) {
        setWaybillId(value);
        return this;
    }

    @Override
    public WaybillItemRecord value3(Integer value) {
        setItemId(value);
        return this;
    }

    @Override
    public WaybillItemRecord values(Long value1, Long value2, Integer value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached WaybillItemRecord
     */
    public WaybillItemRecord() {
        super(WaybillItem.WAYBILL_ITEM);
    }

    /**
     * Create a detached, initialised WaybillItemRecord
     */
    public WaybillItemRecord(Long id, Long waybillId, Integer itemId) {
        super(WaybillItem.WAYBILL_ITEM);

        setId(id);
        setWaybillId(waybillId);
        setItemId(itemId);
    }
}
