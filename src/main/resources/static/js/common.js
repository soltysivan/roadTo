$(function() {
    $(function() {
        function maskPhone() {
            var vid = $('#vid option:selected').val();
            switch (vid) {
                case "sot":
                    $("#phone").mask("+7(999) 999-99-99");
                    break;
                case "gor":
                    $("#phone").mask("999-99-99");
                    break;
            }
        }
        maskPhone();
        $('#vid').change(function() {
            maskPhone();
        });
    });
}