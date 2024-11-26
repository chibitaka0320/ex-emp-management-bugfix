'use strict';
$(function () {
    $('#button-addon2').on('click', function () {
        let submitUrl = 'https://zipcoda.net/api';
        let inputZipcode = $('#zipCode').val();
        let formatZipcode = inputZipcode.replace('-', '');
        $.ajax({
            type: "get",
            url: submitUrl,
            data: {
                zipcode: formatZipcode
            },
            dataType: "json",
        }).done((data) => {
            $('#address').val(data.items[0].address);
        }).fail((xmlHttpRequest, textStatus, errorThrow) => {
            console.log(xmlHttpRequest);
            console.log(textStatus);
            console.log(errorThrow);
            $('#address').val('');
        });
    });
});
