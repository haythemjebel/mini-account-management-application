package com.gestionCompte.GC.DAO;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gestionCompte.GC.entities.Client;
import com.gestionCompte.GC.entities.Users;


/**
 * 
 * 
 * @author JEBELHaythem
 *
 */

public interface ClientRepository extends JpaRepository<Client, Long> {
/*	@Query("insert into client (nom , email) value(nom = :x ,email= :y)")
	Client save(@Param("x")String nom ,@Param("y")String email);*/
	@Query("select p from Client p where p.nom like :x ")
	public Page<Client>chercher(@Param("x") String mc, Pageable pageable);
	@Query("select p from Client p where p.code like :x ")
	public Client findOne(@Param("x")Long code);


}
