package org.sysreg.sia.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.sysreg.sia.model.dao.EnclosureDAO;

import java.security.Principal;

/**
 * Created by jose on 22/06/14.
 */
@Controller
public class DashboardController {
    @Autowired
    EnclosureDAO enclosureDAO;

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard(@RequestParam("enclosureId") String enclosureId, ModelMap model, Principal principal) {
        model.put("enclosure", enclosureDAO.findById(enclosureId));
        return "dashboard";
    }
}
