/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

jQuery(document).ready(function () {
    jQuery(document).ready(function () {
        // twice in document.ready to execute after Primefaces callbacks

        var listOfRects = new Array();
        var listOfSquares = new Array();
        
        var jsonColors = document.getElementById('form:jsonfrommap').value;
        var jsonColorObject = JSON.parse(jsonColors);     
        console.log('JSON:'+jsonColors);
        
        console.log('HOST:'+location.host);
        var hostURL = location.host+'/osloWebApp/faces/PRO/ProductionDisplays/singleViewGauges.xhtml';
        console.log('HOST:'+hostURL);

        var layer;
        var stage;
        var tr;
        var x1, y1, x2, y2;
        initializeStageAndLayer();
        
        JSONfromBB();

        //dodano za menu
        // setup menu
        let currentShape;
        var menuNode = document.getElementById('menu');

        document.getElementById('delete-button').addEventListener('click', () => {
            console.log('Sem v delete-button click event listenerju');
            currentShape.destroy();
            //layer.draw();
        });

        window.addEventListener('click', () => {
            // hide menu
            menuNode.style.display = 'none';
            console.log('Sem v window click event listenerju');
        });

        stage.on('contextmenu', function (e) {
            // prevent default behavior
            e.evt.preventDefault();
            if (e.target === stage) {
                // if we are on empty place of the stage we will do nothing
                return;
            }
            console.log('Current shape:', e);
            console.log('Event which:' + e.button);
            currentShape = e.target.parent;
            // show menu
            menuNode.style.display = 'initial';
            var containerRect = stage.container().getBoundingClientRect();
            menuNode.style.top = stage.getPointerPosition().y + 1 + 'px';
            menuNode.style.left = stage.getPointerPosition().x + 1 + 'px';
        });
        //end menu

//        document.getElementById('form:button').addEventListener('click', addShape);
//        function addShape() {
//            var box = new Konva.Rect({
//                x: Math.random() * 100,
//                y: Math.random() * 100,
//                width: 150,
//                height: 100,
//                fill: Konva.Util.getRandomColor(),
//                stroke: 'black',
//                strokeWidth: 2,
//                draggable: true,
//                name: 'rect',
//                id: 'idrect'
//            });
//            listOfRects.push(box);
//            layer.add(box);
//        }

        document.getElementById('form2:buttonAddWithID').addEventListener('click', () => {
            addShapeWithId(document.getElementById('form2:stWorkcentraIn').value);
        });
        function addShapeWithId(id) {
            console.log('Sem v addShapeWithId');
            //dodano za tekst
            var rectangle = new Konva.Group({
                x: 25,
                y: 25,
                width: 150,
                height: 100,
                draggable: true,
                opacity: 0.7,
                name: 'group',
                id: id
            });

            rectangle.add(new Konva.Rect({
                width: 150,
                height: 100,
                fill: '#DADADA',
                stroke: 'black',
                strokeWidth: 2,
                name: 'rect',
                id: id + ''
            }));

            rectangle.add(new Konva.Text({
                text: '' + id,
                fontSize: 24,
                fontFamily: 'Calibri',
                fill: '#000',
                width: 150,
                height: rectangle.height(),
                padding: 5,
                align: 'center',
                verticalAlign: 'middle',
                draggable: false,
                listening: false
            }));
            //end dodano za tekst
//            var box = new Konva.Rect({
//                x: Math.random() * 100,
//                y: Math.random() * 100,
//                width: 150,
//                height: 100,
//                fill: Konva.Util.getRandomColor(),
//                stroke: 'black',
//                strokeWidth: 2,
//                draggable: true,
//                name: 'rect',
//                id: id + ''
//            });
//            rectangle.add(box);
            //listOfRects.push(box);
            //layer.add(box);
            layer.add(rectangle);
            $('#myModal').modal('toggle');
        }

        document.getElementById('form:buttonSave').addEventListener('click', saveJSON);
        function saveJSON() {
            var json = stage.toJSON();
            console.log(json);
            document.getElementById('form:jsonforsaving').value = json;
            myRemote();
        }

        document.getElementById('form:buttonRead').addEventListener('click', JSONfromBB);
        function JSONfromBB() {
            //JSON se iz backingbeana naloži v skrito polje
            var json = document.getElementById('form:propertyId').value;
            console.log(json);
            
            var jsonColors = document.getElementById('form:jsonfrommap').value;
            var jsonColorObject = JSON.parse(jsonColors);  
            
            // create node using json string
            stage = Konva.Node.create(json, 'container');

            layer = new Konva.Layer();
            stage.add(layer);

            tr = new Konva.Transformer({
                rotateEnabled: true
            });
            layer.add(tr);

            // add a new feature, lets add ability to draw selection rectangle
            selectionRectangle = new Konva.Rect({
                fill: 'rgba(0,0,255,0.5)',
                visible: false
            });
            layer.add(selectionRectangle);
            tr.forceUpdate();

            stage.on('mousedown touchstart', (e) => {
                // do nothing if we mousedown on any shape
                //console.log('mousedown touchstart EVENT');
                if (e.target !== stage) {
                    return;
                }
                e.evt.preventDefault();
                x1 = stage.getPointerPosition().x;
                y1 = stage.getPointerPosition().y;
                x2 = stage.getPointerPosition().x;
                y2 = stage.getPointerPosition().y;

                selectionRectangle.visible(true);
                selectionRectangle.width(0);
                selectionRectangle.height(0);
            });

            stage.on('mousemove touchmove', (e) => {
                // do nothing if we didn't start selection
                //console.log('mousemove touchmove EVENT');
                if (!selectionRectangle.visible()) {
                    return;
                }
                e.evt.preventDefault();
                x2 = stage.getPointerPosition().x;
                y2 = stage.getPointerPosition().y;

                selectionRectangle.setAttrs({
                    x: Math.min(x1, x2),
                    y: Math.min(y1, y2),
                    width: Math.abs(x2 - x1),
                    height: Math.abs(y2 - y1),
                });
            });

            stage.on('mouseup touchend', (e) => {
                // do nothing if we didn't start selection
                //console.log('mouseup touchend EVENT');
                if (!selectionRectangle.visible()) {
                    return;
                }
                e.evt.preventDefault();
                // update visibility in timeout, so we can check it in click event
                setTimeout(() => {
                    selectionRectangle.visible(false);
                });

                var shapes = stage.find('.group'); //before here was .rect
                var box = selectionRectangle.getClientRect();
                var selected = shapes.filter((shape) =>
                    Konva.Util.haveIntersection(box, shape.getClientRect())
                );
                tr.nodes(selected);
            });

            // clicks should select/deselect shapes
            stage.on('click tap', function (e) {
                // if we are selecting with rect, do nothing
                console.log('click tap EVENT, clicked on:',e.target);
                if (selectionRectangle.visible()) {
                    return;
                }

                // if click on empty area - remove all selections
                if (e.target === stage) {
                    tr.nodes([]);
                    return;
                }
                
                // do if clicked on our rectangles
                if (e.target.hasName('rect')) { //before here was rect
                    //alert('kliknil na rect');
                    console.log('gumbek:'+e.evt.button);
                    if(e.evt.button === 2){
                        //odpri context menu
                    }else{
                        //var URL = "http://localhost:7070/osloWebApp/faces/PRO/ProductionDisplays/singleViewGauges.xhtml?wc=" + e.target.getId();
                        var URL = 'http://'+hostURL+'?wc='+e.target.getId();
                        window.open(URL, '_blank');
                    }
                    return;
                }

                // do nothing if clicked NOT on our rectangles
                if (!e.target.hasName('group')) { //before here was rect
                    return;
                }
                
                

                // do we pressed shift or ctrl?
                const metaPressed = e.evt.shiftKey || e.evt.ctrlKey || e.evt.metaKey;
                const isSelected = tr.nodes().indexOf(e.target) >= 0;

                if (!metaPressed && !isSelected) {
                    // if no key pressed and the node is not selected
                    // select just one
                    tr.nodes([e.target]);
                } else if (metaPressed && isSelected) {
                    // if we pressed keys and node was selected
                    // we need to remove it from selection:
                    const nodes = tr.nodes().slice(); // use slice to have new copy of array
                    // remove node from array
                    nodes.splice(nodes.indexOf(e.target), 1);
                    tr.nodes(nodes);
                } else if (metaPressed && !isSelected) {
                    // add the node into selection
                    const nodes = tr.nodes().concat([e.target]);
                    tr.nodes(nodes);
                }
            });

//            stage.on('click', 'Group', function (evt) {
//                var shape = evt.target;
//                var group = evt.currentTarget;
//                console.log('BLABLA, shape:' + shape.getId() + ' group:' + group.getId());
//                var URL = "http://localhost:7070/osloWebApp/faces/PRO/ProductionDisplays/singleViewGauges.xhtml?wc=" + shape.getId();
//                window.open(URL, '_blank');
//            });


            var nodes = stage.find('.group');
            for (let n of nodes) {
                console.log('Found group/okvircek:' + n.getId());
                var children = n.getChildren(function (node) {
                    return node.getClassName() === 'Rect';
                });
                
                for(let c of children){
                    console.log('child Rect:' + c.getId());
                    var wc = c.getId();
                    console.log('color should be:'+jsonColorObject.wc);
//                    var randomColor = Math.floor(Math.random()*16777215).toString(16);
//                    c.setFill("#"+randomColor);
                    c.setFill(jsonColorObject[wc]);
                }

            }


        }

        function initializeStageAndLayer() {
            var width = window.innerWidth;
            var height = window.innerHeight;

            stage = new Konva.Stage({
                container: 'container',
                width: width,
                height: height
            });

            layer = new Konva.Layer();
            stage.add(layer);

            tr = new Konva.Transformer({
                rotateEnabled: false
            });
            layer.add(tr);

            // add a new feature, lets add ability to draw selection rectangle
            selectionRectangle = new Konva.Rect({
                fill: 'rgba(0,0,255,0.5)',
                visible: false
            });
            layer.add(selectionRectangle);

            stage.on('mousedown touchstart', (e) => {
                // do nothing if we mousedown on any shape
                //console.log('mousedown touchstart EVENT');
                if (e.target !== stage) {
                    return;
                }
                e.evt.preventDefault();
                x1 = stage.getPointerPosition().x;
                y1 = stage.getPointerPosition().y;
                x2 = stage.getPointerPosition().x;
                y2 = stage.getPointerPosition().y;

                selectionRectangle.visible(true);
                selectionRectangle.width(0);
                selectionRectangle.height(0);
            });

            stage.on('mousemove touchmove', (e) => {
                // do nothing if we didn't start selection
                //console.log('mousemove touchmove EVENT');
                if (!selectionRectangle.visible()) {
                    return;
                }
                e.evt.preventDefault();
                x2 = stage.getPointerPosition().x;
                y2 = stage.getPointerPosition().y;

                selectionRectangle.setAttrs({
                    x: Math.min(x1, x2),
                    y: Math.min(y1, y2),
                    width: Math.abs(x2 - x1),
                    height: Math.abs(y2 - y1),
                });
            });

            stage.on('mouseup touchend', (e) => {
                // do nothing if we didn't start selection
                //console.log('mouseup touchend EVENT');
                if (!selectionRectangle.visible()) {
                    return;
                }
                e.evt.preventDefault();
                // update visibility in timeout, so we can check it in click event
                setTimeout(() => {
                    selectionRectangle.visible(false);
                });

                var shapes = stage.find('.group'); //before here was .rect (text was not resizing)
                var box = selectionRectangle.getClientRect();
                var selected = shapes.filter((shape) =>
                    Konva.Util.haveIntersection(box, shape.getClientRect())
                );
                tr.nodes(selected);
            });

            // clicks should select/deselect shapes
            stage.on('click tap', function (e) {
                // if we are selecting with rect, do nothing
                //console.log('click tap EVENT');
                if (selectionRectangle.visible()) {
                    return;
                }

                // if click on empty area - remove all selections
                if (e.target === stage) {
                    tr.nodes([]);
                    return;
                }

                // do nothing if clicked NOT on our rectangles
                if (!e.target.hasName('group')) { //before here was rect
                    return;
                }

                // do we pressed shift or ctrl?
                const metaPressed = e.evt.shiftKey || e.evt.ctrlKey || e.evt.metaKey;
                const isSelected = tr.nodes().indexOf(e.target) >= 0;

                if (!metaPressed && !isSelected) {
                    // if no key pressed and the node is not selected
                    // select just one
                    tr.nodes([e.target]);
                } else if (metaPressed && isSelected) {
                    // if we pressed keys and node was selected
                    // we need to remove it from selection:
                    const nodes = tr.nodes().slice(); // use slice to have new copy of array
                    // remove node from array
                    nodes.splice(nodes.indexOf(e.target), 1);
                    tr.nodes(nodes);
                } else if (metaPressed && !isSelected) {
                    // add the node into selection
                    const nodes = tr.nodes().concat([e.target]);
                    tr.nodes(nodes);
                }
            });


        }

    });
});

