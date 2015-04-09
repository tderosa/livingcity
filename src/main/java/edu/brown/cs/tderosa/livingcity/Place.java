package edu.brown.cs.tderosa.livingcity;

import edu.brown.cs.tderosa.utils.LatLng;

public class Place implements LatLng {
	private Double lat, lng;
	private String id, description;
	
	public Place(String id) {
		this.id = id;
	}

	@Override
	public Double lat() {
		return lat;
	}

	@Override
	public Double lng() {
		return lng;
	}

	@Override
	public void setLat(Double newLat) {
		lat = newLat;
	}

	@Override
	public void setLng(Double newLng) {
		lng = newLng;
	}
}
