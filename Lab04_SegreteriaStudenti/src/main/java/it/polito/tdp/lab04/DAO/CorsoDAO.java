package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();
		

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				//System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);

				// Crea un nuovo JAVA Bean Corso
				// Aggiungi il nuovo oggetto Corso alla lista corsi
				corsi.add(new Corso(codins, numeroCrediti, nome, periodoDidattico));
				
			}

			conn.close();
			
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public void getCorso(Corso corso) {
		
		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();
		Corso trovato;

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				
				corsi.add(new Corso(codins, numeroCrediti, nome, periodoDidattico));
			}
			
			for(Corso c : corsi) {
				if(c.getCodins().equals(corso.getCodins())) {
					trovato = c;
					break;
				}
			}

			conn.close();
			
			
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		
		final String sql = "SELECT * FROM studente s, iscrizione i WHERE i.codins=? AND s.matricola=i.matricola";

		List<Studente> studenti = new LinkedList<Studente>();
		

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				Studente s = new Studente(rs.getInt("s.matricola"), rs.getString("s.cognome"), rs.getString("s.nome"), rs.getString("s.CDS"));
				studenti.add(s);
			}
			
			conn.close();
	      	
			return studenti;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		
		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();
        List<Studente> studenti = new LinkedList<Studente>();
        Corso trovatoCorso = null;
        Studente trovatoStudente = null;
        boolean flagCorso = false;
        boolean flagStudente = false;
        
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				corsi.add(new Corso(codins, numeroCrediti, nome, periodoDidattico));
			}

			conn.close();			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		
		final String sq2 = "SELECT * FROM studente";

		

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sq2);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Studente s = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("CDS"));
				studenti.add(s);
			}

			conn.close();
		
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		
		for(Corso c: corsi)
			if(c.equals(corso)){
				trovatoCorso = c;
				flagCorso = true;
			}
		for(Studente s: studenti) 
			if(s.equals(studente)) {
				trovatoStudente = s;
				flagStudente = true;
			}
		
		if(flagCorso == true && flagStudente == true) {
			
			final String sq3 = "INSERT INTO iscrizione (matricola,codins) VALUES (?,?)";

			try {
				Connection conn = ConnectDB.getConnection();
				PreparedStatement st = conn.prepareStatement(sq3);
				st.setInt(1, trovatoStudente.getMatricola());
				st.setString(2, trovatoCorso.getCodins());
				ResultSet rs = st.executeQuery();

				
				conn.close();
				
				return true;

			} catch (SQLException e) {
				// e.printStackTrace();
				throw new RuntimeException("Errore Db", e);
			}
		}
		
		// ritorna true se l'iscrizione e' avvenuta con successo
		return false;
	}

}
