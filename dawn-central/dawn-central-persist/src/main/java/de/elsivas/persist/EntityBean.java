package de.elsivas.persist;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;

/**
 * Persistable Bean
 *
 */
public abstract class EntityBean {

	private Long id;

	private LocalDateTime validUntil;

	private String systemLabel;

	private final LocalDateTime created = LocalDateTime.now();

	public LocalDateTime getValidUntil() {
		return validUntil;
	}

	public Long getId() {
		return id;
	}

	public boolean isValid() {
		return getValidUntil().isAfter(LocalDateTime.now());
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public String getSystemLabel() {
		return systemLabel;
	}

	public void setSystemLabel(final String systemLabel) {
		this.systemLabel = StringUtils.abbreviate(systemLabel, 35);
	}

}
