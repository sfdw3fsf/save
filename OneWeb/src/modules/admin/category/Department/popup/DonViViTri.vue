<template>
  <b-modal
    ref="popupDonViVT"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="frmDonvi_vt_Load"
    @hide="handleHideModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Đơn vị - vị trí
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div>
        <vue-nav>
          <ul class="list">
            <li>
              <a @click="btnGhiLai_Click()">
                <span class="icon one-save"></span> Ghi lại
              </a>
            </li>
          </ul>
        </vue-nav>
        <div class="popup-body">
          <b-row>
            <div class="col-md-12">
              <vue-card>
                <b-row>
                  <vue-element label="Đơn vị" class="col-md-12">
                    <ejs-textbox v-model="tendonvi" id="don_vi"></ejs-textbox>
                  </vue-element>
                </b-row>
                <b-row>
                  <vue-element label="Chức danh" class="col-md-12">
                    <ComboboxGrid :valueField="DmChucDanh.valueField" :textField="DmChucDanh.textField"
                                  v-model="DmChucDanh.selectChucDanh" :value="DmChucDanh.selectChucDanh"
                                  :columns="DmChucDanh.headers"
                                  :panelDataHeight="DmChucDanh.panelDataHeight" :enabledSelectFirstRow="true"
                                  :dataSource="DmChucDanh.data">
                    </ComboboxGrid>
                  </vue-element>
                </b-row>
              </vue-card>
            </div>
          </b-row>
          <b-row>
            <b-col md="12">
              <div class="box-move-select-table">
                <div class="box-col box-form">
                  <vue-card>
                    <b-row>
                      <div class="legend-title">
                        <div class="pull-left">
                          <span></span> Vị trí đã gán
                        </div>
                      </div>
                      <DataGridCustom
                        ref="vtDaGan"
                        v-bind:columns="collumnsVT"
                        v-bind:dataSource="options.dsViTriDaGan"
                        :showColumnCheckbox="true"
                        :showFilter="true"
                        :enabledSelectFirstRow="false"
                        :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                        panelDataHeight="200"
                        @selectedItemsChanged="selectLeft_selectedItemsChanged"
                        :allowPaging="true" :enablePagingServer="false"
                      ></DataGridCustom>
                    </b-row>
                  </vue-card>
                </div>
                <div class="actions">
                  <button class="btn" @click="toRight">
                    <span class="fa fa-angle-right"></span>
                  </button>
                  <button class="btn" @click="toRightAll">
                    <span class="fa fa-angle-double-right"></span>
                  </button>
                  <button class="btn" @click="toLeft">
                    <span class="fa fa-angle-left"></span>
                  </button>
                  <button class="btn" @click="toLeftAll">
                    <span class="fa fa-angle-double-left"></span>
                  </button>
                </div>
                <div class="box-col box-form">
                  <vue-card>
                    <b-row>
                      <div class="legend-title">
                        <div class="pull-left">
                          <span></span> Vị trí chưa gán
                        </div>
                        <div class="clearfix"></div>
                      </div>
                    </b-row>
                    <b-row>
                      <DataGridCustom
                        ref="vtChuaGan"
                        v-bind:columns="collumnsVT"
                        v-bind:dataSource="options.dsViTriChuaGan"
                        :showColumnCheckbox="true"
                        :showFilter="true"
                        :enabledSelectFirstRow="false"
                        :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                        panelDataHeight="200"
                        @selectedItemsChanged="selectRight_selectedItemsChanged"
                        :allowPaging="true" :enablePagingServer="false"
                      ></DataGridCustom>
                    </b-row>
                  </vue-card>
                </div>
              </div>
            </b-col>
          </b-row>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import collums from '../define/collums'
import VueElement from '../components/VueElement.vue'
import DepartmentAPI from '../API'
import DataGridCustom from '../components/DataGrid'

