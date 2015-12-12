var j = jQuery.noConflict(true);

function getDocHeight() {
        var D = document;
        return Math.max(
            D.body.scrollHeight, D.documentElement.scrollHeight,
            D.body.offsetHeight, D.documentElement.offsetHeight,
            D.body.clientHeight, D.documentElement.clientHeight
        );
    }

function bottomScroll () {
    j('body,html').animate({scrollTop: j(window).height()}, 1800, 'swing', function(){
            console.log("scroll bot");
            if(j(window).scrollTop() + j(window).height() == getDocHeight())
                window.setTimeout(topScroll, <<showPageDurationSwitch>>);
            else
                window.setTimeout(bottomScroll, <<showPageDuration>>);
        });
}

function topScroll () {
    j('body,html').animate({scrollTop: -j(window).height()}, 1800, 'swing', function(){
        console.log("scroll top");
        if(j(window).scrollTop() == 0)
            window.setTimeout(this.bottomScroll, <<showPageDuration>>);
        else
            window.setTimeout(topScroll, <<showPageDurationSwitch>>);

    });
}

j('document').ready(function(){
    var timeout = window.setTimeout(bottomScroll, <<showPageDuration>>);
});