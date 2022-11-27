package com.dh.catalog.controller;

import com.dh.catalog.client.MovieServiceClient;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogController {

	private final MovieServiceClient movieServiceClient;

	public CatalogController(MovieServiceClient movieServiceClient) {
		this.movieServiceClient = movieServiceClient;
	}

	@CircuitBreaker(name="subscriptionMovie", fallbackMethod = "subscriptionFallbackMethod")
	@GetMapping("/{genre}")
	ResponseEntity<List<MovieServiceClient.MovieDto>> getGenre(@PathVariable String genre) {
		return ResponseEntity.ok(movieServiceClient.getMovieByGenre(genre));
	}

	private ResponseEntity subscriptionFallbackMethod(CallNotPermittedException exception) {
		return new ResponseEntity<>("El microservició falló", HttpStatus.FAILED_DEPENDENCY);
	}
}
