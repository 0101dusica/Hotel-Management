package hotel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

public class Recepcioner extends Osoba implements ActionListener{
	private String strucna_sprema;
	private int staz;
	private int plata;
	private JComboBox pol_polje;
	private JLabel pol;
	private String selektovan_checkIn, selektovan_tip_sobe, selekt_od, selekt_do = null; 
	private static JButton submit, vrati_se, dodaj_gosta, checkIn, pregledajRezervacije;
	private static JTextField ime_polje, prezime_polje, datum_rodjenja_polje, telefon_polje, adresa_polje, korisnicko_ime_polje, lozinka_polje;
	
	
	public Recepcioner(){
		
	}
	public Recepcioner(String ime, String prezime, String pol, String datum_rodjenja, String telefon, String adresa, String korisnicko_ime, String lozinka, String strucna_sprema, int staz) {
		super(ime, prezime, pol, datum_rodjenja, telefon, adresa, korisnicko_ime, lozinka);
		tip = "recepcioner";
		this.strucna_sprema = strucna_sprema;
		this.staz = staz;
		double osnovna_plata = 50000;
		if(strucna_sprema.equals("osnovna skola") == true) {
			this.plata = (int) (osnovna_plata*(0.5) + staz*2000);//strucna sprema nosi odredjeni koeficijent na platu, a staz po godini povecava platu za 2000
		}else if(strucna_sprema.equals("srednja skola") == true) {
			this.plata = (int) (osnovna_plata*(1) + staz*2000);
		}else if(strucna_sprema.equals("osnovne akademske studije") == true) {
			this.plata = (int) (osnovna_plata*(1.5) + staz*2000);
		}else if(strucna_sprema.equals("master akademske studije") == true) {
			this.plata = (int) (osnovna_plata*(2) + staz*2000);
		}else if(strucna_sprema.equals("specijalisticke akademske studije") == true) {
			this.plata = (int) (osnovna_plata*(2.5) + staz*2000);
		}else if(strucna_sprema.equals("doktorske studije") == true) {
			this.plata = (int) (osnovna_plata*(3) + staz*2000);
		}
	}
	
