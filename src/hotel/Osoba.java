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

public class Osoba {
	private String ime;
	private String prezime;
	private String pol;
	private String datum_rodjenja;
	private String telefon;
	private String adresa; 
	private String korisnicko_ime;
	private String lozinka;
	public String tip;
	 
	public Osoba() {
		 
	}
	
	public ArrayList<String> citaj_iz_fajla() {
		ArrayList<String> stavka = new ArrayList<String>();
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
				
				if(prim[2].equals("administrator") == false) {
					stavka.add(prim[0]);//username
					stavka.add(prim[2]);//tip
					stavka.add(prim[3]);//ime
					stavka.add(prim[4]);//prezime
				}
				
			}			
			PrintWriter writer = new PrintWriter("osobe.txt");
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

	public void obrisi_iz_fajla(String korisnicko_ime) {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = Files.newBufferedReader(Paths.get("osobe.txt"))) {
	
	        String line;
	        String linija = "";
	        while ((line = br.readLine()) != null) {
	            linija = line;
	            String[] prim = linija.split("\\|");
				if(prim[0].equals(korisnicko_ime) == false) {
		            sb.append(line).append("\n"); //prolazi kroz fajl sobe.txt i u line dodaje jednu po jednu liniju, koja ce kasnije biti parsovana
				}
	        }
	        
	        } catch (IOException e) {
	            System.err.format("IOException: %s%n", e);
	        }
			
			try (FileWriter writer = new FileWriter("osobe.txt");
				 BufferedWriter bw = new BufferedWriter(writer)) {
				
				bw.write(sb.toString());
				bw.newLine();
	
			} catch (IOException e) {
				System.err.format("IOException: %s%n", e);
			}
	}
	
	public Osoba(String ime, String prezime, String pol, String datum_rodjenja, String telefon, String adresa, String korisnicko_ime, String lozinka) {
		this.ime = ime;
		this.prezime = prezime;
		this.pol = pol;
		this.datum_rodjenja = datum_rodjenja;
		this.telefon = telefon;
		this.adresa = adresa;
		this.korisnicko_ime = korisnicko_ime;
		this.lozinka = lozinka;
	}
	 
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getPol() {
		return pol;
	}

	public void setPol(String pol) {
		this.pol = pol;
	}

	public String getDatum_rodjenja() {
		return datum_rodjenja;
	}

	public void setDatum_rodjenja(String datum_rodjenja) {
		this.datum_rodjenja = datum_rodjenja;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getKorisnicko_ime() {
		return korisnicko_ime;
	}

	public void setKorisnicko_ime(String korisnicko_ime) {
		this.korisnicko_ime = korisnicko_ime;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}
}
