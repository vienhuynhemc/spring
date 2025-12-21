/* vienhuynhemc */
package com.vienhuynhemc.idempotent_batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SingleDatasourceApplication {

  public static void main(String[] args) {
    SpringApplication.run(SingleDatasourceApplication.class, args);
  }
}
