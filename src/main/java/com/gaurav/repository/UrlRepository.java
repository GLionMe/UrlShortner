package com.gaurav.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gaurav.model.Url;

public interface UrlRepository extends JpaRepository<Url, Long> {
	Url findByShortUrl(String shortLink);
}
