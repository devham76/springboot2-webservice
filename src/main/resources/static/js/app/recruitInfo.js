//set_recruit_info();
function set_recruit_info(){
// 크로스도메인.......ㅂㄷㅂㄷ....;;;;
    console.log("api call");
    var url_seoul = "http://api.career.co.kr/open?id=VjfzNMrb5o8yb/LgS8rsPQ==&jc=H002&ac1=1&ec=1";
    var url_kyengi = "http://api.career.co.kr/open?id=VjfzNMrb5o8yb/LgS8rsPQ==&jc=H002&ac1=3&ec=1";
        $.ajax({
            type: 'GET',
            url: 'http://api.career.co.kr/open?id=VjfzNMrb5o8yb/LgS8rsPQ==&jc=H002&ac1=1&ec=1',
            dataType: 'jsonp', jsonpCallback: "callback"
        }).done(function(data) {
            console.log(data);
            console.log(typeof(data));
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
}