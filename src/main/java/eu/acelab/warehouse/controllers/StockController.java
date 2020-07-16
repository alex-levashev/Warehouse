package eu.acelab.warehouse.controllers;

import eu.acelab.warehouse.models.Stock;
import eu.acelab.warehouse.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@Controller
@RequestMapping(path="/stock")
public class StockController {
    @Autowired
    private StockRepository stockRepository;

    @GetMapping(path="/add")
    public String addNewStock (Stock stock, Model model) {
        model.addAttribute("stock", stock);
        return "stock/stockAdd";
    }

    @PostMapping(path="/add")
    public String addNewStock (Model model, @Valid Stock stock, Errors errors) {
        if (null != errors && errors.getErrorCount() > 0) {
            return "stock/stockAdd";
        } else {
            stockRepository.save(stock);
            model.addAttribute("successMsg", "Added successfully!");
            model.addAttribute("stocks", stockRepository.findAll());
            return "stock/stockShow";
        }
    }

    @GetMapping(path="/list")
    public String getAllStocks(Model model) {
        model.addAttribute("stocks", stockRepository.findAll());
        return "stock/stockShow";
    }

    @GetMapping(path="/remove")
    public String removeStock(@RequestParam int id, Model model) {
        String msg = null;
        if (stockRepository.count() > 1) {
            msg = "Deleted!";
            stockRepository.deleteById(id);
        } else {
            msg = "One stock should be always available!";
        }
        model.addAttribute("errorMsg", msg);
        model.addAttribute("stocks", stockRepository.findAll());
        return "stock/stockShow";
    }

    @GetMapping(path="/edit")
    public String editStock(@RequestParam int id, Model model) {
        Stock stock = stockRepository.findById(id).get();
        model.addAttribute("stock", stock);
        return "stock/stockEdit";
    }

    @PostMapping(path="/edit")
    public String editStock(Model model, @Valid Stock stock, Errors errors) {
        if (null != errors && errors.getErrorCount() > 0) {
            model.addAttribute("stock", stock);
            return "stock/stockEdit";
        } else {
            stockRepository.save(stock);
            model.addAttribute("successMsg", "Added successfully!");
            model.addAttribute("stocks", stockRepository.findAll());
            return "stock/stockShow";
        }
    }


}
