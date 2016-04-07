<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<script type="text/javascript">		
function getData(){			
	var accountName = $("#account").val();		
		
	$.ajax({
	    url: './accounts/'+accountName,
	    type: 'GET',
	    success: function(data) {
	    	$("#expenses").empty();
	    	console.log(data);
	        $.each(data, function(index, expense) {
	        	var tags = "";
	    		$.each(expense.tags, function(index, tag) {
	    			tags += " " + tag.tagName;	
	    		});
	    		
				$("<p>").appendTo("#expenses").html("" +
					"<div class='finance-info bordered'>" +
						"<div>" +
							"<p>" +
								"<span class='date'>" + 
									 expense.date.year + "-" + 
									 expense.date.monthOfYear + "-" +
									 expense.date.dayOfMonth + 
								"</span>" + 
								"<span class='money-amount'>" + 
									expense.currency + " " + (expense.amount / 100.0).toFixed(2) +
								"</span>" +
							"</p>" +
						"</div>" +
						"<div>" +
							"<p>" +
								"<span class='category'>" + expense.category.categoryName + "</span>" + 
								"<span class='tags'>" + tags + "</span>" +
							"</p>" +
						"</div>" +
					"</div>" +
					"<div class='operations'>" +
						"<a href='./editExpense?id=" + expense.id + "' class='btn btn-info btn-xs'>Edit</a>" + 
						"<a href='./verifyDeleteExpense?id=" + expense.id + "' class='btn btn-danger btn-xs'>Delete</a>" +
					"</div>");
		})}
	});
}
</script>
<select class="form-control" id="account" name="select" onchange="getData()">
	<option value="all">All accounts</option>
	<c:forEach var="acc" items="${accounts}">
		<option value="${acc.title}"><c:out value="${acc.title}"></c:out></option>
	</c:forEach>		
</select>