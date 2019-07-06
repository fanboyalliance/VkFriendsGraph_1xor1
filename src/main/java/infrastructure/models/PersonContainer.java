package infrastructure.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonContainer {
    private List<Person> persons;

    public List<Person> getPersons() {
        return persons;
    }

    @JsonSetter("items")
    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }
}
