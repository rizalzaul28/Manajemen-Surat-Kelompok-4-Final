

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ux;

import com.formdev.flatlaf.FlatClientProperties;
import popup.PopUpSuratMasuk;


import Kelas.Bagian;
import Kelas.Kategori;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import kelas.SuratMasuk;
import popup.PopUpSuratMasuk;
import com.toedter.calendar.JTextFieldDateEditor;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;


public class suratmasuk extends javax.swing.JPanel {

    /**
     * Creates new form suratmasuk
     */
    public suratmasuk() {
        initComponents();
        pn_uploadmasuk.putClientProperty(FlatClientProperties.STYLE, "arc:50");
      
        loadTabel();
        blokirtextfieldTanggal();
        cbBagianSurat();
        cbKategoriSurat();

        cb_KategoriMenu.addActionListener(evt -> loadfilterBagianKategoriTanggal());
        cb_BagianMenu.addActionListener(evt -> loadfilterBagianKategoriTanggal());
        TglAwal.addPropertyChangeListener(evt -> {
            if ("date".equals(evt.getPropertyName())) {
                loadfilterBagianKategoriTanggal();
            }
        });

        TglAkhir.addPropertyChangeListener(evt -> {
            if ("date".equals(evt.getPropertyName())) {
                loadfilterBagianKategoriTanggal();
            }
        });
        
    }
    
    
     public void setModel(DefaultTableModel model) {
        tb_SuratMasuk.setModel(model);
    }

    public void loadTabel() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn(null);
        model.addColumn("Bagian");
        model.addColumn("Kategori");
        model.addColumn("Pengirim");
        model.addColumn("Perihal");
        model.addColumn("Tanggal Diterima");
        model.addColumn("File Surat");

        try {
            SuratMasuk k = new SuratMasuk();

            java.util.Date now = new java.util.Date();
            java.sql.Date startOfMonth = new java.sql.Date(now.getYear(), now.getMonth(), 1);
            java.sql.Date endOfMonth = new java.sql.Date(now.getYear(), now.getMonth() + 1, 0);

            ResultSet data = k.KodeTampilByFilters(null, null, null, null);

            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd MMMM yyyy", new java.util.Locale("id", "ID"));

            while (data.next()) {

                String formattedDate = "";
                if (data.getString("tanggal_diterima") != null) {
                    java.util.Date date = java.sql.Date.valueOf(data.getString("tanggal_diterima"));
                    formattedDate = dateFormat.format(date);
                }

                model.addRow(new Object[]{
                    data.getString("id_suratmasuk"),
                    data.getString("bagian"),
                    data.getString("kategori"),
                    data.getString("pengirim"),
                    data.getString("perihal"),
                    formattedDate,
                    data.getString("file_data")
                });
            }

            data.close();
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        }

        tb_SuratMasuk.setModel(model);

