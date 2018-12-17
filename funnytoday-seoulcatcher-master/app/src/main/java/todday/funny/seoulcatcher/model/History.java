package todday.funny.seoulcatcher.model;

import android.os.Parcel;
import android.os.Parcelable;

public class History implements Parcelable {
    private String id;
    private long date;
    private Call call;
    private User user;
    private Schedule schedule;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Call getCall() {
        return call;
    }

    public void setCall(Call call) {
        this.call = call;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }


    public History() {
    }

    public History(Call call) {
        this.call = call;
    }

    public History(User user) {
        this.user = user;
    }

    public History(Schedule schedule) {
        this.schedule = schedule;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeLong(this.date);
        dest.writeParcelable(this.call, flags);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.schedule, flags);
    }

    protected History(Parcel in) {
        this.id = in.readString();
        this.date = in.readLong();
        this.call = in.readParcelable(Call.class.getClassLoader());
        this.user = in.readParcelable(User.class.getClassLoader());
        this.schedule = in.readParcelable(Schedule.class.getClassLoader());
    }

    public static final Creator<History> CREATOR = new Creator<History>() {
        @Override
        public History createFromParcel(Parcel source) {
            return new History(source);
        }

        @Override
        public History[] newArray(int size) {
            return new History[size];
        }
    };
}
