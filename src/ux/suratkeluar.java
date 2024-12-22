package ux;

import Kelas.Bagian;
import Kelas.Kategori;
import Kelas.SuratKeluar;
import com.formdev.flatlaf.FlatClientProperties;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import popup.PopUpSuratKeluar;

public class suratkeluar extends javax.swing.JPanel implements SuratKeluar.PerubahanData {

    private SuratKeluar sk;

    public suratkeluar() throws SQLException {
        initComponents();
        pn_uploadkeluar.putClientProperty(FlatClientProperties.STYLE, "arc:50");
        sk = new SuratKeluar();
        sk.TambahPerubahanData(this);

        loadTabel();
        cbBagianSurat();
        cbKategoriSurat();

        cb_KategoriMenu.addActionListener(evt -> applyFilters());
        cb_BagianMenu.addActionListener(evt -> applyFilters());

        tf_TglAwal.addPropertyChangeListener(evt -> {
            if ("date".equals(evt.getPropertyName())) {
                applyFilters();
            }
        });

        tf_TglAkhir.addPropertyChangeListener(evt -> {
            if ("date".equals(evt.getPropertyName())) {
                applyFilters();
            }
        });

    }

    public void loadTabel() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn(null); // Kolom tersembunyi untuk ID
        model.addColumn("Kategori");
        model.addColumn("Bagian");
        model.addColumn("Nomor");
        model.addColumn("Tanggal");
        model.addColumn("Perihal");
        model.addColumn("Tujuan");
        model.addColumn("Nama File");

        try {
            SuratKeluar k = new SuratKeluar();

            java.util.Date now = new java.util.Date();
            java.sql.Date startOfMonth = new java.sql.Date(now.getYear(), now.getMonth(), 1);
            java.sql.Date endOfMonth = new java.sql.Date(now.getYear(), now.getMonth() + 1, 0);

            ResultSet data = k.KodeTampilByFilters(null, null, null, null);

            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd MMMM yyyy", new java.util.Locale("id", "ID"));

            while (data.next()) {
                // Format tanggal
                String formattedDate = "";
                if (data.getString("tgl_dibuat") != null) {
                    java.util.Date date = java.sql.Date.valueOf(data.getString("tgl_dibuat"));
                    formattedDate = dateFormat.format(date);
                }

                // Tambahkan data ke tabel
                model.addRow(new Object[]{
                    data.getString("id_suratkeluar"), // ID
                    data.getString("kategori"), // Kategori
                    data.getString("bagian"), // Bagian
                    data.getString("no_surat"), // Nomor
                    formattedDate, // Tanggal dalam format Hari Bulan Tahun
                    data.getString("perihal"), // Perihal
                    data.getString("tujuan"), // Tujuan
                    data.getString("nama_file") // Nama File
                });
            }

            data.close();
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        }

        tb_SuratKeluar.setModel(model);

        // Sembunyikan kolom ID
        tb_SuratKeluar.getColumnModel().getColumn(0).setMinWidth(0);
        tb_SuratKeluar.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_SuratKeluar.getColumnModel().getColumn(0).setWidth(0);
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

