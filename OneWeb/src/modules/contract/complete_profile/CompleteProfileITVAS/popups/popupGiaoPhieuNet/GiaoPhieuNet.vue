<style scoped>
.list-actions-top .list > li > div.button {
  display: block;
  padding: 2px 15px;
  color: #0176ff;
  border-left: 1px solid #bae7ff;
  text-decoration: none;
  font-weight: 600;
  cursor: pointer;
}
</style>
<template>
  <ejs-dialog :enableResize="true" :isModal="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogGiaoPhieuNet" :position="position" :header="'Giao phiếu NET'" showCloseIcon="true" width="75%" target="#app .main-wrapper">
    <div class="dialogCreateIssue">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript:;" v-on:click="tsbtnTimKiem_Click"> <span class="icon one-file-attach"></span> Tìm kiếm </a>
          </li>
          <li>
            <a href="javascript:;" v-on:click="tsbtnGiaoPhieu_Click"> <span class="icon one-save"></span> Giao phiếu </a>
          </li>
          <li>
            <a href="javascript:;" v-on:click="tsbtnHuyGiao_Click"> <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Hủy giao </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Giao phiếu NET</div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="row">
                <div class="col-sm-7 col-12">
                  <div class="info-row">
                    <div class="key">Dịch vụ</div>
                    <div class="value">
                      <select2 v-model="controls.cboDichVuVT.value" class="select-custom" :options="controls.cboDichVuVT.list"></select2>
                    </div>
                  </div>
                </div>
                <div class="col-sm-5 col-12">
                  <div class="info-row">
                    <div class="key">Ngày YC</div>
                    <div class="value">
                      <div class="input-icon-right">
                        <date-picker input-class="form-control" :format="dateFormat" :value-type="'format'" v-model="controls.txtNgayYC" :disabled="true"></date-picker>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-7 col-12">
                  <div class="info-row">
                    <div class="key">Mã GD</div>
                    <div class="value">
                      <div class="input-more-button">
                        <button class="btn" @click="btnLayMaHD_Click">
                          <span class="-ap icon-more_horiz"></span>
                        </button>
                        <input ref="txtMaGD" type="text" class="form-control" v-model="controls.txtMaGD" @keypress.enter="txtMaGD_KeyPress" />
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-5 col-12">
                  <div class="info-row">
                    <div class="key">Loại HĐ/PL</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="controls.txtLoaiHD" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-7 col-12">
                  <div class="info-row">
                    <div class="key">Số máy/Acc</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="controls.txtMa_TB" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-5 col-12">
                  <div class="info-row">
                    <div class="key">Tốc độ</div>
                    <div class="value">
                      <select2 v-model="controls.cboTocDo.value" class="select-custom" :options="controls.cboTocDo.list"></select2>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key">Kiểu LĐ</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="controls.txtKieuLD" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key">Địa chỉ LĐ đầu</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="controls.txtDiaChi_LD_Dau" />
                </div>
              </div>
              <div class="info-row">
                <div class="key">Địa chỉ LĐ cuối</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="controls.txtDiaChi_LD_Cuoi" />
                </div>
              </div>
              <div class="info-row">
                <div class="key">Ghi chú</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="controls.txtGhiChu" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="row">
                <div class="col-sm-7 col-12">
                  <div class="info-row">
                    <div class="key">Tên KH</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="controls.txtTenKH" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-5 col-12">
                  <div class="info-row">
                    <div class="key">ĐT liên hệ</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="controls.txtDTLienLac" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key">Địa chỉ KH</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="controls.txtDiachiKH" />
                </div>
              </div>
              <div class="row">
                <div class="col-sm-7 col-12">
                  <div class="info-row">
                    <div class="key">Người lập HĐ</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="controls.txtNguoiLHD" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-5 col-12">
                  <div class="info-row">
                    <div class="key">Số điện thoại</div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="controls.txtDienThoai" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key">Hướng giao</div>
                <div class="value">
                  <select2 v-model="controls.cboHuongGiao.value" class="select-custom" :options="controls.cboHuongGiao.list"></select2>
                </div>
              </div>
              <div class="info-row">
                <div class="key">Đơn vị</div>
                <div class="value">
                  <treeselect placeholder="Chọn đơn vị" ref="treeDonvi" name="treeDonvi" id="treeDonvi" v-model="controls.pceDonVi.value" :multiple="true" :options="controls.pceDonVi.list" @select="loaikhChanged"> </treeselect>
                </div>
              </div>
              <div class="info-row">
                <div class="key">Nội dung giao</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="controls.txtNoiDung_Giao" />
                </div>
              </div>
            </div>
          </div>
          <div class="box-form">
            <div class="row">
              <div class="col-sm-12 col-12">
                <div class="table-content">
                  <DataGrid ref="dgvChiTiet" class="table-result table-gachle" v-bind:columns="dgvChiTiet.headers" v-bind:dataSource="dgvChiTiet.list" @selectedRowChanged="gridChitiet_FocusedRowChanged" :enable-paging-server="false" :allowPaging="true" :showFilter="true"> </DataGrid>
                </div>
              </div>
            </div>
          </div>
        </div>
        <SearchContractModal ref="popupSearchContract" @accept="acceptSearchContract" :loai_hd_id="0" :dichvu_id="0" :ngay_yc="''" :ngay_kt="''" :trangthai_hd="0" />
      </div>
    </div>
  </ejs-dialog>
