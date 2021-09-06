package com.ssm.book.controller;

import com.ssm.book.command.QuantityCommand;
import com.ssm.book.command.ShopCommand;
import com.ssm.book.service.BookService;
import com.ssm.book.service.QuantityService;
import com.ssm.book.service.ShopService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ShopController {
    private BookService bookService;
    private ShopService shopService;
    private QuantityService quantityService;

    public ShopController(BookService bookService, ShopService shopService, QuantityService quantityService) {
        this.bookService = bookService;
        this.shopService = shopService;
        this.quantityService = quantityService;
    }

    @GetMapping("/book/{id}/shop")
    public String showShopList(@PathVariable String id, Model model){
        model.addAttribute("book", bookService.getBookCommandById(id));
        return "shop/shopList";
    }

    @GetMapping("/book/{book_id}/shop/view/{id}")
    public String viewShop(@PathVariable String book_id, @PathVariable String id, Model model){
        model.addAttribute("shop", shopService.findShopById(book_id,id));
        return "shop/show";
    }

    @GetMapping("/book/{book_id}/shop/update/{id}")
    public String displayShopForm(@PathVariable String book_id, @PathVariable String id, Model model){
        model.addAttribute("shop", shopService.findShopById(book_id,id));
        model.addAttribute("quantities",quantityService.showQuantityList());
        return "shop/shopform";
    }

    @PostMapping("/book/{book_id}/shop/save")
    public String saveShop(@PathVariable String book_id, @ModelAttribute ShopCommand shopCommand){
        ShopCommand shopCommand1 = shopService.saveShopCommand(shopCommand);
        return "redirect:/book/" + book_id + "/shop/view/" + shopCommand1.getId();
    }

    @GetMapping("book/{book_id}/shop/delete/{id}")
    public String deleteShop(@PathVariable String book_id, @PathVariable String id){
        shopService.deleteShop(book_id, id);
        return "redirect:/book/" + book_id + "/shop";
    }

    @GetMapping("/book/{id}/shop/new")
    public String newShop(@PathVariable String id, Model model){
        ShopCommand shopCommand = new ShopCommand();
        shopCommand.setBookId(Long.valueOf(id));
        shopCommand.setQuantity(new QuantityCommand());
        model.addAttribute("shop", shopCommand);
        model.addAttribute("quantities",quantityService.showQuantityList());
        return "shop/shopform";
    }
}
