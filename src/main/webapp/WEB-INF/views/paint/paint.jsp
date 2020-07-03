<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
  <head>
<!-- <link href="dist/css/bootstrap-colorpicker.css" rel="stylesheet"> -->
 <link href="dist/css/style.min.css" rel="stylesheet">
    <title>그림판</title>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
    <style>
    	.paintcellborder{
		border:1px solid grey;
    	}
		i {
		padding-top:12px;
		padding-right:12px;
		padding-left:12px;
		padding-bottom:12px;
		}
		.pad{
		padding-top:10px;
		padding-right:10px;
		padding-left:10px;
		padding-bottom:10px;
		}
		.padd{
		padding-top:60px;
		}
      canvas {
        border: 1px solid grey;
      }
		
      .jb_table {
        display: table;
      }

      .row {
        border-radius: 10px;
        display: table-row;
      }

      .cell {
        display: table-cell;
        vertical-align: top;
      }

      textarea {
        background-color: #fcf3cf;
      }
    </style>


  </head>

  <body>
  
    <div class="jb_table">
      <div class="row">
        
        <span class="cell">
          <div>
            <canvas id="canvas" width="720" height="720"></canvas>
          </div>
        </span>
        <span class="cell">
        <!--   <INPUT type="file" id="load_filename" value="Load" onChange="loadFile()" /> -->
       <!--   <INPUT type="button" value="Redraw" onClick="reDrawCanvas()" />-->
       
          <div>
            <div class="jb_table">
              <div class="row">
                <span class="cell">
                  <img src="../../../dist/img/red.png" class="colorChip" onclick="selectColor('red')" />
                  <img src="../../../dist/img/brown.png" class="colorChip" onclick="selectColor('#FFC7EC')"/>
                </span>
              </div>
              <div class="row">
                <span class="cell">
                   <img src="../../../dist/img/orange.png" class="colorChip" onclick="selectColor('#FF8800')"/>
                   <img src="../../../dist/img/pink.png" class="colorChip" onclick="selectColor('#FF61CA')"/>    		
                </span>
              </div>
              <div class="row">
                <span class="cell">
                  <img src="../../../dist/img/yellow.png" class="colorChip" onclick="selectColor('#FFF705')" />
                  <img src="../../../dist/img/purple.png" class="colorChip" onclick="selectColor('darkviolet')" />
                </span>
              </div>
              <div class="row">
                <span class="cell">
                  <img src="../../../dist/img/lightgreen.png" class="colorChip" onclick="selectColor('lightgreen')"/>
                  <img src="../../../dist/img/white.png" class="colorChip" onclick="selectColor('white')"/>
                </span>
              </div>
              <div class="row">
                <span class="cell">
                 <img src="../../../dist/img/green.png" class="colorChip" onclick="selectColor('green')"/>
                 <img src="../../../dist/img/lightgray.png" class="colorChip" onclick="selectColor('lightgray')"/>
                </span>
              </div>
              <div class="row">
                <span class="cell">
                <img src="../../../dist/img/lightblue.png" class="colorChip" onclick="selectColor('#3DB8FF')" />
                <img src="../../../dist/img/gray.png" class="colorChip" onclick="selectColor('gray')" />
                </span>
              </div>
              <div class="row">
                <span class="cell">
              	   <img src="../../../dist/img/blue.png" class="colorChip" onclick="selectColor('blue')" />
                   <img src="../../../dist/img/black.png" class="colorChip" onclick="selectColor('black')"/>
                </span>
              </div>
              <div class="row">
                  <span class="cell">
                  <input id="colorPicker" type="color" class="form-control" value="#BDB3FF">
 				<!--   <i class="fas fa-eye-dropper"></i>    -->
                  </span>
              </div>
                 <div class="row">
                 <span class="cell">
                  <i class="fas fa-pencil-alt" onclick="selectTool('pencil')"></i>
                  <i class="fas fa-minus" onclick="selectTool('line')"></i>
                </span>
          	    </div> 
            <!--   <div class="row">
                <span class="cell">
                  <img src="../../../dist/img/pencil.png"  onclick="selectTool('pencil')" />
                  <img src="../../../dist/img/line.png"  onclick="selectTool('line')" />
                </span>
              </div> -->
              <div class="row">
                 <span class="cell">
                  <i class="far fa-circle" onclick="selectTool('circle')"></i>
                  <i class="fas fa-circle" onclick="selectTool('filledcircle')"></i>
                </span>
          	    </div> 
     	        <div class="row">
                 <span class="cell">
                  <i class="far fa-square" onclick="selectTool('square')"></i>
                  <i class="fas fa-square" onclick="selectTool('filledsquare')"></i>
                </span>
          	    </div>
               <div class="row">
                <span class="cell">
                  <img src="../../../dist/img/triangles.png" class="pad" onclick="selectTool('tri')" />
                  <img src="../../../dist/img/filltriangle.png" class="pad" onclick="selectTool('filledtri')" />
                </span>
              </div>
         	<div class="row">
                <span class="cell">
                  <img src="../../../dist/img/bucket.png" class="pad" id="fill"  />
                </span>
              </div>
 <!--             <div class="row">
                <span class="cell">
                  <img src="../../../dist/img/circle.png" onclick="selectTool('circle')"/>
                  <img src="../../../dist/img/filledcircle.png" onclick="selectTool('filledcircle')"/>
                </span>
              </div>
              <div class="row">
                <span class="cell">
                  <img src="../../../dist/img/ellipse.png" onclick="selectTool('ellipse')"/>
                  <img src="../../../dist/img/filledellipse.png" onclick="selectTool('filledellipse')"/>
                </span>
              </div>
              <div class="row">
                <span class="cell">
                  <img src="../../../dist/img/square.png" onclick="selectTool('square')"/>
                  <img src="../../../dist/img/filledsquare.png" onclick="selectTool('filledsquare')"/>
                </span>
              </div>
              <div class="row">
                <span class="cell">
                  <img src="../../../dist/img/rect.png" onclick="selectTool('rect')" />
                  <img src="../../../dist/img/filledrect.png" onclick="selectTool('filledrect')" />
                </span>
              </div> -->

              <div class="row">
                  <span class="cell">
                   <i class="fas fa-angle-double-left" onclick="undo()"></i>
                   <i class="fas fa-angle-double-right" onclick="redo()"></i>
                  </span>
              </div>
              
          <div> <i class="fas fa-quidditch" onClick="initPage()"></i> Clear</div>
          <div class="padd"></div>
          <div>Title <input id="title" size="10" /></div>
          <div>
            <a id="saveImage" download="image.png">
                <i class="fas fa-save" onClick="saveImage()"></i>
            </a>&nbsp;Save</div>
           <!--  <INPUT type="button" value="History" onClick="showHistory()" /> -->
          <div>
            <textarea id="history" cols="40" rows="37" style="display: none;"></textarea>
          </div>
          
            </div>
          </div>
        </span>
      </div>
    </div>
    
    <script src="../../../dist/js/painter.js"></script>
    <script src="../../../dist/js/drawengine.js"></script>
    <!-- 컬러피커 -->
<!--     <input id="colorPicker" type="color" class="form-control" value="#ffffff"> -->
	
    <script language="JavaScript">
    
/*       var textareaList = ["history"]; */
      function clearText(idOfTextArea) {
        document.getElementById(idOfTextArea).value = "";
      }
	 	$('.colorChip').click(function() {
		 	$('.colorChip').removeClass('paintcellborder');
			$(this).addClass('paintcellborder');
		});
      
		$('#colorPicker').change(function() {
			selectColor($('#colorPicker').val());
			pos.color=$('#colorPicker').val();
		});

/* 		$('.cell').click(function() {
			$('#colorPicker').val()=selectColor;
			console.log('ddddd');
		}); */
      
      var canvas = document.querySelector("#canvas");
      var ctx = canvas.getContext("2d");
      
      $("#fill").click ( function () {
           ctx.fillStyle=pos.color;
          console.log($('selectColor').val());
          ctx.fillRect(0, 0, 720, 720);
      });


 
   </script>
  </body>
</html>








