initDropdown();
initDropdownBig();
updateColorMappingCss();

function initDropdown() {
	var text = $("#dropdownMenuButton").text().trim();
	if (text === "All") {
		showAll();
	} else if (text === "Marked") {
		showMarked();
	} else if (text === "Selected") {
		showSelected();
	} else if (text === "Comparison") {
		showComparison();
	}
}

function initDropdownBig() {
	var text = $("#dropdownMenuButtonBig").text().trim();
	if (text === "Current") {
		showCurrentBig();
	} else if (text === "Marked") {
		showMarkedBig();
	} else if (text === "Selected") {
		showSelectedBig();
	} else if (text === "Comparison") {
		showComparisonBig();
	}
}

// Activates Right Click Menu For Candidates
$(function() {
	var candidateIdCssClass = ".candidate-id";
	$.contextMenu({
		selector : '.candidate-radar-container',
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
			"Remove Current" : {
				name : "Remove Current",
				disabled : function(key, opt) {
					return (!opt.$trigger.hasClass("current"));
				},
				callback : function(key, opt) {
					var id = opt.$trigger.find(candidateIdCssClass).text();
					resetSelectorCurrent(id);
				}
			},
			"Export" : {
				name : "Export",
				disabled : true
			}
		}
	})
});

function showAll() {
	setDropdownName("All");
	var classes = document.getElementById("starDetailed").classList;
	classes.remove("comparison-active");
	classes.remove("marked-active");
	classes.remove("selected-active");
	if (!classes.contains("all-active")) {
		classes.add("all-active");
	}
	setMode_application([ {
		name : 'param',
		value : "All"
	} ]);
}

function showMarked() {
	setDropdownName("Marked");
	var classes = document.getElementById("starDetailed").classList;
	classes.remove("comparison-active");
	classes.remove("selected-active");
	classes.remove("all-active");
	if (!classes.contains("marked-active")) {
		classes.add("marked-active");
	}
	setMode_application([ {
		name : 'param',
		value : "Marked"
	} ]);
}

function showSelected() {
	setDropdownName("Selected");
	var classes = document.getElementById("starDetailed").classList;
	classes.remove("comparison-active");
	classes.remove("marked-active");
	classes.remove("all-active");
	if (!classes.contains("selected-active")) {
		classes.add("selected-active");
	}
	setMode_application([ {
		name : 'param',
		value : "Selected"
	} ]);
}

function showComparison() {
	setDropdownName("Comparison");
	var classes = document.getElementById("starDetailed").classList;
	classes.remove("marked-active");
	classes.remove("selected-active");
	classes.remove("all-active");
	if (!classes.contains("comparison-active")) {
		classes.add("comparison-active");
	}
	setMode_application([ {
		name : 'param',
		value : "Comparison"
	} ]);
}

function showCurrentBig() {
	setDropdownNameBig("Current");
	var classes = document.getElementById("starOverview").classList;
	classes.remove("comparison-active");
	classes.remove("marked-active");
	classes.remove("selected-active");
	if (!classes.contains("current-active")) {
		classes.add("current-active");
	}
	setModeBig_application([ {
		name : 'param',
		value : "Current"
	} ]);
}

function showMarkedBig() {
	setDropdownNameBig("Marked");
	var classes = document.getElementById("starOverview").classList;
	classes.remove("comparison-active");
	classes.remove("selected-active");
	classes.remove("current-active");
	if (!classes.contains("marked-active")) {
		classes.add("marked-active");
	}
	setModeBig_application([ {
		name : 'param',
		value : "Marked"
	} ]);
}

function showSelectedBig() {
	setDropdownNameBig("Selected");
	var classes = document.getElementById("starOverview").classList;
	classes.remove("comparison-active");
	classes.remove("marked-active");
	classes.remove("current-active");
	if (!classes.contains("selected-active")) {
		classes.add("selected-active");
	}
	setModeBig_application([ {
		name : 'param',
		value : "Selected"
	} ]);
}

function showComparisonBig() {
	setDropdownNameBig("Comparison");
	var classes = document.getElementById("starOverview").classList;
	classes.remove("marked-active");
	classes.remove("selected-active");
	classes.remove("current-active");
	if (!classes.contains("comparison-active")) {
		classes.add("comparison-active");
	}
	setModeBig_application([ {
		name : 'param',
		value : "Comparison"
	} ]);
}

function setDropdownName(name) {
	$("#dropdownMenuButton").text(name);
}

function setDropdownNameBig(name) {
	$("#dropdownMenuButtonBig").text(name);
}

function starSearch(element) {
	var doNotShowCssClass = "search-hide";
	var toSearch = element.value.trim().replace(/\s+/g, ' ').toLowerCase();
	// search for items in all list
	var candidateItems = $("#starOverviewContent .candidate-radar-container");
	for (let i = 0; i < candidateItems.length; i++) {
		var itemName = $(candidateItems[i]).find(".candidate-radar-name")
				.text();
		var classes = candidateItems[i].classList;
		// set correct search css class in each element
		if (!itemName.trim().replace(/\s+/g, ' ').toLowerCase().includes(
				toSearch)) {
			if (!classes.contains(doNotShowCssClass)) {
				classes.add(doNotShowCssClass);
			}
		} else {
			classes.remove(doNotShowCssClass);
		}
	}
}

function changePopulation() {
	var checkboxValue = $("#showPopulationCheckbox").prop("checked");
	var classes = document.getElementById("starOptions").classList;
	if (checkboxValue) {
		if (!classes.contains("population-active")) {
			classes.add("population-active");
		}
	} else {
		classes.remove("population-active");
	}

	// notify backend
	setShowPopulation_application([ {
		name : 'param',
		value : checkboxValue
	} ]);
}

function toggleCurrent(element){
	var classes = element.parentElement.classList;
	var id = $(element.parentElement).children(".candidate-id").text();
	if(classes.contains("current")){
		resetSelectorCurrent(id);
	}else{
		setSelectorCurrent(id);
	}
}

function updateColorMappingCss(){
	$( "#colorMappingsCss" ).remove();
	$( "#colorMappings" ).clone().attr('id', 'colorMappingsCss').appendTo( "#head" );
}