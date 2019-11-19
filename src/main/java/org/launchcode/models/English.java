package org.launchcode.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class English {

    @NotNull
    @Size(min=1, max=15)
    private String word;

    @NotNull
    @Size(min=1, message = "Definition must not be empty")
    private String definition;

    @Id
    @GeneratedValue
    private int id;

    @ManyToMany
    private List<Hebrew> hebrew_words;

    public English() {}

    public English(String word, String definition)
    {
        this.word = word;
        this.definition = definition;
    }
    public English(String word, String definition, int id)
    {
        this.word = word;
        this.definition = definition;
        this.id = id;
    }
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public List<Hebrew> getHebrew_words() {
        return hebrew_words;
    }

    public void addItem(Hebrew item)
    {
        hebrew_words.add(item);
    }
    public void RemoveItem(Hebrew item)
    {
        hebrew_words.remove(item);
    }
}
