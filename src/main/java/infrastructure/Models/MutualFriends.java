package infrastructure.Models;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;
import java.util.List;

public class MutualFriends {
    private List<Long> ids;

    public List<Long> getIds() {
        return ids;
    }

    @JsonSetter("response")
    public void setIds(ArrayList<Long> ids) {
        this.ids = ids;
    }
}
