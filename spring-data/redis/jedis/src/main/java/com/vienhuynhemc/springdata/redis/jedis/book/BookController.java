/* vienhuynhemc */
package com.vienhuynhemc.springdata.redis.jedis.book;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookController {

  private final BookService service;

  @Cacheable(cacheNames = "my-book", key = "'my-book.'+#someCondition")
  public Book getMyBook(boolean someCondition) {
    return service.getMyBook();
  }
}
