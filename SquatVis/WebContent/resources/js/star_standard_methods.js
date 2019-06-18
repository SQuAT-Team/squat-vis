function addSelector_star(id, add, remove) {
	var elements = $(".candidate-radar-container.c" + id);
	for (let i = 0; i < elements.length; i++) {
		addSelectorOnElement_star(elements[i], add, remove);
	}
}

function removeSelector_star(id, remove) {
	var elements = $(".candidate-radar-container.c" + id);
	for (let i = 0; i < elements.length; i++) {
		removeSelectorOnElement_star(elements[i], remove);
	}
}

function addAllSelectors_star(add, remove) {
	var elements = $(".candidate-radar-container");
	for (let i = 0; i < elements.length; i++) {
		addSelectorOnElement_star(elements[i], add, remove);
	}
}

function removeAllSelectors_star(remove) {
	var elements = $(".candidate-radar-container");
	for (let i = 0; i < elements.length; i++) {
		removeSelectorOnElement_star(elements[i], remove);
	}
}

function removeSelectorOnElement_star(element, remove) {
	var starItem = $(element).find(".candidate-radar-star").find("i")[0];
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
	var starItem = $(element).find(".candidate-radar-star").find("i")[0];
	var classes = element.classList;
	if (remove) {
		classes.remove(remove);
		if ((remove === "selected") && (add !== "selected")) {
			if (starItem) {
				starItem.classList.remove("fas");
				starItem.classList.add("far");
			}
		}
	}
	if (!classes.contains(add)) {
		classes.add(add);
		if (add === "selected") {
			if (starItem) {
				starItem.classList.remove("far");
				starItem.classList.add("fas");
			}
		}
	}

}

function selectorLevelUp_star(id) {
	// find candidate item with id
	var candidateItems = $(".candidate-radar-container" + ".c" + id);

	// change all items
	for (let i = 0; i < candidateItems.length; i++) {
		var candidateItem = candidateItems[i];
		var classes = candidateItem.classList;
		var starItem = $(candidateItem).find(".candidate-radar-star").find("i")[0];

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