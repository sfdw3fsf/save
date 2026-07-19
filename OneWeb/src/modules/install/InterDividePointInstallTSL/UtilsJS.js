export default{
  isNullOrEmpty(value){
    if(value == undefined || value == null || value.trim() == '' || value == 'undefined' || value == 'null')
      return true;
    return false;
  },
  convertDateTimeToString(value){
    if(value == undefined || value == null || value == '')
    return '';
    try {
      let date = value.getDate();
      if(date < 10)
        date = '0' + date;
      let month = value.getMonth();
      if (month < 10)
        month = '0' + month;
      let year = value.getFullYear();
      let hours = value.getHours();
      if (hours < 10)
        hours = '0' + hours;
      let minutes = value.getMinutes();
      if (minutes < 10)
        minutes = '0' + minutes;
      let sec = value.getSeconds();
      if (sec < 10)
        sec = '0' + sec;
      return date + '/' + month + '/' + year + ' ' + hours + ':' + minutes + ':' + sec;
    } catch (error) {
      return '';
    }
  }
}
