package hotel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import javax.swing.*;

public class Rezervacija {
	private int id_rezervacije;
	private String username;
	private String od_datum;
	private String do_datum;
	private String tip_sobe;
	private String id_sobe;
	private String status;
	private int cena;
	private String dodatne_usluge;
	
	public Rezervacija() {
		
	}
	public Rezervacija(String username, String od_datum, String do_datum, String tip_sobe, String status, String dodatne_usluge) throws ParseException {
		this.setUsername(username);
		this.setOd_datum(od_datum);
		this.setDo_datum(do_datum);
		this.setTip_sobe(tip_sobe);
		this.setId_sobe("null");
		this.setDodatne_usluge(dodatne_usluge);
		this.setStatus(status);
	}
	
	public void izracunaj_cenu(String tip_sobe, String dodatne_usluge, String od, String do_date) {
		
		int cena = 0;
		String[] parse_od = od.split("\\.");
		LocalDate od_datum = LocalDate.of(Integer.valueOf(parse_od[2]), Integer.valueOf(parse_od[1]), Integer.valueOf(parse_od[0]));
		String[] parse_do = do_date.split("\\.");
		LocalDate do_datum = LocalDate.of(Integer.valueOf(parse_do[2]), Integer.valueOf(parse_do[1]), Integer.valueOf(parse_do[0]));
		try (BufferedReader br = Files.newBufferedReader(Paths.get("cenovnik.txt"))) {
	
	        // read line by line
			ArrayList<String> line = new ArrayList<String>();
			line.add(br.readLine());
			while (line.get(line.size()-1) != null) {
				line.add(br.readLine()); //prolazi kroz fajl osobe.txt i u line dodaje jednu po jednu liniju, koja ce kasnije biti parsovana
			}
			line.remove(line.size()-1);
    		
    		LocalDate datum_od_provera = null;
    		LocalDate datum_do_provera = null;
			long razlika = 0;
			for(int i=0; i<line.size(); i++) {
				String linija = line.get(i);
		        String[] prim = linija.split("\\|");

		        if(prim[0].equals("datum_od") == true) {
		        	String[] parse_od1 = prim[1].split("\\.");
		    		datum_od_provera = LocalDate.of(Integer.valueOf(parse_od1[2]), Integer.valueOf(parse_od1[1]), Integer.valueOf(parse_od1[0]));
		    		
		    		String[] parse_do_1 = prim[3].split("\\.");
		    		datum_do_provera = LocalDate.of(Integer.valueOf(parse_do_1[2]), Integer.valueOf(parse_do_1[1]), Integer.valueOf(parse_do_1[0]));

	        		razlika = ChronoUnit.DAYS.between(od_datum, do_datum);
		        }

		        for(int j = 0; j < razlika; j++) {
		        	od_datum.plusDays(j);
		        	
		        	if((datum_od_provera.isBefore(od_datum) == true || datum_od_provera.isEqual(od_datum) == true)  && (datum_do_provera.isAfter(od_datum) == true || datum_do_provera.isEqual(od_datum)== true)) {

			        	System.out.println("tu sam i novi datum provere je " + od_datum);
		        		if(prim[1].equals(tip_sobe) == true) {
		        			cena = cena + Integer.valueOf(prim[2]);

		        		}
		        		String[] dod = dodatne_usluge.split(",");
		        		for(int k = 0; k < dod.length; k++) {
		        			if(prim[1].equals(dod[k]) == true) {
			        			cena = cena + Integer.valueOf(prim[2]);

			        			System.out.println("evo me dodajem na cenu "+ prim[2] + " ukupna cena je sada, " + cena);
			        		}
		        		}
		        	}
        		}
			}
			
			
			PrintWriter writer = new PrintWriter("cenovnik.txt");
			for (int l = 0; l< line.size(); l++) {
				writer.print(line.get(l));
				writer.println();
			}
			writer.close();
			br.close();
		
		}catch (IOException e) {
			System.err.format("IOException: %s%n", e);
			}
		
		this.setCena(cena);
}
	
