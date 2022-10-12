package apap.tugasindividu.sisdm.service;

import apap.tugasindividu.sisdm.model.KaryawanModel;
import apap.tugasindividu.sisdm.model.PresensiModel;
import apap.tugasindividu.sisdm.model.SertifikasiModel;
import apap.tugasindividu.sisdm.model.TugasModel;
import apap.tugasindividu.sisdm.repository.SertifikasiDb;
import apap.tugasindividu.sisdm.repository.TugasDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TugasServiceImpl implements TugasService {
    @Autowired
    TugasDb TugasDb;

    @Override
    public List<TugasModel> getListTugas(){
        return TugasDb.findAll();
    }

    @Override
    public TugasModel getTugasByIdTugas(Long idTugas) {
        Optional<TugasModel> tugas = TugasDb.findByIdTugas(idTugas);
        if (tugas.isPresent()) {
            return tugas.get();
        } else return null;
    }

    @Override
    public void addTugas(TugasModel tugas){
        TugasDb.save(tugas);
    }

    @Override
    public TugasModel updateTugas(TugasModel tugas) {
        TugasDb.save(tugas);
        return tugas;
    }
}