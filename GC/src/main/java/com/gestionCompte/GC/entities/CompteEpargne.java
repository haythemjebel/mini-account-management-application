package com.gestionCompte.GC.entities;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
@Entity
@DiscriminatorValue("CE")
public class CompteEpargne extends Compte {
	
	/**
	 * 
	 * 
	 * @author JEBELHaythem
	 *
	 */
	private static final long serialVersionUID = 1L;
	private double Taux ;

	public CompteEpargne() {
		super();
	}

	public CompteEpargne(String codeCompte, Date dateCreation, Double solde, Client client, double taux) {
		super(codeCompte, dateCreation, solde, client);
		Taux = taux;
	}


	public double getTaux() {
		return Taux;
	}

	public void setTaux(double taux) {
		Taux = taux;
	}
	
	

}
