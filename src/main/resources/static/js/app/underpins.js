
var underpins_index = {
    init : function() {
        var _this = this;
        $('#btn-save').on('click', function(){
            _this.save();
        });
        $('#btn-update').on('click', function () {
            _this.update();
        });
        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },
    save: function() {
        var data = {
            writer: $('#writer').val(),
            content: $('#content').val(),
            isAppend : $("input[name='isAppend']:checked"). val()
        };
        console.log(JSON.stringify(data));
        $.ajax({
            type: 'POST',
            url: '/api/v1/underpins',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('등록되었습니다.');
            // 쿠키 다시 세팅
            deleteCookie("underpinsList");
            underpinsCookieInit();

            window.location.href = '/underpins';
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        var data = {
            writer: $('#writer').val(),
            content: $('#content').val(),
            isAppend : $("input[name='isAppend']:checked"). val()
        };

        var id = $('#id').val();
        $.ajax({
            type: 'PUT',
            url: '/api/v1/underpins/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('수정되었습니다.');
            // 쿠키 다시 세팅
            deleteCookie("underpinsList");
            underpinsCookieInit();

            window.location.href = '/underpins';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
     },
     delete:function(){
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/underpins/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('삭제되었습니다.');
            // 쿠키 다시 세팅
            deleteCookie("underpinsList");
            underpinsCookieInit();

            window.location.href = '/underpins';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
     }
};
underpins_index.init();

$(function(){

    $("#underpinsList_table tr").each(function(){
    	var isAppend = $(this).find("td").eq(1);
	    if(isAppend.text()=="1")
	        isAppend.html("<i class='fas fa-check'></i>");
	    else
	        isAppend.html("");
    });
});