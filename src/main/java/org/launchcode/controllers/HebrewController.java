package org.launchcode.controllers;

import org.launchcode.models.English;
import org.launchcode.models.Hebrew;
import org.launchcode.models.data.EnglishDao;
import org.launchcode.models.data.HebrewDao;
import org.launchcode.models.forms.AddEnglishItemForm;
import org.launchcode.models.forms.AddHebrewItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("hebrew")
public class HebrewController {

    @Autowired
    private EnglishDao englishDao;

    @Autowired
    private HebrewDao hebrewDao;

    // Request path: /hebrew
    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("hebrew_words", hebrewDao.findAll());
        model.addAttribute("title", "Hebrew Words");

        return "hebrew/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddHebrewForm(Model model) {
        model.addAttribute("title", "Add Word");
        model.addAttribute(new Hebrew());

        return "hebrew/add";
    }
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAdHebrewForm(@ModelAttribute  @Valid Hebrew newHebrew,
                                       Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Word");
            return "hebrew/add";
        }

        for(Hebrew w : hebrewDao.findAll())
        {
            if(w.getWord().equals(newHebrew.getWord()))
            {
                model.addAttribute("title", "Add Word");
                return "hebrew/add";
            }
        }

        hebrewDao.save(newHebrew);
        return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveHebrewForm(Model model) {
        model.addAttribute("hebrew_words", hebrewDao.findAll());
        model.addAttribute("title", "Remove Word");
        return "hebrew/remove";
    }
    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveHebrewForm(@RequestParam int[] hebrewIds) {

        for (int hebrewId : hebrewIds)
        {
            Hebrew w = hebrewDao.findOne(hebrewId);
            if(w.getEnglish_words().size() > 0)
            {
                for(English x: w.getEnglish_words())
                {
                    x.RemoveItem(w);
                }
            }
            hebrewDao.delete(hebrewId);
        }

        return "redirect:";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String displaySearchForm(Model model) {
        model.addAttribute("title", "Search Hebrew Word");


        return "hebrew/search";
    }
    @RequestMapping(value = "search", method = RequestMethod.POST)
    public String processSearchForm(Model model, @RequestParam String newHebrew) {

        model.addAttribute("title", "Search Hebrew Word");
        model.addAttribute("last", "newHebrew");
        System.out.println("*****" + newHebrew + "******");

        for(Hebrew w : hebrewDao.findAll())
        {
            if(newHebrew.equals(w.getWord()))
            {
                System.out.println("*****" + w.getWord() + "******");
                model.addAttribute("resultW", w.getWord());
                model.addAttribute("resultID", w.getId());
                model.addAttribute("resultD", w.getDescription());
                System.out.println("-english words:-");
                for(English x : w.getEnglish_words())
                {
                    System.out.println("-----" + x.getWord() + "-----");
                }
                break;
            }
        }

        return "hebrew/search";
    }

    @RequestMapping(value = "view/{hebrewId}", method = RequestMethod.GET)
    public String viewHebrew(Model model, @PathVariable int hebrewId)
    {
        Hebrew hebrew = hebrewDao.findOne(hebrewId);

        String title = hebrew.getWord() + ": " + hebrew.getDescription();

        model.addAttribute("title", title);
        model.addAttribute("hebrew", hebrew);

        return "hebrew/view";
    }

    @RequestMapping(value = "edit/{hebrewId}", method = RequestMethod.GET)
    public String editHebrew(Model model, @PathVariable int hebrewId)
    {
        Hebrew hebrew = hebrewDao.findOne(hebrewId);

        model.addAttribute("title", hebrew.getWord());
        model.addAttribute("hebrew", hebrew);
        //model.addAttribute(new Hebrew());

        return "hebrew/edit";
    }
    @RequestMapping(value = "edit/{hebrewId}", method = RequestMethod.POST)
    public String editHebrew(@RequestParam String Des, @PathVariable int hebrewId) {

        Hebrew hebrewWord = hebrewDao.findOne(hebrewId);
        hebrewWord.setDescription(Des);

        System.out.println("*****" + Des + "******");
        hebrewDao.save(hebrewWord);

        return "redirect:/hebrew/view/" + hebrewWord.getId();
    }

    @RequestMapping(value = "add-new/{hebrewId}", method = RequestMethod.GET)
    public String addNew(Model model, @PathVariable int hebrewId)
    {
        Hebrew hebrew = hebrewDao.findOne(hebrewId);

        model.addAttribute("title", hebrew.getWord());
        model.addAttribute("hebrew", hebrew);

        return "hebrew/add-new";
    }
    @RequestMapping(value = "add-new/{hebrewId}", method = RequestMethod.POST)
    public String addNew(@RequestParam String word,@RequestParam String definition, @PathVariable int hebrewId) {

        Hebrew hebrewWord = hebrewDao.findOne(hebrewId);
        English newWord = new English(word, definition);

        System.out.println("*****" + word + "******");

        for(English w : englishDao.findAll())
        {
            if(word.equals(w.getWord()))
            {
                return "redirect:/hebrew/view/" + hebrewWord.getId();
            }
        }
        englishDao.save(newWord);
        for(English en : englishDao.findAll())
        {
            if(word.equals(en.getWord()))
            {
                hebrewWord.addItem(en);
                hebrewDao.save(hebrewWord);
            }
        }
        return "redirect:/hebrew/view/" + hebrewWord.getId();
    }

    @RequestMapping(value = "add-item/{hebrewId}", method = RequestMethod.GET)
    public String addItem(Model model, @PathVariable int hebrewId)
    {
        Hebrew hebrew = hebrewDao.findOne(hebrewId);

        AddHebrewItemForm form = new AddHebrewItemForm(hebrew, englishDao.findAll());

        model.addAttribute("title", "Add Item to word: " + hebrew.getWord());
        model.addAttribute("form", form);

        return "hebrew/add-item";
    }
    @RequestMapping(value = "add-item", method = RequestMethod.POST)
    public String addItem(Model model, @ModelAttribute @Valid AddHebrewItemForm form, Errors errors)
    {
        English englishWord = englishDao.findOne(form.getEnglishId());
        Hebrew hebrewWord = hebrewDao.findOne(form.getHebrewId());

        for(Hebrew h : englishWord.getHebrew_words())
        {
            if(h.equals(hebrewWord))
            {
                return "redirect:/hebrew/view/" + hebrewWord.getId();
            }
        }
        englishWord.addItem(hebrewWord);
        englishDao.save(englishWord);

        return "redirect:/hebrew/view/" + hebrewWord.getId();
    }

    @RequestMapping(value = "remove-item/{hebrewId}", method = RequestMethod.GET)
    public String displayRemoveItem(Model model, @PathVariable int hebrewId)
    {
        Hebrew hebrew = hebrewDao.findOne(hebrewId);

        AddHebrewItemForm form = new AddHebrewItemForm(hebrew, hebrew.getEnglish_words());

        model.addAttribute("title", "Remove Item from word: " + hebrew.getWord());
        model.addAttribute("form", form);
        model.addAttribute("word_list", hebrew.getEnglish_words());

        return "hebrew/remove-item";
    }
    @RequestMapping(value = "remove-item", method = RequestMethod.POST)
    public String processRemoveItem(Model model, @ModelAttribute @Valid AddEnglishItemForm form, Errors errors)
    {
        Hebrew hebrewWord = hebrewDao.findOne(form.getHebrewId());
        English englishWord = englishDao.findOne(form.getEnglishId());

        englishWord.RemoveItem(hebrewWord);
        englishDao.save(englishWord);

        return "redirect:/hebrew/view/" + hebrewWord.getId();
    }
}
