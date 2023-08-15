package hotel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Soba {
	private int id_sobe;
	private String tip_sobe; //jednokrevetna, dvokrevetna, king, premium
	private String status_sobe; //slobodna, zauzeta, spremanje

	public Soba(){
		
	}
	public Soba(int id, String tip_sobe) {
		this.setId_sobe(id);
		this.tip_sobe = tip_sobe;
		this.status_sobe = "slobodna";
	}
	
	public void upisi_u_fajl(ArrayList<String> stavke) {
		
		try (FileWriter writer = new FileWriter("sobe.txt");
			BufferedWriter bw = new BufferedWriter(writer)) {
			
			for( int i = 0 ; i < stavke.size() ; i = i + 3 ) {
				String stavke_text = stavke.get(i) +"|"+stavke.get(i+1) +"|"+stavke.get(i+2);
				bw.write(stavke_text);
				bw.newLine();
			}
			

		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
	}
	
	public Boolean citaj_zauzetost(String datum_od, String datum_do, String soba) {
		String[] od = datum_od.split("\\.");
		LocalDate od_datum_text = LocalDate.of(Integer.valueOf(od[2]), Integer.valueOf(od[1]), Integer.valueOf(od[0]));
		String[] do_text = datum_do.split("\\.");
		LocalDate do_datum_text = LocalDate.of(Integer.valueOf(do_text[2]), Integer.valueOf(do_text[1]), Integer.valueOf(do_text[0]));
		long razlika_datuma = ChronoUnit.DAYS.between(od_datum_text, do_datum_text);
		
		try (BufferedReader br = Files.newBufferedReader(Paths.get("zauzetost_soba.txt"))) {
	
	        String line;
        	ArrayList<String[]> prim = new ArrayList<String[]>();
        	ArrayList<String> upis = new ArrayList<String>();
        	ArrayList<String> novi_upis = new ArrayList<String>();
	        while ((line = br.readLine()) != null) {
	        	prim.add(line.split("\\|"));
	        	upis.add(line);//prolazi kroz fajl sobe.txt i u line dodaje jednu po jednu liniju, koja ce kasnije biti parsovana	
	        }
	        
	        for(int j = 0; j < razlika_datuma; j++) {
		        Boolean postoji = false;
		        for( int k = 0; k < prim.size(); k++) {
		        	postoji = false;
		        	String[] datumi_parse = prim.get(k)[0].split("\\.");
		        	LocalDate provera = LocalDate.of(Integer.valueOf(datumi_parse[2]), Integer.valueOf(datumi_parse[1]), Integer.valueOf(datumi_parse[0]));
		        	
		        	if(provera.isEqual(od_datum_text.plusDays(j)) == true) {
		        		//sada upisi u tu liniju sobu koja je prosledjena
		        		postoji = true;
		        		for( int m = 0; m< upis.size(); m++) {

							System.out.println("oov je upis " + upis.get(m));
		        			String datum_proveri =  od_datum_text.plusDays(j).getDayOfMonth() + "." + od_datum_text.plusDays(j).getMonthValue() + "." + od_datum_text.plusDays(j).getYear();
		        			String[] parsovano = upis.get(m).split("\\|");
		        			if(parsovano[0].equals(datum_proveri) == true) {
								System.out.println("evo me sada nasao sam poklapanje sa datumom " + datum_proveri);
		        				String[] drugi_parse = parsovano[1].split("\\,");

								System.out.println("ovde je onaj drugi parse " + drugi_parse[0]);
		        				for(int n = 0 ; n<drugi_parse.length ; n++) {
									System.out.println("usao sam  "+ drugi_parse[n]);
		        					
			        				if(soba.equals(drugi_parse[n]) == true) {

										System.out.println("ta soba vec psotoji" + drugi_parse[n]);
			        					return false;
			        				}
		        				}
		        				novi_upis.add(upis.get(m) + "," + soba);
		        				upis.remove(m);
		        			}
		        		}
		        	}
		        }
		        if(postoji == false) {
		        	String nova_linija = od_datum_text.plusDays(j).getDayOfMonth() + "." + od_datum_text.plusDays(j).getMonthValue() + "." + od_datum_text.plusDays(j).getYear() + "|" + soba;
		        	novi_upis.add(nova_linija);
		        }
	        }
		
	    for (int s = 0; s<novi_upis.size(); s++) {
	    	upis.add(novi_upis.get(s));
	    }
		try (FileWriter writer = new FileWriter("zauzetost_soba.txt");
			 BufferedWriter bw = new BufferedWriter(writer)) {
			
			for (int l = 0; l < upis.size(); l++) {
				bw.write(upis.get(l));
				bw.newLine();
			}
			
		}} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
		
		return true;
		
	}
	
	public void dodaj_novu_sobu(ArrayList<String> stavke) {
		StringBuilder sb = new StringBuilder();
		String upis = stavke.get(0) + "|" + stavke.get(1) + "|" + stavke.get(2);
		try (BufferedReader br = Files.newBufferedReader(Paths.get("sobe.txt"))) {
	
	        String line;
	        while ((line = br.readLine()) != null) {
	        	sb.append(line).append("\n"); //prolazi kroz fajl sobe.txt i u line dodaje jednu po jednu liniju, koja ce kasnije biti parsovana	
	        }
	        
	        } catch (IOException e) {
	            System.err.format("IOException: %s%n", e);
	        }
			
			try (FileWriter writer = new FileWriter("sobe.txt");
				 BufferedWriter bw = new BufferedWriter(writer)) {
				
				bw.write(sb.toString());
				bw.write(upis);
				bw.newLine();
	
			} catch (IOException e) {
				System.err.format("IOException: %s%n", e);
			}
	}
	
	public void obrisi(String id) {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = Files.newBufferedReader(Paths.get("sobe.txt"))) {
	
	        String line;
	        while ((line = br.readLine()) != null) {
				String linija = line;
	        	String[] prim = linija.split("\\|");
				if(prim[0].equals(id) == false) {
					sb.append(line).append("\n");
				}
	        }
	        
	        } catch (IOException e) {
	            System.err.format("IOException: %s%n", e);
	        }
			
			try (FileWriter writer = new FileWriter("sobe.txt");
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
			reader1 = new BufferedReader(new FileReader("sobe.txt"));
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
				stavka.add(prim[1]);
				stavka.add(prim[2]);
				
			}			
			PrintWriter writer = new PrintWriter("sobe.txt");
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
	
	public int citaj_popunjenost(String datum_od, String datum_do, String soba){
		String[] od = datum_od.split("\\.");
		LocalDate od_datum_text = LocalDate.of(Integer.valueOf(od[2]), Integer.valueOf(od[1]), Integer.valueOf(od[0]));
		String[] do_text = datum_do.split("\\.");
		LocalDate do_datum_text = LocalDate.of(Integer.valueOf(do_text[2]), Integer.valueOf(do_text[1]), Integer.valueOf(do_text[0]));
		long razlika_datuma = ChronoUnit.DAYS.between(od_datum_text, do_datum_text);

    	int broj_ponavljanja = 0;
		try (BufferedReader br = Files.newBufferedReader(Paths.get("zauzetost_soba.txt"))) {
	
	        String line;
        	ArrayList<String[]> prim = new ArrayList<String[]>();
        	ArrayList<String> upis = new ArrayList<String>();
	        while ((line = br.readLine()) != null) {
	        	prim.add(line.split("\\|"));
	        	upis.add(line);//prolazi kroz fajl sobe.txt i u line dodaje jednu po jednu liniju, koja ce kasnije biti parsovana	
	        }
	        
	        for(int j = 0; j < razlika_datuma; j++) {
		        for( int k = 0; k < prim.size(); k++) {
		        	String[] datumi_parse = prim.get(k)[0].split("\\.");
		        	LocalDate provera = LocalDate.of(Integer.valueOf(datumi_parse[2]), Integer.valueOf(datumi_parse[1]), Integer.valueOf(datumi_parse[0]));
		        	
		        	if(provera.isEqual(od_datum_text.plusDays(j)) == true) {
		        		//sada upisi u tu liniju sobu koja je prosledjena
		        		for( int m = 0; m< upis.size(); m++) {

		        			String datum_proveri =  od_datum_text.plusDays(j).getDayOfMonth() + "." + od_datum_text.plusDays(j).getMonthValue() + "." + od_datum_text.plusDays(j).getYear();
		        			String[] parsovano = upis.get(m).split("\\|");
		        			if(parsovano[0].equals(datum_proveri) == true) {
		        				String[] drugi_parse = parsovano[1].split("\\,");

		        				for(int n = 0 ; n<drugi_parse.length ; n++) {
		        					
			        				if(soba.equals(drugi_parse[n]) == true) {
			        		        	broj_ponavljanja++;
			        				}
		        				}
		        			}
		        		}
		        	}
		        }
	        }
		
		try (FileWriter writer = new FileWriter("zauzetost_soba.txt");
			 BufferedWriter bw = new BufferedWriter(writer)) {
			
			for (int l = 0; l < upis.size(); l++) {
				bw.write(upis.get(l));
				bw.newLine();
			}
			
		}} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}

		return broj_ponavljanja;
	}
	
	public String getTip_sobe() {
		return tip_sobe;
	}

	public void setTip_sobe(String tip_sobe) {
		this.tip_sobe = tip_sobe;
	}

	public String getStatus_sobe() {
		return status_sobe;
	}

	public void setStatus_sobe(String status_sobe) {
		this.status_sobe = status_sobe;
	}
	public int getId_sobe() {
		return id_sobe;
	}
	public void setId_sobe(int id_sobe) {
		this.id_sobe = id_sobe;
	}
}
