package hotel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Cenovnik {
	private String od_datum;
	private String do_datum;
	
	Cenovnik(){
		
	}
	Cenovnik(String od_datum, String do_datum){
		this.setOd_datum(od_datum);
		this.setDo_datum(do_datum);
	}
	
	public void obrisi_fajl(String od_datum, String do_datum) {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = Files.newBufferedReader(Paths.get("cenovnik.txt"))) {
	
	        String line;
	        int count = 0;
	        while ((line = br.readLine()) != null) {
	        	String linija = line;
	        	String[] prim = linija.split("\\|");
				if(prim[1].equals(od_datum) == false || prim[3].equals(do_datum) == false) {
					if(count == 0) {
			            sb.append(line).append("\n");
					}else {
						if(prim[0].equals("datum_od") == true) {
							count = 0;
						}
					}
				}else {
					count = 1;
				}
	        }
	        
	        } catch (IOException e) {
	            System.err.format("IOException: %s%n", e);
	        }
			
			try (FileWriter writer = new FileWriter("cenovnik.txt");
				 BufferedWriter bw = new BufferedWriter(writer)) {
				
				bw.write(sb.toString());
	
			} catch (IOException e) {
				System.err.format("IOException: %s%n", e);
			}
	}
	
	public void upis_u_fajl(ArrayList<String> cene, String datum_od, String datum_do) {
		ArrayList<String> tipovi_soba = citanje_dodatnih_uluga_tipova_soba("tipovi_soba.txt");
		ArrayList<String> dodatne_usluge = citanje_dodatnih_uluga_tipova_soba("dodatne_usluge.txt");
		ArrayList<String> upis = new ArrayList<String>();
		try {
			BufferedReader reader1;
			reader1 = new BufferedReader(new FileReader("cenovnik.txt"));
			ArrayList<String> line = new ArrayList<String>();
			line.add(reader1.readLine());
			String poslednja_linija;
			while (line.get(line.size()-1) != null) {
				line.add(reader1.readLine()); //prolazi kroz fajl osobe.txt i u line dodaje jednu po jednu liniju, koja ce kasnije biti parsovana
			}
			line.remove(line.size()-1); // na ovom mestu je ushvacen null i stavljen u niz
			
			poslednja_linija = line.get(line.size()-1);
			String[] poslednja_parsovana = poslednja_linija.split("\\|");
			int poslednji_id = Integer.valueOf(poslednja_parsovana[0]);
			
			upis.add("datum_od|" + datum_od + "|datum_do|" + datum_do + "\n");
			for (int j = 0; j < tipovi_soba.size() ; j++) {
				String linija = (poslednji_id+j+1) + "|" + tipovi_soba.get(j) + "|" + cene.get(j) + "\n";
				upis.add(linija);
			}
			
			for (int j = 0; j < dodatne_usluge.size() ; j++) {
				String linija = (poslednji_id+tipovi_soba.size()+j+1) + "|" + dodatne_usluge.get(j) + "|" + cene.get(j+tipovi_soba.size()-1) + "\n";
				upis.add(linija);
			}
				
			
			PrintWriter writer = new PrintWriter("cenovnik.txt");
			for (int l = 0; l< line.size(); l++) {
				writer.print(line.get(l));
				writer.println();
			}
			
			for (int l = 0; l< upis.size(); l++) {
				writer.print(upis.get(l));
			}
			
			writer.close();
			
			reader1.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
	}
	
	public ArrayList<String> citanje_dodatnih_uluga_tipova_soba(String adresa){
		ArrayList<String> stavka = new ArrayList<String>();
		try {
			BufferedReader reader1;
			reader1 = new BufferedReader(new FileReader(adresa));
			ArrayList<String> line = new ArrayList<String>();
			line.add(reader1.readLine());
			while (line.get(line.size()-1) != null) {
				line.add(reader1.readLine()); //prolazi kroz fajl osobe.txt i u line dodaje jednu po jednu liniju, koja ce kasnije biti parsovana
			}
			line.remove(line.size()-1); // na ovom mestu je ushvacen null i stavljen u niz
			
			for (int j = 0; j < line.size(); j++) {
				if(adresa.equals("tipovi_soba.txt") == true) {
					String[] prim = line.get(j).split("\\|");
					stavka.add(prim[0]);
					stavka.add(prim[1]);
				}else {
					stavka.add(line.get(j));
				}
				
			}
			PrintWriter writer = new PrintWriter(adresa);
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
	
	public void dodaj_dodatne_usluge_tipove_soba(String naziv, String adresa) {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = Files.newBufferedReader(Paths.get(adresa))) {
	
	        String line;
	        while ((line = br.readLine()) != null) {
	        	sb.append(line).append("\n"); //prolazi kroz fajl sobe.txt i u line dodaje jednu po jednu liniju, koja ce kasnije biti parsovana	
	        }
	        
	        } catch (IOException e) {
	            System.err.format("IOException: %s%n", e);
	        }
			
			try (FileWriter writer = new FileWriter(adresa);
				 BufferedWriter bw = new BufferedWriter(writer)) {
				
				bw.write(sb.toString());
				bw.write(naziv);
				bw.newLine();
	
			} catch (IOException e) {
				System.err.format("IOException: %s%n", e);
			}
	}
		
	public void obrisi_dodatne_usluge_tipove_soba(String naziv, String adresa) { //moze biti ili dodatne_usluge.txt ili tipovi_soba.txt
		StringBuilder sb = new StringBuilder();
		
		try (BufferedReader br = Files.newBufferedReader(Paths.get(adresa))) {
	
	        String line;
	        while ((line = br.readLine()) != null) {
	        	if(adresa.equals("tipovi_soba.txt") == true) {
	    			String[] prim = line.split("\\|");
	    			if(prim[0].equals(naziv) == false) {
	    				sb.append(line).append("\n");
	    			}
	    		}else if (line.equals(naziv) == false) {
		            sb.append(line).append("\n"); //prolazi kroz fajl sobe.txt i u line dodaje jednu po jednu liniju, koja ce kasnije biti parsovana
				}
	        }
	        
	        } catch (IOException e) {
	            System.err.format("IOException: %s%n", e);
	        }
			
			try (FileWriter writer = new FileWriter(adresa);
				 BufferedWriter bw = new BufferedWriter(writer)) {
				
				bw.write(sb.toString());
	
			} catch (IOException e) {
				System.err.format("IOException: %s%n", e);
			}
	}
	
	public ArrayList<String> citanje_iz_fajla() {
		ArrayList<String> stavka = new ArrayList<String>();
		try {
			BufferedReader reader1;
			reader1 = new BufferedReader(new FileReader("cenovnik.txt"));
			ArrayList<String> line = new ArrayList<String>();
			line.add(reader1.readLine());
			while (line.get(line.size()-1) != null) {
				line.add(reader1.readLine()); //prolazi kroz fajl osobe.txt i u line dodaje jednu po jednu liniju, koja ce kasnije biti parsovana
			}
			line.remove(line.size()-1); // na ovom mestu je ushvacen null i stavljen u niz
			
			for (int j = 0; j < line.size(); j++) {
				String linija = line.get(j);
				String[] prim = linija.split("\\|");
				if(prim[0].equals("datum_od") == true) { //ako prepozna da je u txt-u pocetak novog cenovnika
					stavka.add(prim[0]);
					stavka.add(prim[1]);
					stavka.add(prim[2]);
					stavka.add(prim[3]);
				}else { // ako prepozna da je artikal sa svojim id, nazivom i cenom
					stavka.add(prim[0]); //id
					stavka.add(prim[1]); //naziv
					stavka.add(prim[2]); //cena
				}
			}
			
			PrintWriter writer = new PrintWriter("cenovnik.txt");
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
}
