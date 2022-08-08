package com.gaurav.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gaurav.model.Url;
import com.gaurav.model.UrlDto;
import com.gaurav.model.UrlErrorResponseDto;
import com.gaurav.model.UrlResponseDto;
import com.gaurav.service.UrlService;

@RestController
public class UrlShorteningController {
	
	@Autowired
	private UrlService service;
	
	@PostMapping("/generate")
	public ResponseEntity<?> generateShortLink(@RequestBody UrlDto urlDto) {
		Url url = service.generateShortLink(urlDto);
		
		if(url != null) {
			UrlResponseDto responseDto = new UrlResponseDto();
			responseDto.setOriginalUrl(url.getOriginalUrl());
			responseDto.setShortLink(url.getShortUrl());
			responseDto.setExpirationDate(url.getExpirationDate());
			return new ResponseEntity<UrlResponseDto>(responseDto, HttpStatus.OK);
		}
		
		UrlErrorResponseDto errorResponseDto = new UrlErrorResponseDto();
		errorResponseDto.setError("404");
		errorResponseDto.setError("Error creating short url. Try sometime later.");
		return new ResponseEntity<UrlErrorResponseDto>(errorResponseDto, HttpStatus.EXPECTATION_FAILED);
	}
	
	@GetMapping("/{shortUrl}")
	public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
		if(StringUtils.isBlank(shortUrl)) {
			return new ResponseEntity<UrlErrorResponseDto>(
					new UrlErrorResponseDto("400", "Invalid Input"),
					HttpStatus.BAD_REQUEST
					);
		}
		
		Url url = service.getEncodedUrl(shortUrl);
		if(url == null) {
			return new ResponseEntity<UrlErrorResponseDto>(
					new UrlErrorResponseDto("400", "Invalid Input"),
					HttpStatus.OK
					);
		}
		
		if(url.getExpirationDate().isBefore(LocalDateTime.now())) {
			service.deleteShortLink(url);
			return new ResponseEntity<UrlErrorResponseDto>(
					new UrlErrorResponseDto("400", "Invalid Input"),
					HttpStatus.OK
					);
		}
		
		UrlResponseDto responseDto = new UrlResponseDto();
		responseDto.setOriginalUrl(url.getOriginalUrl());
		responseDto.setShortLink(shortUrl);
		responseDto.setExpirationDate(url.getExpirationDate());
		
		response.sendRedirect(url.getOriginalUrl());
		return new ResponseEntity<UrlResponseDto>(responseDto, HttpStatus.OK);
	}
	
	@DeleteMapping("/remove/{shortUrl}")
	public void deleteShortUrl(@PathVariable String shortUrl) {
		Url url = service.getEncodedUrl(shortUrl);
		
		if(url != null)
			service.deleteShortLink(url);
	}
	
}
