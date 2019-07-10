package infrastructure.interfaces;

import infrastructure.models.MinPersonDTO;
import infrastructure.models.PersonContainer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public interface IFriendGetter {
    PersonContainer getFriends(long userId,@NotNull String accessToken) throws Exception;
    HashMap<Long, ArrayList<MinPersonDTO>> getMutualFriends(long sourceId, @NotNull ArrayList<MinPersonDTO> container, @NotNull String accessToken) throws Exception;
}