    private void applyFilters() {
        String selectedKategori = cb_KategoriMenu.getSelectedItem().toString();
        String selectedBagian = cb_BagianMenu.getSelectedItem().toString();
        java.util.Date tanggalAwal = tf_TglAwal.getDate(); // Tanggal awal
        java.util.Date tanggalAkhir = tf_TglAkhir.getDate(); // Tanggal akhir

        String filterKategori = null;
        String filterBagian = null;
        java.sql.Date sqlTanggalAwal = null;
        java.sql.Date sqlTanggalAkhir = null;

        if (!selectedKategori.equals("--Pilih Kategori Surat--")) {
            filterKategori = selectedKategori.split(" - ")[0];
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
        model.addColumn("Kategori");
        model.addColumn("Bagian");
        model.addColumn("Nomor");
        model.addColumn("Tanggal");
        model.addColumn("Perihal");
        model.addColumn("Tujuan");
        model.addColumn("Nama File");

        try {
            SuratKeluar bg = new SuratKeluar();
            ResultSet data = bg.KodeTampilByFilters(filterBagian, filterKategori, sqlTanggalAwal, sqlTanggalAkhir);

            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd MMMM yyyy", new java.util.Locale("id", "ID"));

            while (data.next()) {
                String formattedDate = "";
                if (data.getString("tgl_dibuat") != null) {
                    java.util.Date date = java.sql.Date.valueOf(data.getString("tgl_dibuat"));
                    formattedDate = dateFormat.format(date);
                }

                model.addRow(new Object[]{
                    data.getString("id_suratkeluar"),
                    data.getString("kategori"),
                    data.getString("bagian"),
                    data.getString("no_surat"),
                    formattedDate,
                    data.getString("perihal"),
                    data.getString("tujuan"),
                    data.getString("nama_file"),});
            }

            data.close();
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        }

        tb_SuratKeluar.setModel(model);

        tb_SuratKeluar.getColumnModel().getColumn(0).setMinWidth(0);
        tb_SuratKeluar.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_SuratKeluar.getColumnModel().getColumn(0).setWidth(0);
    }

    private void filterTabelByKategori() {
        String selectedKategori = cb_KategoriMenu.getSelectedItem().toString();
        if (selectedKategori.equals("--Pilih Kategori Surat--")) {
            loadTabel();
            return;
        }

        String kodeKategori = selectedKategori.split(" - ")[0]; // Ambil kode kategori
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn(null);
        model.addColumn("Kategori");
        model.addColumn("Bagian");
        model.addColumn("Nomor");
        model.addColumn("Tanggal");
        model.addColumn("Perihal");
        model.addColumn("Tujuan");
        model.addColumn("Nama File");

        try {
            SuratKeluar bg = new SuratKeluar();
            ResultSet data = bg.KodeTampilByKategori(kodeKategori);

            while (data.next()) {
                if (data.getString("kategori").equals(kodeKategori)) {
                    model.addRow(new Object[]{
                        data.getString("id_suratkeluar"),
                        data.getString("kategori"),
                        data.getString("bagian"),
                        data.getString("no_surat"),
                        data.getString("tgl_dibuat"),
                        data.getString("perihal"),
                        data.getString("tujuan"),
                        data.getString("nama_file"),});
                }
            }

            data.close();
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        }

        tb_SuratKeluar.setModel(model);

        tb_SuratKeluar.getColumnModel().getColumn(0).setMinWidth(0);
        tb_SuratKeluar.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_SuratKeluar.getColumnModel().getColumn(0).setWidth(0);
    }

    private void filterTabelByBagian() {
        String selectedBagian = cb_BagianMenu.getSelectedItem().toString();

        if (selectedBagian.equals("--Pilih Bagian Surat--")) {
            loadTabel();
        } else {
            String filterBagian = selectedBagian.split(" - ")[0];
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn(null);
            model.addColumn("Kategori");
            model.addColumn("Bagian");
            model.addColumn("Nomor");
            model.addColumn("Tanggal");
            model.addColumn("Perihal");
            model.addColumn("Tujuan");
            model.addColumn("Nama File");

            try {
                SuratKeluar bg = new SuratKeluar();
                ResultSet data = bg.KodeTampilByBagian(filterBagian);

                while (data.next()) {
                    model.addRow(new Object[]{
                        data.getString("id_suratkeluar"),
                        data.getString("kategori"),
                        data.getString("bagian"),
                        data.getString("no_surat"),
                        data.getString("tgl_dibuat"),
                        data.getString("perihal"),
                        data.getString("tujuan"),
                        data.getString("nama_file"),});
                }

                data.close();
            } catch (SQLException sQLException) {
                sQLException.printStackTrace();
            }

            tb_SuratKeluar.setModel(model);

            tb_SuratKeluar.getColumnModel().getColumn(0).setMinWidth(0);
            tb_SuratKeluar.getColumnModel().getColumn(0).setMaxWidth(0);
            tb_SuratKeluar.getColumnModel().getColumn(0).setWidth(0);
        }
    }

    private void tf_TglPropertyChange(java.beans.PropertyChangeEvent evt) {
        if ("date".equals(evt.getPropertyName())) {
            java.util.Date selectedDate = tf_TglAkhir.getDate();
            if (selectedDate != null) {
                filterTabelByTanggal(selectedDate);
            } else {
                filterTabelByKategori();
                filterTabelByBagian();
                loadTabel();
            }
        }
    }

    private void filterTabelByTanggal(java.util.Date tanggal) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn(null);
        model.addColumn("Kategori");
        model.addColumn("Bagian");
        model.addColumn("Nomor");
        model.addColumn("Tanggal");
        model.addColumn("Perihal");
        model.addColumn("Tujuan");
        model.addColumn("Nama File");

        try {
            java.sql.Date sqlDate = new java.sql.Date(tanggal.getTime());
            SuratKeluar bg = new SuratKeluar();
            ResultSet data = bg.KodeTampilByTanggal(sqlDate);

            while (data.next()) {
                model.addRow(new Object[]{
                    data.getString("id_suratkeluar"),
                    data.getString("kategori"),
                    data.getString("bagian"),
                    data.getString("no_surat"),
                    data.getString("tgl_dibuat"),
                    data.getString("perihal"),
                    data.getString("tujuan"),
                    data.getString("nama_file"),});
            }

            data.close();
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        }

        tb_SuratKeluar.setModel(model);

        tb_SuratKeluar.getColumnModel().getColumn(0).setMinWidth(0);
        tb_SuratKeluar.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_SuratKeluar.getColumnModel().getColumn(0).setWidth(0);
    }

