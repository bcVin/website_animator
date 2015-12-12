var j = jQuery.noConflict(true);

function realScroll () {
    window.scrollTo(0,0);
    j('body,html').animate({scrollTop: j(document).height()}, <<duration>>, 'linear', function(){
            window.setTimeout(realScroll, <<pauseBeforeRepetition>>);
        });
    }

j('document').ready(function(){
    var timeout = window.setTimeout(realScroll, 0);
});