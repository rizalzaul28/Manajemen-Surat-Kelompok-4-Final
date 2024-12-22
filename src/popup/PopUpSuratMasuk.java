/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package popup;

import AutoClose.AutoCloseJFrame;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import javax.swing.UIManager;
import Kelas.Bagian;
import Kelas.Kategori;
import java.awt.Desktop;
import java.io.File;
import java.sql.SQLException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import kelas.SuratMasuk;
import ux.suratmasuk;
import com.toedter.calendar.JTextFieldDateEditor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.filechooser.FileNameExtensionFilter;
import ux.suratsurat;

public class PopUpSuratMasuk extends javax.swing.JFrame {

    /**
     * Creates new form PopUpsuratmasuk
     */
    public PopUpSuratMasuk() {
        initComponents();

        
         AutoCloseJFrame.autoCloseIfIdle(this, 3000);
        tf_asal.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Masukan asal surat");
        tf_Perihal.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Berikan Perihal ");
        txtfilepath.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Upload File");
        otoID();
        blokirtextfieldTanggal();
        cbBagianSurat();
        cbKategoriSurat();

    }

    public void ambilDetail() {

        tf_id.setText(SuratMasuk.getId_surat());
        cb_Kategori.setSelectedItem(SuratMasuk.getKategori());
        cb_BagianSurat.setSelectedItem(SuratMasuk.getBagian());
        tf_asal.setText(SuratMasuk.getAsal_surat());
        tf_Perihal.setText(SuratMasuk.getPerihal());
        tTanggalDiterima.setDate(SuratMasuk.getTanggal_diterima());
        txtfilepath.setText(SuratMasuk.getFile_data());

    }

    public void otoID() {
        try {
            SuratMasuk surat = new SuratMasuk();
            String autoId = surat.otoID();

            if (autoId != null) {
                tf_id.setText(autoId);
            } else {
                tf_id.setText("1");
            }
        } catch (SQLException sQLException) {
            System.out.println("Error saat memperoleh ID otomatis: " + sQLException.getMessage());
            tf_id.setText("1");
        }
    }

    void reset() {
        tf_id.setText(null);
        cb_Kategori.setSelectedItem(null);
        cb_BagianSurat.setSelectedItem(null);
        tf_asal.setText(null);
        tf_Perihal.setText(null);
        tTanggalDiterima.setDate(null);
        txtfilepath.setText(null);
    }

    void cbKategoriSurat() {
        try {
            PopUpSuratMasuk.this.cb_Kategori.addItem("--Pilih Kategori Surat--");

            Kategori ks = new Kategori();
            ResultSet data = ks.Tampil_CbKategoriSurat();

            while (data.next()) {
                PopUpSuratMasuk.this.cb_Kategori.addItem(data.getString("kode_kategori") + " - " + data.getString("nama_kategori"));
            }

            PopUpSuratMasuk.this.cb_Kategori.setSelectedItem("--Pilih Kategori Surat--");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void cbBagianSurat() {
        try {
            PopUpSuratMasuk.this.cb_BagianSurat.addItem("--Pilih Bagian Surat--");

            Bagian bg = new Bagian();
            ResultSet data = bg.Tampil_CbBagianSurat();

            while (data.next()) {
                cb_BagianSurat.addItem(data.getString("kode_bagian") + " - " + data.getString("nama_bagian"));
            }

            cb_BagianSurat.setSelectedItem("--Pilih Bagian Surat--"); // Pilih default option
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void blokirtextfieldTanggal() {

        if (tTanggalDiterima.getDateEditor() instanceof JTextFieldDateEditor) {
            JTextFieldDateEditor editorAwal = (JTextFieldDateEditor) tTanggalDiterima.getDateEditor();
            editorAwal.setEditable(false);
            editorAwal.setEnabled(false);
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

        tf_id = new javax.swing.JTextField();
        pn_Dasar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cb_Kategori = new javax.swing.JComboBox<>();
        tf_asal = new javax.swing.JTextField();
        tf_Perihal = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        tTanggalDiterima = new com.toedter.calendar.JDateChooser();
        bTambah = new javax.swing.JButton();
        bEdit = new javax.swing.JButton();
        bHapus = new javax.swing.JButton();
        bClose = new javax.swing.JButton();
        bOpen = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        txtfilepath = new javax.swing.JTextField();
        bUpload = new javax.swing.JButton();
        cb_BagianSurat = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        pn_Dasar.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/popup3menit##.jpg"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 1, 19)); // NOI18N
        jLabel6.setText("Form Tambah Surat Masuk");

        jLabel10.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel10.setText("Pengirim");

        jLabel11.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel11.setText("Tanggal Diterima");

        jLabel9.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel9.setText("Kategori");

        jLabel12.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel12.setText("Perihal");

        bTambah.setText("Simpan");
        bTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambahActionPerformed(evt);
            }
        });

        bEdit.setText("Ubah");
        bEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEditActionPerformed(evt);
            }
        });

