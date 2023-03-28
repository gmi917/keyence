<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
    <title>上傳 Excel 檔案</title>
</head>
<body>
    <h1>上傳 Excel 檔案</h1>
    <form:form method="POST" enctype="multipart/form-data" action="${pageContext.request.contextPath}/upload">
        <label for="file">請選擇要上傳的 Excel 檔案：</label>
        <input type="file" name="file" id="file" />
        <br />
        <input type="submit" value="上傳" />
    </form:form>
</body>
</html>
