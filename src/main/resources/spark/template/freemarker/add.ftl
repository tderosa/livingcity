<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../css/normalize.css">
    <link rel="stylesheet" href="../css/html5bp.css">
    <link rel="stylesheet" href="../css/stylesheet.css">
    <link rel="stylesheet" href="../css/main.css">
  </head>
  <body>
     <div class="container">
       <p class="title">Living City</p>
       <a class="back-btn" href=${placeLink}><img class="back-img" src="../assets/back.png"></a>
       <div class="hdr-box">
       <p id="form-hdr">A story surrounding</p>
       <p id="place-name">${name}</p>
       </div>
       <form class="form" method="POST" action="/add">
       	<input type="hidden" name="id" value=${id}>
       	<span class='subheader'>Your Story:</span><br>
       		<textarea rows="10" class="input" id="storyText" name="storyText" placeholder="Your Story*"></textarea><br>
       		<div class="form-date">
       		<div id="date-label">Date of story:</div>
       		<input class="input" id="month" type="text" name="month" placeholder="Month">
       		<input class="input" id="year" type="text" name="year" placeholder="Year*">
       		</div>
        <br><br>
       	<span class='subheader'>About You:</span><br>
       		<input class="input" type="text" name="author" placeholder="Your Name*"><br>
       		<textarea class="input" rows="5" name="authorAbt" placeholder="More about you"></textarea><br>
       		<span class="footnote">* Required</span><br>
       	<input id="add-form-btn" class="btn" type="submit" value="Submit">
       	</form>
     </div>
     <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB4mxarX8cOcCQ2DIKZK1w9hBF8T3j1poo"></script>
     <script src="../js/jquery-2.1.1.js"></script>
     <script src="../js/main.js"></script>
  </body>
</html>