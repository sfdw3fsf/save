export default  {
    isNullOrEmpty(value) {
        return value === null || value === ''
      },
      dateToString(value, format = 'DD/MM/YYYY') {
        return moment(value).format(format)
      },
      stringToDate(value, format = 'DD/MM/YYYY') {
        return moment(value, format).toDate()
      },
      setFirstValue(cbo, key) {
        if (cbo.length) {
          return cbo[0][key]
        } else {
          return ''
        }
      },
}