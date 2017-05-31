package com.kachidoki.me.onenettest.OLDAPP.model.bean;

/**
 * Created by Frank on 16/6/4.
 */
public class DeviceDetil {
    private String id;
    private boolean online;
    private String protocol;
    private String title;
    private String desc;
    private String create_time;
    private Binary binary;
    private String[] tags;
    private Location location;
    private String auth_info;
    private String other;
    private Key[] keys;
    private Datastreams[] datastreams;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public Binary getBinary() {
        return binary;
    }

    public void setBinary(Binary binary) {
        this.binary = binary;
    }



    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public Key[] getKeys() {
        return keys;
    }

    public void setKeys(Key[] keys) {
        this.keys = keys;
    }

    public Datastreams[] getDatastreams() {
        return datastreams;
    }

    public void setDatastreams(Datastreams[] datastreams) {
        this.datastreams = datastreams;
    }

    public String getAuth_info() {
        return auth_info;
    }

    public void setAuth_info(String auth_info) {
        this.auth_info = auth_info;
    }



    public static class Key{
        private String title;
        private String key;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }



    public static class Binary{
        private String index;
        private String at;
        private int size;
        private String desc;

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getAt() {
            return at;
        }

        public void setAt(String at) {
            this.at = at;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public static class Location{
        private float ele;
        private float lon;
        private float lat;


        public float getEle() {
            return ele;
        }

        public void setEle(float ele) {
            this.ele = ele;
        }

        public float getLon() {
            return lon;
        }

        public void setLon(float lon) {
            this.lon = lon;
        }

        public float getLat() {
            return lat;
        }

        public void setLat(float lat) {
            this.lat = lat;
        }
    }

    public class DeviceDetilWrapper{
        private int errno;
        private String error;
        private DeviceDetil data;

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


        public DeviceDetil getData() {
            return data;
        }

        public void setData(DeviceDetil data) {
            this.data = data;
        }
    }

}
