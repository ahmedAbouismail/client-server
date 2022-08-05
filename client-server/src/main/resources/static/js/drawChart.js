
google.charts.load('current', {'packages':['corechart']});

//google.charts.setOnLoadCallback(drawChart);


function drawChart(data, chartType, title, canvasId) {

        // Create the data table.
        if (data.length === 1) {
            data.push(["SS 2022", 1]);
            data.push(["SS 2023", 3.5]);
            var data = new google.visualization.arrayToDataTable(data);
        } else {
            var data = new google.visualization.arrayToDataTable(data);
        }


        var options = {
            title: title,
            curveType: 'function',
            legend: {position: 'bottom'},
            vAxis: {
                title: 'Average',
                direction: '-1'
            }
        };
        if (chartType === "LINE"){
            var chart = new google.visualization.LineChart(document.getElementById(canvasId));
        }else if (chartType === "BAR"){
            var chart = new google.visualization.ColumnChart(document.getElementById(canvasId));
        }
        chart.draw(data, options);
}



