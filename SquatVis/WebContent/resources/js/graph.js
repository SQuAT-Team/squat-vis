updateColorMappingCss();
initializeComparisonMode();
initializeRadarRightClickListener();

function initializeRadarRightClickListener(){
	d3.selectAll("path.radarArea").on("contextmenu", selectRadarCandidateAsCurrent);
}

function selectRadarCandidateAsCurrent(){
	  // Prevent right click menu
	  d3.event.preventDefault();
	  var newCurrentCandidateId = this.getAttribute("candidateId");
	  selectorClearCurrent();
	  setSelectorCurrent((newCurrentCandidateId));		
}

function changeReduceGraph() {
	const populationActiveCss = "reduce-graph-active";
	var checkboxValue = $("#reduceGraphCheckbox").prop("checked");
	var classes = document.getElementById("graphOptions").classList;
	if (checkboxValue) {
		if (!classes.contains(populationActiveCss)) {
			classes.add(populationActiveCss);
		}
	} else {
		classes.remove(populationActiveCss);
	}

	// notify backend
	setReduceGraph_application([ {
		name : 'param',
		value : checkboxValue
	} ]);
}


function showAll() {
	setDropdownName("All");
	deactivateComparisonMode();
	updateGraphElements();
	updateBiGraphElements();
	updateParallelCoordinatesElements();
	setGraphOptions("All-active");
	setMode_application([ {
		name : 'param',
		value : "All"
	} ]);
}

function showMarked() {
	setDropdownName("Marked");
	deactivateComparisonMode();
	updateGraphElements();
	updateBiGraphElements();
	updateParallelCoordinatesElements();
	setGraphOptions("Marked-active");
	setMode_application([ {
		name : 'param',
		value : "Marked"
	} ]);
}

function showSelected() {
	setDropdownName("Selected");
	deactivateComparisonMode();
	updateGraphElements();
	updateBiGraphElements();
	updateParallelCoordinatesElements();
	setGraphOptions("Selected-active");
	setMode_application([ {
		name : 'param',
		value : "Selected"
	} ]);
}

function showComparison() {
	setDropdownName("Comparison");
	activateComparisonMode();
	updateGraphElements();
	updateBiGraphElements();
	updateParallelCoordinatesElements();
	setGraphOptions("Comparison-active");
	setMode_application([ {
		name : 'param',
		value : "Comparison"
	} ]);
}

function setDropdownName(name) {
	$("#graphModeDropdownMenuButton").text(name);
}

function initializeComparisonMode(){
	var groupName = getDropdownName();	
	if (groupName === "Comparison"){
		activateComparisonMode();
	} else {
		deactivateComparisonMode();
	}
}

function activateComparisonMode() {
	$("#graphOptions").addClass("comparison-mode");
	$("#graphOptions").removeClass("normal-mode");
}

function deactivateComparisonMode() {
	$("#graphOptions").removeClass("comparison-mode");
	$("#graphOptions").addClass("normal-mode");
}

function updateColorMappingCss(){
	$( "#colorMappingsCss" ).remove();
	$( "#colorMappings" ).clone().attr('id', 'colorMappingsCss').appendTo( "#head" );
}

function setGraphOptions(option){
	var graphOptionsElement = $( "#graphOptions" );
	graphOptionsElement.removeClass("All-active");
	graphOptionsElement.removeClass("Comparison-active");
	graphOptionsElement.removeClass("Marked-active");
	graphOptionsElement.removeClass("Selected-active");
	graphOptionsElement.addClass(option);
}

function updateStarElements(id, addSelector, removeSelector, moveToFront){
	var starContainer = d3.select("#starOverviewContent");
	var starRadarWrapper = starContainer.selectAll("g.radarWrapper.c"+id);
	var starRadarCircleWrapper = starContainer.selectAll("g.radarCircleWrapper.c"+id);
	if(addSelector){
		starRadarWrapper.classed(addSelector, true);
		starRadarCircleWrapper.classed(addSelector, true);
	}
	if(removeSelector){
		starRadarWrapper.classed(removeSelector, false);
		starRadarCircleWrapper.classed(removeSelector, false);
	}
}

function updateStarPopulationElements(id, addSelector, removeSelector){
	var starContainer = d3.select("#starOverviewContent");
	var starRadarWrapper = starContainer.selectAll("g.radarWrapper.c"+id);
	var starRadarCircleWrapper = starContainer.selectAll("g.radarWrapper.c"+id);
	if(addSelector){
		starRadarWrapper.classed(addSelector, true);
		starRadarCircleWrapper.classed(addSelector, true);
	}
	if(removeSelector){
		starRadarWrapper.classed(removeSelector, false);
		starRadarCircleWrapper.classed(removeSelector, false);
	}
}

function updateStarPopulationElementsWithSelector(selector, addSelector, removeSelector){
	var starContainer = d3.select("#starOverviewContent");
	var starRadarWrapper = starContainer.selectAll("g.radarWrapper."+selector);
	var starRadarCircleWrapper = starContainer.selectAll("g.radarWrapper."+selector);
	if(addSelector){
		starRadarWrapper.classed(addSelector, true);
		starRadarCircleWrapper.classed(addSelector, true);
	}
	if(removeSelector){
		starRadarWrapper.classed(removeSelector, false);
		starRadarCircleWrapper.classed(removeSelector, false);
	}
}

function updateAllStarElements(addSelector, removeSelector){
	var starContainer = d3.select("#starOverviewContent");
	var starRadarWrapper = starContainer.selectAll("g.radarWrapper");
	var starRadarCircleWrapper = starContainer.selectAll("g.radarWrapper");
	if(addSelector){
		starRadarWrapper.classed(addSelector, true);
		starRadarCircleWrapper.classed(addSelector, true);
	}
	if(removeSelector){
		starRadarWrapper.classed(removeSelector, false);
		starRadarCircleWrapper.classed(removeSelector, false);
	}
}

function starCandidateMoveToFront(id){
	var starContainer = d3.select("#starOverviewContent");
	starContainer.selectAll("g.radarWrapper.c"+id).moveToFront();
	starContainer.selectAll("text.tooltip").moveToFront();
	starContainer.selectAll("g.tooltipAxisWrapper").moveToFront();
	starContainer.selectAll("g.radarCircleWrapper").moveToFront();
	starContainer.selectAll("g.radarCircleWrapper.c"+id).moveToFront();
}

function starCandidatesMoveToFront(ids){
	var starContainer = d3.select("#starOverviewContent");
	for (let i = 0; i < ids.length; i++) {
		starContainer.selectAll("g.radarWrapper.c"+ids[i]).moveToFront();
	}
	starContainer.selectAll("text.tooltip").moveToFront();
	starContainer.selectAll("g.tooltipAxisWrapper").moveToFront();
	starContainer.selectAll("g.radarCircleWrapper").moveToFront();
	for (let i = 0; i < ids.length; i++) {
		starContainer.selectAll("g.radarCircleWrapper.c"+ids[i]).moveToFront();
	}
}


function changePopulation() {
	const populationActiveCss = "population-active";
	var checkboxValue = $("#showPopulationCheckbox").prop("checked");
	var classes = document.getElementById("graphOptions").classList;
	if (checkboxValue) {
		if (!classes.contains(populationActiveCss)) {
			classes.add(populationActiveCss);
		}
	} else {
		classes.remove(populationActiveCss);
	}

	// notify backend
	setShowPopulation_application([ {
		name : 'param',
		value : checkboxValue
	} ]);
}
