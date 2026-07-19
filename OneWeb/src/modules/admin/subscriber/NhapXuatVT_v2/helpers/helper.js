import moment from 'moment'

export function onebssIdIsValid(id) {
  if (id == null) return false
  if (id <= 0) return false
  return true
}

export function isNullOrEmpty(input) {
  if (input == null) return true
  // const isArray = Array.isArray(input);
  if (input.length == 0) return true
  return false
}

export function getDateStringOrDefault(input, inputFormat, outputFormat) {
  if (inputFormat == null) {
    inputFormat = 'DD/MM/YYYY HH:mm:ss'
  }
  if (outputFormat == null) {
    outputFormat = 'DD/MM/YYYY'
  }
  if(moment(input, moment.ISO_8601).isValid()) {
    return moment(input).utc().format(outputFormat);
  }
  if(outputFormat == 'ISO') {
    return moment(input, inputFormat).toISOString(); 
  }
  
  return input != null && input != '' && input.length != 0 ? moment(input, inputFormat).format(outputFormat) : ''
}

export function convertToUpcaseKey(input) {
  let results = []
  for (let item of input) {
    let result = {}
    let keys = Object.keys(item)
    let total = keys.length
    let key = null
    for (let i = 0; i < total; i++) {
      key = keys[i]
      result[key.toUpperCase()] = item[key]
    }
    results.push(result)
  }
  return results
}

export function generateComponentKey() {
  let key = moment().valueOf()
  console.log('generateComponentKey')
  console.log(key)
  return key
}
