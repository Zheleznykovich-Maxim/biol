package com.example.biol.models;

import com.example.biol.models.enums.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp = "^\\+375\\d{9}$", message = "Номер телефона должен быть в формате +375XXXXXXXXX")
    private String phoneNumber;
    @Size(min = 3, max = 20, message = "Имя пользователя должно содержать от 3 до 20 символов")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Имя пользователя может содержать только буквы и цифры")
    private String username;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    private Image avatar;
    private boolean active;
    @Email(message = "Некорректный формат email")
    private String email;
    private String activationCode;
    @Column(length = 1000)
//    @Size(min = 6, max = 20, message = "Пароль должен содержать от 6 до 20 символов")
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", message = "Пароль должен содержать хотя бы одну прописную букву, одну заглавную букву и одну цифру")
    private String password;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
    joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true,
            mappedBy = "user")
    private List<Product> productList = new ArrayList<>();

    public void addProductToUser(Product product) {
        product.setUser(this);
        productList.add(product);
    }

    //Security
    public boolean isAdmin() {
        return roles.contains(Role.ROLE_ADMIN);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return username;
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
        return active;
    }
}
