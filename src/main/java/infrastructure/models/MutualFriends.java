package infrastructure.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MutualFriends {
    private long id;
    private List<Long> commonFriendsIds;
    private long commonCount;

    @JsonSetter("id")
    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public List<Long> getCommonFriendsIds() {
        return commonFriendsIds;
    }

    public long getCommonCount() {
        return commonCount;
    }

    @JsonSetter("common_friends")
    public void setCommonFriendsIds(ArrayList<Long> commonFriends) {
        this.commonFriendsIds = commonFriends;
    }

    @JsonSetter("common_count")
    public void setCommonCount(long commonCount) {
        this.commonCount = commonCount;
    }
}
