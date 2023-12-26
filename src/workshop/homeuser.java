/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package workshop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;
import java.text.ParseException;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LENOVO
 */
public class homeuser extends javax.swing.JFrame {

    /**
    
    
     */
    public Statement st;
    public ResultSet rs;
    public Connection kon = konekdb.bukaKon();
    public double totallokasi;
    public static String ID;
    public static String Workshop;
    public static String lokasi;
    public static String Kuota;
    private static int userId = 1;
    public homeuser() {
        initComponents();
        setLocationRelativeTo(null);
        generateDaftarID();
        generateDaftarDate();
        generateDaftarCode("1");
        ShowDaftar();
        setLocationRelativeTo(null);
    }
    public void generateDaftarID() {
        try {
            st = kon.createStatement();
            rs = st.executeQuery("SELECT * FROM daftar WHERE id_daftar IN (SELECT MAX(id_daftar) FROM daftar)");

            // Mendapatkan tanggal, bulan, dan tahun saat ini
            Date daftarDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String tanggalBulanTahun = dateFormat.format(daftarDate).replaceAll("-", "");

            // Mendapatkan angka ID user dan meningkatkan counter
            String userID = null;

            // Menggabungkan semua komponen untuk membentuk ID pembelian sebagai integer
            String daftarID = null;

            if (rs.next()) {
                String lastdaftarID = rs.getString("id_daftar");
                int lastDaftarNumber = Integer.parseInt(lastdaftarID.substring(8));
                int newDaftarNumber = lastDaftarNumber + 1;
                userID = String.format("%03d", newDaftarNumber);
                daftarID = tanggalBulanTahun.substring(2, 8) + userID;
            } else {
                userID = String.format("%03d", userId);
                daftarID = tanggalBulanTahun.substring(2, 8) + userID;
            }

            txtID.setText(daftarID);
        } catch (SQLException ex) {
            Logger.getLogger(homeuser.class.getName()).log(Level.SEVERE, null, ex);
        }

    };
    
    public static void generateDaftarCode(String daftarKode) {

        // Mendapatkan tanggal, bulan, dan tahun saat ini
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateCode = dateFormat.format(currentDate).replaceAll("-", "");

        String lastID = txtID.getText().substring(8);
        int lastIDNumber = Integer.parseInt(lastID);

        String userID = userID = String.format("%03d", lastIDNumber);;

        // Menggabungkan semua komponen untuk membentuk ID pembelian sebagai integer
        String daftarID = daftarKode + userID + dateCode.substring(2, 8);

        txtKode.setText(daftarID);

    }
    
    public void generateDaftarDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String PurchaseDate = dateFormat.format(currentDate);

