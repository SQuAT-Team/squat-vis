// Original: https://bl.ocks.org/heybignick/3faf257bbbbc7743bb72310d03b86ee8
const minRadius = 5;
const maxRadius = 25;
const minLineWith = 1;
const maxLineWith = 11;
const textMargin = 10;

const nodeComponentPrefix = "node-component-";

var simulation;
var node;
var link;
var origLinks;
var currentLink;
var selectLink;
var comparisonLink;
var componentSizeMap;
var linkSizeMap;
var circles;
var lables;
var arcs;
var linkContainer;

// tooltip
var nodeTip;
var linkTip;

var graphColor = d3.scaleOrdinal()
.domain([0,1])
.range(["#00ffff", "#aaaaaa"]);

function updateToolTips(){
	nodeTip = getToolTipNode();
	linkTip = getToolTipLink();
}

function render(nodes, links){
	origLinks = links;
	updateToolTips();
	var numberOfCandidates = getCurrentTotalNumberOfCandidates();
	
	setGraphSizeMaps(numberOfCandidates);
	
	var cfgGraph = {};
	cfgGraph.element = document.getElementById("architecture-container");
	cfgGraph.width = cfgGraph.element.clientWidth;
	cfgGraph.height = cfgGraph.element.clientHeight;
	
	var svg = d3.select(".architecture-container").append("svg").attr("width",  cfgGraph.width).attr("height", cfgGraph.height);
	svg.call(nodeTip);
	svg.call(linkTip);
	
	simulation = d3.forceSimulation()
    .force("link", d3.forceLink().id(function(d) { return d.ID; }))
    .force("charge", d3.forceManyBody().strength(-80).distanceMax(250))
    .force("center", d3.forceCenter(cfgGraph.width / 2, cfgGraph.height / 2));

    linkContainer = svg.append("g")
    .attr("class", "links");
    
    link = linkContainer
      .selectAll("line")
      .data(origLinks)
      .enter().append("line")
      .attr("class","link")
      .attr("stroke-width", function(d) { return linkSizeMap(getCurrentNumberOfCandidates(d)); })
      .classed("zero-element", function(d) {return getCurrentNumberOfCandidates(d) == 0})
      .each(function(d,i){
    	  d.lineId = i;
      });
    
    currentLink = linkContainer
    .selectAll("current-link")
    .data(origLinks)
    .enter().append("line")
    .attr("id", function(d,i){
    	return "line-id-" + i;
    })
    .attr("class","current-link")
    .attr("stroke-width", function(d) {
    	var currentCandidatesCount = countCandidates(activeCurrentCandidates, d.candidates);
    	return linkSizeMap(currentCandidatesCount);
    })
    .classed("zero-element", function(d) {
    	var currentCandidatesCount = countCandidates(activeCurrentCandidates, d.candidates);
    	return currentCandidatesCount == 0}
    );
    
    updateGraphComparisonLinks();
    
    selectLink = linkContainer
    .selectAll("select-link")
    .data(origLinks)
    .enter().append("line")
    .attr("id", function(d,i){
    	return "select-line-id-" + i;
    })
    .attr("candidates", function(d){ return d.candidates })
    .attr("class","select-link")
    .attr("stroke-width", function(d) { return linkSizeMap(getCurrentNumberOfCandidates(d)); })
    .classed("zero-element", function(d) {return getCurrentNumberOfCandidates(d) == 0})
    .style("opacity", 0.0);
    
  node = svg.append("g")
    .attr("class", "nodes")
    .selectAll("g")
    .data(nodes)
    .enter().append("g")
    .attr("class","node")
    .attr("id", function(d){ return nodeComponentPrefix + d.ID; })
    .classed("zero-element", function(d) {return getCurrentNumberOfCandidates(d) == 0});
    
  node
  .on('mouseover', nodeTip.show)
  .on('mouseout', nodeTip.hide);
  
  selectLink
  .on('mouseover', linkTip.show)
  .on('mouseout', linkTip.hide);
  
  var arc;
  var comparisonArc;
  
  node.each(function(d) {
	d.currentRadius = componentSizeMap(getCurrentNumberOfCandidates(d));}
  )

  updateGraphCurrentNodes();

  circles = node.append("circle")
      .attr("r", function(d) { return d.currentRadius; })
      .attr("candidates", function(d){ return d.Candidates })
	  .classed("contains-current", function(d){
		  return d.currentCandidatesCount > 0;
	  });
  
  node.call(d3.drag()
          .on("start", dragstarted)
          .on("drag", dragged)
          .on("end", dragended));

  // select current candidates of element
  circles.on("contextmenu", selectCandidatesAsCurrent);
  selectLink.on("contextmenu", selectCandidatesAsCurrent);
  
  lables = node.append("text")
  	  .attr("class", "component-node-text")
      .text(function(d) {
      	if(shortenName){
      	  var nameParts = d.Name.split(".");
      	  return nameParts[nameParts.length-1];
      	}else{
      	  return d.Name;
      	}
      })
      .attr('x', 0)
      .attr('y', function(d){return d.currentRadius + textMargin;})
      .style("pointer-events", "none");

  simulation
      .nodes(nodes)
      .on("tick", ticked);

  simulation.force("link")
      .links(origLinks)
      .distance(function(d) { return linkDistance;}).strength(0.7);

  // Link arrow heads
  createArrowHeads(minRadius, maxRadius, svg);
  link.attr("marker-end", function(d){return "url(#triangle" + Math.ceil(d.target.currentRadius) + ")";});
  
  markReduced(link, currentLink, selectLink, comparisonLink, node, numberOfCandidates);
  
  function ticked() {    
	    tickLinks(currentLink);
	    tickLinks(selectLink);
	    tickLinks(link);
	    tickLinks(comparisonLink);
	    
    node.attr("transform", function(d) {
    	    	return "translate(" + limit(d.x, cfgGraph.width) + "," + limit(d.y, cfgGraph.height) + ")";});
  }
  
  function createArrowHeads(min, max, element){
		for (i = min; i <= max; i++) {
			element.append("svg:defs").append("svg:marker")
			.attr("class", "line-head")
		    .attr("id", "triangle"+i)
		    .attr("refX", 6 + i)
		    .attr("refY", 3)
		    .attr("markerWidth", 7.5)
		    .attr("markerHeight", 7.5)
		    .attr("markerUnits","userSpaceOnUse")
		    .attr("orient", "auto")
		    .append("path")
		    .attr("d", "M 0 0 6 3 0 6 1.5 3");
		}
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
  	  return Math.max(maxRadius, Math.min(upperLimit - maxRadius, value));
  }
  
};

