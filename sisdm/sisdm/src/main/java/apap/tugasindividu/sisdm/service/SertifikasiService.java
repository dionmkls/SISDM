package apap.tugasindividu.sisdm.service;

import apap.tugasindividu.sisdm.model.SertifikasiModel;

import java.util.List;

public interface SertifikasiService {
    List<SertifikasiModel> getListSertifikasi();

    SertifikasiModel getSertifikasiById(Long idSertifikasiKaryawan);
}