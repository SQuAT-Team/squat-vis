function setSelectorCurrent_d3(id) {
	var id = id.toString();
	activeCurrentCandidates.add(id);
	updateGraphCurrentElements();
	updateBiGraphCurrentElements();
	updateParallelCoordinatesCurrentElements();
	updateStarElements(id, "current");
	starCandidateMoveToFront(id);
}

function resetSelectorCurrent_d3(id) {
	var id = id.toString();
	activeCurrentCandidates.delete(id);
	updateGraphCurrentElements();
	updateBiGraphCurrentElements();
	updateParallelCoordinatesCurrentElements();
	updateStarElements(id, null, "current");
}

function setSelectorComparison_d3(id) {
	var id = id.toString();
	activeComparisonCandidates.add(id);
	updateGraphElements();
	updateBiGraphElements();
	updateParallelCoordinatesElements();
	updateStarElements(id, "comparison");
	starCandidateMoveToFront(id);
}

function resetSelectorComparison_d3(id) {
	var id = id.toString();
	activeComparisonCandidates.delete(id);
	updateGraphElements();
	updateBiGraphElements();
	updateStarElements(id, null ,"comparison");
}

function setSelectorMarked_d3(id) {
	var id = id.toString();
	activeSelectedCandidates.delete(id);
	activeMarkedCandidates.add(id);
	updateGraphElements();
	updateBiGraphElements();
	updateParallelCoordinatesElements();
	updateStarElements(id, "marked", "selected");
}

function resetSelectorMarked_d3(id) {
	var id = id.toString();
	activeMarkedCandidates.delete(id);
	updateGraphElements();
	updateBiGraphElements();
	updateParallelCoordinatesElements();
	updateStarElements(id, null ,"marked");
}

function setSelectorSelected_d3(id) {
	var id = id.toString();
	activeMarkedCandidates.delete(id);
	activeSelectedCandidates.add(id);
	updateGraphElements();
	updateBiGraphElements();
	updateParallelCoordinatesElements();
	updateStarElements(id, "selected", "marked");
}

function resetSelectorSelected_d3(id) {
	var id = id.toString();
	activeSelectedCandidates.delete(id);
	updateGraphElements();
	updateBiGraphElements();
	updateParallelCoordinatesElements();
	updateStarElements(id, null ,"selected");
}

function setAllSelectorCurrent_d3(ids) {
	for (let i = 0; i < ids.length; i++) {
		var id = ids[i].toString();
		activeCurrentCandidates.add(id);
	}
	updateGraphCurrentElements();
	updateBiGraphCurrentElements();
	updateParallelCoordinatesCurrentElements();
	for (let i = 0; i < ids.length; i++) {
		var id = ids[i].toString();
		updateStarElements(id, "current" );
	}
	starCandidatesMoveToFront(ids);
}

function resetAllSelectorCurrent_d3(ids) {
	for (let i = 0; i < ids.length; i++) {
		var id = ids[i].toString();
		activeCurrentCandidates.delete(id);
	}
	updateGraphCurrentElements();
	updateBiGraphCurrentElements();
	updateParallelCoordinatesCurrentElements();
	for (let i = 0; i < ids.length; i++) {
		var id = ids[i].toString();
		updateStarElements(id, null, "current" );
	}
}

function setAllSelectorComparison_d3(ids) {
	for (let i = 0; i < ids.length; i++) {
		var id = ids[i].toString();
		activeComparisonCandidates.add(id);
	}
	updateGraphElements();
	updateBiGraphElements();
	updateParallelCoordinatesElements();
	for (let i = 0; i < ids.length; i++) {
		var id = ids[i].toString();
		updateStarElements(id, "comparison" );
	}
	starCandidatesMoveToFront(ids);
}

function resetAllSelectorComparison_d3(ids) {
	for (let i = 0; i < ids.length; i++) {
		var id = ids[i].toString();
		activeComparisonCandidates.delete(id);
	}
	updateGraphElements();
	updateBiGraphElements();
	updateParallelCoordinatesElements();
	for (let i = 0; i < ids.length; i++) {
		var id = ids[i].toString();
		updateStarElements(id, null, "comparison" );
	}
}

function setAllSelectorMarked_d3(ids) {
	for (let i = 0; i < ids.length; i++) {
		var id = ids[i].toString();
		activeSelectedCandidates.delete(id);
		activeMarkedCandidates.add(id);
	}
	updateGraphElements();
	updateBiGraphElements();
	updateParallelCoordinatesElements();
	for (let i = 0; i < ids.length; i++) {
		var id = ids[i].toString();
		updateStarElements(id, "marked", "selected" );
	}
}

