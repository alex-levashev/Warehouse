package eu.acelab.warehouse.controllers;

import eu.acelab.warehouse.models.SubItem;
import eu.acelab.warehouse.repositories.MainItemRepository;
import eu.acelab.warehouse.repositories.SubItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping(path="/item/subitem")
public class SubItemController {
    @Autowired
    private SubItemRepository subItemRepository;
    @Autowired
    private MainItemRepository mainItemRepository;

    @GetMapping(path="/add")
    public String addNewStock (SubItem subItem, Model model) {
        model.addAttribute("subItem", subItem);
        return "item/subItemAdd";
    }

    @PostMapping(path="/add")
    public String addNewStock (Model model, @Valid SubItem subItem, Errors errors) {
        if (null != errors && errors.getErrorCount() > 0) {
            return "item/subItemAdd";
        } else {
            subItemRepository.save(subItem);
            model.addAttribute("successMsg", "Added successfully!");
            model.addAttribute("subItems", subItemRepository.findAll());
            return "item/subItemShow";
        }
    }

    @GetMapping(path="/list")
    public String getAllSubItems(Model model) {
        model.addAttribute("subItems", subItemRepository.findAll());
        return "item/subItemShow";
    }

    @GetMapping(path="/remove")
    public String removeSubItem(@RequestParam int id, Model model) {
        ArrayList usedSubIds = new ArrayList<>();
        mainItemRepository.findAll().forEach(i -> i.getSubItems().forEach(x -> {
            usedSubIds.add(x.getId());
        }));
        System.out.println(usedSubIds);
        if(usedSubIds.contains(id)) {
            model.addAttribute("error", "Item can't be deleted due presence in mainItem");
        } else {
            subItemRepository.deleteById(id);
        }
        model.addAttribute("subItems", subItemRepository.findAll());
        return "item/subItemShow";


    }

    @GetMapping(path="/edit")
    public String editSubItem(@RequestParam int id, Model model, SubItem subItem) {
        model.addAttribute("subItem", subItemRepository.findById(id).get());
        return "item/subItemEdit";
    }

    @PostMapping(path="/edit")
    public String editSubItem(Model model, @Valid SubItem subItem, Errors errors) {
        if (null != errors && errors.getErrorCount() > 0) {
            model.addAttribute("subItem", subItem);
            return "item/subItemEdit";
        } else {
            subItemRepository.save(subItem);
            model.addAttribute("subItems", subItemRepository.findAll());
            return "item/subItemShow";
        }
    }
}
