package infrastructure.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MutualFriendsContainer {
    private List<MutualFriends> mutualFriends;

    public List<MutualFriends> getMutualFriends() {
        return mutualFriends;
    }

    @JsonSetter("response")
    public void setMutualFriends(ArrayList<MutualFriends> mutualFriends) {
        this.mutualFriends = mutualFriends;
    }

}
