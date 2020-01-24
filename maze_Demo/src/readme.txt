the mdp codes from this website

https://www.programcreek.com/java-api-examples/index.php?source_dir=DistSysDesign-master/eclipseWorkspace/aima-core/src/main/java/aima/core/probability/mdp/MarkovDecisionProcess.java#





//document.getElementById("myDiv").innerHTML=
//document.getElementById("myDiv").innerHTML=
//alert("current direction is :"+direction);
//alert("directionNext is :"+directionNext);

function loadXMLDoc(){
	var xmlhttp;
	if (window.XMLHttpRequest)
	{
		//  IE7+, Firefox, Chrome, Opera, Safari ä¯ÀÀÆ÷Ö´ÐÐ´úÂë
		xmlhttp=new XMLHttpRequest();
	}
	else{
		// IE6, IE5 ä¯ÀÀÆ÷Ö´ÐÐ´úÂë
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function(){
		if (xmlhttp.readyState==4 && xmlhttp.status==200){
			document.getElementById("myDiv").innerHTML=xmlhttp.responseText;
		}
	}
	xmlhttp.open("GET","/try/ajax/ajax_info.txt",true);
	xmlhttp.send();
}







function moveWithDirection(directionNext){
	switch(directionNext){
	case "left":
		if(direction == "left"){ 
			if(!isBoundary(xcenter,ycenter,interval,"left")){
				clearCurrent(xcenter,ycenter,innerDist);
				xcenter -= interval;
				direction = "left";
				goFoward = true;
				arrowDraw(xcenter,ycenter,innerDist,direction);
				drawForward(xcenter,ycenter,innerDist,direction,goFoward);
			}else{
				goFoward = false;
				drawForward(xcenter,ycenter,innerDist,direction,goFoward);
			}
		}else{//if the direction is not the same, change the direction first.
			direction = "left";
			clearCurrent(xcenter,ycenter,innerDist);
			arrowDraw(xcenter,ycenter,innerDist,direction);
		}break;
	case "up":
		if(direction == "up"){
			if(!isBoundary(xcenter,ycenter,interval,"up")){
				clearCurrent(xcenter,ycenter,innerDist);
				ycenter -= interval;
				direction = "up";
				goFoward = true;
				arrowDraw(xcenter,ycenter,innerDist,direction);
				drawForward(xcenter,ycenter,innerDist,direction,goFoward);
			}else{
				//alert("you arrived the boundary of the evironment");
				goFoward = false;
				drawForward(xcenter,ycenter,innerDist,direction,goFoward);
			}
		}else{//if the direction is not the same, change the direction first.
			direction = "up";
			clearCurrent(xcenter,ycenter,innerDist);
			arrowDraw(xcenter,ycenter,innerDist,direction);
		}break;
	case "right":
		if(direction == "right"){
			//alert("moveWithDirection");
			if(!isBoundary(xcenter,ycenter,interval,"right")){
				clearCurrent(xcenter,ycenter,innerDist);
				xcenter += interval;
				direction = "right";
				goFoward = true;
				arrowDraw(xcenter,ycenter,innerDist,direction);
				drawForward(xcenter,ycenter,innerDist,direction,goFoward);
			}else{
				//alert("you arrived the boundary of the evironment");
				goFoward = false;
				drawForward(xcenter,ycenter,innerDist,direction,goFoward);
			}
		}else{//if the direction is not the same, change the direction first.
			direction = "right";
			clearCurrent(xcenter,ycenter,innerDist);
			arrowDraw(xcenter,ycenter,innerDist,direction);
		}break;
	case "down":
		if(direction == "down"){
			if(!isBoundary(xcenter,ycenter,interval,"down")){
				clearCurrent(xcenter,ycenter,innerDist);
				ycenter += interval;
				direction = "down";
				goFoward = true;
				arrowDraw(xcenter,ycenter,innerDist,direction);
				drawForward(xcenter,ycenter,innerDist,direction,goFoward);
			}else{
				//alert("you arrived the boundary of the evironment");
				goFoward = false;
				drawForward(xcenter,ycenter,innerDist,direction,goFoward);
			}
		}else{//if the direction is not the same, change the direction first.
			direction = "down";
			clearCurrent(xcenter,ycenter,innerDist);
			arrowDraw(xcenter,ycenter,innerDist,direction);
		}break;
	}
}






/*function start(){
	//alert("alert start function");
	var xmlhttp;
	if (window.XMLHttpRequest){
		//  IE7+, Firefox, Chrome, Opera, Safari ä¯ÀÀÆ÷Ö´ÐÐ´úÂë
		xmlhttp=new XMLHttpRequest();
	}
	else{// IE6, IE5 ä¯ÀÀÆ÷Ö´ÐÐ´úÂë
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.open("GET","/maze/AjaxServlet?direction="+direction+"&fromServlet="+fromServlet,true);
	xmlhttp.onreadystatechange=function(){
		if (xmlhttp.readyState==4 && xmlhttp.status==200){
			actionNext = xmlhttp.responseText;
			var enactSate = enactAction(actionNext);
			document.getElementById("actionId").value = actionNext;
			setTimeout('start()',1000);
		}
	}
	xmlhttp.send();
}*/




//alert(nextPosX, nextPosY);
	//alert(ctx.getImageData(nextPosX, nextPosY, 1, 1).data);
	//alert("25, 25");
	//alert(ctx.getImageData(25, 25, 1, 1).data);
	
	
	
	
	
	
//response.getWriter().append("Served at: ").append(request.getContextPath());
		//String direction = request.getParameter("direction");
		//System.out.println("The current direction is:"+direction);
		//System.out.println(fromServlet);
		//direction = "right";
		//fromServlet = true;