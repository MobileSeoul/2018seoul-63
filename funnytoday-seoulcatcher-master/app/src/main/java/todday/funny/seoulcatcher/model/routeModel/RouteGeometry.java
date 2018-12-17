package todday.funny.seoulcatcher.model.routeModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class RouteGeometry implements Parcelable {
    private String type; //Point ,LineString,  Polygon
    private List<Object> coordinates; //좌표정보

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Object> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Object> coordinates) {
        this.coordinates = coordinates;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeList(this.coordinates);
    }

    public RouteGeometry() {
    }

    protected RouteGeometry(Parcel in) {
        this.type = in.readString();
        this.coordinates = new ArrayList<Object>();
        in.readList(this.coordinates, Object.class.getClassLoader());
    }

    public static final Parcelable.Creator<RouteGeometry> CREATOR = new Parcelable.Creator<RouteGeometry>() {
        @Override
        public RouteGeometry createFromParcel(Parcel source) {
            return new RouteGeometry(source);
        }

        @Override
        public RouteGeometry[] newArray(int size) {
            return new RouteGeometry[size];
        }
    };
}