        bHapus.setText("Hapus");
        bHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapusActionPerformed(evt);
            }
        });

        bClose.setText("Close");
        bClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bCloseMouseClicked(evt);
            }
        });
        bClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCloseActionPerformed(evt);
            }
        });

        bOpen.setBackground(new java.awt.Color(51, 255, 51));
        bOpen.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bOpen.setForeground(new java.awt.Color(255, 255, 255));
        bOpen.setText("- Lihat Surat -");
        bOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bOpenActionPerformed(evt);
            }
        });

        jTextPane1.setText("Formulir ini akan tertutup secara otomatis jika tidak ada aktivitas selama tiga menit.");
        jScrollPane1.setViewportView(jTextPane1);

        bUpload.setText("Upload File");
        bUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUploadActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel13.setText("Bagian");

        javax.swing.GroupLayout pn_DasarLayout = new javax.swing.GroupLayout(pn_Dasar);
        pn_Dasar.setLayout(pn_DasarLayout);
        pn_DasarLayout.setHorizontalGroup(
            pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_DasarLayout.createSequentialGroup()
                .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_DasarLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bClose))
                    .addGroup(pn_DasarLayout.createSequentialGroup()
                        .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pn_DasarLayout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel9)
                                        .addComponent(bUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel13))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cb_Kategori, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(pn_DasarLayout.createSequentialGroup()
                                            .addComponent(bTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(bEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(bHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(tTanggalDiterima, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(tf_asal)
                                            .addComponent(tf_Perihal)
                                            .addComponent(txtfilepath, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(bOpen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(cb_BagianSurat, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        pn_DasarLayout.setVerticalGroup(
            pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_DasarLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
            .addGroup(pn_DasarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(16, 16, 16)
                .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_BagianSurat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_Kategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tf_asal)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tTanggalDiterima, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_Perihal, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bUpload, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtfilepath, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(bEdit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bHapus, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bOpen, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bClose)
                .addContainerGap())
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

    private void bCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bCloseMouseClicked
        dispose();
    }//GEN-LAST:event_bCloseMouseClicked

    private void bUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUploadActionPerformed
        JFileChooser jfc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Dokumen PDF", "pdf");
        jfc.setFileFilter(filter);
        jfc.setAcceptAllFileFilterUsed(false);

        int result = jfc.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = jfc.getSelectedFile();
                String fileName = selectedFile.getName();
                String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);

                if (!fileExtension.equalsIgnoreCase("pdf")) {
                    JOptionPane.showMessageDialog(null, "Hanya file PDF yang diizinkan!");
                    return;
                }

                String filepath = selectedFile.getAbsolutePath();
                filepath = filepath.replace('\\', '/');

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String timestamp = sdf.format(new Date());

                String newName = timestamp + "_" + fileName;

                String destinationPath = "./Upload/FileSuratMasuk/";
                txtfilepath.setText(destinationPath + newName);

                File destinationDirectory = new File(destinationPath);
                if (!destinationDirectory.exists()) {
                    destinationDirectory.mkdirs();
                }

                File destinationFile = new File(destinationDirectory, newName);
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Gagal menyalin file: " + e.getMessage());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tidak ada file yang dipilih");
        }
    }//GEN-LAST:event_bUploadActionPerformed

    private void bOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bOpenActionPerformed
        try {
            String filePath = txtfilepath.getText().trim();
            File file = new File(filePath);

            if (file.exists()) {
                Desktop.getDesktop().open(file);
            } else {
                JOptionPane.showMessageDialog(this, "File tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_bOpenActionPerformed

    private void bTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambahActionPerformed
        try {
            SuratMasuk surat = new SuratMasuk();

            String idSurat = tf_id.getText().trim();
            String kategori = cb_Kategori.getSelectedItem().toString();
            String bagian = cb_BagianSurat.getSelectedItem().toString();
            String asalSurat = tf_asal.getText().trim();
            String perihal = tf_Perihal.getText().trim();
            java.util.Date tanggalDiterima = tTanggalDiterima.getDate();
            String filePath = txtfilepath.getText().trim();

            if (kategori.equals("--Pilih Kategori Surat--")) {
                JOptionPane.showMessageDialog(this, "Pilih kategori surat terlebih dahulu!", "Kesalahan", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (bagian.equals("--Pilih Bagian Surat--")) {
                JOptionPane.showMessageDialog(this, "Pilih bagian surat terlebih dahulu!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (asalSurat.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Asal Surat harus diisi!", "Kesalahan", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (perihal.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Perihal harus diisi!", "Kesalahan", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tanggalDiterima == null) {
                JOptionPane.showMessageDialog(this, "Tanggal Diterima harus diisi!", "Kesalahan", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (filePath.isEmpty()) {
                JOptionPane.showMessageDialog(this, "File Path harus diisi!", "Kesalahan", JOptionPane.ERROR_MESSAGE);
                return;
            }

            surat.setId_surat(idSurat);
            surat.setKategori(kategori);
            surat.setBagian(bagian.split(" - ")[0]);
            surat.setAsal_surat(asalSurat);
            surat.setPerihal(perihal);
            surat.setTanggal_diterima(new java.sql.Date(tanggalDiterima.getTime()));
            surat.setFile_data(filePath);

            surat.tambahSurat();

            otoID();
            reset();

            dispose();
            suratsurat.pn_suratsurat.removeAll();
            suratsurat.pn_suratsurat.add(new suratmasuk());
            suratsurat.pn_suratsurat.repaint();
            suratsurat.pn_suratsurat.revalidate();
        } catch (SQLException sQLException) {
            System.err.println("Data tidak masuk: " + sQLException.getMessage());
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data surat: " + sQLException.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_bTambahActionPerformed

    private void bEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEditActionPerformed
        try {
            SuratMasuk surat = new SuratMasuk();

            String idSurat = tf_id.getText().trim();
            if (idSurat.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID Surat harus diisi!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            surat.setId_surat(idSurat);

            String kategori = cb_Kategori.getSelectedItem().toString();
            if (kategori.equals("--Pilih Kategori Surat--")) {
                JOptionPane.showMessageDialog(this, "Silahkan pilih kategori surat yang valid!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            surat.setKategori(kategori);

            String bagian = cb_BagianSurat.getSelectedItem().toString();
            if (bagian.equals("--Pilih Bagian Surat--")) {
                JOptionPane.showMessageDialog(this, "Pilih bagian surat terlebih dahulu!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            surat.setBagian(bagian.split(" - ")[0]);

            String asalSurat = tf_asal.getText().trim();
            if (asalSurat.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Asal Surat harus diisi!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            surat.setAsal_surat(asalSurat);

            String perihal = tf_Perihal.getText().trim();
            if (perihal.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Perihal Surat harus diisi!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            surat.setPerihal(perihal);

            java.util.Date tanggalDiterima = tTanggalDiterima.getDate();
            if (tanggalDiterima == null) {
                JOptionPane.showMessageDialog(this, "Tanggal diterima tidak boleh kosong", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            surat.setTanggal_diterima(new java.sql.Date(tanggalDiterima.getTime()));

            String filePath = txtfilepath.getText().trim();
            if (filePath.isEmpty()) {
                JOptionPane.showMessageDialog(this, "File path harus diisi!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            surat.setFile_data(filePath);

            surat.ubahSurat();

        } catch (SQLException sQLException) {
            System.err.println("Data tidak masuk: " + sQLException.getMessage());
            JOptionPane.showMessageDialog(this, "Gagal mengubah data surat: " + sQLException.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        dispose();
        suratsurat.pn_suratsurat.removeAll();
        suratsurat.pn_suratsurat.add(new suratmasuk());
        suratsurat.pn_suratsurat.repaint();
        suratsurat.pn_suratsurat.revalidate();
    }//GEN-LAST:event_bEditActionPerformed

    private void bHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapusActionPerformed
        try {
            SuratMasuk sur = new SuratMasuk();
            sur.setId_surat((tf_id.getText()));
            sur.hapusSurat();
        } catch (SQLException sQLException) {
        }

        dispose();
        suratsurat.pn_suratsurat.removeAll();
        suratsurat.pn_suratsurat.add(new suratmasuk());
        suratsurat.pn_suratsurat.repaint();
        suratsurat.pn_suratsurat.revalidate();
    }//GEN-LAST:event_bHapusActionPerformed

    private void bCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCloseActionPerformed
        dispose();
    }//GEN-LAST:event_bCloseActionPerformed

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
                new PopUpSuratMasuk().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bClose;
    public javax.swing.JButton bEdit;
    public javax.swing.JButton bHapus;
    private javax.swing.JButton bOpen;
    public javax.swing.JButton bTambah;
    private javax.swing.JButton bUpload;
    private javax.swing.JComboBox<String> cb_BagianSurat;
    private javax.swing.JComboBox<String> cb_Kategori;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JPanel pn_Dasar;
    public com.toedter.calendar.JDateChooser tTanggalDiterima;
    private javax.swing.JTextField tf_Perihal;
    private javax.swing.JTextField tf_asal;
    private javax.swing.JTextField tf_id;
    public javax.swing.JTextField txtfilepath;
    // End of variables declaration//GEN-END:variables
}