export default {
  components: {
    VueElement,
    DataGridCustom
  },
  props: {
    donviId: {
      type: Number,
      default: function () {
        return 0
      }
    },
    tendonvi: {
      type: String,
      default: function () {
        return ''
      }
    }
  },
  data () {
    return {
      ...collums,
      target: '.main-wrapper',
      model: {
        don_vi: ''
      },
      DmChucDanh: {
        data: [],
        disabled: false,
        textField: 'chucdanh',
        valueField: 'chucdanh_id',
        panelDataHeight: 'auto',
        headers: [
          {
            fieldName: 'chucdanh',
            headerText: '',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'chucdanh_id',
            headerText: '',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        selectChucDanh: null
      },
      lstChucDanh: [],
      checkedViTriDG: [],
      checkedViTriCG: [],
      options: {
        dsViTriDaGan: [],
        dsViTriChuaGan: []
      }
    }
  },
  watch: {
    'DmChucDanh.selectChucDanh' (val) {
      if (val != null) {
        this.SP_DONVI_VTRI_LAYDS(val)
      }
    }
  },
  methods: {
    async frmDonvi_vt_Load () {
      this.donvi_name = this.tendonvi
      await this.loadCbb_ChucDanh()
    },
    handleHideModal () {
      this.donvi_name = ''
    },
    hideModal () {
      this.$refs.popupDonViVT.hide()
    },
    showModal () {
      this.$refs.popupDonViVT.show()
    },
    async loadCbb_ChucDanh () {
      try {
        const response = await DepartmentAPI.loadCbb_ChucDanh(this.axios)
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          let data = response.data.data
          if (data.length > 0) {
            this.DmChucDanh.data = data
            this.DmChucDanh.selectChucDanh = data[0].chucdanh_id
          }
        }
      } catch (e) {
        console.log(e)
        this.$root.$toast.error('Lỗi khi load chức danh ' + e.message)
      }
    },
    async btnGhiLai_Click () {
      try {
        let params = {}
        let danhsach = []
        for (let index in this.options.dsViTriDaGan) {
          let ov = {}
          ov.DONVI_ID = this.donviId
          ov.vtcd_id = this.options.dsViTriDaGan[index].VTCD_ID
          danhsach.push(ov)
          console.log(danhsach)
        }
        params.p_donvi_id = this.donviId
        params.danhsach = danhsach
        await DepartmentAPI.SP_DONVI_VTRI_GHILAI(this.axios, params).then(response => {
          if (response && response.data && response.data.error_code === 'BSS-00000000') {
            this.$root.$toast.success('Cập nhật thành công')
            this.SP_DONVI_VTRI_LAYDS(this.DmChucDanh.selectChucDanh)
          } else {
            this.$root.$toast.error('Cập nhật thất bại ' + response.data.message_detail)
          }
        }).catch(error => {
          this.$root.$toast.error('Xả ra lỗi: ' + error.data.message_detail)
        })
      } catch (e) {
        console.log(e)
        this.$root.$toast.error('Xảy ra lỗi ' + e.message)
      }
    },
    async toRight () {
      try {
        if (this.options.dsViTriDaGan === undefined) {
          this.options.dsViTriDaGan = []
        }
        if (this.options.dsViTriChuaGan === undefined) {
          this.options.dsViTriChuaGan = []
        }

        this.options.dsViTriChuaGan = this.options.dsViTriChuaGan.concat(this.checkedViTriDG)

        for (let index in this.checkedViTriDG) {
          let ixRemove
          for (let ix in this.options.dsViTriDaGan) {
            if (this.options.dsViTriDaGan[ix].VTCD_ID === this.checkedViTriDG[index].VTCD_ID) {
              ixRemove = ix
              break
            }
          }
          this.options.dsViTriDaGan.splice(ixRemove, 1)
        }

        this.checkedViTriDG = []
        this.checkedViTriCG = []
      } catch (err) {
        console.log(err)
      }
    },
    async toLeft () {
      try {
        if (this.options.dsViTriDaGan === undefined) {
          this.options.dsViTriDaGan = []
        }
        if (this.options.dsViTriChuaGan === undefined) {
          this.options.dsViTriChuaGan = []
        }

        this.options.dsViTriDaGan = this.options.dsViTriDaGan.concat(this.checkedViTriCG)

        for (let index in this.checkedViTriCG) {
          let ixRemove
          for (let ix in this.options.dsViTriChuaGan) {
            if (this.options.dsViTriChuaGan[ix].VTCD_ID === this.checkedViTriCG[index].VTCD_ID) {
              ixRemove = ix
              break
            }
          }
          this.options.dsViTriChuaGan.splice(ixRemove, 1)
        }

        this.checkedViTriDG = []
        this.checkedViTriCG = []
      } catch (err) {
        console.log(err)
      }
    },
    toRightAll () {
      if (this.options.dsViTriDaGan === undefined) {
        this.options.dsViTriDaGan = []
      }
      if (this.options.dsViTriChuaGan === undefined) {
        this.options.dsViTriChuaGan = []
      }
      this.options.dsViTriDaGan.forEach(item => {
        this.options.dsViTriChuaGan.push(item)
      })
      this.options.dsViTriDaGan = []
      this.checkedViTriDG = []
      this.checkedViTriCG = []
    },
    toLeftAll () {
      if (this.options.dsViTriDaGan === undefined) {
        this.options.dsViTriDaGan = []
      }
      if (this.options.dsViTriChuaGan === undefined) {
        this.options.dsViTriChuaGan = []
      }
      this.options.dsViTriChuaGan.forEach(item => {
        this.options.dsViTriDaGan.push(item)
      })
      this.options.dsViTriChuaGan = []
      this.checkedViTriDG = []
      this.checkedViTriCG = []
    },
    selectLeft_selectedItemsChanged (value) {
      this.checkedViTriDG = value
      for (let ix in this.checkedViTriDG) {
        let check = false
        for (let i in this.options.dsViTriDaGan) {
          if (this.checkedViTriDG[ix].VTCD_ID === this.options.dsViTriDaGan[i].VTCD_ID) {
            check = true
            break
          }
        }
        if (!check) {
          this.checkedViTriDG = []
          break
        }
      }
    },
    selectRight_selectedItemsChanged (value) {
      this.checkedViTriCG = value
      for (let ix in this.checkedViTriCG) {
        let check = false
        for (let i in this.options.dsViTriChuaGan) {
          if (this.checkedViTriCG[ix].VTCD_ID === this.options.dsViTriChuaGan[i].VTCD_ID) {
            check = true
            break
          }
        }
        if (!check) {
          this.checkedViTriCG = []
          break
        }
      }
    },
    async SP_DONVI_VTRI_LAYDS (val) {
      this.loading(true)
      this.options.dsViTriDaGan = []
      this.options.dsViTriChuaGan = []
      this.checkedViTriDG = []
      this.checkedViTriCG = []
      try {
        let params = {
          p_chucdanh_id: val,
          p_donvi_id: this.donviId,
          p_type: 1
        }
        let response = await DepartmentAPI.SP_DONVI_VTRI_LAYDS(this.axios, params)
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          this.options.dsViTriDaGan = response.data.data
        }

        params = {
          p_chucdanh_id: val,
          p_donvi_id: this.donviId,
          p_type: 0
        }
        response = await DepartmentAPI.SP_DONVI_VTRI_LAYDS(this.axios, params)
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          this.options.dsViTriChuaGan = response.data.data
        }
      } catch (e) {
        console.log(e)
      }
      this.loading(false)
    }
  }
}
</script>
<style>

</style>
