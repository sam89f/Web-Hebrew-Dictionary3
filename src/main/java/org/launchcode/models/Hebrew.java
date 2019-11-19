package org.launchcode.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by LaunchCode
 */
@Entity
public class Hebrew {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=1, max=15)
    private String word;

    @NotNull
    @Size(min=1, message = "Description must not be empty")
    private String description;

    @ManyToMany(mappedBy = "hebrew_words")
    private List<English> english_words;

    public Hebrew(String word, String description) {
        this.word = word;
        this.description = description;
    }
    public Hebrew(String word, String description, int id) {
        this.word = word;
        this.description = description;
        this.id = id;
    }
    public Hebrew() { }

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addItem(English item)
    {
        english_words.add(item);
    }

    public void RemoveItem(English item)
    {
        english_words.remove(item);
    }

    public List<English> getEnglish_words() {
        return english_words;
    }
}
