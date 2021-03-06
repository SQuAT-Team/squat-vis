initToolbarSearch();

// EXPORT BUTTON
//<![CDATA[
function start() {
    PF('statusDialog').show();
}
 
function stop() {
    PF('statusDialog').hide();
}
//]]>

function reloadButton(){
	reloadSelectedProject_application();
}

function handleLevelClick(element){
	var levelId = element.getAttribute("levelid");
	if(element.checked){
		addActiveLevel_application([ {
			name : 'param',
			value : levelId
		} ]);
	}else{
		removeActiveLevel_application([ {
			name : 'param',
			value : levelId
		} ]);
	}
	updateAllSelectorElements();
}

function handleParentLevelClick(element){
	var levelId = element.getAttribute("levelid");
	if(element.checked){
		addActiveParentLevel_application([ {
			name : 'param',
			value : levelId
		} ]);
	}else{
		removeActiveParentLevel_application([ {
			name : 'param',
			value : levelId
		} ]);
	}
}


function setLevelMode(name){
	setLevelMode_application([ {
		name : 'param',
		value : name
	} ]);
	updateAllSelectorElements();
}

function setParetoMode(name){
	setParetoMode_application([ {
		name : 'param',
		value : name
	} ]);
	updateAllSelectorElements();
}

function updateAllSelectorElements(){
	updateToolbarComparisonCounter();
	updateToolbarComparison();
	updateToolbarCurrentCounter();
	updateToolbarCurrent();
	updateToolbarMarkedCounter();
	updateToolbarMarked();
	updateToolbarSelectedCounter();
	updateToolbarSelected();
	updateToolbarAllCounter();
	updateToolbarAll();
}

function selectorAllParetoToCurrent(){
	var paretoGroup = getCandidateIdsWithElement(".pareto-on");
	
	let idSet = new Set();
	paretoGroup.forEach(item => idSet.add(item));
	
	selectorClearCurrent();
	setAllSelectorCurrent(Array.from(idSet));
}

function selectorAllSuggestedToCurrent(){
	selectorClearCurrent();
	setAllSelectorCurrent(getCandidateIdsWithElement(".suggestion-on"));
}

function changeUseMinimizedMatrix(){
	var checkboxValue = $("#useMinimizedMatrixCheckbox").prop("checked");
	// notify backend
	setUseMinimizedMatrix_application([ {
		name : 'param',
		value : checkboxValue
	} ]);
}

function changeUseNameInsteadOfId(){
	var checkboxValue = $("#useUseNameInsteadOfIdCheckbox").prop("checked");
	// notify backend
	setUseNameInsteadOfId_application([ {
		name : 'param',
		value : checkboxValue
	} ]);
}

function changeShortenName(){
	var checkboxValue = $("#shortenNameCheckbox").prop("checked");
	// notify backend
	setShortenName_application([ {
		name : 'param',
		value : checkboxValue
	} ]);
}

function getCandidateIdsWithElement(element){
	var candidateItems = $("div.list-group.candidate-list.toolbarAll")
	.children(".candidate-item");
	var ids = [];
	for (let i = 0; i < candidateItems.length; i++) {
		var suggestion = $(candidateItems[i]).find(element);
		// set correct search css class in each element
		if (suggestion.length > 0) {
			var id = $(candidateItems[i]).find(".candidate-id").text();
			ids.push(parseInt(id));
		}
	}
	return ids;
}

function setSelectorMenuState(id, element){
	var collapsed = $(element).hasClass("collapsed");	
	setSelectorMenuState_application([ {
		name : 'param1',
		value : id
	},{
		name : 'param2',
		value : collapsed
	}
	]);
}

function setOptionMenuState(id, element){
	var collapsed = $(element).hasClass("collapsed");	
	setOptionMenuState_application([ {
		name : 'param1',
		value : id
	},{
		name : 'param2',
		value : collapsed
	}
	]);
}

function setSelectorTabActive(active){
	setSelectorTabActive_application([ {
		name : 'param',
		value : active
	} ]);
}

function initToolbarSearch(){
	var searchItems = $("#sidebar").find("div.has-search").find("input");
	for (let i = 0; i < searchItems.length; i++) {
		toolbarSearch(searchItems[i]);
	}
}

function toolbarSearch(element, name) {
	var toSearch = element.value.trim().replace(/\s+/g, ' ').toLowerCase();
	// search for items in all list
	var candidateItems = $("#sidebar").find("div.list-group.candidate-list.toolbarAll")
			.children(".candidate-item");
	
	if(toSearch == "p"){
		hideCandidatesWithoutClass(candidateItems, "candidate-pareto.pareto-on");
	}else if (toSearch == "s") {
		hideCandidatesWithoutClass(candidateItems, "suggestion-on");
	} else if (toSearch == "i") {
		hideCandidatesWithoutClass(candidateItems, "initial-on");
	} else {
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
	setSearchState_application([ {
		name : 'param1',
		value : name
	},{
		name : 'param2',
		value : toSearch
	}
	]);
}

// Activates Right Click Menu For Candidates
$(function() {
	var candidateIdCssClass = ".candidate-id";
	$.contextMenu({
				selector : '.candidate-item',
				callback : function(key, options) {
					// do nothing
				},
				items : {
					"Move to Front" : {
						name : "Move to Front",
						disabled : false,
						callback : function(key, opt) {
							var id = opt.$trigger.find(candidateIdCssClass).text();
							moveToFront(id);
						}
					},
					"sep1" : "---------",
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
					"sep2" : "---------",
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
						disabled : false,
						callback : function(key, opt) {
							PrimeFaces.monitorDownload(start, stop);
							opt.$trigger.find("button").click();
						}
					}
				}
			})
});
