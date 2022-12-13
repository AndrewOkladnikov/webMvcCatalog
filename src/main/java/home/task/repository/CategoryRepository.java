package home.task.repository;

import home.task.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category,Integer> {
    Category findByName(String name);
    /**
     * This method is used when creating a new category. Name matching is not allowed
     */
    Category findByNameIgnoreCase(String name);
}