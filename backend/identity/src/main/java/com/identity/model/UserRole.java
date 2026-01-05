package com.identity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="user_roles", schema="identity")
@IdClass(UserRole.Pk.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserRole {

    @Id
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Id
    @Column(nullable = false, length = 32)
    private String role;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @PrePersist
    void onCreate(){
        this.createdAt = Instant.now();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class Pk implements Serializable {
        private UUID userId;
        private String role;

        @Override
        public boolean equals(Object o){
            if(this==o) return true;
            if(!(o instanceof Pk pk)) return false;
            return Objects.equals(userId, pk.userId) && Objects.equals(role, pk.role);
        }
        @Override
        public int hashCode(){
            return Objects.hash(userId, role);
        }

    }
}
