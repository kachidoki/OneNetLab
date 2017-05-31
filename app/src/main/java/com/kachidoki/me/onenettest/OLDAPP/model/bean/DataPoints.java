package com.kachidoki.me.onenettest.OLDAPP.model.bean;

/**
 * Created by Frank on 16/8/17.
 */

public class DataPoints  {
    private String at;
    private String value;

    public String getAt() {
        return at;
    }

    public void setAt(String at) {
        this.at = at;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public class Datastreams{

        private DataPoints[] datapoints;
        private String id;
        public DataPoints[] getDataPoints() {
            return datapoints;
        }

        public void setDataPoints(DataPoints[] dataPoints) {
            this.datapoints = dataPoints;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public class Data{
        private int count;
        private Datastreams[] datastreams;
        public Datastreams[] getDatastreams() {
            return datastreams;
        }

        public void setDatastreams(Datastreams[] datastreams) {
            this.datastreams = datastreams;
        }






        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }


    }

}
