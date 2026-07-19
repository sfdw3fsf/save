import moment from 'moment'

// dang
export function reformatDateTime (dateTime) {
  return moment(dateTime, 'YYYY-MM-DD hh:mm:ss').format('DD/MM/YYYY hh:mm:ss')
}

export function reformatDateTimeFromAPI (dateTime) {
  return moment(dateTime, 'DD/MM/YYYY hh:mm:ss').format('DD/MM/YYYY hh:mm:ss')
}

export function getDateFromString (date) {
  return moment(date, 'DD/MM/YYYY').toDate()
}

export function stringToDate (str) {
  return moment(str, 'DD/MM/YYYY HH:mm:ss').toDate()
}

export function formatDateToParam (date) {
  return moment(date).format('DD/MM/YYYY HH:mm:ss')
}

export function toDay () {
  return moment().format('DD/MM/YYYY')
}
