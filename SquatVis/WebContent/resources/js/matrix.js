initMoveToFront();
initDropdown();
updateColorMappingCss();
drawArrowHead();
drawParent();

function initMoveToFront() {
	var candidateCircles = $("g.cell").children("circle.candidate-circle");
	d3.selectAll(candidateCircles.filter(".comparison")).moveToFront();
	d3.selectAll(candidateCircles.filter(".marked")).moveToFront();
	d3.selectAll(candidateCircles.filter(".selected")).moveToFront();
	d3.selectAll(candidateCircles.filter(".comparison")).moveToFront();
	d3.selectAll(candidateCircles.filter(".initial-on")).moveToFront();
	d3.selectAll(candidateCircles.filter(".current")).moveToFront();
}

function drawArrowHead() {
	svgBig.append("svg:defs").append("svg:marker")
	.attr("class", "parent-line-head")
    .attr("id", "triangle")
    .attr("refX", 12)
    .attr("refY", 6)
    .attr("markerWidth", 15)
    .attr("markerHeight", 15)
    .attr("markerUnits","userSpaceOnUse")
    .attr("orient", "auto")
    .append("path")
    .attr("d", "M 0 0 12 6 0 12 3 6");
}

function drawParent() {
    var circles = $("#matrixDetailedContent circle.candidate-circle.current");
	for (let i = 0; i < circles.length; i++) {
		var currentCircle = circles[i];
		var parentId = circles[i].getAttribute("parent");
		if(parentId.length > 0){
		    var parentCircle = $("circle.c"+parentId);
		    if(parentCircle.length != 0){
		    	var candidateId = currentCircle.getAttribute("candidateId");

		    	svgBig.append("line")
		    	.attr("class", "parent-line c" + candidateId)
		    	.attr("sourceId", parentId)
				.attr("targetId", candidateId)
				.attr("x2", currentCircle.getAttribute("cx"))
				.attr("y2", currentCircle.getAttribute("cy"))
				.attr("x1", parentCircle.attr("cx"))
				.attr("y1", parentCircle.attr("cy"))
				.attr("marker-end", "url(#triangle)");
		    }
		}
	}
}

function drawParentWithId(id) {
    var circles = $("#matrixDetailedContent circle.candidate-circle.c"+id);
	for (let i = 0; i < circles.length; i++) {
		var currentCircle = circles[i];
		var parentId = circles[i].getAttribute("parent");
		if(parentId.length > 0){
		    var parentCircle = $("circle.c"+parentId);
		    if(parentCircle.length != 0){
		    	var candidateId = currentCircle.getAttribute("candidateId");

		    	svgBig.append("line")
		    	.attr("class", "parent-line c" + candidateId)
		    	.attr("sourceId", parentId)
		    	.attr("targetId", candidateId)
		    	.attr("x2", currentCircle.getAttribute("cx"))
		    	.attr("y2", currentCircle.getAttribute("cy"))
				.attr("x1", parentCircle.attr("cx"))
				.attr("y1", parentCircle.attr("cy"))
				.attr("marker-end", "url(#triangle)");
		    }
		}
	}
}

function redrawParent(){
	var lines = $("#matrixDetailedContent .parent-line");
	for (let i = 0; i < lines.length; i++) {
		var line = lines[i];
		var sourceId = line.getAttribute("sourceId");
		var targetId = line.getAttribute("targetId");
		var sourceCandidate = $("#matrixDetailedContent circle.candidate-circle.c"+sourceId);
		var targetCandidate = $("#matrixDetailedContent circle.candidate-circle.c"+targetId);
		line.setAttribute("x1",sourceCandidate.attr("cx"));
		line.setAttribute("y1",sourceCandidate.attr("cy"));
		line.setAttribute("x2",targetCandidate.attr("cx"));
		line.setAttribute("y2",targetCandidate.attr("cy"));
	}
}

function removeParents(){
	$("#matrixDetailedContent .parent-line").remove();
}

function removeParentWithId(id){
	$("#matrixDetailedContent .parent-line.c"+id).remove();
}

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

function setMatrixOptionsFromCheckbox(checkboxId, optionsCss){
	var checkboxValue = $("#"+checkboxId).prop("checked");
	var classes = document.getElementById("matrixOptions").classList;
	if (checkboxValue) {
		if (!classes.contains(optionsCss)) {
			classes.add(optionsCss);
		}
	} else {
		if (classes.contains(optionsCss)) {
			classes.remove(optionsCss);
		}
	}
	return checkboxValue;
}

function changeParents(){
	const parentsActiveCss = "parents-active";
	var checkboxValue = setMatrixOptionsFromCheckbox("showParentsCheckbox",parentsActiveCss);

	// notify backend
	setShowParents_application([ {
		name : 'param',
		value : checkboxValue
	} ]);
}

function changeHighlightInitial() {
	const initialActiveCss = "initial-active";
	var checkboxValue = setMatrixOptionsFromCheckbox("highlightInitialCheckbox",initialActiveCss);

	// notify backend
	setHighlightInitial_application([ {
		name : 'param',
		value : checkboxValue
	} ]);
}

function changePareto() {
	const paretoActiveCss = "pareto-active";
	var checkboxValue = setMatrixOptionsFromCheckbox("showParetoCheckbox",paretoActiveCss);

	// notify backend
	setShowPareto_application([ {
		name : 'param',
		value : checkboxValue
	} ]);
}

function changeSuggestions() {
	const suggestionsActiveCss = "suggestions-active";
	var checkboxValue = setMatrixOptionsFromCheckbox("showSuggestionsCheckbox",suggestionsActiveCss);

	// notify backend
	setShowSuggestions_application([ {
		name : 'param',
		value : checkboxValue
	} ]);
}

function showAll() {
	setDropdownName("All");
	var classes = document.getElementById("matrixOptions").classList;
	if (classes.contains("comparison-active")) {
		classes.remove("comparison-active");
	}
	if (!classes.contains("marked-active")) {
		classes.add("marked-active");
	}
	if (!classes.contains("selected-active")) {
		classes.add("selected-active");
	}
	setMode_application([ {
		name : 'param',
		value : "All"
	} ]);
}

function showMarked() {
	setDropdownName("Marked");
	var classes = document.getElementById("matrixOptions").classList;
	if (classes.contains("comparison-active")) {
		classes.remove("comparison-active");
	}
	if (!classes.contains("marked-active")) {
		classes.add("marked-active");
	}
	classes.remove("selected-active");
	setMode_application([ {
		name : 'param',
		value : "Marked"
	} ]);
}

function showSelected() {
	setDropdownName("Selected");
	var classes = document.getElementById("matrixOptions").classList;
	if (classes.contains("comparison-active")) {
		classes.remove("comparison-active");
	}
	if (!classes.contains("selected-active")) {
		classes.add("selected-active");
	}
	classes.remove("marked-active");
	setMode_application([ {
		name : 'param',
		value : "Selected"
	} ]);
}

function showComparison() {
	setDropdownName("Comparison");
	var classes = document.getElementById("matrixOptions").classList;
	classes.remove("marked-active");
	classes.remove("selected-active");
	if (!classes.contains("comparison-active")) {
		classes.add("comparison-active");
	}
	setMode_application([ {
		name : 'param',
		value : "Comparison"
	} ]);
}

function setDropdownName(name) {
	$("#dropdownMenuButton").text(name);
}

function updateColorMappingCss(){
	$( "#colorMappingsCss" ).remove();
	$( "#colorMappings" ).clone().attr('id', 'colorMappingsCss').appendTo( "#head" );
}
