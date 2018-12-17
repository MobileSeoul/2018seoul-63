package todday.funny.seoulcatcher.model.routeModel;

import android.os.Parcel;
import android.os.Parcelable;

public class RouteProperties implements Parcelable {
    private int totalDistance;
    private int totalTime;
    private int index;
    private int pointIndex;
    private String name;
    private String description;
    private String direction;
    private String nearPoiName;
    private String nearPoiX;
    private String nearPoiY;
    private String intersectionName;
    private String facilityType;
    private String facilityName;
    private String turnType;
    private String pointType;

    public int getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(int totalDistance) {
        this.totalDistance = totalDistance;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPointIndex() {
        return pointIndex;
    }

    public void setPointIndex(int pointIndex) {
        this.pointIndex = pointIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getNearPoiName() {
        return nearPoiName;
    }

    public void setNearPoiName(String nearPoiName) {
        this.nearPoiName = nearPoiName;
    }

    public String getNearPoiX() {
        return nearPoiX;
    }

    public void setNearPoiX(String nearPoiX) {
        this.nearPoiX = nearPoiX;
    }

    public String getNearPoiY() {
        return nearPoiY;
    }

    public void setNearPoiY(String nearPoiY) {
        this.nearPoiY = nearPoiY;
    }

    public String getIntersectionName() {
        return intersectionName;
    }

    public void setIntersectionName(String intersectionName) {
        this.intersectionName = intersectionName;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getTurnType() {
        return turnType;
    }

    public void setTurnType(String turnType) {
        this.turnType = turnType;
    }

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.totalDistance);
        dest.writeInt(this.totalTime);
        dest.writeInt(this.index);
        dest.writeInt(this.pointIndex);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.direction);
        dest.writeString(this.nearPoiName);
        dest.writeString(this.nearPoiX);
        dest.writeString(this.nearPoiY);
        dest.writeString(this.intersectionName);
        dest.writeString(this.facilityType);
        dest.writeString(this.facilityName);
        dest.writeString(this.turnType);
        dest.writeString(this.pointType);
    }

    public RouteProperties() {
    }

    protected RouteProperties(Parcel in) {
        this.totalDistance = in.readInt();
        this.totalTime = in.readInt();
        this.index = in.readInt();
        this.pointIndex = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.direction = in.readString();
        this.nearPoiName = in.readString();
        this.nearPoiX = in.readString();
        this.nearPoiY = in.readString();
        this.intersectionName = in.readString();
        this.facilityType = in.readString();
        this.facilityName = in.readString();
        this.turnType = in.readString();
        this.pointType = in.readString();
    }

    public static final Parcelable.Creator<RouteProperties> CREATOR = new Parcelable.Creator<RouteProperties>() {
        @Override
        public RouteProperties createFromParcel(Parcel source) {
            return new RouteProperties(source);
        }

        @Override
        public RouteProperties[] newArray(int size) {
            return new RouteProperties[size];
        }
    };
}
