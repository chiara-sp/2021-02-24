package it.polito.tdp.PremierLeague.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	PremierLeagueDAO dao;
	private Graph<Player,DefaultWeightedEdge> grafo;
	private Map<Integer,Player> idMap;
	
	public Model() {
		this.dao= new PremierLeagueDAO();
		this.idMap= new HashMap<>();
		this.dao.listAllPlayers(idMap);
	}
	public void creaGrafo(Match m) {
		
		grafo= new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		//aggiungo vertici 
		Graphs.addAllVertices(grafo, this.dao.getVertici(m, idMap));
		
		//aggiunta archi 
		for(Adiacenza a: dao.getAdiacenze(m, idMap)) {
			if(grafo.containsVertex(a.getP1())&& grafo.containsVertex(a.getP2())) {
			if(a.getPeso()>=0)
			Graphs.addEdge(grafo, a.getP1(),a.getP2(), a.getPeso());
			else if(a.getPeso()<0)
				Graphs .addEdge(grafo, a.getP2(), a.getP1(), -a.getPeso());
			}
		}
	}
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	public List<Match> getAllMatches(){
		List<Match> res= dao.listAllMatches();
		Collections.sort(res);
		return res;
	}
	public GiocatoreMigliore getMigliore() {
		if(grafo==null)
			return null;
		
		Player best=null;
		Double maxDelta= (double)Integer.MIN_VALUE;
		
		for(Player p: this.grafo.vertexSet()) {
			//cacolo somma dei pesi degli archi uscenti
			double pesoUscente=0;
			for(DefaultWeightedEdge edge: this.grafo.outgoingEdgesOf(p))
				pesoUscente+= this.grafo.getEdgeWeight(edge);
			double pesoEntrante=0;
			for (DefaultWeightedEdge edge: this.grafo.incomingEdgesOf(p))
				pesoEntrante+= this.grafo.getEdgeWeight(edge);
			
			double delta = Math.abs(pesoUscente - pesoEntrante);
			if(delta>maxDelta) {
				best=p;
				maxDelta=delta;
			}
		}
		return new GiocatoreMigliore(best,maxDelta);
	}
	public boolean getGrafo() {
		if(grafo==null)
			return false;
		return true;
	}
}
