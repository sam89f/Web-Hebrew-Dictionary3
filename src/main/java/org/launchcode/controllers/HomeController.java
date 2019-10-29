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

@Controller
@RequestMapping("hebrew-dictionary")
public class HomeController {

    // Request path: /hebrew-dictionary
    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("title", "Web Hebrew Dictionary");

        return "hebrew-dictionary/index";
    }

}
