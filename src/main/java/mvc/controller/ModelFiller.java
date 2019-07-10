package mvc.controller;

import infrastructure.models.MinPersonDTO;
import infrastructure.models.PersonContainer;
import mvc.model.Person;

import java.util.ArrayList;

public class ModelFiller {
    public ArrayList<MinPersonDTO> getMinDto(ArrayList<Person> person) {
        var models = new ArrayList<MinPersonDTO>();
        var containerPersons = person;
        for (int i = 0; i < containerPersons.size(); i++) {
            var model = new MinPersonDTO();
            model.id = containerPersons.get(i).id;
            model.friendId = containerPersons.get(i).friendId;
            models.add(model);
        }

        return models;
    }

    public ArrayList<Person> getPersonModel(PersonContainer container) {
        var models = new ArrayList<Person>();
        var containerPersons = container.getPersons();
        for (int i = 0; i < containerPersons.size(); i++) {
            var model = new Person();
            model.firstName = containerPersons.get(i).getFirstName();
            model.lastName = containerPersons.get(i).getLastName();
            model.id = containerPersons.get(i).getId();
            model.photoUri = containerPersons.get(i).getPhotoUri();
            models.add(model);
        }

        return models;
    }

    public ArrayList<Person> getMutualFriends(ArrayList<Person> persons, ArrayList<MinPersonDTO> minPersonDTO) {
        var answer = new ArrayList<Person>();
        for (int i = 0; i < persons.size(); i++) {
            for (int j = 0; j < minPersonDTO.size(); j++) {
                if (persons.get(i).id == minPersonDTO.get(j).id) {
                    var personModel = new Person();
                    personModel.id = persons.get(i).id;
                    personModel.friendId = minPersonDTO.get(j).friendId;
                    personModel.mutualFriendsCount = minPersonDTO.get(j).weight;
                    personModel.photoUri = persons.get(i).photoUri;
                    personModel.lastName = persons.get(i).lastName;
                    personModel.firstName = persons.get(i).firstName;

                    answer.add(personModel);
                }
            }
        }

        return answer;
    }
}
