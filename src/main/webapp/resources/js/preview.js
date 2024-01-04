/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//slide structure
class Slide {
    constructor(id, url, timeOnDisplay, tagType) {
        this.id = id;
        this.url = url;
        this.timeOnDisplay = timeOnDisplay;
        this.tagType = tagType;
    }
}

let slideIndex = 1;
let slides;
let sliders;

//LAN je imel prej hardcodan baseRestUrl, jaz sem spremenil da ga sestavi iz getUrl.protocol in getUrl.host
//let baseRestUrl = "http://10.36.64.16:7070/osloWebApp/rest/";
//let baseRestUrl = "http://127.0.0.1:7070/osloWebApp/rest/";
var getUrl = window.location;
var baseUrl = getUrl .protocol + "//" + getUrl.host + "/osloWebApp/rest/";
let baseRestUrl = baseUrl;

//jQuery code
$(document).ready(function () {
    console.log("------ in $(document).ready ------");
    console.log($("SIGNPresentationEditForm").css("background", "red"));
    $("#SIGNPresentationViewDlg_content").css("padding", "0");
    $(".ui-commandlink").removeClass("ui-commandlink ui-widget").addClass("dropdown-item");
    $("#btnLogin").removeAttr("class");
    $("#btnLogin").attr("class", "btn btn-primary block full-width m-b");
});

function checkForPresentation(idPresentation) {
    slideIndex = 1;
    console.log("------ in checkForPresentation(idPresentation) ------");
    console.log("BASEURL:"+baseUrl);
    $("#SIGNPresentationViewDlg").css("left", "25%");
    $("#SIGNPresentationViewDlg").css("top", "15%");
    console.log("in checkForPresentation");
    console.log("Play presentation with ID " + idPresentation);
    $.ajax({
        url: baseRestUrl + "SIGNSlide/findByPresentationId/" + idPresentation,
        method: "GET",
        contentType: "application/json",
        timeout: 0
    }).done(function (response) {
        console.log("call addElements(response)");
        addElements(response);
    }).fail(function () {
        console.log("error - getting slides failed - page will be refreshed");
        setErrorScreen();
    });
}

function checkForPresentationIndexPage(idPresentation) {
    slideIndex = 1;
    console.log("------ in checkForPresentationIndexPage(idPresentation) ------");
    console.log("BASEURL:"+baseUrl);
    $("#SIGNPresentationViewDlg").css("left", "25%");
    $("#SIGNPresentationViewDlg").css("top", "15%");
    console.log("in checkForPresentation");
    console.log("Play presentation with ID " + idPresentation);
    $.ajax({
        url: baseRestUrl + "SIGNSlide/findByPresentationId/" + idPresentation,
        method: "GET",
        contentType: "application/json",
        timeout: 0
    }).done(function (response) {
        console.log("call addElements(response)");
        addElementsIndexPage(response);
    }).fail(function () {
        console.log("error - getting slides failed - page will be refreshed");
        setErrorScreen();
    });
}

function checkForPresentationDean(idPresentation) {
    slideIndex = 1;
    console.log("------ in checkForPresentationDean(idPresentation) ------");
    console.log("BASEURL:"+baseUrl);
    $("#SIGNPresentationViewDlg").css("left", "25%");
    $("#SIGNPresentationViewDlg").css("top", "15%");
    console.log("in checkForPresentation");
    console.log("Play presentation with ID " + idPresentation);
    $.ajax({
        url: baseRestUrl + "SIGNSlide/findByPresentationId/" + idPresentation,
        method: "GET",
        contentType: "application/json",
        timeout: 0
    }).done(function (response) {
        console.log("call addElements(response)");
        addElementsDean(response, idPresentation);
    }).fail(function () {
        console.log("error - getting slides failed - page will be refreshed");
        setErrorScreen();
    });
}

