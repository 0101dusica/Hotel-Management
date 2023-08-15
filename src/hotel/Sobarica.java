package hotel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class Sobarica extends Osoba implements ActionListener{
	private String strucna_sprema;
	private int staz;
	private int plata;
	private int broj_soba;
	private String selektovan_checkIn = null;
	
	public Sobarica(){
		
	}
	public Sobarica(String ime, String prezime, String pol, String datum_rodjenja, String telefon, String adresa, String korisnicko_ime, String lozinka, String strucna_sprema, int staz) {
		super(ime, prezime, pol, datum_rodjenja, telefon, adresa, korisnicko_ime, lozinka);
		tip = "sobarica";
		this.setBroj_soba(0);
		
		this.strucna_sprema = strucna_sprema;
		this.staz = staz;
		double osnovna_plata = 40000;
		if(strucna_sprema.equals("osnovna skola") == true) {
			this.plata = (int) (osnovna_plata*(0.5) + staz*1000);//strucna sprema nosi odredjeni koeficijent na platu, a staz po godini povecava platu za 2000
		}else if(strucna_sprema.equals("srednja skola") == true) {
			this.plata = (int) (osnovna_plata*(1) + staz*1000);
		}else if(strucna_sprema.equals("osnovne akademske studije") == true) {
			this.plata = (int) (osnovna_plata*(1.5) + staz*1000);
		}else if(strucna_sprema.equals("master akademske studije") == true) {
			this.plata = (int) (osnovna_plata*(2) + staz*1000);
		}else if(strucna_sprema.equals("specijalisticke akademske studije") == true) {
			this.plata = (int) (osnovna_plata*(2.5) + staz*1000);
		}else if(strucna_sprema.equals("doktorske studije") == true) {
			this.plata = (int) (osnovna_plata*(3) + staz*1000);
		}
	}
	
	public String min_broj_soba() {
		ArrayList<String> sobarice = new ArrayList<String>();
		String kor_ime = "";
		int minimum;
		String linija_za_brisanje = "";
		try {
			BufferedReader reader1;
			reader1 = new BufferedReader(new FileReader("osobe.txt"));
			ArrayList<String> line = new ArrayList<String>();
			line.add(reader1.readLine());
			while (line.get(line.size()-1) != null) {
				line.add(reader1.readLine()); //prolazi kroz fajl sobe.txt i u line dodaje jednu po jednu liniju, koja ce kasnije biti parsovana
			}
			line.remove(line.size()-1); // na ovom mestu je ushvacen null i stavljen u niz
			
			for (int j = 0; j < line.size(); j++) {
				String linija = line.get(j);
				String[] prim = linija.split("\\|");
				if(prim[2].equals("sobarica") == true) {
					sobarice.add(prim[0]);
					sobarice.add(prim[prim.length-1]);
				}
			}

			if(sobarice.size() == 2) {
				minimum = Integer.parseInt(sobarice.get(1));
				kor_ime = sobarice.get(0);
			}else {
				minimum = Integer.parseInt(sobarice.get(1));
				kor_ime = sobarice.get(0);
				
				for(int i = 0 ; i < sobarice.size() ; i = i+2) {
					if(minimum > Integer.parseInt(sobarice.get(i+1))) {
						minimum = Integer.parseInt(sobarice.get(i+1));
						kor_ime = sobarice.get(i);
					}
				}
			}
			
			
			for (int j = 0; j < line.size(); j++) {
				String linija = line.get(j);
				String[] prim = linija.split("\\|");
				if(prim[0].equals(kor_ime) == true) {
					linija_za_brisanje = linija;
				}
			}
			
			PrintWriter writer = new PrintWriter("osobe.txt");
			for (int l = 0; l< line.size(); l++) {
				if(line.get(l).equals(linija_za_brisanje) == false) {
					writer.print(line.get(l));
					writer.println();
				}else {
					String min = String.valueOf(minimum);
					String novi_min = String.valueOf(minimum+1);
					String ponovo_upis = linija_za_brisanje.substring(0, linija_za_brisanje.length()-min.length());
					ponovo_upis = ponovo_upis + novi_min;
					writer.print(ponovo_upis);
					writer.println();
				}
			}
			
			writer.close();
			
			reader1.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return kor_ime;
	}
	
	public void upis_u_fajl_posao(int id_sobe) {
		String kor_ime = min_broj_soba();
		String status = "spremanje";
		LocalDate datum_od_date = LocalDate.now(); 
		String datum_od = datum_od_date.getDayOfMonth() + "." + datum_od_date.getMonth().getValue() + "." + datum_od_date.getYear();
		String datum_do = "none";
		
		String upis = kor_ime + "|" + id_sobe + "|" + status + "|" + datum_od + "|" + datum_do;
		//username_sobarice|id_sobe|status|datum_od|datum_do
		
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = Files.newBufferedReader(Paths.get("posao_sobe.txt"))) {
	
	        String line;
	        while ((line = br.readLine()) != null) {
	            sb.append(line).append("\n");
	        }
	
	        } catch (IOException e) {
	            System.err.format("IOException: %s%n", e);
	        }
			
			try (FileWriter writer = new FileWriter("posao_sobe.txt");
				 BufferedWriter bw = new BufferedWriter(writer)) {
				
				bw.write(sb.toString());
				bw.write(upis);
				bw.newLine();
	
			} catch (IOException e) {
				System.err.format("IOException: %s%n", e);
			}
		
		
	}
	
	public ArrayList<String> citanje_iz_fajla(String kor_ime) {
		ArrayList<String> stavka = new ArrayList<String>();
		try {
			BufferedReader reader1;
			reader1 = new BufferedReader(new FileReader("posao_sobe.txt"));
			ArrayList<String> line = new ArrayList<String>();
			line.add(reader1.readLine());
			while (line.get(line.size()-1) != null) {
				line.add(reader1.readLine()); //prolazi kroz fajl sobe.txt i u line dodaje jednu po jednu liniju, koja ce kasnije biti parsovana
			}
			line.remove(line.size()-1); // na ovom mestu je ushvacen null i stavljen u niz
			
			for (int j = 0; j < line.size(); j++) {
				String linija = line.get(j);
				String[] prim = linija.split("\\|");
				if(prim[0].equals(kor_ime) == true) {
					stavka.add(prim[1]);
					stavka.add(prim[2]);
					stavka.add(prim[3]);
					//id, status, datum zaduzenja
				}				
			}			
			PrintWriter writer = new PrintWriter("posao_sobe.txt");
			for (int l = 0; l< line.size(); l++) {
				writer.print(line.get(l));
				writer.println();
			}
			
			writer.close();
			
			reader1.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return stavka;
	}
	
	public void ociscena_soba(String selektovan_checkIn) {
		String[] check_prim = selektovan_checkIn.split("\\|"); //prim[0] ce biti id sobe i prim[1] ce biti datum
		String upis,soba = "";
		String datum_do = "";
		String id_sobe = "";
		LocalDate datum_od_date = LocalDate.now(); 
		
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = Files.newBufferedReader(Paths.get("posao_sobe.txt"))) {
			ArrayList<String> line = new ArrayList<String>();
			line.add(br.readLine());
			while (line.get(line.size()-1) != null) {
				line.add(br.readLine()); //prolazi kroz fajl sobe.txt i u line dodaje jednu po jednu liniju, koja ce kasnije biti parsovana

			}
			line.remove(line.size()-1); // na ovom mestu je ushvacen null i stavljen u niz
			
			for (int j = 0; j < line.size(); j++) {
				String linija = line.get(j);
				String[] prim = linija.split("\\|");
				if(prim[1].equals(check_prim[0]) == true && prim[3].equals(check_prim[1]) == true) {
					datum_do = datum_od_date.getDayOfMonth() + "." + datum_od_date.getMonth().getValue() + "." + datum_od_date.getYear();
					upis = prim[0] + "|" + prim[1] + "|slobodna|" + prim[3] + "|" + datum_do;
					id_sobe = prim[1];
					sb.append(upis).append("\n");
				}else {
		            sb.append(line.get(j)).append("\n");
				}
			}
			
			try (FileWriter writer = new FileWriter("posao_sobe.txt");
				 BufferedWriter bw = new BufferedWriter(writer)) {
				
				bw.write(sb.toString());
	
			} catch (IOException e) {
				System.err.format("IOException: %s%n", e);
			}
		}catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
		
		StringBuilder sb1 = new StringBuilder();
		try (BufferedReader br1 = Files.newBufferedReader(Paths.get("sobe.txt"))) {
			ArrayList<String> line = new ArrayList<String>();
			line.add(br1.readLine());
			while (line.get(line.size()-1) != null) {
				line.add(br1.readLine()); //prolazi kroz fajl sobe.txt i u line dodaje jednu po jednu liniju, koja ce kasnije biti parsovana

			}
			line.remove(line.size()-1); // na ovom mestu je ushvacen null i stavljen u niz
			
			for (int j = 0; j < line.size(); j++) {
				String linija = line.get(j);
				String[] prim = linija.split("\\|");
				if(prim[0].equals(id_sobe) == true) {
					soba = prim[0] + "|" + prim[1] + "|slobodna";
					sb1.append(soba).append("\n");
				}else {
		            sb1.append(line.get(j)).append("\n");
				}
			}
			
			try (FileWriter writer = new FileWriter("sobe.txt");
				 BufferedWriter bw = new BufferedWriter(writer)) {
				
				bw.write(sb1.toString());
	
			} catch (IOException e) {
				System.err.format("IOException: %s%n", e);
			}
		}catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
		
		prozorSobarica();
}
		
	public void prozorSobarica() {
		ArrayList<String> stavka = citanje_iz_fajla(this.getKorisnicko_ime());
		
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JPanel dugmad_panel = new JPanel();
		
		String column[]={"ID","Status", "Datum Zaduzenja"};
		DefaultTableModel model = new DefaultTableModel(column, 0);
		JTable table = new JTable(model); 
		
		for (int j = 0; j < stavka.size(); j = j+3) {
			if(stavka.get(j+1).equals("spremanje") == true) {
				String linija[] = {stavka.get(j), stavka.get(j+1), stavka.get(j+2)};
				model.addRow(linija);
			}
		}
		

		if(model.getRowCount() == 0) {
			JLabel ciscenje = new JLabel();  
			ciscenje.setText("Nemate dodeljenih soba sa spremanje!");
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
		        		selektovan_checkIn = (String) table.getValueAt(row[i], 0) + "|" + table.getValueAt(row[i], 2);
		        	}   
		        }       
		    }); 
			
			JButton soba_dugme =new JButton("Soba je Ociscena"); 
			soba_dugme.setBounds(130,100,100, 200); 
			soba_dugme.setAlignmentX(Component.CENTER_ALIGNMENT);
			soba_dugme.setBackground(Color.white);
			soba_dugme.setForeground(Color.DARK_GRAY);
			dugmad_panel.add(soba_dugme);
			soba_dugme.addActionListener(new ActionListener() {
	        	
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.setVisible(false);
					ociscena_soba(selektovan_checkIn);
				}
	        });
		}
		
		JButton odjavi_se =new JButton("Odjavite se sa naloga!"); 
		odjavi_se.setBounds(130,100,100, 200); 
		odjavi_se.setAlignmentX(Component.CENTER_ALIGNMENT);
		odjavi_se.setBackground(Color.DARK_GRAY);
		odjavi_se.setForeground(Color.white);
		dugmad_panel.add(odjavi_se);
		odjavi_se.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				App.logIn();
			}
			});

		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		dugmad_panel.setLayout(new GridLayout(0, 1, 10, 10));
		frame.setTitle("Deo aplikacije za sobarice");  
		frame.setSize(400,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel, BorderLayout.NORTH);
		frame.add(dugmad_panel, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true); 
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
	public int getBroj_soba() {
		return broj_soba;
	}
	public void setBroj_soba(int broj_soba) {
		this.broj_soba = broj_soba;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
