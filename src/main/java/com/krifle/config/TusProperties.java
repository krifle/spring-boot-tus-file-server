package com.krifle.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "tus")
public class TusProperties {

	private String version;
	private String resumableVersion;
	private String baseFilePath;
	private String extensions;
	private long maxSize;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getResumableVersion() {
		return resumableVersion;
	}

	public void setResumableVersion(String resumableVersion) {
		this.resumableVersion = resumableVersion;
	}

	public String getBaseFilePath() {
		return baseFilePath;
	}

	public void setBaseFilePath(String baseFilePath) {
		this.baseFilePath = baseFilePath;
	}

	public String getExtensions() {
		return extensions;
	}

	public void setExtensions(String extensions) {
		this.extensions = extensions;
	}

	public long getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(long maxSize) {
		this.maxSize = maxSize;
	}
}
