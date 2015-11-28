var DateUtils = {
parserNumberToFormatDate: function (number,format) {
	if(format==='yyyy-MM-dd'){/*yyyy-MM-dd*/
		return DateUtils.formatDate(new Date(parseInt(number)));	
	}else if(format==='yyyy-MM-dd HH:mm:ss'){/*yyyy-MM-dd HH:mm:ss*/
		return DateUtils.formatDateTime(new Date(parseInt(number)));
	}
},
formatDate: function formatDate(now) {
    var month = now.getMonth() + 1, day = now.getDate();
    if (month < 10) {
        month = "0" + month;
    }
    if (day < 10) {
        day = "0" + day;
    }
    return now.getFullYear() + "-" + month + "-" + day;
}, formatDateTime: function formatDate(now) {
    var month = now.getMonth() + 1, day = now.getDate(), hour = now.getHours(), minutes = now.getMinutes(), seconds = now.getSeconds();
    if (month < 10) {
        month = "0" + month;
    }
    if (day < 10) {
        day = "0" + day;
    }
    if (hour < 10) {
        hour = "0" + hour;
    }
    if (minutes < 10) {
        minutes = "0" + minutes;
    }
    if (seconds < 10) {
        seconds = "0" + seconds;
    }
    return now.getFullYear() + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;
},
compareTo:function(srcDateTime,toDateTime){
    var srcTimes=parseInt(srcDateTime.replace(/ |-|:/g,""));
    var toTimes=parseInt(toDateTime.replace(/ |-|:/g,""));
    if(srcTimes>toTimes){
        return -1;
    }else if(toTimes>srcTimes) {
        return 1;
    }else if(srcTimes==toTimes){
        return 0;
    }else{
        return 'ERROR';
    }
}};