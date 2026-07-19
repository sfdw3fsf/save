export default {
  install(Vue) {
    Vue.prototype.$consts = {
      dateFormat: 'DD/MM/YYYY',
      dateFormatValue: 'DD/MM/YYYY',
      monthFormat: 'MM/YYYY',
      monthFormatValue: 'MMYYYY'
    }
  }
}
