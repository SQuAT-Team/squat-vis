var queryCssCandidateItem_d3 = "g.radarWrapper";
var queryCssCandidateContainer_d3 = "div.candidate-radar-container";
var candidateStarCssTag_d3 = ".candidate-radar-star";

function setSelectorCurrent_d3(id) {
	addSelector_d3(id, "current");
}

function resetSelectorCurrent_d3(id) {
	removeSelector_d3(id, "current");
}

function setSelectorComparison_d3(id) {
	addSelector_d3(id, "comparison");
}

function resetSelectorComparison_d3(id) {
	removeSelector_d3(id, "comparison");
}

function setSelectorMarked_d3(id) {
	addSelector_d3(id, "marked", "selected");
}

function resetSelectorMarked_d3(id) {
	removeSelector_d3(id, "marked");
}

function setSelectorSelected_d3(id) {
	addSelector_d3(id, "selected", "marked");
}

function resetSelectorSelected_d3(id) {
	removeSelector_d3(id, "selected");
}

function setAllSelectorCurrent_d3(ids) {
	addSelectorAll_d3(ids, "current");
}

function resetAllSelectorCurrent_d3(ids) {
	removeSelectorAll_d3(ids, "current");
}

function setAllSelectorComparison_d3(ids) {
	addSelectorAll_d3(ids, "comparison");
}

function resetAllSelectorComparison_d3(ids) {
	removeSelectorAll_d3(ids, "comparison");
}

function setAllSelectorMarked_d3(ids) {
	addSelectorAll_d3(ids, "marked", "selected");
}

function resetAllSelectorMarked_d3(ids) {
	removeSelectorAll_d3(ids, "marked");
}

function setAllSelectorSelected_d3(ids) {
	addSelectorAll_d3(ids, "selected", "marked");
}

function resetAllSelectorSelected_d3(ids) {
	removeSelectorAll_d3(ids, "selected");
}

function selectorLevelUp_d3(id) {
	selectorLevelUp_star(id);
}

function selectorLevelUpAll_d3(ids) {
	for (let i = 0; i < ids.length; i++) {
		selectorLevelUp_d3(ids[i]);
	}
}

function setCandidateColor_d3(id, color) {
	// nothing to do
}

function selectorClearCurrent_d3() {
	selectorClearElement_d3("current");
}

function selectorClearComparison_d3() {
	selectorClearElement_d3("comparison");
}

function selectorClearMarked_d3() {
	selectorClearElement_d3("marked");
}

function selectorClearSelected_d3() {
	selectorClearElement_d3("selected");
}

function selectorMarkAllCurrent_d3() {
	var candidateItems = $(queryCssCandidateItem_d3 + ".current");
	for (let i = 0; i < candidateItems.length; i++) {
		var classes = candidateItems[i].classList;
		if (!classes.contains("marked") && !classes.contains("selected")) {
			classes.add("marked");
		}
	}

	candidateItems = $(queryCssCandidateContainer_d3 + ".current");
	for (let i = 0; i < candidateItems.length; i++) {
		classes = candidateItems[i].classList;
		if (!classes.contains("marked") && !classes.contains("selected")) {
			classes.add("marked");
		}
	}
}

function selectorSelectAllComparison_d3() {
	var candidateItems = $(queryCssCandidateContainer_d3 + ".comparison");
	for (let i = 0; i < candidateItems.length; i++) {
		var starItem = $(candidateItems[i]).find(candidateStarCssTag_d3).find(
				"i")[0];
		var classes = candidateItems[i].classList;
		if (!classes.contains("selected")) {
			switchToSolid(starItem);
			classes.add("selected");
			classes.remove("marked");
		}
	}

	candidateItems = $(queryCssCandidateItem_d3 + ".comparison");
	for (let i = 0; i < candidateItems.length; i++) {
		classes = candidateItems[i].classList;
		if (!classes.contains("selected")) {
			classes.add("selected");
			classes.remove("marked");
		}
	}
}

function selectorSelectAllMarked_d3() {
	var candidateItems = $(queryCssCandidateContainer_d3 + ".marked");
	for (let i = 0; i < candidateItems.length; i++) {
		var starItem = $(candidateItems[i]).find(candidateStarCssTag_d3).find(
				"i")[0];
		var classes = candidateItems[i].classList;
		switchToSolid(starItem);
		classes.remove("marked");
		classes.add("selected");
	}

	candidateItems = $(queryCssCandidateItem_d3 + ".marked");
	for (let i = 0; i < candidateItems.length; i++) {
		classes = candidateItems[i].classList;
		classes.remove("marked");
		classes.add("selected");
	}
}

function selectorExportAllSelected_d3() {

}

// HELPERS

function selectorClearElement_d3(remove) {
	removeSelectorWithQuery_d3("g.radarWrapper", remove);
	removeSelectorWithQuery_d3("g.radarCircleWrapper", remove);
	removeSelectorWithQuery_d3(".candidate-radar-container", remove);
}

function addSelectorAll_d3(ids, add, remove) {
	for (let i = 0; i < ids.length; i++) {
		addSelectorWithQuery_d3(".radarWrapper.c" + ids[i], add, remove);
		addSelectorWithQuery_d3(".radarCircleWrapper.c" + ids[i], add, remove);
		addSelectorWithQuery_d3(".candidate-radar-container.c" + ids[i], add,
				remove);
	}
}

function addSelector_d3(id, add, remove) {
	addSelectorWithQuery_d3(".radarWrapper.c" + id, add, remove);
	addSelectorWithQuery_d3(".radarCircleWrapper.c" + id, add, remove);
	addSelectorWithQuery_d3(".candidate-radar-container.c" + id, add, remove);
}

function addSelectorWithQuery_d3(query, add, remove) {
	var result = $(query);
	for (let i = 0; i < result.length; i++) {
		var classes = result[i].classList;
		var starItem = $(result[i]).find(candidateStarCssTag_d3).find("i")[0];
		if (remove) {
			classes.remove(remove);
			if (remove === "selected") {
				switchToNonSolid(starItem);
			}
		}
		if (!classes.contains(add)) {
			classes.add(add);
			if (add === "selected") {
				switchToSolid(starItem);
			}
		}
	}
}

function removeSelectorAll_d3(ids, remove) {
	for (let i = 0; i < ids.length; i++) {
		removeSelectorWithQuery_d3("g.radarWrapper.c" + ids[i], remove);
		removeSelectorWithQuery_d3("g.radarCircleWrapper.c" + ids[i], remove);
		removeSelectorWithQuery_d3(".candidate-radar-container.c" + ids[i],
				remove);
	}
}

function removeSelector_d3(id, remove) {
	removeSelectorWithQuery_d3("g.radarWrapper.c" + id, remove);
	removeSelectorWithQuery_d3("g.radarCircleWrapper.c" + id, remove);
	removeSelectorWithQuery_d3(".candidate-radar-container.c" + id, remove);
}

function removeSelectorWithQuery_d3(query, remove) {
	var result = $(query);
	for (let i = 0; i < result.length; i++) {
		var classes = result[i].classList;
		var starItem = $(result[i]).find(candidateStarCssTag_d3).find("i")[0];
		if (remove === "selected") {
			switchToNonSolid(starItem);
		}
		classes.remove(remove);
	}
}