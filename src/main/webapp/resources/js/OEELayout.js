/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

class Square {
    constructor(x, y, width, height, color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }
}

function clickHandler(e) {
    console.log('Sem v clickHandler(e) e:' + e);
}

document.addEventListener("DOMContentLoaded", function () {
    const canvas = document.getElementById("canvas");
    const context = canvas.getContext("2d");
    
   var elemLeft = canvas.offsetLeft;
   var elemTop = canvas.offsetTop;

    const height = 684;
    const width = 1420;

    // resize canvas (CSS does scale it up or down)
    canvas.height = height;
    canvas.width = width;

    //DEAN CUSTOM
    var listOfSquares = new Array();
    let square1 = new Square(100, 150, 200, 300, 'blue');
    let square2 = new Square(400, 150, 200, 150, 'red');

    listOfSquares.push(square1);
    listOfSquares.push(square2);

    console.log('List of squares size:' + listOfSquares.length);
    for (var i = 0; i < listOfSquares.length; i++) {
        context.fillRect(listOfSquares[i].x, listOfSquares[i].y, listOfSquares[i].width, listOfSquares[i].height);
    }
    //END DEAN CUSTOM

    function getMousePos(canvas, evt) {
        var rect = canvas.getBoundingClientRect(),
                scaleX = canvas.width / rect.width,
                scaleY = canvas.height / rect.height;

        return {
            x: (evt.clientX - rect.left) * scaleX,
            y: (evt.clientY - rect.top) * scaleY,
        };
    }

    // implement drawing rectangle
    let start = {}

    function startRect(e) {
        start = getMousePos(canvas, e);
    }

    canvas.addEventListener("mousedown", startRect);

    function endRect(e) {
        let {x, y} = getMousePos(canvas, e);
        context.fillStyle = 'rgba(203,203,203,0.5)';
        context.fillRect(start.x, start.y, x - start.x, y - start.y);
        console.log('Rectangle coordinates for saving x:' + start.x + ' y:' + start.y);
        console.log('Rectangle width/height for saving width:' + (x - start.x) + ' height:' + (y - start.y));
        $('#myModal').modal('toggle');
    }

    canvas.addEventListener("mouseup", endRect);
});
