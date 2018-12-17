package todday.funny.seoulcatcher.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Schedule implements Parcelable {
    private String key = null;
    private String date;
    private String name;

    public Schedule() {
    }

    public Schedule(String key,String date,String location) {
        this.key = key;
        this.date = date;
        this.name = location;
    }
    public Schedule(String date,String location) {

        this.date = date;
        this.name = location;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeString(this.name);
        dest.writeString(this.key);
    }

    protected Schedule(Parcel in) {
        this.date = in.readString();
        this.name = in.readString();
        this.key = in.readString();
    }

    public static final Parcelable.Creator<Schedule> CREATOR = new Parcelable.Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel source) {
            return new Schedule(source);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };
}
