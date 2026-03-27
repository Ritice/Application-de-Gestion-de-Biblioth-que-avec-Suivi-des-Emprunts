package cm.netconsulting.emprunt_service.entity;


import cm.netconsulting.emprunt_service.enumeration.StatutEmprunt;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "emprunts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Emprunt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long utilisateurId;

    @Column(nullable = false)
    private Long livreId;


    private String livreTitre;

    @CreationTimestamp
    private LocalDateTime dateEmprunt;

    private LocalDateTime dateRetourPrevue;

    private LocalDateTime dateRetourReelle;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutEmprunt statut;
}