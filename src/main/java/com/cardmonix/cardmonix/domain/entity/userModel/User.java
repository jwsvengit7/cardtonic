package com.cardmonix.cardmonix.domain.entity.userModel;

import com.cardmonix.cardmonix.domain.constant.Role;
import com.cardmonix.cardmonix.domain.entity.account.Account;
import com.cardmonix.cardmonix.domain.entity.account.Deposit;
import com.cardmonix.cardmonix.domain.entity.coins.Giftcard;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name")
    private String user_name;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "activate")
    private boolean activate;
    @Column(name = "dateOfBirth")
    private String dob;
    @Column(name = "phone_number")
    private String phone;
    @Column(name = "profile_image")
    private String profile;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(cascade = CascadeType.REMOVE,mappedBy = "user",orphanRemoval = true)
    private Balance balance;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user",fetch = FetchType.EAGER)
    private List<Account> account;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user",fetch = FetchType.EAGER)
    private List<CoinWallet> coinWallets;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user", fetch = FetchType.EAGER)
    private List<Deposit> deposits = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user", fetch = FetchType.EAGER)
    private List<Giftcard> giftcards = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public User(String email,String password){
        this.email=email;
        this.password=password;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
