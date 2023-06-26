package com.example.pdv.service;

import com.example.pdv.dto.ProductDTO;
import com.example.pdv.dto.SaleDTO;
import com.example.pdv.entity.Product;
import com.example.pdv.entity.Sale;
import com.example.pdv.entity.SaleItem;
import com.example.pdv.entity.User;
import com.example.pdv.repository.ProductRepository;
import com.example.pdv.repository.SaleItemRepository;
import com.example.pdv.repository.SaleRepository;
import com.example.pdv.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private SaleItemRepository saleItemRepository;

    @Transactional
    public long save(SaleDTO saleDTO){

        User user = userRepository.findById(saleDTO.getUserId()).get();

        Sale newSale = new Sale();
        newSale.setUser(user);
        newSale.setDate(LocalDate.now());
        List<SaleItem> items = getSaleItem(saleDTO.getItems());


        newSale = saleRepository.save(newSale);

        saveSaleItem(items, newSale);

        return newSale.getId();
    }


    private void saveSaleItem(List<SaleItem> items, Sale newSale) {
        for(SaleItem item: items){
            item.setSale(newSale);
            saleItemRepository.save(item);
        }
    }


    public List<SaleItem> getSaleItem(List<ProductDTO> products){

        return products.stream().map(item -> {
            Product product = productRepository.getReferenceById(item.getProductId());

            SaleItem saleItem = new SaleItem();
            saleItem.setProduct(product);
            saleItem.setQuantity(item.getQuantity());

            return saleItem;
        }).collect(Collectors.toList());
    }
}
