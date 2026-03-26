package cm.netconsulting.bibliotheque_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "auteur")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Auteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String adresse;
    private String nationalite;

    @ManyToMany
    @JoinTable(
            name = "auteur_livre",
            joinColumns = @JoinColumn(name = "auteur_id"),
            inverseJoinColumns = @JoinColumn(name = "livre_id")
    )
    private List<Livre> livres;


    public void addLivre(Livre livre) {
        this.livres.add(livre);
        livre.getAuteurs().add(this);
    }
}


