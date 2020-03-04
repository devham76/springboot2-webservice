
    $(function() {
        set_title();
        // 사이드바
        $('.nav li').click(function(){
            $('.nav li').removeClass('active');
            $(this).addClass('active');
        });
        //응원글 흐르기
        $('.tlt').textillate({
            loop: true,
              // out animation settings.
            in: {
                effect: 'fadeIn',
                delayScale: 1.5,
                delay: 70,
                sync: false,
                shuffle: false,
                reverse: false
            },
            out: {
                effect: 'fadeOut',
                delayScale: 1.5,
                delay: 70,
                sync: false,
                shuffle: false,
                reverse: false
            }
        });
    });

    function set_title() {
        var pathname = window.location.pathname;
        pathname = pathname.substring(1);
        var data = {
              "posts" : ["일지", "오늘 하루는 어땠나요? 오늘의 하루를 기록해주세요"]
            , "weekly" : ["공부기록", "매일 공부한 내용을 기록해주세요"]
            , "planGole" : ["목표설정", "목표를 설정하고 계획을 세워보세요"]
            , "recruitInfo" : ["채용정보", "서울,경기 지역의 개발자 신입,인턴 채용공고를 확인해보세요 !"]
            , "jobCalendar" : ["채용달력", "이달의 대졸 공채 소식을 놓치지 마세요 !"]
            , "underpins" : ["응원 글", "매일 애쓰는 나를 위해 응원해주세요"]
        };

        $("#main_title").text(data[pathname][0]);
        $("#sub_title").text(data[pathname][1]);
    }
/*
    // mustache js 이용해볼것
    // 페이지 마다 제목과 부제목이 다르다
    function set_title() {

        var pathname = window.location.pathname;
        pathname = pathname.substring(1);
	    var template = $('#title_mustache').html();
	    Mustache.parse(template);

	    var data = {
	    	main_title: "일지",
		    sub_title: "오늘 하루는 어땠나요? 오늘도 수고했어요"
	    };

	    var rendered = Mustache.render(template, data);
	    $('#title_div').html(rendered);

    }
*/