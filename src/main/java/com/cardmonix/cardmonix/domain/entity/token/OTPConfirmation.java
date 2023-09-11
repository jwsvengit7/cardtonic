package com.cardmonix.cardmonix.domain.entity.token;

import com.cardmonix.cardmonix.domain.entity.userModel.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "otp_confirmation")
public class OTPConfirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String otp_generator;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id" ,nullable = false)
    private User user;

    public OTPConfirmation(String otp_generator, User user) {
        this.otp_generator = otp_generator;
        this.user = user;
        this.expiresAt = calculateExpirationDate();
    }

    private LocalDateTime calculateExpirationDate() {
        return LocalDateTime.now().plusMinutes(5);
    }
}
