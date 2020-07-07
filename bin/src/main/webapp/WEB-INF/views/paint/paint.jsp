<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
  <head>
<link href="dist/css/bootstrap-colorpicker.css" rel="stylesheet">
    <title>그림판</title>
    <style>
      canvas {
        border: 1px solid blue;
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
    <script language="JavaScript">
      // Date: 2019.04.24

      var textareaList = ["history"];

      function clearText(idOfTextArea) {
        document.getElementById(idOfTextArea).value = "";
      }

      function SaveAsTxt() {
        var fileName = document.getElementById("title").value;
        if (fileName.length == 0) {
          fileName = "image";
        }
        fileName += ".txt";

        var preData = 'version: V0.617a1\n';
        var postData =  preData + document.getElementById("history").value;

        var link = document.createElement("a");
        link.setAttribute("download", fileName);
        link.setAttribute(
          "href",
          "data:" +
            "application/[txt]" +
            ";charset=utf-8," +
            encodeURIComponent(postData)
        );
        link.click();
      }

      function SaveAsJson() {
        console.log("SaveAsJson");
        var fileName = document.getElementById("title").value;
        if (fileName.length == 0) {
          fileName = "imsge";
        }

        fileName += ".json";

        var preData = {'version':'V0.617a1'};
        textareaList.forEach(function(e) {
          preData[e] = document.getElementById(e).value;
        });

        var jsonData = JSON.stringify(preData);

        var link = document.createElement("a");
        var file = new Blob([jsonData], { type: "text/plain" });
        link.href = URL.createObjectURL(file);
        link.download = fileName;
        link.click();
      }

      function isJsonFile(filename) {
        var ridx = filename.lastIndexOf(".");
        var extension = filename.substring(ridx + 1);

        console.log(extension);

        if (extension.length != 4 || extension.toLowerCase() != "json") {
          return false;
        }
        return true;
      }

      function isTextFile(filename) {
        var ridx = filename.lastIndexOf(".");
        var extension = filename.substring(ridx + 1);

        console.log(extension);

        if (extension.length != 3 || extension.toLowerCase() != "txt") {
          return false;
        }
        return true;
      }


      function loadFile() {
        var loadFile = document.getElementById("load_filename");
        var file = loadFile.files[0];
          
        if (!file) {
          return;
        }

        var fileName = document.getElementById("load_filename").value;
        var ridx = fileName.lastIndexOf("\\");

        fileName = fileName.substring(ridx + 1);

        if (isJsonFile(fileName)) {
          LoadJson(file, fileName);
        } else if(isTextFile(fileName)) {
          LoadText(file, fileName);
        } 
      }

      function LoadJson(file, fileName) {
        document.getElementById("title").value = fileName;

        var reader = new FileReader();
        reader.onload = function(e) {
          var contents = e.target.result;
          displayLoadJsonData(contents);
        };
        reader.readAsText(file);
      }

      function displayLoadJsonData(contents) {
        var noteData = JSON.parse(contents);

        var version = noteData['version'];
        console.log(version);
        document.getElementById('history').value = noteData['history'];
        reDrawCanvas();
      }

      function LoadText(file, fileName) {
        document.getElementById("title").value = fileName;

        var reader = new FileReader();
        reader.onload = function(e) {
          var contents = e.target.result;
          displayLoadTextData(contents);
        };
        reader.readAsText(file);
      }

      function displayLoadTextData(contents) {
        var noteData = contents.split('\n');
        var history = "";
         
        noteData.forEach(function (e){
          if (e[0] != 'v') {
            history += e + "\n";
          }
        }); 
        document.getElementById('history').value = history;
        reDrawCanvas();
      }

    </script>
  </head>

  <body>
    <div class="jb_table">
      <div class="row">
        <span class="cell" width="82">
          <div>
            <div class="jb_table">
              <div class="row">
                <span class="cell">
                  <img src="../../../dist/img/red.png" onclick="selectColor('red')" />
                  <img src="../../../dist/img/orange.png" onclick="selectColor('orange')"/>
                </span>
              </div>
              <div class="row">
                <span class="cell">
                  <img src="../../../dist/img/yellow.png" onclick="selectColor('yellow')" />
                  <img src="../../../dist/img/green.png" onclick="selectColor('green')"/>
                </span>
              </div>
              <div class="row">
                <span class="cell">
                  <img src="../../../dist/img/blue.png" onclick="selectColor('blue')" />
                  <img src="../../../dist/img/lightblue.png" onclick="selectColor('lightblue')" />
                </span>
              </div>
              <div class="row">
                <span class="cell">
                  <img src="../../../dist/img/lightgreen.png" onclick="selectColor('lightgreen')"/>
                  <img src="../../../dist/img/brown.png" onclick="selectColor('brown')"/>
                </span>
              </div>
              <div class="row">
                <span class="cell">
                  <img src="../../../dist/img/purple.png" onclick="selectColor('purple')" />
                  <img src="../../../dist/img/pink.png" onclick="selectColor('pink')"/>
                </span>
              </div>
              <div class="row">
                <span class="cell">
                  <img src="../../../dist/img/gray.png" onclick="selectColor('gray')" />
                  <img src="../../../dist/img/lightgray.png" onclick="selectColor('lightgray')"/>
                </span>
              </div>
              <div class="row">
                <span class="cell">
                  <img src="../../../dist/img/black.png" onclick="selectColor('black')"/>
                  <img src="../../../dist/img/white.png" onclick="selectColor('white')"/>
                </span>
              </div>
              <div class="row">
                <span class="cell">
                  <img src="../../../dist/img/pencil.png"  onclick="selectTool('pencil')" />
                  <img src="../../../dist/img/line.png"  onclick="selectTool('line')" />
                </span>
              </div>
              <div class="row">
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
              </div>
              <div class="row">
                <span class="cell">
                  <img src="../../../dist/img/triangle.png" onclick="selectTool('tri')" />
                  <img src="../../../dist/img/filledtriangle.png" onclick="selectTool('filledtri')" />
                </span>
              </div>
              <div class="row">
                  <span class="cell">
                    <img src="../../../dist/img/undo.png" onclick="undo()"/>
                    <img src="../../../dist/img/redo.png" onclick="redo()"/>
                  </span>
                </div>
            </div>
          </div>
        </span>
        <span class="cell">
          <div>
            <canvas id="canvas" width="720" height="720"></canvas>
          </div>
        </span>
        <span class="cell">
          <INPUT type="file" id="load_filename" value="Load" onChange="loadFile()" />
          <div>Title <input id="title" size="15" /></div>
          <div>
            <a id="saveImage" download="image.png">
                <INPUT type="button" value="Save" onClick="saveImage()" />
            </a>
            <INPUT type="button" value="Clear" onClick="initPage()" />
            <INPUT type="button" value="History" onClick="showHistory()" />
          </div>
          <div>
            <input type="button" value="Save as Json" onClick="SaveAsJson()" />
            <input type="button" value="Save as Txt" onClick="SaveAsTxt()" />
          </div>
          <div>
            <textarea id="history" cols="40" rows="37" style="display: none;"></textarea>
          </div>
          <div id="command">
          </div>
              <INPUT type="button" value="Redraw" onClick="reDrawCanvas()" />
          <div>
          </div>
        </span>
      </div>
    </div>
    <script src="../../../dist/js/painter.js"></script>
    <script src="../../../dist/js/drawengine.js"></script>
  </body>
</html>
