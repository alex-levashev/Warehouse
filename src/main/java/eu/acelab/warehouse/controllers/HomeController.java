package eu.acelab.warehouse.controllers;

import eu.acelab.warehouse.models.Stock;
import eu.acelab.warehouse.repositories.MainItemRepository;
import eu.acelab.warehouse.repositories.StockRepository;
import eu.acelab.warehouse.repositories.SubItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private MainItemRepository mainItemRepository;
    @Autowired
    private SubItemRepository subItemRepository;
    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("stocksCount", stockRepository.count());
        model.addAttribute("mainItemsCount", mainItemRepository.count());
        model.addAttribute("subItemsCount", subItemRepository.count());
        return "index";
    }
    @GetMapping("/login")
    public String LoginPage(Model model) {
        return "login";
    }

}
