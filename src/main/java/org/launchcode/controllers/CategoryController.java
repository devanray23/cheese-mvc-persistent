package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.data.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;


@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryDao categoryDao;

    @RequestMapping(value= "")
    public String CategoryIndex(Model model){
        model.addAttribute("title", "My Categories");
        model.addAttribute("categories", categoryDao.findAll());
        return "category/index";
    }

    @RequestMapping(value= "add", method = RequestMethod.GET)
    public String DisplayAddCategory(Model model){
        model.addAttribute(new Category());
        model.addAttribute("title", "Add Category");

        return "category/add";
    }

    @RequestMapping(value="add", method = RequestMethod.POST)
    public String ProcessAdd(@Valid Category category, Model model, Errors errors){

        if(errors.hasErrors()){
            model.addAttribute("title", "Add Category");
            return "category/add";
        }

        Category exist = categoryDao.findByName(category.getName());

        if(exist != null){
            model.addAttribute("title", "Add Category");
            model.addAttribute("existingError", "Category already exists.");
            return "category/add";
        }

        categoryDao.save(category);
        return "redirect:/category";
    }
}
