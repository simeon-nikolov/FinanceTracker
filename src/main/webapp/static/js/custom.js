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

	url += "/" + financeOperationType;

	$.ajax({
	    url: url,
	    type: 'GET',
	    success: function(data) {
	    	$(elementId).empty();
	    	var chartData = [];

	        $.each(data, function(index, financeOperation) {
	        	var index = -1;

	        	for(var i = 0, len = chartData.length; i < len; i++ ) {
	        	    if(chartData[i][0] === financeOperation.category) {
	        	    	index = i;
	        	        break;
	        	    }
	        	}

	        	if (index >= 0) {
	        		 chartData[index][1] += financeOperation.amount;
	    		} else {
	    			chartData.push([financeOperation.category, financeOperation.amount]);
	    		}

	        	var html = generateHtml(financeOperation);
				$("<div>").appendTo(elementId).html(html);
	        });

	    	sortByCategory(chartData);
	    	draw3dDonut(financeOperationType, chartData);
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
			"<span class='date'>" +
				financeOperation.date.year + "-" +
					("0" + financeOperation.date.monthOfYear).slice(-2)  + "-" +
					("0" + financeOperation.date.dayOfMonth).slice(-2)  +
			"</span>" +
			"<span class='money-amount'>" +
				financeOperation.userCurrency + " " + (financeOperation.userCurrencyAmount).toFixed(2) +
			"</span>" +
		"</div>" +
		"<br class='clear' />" +
		"<div></div>" +
		"<div>" +
			"<span class='category'>" + financeOperation.category + "</span>" +
			"<span class='tags'>" + tags + "</span>" +
			"<span class='actual-amount'>" +
				financeOperation.currency + " " + (financeOperation.amount).toFixed(2) +
			"</span>" +
		"</div>" +
		"<br class='clear' />" +
		"<div></div>" +
	"</div>" +
	"<div class='operations'>" +
		"<a href='./editExpense?id=" + financeOperation.id + "' class='btn btn-info btn-xs'>Edit</a>" +
		"<a href='./verifyDeleteExpense?id=" + financeOperation.id + "' class='btn btn-danger btn-xs'>Delete</a>" +
	"</div>";

	return html;
}

function sortByCategory(data) {
	data.sort(function(a, b) {
        return ((a[0] < b[0]) ? -1 : ((a[0] == b[0]) ? 0 : 1));
    });
}

function draw3dDonut (financeOperationType, data) {
    $('#chart').highcharts({
        chart: {
            type: 'pie',
            options3d: {
                enabled: true,
                alpha: 45
            }
        },
        title: {
            text: 'Monthly overview of ' + financeOperationType + ' by category.'
        },
        subtitle: {
            text: ''
        },
        plotOptions: {
            pie: {
            	allowPointSelect: true,
                cursor: 'pointer',
                innerSize: 100,
                depth: 65
            }
        },
        series: [{
            name: financeOperationType + ' amount',
            data: data
        }]
    });
}

function draw3dGroupedColumn(cdata, categories, title) {
	var chart = new Highcharts.Chart({
        chart: {
            renderTo: 'chart',
            type: 'column',
            margin: 75,
            options3d: {
                enabled: true,
                alpha: 15,
                beta: 15,
                depth: 50,
                viewDistance: 15
            }
        },
        xAxis: {
            categories: categories
        },
        yAxis: {
            allowDecimals: false,
            min: 0,
            title: {
                text: 'Income / Expense'
            }
        },
        title: {
            text: title
        },
        subtitle: {
            text: ''
        },
        plotOptions: {
            column: {
                depth: 25
            }
        },
        series: cdata
    });
}

function reloadTags(category) {
	$.ajax({
	    url: "./" + category + "/tags",
	    type: 'GET',
	    success: function(data) {
	    	$("#tags").empty();

	    	$.each(data, function(index, tag) {
	    		var html = ' <input id="tag' + tag + '" name="tags" type="checkbox" value="' + tag + '"> ';
	    		html += ' <input type="hidden" name="_tags" value="on"> ';
	    		html += ' <label for="tag' + tag + '">' + tag + '</label> ';
	    		$("#tags").append(html);
	    	});
	    }
	});
}


