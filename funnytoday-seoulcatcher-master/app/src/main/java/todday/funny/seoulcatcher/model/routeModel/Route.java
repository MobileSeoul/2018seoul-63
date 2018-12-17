package todday.funny.seoulcatcher.model.routeModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Route implements Parcelable {
    private String type;
    private ArrayList<RouteFeature> features;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<RouteFeature> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<RouteFeature> features) {
        this.features = features;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeList(this.features);
    }

    public Route() {
    }

    protected Route(Parcel in) {
        this.type = in.readString();
        this.features = new ArrayList<RouteFeature>();
        in.readList(this.features, RouteFeature.class.getClassLoader());
    }

    public static final Parcelable.Creator<Route> CREATOR = new Parcelable.Creator<Route>() {
        @Override
        public Route createFromParcel(Parcel source) {
            return new Route(source);
        }

        @Override
        public Route[] newArray(int size) {
            return new Route[size];
        }
    };
}
