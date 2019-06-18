/**
 * ORIGINAL: https://bl.ocks.org/mbostock/4063663 MODIFIED: Sebastian Frank
 */

var width = 960,
    size = 130,
    padding = 20,
    paddingPlus = 10,
    minSizeBig = 200;
    radius = 2,
    radiusBig = 3,
    textSize = 20;
    n = 0;

    var axisTextSize = "16px";
    
    var svgBig;
    var domainByTrait = {},
    traits;
	
    var x;
    var y;
    var xBig;
    var yBig;
    var xAxis;
    var yAxis;
    var xAxisBig;
    var yAxisBig;
    var brushBig

    // tooltip
    var cellTip = d3.tip().attr('class', 'd3-tip').html(function(d) { return "x: " + traits[d.i] + " y: " + traits[d.j]; });
    
render(parsedValues);
window.onresize = resize;

function resize(){
	resizeBig();
}

function resizeBig(){
	var widthBig = document.getElementById('matrixDetailedContent').clientWidth;
	var heightBig = document.getElementById('matrixDetailedContent').clientHeight;
	  var sizeBig = Math.max(Math.min(widthBig,heightBig), minSizeBig)-padding-textSize-paddingPlus;

	  xBig = d3.scaleLinear()
	  	.range([padding / 2, sizeBig - padding / 2]);

	  yBig = d3.scaleLinear()
	  	.range([sizeBig - padding / 2, padding / 2]);

	  xAxisBig = d3.axisBottom(xBig)
	  .ticks(6);

	  yAxisBig = d3.axisLeft(yBig)
	  .ticks(6);

    var svgBig = d3.select('#matrixDetailedContent svg')
    .attr('width', sizeBig + padding+textSize)
    .attr('height', sizeBig + padding+textSize);
    
    xAxisBig.tickSize(sizeBig);
    yAxisBig.tickSize(-sizeBig);
        
	svgBig.selectAll("g").filter(".x").filter(".axis")
	  .each(function(d) {
	    xBig.domain(d3.extent([0.0,1.0]));
	    d3.select(this).call(xAxisBig);
	});
	
	svgBig.selectAll("g").filter(".y").filter(".axis")
	  .each(function(d) {
	    yBig.domain(d3.extent([0.0,1.0]));
	    d3.select(this).call(yAxisBig);
	});
	
    svgBig.selectAll("rect").filter(".frame")
    .attr("width", sizeBig - padding)
    .attr("height", sizeBig - padding);

    svgBig.selectAll("text").filter(".x").filter(".axis")
    .attr("transform","translate(" + (sizeBig/2) + " ," + 
    (sizeBig+30) + ")");
                         
    svgBig.selectAll("text").filter(".y").filter(".axis")
    .attr("x",0 - (sizeBig / 2));
    
    var cellBig = svgBig.selectAll("g").filter(".cell");
    
    var xTrait = cellBig.attr("x-trait");
    var yTrait = cellBig.attr("y-trait");
        
	xBig.domain(domainByTrait[xTrait]);
	yBig.domain(domainByTrait[yTrait]);
    
    cellBig.selectAll("circle")
    .attr("cx", function(d) {
      return xBig(d[xTrait]);
    })
	.attr("cy", function(d) {
	  return yBig(d[yTrait]);
	});
    
    brushBig.extent([[xBig.range()[0], yBig.range()[1]], [xBig.range()[1], yBig.range()[0]]]);
    cellBig.call(brushBig);
    
    redrawParent();
}

