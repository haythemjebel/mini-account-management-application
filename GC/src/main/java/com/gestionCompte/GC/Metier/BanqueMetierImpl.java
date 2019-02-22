package com.gestionCompte.GC.Metier;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestionCompte.GC.DAO.ClientRepository;
import com.gestionCompte.GC.DAO.CompteRepository;
import com.gestionCompte.GC.DAO.OperationRepository;
import com.gestionCompte.GC.DAO.UsersRepository;
import com.gestionCompte.GC.entities.Compte;
import com.gestionCompte.GC.entities.CompteCourant;
import com.gestionCompte.GC.entities.Operation;
import com.gestionCompte.GC.entities.Retrait;
import com.gestionCompte.GC.entities.Versement;
@Service
@Transactional
public class BanqueMetierImpl implements IBanqueMetier {
	
	/**
	 * 
	 * 
	 * @author JEBELHaythem
	 *
	 */
	
	@Autowired
	private CompteRepository compteRepository;
	@Autowired
	private OperationRepository operationRepository ;
	@Autowired
	private ClientRepository clientRepository;
	@Override
	public Compte consulterCompte(String codeCompte) {
		Compte cpCompte=compteRepository.findOneById(codeCompte);
		if(cpCompte==null)throw new RuntimeException("Compte introuvable");
		return cpCompte;
	}

	@Override
	public void verser(String codeCompte, double montant) {
		Compte compte= consulterCompte(codeCompte);
		Versement v = new Versement(new Date(), montant, compte);
		operationRepository.save(v);
		compte.setSolde(compte.getSolde()+montant);
		compteRepository.save(compte);
	}

	@Override
	public void retirer(String codeCompte, double montant) {
		Compte compte =consulterCompte(codeCompte);
		double facilitecompte = 0 ;
		if(compte instanceof CompteCourant)
			facilitecompte = ((CompteCourant) compte).getDecouvert();
		if(compte.getSolde()+facilitecompte<montant)
			throw new RuntimeException("solde insuffissant");
		Retrait r = new Retrait(new Date(),montant,compte);
		operationRepository.save(r);
		compte.setSolde(compte.getSolde()-montant);
		compteRepository .save(compte);
		
	}

	@Override
	public void virement(String codeCompte1, String codeCompte2, double montant) {
		if(codeCompte1.equals(codeCompte2))throw new RuntimeException("Operation impossible");
		retirer(codeCompte1, montant);
		verser(codeCompte2, montant); 
		
	}

	@Override
	public Page<Operation> listOperation(String codeCompte, int page, int size) {
		return operationRepository.listeOperation(codeCompte, new PageRequest(page, size));
	}


}
