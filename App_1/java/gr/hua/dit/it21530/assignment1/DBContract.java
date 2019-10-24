package gr.hua.dit.it21530.assignment1;

import java.sql.Timestamp;

public class DBContract {

    private int id;
    private String userid;
    private double longitude;
    private double latitude;
    private String dt;

    public DBContract(int id, String userid, double longitude, double latitude, String dt) {
        this.id = id;
        this.userid = userid;
        this.longitude = longitude;
        this.latitude = latitude;
        this.dt = dt;
    }

    public DBContract(String userid, double longitude, double latitude) {
        this.userid = userid;
        this.longitude = longitude;
        this.latitude = latitude;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        this.dt = timestamp.toString();
    }

    public int getId() {
        return id;
    }

    public String getUserid() {
        return userid;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getDt() {
        return dt;
    }

    @Override
    public String toString() {
        return this.id +": UID-" +this.userid.charAt(0) +",lon-" +Math.round(this.longitude)
                +",lat-" +Math.round(this.latitude) +",dt-" +this.dt;
    }
}
