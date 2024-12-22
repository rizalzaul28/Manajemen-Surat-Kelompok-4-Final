/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package popup;

import AutoClose.AutoCloseJFrame;
import Kelas.Bagian;
import Kelas.Kategori;
import Kelas.SuratKeluar;
import Kelas.TimedJOptionPane;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import ux.suratkeluar;

/**
 *
 * @author lenovo
 */
public class PopUpSuratKeluar extends javax.swing.JDialog {

    /**
     * Creates new form PopUpsuratmasuk
     */
    private SuratKeluar ske;
    private boolean isEditMode = false; // Tambahkan flag untuk melacak mode
    public boolean isInitializing = true; // Flag untuk mencegah pemanggilan updateNoSurat saat inisialisasi data

    public PopUpSuratKeluar(java.awt.Frame parent, boolean modal, SuratKeluar sk, boolean editMode) throws SQLException {
        super(parent, modal);
        initComponents();
        this.ske = sk;
        AutoCloseJFrame.autoCloseIfIdle(this, 3000);

        tf_Tgl.setDate(new Date());
        cbBagianSurat();
        cbKategoriSurat();
        autoId();

        tf_NoSurat.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Masukan Nomor");
        tf_Perihal.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Berikan Perihal ");
        tf_Tujuan.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Masukan Tujuan Anda");
        // Tambahkan listener untuk combo box dan date chooser
        cb_Kategori.addItemListener(e -> {
            if (!isInitializing) {
                try {
                    updateNoSurat();
                } catch (SQLException ex) {
                    Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        cb_Bagian.addItemListener(e -> {
            if (!isInitializing) {
                try {
                    updateNoSurat();
                } catch (SQLException ex) {
                    Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        tf_Tgl.addPropertyChangeListener("date", evt -> {
            if (!isInitializing) {
                try {
                    updateNoSurat();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        isInitializing = false; // Inisialisasi selesai
    }

    private void updateNoSurat() throws SQLException {
        if (cb_Kategori.getSelectedItem() == null || cb_Bagian.getSelectedItem() == null) {
            tf_NoSurat.setText("");
            return;
        }

        String kategori = cb_Kategori.getSelectedItem().toString();
        String bagian = cb_Bagian.getSelectedItem().toString();

        if (kategori.equals("--Pilih Kategori Surat--") || bagian.equals("--Pilih Bagian Surat--")) {
            tf_NoSurat.setText("");
            return;
        }

        String selectedKategori = kategori.split(" - ")[0];
        String selectedBagian = bagian.split(" - ")[0];

        Date tanggal = tf_Tgl.getDate();
        if (tanggal == null) {
            tf_NoSurat.setText("");
            return;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tanggal);
        int bulan = calendar.get(Calendar.MONTH) + 1;
        int tahun = calendar.get(Calendar.YEAR);

        // Jika edit mode, gunakan nomor dari tabel
        if (isEditMode) {
            return; // Jangan ubah nomor surat
        }

        // Ambil nomor surat terbaru
        SuratKeluar surat = new SuratKeluar();
        String noSurat = surat.GetNoSurat(selectedKategori, selectedBagian, tahun);
        String bulanRomawi = surat.KonversiRomawi(bulan);

        String formattedNoSurut = String.format("%s.%s/%s/%s/%d",
                selectedKategori,
                noSurat,
                selectedBagian,
                bulanRomawi,
                tahun
        );

        tf_NoSurat.setText(formattedNoSurut);
    }

    void cbKategoriSurat() {
        try {
            cb_Kategori.addItem("--Pilih Kategori Surat--");

            Kategori ks = new Kategori();
            ResultSet data = ks.Tampil_CbKategoriSurat();

            while (data.next()) {
                cb_Kategori.addItem(data.getString("kode_kategori") + " - " + data.getString("nama_kategori"));
            }

            cb_Kategori.setSelectedItem("--Pilih Kategori Surat--");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void cbBagianSurat() {
        try {
            cb_Bagian.addItem("--Pilih Bagian Surat--");

            Bagian bg = new Bagian();
            ResultSet data = bg.Tampil_CbBagianSurat();

            while (data.next()) {
                cb_Bagian.addItem(data.getString("kode_bagian") + " - " + data.getString("nama_bagian"));
            }

            cb_Bagian.setSelectedItem("--Pilih Bagian Surat--"); // Pilih default option
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pn_Dasar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cb_Bagian = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cb_Kategori = new javax.swing.JComboBox<>();
        tf_NoSurat = new javax.swing.JTextField();
        tf_Perihal = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        tf_Tgl = new com.toedter.calendar.JDateChooser();
        tf_Tujuan = new javax.swing.JTextField();
        bt_Upload = new javax.swing.JLabel();
        bt_Tambah = new javax.swing.JButton();
        bt_Ubah = new javax.swing.JButton();
        bt_Hapus = new javax.swing.JButton();
        bt_Close = new javax.swing.JButton();
        bt_Lihat = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        tf_File = new javax.swing.JTextField();
        lb_Id = new javax.swing.JLabel();
        bt_Salin = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        pn_Dasar.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/popup3menit##.jpg"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel2.setText("Bagian");

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 1, 19)); // NOI18N
        jLabel6.setText("Form Tambah Surat Keluar");

        jLabel10.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel10.setText("Nomor");

        jLabel11.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel11.setText("Tanggal Dibuat");

        jLabel9.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel9.setText("Kategori");

        jLabel12.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel12.setText("Perihal");

        jLabel13.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel13.setText("Tujuan");

        tf_Tgl.setDateFormatString("dd MMMM yyyy");

        bt_Upload.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        bt_Upload.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bt_Upload.setText("Upload File");
        bt_Upload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_UploadMouseClicked(evt);
            }
        });

        bt_Tambah.setText("Simpan");
        bt_Tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_TambahActionPerformed(evt);
            }
        });

        bt_Ubah.setText("Ubah");
        bt_Ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_UbahActionPerformed(evt);
            }
        });

        bt_Hapus.setText("Hapus");
        bt_Hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_HapusActionPerformed(evt);
            }
        });

        bt_Close.setText("Close");
        bt_Close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_CloseMouseClicked(evt);
            }
        });

        bt_Lihat.setBackground(new java.awt.Color(51, 255, 51));
        bt_Lihat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bt_Lihat.setForeground(new java.awt.Color(255, 255, 255));
        bt_Lihat.setText("- Lihat Surat -");
        bt_Lihat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_LihatActionPerformed(evt);
            }
        });

        jTextPane1.setText("Formulir ini akan tertutup secara otomatis jika tidak ada aktivitas selama tiga menit.");
        jScrollPane1.setViewportView(jTextPane1);

        lb_Id.setForeground(new java.awt.Color(255, 255, 255));

        bt_Salin.setText("Copy");
        bt_Salin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_SalinActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_DasarLayout = new javax.swing.GroupLayout(pn_Dasar);
        pn_Dasar.setLayout(pn_DasarLayout);
        pn_DasarLayout.setHorizontalGroup(
            pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_DasarLayout.createSequentialGroup()
                .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_DasarLayout.createSequentialGroup()
                        .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pn_DasarLayout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel9))
                                .addGap(28, 28, 28)
                                .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cb_Kategori, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cb_Bagian, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(pn_DasarLayout.createSequentialGroup()
                                            .addComponent(bt_Tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(bt_Ubah, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(bt_Hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(tf_Tujuan)
                                            .addComponent(tf_Tgl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(tf_NoSurat)
                                            .addComponent(tf_Perihal)
                                            .addComponent(bt_Upload, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tf_File))
                                        .addComponent(bt_Lihat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(pn_DasarLayout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(jLabel6)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bt_Salin)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_DasarLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lb_Id)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bt_Close)
                        .addGap(15, 15, 15))))
        );
        pn_DasarLayout.setVerticalGroup(
            pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_DasarLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
            .addGroup(pn_DasarLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel6)
                .addGap(39, 39, 39)
                .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_Bagian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_Kategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bt_Salin, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(tf_NoSurat)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tf_Tgl, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_Perihal, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_Tujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tf_File, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bt_Upload, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(bt_Ubah, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bt_Hapus, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bt_Tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bt_Lihat, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_Close)
                    .addComponent(lb_Id))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_Dasar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_Dasar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bt_CloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_CloseMouseClicked
        dispose();
    }//GEN-LAST:event_bt_CloseMouseClicked

    private void bt_TambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_TambahActionPerformed
        try {
            SuratKeluar kodetambah = new SuratKeluar();

            kodetambah.setId_suratkeluar(Integer.parseInt(lb_Id.getText()));

            String kategori = cb_Kategori.getSelectedItem().toString();
            if (!kategori.equals("--Pilih Kategori Surat--")) {
                kodetambah.setKategori(kategori.split(" - ")[0]);
            } else {

                JOptionPane.showMessageDialog(this, "Pilih kategori surat terlebih dahulu!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String bagian = cb_Bagian.getSelectedItem().toString();
            if (!bagian.equals("--Pilih Bagian Surat--")) {
                kodetambah.setBagian(bagian.split(" - ")[0]);
            } else {
                JOptionPane.showMessageDialog(this, "Pilih bagian surat terlebih dahulu!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String nomorSurat = tf_NoSurat.getText();
            if (nomorSurat != null && !nomorSurat.isEmpty()) {
                kodetambah.setNo_surat(nomorSurat);
            } else {
                JOptionPane.showMessageDialog(this, "Nomor surat tidak boleh kosong!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            java.util.Date tanggalDibuatUtil = tf_Tgl.getDate();
            if (tanggalDibuatUtil != null) {
                java.sql.Date tanggalDibuatSQL = new java.sql.Date(tanggalDibuatUtil.getTime());
                kodetambah.setTgl_dibuat(tanggalDibuatSQL);
            } else {
                JOptionPane.showMessageDialog(this, "Tanggal dibuat tidak valid!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String perihal = tf_Perihal.getText();
            if (perihal != null && !perihal.isEmpty()) {
                kodetambah.setPerihal(perihal);
            } else {
                JOptionPane.showMessageDialog(this, "Perihal tidak boleh kosong!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String tujuan = tf_Tujuan.getText();
            if (tujuan != null && !tujuan.isEmpty()) {
                kodetambah.setTujuan(tujuan);
            } else {
                JOptionPane.showMessageDialog(this, "Tujuan tidak boleh kosong!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            File file = new File(tf_File.getText());
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                String fileExtension = kodetambah.getFileExtension(file.getName());
                String formattedNamaFile = nomorSurat.replace(".", "_").replace("/", "_") + fileExtension;
                kodetambah.setFile(fis);
                kodetambah.setNama_file(formattedNamaFile);
            } else {
                JOptionPane.showMessageDialog(this, "File tidak ditemukan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            kodetambah.KodeTambah();
            autoId();

            suratkeluar kt = new suratkeluar();
            kt.loadTabel();
            ske.NotifPerubahanData();

            dispose();

        } catch (SQLException ex) {
            Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Gagal menyimpan surat keluar: " + ex.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_TambahActionPerformed

    private void bt_UbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_UbahActionPerformed
        try {
            SuratKeluar kodetambah = new SuratKeluar();

            kodetambah.setId_suratkeluar(Integer.parseInt(lb_Id.getText()));

            String kategori = cb_Kategori.getSelectedItem().toString();
            if (!kategori.equals("--Pilih Kategori Surat--")) {
                kodetambah.setKategori(kategori.split(" - ")[0]);
            } else {

                JOptionPane.showMessageDialog(this, "Pilih kategori surat terlebih dahulu!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String bagian = cb_Bagian.getSelectedItem().toString();
            if (!bagian.equals("--Pilih Bagian Surat--")) {
                kodetambah.setBagian(bagian.split(" - ")[0]);
            } else {
                JOptionPane.showMessageDialog(this, "Pilih bagian surat terlebih dahulu!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String nomorSurat = tf_NoSurat.getText();
            if (nomorSurat != null && !nomorSurat.isEmpty()) {
                kodetambah.setNo_surat(nomorSurat);
            } else {
                JOptionPane.showMessageDialog(this, "Nomor surat tidak boleh kosong!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            java.util.Date tanggalDibuatUtil = tf_Tgl.getDate();
            if (tanggalDibuatUtil != null) {
                java.sql.Date tanggalDibuatSQL = new java.sql.Date(tanggalDibuatUtil.getTime());
                kodetambah.setTgl_dibuat(tanggalDibuatSQL);
            } else {
                JOptionPane.showMessageDialog(this, "Tanggal dibuat tidak valid!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String perihal = tf_Perihal.getText();
            if (perihal != null && !perihal.isEmpty()) {
                kodetambah.setPerihal(perihal);
            } else {
                JOptionPane.showMessageDialog(this, "Perihal tidak boleh kosong!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String tujuan = tf_Tujuan.getText();
            if (tujuan != null && !tujuan.isEmpty()) {
                kodetambah.setTujuan(tujuan);
            } else {
                JOptionPane.showMessageDialog(this, "Tujuan tidak boleh kosong!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            File file = new File(tf_File.getText());
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                String fileExtension = kodetambah.getFileExtension(file.getName());
                String formattedNamaFile = nomorSurat.replace(".", "_").replace("/", "_") + fileExtension;
                kodetambah.setFile(fis);
                kodetambah.setNama_file(formattedNamaFile);
            } else {
                JOptionPane.showMessageDialog(this, "File tidak ditemukan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            kodetambah.KodeUbah();
            autoId();

            suratkeluar kt = new suratkeluar();
            kt.loadTabel();
            ske.NotifPerubahanData();

            dispose();

        } catch (SQLException ex) {
            Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Gagal menyimpan surat keluar: " + ex.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_UbahActionPerformed

    private void bt_HapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_HapusActionPerformed
        try {
            if (lb_Id.getText().isEmpty()) {
                TimedJOptionPane timedPane = new TimedJOptionPane();
                timedPane.showTimedMessage("Pilih data yang ingin dihapus!", null, JOptionPane.WARNING_MESSAGE, 1000);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {

                SuratKeluar kodeHapus = new SuratKeluar();
                kodeHapus.setId_suratkeluar(Integer.parseInt(lb_Id.getText()));

                kodeHapus.KodeHapus();
                dispose();

                suratkeluar kt = new suratkeluar();
                kt.loadTabel();
                ske.NotifPerubahanData();

            }
        } catch (SQLException e) {
        }
    }//GEN-LAST:event_bt_HapusActionPerformed

    private void bt_LihatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_LihatActionPerformed
        try {
            String namaFile = tf_File.getText();
            if (namaFile == null || namaFile.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama file tidak tersedia.", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            SuratKeluar suratKeluar = new SuratKeluar();
            suratKeluar.setNama_file(namaFile);
            byte[] fileData = suratKeluar.BukaFile();

            if (fileData != null) {
                File tempFile = new File(System.getProperty("java.io.tmpdir") + "/" + namaFile);
                FileOutputStream fos = new FileOutputStream(tempFile);
                fos.write(fileData);
                fos.close();

                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(tempFile);
                } else {
                    JOptionPane.showMessageDialog(this, "Desktop tidak didukung pada sistem ini.", "Kesalahan", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "File tidak ditemukan untuk surat ini.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat membuka file: " + e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_bt_LihatActionPerformed

    private void bt_UploadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_UploadMouseClicked
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = chooser.getSelectedFile();

                String targetFolderPath = "Upload/FileSuratKeluar";
                File targetFolder = new File(targetFolderPath);

                if (!targetFolder.exists()) {
                    if (!targetFolder.mkdirs()) {
                        System.out.println("Gagal membuat folder FileSurat!");
                        return;
                    }
                }

                String fileBaseName = tf_NoSurat.getText().replace(".", "_").replace("/", "_");

                SuratKeluar kodeUpload = new SuratKeluar();
                String fileExtension = kodeUpload.getFileExtension(selectedFile.getName());

                File targetFile = new File(targetFolderPath, fileBaseName + fileExtension);
                int count = 1;
                while (targetFile.exists()) {
                    targetFile = new File(targetFolderPath, fileBaseName + "(" + count + ")" + fileExtension);
                    count++;
                }

                try {
                    Files.copy(selectedFile.toPath(), targetFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                    tf_File.setText(targetFile.getAbsolutePath());
                } catch (IOException e) {
                    TimedJOptionPane timedPane = new TimedJOptionPane();
                    timedPane.showTimedMessage("Gagal mengunggah file", null, JOptionPane.ERROR_MESSAGE, 1000);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_bt_UploadMouseClicked

    private void bt_SalinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_SalinActionPerformed
        try {
            String textToCopy = tf_NoSurat.getText();

            if (textToCopy != null && !textToCopy.isEmpty()) {
                java.awt.datatransfer.StringSelection stringSelection = new java.awt.datatransfer.StringSelection(textToCopy);
                java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

                TimedJOptionPane timedPane = new TimedJOptionPane();
                timedPane.showTimedMessage("Nomor urut telah disalin ke Clipboard.", "Berhasil", JOptionPane.INFORMATION_MESSAGE, 1000);
            } else {
                TimedJOptionPane timedPane = new TimedJOptionPane();
                timedPane.showTimedMessage("Nomor urut kosong, tidak ada yang disalin.", "Kesalahan", JOptionPane.ERROR_MESSAGE, 2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal menyalin teks.", "Kesalahan", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_bt_SalinActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatLightLaf.setup();

        UIManager.put("Button.arc", 15);
        UIManager.put("Component.arc", 20);
        UIManager.put("CheckBox.arc", 10);
        UIManager.put("Button.background", Color.WHITE);

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SuratKeluar srtkel = new SuratKeluar();
                    new PopUpSuratKeluar(null, true, srtkel, true).setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_Close;
    private javax.swing.JButton bt_Hapus;
    private javax.swing.JButton bt_Lihat;
    private javax.swing.JButton bt_Salin;
    private javax.swing.JButton bt_Tambah;
    private javax.swing.JButton bt_Ubah;
    private javax.swing.JLabel bt_Upload;
    public static javax.swing.JComboBox<String> cb_Bagian;
    public static javax.swing.JComboBox<String> cb_Kategori;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    public static javax.swing.JLabel lb_Id;
    private javax.swing.JPanel pn_Dasar;
    public static javax.swing.JTextField tf_File;
    public static javax.swing.JTextField tf_NoSurat;
    public static javax.swing.JTextField tf_Perihal;
    public static com.toedter.calendar.JDateChooser tf_Tgl;
    public static javax.swing.JTextField tf_Tujuan;
    // End of variables declaration//GEN-END:variables
    private void autoId() throws SQLException {
        SuratKeluar auto = new SuratKeluar();
        int newID = auto.autoId();
        lb_Id.setText(String.valueOf(newID));
    }
}
