package eu.acelab.warehouse.controllers;

import eu.acelab.warehouse.models.MainItem;
import eu.acelab.warehouse.models.Stock;
import eu.acelab.warehouse.models.SubItem;
import eu.acelab.warehouse.repositories.MainItemRepository;
import eu.acelab.warehouse.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun.applet.Main;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping(path="/stockMovements")
public class StockMovementsController {
    @Autowired
    private MainItemRepository mainItemRepository;
    @Autowired
    private StockRepository stockRepository;

    @GetMapping(path="/stockIn")
    public String addMainItemsToStock (Model model) {
        model.addAttribute("stocks", stockRepository.findAll());
        model.addAttribute("mainItems", mainItemRepository.findAll());
        return "stockMovements/stockIn";
    }

    @PostMapping(path="/stockIn")
    public String addMainItemsToStock (@RequestParam(required = false) Integer mainItemId,
                                       @RequestParam(required = false) Integer quantity,
                                       @RequestParam(required = false) Integer stockId,
                                       Stock stock,
                                       MainItem mainItem,
                                       Model model) {
        //GETTING LIST OF ITEMS ON STOCK
        stock = stockRepository.findById(stockId).get();
        List<MainItem> listOfItemsOnStock = stock.getMainItemList();
        System.out.println(listOfItemsOnStock);
        //ADDING ITEM TO THE LIST
        MainItem itemToAdd = mainItemRepository.findById(mainItemId).get();
        itemToAdd.setQuantity(quantity);
        System.out.println(itemToAdd);
        listOfItemsOnStock.add(itemToAdd);
        //ADDING LIST BACK TO STOCK
        stock.setMainItemList(listOfItemsOnStock);
        stockRepository.save(stock);
        // SENDING STOCK LIST TO VIEW
        model.addAttribute("stocks", stockRepository.findAll());
        return "stockMovements/stockCount";
    }

    @GetMapping(path="/stockCount")
    public String listMainItemsToStock (Model model) {
        model.addAttribute("stocks", stockRepository.findAll());
        model.addAttribute("mainItems", mainItemRepository.findAll());
        return "stockMovements/stockCount";
    }
}
