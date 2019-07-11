package infrastructure.logic;

import infrastructure.config.Config;
import infrastructure.interfaces.IFriendGetter;
import infrastructure.interfaces.IFriendGraphGetter;
import infrastructure.interfaces.IGuiConnector;
import infrastructure.models.MinPersonDTO;
import infrastructure.models.PersonContainer;
import infrastructure.requests.FriendGetter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

public class GuiConnector implements IGuiConnector {
    private IFriendGetter friendGetter;
    private IFriendGraphGetter mutualFiendGraph;
    private Logger logger;
    private static HashMap<Long, ArrayList<MinPersonDTO>> mutualFriends;

    public GuiConnector() {
        friendGetter = new FriendGetter();
        mutualFiendGraph = new FriendGraphGetter();
        logger  = LogManager.getLogger(getClass());
    }

    public PersonContainer getFriends() throws Exception {
        try {
            var personContainer = friendGetter.getFriends(Config.USER_ID, Config.API_TOKEN);
            for (int i = 0; i < personContainer.getPersons().size(); i++) {
                var url = personContainer.getPersons().get(i).getPhotoUri();
                var regex = "(\\\\/)";
                url = url.replaceAll(regex, "/");
                personContainer.getPersons().get(i).setPhotoUri(url);
            }
            logger.info("all photo uri have just been replaced with regex");

            return personContainer;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception();
        }
    }

    public ArrayList<MinPersonDTO> getStartGraph(ArrayList<MinPersonDTO> minPersonDto) throws Exception {
        try {
            mutualFriends = friendGetter.getMutualFriends(Config.USER_ID, minPersonDto, Config.API_TOKEN);

            return mutualFiendGraph.getEdgesForStartGraph(mutualFriends);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception();
        }
    }

    public ArrayList<MinPersonDTO> getMaxTree() throws Exception {
        try {
            if (mutualFriends == null) {
                throw new Exception("Mutual friends are empty");
            }

            return mutualFiendGraph.getEdgesForMinTree(mutualFriends);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception();
        }
    }
}
