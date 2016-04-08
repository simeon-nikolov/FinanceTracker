$(document).ready(function() {
    $('#date-picker').datepicker();
    $('.date-picker').datepicker();
});

function loadFinanceOperationsData(financeOperationType) {	
	var accountName = $("#account").val();		
	var elementId = "#" + financeOperationType;
	var url = "./accounts";
	
	if (accountName != "") {
		url += "/" + accountName;
	}
	
	$.ajax({
	    url: url,
	    type: 'GET',
	    success: function(data) {
	    	$(elementId).empty();
	    	var list = [];
	    	
	        $.each(data, function(index, financeOperation) {
	        	list.push({'category': financeOperation.category, 'amount': financeOperation.amount});
	        	var html = generateHtml(financeOperation); 
				$("<div>").appendTo(elementId).html(html);
	        });
	        
	        var labels = [];
	    	var series = [];
	    	sortArrays(list, labels, series)
	        drawPieGraphic(labels, series);
	    }		
	});
}

function generateHtml(financeOperation) {
	var tags = "";
	
	$.each(financeOperation.tags, function(index, tag) {
		tags += " " + tag;	
	});
	
	var html = "" +
	"<div class='finance-info bordered'>" +
		"<div>" +
			"<p>" +
				"<span class='date'>" + 
				financeOperation.date.year + "-" + 
					("0" + financeOperation.date.monthOfYear).slice(-2)  + "-" +
					("0" + financeOperation.date.dayOfMonth).slice(-2)  + 
				"</span>" + 
				"<span class='money-amount'>" + 
				financeOperation.currency + " " + (financeOperation.amount).toFixed(2) +
				"</span>" +
			"</p>" +
		"</div>" +
		"<div>" +
			"<p>" +
				"<span class='category'>" + financeOperation.category + "</span>" + 
				"<span class='tags'>" + tags + "</span>" +
			"</p>" +
		"</div>" +
	"</div>" +
	"<div class='operations'>" +
		"<a href='./editExpense?id=" + financeOperation.id + "' class='btn btn-info btn-xs'>Edit</a>" + 
		"<a href='./verifyDeleteExpense?id=" + financeOperation.id + "' class='btn btn-danger btn-xs'>Delete</a>" +
	"</div>";
	
	return html;
}

function sortArrays(list, labels, series) {
	list.sort(function(a, b) {
        return ((a.category < b.category) ? -1 : ((a.category == b.category) ? 0 : 1));
    });
    
    for (var k = 0; k < list.length; k++) {
        var index = $.inArray(list[k].category, labels);
    	
    	if (index >= 0) {
    		series[index] += list[k].amount;
		} else {
        	labels.push(list[k].category);
        	series.push(list[k].amount);
		}
    }
}

function drawPieGraphic(labelsData, seriesData) {
	var data = {
			labels : labelsData,
			series : seriesData
	};

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