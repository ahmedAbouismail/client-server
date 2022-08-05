$(document).ready(function(){
    $("#modgrad_table").on('click', '#deleteRow', function () {
        $(this).closest('tr').remove();
    });
});
