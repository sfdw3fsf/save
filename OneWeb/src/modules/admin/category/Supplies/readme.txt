Modules

VClickOutside: https://github.com/ndelvalle/v-click-outside

index.js
import directive from './v-click-outside'
const plugin = {
  install(Vue) {
    Vue.directive('click-outside', directive)
  },
  directive,
}
export default plugin
