package infrastructure.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {
    private String firstName;
    private String lastName;
    private long id;
    private boolean isClosed;
    private boolean canAccessClosed;
    private String photoUri;
    private boolean isOnline;
    private String trackCode;

    public String getFirstName() {
        return firstName;
    }

    @JsonSetter("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @JsonSetter("last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    @JsonSetter("id")
    public void setId(long id) {
        this.id = id;
    }

    public boolean isClosed() {
        return isClosed;
    }

    @JsonSetter("is_closed")
    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public boolean isCanAccessClosed() {
        return canAccessClosed;
    }

    @JsonSetter("can_access_closed")
    public void setCanAccessClosed(boolean canAccessClosed) {
        this.canAccessClosed = canAccessClosed;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    @JsonSetter("photo_100")
    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public boolean isOnline() {
        return isOnline;
    }

    @JsonSetter("online")
    public void setOnline(int online) {
        isOnline = online == 1;
    }

    public String getTrackCode() {
        return trackCode;
    }

    @JsonSetter("track_code")
    public void setTrackCode(String trackCode) {
        this.trackCode = trackCode;
    }
}
