function setSelectorCurrent_d3(id) {
	addElementToCandidate_d3(id, "current");
}

function resetSelectorCurrent_d3(id) {
	removeElementFromCandidate_d3(id, "current");
}

function setSelectorComparison_d3(id) {
	addElementToCandidate_d3(id, "comparison");
}

function resetSelectorComparison_d3(id) {
	removeElementFromCandidate_d3(id, "comparison");
}

function setSelectorMarked_d3(id) {
	addElementToCandidate_d3(id, "marked", "selected");
}

function resetSelectorMarked_d3(id) {
	removeElementFromCandidate_d3(id, "marked");
}

function setSelectorSelected_d3(id) {
	addElementToCandidate_d3(id, "selected", "marked");
}

function resetSelectorSelected_d3(id) {
	removeElementFromCandidate_d3(id, "selected");
}

function setAllSelectorCurrent_d3(ids) {
	for (let i = 0; i < ids.length; i++) {
		addElementToCandidate_d3(ids[i], "current");
	}
}

function resetAllSelectorCurrent_d3(ids) {
	for (let i = 0; i < ids.length; i++) {
		removeElementFromCandidate_d3(ids[i], "current");
	}
}

function setAllSelectorComparison_d3(ids) {
	for (let i = 0; i < ids.length; i++) {
		addElementToCandidate_d3(ids[i], "comparison");
	}
}

function resetAllSelectorComparison_d3(ids) {
	for (let i = 0; i < ids.length; i++) {
		removeElementFromCandidate_d3(ids[i], "comparison");
	}
}

function setAllSelectorMarked_d3(ids) {
	for (let i = 0; i < ids.length; i++) {
		addElementToCandidate_d3(ids[i], "marked");
	}
}

function resetAllSelectorMarked_d3(ids) {
	for (let i = 0; i < ids.length; i++) {
		removeElementFromCandidate_d3(ids[i], "marked");
	}
}

function setAllSelectorSelected_d3(ids) {
	for (let i = 0; i < ids.length; i++) {
		addElementToCandidate_d3(ids[i], "selected");
	}
}

function resetAllSelectorSelected_d3(ids) {
	for (let i = 0; i < ids.length; i++) {
		removeElementFromCandidate_d3(ids[i], "selected");
	}
}

function selectorLevelUp_d3(id) {
	var candidateCircles = $("g.cell").children("circle.c" + id);
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
}

function selectorLevelUpAll_d3(ids) {
	for (let i = 0; i < ids.length; i++) {
		selectorLevelUp_d3(ids[i]);
	}
}

function setCandidateColor_d3(id, color) {
	// TODO
}

function selectorClearCurrent_d3() {
	removeElementFromAllCandidates_d3("current");
}

function selectorClearComparison_d3() {
	removeElementFromAllCandidates_d3("comparison");
}

function selectorClearMarked_d3() {
	removeElementFromAllCandidates_d3("marked");
}

function selectorClearSelected_d3() {
	removeElementFromAllCandidates_d3("selected");
}

function selectorMarkAllCurrent_d3() {

}

function selectorSelectAllComparison_d3() {

}

function selectorSelectAllMarked_d3() {

}

function selectorExportAllSelected_d3() {

}

// HELPERS

function addElementToCandidate_d3(id, element, remove) {
	var candidateCircles = $("g.cell").children("circle.c" + id);
	for (let i = 0; i < candidateCircles.length; i++) {
		candidateCircles[i].classList.add(element);
		if (remove) {
			candidateCircles[i].classList.remove(remove);
			d3.select(candidateCircles[i]).moveToFront();
		}
	}
}

function removeElementFromCandidate_d3(id, element) {
	var candidateCircles = $("g.cell").children("circle.c" + id);
	for (let i = 0; i < candidateCircles.length; i++) {
		candidateCircles[i].classList.remove(element);
	}
}

function removeElementFromAllCandidates_d3(element) {
	var candidateCircles = $("g.cell").children("circle" + "." + element);
	for (let i = 0; i < candidateCircles.length; i++) {
		candidateCircles[i].classList.remove(element);
	}
}