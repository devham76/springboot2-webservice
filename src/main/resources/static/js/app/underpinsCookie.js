/*
* 쿠키에 응원글이 있는지 확인한다
* 없으면 불러와서 쿠키에 저장한다

* 응원글을 불러와서 header 응원글에 넣어준다
*/
var pathname = window.location.pathname;
pathname = pathname.substring(1);
if(pathname != "loginView")
    underpinsCookieInit();
else    // 로그인이 안되어있으면 삭제
    deleteCookie("underpinsList");
// 쿠키생성
function setCookie(cookieName, expireDay, value) {
    console.log("[set cookie]... ");

    // 값이 없으면 종료
    if(!value)
        return;

    var day = new Date();
    day.setTime(day.getTime() + (expireDay * 24*60*60*1000));   // 하루
    var expires = "expires="+day.toUTCString();
    document.cookie = cookieName + "=" + value + "; " + expires + ";path=/";
}
//
function underpinsCookieInit() {
    var underpinsList = getCookie("underpinsList");

    // 쿠키가 없다면
    if (!underpinsList) {
        console.log("[cookie does not exists]... ");
        getUnderpinsList(); // 데이터 검색후 세팅
    }
    else {
        console.log("[cookie exists]... ");
    // 쿠키 생성후 헤더 변경
        var step1 = underpinsList.split(",");
        var underpins = {};
        for( var i in step1 ){
            var step2 = step1[i].split(":");
            underpins[step2[0]] = step2[1];
        }
        var html = "";
        for(var i in underpins) {
            html += "<li>"+underpins[i]+"</li>";
        }
        console.log(html);
        $("#underpins_ul").append(html);

    }

}
 /**
  * 쿠키값 추출
  * @param cookieName 쿠키명
  */

function getCookie (cookieName){
    var name = cookieName + "=";
    var cookieArr = document.cookie.split(';');

    var cookieValue;
     for(var i = 0; i < cookieArr.length; i++) {
        var cookie = cookieArr[i].trim();
        if (cookie.indexOf(name) == 0) {
            cookieValue = cookie.substring(name.length, cookie.length);
        }
      }
     return cookieValue;
  }
 function getCookie_( cookieName ) {
    var search = cookieName + "=";
    var cookie = document.cookie;

    // 현재 쿠키가 존재할 경우
    if( cookie.length > 0 ) {
        // 해당 쿠키명이 존재하는지 검색한 후 존재하면 위치를 리턴.
        startIndex = cookie.indexOf( cookieName );

        // 쿠키속에 검색할 쿠키가 존재
        if( startIndex != -1 ) {
            // 값을 얻어내기 위해 시작 인덱스 조절
            startIndex += cookieName.length;
            //console.log("2startIndex =" +startIndex);
            // 값을 얻어내기 위해 종료 인덱스 추출
            endIndex = cookie.indexOf( ";", startIndex );

            // 만약 종료 인덱스를 못찾게 되면 쿠키 전체길이로 설정
            if( endIndex == -1) endIndex = cookie.length;

            // 쿠키값을 추출하여 리턴
            return unescape( cookie.substring( startIndex + 1, endIndex ) );
        }
        // 쿠키 내에 해당 쿠키가 존재하지 않을 경우
        else {
            return false;
        }
    }
     // 쿠키 자체가 없을 경우
    else {
        return false;
    }
 }
  /**
   * 쿠키 삭제
   * @param cookieName 삭제할 쿠키명
   */
function deleteCookie( cookieName ) {
    console.log("deleteCookie...");
    var expireDate = new Date();
    //어제 날짜를 쿠키 소멸 날짜로 설정한다.

    expireDate.setDate( expireDate.getDate() - 1 );
    console.log(cookieName + "= " + "; expires=" + expireDate.toGMTString() + "; path=/");
    document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString() + "; path=/";
}
// 서버에 응원글 요청
function getUnderpinsList() {
    console.log("getUnderpinsList start...");

    $.ajax({
        type: 'GET',
        url: '/api/v1/underpins',
        dataType: 'json',
        contentType:'application/json; charset=utf-8'
    }).done(function(data) {
        console.log(data);

        var cookieVal = "";
        var html = "";
        for(i=0; i<data.length; i++){
            html += "<li class='user'>"+data[i]["content"]+"</li>";

            if(cookieVal != "" ) cookieVal += ",";
            cookieVal += i+":"+data[i]["content"];
        }
        console.log(html +" ,cookieval=   "+ cookieVal);
         // 화면, 응원글 추가
        $("#underpins_ul .user").remove();
        $("#underpins_ul").append(html);
        // 쿠키설정
        setCookie("underpinsList", 1, cookieVal );
    }).fail(function(error) {
        alert(JSON.stringify(error));
    });
}


