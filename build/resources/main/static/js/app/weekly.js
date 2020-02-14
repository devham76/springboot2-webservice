
mobiscroll.settings = {
    theme: 'ios',
    themeVariant: 'light'
};


$(function () {

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
});
