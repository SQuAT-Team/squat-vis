function toolbarSearch(element) {
	var doNotShowCssClass = "search-hide";
	var toSearch = element.value.trim().replace(/\s+/g, ' ').toLowerCase();
	// search for items in all list
	var candidateItems = $("div.list-group.candidate-list.toolbarAll")
			.children(".candidate-item");
	
	if(toSearch == "p"){
		for (let i = 0; i < candidateItems.length; i++) {
			var pareto1 = $(candidateItems[i]).find(".candidate-pareto.pareto-real-level");
			var pareto2 = $(candidateItems[i]).find(".candidate-pareto.pareto-utility-level");
			var pareto3 = $(candidateItems[i]).find(".candidate-pareto.pareto-real-population");
			var pareto4 = $(candidateItems[i]).find(".candidate-pareto.pareto-utility-population");
			var classes = candidateItems[i].classList;
			// set correct search css class in each element
			if (pareto1.length == 0 && pareto2.length == 0 && pareto3.length == 0 && pareto4.length == 0) {
				if (!classes.contains(doNotShowCssClass)) {
					classes.add(doNotShowCssClass);
				}
			} else {
				classes.remove(doNotShowCssClass);
			}
		}	
	}else if(toSearch == "s"){
		for (let i = 0; i < candidateItems.length; i++) {
			var suggestion = $(candidateItems[i]).find(".suggestion-on");
			var classes = candidateItems[i].classList;
			// set correct search css class in each element
			if (suggestion.length == 0) {
				if (!classes.contains(doNotShowCssClass)) {
					classes.add(doNotShowCssClass);
				}
			} else {
				classes.remove(doNotShowCssClass);
			}
		}	
	}else{
	for (let i = 0; i < candidateItems.length; i++) {
		var itemName = $(candidateItems[i]).find(".candidate-name").text();
		var classes = candidateItems[i].classList;
		// set correct search css class in each element
		if (!itemName.trim().replace(/\s+/g, ' ').toLowerCase().includes(toSearch)) {
			if (!classes.contains(doNotShowCssClass)) {
				classes.add(doNotShowCssClass);
			}
		} else {
			classes.remove(doNotShowCssClass);
		}
	}
	}
}

// Activates Right Click Menu For Candidates
$(function() {
	var candidateIdCssClass = ".candidate-id";
	$
			.contextMenu({
				selector : '.candidate-item',
				callback : function(key, options) {
					// do nothing
				},
				items : {
					"Current" : {
						name : "Current",
						disabled : function(key, opt) {
							return (opt.$trigger.hasClass("current"));
						},
						callback : function(key, opt) {
							var id = opt.$trigger.find(candidateIdCssClass).text();
							setSelectorCurrent(id);
						}
					},
					"Comparison" : {
						name : "Comparison",
						disabled : function(key, opt) {
							return (opt.$trigger.hasClass("comparison"));
						},
						callback : function(key, opt) {
							var id = opt.$trigger.find(candidateIdCssClass).text();
							setSelectorComparison(id);
						}
					},
					"Marked" : {
						name : "Marked",
						disabled : function(key, opt) {
							return (opt.$trigger.hasClass("marked"));
						},
						callback : function(key, opt) {
							var id = opt.$trigger.find(candidateIdCssClass).text();
							setSelectorMarked(id);
						}
					},
					"Selected" : {
						name : "Selected",
						disabled : function(key, opt) {
							return (opt.$trigger.hasClass("selected"));
						},
						callback : function(key, opt) {
							var id = opt.$trigger.find(candidateIdCssClass).text();
							setSelectorSelected(id);
						}
					},
					"sep1" : "---------",
					"Remove" : {
						name : "Remove",
						disabled : function(key, opt) {
							return (!(opt.$trigger.parent().hasClass(
									"toolbarMarked")
									|| opt.$trigger.parent().hasClass(
											"toolbarCurrent")
									|| opt.$trigger.parent().hasClass(
											"toolbarComparison") || opt.$trigger
									.parent().hasClass("toolbarSelected")));
						},
						callback : function(key, opt) {
							var id = opt.$trigger.find(candidateIdCssClass).text();
							if (opt.$trigger.parent()
									.hasClass("toolbarCurrent")) {
								resetSelectorCurrent(id);
							} else if (opt.$trigger.parent().hasClass(
									"toolbarComparison")) {
								resetSelectorComparison(id);
							} else if (opt.$trigger.parent().hasClass(
									"toolbarMarked")) {
								resetSelectorMarked(id);
							} else if (opt.$trigger.parent().hasClass(
									"toolbarSelected")) {
								resetSelectorSelected(id);
							}
						}
					},
					"Export" : {
						name : "Export",
						disabled : true
					}
				}
			})
});
