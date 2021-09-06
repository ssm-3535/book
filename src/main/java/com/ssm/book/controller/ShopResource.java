package com.ssm.book.controller;

import com.ssm.book.api.v1.dto.ResponseDTO;
import com.ssm.book.api.v1.dto.ShopDTO;
import com.ssm.book.api.v1.dto.ShopDTOList;
import com.ssm.book.service.ShopService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ShopResource.SHOP_URL)
public class ShopResource {
    public static final String SHOP_URL = "/api/v1/shops";
    private ShopService shopService;

    public ShopResource(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping
    public ShopDTOList getShopDTOList(){
        return new ShopDTOList(shopService.getShops()) ;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO registerShop(@RequestBody ShopDTO shopDTO){
        return shopService.saveShopDTO(shopDTO);
    }

    @GetMapping("{id}")
    public ShopDTO getShopById(@PathVariable Long id){
        return shopService.findShopDTOById(id);
    }

    @PutMapping("{id}")
    public ShopDTO updateShopDTO(@PathVariable Long id,@RequestBody ShopDTO shopDTO){
        return shopService.updateShopDTO(id, shopDTO);
    }

    @PatchMapping("{id}")
    public ShopDTO patchShopDTO(@PathVariable Long id, @RequestBody ShopDTO shopDTO){
        return shopService.patchShopDTO(id, shopDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteShopDTO(@PathVariable Long id){
        shopService.deleteById(id);
    }
}
