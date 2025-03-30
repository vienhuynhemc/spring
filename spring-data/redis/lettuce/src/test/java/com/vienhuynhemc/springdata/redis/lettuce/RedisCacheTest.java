/* vienhuynhemc */
package com.vienhuynhemc.springdata.redis.lettuce;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vienhuynhemc.springdata.redis.lettuce.book.Book;
import com.vienhuynhemc.springdata.redis.lettuce.book.BookController;
import com.vienhuynhemc.springdata.redis.lettuce.book.BookService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class RedisCacheTest {

  @Autowired
  private BookController bookController;

  @Autowired
  private RedisCacheManager redisCacheManager;

  @MockitoBean
  private BookService bookService;

  @Test
  void shouldCacheMyBook() {
    // Arrange
    final Book myBook = new Book("Ngôi làng dục vọng", "Viên Huỳnh");
    when(bookService.getMyBook()).thenReturn(myBook);

    // Act
    bookController.getMyBook(true);
    bookController.getMyBook(true);
    bookController.getMyBook(true);
    final Book result = bookController.getMyBook(true);

    // Act
    assertEquals(myBook.getName(), result.getName());
    assertEquals(myBook.getAuthor(), result.getAuthor());
    verify(bookService, times(1)).getMyBook();

    // Tear down
    Optional.ofNullable(redisCacheManager.getCache("my-book")).ifPresent(Cache::clear);
  }
}
