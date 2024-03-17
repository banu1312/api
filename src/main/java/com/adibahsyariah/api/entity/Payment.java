package com.adibahsyariah.api.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NamaHibah",length = 255)
    private String namaHibah;

    @Column
    private Integer nominal;

    @Column
    private String metode;

    @Column 
    private String reveal;

    @Column(length = 700)
    private String pesan;

    @Column
    private String status;

    @Column
    private String filePath;

    private Long user_id;

    @Column
    private LocalDateTime create_at;

     @PrePersist
    public void setCreatedAt() {
        this.create_at = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;
}
