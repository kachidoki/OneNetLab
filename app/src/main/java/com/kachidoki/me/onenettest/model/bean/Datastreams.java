package com.kachidoki.me.onenettest.model.bean;

/**
 * Created by Frank on 16/8/14.
 */

public class Datastreams{
    private String create_time;
    private String update_at;
    private String current_value;
    private String id;
    private String unit;
    private String unit_symbol;
    private String uuid;

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit_symbol() {
        return unit_symbol;
    }

    public void setUnit_symbol(String unit_symbol) {
        this.unit_symbol = unit_symbol;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public String getCurrent_value() {
        return current_value;
    }

    public void setCurrent_value(String current_value) {
        this.current_value = current_value;
    }

    public class datastreamsWraper{
        private int errno;
        private Datastreams[] data;
        private String error;

        public Datastreams[] getdata() {
            return data;
        }

        public void setdata(Datastreams[] datastreamses) {
            this.data = datastreamses;
        }

        public int getErrno() {
            return errno;
        }

        public void setErrno(int errno) {
            this.errno = errno;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }

    public class dataSingleWraper{
        private int errno;
        private Datastreams data;
        private String error;
        public int getErrno() {
            return errno;
        }

        public void setErrno(int errno) {
            this.errno = errno;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public Datastreams getData() {
            return data;
        }

        public void setData(Datastreams data) {
            this.data = data;
        }
    }
}