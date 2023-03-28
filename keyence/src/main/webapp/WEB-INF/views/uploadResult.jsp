<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <title>上傳結果</title>
</head>
<body>
    <h1>上傳結果</h1>
    <c:if test="${not empty message}">
        <p>${message}</p>
    </c:if>
    <c:if test="${not empty data}">
        <table>
            <thead>
                <tr>
                    <th>欄位1</th>
                    <th>欄位2</th>
                    <th>欄位3</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${data}" var="row">
                    <tr>
                        <td>${row[0]}</td>
                        <td>${row[1]}</td>
                        <td>${row[2]}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</body>
</html>
