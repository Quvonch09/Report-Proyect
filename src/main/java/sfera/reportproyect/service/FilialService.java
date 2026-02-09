package sfera.reportproyect.service;

import org.springframework.stereotype.Service;
import sfera.reportproyect.dto.ApiResponse;
import sfera.reportproyect.dto.request.ReqFilial;
import sfera.reportproyect.repository.FilialRepository;

@Service
public class FilialService {

    private FilialRepository filialRepository;

    public ApiResponse<String> addFilial(ReqFilial req) {
        if (req.getName() == null || !req.getName().isEmpty()) {

        }


    }

    
}
