package it.polito.tdp.PremierLeague.model;

public class GiocatoreMigliore {

	Player p;
	double efficienza;
	public GiocatoreMigliore(Player p, double efficienza) {
		super();
		this.p = p;
		this.efficienza = efficienza;
	}
	public Player getP() {
		return p;
	}
	public void setP(Player p) {
		this.p = p;
	}
	public double getEfficienza() {
		return efficienza;
	}
	public void setEfficienza(double efficienza) {
		this.efficienza = efficienza;
	}
	@Override
	public String toString() {
		return "GiocatoreMigliore [p=" + p + ", efficienza=" + efficienza + "]";
	}
	
	
}
