package home.task.controllers;

import home.task.domain.Category;
import home.task.domain.Product;
import home.task.repository.CategoryRepository;
import home.task.repository.ProductRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class PostControllerTest {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private PostController postController;
    @Autowired
    Map<String, Object> model;
    private final String productName = "New product";
    private final String categoryName = "New category";

    @BeforeEach
    void setUp() {
        Product product = productRepository.findByName(productName);
        if(product!=null) productRepository.delete(product);
        Category category = categoryRepository.findByName(categoryName);
        if(category!=null) categoryRepository.delete(category);
    }

    @Test
    void addProduct1() {
        List<Product> products = (List<Product>) productRepository.findAll();
        int prevNumberOfProducts = products.size();
        postController.addProduct(null,productName,null,null,
                null, null, null, null, null, model);
        products = (List<Product>) productRepository.findAll();
        int currentNumberOfProducts = products.size();
        assertEquals(prevNumberOfProducts+1,currentNumberOfProducts);
        assertEquals(productName,productRepository.findByName(productName).getName());
    }

    @Test
    void deleteProduct() throws IOException {
        postController.addProduct(null,productName,null,null,
                null, null, null, null, null, model);
        Product product = productRepository.findByName(productName);
        assertNotNull(product);
        String stringId = product.getId().toString();
        postController.deleteProduct(stringId);
        product = productRepository.findByName(productName);
        assertNull(product);
    }

    @Test
    void addCategory1() throws IOException {
        List<Category> categories = (List<Category>) categoryRepository.findAll();
        int prevNumberOfCategories = categories.size();
        postController.addCategory(categoryName,null, model);
        categories = (List<Category>) categoryRepository.findAll();
        int currentNumberOfCategories = categories.size();
        assertEquals(prevNumberOfCategories+1,currentNumberOfCategories);
        assertEquals(categoryName,categoryRepository.findByName(categoryName).getName());
    }

    @Test
    void deleteCategory() throws IOException {
        postController.addCategory(categoryName,null, model);
        Category category = categoryRepository.findByName(categoryName);
        assertNotNull(category);
        String stringId = category.getId().toString();
        postController.deleteCategory(stringId);
        category = categoryRepository.findByName(categoryName);
        assertNull(category);
    }

    @Test
    void editCategory() throws IOException {
        String testAbout = "Test about";
        postController.addCategory(categoryName,null, model);
        Category category = categoryRepository.findByName(categoryName);
        assertNotEquals(testAbout,category.getAbout());
        postController.editCategory(category.getId(), categoryName,testAbout,model);
        category = categoryRepository.findByName(categoryName);
        assertEquals(testAbout,category.getAbout());
    }

}