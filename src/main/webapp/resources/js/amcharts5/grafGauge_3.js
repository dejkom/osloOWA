/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


am5.ready(function() {

// Create root element
var rootI = am5.Root.new("chartdivIzmet");

// Set themes
rootI.setThemes([
  am5themes_Animated.new(rootI)
]);


// Create chart
var chartI = rootI.container.children.push(am5radar.RadarChart.new(rootI, {
  panX: false,
  panY: false,
  startAngle: 160,
  endAngle: 380
}));


// Create axis and its renderer
var axisRendererI = am5radar.AxisRendererCircular.new(rootI, {
  innerRadius: -40
});

axisRendererI.grid.template.setAll({
  stroke: rootI.interfaceColors.get("background"),
  visible: false, //To so TICKI
  strokeOpacity: 0.8
});

var xAxisI = chartI.xAxes.push(am5xy.ValueAxis.new(rootI, {
  maxDeviation: 0,
  min: 0,
  max: 100,
  strictMinMax: false,
  renderer: axisRendererI
}));


// Add clock hand
var axisDataItemI = xAxisI.makeDataItem({});

var clockHandI = am5radar.ClockHand.new(rootI, {
    //z pinradius se spreminja velikost krogeca
  pinRadius: am5.percent(50),
  radius: am5.percent(80),
  bottomWidth: 80
})

var bulletI = axisDataItemI.set("bullet", am5xy.AxisBullet.new(rootI, {
  sprite: clockHandI
}));

xAxisI.createAxisRange(axisDataItemI);

var labelI = chartI.radarContainer.children.push(am5.Label.new(rootI, {
  fill: am5.color(0xffffff), //barva napisa v krogu
  centerX: am5.percent(50),
  textAlign: "center",
  centerY: am5.percent(50),
  fontSize: "7em"
}));

axisDataItemI.set("value", 50);

bulletI.get("sprite").on("rotation", function () {
  var value = axisDataItemI.get("value");
  var text = Math.round(axisDataItemI.get("value")).toString();
  var fill = am5.color(0x000000);
  xAxisI.axisRanges.each(function (axisRange) {
    if (value >= axisRange.get("value") && value <= axisRange.get("endValue")) {
      fill = axisRange.get("axisFill").get("fill");
    }
  })

  labelI.set("text", Math.round(value).toString());

  clockHandI.pin.animate({ key: "fill", to: fill, duration: 500, easing: am5.ease.out(am5.ease.cubic) })
  clockHandI.hand.animate({ key: "fill", to: fill, duration: 500, easing: am5.ease.out(am5.ease.cubic) })
});

setInterval(function () {
  axisDataItemI.animate({
    key: "value",
    to: izmetVarForChart,
    duration: 500,
    easing: am5.ease.out(am5.ease.cubic)
  });
}, 1000)

chartI.bulletsContainer.set("mask", undefined);


// Create axis ranges bands
var bandsDataI = [{
  title: "Unsustainable",
  color: "#ee1f25",
  lowScore: 66,
  highScore: 100
}, {
  title: "Sustainable",
  color: "#f3eb0c",
  lowScore: 33,
  highScore: 66
}, {
  title: "High Performing",
  color: "#0f9747",
  lowScore: 0,
  highScore: 33
}];

am5.array.each(bandsDataI, function (data) {
  var axisRangeI = xAxisI.createAxisRange(xAxisI.makeDataItem({}));

  axisRangeI.setAll({
    value: data.lowScore,
    endValue: data.highScore
  });
  

  axisRangeI.get("axisFill").setAll({
    visible: true,
    fill: am5.color(data.color),
    fillOpacity: 0.8
  });

  axisRangeI.get("label").setAll({
    text: data.title,
    inside: true,
    radius: 15,
    fontSize: "1.1em",
    fill: am5.color(0x000000)
    //fill: root.interfaceColors.get("background")
  });
});


// Make stuff animate on load
chartI.appear(1000, 100);



});



