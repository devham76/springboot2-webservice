
var underpins_index = {
    init : function() {
        var _this = this;
        $('#btn-save').on('click', function(){
            deleteCookie("underpinsList");
            _this.save();
        });
        $('#btn-update').on('click', function () {
            deleteCookie("underpinsList");
            _this.update();
        });
        $('#btn-delete').on('click', function () {
            deleteCookie("underpinsList");
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
            // 쿠키 다시 세팅

            getUnderpinsList();
            alert('등록되었습니다.');

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
            // 쿠키 다시 세팅
            getUnderpinsList();
            alert('수정되었습니다.');

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

            // 쿠키 다시 세팅
            getUnderpinsList();
            alert('삭제되었습니다.');

            window.location.href = '/underpins';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
     }
};
underpins_index.init();

$(function(){
    // 적용 부분 체크아이콘으로 변경
    $("#underpinsList_table tr").each(function(){
    	var isAppend = $(this).find("td").eq(1);
	    if(isAppend.text()=="1")
	        isAppend.html("<i class='fas fa-check'></i>");
	    else
	        isAppend.html("");
    });
});