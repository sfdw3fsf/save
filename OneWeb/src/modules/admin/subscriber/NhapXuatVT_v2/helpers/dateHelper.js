import moment from 'moment'
export default {
  MinValue(format) {
    if (format == null) {
      format = 'DD/MM/YYYY HH:mm:ss'
    }
    return moment(new Date('0001-01-01T00:00:00Z')).format(format)
  },
  NgayCN(format) {
    if (format == null) {
      format = 'DD/MM/YYYY HH:mm:ss'
    }
    return moment().format(format)
  },
  ToDate(value, format) {
    if (format == null) {
      format = 'DD/MM/YYYY HH:mm:ss'
    }
    return moment(value, format).toDate();
  },
}
