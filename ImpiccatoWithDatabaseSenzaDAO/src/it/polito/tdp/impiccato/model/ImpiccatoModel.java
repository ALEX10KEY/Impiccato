package it.polito.tdp.impiccato.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

/**
 * Contiene i dati e la logica per giocare a una partita
 * all'impiccato
 * 
 * @author alex10key
 *
 */
public class ImpiccatoModel {

	//parola segreta (da indovinare)
	private String segreto;
	
	//numero di errori fatti finora
	private int errori;
	
	//numero max di errori permessi
	private final int MAX_ERRORI = 8;
	
	//stringa che contiene la parte di parola gi� indovinata
	//es: parte _ _ _ _ _ _ poi diventa _ C _ _ _, ecc...
	private String maschera;
	
	//quali tentativi sono gi� stati fatti?
	private Set<String> tentativi;

	public ImpiccatoModel() {
		this.tentativi = new HashSet<String>();
		segreto = "";
		maschera = "";
		errori = 0;
	}
	
	public void nuovaPartita(String segreto){
		this.segreto = segreto.toUpperCase();
		this.errori = 0;
		this.tentativi.clear();
		this.maschera = "";
		for(int i=0;i<this.segreto.length();i++){
			maschera = maschera + "_";
		}
	}
	
	/**
	 * Verifica se il tentativo fatto � corretto, cio� non � stato
	 * fatto in precedenza ed appartiene alla parola da indovinare
	 * 
	 * @param tenta la lettera che sto tentando di indovinare
	 * @return false se errore, true se tentativo corretto
	 */
	public boolean tentativo(String tenta){
		
		tenta = tenta.toUpperCase();
		
		if ( this.tentativi.contains(tenta) ){
			this.errori++;
			return false;
		}
		
		this.tentativi.add(tenta);
		
		//Verifico se la lettera � presente
		if ( segreto.contains(tenta) ){
			//ok, tentativo buono
			
			
			for(int i = 0; i<this.segreto.length(); i++){
				if ( this.segreto.charAt(i)==tenta.charAt(0) ){
					//la lettera � tornata in posizione i
					//devo sostituire il _ in posizione i con tentativo
					//vorrei fare: this.maschera.charAt(i) = tenta.charAt(0);
					maschera = maschera.substring(0, i)+tenta+this.maschera.substring(i+1);
					
				}
					
			}
			
			return true;
		} else {
			//errore
			this.errori++;
		}
		
		return false;
		
	}

	public boolean isWinner(){
		return ( !this.maschera.contains("_") );
	}
	
	public boolean isLoser(){
		return (errori>=this.MAX_ERRORI);
	}
	
	/**
	 * @return the errori
	 */
	public int getErrori() {
		return errori;
	}

	/**
	 * @return the mAX_ERRORI
	 */
	public int getMAX_ERRORI() {
		return MAX_ERRORI;
	}

	/**
	 * @return the maschera
	 */
	public String getMaschera() {
		return maschera;
	}
	
	/**
	 * Genera una nuova parola segreta, estraendola casualmente dal dizionario
	 * memorizzato nel database
	 * 
	 * @return una parola casuale
	 */
	public String nuovoSegreto() {
		
		String url = "jdbc:mysql://localhost/dizionario?user=root";
		
		try {
			Connection conn = DriverManager.getConnection(url);
			
			//Cambia questo
			String query1 = "select count(id) as numero from parola";
			
			Statement st1 = conn.createStatement();
			ResultSet res1 = st1.executeQuery(query1);
			
			res1.first();
			int numeroParole = res1.getInt("numero");
			
			res1.close();
			
			int indiceParola = (int)(Math.random()*numeroParole);
			
			//String query2 = "select nome from parola limit "+indiceParola+",0";
			String query2 = String.format("select nome from parola limit %d,1", indiceParola);
			
			Statement st2 = conn.createStatement();
			ResultSet res2 = st2.executeQuery(query2);
			
			res2.first();
			String segreto = res2.getString("nome");
			res2.close();
			
			conn.close();
			
			return segreto.toUpperCase();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/*public static void main(String[] args){
		ImpiccatoModel model = new ImpiccatoModel();
		
		model.nuovaPartita("ciao");
		System.out.println(model.getMaschera());
		
		model.tentativo("a");
		System.out.println(model.getMaschera());
		
		model.tentativo("k");
		System.out.println(model.getMaschera());
		
		model.tentativo("c");
		System.out.println(model.getMaschera());
		
		model.tentativo("o");
		System.out.println(model.getMaschera());
	}*/
	
	
}
