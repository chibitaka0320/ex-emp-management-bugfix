'use strict';
$(function () {
    $('#txt-search').autocomplete({
        source: function (req, resp) {
            $.ajax({
                type: "GET",
                url: "/employee/autocomplete",
                dataType: "json",
                success: function (response) {
                    const filteredData = response.filter(item => item.toLowerCase().includes(req.term.toLowerCase()));
                    resp(filteredData);
                },
                error: function (xhr, ts, err) {
                    console.log(xhr);
                    console.log(ts);
                    console.log(err);
                    resp(['']);
                }
            });
        }
    });
});
