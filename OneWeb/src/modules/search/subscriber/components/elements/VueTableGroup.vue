<template>
  <div>
    <div class="table-content">
      <table class="table-result table-gachle">
        <thead class="center">
          <tr>
            <th class="w20"></th>
            <th v-bind:key="'col1' + i" v-for="(item, i) in config">{{item.label}}</th>
          </tr>
          <tr>
            <th class="w20"></th>
            <th v-bind:key="'col2' + i" v-for="(item, i) in config">
              <div v-if="item.filterAble" class="input-icon-right">
                <input @input="filterItem($event, item.key)" type="text" class="form-control" />
                <span class="icon nc-icon-outline ui-1_zoom"></span>
              </div>
            </th>
          </tr>
        </thead>
        <tbody>
          <template v-if="!loading">
            <template v-for="(groupItem, gj) in options_perpage" >
              <tr class="fw6 tree-open" v-bind:key="'gr4' + gj">
                <td :colspan="config.length + 1">
                    <div class="tree-arrow"></div> {{groupItem.label}}
                </td>
              </tr>
              <tr v-bind:key="'col4' + j + gj" v-for="(option, j) in groupItem.data" @click="chooseItem(option[unique], option)" :class="compare(option,value) ? 'highlight' : ''">
                <td class="w20 center">
                  <span v-if="compare(option,value)" class="fa fa-play text-main"></span>
                </td>
                <td v-bind:key="'col3' + i" v-for="(item, i) in config">
                  {{option[item.key]}}
                </td>
              </tr>
            </template>
          </template>
          <template v-else>
          <tr class="text-center">
            <td colspan="75%">
              <b-spinner class="mt-3 mb-3" variant="primary" label="Spinning"></b-spinner>
            </td>
          </tr>
          </template>
        </tbody>
      </table>
      <vue-paginate v-model="page" :per_page="10" :total="temp_options.length"></vue-paginate>
    </div>
  </div>
</template>
<script>
import VuePaginate from '../form/VuePaginate.vue'
export default {
  data: () => ({
    page: 1,
    current: 0,
    temp_options: []
  }),
  props: {
    value: Object,
    options: Array,
    config: Array,
    unique: String,
    loading: Boolean,
    groupField: String,
    groupLabel: String,
    perpage: Number
  },
  components: {
    VuePaginate
  },
  mounted() {
    this.temp_options = this.options
  },
  watch: {
    options: {
      handler(val) {
        this.temp_options = val
      },
      deep: true
    }
  },
  computed: {
    numPerPage() {
      return this.perpage ? this.perpage : 10
    },
    options_perpage() {
      var data = this.temp_options.slice((this.page - 1) * this.numPerPage, this.page * this.numPerPage)
      return this.reOptions(data)
    },
  },
  methods: {
    filterItem (event, key) {
      let text = event.target ? event.target.value : event
      text = text.toLowerCase()
      this.page = 1
      this.temp_options = this.options.filter(option => {
        if (option[key]) {
          return option[key].toLowerCase().includes(text)
        }
      })
    },
    chooseItem(index, option) {
      this.current = index
      this.$emit('input', option)
    },
    reOptions(data) {
      var reOptions = {}
      data.forEach(item => {
        if(item[this.groupField]) {
          if(!reOptions[item[this.groupField]]) {
            reOptions[item[this.groupField]] = {
              data: [],
              label: item[this.groupLabel]
            }
          }
          if(reOptions[item[this.groupField]].data) {
            reOptions[item[this.groupField]].data.push(item)
          }
        }
      })

      return reOptions
    },
    compare(a,b) {
      for(let i in a) {
        if(i !== 'listItem') {
          if(!b) {
            return false
          } else {
            if(a[i] != b[i]) {
              return false
            }
          }
        }
      }
      return true
    }
  }
}
</script>
