package com.cardmonix.cardmonix.domain.entity.token;

import com.cardmonix.cardmonix.domain.constant.TokenType;
import com.cardmonix.cardmonix.domain.entity.userModel.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jwttoken")
public class JwtToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer token_id;

    @Column(unique = true)
    public String token;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;
    public boolean revoked;
    public boolean expired;

    private String refreshToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    public User user;
    private Date generatedAt;
    private Date expiresAt;
    private Date refreshedAt;

    // eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaGlvcmx1amFja0BnbWFpbC5jb20iLCJpYXQiOjE2ODkxMDQwMTQsImV4cCI6MTY4OTE5MDQxNH0.p1n5eEl7hF3qIO92G7cAzKoJkO_foshBfgOFEHcrHrQ

}
