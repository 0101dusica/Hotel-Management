package hotel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.knowm.xchart.*;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.LegendPosition;

import java.awt.*;  
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;

public class Administrator extends Osoba implements ActionListener{
	private static JButton cenovnik, dodaj_recepcionera, dodaj_sobaricu, statistika, submit, vrati_se;
	private static String tip_osobe;
	private String selektovan_checkIn, selektovan_datum_od, selektovan_datum_do, selektovana_soba,selektovan_tip_sobe = null;
	private JLabel pol, strucna_sprema;
	private JComboBox pol_polje, strucna_sprema_polje;
	private static JTextField ime_polje, prezime_polje, datum_rodjenja_polje, telefon_polje, adresa_polje, staz_polje, korisnicko_ime_polje, lozinka_polje;
	
	public Administrator(){
		
	}
	public Administrator(String ime, String prezime, String pol, String datum_rodjenja, String telefon, String adresa, String korisnicko_ime, String lozinka) {
		super(ime, prezime, pol, datum_rodjenja, telefon, adresa, korisnicko_ime, lozinka);
		tip = "administrator";
	}
	
	public void dodajOsobu() {
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
        
        strucna_sprema = new JLabel();  
        strucna_sprema.setText("Nivo strucne spreme");
        newPanel.add(strucna_sprema);
           
        String strucna_sprema_izbor[] = {"osnovna skola", "srednja skola", "osnovne akademske studije", "master akademske studije", "specijalisticke akademske studije", "doktorske studije"};
        strucna_sprema_polje = new JComboBox(strucna_sprema_izbor);  
        strucna_sprema_polje.setBackground(Color.white);
        strucna_sprema_polje.setForeground(Color.DARK_GRAY);
        strucna_sprema_polje.setAlignmentX(Component.LEFT_ALIGNMENT);
        newPanel.add(strucna_sprema_polje);
        
        JLabel staz = new JLabel();  
        staz.setText("Staz");
        newPanel.add(staz);
           
        staz_polje = new JTextField(15);
        newPanel.add(staz_polje); 
        
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
          
        submit = new JButton("SUBMIT");;
        submit.setAlignmentX(Component.CENTER_ALIGNMENT);
        submit.setBackground(Color.white);
        submit.setForeground(Color.DARK_GRAY);
        submit.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ime_polje.getText().equals("") || prezime_polje.getText().equals("") || datum_rodjenja_polje.getText().equals("") || telefon_polje.getText().equals("") || adresa_polje.getText().equals("") || staz_polje.getText().equals("") || korisnicko_ime_polje.getText().equals("") || lozinka_polje.getText().equals("")) {
		 			   JOptionPane.showMessageDialog(frame, "Morate da popunite sva polja!");
		 		}else {
		 			if(tip_osobe == "recepcioner") {
		 				JOptionPane.showMessageDialog(frame, "Uspesno ste dodali novog Recepcionera u sitem!");
		 			}else {
		 				JOptionPane.showMessageDialog(frame, "Uspesno ste dodali novu Sobaricu u sitem!");
		 			}
		 			String data = (String) pol_polje.getItemAt(pol_polje.getSelectedIndex());  
		 	        pol.setText(data);
		 	        
		 	        String data1 = (String) strucna_sprema_polje.getItemAt(strucna_sprema_polje.getSelectedIndex());  
		 	        strucna_sprema.setText(data1);
		 			dodajOsobu_uFajl();//podaci koji su uneti u formu, unose se u txt dokumet
		 			frame.setVisible(false);
		 			prozorAdministratora();
		 		}
			}
        });
        
        panel_dugmad.add(submit);
        
        vrati_se = new JButton("Vrati se na predhodnu Stranicu!");
        vrati_se.setAlignmentX(Component.CENTER_ALIGNMENT);
        vrati_se.setBackground(Color.DARK_GRAY);
        vrati_se.setForeground(Color.white);
        vrati_se.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				prozorAdministratora();
			}
        });
        panel_dugmad.add(vrati_se);

        newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.PAGE_AXIS));
        panel_dugmad.setLayout(new GridLayout(0, 1, 10, 10));
		frame.add(newPanel, BorderLayout.NORTH);
		frame.add(panel_dugmad, BorderLayout.SOUTH);
        
        frame.setSize(400,500);
        frame.setTitle("Administratorski deo aplikacije");  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
        
		frame.setVisible(true);
        
	}

	public void dodajOsobu_uFajl() {
		int staz = Integer.parseInt(staz_polje.getText());
		String osoba_text;
		if(tip_osobe == "recepcioner") {
			Recepcioner osoba = new Recepcioner(ime_polje.getText(), prezime_polje.getText(), pol.getText(), datum_rodjenja_polje.getText(), telefon_polje.getText(), adresa_polje.getText(), korisnicko_ime_polje.getText(), lozinka_polje.getText(), strucna_sprema.getText(), staz);
			osoba_text = korisnicko_ime_polje.getText() + "|" + lozinka_polje.getText() + "|" + osoba.getTip() + "|" + ime_polje.getText() + "|" + prezime_polje.getText() + "|" + pol.getText() + "|" + datum_rodjenja_polje.getText() + "|" + telefon_polje.getText() + "|" + adresa_polje.getText() + "|" + strucna_sprema.getText() + "|" + staz + "|" + osoba.getPlata();
		}else{
			Sobarica osoba = new Sobarica(ime_polje.getText(), prezime_polje.getText(), pol.getText(), datum_rodjenja_polje.getText(), telefon_polje.getText(), adresa_polje.getText(), korisnicko_ime_polje.getText(), lozinka_polje.getText(), strucna_sprema.getText(), staz);
			osoba_text = korisnicko_ime_polje.getText() + "|" + lozinka_polje.getText() + "|" + osoba.getTip() + "|" + ime_polje.getText() + "|" + prezime_polje.getText() + "|" + pol.getText() + "|" + datum_rodjenja_polje.getText() + "|" + telefon_polje.getText() + "|" + adresa_polje.getText() + "|" + strucna_sprema.getText() + "|" + staz + "|" + osoba.getPlata() + "|" + osoba.getBroj_soba();
		}
		
		
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
				bw.write(osoba_text);
				bw.newLine();
	
			} catch (IOException e) {
				System.err.format("IOException: %s%n", e);
			}
			
			
	}
	
	public void obrisi_osobe() {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JPanel dugmad_panel = new JPanel();
		Osoba osoba = new Osoba();
		ArrayList<String> stavka = osoba.citaj_iz_fajla();
		
		String column[]={"Korisnicko Ime","Tip", "Ime", "Prezime"};
		DefaultTableModel model = new DefaultTableModel(column, 0);
		JTable table = new JTable(model); 
		model.addRow(column);
		
		for (int j = 0; j < stavka.size(); j = j+4) {//ovde promeniti uslov, ako se nalazi njegov username dodaj u tabelu
			String linija[] = {stavka.get(j), stavka.get(j+1), stavka.get(j+2), stavka.get(j+3)};
			model.addRow(linija);
		}
		
		if(model.getRowCount() == 0) {
			JLabel ciscenje = new JLabel();  
			ciscenje.setText("Nema podataka koji mogu da se prikazu!");
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
			
			JButton obrisi =new JButton("Obrisi korisnika"); 
			obrisi.setBounds(130,100,100, 200); 
			obrisi.setAlignmentX(Component.CENTER_ALIGNMENT);
			obrisi.setBackground(Color.white);
			obrisi.setForeground(Color.DARK_GRAY);
			dugmad_panel.add(obrisi);
			obrisi.addActionListener(new ActionListener() {
	        	
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.setVisible(false);

					System.out.println("ovo je sleektovano "+ selektovan_checkIn);
					osoba.obrisi_iz_fajla(selektovan_checkIn);
					prozorAdministratora();
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
				
				
				prozorAdministratora();
			}
			});
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		dugmad_panel.setLayout(new GridLayout(0, 1, 10, 10));
		frame.setTitle("Deo aplikacije za administratora");  
		frame.setSize(800,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel, BorderLayout.NORTH);
		frame.add(dugmad_panel, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true); 

	}
	
	public void dodaj_tip() {
		Cenovnik cenovnik = new Cenovnik();
		
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JPanel panel1 = new JPanel();
		JPanel dugmad_panel = new JPanel();
		
		JLabel naziv_tip = new JLabel("Unesite novi tip sobe: ");
		JTextField naziv_text = new JTextField();
		panel.add(naziv_tip);
		panel.add(naziv_text);
		
		JLabel dodatne_usluge_label = new JLabel("Dodatne usluge ", JLabel.LEFT);
        panel.add(dodatne_usluge_label);
        
        JCheckBox klima = new JCheckBox("Klima", false);
        JCheckBox nepusacka = new JCheckBox("Nepusacka", false);
        JCheckBox pusacka = new JCheckBox("Pusacka", false);
        JCheckBox tv = new JCheckBox("Tv", false);
        JCheckBox djakuzi = new JCheckBox("Djakuzi", false);
        JCheckBox spa = new JCheckBox("Spa Kupatilo", false);
        JCheckBox coffe = new JCheckBox("Coffe Aparat", false);
        JCheckBox ketl = new JCheckBox("Ketl", false);
        JCheckBox masina = new JCheckBox("Masina za pranje vesa", false);
        panel1.add(klima);
        panel1.add(nepusacka);
        panel1.add(pusacka);
        panel1.add(tv);
        panel1.add(djakuzi);
        panel1.add(spa);
        panel1.add(coffe);
        panel1.add(ketl);
        panel1.add(masina);
        
		JButton submit_1 =new JButton("Submit"); 
		submit_1.setBounds(130,100,100, 200); 
		submit_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		submit_1.setBackground(Color.DARK_GRAY);
		submit_1.setForeground(Color.white);
		dugmad_panel.add(submit_1);
		submit_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dodatne_usluge = "";
				if (klima.isSelected()) {
					if(dodatne_usluge.equals("")) {
						dodatne_usluge = "klima";
					}else {
			            dodatne_usluge = dodatne_usluge + ",klima";
					}
		        }
				if (nepusacka.isSelected()) {
					if(dodatne_usluge.equals("")) {
						dodatne_usluge = "nepusacka";
					}else {
			            dodatne_usluge = dodatne_usluge + ",nepusacka";
					}
		        }
				if (pusacka.isSelected()) {
					if(dodatne_usluge.equals("")) {
						dodatne_usluge = "pusacka";
					}else {
			            dodatne_usluge = dodatne_usluge + ",pusacka";
					}
		        }
				if (tv.isSelected()) {
					if(dodatne_usluge.equals("")) {
						dodatne_usluge = "tv";
					}else {
			            dodatne_usluge = dodatne_usluge + ",tv";
					}
		        }
				if (djakuzi.isSelected()) {
					if(dodatne_usluge.equals("")) {
						dodatne_usluge = "djakuzi";
					}else {
			            dodatne_usluge = dodatne_usluge + ",djakuzi";
					}
		        }
				if (spa.isSelected()) {
					if(dodatne_usluge.equals("")) {
						dodatne_usluge = "spa";
					}else {
			            dodatne_usluge = dodatne_usluge + ",spa";
					}
		        }
				if (coffe.isSelected()) {
					if(dodatne_usluge.equals("")) {
						dodatne_usluge = "coffe";
					}else {
			            dodatne_usluge = dodatne_usluge + ",coffe";
					}
		        }
				if (ketl.isSelected()) {
					if(dodatne_usluge.equals("")) {
						dodatne_usluge = "ketl";
					}else {
			            dodatne_usluge = dodatne_usluge + ",ketl";
					}
		        }
				if (masina.isSelected()) {
					if(dodatne_usluge.equals("")) {
						dodatne_usluge = "masina";
					}else {
			            dodatne_usluge = dodatne_usluge + ",masina";
					}
		        }
				String upis = naziv_text.getText() + "|" + dodatne_usluge;
				cenovnik.dodaj_dodatne_usluge_tipove_soba(upis, "tipovi_soba.txt");
				JOptionPane.showMessageDialog(frame, "Uspesno ste dodali novi Tip sobe!");
				frame.setVisible(false);
				tipovi_sobe();
			}
		});
		
		
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
				tipovi_sobe();
			}
			});
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel1.setLayout(new GridLayout(0, 3, 10, 10));
		dugmad_panel.setLayout(new GridLayout(0, 1, 10, 10));
		frame.setTitle("Deo aplikacije za administratora");  
		frame.setSize(600,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel, BorderLayout.NORTH);
		frame.add(panel1, BorderLayout.CENTER);
		frame.add(dugmad_panel, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	public void tipovi_sobe() {
		Cenovnik cenovnik = new Cenovnik();
		ArrayList<String> stavka = cenovnik.citanje_dodatnih_uluga_tipova_soba("tipovi_soba.txt");
		
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JPanel dugmad_panel = new JPanel();
		
		String column[]={"Tip Sobe", "Dodatne Usluge"};
		DefaultTableModel model = new DefaultTableModel(column, 0);
		JTable table = new JTable(model); 
		
		for (int j = 0; j < stavka.size(); j = j+2) {//ovde promeniti uslov, ako se nalazi njegov username dodaj u tabelu
			String linija[] = {stavka.get(j), stavka.get(j+1)};
			model.addRow(linija);
		}
		
		if(model.getRowCount() == 0) {
			JLabel ciscenje = new JLabel();  
			ciscenje.setText("Nema podataka koji mogu da se prikazu!");
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
			
			
			JButton obrisi =new JButton("Obrisi Tip Sobe"); 
			obrisi.setBounds(130,100,100, 200); 
			obrisi.setAlignmentX(Component.CENTER_ALIGNMENT);
			obrisi.setBackground(Color.white);
			obrisi.setForeground(Color.DARK_GRAY);
			dugmad_panel.add(obrisi);
			obrisi.addActionListener(new ActionListener() {
	        	
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.setVisible(false);

					cenovnik.obrisi_dodatne_usluge_tipove_soba(selektovan_checkIn, "tipovi_soba.txt");
					tipovi_sobe();
				}
	        });
			
		}
		
		JButton dodaj_tip =new JButton("Dodaj Tip Sobe"); 
		dodaj_tip.setBounds(130,100,100, 200); 
		dodaj_tip.setAlignmentX(Component.CENTER_ALIGNMENT);
		dodaj_tip.setBackground(Color.white);
		dodaj_tip.setForeground(Color.DARK_GRAY);
		dugmad_panel.add(dodaj_tip);
		dodaj_tip.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				dodaj_tip();
			}
        });
		
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
				prozorAdministratora();
			}
			});
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		dugmad_panel.setLayout(new GridLayout(0, 1, 10, 10));
		frame.setTitle("Deo aplikacije za administratora");  
		frame.setSize(800,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel, BorderLayout.NORTH);
		frame.add(dugmad_panel, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	public void dodaj_dodatnu_uslugu() {
		Cenovnik cenovnik = new Cenovnik();
		
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JPanel dugmad_panel = new JPanel();
		
		JLabel naziv_tip = new JLabel("Unesite novu dodatnu uslugu: ");
		JTextField naziv_text = new JTextField();
		panel.add(naziv_tip);
		panel.add(naziv_text);
		JButton submit_1 =new JButton("Submit"); 
		submit_1.setBounds(130,100,100, 200); 
		submit_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		submit_1.setBackground(Color.DARK_GRAY);
		submit_1.setForeground(Color.white);
		dugmad_panel.add(submit_1);
		submit_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cenovnik.dodaj_dodatne_usluge_tipove_soba(naziv_text.getText(), "dodatne_usluge.txt");
				JOptionPane.showMessageDialog(frame, "Uspesno ste dodali novu Dodatnu uslugu hotela!");
				frame.setVisible(false);
				dodatne_usluge();
			}
		});
		
		
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
				dodatne_usluge();
			}
			});
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		dugmad_panel.setLayout(new GridLayout(0, 1, 10, 10));
		frame.setTitle("Deo aplikacije za administratora");  
		frame.setSize(400,200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel, BorderLayout.NORTH);
		frame.add(dugmad_panel, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void dodatne_usluge() {
		Cenovnik cenovnik = new Cenovnik();
		ArrayList<String> stavka = cenovnik.citanje_dodatnih_uluga_tipova_soba("dodatne_usluge.txt");
		
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JPanel dugmad_panel = new JPanel();
		
		String column[]={"Dodatne Usluge"};
		DefaultTableModel model = new DefaultTableModel(column, 0);
		JTable table = new JTable(model); 
		
		for (int j = 0; j < stavka.size(); j++) {//ovde promeniti uslov, ako se nalazi njegov username dodaj u tabelu
			String linija[] = {stavka.get(j)};
			model.addRow(linija);
		}
		
		if(model.getRowCount() == 0) {
			JLabel ciscenje = new JLabel();  
			ciscenje.setText("Nema podataka koji mogu da se prikazu!");
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
			
			
			JButton obrisi =new JButton("Obrisi Dodatnu Uslugu"); 
			obrisi.setBounds(130,100,100, 200); 
			obrisi.setAlignmentX(Component.CENTER_ALIGNMENT);
			obrisi.setBackground(Color.white);
			obrisi.setForeground(Color.DARK_GRAY);
			dugmad_panel.add(obrisi);
			obrisi.addActionListener(new ActionListener() {
	        	
				@Override
				public void actionPerformed(ActionEvent e) {

					System.out.println("ovo je sleektovano "+ selektovan_checkIn);
					cenovnik.obrisi_dodatne_usluge_tipove_soba(selektovan_checkIn, "dodatne_usluge.txt");
					JOptionPane.showMessageDialog(frame, "Uspesno ste obrisali Dodatnu uslugu hotela!");
					frame.setVisible(false);
					dodatne_usluge();
				}
	        });
			
		}
		
		JButton dodaj_tip =new JButton("Dodaj Dodatnu Uslugu"); 
		dodaj_tip.setBounds(130,100,100, 200); 
		dodaj_tip.setAlignmentX(Component.CENTER_ALIGNMENT);
		dodaj_tip.setBackground(Color.white);
		dodaj_tip.setForeground(Color.DARK_GRAY);
		dugmad_panel.add(dodaj_tip);
		dodaj_tip.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				dodaj_dodatnu_uslugu();
			}
        });
		
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
				prozorAdministratora();
			}
			});
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		dugmad_panel.setLayout(new GridLayout(0, 1, 10, 10));
		frame.setTitle("Deo aplikacije za administratora");  
		frame.setSize(800,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel, BorderLayout.NORTH);
		frame.add(dugmad_panel, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void izmena_cenovnika() {
		Cenovnik cenovnik = new Cenovnik();
		ArrayList<String> stavka = cenovnik.citanje_iz_fajla();
		
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JPanel dugmad_panel = new JPanel();
		
		String column[]={"ID", "Naziv", "Cena"};
		DefaultTableModel model = new DefaultTableModel(column, 0);
		JTable table = new JTable(model);
		
		for (int j = 0; j < stavka.size(); j = j + 3) {//ovde promeniti uslov, ako se nalazi njegov username dodaj u tabelu
			if(stavka.get(j).equals("datum_od") == true) {
				String linija[] = {"Datumi:", stavka.get(j+1), stavka.get(j+3)};
				model.addRow(linija);
				j = j + 1;
			}else {
				String linija[] = {stavka.get(j), stavka.get(j+1), stavka.get(j+2)};
				model.addRow(linija);
			}
		}
		
		if(model.getRowCount() == 0) {
			JLabel ciscenje = new JLabel();  
			ciscenje.setText("Nema podataka koji mogu da se prikazu!");
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
		        		selektovan_datum_od = (String) table.getValueAt(row[i], 1);
		        		selektovan_datum_do = (String) table.getValueAt(row[i], 2);
		        	}   
		        }       
		    }); 
			

			JScrollPane js = new JScrollPane(table);
	        js.setVisible(true);
	        frame.add(js);
	        
			JButton obrisi =new JButton("Obrisi Cenovnik"); 
			obrisi.setBounds(130,100,100, 200); 
			obrisi.setAlignmentX(Component.CENTER_ALIGNMENT);
			obrisi.setBackground(Color.white);
			obrisi.setForeground(Color.DARK_GRAY);
			dugmad_panel.add(obrisi);
			obrisi.addActionListener(new ActionListener() {
	        	
				@Override
				public void actionPerformed(ActionEvent e) {

					JOptionPane.showMessageDialog(frame, "Uspesno ste obrisali Cenovnik!");

					cenovnik.obrisi_fajl(selektovan_datum_od, selektovan_datum_do);
					frame.setVisible(false);
					izmena_cenovnika();
				}
	        });
			
		}
		
		JButton dodaj_tip =new JButton("Dodaj Novi Cenovnik"); 
		dodaj_tip.setBounds(130,100,100, 200); 
		dodaj_tip.setAlignmentX(Component.CENTER_ALIGNMENT);
		dodaj_tip.setBackground(Color.white);
		dodaj_tip.setForeground(Color.DARK_GRAY);
		dugmad_panel.add(dodaj_tip);
		dodaj_tip.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				dodaj_cenovnik();
			}
        });
		
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
				prozorAdministratora();
			}
			});
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		dugmad_panel.setLayout(new GridLayout(0, 1, 10, 10));
		frame.setTitle("Deo aplikacije za administratora");  
		frame.setSize(800,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel, BorderLayout.NORTH);
		frame.add(dugmad_panel, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void dodaj_cenovnik() {
		Cenovnik cenovnik = new Cenovnik();
		ArrayList<String> cene = new ArrayList<String>();
		
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JPanel dugmad_panel = new JPanel();
		JPanel lista_panel = new JPanel();
		
		JLabel datum_od = new JLabel("Unesite datum pocetka vazenja Cenovnika: ");
		JTextField datum_od_text = new JTextField();
		panel.add(datum_od);
		panel.add(datum_od_text);
		
		JLabel datum_do = new JLabel("Unesite datum kraja vazenja Cenovnika: ");
		JTextField datum_do_text = new JTextField();
		panel.add(datum_do);
		panel.add(datum_do_text);
		
		ArrayList<String> tipovi_soba = cenovnik.citanje_dodatnih_uluga_tipova_soba("tipovi_soba.txt");
		ArrayList<String> dodatne_usluge = cenovnik.citanje_dodatnih_uluga_tipova_soba("dodatne_usluge.txt");
		
		for(int j = 0; j < tipovi_soba.size() ; j++) {
			JLabel tip_sobe = new JLabel("Unesite cenu za " + tipovi_soba.get(j));
			lista_panel.add(tip_sobe);
			JTextField tip_sobe_cena = new JTextField();
			lista_panel.add(tip_sobe_cena);
			JButton dodaj_tip =new JButton("submit");
			dodaj_tip.setBackground(Color.white);
			dodaj_tip.setForeground(Color.DARK_GRAY);
			lista_panel.add(dodaj_tip);
			dodaj_tip.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cene.add(tip_sobe_cena.getText());
				}
			});
		}
		
		for(int j = 0; j < dodatne_usluge.size() ; j++) {
			JLabel tip_sobe = new JLabel("Unesite cenu za " + dodatne_usluge.get(j));
			lista_panel.add(tip_sobe);
			JTextField tip_sobe_cena = new JTextField();
			lista_panel.add(tip_sobe_cena);
			JButton dodaj_tip =new JButton("submit");
			dodaj_tip.setBackground(Color.white);
			dodaj_tip.setForeground(Color.DARK_GRAY);
			lista_panel.add(dodaj_tip);
			dodaj_tip.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cene.add(tip_sobe_cena.getText());
				}
			});
		}
		
		
		JButton submit_1 =new JButton("Submit"); 
		submit_1.setBounds(130,100,100, 200); 
		submit_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		submit_1.setBackground(Color.DARK_GRAY);
		submit_1.setForeground(Color.white);
		dugmad_panel.add(submit_1);
		submit_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cenovnik.upis_u_fajl(cene, datum_od_text.getText(), datum_do_text.getText());
				JOptionPane.showMessageDialog(frame, "Uspesno ste dodali novu Dodatnu uslugu hotela!");
				frame.setVisible(false);
				izmena_cenovnika();
			}
		});
		
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
				izmena_cenovnika();
			}
			});
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		dugmad_panel.setLayout(new GridLayout(0, 1, 10, 10));
		lista_panel.setLayout(new BoxLayout(lista_panel, BoxLayout.PAGE_AXIS));

		frame.setTitle("Deo aplikacije za administratora");  
		frame.setSize(800,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel, BorderLayout.NORTH);
		

		frame.add(lista_panel, BorderLayout.CENTER);
		JScrollPane scrollPane = new JScrollPane(lista_panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        frame.add(scrollPane);
		frame.add(dugmad_panel, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void nove_sobe() {
		Soba soba = new Soba();
		ArrayList<String> stavka = soba.citanje_iz_fajla();
		
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JPanel dugmad_panel = new JPanel();
		
		String column[]={"Id Sobe", "Tip Sobe", "Status"};
		DefaultTableModel model = new DefaultTableModel(column, 0);
		JTable table = new JTable(model); 
		model.addRow(column);
		
		for (int j = 0; j < stavka.size(); j = j+3) {//ovde promeniti uslov, ako se nalazi njegov username dodaj u tabelu
			String linija[] = {stavka.get(j), stavka.get(j+1), stavka.get(j+2)};
			model.addRow(linija);
		}
		
		if(model.getRowCount() == 0) {
			JLabel ciscenje = new JLabel();  
			ciscenje.setText("Nema podataka koji mogu da se prikazu!");
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
		        		selektovana_soba = (String) table.getValueAt(row[i], 0);
		        	}   
		        }       
		    }); 
			
			
			JButton obrisi =new JButton("Obrisi Tip Sobe"); 
			obrisi.setBounds(130,100,100, 200); 
			obrisi.setAlignmentX(Component.CENTER_ALIGNMENT);
			obrisi.setBackground(Color.white);
			obrisi.setForeground(Color.DARK_GRAY);
			dugmad_panel.add(obrisi);
			obrisi.addActionListener(new ActionListener() {
	        	
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.setVisible(false);

					soba.obrisi(selektovana_soba);
					nove_sobe();
				}
	        });
			
		}
		
		JButton dodaj_tip =new JButton("Dodaj Novu Sobu"); 
		dodaj_tip.setBounds(130,100,100, 200); 
		dodaj_tip.setAlignmentX(Component.CENTER_ALIGNMENT);
		dodaj_tip.setBackground(Color.white);
		dodaj_tip.setForeground(Color.DARK_GRAY);
		dugmad_panel.add(dodaj_tip);
		dodaj_tip.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				dodaj_novu_sobu();
			}
        });
		
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
				prozorAdministratora();
			}
			});
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		dugmad_panel.setLayout(new GridLayout(0, 1, 10, 10));
		frame.setTitle("Deo aplikacije za administratora");  
		frame.setSize(800,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel, BorderLayout.NORTH);
		frame.add(dugmad_panel, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void dodaj_novu_sobu() {
		Soba soba = new Soba();
		ArrayList<String> stavka = soba.citanje_iz_fajla();
		ArrayList<String> upis = new ArrayList<String>();
		
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JPanel dugmad_panel = new JPanel();
		
		int id = Integer.valueOf(stavka.get(stavka.size()-3));
		upis.add(String.valueOf(id+1));
		
		JLabel tip_sobe = new JLabel("Tip sobe: ", JLabel.LEFT);
        panel.add(tip_sobe);

        Cenovnik cenovnik = new Cenovnik();
        ArrayList<String> stavka_1 = cenovnik.citanje_dodatnih_uluga_tipova_soba("tipovi_soba.txt");
        String column[]={"Tip Sobe", "Dodatne Usluge"};
		DefaultTableModel model = new DefaultTableModel(column, 0);
		JTable table = new JTable(model);
		
		for (int j = 0; j < stavka_1.size(); j = j + 2) {//ovde promeniti uslov, ako se nalazi njegov username dodaj u tabelu
			String linija[] = {stavka_1.get(j), stavka_1.get(j+1)};
			model.addRow(linija);
		}
		
		if(model.getRowCount() == 0) {
			JLabel ciscenje = new JLabel();  
			ciscenje.setText("Nema podataka koji mogu da se prikazu!");
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
		        		selektovan_tip_sobe = (String) table.getValueAt(row[i], 0);
		        	}   
		        }       
		    }); 
			

			JScrollPane js = new JScrollPane(table);
	        js.setVisible(true);
	        frame.add(js);
			
		}
		
		JButton submit_1 =new JButton("Submit"); 
		submit_1.setBounds(130,100,100, 200); 
		submit_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		submit_1.setBackground(Color.DARK_GRAY);
		submit_1.setForeground(Color.white);
		dugmad_panel.add(submit_1);
		submit_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				upis.add(selektovan_tip_sobe);
				upis.add("slobodna");

				soba.dodaj_novu_sobu(upis);
				JOptionPane.showMessageDialog(frame, "Uspesno ste dodali novu sobu!");
				frame.setVisible(false);
				nove_sobe();
			}
		});
		
		
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
				nove_sobe();
			}
			});
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		dugmad_panel.setLayout(new GridLayout(0, 1, 10, 10));
		frame.setTitle("Deo aplikacije za administratora");  
		frame.setSize(600,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel, BorderLayout.NORTH);
		frame.add(dugmad_panel, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void statistika() {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		
		JButton prihodi_rashodi =new JButton("Prikaz Prihoda i Rashoda"); 
		prihodi_rashodi.setAlignmentX(Component.CENTER_ALIGNMENT);
		prihodi_rashodi.setBackground(Color.white);
		prihodi_rashodi.setForeground(Color.DARK_GRAY);
		panel.add(prihodi_rashodi);
		prihodi_rashodi.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				prikaz_prihoda();
			}
			});
		
		JButton prikaz_soba =new JButton("Prikaz Prihoda po Sobama"); 
		prikaz_soba.setAlignmentX(Component.CENTER_ALIGNMENT);
		prikaz_soba.setBackground(Color.white);
		prikaz_soba.setForeground(Color.DARK_GRAY);
		panel.add(prikaz_soba);
		prikaz_soba.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				statistike_soba();
			}
			});
		
		JButton graficki_prikaz_soba =new JButton("Godisnji Prihodi po Sobama"); 
		graficki_prikaz_soba.setAlignmentX(Component.CENTER_ALIGNMENT);
		graficki_prikaz_soba.setBackground(Color.white);
		graficki_prikaz_soba.setForeground(Color.DARK_GRAY);
        panel.add(graficki_prikaz_soba);
        graficki_prikaz_soba.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				chart_tipovi_sobe();
			}
			});
		
		JButton graficki_prikaz_sobarica =new JButton("Pie Opterecenja Sobarica"); 
        graficki_prikaz_sobarica.setAlignmentX(Component.CENTER_ALIGNMENT);
        graficki_prikaz_sobarica.setBackground(Color.white);
        graficki_prikaz_sobarica.setForeground(Color.DARK_GRAY);
        panel.add(graficki_prikaz_sobarica);
		graficki_prikaz_sobarica.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				pie_sobarica();
			}
			});
		
		JButton graficki_prikaz_rezervacije =new JButton("Pie za Status rezervacija"); 
		graficki_prikaz_rezervacije.setAlignmentX(Component.CENTER_ALIGNMENT);
		graficki_prikaz_rezervacije.setBackground(Color.white);
		graficki_prikaz_rezervacije.setForeground(Color.DARK_GRAY);
		panel.add(graficki_prikaz_rezervacije);
		graficki_prikaz_rezervacije.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				pie_rezervacija();
			}
			});
		
		JButton odjavi_se =new JButton("Vratite se na predhodnu stranicu!"); 
		odjavi_se.setAlignmentX(Component.CENTER_ALIGNMENT);
		odjavi_se.setBackground(Color.DARK_GRAY);
		odjavi_se.setForeground(Color.white);
		panel.add(odjavi_se);
		odjavi_se.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				prozorAdministratora();
			}
			});

		panel.setLayout(new GridLayout(0, 1, 10, 10));
		frame.setTitle("Administratorski deo aplikacije");
		frame.setSize(400,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel, BorderLayout.CENTER);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void prikaz_prihoda() {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JPanel panel_dugmad = new JPanel();
		
		JLabel datum_od = new JLabel("Unesite datum od kog pretrazujete: ");
		panel.add(datum_od);
		JTextField datum_od_text = new JTextField();
		panel.add(datum_od_text);
		
		JLabel datum_do = new JLabel("Unesite datum od kog pretrazujete: ");
		panel.add(datum_do);
		JTextField datum_do_text = new JTextField();
		panel.add(datum_do_text);
		
		JButton sumbit =new JButton("Submit"); 
		sumbit.setAlignmentX(Component.CENTER_ALIGNMENT);
		sumbit.setBackground(Color.white);
		sumbit.setForeground(Color.DARK_GRAY);
		panel_dugmad.add(sumbit);
		sumbit.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] od = datum_od_text.getText().split("\\.");
				LocalDate od_datum_text = LocalDate.of(Integer.valueOf(od[2]), Integer.valueOf(od[1]), Integer.valueOf(od[0]));
				String[] do_text = datum_do_text.getText().split("\\.");
				LocalDate do_datum_text = LocalDate.of(Integer.valueOf(do_text[2]), Integer.valueOf(do_text[1]), Integer.valueOf(do_text[0]));
				
				Osoba osoba = new Osoba();
				ArrayList<String> sve_osobe = osoba.citaj_iz_fajla();
				ArrayList<String> sobarice = new ArrayList<String>();
				
				Rezervacija rezervacija = new Rezervacija();
				ArrayList<String> sve_rezervacije = rezervacija.citanje_iz_fajla();
				int odobrene = 0;
				int odbijene = 0;
				int otkazane = 0;
				int cekanje = 0;
				
				for(int k = 0; k < sve_rezervacije.size(); k = k + 8) {
					String[] text_od_fajl = sve_rezervacije.get(k+3).split("\\.");
					LocalDate text_od_fajl_datum = LocalDate.of(Integer.valueOf(text_od_fajl[2]), Integer.valueOf(text_od_fajl[1]), Integer.valueOf(text_od_fajl[0]));
					String[] text_do_fajl = sve_rezervacije.get(k+4).split("\\.");
					LocalDate text_do_fajl_datum = LocalDate.of(Integer.valueOf(text_do_fajl[2]), Integer.valueOf(text_do_fajl[1]), Integer.valueOf(text_do_fajl[0]));
					
					if((text_do_fajl_datum.isBefore(do_datum_text) == true || text_do_fajl_datum.isEqual(do_datum_text) == true) && (text_od_fajl_datum.isAfter(od_datum_text) == true || text_od_fajl_datum.isEqual(od_datum_text) == true)) {
						if(sve_rezervacije.get(k+2).equals("odobreno") == true) {
							odobrene++;
						}
						if(sve_rezervacije.get(k+2).equals("odbijeno") == true) {
							odbijene++;
						}
						if(sve_rezervacije.get(k+2).equals("otkazano") == true) {
							otkazane++;
						}
						if(sve_rezervacije.get(k+2).equals("na cekanju") == true) {
							cekanje++;
						}
					}
				}
				
				for(int k = 0; k < sve_osobe.size(); k = k + 4) {
					if(sve_osobe.get(k+1).equals("sobarica") == true) {
						sobarice.add(sve_osobe.get(k));
					}
				}
				
				String column1[]={"Status Rezervacije", "Broj rezervacija"};
				DefaultTableModel model1 = new DefaultTableModel(column1, 0);
				JTable table1 = new JTable(model1);
				model1.addRow(column1);
				
				String linija1[] = {"Odobrene", String.valueOf(odobrene)};
				String linija2[] = {"Odbijene", String.valueOf(odbijene)};
				String linija3[] = {"Otkazane", String.valueOf(otkazane)};
				String linija4[] = {"Na cekanju", String.valueOf(cekanje)};
				model1.addRow(linija1);
				model1.addRow(linija2);
				model1.addRow(linija3);
				model1.addRow(linija4);
				
				
				Sobarica sobarica = new Sobarica();
				
				String column[]={"Sobarica", "Broj Spremljenih Soba"};
				DefaultTableModel model = new DefaultTableModel(column, 0);
				model.addRow(column);
				JTable table = new JTable(model);
				for(int m = 0; m < sobarice.size(); m++) {
					ArrayList<String> stavke = sobarica.citanje_iz_fajla(sobarice.get(m));
					int count = 0;
					for(int j = 0; j < stavke.size(); j= j+3) {

						String[] text_od_fajl = stavke.get(j+2).split("\\.");
						LocalDate text_od_fajl_datum = LocalDate.of(Integer.valueOf(text_od_fajl[2]), Integer.valueOf(text_od_fajl[1]), Integer.valueOf(text_od_fajl[0]));
						String[] text_do_fajl = stavke.get(j+2).split("\\.");
						LocalDate text_do_fajl_datum = LocalDate.of(Integer.valueOf(text_do_fajl[2]), Integer.valueOf(text_do_fajl[1]), Integer.valueOf(text_do_fajl[0]));
						
						if((text_do_fajl_datum.isBefore(do_datum_text) == true || text_do_fajl_datum.isEqual(do_datum_text) == true) && (text_od_fajl_datum.isAfter(od_datum_text) == true || text_od_fajl_datum.isEqual(od_datum_text) == true)) {
							count++;
						}
					}
					
					String linija[] = {sobarice.get(m), String.valueOf(count)};
					model.addRow(linija);
				}

				TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model); 
				table.setRowSorter(sorter);

				TableRowSorter<DefaultTableModel> sorter1 = new TableRowSorter<DefaultTableModel>(model1); 
				table1.setRowSorter(sorter1);
				
				frame.setVisible(false);
				ispis_prihoda(table, table1, datum_od_text.getText(), datum_do_text.getText());
			}
			});
		

		JButton odjavi_se =new JButton("Vratite se na predhodnu stranicu!"); 
		odjavi_se.setAlignmentX(Component.CENTER_ALIGNMENT);
		odjavi_se.setBackground(Color.DARK_GRAY);
		odjavi_se.setForeground(Color.white);
		panel_dugmad.add(odjavi_se);
		odjavi_se.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				statistika();
			}
			});


		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel_dugmad.setLayout(new GridLayout(0, 1, 10, 10));
		frame.setTitle("Administratorski deo aplikacije");
		frame.setSize(400,200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel, BorderLayout.NORTH);
		frame.add(panel_dugmad, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void ispis_prihoda(JTable table, JTable table1, String datum_od_text, String datum_do_text) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();

		JPanel panel_rezervacije = new JPanel();
		JPanel panel_dugmad = new JPanel();
				
		JLabel sobarice = new JLabel("Podaci o Sobaricama");
		panel.add(sobarice);
		panel.add(table);
		
		JLabel rezervacije = new JLabel("Podaci o Rezervacijijama");
		panel_rezervacije.add(rezervacije);
		panel_rezervacije.add(table1);

		JScrollPane js = new JScrollPane();
        js.setVisible(true);
        frame.add(js);
        
        JLabel datumi_od = new JLabel("Datum od kog se pretrazuje: " + datum_od_text);
        datumi_od.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel_dugmad.add(datumi_od);
        
        JLabel datumi_do = new JLabel("Datum od kog se pretrazuje: " + datum_do_text);
        datumi_do.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel_dugmad.add(datumi_do);
		
		JButton odjavi_se =new JButton("Vratite se na predhodnu stranicu!"); 
		odjavi_se.setAlignmentX(Component.CENTER_ALIGNMENT);
		odjavi_se.setBackground(Color.DARK_GRAY);
		odjavi_se.setForeground(Color.white);
		panel_dugmad.add(odjavi_se);
		odjavi_se.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				prikaz_prihoda();
			}
			});


		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel_rezervacije.setLayout(new BoxLayout(panel_rezervacije, BoxLayout.PAGE_AXIS));
		panel_dugmad.setLayout(new GridLayout(0, 1, 10, 10));
		frame.setTitle("Administratorski deo aplikacije");
		frame.setSize(800,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel, BorderLayout.NORTH);
		frame.add(panel_rezervacije, BorderLayout.CENTER);
		frame.add(panel_dugmad, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void statistike_soba() {
		//prikaz id sobe, tipa sobe, ukupan broj nocenja u nekom opsegu datuma, ukupne prihode sobe
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JPanel panel_dugmad = new JPanel();
		
		JLabel datum_od = new JLabel("Unesite datum od kog pretrazujete: ");
		panel.add(datum_od);
		JTextField datum_od_text = new JTextField();
		panel.add(datum_od_text);
		
		JLabel datum_do = new JLabel("Unesite datum od kog pretrazujete: ");
		panel.add(datum_do);
		JTextField datum_do_text = new JTextField();
		panel.add(datum_do_text);
		
		JButton sumbit =new JButton("Submit"); 
		sumbit.setAlignmentX(Component.CENTER_ALIGNMENT);
		sumbit.setBackground(Color.white);
		sumbit.setForeground(Color.DARK_GRAY);
		panel_dugmad.add(sumbit);
		sumbit.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				Soba soba = new Soba();
				Cenovnik cenovnik = new Cenovnik();
				ArrayList<String> podaci = soba.citanje_iz_fajla();
				ArrayList<String> cenovnik_podaci = cenovnik.citanje_iz_fajla();
				
				ArrayList<String> id_soba = new ArrayList<String>();
				ArrayList<String> tipovi_soba = new ArrayList<String>();
				ArrayList<String> popunjenost_soba = new ArrayList<String>();
				ArrayList<String> zarada_sobe = new ArrayList<String>();
				
				for(int i = 0; i < podaci.size(); i = i+3) {
					id_soba.add(podaci.get(i));
					tipovi_soba.add(podaci.get(i+1));
				}
				
				for(int j = 0; j < id_soba.size(); j++) {
					int popunjenost = soba.citaj_popunjenost(datum_od_text.getText(), datum_do_text.getText(), id_soba.get(j));
					popunjenost_soba.add(String.valueOf(popunjenost));
				}
				
				for(int j = 0; j < popunjenost_soba.size(); j++) {
					Boolean provera = false;
					while( provera == false) {
					for(int m = 0; m < cenovnik_podaci.size(); m = m+3) {
						if(cenovnik_podaci.get(m).equals("datum_od") == true) {
							m++;
						}else {
							if(cenovnik_podaci.get(m+1).equals(tipovi_soba.get(j)) == true) {
								zarada_sobe.add(String.valueOf(Integer.valueOf(popunjenost_soba.get(j))*Integer.valueOf(cenovnik_podaci.get(m+2))));

								provera = true;
								break;
							}
							}
						}
					}
				}
				
				String column[]={"ID Sobe", "Tip Sobe", "Ukupan Broj Nocenja", "Zarada"};
				DefaultTableModel model = new DefaultTableModel(column, 0);
				JTable table = new JTable(model);
				model.addRow(column);
				for(int m = 0; m < id_soba.size(); m++) {
					String linija[] = {id_soba.get(m), tipovi_soba.get(m), popunjenost_soba.get(m), zarada_sobe.get(m)};
					model.addRow(linija);
				}

				TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model); 
				table.setRowSorter(sorter);

				frame.setVisible(false);
				prikazi_statistiku_soba(table, datum_od_text.getText(), datum_do_text.getText());
				
			}
		
		});
		

		JButton odjavi_se =new JButton("Vratite se na predhodnu stranicu!"); 
		odjavi_se.setAlignmentX(Component.CENTER_ALIGNMENT);
		odjavi_se.setBackground(Color.DARK_GRAY);
		odjavi_se.setForeground(Color.white);
		panel_dugmad.add(odjavi_se);
		odjavi_se.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				statistika();
			}
			});


		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel_dugmad.setLayout(new GridLayout(0, 1, 10, 10));
		frame.setTitle("Administratorski deo aplikacije");
		frame.setSize(400,200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel, BorderLayout.NORTH);
		frame.add(panel_dugmad, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void prikazi_statistiku_soba(JTable table, String datum_od_text, String datum_do_text) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();

		JPanel panel_dugmad = new JPanel();
				
		panel.add(table);
        
        JLabel datumi_od = new JLabel("Datum od kog se pretrazuje: " + datum_od_text);
        panel_dugmad.add(datumi_od);
        
        JLabel datumi_do = new JLabel("Datum od kog se pretrazuje: " + datum_do_text);
        panel_dugmad.add(datumi_do);
		
		JButton odjavi_se =new JButton("Vratite se na predhodnu stranicu!"); 
		odjavi_se.setAlignmentX(Component.CENTER_ALIGNMENT);
		odjavi_se.setBackground(Color.DARK_GRAY);
		odjavi_se.setForeground(Color.white);
		panel_dugmad.add(odjavi_se);
		odjavi_se.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				statistike_soba();
			}
			});


		JScrollPane js = new JScrollPane(table);
        js.setVisible(true);
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel_dugmad.setLayout(new GridLayout(0, 1, 10, 10));
		frame.setTitle("Administratorski deo aplikacije");
		frame.setSize(800,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel, BorderLayout.NORTH);

        frame.add(js);
		frame.add(panel_dugmad, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void pie_sobarica() {
		// Create Chart
		final PieChart chart = new PieChartBuilder().width(600).height(400).title("Prikaz Statusa rezervacija za poslednjih mesec dana").build();
		// Customize Chart
		chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
				
		
		Sobarica sobarica = new Sobarica();
		ArrayList<String> sobarice = new ArrayList<String>();

		Osoba osoba = new Osoba();
		ArrayList<String> sve_osobe = osoba.citaj_iz_fajla();
		
		for(int k = 0; k < sve_osobe.size(); k = k + 4) {
			if(sve_osobe.get(k+1).equals("sobarica") == true) {
				sobarice.add(sve_osobe.get(k));
			}
		}
		
		LocalDate datum_text = LocalDate.now();
		LocalDate nesto = datum_text.minusMonths(1);
		for(int m = 0; m < sobarice.size(); m++) {
			ArrayList<String> stavke = sobarica.citanje_iz_fajla(sobarice.get(m));
			int count = 0;
			for(int j = 0; j < stavke.size(); j= j+3) {

				String[] text_od_fajl = stavke.get(j+2).split("\\.");
				LocalDate text_od_fajl_datum = LocalDate.of(Integer.valueOf(text_od_fajl[2]), Integer.valueOf(text_od_fajl[1]), Integer.valueOf(text_od_fajl[0]));
				String[] text_do_fajl = stavke.get(j+2).split("\\.");
				LocalDate text_do_fajl_datum = LocalDate.of(Integer.valueOf(text_do_fajl[2]), Integer.valueOf(text_do_fajl[1]), Integer.valueOf(text_do_fajl[0]));
				

				if((text_do_fajl_datum.isBefore(datum_text) == true || text_do_fajl_datum.isEqual(datum_text) == true) && (text_od_fajl_datum.isAfter(nesto) == true || text_od_fajl_datum.isEqual(nesto) == true)) {
					count++;
				}
			}
			
			chart.addSeries(sobarice.get(m), count);
		}

		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

		  @Override
		  public void run() {

		    // Create and set up the window.
		    JFrame frame = new JFrame("Administratorski deo Aplikacije");
		    frame.setLayout(new BorderLayout());
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		    // chart
		    JPanel chartPanel = new XChartPanel<PieChart>(chart);
		    frame.add(chartPanel, BorderLayout.CENTER);

		    // label
		    JButton odjavi_se =new JButton("Vratite se na predhodnu stranicu!"); 
			odjavi_se.setAlignmentX(Component.CENTER_ALIGNMENT);
			odjavi_se.setBackground(Color.DARK_GRAY);
			odjavi_se.setForeground(Color.white);
			odjavi_se.addActionListener(new ActionListener() {
	        	
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.setVisible(false);
					statistika();
				}
				});
		    frame.add(odjavi_se, BorderLayout.SOUTH);

		    // Display the window.
		    frame.pack();
			frame.setLocationRelativeTo(null);
		    frame.setVisible(true);
		  }
		});
	}
	
	public void pie_rezervacija() {
		// Create Chart
		final PieChart chart = new PieChartBuilder().width(600).height(400).title("Prikaz Statusa rezervacija za poslednjih mesec dana").build();
		// Customize Chart
		chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
				
		
		Rezervacija rezervacija = new Rezervacija();
		ArrayList<String> sve_rezervacije = rezervacija.citanje_iz_fajla();
		int odobrene = 0;
		int odbijene = 0;
		int otkazane = 0;
		int cekanje = 0;
		
		LocalDate datum_text = LocalDate.now();
		LocalDate nesto = datum_text.minusMonths(1);
		
		for(int k = 0; k < sve_rezervacije.size(); k = k + 8) {
			
			String[] text_od_fajl = sve_rezervacije.get(k+3).split("\\.");
			LocalDate text_od_fajl_datum = LocalDate.of(Integer.valueOf(text_od_fajl[2]), Integer.valueOf(text_od_fajl[1]), Integer.valueOf(text_od_fajl[0]));
			String[] text_do_fajl = sve_rezervacije.get(k+4).split("\\.");
			LocalDate text_do_fajl_datum = LocalDate.of(Integer.valueOf(text_do_fajl[2]), Integer.valueOf(text_do_fajl[1]), Integer.valueOf(text_do_fajl[0]));
			
			if((text_do_fajl_datum.isBefore(datum_text) == true || text_do_fajl_datum.isEqual(datum_text) == true) && (text_od_fajl_datum.isAfter(nesto) == true || text_od_fajl_datum.isEqual(nesto) == true)) {
				if(sve_rezervacije.get(k+2).equals("odobreno") == true) {
					odobrene++;
				}
				if(sve_rezervacije.get(k+2).equals("odbijeno") == true) {
					odbijene++;
				}
				if(sve_rezervacije.get(k+2).equals("otkazano") == true) {
					otkazane++;
				}
				if(sve_rezervacije.get(k+2).equals("na cekanju") == true) {
					cekanje++;
				}
			}
		}

		chart.addSeries("odobreno", odobrene);
		chart.addSeries("odbijeno", odbijene);
		chart.addSeries("otkazano", otkazane);
		chart.addSeries("na cekanju", cekanje);

		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

		  @Override
		  public void run() {

		    // Create and set up the window.
		    JFrame frame = new JFrame("Administratorski deo Aplikacije");
		    frame.setLayout(new BorderLayout());
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		    // chart
		    JPanel chartPanel = new XChartPanel<PieChart>(chart);
		    frame.add(chartPanel, BorderLayout.CENTER);

		    // label
		    JButton odjavi_se =new JButton("Vratite se na predhodnu stranicu!"); 
			odjavi_se.setAlignmentX(Component.CENTER_ALIGNMENT);
			odjavi_se.setBackground(Color.DARK_GRAY);
			odjavi_se.setForeground(Color.white);
			odjavi_se.addActionListener(new ActionListener() {
	        	
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.setVisible(false);
					statistika();
				}
				});
		    frame.add(odjavi_se, BorderLayout.SOUTH);

		    // Display the window.
		    frame.pack();
			frame.setLocationRelativeTo(null);
		    frame.setVisible(true);
		  }
		});
	}
	
	public void chart_tipovi_sobe() {
		// Create Chart
		final XYChart chart = new XYChartBuilder().width(600).height(400).title("Prikaz prihoda po tipu sobe").xAxisTitle("mesec").yAxisTitle("dinari").build();
		// Customize Chart
		chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
		chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
		
		Soba soba = new Soba();
		Cenovnik cenovnik = new Cenovnik();
		ArrayList<String> podaci = soba.citanje_iz_fajla(); //id sobe|tip|status
		ArrayList<String> cenovnik_podaci = cenovnik.citanje_iz_fajla();
		ArrayList<String> tipovi_podaci = cenovnik.citanje_dodatnih_uluga_tipova_soba("tipovi_soba.txt");
		
		LocalDate datum_text = LocalDate.now().minusYears(1);
		ArrayList<String> meseci_graf = new ArrayList<String>();
		ArrayList<String> zarada_graf = new ArrayList<String>();
		ArrayList<String> ukupna_zarada= new ArrayList<String>();
		
		ArrayList<String> id_soba = new ArrayList<String>();
		ArrayList<String> tipovi_soba = new ArrayList<String>();
		for(int i = 0; i < podaci.size(); i = i+3) {
			id_soba.add(podaci.get(i));
			tipovi_soba.add(podaci.get(i+1));
		}
		
		for(int k = 0; k < 12; k++) {
			ukupna_zarada.clear();
			for(int i = 0; i < tipovi_podaci.size(); i= i+2) {
				ukupna_zarada.add("0");
			}

			ArrayList<String> popunjenost_soba = new ArrayList<String>();
			ArrayList<String> zarada_sobe = new ArrayList<String>();
			datum_text = datum_text.plusMonths(1);
			
			LocalDate nesto = datum_text.minusMonths(1);
			String datum_od_text = nesto.getDayOfMonth() + "." + nesto.getMonthValue() + "." + nesto.getYear();
			String datum_do_text = datum_text.getDayOfMonth() + "." + datum_text.getMonthValue() + "." + datum_text.getYear();
			
			meseci_graf.add(String.valueOf(datum_text.getMonthValue()));
						
			for(int j = 0; j < id_soba.size(); j++) {
				int popunjenost = soba.citaj_popunjenost(datum_od_text, datum_do_text, id_soba.get(j));
				popunjenost_soba.add(String.valueOf(popunjenost));
			}
			
			for(int j = 0; j < popunjenost_soba.size(); j++) {
				Boolean provera = false;
				while( provera == false) {
					for(int m = 0; m < cenovnik_podaci.size(); m = m+3) {
						if(cenovnik_podaci.get(m).equals("datum_od") == true) {
							m++;
						}else {
							if(cenovnik_podaci.get(m+1).equals(tipovi_soba.get(j)) == true) {
								zarada_sobe.add(String.valueOf(Integer.valueOf(popunjenost_soba.get(j))*Integer.valueOf(cenovnik_podaci.get(m+2))));
		
								provera = true;
								break;
							}
						}
					}
				}
			}

			for(int l = 0; l < tipovi_podaci.size(); l= l+2) {
				for(int m = 0; m < id_soba.size(); m++) {
					if(tipovi_soba.get(m).equals(tipovi_podaci.get(l)) == true) {
						int zarada = Integer.valueOf(ukupna_zarada.get(l/2)) + Integer.valueOf(zarada_sobe.get(m));
			        	ukupna_zarada.remove(l/2);
			        	ukupna_zarada.add(l/2, String.valueOf(zarada));
					}
				}
			}

			for(int c = 0; c<ukupna_zarada.size(); c++) {
				zarada_graf.add(ukupna_zarada.get(c));
			}
		}
		
		

		double ukupna_zarada_linija[] = {0,0,0,0,0,0,0,0,0,0,0,0};
		for(int m = 0; m < tipovi_podaci.size(); m= m+2) {//dodaje chart liniju za odredjeni tip sobe

        	System.out.println("OVO JE POCETAK ZA TIP SOBE " + tipovi_podaci.get(m));
			double mes[] = new double[12];
			double zar[] = new double[12];
			
			for(int l = 0; l < zar.length ; l++) { //vraca listu sa 12 podataka
				Boolean some = false;
				for(int s = 0; s < mes.length; s++){
					String mesec = String.valueOf(l+1);
					if(meseci_graf.get(s).equals(mesec) == true) {
						some = true;
						break;
					}			
				}
				
				while (some == true) {
		        	System.out.println("ja mu dodajem " + zarada_graf.get(l*(m/2)));
		        	ukupna_zarada_linija[l] = ukupna_zarada_linija[l] + Double.valueOf(zarada_graf.get(l*(m/2)));
					zar[l] = Double.parseDouble(zarada_graf.get(l*(m/2)));
					some = false;
				}
			}
			
			chart.addSeries(tipovi_podaci.get(m), new double[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}, zar);
			
		}

		chart.addSeries("ukupni prihodi", new double[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}, ukupna_zarada_linija);

		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

		  @Override
		  public void run() {

		    // Create and set up the window.
		    JFrame frame = new JFrame("Administratorski deo Aplikacije");
		    frame.setLayout(new BorderLayout());
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		    // chart
		    JPanel chartPanel = new XChartPanel<XYChart>(chart);
		    frame.add(chartPanel, BorderLayout.CENTER);

		    // label
		    JButton odjavi_se =new JButton("Vratite se na predhodnu stranicu!"); 
			odjavi_se.setAlignmentX(Component.CENTER_ALIGNMENT);
			odjavi_se.setBackground(Color.DARK_GRAY);
			odjavi_se.setForeground(Color.white);
			odjavi_se.addActionListener(new ActionListener() {
	        	
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.setVisible(false);
					statistika();
				}
				});
		    frame.add(odjavi_se, BorderLayout.SOUTH);

		    // Display the window.
		    frame.pack();
			frame.setLocationRelativeTo(null);
		    frame.setVisible(true);
		  }
		});
	}
	
	public void prozorAdministratora() {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		
		statistika =new JButton("Statistike Hotela"); 
		statistika.setAlignmentX(Component.CENTER_ALIGNMENT);
		statistika.setBackground(Color.DARK_GRAY);
		statistika.setForeground(Color.white);
		panel.add(statistika);
		statistika.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				statistika();
			}
			});
		
		dodaj_recepcionera =new JButton("Dodaj Recepcionera"); 
		dodaj_recepcionera.setAlignmentX(Component.CENTER_ALIGNMENT);
		dodaj_recepcionera.setBackground(Color.white);
		dodaj_recepcionera.setForeground(Color.DARK_GRAY);
		panel.add(dodaj_recepcionera);
		dodaj_recepcionera.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				tip_osobe = "recepcioner";
				dodajOsobu();
			}
			});
		
		dodaj_sobaricu =new JButton("Dodaj Sobaricu"); 
		dodaj_sobaricu.setAlignmentX(Component.CENTER_ALIGNMENT);
		dodaj_sobaricu.setBackground(Color.white);
		dodaj_sobaricu.setForeground(Color.DARK_GRAY);
		panel.add(dodaj_sobaricu);
		dodaj_sobaricu.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				tip_osobe = "sobarica";
				dodajOsobu();
			}
			});
		
		
		JButton obrisi_osobe =new JButton("Obrisi korisnika"); 
		obrisi_osobe.setAlignmentX(Component.CENTER_ALIGNMENT);
		obrisi_osobe.setBackground(Color.white);
		obrisi_osobe.setForeground(Color.DARK_GRAY);
		panel.add(obrisi_osobe);
		obrisi_osobe.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				obrisi_osobe();
			}
			});
		
		JButton dodaj_tip_sobe =new JButton("Izmeni tip sobe"); 
		dodaj_tip_sobe.setAlignmentX(Component.CENTER_ALIGNMENT);
		dodaj_tip_sobe.setBackground(Color.white);
		dodaj_tip_sobe.setForeground(Color.DARK_GRAY);
		panel.add(dodaj_tip_sobe);
		dodaj_tip_sobe.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				tipovi_sobe();
			}
			});
		
		JButton dodaj_sobe =new JButton("Izmeni Hotelske sobe"); 
		dodaj_sobe.setAlignmentX(Component.CENTER_ALIGNMENT);
		dodaj_sobe.setBackground(Color.white);
		dodaj_sobe.setForeground(Color.DARK_GRAY);
		panel.add(dodaj_sobe);
		dodaj_sobe.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				nove_sobe();
			}
			});
		
		JButton dodaj_dodatno =new JButton("Izmeni dodatne usluge hotela"); 
		dodaj_dodatno.setAlignmentX(Component.CENTER_ALIGNMENT);
		dodaj_dodatno.setBackground(Color.white);
		dodaj_dodatno.setForeground(Color.DARK_GRAY);
		panel.add(dodaj_dodatno);
		dodaj_dodatno.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				dodatne_usluge();
			}
			});
		
		cenovnik = new JButton("Izmeni cenovnik"); 
		cenovnik.setAlignmentX(Component.CENTER_ALIGNMENT);
		cenovnik.setBackground(Color.white);
		cenovnik.setForeground(Color.DARK_GRAY);
		panel.add(cenovnik);
		cenovnik.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				izmena_cenovnika();
			}
			});
		
		JButton odjavi_se =new JButton("Odjavite se sa Administratorskog naloga!"); 
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
		frame.setTitle("Administratorski deo aplikacije");
		frame.setSize(400,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel, BorderLayout.CENTER);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

	}

}
