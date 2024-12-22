package ux;

import Kelas.Bagian;
import com.formdev.flatlaf.FlatClientProperties;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import popup.PopUpTambahBagian;
import static popup.PopUpTambahBagian.lb_Id;
import static popup.PopUpTambahBagian.tf_Kode;
import static popup.PopUpTambahBagian.tf_Nama;

public class bagian extends javax.swing.JPanel implements Bagian.PerubahanData {

    private Bagian bgn;

    public bagian() throws SQLException {
        initComponents();
        pn_Dasar.putClientProperty(FlatClientProperties.STYLE, "arc:50");
        pn_uploadmasuk.putClientProperty(FlatClientProperties.STYLE, "arc:50");

        bgn = new Bagian();
        bgn.TambahPerubahanData(this);

        loadTabel();
    }

    public void loadTabel() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn(null);
        model.addColumn("Kode Bagian Surat");
        model.addColumn("Nama Bagian Surat");

        try {
            Bagian k = new Bagian();
            ResultSet data = k.KodeTampilTabel();

            while (data.next()) {
                model.addRow(new Object[]{
                    data.getString("id_bagian"),
                    data.getString("kode_bagian"),
                    data.getString("nama_bagian"),});
            }

            data.close();
        } catch (SQLException sQLException) {
        }

        tb_Bagian.setModel(model);

        tb_Bagian.getColumnModel().getColumn(0).setMinWidth(0);
        tb_Bagian.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_Bagian.getColumnModel().getColumn(0).setWidth(0);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pn_Dasar = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_Bagian = new javax.swing.JTable();
        pn_uploadmasuk = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(234, 242, 248));
        setLayout(new java.awt.CardLayout());

        pn_Dasar.setBackground(new java.awt.Color(255, 255, 255));

        tb_Bagian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "1", "1", "1"},
                {"2", "2", "2", "2"},
                {"3", "3", "3", "3"},
                {"4", "4", "4", "4"}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tb_Bagian.setRowHeight(30);
        tb_Bagian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_BagianMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_Bagian);

        pn_uploadmasuk.setBackground(new java.awt.Color(234, 242, 248));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 125, 197));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Tambah Bagian");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Disini !");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/upload_file14.png"))); // NOI18N

        javax.swing.GroupLayout pn_uploadmasukLayout = new javax.swing.GroupLayout(pn_uploadmasuk);
        pn_uploadmasuk.setLayout(pn_uploadmasukLayout);
        pn_uploadmasukLayout.setHorizontalGroup(
            pn_uploadmasukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_uploadmasukLayout.createSequentialGroup()
                .addGap(578, 578, 578)
                .addGroup(pn_uploadmasukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(551, 551, 551))
        );
        pn_uploadmasukLayout.setVerticalGroup(
            pn_uploadmasukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_uploadmasukLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout pn_DasarLayout = new javax.swing.GroupLayout(pn_Dasar);
        pn_Dasar.setLayout(pn_DasarLayout);
        pn_DasarLayout.setHorizontalGroup(
            pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_DasarLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pn_uploadmasuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1355, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        pn_DasarLayout.setVerticalGroup(
            pn_DasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_DasarLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(pn_uploadmasuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(72, Short.MAX_VALUE))
        );

        add(pn_Dasar, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        try {
            PopUpTambahBagian pusm = new PopUpTambahBagian(null, true, bgn);
            pusm.setVisible(true);
        } catch (SQLException sQLException) {
        }
    }//GEN-LAST:event_jLabel2MouseClicked

    private void tb_BagianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_BagianMouseClicked
        try {
            int baris = tb_Bagian.rowAtPoint(evt.getPoint());
            String id = tb_Bagian.getModel().getValueAt(baris, 0).toString();
            String kode = tb_Bagian.getValueAt(baris, 1).toString();
            String nama = tb_Bagian.getValueAt(baris, 2).toString();

            PopUpTambahBagian popUpKategori = new PopUpTambahBagian(null, true, bgn);

            lb_Id.setText(id);
            tf_Kode.setText(kode);
            tf_Nama.setText(nama);
            popUpKategori.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(bagian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tb_BagianMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pn_Dasar;
    private javax.swing.JPanel pn_uploadmasuk;
    private javax.swing.JTable tb_Bagian;
    // End of variables declaration//GEN-END:variables
@Override
    public void AktifPerubahanData() {
        loadTabel();
    }
}