function dragstarted(d) {
  var node =  d3.select(this);
  node.moveToFront();
  node.classed("node-highlight", true);
      
  if (!d3.event.active) simulation.alphaTarget(0.3).restart();
  simulation.alpha(0);
  d.fx = d.x;
  d.fy = d.y;
}

function dragged(d) {
  d.fx = d3.event.x;
  d.fy = d3.event.y;
}

function dragended(d) {
  var node =  d3.select(this);
  node.classed("node-highlight", false);
  
  if (!d3.event.active) simulation.alphaTarget(0);
  simulation.alpha(1);
  d.fx = null;
  d.fy = null;
}

function setGraphSizeMaps(numberOfCandidates){
	componentSizeMap = d3.scaleLinear()
	  .domain([1, numberOfCandidates])
	  .range([minRadius, maxRadius]);
	
	linkSizeMap = d3.scaleLinear()
	  .domain([1, numberOfCandidates])
	  .range([minLineWith, maxLineWith]);
}

function updateGraphElements(){
	numberOfCandidates = getCurrentTotalNumberOfCandidates();
	setGraphSizeMaps(numberOfCandidates);

	link
    .attr("stroke-width", function(d) { return linkSizeMap(getCurrentNumberOfCandidates(d)); })
    .classed("zero-element", function(d) {return getCurrentNumberOfCandidates(d) == 0; });
	
	selectLink
    .attr("stroke-width", function(d) { return linkSizeMap(getCurrentNumberOfCandidates(d)); })
    .classed("zero-element", function(d) {return getCurrentNumberOfCandidates(d) == 0; });
	
	node
    .classed("zero-element", function(d) {return getCurrentNumberOfCandidates(d) == 0});
	
	circles
    .attr("r", function(d) {
  	  var currentRadius = componentSizeMap(getCurrentNumberOfCandidates(d));
  	  d.currentRadius = currentRadius;
  	  return currentRadius; });
	
	lables
    .attr('y', function(d){return d.currentRadius + textMargin;});
	
	link
	.attr("marker-end", function(d){return "url(#triangle" + Math.ceil(d.target.currentRadius) + ")";});
	
	updateGraphCurrentElements();
	markReduced(link, currentLink, selectLink, comparisonLink, node, numberOfCandidates);
}

