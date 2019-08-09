// Original: https://bl.ocks.org/heybignick/3faf257bbbbc7743bb72310d03b86ee8
const minRadiusBiGraph = 5;
const maxRadiusBiGraph = 25;
const rectWidthBigGraph = 40;
const rectHeightBigGraph = 20;
const minLineWithBiGraph = 1;
const maxLineWithBiGraph = 11;
const textMarginBiGraph = 10;

const nodeComponentPrefixBiGraph = "node-bi-graph-component-";
const nodeServerPrefixBiGraph = "node-server-";

var simulationBiGraph;
var nodeBiGraph;
var servers;
var linkBiGraph;
var origLinksBiGraph;
var currentLinkBiGraph;
var selectLinkBiGraph;
var comparisonLinkBiGraph;
var componentSizeMapBiGraph;
var linkSizeMapBiGraph;
var circlesBiGraph;
var lablesBiGraph;
var arcsBiGraph;
var linkContainerBiGraph;

//tooltip
var nodeTipBiGraph;
var linkTipBiGraph;

var biGraphColor = d3.scaleOrdinal()
.domain([0,1])
.range(["#00ffff", "#aaaaaa"]);

function updateToolTipsBiGraph(){
	nodeTipBiGraph = getToolTipNode();
	linkTipBiGraph = getToolTipLink();
}

