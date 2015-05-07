<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>Living City</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="../assets/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="../css/normalize.css">
    <link rel="stylesheet" href="../css/html5bp.css">
    <link rel="stylesheet" href="../css/stylesheet.css">
    <link rel="stylesheet" href="../css/main.css">
  </head>
  <body>
     <div class="container">
     <a class="back-btn" href="/"><img class="back-img" src="../assets/back.png"></a>
     <img class="title" src="../assets/logo.png">
     <div class="name">${name}</div>
     <img class="place-img" src=${picture}>
     <div class="place-options">
     	<p class="option" id="intro-btn">INTRO</p>
     	<p class="option" id="stories-btn">STORIES</p>
     </div>
     <p id="intro">${intro}</p>
     <div id="stories">
     	<a href=${addLink} class="btn" id="add-btn">Add a Story</a>
     	<p class="h-text" id="stories-title">Recent Stories...</p>
     	<div id="stories-list">${stories}</div>
     </div>
   </div>
     <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB4mxarX8cOcCQ2DIKZK1w9hBF8T3j1poo"></script>
     <script src="../js/jquery-2.1.1.js"></script>
     <script src="../js/place.js"></script>
     <script src="../js/main.js"></script>
  </body>
</html>