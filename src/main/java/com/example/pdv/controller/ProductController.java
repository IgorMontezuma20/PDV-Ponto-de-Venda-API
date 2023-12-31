package com.example.pdv.controller;

import com.example.pdv.dto.ProductDTO;
import com.example.pdv.dto.ResponseDTO;
import com.example.pdv.entity.Product;
import com.example.pdv.repository.ProductRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;
    private ModelMapper mapper;

    public ProductController(@Autowired ProductRepository productRepository){
        this.productRepository = productRepository;
        this.mapper = new ModelMapper();
    }

    @GetMapping()
    public ResponseEntity getAll(){
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity post(@Valid @RequestBody ProductDTO product){
        try {
            return new ResponseEntity<>(productRepository.save(mapper.map(product, Product.class)), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(new ResponseDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity put(@Valid @RequestBody ProductDTO product){
        try {
            return new ResponseEntity<>(productRepository.save(mapper.map(product, Product.class)), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new ResponseDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        try {
            productRepository.deleteById(id);
            return new ResponseEntity<>(new ResponseDTO("Produto removido com sucesso!"), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new ResponseDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
