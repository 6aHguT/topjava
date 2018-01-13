var ajaxUrl = "ajax/profile/meals/";
var datatableApi;

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize(),
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

$(function () {
    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "render": function (date, type, row) {
                    if (type === "display") {
                        return date.substring(0, 16).replace("T", " ");
                    }
                    return date;
                }
            },
            {
                "data": "description",
            },
            {
                "data": "calories",
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            $(row).addClass(data.exceed ? 'exceeded' : 'normal');
        },
        "initComplete": makeEditable
    });


//  http://xdsoft.net/jqplugins/datetimepicker/
    var startDate = $('#startDate');
    var endDate = $('#endDate');
    var startTime = $('#startTime');
    var endTime = $('#endTime');
    startDate.datetimepicker({
        format:'YYYY-MM-DD'
    });
    endDate.datetimepicker({
        format:'YYYY-MM-DD'
    });
    startTime.datetimepicker({
        format:'HH:mm'
    });
    endTime.datetimepicker({
        format:'HH:mm'
    });
    $('#dateTime').datetimepicker({
        format:'YYYY-MM-DD\\THH:mm'
    });
});