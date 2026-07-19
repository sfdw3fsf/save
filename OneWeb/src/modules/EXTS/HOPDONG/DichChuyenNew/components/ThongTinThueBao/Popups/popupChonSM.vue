<template>
<div class="modal-content popup-box">
    <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>Chọn số máy</div>
        <div class="close -ap icon-close" data-dismiss="modal" @click.prevent="closeForm">
        </div>
    </div>
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click.prevent="chapnhan">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận (F5)
                </a>
            </li>
            <li>
                <a href="#" :class="{disabled:!tsbtnIn}">
                    <span class="icon one-print"></span> In số
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="btnGiuSoClicked">
                    <span class="icon one-luu-so"></span> Giữ số
                </a>
            </li>
            <li>
                <a href="#">
                    <span class="icon one-xlsx-import"></span> Xuất Excel
                </a>
            </li>

        </ul>
    </div>
    <div class="popup-body">
        <div class="row">
            <div class="col-sm-6 col-12">
                <div class="box-form">
                    <div class="legend-title">Thông tin tổng đài</div>
                    <div class="info-row">
                        <div class="key w90">Quận/Huyện</div>
                        <div class="value">
                            <div class="select-custom disabled" ref="quan">
                                <SelectExt v-model="diachi.quan_id"
                                :settings="{dropdownParent: $refs['quan']}"
                                :dataSource="dsQuan"
                                :disabled="true"
                                @change="onChangeQuan()"
                                dataTextField="TEN_QUAN"
                                dataValueField="QUAN_ID"/>
                            </div>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w90">Phường/Xã</div>
                        <div class="value">
                            <div class="select-custom disabled" ref="phuong">
                                <SelectExt v-model="diachi.phuong_id"
                                :settings="{dropdownParent: $refs['phuong']}"
                                :dataSource="dsPhuong"
                                :disabled="true"
                                @change="onChangePhuong()"
                                dataTextField="TEN_PHUONG"
                                dataValueField="PHUONG_ID"/>
                            </div>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w90">Loại số</div>
                        <div class="value">
                            <select2 v-model="loaiso.value" class="select-custom" :options="loaiso.list.map(ls=>({id:ls.LOAISO_ID,text:ls.TEN_LS}))" :disabled="true" @change="getDauSo"></select2>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w90">
                            <div class="check-action">
                                <input type="checkbox" :disabled="true" class="check" v-model="vetinh.ischecked">
                                <span class="name">Vệ tinh</span>
                            </div>
                        </div>
                        <div class="value" :class="{disabled:!vetinh.ischecked}">
                            <select2 v-model="vetinh.value" :disabled="true" class="select-custom" :options="vetinh.list.map(ls=>({id:ls.donvi_id,text:ls.ten_dv}))" @change="getDauSo"></select2>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w90">
                            <div class="check-action">
                                <input type="checkbox" class="check" v-model="nhomso.ischecked">
                                <span class="name">Nhóm số</span>
                            </div>
                        </div>
                        <div class="value" :class="{disabled:!nhomso.ischecked}">
                            <select2 v-model="nhomso.value" class="select-custom" :options="nhomso.list.map(ls=>({id:ls.NHOMSO_ID,text:ls.NHOM_SO}))" @change="getDauSo"></select2>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w90">
                            <div class="check-action">
                                <input type="checkbox" class="check" v-model="kieuso.ischecked">
                                <span class="name">Kiểu số</span>
                            </div>
                        </div>
                        <div class="value" :class="{disabled:!kieuso.ischecked}">
                            <select2 v-model="kieuso.value" class="select-custom" :options="kieuso.list.filter(x=>x.NHOMSO_ID==nhomso.value).map(ls=>({id:ls.KIEUSO_ID,text:ls.KIEU_SO}))" @change="getDauSo"></select2>
                        </div>
                    </div>
                    <div class="legend-title mart20">Danh sách đầu số</div>

                    <DataGrid
                            v-bind:columns="[
                            {fieldName: 'prefix', headerText: 'Đầu số', allowFiltering: true, allowSorting: true},
                            {fieldName: 'tuso', headerText: 'Từ số', allowFiltering: true, allowSorting: false},
                            {fieldName: 'denso', headerText: 'Đến số', allowFiltering: true, allowSorting: false},
                            {fieldName: 'tongso', headerText: 'Tổng số', allowFiltering: true, allowSorting: true},
                            ]"
                        v-bind:dataSource="ds_dauso" :showFilter="true" :showColumnCheckbox="false"
                        dataKeyField="prefix" :defaultColumnCheckboxChecked="false"  @rowClicked="dsDauSoChanged">
                    </DataGrid>

                </div>
            </div>
            <div class="col-sm-6 col-12">
                <div class="box-form">
                    <div class="box-form" style="min-height: 260px;">
                        <div class="info-row">
                            <div class="key w70">Số máy</div>
                            <div class="value">
                                <input type="text" class="form-control highlight" v-model="somay.value">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w70">Tìm kiếm</div>
                            <div class="value">
                                <input type="text" class="form-control highlight" v-model="timkiem" @keypress.enter="TimKiemEnter">
                            </div>
                        </div>
                        <div class="info-row" v-if="thueso">
                            <div class="key w70"><input type="radio" value="truotso" v-model="kieuthue"> Trượt số</div>
                            <div class="value">
                                <input type="text" class="form-control w50" v-model="truotso">
                                T.gian thuê <input type="number" class="form-control w20" v-model="tgianthue"> tháng
                            </div>
                        </div>
                        <div class="info-row" v-if="thueso">
                            <div class="key w70"><input type="radio" value="thongbao" v-model="kieuthue"> Thông báo</div>
                            <div class="value">
                                <textarea class="form-control" v-model="thongbao"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="legend-title mart20">Danh sách số máy</div>
                    <DataGrid
                            v-bind:columns="[
                            {fieldName: 'somay', headerText: 'Số máy', allowFiltering: true, allowSorting: true},
                            {fieldName: 'kieu_so', headerText: 'Kiểu số', allowFiltering: true, allowSorting: false},
                            {fieldName: 'tien_hm', headerText: 'Tiền số đẹp', allowFiltering: true, allowSorting: false},
                            {fieldName: 'mota', headerText: 'Mô tả', allowFiltering: true, allowSorting: true},
                            ]"
                        v-bind:dataSource="filteredSoMay" :showFilter="true" :showColumnCheckbox="true"
                        dataKeyField="somay" :defaultColumnCheckboxChecked="false" :selectedRow="somay.idx" @rowClicked="dsSoMayChanged" @selectedItemsChanged="selectedItemsChanged">
                    </DataGrid>
                </div>
            </div>
        </div>
    </div>
