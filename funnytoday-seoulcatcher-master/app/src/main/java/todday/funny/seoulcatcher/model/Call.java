package todday.funny.seoulcatcher.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class Call implements Parcelable {
    //여긴 무조건 String 만 넣어야댐
    private String date;
    private String toUserId;
    private String toUserName;
    private String toUserPhotoUrl;
    private String kind;
    private String age;
    private String name;
    private String address;
    private String latitude;
    private String longitude;

    public String getDate() {
        return date != null ? date : "0";
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getToUserPhotoUrl() {
        return toUserPhotoUrl;
    }

    public void setToUserPhotoUrl(String toUserPhotoUrl) {
        this.toUserPhotoUrl = toUserPhotoUrl;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude != null ? latitude : "0";
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude != null ? longitude : "0";
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Call() {
    }

    public Call(Map<String, String> data) {
        setToUserId(data.get("toUserId"));
        setToUserName(data.get("toUserName"));
        setToUserPhotoUrl(data.get("toUserPhotoUrl"));
        setKind(data.get("kind"));
        setAddress(data.get("age"));
        setName(data.get("name"));
        setAddress(data.get("address"));
        setLatitude(data.get("latitude"));
        setLongitude(data.get("longitude"));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeString(this.toUserId);
        dest.writeString(this.toUserName);
        dest.writeString(this.toUserPhotoUrl);
        dest.writeString(this.kind);
        dest.writeString(this.age);
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
    }

    protected Call(Parcel in) {
        this.date = in.readString();
        this.toUserId = in.readString();
        this.toUserName = in.readString();
        this.toUserPhotoUrl = in.readString();
        this.kind = in.readString();
        this.age = in.readString();
        this.name = in.readString();
        this.address = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
    }

    public static final Creator<Call> CREATOR = new Creator<Call>() {
        @Override
        public Call createFromParcel(Parcel source) {
            return new Call(source);
        }

        @Override
        public Call[] newArray(int size) {
            return new Call[size];
        }
    };
}
