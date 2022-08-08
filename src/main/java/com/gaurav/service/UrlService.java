package com.gaurav.service;

import org.springframework.stereotype.Service;

import com.gaurav.model.Url;
import com.gaurav.model.UrlDto;

@Service
public interface UrlService {
	Url generateShortLink(UrlDto urlDto);
	Url persistShortLink(Url url);
	Url getEncodedUrl(String url);
	void deleteShortLink(Url url);
}
