$(function(){
    init();
    // 계획 입력,수정
    $(document).on("focusout",".plan_area",function(){
        var new_plan = $(this).val();
        var original = $(this).attr("original");
        // 목표아이디
        var gole_id = $(this).parent().parent().parent().parent().attr("id");
        var split_arr = gole_id.split("_");
        var gole_id = split_arr[1];
        // 계획 아이디
        var this_id = $(this).attr("id");
        split_arr = this_id.split("_");
        var plan_id = split_arr[1];

        if(original != new_plan){
            console.log(original+" ---> "+new_plan);
            plan_save(new_plan, plan_id, gole_id);
        }
    });
    $(document).on("mouseover",".gole_td",function(){
        $( this ).find(".delete_gole").css( "display", "unset" );
    });
    $(document).on("mouseout",".gole_td",function(){
        $( this ).find(".delete_gole").css( "display", "none" );
    });
    $(document).on("focusout",".gole_area",function(){
            var new_gole = $(this).val();
            var original = $(this).attr("original");
            // 목표아이디
            var gole_id = $(this).parent().parent().parent().parent().attr("id");
            var split_arr = gole_id.split("_");
            var gole_id = split_arr[1];

            if(original != new_plan){
                gole_update(new_gole, gole_id);
            }
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
function plan_save(content, plan_id, gole_id ){
    var data = {
        content : content
        , goleSeq : gole_id
    };
    console.log("plan_save ...");
    console.log(data);
    if ( plan_id == 0 ){    // 삽입
        var type = "POST";
        var url = "plan/save";
    } else {    // 수정
        var type = "PUT";
        var url = "plan/update/"+plan_id;
    }
    $.ajax({
        type: type,
        url: "/api/v1/"+url,
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
function gole_update(title, gole_id ){
    var data = {
        title : title
    };
    $.ajax({
        type: "PUT",
        url: "/api/v1/gole/update/"+gole_id
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
        console.log(error);
        alert(JSON.stringify(error));
    });
}
function delete_gole(gole_id){
    $.ajax({
        type : "DELETE",
        url : "/api/v1/gole/delete/"+gole_id,
        dataType : "json",
        contentType : "application/json; charset=utf-8"
    }).done(function(){
        window.location.href = '/planGole';
    }).fail(function(error){
        console.log("[delete_gole]="+error);
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
                    +'<td><textarea id="plan_'+planList[1]['planSeq']+'" class="text_area plan_area" original="'+planList[1]['content']+'">'+planList[1]["content"]+'</textarea></td>'
                    +'<td><textarea id="plan_'+planList[2]['planSeq']+'" class="text_area plan_area" original="'+planList[2]['content']+'">'+planList[2]["content"]+'</textarea></td>'
                +'</tr>'
                +'<tr>'
                    +'<td><textarea id="plan_'+planList[3]['planSeq']+'" class="text_area plan_area" original="'+planList[3]['content']+'">'+planList[3]["content"]+'</textarea></td>'
                    +'<td class="table-success gole_td" >'
                        +'<div class="delete_gole" onclick="delete_gole('+data['goleSeq']+')"><i class="fas fa-minus-circle" aria-hidden="true"></i></div>'
                        +'<textarea class="table-success text_area gole_area"  original="'+data['title']+'">'+data['title']+'</textarea>'
                    +'</td>'
                    +'<td><textarea id="plan_'+planList[4]['planSeq']+'" class="text_area plan_area" original="'+planList[4]['content']+'">'+planList[4]["content"]+'</textarea></td>'
                +'</tr>'
                +'<tr>'
                    +'<td><textarea id="plan_'+planList[5]['planSeq']+'" class="text_area plan_area" original="'+planList[5]['content']+'">'+planList[5]["content"]+'</textarea></td>'
                    +'<td><textarea id="plan_'+planList[6]['planSeq']+'" class="text_area plan_area" original="'+planList[6]['content']+'">'+planList[6]["content"]+'</textarea></td>'
                    +'<td><textarea id="plan_'+planList[7]['planSeq']+'" class="text_area plan_area" original="'+planList[7]['content']+'">'+planList[7]["content"]+'</textarea></td>'
                +'</tr>'
            +'</table>'
            +'</div>';
    $(".container .row").append(html);
}