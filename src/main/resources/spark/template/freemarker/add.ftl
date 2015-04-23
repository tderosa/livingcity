<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/html5bp.css">
    <link rel="stylesheet" href="css/stylesheet.css">
    <link rel="stylesheet" href="css/main.css">
  </head>
  <body>
     <div class="container">
       <p class="title">Living City</p>
       <a href="/"><img id="back-btn-addform" src="../assets/back.png"></a>
       <div id="form-hdr">${name}</div>
       <form class="form" method="POST" action="/add">
       	<input type="hidden" name="id" value=${id}>
       	Your Story:<br>
       		<input type="text" name="storyText" placeholder="Your Story*"><br>
       		Date of story:
       		<input type="text" name="month" placeholder="Month">
       		<input type="text" name="year" placeholder="Year*">
        <br>
       	About You:<br>
       		<input type="text" name="author" placeholder="Your Name*"><br>
       		<input type="text" name="authorAbt" placeholder="More about you"><br>
       		<span class="footnote">* Required</span>
       	<button class="btn" type="submit" value="Submit">
       	</form>
     </div>
     <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB4mxarX8cOcCQ2DIKZK1w9hBF8T3j1poo"></script>
     <script src="js/jquery-2.1.1.js"></script>
     <script src="js/main.js"></script>
  </body>
</html>