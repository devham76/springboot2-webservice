function gole_modal() {
     $("#gole_modal").modal('show');
}
function gole_modal_save(){
    var data = {
            title: $('#gole_modal_title').val()
    };
    $.ajax({
        type: 'POST',
        url: "/api/v1/goles",
        dataType: 'json',
        contentType:'application/json; charset=utf-8',
        data: JSON.stringify(data)
    }).done(function() {
        //window.location.href = '/planGole';
        //alert("등록되었습니다");
    }).fail(function(error) {
        alert(JSON.stringify(error));
    });
}

function init(){

    $.ajax({

    }).done(function(data){

    }).fail(function(error){
        alert(JSON.stringify(error));
    });
}