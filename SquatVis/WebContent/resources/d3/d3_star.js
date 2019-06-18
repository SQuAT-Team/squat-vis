// ORIGINAL: https://gist.github.com/nbremer/21746a9668ffdf6d8242#file-radarchart-js
// MODIFICATIONS: Sebastian Frank

//Practically all this code comes from https://github.com/alangrafu/radar-chart-d3
//I only made some additions and aesthetic adjustments to make the chart look better 
// https://gist.github.com/nbremer/21746a9668ffdf6d8242#file-radarchart-js

/////////////////////////////////////////////////////////
/////////////// The Radar Chart Function ////////////////
/////////////// Written by Nadieh Bremer ////////////////
////////////////// VisualCinnamon.com ///////////////////
/////////// Inspired by the code of alangrafu ///////////
/////////////////////////////////////////////////////////
	
function RadarChart(id, data, options, populationData) {
	var cfg = {
	 w: 600,				// Width of the circle
	 h: 600,				// Height of the circle
	 margin: {top: 10, right: 10, bottom: 10, left: 10}, // The margins of
															// the SVG
	 levels: 4,				// How many levels or inner circles should there be
							// drawn
	 maxValue: 1.0, 			// What is the value that the biggest circle
								// will
							// represent
	 labelFactor: 1.1, 		// How much farther than the radius of the outer
							// circle should the labels be placed
	 wrapWidth: 60, 		// The number of pixels after which a label needs to
							// be given a new line
	 opacityArea: 0.35, 	// The opacity of the area of the blob
	 dotRadius: 3, 			// The size of the colored circles of each blog
	 opacityCircles: 0.0, 	// The opacity of the circles of each blob
	 strokeWidth: 1, 		// The width of the stroke around each blob
	 roundStrokes: true,	// If true the area and stroke will follow a round
							// path (cardinal-closed)
	 color: d3.scaleOrdinal(d3.schemeCategory10),	// Color function
	 showAxisNames: false,
	 showAxisValues: false,
	 axisColor: d3.scaleOrdinal().range(["#000000","#000000"]),
	 startIndex: 5,
	 glow: true,
	 responsiveWidth: false,
	 responsiveId: 'content',
	 resize: false,
	 showName: false
	};
	
	// MANUAL OPTIONS TO CFG
	if('undefined' !== typeof options){
	  for(var i in options){
		if('undefined' !== typeof options[i]){ cfg[i] = options[i]; }
	  }// for i
	}// if
	
	// SET INITIAL RESPONSIVE WIDTH
	if(cfg.responsiveWidth){
		var element = document.getElementById(cfg.responsiveId);
		var width = element.clientWidth - cfg.margin.left - cfg.margin.right;
		var height = element.clientHeight - cfg.margin.top - cfg.margin.bottom;
		cfg.w = Math.min(width, height);
		cfg.h = Math.min(width, height);
	}
	
	var traits = d3.keys(data[0]).filter(function(d) { return ((d !== "ID") && (d !== "SelectorTags") && (d !== "Parent") && (d !== "ParetoTags") && (d !== "SuggestionTags")); });
	var n = traits.length;
	var total_n = d3.values(data[0]).length;
	
	// If the supplied maxValue is smaller than the actual one, replace by the
	// max in the data
	var maxValue = Math.max(cfg.maxValue, d3.max(data, function(i){var values = d3.values(i);return d3.max(values.slice(cfg.startIndex,total_n))}));

		radius = Math.min(cfg.w/2, cfg.h/2), 	// Radius of the outermost
												// circle
		Format = d3.format('.2p'),			 	// Percentage formatting
		angleSlice = Math.PI * 2 / n;		// The width in radians of each
												// "slice"
	
	// Scale for the radius
	var rScale = d3.scaleLinear()
		.range([0, radius])
		.domain([0, maxValue]);
		
	// ///////////////////////////////////////////////////////
	// ////////// Create the container SVG and g /////////////
	// ///////////////////////////////////////////////////////

	// Remove whatever chart with the same id/class was present before
	d3.select(id).select("svg").remove();
	
	// Initiate the radar chart SVG
	var svg = d3.select(id).append("svg")
			.attr("width",  cfg.w + cfg.margin.left + cfg.margin.right)
			.attr("height", cfg.h + cfg.margin.top + cfg.margin.bottom)
			.attr("class", "radar");
	// Append a g element
	var g = svg.append("g")
			.attr("transform", "translate(" + (cfg.w/2 + cfg.margin.left) + "," + (cfg.h/2 + cfg.margin.top) + ")");
	
	// ///////////////////////////////////////////////////////
	// //////// Glow filter for some extra pizzazz ///////////
	// ///////////////////////////////////////////////////////
	
	// Filter for the outside glow
	var filter = g.append('defs').append('filter').attr('id','glow'),
		feGaussianBlur = filter.append('feGaussianBlur').attr('stdDeviation','2.5').attr('result','coloredBlur'),
		feMerge = filter.append('feMerge'),
		feMergeNode_1 = feMerge.append('feMergeNode').attr('in','coloredBlur'),
		feMergeNode_2 = feMerge.append('feMergeNode').attr('in','SourceGraphic');

	// ///////////////////////////////////////////////////////
	// ///////////// Draw the Circular grid //////////////////
	// ///////////////////////////////////////////////////////
	
	// Wrapper for the grid & axes
	var axisGrid = g.append("g").attr("class", "axisWrapper");
	
	// Draw the background circles
	var levelCircles = axisGrid.selectAll(".levels")
	   .data(d3.range(1,(cfg.levels+1)).reverse())
	   .enter()
		.append("circle")
		.attr("class", "gridCircle")
		.attr("r", function(d, i){return radius/cfg.levels*d;})
		.style("fill", "#CDCDCD")
		.style("stroke", "#CDCDCD")
		.style("fill-opacity", cfg.opacityCircles);
	
	var axisLabels;
	if(cfg.showAxisValues){
	// Text indicating at what % each level is
	axisLabels = axisGrid.selectAll(".axisLabel")
	   .data(d3.range(1,(cfg.levels+1)).reverse())
	   .enter().append("text")
	   .attr("class", "axisLabel")
	   .attr("x", 4)
	   .attr("y", function(d){return -d*radius/cfg.levels;})
	   .attr("dy", "0.4em")
	   .style("font-size", "10px")
	   .attr("fill", "#737373")
	   .text(function(d,i) { return Format(maxValue * d/cfg.levels); });
	}

	// ///////////////////////////////////////////////////////
	// ////////////////// Draw the axes //////////////////////
	// ///////////////////////////////////////////////////////
	
	// Create the straight lines radiating outward from the center
	var axis = axisGrid.selectAll(".axis")
		.data(traits)
		.enter()
		.append("g")
		.attr("class", "axis");
	// Append the lines
	var axisLines = axis.append("line")
	.attr("x1", 0)
	.attr("y1", 0)
	.attr("x2", function(d, i){ return rScale(maxValue*1.05) * Math.cos(angleSlice*i - Math.PI/2); })
	.attr("y2", function(d, i){ return rScale(maxValue*1.05) * Math.sin(angleSlice*i - Math.PI/2); })
	.attr("class", "line axis-line")
	.style("stroke", function(d,i){return cfg.axisColor(i)});
	
	// Set up the small tooltip for when you hover over a circle
	var axisTooltip = g.append("text")
		.attr("class", "tooltip")
		.style("opacity", 0);

	var axisNames;
	if(cfg.showAxisNames){
	// Append the labels at each axis
	axisNames = axis.append("text")
		.attr("class", "legend")
		.style("font-size", "11px")
		.attr("text-anchor", "middle")
		.attr("dy", "0.35em")
		.attr("x", function(d, i){ return rScale(maxValue * cfg.labelFactor) * Math.cos(angleSlice*i - Math.PI/2); })
		.attr("y", function(d, i){ return rScale(maxValue * cfg.labelFactor) * Math.sin(angleSlice*i - Math.PI/2); })
		.text(function(d){return d})
		.call(wrap, cfg.wrapWidth);
	}
	
	// ///////////////////////////////////////////////////////
	// /////////// Draw the radar chart blobs ////////////////
	// ///////////////////////////////////////////////////////
	
	// The radial line function
	var radarLine = d3.lineRadial()
		.curve(d3.curveLinearClosed)
		.radius(function(d,i) { return rScale(d); })
		.angle(function(d,i) {	return i*angleSlice; });
		
	if(cfg.roundStrokes) {
		radarLine.curve(d3.curveCardinalClosed);
	}

	var blobWrapperPopulation;
	var blobWrapperPopulationPath;	
	if(populationData){
	// Create a wrapper for the population
	blobWrapperPopulation = g.selectAll(".radarWrapperPopulation")
		.data(populationData)
		.enter().append("g")
		.attr("class", "radarWrapperPopulation");
	
	// Create the outlines of the population
	blobWrapperPopulationPath = blobWrapperPopulation.append("path")
		.attr("class", "radarStrokePopulation")
		.attr("d", function(d,i) { return radarLine(d3.values(d).slice(cfg.startIndex,total_n)); })
		.style("stroke-width", cfg.strokeWidth + "px");
	}
	
	// Create a wrapper for the blobs
	var blobWrapper = g.selectAll(".radarWrapper")
		.data(data)
		.enter().append("g")
		.attr("class", function(d){return "radarWrapper" + " c" +d ["ID"] + " " + d["SelectorTags"];})
			
	// Append the backgrounds
	var blobArea = blobWrapper
		.append("path")
		.attr("class", "radarArea")
		.attr("candidateId", function(d){return d["ID"];})
		.attr("d", function(d,i) { return radarLine(d3.values(d).slice(cfg.startIndex,total_n)); })
		.style("fill-opacity", cfg.opacityArea)
		.on('mouseover', function (d,i){
			// Dim all blobs
			d3.selectAll(".radarArea")
				.transition().duration(200)
				.style("fill-opacity", 0.1); 
			// Bring back the hovered over blob
			var currentCandidateId = this.getAttribute("candidateId");
			d3.selectAll(".radarWrapper.c"+currentCandidateId).selectAll(".radarArea")
				.transition().duration(200)
				.style("fill-opacity", 0.7);
			
			if(cfg.showName){
			    var mouse = d3.mouse(this);
				newX =  mouse[0]-10;
				newY =  mouse[1]-10;
				
				tooltipName
				.attr('x', newX)
				.attr('y', newY)
				.text("Candidate " + d["ID"])
				.transition().duration(0)
				.style('opacity', 1);
			}
		})
		.on('mouseout', function(){
			// Bring back all blobs
			d3.selectAll(".radarArea")
				.transition().duration(200)
				.style("fill-opacity", cfg.opacityArea);
			
			if(cfg.showName){
				tooltipName.transition().duration(0)
				.style("opacity", 0);
			}
		});
		
	// Set up the small tooltip for when you hover over an area
	var tooltipName = g.append("text")
		.attr("class", "tooltip")
		.style("opacity", 0);
	
	// Create the outlines
	var outline = blobWrapper.append("path")
		.attr("class", "radarStroke")
		.attr("d", function(d,i) { return radarLine(d3.values(d).slice(cfg.startIndex,total_n)); })
		.style("stroke-width", cfg.strokeWidth + "px")
		.style("fill", "none")
	if(cfg.glow){
		outline.style("filter" , "url(#glow)");
	}
	
	// Append the circles
	var blobCircles = blobWrapper.selectAll(".radarCircle")
		.data(function(d,i) { return d3.values(d).slice(cfg.startIndex,total_n); })
		.enter().append("circle")
		.attr("class", "radarCircle")
		.attr("r", cfg.dotRadius)
		.attr("cx", function(d,i){ return rScale(d) * Math.cos(angleSlice*i - Math.PI/2); })
		.attr("cy", function(d,i){ return rScale(d) * Math.sin(angleSlice*i - Math.PI/2); })
		.style("fill-opacity", 0.8);

	// ///////////////////////////////////////////////////////
	// ////// Append invisible circles and axis for tooltip ///////////
	// ///////////////////////////////////////////////////////
	

	// Wrapper for the grid & axes
	var tooltipAxisGrid = g.append("g").attr("class", "tooltipAxisWrapper");
	
	var tooltipAxis = tooltipAxisGrid.selectAll(".tooltipAxis")
	.data(traits)
	.enter()
	.append("g")
	.attr("class", "tooltipAxis");
	
	tooltipAxis.append("line")
	.attr("x1", 0)
	.attr("y1", 0)
	.attr("x2", function(d, i){ return rScale(maxValue*1.05) * Math.cos(angleSlice*i - Math.PI/2); })
	.attr("y2", function(d, i){ return rScale(maxValue*1.05) * Math.sin(angleSlice*i - Math.PI/2); })
	.attr("class", "line hidden-axis-line")
	.style("pointer-events", "all")
	.on("mouseover", function(d,i) {
	    var mouse = d3.mouse(this);
		newX =  mouse[0]-10;
		newY =  mouse[1]-10;
				
		axisTooltip
			.attr('x', newX)
			.attr('y', newY)
			.text(d)
			.transition().duration(0)
			.style('opacity', 1);
	})
	.on("mouseout", function(){
		axisTooltip.transition().duration(0)
			.style("opacity", 0);
	});
	
	
	// Wrapper for the invisible circles on top
	var blobCircleWrapper = g.selectAll(".radarCircleWrapper")
		.data(data)
		.enter().append("g")
		.attr("class", "radarCircleWrapper");
		
	// Append a set of invisible circles on top for the mouseover pop-up
	blobCircleWrapper.selectAll(".radarInvisibleCircle")
		.data(function(d,i) { return d3.values(d).slice(cfg.startIndex,total_n); })
		.enter().append("circle")
		.attr("class", "radarInvisibleCircle")
		.attr("r", cfg.dotRadius*1.5)
		.attr("cx", function(d,i){ return rScale(d) * Math.cos(angleSlice*i - Math.PI/2); })
		.attr("cy", function(d,i){ return rScale(d) * Math.sin(angleSlice*i - Math.PI/2); })
		.style("fill", "none")
		.style("pointer-events", "all")
		.on("mouseover", function(d,i) {
			newX =  parseFloat(d3.select(this).attr('cx')) - 10;
			newY =  parseFloat(d3.select(this).attr('cy')) - 10;
					
			tooltip
				.attr('x', newX)
				.attr('y', newY)
				.text(Format(d))
				.transition().duration(200)
				.style('opacity', 1);
		})
		.on("mouseout", function(){
			tooltip.transition().duration(200)
				.style("opacity", 0);
		});
		
	// Set up the small tooltip for when you hover over a circle
	var tooltip = g.append("text")
		.attr("class", "tooltip")
		.style("opacity", 0);
	
	// ///////////////////////////////////////////////////////
	// ///////////////// Helper Function /////////////////////
	// ///////////////////////////////////////////////////////

	// Taken from http://bl.ocks.org/mbostock/7555321
	// Wraps SVG text
	function wrap(text, width) {
	  text.each(function() {
		var text = d3.select(this),
			words = text.text().split(/\s+/).reverse(),
			word,
			line = [],
			lineNumber = 0,
			lineHeight = 1.4, // ems
			y = text.attr("y"),
			x = text.attr("x"),
			dy = parseFloat(text.attr("dy")),
			tspan = text.text(null).append("tspan").attr("x", x).attr("y", y).attr("dy", dy + "em");
			
		while (word = words.pop()) {
		  line.push(word);
		  tspan.text(line.join(" "));
		  if (tspan.node().getComputedTextLength() > width) {
			line.pop();
			tspan.text(line.join(" "));
			line = [word];
			tspan = text.append("tspan").attr("x", x).attr("y", y).attr("dy", ++lineNumber * lineHeight + dy + "em").text(word);
		  }
		}
	  });
	}// wrap
	
	// ACTIVATE RESIZE
	if(cfg.resize){
		window.onresize = resize;			
	}
	
	function resize(){
		var element = document.getElementById(cfg.responsiveId);
		var width = element.clientWidth - cfg.margin.left - cfg.margin.right;
		var height = element.clientHeight - cfg.margin.top - cfg.margin.bottom;
		cfg.w = Math.max(Math.min(width, height), 200);
		cfg.h = Math.max(Math.min(width, height), 200);
		
		radius = Math.min(cfg.w/2, cfg.h/2); 	// Radius of the outermost
		rScale = d3.scaleLinear()
		.range([0, radius])
		.domain([0, maxValue]);
		
		svg.attr("width",  cfg.w + cfg.margin.left + cfg.margin.right)
		.attr("height", cfg.h + cfg.margin.top + cfg.margin.bottom);
		
		g.attr("transform", "translate(" + (cfg.w/2 + cfg.margin.left) + "," + (cfg.h/2 + cfg.margin.top) + ")");
		
		levelCircles.attr("r", function(d, i){return radius/cfg.levels*d;});
		
		if(cfg.showAxisValues){
			axisLabels.attr("y", function(d){return -d*radius/cfg.levels;});
		}
		
		axisLines.attr("x2", function(d, i){return rScale(maxValue*1.05) * Math.cos(angleSlice*i - Math.PI/2); })
		.attr("y2", function(d, i){ return rScale(maxValue*1.05) * Math.sin(angleSlice*i - Math.PI/2); });
		
		if(cfg.showAxisNames){
			axisNames.attr("x", function(d, i){ return rScale(maxValue * cfg.labelFactor) * Math.cos(angleSlice*i - Math.PI/2); })
				.attr("y", function(d, i){ return rScale(maxValue * cfg.labelFactor) * Math.sin(angleSlice*i - Math.PI/2); })
				.call(wrap, cfg.wrapWidth);
		}
		
		radarLine.radius(function(d,i) { return rScale(d); });
		if(populationData){
			blobWrapperPopulationPath.attr("d", function(d,i) { return radarLine(d3.values(d).slice(cfg.startIndex,total_n)); });
		}

		outline.attr("d", function(d,i) { return radarLine(d3.values(d).slice(cfg.startIndex,total_n)); });
		blobCircles.attr("cx", function(d,i){ return rScale(d) * Math.cos(angleSlice*i - Math.PI/2); })
		.attr("cy", function(d,i){ return rScale(d) * Math.sin(angleSlice*i - Math.PI/2); });
		blobArea.attr("d", function(d,i) { return radarLine(d3.values(d).slice(cfg.startIndex,total_n)); });
	}
	
}// RadarChart