</div>
</template>
<style scoped>
    input.disabled, select.disabled, .disabled input, .disabled select, .disabled /deep/ .select2-selection { background-color: #EEE; color:lightgray;}
</style>
<script>
import {
  THUESO
} from '../Enums'
export default {
  name: 'popupChonSM',
  props: [ 'dulieu' ],
  watch: {
    'nhomso.value': {
      handler (newval) {
        this.getDauSo()
      },
      deep: true
    },
    'nhomso.ischecked': {
      handler (newval) {
        if (newval && this.nhomso.list && this.nhomso.list.length > 0) { this.nhomso.value = this.nhomso.list[0].NHOMSO_ID } else this.nhomso.value = -1
      },
      deep: true
    },
    'kieuso.ischecked': {
      handler (newval) {
        var ns = this.nhomso.value
        var list = this.kieuso.list.filter(x => x.NHOMSO_ID == ns)
        if (newval && list && list.length > 0) { this.kieuso.value = list[0].KIEUSO_ID } else this.kieuso.value = 0
      },
      deep: true
    },
    'vetinh.ischecked': {
      handler (newval) {
        if (newval && this.vetinh.list && this.vetinh.list.length > 0) { this.vetinh.value = this.vetinh.list[0].donvi_id } else this.vetinh.value = 0
      },
      deep: true
    }
  },
  data () {
    return {
      diachi: {
        diachi_id: null,
        tinh_id: null,
        quan_id: null,
        phuong_id: null,
        pho_id: null,
        ap_id: null,
        khu_id: null,
        dac_diem_id: null,
        so_nha: '',
        daydiachi: '',
        diachimoi: ''
      },
      kieuthue: 'truotso',
      thongbao: '',
      truotso: '',
      tgianthue: 0,
      dsTinh: [],
      dsQuan: [],
      dsPhuong: [],
      tentinh: '',
      tenquan: '',
      tenphuong: '',
      loaiso: { list: [], value: 0 },
      nhomso: { list: [], value: 0, ischecked: true },
      kieuso: { list: [], value: 0, ischecked: false },
      vetinh: { list: [], value: 0, ischecked: false },
      somay: { list: [], value: '', idx: 0, valuearr: [] },
      simcard: '',
      thueso: false,
      loaitb_id: 0,
      donvi_ql_id: 0,
      dichvuvt_id: 0,
      hdkh_id: 0,
      ds_dauso: [],
      dauso: '',
      tongdai_id: 0,
      timkiem: '',
      tsbtnIn: false,
      tiensodep: 0
    }
  },
  async mounted () {
    this.loading(true)
    // Load default data
    {
      this.diachi.quan_id = this.dulieu._quan_id ? this.dulieu._quan_id : 0
      this.diachi.phuong_id = this.dulieu._phuong_id ? this.dulieu._phuong_id : 0
      this.loaiso.value = this.dulieu._loaiso_id ? this.dulieu._loaiso_id : 0
      this.thueso = this.dulieu._thueso
      this.loaitb_id = this.dulieu.loaitb_id
      this.donvi_ql_id = this.dulieu.donvi_ql_id
      this.dichvuvt_id = this.dulieu.dichvuvt_id
      this.hdkh_id = this.dulieu.hdkh_id
    }

    var load1 = this.getQuan()
    var load2 = this.getPhuong()
    var load3 = this.$root.context.get('/web-quantri/danhmuc-chung/CSS_LOAI_SO')
    var load4 = this.$root.context.get('/web-quantri/danhmuc-chung/CSS_NHOM_SO')
    var load5 = this.$root.context.get('/web-quantri/danhmuc-chung/CSS_KIEU_SO')
    var load6 = this.getVeTinh()
    // if(loaiso && loaiso.data) this.loaiso.list = loaiso.data;
    Promise.all([load1, load2, load3, load4, load5, load6]).then(([data1, data2, data3, data4, data5, data6]) => {
      if (data3 && data3.data) this.loaiso.list = data3.data
      if (data4 && data4.data) this.nhomso.list = data4.data
      if (data5 && data5.data) this.kieuso.list = data5.data
    }).finally(() => {
      this.nhomso.value = 5
      this.loading(false)
    })
  },
  computed: {
    filteredSoMay: function () {
      var newlist = this.somay.list.filter(x => x.somay.indexOf(this.timkiem) >= 0)
      if (newlist && newlist.length > 0) this.dsSoMayChanged(0, newlist[0])
      return newlist || []
    }
  },
  methods: {
    closeForm () {
      this.$emit('form-close', null)
    },
    getDauSo: async function () {
      this.loading(true)
      var data = { 'vdichvuvt_id': this.dichvuvt_id, 'vdonvi_ql_id': this.donvi_ql_id, 'vloaiso_id': this.loaiso.value, 'vloaitb_id': this.loaitb_id, 'vnhomso_id': this.nhomso.value, 'vphuong_id': this.diachi.phuong_id, 'vquan_id': this.diachi.quan_id, 'vvetinh_id': this.vetinh.value }
      console.log('🚀 ~ file: popupChonSM.vue ~ line 293 ~ data', data)
      var dsDauSo = await this.$root.context.post('/web-hopdong/lapdatmoi/lay_ds_dauso_theo_quan_dvvt_v3', data) // loaitb = 0 do bien mac dinh
      console.log('🚀 ~ file: popupChonSM.vue ~ line 286 ~ dsDauSo', dsDauSo)
      this.loading(false)
      if (dsDauSo && dsDauSo.data) {
        this.ds_dauso = dsDauSo.data
        if (this.ds_dauso && this.ds_dauso.length > 0) {
          this.dsDauSoChanged(0, this.ds_dauso[0])
        }
      }
    },
    getVeTinh: async function () {
      var vetinh = await this.$root.context.post('/web-hopdong/lapdatmoi/sp_lay_ds_vetinh_prefix', { 'dichvuvt_id': this.dichvuvt_id, 'donvi_ql_id': this.donvi_ql_id, 'loaiso_id': this.loaiso.value, 'loaitb_id': this.loaitb_id, 'phuong_id': this.diachi.phuong_id, 'quan_id': this.diachi.quan_id }) // loaitb = 0 do bien mac dinh
      if (vetinh && vetinh.data) this.vetinh.list = vetinh.data
    },
    getQuan: async function () {
      try {
        this.$root.showLoading(true)
        var rs = await this.$root.context.get('/web-cabman/bando_tuyencot/lay_ds_quan_huyen/' + this.$root.token.getPhanVungID())
        this.dsQuan = rs.data
        if (this.diachi.quan_id != null) { this.tenquan = this.dsQuan.filter((x) => x.QUAN_ID == this.diachi.quan_id)[0].TEN_QUAN }
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    getPhuong: async function () {
      try {
        this.$root.showLoading(true)
        var rs = await this.$root.context.get('/web-cabman/bando_tuyencot/lay_ds_phuong_xa/' + this.diachi.quan_id)
        this.dsPhuong = rs.data
        if (this.diachi.phuong_id != null) { this.tenphuong = this.dsPhuong.filter(x => x.PHUONG_ID == this.diachi.phuong_id)[0].TEN_PHUONG }
      } catch (error) {
        console.log(error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    onChangeTinh: async function () {
      await this.getQuan()
      this.tentinh = this.dsTinh.filter(
        (x) => x.TINH_ID == this.diachi.tinh_id
      )[0].TENTINH
    },
    onChangeQuan: async function () {
      await this.getPhuong()
      this.tenquan = this.dsQuan.filter(
        (x) => x.QUAN_ID == this.diachi.quan_id
      )[0].TEN_QUAN
      await this.getVeTinh()
    },
    onChangePhuong: async function () {
      // await this.getPhoApKhu();
      this.tenphuong = this.dsPhuong.filter(
        x => x.PHUONG_ID == this.diachi.phuong_id)[0].TEN_PHUONG
      await this.getVeTinh()
    },
    selectedItemsChanged (items) {
      this.somay.valuearr = items
      this.tiensodep = items.map(x => x.tien_hm).reduce((a, b) => a + b, 0)
    },
    chapnhan: function () {
      var retData = {somay: this.somay.value, loaiso: this.loaiso.value, arraySoMay: this.somay.valuearr, nhomso: this.nhomso.value, tiensodep: this.tiensodep}
      if (this.thueso) {
        if (!this.tgianthue || this.tgianthue < 0) { this.$toast.error('Bạn chưa nhập Thời gian thuê số !'); return }
        if (this.kieuthue == 'truotso' && !this.truotso) { this.$toast.error('Bạn chưa nhập Số trượt !'); return }
        if (this.kieuthue == 'thongbao' && !this.thongbao) { this.$toast.error('Bạn chưa nhập Thông báo !'); return }
        if (this.kieuthue == 'thongbao') {
          retData.noidung_thueso = this.thongbao
          retData.kieu_thueso = THUESO.THONGBAO
        }
        if (this.kieuthue == 'truotso') {
          retData.noidung_thueso = this.truotso
          retData.kieu_thueso = THUESO.TRUOTSO
        }
        retData.sothang_thueso = this.tgianthue
        retData.tongdai_id = this.tongdai_id
      }
      if (this.simcard && this.simcard != '') retData.simcard = this.simcard
      this.$emit('form-close', retData)
      this.$bvModal.hide(this.modalId)
    },
    async dsDauSoChanged (idx, item) {
      if (!this.ds_dauso || this.ds_dauso.length <= 0) {
        this.somay.value = ''
        this.somay.list = []
      } else {
        this.tongdai_id = item.tongdai_id
        // TODO continue line 589, dang thieu API 256_010
        this.dauso = item.prefix
        this.loading(true)
        var rs = await this.$root.context.post('/web-quantri/dauso/layds_dai_so_ims', {
          'dichvu_vienthong_id': this.dichvuvt_id,
          'kieuso_id': this.kieuso.value,
          'loaiso_id': this.loaiso.value,
          'nhomso_id': this.nhomso.value,
          'prefix': this.dauso,
          'so_batdau': item.tuso,
          'so_ketthuc': item.denso
        })
        this.loading(false)
        if (rs.data && rs.data.length > 0) {
          this.somay.list = rs.data
          if (this.somay.list && this.somay.list.length > 0) this.dsSoMayChanged(0, this.somay.list[0])
        } else {
          // if(rs.error=="200") this.$toast.warning("Không có thông tin số máy!");
          // else this.$toast.error("Có lỗi: "+rs.message);
          this.somay.list = []
        }
        this.tsbtnIn = true
      }
    },
    async dsSoMayChanged (idx, item) {
      if (item) {
        console.log('🚀 ~ file: popupChonSM.vue ~ line 406 ~ dsSoMayChanged ~ item', item)
        this.somay.value = item.somay
        if (item.simcard) this.simcard = item.simcard
      }
    },
    async btnGiuSoClicked () {
      // this.loading(true)
      // var ketqua = await this.$root.context.post('/web-hopdong/lapdatmoi/sp_capnhat_giuso', {
      //   'dichvuvt_id': this.dichvuvt_id,
      //   'js_somay': JSON.stringify(this.somay.valuearr),
      //   'nguoidung_id': this.$root.token.getNguoiDungID()
      // })
      // if (ketqua && ketqua.message.toLowerCase() == 'success') { this.$toast.success(ketqua.message) } else this.$toast.error(ketqua.message)
      // this.loading(false)
      // if (rs.data && rs.data.length > 0) {
      //   this.popupComponent = () => import('./popupGiuSo')
      //   this.popupComponentName = 'popupGiuSo'
      //   this.popupComponentData = {'dichvuvt_id': this.dichvuvt_id, 'nguoidung_id': this.$root.token.getNguoiDungID()}
      //   this.Popup('popupComponents')
      // } else this.$toast.error(rs.message + (rs.message_detail ? ': ' + rs.message_detail : ''))
    },
    async TimKiemEnter () {
      if (this.timkiem.length <= 5) {
        this.$toast.error('Số máy phải lớn hơn 5 ký tự !')
        return
      }
      var ktra = false
      var kw = this.timkiem
      var dauso = this.ds_dauso.filter(x => kw.substring(0, 6).indexOf(x.prefix) == 0)
      if (dauso && dauso.length > 0) {
        this.dauso = dauso[0].prefix
        // TODO Chon row cua dau so
        var listsm = this.somay.list.filter(x => x.somay == kw.trim())
        if (listsm && listsm.length > 0) {
          // TODO chon row cua somay
          this.somay.value = this.timkiem.trim()
          ktra = true
        }
      }
      if (!ktra) {
        this.$toast.error("Số máy : '" + this.timkiem.trim() + "' không có trong kho số hoặc đã được cấp !")
        return
      }
      this.tsbtnIn = true
    }
  }
}
</script>
