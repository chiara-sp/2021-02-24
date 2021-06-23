package it.polito.tdp.PremierLeague.model;

public class Evento implements Comparable<Evento>{

	enum EventType{
		GOAL,
		ESPULSIONE,
		INFORTUNIO,
	};
	
	private int numAzione;
	private EventType tipo;
	public Evento(int numAzione, EventType tipo) {
		super();
		this.numAzione = numAzione;
		this.tipo = tipo;
	}
	public int getNumAzione() {
		return numAzione;
	}
	public void setNumAzione(int numAzione) {
		this.numAzione = numAzione;
	}
	public EventType getTipo() {
		return tipo;
	}
	public void setTipo(EventType tipo) {
		this.tipo = tipo;
	}
	@Override
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
		return this.numAzione- o.numAzione;
	}
	
	
	
}