    public void reset() {
        cb_KategoriMenu.setSelectedIndex(0);
        cb_BagianMenu.setSelectedIndex(0);
        tf_TglAkhir.setCalendar(null);
        tf_TglAwal.setCalendar(null);
        loadTabel();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tf_Tgl = new com.toedter.calendar.JDateChooser();
        pn_Dasar = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_SuratKeluar = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        pn_uploadkeluar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cb_BagianMenu = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cb_KategoriMenu = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        tf_TglAwal = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        tf_TglAkhir = new com.toedter.calendar.JDateChooser();
        bt_Reset = new javax.swing.JButton();

        setBackground(new java.awt.Color(248, 248, 248));
        setLayout(new java.awt.CardLayout());

        pn_Dasar.setBackground(new java.awt.Color(255, 255, 255));

        tb_SuratKeluar.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_SuratKeluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_SuratKeluarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_SuratKeluar);

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Filter Pencarian");

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Bagian");

        pn_uploadkeluar.setBackground(new java.awt.Color(234, 242, 248));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 125, 197));
        jLabel1.setText("Tambah Surat Keluar");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Disini !");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/upload_file14.png"))); // NOI18N

        javax.swing.GroupLayout pn_uploadkeluarLayout = new javax.swing.GroupLayout(pn_uploadkeluar);
        pn_uploadkeluar.setLayout(pn_uploadkeluarLayout);
        pn_uploadkeluarLayout.setHorizontalGroup(
            pn_uploadkeluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_uploadkeluarLayout.createSequentialGroup()
                .addGap(578, 578, 578)
                .addGroup(pn_uploadkeluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(551, Short.MAX_VALUE))
        );
        pn_uploadkeluarLayout.setVerticalGroup(
            pn_uploadkeluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_uploadkeluarLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(25, 25, 25))
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Kategori");

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Tanggal Awal ");

        tf_TglAwal.setDateFormatString("dd MMMM yyyy");

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Tanggal Akhir");

        tf_TglAkhir.setDateFormatString("dd MMMM yyyy");

        bt_Reset.setText("Reset");
        bt_Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_ResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_DasarLayout = new javax.swing.GroupLayout(pn_Dasar);
        pn_Dasar.setLayout(pn_DasarLayout);
        pn_DasarLayout.setHorizontalGroup(
            pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_DasarLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_DasarLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(cb_BagianMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(cb_KategoriMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(100, 100, 100)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(tf_TglAwal, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(tf_TglAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bt_Reset))
                    .addComponent(jLabel4)
                    .addComponent(pn_uploadkeluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1368, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        pn_DasarLayout.setVerticalGroup(
            pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_DasarLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(pn_uploadkeluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(cb_BagianMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(cb_KategoriMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7))
                    .addComponent(tf_TglAwal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tf_TglAkhir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(bt_Reset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11))
        );

        add(pn_Dasar, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        try {
            PopUpSuratKeluar pusm = new PopUpSuratKeluar(null, true, sk, true);
            pusm.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(suratkeluar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel1MouseClicked

    private void tb_SuratKeluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_SuratKeluarMouseClicked
        try {
            int baris = tb_SuratKeluar.getSelectedRow();
            if (baris != -1 && tb_SuratKeluar.getValueAt(baris, 0) != null) {

                String Id = tb_SuratKeluar.getValueAt(baris, 0).toString();
                String Kategori = tb_SuratKeluar.getValueAt(baris, 1).toString();
                String Bagian = tb_SuratKeluar.getValueAt(baris, 2).toString();
                String Nomor = tb_SuratKeluar.getValueAt(baris, 3).toString();
                String Tanggal = tb_SuratKeluar.getValueAt(baris, 4).toString();
                String Perihal = tb_SuratKeluar.getValueAt(baris, 5).toString();
                String Tujuan = tb_SuratKeluar.getValueAt(baris, 6).toString();
                String File = tb_SuratKeluar.getValueAt(baris, 7).toString();

                PopUpSuratKeluar pusk = new PopUpSuratKeluar(null, true, sk, true);

                pusk.isInitializing = true; // Set flag sebelum pengisian data
                pusk.lb_Id.setText(Id);

                for (int i = 0; i < pusk.cb_Kategori.getItemCount(); i++) {
                    String item = pusk.cb_Kategori.getItemAt(i);
                    if (item.startsWith(Kategori + " -")) {
                        pusk.cb_Kategori.setSelectedIndex(i);
                        break;
                    }
                }

                for (int i = 0; i < pusk.cb_Bagian.getItemCount(); i++) {
                    String item = pusk.cb_Bagian.getItemAt(i);
                    if (item.startsWith(Bagian + " -")) {
                        pusk.cb_Bagian.setSelectedIndex(i);
                        break;
                    }
                }

                pusk.tf_NoSurat.setText(Nomor);

                if (Tanggal != null && !Tanggal.isEmpty()) {
                    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd MMMM yyyy", new java.util.Locale("id", "ID"));
                    java.util.Date date = formatter.parse(Tanggal);
                    pusk.tf_Tgl.setDate(date);
                }

                pusk.tf_Perihal.setText(Perihal);
                pusk.tf_Tujuan.setText(Tujuan);
                pusk.tf_File.setText(File);

                pusk.isInitializing = false; // Selesai inisialisasi
                pusk.setVisible(true);
            }
        } catch (SQLException | java.text.ParseException ex) {
            Logger.getLogger(suratkeluar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tb_SuratKeluarMouseClicked

    private void bt_ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_ResetActionPerformed
        reset();
    }//GEN-LAST:event_bt_ResetActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_Reset;
    private javax.swing.JComboBox<String> cb_BagianMenu;
    private javax.swing.JComboBox<String> cb_KategoriMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pn_Dasar;
    private javax.swing.JPanel pn_uploadkeluar;
    private javax.swing.JTable tb_SuratKeluar;
    private com.toedter.calendar.JDateChooser tf_Tgl;
    private com.toedter.calendar.JDateChooser tf_TglAkhir;
    private com.toedter.calendar.JDateChooser tf_TglAwal;
    // End of variables declaration//GEN-END:variables
    @Override
    public void AktifPerubahanData() {
        loadTabel();
    }
}
