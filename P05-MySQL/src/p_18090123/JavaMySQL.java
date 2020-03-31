/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p_18090123;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lenovo
 */
public class JavaMySQL extends javax.swing.JFrame {

    int idBaris=0;
    String role;
    DefaultTableModel model;
    
    /**
     * Creates new form JavaMySQL
     */
    
    
    public JavaMySQL() {
        initComponents();
        //KoneksiDB.sambungDB();
        aturModelTabel();
        pangkat();
        jafung();
        showForm(false);
        showData("");
    }
    
   private void aturModelTabel(){
Object[] kolom = {"No","NIDN","NAMA","JAFUNG","Golongan/Pangkat","No HP"};
model = new DefaultTableModel(kolom,0) {
boolean[] canEdit = new boolean [] {
false, false, false, false, false, false
};

@Override public boolean isCellEditable(int rowIndex, int columnIndex) {
return canEdit [columnIndex];
}

};

tblData.setModel(model);
 tblData.setRowHeight(20);
  tblData.getColumnModel().getColumn(0).setMinWidth(0);
  tblData.getColumnModel().getColumn(0).setMaxWidth(0);
  }
  private void showForm(boolean b){
  areaSplit.setDividerLocation(0.3);
  areaSplit.getLeftComponent().setVisible(b);
 }
  
  
  private void resetForm(){
 tblData.clearSelection();
 txtNIDN.setText("");
 txtNama.setText("");
  cmbJafung.setSelectedIndex(0);
  cmbPangkat.setSelectedIndex(0);
  txtNoHP.setText("");
  txtNIDN.requestFocus();
          }
  
  
  private void pangkat(){
    cmbPangkat.removeAllItems();
    cmbPangkat.addItem("Pilih Golongan/Pangkat");
    cmbPangkat.addItem("Belum ada"); 
    cmbPangkat.addItem("II/c (Pengatur)"); 
    cmbPangkat.addItem("II/d (Pengatur Tk. I)"); 
    cmbPangkat.addItem("III/a (Penata Muda)"); 
    cmbPangkat.addItem("III/b (Penata Muda Tk. I)");
    cmbPangkat.addItem("III/c (Penata)");
    cmbPangkat.addItem("III/d (Penata Tk. I)");
    cmbPangkat.addItem("IV/a (Pembina)");
    cmbPangkat.addItem("IV/b (Pembina Tk. I)");
    cmbPangkat.addItem("IV/c (Pembina Utama Muda)");
    cmbPangkat.addItem("IV/d (Pembina Utama Madya)");
    cmbPangkat.addItem("IV/e (Pembina Utama)");
}
private void jafung(){
    cmbJafung.removeAllItems();
    cmbJafung.addItem("Pilih Golongan/Pangkat");
    cmbJafung.addItem("Belum ada"); 
    cmbJafung.addItem("Asisten Ahli (100)");
    cmbJafung.addItem("Asisten Ahli (150)");
    cmbJafung.addItem("Lektor (200)");
    cmbJafung.addItem("Lektor (300)");
    cmbJafung.addItem("Lektor Kepala (400)");
    cmbJafung.addItem("Lektor Kepala (550)");
    cmbJafung.addItem("Lektor Kepala (700)");
    cmbJafung.addItem("Profesor (850)");
    cmbJafung.addItem("Profesor (1050)");
}
                


private void showData(String key){
    model.getDataVector().removeAllElements();

    String where = "";if(!key.isEmpty()){
    where += "WHERE nidn LIKE '%"+key+"%' "+ "OR nama LIKE '%"+key+"%' "+ "OR jabatan_fungsional LIKE '%"+key+"%' "+ "OR pangkat_golongan LIKE '%"+key+"%' "+ "OR no_hp LIKE '%"+key+"%'";
    }
    String sql = "SELECT * FROM data_dosen "+where;
                           Connection con;Statement st;
                           ResultSet rs;int baris = 0;
                           try {con = KoneksiDB.sambungDB();
                           st = con.createStatement();
                           rs = st.executeQuery(sql);
                           while (rs.next()) {
                               Object id = rs.getInt(1);
                           Object nidn = rs.getString(2);
                           Object nama = rs.getString(3);
                           Object jafung = rs.getString(4);
                           Object pangkat = rs.getString(5);
                           Object no_hp = rs.getString(6);Object[] data = {id,nidn,nama,jafung,pangkat,no_hp};
                           model.insertRow(baris, data); baris++;}
                           st.close();
                           con.close();
                           tblData.revalidate();
                           tblData.repaint();
                           } catch (SQLException e) {System.err.println("showData(): "+e.getMessage());}
}


private void resetView(){
    resetForm();
    showForm(false);
    showData("");
    btnHapus.setEnabled(false);
    idBaris = 0;
}

                              


private void pilihData(String n){
    btnHapus.setEnabled(true);

    String sql = "SELECT * FROM data_dosen WHERE id='"+n+"'";
    Connection con;
    Statement st;
    ResultSet rs;
    try {con = KoneksiDB.sambungDB();
    st = con.createStatement();
    rs = st.executeQuery(sql);
    while (rs.next()) {
        int id = rs.getInt(1);
        String nidn = rs.getString(2);
        String nama = rs.getString(3);
        Object jafung = rs.getString(4);
        Object pangkat = rs.getString(5);
        String no_hp = rs.getString(6);
        idBaris = id;
        txtNIDN.setText(nidn);
        txtNama.setText(nama);
        cmbJafung.setSelectedItem(jafung);
        cmbPangkat.setSelectedItem(pangkat);
        txtNoHP.setText(no_hp);
}
    st.close();
    con.close();

    con.close();
    showForm(true);
    } catch (SQLException e) {System.err.println("pilihData(): "+e.getMessage());}}
    

private void simpanData(){String nidn = txtNIDN.getText();
    String nama = txtNama.getText();
    int jafung = cmbJafung.getSelectedIndex();
    int pangkat = cmbPangkat.getSelectedIndex();
    String nohp = txtNoHP.getText();
    if(nidn.isEmpty() || nama.isEmpty() || jafung==0 || pangkat==0 ||nohp.isEmpty()){
        JOptionPane.showMessageDialog(this, "Mohon lengkapi data!");
    }else{
    String jafung_isi = cmbJafung.getSelectedItem().toString();
    String pangkat_isi = cmbPangkat.getSelectedItem().toString();
    String sql = "INSERT INTO data_dosen (nidn,nama,jabatan_fungsional,"+ "pangkat_golongan,no_hp) "+ "VALUES (\""+nidn+"\",\""+nama+"\","+ "\""+jafung_isi+"\",\""+pangkat_isi+"\",\""+nohp+"\")";Connection con;
    Statement st;try { 
        con = KoneksiDB.sambungDB();
   
    st = con.createStatement();
    st.executeUpdate(sql);
    st.close();
     con.close();               
    resetView();
    JOptionPane.showMessageDialog(this,"Data telahdisimpan!");
    } catch (SQLException e) {JOptionPane.showMessageDialog(this, e.getMessage());
    }}
}

private void ubahData(){String nidn = txtNIDN.getText();
    String nama = txtNama.getText();
    int jafung = cmbJafung.getSelectedIndex();
    int pangkat = cmbPangkat.getSelectedIndex();
    String nohp = txtNoHP.getText();
    if(nidn.isEmpty() || nama.isEmpty() || jafung==0 || pangkat==0 ||nohp.isEmpty()){
        JOptionPane.showMessageDialog(this, "Mohon lengkapi data!");
    
   }else{String jafung_isi = cmbJafung.getSelectedItem().toString();
    String pangkat_isi = cmbPangkat.getSelectedItem().toString();
   
    
     String sql = "UPDATE data_dosen "+ "SET nidn=\""+nidn+"\","+ "nama=\""+nama+"\","+ "jabatan_fungsional=\""+jafung_isi+"\","+ "pangkat_golongan=\""+pangkat_isi+"\","+ "no_hp=\""+nohp+"\"WHERE id=\""+idBaris+"\"";Connection con;Statement st;
    try {
    con = KoneksiDB.sambungDB();
    
    st = con.createStatement();
    st.executeUpdate(sql);
    st.close();con.close();
    resetView();
    JOptionPane.showMessageDialog(this,"Data telah diubah!");
    }
    catch (SQLException e) {JOptionPane.showMessageDialog(this, e.getMessage());
}
}
                                              
     }


private void hapusData(int baris){
    Connection con;Statement st;
    try {                
        con = KoneksiDB.sambungDB();
        st = con.createStatement();
        st.executeUpdate("DELETE FROM data_dosen WHERE id="+baris);
        st.close();con.close();                resetView();
        JOptionPane.showMessageDialog(this, "Data telah dihapus");
    }
        catch (SQLException e) {JOptionPane.showMessageDialog(this, e.getMessage());
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
        java.awt.GridBagConstraints gridBagConstraints;

        btnTambah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        txtCari = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        areaSplit = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cmbPangkat = new javax.swing.JComboBox<>();
        cmbJafung = new javax.swing.JComboBox<>();
        txtNIDN = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtNoHP = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        btnTutup = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblData = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        btnTambah.setText("TAMBAH DATA");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnHapus.setText("HAPUS DATA");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariKeyReleased(evt);
            }
        });

        btnCari.setText("CARI");

        areaSplit.setResizeWeight(0.5);
        areaSplit.setOneTouchExpandable(true);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("NIDN");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(13, 133, 0, 0);
        jPanel2.add(jLabel2, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("NAMA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 133, 0, 0);
        jPanel2.add(jLabel6, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("JABATAN FUNGSIONAL");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 11, 0, 0);
        jPanel2.add(jLabel7, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("PANGKAT / JABATAN");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 24, 0, 0);
        jPanel2.add(jLabel8, gridBagConstraints);

        cmbPangkat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 0);
        jPanel2.add(cmbPangkat, gridBagConstraints);

        cmbJafung.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 0);
        jPanel2.add(cmbJafung, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 0);
        jPanel2.add(txtNIDN, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 0);
        jPanel2.add(txtNama, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("NO HP");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(13, 123, 0, 0);
        jPanel2.add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 0);
        jPanel2.add(txtNoHP, gridBagConstraints);

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 335;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.insets = new java.awt.Insets(25, 0, 0, 0);
        jPanel2.add(jSeparator2, gridBagConstraints);

        btnTutup.setText("TUTUP");
        btnTutup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTutupActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.ipadx = 90;
        gridBagConstraints.ipady = 20;
        jPanel2.add(btnTutup, gridBagConstraints);

        btnSimpan.setText("SIMPAN");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.ipadx = 90;
        gridBagConstraints.ipady = 20;
        jPanel2.add(btnSimpan, gridBagConstraints);

        areaSplit.setLeftComponent(jPanel2);

        tblData.setModel(new javax.swing.table.DefaultTableModel(
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
        tblData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDataMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblData);

        areaSplit.setRightComponent(jScrollPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(areaSplit, javax.swing.GroupLayout.DEFAULT_SIZE, 990, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnTambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(areaSplit, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:
        
        role="Tambah";
        btnSimpan.setText("Simpan");
        idBaris=0;
        resetForm();
        showForm(true);
        btnHapus.setEnabled(false);
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        if (role.equals("Tambah")) {
            simpanData();
            
        }else if(role.equals("Ubah")){
            ubahData();
        }
        
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnTutupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTutupActionPerformed
        // TODO add your handling code here:
        
        resetForm();
        showForm(false);
        btnHapus.setEnabled(false);
        idBaris=0;
    }//GEN-LAST:event_btnTutupActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        
        if (idBaris == 0) {
            JOptionPane.showMessageDialog(this, "Pilih data akan " + "Hapus!");
            
        }else{
            hapusData(idBaris);
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void txtCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyReleased
        // TODO add your handling code here:
        
        String key = txtCari.getText();
        showData(key);
    }//GEN-LAST:event_txtCariKeyReleased

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        // TODO add your handling code here:
        areaSplit.setDividerLocation(0.3);
    }//GEN-LAST:event_formComponentResized

    private void tblDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDataMouseClicked
        // TODO add your handling code here:
        role= "Ubah";
        int row = tblData.getRowCount();
        if (row > 0) {
            int sel = tblData.getSelectedRow();
            if(sel != -1){
                pilihData(tblData.getValueAt(sel, 0).toString());
                btnSimpan.setText("UBAH DATA");
            }
            
        }
    }//GEN-LAST:event_tblDataMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JavaMySQL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JavaMySQL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JavaMySQL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JavaMySQL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JavaMySQL().setVisible(true);
            }
        });
        
        
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSplitPane areaSplit;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnTutup;
    private javax.swing.JComboBox<String> cmbJafung;
    private javax.swing.JComboBox<String> cmbPangkat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable tblData;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtNIDN;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNoHP;
    // End of variables declaration//GEN-END:variables

 
    
}


