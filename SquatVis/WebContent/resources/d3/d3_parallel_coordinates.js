// https://www.d3-graph-gallery.com/graph/parallel_custom.html
var svgPC;
var svgPCLines;
var svgPCAxis;

function renderParallelCoordinates(data, metadata) {
	if(!initializedActiveCandidates){
		initializeActiveCandidateSets();
	}
	
	// set the dimensions and margins of the graph
	var element = document.getElementById("resources-container");
	var marginPC = {top: 25, right: 50, bottom: 10, left: 50},
	  widthPC = element.clientWidth - marginPC.left - marginPC.right,
	  heightPC = element.clientHeight - marginPC.top - marginPC.bottom;

	// append the svg object to the body of the page
	svgPC = d3.select("#resources-container")
	.append("svg")
	  .attr("width", widthPC + marginPC.left + marginPC.right)
	  .attr("height", heightPC + marginPC.top + marginPC.bottom)
	.append("g")
	  .attr("transform",
	        "translate(" + marginPC.left + "," + marginPC.top + ")");
	svgPCLines = svgPC.append("g").attr("class", "lines");
	svgPCAxis = svgPC.append("g").attr("class", "axis");

  // Here I set the list of dimension manually to control the order of axis:
  // For each dimension, I build a linear scale. I store all in a y object
  var dimensions = [];
  var y = {}
  var dimensionsNew = d3.selectAll("dimension").data(metadata).enter()
  .each(function(d){
	  dimensions = dimensions.concat([d.Name]);
	  y[d.Name] = d3.scaleLinear()
	      .domain( [d.Min,d.Max] ) // --> Same axis range for each group
	      // --> different axis range for each group --> .domain( [d3.extent(data, function(d) { return +d[name]; })] )
	      .range([heightPC, 0])
  });

  // Build the X scale -> it find the best position for each Y axis
  x = d3.scalePoint()
    .range([0, widthPC])
    .domain(dimensions);

  // Highlight the line that is hovered
  var highlight = function(d){
    d3.select(this)
      .transition().duration(200)
      .style("opacity", "1.0")
      .style("stroke-width", "2px");
    d3.select(this).moveToFront();
  }

  // Unhighlight
  var doNotHighlight = function(d){
    d3.selectAll(".resource-line")
      .transition().duration(200).delay(350)
      .style("opacity", "0.5")
      .style("stroke-width", "1px");
  }

  // The path function take a row of the csv as input, and return x and y coordinates of the line to draw for this raw.
  function path(d) {
      return d3.line()(dimensions.map(function(p) { return [x(p), y[p](d[p])]; }));
  }

  // Draw the lines
  var paths = svgPCLines
    .selectAll("myPath")
    .data(data)
    .enter()
    .append("path")
      .attr("class", function (d) { return "resource-line c" + d.ID; } )
      .attr("candidate-id", function (d) { return d.ID; } )
      .attr("d",  path)
      .style("fill", "none" )
      .style("opacity", 0.5)
      .on("mouseover", highlight)
      .on("mouseleave", doNotHighlight );
  
  paths.on("contextmenu", selectCandidatesAsCurrentParallelCoordinates);

  // Draw the axis:
  var axisTexts = svgPCAxis
  	.selectAll("myAxis")
    // For each dimension of the dataset I add a 'g' element:
    .data(dimensions).enter()
    .append("g")
    .attr("class", "axis")
    // I translate this element to its right position on the x axis
    .attr("transform", function(d) { return "translate(" + x(d) + ")"; })
    // And I build the axis with the call function
    .each(function(d) { d3.select(this).call(d3.axisLeft().ticks(5).scale(y[d])); })
    // Add axis title
    .append("text")
    .attr("class",function(d,i) {
    	var serverId = metadata[i].ContainerId;
    	return "resource-text server-id-" + serverId;
    })
    .attr("server-id", function (d,i) {
    	return metadata[i].ContainerId;
    })
    .style("text-decoration", "underline")
      .style("text-anchor", "middle")
      .attr("y", -9)
      .text(function(d) { return d; });
  
  axisTexts.on("contextmenu", setActiveServer);
  
  updatePopulationForParallelCoordinates();
  updateParallelCoordinatesCurrentElements();
}

function updatePopulationForParallelCoordinates(){
	var lines = svgPCLines.selectAll(".resource-line");

	// find correct group
	var groupName = getDropdownName();
	var groupSet;
	if(groupName === "Comparison"){
		groupSet = activeComparisonCandidates;
	}
	if(groupName === "Marked"){
		groupSet = activeMarkedCandidates;
	}
	if(groupName === "Selected"){
		groupSet = activeSelectedCandidates;
	}
	
	if(groupSet){
		// reset all
		lines.style("visibility", "hidden");
	}else{
		lines.style("visibility", "visible");
		return;
	}
	
	groupSet.forEach(setLineAsVisible);
	
	function setLineAsVisible(value1, value2, set) {
		lines.filter(".c" + value2).style("visibility", "visible");
	}

}

function updateParallelCoordinatesElements(){
	updatePopulationForParallelCoordinates();
	updateParallelCoordinatesCurrentElements();
}

function updateParallelCoordinatesCurrentElements(){
	var lines = svgPCLines.selectAll(".resource-line");
	lines.classed("current", false);
	activeCurrentCandidates.forEach(setLineAsCurrent);
	
	function setLineAsCurrent(value1, value2, set) {
		var theLine = lines.filter(".c" + value2);
		theLine.classed("current", true);
		theLine.moveToFront();
	}
}

function selectCandidatesAsCurrentParallelCoordinates(){
	  // Prevent right click menu
	  d3.event.preventDefault();
	  var newCurrentCandidateId = this.getAttribute("candidate-id");
	  selectorClearCurrent();
	  setSelectorCurrent(newCurrentCandidateId);
}