	public void upis_u_fajl() {
		//id_rezervacije|status_rezervacije|username|datum_od|datum_do|cena|soba_tip|id_sobe=null|dodatne_usluge
		String rezervacija_text;
		rezervacija_text = this.getStatus() + "|" + this.getUsername() + "|" + this.getOd_datum() + "|" + this.getDo_datum() + "|" + this.getCena() + "|" + this.getTip_sobe() + "|" + this.getId_sobe() + "|" + this.getDodatne_usluge();
			
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = Files.newBufferedReader(Paths.get("rezervacije.txt"))) {
	
	        String line;
	        String linija = "";
	        while ((line = br.readLine()) != null) {
	            sb.append(line).append("\n");
	            linija = line;
	        }
	        
	        String[] prim = linija.split("\\|");
	        this.setId_rezervacije(Integer.valueOf(prim[0])+1);
	        rezervacija_text = this.getId_rezervacije() + "|" + rezervacija_text;
	        
	        } catch (IOException e) {
	            System.err.format("IOException: %s%n", e);
	        }
			
			try (FileWriter writer = new FileWriter("rezervacije.txt");
				 BufferedWriter bw = new BufferedWriter(writer)) {
				
				bw.write(sb.toString());
				bw.write(rezervacija_text);
				bw.newLine();
	
			} catch (IOException e) {
				System.err.format("IOException: %s%n", e);
			}
		
	}
	
	public ArrayList<String> citanje_iz_fajla(){
		ArrayList<String> stavka = new ArrayList<String>();
		try {
			BufferedReader reader1;
			reader1 = new BufferedReader(new FileReader("rezervacije.txt"));
			ArrayList<String> line = new ArrayList<String>();
			line.add(reader1.readLine());
			while (line.get(line.size()-1) != null) {
				line.add(reader1.readLine()); //prolazi kroz fajl sobe.txt i u line dodaje jednu po jednu liniju, koja ce kasnije biti parsovana
			}
			line.remove(line.size()-1); // na ovom mestu je ushvacen null i stavljen u niz
			
			for (int j = 0; j < line.size(); j++) {
				String linija = line.get(j);
				String[] prim = linija.split("\\|");
					stavka.add(prim[0]);
					stavka.add(prim[2]);
					stavka.add(prim[1]);
					stavka.add(prim[3]);
					stavka.add(prim[4]);
					stavka.add(prim[5]);
					stavka.add(prim[6]);
					stavka.add(prim[8]);
					//"ID","Status", "Pocetni Datum Rezervacije", "Krajnji Datum Rezervacije", "Cena Rezervacije", "Tip sobe", "Dodatne usluge"
					//2|na cekanju|marko@gmail.com|28.07.2022|30.07.2022|12000|trokrevetna soba|null|dorucak				
			}			
			PrintWriter writer = new PrintWriter("rezervacije.txt");
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
	
	public ArrayList<String> citanje_iz_fajla_sa_sobom(){
		ArrayList<String> stavka = new ArrayList<String>();
		try {
			BufferedReader reader1;
			reader1 = new BufferedReader(new FileReader("rezervacije.txt"));
			ArrayList<String> line = new ArrayList<String>();
			line.add(reader1.readLine());
			while (line.get(line.size()-1) != null) {
				line.add(reader1.readLine()); //prolazi kroz fajl sobe.txt i u line dodaje jednu po jednu liniju, koja ce kasnije biti parsovana
			}
			line.remove(line.size()-1); // na ovom mestu je ushvacen null i stavljen u niz
			
			for (int j = 0; j < line.size(); j++) {
				String linija = line.get(j);
				String[] prim = linija.split("\\|");
					stavka.add(prim[0]);
					stavka.add(prim[2]);
					stavka.add(prim[1]);
					stavka.add(prim[3]);
					stavka.add(prim[4]);
					stavka.add(prim[5]);
					stavka.add(prim[6]);
					stavka.add(prim[7]);
					//"ID","Status", "Pocetni Datum Rezervacije", "Krajnji Datum Rezervacije", "Cena Rezervacije", "Tip sobe", "Dodatne usluge"
					//2|na cekanju|marko@gmail.com|28.07.2022|30.07.2022|12000|trokrevetna soba|null|dorucak				
			}			
			PrintWriter writer = new PrintWriter("rezervacije.txt");
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

	public void menjaj_status(String id_rezervacije, String novi_status, String soba_id) {
		
		String rezervacija = "";
		StringBuilder sb1 = new StringBuilder();
		try (BufferedReader br1 = Files.newBufferedReader(Paths.get("rezervacije.txt"))) {
			ArrayList<String> line = new ArrayList<String>();
			line.add(br1.readLine());
			while (line.get(line.size()-1) != null) {
				line.add(br1.readLine()); //prolazi kroz fajl sobe.txt i u line dodaje jednu po jednu liniju, koja ce kasnije biti parsovana

			}
			line.remove(line.size()-1); // na ovom mestu je ushvacen null i stavljen u niz
			
			for (int j = 0; j < line.size(); j++) {
				String linija = line.get(j);
				String[] prim = linija.split("\\|");
				if(id_rezervacije.equals(prim[0]) == true) {
					if(novi_status.equals("odobreno")== true) {
						rezervacija = prim[0] + "|" + novi_status + "|" + prim[2] + "|" + prim[3] + "|" + prim[4] + "|" + prim[5] + "|" + prim[6] + "|" + soba_id + "|" + prim[8];	
					}else if(novi_status.equals("odbijeno") == true) {
						rezervacija = prim[0] + "|" + novi_status + "|" + prim[2] + "|" + prim[3] + "|" + prim[4] + "|" + 0 + "|" + prim[6] + "|" + prim[7] + "|" + prim[8];
					}else {
						rezervacija = prim[0] + "|" + novi_status + "|" + prim[2] + "|" + prim[3] + "|" + prim[4] + "|" + prim[5] + "|" + prim[6] + "|" + prim[7] + "|" + prim[8];
					}
					sb1.append(rezervacija).append("\n");
				}else {
		            sb1.append(line.get(j)).append("\n");
				}
			}
			
			try (FileWriter writer = new FileWriter("rezervacije.txt");
				 BufferedWriter bw = new BufferedWriter(writer)) {
				
				bw.write(sb1.toString());
	
			} catch (IOException e) {
				System.err.format("IOException: %s%n", e);
			}
		}catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
	}
	
	public String getOd_datum() {
		return od_datum;
	}
	public void setOd_datum(String od_datum) {
		this.od_datum = od_datum;
	}
	public String getDo_datum() {
		return do_datum;
	}
	public void setDo_datum(String do_datum) {
		this.do_datum = do_datum;
	}
	public String getTip_sobe() {
		return tip_sobe;
	}
	public void setTip_sobe(String tip_sobe) {
		this.tip_sobe = tip_sobe;
	}
	public int getCena() {
		return cena;
	}
	public void setCena(int cena) {
		this.cena = cena;
	}
	public String getDodatne_usluge() {
		return dodatne_usluge;
	}
	public void setDodatne_usluge(String dodatne_usluge) {
		this.dodatne_usluge = dodatne_usluge;
	}
	
	public void prozorRezervacija(String[] args) {
		JFrame f=new JFrame();//creating instance of JFrame  
		          
		JButton b=new JButton("rezervacija");//creating instance of JButton  
		b.setBounds(130,100,100, 40);//x axis, y axis, width, height  
		          
		f.add(b);//adding button in JFrame  
		          
		f.setSize(400,500);//400 width and 500 height  
		f.setLayout(null);//using no layout managers  
		f.setVisible(true);//making the frame visible  
	}
	public int getId_rezervacije() {
		return id_rezervacije;
	}
	public void setId_rezervacije(int id_rezervacije) {
		this.id_rezervacije = id_rezervacije;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getId_sobe() {
		return id_sobe;
	}
	public void setId_sobe(String id_sobe) {
		this.id_sobe = id_sobe;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
