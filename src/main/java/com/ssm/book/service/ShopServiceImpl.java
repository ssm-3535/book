package com.ssm.book.service;

import com.ssm.book.api.v1.dto.ResponseDTO;
import com.ssm.book.api.v1.dto.ShopDTO;
import com.ssm.book.api.v1.mapper.QuantityMapper;
import com.ssm.book.api.v1.mapper.ShopMapper;
import com.ssm.book.command.ShopCommand;
import com.ssm.book.converter.ShopCommandToShop;
import com.ssm.book.converter.ShopToShopCommand;
import com.ssm.book.domain.Book;
import com.ssm.book.domain.Shop;
import com.ssm.book.repositories.BookRepository;
import com.ssm.book.repositories.QuantityRepository;
import com.ssm.book.repositories.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ShopServiceImpl implements ShopService {
    private final ShopToShopCommand shopToShopCommand;
    private final BookRepository bookRepository;
    private final ShopCommandToShop shopCommandToShop;
    private final QuantityRepository quantityRepository;
    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;
    private final QuantityMapper quantityMapper;

    public ShopServiceImpl(ShopToShopCommand shopToShopCommand, BookRepository bookRepository,
                           ShopCommandToShop shopCommandToShop, QuantityRepository quantityRepository, ShopRepository shopRepository, ShopMapper shopMapper, QuantityMapper quantityMapper) {
        this.shopToShopCommand = shopToShopCommand;
        this.bookRepository = bookRepository;
        this.shopCommandToShop = shopCommandToShop;
        this.quantityRepository = quantityRepository;
        this.shopRepository = shopRepository;
        this.shopMapper = shopMapper;
        this.quantityMapper = quantityMapper;
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

    @Override
    public void deleteShop(String book_id, String id) {
//        Book book = bookRepository.findById(Long.valueOf(book_id)).get();
//        Optional<Shop> shopOptional = book.getShops().stream().filter(shop -> shop.getId().equals(id)).findFirst();
//        if(shopOptional.isPresent()){
//            System.out.println("win lar ");
//            Shop shopToDelete = shopOptional.get();
//            shopToDelete.setBook(null);
//            book.getShops().remove(shopOptional.get());
//            bookRepository.save(book);
//        }else {
//            System.out.println("no no ======");
//        }
        shopRepository.deleteById(Long.valueOf(id));
    }

    @Override
    public List<ShopDTO> getShops() {
        return StreamSupport.stream(shopRepository.findAll()
                .spliterator(), false)
                .map(shopMapper::shopToShopDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseDTO saveShopDTO(ShopDTO shopDTO) {
        Long book_id = shopDTO.getBookId();
        Optional<Book> bookOptional = bookRepository.findById(book_id);
        if(!bookOptional.isPresent()){
            return new ResponseDTO(false, "Book ID" + book_id + " is not found.");
        }else {
            Book book = bookOptional.get();
            Optional<Shop> shopOptional = book.getShops().stream().filter(shop -> shop.getId().equals(shopDTO.getId())).findFirst();

            if(shopOptional.isPresent()){
                Shop shop = shopOptional.get();
                shop.setName(shopDTO.getName());
                shop.setAddress(shopDTO.getAddress());
                shop.setPhone(shopDTO.getPhone());
                shop.setQuantity(quantityRepository.findById(shopDTO.getQuantity().getId()).get());
            }else{
                Shop shop = shopMapper.shopDTOToShop(shopDTO);
                shop.setBook(book);
                book.addShops(shop);
            }
            Book savedBook = bookRepository.save(book);
            if (savedBook != null){
                return new ResponseDTO(true, "Successfully created!");
            }else {
                return new ResponseDTO(false, "Fail to create book!");
            }
        }
    }

    @Override
    public ShopDTO findShopDTOById(Long id) {
        return shopMapper.shopToShopDTO(shopRepository.findById(id).get());
    }

    @Override
    public ShopDTO updateShopDTO(Long id, ShopDTO shopDTO) {
        Shop shop = shopMapper.shopDTOToShop(shopDTO);
        shop.setId(id);
        Shop savedShop = shopRepository.save(shop);
        return shopMapper.shopToShopDTO(savedShop);
    }

    @Override
    public ShopDTO patchShopDTO(Long id, ShopDTO shopDTO) {
        Shop shop = shopRepository.findById(id).get();
        ShopDTO shopDTO1;
        if(shop != null){
            if(shopDTO.getName() != null){
                shop.setName(shopDTO.getName());
            }
            if(shopDTO.getAddress() != null){
                shop.setAddress(shopDTO.getAddress());
            }
            if(shopDTO.getPhone() != null){
                shop.setPhone(shopDTO.getPhone());
            }
            if(shopDTO.getQuantity() != null){
                if(shopDTO.getQuantity().getAmount() != null){
                    shop.setQuantity(quantityMapper.quantityDTOToQuantity(shopDTO.getQuantity()));
                }
            }
            shopRepository.save(shop);
            shopDTO1 = shopMapper.shopToShopDTO(shop);
        }else {
            throw new RuntimeException("Shop not found!!");
        }
        return shopDTO1;
    }

    @Override
    public void deleteById(Long id) {
        shopRepository.deleteById(id);
    }
}
