/*
mobiscroll.settings = {
    theme: 'ios',
    themeVariant: 'light'
};
*/

$(function () {

            var today = new Date();
            var dayWeek = today.getDay();	// 요일
            var day = today.getDate();
            var month = today.getMonth()
            var year = today.getFullYear();
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
    });
    /*
    var eventData = [
      {"date":"2015-01-01","badge":false,"title":"Example 1"},
      {"date":"2015-01-02","badge":true,"title":"Example 2"}
    ];
    $(document).ready(function () {
      $("#my-calendar").zabuto_calendar({
        data: eventData
      });
    });
/*
    var fromMonday = [],
        fromSaturday = [],
        twoWeeks = [],
        i = 0,
        j = 0;

    for (i; i < 7; i++) {
        fromMonday.push(new Date(2018, 0, 8 + i));
        fromSaturday.push(new Date(2018, 0, 6 + i));
    }

    for (j; j < 14; j++) {
        twoWeeks.push(new Date(2018, 0, 8 + j));
    }

    $('#demo-mon-sun').mobiscroll().calendar({
        display: 'inline',
        selectType: 'week',
        defaultValue: fromMonday,
        firstSelectDay: 1,
        firstDay: 1
    });

    $('#demo-sat-fri').mobiscroll().calendar({
        display: 'inline',
        selectType: 'week',
        defaultValue: fromSaturday,
        firstSelectDay: 6,
        firstDay: 1
    });

    $('#demo-multi').mobiscroll().calendar({
        display: 'inline',
        selectType: 'week',
        defaultValue: twoWeeks,
        firstSelectDay: 1,
        firstDay: 1,
        select: 'multiple'
    });
    */
});
