<template src="./index.template.html"></template>
<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import { getterName, actionName, statePropertyName } from './../store'

import EventBus from '@/utils/eventBus'
import Vue from 'vue'
import DynamicMarker from '@/modules/CABMAN/BanDoRanh/components/DynamicMarker'
const DynamicMarkerConstructor = Vue.extend(DynamicMarker)
export default {
  name: 'leftPaneContent',
  components: {},
  computed: {
    ...mapState('cabman/BanDoRanh', statePropertyName),
    ...mapState('cabman', ['BanDoRanh']),
    ...mapGetters('cabman/BanDoRanh', getterName),
  },
  data () {
    return {
      dsIdChon : [],
      checkAll: false,
      message: null,
      lock_events: false,
      selectedTTVT: {
        DONVI_ID: null,
        TEN_DV: null
      },
      selectedTRAM: {
        DONVI_ID: null,
        TEN_DV: null
      },
      pageSettings: { pageSize: 20 },
      customAttributes: {class: 'customcss'},
      getColumnTemplate(parent) {
         return function() {
                return {
                    template: {
                        template: `<div class="image">
                                      <img :src="image"/>
                                      {{this.data.MA_TRAM}}
                                  </div>`,
                        data() {
                            return {
                                parent: parent,
                                data: { }
                            }
                        },
                        computed: {
                          image: function() {
                            let icon=parent.getDynamicMarkerIcon('iconDiemUon', this.data.STYLE_FILL)
                            return icon
                          },

                        }
                    }
                }
            }
      },
    }
  },
  provide: {

  },
  methods: {
    ...mapActions('cabman/BanDoRanh', actionName),
    getDynamicMarkerIcon (text, color) {
      const iconComponent = new DynamicMarkerConstructor({
        propsData: {
          text, color
        }
      })
      iconComponent.$mount()
      const iconDom = iconComponent.$el
      const iconString = new XMLSerializer().serializeToString(iconDom)
      return 'data:image/svg+xml;charset=UTF-8;base64,' + btoa(iconString)
    },
    async onDoubleClick (args) {
      try {
        // var rowData = args.rowData
        // this.set_cap_goc(rowData).then(async () => {
        //   this.setState({key: 'IsLoadNewGraph', value: true})
        //   await this.napGianDo_v2(null, null)
        // })
      } catch (error) {
        this.$root.$toast.error('Lỗi thực hiện nạp dữ liệu')
      }
    },
    async loadData () {
      try {
        this.$root.showLoading(true)
        await this.api_get_ds_ttvt(this.$root.token.getPhanVungID())
        if (this.ds_ttvt.length > 0) {
          this.selectedTTVT = this.ds_ttvt[0]
          this.setState({key: 'ttvt_id', value: this.ds_ttvt[0].DONVI_ID})
        } else {
          this.selectedTTVT.DONVI_ID = null
          this.selectedTTVT.TEN_DV = null
        }
        this.$root.showLoading(false)
      } catch (error) {
        this.$root.showLoading(false)
        console.log('🚀 ~ loadData ~ error:', error)
        this.$root.$toast.error('Lỗi thực hiện nạp dữ liệu')
      }
    },
    async getDsRanhTram () {
      try {
        this.checkAll=false
        this.dsIdChon = []
        this.$root.showLoading(true)
        await this.api_get_ds_ranhtram(this.selectedTTVT.DONVI_ID)
        if (this.ds_tram.length > 0) {
          this.selectedTRAM = this.ds_tram[0]
          this.setState({key: 'tramvt_id', value: this.ds_tram[0].DONVI_ID})
          this.checkAll=true
        } else {
          this.selectedTRAM.DONVI_ID = null
          this.selectedTRAM.TEN_DV = null
        }
        EventBus.$emit('VE_RANH_TTVT2', this.ds_tram)
        this.$root.showLoading(false)
      } catch (error) {
        this.$root.showLoading(false)
        this.$root.$toast.error('Lỗi thực hiện nạp dữ liệu')
      }
    },
    gridRanhTram_rowSelected (data) {
    },
    gridRanhTram_created(e){
      const gridRanhTram = document.getElementById("gridRanhTram")
      if (gridRanhTram)
      {
        gridRanhTram.addEventListener('mouseover', (event) =>this.mouseoverEvent(event))
      }
    },
    mouseleaveEvent(e) {
      const targetElement = e.target
      targetElement.removeEventListener('mouseleave', this.mouseleaveEvent);
      if (targetElement.classList.contains('e-rowcell')) {
        if (targetElement.ej2_instances && targetElement.ej2_instances[0]){return;}
        const currentRow = targetElement.parentElement;
        const data_uid=currentRow.getAttribute('data-uid')
        const data_row=  this.$refs.gridRanhTram.$refs.grid.ej2Instances.getRowObjectFromUID(data_uid).data
        EventBus.$emit('VE_RANH_SET_DEACTIVE', data_row)
      }
    },
    getColumnTemplateChonNhieu (parent) {
      return function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="check"
                                    v-model="parent.dsIdChon"
                                    :value="data.DONVI_ID + '_' + data.MA_TRAM"/>
                                <span class="name"></span>
                            </div>
                        `,
            data () {
              return {
                parent: parent,
                data: {}
              }
            }
          }
        }
      }
    },
    getHeaderTemplateChonNhieu (parent) {
      return function () {
        return {
          template: {
            template: `
                            <div class="check-action" style="display: inline; left: 5px;">
                                <input type="checkbox" v-model="parent.checkAll" class="check"/>
                                <span class="name"></span>
                            </div>
                        `,
            data () {
              return {
                parent: parent
              }
            }
          }
        }
      }
    },
    mouseoverEvent(e) {
      const targetElement = e.target
      if (targetElement.classList.contains('e-rowcell')) {
        if (targetElement.ej2_instances && targetElement.ej2_instances[0]){return;}
        const currentRow = targetElement.parentElement;
        const data_uid=currentRow.getAttribute('data-uid')
        const data_row=  this.$refs.gridRanhTram.$refs.grid.ej2Instances.getRowObjectFromUID(data_uid).data
        EventBus.$emit('VE_RANH_SET_ACTIVE', data_row)
        targetElement.addEventListener('mouseleave', this.mouseleaveEvent);
      }
    },
  },
  watch: {
    checkAll (newVal) {
      if (newVal) {
        this.dsIdChon = this.ds_tram.map(item => item.DONVI_ID + "_" + item.MA_TRAM)
        return
      }
      if (this.dsIdChon.length > 0 && this.dsIdChon.length === this.ds_tram.length) {
        this.dsIdChon = []
      }
    },
    dsIdChon (newVal) {
      this.checkAll = newVal.length > 0 && newVal.length === this.ds_tram.length
      EventBus.$emit('VE_RANH_SET_VISBLE', newVal)
    },
    message (val) {
    },
    lock_events (val) {
    },
    selectedTTVT: function (val) {
      if (this.lock_events) return
      this.setState({key: 'ttvt_id', value: val.DONVI_ID})
      this.getDsRanhTram()
    }
  },
  created () {
    this.clearState()
    this.createApiManager()
    this.loadData()
    EventBus.$on('LOAD_TTVT', this.loadData)
    EventBus.$on('LOAD_RANHTRAM', this.getDsRanhTram)
  },
  beforeDestroy () {
    this.loadData && EventBus.$off('LOAD_TTVT', this.loadData)
    this.getDsRanhTram && EventBus.$off('LOAD_RANHTRAM', this.getDsRanhTram)
  },
  async mounted () {
  }
}
</script>

<style>
#grid2 .e-grid .e-gridheader .e-columnheader
{
  display: none;
}
#grid3 .e-grid .e-gridheader .e-columnheader
{
  display: none;
}
.e-dropdownbase .e-list-item.e-item-focus, .e-dropdownbase .e-list-item.e-active, .e-dropdownbase .e-list-item.e-active.e-hover, .e-dropdownbase .e-list-item.e-hover
{
  background-color: #0B7FC5;
  color: #FDFEFE;
}
.e-dropdownbase .e-list-item, .e-dropdownbase .e-list-item.e-item-focus
{
    font-size: 9;
}
</style>
