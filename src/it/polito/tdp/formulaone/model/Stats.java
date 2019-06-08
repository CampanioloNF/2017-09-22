package it.polito.tdp.formulaone.model;

public class Stats implements Comparable<Stats>{

	
         private int driverId;
         private int punti;
		
         public Stats(int driverId) {
			super();
			this.driverId = driverId;
			this.punti = 0;
		}

		public int getDriverId() {
			return driverId;
		}

		public int getPunti() {
			return punti;
		}
         
        public void aggiornaPunti() {
        	this.punti++;
        }

		@Override
		public int compareTo(Stats o) {
			// TODO Auto-generated method stub
			return o.punti-this.punti;
		}
}
