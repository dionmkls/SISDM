package apap.tugasindividu.sisdm.repository;

import apap.tugasindividu.sisdm.model.SertifikasiKaryawanModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SertifikasiKaryawanDb extends JpaRepository<SertifikasiKaryawanModel, String> {
}
