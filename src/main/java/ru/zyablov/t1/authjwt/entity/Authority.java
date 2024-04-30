package ru.zyablov.t1.authjwt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Сущность для работы с привилегиями пользователей в БД
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "security", name = "authority")
@Builder
public class Authority {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private String name;
}
