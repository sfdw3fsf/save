<template>
    <div>
        <ejs-dialog
            :enableResize='true'
            :allowDragging='true'
            :visible='false'
            ref="dlgLuotGiao"
            header="Danh sách các lượt giao"
            :animationSettings='animationSettings'
            showCloseIcon=true width='80%'
            :target='target'
            :open="dialogOpen"
            :close="close"
            :isModal="true"
        >
            <div class="">
                    <div class="list-actions-top">
                        <ul class="list">
                            <li>
                                <a href="javascript: void(0)" @click="onChon" title="Chọn">
                                    <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span>Chọn
                                </a>
                            </li>
                            <li class="dropdown">
                                <a href="javascript: void(0)" data-toggle="dropdown" title="Phiếu giao">
                                    <span class="icon one-print"></span>Phiếu giao
                                </a>
                                <ul class="dropdown-menu">
                                    <li>
                                        <a href="javascript: void(0)" @click="onDSGiao" class="text-primary">
                                            <span class="dropdown-icon one-print"></span> DS giao
                                        </a>
                                        <appModalXuatDuLieu
                                            id="dsgiao-modal-xuat-file"
                                            :thuocTinh="dsGiao.fields"
                                            :data="dsGiao.items"
                                        />
                                    </li>
                                    <li>
                                        <a href="javascript: void(0)" @click="onBBBanGiao" class="text-primary">
                                            <span class="dropdown-icon one-print"></span> BB bàn giao
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li>
                                <a href="javascript: void(0)" @click="onDSTon" title="DS tồn">
                                    <span class="icon one-print"></span>DS Tồn
                                </a>
                                <appModalXuatDuLieu
                                    id="dston-modal-xuat-file"
                                    :thuocTinh="dsTon.fields"
                                    :data="dsTon.items"
                                />
                            </li>
                            <li>
                                <a href="javascript: void(0)" @click="onHuyGiao" title="Hủy giao">
                                    <span class="icon one-cancel"></span>Hủy giao
                                </a>
                            </li>
                            <li>
                                <a href="javascript: void(0)" @click="onXuatExcel" title="Xuất excel">
                                    <span class="icon one-xlsx-import"></span>Xuất Excel
                                </a>
                            </li>
                            <li>
                                <a href="javascript: void(0)" @click="close" title="Thoát">
                                    <span class="icon one-export"></span> Thoát
                                </a>
                            </li>
                        </ul>
                    </div>
                    <div class="popup-body">
                        <div class="box-form">
                            <div class="legend-title" style="font-weight: 600!important">Danh sách các lượt giao</div>
                            <div class="table-content">
                                <DataGrid v-bind:columns="luotGiao_grid.columns"
                                    v-bind:dataSource="luotGiao_grid.items"
                                    dataKeyField="GIAOPHIEU_ID"
                                    :showFilter="true"
                                    :allowPaging="false"
                                    :enablePagingServer="false"
                                    :allowExcelExport="true"
                                    :showColumnCheckbox="true"
                                    :aggregates="aggregatesColumns"
                                    :enabledSelectFirstRow="false"
                                    ref="lgGrid"
                                    @selectedItemsChanged="onLuotGiaoChecked"
                                    :key="luotGiao_grid.key"
                                >
                                </DataGrid>
                            </div>
                        </div>
                    </div>                    
            </div>
        </ejs-dialog>
    </div>
</template>
<style scoped src="../style.scss"></style>
<script>
import API from '@/modules/QLTN/api/GiaoPhieuTonAPI'
import { export_json_to_excel } from './Export2Excel'
import XuatFile from '../../../CSKH/utils/XuatFile.vue'
import { previewPrint } from '../../../../utils/util'
// import PhieuGiao from '../../QLDLGiaoPhieu/components/phieu-giao'
import Vue from 'vue'
// import VueHtml2pdf from 'vue-html2pdf'

