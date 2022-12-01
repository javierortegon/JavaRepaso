package com.example.demo.controller;

import com.example.demo.enums.TipoReporteEnum;
import com.example.demo.model.Product;
import com.example.demo.model.ReporteProductosDTO;
import com.example.demo.model.Supplier;
import com.example.demo.model.User;
import com.example.demo.service.ProductService;
import com.example.demo.service.ReporteProductoService;
import com.example.demo.service.SupplierService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@Controller
public class ProductViewController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ReporteProductoService reporteProductoService;

    @GetMapping("/product/all")
    public String getProducts(Model model){
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loginUser = (User)authentication.getPrincipal();

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

    @GetMapping("/product/report")
    public ResponseEntity<Resource> download(@RequestParam Map<String, Object> params)throws JRException, IOException, SQLException{
        ReporteProductosDTO dto = reporteProductoService.obtenerReporteProductos(params);

        InputStreamResource streamResource = new InputStreamResource(dto.getStream());
        MediaType mediaType = null;
        if(params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name())){
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }else{
            mediaType = MediaType.APPLICATION_PDF;
        }

        return ResponseEntity.ok().header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"")
                .contentLength(dto.getLenght()).contentType(mediaType).body(streamResource);

    }

}