function addElements(response) {
    console.log("------ in addElements(response) ------");
    console.log("set sliders");
    //set the slides
    response = JSON.stringify(response);
    sliders = $.parseJSON(response);

    console.log("get .container element");
    //get the jQuery element of the container
    let container = $(".container").eq(0);

    let slider;
    //add dom elements in the container
    console.log("set HTML slide elements with for loop");
    for (let index = 0; index < sliders.length; index++) {
        //creating a jQuery element with an mySlides class
        slider = $("<div></div>").attr("class", "mySlides");
        //adding a div element to the div with the class mySlides
        slider.append($("<div></div>").attr("class", "numbertext").text((index + 1) + "/" + sliders.length));
        //checking the type of the element that is going to be added
        if (sliders[index].idTagType.name === "img") {
            //setting the attributes of the img element
            slider.append($("<img></img>").attr({id: sliders[index].idSlide, src: sliders[index].sourceUrl, class: "img"}));
        } else if (sliders[index].idTagType.name === "video") {
            //setting video attributes
            let video = $("<video></video>").attr({id: sliders[index].idSlide, autoplay: "true", class: "video"});
            //setting source to the video element
            slider.append(video.append($("<source></source>").attr({src: sliders[index].sourceUrl, type: "video/" + sliders[index].fileName.split(".").pop(), class: "source"})));
            //setting to get the video to be muted
            video.get(0).muted = "muted";
            video = null;
        }
        //add the element to the container
        container.append(slider);
        slider = null;
    }

    console.log("set prev and next buttons to presentation view");
    //setting next and previous buttons
    let prev = $("<a></a>").attr({class: "prev"}).text("<").css("color", "white");
    let next = $("<a></a>").attr({class: "next"}).text(">").css("color", "white");
    prev.on("click", function () {
        plusSlides(-2);
    });
    next.on("click", function () {
        plusSlides(0);
    });
    container.append(prev);
    container.append(next);

    //get all the slides
    slides = $(".mySlides");

    //call the event with the timer
    console.log("call showSlides()");
    showSlides();
}

function addElementsIndexPage(response) {
    console.log("------ in addElements(response) ------");
    console.log("set sliders");
    //set the slides
    response = JSON.stringify(response);
    sliders = $.parseJSON(response);

    console.log("get .containerIndexPage element");
    //get the jQuery element of the container
    let container = $(".containerIndexPage").eq(0);

    let slider;
    //add dom elements in the container
    console.log("set HTML slide elements with for loop");
    for (let index = 0; index < sliders.length; index++) {
        //creating a jQuery element with an mySlides class
        slider = $("<div></div>").attr("class", "mySlides");
        //adding a div element to the div with the class mySlides
        slider.append($("<div></div>").attr("class", "numbertextIndex").text((index + 1) + "/" + sliders.length));
        //checking the type of the element that is going to be added
        if (sliders[index].idTagType.name === "img") {
            //setting the attributes of the img element
            slider.append($("<img></img>").attr({id: sliders[index].idSlide, src: sliders[index].sourceUrl, class: "img"}));
        } else if (sliders[index].idTagType.name === "video") {
            //setting video attributes
            let video = $("<video></video>").attr({id: sliders[index].idSlide, autoplay: "true", class: "video"});
            //setting source to the video element
            slider.append(video.append($("<source></source>").attr({src: sliders[index].sourceUrl, type: "video/" + sliders[index].fileName.split(".").pop(), class: "source"})));
            //setting to get the video to be muted
            video.get(0).muted = "muted";
            video = null;
        }
        //add the element to the container
        container.append(slider);
        slider = null;
    }

    console.log("set prev and next buttons to presentation view");
    //setting next and previous buttons
    let prev = $("<a></a>").attr({class: "prevIndex"}).text("<").css("color", "white");
    let next = $("<a></a>").attr({class: "nextIndex"}).text(">").css("color", "white");
    prev.on("click", function () {
        plusSlides(-2);
    });
    next.on("click", function () {
        plusSlides(0);
    });
    container.append(prev);
    container.append(next);

    //get all the slides
    slides = $(".mySlides");

    //call the event with the timer
    console.log("call showSlides()");
    showSlides();
}

