package eu.acelab.warehouse.controllers;

import eu.acelab.warehouse.models.MainItem;
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
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(path="/item/mainitem")
public class MainItemController {
    @Autowired
    private MainItemRepository mainItemRepository;
    @Autowired
    private SubItemRepository subItemRepository;

    @GetMapping(path="/add")
    public String addMainItem (MainItem mainItem, Model model) {
        model.addAttribute("mainItem", mainItem);
        model.addAttribute("subItems", subItemRepository.findAll());
        return "item/mainItemAdd";
    }

    @PostMapping(path="/add")
    public String addMainItem (@RequestParam(required = false) String subItemIds, Model model, @Valid MainItem mainItem, Errors errors) {
        if (null != errors && errors.getErrorCount() > 0) {
            model.addAttribute("mainItem", mainItem);
            model.addAttribute("subItems", subItemRepository.findAll());
            return "item/mainItemAdd";
        } else {
            long price = 0;

            if(subItemIds != null) {
                List<SubItem> subItemsList = new ArrayList<>();
                List<String> subItemIdsArray = Arrays.asList(subItemIds.split("\\s*,\\s*"));
                for(String item : subItemIdsArray) {
                    SubItem tmp = subItemRepository.findById(Integer.parseInt(item)).get();
                    subItemsList.add(tmp);
                    price += tmp.getPrice();
                }
                mainItem.setSubItems(subItemsList);
            }
            if(mainItem.getPrice() == 0) {
                mainItem.setPrice(price);
            }

            mainItemRepository.save(mainItem);
            model.addAttribute("mainItems", mainItemRepository.findAll());
            return "item/mainItemShow";
        }

    }

    @GetMapping(path="/list")
    public String getAllMainItems(Model model) {
        model.addAttribute("mainItems", mainItemRepository.findAll());
        return "item/mainItemShow";
    }

    @GetMapping(path="/remove")
    public String removeMainItem(@RequestParam int id, Model model) {
        mainItemRepository.deleteById(id);
        model.addAttribute("mainItems", mainItemRepository.findAll());
        return "item/mainItemShow";
    }

    @GetMapping(path="/edit")
    public String editMainItem(@RequestParam int id, Model model) {
        model.addAttribute("mainItem", mainItemRepository.findById(id).get());
        ArrayList selectedIds = new ArrayList();
        for(SubItem item : mainItemRepository.findById(id).get().getSubItems()) {
            selectedIds.add(item.getId());
        }
        model.addAttribute("allSubItems", subItemRepository.findAll());
        System.out.println(selectedIds);
        model.addAttribute("selectedIds", selectedIds);
        return "item/mainItemEdit";
    }

    @PostMapping(path="/edit")
    public String editMainItem (@RequestParam(required = false) String subItemIds, Model model, @Valid MainItem mainItem, Errors errors) {
        if (null != errors && errors.getErrorCount() > 0) {
            model.addAttribute("mainItem", mainItem);
            model.addAttribute("subItems", subItemRepository.findAll());
            return "item/mainItemEdit";
        } else {
            if(subItemIds != null) {
                List<SubItem> subItemsList = new ArrayList<>();
                List<String> subItemIdsArray = Arrays.asList(subItemIds.split("\\s*,\\s*"));
                for(String item : subItemIdsArray) {
                    SubItem tmp = subItemRepository.findById(Integer.parseInt(item)).get();
                    subItemsList.add(tmp);
                }
                mainItem.setSubItems(subItemsList);
            }
            mainItemRepository.save(mainItem);
            model.addAttribute("mainItems", mainItemRepository.findAll());
            return "item/mainItemShow";
        }
    }
}
