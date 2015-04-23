$(document).ready(function() {
	var map;
	var currPos;
	var places = [];
	var showIntro = true;
	
	function initialize() {
	  var mapOptions = {
	    zoom: 15
	  };
	  map = new google.maps.Map(document.getElementById('map-container'),
	      mapOptions);
	
	  // Try HTML5 geolocation
	  if(navigator.geolocation) {
	    navigator.geolocation.getCurrentPosition(function(position) {
	      var pos = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
		  
		  currPos = new google.maps.Circle({
		  	map: map,
		  	center: pos,
		  	clickable: false,
		  	radius: 20,
		  	strokeColor: "#2EB9FF",
		  	fillColor: "#2EB9FF",
		  	fillOpacity: 0.5,
		  	strokeWeight: 3,
		  });
	      map.setCenter(pos);
	    }, function() {
	      handleNoGeolocation(true);
	    });
	  } else {
	    // Browser doesn't support Geolocation
	    handleNoGeolocation(false);
	  }
	  
	  getAllPlaces();
	}
	
	function handleNoGeolocation(errorFlag) {
	  if (errorFlag) {
	    var content = 'Error: The Geolocation service failed.';
	  } else {
	    var content = 'Error: Your browser doesn\'t support geolocation.';
	  }
	
	  var options = {
	    map: map,
	    position: new google.maps.LatLng(41.826206, -71.403273),
	    content: content
	  };
	
	  var infowindow = new google.maps.InfoWindow(options);
	  map.setCenter(options.position);
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
				//console.log("place: " + places[i]);
			}
		})
	}
	
	function getAllPlaces() {
		$.post("/getAllPlaces", function(responseJSON) {
			var allPlaces = JSON.parse(responseJSON);
			allPlaces.forEach(function(p) {
				places.push(p);
				var point = new google.maps.LatLng(p.location.lat, p.location.lng);
				var link = "/" + p.id;
				console.log(link);
				var marker = new google.maps.Marker({
					position: point,
					map: map,
				});
				console.log("link " + link);
				console.log("pic " + p);
				var text = "<a href='" + link + "' class='h-text'>"+p.name+"</a><img class='thumbnail' src='../assets/"+p.picture+"' >";
				google.maps.event.addListener(marker, 'click', function() {
			      	new google.maps.InfoWindow({
			      		map: map,
			      		position: point,
			      		content: text,
			      		maxWidth: 150,
			      	});
			    });
			});
		})
	}
	
	
	google.maps.event.addDomListener(window, 'load', initialize);
});