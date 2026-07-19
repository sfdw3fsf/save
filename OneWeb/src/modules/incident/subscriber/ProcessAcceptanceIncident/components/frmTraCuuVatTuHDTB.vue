<template>
  <div id="dialogfrmTraCuuVatTuHDTB">
    <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogfrmTraCuuVatTuHDTB" :position="position" :close="closeDialog" :header="'Thông tin cấp vật tư cho thuê bao'" showCloseIcon="true" width="80%" target="#app .main-wrapper">
      <div class="">
        <div class="list-actions-top">
          <ul class="list">
            <li>
              <a href="#" @click.prevent="tsbtnExcel_Click"> <span class="icon one-excel"></span> Xuất Excel </a>
            </li>
          </ul>
        </div>
        <div class="popup-body">
          <div class="box-form">
            <div class="legend-title">Thông tin phiếu</div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w60">KQ xử lý</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="txtKQTH" />
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w60">Lý do TH</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="txtLyDo" />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w90">Mục đích cấp</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="txtMucDichCap" />
                  </div>
                </div>
              </div>
            </div>
            <div class="legend-title mart20">Danh sách vật tư</div>
            <DataGrid
              v-bind:columns="gridviewVatTu.headers"
              :enabledSelectFirstRow="false"
              v-bind:dataSource="gridviewVatTu.data"
              @selectedItemsChanged="gridviewVatTu_selectedItemsChanged"
              @rowClicked="gridviewVatTu_FocusedRowChanged"
              :showColumnCheckbox="false"
              :enable-paging-server="false"
              :allowPaging="true"
              :showFilter="true"
              ref="gridviewVatTu"
            >
            </DataGrid>
          </div>
        </div>
        <ExportDataModal ref="exportDataModal" :data="gridviewVatTu.data"></ExportDataModal>
      </div>
    </ejs-dialog>
    <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogfrmTraCuuVatTuHDTBHinhAnh" :position="position" :close="closeDialog" :header="'Thông tin thiết bị'" showCloseIcon="true" width="60%" target="#dialogfrmTraCuuVatTuHDTB">
      <div>
        <img v-bind:src="urlImage" style="width: 100%" />
      </div>
    </ejs-dialog>
  </div>
