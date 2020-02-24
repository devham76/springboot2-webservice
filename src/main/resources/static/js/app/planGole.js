$(function(){
    init();

    $(document).on("focusout",".plan_area",function(){
        console.log($(this).val());
        console.log($(this).attr("original"));
    });

});

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
        window.location.href = '/planGole';
        //alert("등록되었습니다");
    }).fail(function(error) {
        alert(JSON.stringify(error));
    });
}
function plan_modal_save(content, planSeq ){
    $.ajax({
        type: 'POST',
        url: "/api/v1/goles",
        dataType: 'json',
        contentType:'application/json; charset=utf-8',
        data: JSON.stringify(data)
    }).done(function() {
        window.location.href = '/planGole';
        //alert("등록되었습니다");
    }).fail(function(error) {
        alert(JSON.stringify(error));
    });
}
function init(){
    $.ajax({
        type : "GET",
        url : "/api/v1/planGole",
        dataType : "json",
        contentType : "application/json; charset=utf-8"
    }).done(function(data){
        console.log(data);
        for(i=0; i<data.length; i++){
            make_table(data[i]);
        }
    }).fail(function(error){
        alert(JSON.stringify(error));
    });
}

function make_table(data){
    var html = "";
    if( ($(".container .row").children().length - $(".w-100").length ) % 2 == 0 && $(".container .row").children().length > 0)
	    html = '<div class="w-100"></div>';

    var planList = data["planList"];
    for(idx=0; idx<8; idx++){
        if(planList[idx] == null) {
    	    planList[idx] = {"planSeq" : 0, "content" : ""};
        }
    }
    html += '<div class="col">'
            +'<table class="table table-bordered planGole_table" id="gole_'+data['goleSeq']+'">'
                +'<tr>'
                    +'<td><textarea id="plan_'+planList[0]['planSeq']+'" class="text_area plan_area" original="'+planList[0]['content']+'">'+planList[0]["content"]+'</textarea></td>'
                    +'<td><textarea id="plan_'+planList[1]['planSeq']+'" class="text_area plan_area">'+planList[1]["content"]+'</textarea></td>'
                    +'<td><textarea id="plan_'+planList[2]['planSeq']+'" class="text_area plan_area">'+planList[2]["content"]+'</textarea></td>'
                +'</tr>'
                +'<tr>'
                    +'<td><textarea id="plan_'+planList[3]['planSeq']+'" class="text_area plan_area">'+planList[3]["content"]+'</textarea></td>'
                    +'<td class="table-success gole_area" >'
                        +'<textarea class="table-success text_area">'+data['title']+'</textarea>'
                    +'</td>'
                    +'<td><textarea id="plan_'+planList[4]['planSeq']+'" class="text_area plan_area">'+planList[4]["content"]+'</textarea></td>'
                +'</tr>'
                +'<tr>'
                    +'<td><textarea id="plan_'+planList[5]['planSeq']+'" class="text_area plan_area">'+planList[5]["content"]+'</textarea></td>'
                    +'<td><textarea id="plan_'+planList[6]['planSeq']+'" class="text_area plan_area">'+planList[6]["content"]+'</textarea></td>'
                    +'<td><textarea id="plan_'+planList[7]['planSeq']+'" class="text_area plan_area">'+planList[7]["content"]+'</textarea></td>'
                +'</tr>'
            +'</table>'
            +'</div>';
    $(".container .row").append(html);
}