package apap.tugasindividu.sisdm.service;

import apap.tugasindividu.sisdm.model.PresensiModel;
import apap.tugasindividu.sisdm.model.SertifikasiModel;
import apap.tugasindividu.sisdm.model.TugasModel;

import java.util.List;

public interface TugasService {
    List<TugasModel> getListTugas();
    TugasModel getTugasByIdTugas(Long idTugas);
    void addTugas(TugasModel tugas);
    TugasModel updateTugas(TugasModel tugas);
}
