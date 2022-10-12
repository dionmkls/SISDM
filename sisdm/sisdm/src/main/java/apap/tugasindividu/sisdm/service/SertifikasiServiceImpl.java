package apap.tugasindividu.sisdm.service;

import apap.tugasindividu.sisdm.model.SertifikasiModel;
import apap.tugasindividu.sisdm.repository.SertifikasiDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SertifikasiServiceImpl implements SertifikasiService {
    @Autowired
    SertifikasiDb SertifikasiDb;

    @Override
    public List<SertifikasiModel> getListSertifikasi(){
        return SertifikasiDb.findAll();
    }

    @Override
    public SertifikasiModel getSertifikasiById(Long idSertifikasiKaryawan) {
        Optional<SertifikasiModel> sertifikasi = SertifikasiDb.findById(idSertifikasiKaryawan);
        if (sertifikasi.isPresent()) {
            return sertifikasi.get();
        } else return null;
    }
}