function resetAllSelectorMarked_d3(ids) {
	for (let i = 0; i < ids.length; i++) {
		var id = ids[i].toString();
		activeSelectedCandidates.delete(id);
	}
	updateGraphElements();
	updateBiGraphElements();
	updateParallelCoordinatesElements();
	for (let i = 0; i < ids.length; i++) {
		var id = ids[i].toString();
		updateStarElements(id, null, "marked" );
	}
}

function setAllSelectorSelected_d3(ids) {
	for (let i = 0; i < ids.length; i++) {
		var id = ids[i].toString();
		activeMarkedCandidates.delete(id);
		activeSelectedCandidates.add(id);
	}
	updateGraphElements();
	updateBiGraphElements();
	updateParallelCoordinatesElements();
	for (let i = 0; i < ids.length; i++) {
		var id = ids[i].toString();
		updateStarElements(id, "selected", "marked" );
	}
}

function resetAllSelectorSelected_d3(ids) {
	for (let i = 0; i < ids.length; i++) {
		var id = ids[i].toString();
		activeSelectedCandidates.delete(id);
	}
	updateGraphElements();
	updateBiGraphElements();
	updateParallelCoordinatesElements();
	for (let i = 0; i < ids.length; i++) {
		var id = ids[i].toString();
		updateStarElements(id, null, "selected" );
	}
}

function selectorLevelUp_d3(id) {
	var id = id.toString();
	if(activeMarkedCandidates.has(id)){
		activeMarkedCandidates.delete(id);
		activeSelectedCandidates.add(id);
		updateStarElements(id, "selected", "marked" );
	} else if (activeSelectedCandidates.has(id)){
		activeSelectedCandidates.delete(id);
		updateStarElements(id, null, "selected" );
	} else {
		activeMarkedCandidates.add(id);
		updateStarElements(id, "marked" );
	}
	updateGraphElements();
	updateBiGraphElements();
	updateParallelCoordinatesElements();
}

function selectorLevelUpAll_d3(ids) {
	for (let i = 0; i < ids.length; i++) {
		var id = ids[i].toString();
		if(activeMarkedCandidates.has(id)){
			activeMarkedCandidates.delete(id);
			activeSelectedCandidates.add(id);
			updateStarElements(id, "selected", "marked" );
		}else if(activeSelectedCandidates.has(id)){
			activeSelectedCandidates.delete(id);
			updateStarElements(id, null, "selected" );
		}else{
			activeMarkedCandidates.add(id);
			updateStarElements(id, "marked" );
		}
	}
	updateGraphElements();
	updateBiGraphElements();
	updateParallelCoordinatesElements();
}

function setCandidateColor_d3(id, color) {
	
}

function selectorClearCurrent_d3() {
	activeCurrentCandidates.clear();
	updateGraphCurrentElements();
	updateBiGraphCurrentElements();
	updateParallelCoordinatesCurrentElements();
	updateAllStarElements(null, "current" );
}

function selectorClearComparison_d3() {
	activeComparisonCandidates.clear();
	updateGraphElements();
	updateBiGraphElements();
	updateParallelCoordinatesElements();
	updateAllStarElements(null, "comparison" );
}

function selectorClearMarked_d3() {
	activeMarkedCandidates.clear();
	updateGraphElements();
	updateBiGraphElements();
	updateParallelCoordinatesElements();
	updateAllStarElements(null, "marked" );
}

function selectorClearSelected_d3() {
	activeSelectedCandidates.clear();
	updateGraphElements();
	updateBiGraphElements();
	updateParallelCoordinatesElements();
	updateAllStarElements(null, "selected" );
}

function selectorMarkAllCurrent_d3() {
	for (var it = activeCurrentCandidates.values(), val= null; val=it.next().value; ) {
		if(!activeSelectedCandidates.has(val)){
			activeMarkedCandidates.add(val);
			updateStarElements(val.toString(), "marked" );
		}
	}
	updateGraphElements();
	updateBiGraphElements();
	updateParallelCoordinatesElements();
}

function selectorSelectAllComparison_d3() {
	for (var it = activeComparisonCandidates.values(), val= null; val=it.next().value; ) {
		activeMarkedCandidates.delete(val);
		activeSelectedCandidates.add(val);
		updateStarElements(val.toString(), "marked", "selected" );
	}
	updateGraphElements();
	updateBiGraphElements();
	updateParallelCoordinatesElements();
}

function selectorSelectAllMarked_d3() {
	activeSelectedCandidates = new Set([activeSelectedCandidates, activeMarkedCandidates]);
	activeMarkedCandidates.clear();
	updateGraphElements();
	updateBiGraphElements();
	updateParallelCoordinatesElements();
	updateStarPopulationElementsWithSelector("marked", "selected", "marked");
}

function selectorExportAllSelected_d3() {

}
