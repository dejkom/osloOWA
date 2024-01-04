//slide structure
class Slide {
    constructor(id, url, timeOnDisplay, tagType) {
        this.id = id;
        this.url = url;
        this.timeOnDisplay = timeOnDisplay;
        this.tagType = tagType;
    }
}

let alertMessage = false;
let slideIndex;

let baseRestUrl = "http://10.36.64.16:7070/osloWebApp/rest/";
//let baseRestUrl = "http://127.0.0.1:7070/osloWebApp/rest/";

let restUrl = baseRestUrl + "SIGNtelevision/getByIpaddress";
let playCounter = 0;
//jQuery code
$(document).ready(function () {
    let logo = $("#logo_img").attr("src");
    console.log("atr src: " + logo);
    loadAlerts();
});

function loadAlerts() {
    slideIndex = 1;
    //zaenkrat dela vse tut brez clear
    //clearAllTimeouts();
    $.ajax({
        url: baseRestUrl + "SIGNalert/last",
        method: "GET",
        contentType: "application/json",
        timeout: 0
    }).done(function (response) {
        if (response !== undefined && response.innerHTML.trim() !== "") {
            $(".alerts").css("display", "block");
            alertMessage = $.parseHTML(response.innerHTML);
        } else {
            $(".alerts").css("display", "none");
            alertMessage = null;
        }
        checkForNewPresentation();
    }).fail(function () {
        alertMessage = null;
        checkForNewPresentation();
    });
}

function checkForNewPresentation() {
    let url = new URL(window.location.href);
    let ip = url.searchParams.get("ip");
    console.log(ip);
    if (ip !== null) {
        restUrl = baseRestUrl + "SIGNtelevision/getByIpaddress?ip=" + ip;
    }
    console.log(restUrl);
    $.ajax({
        url: restUrl,
        method: "GET",
        contentType: "application/json",
        timeout: 0
    }).done(function (response) {
        if (response === undefined && response.trim() === "") {
            setErrorScreen();
            console.log("Televison JSON in undefined or an empty string");
            return;
        }
        //get the TVs presentationId
        response = JSON.stringify(response);
        let tv = $.parseJSON(response);
        console.log(tv);
        if ($.isEmptyObject(tv) === false && tv.hasOwnProperty('idPresentation') === true) {
            if (response === undefined && response === "") {
                setNoPresentationScreen();
                console.log("Slides JSON in undefined or an empty string");
                return;
            }
            let idPresentation = tv.idPresentation.idPresentation;
            console.log("Play presentation " + tv.idPresentation.name + " with ID " + idPresentation);
            $.ajax({
                url: baseRestUrl + "SIGNSlide/findByPresentationId/" + idPresentation,
                method: "GET",
                contentType: "application/json",
                timeout: 0
            }).done(function (response) {
                console.log("set sliders");
                //set the slides
                response = JSON.stringify(response);
                let sliders = $.parseJSON(response);
                console.log("get slides");

                //get the jQuery element of the container
                let container = $(".container").eq(0);
                if (alertMessage !== null) {
                    container.css("width", "70vw");
                } else {
                    container.removeAttr("style");
                }
                //remove all elements from the first container div
                container.empty();
                let slider;
                //add dom elements in the container
                for (let index = 0; index < sliders.length; index++) {
                    //creating a jQuery element with an mySlides class
                    slider = $("<div></div>").attr("class", "mySlides");
                    if (alertMessage !== null) {
                        slider.css("width", "70vw");
                    }
                    //adding a div element to the div with the class mySlides
                    slider.append($("<div></div>").attr("class", "numbertext").text((index + 1) + "/" + sliders.length));
                    //checking the type of the element that is going to be added
                    if (sliders[index].idTagType.name === "img") {
                        let img = $("<img></img>").attr({id: sliders[index].idSlide, src: sliders[index].sourceUrl, class: "slideImg"});
                        if (alertMessage !== null) {
                            img.css("width", "70vw");
                        }
                        //setting the attributes of the img element
                        slider.append(img);
                    } else if (sliders[index].idTagType.name === "video") {
                        //setting video attributes
                        let video = $("<video></video>").attr({id: sliders[index].idSlide, autoplay: "true", class: "slideVideo"});
                        if (alertMessage !== null) {
                            video.css("width", "70vw");
                        }
                        //setting source to the video element
                        slider.append(video.append($("<source></source>").attr({src: sliders[index].sourceUrl, type: "video/" + sliders[index].fileName.split(".").pop()})));
                        //setting to get the video to be muted
                        video.get(0).muted = "muted";
                        video = null;
                    }
                    //add the element to the container
                    container.append(slider);
                    slider = null;
                }

                //get all the slides
                var slides = $(".mySlides");

                if (alertMessage !== null) {
                    let div = $(".alerts");
                    let logo = $("#logo_img").attr("src");
                    div.empty();
                    div.css("display", "block");
                    let img = $("<img></img>").attr("src", logo).css({display: "block", width: "10vw", height: "10vh"});
                    div.append(img);
                    div.append(alertMessage);
                    $("body").append(div);
                }

                //call the event with the timer
                showSlides();
                function showSlides() {
                    //check if the slideIndex is out of bound of the array
                    if (slideIndex > slides.length) {
                        $.ajax({
                            url: restUrl,
                            method: "GET",
                            contentType: "application/json",
                            timeout: 0
                        }).done(function (response) {
                            let newIdPresentation;
                            if(response.idPresentation !== undefined){
                                newIdPresentation = response.idPresentation.idPresentation;
                            }
                            else{
                                setNoPresentationScreen();
                            }
                            if (idPresentation === newIdPresentation) {
                                console.log("presentation is the same id: " + newIdPresentation);
                                slideIndex = 1;
                                playCounter = playCounter + 1;
                                if (playCounter === 15) {
                                    refreshWindow();
                                } else {
                                    $.ajax({
                                        url: baseRestUrl + "SIGNalert/last",
                                        method: "GET",
                                        contentType: "application/json",
                                        timeout: 0
                                    }).done(function (response) {
                                        if (response !== undefined && response.innerHTML.trim() !== "") {
                                            $(".alerts").css("display", "block");
                                            alertMessage = $.parseHTML(response.innerHTML);
                                            let div = $(".alerts");
                                            div.empty();
                                            div.css("display", "block");
                                            let logo = $("#logo_img").attr("src");
                                            let img = $("<img></img>").attr("src", logo).css({display: "block", width: "10vw", height: "10vh"});
                                            div.append(img);
                                            div.append(alertMessage);
                                            $("body").append(div);
                                            $(".container").eq(0).css("width","70vw");
                                            for(let i =0; i<sliders.length; i++){
                                                let id = "#" + sliders[i].idSlide;
                                                $(id).css("width","70vw");
                                            }
                                        } else {
                                            $(".alerts").css("display", "none");
                                            $(".container").eq(0).css("width","100vw");
                                            for(let i =0; i<sliders.length; i++){
                                                let id = "#" + sliders[i].idSlide;
                                                $(id).css("width","100vw");
                                            }
                                            alertMessage = null;
                                        }
                                        showSlides();
                                    }).fail(function () {
                                        alertMessage = null;
                                        showSlides();
                                        console.log("Error getting alert.");
                                    });
                                }
                            } else {
                                console.log("new presentation with id: " + newIdPresentation);
                                refreshWindow();
                            }
                        }).fail(function () {
                            setErrorScreen();
                        });
                        return;
                    }
                    //loop for hiding the unused elements 
                    for (i = 0; i < slides.length; i++) {
                        //check if the element is a video type
                        if (sliders[i].idTagType.name === "video") {
                            //pause the video
                            let video = document.getElementById(sliders[i].idSlide);
                            if (!$(video.paused)) {
                                console.log("pause video with index " + (slideIndex - 1));
                                $(video.pause());
                            }
                        }
                        //hide the element
                        slides[i].style.display = "none";
                    }
                    //chek if the element is of video type
                    if (sliders[slideIndex - 1].idTagType.name === "video") {
                        //get the jQuery elemnt
                        let id = "#" + sliders[slideIndex - 1].idSlide;
                        let video = $(id).get(0);
                        console.log("start video with index " + (slideIndex - 1));
                        //set the video to start
                        video.currentTime = 0;
                        console.log("Video duration: " + video.duration);
                        sliders[slideIndex - 1].timeOnDisplay = video.duration;
                        //play the video
                        video.play();
                    }
                    //display the element
                    slides[slideIndex - 1].style.display = "block";

                    console.log("playing slide " + (slideIndex - 1) + " with time delay " + sliders[slideIndex - 1].timeOnDisplay + "seconds");

                    //call the method again
                    setTimeout(showSlides, sliders[slideIndex - 1].timeOnDisplay * 1000);

                    //add to the index atribute
                    slideIndex++;
                }
            }).fail(function () {
                console.log("error - getting slides failed - page will be refreshed");
                setErrorScreen();
            });
        } else {
            console.log("error - tv does not hava a defined presentation id");
            setNoPresentationScreen();
        }
    }).fail(function () {
        console.log("error - getting TVs slide failed - page will be refreshed");
        setErrorScreen();
    });
}

