init();

function recruitInfo_modal() {
     $("#recruitInfo_modal").modal('show');
}

// 채용 정보 url
function init() {
    var array = [
         ["주니어 개발자 채용 정보" , "https://github.com/jojoldu/junior-recruit-scheduler"]
        ,["잡플래닛 채용 정보", "https://www.jobplanet.co.kr/job_postings/search?utf8=%E2%9C%93&jp_min_overall=2.0&recruitment_type_ids%5B%5D=1&recruitment_type_ids%5B%5D=4&city_ids%5B%5D=1&city_ids%5B%5D=2&occupation_level2_ids%5B%5D=11604&order_by=score"]
        ,["링커리어 채용 정보", "https://linkareer.com/list/recruit?activityOrderDirection=DESC&activityOrderField=RECENT&activityTypeID=5&categoryIDs[0]=58&jobTypes[0]=NEW&jobTypes[1]=INTERN&page=1&regionIDs[0]=2&regionIDs[1]=9"]
        ,["네이버 인턴 채용", "https://recruit.navercorp.com/naver/job/list/developer?searchSysComCd=&entTypeCd=004&searchTxt="]
        ,["라인 플러스 채용", "https://recruit.linepluscorp.com/lineplus/career/list?classId=148"]
        ,["NHN 채용 정보", "https://recruit.nhn.com/ent/recruitings?type=class"]
        ,["카카오 인턴 채용", "https://careers.kakao.com/jobs?employeeType=&keyword=%EC%9D%B8%ED%84%B4&page=1&part=TECHNOLOGY&skilset=&company=KAKAO"]
        ,["카카오 엔터프라이즈", "https://kakaoenterprise.recruiter.co.kr/app/jobnotice/list"]
     ];

    var html = "";
    for(idx in array){
        html += "<tr><td><a href='"+array[idx][1]+"'>"+array[idx][0]+"</td></tr>";
    }
    console.log("html");
    $("#recruitInfo_modal_table").html(html);

}