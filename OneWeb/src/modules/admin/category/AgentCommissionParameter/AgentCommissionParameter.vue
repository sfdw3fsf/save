<template>
  <div>
    <div class="breadcrumb-top">
      <div class="main-title">Bảng tham số tính lương nhân viên thu cước</div>
      <ul class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="#"
            ><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ
          </a>
        </li>
        <li class="breadcrumb-item"><a href="#">Lập hợp đồng</a></li>
        <li class="breadcrumb-item active">Lắp đặt mới</li>
      </ul>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" :class="{disabled: isInsertMode}" @click.prevent="createAgent"> <span class="icon one-file-plus"></span>Nhập mới </a>
        </li>
        <li>
          <a href="#" @click.prevent="saveAgent"> <span class="icon one-save"></span>Ghi lại </a>
        </li>

        <li>
          <a href="#" @click.prevent="cancelInput">
            <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy
          </a>
        </li>
        <li>
          <a href="#" @click.prevent="deleteAgent" :class="{disabled: isInsertMode}"> <span class="icon one-trash"></span>Xóa </a>
        </li>
      </ul>
    </div>
    <div class="page-content">
      <div class="box-form">
        <div class="legend-title">Tham số tính lương</div>
        <div class="row">
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w110">Từ tỷ lệ <span class="text-danger">*</span></div>
              <div class="value">
                <div :class="{'e-error e-input-group': showValidError && form.fromPercent === null}">
                  <ejs-numerictextbox
                    ref="fromPercent"
                    locale='vi-VN'
                    format="n"
                    :showSpinButton="false"
                    :min="0"
                    class="tright e-input"
                    v-model="form.fromPercent"
                  ></ejs-numerictextbox>
                </div>
              </div>
              <div class="value w10 padt7">%</div>
            </div>
            <div class="info-row">
              <div class="key w110">Đến tỷ lệ <span class="text-danger">*</span></div>
              <div class="value">
                <div :class="{'e-error e-input-group': showValidError && form.toPercent === null}">
                  <ejs-numerictextbox
                    format="n"
                    locale='vi-VN'
                    :showSpinButton="false"
                    :min="0"
                    class="tright e-input"
                    v-model="form.toPercent"
                  ></ejs-numerictextbox>
                </div>
              </div>
              <div class="value w10 padt7">%</div>
            </div>
            <div class="info-row">
              <div class="key">
                <div class="check-action">
                  <input type="checkbox" class="check" v-model="form.isCheckedDate">
                  <span class="name">Từ ngày thu</span>
                </div>
              </div>
              <div class="value">
                <div :class="{'e-error e-input-group': showValidError && form.isCheckedDate && form.fromDate > form.toDate}">
                  <ejs-numerictextbox
                    format="n"
                    locale='vi-VN'
                    :min="0"
                    class="tright e-input"
                    :enabled="form.isCheckedDate"
                    v-model="form.fromDate"
                  ></ejs-numerictextbox>
                </div>
              </div>

            </div>
            <div class="info-row">
              <div class="key">
                Đến ngày
              </div>
              <div class="value">
                <div :class="{'e-error e-input-group': showValidError && form.isCheckedDate && form.fromDate > form.toDate}">
                  <ejs-numerictextbox
                    format="n"
                    locale='vi-VN'
                    :min="0"
                    class="tright e-input"
                    :enabled="form.isCheckedDate"
                    v-model="form.toDate"
                  ></ejs-numerictextbox>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key">
                <div class="check-action">
                  <input v-model="form.isCheckedMoney" class="check" type="checkbox">
                  <span class="name">
                    Tiền HĐ từ <span v-if="form.isCheckedMoney" class="text-danger">*</span></span>
                </div>
              </div>
              <div class="value">
                <div :class="{'e-error e-input-group': showValidError && form.isCheckedMoney && form.fromMoney === null}">
                  <ejs-numerictextbox
                    locale='vi-VN'
                    format="n"
                    :showSpinButton="false"
                    :min="0"
                    class="tright"
                    :enabled="form.isCheckedMoney"
                    v-model="form.fromMoney"
                  ></ejs-numerictextbox>
                </div>
              </div>
            </div>

            <div class="info-row">
              <div class="info-row">
                <div class="key">
                  Đến <span v-if="form.isCheckedMoney" class="text-danger">*</span>
                </div>
                <div class="value">
                  <div :class="{'e-error e-input-group': showValidError && form.isCheckedMoney && form.toMoney === null}">
                    <ejs-numerictextbox
                      locale='vi-VN'
                      format="n"
                      :showSpinButton="false"
                      :min="0"
                      class="tright"
                      :enabled="form.isCheckedMoney"
                      v-model="form.toMoney"
                    ></ejs-numerictextbox>
                  </div>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key">
                Thù lao/HĐ
              </div>
              <div class="value">
                <ejs-numerictextbox
                  locale='vi-VN'
                  format="n"
                  :showSpinButton="false"
                  :min="0"
                  class="tright"
                  v-model="form.thuLao"
                ></ejs-numerictextbox>
              </div>
            </div>
          </div>
          <div class="col-sm-8 col-12">
            <div class="box-move-select-table">
              <map-table
                title="Khu vực đã gán"
                name="khu-vuc-da-gan"
                :items.sync="khuVucDaGan"
                :loading="isLoaddingmapTable"
              />
              <moving-button
                @move-to-right="moveItems(khuVucDaGan, khuVucChuaGan)"
                @move-all-to-right="moveAllItems(khuVucDaGan, khuVucChuaGan)"
                @move-to-left="moveItems(khuVucChuaGan, khuVucDaGan)"
                @move-all-to-left="moveAllItems(khuVucChuaGan, khuVucDaGan)"
              ></moving-button>

              <map-table
                title="Khu vực chưa gán"
                name="khu-vuc-chua-gan"
                :items.sync="khuVucChuaGan"
                :loading="isLoaddingmapTable"
              />
            </div>
            <div class="box-move-select-table">
              <map-table
                title="Loại phiếu đã gán"
                name="loai-phieu-da-gan"
                :items.sync="loaiPhieuDaGan"
                :loading="isLoaddingmapTable"
              />
              <moving-button
                @move-to-right="moveItems(loaiPhieuDaGan, loaiPhieuChuaGan)"
                @move-all-to-right="moveAllItems(loaiPhieuDaGan, loaiPhieuChuaGan)"
                @move-to-left="moveItems(loaiPhieuChuaGan, loaiPhieuDaGan)"
                @move-all-to-left="moveAllItems(loaiPhieuChuaGan, loaiPhieuDaGan)"
              ></moving-button>
              <map-table
                title="Loại phiếu chưa gán"
                name="loai-phieu-chua-gan"
                :items.sync="loaiPhieuChuaGan"
                :loading="isLoaddingmapTable"
              />
            </div>
          </div>
        </div>
      </div>

      <div class="box-form">
        <div class="legend-title">Danh sách tham số tính lương</div>
        <div class="table-content">
          <ejs-grid
            ref="gridAgent"
            :dataSource="agentTable.data"
            :allowPaging="true"
            :pageSettings="pageSettings"
            :allowSorting="false"
            :allowFiltering="true"
            :allowTextWrap="false"
            :selectionSettings='selectionOptionsSingle'
            :pagerTemplate='pagerTemplate'
            @rowSelected="rowSelectedEvent"
            :selectedRowIndex="0"
            gridLines="Both"
          >
            <e-columns>
              <e-column template="<span class='fa selected-flag'></span>" :allowFiltering="false"
                        filterTemplate="<span></span>"
                        width="26" maxWidth="26" minWidth="26" textAlign="center"
                        :customAttributes="{class: 'column-selected-flag'}"
                        :freeze="'left'" :allowResizing="false" clipMode="Ellipsis"></e-column>
              <e-column headerText="STT" width="70" AllowFiltering="false" textAlign="Center" :template="columnIndexTemplate"></e-column>
              <e-column field="TU_TYLE" headerText="Từ tỷ lệ" :filter="filterOptions"></e-column>
              <e-column field="DEN_TYLE" headerText="Đến tỷ lệ" :filter="filterOptions"></e-column>
              <e-column field="TU_NGAYTHU" headerText="Từ ngày" :filter="filterOptions"></e-column>
              <e-column field="DEN_NGAYTHU" headerText="Đến ngày" :filter="filterOptions"></e-column>
              <e-column field="THULAO" headerText="Thù lao" :filter="filterOptions"></e-column>
            </e-columns>
          </ejs-grid>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MapTable from './part/MapTable'
