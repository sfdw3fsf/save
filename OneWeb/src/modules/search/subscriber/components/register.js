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
import VueBreadcrumb  from './elements/VueBreadcrumb'
import VueTree  from './elements/VueTree'
import VueTreeItem  from './elements/VueTreeItem'
import VueElement from './elements/VueElement'
import Vue from 'vue'

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
    'vue-bread-crumb' : VueBreadcrumb,
    'vue-tree': VueTree,
    'vue-tree-item': VueTreeItem,
    'vue-element': VueElement
}

Object.entries(components).forEach(([name, component]) => Vue.component(name, component))
