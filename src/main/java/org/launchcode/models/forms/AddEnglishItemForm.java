package org.launchcode.models.forms;

import org.launchcode.models.English;
import org.launchcode.models.Hebrew;

import javax.validation.constraints.NotNull;

public class AddEnglishItemForm
{
    @NotNull
    private int englishId;

    @NotNull
    private int hebrewId;

    private English english;

    private Iterable<Hebrew> hebrew_words;

    public AddEnglishItemForm() {
    }

    public AddEnglishItemForm(English english, Iterable<Hebrew> hebrew_words) {
        this.english = english;
        this.hebrew_words = hebrew_words;
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

    public English getEnglish() {
        return english;
    }

    public void setEnglish(English english) {
        this.english = english;
    }

    public Iterable<Hebrew> getHebrew_words() {
        return hebrew_words;
    }

    public void setHebrew_words(Iterable<Hebrew> hebrew_words) {
        this.hebrew_words = hebrew_words;
    }
}
