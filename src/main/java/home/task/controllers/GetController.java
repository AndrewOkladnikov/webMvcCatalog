package home.task.controllers;

import home.task.domain.Category;
import home.task.domain.Product;
import home.task.repository.CategoryRepository;
import home.task.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Objects;

@Controller
public class GetController {
    /**
     * Inject ProductRepository bean
     */
    @Autowired
    private ProductRepository productRepository;
    /**
     * Inject CategoryRepository bean
     */
    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * The main window of product navigation
     */
    @GetMapping(value = {"/", "/products"})
    public String getProducts(@RequestParam(name = "selected", required = false) String categoryName, Map<String, Object> model) {
        Iterable<Product> products = getProducts(categoryName);
        model.put("products", products);
        model.put("categoryRepository", categoryRepository);
        return "main";
    }
    /**
     * Show list of categories
     */
    @GetMapping("/categories")
    public String getCategories( Map<String, Object> model) {
        Iterable<Category> categories = categoryRepository.findAll();
        model.put("categories", categories);
        return "categories";
    }

    /**
     * The method returns a list of all products or related to the selected category
     */
    private Iterable<Product> getProducts(String categoryName) {
        Iterable<Product> products;
        if (categoryName != null && !Objects.equals(categoryName, "")) {
            Category category = categoryRepository.findByName(categoryName);
            products = productRepository.findByCategory_idEquals(category.getId());
        } else products = productRepository.findAll();
        return products;
    }

    /**
     *  get method for adding new product
     */
    @GetMapping("/add")
    public String addNewProduct(Map<String, Object> model) {
        Iterable<Category> categories = categoryRepository.findAll();
        model.put("categories", categories);
        return "add";
    }

    /**
     *  get method for editing an existing product
     */
    @GetMapping("/products/{id}/edit")
    public String editProduct(@PathVariable("id") int id, Map<String, Object> model) {
        Product product = productRepository.findById(id).orElse(null);
        Iterable<Category> categories = categoryRepository.findAll();
        model.put("categories", categories);
        model.put("product", product);
        return "product";
    }
    /**
     * Show view for editing category
     */
    @GetMapping("/categories/{id}/edit")
    public String editCategory(@PathVariable("id") int id, Map<String, Object> model) {
        Category category = categoryRepository.findById(id).orElse(null);
        model.put("category", category);
        return "category";
    }

}