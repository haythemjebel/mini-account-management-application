package com.gestionCompte.GC.DAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gestionCompte.GC.entities.Operation;

/**
 * 
 * 
 * @author JEBELHaythem
 *
 */

public interface OperationRepository extends JpaRepository<Operation, Long> {
	@Query("select o from Operation o where o.compte.codeCompte = :y order by o.dateOperation DESC")
	public Page<Operation>listeOperation(@Param("y")String codecompte , Pageable pageable );

}
