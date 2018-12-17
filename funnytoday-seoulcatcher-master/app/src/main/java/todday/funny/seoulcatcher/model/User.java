package todday.funny.seoulcatcher.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseUser;

import todday.funny.seoulcatcher.util.Keys;

public class User implements Parcelable {
    private String id;
    private String name;
    private String nickName;
    private String email;
    private String phoneNumber;
    private String photoUrl;
    private String backgroundUrl;
    private String level;
    private boolean emailAuth;

    public void setUser(FirebaseUser firebaseUser) {
        setId(firebaseUser.getUid());
        setEmail(firebaseUser.getEmail());
        setPhoneNumber(firebaseUser.getPhoneNumber());
        setLevel("0");
        Uri uri = firebaseUser.getPhotoUrl();
        if (uri != null) {
            setPhotoUrl(String.valueOf(firebaseUser.getPhotoUrl()));
        } else {
            setPhotoUrl(Keys.DEFAULT_USER_PROFILE_URL);
        }
        setBackgroundUrl(Keys.DEFAULT_USER_BACKGROUND_URL);
        if (email != null && email.length() > 0) {
            String[] split = email.split("@");
            for (String s : split) {
                setNickName(s);
                setName(s);
                break;
            }
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public boolean isEmailAuth() {
        return emailAuth;
    }

    public void setEmailAuth(boolean emailAuth) {
        this.emailAuth = emailAuth;
    }

    public User() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.nickName);
        dest.writeString(this.email);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.photoUrl);
        dest.writeString(this.backgroundUrl);
        dest.writeString(this.level);
        dest.writeByte(this.emailAuth ? (byte) 1 : (byte) 0);
    }

    protected User(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.nickName = in.readString();
        this.email = in.readString();
        this.phoneNumber = in.readString();
        this.photoUrl = in.readString();
        this.backgroundUrl = in.readString();
        this.level = in.readString();
        this.emailAuth = in.readByte() != 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
