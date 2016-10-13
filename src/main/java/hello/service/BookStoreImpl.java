package hello.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import hello.client.Book;
import hello.client.BookStoreInterface;

@Service
public class BookStoreImpl implements BookStoreInterface {
	Map<String, Book> books = new HashMap<String, Book>();
	
	@Override
	public Book addNewBook(Book book) throws IllegalArgumentException {
		if (book == null)
			throw new IllegalArgumentException("null");
		
		validateBook(book);
			
		String bookId = book.getId();
		if (books.get(bookId) != null)
			throw new IllegalArgumentException("book exists");
		if (bookId == null) {
			bookId = UUID.randomUUID().toString();
			book.setId(bookId);
		}
		books.put(bookId, book);
		return book;
	}
	
	private void validateBook(Book book) throws IllegalArgumentException {
		// book name can not be empty
		if (book.getName() == null)
			throw new IllegalArgumentException("bad book attributes");
		// price can be empty but not negative
		if (book.getPrice() != null && book.getPrice() < 0)
			throw new IllegalArgumentException("bad book attributes");
	}
	
	@Override
	public Collection<Book> listAll() {
		
		return books.values();
	}


	@Override
	public Book lookup(String bookId) {
		return books.get(bookId);
	}


	@Override
	public Book updateBook(String bookId, Book modified) throws IllegalArgumentException {
		Book book = lookup(bookId);
		if (book == null)
			return null;
		
        // if there's new name
        if (modified.getName() != null)
        	book.setName(modified.getName());
        
        // if there's new price
        if (modified.getPrice() != null)
        	book.setPrice(modified.getPrice());
        
        // validate the new attributes
        validateBook(book);
        
        return book;
	}

	@Override
	public void deleteBook(Book book) {
		books.remove(book.getId());
	}
}
