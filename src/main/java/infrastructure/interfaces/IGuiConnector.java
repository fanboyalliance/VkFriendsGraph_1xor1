package infrastructure.interfaces;

import infrastructure.models.MinPersonDTO;
import infrastructure.models.PersonContainer;

import java.util.ArrayList;

public interface IGuiConnector {
    PersonContainer getFriends() throws Exception;
    ArrayList<MinPersonDTO> getStartGraph(ArrayList<MinPersonDTO> minPersonDto) throws Exception;
    ArrayList<MinPersonDTO> getMaxTree() throws Exception;
}
