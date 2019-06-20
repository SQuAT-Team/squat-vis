const starCss_star = ".candidate-radar-star";
const candidateRadarContainerCss_star = ".candidate-radar-container";

function addSelector_star(id, add, remove) {
	var elements = $(candidateRadarContainerCss_star + ".c" + id);
	for (let i = 0; i < elements.length; i++) {
		addSelectorOnElement_star(elements[i], add, remove);
	}
}

function removeSelector_star(id, remove) {
	var elements = $(candidateRadarContainerCss_star + ".c" + id);
	for (let i = 0; i < elements.length; i++) {
		removeSelectorOnElement_star(elements[i], remove);
	}
}

function addAllSelectors_star(add, remove) {
	var elements = $(candidateRadarContainerCss_star);
	for (let i = 0; i < elements.length; i++) {
		addSelectorOnElement_star(elements[i], add, remove);
	}
}

function removeAllSelectors_star(remove) {
	var elements = $(candidateRadarContainerCss_star);
	for (let i = 0; i < elements.length; i++) {
		removeSelectorOnElement_star(elements[i], remove);
	}
}

function removeSelectorOnElement_star(element, remove) {
	var starItem = $(element).find(starCss_star).find("i")[0];
	var classes = element.classList;
	classes.remove(remove);
	if (remove === "selected") {
		if (starItem) {
			starItem.classList.remove("fas");
			starItem.classList.add("far");
		}
	}
}

function addSelectorOnElement_star(element, add, remove) {
	var starItem = $(element).find(starCss_star).find("i")[0];
	var classes = element.classList;
	if (remove) {
		classes.remove(remove);
		if ((remove === "selected") && (add !== "selected")) {
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

function selectorLevelUp_star(id) {
	// find candidate item with id
	var candidateItems = $(candidateRadarContainerCss_star + ".c" + id);

	// change all items
	for (let i = 0; i < candidateItems.length; i++) {
		var candidateItem = candidateItems[i];
		var classes = candidateItem.classList;
		var starItem = $(candidateItem).find(starCss_star).find("i")[0];

		if (classes.contains("marked")) {
			classes.remove("marked");
			classes.add("selected");
			switchToSolid(starItem);
		} else if (classes.contains("selected")) {
			classes.remove("selected");
			switchToNonSolid(starItem);
		} else {
			classes.add("marked");
		}
	}
	
	candidateItems = $(".radarWrapper" + ".c" + id);
	// change all items
	for (let i = 0; i < candidateItems.length; i++) {
		candidateItem = candidateItems[i];
		classes = candidateItem.classList;

		if (classes.contains("marked")) {
			classes.remove("marked");
			classes.add("selected");
		} else if (classes.contains("selected")) {
			classes.remove("selected");
		} else {
			classes.add("marked");
		}
	}
}

function switchToSolid(starItem){
	if (starItem) {
		starItem.classList.remove("far");
		starItem.classList.add("fas");
	}
}

function switchToNonSolid(starItem){
	if (starItem) {
		starItem.classList.remove("fas");
		starItem.classList.add("far");
	}
}
