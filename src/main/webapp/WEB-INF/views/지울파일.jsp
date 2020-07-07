<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript">
		var paintMode = [
		  "point",
		  "line",
		  "circle",
		  "filledcircle",
		  "square",
		  "filledsquare",
		  "rect",
		  "filledrect",
		  "tri",
		  "filledtri",
		  "ellipse",
		  "filledellipse",
		  "pencil_begin",
		  "pencil_end"
		];
		var toolTable = {
				  pencil: 0,
				  line: 1,
				  circle: 2,
				  filledcircle: 3,
				  square: 4,
				  filledsquare: 5,
				  rect: 6,
				  filledrect: 7,
				  tri: 8,
				  filledtri: 9,
				  ellipse: 10,
				  filledellipse: 11
				};

		var pointShape = {
		  mouseDown: pointMouseDown,
		  mouseMove: pointMouseMove,
		  mouseUp: pointMouseUp
		};

		var shapeList = [pointShape];

		var paintMouseDownAction = {
		  point: pointMouseDown,
		  line: lineMouseDown,
		  circle: circleMouseDown,
		  filledcircle: circleMouseDown,
		  square: squareMouseDown,
		  filledsquare: squareMouseDown,
		  rect: rectMouseDown,
		  filledrect: rectMouseDown,
		  tri: triMouseDown,
		  filledtri: triMouseDown,
		  ellipse: ellipseMouseDown,
		  filledellipse: ellipseMouseDown
		};

		function mouseListener(event) {
			  switch (event.type) {
			    case "mousedown":
			      if (!pos.isDraw) {
			        pos.mouseDownAction(event);
			      }
			      break;
			    case "mousemove":
			      if (pos.isDraw) {
			        pos.mouseMoveAction(event);
			      }
			      break;
			    case "mouseup":
			    case "mouseout":
			      if (pos.isDraw) {
			        pos.mouseUpAction(event);
			      }
			      break;
			  }
			}
		function mouseListener(event) {
			  switch (event.type) {
			    case "mousedown":
			      if (!pos.isDraw) {
			        pos.mouseDownAction(event);
			      }
			      break;
			    case "mousemove":
			      if (pos.isDraw) {
			        pos.mouseMoveAction(event);
			      }
			      break;
			    case "mouseup":
			    case "mouseout":
			      if (pos.isDraw) {
			        pos.mouseUpAction(event);
			      }
			      break;
			  }
			}
		function onLoadPage() {
			  canvas = document.getElementById("canvas");
			  cvs = canvas.getContext("2d");

			  bufCanvas = document.createElement("canvas");
			  bufCanvas.width = canvas.width;
			  bufCanvas.height = canvas.height;
			  bufCtx = bufCanvas.getContext("2d");

			  canvas.addEventListener("mousedown", mouseListener);
			  canvas.addEventListener("mousemove", mouseListener);
			  canvas.addEventListener("mouseout", mouseListener);
			  canvas.addEventListener("mouseup", mouseListener);

			  initPage();
			}
</script>
</body>
</html>