
var today = new Date();
var dayWeek = today.getDay();	// 요일
var day = today.getDate();
var month = today.getMonth()
var year = today.getFullYear();
var date = makeDate(year,month, day, 1);
$(function () {

    $("#my-calendar").zabuto_calendar({
        year: year,
        month: month+1,
        today: true,
        nav_icon: {
              prev: '<i class="fa fa-chevron-circle-left"></i>',
              next: '<i class="fa fa-chevron-circle-right"></i>'
            },
        legend: [
            {type: "text", label: "Bad"},
            {type: "list", list: ["grade-1", "grade-2", "grade-3", "grade-4", "grade-5"]},
            {type: "text", label: "Good"}
        ]
        // 날짜클릭시
        ,action: function () {
            return myDateFunction(this.id);
        },
        // month 이동 클릭시
        action_nav: function () {
            return myNavFunction(this.id);
        }
        ,ajax: { url: "/api/v1/recordsForMark" }
        // {date: yyyy-mm-dd, badge: boolean, title: string, body: string, footer: string, classname: string}
    });
    changeSelectedDate(date);

    // 날짜 클릭시
    function myDateFunction(id, fromModal) {
        var date = $("#" + id).data("date");
        changeSelectedDate(date+" 00:00:00");
        return true;
    }

        function myNavFunction(id) {
            $("#date-popover").hide();
            var nav = $("#" + id).data("navigation");
            var to = $("#" + id).data("to");
            console.log( to.month + '/' + to.year);
        }


});
    // 날짜 형식에 맞춰 만들기
    function makeDate(year,month, day, useTime){
        month = month+1;
        month = month<10 ? "0"+month : month;
        day   = day < 10 ? "0"+day   : day;
        var time = useTime == 1 ? " 00:00:00" : "";
        var date = year+"-"+month+"-"+day+ time;
        return date;
    }
    // 테이블에 선택한 데이터 뿌려주기
    function appendWeeklyData(data){
        console.log("weekly_data");
        console.log(data);
        var array = Object.values(data);    // 배열로 변환

        var head_html = "<tr style='height: fit-content;'>";
        var table_html = "<tr>";
        var dateOfWeekArr = ["월","화","수","목","금","토","일"];
        for(i=0; i<7; i++){
            var content = array[i]["content"];
            var r_id = array[i]["id"];
            var hour = array[i]["hour"];
            var minute = array[i]["minute"];
            var recordDate = array[i]["recordDate"].substring(0,10);
            recordDate = new Date(recordDate);
            if(r_id == null) {
                recordDate.setDate(recordDate.getDate() + 1);
            }
            var r_day = recordDate.getDate();
            var r_month = recordDate.getMonth()
            var r_year = recordDate.getFullYear();
            var r_date = makeDate(r_year, r_month, r_day, 0);

            head_html += "<td align=center>"+dateOfWeekArr[i]
                        + "<span class='edit_icon' onclick='showModal(\""+i+"\")' style='padding-left:5px;cursor: pointer;' >"
                        + "<i class='far fa-edit'></i></span>"
                        + "</td>";

            table_html += "<td class='day_record' id='content_"+i+"' style='width: 14%;padding: 0.1em;' >"
                                +"<input type='hidden' name='r_id' value='"+r_id+"'>"
                                +"<input type='hidden' name='recordDate' value='"+r_date+"'>"
                                +"<input type='hidden' name='hour'       value='"+hour+"'>"
                                +"<input type='hidden' name='minute'     value='"+minute+"'>"
                                +"<input type='hidden' name='content'    value='"+content+"'>"
                                +"<div class='day_date'>" + r_date + "</div>"
                                +"<div class='day_time'>" + hour + "시간 " + minute + "분</div>"
                                +"<div class='day_content'>" +content + "</div>"
                          +"</td>";

        }
        head_html += "</tr>";
        table_html += "</tr>";

        $("#weekly_table *").remove();
        $("#weekly_table").append(head_html + table_html);

    }
    // 날짜 클릭시
    function changeSelectedDate(selectedDate){
        console.log("selectedDate :"+selectedDate);
        var data = {
            selectedDate: selectedDate
        };

         $.ajax({
            type: 'POST',
            url: '/api/v1/records',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
            }).done(function(data) {
                console.log(data);
                appendWeeklyData(data); // 테이블에 추가
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });

    }
        // 수정 모달
    function showModal(idx){
            var r_id = $("#content_"+idx).children('[name=r_id]').val();
            var recordDate = $("#content_"+idx).children('[name=recordDate]').val();
            var content = $("#content_"+idx).children('[name=content]').val();
            var hour = $("#content_"+idx).children('[name=hour]').val();
            var minute = $("#content_"+idx).children('[name=minute]').val();
            $("#edit_modal_title").html(recordDate);

            $("#modal_id").val(r_id);
            $("#modal_hour").val(hour);
            $("#modal_minute").val(minute);
            $("#modal_content").val(content);

            $("#edit_modal").modal('show');
    }

    // 저장하기
    function modal_save() {
        var data = {
            hour: $('#modal_hour').val(),
            minute: $('#modal_minute').val(),
            content : $('#modal_content').val(),
            recordDate : $("#edit_modal_title").html()
        };

        var id = $("#modal_id").val();
        var url = (id == "null") ? '/api/v1/recordSave' : '/api/v1/recordUpdate/'+id;
        $.ajax({
            type: 'POST',
            url: url,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            window.location.href = '/weekly';
            //alert("등록되었습니다");
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    }