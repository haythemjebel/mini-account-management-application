package com.gestionCompte.GC.web;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gestionCompte.GC.DAO.ClientRepository;
import com.gestionCompte.GC.DAO.CompteRepository;
import com.gestionCompte.GC.DAO.UsersRepository;
import com.gestionCompte.GC.Metier.IBanqueMetier;
import com.gestionCompte.GC.entities.Client;
import com.gestionCompte.GC.entities.Compte;
import com.gestionCompte.GC.entities.CompteCourant;
import com.gestionCompte.GC.entities.CompteEpargne;
import com.gestionCompte.GC.entities.Operation;
import com.gestionCompte.GC.entities.Users;

@Controller
public class ControleurBanque {
	
	/**
	 * 
	 * 
	 * @author JEBELHaythem
	 *
	 */
	
	@Autowired
	private IBanqueMetier banqueMetier;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private UsersRepository userrepository;
	@Autowired
	private CompteRepository compteRepository;

	@RequestMapping("/")
	public String first() {
		return "login";
	}

	@RequestMapping("/operations")
	public String index() {
		return "comptes";
	}

	@RequestMapping("/consulterCompte")
	public String Consulter(Model model, String codeCompte, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		model.addAttribute("codeCompte", codeCompte);
		try {
			Compte cp = banqueMetier.consulterCompte(codeCompte);
			Page<Operation> pageOperations = banqueMetier.listOperation(codeCompte, page, size);
			model.addAttribute("listopera", pageOperations.getContent());
			int[] pages = new int[pageOperations.getTotalPages()];
			model.addAttribute("pages", pages);
			model.addAttribute("compte", cp);
		} catch (Exception e) {
			model.addAttribute("excepetion", e);
		}
		return "comptes";
	}

	@RequestMapping(value = "/saveOperation", method = RequestMethod.POST)
	public String saveOperation(Model model, String typeoperation, String codeCompte, double montant,
			String codeCompte2) {
		try {
			if (typeoperation.equals("VER")) {
				banqueMetier.verser(codeCompte, montant);
			} else if (typeoperation.equals("RET")) {
				banqueMetier.retirer(codeCompte, montant);
			}
			if (typeoperation.equals("VIR"))
				banqueMetier.virement(codeCompte, codeCompte2, montant);

		} catch (Exception e) {
			model.addAttribute("error", e);
			return "redirect:/consulterCompte?codeCompte=" + codeCompte + "&error=" + e.getMessage();
		}
		return "redirect:/consulterCompte?codeCompte=" + codeCompte;
	}

	@RequestMapping(value = "/logout")
	public String saveOperation(HttpSession session) {
		session.invalidate();
		return "login";
	}

	@RequestMapping(value = "/verif", method = RequestMethod.POST)
	public String authentification(Long id, String password, HttpSession session) {
		Users u = userrepository.findusers(id, password);
		if (u == null) {
			return "login";
		}
		String name = u.getUsername();
		session.setAttribute("name", name);
		return "redirect:/operations";
	}

	@RequestMapping("/clients")
	public String client() {
		return "client";
	}

	@RequestMapping(value = "/find")
	public String find(Model model, @RequestParam(name = "page", defaultValue = "0") int p,
			@RequestParam(name = "size", defaultValue = "5") int s,
			@RequestParam(name = "motcle", defaultValue = "") String mc) {
		Page<Client> Pageclient = clientRepository.chercher("%" + mc + "%", new PageRequest(p, s));
		model.addAttribute("listclient", Pageclient.getContent());
		int[] pages = new int[Pageclient.getTotalPages()];
		model.addAttribute("pages", pages);
		model.addAttribute("size", s);
		model.addAttribute("pageCourant", p);
		model.addAttribute("motcle", mc);
		return "client";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String Delete(Long code, String motcle, int page, int size) {
		clientRepository.deleteById(code);
		return "redirect:/find?page=" + page + "&size=" + size + "&motcle=" + motcle;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model, Long code) {
		Client u = clientRepository.findOne(code);
		model.addAttribute("client", u);
		return "EditClient";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String SaveP(Model model, @Valid Client client, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "EditClient";
		}
		clientRepository.save(client);
		return "confirmation";
	}

	@RequestMapping("/ajouter")
	public String ajouter() {
		return "ajoutClient";
	}

	@RequestMapping(value = "/enregister", method = RequestMethod.POST)
	public String SaveU(Model model, @Valid Client client, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "ajoutClient";
		}
		clientRepository.save(client);
		return "confirmation";
	}

	@RequestMapping("/compte")
	public String addcpt(Model model) {
		model.addAttribute("typecompte", compteRepository.findAll());
		model.addAttribute("cli", clientRepository.findAll());
		return "ajoutCompte";
	}

	@RequestMapping(value = "/saveCompte", method = RequestMethod.GET)
	public String Savecompte(Model model/*, String type_compte, String code_compte, double solde, double decouvert,
			double taux, String date ,Client code_client*/ ) {
		/*if(type_compte.equals("CompteCourant")) {
			compteRepository.save(new CompteCourant(code_compte, date,solde,code_client,decouvert));
		}else {
			compteRepository.save(new CompteEpargne(code_compte, date,solde,code_client,taux));
		}*/
		//System.out.println("type:"+type_compte+"code:"+code_compte+"solde:"+solde+"decouvert"+decouvert+"Taux:"+taux+"date:"+date+"Code client:"+code_client);
		return "redirect:/compte";
	}
}
