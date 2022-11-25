package com.example.demo.controller;

import com.example.demo.model.Supplier;
import com.example.demo.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class SupplierViewController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping("/supplier/all")
    public String getSuppliers(Model model){
        List<Supplier> suppliers = supplierService.getSuppliers();
        model.addAttribute("suppliers", suppliers);
        return "supplier/all";
    }

    @GetMapping("/supplier/new")
    public String showNewSupplier(Model model){
        model.addAttribute("supplier", new Supplier());
        return "supplier/new";
    }

    @PostMapping("/supplier/save")
    public String newSupplier(@Valid Supplier supplier, BindingResult result){
        if(result.hasErrors()){
            return "/supplier/new";
        }
        supplierService.saveSupplier(supplier);
        return "redirect:/supplier/all";
    }

    @GetMapping("/supplier/update/{id}")
    public String showUpdateSupplier(@PathVariable Long id, Model model){
        model.addAttribute("supplier", supplierService.getSupplier(id));
        return "supplier/update";
    }

    @PostMapping("/supplier/update/{id}")
    public String updateSupplier(@PathVariable Long id,Supplier supplier){
        supplier.setId(id);
        supplierService.updateSupplier(supplier);
        return "redirect:/supplier/all";
    }

    @GetMapping("/supplier/delete/{id}")
    public String deleteSupplier(@PathVariable Long id){
        supplierService.deleteSupplier(id);
        return "redirect:/supplier/all";
    }

}
