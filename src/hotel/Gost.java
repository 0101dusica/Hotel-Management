package hotel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.RowFilter.Entry;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class Gost extends Osoba implements ActionListener{
	private static JButton prikaz_rezervacija, kreiraj_rezervaciju;

	private String selektovan_checkIn, selektovan_tip_sobe = null;
	
	
	public Gost(){
		
	}
	public Gost(String ime, String prezime, String pol, String datum_rodjenja, String telefon, String adresa, String korisnicko_ime, String lozinka) {
		super(ime, prezime, pol, datum_rodjenja, telefon, adresa, korisnicko_ime, lozinka);
		tip = "gost";
	}

	public void prikazi_rezervacije() {
		Rezervacija rezervacija = new Rezervacija();
		ArrayList<String> stavka = rezervacija.citanje_iz_fajla();
		int ukupna_cena = 0;
		
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JPanel dugmad_panel = new JPanel();
		
		String column[]={"ID","Status", "Pocetni Datum Rezervacije", "Krajnji Datum Rezervacije", "Cena Rezervacije", "Tip sobe", "Dodatne usluge"};
		DefaultTableModel model = new DefaultTableModel(column, 0);
		JTable table = new JTable(model); 
		model.addRow(column);
		
		for (int j = 0; j < stavka.size(); j = j+8) {//ovde promeniti uslov, ako se nalazi njegov username dodaj u tabelu
			if(stavka.get(j+1).equals(this.getKorisnicko_ime()) == true) {
				String linija[] = {stavka.get(j), stavka.get(j+2), stavka.get(j+3), stavka.get(j+4), stavka.get(j+5), stavka.get(j+6), stavka.get(j+7)};
				ukupna_cena = ukupna_cena + Integer.valueOf(stavka.get(j+5));
				model.addRow(linija);
			}
		}
		
		if(model.getRowCount() == 0) {
			JLabel ciscenje = new JLabel();  
			ciscenje.setText("Nemate napravljenih rezervacija koje mogu da se prikazu!");
			ciscenje.setAlignmentX(Component.CENTER_ALIGNMENT);
			panel.add(ciscenje);

		}else {

			table.setBounds(30,40,200,500);          
			panel.add(table);
			
			table.setCellSelectionEnabled(true);  
			ListSelectionModel select= table.getSelectionModel();
			select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			select.addListSelectionListener(new ListSelectionListener() {  
		        public void valueChanged(ListSelectionEvent e) {  
		        	int[] row = table.getSelectedRows();  
		        	for (int i = 0; i < row.length; i++) {
		        		selektovan_checkIn = (String) table.getValueAt(row[i], 0);
		        	}   
		        }       
		    }); 
			
			JLabel cena = new JLabel("Ukupni Troskovi vasih rezervacija: " + ukupna_cena);
			cena.setAlignmentX(Component.RIGHT_ALIGNMENT);
			cena.setForeground(Color.red);
			cena.setFont(null);
			panel.add(cena);
			
			JButton soba_dugme =new JButton("Otkazi rezervaciju"); 
			soba_dugme.setBounds(130,100,100, 200); 
			soba_dugme.setAlignmentX(Component.CENTER_ALIGNMENT);
			soba_dugme.setBackground(Color.white);
			soba_dugme.setForeground(Color.DARK_GRAY);
			dugmad_panel.add(soba_dugme);
			soba_dugme.addActionListener(new ActionListener() {
	        	
				@Override
				public void actionPerformed(ActionEvent e) {
					for (int j = 0; j < stavka.size(); j = j+8) {//ovde promeniti uslov, ako se nalazi njegov username dodaj u tabelu
						System.out.println(" ovo je id " + selektovan_checkIn + " ovo je  ovaj sto se menja id " + stavka.get(j));
						if(stavka.get(j).equals(selektovan_checkIn) == true) {
							if(stavka.get(j+2).equals("odbijeno") != true && stavka.get(j+2).equals("otkazano") != true) {
								frame.setVisible(false);
								rezervacija.menjaj_status(selektovan_checkIn, "otkazano", "");
								prozorGost();
							}else {
								JOptionPane.showMessageDialog(frame, "Nije moguce da otkazete rezervaciju sa ovim statusom!");
							}
						}
					}
				}
	        });
		}
		
		JButton odjavi_se =new JButton("Vratite se na predhodnu stranu!"); 
		odjavi_se.setBounds(130,100,100, 200); 
		odjavi_se.setAlignmentX(Component.CENTER_ALIGNMENT);
		odjavi_se.setBackground(Color.DARK_GRAY);
		odjavi_se.setForeground(Color.white);
		dugmad_panel.add(odjavi_se);
		odjavi_se.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				prozorGost();
			}
			});
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		dugmad_panel.setLayout(new GridLayout(0, 1, 10, 10));
		frame.setTitle("Deo aplikacije za goste");  
		frame.setSize(800,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel, BorderLayout.NORTH);
		frame.add(dugmad_panel, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true); 
		
	}
	
	public void kreiraj_rezervaciju() {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JPanel panel1 = new JPanel();
		JPanel panel_dodatne = new JPanel();
		String korisnicko_ime = this.getKorisnicko_ime();

		JPanel panel_dugmad = new JPanel();

		JLabel od_datum = new JLabel("Pocetni datum rezervacije: ", JLabel.LEFT); 
        panel.add(od_datum);
           
        JTextField od_datum_polje = new JTextField(15);
        panel.add(od_datum_polje); 
        
        JLabel do_datum = new JLabel("Krajnji datum rezervacije: ", JLabel.LEFT);
        panel.add(do_datum);
           
        JTextField do_datum_polje = new JTextField(15);
        panel.add(do_datum_polje); 
        
        JLabel tip_sobe = new JLabel("Tip sobe: ", JLabel.LEFT);
        panel.add(tip_sobe);

        Cenovnik cenovnik = new Cenovnik();
        ArrayList<String> stavka = cenovnik.citanje_dodatnih_uluga_tipova_soba("tipovi_soba.txt");
        String column[]={"Tip Sobe", "Dodatne Usluge"};
		DefaultTableModel model = new DefaultTableModel(column, 0);
		JTable table = new JTable(model);
		
		for (int j = 0; j < stavka.size(); j = j + 2) {//ovde promeniti uslov, ako se nalazi njegov username dodaj u tabelu
			String linija[] = {stavka.get(j), stavka.get(j+1)};
			model.addRow(linija);
		}
		
		if(model.getRowCount() == 0) {
			JLabel ciscenje = new JLabel();  
			ciscenje.setText("Nema podataka koji mogu da se prikazu!");
			ciscenje.setAlignmentX(Component.CENTER_ALIGNMENT);
			panel1.add(ciscenje);

		}else {

			table.setBounds(30,40,200,500);          
			panel1.add(table);
			 
			
			table.setCellSelectionEnabled(true);  
			ListSelectionModel select= table.getSelectionModel();
			select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			select.addListSelectionListener(new ListSelectionListener() {  
		        public void valueChanged(ListSelectionEvent e) {  
		        	int[] row = table.getSelectedRows();  
		        	for (int i = 0; i < row.length; i++) {
		        		selektovan_tip_sobe = (String) table.getValueAt(row[i], 0);
		        	}   
		        }       
		    }); 
			

			JScrollPane js = new JScrollPane(table);
	        js.setVisible(true);
	        frame.add(js);
	        
	        
			TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model); 
			table.setRowSorter(sorter);
	        JLabel pretraga = new JLabel("Pretrazite po dodatnim kriterijumima: ");
			panel1.add(pretraga);
			JTextField pretraga_text = new JTextField();
			panel1.add(pretraga_text);
			
			JButton pretraga_dugme = new JButton("Submit");
			panel1.add(pretraga_dugme);
			pretraga_dugme.addActionListener(new ActionListener() {
	        	
				@Override
				public void actionPerformed(ActionEvent e) {
					sorter.setRowFilter(new RowFilter() {

						@Override
						public boolean include(Entry entry) {
							// TODO Auto-generated method stub
							if(entry.getStringValue(0).indexOf(pretraga_text.getText()) >= 0) {
								return entry.getStringValue(0).indexOf(pretraga_text.getText()) >= 0;
							}
							if(entry.getStringValue(1).indexOf(pretraga_text.getText()) >= 0) {
								return entry.getStringValue(1).indexOf(pretraga_text.getText()) >= 0;
							}
							return false;
						}
						
						
					});
				}
	        });
			
		}
        
        JLabel dodatne_usluge_label = new JLabel("Dodatne usluge: ", JLabel.LEFT);
        panel_dodatne.add(dodatne_usluge_label);
        
        ArrayList<String> dod = cenovnik.citanje_dodatnih_uluga_tipova_soba("dodatne_usluge.txt");
        ArrayList<JCheckBox> dodatne_usluge_box = new ArrayList<JCheckBox>();
        for(int k = 0; k < dod.size() ; k++) {
            JCheckBox dodaj_box = new JCheckBox(dod.get(k), false);
        	dodatne_usluge_box.add(dodaj_box);
        	panel_dodatne.add(dodaj_box);
        }


        panel_dugmad.add(panel_dodatne);
        
        JButton submit = new JButton("SUBMIT");
        submit.setAlignmentX(Component.CENTER_ALIGNMENT);
        submit.setBackground(Color.white);
        submit.setForeground(Color.DARK_GRAY);
        submit.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(od_datum_polje.getText().equals("") && do_datum_polje.getText().equals("")) {
		 			   JOptionPane.showMessageDialog(frame, "Morate da popunite sva polja!");
		 		}else {
		 			Rezervacija rezervacija;
					try {
						String dodatne_usluge = "";
						for(int m = 0; m < dodatne_usluge_box.size() ; m++) {
							if(dodatne_usluge_box.get(m).isSelected() == true) {
								if(dodatne_usluge.equals("")) {
									dodatne_usluge = dod.get(m);
								}else {
						            dodatne_usluge = dodatne_usluge + "," + dod.get(m);
								}
							}
						}
						
						rezervacija = new Rezervacija(korisnicko_ime, od_datum_polje.getText(), do_datum_polje.getText(), selektovan_tip_sobe, "na cekanju", dodatne_usluge);
						rezervacija.izracunaj_cenu(selektovan_tip_sobe, dodatne_usluge, od_datum_polje.getText(), do_datum_polje.getText());
						rezervacija.upis_u_fajl();
			 			JOptionPane.showMessageDialog(frame, "Uspesno ste napravili novu rezervaciju!");

						frame.setVisible(false);
			 			prozorGost();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
			 			JOptionPane.showMessageDialog(frame, "Nije moguce izvrsiti rezervaciju do kraja!");
					}
		 		}
			}
        });
        panel_dugmad.add(submit);
        
        JButton vrati_se = new JButton("Vrati se na Predhodnu stranicu!");
        vrati_se.setAlignmentX(Component.CENTER_ALIGNMENT);
        vrati_se.setBackground(Color.DARK_GRAY);
        vrati_se.setForeground(Color.white);
        vrati_se.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				prozorGost();
			}
			});
        panel_dugmad.add(vrati_se);
        
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel1.setLayout(new GridLayout(8, 1, 10, 10));
        panel_dodatne.setLayout(new BoxLayout(panel_dodatne, BoxLayout.LINE_AXIS));
        panel_dugmad.setLayout(new GridLayout(0, 1, 10, 10));
		frame.add(panel, BorderLayout.NORTH);
		frame.add(panel1, BorderLayout.EAST);
		frame.add(panel_dugmad, BorderLayout.PAGE_END);
        frame.setSize(800,500);
        frame.setTitle("Deo aplikacije za Gosta");  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setLocationRelativeTo(null);
        
		frame.setVisible(true);
	}
	
	public void prozorGost() {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		
		prikaz_rezervacija = new JButton("Prikaz svih Rezervacija");
		prikaz_rezervacija.setBounds(130,100,100, 200); 
		prikaz_rezervacija.setAlignmentX(Component.CENTER_ALIGNMENT);
		prikaz_rezervacija.setBackground(Color.white);
		prikaz_rezervacija.setForeground(Color.DARK_GRAY);
		panel.add(prikaz_rezervacija);
		prikaz_rezervacija.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				prikazi_rezervacije();
			}
        });
		
		kreiraj_rezervaciju = new JButton("Kreiraj Novu Rezervaciju");
		kreiraj_rezervaciju.setBounds(130,100,100, 200); 
		kreiraj_rezervaciju.setAlignmentX(Component.CENTER_ALIGNMENT);
		kreiraj_rezervaciju.setBackground(Color.white);
		kreiraj_rezervaciju.setForeground(Color.DARK_GRAY);
		panel.add(kreiraj_rezervaciju);
		kreiraj_rezervaciju.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				kreiraj_rezervaciju();
			}
        });
		
		JButton odjavi_se =new JButton("Odjavite se sa naloga!"); 
		odjavi_se.setBounds(130,100,100, 200); 
		odjavi_se.setAlignmentX(Component.CENTER_ALIGNMENT);
		odjavi_se.setBackground(Color.DARK_GRAY);
		odjavi_se.setForeground(Color.white);
		panel.add(odjavi_se);
		odjavi_se.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				App.logIn();
			}
			});
		
		panel.setLayout(new GridLayout(0, 1, 10, 10));
		frame.add(panel, BorderLayout.CENTER);
		
		frame.setTitle("Deo aplikacije za Goste");  
		frame.setSize(400,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == kreiraj_rezervaciju) {
			   System.out.println("kreiraj rezervacije");
		}
		
	}
}
