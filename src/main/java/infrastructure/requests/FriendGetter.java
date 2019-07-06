package infrastructure.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import infrastructure.models.MutualFriendsContainer;
import infrastructure.models.PersonContainer;
import infrastructure.config.Config;
import infrastructure.interfaces.IFriendGetter;
import infrastructure.interfaces.IHttpSenter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.StringReader;
import java.util.ArrayList;

public class FriendGetter implements IFriendGetter {
    private Logger logger;
    private IHttpSenter requestSenter;

    @Inject
    public FriendGetter(IHttpSenter requestSenter) {
        logger = LogManager.getLogger(getClass());
        this.requestSenter = requestSenter;
    }

    @Override
    public PersonContainer getFriends(long userId, String accessToken) throws Exception {
        if (accessToken == null) {
            logger.error("Access token is null");
            throw new Exception("Access token is null");
        }
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

    @Override
    public MutualFriendsContainer getMutualFriends(long sourceId, ArrayList<Long> targetIds, String accessToken) throws Exception {
        if (accessToken == null) {
            logger.error("Access token is null");
            throw new Exception("Access token is null");
        }
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

    private String createQueryToGetFriends(long userId, String accessToken) {
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

    private String createQueryToGetMutualFriends(long sourceId, ArrayList<Long> targetIds, String accessToken) {
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
