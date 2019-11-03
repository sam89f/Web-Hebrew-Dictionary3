package org.launchcode.models.forms;
import org.launchcode.models.English;
import org.launchcode.models.Hebrew;
import javax.validation.constraints.NotNull;

public class AddHebrewItemForm
{
    @NotNull
    private int englishId;

    @NotNull
    private int hebrewId;

    private Hebrew hebrew;

    private Iterable<English> english_words;

    public AddHebrewItemForm() {
    }

    public AddHebrewItemForm(Hebrew hebrew, Iterable<English> english_words) {
        this.hebrew = hebrew;
        this.english_words = english_words;
    }

    public int getEnglishId() {
        return englishId;
    }

    public void setEnglishId(int englishId) {
        this.englishId = englishId;
    }

    public int getHebrewId() {
        return hebrewId;
    }

    public void setHebrewId(int hebrewId) {
        this.hebrewId = hebrewId;
    }

    public Hebrew getHebrew() {
        return hebrew;
    }

    public void setHebrew(Hebrew hebrew) {
        this.hebrew = hebrew;
    }

    public Iterable<English> getEnglish_words() {
        return english_words;
    }

    public void setEnglish_words(Iterable<English> english_words) {
        this.english_words = english_words;
    }
}
