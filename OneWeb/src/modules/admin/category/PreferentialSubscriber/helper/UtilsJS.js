import moment from 'moment'
export default {
  isNullOrEmpty(value) {
    if (value == undefined || value == null || value.trim() == '' || value == 'undefined' || value == 'null') return true
    return false
  },
  convertDateTimeToString(value) {
    if (value == undefined || value == null || value == '') return ''
    try {
      let date = value.getDate()
      if (date < 10) date = '0' + date
      let month = value.getMonth()
      if (month < 10) month = '0' + month
      let year = value.getFullYear()
      let hours = value.getHours()
      if (hours < 10) hours = '0' + hours
      let minutes = value.getMinutes()
      if (minutes < 10) minutes = '0' + minutes
      let sec = value.getSeconds()
      if (sec < 10) sec = '0' + sec
      return date + '/' + month + '/' + year + ' ' + hours + ':' + minutes + ':' + sec
    } catch (error) {
      return ''
    }
  },
  /* format currency VND muc cuoc tra them */
  formatCurrency(num) {
    if (parseInt(num) >= 0) {
      let currency = parseInt(num)
        .toFixed(2)
        .replace(/\d(?=(\d{3})+\.)/g, '$&,')
      return currency
    }
  },
  momentFormatStringify: (date) => {
    return date ? moment(date).format('MM/YYYY') : ''
  },
  momentFormatParse: (value) => {
    return value ? moment(value, 'MM/YYYY').toDate() : null
  },
  formatDateToOb: (date) => {
    if(!date){
      return null;
    }
    const dateStr = moment(date).format('MM/YYYY');
    const month = dateStr.slice(0, 2)
    const year = dateStr.slice(3, 7)
    const val = year + '' + month
    return {
      month: parseInt(month),
      year: parseInt(year),
      val
    }
  },
  convertMaskTextToDate(str){
    if (str.length === 6) {
      const valueMonthBD = str.slice(0, 2)
      const valueYearBD = str.slice(2, 6)
      return   valueYearBD + "" + valueMonthBD
    }
    return 0;
  }  
}
