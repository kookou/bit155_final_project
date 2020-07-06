<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
</head>
<body>
	<form id="formId" enctype="multipart/form-data">
		<input type="file" name="fileName" id="excelFile" />
		<input type="submit" value="파일보내기" />
	</form>

	<script type="text/javascript">
		var file = document.querySelector('#excelFile');
        file.onchange = function () {
            var fileList = file.files;
            console.log(fileList[0].name);
            $("#formId").attr("action", "/write-file/"+fileList[0].name);
        };
	</script>
</body>
</html>