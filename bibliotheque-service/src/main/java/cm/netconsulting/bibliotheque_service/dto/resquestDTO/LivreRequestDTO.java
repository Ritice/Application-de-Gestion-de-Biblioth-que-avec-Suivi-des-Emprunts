package cm.netconsulting.bibliotheque_service.dto.resquestDTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class LivreRequestDTO {
    private String titre;
    private String resume;
    private int seuilMinimal;
    private int totalExemplaires;
    private int exemplairesDisponibles;
    private String category;
    private MultipartFile imgCouverture;

    private List<Long> auteurIds;
}
