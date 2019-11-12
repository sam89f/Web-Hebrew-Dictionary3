package org.launchcode.controllers;


import org.launchcode.models.English;
import org.launchcode.models.Hebrew;
import org.launchcode.models.data.EnglishDao;
import org.launchcode.models.data.HebrewDao;
import org.launchcode.models.forms.AddEnglishItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("english")
public class EnglishController
{
    @Autowired
    private HebrewDao hebrewDao;

    @Autowired
    private EnglishDao englishDao;

    // Request path: /english
    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("english_words", englishDao.findAll());
        model.addAttribute("title", "English Words");

        return "english/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {

        model.addAttribute("title", "Add Word");
        model.addAttribute(new English());

        return "english/add";
    }
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid English english,
                      Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Word");
            return "english/add";
        }
        for(English w: englishDao.findAll())
        {
            if(w.getWord().equals(english.getWord()))
            {
                model.addAttribute("title", "Add Word");
                return "english/add";
            }
        }

        englishDao.save(english);
        return "redirect:view/" + english.getId();
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveEnglishForm(Model model) {
        model.addAttribute("english_words", englishDao.findAll());
        model.addAttribute("title", "Remove Word");
        return "english/remove";
    }
    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveEnglishForm(@RequestParam int[] englishIds) {

        for (int englishId : englishIds) {
            englishDao.delete(englishId);
        }

        return "redirect:";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String displaySearchForm(Model model) {
        model.addAttribute("title", "Search English Word");


        return "english/search";
    }
    @RequestMapping(value = "search", method = RequestMethod.POST)
    public String processSearchForm(Model model, @RequestParam String newEnglish) {

        model.addAttribute("title", "Search English Word");
        model.addAttribute("last", "newEnglish");
        System.out.println("*****" + newEnglish + "******");

        for(English w : englishDao.findAll())
        {
            if(newEnglish.equals(w.getWord()))
            {
                System.out.println("*****" + w.getWord() + "******");
                model.addAttribute("result", w);
                break;
            }
        }

        return "english/search";
    }

    @RequestMapping(value = "view/{englishId}", method = RequestMethod.GET)
    public String viewEnglish(Model model, @PathVariable int englishId)
    {
        English english = englishDao.findOne(englishId);
        String title = english.getWord() + ": " + english.getDefinition();
        model.addAttribute("title", title);
        model.addAttribute("english", english);

        return "english/view";
    }

    @RequestMapping(value = "edit/{englishId}", method = RequestMethod.GET)
    public String editEnglish(Model model, @PathVariable int englishId)
    {
        English english = englishDao.findOne(englishId);

        model.addAttribute("title", english.getWord());
        model.addAttribute("english", english);

        return "english/edit";
    }
    @RequestMapping(value = "edit/{englishId}", method = RequestMethod.POST)
    public String editEnglish(@RequestParam String Def, @PathVariable int englishId) {

        English englishWord = englishDao.findOne(englishId);
        englishWord.setDefinition(Def);

        System.out.println("*****" + Def + "******");
        englishDao.save(englishWord);

        return "redirect:/english/view/" + englishWord.getId();
    }

    @RequestMapping(value = "add-new/{englishId}", method = RequestMethod.GET)
    public String addNew(Model model, @PathVariable int englishId)
    {
        English english = englishDao.findOne(englishId);

        model.addAttribute("title", english.getWord());
        model.addAttribute("english", english);

        return "english/add-new";
    }
    @RequestMapping(value = "add-new/{englishId}", method = RequestMethod.POST)
    public String addNew(@RequestParam String word, @RequestParam String description, @PathVariable int englishId) {

        English englishWord = englishDao.findOne(englishId);
        Hebrew newWord = new Hebrew(word, description);

        System.out.println("*****" + word + "******");

        for(Hebrew w : hebrewDao.findAll())
        {
            if(word.equals(w.getWord()))
            {
                return "redirect:/english/view/" + englishWord.getId();
            }
        }
        hebrewDao.save(newWord);
        for(Hebrew he : hebrewDao.findAll())
        {
            if(word.equals(he.getWord()))
            {
                englishWord.addItem(he);
                englishDao.save(englishWord);
            }
        }
        return "redirect:/english/view/" + englishWord.getId();
    }

    @RequestMapping(value = "add-item/{englishId}", method = RequestMethod.GET)
    public String addItem(Model model, @PathVariable int englishId)
    {
        English english = englishDao.findOne(englishId);

        AddEnglishItemForm form = new AddEnglishItemForm(english, hebrewDao.findAll());

        model.addAttribute("title", "Add Item to word: " + english.getWord());
        model.addAttribute("form", form);

        return "english/add-item";
    }
    @RequestMapping(value = "add-item", method = RequestMethod.POST)
    public String addItem(Model model, @ModelAttribute @Valid AddEnglishItemForm form, Errors errors)
    {
        Hebrew hebrewWord = hebrewDao.findOne(form.getHebrewId());
        English englishWord = englishDao.findOne(form.getEnglishId());

        System.out.println("*****" +englishWord.getHebrew_words().size() + "*****");

        for(Hebrew h : englishWord.getHebrew_words())
        {
            if(h.equals(hebrewWord))
            {
                return "redirect:/english/view/" + englishWord.getId();
            }
        }
        englishWord.addItem(hebrewWord);
        englishDao.save(englishWord);

        return "redirect:/english/view/" + englishWord.getId();
    }

    @RequestMapping(value = "remove-item/{englishId}", method = RequestMethod.GET)
    public String displayRemoveItem(Model model, @PathVariable int englishId)
    {
        English english = englishDao.findOne(englishId);

        AddEnglishItemForm form = new AddEnglishItemForm(english, english.getHebrew_words());

        model.addAttribute("title", "Remove Item from word: " + english.getWord());
        model.addAttribute("form", form);
        model.addAttribute("word_list", english.getHebrew_words());

        return "english/remove-item";
    }
    @RequestMapping(value = "remove-item", method = RequestMethod.POST)
    public String processRemoveItem(Model model, @ModelAttribute @Valid AddEnglishItemForm form, Errors errors)
    {
        Hebrew hebrewWord = hebrewDao.findOne(form.getHebrewId());
        English englishWord = englishDao.findOne(form.getEnglishId());

        englishWord.RemoveItem(hebrewWord);
        englishDao.save(englishWord);

        for(Hebrew w : englishWord.getHebrew_words())
        {
            System.out.println("+++++" + w.getWord() + "+++++");
        }

        return "redirect:/english/view/" + englishWord.getId();
    }
}


