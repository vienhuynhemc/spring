/* vienhuynhemc */
package com.vienhuynhemc.springdata.redis.controllers;

import com.vienhuynhemc.springdata.redis.dtos.FoodDto;
import jakarta.annotation.Nonnull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "api/v1/foods")
public interface FoodController {
  @GetMapping(path = "/{id}")
  ResponseEntity<FoodDto> getFood(@PathVariable @Nonnull String id);
}
