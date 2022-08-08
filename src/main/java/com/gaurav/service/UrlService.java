package com.gaurav.service;

import com.gaurav.model.Url;
import com.gaurav.model.UrlDto;

public interface UrlService {
	Url generateShortLink(UrlDto urlDto);
	Url persistShortLink(Url url);
	Url getEncodedUrl(String url);
	void deleteShortLink(Url url);
}
