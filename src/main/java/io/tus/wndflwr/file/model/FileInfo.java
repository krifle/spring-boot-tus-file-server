package io.tus.wndflwr.file.model;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.io.BaseEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FileInfo {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileInfo.class);
	private static final int KEY_VALUE_PAIR_SIZE = 2;

	private String id;
	private long entityLength = -1L;
	private long offset = -1L;
	private String metadata;
	private String suggestedFileName;
	private String username;
	private Map<String, String> metadataMap;

	public FileInfo(long entityLength, String metadata, String username) {
		// TODO Currently fileId is randomly generated string.
		// TODO However, this should be replaced with the file path excluding base file directory name
		this.id = UUID.randomUUID().toString().replace("-", "_");
		this.entityLength = entityLength;
		this.metadata = metadata;
		this.username = username;
		metadataMap = decodeMetadata(metadata);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getEntityLength() {
		return entityLength;
	}

	public void setEntityLength(long entityLength) {
		this.entityLength = entityLength;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	public String getSuggestedFileName() {
		return suggestedFileName;
	}

	public void setSuggestedFileName(String suggestedFileName) {
		this.suggestedFileName = suggestedFileName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Map<String, String> getMetadataMap() {
		return metadataMap;
	}

	public void setMetadataMap(Map<String, String> metadataMap) {
		this.metadataMap = metadataMap;
	}

	public boolean alreadyHasEntireFile() {
		return offset == entityLength;
	}

	public boolean hasValidMetadata() {
		return !CollectionUtils.isEmpty(metadataMap);
	}

	public boolean transferCompleted(long transferred) {
		return transferred == entityLength;
	}

	private Map<String, String> decodeMetadata(String metadata) {
		Map<String, String> metadataMap = new HashMap<>();
		Splitter.on(",").trimResults().omitEmptyStrings().split(metadata).forEach(data -> {
			List<String> pair = Lists.newArrayList(Splitter.on(" ").trimResults().omitEmptyStrings().split(data));
			if (pair.size() != KEY_VALUE_PAIR_SIZE) {
				LOGGER.info("Invalid metadata form [{}]", metadata);
				return;
			}
			String key = pair.get(0);
			String value = new String(BaseEncoding.base64().decode(pair.get(1)));
			metadataMap.put(key, value);
		});
		return metadataMap;
	}
}
