(
    function($) {
        function realScroll () {
            window.scrollTo(0,0);
            $('body,html').animate({scrollTop: $(document).height()}, <<duration>>, 'linear', function(){
                    window.setTimeout(realScroll, <<pauseBeforeRepetition>>);
                });
            }
            $('document').ready(function(){
                var timeout = window.setTimeout(realScroll, 0);
            });
    }
)(jQuery);