const candidateCircleCss_matrix = "circle.candidate-circle";
var allCandidateCircles_matrix = $("g.cell").children(candidateCircleCss_matrix);

function setSelectorCurrent_d3(id) {
	addElementToCandidate_d3(id, "current");
	drawParentWithId(id);
	addSelector_star(id, "current");
}

function resetSelectorCurrent_d3(id) {
	removeElementFromCandidate_d3(id, "current");
	removeParentWithId(id);
	removeSelector_star(id, "current");
}

function setSelectorComparison_d3(id) {
	addElementToCandidate_d3(id, "comparison");
	addSelector_star(id, "comparison");
}

function resetSelectorComparison_d3(id) {
	removeElementFromCandidate_d3(id, "comparison");
	removeSelector_star(id, "comparison");
}

function setSelectorMarked_d3(id) {
	addElementToCandidate_d3(id, "marked", "selected");
	addSelector_star(id, "marked", "selected");
}

function resetSelectorMarked_d3(id) {
	removeElementFromCandidate_d3(id, "marked");
	removeSelector_star(id, "marked");
}

function setSelectorSelected_d3(id) {
	addElementToCandidate_d3(id, "selected", "marked");
	addSelector_star(id, "selected", "marked");
}

function resetSelectorSelected_d3(id) {
	removeElementFromCandidate_d3(id, "selected");
	removeSelector_star(id, "selected");
}

function setAllSelectorCurrent_d3(ids) {
	for (let i = 0; i < ids.length; i++) {
		addElementToCandidate_d3(ids[i], "current");
		addSelector_star(ids[i], "current");
	}
}

function resetAllSelectorCurrent_d3(ids) {
	for (let i = 0; i < ids.length; i++) {
		removeElementFromCandidate_d3(ids[i], "current");
		removeSelector_star(ids[i], "current");
	}
}

function setAllSelectorComparison_d3(ids) {
	for (let i = 0; i < ids.length; i++) {
		addElementToCandidate_d3(ids[i], "comparison");
		addSelector_star(ids[i], "comparison");
	}
}

function resetAllSelectorComparison_d3(ids) {
	for (let i = 0; i < ids.length; i++) {
		removeElementFromCandidate_d3(ids[i], "comparison");
		removeSelector_star(ids[i], "comparison");
	}
}

function setAllSelectorMarked_d3(ids) {
	for (let i = 0; i < ids.length; i++) {
		addElementToCandidate_d3(ids[i], "marked");
		addSelector_star(ids[i], "marked", "selected");
	}
}

function resetAllSelectorMarked_d3(ids) {
	for (let i = 0; i < ids.length; i++) {
		removeElementFromCandidate_d3(ids[i], "marked");
		removeSelector_star(ids[i], "marked");
	}
}

function setAllSelectorSelected_d3(ids) {
	for (let i = 0; i < ids.length; i++) {
		addElementToCandidate_d3(ids[i], "selected");
		addSelector_star(ids[i], "selected", "marked");
	}
}

function resetAllSelectorSelected_d3(ids) {
	for (let i = 0; i < ids.length; i++) {
		removeElementFromCandidate_d3(ids[i], "selected");
		removeSelector_star(ids[i], "selected");
	}
}

function selectorLevelUp_d3(id) {
	var circles = allCandidateCircles_matrix.filter(".c" + id);
	for (let i = 0; i < candidateCircles.length; i++) {
		var classList = candidateCircles[i].classList;
		if (classList.contains("marked")) {
			classList.remove("marked");
			classList.add("selected");
		} else if (classList.contains("selected")) {
			classList.remove("selected");
		} else {
			classList.add("marked");
		}
	}
	selectorLevelUp_star(id);
}

function selectorLevelUpAll_d3(ids) {
	for (let i = 0; i < ids.length; i++) {
		selectorLevelUp_d3(ids[i]);
	}
}

function setCandidateColor_d3(id, color) {
	// nothing todo
}

function selectorClearCurrent_d3() {
	removeElementFromAllCandidates_d3("current");
	removeParents();
	removeAllSelectors_star("current");
}

function selectorClearComparison_d3() {
	removeElementFromAllCandidates_d3("comparison");
	removeAllSelectors_star("comparison");
}

function selectorClearMarked_d3() {
	removeElementFromAllCandidates_d3("marked");
	removeAllSelectors_star("marked");
}

function selectorClearSelected_d3() {
	removeElementFromAllCandidates_d3("selected");
	removeAllSelectors_star("selected");
}

function selectorMarkAllCurrent_d3() {
	var circles = allCandidateCircles_matrix.filter(".current:not(.selected)");
	for (let i = 0; i < circles.length; i++) {
		var classes = circles[i].classList;
		classes.add("marked");
	}
	
	var radars = $(".candidate-radar-container.current:not(.selected)");
	for (let i = 0; i < radars.length; i++) {
		addSelectorOnElement_star(radars[i], "marked");
	}
}

function selectorSelectAllComparison_d3() {
	var circles = allCandidateCircles_matrix.filter(".comparison");
	for (let i = 0; i < circles.length; i++) {
		var classes = circles[i].classList;
		classes.remove("marked");
		classes.add("selected");
	}
	
	var radars = $(".candidate-radar-container.comparison");
	for (let i = 0; i < radars.length; i++) {
		addSelectorOnElement_star(radars[i], "selected", "marked");
	}
}

function selectorSelectAllMarked_d3() {
	var circles = allCandidateCircles_matrix.filter(".marked");
	for (let i = 0; i < circles.length; i++) {
		var classes = circles[i].classList;
		classes.remove("marked");
		classes.add("selected");
	}
	
	var radars = $(".candidate-radar-container.marked");
	for (let i = 0; i < radars.length; i++) {
		addSelectorOnElement_star(radars[i], "selected", "marked");
	}
}

function selectorExportAllSelected_d3() {

}

// HELPERS

function addElementToCandidate_d3(id, element, remove) {
	var candidateCircles = allCandidateCircles_matrix.filter(".c" + id);
	for (let i = 0; i < candidateCircles.length; i++) {
		candidateCircles[i].classList.add(element);
		if (remove) {
			candidateCircles[i].classList.remove(remove);
		}
		d3.select(candidateCircles[i]).moveToFront();
	}
}

function removeElementFromCandidate_d3(id, element) {
	var candidateCircles = allCandidateCircles_matrix.filter(".c" + id);
	for (let i = 0; i < candidateCircles.length; i++) {
		candidateCircles[i].classList.remove(element);
	}
}

function removeElementFromAllCandidates_d3(element) {
	var candidateCircles = allCandidateCircles_matrix.filter("." + element);
	for (let i = 0; i < candidateCircles.length; i++) {
		candidateCircles[i].classList.remove(element);
	}
}
