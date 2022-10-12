package apap.tugasindividu.sisdm.service;

import apap.tugasindividu.sisdm.model.KaryawanModel;
import apap.tugasindividu.sisdm.model.PresensiModel;
import apap.tugasindividu.sisdm.repository.KaryawanDb;
import apap.tugasindividu.sisdm.repository.PresensiDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PresensiServiceImpl implements PresensiService{
    @Autowired
    PresensiDb PresensiDb;

    @Override
    public List<PresensiModel> getListPresensi(){
        return PresensiDb.findAll();
    }

    @Override
    public void addPresensi(PresensiModel presensi){
        if (checkLate(presensi.getWaktuMasuk())) {
            presensi.setStatus(0);
        }
        else {
            presensi.setStatus(1);
        }
        PresensiDb.save(presensi);
    }

    @Override
    public PresensiModel getPresensiByIdPresensi(Long idPresensi) {
        Optional<PresensiModel> presensi = PresensiDb.findByIdPresensi(idPresensi);
        if (presensi.isPresent()) {
            return presensi.get();
        } else return null;
    }

    @Override
    public PresensiModel updatePresensi(PresensiModel presensi) {
        PresensiDb.save(presensi);
        return presensi;
    }

    public boolean checkLate(LocalTime waktuMasuk) {
        if (waktuMasuk.isAfter(LocalTime.parse("07:00"))) {
            return true;
        } else {
            return false;
        }
    }
}
