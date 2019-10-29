package org.launchcode.controllers;

import org.launchcode.models.Hebrew;
import org.launchcode.models.data.HebrewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("hebrew")
public class HebrewController {

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

        for (int hebrewId : hebrewIds) {
            hebrewDao.delete(hebrewId);
        }

        return "redirect:";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String displaySearchForm(Model model) {
        model.addAttribute("title", "Search Word");


        return "hebrew/search";
    }
    @RequestMapping(value = "search", method = RequestMethod.POST)
    public String processSearchForm(Model model, @RequestParam String newHebrew) {

        model.addAttribute("title", "Search Word");
        model.addAttribute("last", "newHebrew");
        System.out.println("*****" + newHebrew + "******");

        for(Hebrew w : hebrewDao.findAll())
        {
            if(newHebrew.equals(w.getWord()))
            {
                System.out.println("*****" + w.getWord() + "******");
                model.addAttribute("resultW", w.getWord());
                model.addAttribute("resultD", w.getDescription());

                //System.out.println("*****" + result.getWord() + result + "******");
                break;
            }
        }

        return "hebrew/search";
    }

}
