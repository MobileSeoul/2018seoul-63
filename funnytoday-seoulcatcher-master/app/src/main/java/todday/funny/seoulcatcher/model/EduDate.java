package todday.funny.seoulcatcher.model;

import android.os.Parcel;
import android.os.Parcelable;

public class EduDate implements Parcelable{

    private String date;
    private String location;

    public EduDate() {
    }

    public EduDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return location;
    }

    public void setName(String name) {
        this.location = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeString(this.location);
    }

    protected EduDate(Parcel in) {
        this.date = in.readString();
        this.location = in.readString();
    }

    public static final Parcelable.Creator<EduDate> CREATOR = new Parcelable.Creator<EduDate>() {
        @Override
        public EduDate createFromParcel(Parcel source) {
            return new EduDate(source);
        }

        @Override
        public EduDate[] newArray(int size) {
            return new EduDate[size];
        }
    };
}
