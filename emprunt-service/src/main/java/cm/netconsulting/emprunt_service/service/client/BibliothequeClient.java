package cm.netconsulting.emprunt_service.service.client;

import cm.netconsulting.emprunt_service.response.Response;
import cm.netconsulting.emprunt_service.service.client.dto.LivreResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(
        name = "bibliotheque-client",
        url  = "${services.bibliotheque-url}"
)
public interface BibliothequeClient {

    @GetMapping("/api/v1/livres/{id}")
    Response<LivreResponseDTO> getLivre(@PathVariable("id") Long id);


    @PutMapping("/api/v1/livres/{id}/decrementer")
    Response<Void> decrementerExemplaires(@PathVariable("id") Long id);


    @PutMapping("/api/v1/livres/{id}/incrementer")
    Response<Void> incrementerExemplaires(@PathVariable("id") Long id);
}
