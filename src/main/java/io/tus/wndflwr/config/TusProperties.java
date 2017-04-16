package io.tus.wndflwr.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.regex.Matcher;

@Component
@ConfigurationProperties(prefix = "tus")
public class TusProperties {

	private String version;
	private String resumableVersion;
	private String binFilePath;
	private String infoFilePath;
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

	public String getBinFilePath() {
		return binFilePath.replaceAll("[/\\\\]", Matcher.quoteReplacement(File.separator));
	}

	public void setBinFilePath(String binFilePath) {
		this.binFilePath = binFilePath;
	}

	public String getInfoFilePath() {
		return infoFilePath.replaceAll("[/\\\\]", Matcher.quoteReplacement(File.separator));
	}

	public void setInfoFilePath(String infoFilePath) {
		this.infoFilePath = infoFilePath;
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