function render(data) {
  var widthBig = document.getElementById('matrixDetailedContent').clientWidth;
  var heightBig = document.getElementById('matrixDetailedContent').clientHeight;
  traits = d3.keys(data[0]).filter(function(d) { return ((d !== "ID") && (d !== "SelectorTags") && (d !== "Parent") && (d !== "ParetoTags") && (d !== "SuggestionTags")); });
  n = traits.length;
  
  var sizeBig = Math.max(Math.min(widthBig,heightBig), minSizeBig)-2*padding-textSize-paddingPlus;
	
  x = d3.scaleLinear()
      .range([padding / 2, size - padding / 2]);

  y = d3.scaleLinear()
      .range([size - padding / 2, padding / 2]);

  xBig = d3.scaleLinear()
  	.range([padding / 2, sizeBig - padding / 2]);

  yBig = d3.scaleLinear()
  	.range([sizeBig - padding / 2, padding / 2]);

  xAxis = d3.axisBottom(x)
  .ticks(6);

  yAxis = d3.axisLeft(y)
  .ticks(6);

  xAxisBig = d3.axisBottom(xBig)
  .ticks(6);

  yAxisBig = d3.axisLeft(yBig)
  .ticks(6);

  
  traits.forEach(function(trait) {
	  domainByTrait[trait] = d3.extent([0.0,1.0]);
	  // automatic
	  // domainByTrait[trait] = d3.extent(data, function(d) { return d[trait];
		// });
  });

  xAxis.tickSize(size * n);
  yAxis.tickSize(-size * n);
  
  xAxisBig.tickSize(sizeBig);
  yAxisBig.tickSize(-sizeBig);

  var brush = d3.brush().extent([[x.range()[0], y.range()[1]], [x.range()[1], y.range()[0]]])
      .on("start", brushstart)
      .on("brush", brushmove)
      .on("end", brushend);
  
  brushBig = d3.brush().extent([[xBig.range()[0], yBig.range()[1]], [xBig.range()[1], yBig.range()[0]]])
  .on("start", brushstartBig)
  .on("brush", brushmoveBig)
  .on("end", brushendBig);
  
  // Big Area
  svgBig = d3.select("#matrixDetailedContent").append("svg")
  	  .attr("width", sizeBig+padding+textSize)
  	  .attr("height", sizeBig+padding+textSize)
  	  .append("g")
  	  .attr("transform", "translate(" + (padding+textSize )+ "," + (padding/ 2)+ ")");
  	  
	svgBig.append("g")
	  .attr("class", "x axis")
	  .each(function(d) {
	    xBig.domain(d3.extent([0.0,1.0]));
	    d3.select(this).call(xAxisBig);
    });

    svgBig.append("g")
      .attr("class", "y axis")
      .each(function(d) {
    	yBig.domain(d3.extent([0.0,1.0]));
    	d3.select(this).call(yAxisBig);
    });
    
    // Text Label X Axis
    svgBig.append("text")
    .attr("class", "x axis")
    .attr("transform",
          "translate(" + (sizeBig/2) + " ," + 
                         (sizeBig+30) + ")")
    .style("text-anchor", "middle")
    .style("font-size",axisTextSize)
    .style("font-weight","bold")
    .text(traits[traits.length-1]);
	
    // Text Label Y Axis
    svgBig.append("text")
        .attr("class", "y axis")
        .attr("transform", "rotate(-90)")
        .attr("y", 0 - 40)
        .attr("x",0 - (sizeBig / 2))
        .attr("dy", "1em")
        .style("text-anchor", "middle")
        .style("font-size",axisTextSize)
        .style("font-weight","bold")
        .text(traits[0]);
    
  var svg = d3.select("#matrixContent").append("svg")
      .attr("width", size * n + padding)
      .attr("height", size * n + padding)
      .append("g")
      .attr("transform", "translate(" + padding + "," + padding / 2 + ")");
  
  svg.selectAll(".x.axis")
      .data(traits)
    .enter().append("g")
      .attr("class", "x axis")
      .attr("transform", function(d, i) {
    	  return "translate(" + (n - i - 1) * size + ",0)";
      })
      .each(function(d) {
    	  x.domain(domainByTrait[d]);
    	  d3.select(this).call(xAxis);
      });

  svg.selectAll(".y.axis")
      .data(traits)
    .enter().append("g")
      .attr("class", "y axis")
      .attr("transform", function(d, i) {
    	  return "translate(0," + i * size + ")";
      })
      .each(function(d) {
    	  y.domain(domainByTrait[d]);
    	  d3.select(this).call(yAxis);
      });

  var c = [];
  c.push({x: traits[traits.length-1], y: traits[0]});

  svg.call(cellTip);
  
  cellBig = svgBig.selectAll(".cell")
  .data(c)
  .enter().append("g")
  .attr("class", "cell")
  .attr("x-trait", function(d){return traits[traits.length-1]})
  .attr("y-trait", function(d){return traits[0]})
  .each(plotBig);
  
  cellBig.call(brushBig);
  
  var cell = svg.selectAll(".cell")
      .data(cross(traits, traits))
      .enter().append("g")
      .attr("class", "cell")
      .attr("x-trait", function(d){return traits[d.i]})
      .attr("y-trait", function(d){return traits[d.j]})
      .attr("transform", function(d) {
    	  return "translate(" + (n - d.i - 1) * size + "," + d.j * size + ")";
      })
      .on("contextmenu", selectCell)
      .on('mouseover', cellTip.show)
	  .on('mouseout', cellTip.hide)
      .each(plot);

  // Titles for the diagonal.
  cell.filter(function(d) {
	  	return d.i === d.j;
	  }).append("text")
      .attr("x", padding)
      .attr("y", padding)
      .attr("dy", ".71em")
      .text(function(d) {
    	  return d.x;
      });

  cell.call(brush);

  function plot(p) {
	    var cell = d3.select(this);

	    x.domain(domainByTrait[p.x]);
	    y.domain(domainByTrait[p.y]);

	    cell.append("rect")
	        .attr("class", "frame")
	        .attr("x", padding / 2)
	        .attr("y", padding / 2)
	        .attr("width", size - padding)
	        .attr("height", size - padding);


	    // Pareto Tag circle
	    cell.selectAll(".pareto-circle")
        .data(data)
      .enter().append("circle").attr("class", function(d) {
    	  return "pareto-circle c"+d["ID"] + " " + d["ParetoTags"];
      	})
        .attr("cx", function(d) {
        	return x(d[p.x]);
       	})
        .attr("cy", function(d) {
        	return y(d[p.y]);
       	})
        .attr("r", radius+1)
        .style("pointer-events", "none");
	    
	    // Suggestion Tag circle
	    cell.selectAll(".suggestion-circle")
        .data(data)
      .enter().append("circle").attr("class", function(d) {
    	  return "suggestion-circle c"+d["ID"] + " " + d["SuggestionTags"];
      	})
        .attr("cx", function(d) {
        	return x(d[p.x]);
       	})
        .attr("cy", function(d) {
        	return y(d[p.y]);
       	})
        .attr("r", radius+2)
        .style("pointer-events", "none");
	    
	    
	    cell.selectAll(".candidate-circle")
	        .data(data)
	      .enter().append("circle").attr("class", function(d) {
	    	  return "candidate-circle c"+d["ID"] + " " + d["SelectorTags"];
	      	})
	        .attr("cx", function(d) {
	        	return x(d[p.x]);
	       	})
	        .attr("cy", function(d) {
	        	return y(d[p.y]);
	       	})
	        .attr("r", radius)
	        .style("pointer-events", "none")
	  }
  
  var cellBig;
  function plotBig(p) {
	    cellBig = d3.select(this);

	    xBig.domain(domainByTrait[p.x]);
	    yBig.domain(domainByTrait[p.y]);

	    cellRect = cellBig.append("rect")
	        .attr("class", "frame")
	        .attr("x", padding / 2)
	        .attr("y", padding / 2)
	        .attr("width", sizeBig - padding)
	        .attr("height", sizeBig - padding);

	    // Pareto Tag circle
	    cellBig.selectAll(".pareto-circle")
        .data(data)
      .enter().append("circle").attr("class", function(d) {
    	  return "pareto-circle c"+d["ID"] + " " + d["ParetoTags"];
      	})
        .attr("cx", function(d) {
        	return xBig(d[p.x]);
       	})
        .attr("cy", function(d) {
        	return yBig(d[p.y]);
       	})
        .attr("r", radiusBig+1)
        .style("pointer-events", "none");
	    
	    // Suggestion Tag circle
	    cellBig.selectAll(".suggestion-circle")
        .data(data)
      .enter().append("circle").attr("class", function(d) {
    	  return "suggestion-circle c"+d["ID"] + " " + d["SuggestionTags"];
      	})
        .attr("cx", function(d) {
        	return xBig(d[p.x]);
       	})
        .attr("cy", function(d) {
        	return yBig(d[p.y]);
       	})
        .attr("r", radiusBig+2)
        .style("pointer-events", "none");
	    
	    // Candidate itself
	    cellBig.selectAll(".candidate-circle")
        .data(data)
      .enter().append("circle").attr("class", function(d) {
    	  return "candidate-circle c"+d["ID"] + " " + d["SelectorTags"];
      	})
      	.attr("candidateId", function(d) {
      		return d["ID"];
      	})
       	.attr("parent", function(d) {
      		return d["Parent"];
      	})
        .attr("cx", function(d) {
        	return xBig(d[p.x]);
       	})
        .attr("cy", function(d) {
        	return yBig(d[p.y]);
       	})
        .attr("r", radiusBig)
        .style("pointer-events", "none");
     }
  
  var brushCellBig;
  var brushCell;

  // Clear the previously-active brush, if any.
  function brushstartBig(p) {
    if (brushCellBig !== this) {
      var xTrait = this.getAttribute("x-trait");
      var yTrait = this.getAttribute("y-trait");
      xBig.domain(domainByTrait[xTrait]);
      yBig.domain(domainByTrait[yTrait]);
      brushCellBig = this;
    }
    d3.select(brushCellBig).call(brushBig.move, null);
	selectorClearCurrent();
  }

  // Highlight the selected circles.
  function brushmoveBig(p) {
	if (!d3.event.selection) return; // Ignore empty selections.
  }

  // If the brush is empty, select all circles.
  function brushendBig(p) {
	var e = d3.event.selection;
    var xTrait = this.getAttribute("x-trait");
    var yTrait = this.getAttribute("y-trait");
    if (!e || e === null){
    	svgBig.selectAll(".current").classed("current", false);
    	selectorClearCurrent();
    }else{
        svgBig.selectAll("circle").filter(".candidate-circle").classed("current", function(d) {
        	var isSelected = xBig(d[xTrait]) >= e[0][0] && e[1][0] >= xBig(d[xTrait])
            && yBig(d[yTrait]) >= e[0][1] && e[1][1] >= yBig(d[yTrait]);
            if(isSelected){
            	setSelectorCurrent(d["ID"]);
            }
        return isSelected;
        });
    }
  }
  
  // Clear the previously-active brush, if any.
  function brushstart(p) {
    if (brushCell !== this) {
      x.domain(domainByTrait[p.x]);
      y.domain(domainByTrait[p.y]);
      brushCell = this;
    }
    d3.select(brushCell).call(brush.move, null);
	selectorClearCurrent();
  }

  // Highlight the selected circles.
  function brushmove(p) {
	if (!d3.event.selection) return; // Ignore empty selections.
  }

  // If the brush is empty, select all circles.
  function brushend(p) {
	var e = d3.event.selection;
    if (e === null){
    	svg.selectAll(".current").classed("current", false);
    	selectorClearCurrent();
    }else{
        svg.selectAll("circle").filter(".candidate-circle").classed("current", function(d) {
        	var isSelected = x(d[p.x]) >= e[0][0] && e[1][0] >= x(d[p.x])
            && y(d[p.y]) >= e[0][1] && e[1][1] >= y(d[p.y]);
            if(isSelected){
            	setSelectorCurrent(d["ID"]);
            }
        return isSelected;
        });
    }
  }
  
  function selectCell(){
	  // Prevent right click menu
	  d3.event.preventDefault();
	  // Get trait names
	  var xTrait = this.getAttribute("x-trait");
	  var yTrait = this.getAttribute("y-trait");
	  
	  xBig.domain(domainByTrait[xTrait]);
	  yBig.domain(domainByTrait[yTrait]);
	  
	  cellBig.attr("x-trait", xTrait)
	  .attr("y-trait", yTrait);
	  
	    cellBig.selectAll("circle")
        .attr("cx", function(d) {
        	return xBig(d[xTrait]);
       	})
        .attr("cy", function(d) {
        	return yBig(d[yTrait]);
       	})
       	
       	svgBig.selectAll("text").filter(".x").filter(".axis").text(xTrait);
       	svgBig.selectAll("text").filter(".y").filter(".axis").text(yTrait);
       	
       	redrawParent();
  }
}

function cross(a, b) {
  var c = [], n = a.length, m = b.length, i, j;
  for (i = -1;n > ++i;){
	  for (j = -1;m > ++j; ){
		  c.push({x: a[i], i: i, y: b[j], j: j});
	  }
	}
  return c;
}
