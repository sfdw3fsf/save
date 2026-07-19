<template src="./index.template.html"></template>
<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import { getterName, actionName, statePropertyName } from './../store'

import EventBus from '@/utils/eventBus'
import Vue from 'vue'
import DynamicMarker from '@/modules/CABMAN/QuanLyRanh/components/DynamicMarker'
const DynamicMarkerConstructor = Vue.extend(DynamicMarker)
export default {
  name: 'leftPaneContent',
  components: {},
  computed: {
    ...mapState('cabman/QuanLyRanh', statePropertyName),
    ...mapState('cabman', ['QuanLyRanh']),
    ...mapGetters('cabman/QuanLyRanh', getterName)
  },
  data() {
    return {
      dsIdChon: [],
      checkAll: false,
      message: null,
      lock_events: false,
      selectedLOAIRANH: {
        LOAIRANH_ID: null,
        LOAI_RANH: null
      },
      selectedTTVT: {
        DONVI_ID: null,
        TEN_DV: null
        // ds_ttvt: null
      },
      selectedTOKT: {
        DONVI_ID: null,
        TEN_DV: null
        // ds_tokt: null
      },
      selectedTRAM: {
        DONVI_ID: null,
        TEN_DV: null,
        TEN_PHUONG: null,
        TEN_KHUVUC: null
      },
      pageSettings: { pageSize: 20 },
      customAttributes: { class: 'customcss' },
      getColumnTemplate(parent) {
        return function () {
          return {
            template: {
              template: `<div class="image">
                                      <img :src="image"/>
                                      {{this.data.MA_TRAM}}
                                  </div>`,
              data() {
                return {
                  parent: parent,
                  data: {}
                }
              },
              computed: {
                image: function () {
                  let icon = parent.getDynamicMarkerIcon('iconDiemUon', this.data.STYLE_FILL)
                  return icon
                }
              }
            }
          }
        }
      }
    }
  },
  provide: {},
  methods: {
    ...mapActions('cabman/QuanLyRanh', actionName),
    getDynamicMarkerIcon(text, color) {
      const iconComponent = new DynamicMarkerConstructor({
        propsData: {
          text,
          color
        }
      })
      iconComponent.$mount()
      const iconDom = iconComponent.$el
      const iconString = new XMLSerializer().serializeToString(iconDom)
      return 'data:image/svg+xml;charset=UTF-8;base64,' + btoa(iconString)
    },
    async onDoubleClick(args) {
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
    async loadData() {
      //load loại ranh
      try {
        this.$root.showLoading(true)
        await this.api_get_ds_loairanh()
        if (this.ds_loairanh.length > 0) {
          this.selectedLOAIRANH = this.ds_loairanh[0]
          this.setState({ key: 'loairanh_id', value: this.ds_loairanh[0].LOAIRANH_ID })
        } else {
          this.selectedLOAIRANH.LOAIRANH_ID = null
          this.selectedLOAIRANH.LOAI_RANH = null
        }
        this.$root.showLoading(false)
      } catch (error) {
        this.$root.showLoading(false)
        console.log('🚀 ~ loadData ~ error:', error)
        this.$root.$toast.error('Lỗi thực hiện nạp dữ liệu loại ranh')
      }
      //load ttvt
      try {
        this.$root.showLoading(true)
        await this.api_get_ds_ttvt(this.$root.token.getPhanVungID())
        if (this.ds_ttvt.length > 0) {
          this.selectedTTVT = this.ds_ttvt[0]
          this.setState({ key: 'ttvt_id', value: this.ds_ttvt[0].DONVI_ID })
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
    async getDsToKT() {
      try {
        await this.api_get_ds_tokt(this.selectedTTVT.DONVI_ID)
        if (this.ds_tokt.length > 0) {
          this.selectedTOKT = this.ds_tokt[0].DONVI_ID
        } else {
          this.selectedTOKT.DONVI_ID = null
          this.selectedTOKT.TEN_DV = null
        }
      } catch (error) {
        this.$root.$toast.error('Lỗi thực hiện nạp dữ liệu')
      }
    },
    async getDsRanhTram() {
      try {
        this.checkAll = false
        this.dsIdChon = []
        this.$root.showLoading(true)
        await this.api_get_ds_ranhtram({
          p_ttvt_id: this.selectedTTVT.DONVI_ID,
          p_tovt_id: this.tovt_id,
          p_loairanh_id: this.selectedLOAIRANH.LOAIRANH_ID
        })
        if (this.ds_tram.length > 0) {
          this.selectedTRAM = this.ds_tram[0]
          this.setState({ key: 'tramvt_id', value: this.ds_tram[0].DONVI_ID })
          this.checkAll = true
        } else {
          this.selectedTRAM.DONVI_ID = null
          this.selectedTRAM.TEN_DV = null
          this.selectedTRAM.TEN_PHUONG = null
          this.selectedTRAM.TEN_KHUVUC = null
        }
        EventBus.$emit('VE_RANH_TTVT2', this.ds_tram)
        this.$root.showLoading(false)
      } catch (error) {
        this.$root.showLoading(false)
        this.$root.$toast.error('Lỗi thực hiện nạp dữ liệu')
      }
    },
    gridRanhTram_rowSelected(data) {
      // EventBus.$emit('VE_RANH_SET_ACTIVE', data)
    },
    gridRanhTram_created(e) {
      const gridRanhTram = document.getElementById('gridRanhTram')
      if (gridRanhTram) {
        gridRanhTram.addEventListener('mouseover', (event) => this.mouseoverEvent(event))
      }
    },
    mouseleaveEvent(e) {
      const targetElement = e.target
      targetElement.removeEventListener('mouseleave', this.mouseleaveEvent)
      if (targetElement.classList.contains('e-rowcell')) {
        if (targetElement.ej2_instances && targetElement.ej2_instances[0]) {
          return
        }
        const currentRow = targetElement.parentElement
        const data_uid = currentRow.getAttribute('data-uid')
        const data_row = this.$refs.gridRanhTram.$refs.grid.ej2Instances.getRowObjectFromUID(data_uid).data
        EventBus.$emit('VE_RANH_SET_DEACTIVE', data_row)
      }
    },
    getColumnTemplateChonNhieu(parent) {
      return function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="check"
                                    v-model="parent.dsIdChon"
                                    :value="data.RANH_ID"/>
                                <span class="name"></span>
                            </div>
                        `,
            data() {
              return {
                parent: parent,
                data: {}
              }
            }
          }
        }
      }
    },
    getHeaderTemplateChonNhieu(parent) {
      return function () {
        return {
          template: {
            template: `
                            <div class="check-action" style="display: inline-block; left: 5px;">
                                <input type="checkbox" v-model="parent.checkAll" class="check"/>
                                <span class="name"></span>
                            </div>
                        `,
            data() {
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
        if (targetElement.ej2_instances && targetElement.ej2_instances[0]) {
          return
        }
        const currentRow = targetElement.parentElement
        const data_uid = currentRow.getAttribute('data-uid')
        const data_row = this.$refs.gridRanhTram.$refs.grid.ej2Instances.getRowObjectFromUID(data_uid).data
        EventBus.$emit('VE_RANH_SET_ACTIVE', data_row)
        targetElement.addEventListener('mouseleave', this.mouseleaveEvent)
      }
    }
  },
  watch: {
    checkAll(newVal) {
      if (newVal) {
        this.dsIdChon = this.ds_tram.map((item) => item.RANH_ID)
        // khai báo biến chứa ds ranh id
        let ds_ranh_id = this.ds_tram.map((item) => item.RANH_ID)
        this.setState({ key: 'ds_ranh_id', value: ds_ranh_id })
        return
      }
      if (this.dsIdChon.length > 0 && this.dsIdChon.length === this.ds_tram.length) {
        this.dsIdChon = []
        this.setState({ key: 'ds_ranh_id', value: [] })
      }
    },
    dsIdChon(newVal) {
      this.checkAll = newVal.length > 0 && newVal.length === this.ds_tram.length
      this.setState({ key: 'ds_ranh_id', value: newVal })
      EventBus.$emit('VE_RANH_SET_VISBLE', newVal)
    },
    message(val) {},
    lock_events(val) {},
    // selectedLOAIRANH: function (val) {
    //   if (this.lock_events) return
    //   this.setState({ key: 'loairanh_id', value: val.LOAIRANH_ID })
    //   this.getDsRanhTram()
    // },
    // selectedTTVT: function (val) {
    //   if (this.lock_events) return
    //   this.setState({ key: 'ttvt_id', value: val.DONVI_ID })
    //   this.getDsToKT()
    //   this.getDsRanhTram()
    // },
    // selectedTOKT: function (val) {
    //   if (this.lock_events) return
    //   this.clearState_ds_cap()
    //   this.set_tokt(val)
    //   this.getDsRanhTram()
    // },
    selectedLOAIRANH: function (val) {
      if (this.lock_events) return
      this.setState({ key: 'loairanh_id', value: val.LOAIRANH_ID })
      if (val.LOAIRANH_ID !== 3 || this.selectedTOKT) {
        this.getDsRanhTram()
      }
      if (val.LOAIRANH_ID === 3) {
        this.getDsToKT()
      }
    },
    selectedTTVT: function (val) {
      if (this.lock_events) return
      this.setState({ key: 'ttvt_id', value: val.DONVI_ID })
      this.getDsToKT()
      if (this.selectedLOAIRANH.LOAIRANH_ID !== 3 || val) {
        this.getDsRanhTram()
      }
    },
    selectedTOKT: function (val) {
      console.log(val)
      if (this.lock_events) return
      this.setState({ key: 'tovt_id', value: val })
      // this.clearState_ds_cap()
      // this.set_tokt(val)
      if (this.selectedLOAIRANH.LOAIRANH_ID !== 3 || val) {
        this.getDsRanhTram()
      }
    }
  },
  created() {
    this.clearState()
    this.createApiManager()
    this.loadData()
    EventBus.$on('LOAD_TTVT', this.loadData)
    EventBus.$on('LOAD_RANHTRAM', this.getDsRanhTram)
  },
  beforeDestroy() {
    this.loadData && EventBus.$off('LOAD_TTVT', this.loadData)
    this.getDsRanhTram && EventBus.$off('LOAD_RANHTRAM', this.getDsRanhTram)
  },
  async mounted() {}
}
</script>

<style>
#grid2 .e-grid .e-gridheader .e-columnheader {
  display: none;
}

#grid3 .e-grid .e-gridheader .e-columnheader {
  display: none;
}

.e-dropdownbase .e-list-item.e-item-focus,
.e-dropdownbase .e-list-item.e-active,
.e-dropdownbase .e-list-item.e-active.e-hover,
.e-dropdownbase .e-list-item.e-hover {
  background-color: #0b7fc5;
  color: #fdfefe;
}

.e-dropdownbase .e-list-item,
.e-dropdownbase .e-list-item.e-item-focus {
  font-size: 9;
}

.e-grid .e-gridheader .e-sortfilter .e-headercelldiv {
  padding: 0 12px 0 5px;
}
</style>
