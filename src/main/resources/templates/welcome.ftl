<!DOCTYPE html>

<html lang="en">

<body>
	Date: ${time?date}
	<br/>
	Time: ${time?time}
	<br/>
	Message: ${message}
	<br/>
	<p>Click <a th:href="@{/hello}">here</a> to see a greeting.</p>
</body>

</html>