function renderBiGraph(nodes, serverNodes, links){
	origLinksBiGraph = links;
	updateToolTipsBiGraph();
	var numberOfCandidates = getCurrentTotalNumberOfCandidates();
	
	setGraphSizeMapsBiGraph(numberOfCandidates);
	
	var cfgGraph = {};
	cfgGraph.element = document.getElementById("allocation-container");
	cfgGraph.width = cfgGraph.element.clientWidth;
	cfgGraph.height = cfgGraph.element.clientHeight;
	
	var svgBiGraph = d3.select(".allocation-container").append("svg").attr("width",  cfgGraph.width).attr("height", cfgGraph.height);
	svgBiGraph.call(nodeTipBiGraph);
	svgBiGraph.call(linkTipBiGraph);
	
	simulationBiGraph = d3.forceSimulation()
    .force("link", d3.forceLink().id(function(d) { return d.ID; }))
    .force("charge", d3.forceManyBody().strength(-80).distanceMax(250))
    .force("center", d3.forceCenter(cfgGraph.width / 2, cfgGraph.height / 2));
	    
    linkContainerBiGraph = svgBiGraph.append("g")
    .attr("class", "links");
    
    linkBiGraph = linkContainerBiGraph
      .attr("class", "links")
      .selectAll("line")
      .data(origLinksBiGraph)
      .enter().append("line")
      .attr("class","link")
      .attr("stroke-width", function(d) { return linkSizeMapBiGraph(getCurrentNumberOfCandidates(d)); })
      .classed("zero-element", function(d) {return getCurrentNumberOfCandidates(d) == 0})
      .each(function(d,i){
    	  d.lineId = i;
      });
  	
    currentLinkBiGraph = linkContainerBiGraph
    .selectAll("current-link")
    .data(origLinksBiGraph)
    .enter().append("line")
    .attr("id", function(d,i){
    	return "line-bi-graph-id-" + i;
    })
    .attr("class","current-link")
    .attr("stroke-width", function(d) {
    	var currentCandidatesCount = countCandidates(activeCurrentCandidates, d.candidates);
    	return linkSizeMapBiGraph(currentCandidatesCount);
    })
    .classed("zero-element", function(d) {
    	var currentCandidatesCount = countCandidates(activeCurrentCandidates, d.candidates);
    	return currentCandidatesCount == 0}
    );
    
    updateBiGraphComparisonLinks();
    
    selectLinkBiGraph = linkContainerBiGraph
    .selectAll("select-link")
    .data(origLinksBiGraph)
    .enter().append("line")
    .attr("id", function(d,i){
    	return "select-line-bi-graph-id-" + i;
    })
    .attr("candidates", function(d){ return d.candidates })
    .attr("class","select-link")
    .attr("stroke-width", function(d) { return linkSizeMapBiGraph(getCurrentNumberOfCandidates(d)); })
    .classed("zero-element", function(d) {return getCurrentNumberOfCandidates(d) == 0})
    .style("opacity", 0.0);
    
    servers = svgBiGraph.append("g")
    .attr("class", "servers")
    .selectAll("g")
    .data(serverNodes)
    .enter().append("g")
    .attr("class", "server")
    .attr("id", function(d){ return nodeServerPrefixBiGraph + d.ID; });
    
    var rects = servers.append("rect")
    .attr("height",  rectHeightBigGraph)
    .attr("width",  rectWidthBigGraph)
    .attr("fill", function(d) { return biGraphColor(1); })
    .attr("class",function(d){ return "server server-id-" + d.ID; })
    .attr("server-id", function(d){ return d.ID; });
    servers.call(d3.drag()
        .on("start", dragstartedBiGraph)
        .on("drag", draggedBiGraph)
        .on("end", dragendedBiGraph));
    
    
  nodeBiGraph = svgBiGraph.append("g")
    .attr("class", "nodes")
    .selectAll("g")
    .data(nodes)
    .enter().append("g")
    .attr("class","node")
    .attr("id", function(d){ return nodeComponentPrefixBiGraph + d.ID; })
    .classed("zero-element", function(d) {return getCurrentNumberOfCandidates(d) == 0});
  
  nodeBiGraph
  .on('mouseover', nodeTipBiGraph.show)
  .on('mouseout', nodeTipBiGraph.hide);
  
  selectLinkBiGraph
  .on('mouseover', linkTipBiGraph.show)
  .on('mouseout', linkTipBiGraph.hide);
  
  var arcBiGraph;
  var comparisonArcBiGraph;
  
  nodeBiGraph.each(function(d) {
		d.currentRadius = componentSizeMapBiGraph(getCurrentNumberOfCandidates(d));}
  )
  
  updateBiGraphCurrentNodes();
  
  circlesBiGraph = nodeBiGraph.append("circle")
      .attr("r", function(d) { return d.currentRadius; })
      .attr("candidates", function(d){ return d.Candidates })
      .classed("contains-current", function(d){
		  return d.currentCandidatesCount > 0;
	  });
  nodeBiGraph.call(d3.drag()
          .on("start", dragstartedBiGraph)
          .on("drag", draggedBiGraph)
          .on("end", dragendedBiGraph));
  
  // select current candidates of element
  rects.on("contextmenu", setActiveServer);
  circlesBiGraph.on("contextmenu", selectCandidatesAsCurrent);
  selectLinkBiGraph.on("contextmenu", selectCandidatesAsCurrent);
  
  lablesBiGraph = nodeBiGraph.append("text")
  	  .attr("class", "component-node-text")
      .text(function(d) {
        return d.Name;
      })
      .attr('x', 0)
      .attr('y', function(d){return d.currentRadius + textMarginBiGraph;})
      .style("pointer-events", "none");

  
  var rectLabels = servers.append("text")
	  .attr("class", "server-node-text")
  .text(function(d) {
    return d.Name;
  })
  .attr('x', function(d){return rectWidthBigGraph/2;})
  .attr('y', function(d){return rectHeightBigGraph + textMarginBiGraph;});

  nodeBiGraph.append("title")
      .text(function(d) { return d.Name; });

  simulationBiGraph
  .nodes(nodes.concat(serverNodes))
  .on("tick", ticked);
  

  simulationBiGraph.force("link")
      .links(origLinksBiGraph)
      .distance(function(d) { return linkDistanceBiGraph;});
  
  markReducedBiGraph(linkBiGraph, currentLinkBiGraph, selectLinkBiGraph, comparisonLinkBiGraph, nodeBiGraph, servers, numberOfCandidates);
  
  function ticked() {
	    tickLinks(currentLinkBiGraph);
	    tickLinks(selectLinkBiGraph);
	    tickLinks(linkBiGraph);
	    tickLinks(comparisonLinkBiGraph);
	    
	      nodeBiGraph
	      .attr("transform", function(d) {
	      return "translate(" + limit(d.x, cfgGraph.width) + "," + limit(d.y, cfgGraph.height) + ")";
	      });
	      
	      servers
	      .attr("transform", function(d) {
	          return "translate(" + limit(d.x - (rectWidthBigGraph / 2), cfgGraph.width) + "," + limit(d.y - (rectHeightBigGraph / 2), cfgGraph.height) + ")";
	       });
	    }
  
  function tickLinks(theLinks){
  	if(theLinks){
  		theLinks
  	    .attr("x1", function(d) { return limit(d.source.x, cfgGraph.width); })
  	    .attr("y1", function(d) { return limit(d.source.y, cfgGraph.height); })
  	    .attr("x2", function(d) { return limit(d.target.x, cfgGraph.width); })
  	    .attr("y2", function(d) { return limit(d.target.y, cfgGraph.height); });	
  	}
  }
  
  function limit(value, upperLimit){
	  return Math.max(maxRadiusBiGraph, Math.min(upperLimit - maxRadiusBiGraph, value));
  }
};

