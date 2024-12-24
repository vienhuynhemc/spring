/* vienhuynhemc */
package com.vienhuynhemc.springdata.redis.controllers.impls;

import com.vienhuynhemc.springdata.redis.controllers.FoodController;
import com.vienhuynhemc.springdata.redis.dtos.FoodDto;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class FoodControllerImpl implements FoodController {

  @Override
  public ResponseEntity<FoodDto> getFood(@Nonnull String id) {
    final FoodDto foodDto = FoodDto.builder().name("Hello world").build();
    return ResponseEntity.ok(foodDto);
  }
}
