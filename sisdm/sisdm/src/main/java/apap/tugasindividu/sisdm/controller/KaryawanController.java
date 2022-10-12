package apap.tugasindividu.sisdm.controller;

import apap.tugasindividu.sisdm.model.KaryawanModel;
import apap.tugasindividu.sisdm.model.SertifikasiKaryawanModel;
import apap.tugasindividu.sisdm.model.SertifikasiModel;
import apap.tugasindividu.sisdm.service.KaryawanService;
import apap.tugasindividu.sisdm.service.SertifikasiKaryawanService;
import apap.tugasindividu.sisdm.service.SertifikasiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class KaryawanController {
    @Autowired
    private KaryawanService karyawanService;

    @Autowired
    private SertifikasiService sertifikasiService;

    @Autowired
    private SertifikasiKaryawanService sertifikasiKaryawanService;

    //    Sumber : https://coderanch.com/t/738297/java/Converting-String-Letters-Numbers
    private static int charToCode(char c) throws IllegalArgumentException {
        if (c == ' ') return 0;
        if (c >= 'A' && c <= 'Z') return c - 'A' + 1;
        throw new IllegalArgumentException(
                "Only spaces and capital letters are allowed");
    }

    //    View All Karyawan
    @GetMapping("karyawan")
    public String listKaryawan(Model model) {
        List<KaryawanModel> listKaryawan = karyawanService.getListKaryawan();
        model.addAttribute("listKaryawan", listKaryawan);
        return "viewall-karyawan";
    }

    //    Add Karyawan
    @GetMapping("karyawan/tambah")
    public String addKaryawanFormPage(Model model) {
        KaryawanModel karyawan = new KaryawanModel();
        List<SertifikasiModel> listSertifikasi = sertifikasiService.getListSertifikasi();
        List<SertifikasiKaryawanModel> listSertifikasiKaryawanNew = new ArrayList<>();

        karyawan.setListSertifikasiKaryawan(listSertifikasiKaryawanNew);
        karyawan.getListSertifikasiKaryawan().add(new SertifikasiKaryawanModel());

        model.addAttribute("karyawan", karyawan);
        model.addAttribute("listSertifikasiExisting", listSertifikasi);

        return "form-add-karyawan";
    }

    @PostMapping(value = "karyawan/tambah", params = {"save"})
    public String addKaryawanSubmitPage(@ModelAttribute KaryawanModel karyawan, Model model) {
        List <SertifikasiKaryawanModel> listSertifikasiKaryawanNew =  karyawan.getListSertifikasiKaryawan();

        if (karyawan.getListSertifikasiKaryawan() == null) {
            karyawan.setListSertifikasiKaryawan(new ArrayList<>());
        } else {
            for(int i = 0; i < listSertifikasiKaryawanNew.size(); i++) {
                listSertifikasiKaryawanNew.get(i).setNoSertifikasi("temp");
                Long idSertifikasi = listSertifikasiKaryawanNew.get(i).getKeyId().getIdSertifikasi();
                listSertifikasiKaryawanNew.get(i).setSertifikasi(sertifikasiService.getSertifikasiById(idSertifikasi));
                listSertifikasiKaryawanNew.get(i).setKaryawan(karyawan);
            }
        }

        karyawanService.addKaryawan(karyawan);

        for (int i = 0; i < listSertifikasiKaryawanNew.size(); i++) {
            String noSertifikasi = "SER";
            LocalDate tanggalPengambilan = listSertifikasiKaryawanNew.get(i).getTanggalPengambilan();

            int tanggalUnique = tanggalPengambilan.getYear() + Integer.parseInt(tanggalPengambilan.getDayOfMonth() + "" + tanggalPengambilan.getMonthValue());
            char namaKaryawan = listSertifikasiKaryawanNew.get(i).getKaryawan().getNamaDepan().toUpperCase().charAt(0);
            char namaSertifikasi = listSertifikasiKaryawanNew.get(i).getSertifikasi().getNama().toUpperCase().charAt(0);

            int positionNamaKaryawan = charToCode(namaKaryawan);
            int positionNamaSertifikasi = charToCode(namaSertifikasi);

            String idUnique = String.format("%02d%02d%03d", positionNamaKaryawan, positionNamaSertifikasi, listSertifikasiKaryawanNew.get(i).getKaryawan().getIdKaryawan());
            noSertifikasi = noSertifikasi + tanggalUnique + idUnique;

            listSertifikasiKaryawanNew.get(i).setNoSertifikasi(noSertifikasi);
        }

        karyawanService.updateKaryawan(karyawan);

        String namaPanjang = karyawan.getNamaDepan() + " " + karyawan.getNamaBelakang();
        System.out.println(namaPanjang);
        model.addAttribute("karyawan", namaPanjang);
        return "add-karyawan";
    }

    //    Add Karyawan (add row)
    @PostMapping(value = "/karyawan/tambah", params = {"addRowSertifikasi"})
    private String addRowSertifikasiMultiple(
            @ModelAttribute KaryawanModel karyawan,
            Model model
    ) {
        if (karyawan.getListSertifikasiKaryawan() == null || karyawan.getListSertifikasiKaryawan().size() == 0) {
            karyawan.setListSertifikasiKaryawan(new ArrayList<>());
        }
        karyawan.getListSertifikasiKaryawan().add(new SertifikasiKaryawanModel());
        List<SertifikasiModel> listSertifikasi = sertifikasiService.getListSertifikasi();

        model.addAttribute("karyawan", karyawan);
        model.addAttribute("listSertifikasiExisting", listSertifikasi);

        return "form-add-karyawan";
    }

    @PostMapping(value = "/karyawan/tambah", params = {"deleteRowSertifikasi"})
    private String deleteRowSertifikasiMultiple(
            @ModelAttribute KaryawanModel karyawan,
            @RequestParam("deleteRowSertifikasi") Integer row,
            Model model
    ) {
        final Integer rowId = Integer.valueOf(row);
        karyawan.getListSertifikasiKaryawan().remove(rowId.intValue());

        List<SertifikasiModel> listSertifikasiKaryawan = sertifikasiService.getListSertifikasi();

        model.addAttribute("karyawan", karyawan);
        model.addAttribute("listSertifikasiExisting", listSertifikasiKaryawan);

        return "form-add-karyawan";
    }

    //    Detail Karyawan
    @GetMapping("karyawan/{idKaryawan}")
    public String viewDetailCoursePage(@PathVariable Long idKaryawan, Model model) {
        KaryawanModel karyawan = karyawanService.getKaryawanByIdKaryawan(idKaryawan);

        List<SertifikasiKaryawanModel> listSertifikasiKaryawan = karyawan.getListSertifikasiKaryawan();
        List<SertifikasiModel> listSertifikasi = sertifikasiService.getListSertifikasi();

        model.addAttribute("listSertifikasiExisting", listSertifikasi);
        model.addAttribute("listSertifikasiKaryawan", listSertifikasiKaryawan);
        model.addAttribute("karyawan", karyawan);


        return "view-karyawan";
    }

    //    Update Karyawan
    @GetMapping(value = "/karyawan/{idKaryawan}/ubah")
    public String updateKaryawanFormPage(@PathVariable Long idKaryawan, Model model) {
        KaryawanModel karyawan = karyawanService.getKaryawanByIdKaryawan(idKaryawan);

        List<SertifikasiModel> listSertifikasi = sertifikasiService.getListSertifikasi();
        List<SertifikasiKaryawanModel> listSertifikasiKaryawanEx = karyawan.getListSertifikasiKaryawan();

        karyawan.setListSertifikasiKaryawan(listSertifikasiKaryawanEx);
//        karyawan.getListSertifikasiKaryawan().add(new SertifikasiKaryawanModel());

        model.addAttribute("karyawan", karyawan);
        model.addAttribute("listSertifikasiExisting", listSertifikasi);
        model.addAttribute("listSertifikasiKaryawan", listSertifikasiKaryawanEx);

        return "form-update-karyawan";
    }

    @PostMapping(value = "/karyawan/{idKaryawan}/ubah")
    public String updateKaryawanSubmitPage(@ModelAttribute KaryawanModel karyawan, Model model) {
        KaryawanModel updatedKaryawan = karyawanService.updateKaryawan(karyawan);
        String namaPanjang = updatedKaryawan.getNamaDepan() + " " + updatedKaryawan.getNamaBelakang();
        System.out.println(namaPanjang);
        model.addAttribute("karyawan", namaPanjang);

        return "update-karyawan";
    }

    //    Update Karyawan (add row)
    @PostMapping(value = "/karyawan/{idKaryawan}/ubah", params = {"addRowSertifikasiUpdate"})
    private String addRowSertifikasiUpdateMultiple(
            @ModelAttribute KaryawanModel karyawan,
            Model model
    ) {
        if (karyawan.getListSertifikasiKaryawan() == null || karyawan.getListSertifikasiKaryawan().size() == 0) {
            karyawan.setListSertifikasiKaryawan(new ArrayList<>());
        }
        karyawan.getListSertifikasiKaryawan().add(new SertifikasiKaryawanModel());
        List<SertifikasiModel> listSertifikasi = sertifikasiService.getListSertifikasi();
        List<SertifikasiKaryawanModel> listSertifikasiKaryawanEx = karyawan.getListSertifikasiKaryawan();

        karyawan.setListSertifikasiKaryawan(listSertifikasiKaryawanEx);

        model.addAttribute("karyawan", karyawan);
        model.addAttribute("listSertifikasiExisting", listSertifikasi);
        model.addAttribute("listSertifikasiKaryawanEx", listSertifikasiKaryawanEx);

        return "form-update-karyawan";
    }

    @PostMapping(value = "/karyawan/{idKaryawan}/ubah", params = {"deleteRowSertifikasiUpdate"})
    private String deleteRowSertifikasiMultipleUpdate (
            @ModelAttribute KaryawanModel karyawan,
            @RequestParam("deleteRowSertifikasiUpdate") Integer row,
            Model model
    ) {
        final Integer rowId = Integer.valueOf(row);
        karyawan.getListSertifikasiKaryawan().remove(rowId.intValue());

        List<SertifikasiModel> listSertifikasiKaryawan = sertifikasiService.getListSertifikasi();

        model.addAttribute("karyawan", karyawan);
        model.addAttribute("listSertifikasiExisting", listSertifikasiKaryawan);

        return "form-update-karyawan";
    }

    //    Delete Karyawan
    @PostMapping(value = "/karyawan/{idKaryawan}/hapus", params = {"hapusKaryawan"})
    public String deleteKaryawanFormPage(@RequestParam("hapusKaryawan") Long idKaryawan, Model model) {
        KaryawanModel karyawan = karyawanService.getKaryawanByIdKaryawan(idKaryawan);
        String namaPanjang = karyawan.getNamaDepan() + " " + karyawan.getNamaBelakang();
        karyawanService.deleteKaryawan(karyawan);
        model.addAttribute("karyawan", namaPanjang);

        return "delete-karyawan";
    }

    //    Filter Karyawan By Sertifikasi
    @GetMapping("/filter-sertifikasi")
    public String filterSertifikasiFormPage(Model model) {
        List<SertifikasiModel> listSertifikasi = sertifikasiService.getListSertifikasi();
        List<KaryawanModel> listKaryawan = karyawanService.getListKaryawan();

        model.addAttribute("listKaryawan", listKaryawan);
        model.addAttribute("listSertifikasi", listSertifikasi);

        return "filter-sertifikasi";
    }

    @GetMapping(value = "/filter-sertifikasi", params = {"filterSertifikasi"})
    public String filterSertifikasiSubmitPage(
            @RequestParam(value = "id-sertifikasi", required = true) Long id_sertifikasi,
            Model model) {
        List<SertifikasiModel> listSertifikasi = sertifikasiService.getListSertifikasi();

        List<KaryawanModel> listKaryawan = karyawanService.getListKaryawan();
        List<KaryawanModel> listKaryawanBaru = new ArrayList<>();

        for (int ii = 0; ii < listKaryawan.size(); ii++) {
            KaryawanModel karyawan = listKaryawan.get(ii);
            List<SertifikasiKaryawanModel> sertifikasiKaryawan = listKaryawan.get(ii).getListSertifikasiKaryawan();
            for (int jj = 0; jj < sertifikasiKaryawan.size(); jj++) {
                if (id_sertifikasi == (sertifikasiKaryawan.get(jj).getSertifikasi().getIdSertifikasi())) {
                    listKaryawanBaru.add(karyawan);
                }
            }
        }
        model.addAttribute("listKaryawan", listKaryawanBaru);
        model.addAttribute("listSertifikasi", listSertifikasi);

        return "filter-sertifikasi";
    }
}