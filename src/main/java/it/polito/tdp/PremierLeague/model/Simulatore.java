package it.polito.tdp.PremierLeague.model;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.PremierLeague.model.Evento.EventType;

public class Simulatore {

	// Tipi di evento? -> coda prioritaria
		//private PriorityQueue<Evento> queue = new PriorityQueue<>();

	    
		
		// MODELLO DEL MONDO
		private Graph<Player, DefaultWeightedEdge> grafo;
		private Model model;
		List<Evento> azioni;
		Match match;
		private int numAzioni;
		Player giocatoreMigliore;

		// VALORI DA CALCOLARE
		private int espulsioniCasa;
		private int espulsioniOspiti;
		private int goalCasa;
		private int goalOspiti;
		

		public Simulatore(Graph<Player, DefaultWeightedEdge> grafo, Model model) {
			this.grafo = grafo;
			this.model = model;
		}

		// INIZIALIZZAZIONE
		public void init(Match match, int numAzioni) {
		
			this.match=match;
			this.numAzioni= numAzioni;
			this.azioni= new LinkedList<Evento>();
			this.espulsioniCasa=0;
			this.espulsioniOspiti=0;
			this.goalCasa=0;
			this.goalOspiti=0;
			this.giocatoreMigliore= model.getMigliore().p;
		}

		// SIMULAZIONE VERA E PROPRIA
		public void run() {
			int idSquadraGiocatoreMigliore=model.getSquadraGiocatoreMigliore(giocatoreMigliore);
				for(int i=0; i<numAzioni; i++) {
					double numero= Math.random();
					if(numero<0.5) {
						int giocatoriCasa= 11- this.espulsioniCasa;
						int giocatoriOspiti= 11- this.espulsioniOspiti;
						if(giocatoriCasa>giocatoriOspiti) {
							azioni.add(new Evento(i+1,EventType.GOAL));
							System.out.println("GOAL squadra casa");
							this.goalCasa++;
						}
						else if(giocatoriCasa<giocatoriOspiti) {
							azioni.add(new Evento(i+1,EventType.GOAL));
							System.out.println("GOAL squadra ospite");
							this.goalOspiti++;
						}
						else {
							if(match.getTeamHomeID()==idSquadraGiocatoreMigliore) {
								azioni.add(new Evento(i+1,EventType.GOAL));
								System.out.println("GOAL squadra casa");
								this.goalCasa++;
							}
							if(match.getTeamAwayID()==idSquadraGiocatoreMigliore) {
								azioni.add(new Evento(i+1,EventType.GOAL));
								System.out.println("GOAL squadra ospite");
								this.goalOspiti++;
							}
						}
					}
					if(numero>0.5 && numero<0.8) {
						double num= Math.random();
						if(num<0.6) {
							if(match.getTeamAwayID()==idSquadraGiocatoreMigliore) {
							azioni.add(new Evento(i+1,EventType.ESPULSIONE));
							System.out.println("ESPULSIONE squadra ospite ");
							this.espulsioniOspiti++;
							}
							if(match.getTeamHomeID()==idSquadraGiocatoreMigliore) {
								azioni.add(new Evento(i+1,EventType.ESPULSIONE));
								System.out.println("ESPULSIONE squadra casa");
								this.espulsioniCasa++;
							}
						}else {
							if(match.getTeamAwayID()!=idSquadraGiocatoreMigliore) {
								azioni.add(new Evento(i+1,EventType.ESPULSIONE));
								System.out.println("ESPULSIONE squadra ospite ");
								this.espulsioniOspiti++;
								}
								if(match.getTeamHomeID()!=idSquadraGiocatoreMigliore) {
									azioni.add(new Evento(i+1,EventType.ESPULSIONE));
									System.out.println("ESPULSIONE squadra casa");
									this.espulsioniCasa++;
								}
						}
					}
					else if (numero>0.8){
						double num= Math.random();
						if(num<0.5) {
							this.numAzioni+=2;
							this.azioni.add(new Evento(i+1,EventType.INFORTUNIO));
							System.out.println("infortunio partita allungata di 2 azioni");
						}else {
							this.numAzioni+=3;
							this.azioni.add(new Evento(i+1,EventType.INFORTUNIO));
							System.out.println("infortunio partita allungata di 3 azioni");
						}
					}
				}
		}
		public String risultato() {
			return this.match.teamHomeNAME + " "+ this.goalCasa+ " - "+ this.match.teamAwayNAME+ " "+this.goalOspiti;
		}
		public String espulsioni() {
			return this.match.teamHomeNAME + " "+ this.espulsioniCasa+ " - "+ this.match.teamAwayNAME+ " "+this.espulsioniOspiti;
		}
		
		
}