	public void dodajGosta() {
		JFrame frame = new JFrame();
		JPanel newPanel = new JPanel();

		JPanel panel_dugmad = new JPanel();
		
		JLabel ime = new JLabel();  
        ime.setText("Ime");
        newPanel.add(ime);
           
        ime_polje = new JTextField(15);
        newPanel.add(ime_polje); 
        
        JLabel prezime = new JLabel();  
        prezime.setText("Prezime");
        newPanel.add(prezime);
           
        prezime_polje = new JTextField(15);
        newPanel.add(prezime_polje);
        
        pol = new JLabel();  
        pol.setText("Pol");
        newPanel.add(pol);
        
        String tipovi_soba[] = {"muski", "zenski", "ne bih se izjasnjavao"};
        pol_polje = new JComboBox(tipovi_soba);  
        pol_polje.setBackground(Color.white);
        pol_polje.setForeground(Color.DARK_GRAY);
        pol_polje.setAlignmentX(Component.LEFT_ALIGNMENT);
        newPanel.add(pol_polje);
        
        JLabel datum_rodjenja = new JLabel();  
        datum_rodjenja.setText("Datum rodjenja");
        newPanel.add(datum_rodjenja);
           
        datum_rodjenja_polje = new JTextField(15);
        newPanel.add(datum_rodjenja_polje);
        
        JLabel telefon = new JLabel();  
        telefon.setText("Telefon");
        newPanel.add(telefon);
           
        telefon_polje = new JTextField(15);
        newPanel.add(telefon_polje); 
        
        JLabel adresa = new JLabel();  
        adresa.setText("Adresa");
        newPanel.add(adresa);
           
        adresa_polje = new JTextField(15);
        newPanel.add(adresa_polje);
        
		JLabel korisnicko_ime = new JLabel();  
		korisnicko_ime.setText("Korisnicko ime");
        newPanel.add(korisnicko_ime);
           
        korisnicko_ime_polje = new JTextField(15);
        newPanel.add(korisnicko_ime_polje); 
  
        JLabel lozinka = new JLabel();  
        lozinka.setText("Lozinka");
        newPanel.add(lozinka);
           
        lozinka_polje = new JTextField(15); 
        newPanel.add(lozinka_polje);
          
        submit = new JButton("SUBMIT");
        submit.setAlignmentX(Component.CENTER_ALIGNMENT);
        submit.setBackground(Color.white);
        submit.setForeground(Color.DARK_GRAY);
        submit.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ime_polje.getText().equals("") || prezime_polje.getText().equals("") || datum_rodjenja_polje.getText().equals("") || telefon_polje.getText().equals("") || adresa_polje.getText().equals("") || korisnicko_ime_polje.getText().equals("") || lozinka_polje.getText().equals("")) {
		 			   JOptionPane.showMessageDialog(frame, "Morate da popunite sva polja!");
		 		}else {
		 			JOptionPane.showMessageDialog(frame, "Uspesno ste dodali novog Gosta u sitem!");
		 			String data = (String) pol_polje.getItemAt(pol_polje.getSelectedIndex());  
		 	        pol.setText(data);
		 			dodajGosta_uFajl();

					frame.setVisible(false);
		 			prozorRecepcioner();
		 		}
			}
        });
        panel_dugmad.add(submit);
        
        vrati_se = new JButton("Vrati se na Predhodnu stranicu!");
        vrati_se.setAlignmentX(Component.CENTER_ALIGNMENT);
        vrati_se.setBackground(Color.DARK_GRAY);
        vrati_se.setForeground(Color.white);
        vrati_se.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				prozorRecepcioner();
			}
			});
        panel_dugmad.add(vrati_se);
        
        newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.PAGE_AXIS));
        panel_dugmad.setLayout(new GridLayout(0, 1, 10, 10));
		frame.add(newPanel, BorderLayout.NORTH);
		frame.add(panel_dugmad, BorderLayout.SOUTH);
        frame.setSize(400,400);
        frame.setTitle("Recepcionerski deo aplikacije");  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setLocationRelativeTo(null);
        
		frame.setVisible(true);
	}
	
	public void dodajGosta_uFajl() {
		Gost gost = new Gost(ime_polje.getText(), prezime_polje.getText(), pol.getText(), datum_rodjenja_polje.getText(), telefon_polje.getText(), adresa_polje.getText(), korisnicko_ime_polje.getText(), lozinka_polje.getText());
		String gost_text;
		gost_text = korisnicko_ime_polje.getText() + "|" + lozinka_polje.getText() + "|" + gost.getTip() + "|" + ime_polje.getText() + "|" + prezime_polje.getText() + "|" + pol.getText() + "|" + datum_rodjenja_polje.getText() + "|" + telefon_polje.getText() + "|" + adresa_polje.getText();
		
		
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = Files.newBufferedReader(Paths.get("osobe.txt"))) {
	
	        // read line by line
	        String line;
	        while ((line = br.readLine()) != null) {
	            sb.append(line).append("\n");
	        }
	
	        } catch (IOException e) {
	            System.err.format("IOException: %s%n", e);
	        }
			
			try (FileWriter writer = new FileWriter("osobe.txt");
				 BufferedWriter bw = new BufferedWriter(writer)) {
				
				bw.write(sb.toString());
				bw.write(gost_text);
				bw.newLine();
	
			} catch (IOException e) {
				System.err.format("IOException: %s%n", e);
			}
		
		
}

	public void checkIn(){
		Soba soba = new Soba();
		ArrayList<String> stavka = soba.citanje_iz_fajla();
		
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JPanel panel1 = new JPanel();
		JPanel panel_dugmad = new JPanel();
		
		JLabel rez = new JLabel("Lista Aktivnih rezervacija za danasnji datum!");
		rez.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel1.add(rez);
		
		String column1[]={"ID","Username Gosta","Datum Pocetka Rezervacije","Datum Kraja Rezervacije", "Tip Sobe", "ID Sobe"};
		DefaultTableModel model1 = new DefaultTableModel(column1, 0);
		JTable table1 = new JTable(model1); 
		model1.addRow(column1);
		
		Rezervacija rezervacija = new Rezervacija();
		ArrayList<String> stavke_rezervacije = rezervacija.citanje_iz_fajla_sa_sobom();
		for (int j = 0; j < stavke_rezervacije.size(); j = j+8) {
			String[] text_od_fajl = stavke_rezervacije.get(j+3).split("\\.");
			LocalDate text_od_fajl_datum = LocalDate.of(Integer.valueOf(text_od_fajl[2]), Integer.valueOf(text_od_fajl[1]), Integer.valueOf(text_od_fajl[0]));
			String[] text_do_fajl = stavke_rezervacije.get(j+4).split("\\.");
			LocalDate text_do_fajl_datum = LocalDate.of(Integer.valueOf(text_do_fajl[2]), Integer.valueOf(text_do_fajl[1]), Integer.valueOf(text_do_fajl[0]));
			LocalDate datum_text = LocalDate.now();
			if((text_do_fajl_datum.isAfter(datum_text) == true || text_do_fajl_datum.isEqual(datum_text) == true) && (text_od_fajl_datum.isBefore(datum_text) == true || text_od_fajl_datum.isEqual(datum_text) == true)) {
				
				if(stavke_rezervacije.get(j+2).equals("odobreno") == true) {
					String linija[] = {stavke_rezervacije.get(j), stavke_rezervacije.get(j+1), stavke_rezervacije.get(j+3), stavke_rezervacije.get(j+4), stavke_rezervacije.get(j+6), stavke_rezervacije.get(j+7)};
					model1.addRow(linija);
				}
				if(stavke_rezervacije.get(j+2).equals("na cekanju") == true) {
					rezervacija.menjaj_status(stavke_rezervacije.get(j), "odbijeno", "null");
				}
			}
		}
		
		if(model1.getRowCount() > 1) {
			table1.setBounds(30,40,200,500);          
			panel1.add(table1);
		}else {
			JLabel rezervacije_prazno = new JLabel("Za danasnji datum ne postoji ni jedna rezervacija!");
			panel1.add(rezervacije_prazno);
		}
		

		JLabel r = new JLabel("Lista Hotelskih Soba");
		r.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(r);
		
		String column[]={"ID","Tip Sobe","Status"};
		DefaultTableModel model = new DefaultTableModel(column, 0);
		JTable table = new JTable(model); 
		
		for (int j = 0; j < stavka.size(); j = j+3) {
			String linija[] = {stavka.get(j), stavka.get(j+1), stavka.get(j+2)};
			model.addRow(linija);
		}
		
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
		table.setBounds(30,40,200,500);
		panel.add(table);   
		JScrollPane js = new JScrollPane(table);
        js.setVisible(true);
		
		JButton uradi_checkIn =new JButton("Uradi CheckIn"); 
		uradi_checkIn.setBounds(130,100,100, 200); 
		panel_dugmad.add(uradi_checkIn);
		uradi_checkIn.setAlignmentX(Component.CENTER_ALIGNMENT);
		uradi_checkIn.setBackground(Color.white);
		uradi_checkIn.setForeground(Color.DARK_GRAY);
		uradi_checkIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(selektovan_checkIn == null) {
		 			   JOptionPane.showMessageDialog(frame, "Morate da selektujete sobu u tabeli za kou zelite da uradite check in!");
		 		}else{
		 			int checkIn = Integer.parseInt(selektovan_checkIn);
		 			
		 			if(stavka.get(3*(checkIn-1)+2).equals("slobodna") == true) {
		 				//promeni u zauzeta ali i u fajlu
		 				stavka.set(3*(checkIn-1)+2, "zauzeta");
		 				soba.upisi_u_fajl(stavka);
		 				JOptionPane.showMessageDialog(frame, "Uspesno ste uradili check in za sobu koja ima ID: "+checkIn +" !");
		 				frame.setVisible(false);
		 				checkIn();
		 			}else {
		 				JOptionPane.showMessageDialog(frame, "Nije moguce uraditi check in za izabranu sobu, njen status nije slobodan!");
		 			}
		 			
		 		}
			}
        });
		
		JButton uradi_checkOut =new JButton("Uradi CheckOut"); 
		uradi_checkOut.setBounds(130,100,100, 200); 
		panel_dugmad.add(uradi_checkOut);
		uradi_checkOut.setAlignmentX(Component.CENTER_ALIGNMENT);
		uradi_checkOut.setBackground(Color.white);
		uradi_checkOut.setForeground(Color.DARK_GRAY);
		uradi_checkOut.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(selektovan_checkIn == null) {
		 			   JOptionPane.showMessageDialog(frame, "Morate da selektujete sobu u tabeli za kou zelite da uradite check out!");
		 		}else{
		 			int checkIn = Integer.parseInt(selektovan_checkIn);
		 			
		 			if(stavka.get(3*(checkIn-1)+2).equals("zauzeta") == true) {
		 				//promeni u zauzeta ali i u fajlu
		 				stavka.set(3*(checkIn-1)+2, "spremanje");
		 				soba.upisi_u_fajl(stavka);
		 				JOptionPane.showMessageDialog(frame, "Uspesno ste uradili check out za sobu koja ima ID: "+checkIn +" !");
		 				Sobarica sobarica = new Sobarica();
		 				sobarica.upis_u_fajl_posao(checkIn);
		 				frame.setVisible(false);
		 				checkIn();
		 			}else {
		 				JOptionPane.showMessageDialog(frame, "Nije moguce uraditi check out za izabranu sobu! Status je pogresan!");
		 			}
		 			
		 		}
			}
        });
		
		vrati_se = new JButton("Vrati se na Predhodnu stranicu!");
        vrati_se.setAlignmentX(Component.CENTER_ALIGNMENT);
        vrati_se.setBackground(Color.DARK_GRAY);
        vrati_se.setForeground(Color.white);
        vrati_se.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				prozorRecepcioner();
			}
			});
        panel_dugmad.add(vrati_se);
        
		
		frame.setTitle("Recepcionerski deo aplikacije");  
		frame.setSize(800,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		panel_dugmad.setLayout(new GridLayout(0, 1, 10, 10));
		
		frame.add(panel1, BorderLayout.NORTH);
		
		frame.add(panel, BorderLayout.CENTER);

        frame.add(js);
		frame.add(panel_dugmad, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true); 
		
		
	}
	
	public void pregledaj_rezervacije() {
		Rezervacija rezervacija = new Rezervacija();
		ArrayList<String> stavka = rezervacija.citanje_iz_fajla();
		
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JPanel panel1 = new JPanel();
		JPanel dugmad_panel = new JPanel();
		
		String column[]={"ID","Korisnicko Ime", "Pocetni Datum Rezervacije", "Krajnji Datum Rezervacije", "Cena Rezervacije", "Tip sobe", "Dodatne usluge"};
		DefaultTableModel model = new DefaultTableModel(column, 0);
		JTable table = new JTable(model);
		
		for (int j = 0; j < stavka.size(); j = j+8) {//ovde promeniti uslov, ako se nalazi njegov username dodaj u tabelu
			if(stavka.get(j+2).equals("na cekanju") == true) {
				String linija[] = {stavka.get(j), stavka.get(j+1), stavka.get(j+3), stavka.get(j+4), stavka.get(j+5), stavka.get(j+6), stavka.get(j+7)};
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
			TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model); 
			table.setRowSorter(sorter);
			
			table.setCellSelectionEnabled(true);  
			ListSelectionModel select= table.getSelectionModel();
			select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			select.addListSelectionListener(new ListSelectionListener() {  
		        public void valueChanged(ListSelectionEvent e) {  
		        	int[] row = table.getSelectedRows();  
		        	for (int i = 0; i < row.length; i++) {
		        		selektovan_checkIn = (String) table.getValueAt(row[i], 0);
		        		selektovan_tip_sobe = (String) table.getValueAt(row[i], 5);
		        		selekt_od = (String) table.getValueAt(row[i], 2);
		        		selekt_do = (String) table.getValueAt(row[i], 3);
		        	}   
		        }       
		    }); 
			
		       
			panel.add(table);
			
			JLabel pretraga = new JLabel("Pretrazite po kriterijumu: ");
			panel1.add(pretraga);
			JTextField pretraga_text = new JTextField();
			panel1.add(pretraga_text);
			
			JButton pretraga_dugme = new JButton("Submit");
			pretraga_dugme.setSize(new Dimension(70, 24));
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
							if(entry.getStringValue(2).indexOf(pretraga_text.getText()) >= 0) {
								return entry.getStringValue(2).indexOf(pretraga_text.getText()) >= 0;
							}
							if(entry.getStringValue(3).indexOf(pretraga_text.getText()) >= 0) {
								return entry.getStringValue(3).indexOf(pretraga_text.getText()) >= 0;
							}
							if(entry.getStringValue(4).indexOf(pretraga_text.getText()) >= 0) {
								return entry.getStringValue(4).indexOf(pretraga_text.getText()) >= 0;
							}
							if(entry.getStringValue(5).indexOf(pretraga_text.getText()) >= 0) {
								return entry.getStringValue(5).indexOf(pretraga_text.getText()) >= 0;
							}
							if(entry.getStringValue(6).indexOf(pretraga_text.getText()) >= 0) {
								return entry.getStringValue(6).indexOf(pretraga_text.getText()) >= 0;
							}
							return false;
						}
						
						
					});
				}
	        });
			
			JButton odobri_rezervaciju =new JButton("Odobri Rezervaciju"); 
			odobri_rezervaciju.setBounds(130,100,100, 200); 
			odobri_rezervaciju.setAlignmentX(Component.CENTER_ALIGNMENT);
			odobri_rezervaciju.setBackground(Color.white);
			odobri_rezervaciju.setForeground(Color.DARK_GRAY);
			dugmad_panel.add(odobri_rezervaciju);
			odobri_rezervaciju.addActionListener(new ActionListener() {
	        	
				@Override
				public void actionPerformed(ActionEvent e) {
					//dodati i id sobe na str[7] u rezervacije.txt, prvo treba prozor da iskoci i da dopusti da se izaberu
					Soba soba = new Soba();
					String soba_id = "";

					ArrayList<String> sve_sobe = soba.citanje_iz_fajla();
					
					Boolean p = false;
					int count = 0;
					for (int m = 0; m< sve_sobe.size() ; m = m+3) {
						if(p == false) {
						System.out.println(" ovo je selektovano "+ selektovan_tip_sobe + " ovo je soba koju mecujem " + sve_sobe.get(m+1));
						if(sve_sobe.get(m+1).equals(selektovan_tip_sobe) == true) {
							System.out.println(" joj tu sam i id je " + sve_sobe.get(m));
							
							Boolean proveri = soba.citaj_zauzetost(selekt_od, selekt_do, sve_sobe.get(m));
							if(proveri == true) {
								soba_id = sve_sobe.get(m);
								rezervacija.menjaj_status(selektovan_checkIn, "odobreno", soba_id);
								count++;
								JOptionPane.showMessageDialog(frame, "Rezervacija je uspesno odobrena!");
								frame.setVisible(false);
								prozorRecepcioner();

								p = true;
							}
						}
					}}
					
					if(count == 0) {
						JOptionPane.showMessageDialog(frame, "Nije moguce odobriti rezervaciju sa ovim opsegom datuma!");

						frame.setVisible(false);
						pregledaj_rezervacije();
					}

				}
	        });
			
			JButton otkazi_rezervaciju =new JButton("Odbij Rezervaciju"); 
			otkazi_rezervaciju.setBounds(130,100,100, 200); 
			otkazi_rezervaciju.setAlignmentX(Component.CENTER_ALIGNMENT);
			otkazi_rezervaciju.setBackground(Color.white);
			otkazi_rezervaciju.setForeground(Color.DARK_GRAY);
			dugmad_panel.add(otkazi_rezervaciju);
			otkazi_rezervaciju.addActionListener(new ActionListener() {
	        	
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.setVisible(false);
					rezervacija.menjaj_status(selektovan_checkIn, "odbijeno", "");
					prozorRecepcioner();
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
				prozorRecepcioner();
			}
		});
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel1.setLayout(new GridLayout(10, 1, 10, 10));
		dugmad_panel.setLayout(new GridLayout(0, 1, 10, 10));
		frame.setTitle("Deo aplikacije za Recepcionera");  
		frame.setSize(800,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JScrollPane js = new JScrollPane(table);
        js.setVisible(true);
        frame.add(js);
		frame.add(panel, BorderLayout.NORTH);
		frame.add(panel1, BorderLayout.EAST);
		frame.add(dugmad_panel, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true); 
		
	}	
	
	public void prozorRecepcioner() {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		          
		dodaj_gosta =new JButton("Dodaj Gosta"); 
		dodaj_gosta.setBounds(130,100,100, 200); 
		panel.add(dodaj_gosta);
		dodaj_gosta.setAlignmentX(Component.CENTER_ALIGNMENT);
		dodaj_gosta.setBackground(Color.white);
		dodaj_gosta.setForeground(Color.DARK_GRAY);
		dodaj_gosta.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				dodajGosta();
			}
			});
		
		checkIn = new JButton("Check In/Check Out"); 
		checkIn.setBounds(130,100,100, 200);
		panel.add(checkIn);
		checkIn.setAlignmentX(Component.CENTER_ALIGNMENT);
		checkIn.setBackground(Color.white);
		checkIn.setForeground(Color.DARK_GRAY);
		checkIn.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				checkIn();
			}
			});
		
		pregledajRezervacije = new JButton("Pregledajte sve Rezervacije");
		pregledajRezervacije.setBounds(130,100,100,200);
		pregledajRezervacije.setAlignmentX(Component.CENTER_ALIGNMENT);
		pregledajRezervacije.setBackground(Color.white);
		pregledajRezervacije.setForeground(Color.DARK_GRAY);
		panel.add(pregledajRezervacije);
		pregledajRezervacije.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				pregledaj_rezervacije();
			}
			});
		
		JButton odjavi_se =new JButton("Odjavite se sa naloga!"); 
		odjavi_se.setBounds(130,100,100, 200);
		odjavi_se.setAlignmentX(Component.CENTER_ALIGNMENT);
		odjavi_se.setBackground(Color.DARK_GRAY);
	    odjavi_se.setForeground(Color.white);
		//odjavi_se.text
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
		frame.setTitle("Recepcionerski deo aplikacije");  
		frame.setSize(400,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setLocationRelativeTo(null);
		frame.add(panel);
		frame.setVisible(true); 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	
	public String getStrucna_sprema() {
		return strucna_sprema;
	}
	public void setStrucna_sprema(String strucna_sprema) {
		this.strucna_sprema = strucna_sprema;
	}
	public int getStaz() {
		return staz;
	}
	public void setStaz(int staz) {
		this.staz = staz;
	}
	public int getPlata() {
		return plata;
	}
	public void setPlata(int plata) {
		this.plata = plata;
	}
}
