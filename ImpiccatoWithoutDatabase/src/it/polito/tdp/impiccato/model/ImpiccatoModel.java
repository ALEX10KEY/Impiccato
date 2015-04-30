package it.polito.tdp.impiccato.model;

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
	
	//stringa che contiene la parte di parola già indovinata
	//es: parte _ _ _ _ _ _ poi diventa _ C _ _ _, ecc...
	private String maschera;
	
	//quali tentativi sono già stati fatti?
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
	 * Verifica se il tentativo fatto è corretto, cioè non è stato
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
		
		//Verifico se la lettera è presente
		if ( segreto.contains(tenta) ){
			//ok, tentativo buono
			
			
			for(int i = 0; i<this.segreto.length(); i++){
				if ( this.segreto.charAt(i)==tenta.charAt(0) ){
					//la lettera è tornata in posizione i
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