</template>
<script>
import api from '../Api'
import frmApi from './PopUpApi.js'
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import ExportDataModal from '@/modules/contract/profile/ExportData/ExportDataModal.vue'
export default {
  name: 'frmTraCuuVatTuHDTB',
  components: {
    moment,
    DatePicker,
    api,
    frmApi,
    ExportDataModal
  },
  data() {
    return {
      Loading: false,
      dateFormat: 'DD/MM/YYYY HH:mm:ss',
      typeFormat: 'datetime',
      target: '#app', //  .main-wrapper-payment
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      hdtb_id: 0,
      baohong_id: 0,
      gridviewVatTu: {
        headers: [
          {
            fieldName: 'loai_tbi',
            headerText: 'Tên vật tư',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'seri',
            headerText: 'Seri',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'soluong',
            headerText: 'Số lượng',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'dongia',
            headerText: 'Đơn giá',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'lohang',
            headerText: 'Lô hàng',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: '',
            headerText: 'Mô tả',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'trangbi',
            headerText: 'Trang bị',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'kieu_tbi',
            headerText: 'Kiểu thiết bị',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'tinhtrang_tbi',
            headerText: 'Tình trạng',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'mucdich_cap',
            headerText: 'Lý do cấp',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ngay_th',
            headerText: 'Ngày cấp',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nguoi_cn',
            headerText: 'Người CN',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_dv',
            headerText: 'Đơn vị',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_kho',
            headerText: 'Kho cấp',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'so_phieu',
            headerText: 'Số phiếu',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'trangthai',
            headerText: 'Trạng thái',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        data: [],
        selectedItem: {},
        selectedItems: []
      },
      txtKQTH: '',
      txtLyDo: '',
      txtMucDichCap: '',
      urlImage: '',
      result: false
    }
  },
  watch: {
    Loading: function (val) {
      this.$root.showLoading(val)
    }
  },
  created() {},
  mounted() {},
  methods: {
    openDiaLog(vbaohong_id, vhdtb_id) {
      console.log('openDiaLog', 'vbaohong_id', vbaohong_id, 'vhdtb_id', vhdtb_id)
      this.baohong_id = vbaohong_id
      this.hdtb_id = vhdtb_id
      Promise.all([this.frmTraCuuVatTuHDTB_Load()])
        .then((r) => {
          console.log('Promise.all', r)
        })
        .catch((x) => {
          this.ShowError(`${x.message}`)
        })
        .finally(() => {
          this.$refs.dialogfrmTraCuuVatTuHDTB.show()
        })
    },
    closeDialog() {
      this.$emit('closefrmTraCuuVatTuHDTB', this.result)
    },
    async frmTraCuuVatTuHDTB_Load() {
      // await this.sp_lay_kqxl_huonggiao_thuhoi()
      if (this.hdtb_id != 0) {
        let ds_thuhoi = await this.sp_lay_kqxl_huonggiao_thuhoi()
        if (ds_thuhoi != null && ds_thuhoi.length > 0) {
          this.txtKQTH = ds_thuhoi[0]['ketqua'] == null ? '' : ds_thuhoi[0]['ketqua']
          this.txtLyDo = ds_thuhoi[0]['lydo'] == null ? '' : ds_thuhoi[0]['lydo']
        }
        let ds = await this.sp_lay_ds_vattu_hdtb() // new GiaoPhieu_TbiFacade().Lay_ds_vattu_hdtb(hdtb_id);
        if (ds != null && ds.length > 0) {
          this.txtMucDichCap = ds[0]['lydoto']
          this.gridviewVatTu.data = ds
        } else {
          this.gridviewVatTu.data = []
          this.ShowSuccess(`Không có danh sách vật tư hợp đồng !`)
        }
      } else {
        if (this.baohong_id != 0) {
          let ds = await this.lay_ds_vattu_baohong() // new GiaoPhieu_TbiFacade().Lay_ds_vattu_baohong(baohong_id);
          if (ds != null && ds.length > 0) {
            this.gridviewVatTu.data = ds
          } else {
            this.gridviewVatTu.data = []
            this.ShowSuccess(`Không có danh sách vật tư báo hỏng !`)
          }
        }
      }
      // Rectangle ManHinh = Screen.PrimaryScreen.Bounds;
      // popupIM.Width = ManHinh.Width / 2;
      // popupIM.Height = ManHinh.Height / 2;
    },
    sp_lay_kqxl_huonggiao_thuhoi: async function () {
      //this.hdtb_id
      var input = { hdtb_id: this.hdtb_id } // 2032072
      return this.GetData(await frmApi.sp_lay_kqxl_huonggiao_thuhoi(this.axios, input))
    },
    sp_lay_ds_vattu_hdtb: async function () {
      // this.hdtb_id
      var input = { hdtb_id: this.hdtb_id }
      return this.GetData(await frmApi.sp_lay_ds_vattu_hdtb(this.axios, input))
    },
    lay_ds_vattu_baohong: async function () {
      /*this.baohong_id*/
      var input = { baohong_id: this.baohong_id } // 3889098 this.baohong_id
      return this.GetData(await frmApi.lay_ds_vattu_baohong(this.axios, input))
    },
    fn_lay_mota_vattu_theo_phieuid: async function (phieuvt_id) {
      var input = { phieuvt_id: phieuvt_id }
      return this.GetData(await frmApi.fn_lay_mota_vattu_theo_phieuid(this.axios, input))
    },
    async gridviewVatTu_FocusedRowChanged(i, item) {
      try {
        this.gridviewVatTu.selectedItem = item
        var kt = this.gridviewVatTu.selectedItem['phieuvt_id']
        if (kt != '') {
          let link = await this.fn_lay_mota_vattu_theo_phieuid(kt)
          if (link != -1) await this.load_img(link)
        }
      } catch (err) {
        this.ShowError(`${err}`)
      }
    },
    gridviewVatTu_selectedItemsChanged(selectedItems) {},
    async tsbtnExcel_Click() {
      try {
        if (this.gridviewVatTu.data != null && this.gridviewVatTu.data.length > 0) {
          if (this.gridviewVatTu.data.length > 0) {
            this.$refs.exportDataModal.showModal()
          } else {
            this.$root.$toast.warning('Không có dữ liệu. Bạn hãy kiểm tra lại điều kiện lấy báo cáo !')
          }
        } else {
          this.$toast.error('Không có dữ liệu!')
        }
      } catch (err) {}
    },
    async load_img(link) {
      if (this.link != null && this.link.length > 5) {
        this.urlImage = link
        this.$refs.dialogfrmTraCuuVatTuHDTBHinhAnh.show()
      }
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    ShowAlert: function (msg) {
      this.$toast.warning(msg)
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg)
    },
    ShowError: function (msg) {
      this.$toast.error(msg)
    }
  }
}
</script>
