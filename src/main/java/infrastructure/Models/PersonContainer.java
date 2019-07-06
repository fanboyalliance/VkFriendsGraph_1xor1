package infrastructure.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonContainer {
    private List<Person> person;

    public List<Person> getPerson() {
        return person;
    }

    @JsonSetter("items")
    public void setPerson(ArrayList<Person> person) {
        this.person = person;
    }
}
