package com.gestionCompte.GC;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gestionCompte.GC.DAO.ClientRepository;
import com.gestionCompte.GC.DAO.CompteRepository;
import com.gestionCompte.GC.DAO.OperationRepository;
import com.gestionCompte.GC.Metier.IBanqueMetier;
import com.gestionCompte.GC.entities.Client;
import com.gestionCompte.GC.entities.Compte;
import com.gestionCompte.GC.entities.CompteCourant;
import com.gestionCompte.GC.entities.CompteEpargne;
import com.gestionCompte.GC.entities.Retrait;
import com.gestionCompte.GC.entities.Versement;

/**
 * 
 * 
 * @author JEBEL Haythem
 *
 */

@SpringBootApplication
public class GcApplication  implements CommandLineRunner{
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private CompteRepository compteRepository ;
	@Autowired
	private OperationRepository operationRepository ;
	@Autowired
	private IBanqueMetier banqueMetier;
	public static void main(String[] args)  {
		SpringApplication.run(GcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*Client c1 =clientRepository.save(new Client("hassen","hassan@gmail.com"));
		Client c2 =clientRepository.save(new Client("rachid","rachid@gmail.com"));
		
		Compte cp1 = compteRepository.save(new CompteCourant("c1", new Date(),90000.0,c1,6000.0));
		Compte cp2 = compteRepository.save(new CompteEpargne("c2", new Date(),6000.0,c2,5.5));
		
		operationRepository.save(new Versement(new Date(), 9000, cp1));
		operationRepository.save(new Versement(new Date(), 6000, cp1));
		operationRepository.save(new Versement(new Date(), 2600, cp1));
		operationRepository.save(new Retrait(new Date(), 9000, cp1));
		
		operationRepository.save(new Versement(new Date(), 2600, cp2));
		operationRepository.save(new Versement(new Date(), 4200, cp2));
		operationRepository.save(new Versement(new Date(), 600, cp2));
		operationRepository.save(new Retrait(new Date(), 1800, cp2));*/
			
		//banqueMetier.verser("c1", 1000);
		//banqueMetier.retirer("c1", 1000);
	}

}

