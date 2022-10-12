package apap.tugasindividu.sisdm.service;

import apap.tugasindividu.sisdm.model.KaryawanModel;
import apap.tugasindividu.sisdm.repository.KaryawanDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class KaryawanServiceImpl implements KaryawanService{
    @Autowired
    KaryawanDb KaryawanDb;

    @Override
    public List<KaryawanModel> getListKaryawan(){
        return KaryawanDb.findAll();
    }

    @Override
    public void addKaryawan(KaryawanModel karyawan){
        KaryawanDb.save(karyawan);
    }

    @Override
    public KaryawanModel getKaryawanByIdKaryawan(Long idKaryawan) {
        Optional<KaryawanModel> karyawan = KaryawanDb.findByIdKaryawan(idKaryawan);
        if (karyawan.isPresent()) {
            return karyawan.get();
        } else return null;
    }

    @Override
    public KaryawanModel updateKaryawan(KaryawanModel karyawan) {
        KaryawanDb.save(karyawan);
        return karyawan;
    }

    @Override
    public void deleteKaryawan(KaryawanModel karyawan) {
        KaryawanDb.delete(karyawan);
    }
}
