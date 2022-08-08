package com.gaurav.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaurav.model.Url;
import com.gaurav.model.UrlDto;
import com.gaurav.repository.UrlRepository;
import com.google.common.hash.Hashing;

@Service
public class UrlServiceImpl implements UrlService {
	
	@Autowired
	private UrlRepository repository;
	
	@Override
	public Url generateShortLink(UrlDto urlDto) {
		if(StringUtils.isNotEmpty(urlDto.getUrl())) {
			String encodedUrl = encodeUrl(urlDto.getUrl());
			Url url = new Url();
			url.setCreatedDate(LocalDateTime.now());
			url.setExpirationDate(getExpirationDate(urlDto.getExpirationDate(), url.getCreatedDate()));
			url.setOriginalUrl(urlDto.getUrl());
			url.setShortUrl(encodedUrl);
			return persistShortLink(url);
		}
		return null;
	}

	private LocalDateTime getExpirationDate(String expirationDate, LocalDateTime createdDate) {
		if(StringUtils.isBlank(expirationDate)) {
			return createdDate.plusDays(2);
		}
		
		return LocalDateTime.parse(expirationDate);
	}

	private String encodeUrl(String url) {
		LocalDateTime time = LocalDateTime.now();
		
		String encodedUrl = Hashing
				.murmur3_128()
				.hashString(url.concat(time.toString()), StandardCharsets.UTF_8)
				.toString();
		return encodedUrl;
	}

	@Override
	public Url persistShortLink(Url url) {
		return repository.save(url);
	}

	@Override
	public Url getEncodedUrl(String url) {
		return repository.findByShortUrl(url);
	}

	@Override
	public void deleteShortLink(Url url) {
		repository.delete(url);
	}

}
