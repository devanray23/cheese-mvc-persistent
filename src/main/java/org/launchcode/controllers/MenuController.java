package org.launchcode.controllers;

import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private CheeseDao cheeseDao;

    @RequestMapping("")
    public String MenuIndex(Model model){
        model.addAttribute("title", "My Menus");
        model.addAttribute("menus", menuDao.findAll());

        return "menu/index";
    }

    @RequestMapping("view/{id}")
    public String ViewMenu(Model model, @PathVariable(value="id") String id){
        Menu viewMenu = menuDao.findOne(Integer.parseInt(id));
        model.addAttribute("title", "View " + viewMenu.getName());

        model.addAttribute("cheeses", viewMenu.getCheeses());

        return "menu/view/" + id;
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String AddMenu(Model model){

        model.addAttribute("title","Add Menu");
        model.addAttribute(new Menu());

        return "menu/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String ProcessAddMenuForm(Model model, @ModelAttribute @Valid Menu menu, Errors errors){

        if(errors.hasErrors()){
            model.addAttribute("title", "Add Menu");
            return "menu/add";
        }

        Menu exist = menuDao.findByName(menu.getName());

        if(exist == null){
            model.addAttribute("title", "Add Menu");
            model.addAttribute("existingError", "Menu already exists.");
            return "menu/add";
        }

        menuDao.save(menu);
        return "redirect:/menu/view" + String.valueOf(menu.getId());
    }

    @RequestMapping(value="add-item/{id}", method = RequestMethod.GET)
    public String ViewAddItemForm(Model model, @PathVariable(value = "id") String id){

        Menu menu = menuDao.findOne(Integer.parseInt(id));
        model.addAttribute(new AddMenuItemForm());
        model.addAttribute("menu", menu);
        model.addAttribute("title", "Add Item to Menu: " + menu.getName());

        return "menu/add-item";
    }

    @RequestMapping(value="add-item/{id}", method = RequestMethod.POST)
    public String ProcessAddItemForm(Model model,
                                     @ModelAttribute @Valid AddMenuItemForm form,
                                     Errors errors,
                                     @PathVariable(value = "id") String id){

        Menu menu = menuDao.findOne(Integer.parseInt(id));

        if(errors.hasErrors()){
            model.addAttribute("menu", menu);
            model.addAttribute("title", "Add Item to Menu: " + menu.getName());
            return "menu/add-item";
        }

        Cheese addCheese = cheeseDao.findOne(form.getCheeseId());

        menu.addCheese(addCheese);
        menuDao.save(menu);
        return "redirect:/menu/view" + String.valueOf(menu.getId());
    }


}
