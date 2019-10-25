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
    @Size(min=3, max=15)
    private String word;

    @Id
    @GeneratedValue
    private int id;

    @ManyToMany
    private List<Hebrew> hebrew_words;

    public English() {}

    public English(String word) {
        this.word = word;
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
