package cm.netconsulting.emprunt_service.entity;


import cm.netconsulting.emprunt_service.enumeration.TypeAction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "suivi_emprunts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuiviEmprunt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long empruntId;

    @Column(nullable = false)
    private Long livreId;

    private String livreTitre;

    @Column(nullable = false)
    private Long utilisateurId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeAction typeAction;

    @Column(nullable = false)
    private LocalDateTime dateAction;
}
