"use strict";
var j = jQuery.noConflict(true);

function getDocHeight() {
        var D = document;
        return Math.max(
            D.body.scrollHeight, D.documentElement.scrollHeight,
            D.body.offsetHeight, D.documentElement.offsetHeight,
            D.body.clientHeight, D.documentElement.clientHeight
        );
    }

function scrollDown() {
    j('body,html').animate({scrollTop: j(window).scrollTop() + j(window).height()}, 1800, 'swing', function(){
            if(j(window).scrollTop() + j(window).height() == getDocHeight())
            {
                window.setTimeout(<<bottomReachedAction>>, <<duration>>);
            }
            else
            {
                window.setTimeout(scrollDown, <<duration>>);
            }
        });
}

function scrollUp() {
    j('body,html').animate({scrollTop: j(window).scrollTop() - j(window).height()}, 1800, 'swing', function(){
        if(j(window).scrollTop() === 0)
        {
            window.setTimeout(scrollDown, <<duration>>);
        }
        else
        {
            window.setTimeout(scrollUp, <<duration>>);
        }
    });
}

j(document).ready(function(){
    window.setTimeout(scrollDown, <<duration>>);
});