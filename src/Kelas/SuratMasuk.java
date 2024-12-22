package kelas;

import Kelas.Koneksi;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

public class SuratMasuk {

    int jumlah = 0;
    public static String id_suratmasuk, kategori, bagian, pengirim, perihal, file_data;
    public static java.sql.Date tanggal_diterima;

    private Connection konek;
    private PreparedStatement ps;
    private Statement st;
    private ResultSet rs;
    private String query;

    public SuratMasuk() throws SQLException {
        Koneksi koneksi = new Koneksi();
        konek = koneksi.koneksiDB();
    }

    public static String getId_surat() {
        return id_suratmasuk;
    }

    public static void setId_surat(String id_surat) {
        SuratMasuk.id_suratmasuk = id_surat;
    }

    public static String getBagian() {
        return bagian;
    }

    public static void setBagian(String bagian) {
        SuratMasuk.bagian = bagian;
    }

    public static String getKategori() {
        return kategori;
    }

    public static void setKategori(String kategori) {
        SuratMasuk.kategori = kategori;
    }

    public static String getAsal_surat() {
        return pengirim;
    }

    public static void setAsal_surat(String asal_surat) {
        SuratMasuk.pengirim = asal_surat;
    }

    public static String getPerihal() {
        return perihal;
    }

    public static void setPerihal(String perihal) {
        SuratMasuk.perihal = perihal;
    }

    public static String getFile_data() {
        return file_data;
    }

    public static void setFile_data(String file_data) {
        SuratMasuk.file_data = file_data;
    }

    public static Date getTanggal_diterima() {
        return tanggal_diterima;
    }

    public static void setTanggal_diterima(Date tanggal_diterima) {
        SuratMasuk.tanggal_diterima = tanggal_diterima;
    }

    public Connection getKonek() {
        return konek;
    }

    public void setKonek(Connection konek) {
        this.konek = konek;
    }

    public PreparedStatement getPs() {
        return ps;
    }

    public void setPs(PreparedStatement ps) {
        this.ps = ps;
    }

    public Statement getSt() {
        return st;
    }

