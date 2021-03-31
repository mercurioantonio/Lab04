package it.polito.tdp.lab04.model;

import java.util.List;
import java.util.Map;
import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	

	private CorsoDAO corsoDao;
	private StudenteDAO studenteDao;
	
	
	public Model() {
		corsoDao = new CorsoDAO();
		studenteDao = new StudenteDAO();
	}
	
	public List<Corso> getCorso(){
		return corsoDao.getTuttiICorsi();
	}

	public List<Studente> getStudentiIscrittiAlCorso(Corso corso){
		return corsoDao.getStudentiIscrittiAlCorso(corso);
	}
	
	public boolean inscriviStudenteACorso(Studente studente, Corso corso){
		return corsoDao.inscriviStudenteACorso(studente, corso);
	}

	public List<Studente> getTuttiStudenti(){
		return studenteDao.getTuttiStudenti();
	}
	
	public List<Corso> getCorsiPerStudente(Studente studente){
		return studenteDao.getCorsiPerStudente(studente);
	}
}
