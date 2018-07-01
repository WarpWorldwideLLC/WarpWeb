<%@ include file="/htx/pagehead.html"%>



<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/htx/html.head.html"%>
	    <title><fmt:message key="landing.page_title" /></title>
	</head>

<body >
	<!--  BODY.HEADER -->
	<header>		
		<%@ include file="/htx/menu.html"%>
	</header>
	<!--  BODY.FORM  -->
	<form method="post" action="landing" name="landing" autocomplete="off">
	
<div id="myCarousel" class="carousel slide" data-ride="carousel">
  <!-- Indicators -->
  <ol class="carousel-indicators">
    <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
    <li data-target="#myCarousel" data-slide-to="1"></li>
    <li data-target="#myCarousel" data-slide-to="2"></li>
  </ol>

  <!-- Wrapper for slides -->
  <div class="carousel-inner">
  <!-- 
    <div class="item active">
      <img src="imgx/1_Panda.png" alt="Los Angeles">
      <div class="carousel-caption">
        <h3>Los Angeles</h3>
        <p>LA is always so much fun!</p>
      </div>
    </div>
 -->
 
 	<!-- 
    <div class="item active">
      <img src="imgx/1600x650-grace2.jpg" alt=<fmt:message key="landing.carousel.grace_alt" />>
    </div>
    -->
    <div class="item active">
      <img src="imgx/Warp_Image_Hands2-1600-650.jpg" alt=<fmt:message key="landing.carousel.hands_alt" />>
    </div>
    
    <div class="item">
      <img src="imgx/Warp_Image_Shutterstock_Success-1600-650-Resize.jpg" alt=<fmt:message key="landing.carousel.success_alt" />>
    </div>
    
    <div class="item">
      <img src="imgx/Warp_Image_Shutterstock_English-1600-650-Resize.jpg" alt=<fmt:message key="landing.carousel.english_alt" />>
    </div>
  <!--  
    <div class="item">
      <img src="imgx/Warp_Image_Shutterstock_Chinese-1600-650-Resize.jpg" alt=<fmt:message key="landing.carousel.chinese_alt" /> >
    </div>
  -->    
  </div>

  <!-- Left and right controls -->
  <a class="left carousel-control" href="#myCarousel" data-slide="prev">
    <span class="glyphicon glyphicon-chevron-left"></span>
    <span class="sr-only"><fmt:message key="landing.carousel.chevron_left" /></span>
  </a>
  <a class="right carousel-control" href="#myCarousel" data-slide="next">
    <span class="glyphicon glyphicon-chevron-right"></span>
    <span class="sr-only"><fmt:message key="landing.carousel.chevron_right" /></span>
  </a>

  <!--  
    <a class="right carousel-control" href="#myCarousel" data-slide="next">
    <span class="glyphicon glyphicon-chevron-right"></span>
    <span class="sr-only">Next</span>
   </a>
  -->
</div> <!--  Carousel -->
	


<div class="warpRow" >
  	<!--  ELL  -->
  	<div class="col-sm-4 warpItem">
		<h2><a href="product?productPage=warp_esl"><fmt:message key="landing.ell_header" /></a></h2>
		<p>
			<a href="product?productPage=warp_esl">
				<img class="myImage" src="imgx/esl_800x450-3.jpg" alt=<fmt:message key="landing.ell_alt" /> /><br />
			</a>
			<b><p><fmt:message key="landing.ell_text3" /></p></b>
			<b><p><fmt:message key="landing.ell_text4" /></p></b>
			<p><fmt:message key="landing.ell_text1" /></p>
			<p><fmt:message key="landing.ell_text2" /></p>

			
		</p>
	</div>
	
	<!--  STEM -->
	<div class="col-sm-4 warpItem">
		<h2><a href="product?productPage=warp_stem"><fmt:message key="landing.stem_header" /></a></h2>
		<!-- 	<p><img class="warpImage" src="http://warp.zaisscodev2.info/wp-content/uploads/2018/03/800x450-2.jpg" alt="" /><br />  -->
		<p>
			<a href="product?productPage=warp_stem">
				<img class="myImage" src="imgx/Warp_Image_Shutterstock_STEM-800-450-Resize.jpg" alt=<fmt:message key="landing.stem_alt" /> />
			</a>
			<b><p><fmt:message key="landing.stem_text4" /></p></b>
			<b><p><fmt:message key="landing.stem_text5" /></p></b>
			<p><fmt:message key="landing.stem_text1" /></p>
			<p><fmt:message key="landing.stem_text2" /></p>
			<p><fmt:message key="landing.stem_text3" /></p>
		</p>
	</div>
	
	<!-- US Greenpath -->
	<!-- class="wpb_animate_when_almost_visible wpb_fadeIn fadeIn wpb_column vc_column_container vc_col-sm-4" -->
	
	<div class="col-sm-4 warpItem">
		<h2><a href="product?productPage=warp_stem"><fmt:message key="landing.greenpath_header" /></a></h2>
		<p>
			<a href="product?productPage=warp_greenpath">
				<img class="myImage" src="imgx/college-learning-800x450-1.jpeg" alt=<fmt:message key="landing.prodev_alt" /> />
			</a>
			<p><fmt:message key="landing.greenpath_text2" /></p>
			<p><fmt:message key="landing.greenpath_text3" /></p>
			<p><fmt:message key="landing.greenpath_text1" /></p>

		</p>
	</div>
	
	<!-- Cultural Camps -->
	<!-- class="wpb_animate_when_almost_visible wpb_fadeIn fadeIn wpb_column vc_column_container vc_col-sm-4" -->
	<!--  
	<div class="col-sm-4 warpItem">
		<h2><a href="product?productPage=warp_camps"><fmt:message key="landing.camps_header" /></a></h2>
		<p>
			<a href="product?productPage=warp_camps">
				<img class="myImage" src="imgx/college-learning-800x450-1.jpeg" alt=<fmt:message key="landing.camps_alt" /> />
			</a>
			<p><fmt:message key="landing.camps_text1" /></p>
			<p><fmt:message key="landing.camps_text2" /></p>
			<p><fmt:message key="landing.camps_text3" /></p>

		</p>
	</div>
	-->
</div>


 <!--  warpRow -->
	</form>
	<!-- BODY.FOOTER -->
    <footer>
    
    </footer>
</body>

</html>