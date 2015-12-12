function getDocHeight() {
        var D = document;
        return Math.max(
            D.body.scrollHeight, D.documentElement.scrollHeight,
            D.body.offsetHeight, D.documentElement.offsetHeight,
            D.body.clientHeight, D.documentElement.clientHeight
        );
    }

(
    function($) {

        function bottomScroll () {
            $('body,html').animate({scrollTop: $(window).height()}, 1800, 'swing', function(){
                    console.log("scroll bot");
                    if($(window).scrollTop() + $(window).height() == getDocHeight())
                        window.setTimeout(topScroll, <<showPageDurationSwitch>>);
                    else
                        window.setTimeout(bottomScroll, <<showPageDuration>>);
                });
        }

        function topScroll () {
            $('body,html').animate({scrollTop: -$(window).height()}, 1800, 'swing', function(){
                console.log("scroll top");
                if($(window).scrollTop() == 0)
                    window.setTimeout(this.bottomScroll, <<showPageDuration>>);
                else
                    window.setTimeout(topScroll, <<showPageDurationSwitch>>);

            });
        }

        $('document').ready(function(){
            var timeout = window.setTimeout(bottomScroll, <<showPageDuration>>);
        });
    }
)(jQuery);