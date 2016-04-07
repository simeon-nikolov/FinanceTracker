<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<script src="http://code.jquery.com/jquery-2.2.2.js"
	integrity="sha256-4/zUCqiq0kqxhZIyp4G0Gk+AOtCJsY1TA00k5ClsZYE="
	crossorigin="anonymous">
</script>
<script type="text/javascript">
	function downloadData() {
		$("#result1").empty();
		
		var accountName = $("#account").val();	
		
		$.get("./accounts/"+accountName).success(function(ohluvs) {
			$.each(ohluvs, function(index, ohluv) {
				
				$("<p>").appendTo("#result1").text(ohluv.currency + '  ' + ohluv.amount/100);				
				$("<p>").appendTo("#result1").text('Category: ' + ohluv.category.categoryName);
				$("<hr>").appendTo("#result1");
			})
		});
	}
	
	function addNewOhliuv() {
		var nameVal = $("#name").val();
		var maxSpeedVal = $("#maxSpeed").val();
		
		$.ajax({
		    url: './ohluvs',
		    type: 'PUT',
		    data: JSON.stringify({name: nameVal, maxSpeed:maxSpeedVal}),
		    success: function(result) {
		        downloadData();
		    }
		});
	}
	
				
		
	function getData(){			
			
			var accountName = $("#account").val();		
						
					
			$.ajax({
			    url: './accounts/'+accountName,
			    type: 'GET',
			    data: JSON.stringify({accountName: accountName}),
			    success: function(result) {
			        downloadData();
			    }
			});
	}	
		
	
</script>	
	
<select class="form-control" id="account" name="select"
		onchange="downloadData()">
	<option value="all">All accounts</option>
	<c:forEach var="acc" items="${accounts}">
		<option value="${acc.title}"><c:out value="${acc.title}"></c:out></option>
	</c:forEach>		
</select>
<div id="result1">
</div>