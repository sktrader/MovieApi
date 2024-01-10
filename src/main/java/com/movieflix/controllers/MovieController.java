package com.movieflix.controllers;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieflix.dtos.MovieDto;
import com.movieflix.service.MovieService;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {
	
	private final MovieService movieService;
	
	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}
	
	@PostMapping("/add-movie")
	public ResponseEntity<MovieDto> addMovieHandler(@RequestPart MultipartFile file,@RequestPart String movieDto) throws IOException{
		MovieDto dto=convertToMovieDto(movieDto);
		return new ResponseEntity(movieService.addMovie(dto, file),HttpStatus.CREATED);
	}
	
	private MovieDto convertToMovieDto(String movieDtoObj) throws JsonMappingException, JsonProcessingException {
		//MovieDto movieDto= new MovieDto();
		ObjectMapper objectMapper =new ObjectMapper();
		MovieDto movieDto =objectMapper.readValue(movieDtoObj, MovieDto.class);
		return movieDto;
	}

}
