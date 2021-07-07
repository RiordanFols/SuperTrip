package ru.chernov.supertrip.domain;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Pavel Chernov
 */
public enum Role implements GrantedAuthority {
    USER,
    MANAGER,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