export default {
  components: { appModalXuatDuLieu: XuatFile},
  name: 'PopupLuotGiao',

  props: {
    input: Object
  },

  async created () {},

  mounted () {},

  computed: {
    aggregatesColumns () {
      return [
        { field: 'GIAOPHIEU_ID', type: 'Count', format: 'N0', footerTemplate: this.countTemplate },
        { field: 'SOLUONG_GBC', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate },
        { field: 'SOLUONG_HD', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate },
        { field: 'TONGTIEN_HD', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate },
        { field: 'TRA_HD', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate },
        { field: 'TRA_TB', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate }
      ]
    },
    itemSelected: function () {
      return this.luotGiao_grid.checked[0]
    }
  },

  data () {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      luotGiao_grid: {
        items: [],
        checked: [],
        key: 1,
        columns: [
          {fieldName: 'TEN_NV', headerText: 'Nhân viên', allowFiltering: true, allowSorting: false, width: 120, isGroupedColumn: true},
          {fieldName: 'GIAOPHIEU_ID', headerText: 'Lượt giao', allowFiltering: true, allowSorting: false, width: 90, cssClass: 'text-right'},
          {fieldName: 'NGAYGIAO', headerText: 'Ngày giao', allowFiltering: true, allowSorting: false, width: 110, cssClass: 'text-right'},
          {fieldName: 'SOLUONG_GBC', headerText: 'Giấy báo giao', allowFiltering: true, allowSorting: false, width: 120, cssClass: 'text-right'},
          {fieldName: 'SOLUONG_HD', headerText: 'Hóa đơn giao', allowFiltering: true, allowSorting: false, width: 120, cssClass: 'text-right'},
          {fieldName: 'TONGTIEN_HD', headerText: 'Tiền hóa đơn giao', allowFiltering: true, allowSorting: false, width: 150, cssClass: 'text-right', format: 'N0', type: 'number'},
          {fieldName: 'TRA_HD', headerText: 'Trả hóa đơn', allowFiltering: true, allowSorting: false, width: 120, cssClass: 'text-right'},
          {fieldName: 'TRA_TB', headerText: 'Trả trọn bộ', allowFiltering: true, allowSorting: false, width: 110, cssClass: 'text-right'},
          {fieldName: 'DS_DUONG', headerText: 'Danh sách đường', allowFiltering: true, allowSorting: false, width: 140, cssClass: 'text-left'},
          {fieldName: 'DS_DOT', headerText: 'Danh sách đợt', allowFiltering: true, allowSorting: false, width: 120, cssClass: 'text-left'},
          {fieldName: 'NVGIAO', headerText: 'NV giao', allowFiltering: true, allowSorting: false, width: 150}
        ]
      },
      dsTon: {
        items: [],
        fields: [
          {fieldName: 'STT', headerText: 'STT', allowFiltering: true, allowSorting: false, width: 70,},
          {fieldName: 'GIAOPHIEU_ID', headerText: 'GIAOPHIEU_ID', allowFiltering: true, allowSorting: false, width: 100},
          {fieldName: 'MA_TT', headerText: 'MA_TT', allowFiltering: true, allowSorting: false, width: 100},
          {fieldName: 'MATB_DD', headerText: 'MATB_DD', allowFiltering: true, allowSorting: false, width: 100},
          {fieldName: 'TEN_TT', headerText: 'TEN_TT', allowFiltering: true, allowSorting: false, width: 100},
          {fieldName: 'DIACHI_TT', headerText: 'DIACHI_TT', allowFiltering: true, allowSorting: false, width: 100},
          {fieldName: 'SO_SERI', headerText: 'SO_SERI', allowFiltering: true, allowSorting: false, width: 100},
          {fieldName: 'HINHTHUC_TT', headerText: 'HINHTHUC_TT', allowFiltering: true, allowSorting: false, width: 100},
          {fieldName: 'NO_DK', headerText: 'NO_DK', allowFiltering: true, allowSorting: false, width: 100},
          {fieldName: 'NO_PS', headerText: 'NO_PS', allowFiltering: true, allowSorting: false, width: 100},
          {fieldName: 'MAIN_GHEP', headerText: 'MAIN_GHEP', allowFiltering: true, allowSorting: false, width: 100},
          {fieldName: 'MAPHAT_HD', headerText: 'MAPHAT_HD', allowFiltering: true, allowSorting: false, width: 100},
          {fieldName: 'GHICHU', headerText: 'GHICHU', allowFiltering: true, allowSorting: false, width: 100},
          {fieldName: 'TONGNO', headerText: 'TONGNO', allowFiltering: true, allowSorting: false, width: 100},
          {fieldName: 'EMAIL', headerText: 'EMAIL', allowFiltering: true, allowSorting: false, width: 100},
          {fieldName: 'NGAYGIAO', headerText: 'NGAYGIAO', allowFiltering: true, allowSorting: false, width: 100},
          {fieldName: 'NGAYGIAO_RPT', headerText: 'NGAYGIAO_RPT', allowFiltering: true, allowSorting: false, width: 100}
        ]
      },
      dsGiao: {
        items: [],
        fields: [
          {fieldName: 'STT', headerText: 'STT', allowFiltering: true, allowSorting: false, width: 70,},
          {fieldName: 'MA_TT', headerText: 'MA_TT', allowFiltering: true, allowSorting: false, width: 100},
          {fieldName: 'MATB_DD', headerText: 'MATB_DD', allowFiltering: true, allowSorting: false, width: 100},
          {fieldName: 'TEN_TT', headerText: 'TEN_TT', allowFiltering: true, allowSorting: false, width: 100},
          {fieldName: 'DIACHI_TT', headerText: 'DIACHI_TT', allowFiltering: true, allowSorting: false, width: 100},
          {fieldName: 'SO_THUNG', headerText: 'SO_THUNG', allowFiltering: true, allowSorting: false, width: 100},
          {fieldName: 'SO_SERI', headerText: 'SO_SERI', allowFiltering: true, allowSorting: false, width: 100},
          {fieldName: 'TONGNO_HT', headerText: 'TONGNO_HT', allowFiltering: true, allowSorting: false, width: 100},
          {fieldName: 'TONGNO', headerText: 'TONGNO', allowFiltering: true, allowSorting: false, width: 100},
          {fieldName: 'TIEN_HD', headerText: 'TIEN_HD', allowFiltering: true, allowSorting: false, width: 100},
          {fieldName: 'MAIN_GHEP', headerText: 'MAIN_GHEP', allowFiltering: true, allowSorting: false, width: 100},
          {fieldName: 'MAPHAT_HD', headerText: 'MAPHAT_HD', allowFiltering: true, allowSorting: false, width: 100},
          {fieldName: 'GHICHU', headerText: 'GHICHU', allowFiltering: true, allowSorting: false, width: 100},
          {fieldName: 'EMAIL', headerText: 'EMAIL', allowFiltering: true, allowSorting: false, width: 100}
        ]
      },
      sumTemplate: function () {
        return { template: Vue.component('sumTemplate', {
          template: `<input type="text" class="form-control tright" :value="data.Sum" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
          data: function () {return {data: {data: {}}}}
        })
        }
      },
      countTemplate: function () {
        return { template: Vue.component('countTemplate', {
          template: `<input type="text" class="form-control tright" :value="data.Count" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
          data: function () {return {data: {data: {}}}}
        })
        }
      },
      dsPhieuGiao: []
    }
  },

  methods: {
    async dialogOpen () {
      await this.getDSLuotGiao()
    },

    dialogClose () {
    },

    openDialog () {
      this.$refs.dlgLuotGiao.show()
    },

    close () {
      this.$refs.dlgLuotGiao.hide()
    },

    onhasStartedGeneration () {
      this.$root.showLoading(true)
    },

    onhasGenerated (evt) {
      this.$root.showLoading(false)
    },

    attemptPrint (blob) {
      let url = URL.createObjectURL(blob)
      window.open(url) // opens the pdf in a new tab
    },

    onLuotGiaoChecked (items) {
      this.luotGiao_grid.checked = this.luotGiao_grid.items.filter(e => items.findIndex(i => i === e.GIAOPHIEU_ID) !== -1)
    },

    onChon () {
      if (this.luotGiao_grid.checked.length <= 0)
        return this.$toast.error('Vui lòng chọn lượt giao để thực hiện!')
      else {
        this.$emit('select', this.luotGiao_grid.checked.map(e => e.GIAOPHIEU_ID))
        this.close()
      }
    },

    async onDSGiao () {
      this.dsPhieuGiao = []
      if (this.luotGiao_grid.checked.length <= 0) {
        this.$toast.error('Vui lòng chọn lượt giao để thực hiện!')
      } else if (this.luotGiao_grid.checked.length > 1) {
        this.$toast.error('Bạn không được chọn nhiều hơn 1 lượt giao để in!')
      } else {
        await this.getBaoCaoDSGiao(this.luotGiao_grid.checked[0].GIAOPHIEU_ID)
        if (this.dsGiao.items.length <= 0) {
          return this.$toast.error('Không có dữ liệu, vui lòng thử lại!')
        }

        // Xuat du lieu:
        const ret = await this.showConfirmPopup('Bạn có muốn xuất Excel không?', () => {
          this.$bvModal.show('dsgiao-modal-xuat-file')
        })

        // In bao cao:
        try {
          this.loading(true)
          let tenNV = this.input.NVTC.TEN_NV
          await this.axios({
            url: '/web-thuno/api/thu-no/bao-cao/giao-in-bao-cao',
            method: 'POST',
            responseType: 'blob',
            data: {
              kyCuoc: this.input.kyCuoc,
              giaoPhieuId: this.luotGiao_grid.checked[0].GIAOPHIEU_ID,
              giaoCho: tenNV,
              ngayGiao: this.luotGiao_grid.checked[0].NGAYGIAO,
              soGBC: this.luotGiao_grid.checked[0].SOLUONG_GBC
            }
          }).then((response) => {
            if (ret === false) previewPrint(response.data)
          })

          // const param = {
          //   page: 0,
          //   size: 10000,
          //   kyCuoc: this.input.kyCuoc, // "20210301",
          //   giaoPhieuId: this.luotGiao_grid.checked[0].GIAOPHIEU_ID
          // }
          // try {
          //   const rs = await this.$root.context.get(
          //     `/web-thuno/api/thu-no/bao-cao/giao-in-bao-cao`,
          //     param
          //   )
          //   if (ret === false) {
          //     this.$root.showLoading(true)
          //     this.dsPhieuGiao = [...rs.data]
          //     this.$refs.html2Pdf2.generatePdf()
          //   }
          // } catch (error) {
          // }
        } catch (error) {
          this.$root.toastError(error.statusText)
          this.loading(false)
        } finally {
          this.loading(false)
        }
      }
    },

    async onBBBanGiao () {
      if (this.luotGiao_grid.checked.length <= 0) {
        this.$toast.error('Vui lòng chọn lượt giao để thực hiện!')
      } else if (this.luotGiao_grid.checked.length > 1) {
        this.$toast.error('Bạn không được chọn nhiều hơn 1 lượt giao để in!')
      } else {
        try {
          this.loading(true)
          await this.axios({
            url: '/web-thuno/api/thu-no/bao-cao/in-bien-ban-ban-giao',
            method: 'POST',
            responseType: 'blob',
            data: {
              kyCuoc: this.input.kyCuoc,
              giaoPhieuId: this.luotGiao_grid.checked[0].GIAOPHIEU_ID
            }
          }).then((response) => {
            previewPrint(response.data)
          })
        } catch (error) {
          this.$root.toastError(error.statusText)
        } finally {
          this.loading(false)
        }
      }
    },

    async onDSTon () {
      if (this.luotGiao_grid.checked.length <= 0) {
        this.$toast.error('Vui lòng chọn lượt giao để thực hiện!')
      } else {
        this.$root.showLoading(true)
        await this.getBaoCaoDSTon(this.luotGiao_grid.checked[0].GIAOPHIEU_ID)
        if (this.dsTon.items.length <= 0) {
          this.$toast.error('Không có dữ liệu, vui lòng thử lại!')
        } else {
          // Xuat du lieu:
          const ret = await this.showConfirmPopup('Bạn có muốn xuất Excel không?', () => {
            this.$bvModal.show('dston-modal-xuat-file')
          })

          // In bao cao:
          try {
            this.loading(true)
            let tenNV = this.input.NVTC.TEN_NV
            await this.axios({
              url: '/web-thuno/api/thu-no/cong-no/bao-cao-in-ds-con-no',
              method: 'POST',
              responseType: 'blob',
              data: {
                kyCuoc: this.input.kyCuoc,
                dsPhieuGiao: this.luotGiao_grid.checked.map(e => e.GIAOPHIEU_ID),
                giaoCho: tenNV,
                ngayGiao: this.luotGiao_grid.checked[0].NGAYGIAO
              }
            }).then((response) => {
              if (ret === false) previewPrint(response.data)
            })
          } catch (error) {
            this.$root.toastError(error.statusText)
          } finally {
            this.loading(false)
          }
        }
      }
    },

    async onHuyGiao () {
      if (this.luotGiao_grid.checked.length <= 0)
        this.$toast.error('Chưa chọn lượt giao để hủy giao!')
      else if (this.luotGiao_grid.checked.length > 1)
        this.$toast.error('Bạn không được chọn nhiều hơn 1 lượt giao hủy giao!')
      else {
        let nguoiCN = this.luotGiao_grid.checked[0].NGUOI_CN.trim()
        if (nguoiCN !== this.$auth.getUserName())
          return this.$toast.error(`Bạn không được hủy giao phiếu do user ${nguoiCN} giao!`)

        await this.showConfirmPopup('Bạn có chắc chắn hủy giao hay không?', async () => {
          let result = await this.updateHuyGiao([this.luotGiao_grid.checked[0].GIAOPHIEU_ID])
          if (result)
            this.$toast.success('Hủy giao phiếu thành công!')

          await this.getDSLuotGiao()
        })
      }
    },

    onXuatExcel () {
      if (this.luotGiao_grid.items.length <= 0)
        return this.$toast.error('Chưa có dữ liệu để xuất excel!')
      else {
        // Không xuất bằng syncfusion được do có aggregate -> Xuất bằng xlsx
        const tHeader = this.luotGiao_grid.columns.map((item) => item.headerText)
        const filterVal = this.luotGiao_grid.columns.map((item) => item.fieldName)
        const data = this.formatJson(filterVal, this.luotGiao_grid.items)
        export_json_to_excel({
          header: tHeader,
          data,
          filename: `DSLuotDaGiao`,
          sheetname: 'export',
          bookType: 'xlsx',
        })
        this.$toast.success('Xuất excel thành công!')
      }
    },

    formatJson (filterVal, jsonData) {
      return jsonData.map((v) =>
        filterVal.map((j) => {
          return v[j]
        })
      )
    },

    async showConfirmPopup (message, fncCallback) {
      let ret = null
      try {
        await this.$bvModal.msgBoxConfirm(message, {
          'title': 'Thông báo',
          'size': 'lg',
          'buttonSize': 'lg',
          'okVariant': 'primary',
          'okTitle': 'Đồng ý',
          'cancelTitle': 'Hủy bỏ',
          'hideHeaderClose': false,
          'centered': true,
          'modal-class': ['f18','text-center'],
        })
          .then(async (value) => {
            if (value) {
              fncCallback()
            }
            ret = value
          })
          .catch(err => {
            // An error occurred
            console.log(err)
            return null
          })
      } catch (error) {
        this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!')
      } finally {
        return ret
      }
    },

    async getDSLuotGiao () {
      try {
        this.loading(true)
        this.luotGiao_grid.key++
        this.luotGiao_grid.items = []
        let apiBody = {
          pChuKyNo: this.input.kyCuoc,
          pNVTCId: this.input.NVTCId,
          pKieu: this.input.kieu,
          pLanTaoId: 0,
        }
        let response = await API.getDSLuotGiao(this.axios, apiBody)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          this.luotGiao_grid.items = response.data.data.map((item) => ({
            ...item,
            NGAYGIAO: item.NGAYGIAO.split(' ')[0].split('-').reverse().join('/'),
          }))
        }
        this.luotGiao_grid.items.sort(function (a, b) {
          return b.GIAOPHIEU_ID - a.GIAOPHIEU_ID
        })
      }
      catch (error) {
        console.log(error)
      }
      finally {
        this.loading(false)
      }
    },

    async getBaoCaoDSGiao (id) {
      try {
        this.loading(true)
        this.dsGiao.items = []
        let apiParams = {
          kyCuoc: this.input.kyCuoc,
          giaoPhieuId: id,
          page: 0,
          size: 10000
        }
        let response = await API.getBaoCaoDSGiao(this.axios, apiParams)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          this.dsGiao.items = response.data.data
        }
      }
      catch (error) {
        console.log(error)
      }
      finally {
        this.loading(false)
      }
    },

    async getBaoCaoDSTon (ds) {
      try {
        this.loading(true)
        this.dsTon.items = []
        let apiParams = {
          kyCuoc: this.input.kyCuoc,
          dsPhieuGiao: ds,
          page: 0,
          size: 10000
        }
        let response = await API.getBaoCaoDSTon(this.axios, apiParams)
        if (response.data.error_code === 'BSS-00000000' && response.data.data.data !== undefined) {
          this.dsTon.items = response.data.data.data
        }
      }
      catch (error) {
        console.log(error)
      }
      finally {
        this.loading(false)
      }
    },

    async updateHuyGiao (ds) {
      try {
        this.loading(true)
        let apiParams = {
          dsGiaoPhieu: ds,
          nguoiHuy: this.$auth.getMaNhanVien(),
          mayHuy: 'test',
          chuKyNo: this.input.kyCuoc,
          loaiDS:1,
        }
        let response = await API.updateHuyGiao(this.axios, apiParams)
        if (response.data.error_code === 'BSS-00000000') {
          return true
        } else {
          this.$toast.error(response.data.message_detail)
          return false
        }
      }
      catch (error) {
        this.$toast.error(error.data.message_detail)
        return false
      }
      finally {
        this.loading(false)
      }
    },
    onProgress (evt) {
      if (evt === 100) this.$root.showLoading(false)
      else this.$root.showLoading(true)
    }
  },

  watch: {
  }
}
</script>
