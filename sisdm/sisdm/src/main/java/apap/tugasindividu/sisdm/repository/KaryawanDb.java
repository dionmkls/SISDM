package apap.tugasindividu.sisdm.repository;


import apap.tugasindividu.sisdm.model.KaryawanModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface KaryawanDb extends JpaRepository<KaryawanModel, String> {

    Optional<KaryawanModel> findByIdKaryawan(Long idKaryawan);
}
