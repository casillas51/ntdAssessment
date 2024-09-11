package com.ntdsoftware.homework.casillas.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Table(name = "tokens")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Token {

    @Id
    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "valid", nullable = false)
    private boolean valid;

    @Column(name = "path")
    private String path;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    public Token(String token, User user) {
        this.token = token;
        this.user = user;
        this.valid = true;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

}
