$(document).ready(
    function() {
        $('#navigace a.polozka').click(function(event) {
            $('#navigace a').removeClass('selected');
            $(this).addClass('selected');
            $('#obsah').children().hide();
            $('#obsah').children($(this).attr('href')).show();
            event.preventDefault();
            return false;
        });
    }
);

