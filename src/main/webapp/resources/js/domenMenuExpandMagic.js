$(document).ready(function () {
    console.log('sem v document ready funkciji');

    var all = document.getElementsByTagName("a");
    for (var i = 0; i < all.length; i++) {
        if (all[i].href == window.location) {
            all[i].setAttribute("style", "color:white; font-weight:bold");
            var parent = all[i].parentElement;
            for (var j = 0; j < 10; j++)
            {
                if (parent.tagName == 'UL')
                {
                    parent.setAttribute("class", parent.getAttribute("class") + " in");
                    parent.setAttribute("style", "");
                }
                parent = parent.parentElement;
            }
        }
    }

});