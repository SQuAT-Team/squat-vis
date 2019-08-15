const doNotShowCssClass = "search-hide";

function hideCandidatesWithoutClass(candidateItems, candidateClass){
	for (let i = 0; i < candidateItems.length; i++) {
		var initial = $(candidateItems[i]).find("." + candidateClass);
		var classes = candidateItems[i].classList;
		// set correct search css class in each element
		if (initial.length == 0) {
			if (!classes.contains(doNotShowCssClass)) {
				classes.add(doNotShowCssClass);
			}
		} else {
			classes.remove(doNotShowCssClass);
		}
	}
}
