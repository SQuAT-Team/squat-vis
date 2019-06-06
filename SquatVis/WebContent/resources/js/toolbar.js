function toolbarSearch(element) {
	var toSearch = element.value.trim().replace(/\s+/g, ' ');
	// search for items in all list
	var candidateItems = $("div.list-group.candidate-list.toolbarAll")
			.children(".candidate-item");
	for (let i = 0; i < candidateItems.length; i++) {
		var itemName = $(candidateItems[i]).find(".candidate-name").text();
		var classes = candidateItems[i].classList;
		// set correct search css class in each element
		if (!itemName.trim().replace(/\s+/g, ' ').includes(toSearch)) {
			if (!classes.contains("search-hide")) {
				classes.add("search-hide");
			}
		} else {
			classes.remove("search-hide");
		}
	}
}

// Activates Right Click Menu For Candidates
$(function() {
	$
			.contextMenu({
				selector : '.candidate-item',
				callback : function(key, options) {
					var m = "clicked: " + key;
					console.log($(this).find(".candidate-id").text());
					console.log(this);
					console.log(m) || alert(m);
				},
				items : {
					"Current" : {
						name : "Current",
						disabled : function(key, opt) {
							return (opt.$trigger.hasClass("current"));
						},
						callback : function(key, opt) {
							var id = opt.$trigger.find(".candidate-id").text();
							setSelectorCurrent(id);
						}
					},
					"Comparison" : {
						name : "Comparison",
						disabled : function(key, opt) {
							return (opt.$trigger.hasClass("comparison"));
						},
						callback : function(key, opt) {
							var id = opt.$trigger.find(".candidate-id").text();
							setSelectorComparison(id);
						}
					},
					"Marked" : {
						name : "Marked",
						disabled : function(key, opt) {
							return (opt.$trigger.hasClass("marked"));
						},
						callback : function(key, opt) {
							var id = opt.$trigger.find(".candidate-id").text();
							setSelectorMarked(id);
						}
					},
					"Selected" : {
						name : "Selected",
						disabled : function(key, opt) {
							return (opt.$trigger.hasClass("selected"));
						},
						callback : function(key, opt) {
							var id = opt.$trigger.find(".candidate-id").text();
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
							var id = opt.$trigger.find(".candidate-id").text();
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
