package cm.netconsulting.emprunt_service.mapper;

import cm.netconsulting.emprunt_service.dto.responseDTO.EmpruntResponse;
import cm.netconsulting.emprunt_service.dto.responseDTO.SuiviResponse;
import cm.netconsulting.emprunt_service.entity.Emprunt;
import cm.netconsulting.emprunt_service.entity.SuiviEmprunt;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class EmpruntMapper {

    public EmpruntResponse toEmpruntResponse(Emprunt emprunt) {
        EmpruntResponse response = new EmpruntResponse();
        BeanUtils.copyProperties(emprunt, response);
        return response;
    }

    public SuiviResponse toSuiviResponse(SuiviEmprunt suiviEmprunt) {
        SuiviResponse response = new SuiviResponse();
        BeanUtils.copyProperties(suiviEmprunt, response);
        return response;
    }
}
