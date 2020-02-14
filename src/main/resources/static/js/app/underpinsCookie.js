/*
* 쿠키에 응원글이 있는지 확인한다
* 없으면 불러와서 쿠키에 저장한다

* 응원글을 불러와서 header 응원글에 넣어준다
*/
// deleteCookie("underpinsList");  // test완료후삭제해야함
underpinsCookieInit();
// 쿠키생성
function setCookie(cookieName, expireDay, value) {
    console.log("[set cookie]... ");

    console.log("type of value : "+ typeof(value));
    // 값이 없으면 종료
    if(!value)
        return;

    if(typeof(value) == "object")
        value = JSON.stringify(value);    // array to string(json)

    var day = new Date();
    day.setTime(day.getTime() + (expireDay * 24*60*60*1000));   // 하루
    var expires = "expires="+day.toUTCString();
    document.cookie = cookieName + "=" + value + "; " + expires;
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

        console.log("[cookie]... ");
        console.log(typeof(underpinsList));
        console.log(underpinsList);
        var html = "";
        var arr = JSON.parse(underpinsList);    // string to object
        for(i=0; i<arr.length; i++) {
            html += "<li>"+arr[i]+"</li>";
        }
        console.log(html);
        $("#underpins_ul").append(html);

    }

}
 /**
  * 쿠키값 추출
  * @param cookieName 쿠키명
  */
 function getCookie( cookieName ) {
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
    document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString() + "; path=/";
}

// 서버에 응원글 요청
function getUnderpinsList() {
    console.log("getUnderpinsList start...");

    $.ajax({
        type: 'GET',
        url: '/api/v1/underpins',
        dataType: 'json',
        data: { userId: "test" },
        contentType:'application/json; charset=utf-8'
    }).done(function(data) {
        console.log(data);

        var resultArr = [];
        var html = "";
        for(i=0; i<data.length; i++){
            html += "<li>"+data[i]["content"]+"</li>";
            resultArr.push(data[i]["content"]);
        }
        console.log(html);
        // 화면, 응원글 추가
        //$("#underpins_ul").append(html);
        // 쿠키설정
        setCookie("underpinsList", 1, resultArr);
    }).fail(function(error) {
        alert(JSON.stringify(error));
    });
}