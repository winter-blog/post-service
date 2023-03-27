package com.devwinter.postservice.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Component
@ConfigurationProperties(prefix = "post")
public class PostLengthProperties {
	private PaginationProperties pagination;
	private TitleProperties title;
	private ContentsProperties contents;

	public int getDefaultPaginationSize() {
		return pagination.defaultSize;
	}
	public int getMaxTitleLength() {
		return title.maxLength;
	}
	public int getMaxContentsLength() {
		return contents.maxLength;
	}

	@Setter
	public static class PaginationProperties {
		private int defaultSize;
	}

	@Setter
	public static class TitleProperties {
		private int maxLength;
	}

	@Setter
	public static class ContentsProperties {
		private int maxLength;
	}
}