package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.model.Supplier;
import com.example.demo.model.User;
import com.example.demo.service.ProductService;
import com.example.demo.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductViewController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SupplierService supplierService;

    @GetMapping("/product/all")
    public String getProducts(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentPrincipalName = (User) authentication.getPrincipal();

        model.addAttribute("products", productService.getProducts());
        return "product/all";
    }

    @GetMapping("/product/new")
    public String showNewProduct(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("suppliers", supplierService.getSuppliers());
        return "product/new";
    }

    @PostMapping("/product/save")
    public String newProduct(Product product){
        productService.saveProduct(product);
        return "redirect:/product/all";
    }

    @GetMapping("/product/update/{id}")
    public String showUpdateProduct(@PathVariable Long id, Model model){
        model.addAttribute("product", productService.getProduct(id));
        model.addAttribute("suppliers", supplierService.getSuppliers());
        return "product/update";
    }

    @PostMapping("/product/update/{id}")
    public String updateProduct(@PathVariable Long id,Product product){
        product.setId(id);
        productService.updateProduct(product);
        return "redirect:/product/all";
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return "redirect:/product/all";
    }

}
