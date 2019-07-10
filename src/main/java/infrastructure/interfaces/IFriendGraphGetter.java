package infrastructure.interfaces;

import infrastructure.models.MinPersonDTO;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public interface IFriendGraphGetter {
    ArrayList<MinPersonDTO> getEdgesForStartGraph(@NotNull HashMap<Long, ArrayList<MinPersonDTO>> friendsByPersonId);
    ArrayList<MinPersonDTO> getEdgesForMinTree(@NotNull HashMap<Long, ArrayList<MinPersonDTO>> friendsByPersonId);
}
