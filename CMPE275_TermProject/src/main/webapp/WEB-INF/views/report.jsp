<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>
Reports
</title>
</head>
<body>

Task Units finished: ${report.taskUnitsFinished}
<br>
Task Units To be finished:${report.taskUnitsTobeFinished}
<br>
Task Units Cancelled: ${report.taskUnitsCancelled}
<br>
Task Units During Planning Phase: ${report.taskUnitsAtPlanningPhase}

</body>
</html>