function dragstartedBiGraph(d) {
  var node =  d3.select(this);
  node.moveToFront();
  node.classed("node-highlight", true);
	  
  if (!d3.event.active) simulationBiGraph.alphaTarget(0.3).restart();
  simulation.alpha(0);
  d.fx = d.x;
  d.fy = d.y;
}

function draggedBiGraph(d) {
  d.fx = d3.event.x;
  d.fy = d3.event.y;
}

function dragendedBiGraph(d) {
  var node =  d3.select(this);
  node.classed("node-highlight", false);
  
  if (!d3.event.active) simulationBiGraph.alphaTarget(0);
  simulation.alpha(1);
  d.fx = null;
  d.fy = null;
}

function setGraphSizeMapsBiGraph(numberOfCandidates){
	componentSizeMapBiGraph = d3.scaleLinear()
	  .domain([1, numberOfCandidates])
	  .range([minRadiusBiGraph, maxRadiusBiGraph]);
	
	linkSizeMapBiGraph = d3.scaleLinear()
	  .domain([1, numberOfCandidates])
	  .range([minLineWithBiGraph, maxLineWithBiGraph]);
}

function updateBiGraphElements(){
	numberOfCandidates = getCurrentTotalNumberOfCandidates();
	setGraphSizeMapsBiGraph(numberOfCandidates);

	linkBiGraph
	.classed("zero-element", function(d) {return getCurrentNumberOfCandidates(d) == 0})
    .attr("stroke-width", function(d) { return linkSizeMapBiGraph(getCurrentNumberOfCandidates(d)); });
	
	selectLinkBiGraph
    .attr("stroke-width", function(d) { return linkSizeMapBiGraph(getCurrentNumberOfCandidates(d)); })
    .classed("zero-element", function(d) {return getCurrentNumberOfCandidates(d) == 0; });
	
	nodeBiGraph
    .classed("zero-element", function(d) {return getCurrentNumberOfCandidates(d) == 0});
	
	circlesBiGraph
    .attr("r", function(d) {
  	  var currentRadius = componentSizeMapBiGraph(getCurrentNumberOfCandidates(d));
  	  d.currentRadius = currentRadius;
  	  return currentRadius; });
	
	lablesBiGraph
    .attr('y', function(d){return d.currentRadius + textMarginBiGraph;});
	
	updateBiGraphCurrentElements();
	markReducedBiGraph(linkBiGraph, currentLinkBiGraph, selectLinkBiGraph, comparisonLinkBiGraph, nodeBiGraph, servers, numberOfCandidates);
}

function updateBiGraphCurrentElements(){
	updateBiGraphCurrentNodes();
	updateBiGraphCurrentLinks();
	updateToolTipsBiGraph();
}

function updateBiGraphCurrentLinks(){
    currentLinkBiGraph
    .attr("stroke-width", function(d) {
    	var currentCandidatesCount = countCandidates(activeCurrentCandidates, d.candidates);
    	return linkSizeMapBiGraph(currentCandidatesCount);
    })
    .classed("zero-element", function(d) {
    	var currentCandidatesCount = countCandidates(activeCurrentCandidates, d.candidates);
    	return currentCandidatesCount == 0}
    );
    updateBiGraphComparisonLinks();
}

function updateBiGraphComparisonLinks(){
	// remove old arcs
	if(comparisonLinkBiGraph){
		linkContainerBiGraph.selectAll(".comparison-link").remove();
	}
		
	linkContainerBiGraph
		.selectAll("comparison-link")
		.data(origLinksBiGraph)
		.enter()
		.each(function(d,i){
			
    	var linkId = i;
  	  	var comparisonCandidateIds = getComparisonCandidateIds(d.candidates);
  	  	var comparisonData = [];
  	  	for (i = 0; i < comparisonCandidateIds.length; i++) {
  	  		comparisonData.push({source: d.source, target: d.target, id: comparisonCandidateIds[i]});
  	  	};
    	  	  	
		for (j = 0; j < comparisonData.length; j++) {
			var newSelection = d3.select(this)
			  .append("line")
			  .attr("class", function(d,i){
				 var candidateId = comparisonData[j].id;
				 return 'comparison-link c'+candidateId + ' comparison-line-id-'+linkId;
			  })
			  .attr("stroke-width", function(d,i) {
				  return linkSizeMapBiGraph(comparisonData.length-j); });
	  	}
		});
	
	linkContainerBiGraph.selectAll(".select-link").moveToFront();
	comparisonLinkBiGraph = linkContainerBiGraph.selectAll(".comparison-link");
	// reposition links
	simulationBiGraph.restart();
}


