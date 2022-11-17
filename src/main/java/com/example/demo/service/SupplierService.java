package com.example.demo.service;

import com.example.demo.model.Supplier;
import com.example.demo.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public List<Supplier> getSuppliers(){
        return supplierRepository.findAll();
    }

    public Supplier saveSupplier(Supplier supplier){
        return supplierRepository.save(supplier);
    }

    public Supplier getSupplier(Long id){
        return supplierRepository.findById(id).get();
    }

    public Supplier updateSupplier(Supplier supplier){
        return supplierRepository.save(supplier);
    }

    public void deleteSupplier(Long id){
        supplierRepository.deleteById(id);
    }

}