function refreshWindow() {
    window.location.reload(true);
    console.log("Refreshing page");
}

function setErrorScreen() {
    let container = $(".container").eq(0);
    $(".alerts").css("display", "none");
    container.css("width", "100vw");
    let alert_image_src = $("#error_image").attr("src");
    var userLang = navigator.language || navigator.userLanguage;
    let message = "Naši IT strokovnjaki odpravljajo napako";
    let platform = navigator.platform;
    if (platform !== ("Linux armv7l")) {
        switch (userLang) {
            case "en-US":
                message = "Our IT experts are already fixing the problem";
                break;
            case "tr-TR":
                message = "BT uzmanlarımız sorunu zaten çözüyor";
                break;
        }
    }

    container.append($("<h1></h1>").attr("class", "errorlabel").text(message));
    container.append($("<img></img>").attr("src", alert_image_src).css({width: "100vw", height: "100vh"}));

    setTimeout(refreshWindow, 30000);
}


function setNoPresentationScreen() {
    let container = $(".container").eq(0);
    $(".alerts").css("display", "none");
    container.css("width", "100vw");
    let logo = $("#logo_img").attr("src");
    var userLang = navigator.language || navigator.userLanguage;
    let message = "Predstavitev ni nastavljena";
    let platform = navigator.platform;
    if (platform !== ("Linux armv7l")) {
        switch (userLang) {
            case "en-US":
                message = "Presentation is not set";
                break;
            case "tr-TR":
                message = "Sunum ayarlanmadı";
                break;
        }
    }

    container.append($("<h1></h1>").attr("class", "errorlabel").text(message));
    container.append($("<img></img>").attr("src", logo).css({"margin-top": "12%", width: "50vw", height: "50vh"}));

    setTimeout(refreshWindow, 30000);
}