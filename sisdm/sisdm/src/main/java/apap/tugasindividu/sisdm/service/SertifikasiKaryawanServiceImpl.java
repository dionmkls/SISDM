package apap.tugasindividu.sisdm.service;

import apap.tugasindividu.sisdm.model.SertifikasiKaryawanModel;
import apap.tugasindividu.sisdm.repository.SertifikasiKaryawanDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SertifikasiKaryawanServiceImpl implements SertifikasiKaryawanService {
    @Autowired
    SertifikasiKaryawanDb SertifikasiKaryawanDb;

    @Override
    public List<SertifikasiKaryawanModel> getListSertifikasiKaryawan(){
        return SertifikasiKaryawanDb.findAll();
    }
}
