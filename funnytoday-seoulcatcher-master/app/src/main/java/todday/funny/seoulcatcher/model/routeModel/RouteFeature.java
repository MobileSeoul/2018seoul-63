package todday.funny.seoulcatcher.model.routeModel;

import android.os.Parcel;
import android.os.Parcelable;

public class RouteFeature implements Parcelable {
    private String type;
    private RouteGeometry geometry;
    private RouteProperties properties;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RouteGeometry getGeometry() {
        return geometry;
    }

    public void setGeometry(RouteGeometry geometry) {
        this.geometry = geometry;
    }

    public RouteProperties getProperties() {
        return properties;
    }

    public void setProperties(RouteProperties properties) {
        this.properties = properties;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeParcelable(this.geometry, flags);
        dest.writeParcelable(this.properties, flags);
    }

    public RouteFeature() {
    }

    protected RouteFeature(Parcel in) {
        this.type = in.readString();
        this.geometry = in.readParcelable(RouteGeometry.class.getClassLoader());
        this.properties = in.readParcelable(RouteProperties.class.getClassLoader());
    }

    public static final Parcelable.Creator<RouteFeature> CREATOR = new Parcelable.Creator<RouteFeature>() {
        @Override
        public RouteFeature createFromParcel(Parcel source) {
            return new RouteFeature(source);
        }

        @Override
        public RouteFeature[] newArray(int size) {
            return new RouteFeature[size];
        }
    };
}
