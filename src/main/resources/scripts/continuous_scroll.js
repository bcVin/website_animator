"use strict";
var j = jQuery.noConflict(true);

function realScroll () {
    j('html, body').animate({ scrollTop: 0 }, 'fast');
    j('body,html').animate({scrollTop: j(document).height()}, <<duration>>, 'linear', function(){
            window.setTimeout(realScroll, <<pauseBeforeRepetition>>);
        });
    }

j('document').ready(function(){
     window.setTimeout(realScroll, 0);
});