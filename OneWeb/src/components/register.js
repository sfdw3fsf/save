import VueCard from './elements/VueCard'
import VueNav from './elements/VueNav'
import VueTable from './elements/VueTable'
import VueTableGroup from './elements/VueTableGroup'
import VueChechbox from './form/VueCheckbox'
import VueDate from './form/VueDate'
import VueInput from './form/VueInput'
import VuePaginate from './form/VuePaginate'
import VueSelect from './form/VueSelect'
import VueTextarea from './form/VueTextarea'
import VueRadio from './form/VueRadio'
import Vue from 'vue'
import gridview from './Shared/gridview'
import breadcrumb from './breadcrumb'

let components = {
  'vue-card': VueCard,
  'vue-nav': VueNav,
  'vue-table': VueTable,
  'vue-checkbox': VueChechbox,
  'vue-date': VueDate,
  'vue-input': VueInput,
  'vue-paginate': VuePaginate,
  'vue-select': VueSelect,
  'vue-textarea': VueTextarea,
  'vue-radio': VueRadio,
  'vue-table-group': VueTableGroup,
  'gridview': gridview,
  'breadcrumb': breadcrumb

}

Object.entries(components).forEach(([name, component]) => Vue.component(name, component))
