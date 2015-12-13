"use strict";
var j = jQuery.noConflict(true);

function scrollTop() {
    j('html, body').animate({ scrollTop: 0 }, 'medium');
    scrollDown();
}