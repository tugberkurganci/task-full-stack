package com.tobeto_case.tobeto_case.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="tokens")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Token {

    @Id
    private String refreshToken;

    private String baseToken;

    @Transient
    private String prefix ="Bearer";

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Token(String token, String prefix) {
        this.refreshToken = token;
        this.prefix = prefix;
    }
}
