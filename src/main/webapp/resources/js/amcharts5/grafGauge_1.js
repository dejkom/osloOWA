/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


am5.ready(function() {

// Create root element
// https://www.amcharts.com/docs/v5/getting-started/#Root_element
var root = am5.Root.new("chartdiv");

// Set themes
// https://www.amcharts.com/docs/v5/concepts/themes/
root.setThemes([
  am5themes_Animated.new(root)
]);


// Create chart
// https://www.amcharts.com/docs/v5/charts/radar-chart/
var chart = root.container.children.push(am5radar.RadarChart.new(root, {
  panX: false,
  panY: false,
  startAngle: 160,
  endAngle: 380
}));


// Create axis and its renderer
// https://www.amcharts.com/docs/v5/charts/radar-chart/gauge-charts/#Axes
var axisRenderer = am5radar.AxisRendererCircular.new(root, {
  innerRadius: -40
});

axisRenderer.grid.template.setAll({
  stroke: root.interfaceColors.get("background"),
  visible: false, //To so TICKI
  strokeOpacity: 0.8
});

var xAxis = chart.xAxes.push(am5xy.ValueAxis.new(root, {
  maxDeviation: 0,
  min: 0,
  max: 100,
  strictMinMax: false,
  renderer: axisRenderer
}));


// Add clock hand
// https://www.amcharts.com/docs/v5/charts/radar-chart/gauge-charts/#Clock_hands
var axisDataItem = xAxis.makeDataItem({});

var clockHand = am5radar.ClockHand.new(root, {
    //z pinradius se spreminja velikost krogeca
  pinRadius: am5.percent(50),
  radius: am5.percent(80),
  bottomWidth: 80
})

var bullet = axisDataItem.set("bullet", am5xy.AxisBullet.new(root, {
  sprite: clockHand
}));

xAxis.createAxisRange(axisDataItem);

var label = chart.radarContainer.children.push(am5.Label.new(root, {
  fill: am5.color(0xffffff), //barva napisa v krogu
  centerX: am5.percent(50),
  textAlign: "center",
  centerY: am5.percent(50),
  fontSize: "7em"
}));

axisDataItem.set("value", 50);

bullet.get("sprite").on("rotation", function () {
  var value = axisDataItem.get("value");
  var text = Math.round(axisDataItem.get("value")).toString();
  var fill = am5.color(0x000000);
  xAxis.axisRanges.each(function (axisRange) {
    if (value >= axisRange.get("value") && value <= axisRange.get("endValue")) {
      fill = axisRange.get("axisFill").get("fill");
    }
  })

  label.set("text", Math.round(value).toString());

  clockHand.pin.animate({ key: "fill", to: fill, duration: 500, easing: am5.ease.out(am5.ease.cubic) })
  clockHand.hand.animate({ key: "fill", to: fill, duration: 500, easing: am5.ease.out(am5.ease.cubic) })
});

setInterval(function () {
  axisDataItem.animate({
    key: "value",
    to: oeeVarForChart,
    duration: 500,
    easing: am5.ease.out(am5.ease.cubic)
  });
}, 1000)

chart.bulletsContainer.set("mask", undefined);


// Create axis ranges bands
// https://www.amcharts.com/docs/v5/charts/radar-chart/gauge-charts/#Bands
var bandsData = [{
  title: "",
  color: "#ee1f25",
  lowScore: 0,
  highScore: 80
}, {
  title: "",
  color: "#f3eb0c",
  lowScore: 81,
  highScore: 90
}, {
  title: "",
  color: "#0f9747",
  lowScore: 91,
  highScore: 100
}];

am5.array.each(bandsData, function (data) {
  var axisRange = xAxis.createAxisRange(xAxis.makeDataItem({}));

  axisRange.setAll({
    value: data.lowScore,
    endValue: data.highScore
  });
  

  axisRange.get("axisFill").setAll({
    visible: true,
    fill: am5.color(data.color),
    fillOpacity: 0.8
  });

  axisRange.get("label").setAll({
    text: data.title,
    inside: true,
    radius: 15,
    fontSize: "1.1em",
    fill: am5.color(0x000000)
    //fill: root.interfaceColors.get("background")
  });
});


// Make stuff animate on load
chart.appear(1000, 100);




//// Create root element
//var rootZ = am5.Root.new("chartdivZastoji");
//
//// Set themes
//rootZ.setThemes([
//  am5themes_Animated.new(rootZ)
//]);
//
//
//// Create chart
//var chartZ = rootZ.container.children.push(am5radar.RadarChart.new(rootZ, {
//  panX: false,
//  panY: false,
//  startAngle: 160,
//  endAngle: 380
//}));
//
//
//// Create axis and its renderer
//var axisRendererZ = am5radar.AxisRendererCircular.new(rootZ, {
//  innerRadius: -40
//});
//
//axisRendererZ.grid.template.setAll({
//  stroke: rootZ.interfaceColors.get("background"),
//  visible: false, //To so TICKI
//  strokeOpacity: 0.8
//});
//
//var xAxisZ = chartZ.xAxes.push(am5xy.ValueAxis.new(rootZ, {
//  maxDeviation: 0,
//  min: 0,
//  max: 100,
//  strictMinMax: false,
//  renderer: axisRendererZ
//}));
//
//
//// Add clock hand
//var axisDataItemZ = xAxisZ.makeDataItem({});
//
//var clockHandZ = am5radar.ClockHand.new(rootZ, {
//    //z pinradius se spreminja velikost krogeca
//  pinRadius: am5.percent(50),
//  radius: am5.percent(80),
//  bottomWidth: 80
//})
//
//var bulletZ = axisDataItemZ.set("bullet", am5xy.AxisBullet.new(rootZ, {
//  sprite: clockHandZ
//}));
//
//xAxisZ.createAxisRange(axisDataItemZ);
//
//var labelZ = chartZ.radarContainer.children.push(am5.Label.new(rootZ, {
//  fill: am5.color(0xffffff), //barva napisa v krogu
//  centerX: am5.percent(50),
//  textAlign: "center",
//  centerY: am5.percent(50),
//  fontSize: "7em"
//}));
//
//axisDataItemZ.set("value", 50);
//
//bulletZ.get("sprite").on("rotation", function () {
//  var value = axisDataItemZ.get("value");
//  var text = Math.round(axisDataItemZ.get("value")).toString();
//  var fill = am5.color(0x000000);
//  xAxisZ.axisRanges.each(function (axisRange) {
//    if (value >= axisRange.get("value") && value <= axisRange.get("endValue")) {
//      fill = axisRange.get("axisFill").get("fill");
//    }
//  })
//
//  labelZ.set("text", Math.round(value).toString());
//
//  clockHandZ.pin.animate({ key: "fill", to: fill, duration: 500, easing: am5.ease.out(am5.ease.cubic) })
//  clockHandZ.hand.animate({ key: "fill", to: fill, duration: 500, easing: am5.ease.out(am5.ease.cubic) })
//});
//
//setInterval(function () {
//  axisDataItemZ.animate({
//    key: "value",
//    to: zastojiVarForChart,
//    duration: 500,
//    easing: am5.ease.out(am5.ease.cubic)
//  });
//}, 1000)
//
//chartZ.bulletsContainer.set("mask", undefined);
//
//
//// Create axis ranges bands
//var bandsDataZ = [{
//  title: "Unsustainable",
//  color: "#ee1f25",
//  lowScore: 0,
//  highScore: 33
//}, {
//  title: "Sustainable",
//  color: "#f3eb0c",
//  lowScore: 33,
//  highScore: 66
//}, {
//  title: "High Performing",
//  color: "#0f9747",
//  lowScore: 66,
//  highScore: 100
//}];
//
//am5.array.each(bandsDataZ, function (data) {
//  var axisRangeZ = xAxisZ.createAxisRange(xAxisZ.makeDataItem({}));
//
//  axisRangeZ.setAll({
//    value: data.lowScore,
//    endValue: data.highScore
//  });
//  
//
//  axisRangeZ.get("axisFill").setAll({
//    visible: true,
//    fill: am5.color(data.color),
//    fillOpacity: 0.8
//  });
//
//  axisRangeZ.get("label").setAll({
//    text: data.title,
//    inside: true,
//    radius: 15,
//    fontSize: "1.1em",
//    fill: am5.color(0x000000)
//    //fill: root.interfaceColors.get("background")
//  });
//});
//
//
//// Make stuff animate on load
//chartZ.appear(1000, 100);
//
//
//
//
//
//
//




});




