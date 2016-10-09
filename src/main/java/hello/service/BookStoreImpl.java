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
		if (!validateBook(book))
			throw new IllegalArgumentException("bad book attributes");
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

	
	private boolean validateBook(Book book) {
		if (book.getName() == null)
			return false;
		return true;
	}
	
	@Override
	public Collection<Book> listAll() {
		
		return books.values();
	}


	@Override
	public Book lookup(String bookId) {
		return books.get(bookId);
	}
}
