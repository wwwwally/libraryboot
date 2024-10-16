package kz.wojt.LibraryProjectBoot.services;


import kz.wojt.LibraryProjectBoot.models.Book;
import kz.wojt.LibraryProjectBoot.models.Person;
import kz.wojt.LibraryProjectBoot.repositories.BookRepository;
import kz.wojt.LibraryProjectBoot.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PeopleRepository peopleRepository) {
        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> getBooks(int id) {
       return checkOverdue(bookRepository.findByOwnerId(id));
       // return bookRepository.findByOwnerId(id);
    }

    private List<Book> checkOverdue(List<Book> books) {
        for (Book book : books) {
            if (book.getTakenAt() != null && LocalDateTime.now().minusDays(10).isAfter(book.getTakenAt())) {
                book.setOverdue(true);
            }
        }
        return books;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = bookRepository.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int book_id) {
        bookRepository.deleteById(book_id);
    }

    @Transactional
    public void update(int bookId, Book updatedBook) {
        Book bookToBeUpdated = findOne(bookId);
        updatedBook.setId(bookId);
        updatedBook.setOwner(bookToBeUpdated.getOwner());
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void release(int id) {
        bookRepository.findById(id).ifPresent(book -> { book.setOwner(null); book.setTakenAt(null); });
    }

    public Person getRelatedPersonById(int id) {
        return bookRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    @Transactional
    public void assign(int bookId, int personId) {
        bookRepository.findById(bookId).ifPresent(book -> {
            peopleRepository.findById(personId).ifPresent(person -> {
                book.setOwner(person);
                book.setTakenAt(LocalDateTime.now());
                bookRepository.save(book);
            });
        });
    }

    public List<Book> findAll(boolean sortByYear) {
        if (sortByYear)
            return bookRepository.findAll(Sort.by("year"));
        else
            return bookRepository.findAll();
    }

    public List<Book> sortByYearWithPagination(int page, int booksPerPage, boolean sortByYear) {
        if (sortByYear)
            return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        else
            return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public List<Book> searchByTitle (String title) {
       return bookRepository.findByTitleStartingWith(title);
    }
}
