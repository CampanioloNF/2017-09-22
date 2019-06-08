package it.polito.tdp.formulaone.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;


import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {
	
	
	private FormulaOneDAO dao;
	private List<Season> seasons;
	private Graph <Race, DefaultWeightedEdge> graph;
	
	private Map<Integer, Race> idMapRace;
	private Map<Integer, Driver> idMapDriver;
	
	private Simulatore sim;
	private PriorityQueue<LapTime> eventi;
	
	
	public Model() {
		this.dao = new FormulaOneDAO();
		this.sim = new Simulatore();
	}

	public List<Season> getAllSeason() {
		// TODO Auto-generated method stub
		this.seasons = new ArrayList<>( dao.getAllSeasons());
		return seasons;
	}

	public void creaGrafo(int year) {
		
		// creo il grafo
		this.graph = new SimpleWeightedGraph<Race, DefaultWeightedEdge>(DefaultWeightedEdge.class); 
		
		//creo i vertici 
		this.idMapRace = new HashMap<Integer, Race>();
		dao.getAllVertexs(graph, idMapRace, year);
		
		//creo gli archi
		dao.getAllEdges(graph, idMapRace, year);
		
		
	}

	public String getMaxWeight() {

		if(graph!=null) {
			
			DefaultWeightedEdge max = null;	
			double peso = 0.0;
			
			String ris = null;
			
		   for(Race source : graph.vertexSet()) {
			   
			   for(Race dest : graph.vertexSet()) {
				   
				   if(graph.getEdge(source, dest)!=null) {
					   if(graph.getEdgeWeight(graph.getEdge(source, dest))>peso) {
						   peso=graph.getEdgeWeight(graph.getEdge(source, dest));
						   max=graph.getEdge(source, dest);
					   }
				   }
				   
			   }
			   
		   }
			
			   
			 ris =  graph.getEdgeSource(max).toString()+" - "+graph.getEdgeTarget(max).toString() +" peso: "+peso; 
			 return ris;
		}

		return null;
	}

	public List<Race> getVertex() {
		
		if(graph!=null) {
			
			
			List<Race> ris = new LinkedList<>(graph.vertexSet());
			Collections.sort(ris);
			return ris;
			
		}
		
		return null;
		
	}

	public void simulate( Race race, double p, int t) {
	
		/*
		 * Cosa passo al simulatore?
		 *  
		 *   - Una lista di Drivers
		 *   - Una lista di evnti che rappresentano l'arrivo del Driver D al traguardo nel LapTime L
		 *   
		 */
		
		// mappo i drivers
		
		this.idMapDriver = new HashMap<Integer, Driver>();
		this.eventi = new PriorityQueue<LapTime>();
		 
		dao.getDrivers(race, idMapDriver);
		dao.getAllLapTime(race,idMapDriver,eventi, p, t);
		
		sim.init(eventi, idMapDriver);
		sim.run();
		
		
	}

	
	public String getStats() {
		
		return sim.getStatistiche();
	}

}