function updateGraphCurrentElements(){
	updateGraphCurrentNodes();
	updateGraphCurrentLinks();
	updateToolTips();
}

function updateGraphCurrentLinks(){
    currentLink
    .attr("stroke-width", function(d) {
    	var currentCandidatesCount = countCandidates(activeCurrentCandidates, d.candidates);
    	return linkSizeMap(currentCandidatesCount);
    })
    .classed("zero-element", function(d) {
    	var currentCandidatesCount = countCandidates(activeCurrentCandidates, d.candidates);
    	return currentCandidatesCount == 0}
    );
    updateGraphComparisonLinks();
}

function updateGraphComparisonLinks(){
	// remove old arcs
	if(comparisonLink){
		linkContainer.selectAll(".comparison-link").remove();
	}
		
	linkContainer
		.selectAll(".comparison-link")
		.data(origLinks)
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
				  return linkSizeMap(comparisonData.length-j); });
	  	}
		});
	
	linkContainer.selectAll(".select-link").moveToFront();
	comparisonLink = linkContainer.selectAll(".comparison-link");
	// reposition links
	simulation.restart();
}

function updateGraphCurrentNodes(){
	// remove old arcs
	if(arcs){
		arcs.selectAll(".current-arc").remove();
		arcs.selectAll(".comparison-arc").remove();
	}
	
	// set new arcs
	arcs = node
	  .each(function(d) {
		  var currentCandidatesCount = countCandidates(activeCurrentCandidates, d.Candidates);
		  var data = {current: currentCandidatesCount, notCurrent: getCurrentNumberOfCandidates(d)-currentCandidatesCount};
		  var pie = d3.pie().sort(null)
		  .value(function(d) {return d.value; });
		  d.data_ready = pie(d3.entries(data));
		  d.currentCandidatesCount = currentCandidatesCount;
		  
		  arc = d3.arc()
		    .innerRadius(0)
		    .outerRadius(d.currentRadius);
		  		  
		  d3.select(this)
		  .selectAll('pie')
		  .data(function(d){return d.data_ready})
		  .enter()
		  .append('path')
		  .attr('class', 'current-arc')
		  .attr('d', arc)
		  .attr('fill', function(d,i){ return(graphColor(i)) })
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
		  comparisonArc = d3.arc()
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
		  .attr('d', comparisonArc)
		  .attr("stroke", "black")
		  .style("stroke-width", "0.3px")
		  .style("opacity", 1.0);
	  })
	  
	  // circle should be in front, otherwise no dragging
	  arcs.selectAll("circle")
	  .classed("contains-current", function(d){
		  return d.currentCandidatesCount > 0;
	  })
	  .moveToFront();
}

function markReduced(links, currentLinks, selectLinks, comparisonLinks, nodes, maxValue){
	nodes.classed("visibleNode", function(d){
		return shouldBeVisible(d, maxValue);
	});
	links.classed("visibleLink", false);
	currentLinks.classed("visibleLink", false);
	selectLinks.classed("visibleLink", false);	
	comparisonLinks.classed("visibleLink", false);	

	links.filter(":not(.zero-element)")
	.classed("visibleLink", function(d){
		if (shouldBeVisible(d, maxValue)) {
			nodes.filter("#" + nodeComponentPrefix + d.source.ID)
			.classed("visibleNode", true);
			nodes.filter("#" + nodeComponentPrefix + d.target.ID)
			.classed("visibleNode", true);
			currentLinks.filter("#line-id-" + d.lineId)
			.classed("visibleLink", true);
			selectLinks.filter("#select-line-id-" + d.lineId)
			.classed("visibleLink", true);
			comparisonLinks.filter(".comparison-line-id-" + d.lineId)
			.classed("visibleLink", true);
			return true;
		} else {
			return false;
		}
	});
}
