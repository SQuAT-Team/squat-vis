// INTERACE
const candidateStarCssTag_toolbar = ".candidate-star";
const queryCssCandidateItem_toolbar = "div.candidate-item";

function setSelectorCurrent_toolbar(id) {
	addElement(id, "current");
}

function resetSelectorCurrent_toolbar(id) {
	removeElement(id, "current");
}

function setSelectorComparison_toolbar(id) {
	addElement(id, "comparison");
}

function resetSelectorComparison_toolbar(id) {
	removeElement(id, "comparison");
}

function setSelectorMarked_toolbar(id) {
	addElement(id, "marked", "selected");
}

function resetSelectorMarked_toolbar(id) {
	removeElement(id, "marked");
}

function setSelectorSelected_toolbar(id) {
	addElement(id, "selected", "marked");
}

function resetSelectorSelected_toolbar(id) {
	// find candidate item with id
	var candidateItems = $(queryCssCandidateItem_toolbar + ".c" + id);
	var element = "selected";
	for (let i = 0; i < candidateItems.length; i++) {
		var candidateItem = candidateItems[i];
		var starItem = $(candidateItem).find(candidateStarCssTag_toolbar).find(
				"i")[0];
		var classes = candidateItem.classList;
		if (classes.contains(element)) {
			if (starItem) {
				starItem.classList.remove("fas");
				starItem.classList.add("far");
			}
			classes.remove(element);
		}
	}
}

function setAllSelectorCurrent_toolbar(ids) {
	for (let i = 0; i < ids.length; i++) {
		addElement(ids[i], "current");
	}
}

function resetAllSelectorCurrent_toolbar(ids) {
	for (let i = 0; i < ids.length; i++) {
		removeElement(ids[i], "current");
	}
}

function setAllSelectorComparison_toolbar(ids) {
	for (let i = 0; i < ids.length; i++) {
		addElement(ids[i], "comparison");
	}
}

function resetAllSelectorComparison_toolbar(ids) {
	for (let i = 0; i < ids.length; i++) {
		removeElement(ids[i], "comparison");
	}
}

function setAllSelectorMarked_toolbar(ids) {
	for (let i = 0; i < ids.length; i++) {
		addElement(ids[i], "marked", "selected");
	}
}

function resetAllSelectorMarked_toolbar(ids) {
	for (let i = 0; i < ids.length; i++) {
		removeElement(ids[i], "marked");
	}
}

function setAllSelectorSelected_toolbar(ids) {
	for (let i = 0; i < ids.length; i++) {
		addElement(ids[i], "selected", "marked");
	}
}

function resetAllSelectorSelected_toolbar(ids) {
	for (let i = 0; i < ids.length; i++) {
		removeElement(ids[i], "selected");
	}
}

function selectorLevelUp_toolbar(id) {
	levelUp(id);
}

function selectorLevelUpAll_toolbar(ids) {
	for (let i = 0; i < ids.length; i++) {
		levelUp(ids[i]);
	}
}

function setCandidateColor_toolbar(id, color) {
}

function selectorClearCurrent_toolbar() {
	removeElementFromAll("current");
}

function selectorClearComparison_toolbar() {
	removeElementFromAll("comparison");
}

function selectorClearMarked_toolbar() {
	removeElementFromAll("marked");
}

function selectorClearSelected_toolbar() {
	removeElementFromAll("selected");
}

function selectorMarkAllCurrent_toolbar() {
	var candidateItems = $(queryCssCandidateItem_toolbar + ".current");
	for (let i = 0; i < candidateItems.length; i++) {
		var classes = candidateItems[i].classList;
		if (!classes.contains("marked") && !classes.contains("selected")) {
			classes.add("marked");
		}
	}
}

function selectorSelectAllComparison_toolbar() {
	var candidateItems = $(queryCssCandidateItem_toolbar + ".comparison");
	for (let i = 0; i < candidateItems.length; i++) {
		var starItem = $(candidateItems[i]).find(candidateStarCssTag_toolbar)
				.find("i")[0];
		var classes = candidateItems[i].classList;
		if (!classes.contains("selected")) {
			if (starItem) {
				starItem.classList.remove("far");
				starItem.classList.add("fas");
			}
			classes.add("selected");
			classes.remove("marked");
		}
	}
}

function selectorSelectAllMarked_toolbar() {
	var candidateItems = $(queryCssCandidateItem_toolbar + ".marked");
	for (let i = 0; i < candidateItems.length; i++) {
		var starItem = $(candidateItems[i]).find(candidateStarCssTag_toolbar)
				.find("i")[0];
		var classes = candidateItems[i].classList;
		if (starItem) {
			starItem.classList.remove("far");
			starItem.classList.add("fas");
		}
		classes.remove("marked");
		classes.add("selected");
	}
}

function selectorExportAllSelected_toolbar() {
	var button = $("nav").find("button.export-all-button");
	PrimeFaces.monitorDownload(start, stop);
	button.click();
}

// HELPERS

function addElement(id, element, remove) {
	// find candidate item with id
	var candidateItems = $(queryCssCandidateItem_toolbar + ".c" + id);
	for (let i = 0; i < candidateItems.length; i++) {
		var candidateItem = candidateItems[i];
		var starItem = $(candidateItem).find(candidateStarCssTag_toolbar).find(
		"i")[0];
		var classes = candidateItem.classList;
		if(remove){
			classes.remove(remove);
			if(remove === "selected" && starItem){
				starItem.classList.remove("fas");
				starItem.classList.add("far");
			}
		}
		if (!classes.contains(element)) {
			classes.add(element);
			if(element === "selected" && starItem){
				starItem.classList.remove("far");
				starItem.classList.add("fas");
			}
		}
	}
}

function removeElement(id, element) {
	// find candidate item with id
	var candidateItems = $(queryCssCandidateItem_toolbar + ".c" + id);
	for (let i = 0; i < candidateItems.length; i++) {
		var candidateItem = candidateItems[i];
		var starItem = $(candidateItem).find(candidateStarCssTag_toolbar).find(
				"i")[0];
		var classes = candidateItem.classList;
		if (classes.contains(element)) {
			classes.remove(element);
			if (element === "selected" && starItem) {
				starItem.classList.remove("fas");
				starItem.classList.add("far");
			}
		}
	}
}

function removeElementFromAll(element) {
	var candidateItems = $(queryCssCandidateItem_toolbar);
	for (let i = 0; i < candidateItems.length; i++) {
		var starItem = $(candidateItems[i]).find(candidateStarCssTag_toolbar).find(
		"i")[0];
		var classes = candidateItems[i].classList;
		classes.remove(element);
		if (element === "selected" && starItem) {
			starItem.classList.remove("fas");
			starItem.classList.add("far");
		}
	}
}

function levelUp(id) {
	// find candidate item with id
	var candidateItems = $(queryCssCandidateItem_toolbar + ".c" + id);

	// change all items
	for (let i = 0; i < candidateItems.length; i++) {
		var candidateItem = candidateItems[i];
		var classes = candidateItem.classList;
		var starItem = $(candidateItem).find(candidateStarCssTag_toolbar).find(
				"i")[0];

		if (classes.contains("marked")) {
			classes.remove("marked");
			classes.add("selected");
			if (starItem) {
				starItem.classList.remove("far");
				starItem.classList.add("fas");
			}
		} else if (classes.contains("selected")) {
			classes.remove("selected");
			if (starItem) {
				starItem.classList.remove("fas");
				starItem.classList.add("far");
			}
		} else {
			classes.add("marked");
		}
	}
}
