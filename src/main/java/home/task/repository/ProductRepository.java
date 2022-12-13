package home.task.repository;

import home.task.domain.Product;
import org.springframework.data.repository.CrudRepository;


public interface ProductRepository extends CrudRepository<Product,Integer> {
    Product findByName(String name);
    /**
     * This method is used when filtering products by a certain category
     */
    Iterable<Product> findByCategory_idEquals(Integer filter);
    //Iterable<Product> findByNameContaining(String filter);


}