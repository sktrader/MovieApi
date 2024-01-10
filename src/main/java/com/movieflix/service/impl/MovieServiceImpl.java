package com.movieflix.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.movieflix.dtos.MovieDto;
import com.movieflix.entities.Movie;
import com.movieflix.repositories.MovieRepository;
import com.movieflix.service.FileService;
import com.movieflix.service.MovieService;
@Service
public class MovieServiceImpl implements MovieService{
	
	private final MovieRepository movieRepository;
	
	private final FileService fileService;
	
	public MovieServiceImpl(MovieRepository movieRepository,FileService fileService) {
		this.movieRepository = movieRepository;
		this.fileService = fileService;
	}

	@Value("${project.poster}")
	private String path;
	
	@Value("${base.url}")
	private String baseUrl;
	
	@Override
	public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {
		// 1.upload the file 
		String uploadedFileName = fileService.uploadFile(path, file);
		// 2.set the value of field poster as filename
		movieDto.setPoster(uploadedFileName);
		// 3.map dto to entity
		Movie movie =new Movie(
				movieDto.getMovieId(),
				movieDto.getTitle(),
				movieDto.getDirector(),
				movieDto.getStudio(),
				movieDto.getMovieCast(),
				movieDto.getReleaseYear(),
				movieDto.getPoster()
				);
		// 4.save the movie object
		Movie savedMovie = movieRepository.save(movie);
		// 5.generate the url posterUrl
		String posterUrl = baseUrl + "/file/" + uploadedFileName;
		// 6.return map entity to dto
		MovieDto responDto = new MovieDto(
				savedMovie.getMovieId(),
				savedMovie.getTitle(),
				savedMovie.getDirector(),
				savedMovie.getStudio(),
				savedMovie.getMovieCast(),
				savedMovie.getReleaseYear(),
				savedMovie.getPoster(),
				posterUrl
				);
		return responDto;
	}

	@Override
	public MovieDto getMovie(Integer movieId) {
		// 1.
		return null;
	}

	@Override
	public List<MovieDto> getAllMovies() {
		// TODO Auto-generated method stub
		return null;
	}

}
