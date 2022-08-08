package com.gaurav.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Url {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Lob
	private String originalUrl;
	private String shortUrl;
	private LocalDateTime createdDate;
	private LocalDateTime expirationDate;
	public Url() {
		super();
	}
	public Url(long id, String originalUrl, String shortUrl, LocalDateTime createdDate, LocalDateTime expirationDate) {
		super();
		this.id = id;
		this.originalUrl = originalUrl;
		this.shortUrl = shortUrl;
		this.createdDate = createdDate;
		this.expirationDate = expirationDate;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOriginalUrl() {
		return originalUrl;
	}
	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}
	public String getShortUrl() {
		return shortUrl;
	}
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(LocalDateTime expirationDate) {
		this.expirationDate = expirationDate;
	}
	@Override
	public String toString() {
		return "Url [id=" + id + ", originalUrl=" + originalUrl + ", shortUrl=" + shortUrl + ", createdDate="
				+ createdDate + ", expirationDate=" + expirationDate + "]";
	}
}