        txtTanggal.setText(PurchaseDate);
        txtTanggal.setEditable(false);
    }
    
    public void setTotallokasi(double totallokasi) {
        this.totallokasi = totallokasi;
    }

    public static String getID() {
        return ID;
    }

    public static void setID(String ID) {
        homeuser.ID = ID;
    }

    public static String getWorkshop() {
        return Workshop;
    }

    public static void setWorkshop(String workshop) {
        Workshop = workshop;
    }

    public static String getlokasi() {
        return lokasi;
    }

    public static void setlokasi(String lokasi) {
        homeuser.lokasi = lokasi;
    }

    public static String getKuota() {
        return Kuota;
    }

    public static void setKuota(String kuota) {
        Kuota = kuota;
    }
    
    private void ShowDaftar() {
        try {
            st = kon.createStatement();
            rs = st.executeQuery("SELECT * FROM daftar");

            DefaultTableModel model = new DefaultTableModel();

            model.addColumn("ID Daftar");
            model.addColumn("Kode Daftar");
            model.addColumn("Nama");
            model.addColumn("Email");
            model.addColumn("Workshop");
            model.addColumn("Tanggal");
            model.addColumn("Kuota");
            model.addColumn("lokasi");

            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            model.setRowCount(0);

            while (rs.next()) {
                Object[] data = {
                    rs.getString("id_daftar"),
                    rs.getString("kode"),
                    rs.getString("nama"),
                    rs.getString("email"),
                    rs.getString("jenis_workshop"),
                    rs.getString("tanggal"),
                    rs.getString("jumlah"),
                    rs.getString("lokasi"),
                };
                model.addRow(data);
                tabelhome.setModel(model);

            }

        } catch (Exception e) {
            Logger.getLogger(homeuser.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    private void findDaftar() {
        try {
            st = kon.createStatement();
            String searchQuery = "SELECT * FROM purchase WHERE id_daftar LIKE '%" + txtCari.getText()
                    + "%' OR kode LIKE '%" + txtCari.getText()
                    + "%' OR nama LIKE '%" + txtCari.getText()
                    + "%' OR email LIKE '%" + txtCari.getText()
                    + "%' OR jenis_workshop LIKE '%" + txtCari.getText()
                    + "%' OR tanggal LIKE '%" + txtCari.getText()
                    + "%' OR lokasi LIKE '%" + txtCari.getText()
                    + "%' OR status LIKE '%" + txtCari.getText() + "%'";
            rs = st.executeQuery(searchQuery);

            DefaultTableModel model = new DefaultTableModel();

            model.addColumn("ID Daftar");
            model.addColumn("Kode Daftar");
            model.addColumn("Nama");
            model.addColumn("Email");
            model.addColumn("Workshop");
            model.addColumn("Tanggal");
            model.addColumn("Kuota");
            model.addColumn("lokasi");

            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            model.setRowCount(0);

            while (rs.next()) {
                Object[] data = {
                    rs.getString("id_daftar"),
                    rs.getString("kode"),
                    rs.getString("nama"),
                    rs.getString("email"),
                    rs.getString("jenis_workshop"),
                    rs.getString("tanggal"),
                    rs.getString("jumlah"),
                    rs.getString("lokasi"),};
                model.addRow(data);
                tabelhome.setModel(model);

            }

        } catch (Exception e) {
            Logger.getLogger(homeuser.class.getName()).log(Level.SEVERE, null, e);

        }
    }
    
    public void clear() {
        txtID.setText("");
        txtKode.setText("");
        txtNama.setText("");
        txtEmail.setText("");
        txtWorkshop.setText("");
        txtJumlah.setText("");
        txtlokasi.setText("");

        generateDaftarID();
        generateDaftarCode("1");
        generateDaftarDate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtCari = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelhome = new javax.swing.JTable();
        txtID = new javax.swing.JTextField();
        txtTanggal = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        txtKode = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnSimpan = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtWorkshop = new javax.swing.JTextField();
        txtlokasi = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JTextField();
        btnPilih = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 500));

        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });

        jButton1.setText("Cari");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tabelhome.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tabelhome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelhomeMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelhome);

        txtID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDActionPerformed(evt);
            }
        });

        txtTanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTanggalActionPerformed(evt);
            }
        });

        txtNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaActionPerformed(evt);
            }
        });

        txtKode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodeActionPerformed(evt);
            }
        });

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        jLabel1.setText("ID");

        jLabel2.setText("Nama");

        jLabel3.setText("Tanggal");

        jLabel4.setText("Kode");

        jLabel5.setText("Email");

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        jButton3.setText("Ubah");

        jButton4.setText("Hapus");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("QR");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel6.setText("Workshop");

        txtWorkshop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtWorkshopActionPerformed(evt);
            }
        });

        txtlokasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtlokasiActionPerformed(evt);
            }
        });

        jLabel7.setText("Jumlah");

        jLabel8.setText("Lokasi");

        txtJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahActionPerformed(evt);
            }
        });

        btnPilih.setText("pilih");
        btnPilih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPilihActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 743, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(jButton1)))
                        .addGap(29, 29, 29))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtlokasi, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(52, 52, 52)
                                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                                    .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel5))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                        .addComponent(txtWorkshop)
                                        .addComponent(txtJumlah))))
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPilih, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addComponent(btnSimpan)
                .addGap(68, 68, 68)
                .addComponent(jButton3)
                .addGap(70, 70, 70)
                .addComponent(jButton4)
                .addGap(66, 66, 66)
                .addComponent(jButton5)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(txtEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(txtWorkshop)
                    .addComponent(btnPilih))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtlokasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(txtJumlah))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 831, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDActionPerformed

    private void txtTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTanggalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTanggalActionPerformed

    private void txtNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaActionPerformed

    private void txtKodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKodeActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:

            String currentDate = txtTanggal.getText();

            // Assuming currentDate is in the format "dd-MM-yyyy"
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = inputDateFormat.parse(currentDate);

            // Create a new SimpleDateFormat with Indonesian locale
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = outputDateFormat.format(date);
            System.out.println(formattedDate);

            st = kon.createStatement();
            String cekData = "SELECT * FROM daftar WHERE id_daftar = '" + txtID.getText() + "'";
            rs = st.executeQuery(cekData);

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "data sudah ada");

            } else {
                String addData = "INSERT INTO daftar VALUES ('" + txtID.getText()
                        + "','" + txtKode.getText()
                        + "','" + txtNama.getText()
                        + "','" + txtEmail.getText()
                        + "','" + txtWorkshop.getText()
                        + "','" + formattedDate
                        + "','" + txtlokasi.getText()
                        + "','" + txtJumlah.getText()
                        + "','" + "aktif"
                        + "')";
                st.executeUpdate(addData);

                String updateKuotaQuery = "UPDATE tabel_workshop SET kapasitas = kapasitas - 1 WHERE workshop_id = '" + getID() + "'";
                st.executeUpdate(updateKuotaQuery);

                userId++;
                generateDaftarID();
                generateDaftarCode("1");
                JOptionPane.showMessageDialog(null, "berhasil melakukan pendaftaran");
                clear();
                ShowDaftar();

            }
        } catch (SQLException ex) {
            Logger.getLogger(homeuser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(homeuser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void txtWorkshopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtWorkshopActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtWorkshopActionPerformed

    private void txtlokasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtlokasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtlokasiActionPerformed

    private void txtJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumlahActionPerformed

    private void btnPilihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPilihActionPerformed
        // TODO add your handling code here:
        new daftar().setVisible(true);
    }//GEN-LAST:event_btnPilihActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        try {
            ByteArrayOutputStream out = QRCode.from(txtKode.getText())
                    .to(ImageType.PNG).stream();
            String qr_name = txtKode.getText();
            String path = "src/barcode/";

            FileOutputStream fous = new FileOutputStream(new File(path + (qr_name + ".png")));
            fous.write(out.toByteArray());
            fous.flush();
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void tabelhomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelhomeMouseClicked
        // TODO add your handling code here:
        txtID.setText(tabelhome.getValueAt(tabelhome.getSelectedRow(), 0).toString());
        txtKode.setText(tabelhome.getValueAt(tabelhome.getSelectedRow(), 1).toString());
        txtNama.setText(tabelhome.getValueAt(tabelhome.getSelectedRow(), 2).toString());
        txtEmail.setText(tabelhome.getValueAt(tabelhome.getSelectedRow(), 3).toString());
        txtWorkshop.setText(tabelhome.getValueAt(tabelhome.getSelectedRow(), 4).toString());
        txtTanggal.setText(tabelhome.getValueAt(tabelhome.getSelectedRow(), 5).toString());
        txtJumlah.setText(tabelhome.getValueAt(tabelhome.getSelectedRow(), 6).toString());
        txtlokasi.setText(tabelhome.getValueAt(tabelhome.getSelectedRow(), 7).toString());
    }//GEN-LAST:event_tabelhomeMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
         if (txtID.getText().isEmpty()) {
    JOptionPane.showMessageDialog(this, "Silakan pilih data yang akan dihapus");
        } else {
    int jawab = JOptionPane.showConfirmDialog(null, "Data ini akan dihapus, lanjutkan?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (jawab == JOptionPane.YES_OPTION) {
        try {
            st = kon.createStatement();
            String deleteQuery = "DELETE FROM daftar WHERE id_daftar = '" + txtID.getText() + "'";
            int rowsAffected = st.executeUpdate(deleteQuery);

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus"); 
                
                DefaultTableModel model = (DefaultTableModel) tabelhome.getModel();
                int rowCount = model.getRowCount();
                for (int i = 0; i < rowCount; i++) {
                    if (model.getValueAt(i, 0).toString().equals(txtID.getText())) {
                        model.removeRow(i);
                        break; 
                    }
                }
                ShowDaftar();
                clear();
            } else {
                JOptionPane.showMessageDialog(null, "Tidak ada data yang dihapus");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menghapus data: " + e.getMessage());
        }
    }
}
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(addworkshop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(addworkshop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(addworkshop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(addworkshop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new homeuser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPilih;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelhome;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtEmail;
    private static javax.swing.JTextField txtID;
    public static javax.swing.JTextField txtJumlah;
    private static javax.swing.JTextField txtKode;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtTanggal;
    public static javax.swing.JTextField txtWorkshop;
    public static javax.swing.JTextField txtlokasi;
    // End of variables declaration//GEN-END:variables
}
