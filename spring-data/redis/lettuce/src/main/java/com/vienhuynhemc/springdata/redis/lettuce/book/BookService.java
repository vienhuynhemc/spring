/* vienhuynhemc */
package com.vienhuynhemc.springdata.redis.lettuce.book;

import org.springframework.stereotype.Service;

@Service
public class BookService {

  public Book getMyBook() {
    return new Book("Ngôi sao tinh tú", "Viên Huỳnh");
  }
}
