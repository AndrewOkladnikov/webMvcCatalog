package home.task.controllers;

import home.task.domain.Category;
import home.task.domain.Product;
import home.task.repository.CategoryRepository;
import home.task.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Controller
public class PostController {
    /**
     * Assigns the path to files from properties
     */
    @Value("${upload.path}")
    private String uploadPath;
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
     * We use this method both to create a new product or to edit an existing one
     */
    @PostMapping("/add-product")
    public RedirectView addProduct(@RequestParam(required = false) String id,
                                   @RequestParam(required = false) String name,
                                   @RequestParam(required = false) String about,
                                   @RequestParam(required = false) String manufacturer,
                                   @RequestParam("picture") MultipartFile file,
                                   @RequestParam(required = false) Integer price,
                                   @RequestParam(required = false) String createdDate,
                                   @RequestParam(name = "hidden", required = false) String instruction,
                                   @RequestParam(name = "selected") String categoryName,
                                   Map<String, Object> model) {
        saveProduct(id, name, about, manufacturer, file, price, createdDate, categoryName);
        Iterable<Product> products = productRepository.findAll();
        model.put("messages", products);
        return new RedirectView("/");
    }

    /**
     * The product is deleted by clicking the "delete" button from the main window
     */
    @PostMapping("/delete-product")
    public RedirectView deleteProduct(@RequestParam String value) throws IOException {
        productRepository.deleteById(Integer.parseInt(value));
        return new RedirectView("/");
    }

    /**
     * Method called when deleting category
     */
    @PostMapping("/delete-category")
    public RedirectView deleteCategory(@RequestParam String value) throws IOException {
        categoryRepository.deleteById(Integer.parseInt(value));
        return new RedirectView("/categories");
    }

    /**
     * A new category is added by clicking the "add"(category) button from the "add" window
     */
    @PostMapping("/add-category")
    public RedirectView addCategory(@RequestParam(name = "categoryName") String categoryName,
                                    @RequestParam(required = false) String about,
                                    Map<String, Object> model) throws IOException {
        Category category;
        category = categoryRepository.findByNameIgnoreCase(categoryName);
        if (category != null) return new RedirectView("/categories");
        category = new Category(categoryName);
        category.setAbout(about);
        categoryRepository.save(category);
        Iterable<Category> categories;
        categories = categoryRepository.findAll();
        model.put("categories", categories);
        return new RedirectView("/categories");
    }

    /**
     * Method called when editing category
     */
    @PostMapping("/category/{id}")
    public RedirectView editCategory(@PathVariable("id") int id, @RequestParam(required = false) String name,
                                     @RequestParam(required = false) String about, Map<String, Object> model) {
        Category category = categoryRepository.findById(id).get();
        System.out.println("Number oof chars: " + about.length());
        category.setName(name);
        category.setAbout(about);
        categoryRepository.save(category);
//        Iterable<Product> products = productRepository.findAll();
//        model.put("products", products);
        //model.put("categoryRepository", categoryRepository);
        return new RedirectView("/categories");
    }

    /**
     * Saves a new or edited product(if the product id is not null)
     */
    private void saveProduct(String id, String name, String about, String manufacturer, MultipartFile file,
                             Integer price, String createdDate, String categoryName) {
        Product product;
        if (id == null) {
            product = new Product();
        } else product = productRepository.findById(Integer.parseInt(id)).orElse(null);
        try {
            setProduct(id, name, about, manufacturer, file, price, createdDate, categoryName, product);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert product != null;
        productRepository.save(product);
    }

    /**
     * This method fill Product data
     */
    private void setProduct(String id, String name, String about, String manufacturer, MultipartFile file,
                            Integer price, String createdDate, String cat, Product product) throws IOException {
        if (id != null) {
            product.setId(Integer.parseInt(id));
        }
        if (!Objects.equals(name, "")) {
            product.setName(name);
        }
        if (!Objects.equals(about, "")) {
            product.setAbout(about);
        }
        if (!Objects.equals(manufacturer, "")) {
            product.setManufacturer(manufacturer);
        }
        if (price != null) {
            product.setPrice(price);
        }
        if (createdDate != null && !createdDate.equals("")) {
            product.setCreatedDate(LocalDate.parse(createdDate));
        } else product.setCreatedDate(LocalDate.now());

        if (!Objects.equals(cat, "")) {
            Category category = categoryRepository.findByName(cat);
            product.setCategory(category);
        }
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            product.setPicture(resultFilename);
        }
    }

    /**/

}