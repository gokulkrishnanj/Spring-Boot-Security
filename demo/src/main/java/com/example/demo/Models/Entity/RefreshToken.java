package com.example.demo.Models.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "REFRESH_TOKEN")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "REFRESH_TOKEN_VALUE")
    private String refreshToken;

    @Column(name = "EXPIRATION_TIME")
    private Date expiry;

    @OneToOne
    @JoinColumn(name = "USER_TOKEN_MAP")
    private User user;
}
