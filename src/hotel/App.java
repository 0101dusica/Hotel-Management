package hotel;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;   

public class App implements ActionListener{ 

	private static JFrame frame;
	private static JPanel panel;
	private static JLabel korisnicko_ime, lozinka;
	private static JTextField korisnicko_ime_input,lozinka_input ;
	private static JButton button;
	
	public static void main(String[] arg){  
         logIn();
	}
	
	public static void logIn() {
		setFrame(new JFrame());
		panel = new JPanel();
		JPanel button_panel = new JPanel();
		getFrame().setTitle("Hotel SV 42/2021");  
		getFrame().setSize(300,150);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		korisnicko_ime = new JLabel("Korisnicko Ime: ");
		korisnicko_ime.setBounds(10,20,80,25);
		panel.add(korisnicko_ime);
		
		korisnicko_ime_input = new JTextField(15); //max duzina korisnickog imena je 15 karaktera
		korisnicko_ime_input.setBounds(100,20,165,25);
		panel.add(korisnicko_ime_input);
		
		lozinka = new JLabel("Lozinka: ");
		lozinka.setBounds(10,20,80,25);
		panel.add(lozinka);
		
		lozinka_input = new JTextField(15);
		lozinka_input.setBounds(100,20,165,25);
		panel.add(lozinka_input);
		
		button = new JButton("LogIn");
		button.setBounds(10,80,100,25);
		button.setBackground(Color.white);
		button.setForeground(Color.DARK_GRAY);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.addActionListener(new App());
		button_panel.add(button);
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		button_panel.setLayout(new BoxLayout(button_panel, BoxLayout.PAGE_AXIS));

		getFrame().add(panel, BorderLayout.NORTH);
		getFrame().add(button_panel, BorderLayout.SOUTH);
		getFrame().setLocationRelativeTo(null);
		getFrame().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String korisnicko_ime_uneto = korisnicko_ime_input.getText();
		String lozinka_uneta = lozinka_input.getText();
		int count = 0;
		
		try {
			BufferedReader reader1;
			reader1 = new BufferedReader(new FileReader("osobe.txt"));
			ArrayList<String> line = new ArrayList<String>();
			line.add(reader1.readLine());
			while (line.get(line.size()-1) != null) {
				line.add(reader1.readLine()); //prolazi kroz fajl osobe.txt i u line dodaje jednu po jednu liniju, koja ce kasnije biti parsovana
			}
			line.remove(line.size()-1); // na ovom mestu je ushvacen null i stavljen u niz
			
			for (int j = 0; j < line.size(); j++) {
				String linija = line.get(j);
				String[] prim = linija.split("\\|");
				if(prim[0].equals(korisnicko_ime_uneto) == true) {
					if(prim[1].equals(lozinka_uneta) == true) {
						if(prim[2].equals("administrator") == true) {
							Administrator administrator = new Administrator(prim[3], prim[4], prim[5], prim[6], prim[7], prim[8], prim[0], prim[1]);
							administrator.prozorAdministratora();
							App.getFrame().setVisible(false);
							count = count + 1;
						}else if(prim[2].equals("recepcioner") == true){
							int staz = Integer.parseInt(prim[10]);
							Recepcioner recepcioner = new Recepcioner(prim[3], prim[4], prim[5], prim[6], prim[7], prim[8], prim[0], prim[1], prim[9], staz);
							recepcioner.prozorRecepcioner();
							App.getFrame().setVisible(false);
							count = count + 1;
						}else if(prim[2].equals("sobarica") == true){
							int staz = Integer.parseInt(prim[10]);
							Sobarica sobarica = new Sobarica(prim[3], prim[4], prim[5], prim[6], prim[7], prim[8], prim[0], prim[1], prim[9], staz);
							sobarica.prozorSobarica();
							App.getFrame().setVisible(false);
							count = count + 1;
						}else if(prim[2].equals("gost") == true){
							Gost gost = new Gost(prim[3], prim[4], prim[5], prim[6], prim[7], prim[8], prim[0], prim[1]);
							gost.prozorGost();
							App.getFrame().setVisible(false);
							count = count + 1;
						}
					}
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
		
		if(count == 0) {
			JOptionPane.showMessageDialog(getFrame(), "Neispravno korisnicko ime ili lozinka!");
		}
	}

	public static JFrame getFrame() {
		return frame;
	}

	public static void setFrame(JFrame frame) {
		App.frame = frame;
	}  
}
