package apap.tugasindividu.sisdm.service;

import apap.tugasindividu.sisdm.model.KaryawanModel;
import apap.tugasindividu.sisdm.model.PresensiModel;

import java.util.List;

public interface PresensiService {
    List<PresensiModel> getListPresensi();
    void addPresensi(PresensiModel presensi);
    PresensiModel updatePresensi(PresensiModel presensi);
    PresensiModel getPresensiByIdPresensi(Long idPresensi);
}