</template>
<script>
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import moment from 'moment'
import api from './PopUpApi'
import { DICHVU_VIENTHONG, TRANGTHAI_PH } from '@/constants'
import commonFn from '@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions'
import SearchContractModal from '@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue'

export default {
  emits: ['tsbtnGiaoPhieu_Click'],
  components: {
    moment,
    DatePicker,
    api,
    SearchContractModal
  },
  name: 'GiaoPhieuNet',
  data() {
    return {
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: '',
      popupComponentAttr: {},
      popupComponentEvts: {
        'form-close': this.popupClosed,
        acceptSearchContract: this.acceptSearchContract
      },
      Loading: false,
      dateFormat: 'DD/MM/YYYY',
      dateFormatMonth: 'MM/YYYY',
      typeFormat: 'datetime',
      typeFormatMonth: 'month',
      dateConfig: {
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      target: '#app', //  .main-wrapper-payment
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      showTimePanel: false,
      tabIndex: 0,
      donvi_list: [],
      fieldsDonVi: '',
      state: {},
      tt_nd: {
        nhanvien_id: this.$root.token.getNhanVienID() * 1,
        nguoidung_id: this.$root.token.getNguoiDungID() * 1,
        phanvung_id: this.$root.token.getPhanVungID() * 1,
        donvi_id: this.$root.token.getDonViID() * 1,
        may_cn: this.$root.token.getMachine(),
        ip_cn: this.$root.token.getIP(),
        ngay_cn: moment(new Date()).format('DD/MM/YYYY hh:mm A'),
        ngay_bg: moment(new Date().setSeconds(new Date().getSeconds() + 30)).format('DD/MM/YYYY hh:mm A'),
        ten_dv: '',
        nguoi_cn: this.$root.token.getUserName()
      },

      controls: {
        cboDichVuVT: { list: [], value: 0 },
        cboTocDo: { list: [], value: 0 },
        cboHuongGiao: { list: [], value: 0 },
        pceDonVi: {
          list: [],
          textField: 'ten_dv',
          valueField: 'donvi_id',
          panelDataHeight: 'auto',
          headers: [
            {
              fieldName: 'ten_dv',
              headerText: 'Tên đơn vị',
              allowFiltering: true,
              allowSorting: false
            }
          ],
          value: null
        }
      },
      dgvChiTiet: {
        headers: [
          { fieldName: 'huonggiao', headerText: 'Hướng giao', allowFiltering: true, isGroupedColumn: true },
          { fieldName: 'dvg', headerText: 'Đơn vị giao', allowFiltering: true },
          { fieldName: 'ngay_giao', headerText: 'Ngày giao', allowFiltering: true, type: 'dateTime', format: 'dd/MM/yyyy HH:mm:ss', textAlign: 'right' },
          { fieldName: 'nv_giao', headerText: 'Người giao', allowFiltering: true },
          { fieldName: 'nd_giao', headerText: 'Nội dung giao', allowFiltering: true },
          { fieldName: 'dvn', headerText: 'Đơn vị nhận', allowFiltering: true },
          { fieldName: 'ngay_tra', headerText: 'Ngày trả', allowFiltering: true, type: 'dateTime', format: 'dd/MM/yyyy HH:mm:ss', textAlign: 'right' },
          { fieldName: 'nd_tra', headerText: 'Nội dung trả', allowFiltering: true },
          { fieldName: 'lydotra', headerText: 'Lý do trả', allowFiltering: true },
          { fieldName: 'ngay_th', headerText: 'Ngày CN', allowFiltering: true, type: 'dateTime', format: 'dd/MM/yyyy HH:mm:ss', textAlign: 'right' },
          { fieldName: 'nv_th', headerText: 'Người CN', allowFiltering: true },
          { fieldName: 'so_dt', headerText: 'Điện thoại liên hệ', allowFiltering: true },
          { fieldName: 'trangthai_ph', headerText: 'Trạng thái phiếu', allowFiltering: true }
        ],
        list: []
      }
    }
  },
  created: async function () {},
  mounted: function () {},
  watch: {},
  methods: {
    dialogClose() {},
    openDialog(data) {
      if (!data) {
        return
      }
      this.state = {
        vphieu_cha_id: data.phieu_cha_id,
        vphieu_tinh_id: data.phieu_tinh_id,
        vdonvi_ql_id: data.donvi_ql_id,
        tinh_id: data.tinh_id
      }
      console.log(this.state)
      this.$refs.dialogGiaoPhieuNet.show()
      this.pageLoad()
    },
    hideDialog() {
      this.$refs.dialogGiaoPhieuNet.hide()
    },
    list2treeNew(list) {
      let rootList = list.filter((x) => x.donvi_cha_id == null)
      let openList = []
      rootList.forEach((item) => {
        item.id = item.donvi_id
        item.label = item.ten_dv ? item.ten_dv : 'Null'
        item.level = 0
        openList.push(item)
        let children = list.filter((x) => x.donvi_cha_id == item.donvi_id && x.donvi_cha_id != null)
        if (children.length > 0) {
          item.children = children
          children.forEach((childItem) => {
            childItem.level = item.level + 1
            childItem.id = childItem.donvi_id
            childItem.label = childItem.ten_dv ? childItem.ten_dv : 'Null'
            openList.push(childItem)
          })
        }
      })
      let rootList_new = openList.filter((x) => x.donvi_cha_id == null)
      console.log(22222222)
      console.log(rootList_new)
      return rootList_new
    },

    loaikhChanged() {},
    async pageLoad() {
      try {
        this.loading(true)
        this.tt_nd.may_cn = await this.$root.token.getMachine()
        this.tt_nd.ip_cn = await this.$root.token.getIP()

        let data = this.GetData(await api.CSS_DICHVU_VT(this.axios, {}))
        if (data && data.length > 0) {
          data = this.LowerCasePropertyList(data)
          this.controls.cboDichVuVT.list = data.map((x) => {
            return { id: x.dichvuvt_id, text: x.ten_dvvt }
          })
          this.controls.cboDichVuVT.value = this.controls.cboDichVuVT.list[0].id
        }
        data = this.GetData(await api.ht_tocdo_kenh_combobox(this.axios, { matinh: this.state.tinh_id }))
        if (data && data.length > 0) {
          data = this.LowerCasePropertyList(data)
          this.controls.cboTocDo.list = data.map((x) => {
            return { id: x.tocdo_id, text: x.toc_do }
          })
          this.controls.cboTocDo.value = this.controls.cboTocDo.list[0].id
        }
        data = this.GetData(await api.lay_chitiet_huonggiao(this.axios, { huonggiao_id: 8888 }))
        if (data && data.length > 0) {
          data = this.LowerCasePropertyList(data)
          this.controls.cboHuongGiao.list = data.map((x) => {
            return { id: x.huonggiao_id, text: x.huonggiao }
          })
          this.controls.cboHuongGiao.value = this.controls.cboHuongGiao.list[0].id
        }

        await this.Load_TT_TB()
        data = this.GetData(await api.ht_tvdonvi_giaophieu_net(this.axios, { vdonvi_ql_id: this.state.vdonvi_ql_id }))
        if (data && data.length > 0) {
          data = this.LowerCasePropertyList(data)
          this.donvi_list = data
          //this.controls.pceDonVi.list = data.map((x) => {return {donvi_id: x.donvi_id ? x.donvi_id: 0, ten_dv: x.ten_dv}});
          this.controls.pceDonVi.list = this.list2treeNew(data)
          //this.controls.pceDonVi.value = this.controls.pceDonVi.list[0].id;
          //this.fieldsDonVi = { dataSource: data, id: 'donvi_id', text: 'ten_dv', parentID: 'donvi_cha_id', hasChildren: 'hasChild' }
          console.log('ht_tvdonvi_giaophieu_net', this.controls.pceDonVi.list)
        }
      } catch (ex) {
        console.error(ex)
        this.$toast.error(ex.message)
        console.error(ex.message)
      }
      this.loading(false)
    },
    async Load_TT_TB() {
      let dtTemp = this.GetData(await api.load_tt_tb_giaophieu_net(this.axios, { vphieu_cha_id: this.state.vphieu_cha_id, tinh: this.state.tinh_id }))
      if (dtTemp && dtTemp.length > 0) {
        dtTemp = this.LowerCasePropertyList(dtTemp)
        this.controls.txtMa_TB = dtTemp[0]['ma_tb']
        this.state.hdtb_id = dtTemp[0]['hdtb_id']
        this.state.tthd_id = dtTemp[0]['tthd_id']
        this.controls.txtGhiChu = dtTemp[0]['ghichu']
        this.controls.cboDichVuVT.value = dtTemp[0]['dichvuvt_id']
        this.controls.txtKieuLD = dtTemp[0]['ten_kieuld']
        this.state.dichvuvt_id = dtTemp[0]['dichvuvt_id']
        this.controls.txtTenKH = dtTemp[0]['ten_kh']
        this.controls.txtDiachiKH = dtTemp[0]['diachi_kh']
        this.controls.txtNguoiLHD = dtTemp[0]['nguoilap_hd']
        this.controls.txtDTLienLac = dtTemp[0]['so_dt']
        this.controls.txtNgayYC = moment(dtTemp[0]['ngaygiao'], 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY') //2021-06-04 09:43:47
        this.controls.txtMaGD = dtTemp[0]['ma_gd']
        this.controls.txtLoaiHD = dtTemp[0]['ten_loaihd']
      }
      await this.HienThiDS_PhieuTH(this.state.vphieu_cha_id)
      if (this.state.dichvuvt_id == DICHVU_VIENTHONG.MEGAWAN || this.state.dichvuvt_id == DICHVU_VIENTHONG.METRONET) {
        let ds_tocdo = this.GetData(await api.lay_tocdo_giaophieu_net(this.axios, { vhdtb_id: this.state.hdtb_id, tinh: this.state.tinh_id }))
        ds_tocdo = this.LowerCasePropertyList(ds_tocdo)
        if (ds_tocdo && ds_tocdo.length > 0) {
          this.controls.cboTocDo.value = Number(ds_tocdo[0]['tocdo_id'])
        } else {
          this.controls.cboTocDo.value = -1
        }
      } else if (this.state.dichvuvt_id == DICHVU_VIENTHONG.TSL) {
        let ds_tocdo = this.GetData(await api.lay_diachi_daucuoi_giaophieu_net(this.axios, { vhdtb_id: this.state.hdtb_id, tinh: this.state.tinh_id }))
        if (ds_tocdo && ds_tocdo.length > 0) {
          ds_tocdo = this.LowerCasePropertyList(ds_tocdo)
          this.controls.cboTocDo.value = Number(ds_tocdo[0]['tocdo_id'])
          for (const item of ds_tocdo) {
            if (item['daucuoi_id'] == '1') {
              this.controls.txtDiaChi_LD_Dau = item['diachi']
            }
            if (item['daucuoi_id'] == '2') {
              this.controls.txtDiaChi_LD_Cuoi = item['diachi']
            }
          }
        } else {
          this.controls.cboTocDo.value = -1
          this.controls.txtDiaChi_LD_Dau = ''
          this.controls.txtDiaChi_LD_Cuoi = ''
        }
      } else {
        this.controls.cboTocDo.value = -1
        this.controls.txtDiaChi_LD_Dau = ''
        this.controls.txtDiaChi_LD_Cuoi = ''
      }
    },
    async HienThiDS_PhieuTH(phieu_id) {
      let myDataTables = null
      if ([97, 100].includes(this.tinh_id)) {
        myDataTables = this.GetData(
          await api.lay_danhsach_phieu_net_con(this.axios, {
            vphieu_cha_id: this.state.tinh_id
          })
        )
      } else {
        myDataTables = this.GetData(
          await api.lay_danhsach_phieu_net_con_bancheo(this.axios, {
            vphieu_cha_id: phieu_id,
            vphanvung_id: this.$root.token.getPhanVungID()
          })
        )
      }
      // let myDataTables = this.GetData(await api.lay_danhsach_phieu_net_con(this.axios, {vphieu_cha_id: phieu_id, tinh: this.state.tinh_id}));
      this.dgvChiTiet.list = this.LowerCasePropertyList(myDataTables)
    },
    async btnLayMaHD_Click(e) {
      // this.popupComponent = () => import("@/modules/contract/setup/ChangeInstallUnit/popup/SearchContract/SearchContractModal.vue");
      // this.popupComponentName = "popupLayMaHD";
      // var data = {
      //   loai_hd_id: 0,
      //   kieu: 1,
      //   donvi_dl_id: this.tt_nd.donvi_id,
      //   dichvu_id: 0
      // };

      // this.state.modalTitle = "Tra cứu hợp đồng, phụ lục";
      // this.popupComponentAttr = data;
      // console.log("popupLayMaHD DATA", data);
      // this.Popup("popupComponentsGiaoPhieuNet");
      this.$refs.popupSearchContract.showModal()
    },
    async acceptSearchContract(item) {
      console.log('acceptSearchContract', item)
      if (item && item.ma_gd) {
        this.controls.txtMaGD = item.ma_gd
        this.Search()
      }
      this.$refs.txtMaGD.focus()
    },
    async txtMaGD_KeyPress() {
      this.state.ma_tk = this.controls.txtMaGD
      this.Search()
    },
    async tsbtnTimKiem_Click() {
      this.state.ma_tk = this.controls.txtMaGD
      this.Search()
    },
    async Search() {
      try {
        this.loading(true)
        let ds = this.GetData(await api.lay_ds_hd_theo_ma_tb(this.axios, { ma_tb: this.controls.txtMaGD, tinh: this.state.tinh_id }))
        if (!ds || ds.length == 0) {
          this.loading(false)
          this.$toast.warning('Không tìm thấy hợp đồng')
          this.$refs.txtMaGD.focus()
          this.Clear()
          return
        }
        if (ds.length > 1) {
          //TODO
        }
        let row = ds[0]
        this.state.hdkh_id = ds[0].hdkh_id * 1
        this.state.khachhang_id = 0
        if (row.khachhang_id && row.khachhang_id > 0) this.state.khachhang_id = row.khachhang_id * 1
        this.state.tthd_id = row.tthd_id
        this.controls.txtDTLienLac = row.so_dt
        this.controls.txtMaGD = row.ma_gd
        this.controls.txtTenKH = commonFn.ChuanHoaTen(row.ten_kh)
        this.controls.txtDiachiKH = commonFn.ChuanHoaTen(row.diachi_kh)
        this.controls.txtNgayYC = moment(row.ngay_yc, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY')
        this.controls.txtNguoiNhan = commonFn.ChuanHoaTen(row.ten_nv)
        this.controls.txtLoaiHD = row.ten_loaihd
        await this.LoadDS_HopDongTB(this.state.hdkh_id)
        let nguoilap_hd = await commonFn.MAP_ID(this.axios, 'nguoilap_hd', 'css.hd_khachhang', 'where hdkh_id = ' + this.state.hdkh_id)
        if (nguoilap_hd != '-1') this.controls.txtNguoiLHD = nguoilap_hd
        else this.controls.txtNguoiLHD = ''
      } catch (ex) {
        console.error(ex)
        this.$toast.error(ex)
      }
      this.loading(false)
    },
    async LoadDS_HopDongTB() {},
    async gridChitiet_FocusedRowChanged(row) {
      if (!row) return
      this.state.huonggiao_id = row.huonggiao_id
      this.state.phieu_id = row.phieu_id
      this.state.ttph_id = row.ttph_id
      if (row.nhanvien_th_id != '') this.state.nvth_id = row.nhanvien_th_id
      else this.state.nvth_id = 0
      this.state.cv_th = row.nhiemvu
    },
    async pceDonVi_changed(e) {
      console.log('pceDonVi_changed', e)
    },
    async Getkey_(keyname) {
      let result = ''
      console.log(333333)
      try {
        let params = {
          keyname: keyname,
          numblocksize: 0,
          numretry: 0
        }
        let response = await api.get_keys(this.axios, params)
        console.log(response)
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          result = response.data.data
        }
      } catch (e) {
        console.log(e)
      }
      return result
    },
    async tsbtnGiaoPhieu_Click() {
      console.log('tsbtnGiaoPhieu_Click')
      try {
        this.loading(true)
        let vdonvi_id = this.controls.pceDonVi.value
        if (vdonvi_id == null || vdonvi_id == '-1' || vdonvi_id == '0') {
          this.$toast.warning('Bạn chưa chọn đơn vị nhận.')
          //pceDonVi.Focus();
          this.loading(false)
          return
        }
        let vdonvi_dagiao = ''
        let item_ = {}
        for (let i = 0; i < vdonvi_id.length; i++) {
          item_ = this.donvi_list.find((item) => item.donvi_id === vdonvi_id[i])
          console.log(22222222222)
          console.log(vdonvi_id[i])
          console.log(item_)
          let req = {
            vhdtb_id: this.state.hdtb_id,
            vdonvi_id: vdonvi_id[i],
            vtendv: item_.ten_dv,
            vphanvungbc_id: this.state.tinh_id
          }
          let dt = this.GetData(await api.fn_check_thongtin_donvi_giao_bancheo(this.axios, req))

          console.log(dt)
          if (dt != 'OK') {
            vdonvi_dagiao += ',' + item_.ten_dv
          }
        }

        if (vdonvi_dagiao != '') {
          this.$toast.warning('Đã giao phiếu cho đơn vị ' + vdonvi_dagiao.substring(1) + '!')
          this.loading(false)
          return
        }
        for (let i = 0; i < vdonvi_id.length; i++) {
          let key = await this.Getkey_('GIAOPHIEU')
          let req = {
            vphanvungbc_id: this.state.tinh_id,
            vhdtb_id: this.state.hdtb_id,
            vphieu_id: key,
            vphieucha_id: this.state.vphieu_cha_id,
            vdonvinhan_id: vdonvi_id[i],
            vdonvigiao_id: this.tt_nd.donvi_id,
            vnhanvien_id: this.tt_nd.nhanvien_id,
            vnguoicn: this.tt_nd.nguoi_cn,
            vmaycn: this.tt_nd.may_cn,
            vipcn: this.tt_nd.ip_cn,
            vnd_giao: this.controls.txtNoiDung_Giao
          }
          let res = this.GetData(await api.fn_thuchien_giaophieu_frm_giaophieu_net_khdn(this.axios, req))
        }
        this.$toast.success('Giao phiếu thành công !')
        await this.HienThiDS_PhieuTH(this.state.vphieu_cha_id)
        this.$emit('tsbtnGiaoPhieu_Click', { status: 1 })
      } catch (ex) {
        console.error(ex)
        this.$toast.error(ex)
      }
      this.loading(false)
    },
    async tsbtnHuyGiao_Click() {
      console.log('tsbtnHuyGiao_Click')
      try {
        if (this.state.ttph_id != TRANGTHAI_PH.MOI) {
          this.$toast.warning('Trạng thái phiếu khác Mới. Bạn không thể hủy !')
          return
        }
        if (this.state.huonggiao_id != 8888) {
          this.$toast.warning("Chỉ được hủy giao với hướng giao 'Net phối hợp' !")
          return
        }
        this.loading(true)
        req = {
          vphanvungbc_id: this.state.tinh_id,
          vphieu_id: this.state.phieu_id,
          vhdtb_id: this.state.hdtb_id
        }
        let res = this.GetData(await api.fn_huygiao_frm_giaophieu_net(this.axios, req))
        if (res == 'ok') {
          this.$toast.success('Hủy giao phiếu thành công !')
          await this.HienThiDS_PhieuTH(this.state.vphieu_cha_id)
        } else {
          this.$toast.error(res)
        }
      } catch (ex) {
        console.error(ex)
        this.$toast.error(ex)
      }
      this.loading(false)
    },
    async Clear() {
      this.controls.txtNguoiLHD = ''
      this.state.ngay_ht = ''
      this.state.phieu_id = 0
      this.state.ttph_id = 0
      this.state.hdtb_id = 0
      this.state.thuebao_id = 0
      this.state.dichvuvt_id = 0
      this.controls.txtKieuLD = ''
      this.controls.txtMaGD = ''
      this.controls.txtLoaiHD = ''
      this.controls.txtGhiChu = ''
      this.controls.txtTenKH = ''
      this.controls.txtDiachiKH = ''
      this.controls.txtNgayYC = ''
      this.controls.txtMa_TB = ''
      this.controls.txtDTLienLac = ''
      this.dgvChiTiet.list = []
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200' || response.data.error === '204' || response.data.errorCode === 0) {
        return response.data.data
      } else {
        console.log(response.data.error_code)
        console.log(response.data.faultString)
        if (response.data && response.data.faultString) {
          throw response.data.faultString
        }
      }
      return null
    },

    ShowAlert: function (msg) {
      this.$toast.warning(msg)
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg)
    },
    ShowError: function (msg) {
      this.$toast.error(msg)
    },
    Popup(modalId) {
      this.$bvModal.show(modalId)
    },
    async popupClosed(val) {
      console.log('popupClosed', val)
    },
    LowerCasePropertyList(obj) {
      if (!obj) return []
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toLowerCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },
    UpperCasePropertyList(obj) {
      if (!obj) return []
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toUpperCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    }
  }
}
</script>
<style>
.chuyenTo .page-content {
  top: 130px !important;
}
</style>