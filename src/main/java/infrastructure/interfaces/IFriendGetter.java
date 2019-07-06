package infrastructure.interfaces;

import infrastructure.Models.MutualFriendsContainer;
import infrastructure.Models.PersonContainer;

import java.util.ArrayList;

public interface IFriendGetter {
    PersonContainer getFriends(long userId, String accessToken) throws Exception;
    MutualFriendsContainer getMutualFriends(long sourceId, ArrayList<Long> targetIds, String accessToken) throws Exception;
}
