package com.identity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="refresh_tokens", schema="identity")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RefreshToken {
    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "token_hash", nullable = false, length = 255)
    private String tokenHash;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "revoked_at")
    private Instant revokedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    // Track device/session info
    @Column(name="user_agent", length = 500)
    private String userAgent;

    @Column(name="ip_address", length = 45)
    private String ipAddress;

    @Column(name="device_name", length = 40)
    private String deviceName;

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }

    public boolean isRevoked() { return revokedAt != null; }
    public boolean isExpired() {
        Instant now = Instant.now();
        return expiresAt.isBefore(now) || expiresAt.equals(now);
    }
    public void revoke() {
        this.revokedAt = Instant.now();
    }
}
