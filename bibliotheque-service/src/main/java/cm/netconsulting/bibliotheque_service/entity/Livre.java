package cm.netconsulting.bibliotheque_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "livre")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    @Column(columnDefinition = "TEXT")
    private String resume;

    private int seuilMinimal;
    private int totalExemplaires;
    private int exemplairesDisponibles;

    @Lob
    private byte[] imgCouverture;

    private String category;

    @ManyToMany(mappedBy = "livres")
    private List<Auteur> auteurs;


    public void addAuteur(Auteur auteur) {
        this.auteurs.add(auteur);
        auteur.getLivres().add(this);
    }

}