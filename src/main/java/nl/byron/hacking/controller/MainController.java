package nl.byron.hacking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Byron Voorbach
 */

@Controller
public class MainController {

    @Autowired
    private TunnelController tunnelController;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showMainPage(Model model) {
        model.addAttribute("victims", tunnelController.getVictims().values());
        return "index";
    }

}