function updateBiGraphCurrentNodes(){
	// remove old arcs
	if(arcsBiGraph){
		arcsBiGraph.selectAll(".current-arc").remove();
		arcsBiGraph.selectAll(".comparison-arc").remove();
	}
	
	// set new arcs
	arcsBiGraph = nodeBiGraph
	  .each(function(d) {
		  var currentCandidatesCount = countCandidates(activeCurrentCandidates, d.Candidates);
		  var data = {current: currentCandidatesCount, notCurrent: getCurrentNumberOfCandidates(d)-currentCandidatesCount};
		  var pie = d3.pie().sort(null)
		  .value(function(d) {return d.value; });
		  d.data_ready = pie(d3.entries(data));
		  d.currentCandidatesCount = currentCandidatesCount;
		  arcBiGraph = d3.arc()
		    .innerRadius(0)
		    .outerRadius(d.currentRadius);
		  		  
		  d3.select(this)
		  .selectAll('pie')
		  .data(function(d){return d.data_ready})
		  .enter()
		  .append('path')
		  .attr('class', 'current-arc')
		  .attr('d', arcBiGraph)
		  .attr('fill', function(d,i){ return(biGraphColor(i)) })
		  .attr("stroke", "black")
		  .style("stroke-width", "0.3px")
		  .style("opacity", 1.0);
		  
		  var comparisonCandidateIds = getComparisonCandidateIds(d.Candidates);
		  var comparisonData = {};
		  for (i = 0; i < comparisonCandidateIds.length; i++) {
			  comparisonData[comparisonCandidateIds[i]] = 1;
		  }
		  var comparisonPie = d3.pie().sort(null)
		  .value(function(d) {return d.value; });
		  d.comparison_data_ready = comparisonPie(d3.entries(comparisonData));
		  comparisonArcBiGraph = d3.arc()
		    .innerRadius(0)
		    .outerRadius(d.currentRadius);

		  d3.select(this)
		  .selectAll('comparison-pie')
		  .data(function(d){return d.comparison_data_ready})
		  .enter()
		  .append('path')
		  .attr('class', function(d){
			  var candidateId = d.data.key;
			  return 'comparison-arc c'+candidateId;
		  })
		  .attr('d', comparisonArcBiGraph)
		  .attr("stroke", "black")
		  .style("stroke-width", "0.3px")
		  .style("opacity", 1.0);
	  })
	  
	  // circle should be in front, otherwise no dragging
	  arcsBiGraph.selectAll("circle")
	  .classed("contains-current", function(d){
		  return d.currentCandidatesCount > 0;
	  })
	  .moveToFront();
}

function markReducedBiGraph(links, currentLinks, selectLinks, comparisonLinks, nodes, servers, maxValue){	
	links.classed("visibleLink", false);
	nodes.classed("visibleNode", false);
	servers.classed("visibleNode", false);
	currentLinks.classed("visibleLink", false);
	selectLinks.classed("visibleLink", false);
	comparisonLinks.classed("visibleLink", false);
	
	links.filter(":not(.zero-element)")
	.classed("visibleLink", function(d){
		if (getCurrentNumberOfCandidates(d) < maxValue) {
			nodes.filter("#" + nodeComponentPrefixBiGraph + d.source.ID)
			.classed("visibleNode", true);
			servers.filter("#" + nodeServerPrefixBiGraph + d.target.ID)
			.classed("visibleNode", true);
			currentLinks.filter("#line-bi-graph-id-" + d.lineId)
			.classed("visibleLink", true);
			selectLinks.filter("#select-line-bi-graph-id-" + d.lineId)
			.classed("visibleLink", true);
			comparisonLinks.filter(".comparison-line-id-" + d.lineId)
			.classed("visibleLink", true);
			return true;
		} else {
			return false;
		}
	});
}
