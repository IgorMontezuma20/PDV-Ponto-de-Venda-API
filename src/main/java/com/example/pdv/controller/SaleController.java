package com.example.pdv.controller;

import com.example.pdv.dto.SaleDTO;
import com.example.pdv.exceptions.InvalidOperationException;
import com.example.pdv.exceptions.NoItemException;
import com.example.pdv.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sale")
public class SaleController {

    private SaleService saleService;

    public SaleController(@Autowired SaleService saleService){
        this.saleService = saleService;
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody SaleDTO saleDTO){
        try {
            long id = saleService.save(saleDTO);
            return new ResponseEntity<>("Venda realizada com sucesso: " + id, HttpStatus.CREATED);

        }catch (NoItemException | InvalidOperationException e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping()
    public ResponseEntity getAll(){
        return new ResponseEntity<>(saleService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable long id){
        try {
            return new ResponseEntity<>(saleService.getById(id), HttpStatus.OK);
        }catch (NoItemException | InvalidOperationException e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }

    }
}
