
$(document).ready(function() {
    $("#add_row").on("click", function() {
        // Dynamic Rows Code

        // Get max row id and set new id
        var newid = 0;
        $.each($("#modgrad_table tr"), function() {
            if (parseInt($(this).data("id")) > newid) {
                newid = parseInt($(this).data("id"));
            }
        });
        newid++;

        var tr = $("<tr></tr>", {
            id: "module_row"+newid,
            "data-id": newid
        });

        // loop through each td and create new elements with name of newid
        $.each($("#modgrad_table tbody tr:nth(0) td"), function() {
            var td;
            var cur_td = $(this);

            var children = cur_td.children();

            // add new td and element if it has a nane
            if ($(this).data("name") !== undefined) {
                td = $("<td></td>", {
                    "data-name": $(cur_td).data("name")
                });


                var c = $(cur_td).find($(children[0]).prop('tagName')).clone().val("");
                c.attr("name",  "grades" + "[" + newid + "]" + "." + $(cur_td).data("name"));
                c.attr("id", "grades" + newid + "." + $(cur_td).data("name"))
                c.appendTo($(td));
                td.appendTo($(tr));

            } else {
                td = $("<td></td>", {
                    'text': $('#modgrad_table tr').length
                }).appendTo($(tr));
            }
        });

        // add the new row
        $(tr).appendTo($('#modgrad_table'));

        $(tr).find("td button.row-remove").on("click", function() {
            $(this).closest("tr").remove();
        });
    });



    // Sortable Code
    var fixHelperModified = function(e, tr) {
        var $originals = tr.children();
        var $helper = tr.clone();

        $helper.children().each(function(index) {
            $(this).width($originals.eq(index).width())
        });

        return $helper;
    };

    $(".table-sortable tbody").sortable({
        helper: fixHelperModified
    }).disableSelection();

    $(".table-sortable thead").disableSelection();



    $("#add_row").trigger("click");
});

// Example starter JavaScript for disabling form submissions if there are invalid fields
(function() {
    'use strict';
    window.addEventListener('load', function() {
        // Fetch all the forms we want to apply custom Bootstrap validation styles to
        var forms = document.getElementsByClassName('needs-validation');
        // Loop over them and prevent submission
        var validation = Array.prototype.filter.call(forms, function(form) {
            form.addEventListener('submit', function(event) {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    }, false);
})();

