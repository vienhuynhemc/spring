/* vienhuynhemc */
package com.vienhuynhemc.springdata.redis.lettuce.book;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book implements Serializable {

  private String name;
  private String author;
}
