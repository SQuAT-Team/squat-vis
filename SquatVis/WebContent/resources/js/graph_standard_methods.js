var activeCurrentCandidates = new Set();
var activeSelectedCandidates = new Set();
var activeMarkedCandidates = new Set();
var activeComparisonCandidates = new Set();
var activeSelectedServer = null;
var initializedActiveCandidates = false;

function initializeActiveCandidateSets(){
	initializedActiveCandidates = true;
	for (i = 0; i < parsedValues.length; i++) {		
		var candidate = parsedValues[i];
		var candidateId = candidate.ID;
		if(candidate.SelectorTags.includes("marked")){
			activeMarkedCandidates.add(candidateId);
		}
		if(candidate.SelectorTags.includes("selected")){
			activeSelectedCandidates.add(candidateId);
		}
		if(candidate.SelectorTags.includes("comparison")){
			activeComparisonCandidates.add(candidateId);
		}
		if(candidate.SelectorTags.includes("current")){
			activeCurrentCandidates.add(candidateId);
		}
	}
}

function getCurrentTotalNumberOfCandidates(){
	if(!initializedActiveCandidates){
		initializeActiveCandidateSets();
	}
	var groupName = getDropdownName();	
	if(groupName === "All"){
		return parsedValues.length;
	}
	if(groupName === "Comparison"){
		return activeComparisonCandidates.size;
	}
	if(groupName === "Marked"){
		return activeMarkedCandidates.size;
	}
	if(groupName === "Selected"){
		return activeSelectedCandidates.size;
	}
	console.log("Did not find value for getCurrentTotalNumberOfCandidates");
}

function getNumberOfCandidatesFromString(candidatesString){
	if(candidatesString.length == 0){
		return 0;
	}else{
		return (candidatesString.match(/ \+ /g) || []).length + 1;
	}
}

function getCurrentNumberOfCandidates(d){
	// add decision
	var candidatesString = d.candidates;
	if(!candidatesString){
		candidatesString = d.Candidates;
	}
	var groupName = getDropdownName();
	if(groupName === "All"){
		return getNumberOfCandidatesFromString(candidatesString);
	}
	if(groupName === "Comparison"){
		return countCandidates(activeComparisonCandidates, candidatesString);
	}
	if(groupName === "Marked"){
		return countCandidates(activeMarkedCandidates, candidatesString);
	}
	if(groupName === "Selected"){
		return countCandidates(activeSelectedCandidates, candidatesString);
	}
}

function countCandidates(candidateSet, candidateString){
	var count = 0;
	var elementsCandidates = candidateStringToIds(candidateString);
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
	for (i = 0; i < elementsCandidates.length; i++) {
		if(candidateSet.has(elementsCandidates[i]) && (!groupSet || groupSet.has(elementsCandidates[i]))){
			count++;
		}
	}
	return count;
}

function getComparisonCandidateIds(candidateString){
	var candidateIds = [];
	var elementsCandidates = candidateStringToIds(candidateString);
	var groupName = getDropdownName();
	var groupSet;
	if(groupName === "Marked"){
		groupSet = activeMarkedCandidates;
	}
	if(groupName === "Selected"){
		groupSet = activeSelectedCandidates;
	}
	for (i = 0; i < elementsCandidates.length; i++) {
		if(activeComparisonCandidates.has(elementsCandidates[i]) && (!groupSet || groupSet.has(elementsCandidates[i]))){
			candidateIds.push(elementsCandidates[i]);
		}
	}
	return candidateIds;
}

function candidateStringToIds(candidateString){
	return candidateString.split(" + ");
}
function filterNotVisibleCandidates(candidateIds){
	var groupName = getDropdownName();
	var groupSet;
	if(groupName === "All"){
		return candidateIds;
	}
	if(groupName === "Comparison"){
		groupSet = activeComparisonCandidates;
	}
	if(groupName === "Marked"){
		groupSet = activeMarkedCandidates;
	}
	if(groupName === "Selected"){
		groupSet = activeSelectedCandidates;
	}
	var filtered = candidateIds.filter(function(value, index, arr){
	    return groupSet.has(value);
	});
	return filtered;
}

function getDropdownName() {
	return $("#graphModeDropdownMenuButton").text().trim();
}

function getToolTipNode(){
	return d3.tip().attr('class', 'd3-tip').html(function(d) {
		var candidatesString = d.Candidates;
		return "Name: " + d.Name + "<br/>"
		+ "ID: " + d.ID + "<br/>"
		+ "Total: " + getCurrentNumberOfCandidates(d) + "<br/>"
		+ "Current: " + countCandidates(activeCurrentCandidates, candidatesString) + "<br/>"
		; });
}

function getToolTipLink(){
	return d3.tip().attr('class', 'd3-tip').html(function(d) {
		var candidatesString = d.candidates;
		return "Source: " + d.source.Name + "<br/>"
		+ "Target: " + d.target.Name + "<br/>"
		+ "Total: " + getCurrentNumberOfCandidates(d) + "<br/>"
		+ "Current: " + countCandidates(activeCurrentCandidates, candidatesString) + "<br/>"
		; });
}

function selectCandidatesAsCurrent(){
	  // Prevent right click menu
	  d3.event.preventDefault();
	  var newCurrentCandidatesId = candidateStringToIds(this.getAttribute("candidates"));
	  selectorClearCurrent();
	  setAllSelectorCurrent(filterNotVisibleCandidates(newCurrentCandidatesId));
}

function setActiveServer(){
	d3.event.preventDefault();
	var element = d3.select(this);
	var id = element.attr("server-id");
	var elementStatus = element.classed("selected-server");
	d3.selectAll(".server-id-" + id).classed("selected-server", function(d){return !elementStatus});
}
