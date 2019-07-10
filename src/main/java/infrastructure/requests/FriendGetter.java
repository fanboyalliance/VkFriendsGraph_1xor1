package infrastructure.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import infrastructure.models.*;
import infrastructure.config.Config;
import infrastructure.interfaces.IFriendGetter;
import infrastructure.interfaces.IHttpSenter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

public class FriendGetter implements IFriendGetter {
    private Logger logger;
    private IHttpSenter requestSenter;

    public FriendGetter() {
        logger = LogManager.getLogger(getClass());
        this.requestSenter = new HttpSenter();
    }

    @Override
    public PersonContainer getFriends(long userId,@NotNull String accessToken) throws Exception {
        var query = createQueryToGetFriends(userId, accessToken);
        logger.info("Created query string to get friends for userId " + userId);
        try {
            logger.info("Trying to sent get query");
            var response = requestSenter.getRequest(query);
            logger.info("Response was got: " + response);
            var objectMapper = new ObjectMapper();
            var node = new ObjectMapper().readTree(new StringReader(response));
            var friends = objectMapper.readValue(node.get("response").toString(), PersonContainer.class);
            logger.info("PersonContainer has " + friends.getPersons().size() + " persons");

            return friends;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception();
        }
    }

    private MutualFriendsContainer getMutualFriendsFromApi(long sourceId,@NotNull ArrayList<Long> targetIds,@NotNull String accessToken) throws Exception {
        if (targetIds.size() > 100) {
            // TODO: change later
            targetIds.remove(100);
        }
        var query = createQueryToGetMutualFriends(sourceId, targetIds, accessToken);
        logger.info("Created query string to get mutual friends for sourceId " + sourceId
                + " and " + targetIds.size() + " numbers targetIdz");
        try {
            logger.info("Trying to sent get query");
            var response = requestSenter.getRequest(query);
            logger.info("Response was got: " + response);
            var objectMapper = new ObjectMapper();
            var friends = objectMapper.readValue(response, MutualFriendsContainer.class);
            logger.info("MutualFriendsContainer has " + friends.getMutualFriends().size() + " persons");

            return friends;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception();
        }
    }

    @Override
    public HashMap<Long, ArrayList<MinPersonDTO>>  getMutualFriends(long sourceId,@NotNull ArrayList<MinPersonDTO> container,@NotNull String accessToken) throws Exception {
        HashMap<Long, ArrayList<MinPersonDTO>> map = new HashMap<>();

        for (int i = 0; i < container.size(); i++) {
            var firstId = container.get(i).id;
            ArrayList<Long> arrayListId = (ArrayList<Long>) container.stream().map(x -> x.id).collect(Collectors.toList());
            var friends = getMutualFriendsFromApi(firstId, arrayListId, accessToken);
            ArrayList<MinPersonDTO> helperList = new ArrayList<>();
            for (int j = 0; j < friends.getMutualFriends().size(); j++) {
                if (firstId == friends.getMutualFriends().get(j).getId()) {
                    // when itself
                    continue;
                }
                var minPerson = new MinPersonDTO();
                var mutualFriend = friends.getMutualFriends().get(j);
                minPerson.friendId = mutualFriend.getId();
                minPerson.weight = mutualFriend.getCommonCount();
                helperList.add(minPerson);
            }
            map.put(firstId, helperList);

            //Delay for requests
            Thread.sleep(1000);
        }

        return map;
    }

    private String createQueryToGetFriends(long userId, @NotNull String accessToken) {
        StringBuilder sb = new StringBuilder();
        sb.append(Config.API_BASE_POINT);
        sb.append(Config.GET_FRIENDS);
        sb.append("?");
        sb.append("v=").append(Config.VERSION);
        sb.append("&user_id=").append(userId);
        sb.append("&fields=photo_100,online");
        sb.append("&access_token=").append(accessToken);

        return sb.toString();
    }

    private String createQueryToGetMutualFriends(long sourceId, @NotNull ArrayList<Long> targetIds, @NotNull String accessToken) {
        StringBuilder sb = new StringBuilder();
        sb.append(Config.API_BASE_POINT);
        sb.append(Config.GET_MUTUAL_FRIENDS);
        sb.append("?");
        sb.append("&v=").append(Config.VERSION);
        sb.append("&source_uid=").append(sourceId);
        sb.append("&access_token=").append(accessToken);
        sb.append("&target_uids=");
        for (int i = 0; i < targetIds.size(); i++) {
            sb.append(targetIds.get(i));
            if (i != targetIds.size() - 1) {
                sb.append(",");
            }
        }

        return sb.toString();
    }
}
