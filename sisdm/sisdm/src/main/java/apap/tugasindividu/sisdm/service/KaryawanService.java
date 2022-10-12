package apap.tugasindividu.sisdm.service;

import apap.tugasindividu.sisdm.model.KaryawanModel;

import java.util.List;

public interface KaryawanService {
    List<KaryawanModel> getListKaryawan();
    void addKaryawan(KaryawanModel karyawan);
    KaryawanModel getKaryawanByIdKaryawan(Long idKaryawan);
    KaryawanModel updateKaryawan(KaryawanModel karyawan);
    void deleteKaryawan(KaryawanModel karyawan);
}