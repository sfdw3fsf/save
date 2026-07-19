<template>
  <div>
    <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogObjNhacViecBH" :position="position" :close="closeDialog" :header="'Nhắn tin SMS'" showCloseIcon="true" width="80%" target="#app .main-wrapper">
      <div>
        <div class="list-actions-top">
          <ul class="list">
            <li>
              <a href="#" @click.prevent="tsbtnGhiLai_Click"> <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Gửi SMS</a>
            </li>
            <li>
              <a href="#" @click.prevent="Thoat"> <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát</a>
            </li>
          </ul>
        </div>
        <div class="popup-body">
          <div class="box-form">
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="box-form">
                  <div class="row">
                    <div class="tree-plus" style="height: 355px; overflow: auto">
                      <ejs-treeview id="treeviewDonVi" ref="treeObj" :fields="DonViTree" :enablePersistence="true" @nodeSelected="onDonViSelect"></ejs-treeview>
                    </div>
                  </div>
                  <div class="row">
                    <DataGrid
                      :selectionSettings="selectionOptions"
                      @rowClicked="gridviewDanhSach_FocusedRowChanged"
                      @rowDoubleClicked="gridviewDanhSach_DoubleClick"
                      @selectedItemsChanged="gridviewDanhSach_selectedItems"
                      v-bind:columns="gridviewDanhSach.headers"
                      :enabledSelectFirstRow="true"
                      v-bind:dataSource="gridviewDanhSach.data"
                      :showColumnCheckbox="true"
                      :enable-paging-server="false"
                      :allowPaging="true"
                      :showFilter="true"
                    >
                    </DataGrid>
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="box-form">
                  <div class="legend-title">Danh sách nhân viên</div>
                  <div class="info-row">
                    <div class="key w90">Số ĐT</div>
                    <div class="value">
                      <input type="text" v-model="txtSoDT" class="form-control" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w90">ND nhắc mẫu</div>
                    <div class="value SMSTemp">
                      <ComboboxGrid
                        :valueField="SMSTemp.valueField"
                        :textField="SMSTemp.textField"
                        v-model="cboSMSTemp"
                        :value="SMSTemp.valueField"
                        :columns="SMSTemp.headers"
                        :panelDataHeight="SMSTemp.panelDataHeight"
                        :enabledSelectFirstRow="false"
                        :dataSource="SMSTemp.data"
                        :placeHolder="`Chọn nội dung nhắc mẫu ...`"
                        @selectedChanged="smsTemp_selectedChanged"
                      >
                      </ComboboxGrid>
                      <!-- <SelectExt v-model="cboSMSTemp" :placeholder="`Chọn nội dung`" :dataSource="cboSMSTempList" dataTextField="sms" dataValueField="id"></SelectExt> -->
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w90">
                      <p class="text-desc f12 mart5">{{ totalChar }} / 500</p>
                    </div>
                    <div class="value">
                      <textarea type="text" rows="3" v-model="txtNoiDung" class="form-control" maxlength="500" />
                    </div>
                  </div>
                  <div class="note red fw6 article">
                    <span class="one-alert f20"></span> Lưu ý:
                    <ul class="italic">
                      <li>Có thể gửi tin nhắn tới nhiều số cùng lúc, để gửi tin nhập các số điện thoại phân cách nhau bởi dấu chấm phẩy “;”</li>
                      <li>Nhảy đúp vào một dòng trên danh bạ để thêm số điện thoại vào danh sách cần gửi.</li>
                    </ul>
                  </div>
                  <div class="legend-title">Danh sách tin nhắn đã gửi</div>
                  <DataGrid :selectionSettings="selectionOptions" v-bind:columns="dgvTienTrinhSMS.headers" :enabledSelectFirstRow="true" v-bind:dataSource="dgvTienTrinhSMS.data" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true" :showFilter="true"> </DataGrid>
                </div>
              </div>
            </div>
          </div>
        </div>
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
import { DataManager, Query, ODataAdaptor, Predicate } from '@syncfusion/ej2-data'
export default {
  name: 'frmNhacViecBH',
  components: { moment, DatePicker, api, frmApi, DataManager, Query, ODataAdaptor, Predicate },
  props: {
    params: {
      type: Object,
      default: function () {
        return null
      }
    }
  },
  data() {
    return {
      Loading: false,
      target: '#app', //  .main-wrapper-payment
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      txtSoDT: '',
      cboSMSTemp: '',
      cboSMSTempList: [],
      txtNoiDung: '',
      chapnhan: false,
      so_dt: '',
      huonggiao_id: 0,
      loaidv_nhan_id: '',
      phieu_id: 0,
      kieu_nhac: 0,
      thuebao_id: 0,
      selectionOptions: {
        checkboxMode: 'ResetOnRowClick',
        enableToggle: true
      },
      DonViTree: {
        dataSource: null,
        id: 'donvi_id',
        parentID: 'donvi_cha_id',
        text: 'ten_dv',
        hasChildren: 'hasChild'
      },
      SMSTemp: {
        headers: [
          { fieldName: 'id', headerText: 'id', width: '30px' },
          { fieldName: 'sms', headerText: 'sms' }
        ],
        data: [],
        valueField: 'id',
        textField: 'sms',
        panelDataHeight: 200
      },
      dgvTienTrinhSMS: {
        headers: [
          { fieldName: 'nguoi_cn', headerText: 'Người gửi' },
          { fieldName: 'ngay_cn', headerText: 'Ngày gửi' },
          { fieldName: 'do_dt', headerText: 'Số điện thoại' },
          { fieldName: 'nv_nhan', headerText: 'Người nhận' },
          { fieldName: 'noidung', headerText: 'Nội dung' }
        ],
        data: []
      },
      gridviewDanhSach: {
        headers: [
          { fieldName: 'stt', headerText: 'STT', width: '70px' },
          { fieldName: 'ma_nv', headerText: 'Mã NV' },
          { fieldName: 'ten_nv', headerText: 'Tên NV' },
          { fieldName: 'so_dt', headerText: 'SĐT' },
          { fieldName: 'chucdanh', headerText: 'Chức danh' },
          { fieldName: 'vitri', headerText: 'Vị trí' },
          { fieldName: 'diachi_nv', headerText: 'Địa chỉ' }
        ],
        data: [],
        selectedItem: {},
        selectedItems: []
      },
      donviID: 0,
      donviIDCHA: -1,
      muc_id_cha: 0,
      donviBC: 0,
      nhanvien_id: 0,
      ten_nv: '',
      isLoadDB: false,
      strLoadDB: '',
      THOIHAN_NHACVIEC_TTKD: -1,
      vnoidung: '',
      dicNV: []
    }
  },
  watch: {
    // cboSMSTemp: function (val) {
    //   let text = ''
    //   if (this.SMSTemp.data.filter((x) => x.id == val).length > 0) {
    //     text = this.SMSTemp.data.filter((x) => x.id == val)[0].sms
    //   }
    //   if (this.vnoidung != null && this.vnoidung != '') this.txtNoiDung = text + ';' + this.vnoidung
    // },
    Loading: function (val, oldval) {
      this.$root.showLoading(val)
    }
  },
  computed: {
    totalChar: function () {
      return this.txtNoiDung.length
    }
  },
  methods: {
    openDialog() {
      try {
        this.txtNoiDung = ''
        this.getDataFromProp()
        this.Loading = true
        Promise.all([this.frmNhacViecBH_Load()])
          .then((r) => {
            console.log(r)
          })
          .finally((x) => {
            // this.Loading = false
          })
      } catch (ex) {
        console.log(ex)
      } finally {
        this.Loading = false
        this.$refs.dialogObjNhacViecBH.show()
      }
    },
    closeDialog() {
      this.txtSoDT = ''
      this.txtNoiDung = ''
      if (this.SMSTemp.data != null && this.SMSTemp.data.length > 0) this.cboSMSTemp = this.SMSTemp.data[0].id
      else this.cboSMSTemp = -1
    },
    async displayTreview() {
      try {
        let user_map = await this.user_map()
        console.log('user_map', user_map)
        if (user_map == '-1') user_map = ''
        if (user_map != '') {
          this.donviIDCHA = await this.sp_lay_donvi_id_theo_ma_nd(user_map)
          this.donviBC = this.donviIDCHA
          this.muc_id_cha = await this.get_MUC_ID(this.donviIDCHA)
          // this.muc_id_cha = objGiaoSIM.get_MUC_ID(donviIDCHA);
        } else {
          let donviId = this.$root.token.getDonViID()
          this.donviIDCHA = await this.map_donvi_cha() // Convert.ToInt32(new CheckDataFacade().MAP_ID("donvi_cha_id", DatabaseConstants.DB1 + ".donvi", "where donvi_id =" + tt_nd.donvi_id.ToString()));
          console.log('await this.map_donvi_cha()', this.donviIDCHA)
          if (this.donviIDCHA == -1) this.donviIDCHA = donviId
          this.donviBC = this.donviIDCHA
          this.muc_id_cha = await this.get_MUC_ID(this.donviIDCHA) // objGiaoSIM.get_MUC_ID(this.donviIDCHA)
        }
        await this.lay_ds_donvi_phkh(423, 2279, 0)
      } catch (err) {
        this.ShowError(`${err.message}`)
      } finally {
      }
    },
    async LoadTienTrinhSMS() {
      await this.lay_ds_tientrinh_sms()
    },
    Thoat() {
      this.$refs.dialogObjNhacViecBH.hide()
    },
    async onDonViSelect(node) {
      console.log('onDonViSelect', node)
      try {
        this.Loading = true
        let donvi_id = node.nodeData.id
        await this.LoadListView(donvi_id)
      } catch (err) {
        this.ShowError(`${err.message}`)
        console.log(err)
      } finally {
        this.Loading = false
      }
    },
    async onDonViSelect1() {
      // console.log('onDonViSelect')
      try {
        this.Loading = true
        let treeObj = document.getElementById('treeviewDonVi').ej2_instances[0]
        console.log('onDonViSelect', treeObj, treeObj.selectedNodes)
        if (treeObj.selectedNodes.length <= 0) return
        console.log('onDonViSelect', treeObj.selectedNodes[0])
        let donvi_id = treeObj.selectedNodes[0]
        await this.LoadListView(donvi_id)
      } catch (err) {
        this.ShowError(`${err.message}`)
        console.log(err)
      } finally {
        this.Loading = false
      }
    },
    async frmNhacViecBH_Load() {
      try {
        // await this.lay_ds_cay_donvi()
        await this.displayTreview()
        await this.sp_lay_nvtn_theo_baohong_id()
        await this.lay_tinnhan_mau_nhacviec()
        await this.LoadTienTrinhSMS()
        await this.LoadListView(this.donviIDCHA)
        this.vnoidung = await this.lay_noidung_sms_giaoviec()
        let ds_kt1 = await this.LAY_DS_THAMSO_MD()
        if (ds_kt1 != null && ds_kt1.length > 0) {
          let det = ds_kt1.filter((x) => x.ma_ts == 'THOIHAN_NHACVIEC_TTKD')
          if (det.length > 0) {
            let row = det[0]
            if (row['ten_ts'] != '0') this.THOIHAN_NHACVIEC_TTKD = row['ten_ts']
          }
        }
      } catch (err) {
        this.ShowError(`${err.message}`)
      }
    },
    async LoadListView(DonVi_ID) {
      let data = await this.lay_nv_giao_phkh_v2(DonVi_ID, this.loaidv_nhan_id, 0)
      // console.log('LoadListView', data)
      this.gridviewDanhSach.data = data
      if (data == null || data.length == 0) {
        this.Clear()
      } else {
        await this.gridviewDanhSach_FocusedRowChanged(0, data[0])
      }
    },
    Clear() {},
    getDataFromProp() {
      if (this.params != null) {
        let keys = Object.keys(this.params)
        for (const k of keys) {
          console.log('getDataFromProp:', k, ',value:', this.params[k])
          if (this.params[k] != null && this.params[k] != undefined) this[k] = this.params[k]
        }
      }
    },
    gridviewDanhSach_FocusedRowChanged(i, item) {
      try {
        this.nhanvien_id = item.nhanvien_id // Convert.ToInt32(gridviewDanhSach.GetRowCellValue(i, "NHANVIEN_ID"));
        this.ten_nv = item.ten_nv // gridviewDanhSach.GetRowCellValue(i, "TEN_NV").ToString();
        this.so_dt = item.so_dt // gridviewDanhSach.GetRowCellValue(i, "SO_DT").ToString();
      } catch (err) {
        this.ShowError(`${err.message}`)
      }
    },
    gridviewDanhSach_selectedItems(items) {
      this.gridviewDanhSach.selectedItems = items
    },
    gridviewDanhSach_DoubleClick(i, item) {
      try {
        if (item.so_dt == null || item.so_dt == '') {
          this.ShowError(`Số điện thoại trống không thể thêm vào danh sách gửi đi`)
          return
        }
        if (item.so_dt.length <= 3) {
          this.ShowError(`Số điện thoại không hợp lệ không thể thêm vào danh sách gửi đi`)
          return
        }
        if (this.dicNV.filter((x) => x.nhanvien_id == item.nhanvien_id).length <= 0) {
          this.dicNV.push(item)
        }
        this.txtSoDT = this.dicNV
          .filter((x) => x.so_dt != null && x.so_dt != '' && x.so_dt.length > 2)
          .map((x) => x.so_dt)
          .join(';')
        //this.txtSoDT = this.gridviewDanhSach.selectedItems.filter(x=>x.so_dt!=null && x.so_dt!='').map((x) => x.so_dt).join()
      } catch (err) {}
    },
    async sp_lay_nvtn_theo_baohong_id() {
      let input = { baohong_id: this.baohong_id }
      let data = this.GetData(await frmApi.sp_lay_nvtn_theo_baohong_id(this.axios, input))
      // this.gridviewDanhSach.data = data
      return data
    },
    async lay_ds_donvi_phkh(loaidv_id, huonggiao_id, donvi_id) {
      let input = {
        vdonvi_id: donvi_id,
        vhuonggiao_id: huonggiao_id,
        vloaidv_id: loaidv_id
      }
      let data = this.GetData(await frmApi.lay_ds_donvi_phkh(this.axios, input))
      console.log('lay_ds_donvi_phkh', data)
      let donvicha_id = this.donviIDCHA
      let root = await this.get_RootName(this.donviIDCHA)
      console.log('root', root)
      if (root != null && root.length > 0) {
        if (data != null && data.filter((x) => x.donvi_id == root[0].donvi_id).length == 0) {
          // root[0].donvi_cha_id = null
          console.log('insert root', root)
          data.push(root[0])
        } else {
          console.log('exits root', root)
        }
      }
      if (root[0].donvi_cha_id != null) {
        data.forEach(function (item) {
          // item.donvi_id == root[0].donvi_cha_id ||
          if (item.donvi_id == root[0].donvi_id) {
            item.donvi_cha_id = null
          }
        })
        data.forEach(function (item) {
          item.hasChild = data.filter((x) => x.donvi_cha_id == item.donvi_id).length > 1
        })
      } else {
        data.forEach(function (item) {
          // if (item.donvi_cha_id == 0) item.donvi_cha_id = null
          item.hasChild = data.filter((x) => x.donvi_cha_id == item.donvi_id).length > 1
        })
      }
      //let root = await this.get_RootName(this.donviIDCHA)
      console.log(
        'parent is null',
        data.filter((x) => x.donvi_cha_id == null)
      )
      //data.unshift(root.map((x) => ({ donvi_id: x.donvi_id, donvi_cha_id: null, ten_dv: x.ten_dv, hasChild: true })))
      console.log(
        'data.sort',
        data.sort((a, b) => a.ten_dv.localeCompare(b.ten_dv))
      )
      this.DonViTree = {
        dataSource: data.sort((a, b) => a.ten_dv.localeCompare(b.ten_dv)),
        id: 'donvi_id',
        parentID: 'donvi_cha_id',
        text: 'ten_dv',
        hasChildren: 'hasChild'
      }
      return data
    },
    async lay_tinnhan_mau_nhacviec() {
      // da load roi thi ko call lai nua
      if (this.SMSTemp.data != null && this.SMSTemp.data.length > 0) return
      let input = { kieugoi_id: this.kieugoi_id }
      let data = this.GetData(await frmApi.lay_tinnhan_mau_nhacviec(this.axios, input))
      this.cboSMSTempList = data
      this.SMSTemp.data = data
      return data
    },
    async lay_noidung_sms_giaoviec() {
      try {
        let input = { vkieu: 2, vhdtb_id: 0, vthuebao_id: this.thuebao_id, vma_tb: this.ma_tb, vnhiemvu: '', vghichu: '', vphieu_id: this.phieu_id }
        let data = this.GetData(await frmApi.lay_noidung_sms_giaoviec(this.axios, input))
        return data
      } catch (err) {
        return ''
      }
    },
    async SendSMS(sdt, noidung) {
      try {
        let input = { content: noidung, msisdn: sdt, type: 1 }
        let response = await frmApi.send_sms(this.axios, input)
        if (response.data.errorCode == 0) {
        } else {
        }
        console.log('SendSMS', sdt, noidung, response)
      } catch (err) {}
    },
    async smsTemp_selectedChanged(items) {
      // console.log('call smsTemp_selectedChanged')
      if (items != null) {
        // console.log('smsTemp_selectedChanged', items)
        let text = items.sms
        // let val = items
        // if (this.SMSTemp.data.filter((x) => x.id == val).length > 0) {
        //   text = this.SMSTemp.data.filter((x) => x.id == val)[0].sms
        // }
        this.txtNoiDung = text + ';' + this.vnoidung
      }
    },
    async sp_lay_tientrinh_nvn_theo_id() {
      let input = { baohong_id: this.baohong_id }
      let data = this.GetData(await frmApi.sp_lay_tientrinh_nvn_theo_id(this.axios, input))
      return data
    },
    async kiemtra_thoigian_nhacviec_bh(baohong_id, tg_nhac, kieu_nhac) {
      let input = { baohong_id: baohong_id, tg_nhac: tg_nhac, kieu_nhac: kieu_nhac }
      let data = this.GetData(await frmApi.kiemtra_thoigian_nhacviec_bh(this.axios, input))
      return data
    },
    async sp_capnhat_tientrinh_sms(noidung, so_dt, nhanvien_nhan_id, kieu_nhac) {
      let donviId = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      let nguoidungId = this.$root.token.getNguoiDungID()
      let phanvungId = this.$root.token.getPhanVungID()
      let username = this.$root.token.getUserName()
      let input = {
        phanvung_id: phanvungId,
        baohong_id: this.baohong_id,
        phieu_id: this.phieu_id,
        donvi_id: donviId,
        nhanvien_id: nhanvienId,
        nguoidung_id: nguoidungId,
        noidung: noidung,
        so_dt: so_dt,
        nguoi_cn: username,
        may_cn: 'web',
        ip_cn: '127.0.0.1',
        nhanvien_nhan_id: nhanvien_nhan_id,
        kieu_nhac: kieu_nhac
      }
      let data = this.GetData(await frmApi.sp_capnhat_tientrinh_sms(this.axios, input))
      return data
    },
    async user_map() {
      let nguoidungid = this.$root.token.getNguoiDungID()
      //let input = { id_neo: 'user_map', in_table: 'admin.nguoidung', in_dk: `where nguoidung_id= ${nguoidungid}` }
      //let data = this.GetData(await api.map_id(this.axios, input))
      let input = { type: 3, param: nguoidungid}
      let data = this.GetData(await frmApi.fn_tt_nguoidung(this.axios, input))
      return data
    },
    async get_MUC_ID(DONVI_ID) {
      let data = await this.sp_lay_donvi_theo_id(DONVI_ID)
      return data.muc_id
    },
    async map_donvi_cha() {
      let donvi_id = this.$root.token.getDonViID()
      let input = { id_neo: 'donvi_cha_id', in_table: 'admin.donvi', in_dk: `where donvi_id= ${donvi_id}` }
      let data = this.GetData(await api.map_id(this.axios, input))
      return data
    },
    async sp_lay_donvi_id_theo_ma_nd(username) {
      let input = { v_ma_nd: username }
      let data = this.GetData(await frmApi.sp_lay_donvi_id_theo_ma_nd(this.axios, input))
      return data
    },
    async get_RootName(donvi_id) {
      let data = this.GetData(
        await api.lay_dulieu_bang_theo_dieukien(this.axios, {
          schema: 'admin',
          table_name: 'donvi',
          list_of_cols: 'donvi_cha_id,donvi_id,ten_dv',
          where: `donvi_id=${donvi_id} and rownum = 1`,
          order: ''
        })
      )
      return data
      // return await this.sp_lay_donvi_theo_id(donvi_id)
    },
    async sp_lay_donvi_theo_id(donvi_id) {
      let data = this.GetData(
        await api.lay_dulieu_bang_theo_dieukien(this.axios, {
          schema: 'admin',
          table_name: 'donvi',
          list_of_cols: '*',
          where: `donvi_id=${donvi_id} and rownum = 1`,
          order: ''
        })
      )
      return data
      // let input = { v_donvi_id: donvi_id }
      // let data = this.GetData(await frmApi.sp_lay_donvi_theo_id(this.axios, input))
      // return data
    },
    async sp_lay_donvi_theo_id_old(donvi_id) {
      let input = { v_donvi_id: donvi_id }
      let data = this.GetData(await frmApi.sp_lay_donvi_theo_id(this.axios, input))
      return data
    },
    async lay_nv_giao_phkh_v2(donvi_id, loaidv_nhan_id, huonggiao_id) {
      let input = { vdonvi_id: donvi_id, vloaidv_nhan_id: loaidv_nhan_id, vhuonggiao_id: huonggiao_id }
      let data = this.GetData(await frmApi.lay_nv_giao_phkh_v2(this.axios, input))
      return data
    },
    async lay_ds_tientrinh_sms() {
      let input = { baohong_id: this.baohong_id }
      let data = this.GetData(await frmApi.lay_ds_tientrinh_sms(this.axios, input))
      this.dgvTienTrinhSMS.data = data
      return data
    },
    async lay_ds_cay_donvi() {
      let input = {}
      let response = this.GetData(await frmApi.lay_ds_cay_donvi(this.axios, input))
      let data = response.ds_donvi
      data.forEach(function (item) {
        item.hasChild = data.filter((x) => x.donvi_cha_id === item.donvi_id).length > 1
        if (item.donvi_cha_id === 0 || item.donvi_cha_id == null) {
          item.expanded = true
        }
      })
      console.log('lay_ds_cay_donvi', response)
      return data
    },
    async LAY_DS_THAMSO_MD() {
      let input = { kieu_id: 0 }
      let data = this.GetData(await api.lay_ds_thamso_macdinh(this.axios, input))
      return data
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
    },
    async tsbtnGhiLai_Click() {
      if (this.txtSoDT != '') {
        // if (this.txtSoDT.Text.ToCharArray().Any(c => !char.IsDigit(c) && c != ';'))
        // {
        //     Message_Box.ShowWarning("Số điện thoại nhập vào không đúng");
        //     return;
        // }
      }
      if (this.txtNoiDung == '') {
        this.ShowError('Bạn chưa nhập nội dung tin nhắn')
        return
      }
      if (this.THOIHAN_NHACVIEC_TTKD > 0 && this.kieu_nhac != 0) {
        let kt = await this.kiemtra_thoigian_nhacviec_bh(this.baohong_id, this.THOIHAN_NHACVIEC_TTKD, this.kieu_nhac) // new BaoHong_Facade().Kiemtra_thoigian_nhacviec_bh(baohong_id, THOIHAN_NHACVIEC_TTKD, kieu_nhac);
        if (kt.toLowerCase() != 'ok') {
          this.ShowError(kt)
          return
        }
      }
      try {
        if (this.txtSoDT != '') {
          // #region Nhắn tin số đt trên text
          let selectedItems = this.gridviewDanhSach.data
          let arrSDT = this.txtSoDT.split(';')
          let kieunhac = this.kieu_nhac
          for (const sdt of arrSDT) {
            if (sdt != '') {
              // Gửi SMS
              var noidung = this.txtNoiDung
              await this.SendSMS(sdt, noidung)
              let vnhanvien_nhan_id = -1
              if (selectedItems.filter((x) => x.so_dt == sdt).length > 0) {
                vnhanvien_nhan_id = selectedItems.filter((x) => x.so_dt == sdt)[0].nhanvien_id
              }
              await this.sp_capnhat_tientrinh_sms(noidung, sdt, vnhanvien_nhan_id, kieunhac)
              // // Lưu log
              // let vdonvi_id = tt_nd.donvi_id;
              // let vnhanvien_id = tt_nd.nhanvien_id;
              // let vnguoidung_id = tt_nd.nguoidung_id;
              // let vnoidung = "'" + noidung.Replace("'", "''") + "'";
              // let vso_dt = "'" + sdt.Replace("'", "''") + "'";
              // var vngay_cn = "sysdate";
              // var vnguoi_cn = "'" + tt_nd.ma_nd.Replace("'", "''") + "'";
              // var vip_cn = "'" + tt_nd.ip.Replace("'", "''") + "'";
              // int vnhanvien_nhan_id = -1;
              // if (dicNV.ContainsValue(sdt.ToString()))
              // {
              //     List<int> dsNV = (from kvp in dicNV where kvp.Value == sdt select kvp.Key).ToList();
              //     vnhanvien_nhan_id = dsNV[0];
              // }
              // var str = "";
              // str += " INSERT INTO " + DatabaseConstants.DB7 + ".tientrinh_sms (tientrinhsms_id, baohong_id, donvi_id, nhanvien_id, "
              //     + " nguoidung_id, noidung, so_dt, ngay_cn, nguoi_cn, ip_cn ";
              // if (vnhanvien_nhan_id != -1)
              //     str += " ,nhanvien_nhan_id";
              // if (THOIHAN_NHACVIEC_TTKD > 0 && kieu_nhac != 0)
              // {
              //     str += " ,kieu_nhac";
              // }
              // str += ")";
              // str += " VALUES (";
              // str += DatabaseConstants.DB1 + ".get_keys('TIENTRINH_SMS'), ";
              // str += baohong_id + ", ";
              // str += vdonvi_id + ", ";
              // str += vnhanvien_id + ", ";
              // str += vnguoidung_id + ", ";
              // str += vnoidung + ", ";
              // str += vso_dt + ", ";
              // str += vngay_cn + ", ";
              // str += vnguoi_cn + ", ";
              // str += vip_cn;
              // if (vnhanvien_nhan_id != -1)
              //     str += " ," + vnhanvien_nhan_id;
              // if (THOIHAN_NHACVIEC_TTKD > 0 && kieu_nhac != 0)
              // {
              //     str += " ," + kieu_nhac;
              // }
              // str += ")";
              // bangts.ExeSQL(str);
            }
          }
          // #endregion
        }
        // new BaoHong_Facade().Nhantin_nv_qldb_v2(phieu_id, baohong_id, txtNoiDung.Text.Trim(), tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.nguoidung_id,
        //     tt_nd.may_cn, tt_nd.ma_nd, tt_nd.ip, kieu_nhac);
        await this.LoadTienTrinhSMS()
        this.ShowSuccess('Gửi tin nhắn thành công')
      } catch (ex) {
        this.ShowError(ex.message)
      }
    }
  }
}
</script>
<style scoped>
.SMSTemp .column-selected-flag {
  width: 15px !important;
  display: none !important;
}
</style>