function setSelectorCurrent_backend(id) {
	setSelectorCurrent_application([ {
		name : 'param',
		value : id
	} ]);
	updateToolbarCurrent();
	updateToolbarCurrentCounter();
}

function resetSelectorCurrent_backend(id) {
	resetSelectorCurrent_application([ {
		name : 'param',
		value : id
	} ]);
	updateToolbarCurrent();
	updateToolbarCurrentCounter();
}

function setSelectorComparison_backend(id) {
	setSelectorComparison_application([ {
		name : 'param',
		value : id
	} ]);
	updateToolbarComparison();
	updateToolbarComparisonCounter();
	updateColorMappings();
	updateColorMappingCss();
}

function resetSelectorComparison_backend(id) {
	resetSelectorComparison_application([ {
		name : 'param',
		value : id
	} ]);
	updateToolbarComparison();
	updateToolbarComparisonCounter();
	updateColorMappings();
	updateColorMappingCss();
}

function setSelectorMarked_backend(id) {
	setSelectorMarked_application([ {
		name : 'param',
		value : id
	} ]);
	updateToolbarMarked();
	updateToolbarMarkedCounter();
	updateToolbarSelected();
	updateToolbarSelectedCounter();
}

function resetSelectorMarked_backend(id) {
	resetSelectorMarked_application([ {
		name : 'param',
		value : id
	} ]);
	updateToolbarMarked();
	updateToolbarMarkedCounter();
}

function setSelectorSelected_backend(id) {
	setSelectorSelected_application([ {
		name : 'param',
		value : id
	} ]);
	updateToolbarSelected();
	updateToolbarSelectedCounter();
	updateToolbarMarked();
	updateToolbarMarkedCounter();
}

function resetSelectorSelected_backend(id) {
	resetSelectorSelected_application([ {
		name : 'param',
		value : id
	} ]);
	updateToolbarSelected();
	updateToolbarSelectedCounter();
}

function setAllSelectorCurrent_backend(ids) {
	setAllSelectorCurrent_application([ {
		name : 'param',
		value : ids
	} ]);
	updateToolbarCurrent();
	updateToolbarCurrentCounter();
}

function resetAllSelectorCurrent_backend(ids) {
	resetAllSelectorCurrent_application([ {
		name : 'param',
		value : ids
	} ]);
	updateToolbarCurrent();
	updateToolbarCurrentCounter();
}

function setAllSelectorComparison_backend(ids) {
	setAllSelectorComparison_application([ {
		name : 'param',
		value : ids
	} ]);
	updateToolbarComparison();
	updateToolbarComparisonCounter();
	updateColorMappings();
	updateColorMappingCss();
}

function resetAllSelectorComparison_backend(ids) {
	resetAllSelectorComparison_application([ {
		name : 'param',
		value : ids
	} ]);
	updateToolbarComparison();
	updateToolbarComparisonCounter();
}

function setAllSelectorMarked_backend(ids) {
	setAllSelectorMarked_application([ {
		name : 'param',
		value : ids
	} ]);
	updateToolbarMarked();
	updateToolbarMarkedCounter();
}

function resetAllSelectorMarked_backend(ids) {
	resetAllSelectorMarked_application([ {
		name : 'param',
		value : ids
	} ]);
	updateToolbarMarked();
	updateToolbarMarkedCounter();
}

function setAllSelectorSelected_backend(ids) {
	setAllSelectorSelected_application([ {
		name : 'param',
		value : ids
	} ]);
	updateToolbarSelected();
	updateToolbarSelectedCounter();
}

function resetAllSelectorSelected_backend(ids) {
	resetAllSelectorSelected_application([ {
		name : 'param',
		value : ids
	} ]);
	updateToolbarSelected();
	updateToolbarSelectedCounter();
}

function selectorLevelUp_backend(id) {
	selectorLevelUp_application([ {
		name : 'param',
		value : id
	} ]);
	updateToolbarSelected();
	updateToolbarMarked();
	updateToolbarMarkedCounter();
	updateToolbarSelectedCounter();
}

function selectorLevelUpAll_backend(ids) {
	selectorLevelUpAll_application([ {
		name : 'param',
		value : ids
	} ]);
	updateToolbarSelected();
	updateToolbarMarked();
	updateToolbarMarkedCounter();
	updateToolbarSelectedCounter();
}

function setCandidateColor_backend(id, color) {
	setCandidateColor_application([ {
		name : 'id',
		value : id
	}, {
		name : 'color',
		value : color
	} ]);
	updateColorMappings();
	updateColorMappingCss();
}

function selectorClearCurrent_backend(){
	selectorClearCurrent_application();
	updateToolbarCurrent();
	updateToolbarCurrentCounter();
}

function selectorClearComparison_backend(){
	selectorClearComparison_application();
	updateToolbarComparison();
	updateToolbarComparisonCounter();
	updateColorMappings();
	updateColorMappingCss();
}

function selectorClearMarked_backend(){
	selectorClearMarked_application();
	updateToolbarMarked();
	updateToolbarMarkedCounter();
}

function selectorClearSelected_backend(){
	selectorClearSelected_application();
	updateToolbarSelected();
	updateToolbarSelectedCounter();
}

function selectorMarkAllCurrent_backend(){
	selectorMarkAllCurrent_application();
	updateToolbarMarked();
	updateToolbarMarkedCounter();
}

function selectorSelectAllComparison_backend(){
	selectorSelectAllComparison_application();
	updateToolbarMarked();
	updateToolbarMarkedCounter();
	updateToolbarSelected();
	updateToolbarSelectedCounter();
	updateColorMappings();
	updateColorMappingCss();
}

function selectorSelectAllMarked_backend(){
	selectorSelectAllMarked_application();
	updateToolbarMarked();
	updateToolbarMarkedCounter();
	updateToolbarSelected();
	updateToolbarSelectedCounter();
}

function selectorExportAllSelected_backend(){
	selectorExportAllSelected_application();
}
