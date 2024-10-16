package kz.wojt.LibraryProjectBoot.repositories;



import kz.wojt.LibraryProjectBoot.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByOwnerId(int id);
    List<Book> findByTitleStartingWith(String title);
}
