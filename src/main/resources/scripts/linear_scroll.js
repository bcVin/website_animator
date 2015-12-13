"use strict";
var j = jQuery.noConflict(true);

function scrollDown() {
        j('body,html').animate({scrollTop: j(document).height()}, <<duration>>, 'linear', null);
        window.setTimeout(<<bottomReachedAction>>, <<duration>> * 0.2);
    }

function scrollUp() {
        j('body,html').animate({scrollTop: 0}, <<duration>>, 'linear', null);
        window.setTimeout(scrollDown, <<duration>> * 0.2);
    }

j(document).ready(function(){
    j('html, body').animate({ scrollTop: 0 }, 'medium');
    window.setTimeout(scrollDown, 0);
});