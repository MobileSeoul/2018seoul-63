package todday.funny.seoulcatcher.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MemberShip implements Parcelable {
    private String name;
    private String logoUrl;
    private String color;
    private int discountRate;

    public MemberShip(String name, String color, int discountRate) {
        this.name = name;
        this.color = color;
        this.discountRate = discountRate;
    }

    public MemberShip(String name, String logoUrl, String color, int discountRate) {
        this.name = name;
        this.logoUrl = logoUrl;
        this.color = color;
        this.discountRate = discountRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(int discountRate) {
        this.discountRate = discountRate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.logoUrl);
        dest.writeInt(this.discountRate);
    }

    public MemberShip() {
    }

    protected MemberShip(Parcel in) {
        this.name = in.readString();
        this.logoUrl = in.readString();
        this.discountRate = in.readInt();
    }

    public static final Parcelable.Creator<MemberShip> CREATOR = new Parcelable.Creator<MemberShip>() {
        @Override
        public MemberShip createFromParcel(Parcel source) {
            return new MemberShip(source);
        }

        @Override
        public MemberShip[] newArray(int size) {
            return new MemberShip[size];
        }
    };
}
