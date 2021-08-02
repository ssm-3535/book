package com.ssm.book.service;

import com.ssm.book.command.ShopCommand;
import com.ssm.book.converter.ShopCommandToShop;
import com.ssm.book.converter.ShopToShopCommand;
import com.ssm.book.domain.Book;
import com.ssm.book.domain.Shop;
import com.ssm.book.repositories.BookRepository;
import com.ssm.book.repositories.QuantityRepository;
import com.ssm.book.repositories.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ShopServiceImpl implements ShopService {
    private ShopRepository shopRepository;
    private ShopToShopCommand shopToShopCommand;
    private BookRepository bookRepository;
    private ShopCommandToShop shopCommandToShop;
    private QuantityRepository quantityRepository;

    public ShopServiceImpl(ShopRepository shopRepository, ShopToShopCommand shopToShopCommand,
                           BookRepository bookRepository, ShopCommandToShop shopCommandToShop,
                           QuantityRepository quantityRepository) {
        this.shopRepository = shopRepository;
        this.shopToShopCommand = shopToShopCommand;
        this.bookRepository = bookRepository;
        this.shopCommandToShop = shopCommandToShop;
        this.quantityRepository = quantityRepository;
    }

    @Override
    public ShopCommand findShopById(String book_id, String id) {
        Optional<Book> bookOptional = bookRepository.findById(Long.valueOf(book_id));

        if (!bookOptional.isPresent()){
            //todo impl error handling
            throw new RuntimeException("No Recipe Found !!");
        }

        Book book = bookOptional.get();

        Optional<ShopCommand> shopCommandOptional = book.getShops().stream()
                .filter(shop -> shop.getId().equals(Long.valueOf(id)))
                .map( shop -> shopToShopCommand.convert(shop)).findFirst();

        if(!shopCommandOptional.isPresent()){
            //todo impl error handling
            throw new RuntimeException("No Recipe Found !!");
        }

        return shopCommandOptional.get();
    }

    @Override
    public ShopCommand saveShopCommand(ShopCommand shopCommand){
        Optional<Book> bookOptional = bookRepository.findById(shopCommand.getBookId());
        if(!bookOptional.isPresent()){
            return new ShopCommand();
        }else {
            Book book = bookOptional.get();
            Optional<Shop> shopOptional = book.getShops().stream().filter(shop -> shop.getId().equals(shopCommand.getId())).findFirst();

            if(shopOptional.isPresent()){
                Shop shop = shopOptional.get();
                shop.setName(shopCommand.getName());
                shop.setAddress(shopCommand.getAddress());
                shop.setPhone(shopCommand.getPhone());
                shop.setQuantity(quantityRepository.findById(shopCommand.getQuantity().getId()).get());
            }else{
                Shop shop = shopCommandToShop.convert(shopCommand);
                shop.setBook(book);
                book.addShops(shop);
            }
            Book savedBook = bookRepository.save(book);
            Optional<ShopCommand> savedShop = savedBook.getShops().stream()
                    .filter(shop -> shop.getName().equals(shopCommand.getName()))
                    .filter(shop -> shop.getPhone().equals(shopCommand.getPhone()))
                    .filter(shop -> shop.getAddress().equals(shopCommand.getAddress()))
                    .map(shop -> shopToShopCommand.convert(shop)).findFirst();
            return savedShop.get();
        }
    }
}
