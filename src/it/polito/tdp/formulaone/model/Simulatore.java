package it.polito.tdp.formulaone.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Simulatore {
	
	/*
	 * Per la simulazione sono necessari:
	 *  -I piloti che partecipano alla gara 
	 *  -Evento dato dal passaggio del giro
	 *  -Dobbiamo trovare un modo per mappare l'arrivo -- si potrebbe utilizzare una Lista(?)
	 */

	

	private PriorityQueue<LapTime> queue;
	private Map<Integer, Driver> idMapDriver;
	private List<Stats> statistiche;
	private int contatore;
	
	public void init(PriorityQueue<LapTime> eventi, Map<Integer, Driver> idMapDriver) {
		
		
		this.queue = new PriorityQueue<>(eventi);
		System.out.println(queue);
		this.idMapDriver = idMapDriver;
		this.statistiche = new LinkedList<>();
		
		//puliamo le statistiche
		
		for(Integer d : idMapDriver.keySet()) {

			statistiche.add(new Stats(d));

		}
		
		this.contatore = 0;
		
		//essenzialmente abbiamo un solo tipo di evento
		
	}

	public void run() {
	
		if(queue==null)
			System.out.println("Errore nella simulazione");
		else {
			
			
			while(!queue.isEmpty()) {
				
				//vado prendendo gli eventi
				
				/*
				 * L'idea di base è che essendo gli eventi
				 * in ordine di arrivo il primo elemento avente un lap maggiore 
				 * del contatore contiene il driver che ha tagliato il traguardo
				 */
				
				LapTime lp = queue.poll();
				
				
				// il primo pilota del giro successivo taglia il traguardo 
				
				if(lp.getLap()>contatore) {
					
				    for(Stats stats : statistiche) {
				    	if(stats.getDriverId() == lp.getDriverId()) {
				    		stats.aggiornaPunti();
				    		break;
				    	}
				    }
					contatore++;
					
				}
				
			}
			
		}
		
	}

	public String getStatistiche() {
		
		
	    if(!statistiche.isEmpty()) {
			
	    	String ris = "";
	    	
	    	Collections.sort(statistiche);
	    	
	    	for(Stats stats : statistiche ) 
	    			ris+=idMapDriver.get(stats.getDriverId())+"  Punteggio: "+stats.getPunti()+"\n";
	    	
	    	return ris;
	    	
	    	
		}
		
		return null;
	}

}
