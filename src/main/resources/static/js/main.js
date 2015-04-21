$(document).ready(function() {
 	var map = null;
 	
	function initialize() {
	    var mapOptions = {
	      center: {lat: 41.826206, lng: -71.403273},
	      zoom: 16,
	    };
	    map = new google.maps.Map(document.getElementById('map-container'), mapOptions);
	    getAllPlaces();
	    console.log("hey");
	}
	
	function getPlaces() {
		var bounds = map.getBounds();
	    var NE = bounds.getNorthEast();
	    var SW = bounds.getSouthWest();;
	    var postParameters = {latNE: NE.lat(), lngNE: NE.lng(), latSW: SW.lat(), lngSW: SW.lng()};
	    console.log(postParameters);
	    $.post("/getPlaces", postParameters, function(responseJSON) {
			var places = JSON.parse(responseJSON);
			for (var i=0; i<places.length; i++) {
				console.log("place: " + places[i]);
			}
		})
	}
	
	function getAllPlaces() {
		$.post("/getAllPlaces", function(responseJSON) {
			var places = JSON.parse(responseJSON);
			for (var i=0; i<places.length; i++) {
				console.log("place: " + places[i]);
			}
		})
	}
	
	google.maps.event.addDomListener(window, 'load', initialize);
});