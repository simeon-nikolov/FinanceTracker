$(document).ready(function() {
    $('#date-picker').datepicker();
    $('.date-picker').datepicker();
});

function loadExpenses() {	
	var accountName = $("#account").val();		
		
	$.ajax({
	    url: './accounts/'+accountName,
	    type: 'GET',
	    success: function(data) {
	    	$("#expenses").empty();
	    	var labels = [];
	    	var series = [];
	    	
	        $.each(data, function(index, expense) {
	        	var index = $.inArray(expense.category.categoryName, labels);
	        	
	        	if (index >= 0) {
	        		series[index] += expense.amount;
				} else {
		        	labels.push(expense.category.categoryName);
		        	series.push(expense.amount);
				}
	        	
	        	var tags = "";
	        	
	    		$.each(expense.tags, function(index, tag) {
	    			tags += " " + tag.tagName;	
	    		});
	    		
				$("<div>").appendTo("#expenses").html("" +
					"<div class='finance-info bordered'>" +
						"<div>" +
							"<p>" +
								"<span class='date'>" + 
									expense.date.year + "-" + 
									("0" + expense.date.monthOfYear).slice(-2)  + "-" +
									("0" + expense.date.dayOfMonth).slice(-2)  + 
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
	        });
	        
	        drawPieGraphic(labels, series);
	    }		
	});
}

function drawPieGraphic(labelsData, seriesData) {
	var data = {
			labels : labelsData,
			series : seriesData
	};
	
	console.log(labelsData);
    console.log(seriesData);

	var options = {
		labelInterpolationFnc : function(value) {
			return value[0]
		}
	};

	var responsiveOptions = [ [ 'screen and (min-width: 640px)', {
		chartPadding : 30,
		labelOffset : 100,
		labelDirection : 'explode',
		labelInterpolationFnc : function(value) {
			return value;
		}
	} ], [ 'screen and (min-width: 1024px)', {
		labelOffset : 80,
		chartPadding : 20
	} ] ];

	new Chartist.Pie('.ct-chart', data, options, responsiveOptions);
}