function addElementsDean(response, idPresentation) {
    console.log("------ in addElementsDean(response) for presentation"+idPresentation+" ------");
    console.log("set sliders");
    //set the slides
    response = JSON.stringify(response);
    sliders = $.parseJSON(response);
    
    //console.log("SLIDERS:"+sliders);

    console.log("get .container"+idPresentation+" element");
    //get the jQuery element of the container
    let container = $(".container"+idPresentation+"").eq(0);

    let slider;
    //add dom elements in the container
    console.log("set HTML slide elements with for loop");
    for (let index = 0; index < sliders.length; index++) {
        //creating a jQuery element with an mySlides class
        slider = $("<div></div>").attr("class", "mySlides");
        //adding a div element to the div with the class mySlides
        slider.append($("<div></div>").attr("class", "numbertextIndex").text((index + 1) + "/" + sliders.length));
        //checking the type of the element that is going to be added
        if (sliders[index].idTagType.name === "img") {
            //setting the attributes of the img element
            slider.append($("<img></img>").attr({id: sliders[index].idSlide, src: sliders[index].sourceUrl, class: "img"}));
        } else if (sliders[index].idTagType.name === "video") {
            //setting video attributes
            let video = $("<video></video>").attr({id: sliders[index].idSlide, autoplay: "true", class: "video"});
            //setting source to the video element
            slider.append(video.append($("<source></source>").attr({src: sliders[index].sourceUrl, type: "video/" + sliders[index].fileName.split(".").pop(), class: "source"})));
            //setting to get the video to be muted
            video.get(0).muted = "muted";
            video = null;
        }
        //add the element to the container
        container.append(slider);
        slider = null;
    }

    console.log("set prev and next buttons to presentation view");
    //setting next and previous buttons
    let prev = $("<a></a>").attr({class: "prevIndex"}).text("<").css("color", "white");
    let next = $("<a></a>").attr({class: "nextIndex"}).text(">").css("color", "white");
    prev.on("click", function () {
        plusSlides(-2);
    });
    next.on("click", function () {
        plusSlides(0);
    });
    container.append(prev);
    container.append(next);

    //get all the slides
    slides = $(".mySlides");

    //call the event with the timer
    console.log("call showSlides()");
    showSlides();
}

let timeout;
function showSlides() {
    console.log("------ in showSlides() ------");
    if (slideIndex > slides.length) {
        slideIndex = 1;
    }
    if (slideIndex < 1) {
        clearAllTimeouts();
        slideIndex = slides.length;
    }
    //check if the slideIndex is out of bound of the array
    if (slideIndex > slides.length) {
        console.log("check for new presentation");
        slideIndex = 1;
        checkForNewPresentation();
    }
    //loop for hiding the unused elements 
    for (i = 0; i < slides.length; i++) {
        //check if the element is a video type
        if (sliders[i].idTagType.name === "video") {
            //pause the video
            console.log("pause video with index " + (slideIndex - 1));
            $(slides[i]).find("video").get(0).pause();
        }
        //hide the element
        slides[i].style.display = "none";
    }
    //chek if the element is of video type
    if (sliders[slideIndex - 1].idTagType.name === "video") {
        //get the jQuery elemnt
        let video = $(slides[slideIndex - 1]).find("video");
        console.log("start video with index " + (slideIndex - 1));
        //set the video to start
        video.get(0).currentTime = 0;
        //play the video
        console.log("Video duration: " + video.get(0).duration);
        sliders[slideIndex - 1].timeOnDisplay = video.get(0).duration;
        video.get(0).play();
    }
    //display the element
    slides[slideIndex - 1].style.display = "block";

    console.log("playing slide " + (slideIndex - 1) + " with time delay " + sliders[slideIndex - 1].timeOnDisplay + "seconds");

    //call the method again
    timeout = setTimeout(showSlides, sliders[slideIndex - 1].timeOnDisplay * 1000);
    //add to the index atribute
    slideIndex++;
}

function clearAllTimeouts() {
    let id = window.setTimeout(function () {}, 0);

    while (id--) {
        window.clearTimeout(id); // will do nothing if no timeout with id is present
    }
}

function stopPresentation() {
    clearAllTimeouts();
    $(".container").eq(0).empty();
}

function plusSlides(n) {
    console.log("------ in plusSlides(n) ------");
    clearTimeout(timeout);
    console.log("Before change slide click with slideIndex: " + slideIndex);
    slideIndex = slideIndex + n;
    console.log("After change slide click with slideIndex: " + slideIndex);
    showSlides();
}

function setErrorScreen() {
    console.log("------ in setErrorScreen() ------");
    let container = $(".container").eq(0);
    var userLang = navigator.language || navigator.userLanguage;
    let message = "Naši IT strokovnjaki odpravljajo napako";
    let platform = navigator.platform;
    if (platform !== ("Linux armv7l")) {
        switch (userLang) {
            case "en-US":
                message = "Our IT experts are already fixing the problem";
                break;
            case "sl-SI":
                message = "Naši IT strokovnjaki odpravljajo napako";
                break;
            case "tr-TR":
                message = "BT uzmanlarımız sorunu zaten çözüyor";
                break;
        }
    }
    container.append($("<h1>" + message + "</h1>"));
    container.append($("<img></img>").attr("src", "https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/51fc3d72702163.5bf058140cbac.gif"));
}