        tb_SuratMasuk.getColumnModel().getColumn(0).setMinWidth(0);
        tb_SuratMasuk.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_SuratMasuk.getColumnModel().getColumn(0).setWidth(0);
    }

    private void loadfilterBagian() {
        String selectedBagian = cb_BagianMenu.getSelectedItem().toString();

        if (selectedBagian.equals("--Pilih Bagian Surat--")) {
            loadTabel();
        } else {
            String filterBagian = selectedBagian.split(" - ")[0];
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn(null);
            model.addColumn("Bagian");
            model.addColumn("Kategori");
            model.addColumn("Pengirim");
            model.addColumn("Perihal");
            model.addColumn("Tanggal Diterima");
            model.addColumn("File Surat");

            try {
                SuratMasuk bg = new SuratMasuk();
                ResultSet data = bg.tampilSuratBagian(filterBagian);

                while (data.next()) {
                    model.addRow(new Object[]{
                        data.getString("id_suratmasuk"),
                        data.getString("bagian"),
                        data.getString("kategori"),
                        data.getString("pengirim"),
                        data.getString("perihal"),
                        data.getString("tanggal_diterima"),
                        data.getString("file_data")});
                }

                data.close();
            } catch (SQLException sQLException) {
                sQLException.printStackTrace();
            }

            tb_SuratMasuk.setModel(model);

            tb_SuratMasuk.getColumnModel().getColumn(0).setMinWidth(0);
            tb_SuratMasuk.getColumnModel().getColumn(0).setMaxWidth(0);
            tb_SuratMasuk.getColumnModel().getColumn(0).setWidth(0);
        }
    }

    private void loadfilterKategori() {
        String selectedKategori = cb_KategoriMenu.getSelectedItem().toString();
        if (selectedKategori.equals("--Pilih Kategori Surat--")) {
            loadTabel();
            return;
        }

        String kodeKategori = selectedKategori.split(" - ")[0]; // Ambil kode kategori
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn(null);
        model.addColumn("Bagian");
        model.addColumn("Kategori");
        model.addColumn("Pengirim");
        model.addColumn("Perihal");
        model.addColumn("Tanggal Diterima");
        model.addColumn("File Surat");

        try {
            SuratMasuk bg = new SuratMasuk();
            ResultSet data = bg.tampilSuratKategori(kodeKategori);

            while (data.next()) {
                if (data.getString("kategori").equals(kodeKategori)) {
                    model.addRow(new Object[]{
                        data.getString("id_suratmasuk"),
                        data.getString("bagian"),
                        data.getString("kategori"),
                        data.getString("pengirim"),
                        data.getString("perihal"),
                        data.getString("tanggal_diterima"),
                        data.getString("file_data")
                    });
                }
            }

            data.close();
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        }

        tb_SuratMasuk.setModel(model);

        tb_SuratMasuk.getColumnModel().getColumn(0).setMinWidth(0);
        tb_SuratMasuk.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_SuratMasuk.getColumnModel().getColumn(0).setWidth(0);
    }

    private void loadfilterTanggal(java.util.Date tanggal) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn(null);
        model.addColumn("Bagian");
        model.addColumn("Kategori");
        model.addColumn("pengirim");
        model.addColumn("Perihal");
        model.addColumn("Tanggal Diterima");
        model.addColumn("File Surat");

        try {
            java.sql.Date sqlDate = new java.sql.Date(tanggal.getTime());
            SuratMasuk bg = new SuratMasuk();
            ResultSet data = bg.tampilSuratTanggal(sqlDate);

            while (data.next()) {
                model.addRow(new Object[]{
                    data.getString("id_suratmasuk"),
                    data.getString("bagian"),
                    data.getString("kategori"),
                    data.getString("pengirim"),
                    data.getString("perihal"),
                    data.getString("tanggal_diterima"),
                    data.getString("file_data")});
            }

            data.close();
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        }

        tb_SuratMasuk.setModel(model);

        tb_SuratMasuk.getColumnModel().getColumn(0).setMinWidth(0);
        tb_SuratMasuk.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_SuratMasuk.getColumnModel().getColumn(0).setWidth(0);
    }

    private void loadfilterBagianKategoriTanggal() {
        String selectedKategori = cb_KategoriMenu.getSelectedItem().toString();
        String selectedBagian = cb_BagianMenu.getSelectedItem().toString();
        java.util.Date tanggalAwal = TglAwal.getDate();
        java.util.Date tanggalAkhir = TglAkhir.getDate();

        String filterKategori = null;
        String filterBagian = null;
        java.sql.Date sqlTanggalAwal = null;
        java.sql.Date sqlTanggalAkhir = null;

        if (!selectedKategori.equals("--Pilih Kategori Surat--")) {
            filterKategori = selectedKategori;
        }

        if (!selectedBagian.equals("--Pilih Bagian Surat--")) {
            filterBagian = selectedBagian.split(" - ")[0];
        }

        if (tanggalAwal != null) {
            sqlTanggalAwal = new java.sql.Date(tanggalAwal.getTime());
        }

        if (tanggalAkhir != null) {
            sqlTanggalAkhir = new java.sql.Date(tanggalAkhir.getTime());
        }

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn(null);
        model.addColumn("Bagian");
        model.addColumn("Kategori");
        model.addColumn("Pengirim");
        model.addColumn("Perihal");
        model.addColumn("Tanggal Diterima");
        model.addColumn("File Surat");

        try {
            SuratMasuk bg = new SuratMasuk();
            ResultSet data = bg.KodeTampilByFilters(filterBagian, filterKategori, sqlTanggalAwal, sqlTanggalAkhir);

            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd MMMM yyyy", new java.util.Locale("id", "ID"));

            while (data.next()) {
                String formattedDate = "";
                if (data.getString("tanggal_diterima") != null) {
                    java.util.Date date = java.sql.Date.valueOf(data.getString("tanggal_diterima"));
                    formattedDate = dateFormat.format(date);
                }

                model.addRow(new Object[]{
                    data.getString("id_suratmasuk"),
                    data.getString("bagian"),
                    data.getString("kategori"),
                    data.getString("pengirim"),
                    data.getString("perihal"),
                    formattedDate,
                    data.getString("file_data")});
            }

            data.close();
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        }

        tb_SuratMasuk.setModel(model);

        tb_SuratMasuk.getColumnModel().getColumn(0).setMinWidth(0);
        tb_SuratMasuk.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_SuratMasuk.getColumnModel().getColumn(0).setWidth(0);
    }

    void cbBagianSurat() {
        try {
            cb_BagianMenu.addItem("--Pilih Bagian Surat--");

            Bagian bg = new Bagian();
            ResultSet data = bg.Tampil_CbBagianSurat();

            while (data.next()) {
                cb_BagianMenu.addItem(data.getString("kode_bagian") + " - " + data.getString("nama_bagian"));
            }

            cb_BagianMenu.setSelectedItem("--Pilih Bagian Surat--"); // Pilih default option
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void cbKategoriSurat() {
        try {
            cb_KategoriMenu.addItem("--Pilih Kategori Surat--");

            Kategori ks = new Kategori();
            ResultSet data = ks.Tampil_CbKategoriSurat();

            while (data.next()) {
                cb_KategoriMenu.addItem(data.getString("kode_kategori") + " - " + data.getString("nama_kategori"));
            }

            cb_KategoriMenu.setSelectedItem("--Pilih Kategori Surat--");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void blokirtextfieldTanggal() {
        if (TglAwal.getDateEditor() instanceof JTextFieldDateEditor) {
            JTextFieldDateEditor editorAwal = (JTextFieldDateEditor) TglAwal.getDateEditor();
            editorAwal.setEditable(false);
            editorAwal.setEnabled(false);
        }

        if (TglAkhir.getDateEditor() instanceof JTextFieldDateEditor) {
            JTextFieldDateEditor editorAkhir = (JTextFieldDateEditor) TglAkhir.getDateEditor();
            editorAkhir.setEditable(false);
            editorAkhir.setEnabled(false);
        }
    }

    public void reset() {
        cb_KategoriMenu.setSelectedIndex(0);
        cb_BagianMenu.setSelectedIndex(0);
        TglAwal.setCalendar(null);
        TglAkhir.setCalendar(null);
        loadTabel();
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
        pn_uploadmasuk = new javax.swing.JPanel();
        bTambah = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_SuratMasuk = new javax.swing.JTable();
        TglAwal = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cb_BagianMenu = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cb_KategoriMenu = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        TglAkhir = new com.toedter.calendar.JDateChooser();
        bReset = new javax.swing.JButton();

        setLayout(new java.awt.CardLayout());

        pn_Dasar.setBackground(new java.awt.Color(255, 255, 255));

        pn_uploadmasuk.setBackground(new java.awt.Color(234, 242, 248));

        bTambah.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        bTambah.setForeground(new java.awt.Color(0, 125, 197));
        bTambah.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bTambah.setText("Tambah Surat Masuk ");
        bTambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bTambahMouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Disini !");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/upload_file14.png"))); // NOI18N

        javax.swing.GroupLayout pn_uploadmasukLayout = new javax.swing.GroupLayout(pn_uploadmasuk);
        pn_uploadmasuk.setLayout(pn_uploadmasukLayout);
        pn_uploadmasukLayout.setHorizontalGroup(
            pn_uploadmasukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(bTambah, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pn_uploadmasukLayout.setVerticalGroup(
            pn_uploadmasukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_uploadmasukLayout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bTambah)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(10, 10, 10))
        );

        tb_SuratMasuk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tb_SuratMasuk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_SuratMasukMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_SuratMasuk);

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Filter Pencarian");

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Bagian");

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Kategori");

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Tanggal Awal");

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Tanggal Akhir");

        bReset.setText("RESET");
        bReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_DasarLayout = new javax.swing.GroupLayout(pn_Dasar);
        pn_Dasar.setLayout(pn_DasarLayout);
        pn_DasarLayout.setHorizontalGroup(
            pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_DasarLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_DasarLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(cb_BagianMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(cb_KategoriMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(TglAwal, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TglAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(bReset))
                    .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1200, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pn_uploadmasuk, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        pn_DasarLayout.setVerticalGroup(
            pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_DasarLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(pn_uploadmasuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(cb_BagianMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(cb_KategoriMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7))
                    .addComponent(TglAwal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(TglAkhir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(bReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11))
        );

        add(pn_Dasar, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void bTambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bTambahMouseClicked
        
          PopUpSuratMasuk sk = new PopUpSuratMasuk();
        sk.setVisible(true);
        sk.bEdit.setVisible(false);
        sk.bHapus.setVisible(false);
        sk.otoID();
        sk.tTanggalDiterima.setDate(new Date());
        sk.txtfilepath.setEnabled(false);
        sk.setVisible(true);
    }//GEN-LAST:event_bTambahMouseClicked

    private void tb_SuratMasukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_SuratMasukMouseClicked
       PopUpSuratMasuk suratMasukFrame = new PopUpSuratMasuk();
        suratMasukFrame.setVisible(true);
        suratMasukFrame.bTambah.setVisible(false);
        suratMasukFrame.setLocationRelativeTo(null);
        suratMasukFrame.txtfilepath.setEnabled(false);
        suratMasukFrame.ambilDetail();

        try {
            SuratMasuk sur = new SuratMasuk();

            int baris = tb_SuratMasuk.getSelectedRow();

            sur.setId_surat(tb_SuratMasuk.getValueAt(baris, 0).toString());

            String kode = tb_SuratMasuk.getValueAt(baris, 1).toString();
            Bagian.setKode_bagian(kode);
            Bagian bag = new Bagian();
            ResultSet data = bag.KonversiBagian();
            if (data.next()) {
                String namaBagian = data.getString("nama_bagian");
                SuratMasuk.setBagian(kode + " - " + namaBagian);
            }
            sur.setKategori(tb_SuratMasuk.getValueAt(baris, 2).toString());
            sur.setAsal_surat(tb_SuratMasuk.getValueAt(baris, 3).toString());
            sur.setPerihal(tb_SuratMasuk.getValueAt(baris, 4).toString());

            String tanggalDiterimaStr = tb_SuratMasuk.getValueAt(baris, 5).toString();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", new Locale("id", "ID"));
            try {
                Date tanggalDiterima = dateFormat.parse(tanggalDiterimaStr);
                sur.setTanggal_diterima(new java.sql.Date(tanggalDiterima.getTime()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            sur.setFile_data(tb_SuratMasuk.getValueAt(baris, 6).toString());

            suratMasukFrame.ambilDetail();

        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_tb_SuratMasukMouseClicked

    private void bResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bResetActionPerformed
      reset();
    }//GEN-LAST:event_bResetActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser TglAkhir;
    private com.toedter.calendar.JDateChooser TglAwal;
    private javax.swing.JButton bReset;
    private javax.swing.JLabel bTambah;
    private javax.swing.JComboBox<String> cb_BagianMenu;
    private javax.swing.JComboBox<String> cb_KategoriMenu;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JPanel pn_Dasar;
    private javax.swing.JPanel pn_uploadmasuk;
    private javax.swing.JTable tb_SuratMasuk;
    // End of variables declaration//GEN-END:variables
}
