	var width = 960,
    size = 130,
    padding = 20;

var x = d3.scaleLinear()
    .range([padding / 2, size - padding / 2]);

var y = d3.scaleLinear()
    .range([size - padding / 2, padding / 2]);

var xAxis = d3.axisBottom(x)
    .ticks(6);

var yAxis = d3.axisLeft(y)
    .ticks(6);

var color = d3.scaleOrdinal(d3.schemeCategory10);

render(d3.csvParse(candidateValues));

function render(data) {
  var domainByTrait = {},
      traits = d3.keys(data[0]).filter(function(d) { return ((d !== "ID") && (d !== "InitialTags")); }),
      n = traits.length;

  traits.forEach(function(trait) {
	  domainByTrait[trait] = d3.extent([0.0,1.0]);
	  // automatic
	  // domainByTrait[trait] = d3.extent(data, function(d) { return d[trait];
		// });
  });

  xAxis.tickSize(size * n);
  yAxis.tickSize(-size * n);

  var brush = d3.brush().extent([[x.range()[0], y.range()[1]], [x.range()[1], y.range()[0]]])
      .on("start", brushstart)
      .on("brush", brushmove)
      .on("end", brushend);

  var svg = d3.select("#matrixContent").append("svg")
      .attr("width", size * n + padding)
      .attr("height", size * n + padding)
    .append("g")
      .attr("transform", "translate(" + padding + "," + padding / 2 + ")");

  svg.selectAll(".x.axis")
      .data(traits)
    .enter().append("g")
      .attr("class", "x axis")
      .attr("transform", function(d, i) { return "translate(" + (n - i - 1) * size + ",0)"; })
      .each(function(d) { x.domain(domainByTrait[d]); d3.select(this).call(xAxis); });

  svg.selectAll(".y.axis")
      .data(traits)
    .enter().append("g")
      .attr("class", "y axis")
      .attr("transform", function(d, i) { return "translate(0," + i * size + ")"; })
      .each(function(d) { y.domain(domainByTrait[d]); d3.select(this).call(yAxis); });

  var cell = svg.selectAll(".cell")
      .data(cross(traits, traits))
    .enter().append("g")
      .attr("class", "cell")
      .attr("transform", function(d) { return "translate(" + (n - d.i - 1) * size + "," + d.j * size + ")"; })
      .each(plot);

  // Titles for the diagonal.
  cell.filter(function(d) { return d.i === d.j; }).append("text")
      .attr("x", padding)
      .attr("y", padding)
      .attr("dy", ".71em")
      .text(function(d) { return d.x; });

  cell.call(brush);

  function plot(p) {
    var cell = d3.select(this);

    x.domain(domainByTrait[p.x]);
    y.domain(domainByTrait[p.y]);

    cell.append("rect")
        .attr("class", "frame")
        .attr("x", padding / 2)
        .attr("y", padding / 2)
        .attr("width", size - padding)
        .attr("height", size - padding);

    cell.selectAll("circle")
        .data(data)
      .enter().append("circle").attr("class", function(d) { return d["InitialTags"];})
        .attr("cx", function(d) { return x(d[p.x]); })
        .attr("cy", function(d) { return y(d[p.y]); })
        .attr("r", 2)
        // not used
        // .style("fill", function(d) { return color(d.species); });
  }

  var brushCell;

  // Clear the previously-active brush, if any.
  function brushstart(p) {
    if (brushCell !== this) {
      d3.select(brushCell).call(brush.move, null);
      x.domain(domainByTrait[p.x]);
      y.domain(domainByTrait[p.y]);
      brushCell = this;
    }
  }

  // Highlight the selected circles.
  function brushmove(p) {
	if (!d3.event.selection) return; // Ignore empty selections.
  }

  // If the brush is empty, select all circles.
  function brushend(p) {
	var e = d3.event.selection;
    if (e === null){
    	svg.selectAll(".current").classed("current", false);
    	selectorClearCurrent();
    }else{
        svg.selectAll("circle").classed("current", function(d) {
        	var isSelected = x(d[p.x]) >= e[0][0] && e[1][0] >= x(d[p.x])
            && y(d[p.y]) >= e[0][1] && e[1][1] >= y(d[p.y]);
            if(isSelected){
            	setSelectorCurrent(d["ID"]);
            }
        return isSelected;
        });
    }
  }
};

function cross(a, b) {
  var c = [], n = a.length, m = b.length, i, j;
  for (i = -1;n > ++i;){
	  for (j = -1;m > ++j; ){
		  c.push({x: a[i], i: i, y: b[j], j: j});
	  }
	}
  return c;
};