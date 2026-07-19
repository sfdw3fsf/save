<template>
  <div class="">
    <!-- Form -->
    <div class="modal-content popup-box" style="max-width:800px">
      <div class="popup-header">
        <div class="title"><span class="icon one-appstore"></span> Nhập thông tin cuộn cáp</div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li @click="onClickXacNhan">
            <a> <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Xác nhận </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin số lượng cuộn cáp</div>
          <div class="" style="padding-bottom: 5px;">
            <div class="pull-left">{{ lblThongTin }}</div>
            <div class="pull-right">
              <a class="btn btn-second pad4 lh14" @click="addRowThietBi()">
                <span class="nc-icon-glyph ui-2_fat-add"></span>
              </a>
            </div>
            <div class="clearfix"></div>
          </div>
          <div class="table-content">
            <DataGrid3
              :columns="[
                { fieldName: '', headerText: 'Xóa', allowFiltering: false, width: 120, template: deleteTemplate, cssClass: 'text-center' },
                { fieldName: 'SOCUON', headerText: 'Số lượng cuộn cáp', allowFiltering: true, type: 'number', format: 'N0', allowEditing: true },
                { fieldName: 'SOLUONG', headerText: 'Độ dài cuộn cáp', allowFiltering: true, type: 'number', format: 'N3', allowEditing: true },
                { fieldName: 'ID', headerText: '', visible: false, isPrimaryKey: true }
              ]"
              :dataSource="DSThietBi"
              :showFilter="true"
              dataKeyField="ID"
              ref="gridThietBi"
              :allowPaging="true"
              :enablePagingServer="false"
              :editSettings="editSettings"
              @cellSaved="editGridThietBi"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState, mapGetters, mapMutations } from 'vuex'
import breadcrumb from '@/components/breadcrumb'
import { getterName, actionName, statePropertyName, mutationName } from '../store/frmnhapcap'
import moment from 'moment'
import Vue from 'vue'
import { GridPlugin, Group, Page, Filter, Sort, Resize } from '@syncfusion/ej2-vue-grids'
import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import EventBus from '@/utils/eventBus'

Vue.use(DatePickerPlugin)

Vue.use(GridPlugin)

export default {
  components: { breadcrumb },
  name: 'NhapCap',
  data() {
    return {
      ten_vt: '',
      tong_sl: 0,
      xacnhan: false,
      tong: 0,
      data: [],
      lblThongTin: '',
      DSThietBi: [],
      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper',
      maxID: 0,
      // parentForm: "",
      editSettings: { allowEditing: true, mode: 'Batch' },
      deleteTemplate: function() {
        return {
          template: Vue.component('columnTemplate', {
            template: `<div>
                          <button class="btn btn-danger lh14 pad3" @click="onDeleteRow" title="Xóa chi tiết">
                            <span class="-ap icon-close"></span>
                          </button>
                      </div>`,
            data() {
              return { data: {} }
            },
            methods: {
              onDeleteRow() {
                EventBus.$emit('onDeleteRow', this.data)
              }
            }
          })
        }
      }
    }
  },

  computed: {
    ...mapState('frmnhapcap', statePropertyName),
    ...mapGetters('frmnhapcap', getterName)
  },

  methods: {
    ...mapActions('frmnhapcap', actionName),
    ...mapMutations('frmnhapcap', mutationName),

    async onClickXacNhan() {
      if (this.tong > this.tong_sl) {
        this.$toast.error('Số lượng bạn chọn lớn hơn số lương tổng gốc')
        this.xacnhan = false
      } else if (this.tong == 0) {
        this.$toast.error('Số lượng tổng cáp cắt không thể bằng 0')
        this.xacnhan = false
      } else if (this.tong < this.tong_sl) {
        this.$toast.error('Số lượng tổng cáp cắt chưa bằng tổng số lượng gốc')
        this.xacnhan = false
      } else {
        this.data = this.DSThietBi
        this.xacnhan = true
        this.Close()
      }
    },

    async frmNhapCap_Load() {
      this.lblThongTin = this.ten_vt + ' có số lượng cáp: 0/' + this.tong_sl
      this.DSThietBi = [
        {
          ID: this.maxID,
          SOLUONG: 0,
          SOCUON: 0
        }
      ]
    },
    async Open() {
      EventBus.$on('onDeleteRow', this.onDeleteRowHandler)
      this.frmNhapCap_Load()
      this.$refs.popupNhapCap.show()
    },
    async Close() {
      this.$parent.hasOwnProperty('XacNhan_frmNhapCap') ? await this.$parent.XacNhan_frmNhapCap() : await this.$parent.$parent.XacNhan_frmNhapCap()
      this.$refs.popupNhapCap.hide()
    },
    onDeleteRowHandler(item) {
      console.log(item, ' = item')
      var NewDS = this.DSThietBi.filter(function(el) {
        return el.ID != item.ID
      })
      this.DSThietBi = NewDS
      this.tong = 0
      for (const i in this.DSThietBi) {
        this.tong += this.DSThietBi[i].SOCUON * this.DSThietBi[i].SOLUONG
      }
      this.lblThongTin = this.ten_vt + ' có số lượng cáp: ' + this.tong + '/' + this.tong_sl
    },
    destroyed() {
      EventBus.$off('onDeleteRow', this.onDeleteRowHandler)
    },
    editGridThietBi(arg) {
      let gridThietBi = this.$refs.gridThietBi
      let changes = gridThietBi.getBatchChanges()

      if (changes.changedRecords.length > 0) {
        if (changes.changedRecords[0].SOLUONG < 0) {
          changes.changedRecords[0].SOLUONG = 0
          this.$toast.error('Không được nhập số âm!')
        }
        if (changes.changedRecords[0].SOCUON < 0) {
          changes.changedRecords[0].SOCUON = 0
          this.$toast.error('Không được nhập số âm!')
        }
      }

      gridThietBi.batchUpdate(changes)
      this.tong = 0
      for (const i in this.DSThietBi) {
        this.tong += this.DSThietBi[i].SOCUON * this.DSThietBi[i].SOLUONG
      }
      this.lblThongTin = this.ten_vt + ' có số lượng cáp: ' + this.tong + '/' + this.tong_sl
    },

    addRowThietBi() {
      this.maxID += 1
      this.DSThietBi.push({
        ID: this.maxID,
        SOLUONG: 0,
        SOCUON: 0
      })
    }
  },

  created: async function() {},
  provide: {
    grid: [Group, Page, Filter, Sort, Resize]
  }
}
</script>
<style scoped></style>
