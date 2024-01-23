package org.launchcode.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SpaDayController {

    public boolean checkSkinType(String skinType, String facialType) {
        if (skinType.equals("oily")) {
            return facialType.equals("Microdermabrasion") || facialType.equals("Rejuvenating");
        }
        else if (skinType.equals("combination")) {
            return facialType.equals("Microdermabrasion") || facialType.equals("Rejuvenating") || facialType.equals("Enzyme Peel");
        }
        else if (skinType.equals("dry")) {
            return facialType.equals("Rejuvenating") || facialType.equals("Hydrofacial");
        }
        else {
            return true;
        }
    }

    @GetMapping(value="")
    @ResponseBody
    public String customerForm() {
        String html =
                "<!DOCTYPE html>" +
                        "<html lang='en'>" +
                        "<head>" +
                        "<meta charset='UTF-8' />" +
                        "<title>Customer Form</title>" +
                        "<style>" +
                        "html, body { " +
                        "height: 100%; " +
                        "margin: 0; " +
                        "display: flex; " +
                        "align-items: center; " +
                        "justify-content: center; " +
                        "}" +
                        "form { " +
                        "display: flex; " +
                        "flex-direction: column; " +
                        "}" +
                        "</style>" +
                        "</head>" +
                        "<body>" +
                        "<form method='post'>" +
                        "Name: <br>" +
                        "<input type='text' name='name' required><br>" +
                        "Skin type: <br>" +
                        "<select name='skintype'>" +
                        "<option value='oily'>Oily</option>" +
                        "<option value='combination'>Combination</option>" +
                        "<option value='normal'>Normal</option>" +
                        "<option value='dry'>Dry</option>" +
                        "</select><br>" +
                        "Manicure or Pedicure? <br>" +
                        "<select name='manipedi'>" +
                        "<option value='manicure'>Manicure</option>" +
                        "<option value='pedicure'>Pedicure</option>" +
                        "<option value='mani-pedi'>Mani-Pedi</option>" +
                        "</select><br>" +
                        "<input type='submit' value='Submit'>" +
                        "</form>" +
                        "</body>" +
                        "</html>";
        return html;
    }

    @PostMapping(value="")
    public String spaMenu(@RequestParam String name,
                          @RequestParam String skintype,
                          @RequestParam(required = false) List<String> manipedi,
                          Model model) {

        if (manipedi == null) manipedi = new ArrayList<>();

        ArrayList<String> facials = new ArrayList<>();
        facials.add("Microdermabrasion");
        facials.add("Hydrofacial");
        facials.add("Rejuvenating");
        facials.add("Enzyme Peel");

        ArrayList<String> appropriateFacials = new ArrayList<>();
        for (String facial : facials) {
            if (checkSkinType(skintype, facial)) {
                appropriateFacials.add(facial);
            }
        }

        model.addAttribute("name", name);
        model.addAttribute("skintype", skintype);
        model.addAttribute("manipedi", manipedi);
        model.addAttribute("appropriateFacials", appropriateFacials);

        return "menu";
    }
}
