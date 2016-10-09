package hello.client;

import java.util.Collection;

public interface BookStoreInterface {
	public Book addNewBook(Book book) throws IllegalArgumentException;
	public Collection<Book> listAll();
	public Book lookup(String bookId);
}
