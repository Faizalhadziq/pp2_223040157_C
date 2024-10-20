package pertemuan3;

import java.awt.event.*;
import javax.swing.*;

public class Biodata extends JFrame{
    private boolean checkBoxSelected;

    public Biodata(){
        this.checkBoxSelected = false;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Label Nama
        JLabel labelInput = new JLabel("Nama:");
        labelInput.setBounds(50, 20, 100, 20);

        // Input Nama
        JTextField textField = new JTextField();
        textField.setBounds(150, 20, 150, 20);

        // Label Nomor HP
        JLabel labelInput2 = new JLabel("Nomor HP:");
        labelInput2.setBounds(50, 60, 100, 20);

        // Input Nomor HP
        JTextField textField2 = new JTextField();
        textField2.setBounds(150, 60, 150, 20);

        // Label Jenis Kelamin
        JLabel labelRadio = new JLabel("Jenis Kelamin:");
        labelRadio.setBounds(50, 100, 100, 20);

        // Radio button Laki-Laki
        JRadioButton radioButton1 = new JRadioButton("Laki-Laki", true);
        radioButton1.setBounds(150, 100, 100, 20); 

        // Radio button Perempuan
        JRadioButton radioButton2 = new JRadioButton("Perempuan");
        radioButton2.setBounds(250, 100, 100, 20); 

        // Grouping radio buttons
        ButtonGroup bg = new ButtonGroup();
        bg.add(radioButton1);
        bg.add(radioButton2);

        // Checkbox Warga Negara Asing
        JCheckBox checkBox = new JCheckBox("Warga Negara Asing");
        checkBox.setBounds(150, 140, 200, 20); 

        // Tombol Simpan
        JButton button = new JButton("Simpan");
        button.setBounds(150, 180, 100, 30);

        // Output area
        JTextArea txtOutput = new JTextArea("");
        txtOutput.setBounds(50, 230, 250, 100);

        // Event listener untuk checkbox
        checkBox.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                checkBoxSelected = e.getStateChange()==1;
            }
        }); 

        // Event listener untuk tombol Simpan
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String jenisKelamin = "";
                if(radioButton1.isSelected()){
                    jenisKelamin = radioButton1.getText();
                }
                if(radioButton2.isSelected()){
                    jenisKelamin = radioButton2.getText();
                }

                String nama = textField.getText();
                String nomor = textField2.getText();
                String wnaStatus = checkBox.isSelected() ? "Ya" : "Tidak";

                txtOutput.setText(""); // Clear output
                txtOutput.append("Nama: "+nama+"\n");
                txtOutput.append("Nomor HP: "+nomor+"\n");
                txtOutput.append("Jenis Kelamin: "+jenisKelamin+"\n");
                txtOutput.append("WNA: "+wnaStatus+"\n");
                
                // Clear input fields
                textField.setText("");
                textField2.setText("");
            }
        });

        // Add components ke frame
        this.add(labelInput);
        this.add(textField);
        this.add(labelInput2);
        this.add(textField2);
        this.add(labelRadio);
        this.add(radioButton1);
        this.add(radioButton2);
        this.add(checkBox);
        this.add(button);
        this.add(txtOutput);

        // Set ukuran frame dan layout
        this.setSize(400, 400);
        this.setLayout(null);
    }

     public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Biodata h = new Biodata();
                h.setVisible(true);
            }
        });
    }
}

