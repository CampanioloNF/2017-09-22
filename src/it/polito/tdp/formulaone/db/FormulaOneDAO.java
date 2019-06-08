package it.polito.tdp.formulaone.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;


import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.formulaone.model.Driver;
import it.polito.tdp.formulaone.model.LapTime;
import it.polito.tdp.formulaone.model.Race;
import it.polito.tdp.formulaone.model.Season;

public class FormulaOneDAO {

	public List<Season> getAllSeasons() {
		String sql = "SELECT year, url FROM seasons ORDER BY year";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			List<Season> list = new ArrayList<>();
			while (rs.next()) {
				list.add(new Season(rs.getInt("year"), rs.getString("url")));
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void getAllVertexs(Graph<Race, DefaultWeightedEdge> graph, Map<Integer, Race> idMapRace, int year) {
		
		String sql = "SELECT * FROM races WHERE year = ? ";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, year);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				
				if(!idMapRace.containsKey(rs.getInt("raceId"))) {
					
					Race vertice = new Race(rs.getInt("raceId"), rs.getInt("year"), rs.getInt("round"), 
							rs.getInt("circuitId"), rs.getString("name"), rs.getDate("date").toLocalDate(), 
							null, rs.getString("url"));
					
					idMapRace.put(rs.getInt("raceId"), vertice);
					
					//aggiungiamo il vertice
					
					graph.addVertex(vertice);
					
				}
				
			}
			conn.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
	
		}
		
		
	}
	
	/*
	 * Forse una scelta più saggia era elaborare direttamente in Java
	 */

	public void getAllEdges(Graph<Race, DefaultWeightedEdge> graph, Map<Integer, Race> idMapRace, int year) {

		String sql = "SELECT re1.raceId, re2.raceId, COUNT(*) AS peso " + 
				"FROM results re1, results re2, races ra1, races ra2 " + 
				"WHERE ra1.YEAR = ? AND  ra2.YEAR = ra1.YEAR " + 
				"AND re1.raceId = ra1.raceId AND re2.raceId = ra2.raceId AND ra2.raceId > ra1.raceId " + 
				"AND re1.driverId = re2.driverId  AND re1.statusId = re2.statusId AND re1.statusId =1 " + 
				"GROUP BY  re1.raceId, re2.raceId ";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, year);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				
				if(idMapRace.containsKey(rs.getInt("re1.raceId")) && idMapRace.containsKey(rs.getInt("re2.raceId"))) {
					
					Graphs.addEdge(graph,
							idMapRace.get(rs.getInt("re1.raceId")), idMapRace.get(rs.getInt("re2.raceId")), 
							rs.getInt("peso"));
					
				}
				else
					System.out.println("Errore vertice inesistente");
				
			}
			conn.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
	
		}
		
		
	}

	public void getDrivers(Race race, Map<Integer, Driver> idMapDriver) {
	

		String sql = "SELECT d.driverId, d.driverRef, d.NUMBER, d.CODE, d.forename, d.surname, d.dob, d.nationality, d.url " + 
				"FROM drivers d, results r " + 
				"WHERE d.driverId = r.driverId AND r.raceId = ? ";
		
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, race.getRaceId());
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				
				if(!idMapDriver.containsKey(rs.getInt("d.driverId"))) {
					
					idMapDriver.put(rs.getInt("d.driverId"), 
							new Driver(rs.getInt("d.driverId"), rs.getString("d.driverRef"), rs.getInt("d.NUMBER"), 
									rs.getString("d.CODE"), rs.getString("d.forename"), rs.getString("d.surname"), 
									rs.getDate("d.dob").toLocalDate(), rs.getString("d.nationality"), rs.getString("d.url")));
				}
				
				
			}
			conn.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
	
		}
		
	}

	public void getAllLapTime(Race race, Map<Integer, Driver> idMapDriver, PriorityQueue<LapTime> eventi, double p, int t) {
		
		System.out.println(race.getRaceId());
		
		String sql = "SELECT * FROM laptimes WHERE raceId = ? ORDER BY lap, POSITION";
		
		
		
		try {
		
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, race.getRaceId());
			
			ResultSet rs = st.executeQuery();
			
			//problema nell'entrare dentro al while
			
			while (rs.next()) {
				
				/*
				 * Serve un modo per cercare velocemente 
				 * All'interno dei LapTime aggiunti
				 */
				
			
				
				  //verifico che il dirver esista
				if(idMapDriver.containsKey(rs.getInt("driverId"))) {
				
					
					
					//salvo i dati della query
				   int raceId = race.getRaceId();
			       int driverId = rs.getInt("driverId");
				   int lap = rs.getInt("lap");
				   
				   int position = rs.getInt("position");
				   String time = rs.getString("time");
				   int miliseconds = rs.getInt("milliseconds");
				   
				   if(p>Math.random())
					   miliseconds+=t*1000;
				   
					// se sono al primo giro
				   if(lap == 1) {
					   
					   //creo un evento
					   LapTime lp = new LapTime(raceId, driverId, lap, position, time, miliseconds, 0);
					   eventi.add(lp);
				   }
				
				   else {
					   
					   //devo andarmi a cercare il giro di prima
					   
					   LapTime giroPrima = null;
					   
					   for(LapTime lp1 : eventi) {
						   if(lp1.getDriverId() == driverId  && 
								    lp1.getLap() == lap-1) {
							   giroPrima=lp1;
							   break;
						   }
					   }
					   
					   if(giroPrima == null)
						   System.out.println("Comportamento Strano");
					   
					   else {
						   
						   //avento il rifermento al precedente andiamo con il prossimo giro
						   LapTime lp = new LapTime(raceId, driverId, lap, position, time, miliseconds, giroPrima.getIndex());
						   eventi.add(lp);
					   }
					   
				   }
					
					
				
					
						
			}
		
		}		
			conn.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("NO ok");
		}
	
	}
}
