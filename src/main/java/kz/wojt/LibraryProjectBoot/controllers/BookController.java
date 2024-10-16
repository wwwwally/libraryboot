package kz.wojt.LibraryProjectBoot.controllers;
import jakarta.validation.Valid;

import kz.wojt.LibraryProjectBoot.models.Book;
import kz.wojt.LibraryProjectBoot.models.Person;
import kz.wojt.LibraryProjectBoot.services.BookService;
import kz.wojt.LibraryProjectBoot.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {
    private final PeopleService peopleService;
    private final BookService bookService;

    @Autowired
    public BookController(PeopleService peopleService, BookService bookService) {
        this.peopleService = peopleService;
        this.bookService = bookService;
    }

    @GetMapping
    public String index(Model model,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) boolean sortByYear) {
        if (page == null || booksPerPage == null)
        model.addAttribute("books", bookService.findAll(sortByYear));
        else
            model.addAttribute("books", bookService.sortByYearWithPagination(page, booksPerPage, sortByYear));
        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @GetMapping("/{book_id}")
    public String show(@PathVariable("book_id") int book_id, Model model, @ModelAttribute("owner") Person owner) {
        model.addAttribute("book", bookService.findOne(book_id));
        model.addAttribute("person", bookService.getRelatedPersonById(book_id));
        model.addAttribute("people", peopleService.findAll());
        return "books/show";
    }


    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{book_id}/edit")
    public String edit(Model model, @PathVariable("book_id") int book_id) {
        model.addAttribute("book", bookService.findOne(book_id));
        return "books/edit";
    }

    @PatchMapping("/{book_id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable("book_id") int book_id) {
        bookService.update(book_id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{book_id}")
    public String delete(@PathVariable("book_id") int book_id) {
        bookService.delete(book_id);
        return "redirect:/books";
    }

    @PutMapping("/{book_id}")
    public String deletePersonFromBook(@PathVariable("book_id") int book_id) {
        bookService.release(book_id);
        return "redirect:/books/" + book_id;
    }

    @PatchMapping("/addPerson")
    public String addPersonToBook(@ModelAttribute("id") int personId,
                                   @ModelAttribute("bookId") int bookId) {
        bookService.assign(bookId, personId);
        return "redirect:/books/" + bookId;
    }

    @GetMapping("/search")
    public String search() {
        return "books/search";
    }

    @PostMapping("/search")
    public String toSearch (Model model, @RequestParam("query") String query) {
        model.addAttribute("books", bookService.searchByTitle(query));
        return "books/search";
    }

}
