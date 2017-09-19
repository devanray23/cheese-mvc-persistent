package org.launchcode.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("")
public class BootRun {

    @RequestMapping(value="")
    public String bootRun(){
        return "redirect:/cheese";
    }
}