    public void setSt(Statement st) {
        this.st = st;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void tambahSurat() {
        query = "INSERT INTO surat_masuk(id_suratmasuk,"
                + "bagian,"
                + "kategori,"
                + "pengirim,"
                + "perihal,"
                + "tanggal_diterima,"
                + "file_data) VALUES(?,?,?,?,?,?,?)";
        try {
            ps = konek.prepareStatement(query);
            ps.setString(1, id_suratmasuk);
            ps.setString(2, bagian);
            ps.setString(3, kategori);
            ps.setString(4, pengirim);
            ps.setString(5, perihal);
            ps.setDate(6, tanggal_diterima);
            ps.setString(7, file_data);

            ps.executeUpdate();
            ps.close(); 
            JOptionPane.showMessageDialog(null, "Surat berhasil ditambahkan");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

   
    
    

    public void hapusSurat() {
        query = "DELETE FROM surat_masuk WHERE id_suratmasuk = ?";
        try {
            ps = konek.prepareStatement(query);
            ps.setString(1, id_suratmasuk);

            ps.executeUpdate();
            ps.close();
            JOptionPane.showMessageDialog(null, "Surat berhasil dihapus");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Surat gagal dihapus: " + ex.getMessage());
        }
    }

    public void ubahSurat() {
        query = "UPDATE surat_masuk SET "
                + "bagian = ?, "
                + "kategori = ?, "
                + "pengirim = ?, "
                + "perihal = ?, "
                + "tanggal_diterima = ?, "
                + "file_data = ? "
                + "WHERE id_suratmasuk = ?";
        try {
            ps = konek.prepareStatement(query);
            ps.setString(1, bagian);
            ps.setString(2, kategori);
            ps.setString(3, pengirim);
            ps.setString(4, perihal);
            ps.setDate(5, tanggal_diterima);
            ps.setString(6, file_data);
            ps.setString(7, id_suratmasuk);

            ps.executeUpdate();
            ps.close();
            JOptionPane.showMessageDialog(null, "Surat berhasil diubah");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Surat gagal diubah: " + ex.getMessage());
        }
    }

    public String otoID() {

        LocalDate today = LocalDate.now();
        String todayFormatted = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String query = "SELECT id_suratmasuk FROM surat_masuk WHERE id_suratmasuk LIKE '" + todayFormatted + "%' ORDER BY id_suratmasuk DESC LIMIT 1";

        try {
            st = konek.createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                String lastId = rs.getString("id_suratmasuk");
                int num = Integer.parseInt(lastId.substring(8)) + 1;
                String newId = todayFormatted + String.format("%03d", num);
                return newId;
            } else {

                return todayFormatted + "001";
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Failed to fetch next surat ID: " + ex.getMessage());
            return null;
        }
    }

    public ResultSet KodeTampilByFilters(String bagian, String kategori, Date tanggalAwal, Date tanggalAkhir) throws SQLException {
        String query = "SELECT * FROM surat_masuk WHERE 1=1";

        if (bagian != null && !bagian.isEmpty()) {
            query += " AND bagian = ?";
        }

        if (kategori != null && !kategori.isEmpty()) {
            query += " AND kategori = ?";
        }

        if (tanggalAwal != null && tanggalAkhir != null) {
            query += " AND tanggal_diterima BETWEEN ? AND ? ORDER BY tanggal_diterima ASC";
        }

        PreparedStatement ps = konek.prepareStatement(query);

        int paramIndex = 1;

        if (bagian != null && !bagian.isEmpty()) {
            ps.setString(paramIndex++, bagian);
        }
        if (kategori != null && !kategori.isEmpty()) {
            ps.setString(paramIndex++, kategori);
        }
        if (tanggalAwal != null && tanggalAkhir != null) {
            ps.setDate(paramIndex++, new java.sql.Date(tanggalAwal.getTime()));
            ps.setDate(paramIndex++, new java.sql.Date(tanggalAkhir.getTime()));
        }

        return ps.executeQuery();
    }

    public ResultSet tampilSuratKategori(String filterKategori) {
        try {
            if (filterKategori == null || filterKategori.isEmpty()) {
                query = "SELECT id_suratmasuk, bagian, kategori, pengirim, perihal, tanggal_diterima FROM surat_masuk";
                PreparedStatement ps = konek.prepareStatement(query);
                rs = ps.executeQuery();
            } else {
                query = "SELECT id_suratmasuk, bagian, kategori, pengirim, perihal, tanggal_diterima FROM surat_masuk";
                PreparedStatement ps = konek.prepareStatement(query);
                ps.setString(1, filterKategori);
                rs = ps.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet tampilSuratBagian(String filterBagian) {
        try {
            if (filterBagian == null || filterBagian.isEmpty()) {

                query = "SELECT id_suratmasuk, bagian, kategori, pengirim, perihal, tanggal_diterima FROM surat_masuk";
                PreparedStatement ps = konek.prepareStatement(query);
                rs = ps.executeQuery();
            } else {

                query = "SELECT id_suratmasuk, bagian, kategori, pengirim, perihal, tanggal_diterima FROM surat_masuk";
                PreparedStatement ps = konek.prepareStatement(query);
                ps.setString(1, filterBagian);
                rs = ps.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet tampilSuratTanggal(java.sql.Date tanggal) {
        try {
            query = "SELECT id_suratmasuk, bagian, kategori, pengirim, perihal, tanggal_diterima FROM surat_masuk WHERE tanggal_diterima = ?";
            PreparedStatement ps = konek.prepareStatement(query);
            ps.setDate(1, tanggal);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;

    }

    public int TampilJumlahBagian() {
        query = "SELECT COUNT(*) AS jumlah FROM surat_masuk";

        try {
            st = konek.createStatement();
            rs = st.executeQuery(query);

            if (rs.next()) {
                jumlah = rs.getInt("jumlah");
            }

            rs.close();
            st.close();
        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, "Data gagal ditampilkan: " + sQLException.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return jumlah;
    }
}