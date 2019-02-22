package com.gestionCompte.GC.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gestionCompte.GC.entities.Compte;

/**
 * 
 * 
 * @author JEBELHaythem
 *
 */
public interface CompteRepository extends JpaRepository<Compte, String> {
	@Query("Select c from Compte c where c.id = :x ")
	Compte findOneById(@Param("x")String codeCpte);

}
