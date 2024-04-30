package ru.zyablov.t1.authjwt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Сущность пользователя в БД
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "security", name = "user")
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(schema = "security", name = "user_authority",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_authority"))
    private List<Authority> authorities;
}
