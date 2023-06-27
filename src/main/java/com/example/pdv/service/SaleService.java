package com.example.pdv.service;

import com.example.pdv.dto.ProductDTO;
import com.example.pdv.dto.ProductInfoDTO;
import com.example.pdv.dto.SaleDTO;
import com.example.pdv.dto.SaleInfoDTO;
import com.example.pdv.entity.Product;
import com.example.pdv.entity.Sale;
import com.example.pdv.entity.ItemSale;
import com.example.pdv.entity.User;
import com.example.pdv.repository.ProductRepository;
import com.example.pdv.repository.ItemSaleRepository;
import com.example.pdv.repository.SaleRepository;
import com.example.pdv.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final ItemSaleRepository itemSaleRepository;

    public List<SaleInfoDTO> findAll(){
        return saleRepository.findAll().stream().map(sale -> getSaleInfo(sale)).collect(Collectors.toList());
    }

    private SaleInfoDTO getSaleInfo(Sale sale) {
        SaleInfoDTO saleInfoDTO = new SaleInfoDTO();
        saleInfoDTO.setUser(sale.getUser().getName());
        saleInfoDTO.setDate(sale.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        saleInfoDTO.setProducts(getProductInfo(sale.getItems()));

        return saleInfoDTO;
    }

    private List<ProductInfoDTO> getProductInfo(List<ItemSale> items) {

        return items.stream().map(item -> {
            ProductInfoDTO productInfoDTO = new ProductInfoDTO();
            productInfoDTO.setDescription(item.getProduct().getDescription());
            productInfoDTO.setQuantity(item.getQuantity());

            return productInfoDTO;
        }).collect(Collectors.toList());
    }

    @Transactional
    public long save(SaleDTO sale){

        User user = userRepository.findById(sale.getUserid()).get();

        Sale newSale = new Sale();
        newSale.setUser(user);
        newSale.setDate(LocalDate.now());
        List<ItemSale> items = getItemSale(sale.getItems());


        newSale = saleRepository.save(newSale);

        saveItemSale(items, newSale);

        return newSale.getId();
    }


    private void saveItemSale(List<ItemSale> items, Sale newSale) {
        for (ItemSale item: items){
            item.setSale(newSale);
            itemSaleRepository.save(item);
        }
    }


    public List<ItemSale> getItemSale(List<ProductDTO> products) {

        return products.stream().map(item -> {
            Product product = productRepository.getReferenceById(item.getProductid());

            ItemSale itemSale = new ItemSale();
            itemSale.setProduct(product);
            itemSale.setQuantity(item.getQuantity());

            if (product.getQuantity() == 0){
                throw new IllegalArgumentException();
            } else if (product.getQuantity() < item.getQuantity()) {
                throw new IllegalArgumentException();
            }

            int total = product.getQuantity() - item.getQuantity();
            product.setQuantity(total);
            productRepository.save(product);

            return itemSale;
        }).collect(Collectors.toList());
    }

    public SaleInfoDTO getById(long id) {
      Sale sale =  saleRepository.findById(id).get();

      return  getSaleInfo(sale);
    }
}