import MovingButton from './part/MovingButton'
import { pagingAndFilter } from '@/modules/admin/category/Staff/mixins/pagingAndFilter.js'
import {
  getAgentList,
  getKhuVucList,
  getLoaiPhieuList,
  updateAgent,
  deleteAgent
} from './api'

export default {
  name: 'AgentCommissionParameter',
  mixins: [pagingAndFilter],
  components: { MapTable, MovingButton },
  data: () => ({
    // mode insert 1, mode update 0
    isInsertMode: false,
    showValidError: false,
    form: {
      fromPercent: '',
      toPercent: '',
      fromDate: '',
      toDate: '',
      fromMoney: '',
      toMoney: '',
      thuLao: '',
      isCheckedDate: true,
      isCheckedMoney: true
    },
    isLoaddingmapTable: true,
    agentTable: {
      data: [],
      selected: {}
    },
    khuVucDaGan: [],
    khuVucDaGanClone: '',
    khuVucChuaGan: [],
    khuVucChuaGanClone: '',
    loaiPhieuDaGan: [],
    loaiPhieuDaGanClone: '',
    loaiPhieuChuaGan: [],
    loaiPhieuChuaGanClone: ''
  }),
  async mounted () {
    this.loadGridAgent()
    await this.loadDataMapTable()
    this.$nextTick(() => {
      this.$refs.fromPercent.focusIn()
    })
  },
  methods: {
    async loadGridAgent () {
      this.loading(true)
      this.agentTable.data = await getAgentList(this.axios).catch(err => {
        this.$toast.error(err.data.message_detail)
      })
      if (this.agentTable.data.length === 0) {
        this.$toast.errror('Không có dữ liệu danh sách tham số lương!!')
      }

      this.loading(false)
    },

    async loadDataMapTable () {
      this.isLoaddingmapTable = true
      this.khuVucDaGan = await getKhuVucList(this.axios, {
        thulao_id: this.agentTable.selected.THULAO_ID,
        kieu: 1
      })

      this.khuVucChuaGan = await getKhuVucList(this.axios, {
        thulao_id: this.agentTable.selected.THULAO_ID,
        kieu: 0
      })
      this.loaiPhieuDaGan = await getLoaiPhieuList(this.axios, {
        thulao_id: this.agentTable.selected.THULAO_ID,
        kieu: 1
      })
      this.loaiPhieuChuaGan = await getLoaiPhieuList(this.axios, {
        thulao_id: this.agentTable.selected.THULAO_ID,
        kieu: 0
      })
      this.isLoaddingmapTable = false
    },
    moveItems (source, destination) {
      const selectedItem = source.filter((item) => item.isChecked)
      const cloneItem = JSON.parse(JSON.stringify(selectedItem))
      cloneItem.forEach((item) => {
        item.isChecked = false
      })
      destination.push(...cloneItem)

      selectedItem.forEach((item) => {
        let index = source.findIndex((element) => element.value === item.value)
        source.splice(index, 1)
      })
    },
    moveAllItems (source, destination) {
      const cloneItem = JSON.parse(JSON.stringify(source))
      cloneItem.forEach((item) => {
        item.isChecked = false
      })
      destination.push(...cloneItem)
      source.splice(0)
    },
    async saveAgent () {
      if (!this.isValidForm()) {
        setTimeout(() => {
          this.showValidError = false
        }, 3000)
        return
      }
      let param = {
        json_dsthulaolp: this.loaiPhieuDaGan.map(item => ({
          LOAIPHIEU_ID: item.value
        })),
        json_dsthulaodl: {
          TU_TYLE: this.form.fromPercent,
          DEN_TYLE: this.form.toPercent,
          TU_NGAYTHU: this.form.fromDate || null,
          DEN_NGAYTHU: this.form.toDate || null,
          TIEN_HDPS_TU: this.form.fromMoney || null,
          TIEN_HDPS_DEN: this.form.toMoney || null,
          THULAO: this.form.thuLao,
          KIEUTHU: 1
        },
        is_insert: this.isInsertMode ? 1 : 0,
        json_dsthulaokv: this.khuVucDaGan.map(item => ({
          KHUVUCTC_ID: item.value
        }))
      }
      if (!this.isInsertMode) {
        param.thulao_id = this.agentTable.selected.THULAO_ID
      }

      this.loading(true)
      const isUpdateSuccess = await updateAgent(this.axios, param).catch(err => {
        this.$toast.error(err.data.message_detail)
      }).finally(() => {
        this.loading(false)
      })
      if (isUpdateSuccess) {
        this.$toast.success('Cập nhật dữ liệu thành công!')
        const agentId = this.agentTable.selected.THULAO_ID
        let index = 0
        await this.loadGridAgent()
        if (!this.isInsertMode) {
          index = this.agentTable.data.findIndex(element => element.THULAO_ID === agentId)
        }
        this.agentTable.selected = JSON.parse(JSON.stringify(this.agentTable.data[index]))
        await this.showAgentInformation()
      }
    },
    isValidForm () {
      if (this.form.fromPercent === null || this.form.toPercent === null) {
        this.$toast.error('Bạn chưa nhập tỷ lệ thu ngay !')
        this.showValidError = true
        return false
      }
      if (this.form.isCheckedDate) {
        if (this.form.fromDate > this.form.toDate) {
          this.$toast.error('Ngày thu sau phải lớn hơn ngày thu trước !')
          this.showValidError = true
          return false
        }
      }

      if (this.form.isCheckedMoney) {
        if (this.form.fromMoney === null && this.form.toMoney === null) {
          this.$toast.error('Bạn chưa nhập giá trị tiền cước trên hóa đơn !')
          this.showValidError = true
          return false
        }
      }

      if (this.form.thuLao === null) {
        this.$toast.error('Bạn chưa nhập thù lao/Hóa đơn !')
        this.showValidError = true
        return false
      }

      if (this.khuVucDaGan.length <= 0) {
        this.$toast.error('Bạn chưa chọn khu vực !')
        return false
      }
      if (this.loaiPhieuDaGan.length <= 0) {
        this.$toast.error('Bạn chưa chọn loại phiếu !')
        return false
      }
      return true
    },
    cancelInput () {
      this.isInsertMode = false
      this.setSelectedValueToForm()
      if (!this.khuVucDaGanClone && !this.khuVucChuaGanClone && !this.loaiPhieuChuaGanClone && !this.loaiPhieuDaGanClone) return
      this.khuVucDaGan = JSON.parse(this.khuVucDaGanClone)
      this.khuVucChuaGan = JSON.parse(this.khuVucChuaGanClone)
      this.loaiPhieuChuaGan = JSON.parse(this.loaiPhieuChuaGanClone)
      this.loaiPhieuDaGan = JSON.parse(this.loaiPhieuDaGanClone)
    },

    createAgent () {
      this.isInsertMode = true
      this.setSelectedValueToForm()
      this.khuVucDaGanClone = JSON.stringify(this.khuVucDaGan)
      this.khuVucChuaGanClone = JSON.stringify(this.khuVucChuaGan)
      this.loaiPhieuChuaGanClone = JSON.stringify(this.loaiPhieuChuaGan)
      this.loaiPhieuDaGanClone = JSON.stringify(this.loaiPhieuDaGan)

      this.moveAllItems(this.khuVucDaGan, this.khuVucChuaGan)
      this.moveAllItems(this.loaiPhieuDaGan, this.loaiPhieuChuaGan)
      this.$nextTick(() => {
        this.$refs.fromPercent.focusIn()
      })
    },

    setSelectedValueToForm () {
      this.form.fromPercent = this.isInsertMode ? '' : this.agentTable.selected.TU_TYLE
      this.form.toPercent = this.isInsertMode ? '' : this.agentTable.selected.DEN_TYLE
      this.form.fromDate = this.isInsertMode ? '' : this.agentTable.selected.TU_NGAYTHU
      this.form.toDate = this.isInsertMode ? '' : this.agentTable.selected.DEN_NGAYTHU
      this.form.isCheckedDate = !(this.form.fromDate === null || this.form.fromDate === undefined) &&
        !(this.form.toDate === null || this.form.toDate === undefined)

      this.form.fromMoney = this.isInsertMode ? '' : this.agentTable.selected.TIEN_HDPS_TU
      this.form.toMoney = this.isInsertMode ? '' : this.agentTable.selected.TIEN_HDPS_DEN

      this.form.isCheckedMoney = !(this.form.fromMoney === null || this.form.fromMoney === undefined) &&
        !(this.form.toMoney === null || this.form.toMoney === undefined)

      this.form.thuLao = this.isInsertMode ? '' : this.agentTable.selected.THULAO
    },

    async deleteAgent () {
      if (confirm('Bạn có thật sự muốn xóa dữ liệu không ?')) {
        this.loading(true)
        const isDeleteSuccess = await deleteAgent(this.axios, {
          thulao_id: this.agentTable.selected.THULAO_ID
        }).catch(err => {
          this.$toast.error(err.data.message_detail)
        }).finally(() => {
          this.loading(false)
        })
        if (isDeleteSuccess) {
          this.$toast.success('Xóa dữ liệu thành công!')
          this.agentTable.isLoadding = false
          this.agentTable.data = await getAgentList(this.axios)
          this.agentTable.isLoadding = false
          this.agentTable.selected = JSON.parse(JSON.stringify(this.agentTable.data[0]))
          await this.showAgentInformation()
          this.$nextTick(() => {
            this.$refs.gridAgent.selectRow(0)
          })
        } else {
          this.$toast.error('Xóa dữ liệu thất bại!')
        }
      }
    },

    rowSelectedEvent ($event) {
      if (this.agentTable.selected && this.agentTable.selected.THULAO_ID === $event.data.THULAO_ID) return
      this.agentTable.selected = JSON.parse(JSON.stringify($event.data))
      this.showAgentInformation()
    },

    async showAgentInformation () {
      this.loading(true)
      await this.loadDataMapTable()
      this.isInsertMode = false
      this.setSelectedValueToForm()
      this.khuVucDaGanClone = ''
      this.khuVucChuaGanClone = ''
      this.loaiPhieuChuaGanClone = ''
      this.loaiPhieuDaGanClone = ''
      this.loading(false)
    }
  }
}
</script>
<style scoped>
/deep/ input {
  padding-right: 3px !important;
}
</style>
