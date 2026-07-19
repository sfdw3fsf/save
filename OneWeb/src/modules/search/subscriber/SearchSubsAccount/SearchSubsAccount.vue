<template src="./SearchSubsAccount.html"></template>
<style src="./SearchSubsAccount.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component'
import SearchSubsAccountAPI from './SearchSubsAccountAPI'
import SearchAccountAPI from '../../api/SearchAccountAPI'
import gridview from '@/components/Shared/gridview'
import XLSX from 'xlsx'
import TraCuuThongSoKTModal from '@/modules/contract/setup/DeclareServiceSpeedITSecure/popups/TraCuuThongSoKT/TraCuuThongSoKTModal.vue'
import ExportDataModal from '@/modules/contract/profile/ExportData/ExportDataModal'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css'
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
import { defineAsyncComponent } from '@vue/composition-api'
//
export default {
  components: {
    breadcrumb,
    VueFlatPickr,
    gridview,
    SearchSubsAccountAPI,
    TraCuuThongSoKTModal,
    XLSX,
    ExportDataModal,
    frmDiaChi: defineAsyncComponent(() => import('./popup/dlgDiaChi/frmDiaChi.vue')),
    frmTTLoaiNha: defineAsyncComponent(() => import('./popup/dlgTTLoaiNha/frmTTLoaiNha.vue'))
  },
  name: 'SearchSubsAccount',
  mounted() {
    // var pdata = {};
    // pdata.nhom_id =1;
    // SearchSubsAccountAPI.lay_dk_tracuu(this.axios, pdata).then((response) => {
    //   if(response.data.error_code && response.data.error_code === this.success_code) {
    //     this.listbox.ds_dktracuu1.list = response.data.data;
    //     this.listbox.ds_dktracuu2.list = response.data.data;
    //     this.listbox.ds_dktracuu3.list = response.data.data;
    //     for (var item of this.listbox.ds_dktracuu1.list)
    //     {
    //       if (item.kieutk_id == "10")
    //       {
    //         this.listbox.ds_dktracuu1.value = item;
    //         this.listbox.ds_dktracuu2.value = item;
    //         this.listbox.ds_dktracuu3.value = item;
    //       }
    //     }
    //   }
    // });
    // nhomId: 2 = KHCN, 98 = KHDN, 100 = ????
    SearchAccountAPI.get_kieu_taikhoan(this.axios, { params: { nhomId: 1 } }).then((response) => {
      if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.listbox.dk_tracuu.list = response.data.data
        if (this.listbox.dk_tracuu.list && this.listbox.dk_tracuu.list.length > 0) {
          this.listbox.dk_tracuu.dk1.value = this.listbox.dk_tracuu.list[0].ID
          this.listbox.dk_tracuu.dk2.value = this.listbox.dk_tracuu.list[0].ID
          this.listbox.dk_tracuu.dk3.value = this.listbox.dk_tracuu.list[0].ID
        }
        //this.filterbox.dichvu_vienthong.list = response.data.data;
      }
    })

    //load dịch vụ
    SearchSubsAccountAPI.lay_ds_dvvt(this.axios, {}).then((response) => {
      if (response.data.error_code && response.data.error_code === this.success_code) {
        this.listbox.ds_dichvu.list = response.data.data.map((x) => ({ id: x.dichvuvt_id, text: x.ten_dvvt }))
        this.listbox.ds_dichvu1.list = response.data.data.map((x) => ({ id: x.dichvuvt_id, text: x.ten_dvvt }))
        if (response.data.data.length > 0) {
          this.listbox.ds_dichvu.value.dichvuvt_id = this.listbox.ds_dichvu.list[0].id
          this.listbox.ds_dichvu1.value.dichvuvt_id = this.listbox.ds_dichvu1.list[0].id
          this.DichVuChanged()
        }
      }
    })
    //load đơn vị
    var pdata = {}
    pdata.donvi_id = 0
    SearchSubsAccountAPI.lay_ds_donvi(this.axios, pdata).then((response) => {
      if (response.data.error_code && response.data.error_code === this.success_code) {
        this.listbox.ds_donvi.list = response.data.data.map((x) => ({ id: x.donvi_id, text: x.ten_dv }))
        if (this.listbox.ds_donvi.list.length > 0) {
          this.listbox.ds_donvi.value.donvi_id = this.listbox.ds_donvi.list[0].id
        }
      }
    })
    this.listbox.ketquatimkiem.list = []
    //this.listbox.ketquatimkiem.header = [{name:'',text:'Mã TT'},{name:'so_dt',text:'Số máy/Acc'},{name:'',text:'Số ảo'},{name:'',text:'Loại hình'},{name:'',text:'Tốc độ'},{name:'ten_kh',text:'Tên thuê bao'},{name:'diachi_kh',text:'Địa chỉ thuê bao'},{name:'diachi_ndd',text:'Địa chỉ lắp đặt'},{name:'ngaylap_hd',text:'Ngày SD'},{name:'',text:'Trạng thái'},{name:'',text:'Mã cước'}]
  },
  props: {
    isPopup: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      dataDB: null,
      dateconfig: {
        altFormat: 'd/m/Y',
        altInput: true,
        dateFormat: 'd/m/Y',
        allowInput: true
      },
      ngay_ht: new Date(),
      header: {
        title: 'TRA CỨU DANH BẠ THUÊ BAO',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      isTraTheo1: false,
      isTraTheo2: false,
      isDichVu: false,
      isDichVu1: false,
      isDonVi: false,
      isMaKH: false,
      isLoaiHinh: false,
      header_dlgDiaChi: '',
      listbox: {
        ds_dktracuu1: {
          list: [],
          value: { kieutk_id: '', kieu_tk: '' },
          isEnabled: false
        },
        ds_dktracuu2: {
          list: [],
          value: { kieutk_id: '', kieu_tk: '' },
          isEnabled: false
        },
        ds_dktracuu3: {
          list: [],
          value: { kieutk_id: '', kieu_tk: '' },
          isEnabled: false
        },
        dk_tracuu: {
          dk1: {
            value: '',
            value_text: '',
            isEnabled: true
          },
          dk2: {
            value: '',
            value_text: '',
            isEnabled: false
          },
          dk3: {
            value: '',
            value_text: '',
            isEnabled: false
          },
          list: []
        },
        ds_dichvu: {
          list: [],
          value: { dichvuvt_id: '', ten_dvvt: '' },
          isEnabled: false
        },
        ds_dichvu1: {
          list: [],
          value: { dichvuvt_id: '', ten_dvvt: '' },
          isEnabled: false
        },
        ds_donvi: {
          list: [],
          value: { donvi_id: '', ten_dv: '' },
          isEnabled: false
        },
        ds_loaihinh: {
          list: [],
          value: { loaitb_id: '', loaihinh_tb: '' },
          isEnabled: false
        },
        ketquatimkiem: {
          list: [],
          header: [
            { fieldName: 'ma_tt', headerText: 'Mã TT', allowFiltering: true, with: 150, freeze: 'left' },
            { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, freeze: 'left' },
            { fieldName: 'ma_lt', headerText: 'Số ảo', allowFiltering: true, allowHtml: true, freeze: 'left' },
            {
              fieldName: 'loaihinh_tb',
              headerText: 'Loại hình',
              allowFiltering: true,
              allowHtml: true,
              freeze: 'left'
            },
            { fieldName: 'tocdo', headerText: 'Tốc độ', allowFiltering: true, allowHtml: true, freeze: 'left' },
            { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, allowHtml: true },
            { fieldName: 'diachi_tb', headerText: 'Địa chỉ thuê bao', allowFiltering: true, allowHtml: true },
            { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true, allowHtml: true },
            { fieldName: 'ngay_sd', headerText: 'Ngày SD', allowFiltering: true, allowHtml: true },
            { fieldName: 'trangthai_tb', headerText: 'Trạng thái', allowFiltering: true, allowHtml: true },
            { fieldName: 'macuoc', headerText: 'Mã cước', allowFiltering: true, allowHtml: true }
          ],
          value: {},
          isEnabled: true
        },
        ketquatimkiem_temp: {
          list: [],
          header: [],
          value: {},
          isEnabled: true
        }
      },
      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      formData: {
        thuebao_id: 0,
        dichvuvt_id: 0,
        ma_tb: ''
      },
      isShow: {
        dlg_Diachi: false
      },
      success_code: 'BSS-00000000',
      from_date: new Date(),
      to_date: new Date(),
      thoihancon_lonhon: 0,
      thoihancon_nhohon: 0,
      stratheo1: '',
      stratheo2: '',
      sdichvu: '',
      sdonvi: '',
      smakh: '',
      ssomay: '',
      ssoao: '',
      txtTraCuu: '',
      txtTraCuu1: '',
      txtTraCuu2: '',
      txtMaKH: '',
      txtSoMayAcc: '',
      isSearchtxtSoMayAcc: true,
      txtSoAo: ''
    }
  },
  computed: {
    tratheo_1: {
      get() {
        return this.isTraTheo1
      },
      set(value) {
        this.isTraTheo1 = value
        this.listbox.ds_dktracuu2.value.kieutk_id = '10'
        this.txtTraCuu1 = ''
      }
    },
    tratheo_2: {
      get() {
        return this.isTraTheo2
      },
      set(value) {
        this.isTraTheo2 = value
        this.txtTraCuu2 = ''
      }
    },
    dich_vu: {
      get() {
        return this.isDichVu
      },
      set(value) {
        this.isDichVu = value
        this.listbox.ds_dichvu.value.dichvuvt_id = 1
        this.sdichvu = ''
      }
    },
    dich_vu1: {
      get() {
        return this.isDichVu1
      },
      set(value) {
        this.isDichVu1 = value
        this.listbox.ds_dichvu1.value.dichvuvt_id = 1
        this.DichVuChanged()
      }
    },
    don_vi: {
      get() {
        return this.isDonVi
      },
      set(value) {
        this.isDonVi = value
        this.listbox.ds_donvi.value.donvi_id = 2
        this.sdonvi = ''
      }
    },
    ma_kh: {
      get() {
        return this.isMaKH
      },
      set(value) {
        this.isMaKH = value
        this.smakh = ''
        this.txtMaKH = ''
      }
    },
    loai_hinh: {
      get() {
        return this.isLoaiHinh
      },
      set(value) {
        this.isLoaiHinh = value
        this.listbox.ds_loaihinh.value.loaitb_id = 1
      }
    },
    searchCondition() {
      var dieukiens = []
      for (var i = 1; i <= 3; i++)
        if (this.listbox.dk_tracuu['dk' + i].isEnabled && this.listbox.dk_tracuu['dk' + i].value && this.listbox.dk_tracuu['dk' + i].value_text)
          dieukiens.push({
            KIEUTK_ID: this.listbox.dk_tracuu['dk' + i].value,
            GIATRI: this.listbox.dk_tracuu['dk' + i].value_text
          })
      return {
        dichvuvt_id: this.dich_vu && this.listbox.ds_dichvu.value.dichvuvt_id ? this.listbox.ds_dichvu.value.dichvuvt_id : 0,
        donvi_id: this.don_vi && this.listbox.ds_donvi.value.donvi_id ? this.listbox.ds_donvi.value.donvi_id : 0,
        vmatb_tk: this.txtSoMayAcc && this.isSearchtxtSoMayAcc ? this.txtSoMayAcc : '',
        ma_kh: this.ma_kh && this.txtMaKH ? this.txtMaKH : '',
        vjs_dieukien: dieukiens && dieukiens.length > 0 ? JSON.stringify(dieukiens) : ''
      }
    }
  },
  methods: {
    selectedItemsChangedGridbox(items) {
      this.dataDB = null
      if (items.length > 0) {
        this.dataDB = items[0]
      }
    },
    btnChonClick() {
      if (!this.dataDB) {
        this.$toast.warning('Chọn một dòng dữ liệu')
      } else {
        this.$emit('onSuccess', this.dataDB)
      }
    },
    ShowAlert(mesage, val) {
      if (val == 0) {
        this.$toast.success(mesage)
      } else if (val == 1) {
        this.$toast.warning(mesage)
      } else if (val == 2) {
        this.$toast.error(mesage)
      }
    },
    btnExportFileClicked: function () {
      //console.log(this.listbox.ds_phieuyeucau.list);
      if (this.listbox.ketquatimkiem.list != null && this.listbox.ketquatimkiem.list.length > 0) {
        let data = XLSX.utils.json_to_sheet(
          this.listbox.ketquatimkiem.list.map((x) => ({
            'Mã TT': x.ma_tt,
            'Số máy/Acc': x.ma_tb,
            'Số ảo': '',
            'Loại hình': x.loaihinh_tb,
            'Tốc độ': x.tocdo,
            'Tên thuê bao': x.ten_tb,
            'Địa chỉ thuê bao': x.diachi_tb,
            'Địa chỉ lắp đặt': x.diachi_ld,
            'Ngày SD': x.ngay_sd,
            'Trạng thái': x.trangthai_tb,
            'Mã cước': ''
          }))
        )
        let wb = XLSX.utils.book_new() // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, data, 'DsPorts') // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, 'ketquatimkiem.xlsx')
      } else this.$toast.error('Không có dữ liệu!')
    },
    btnExportFile: function () {
      this.$refs.exportDataModal.showModal()
    },
    DichVuChanged() {
      //Load danh sách loại hình
      var pdata = {}
      pdata.dvvt_id = this.listbox.ds_dichvu1.value.dichvuvt_id
      // pdata.dichvuvt_id = this.listbox.ds_dichvu1.value.dichvuvt_id;
      this.loading(true)
      SearchSubsAccountAPI.lay_ds_loaihinh_theo_dichvu(this.axios, pdata).then((response) => {
        this.loading(false)
        if (response.data.error_code && response.data.error_code === this.success_code) {
          this.listbox.ds_loaihinh.list = response.data.data.map((x) => ({ id: x.ID, text: x.NAME }))
          if (this.listbox.ds_loaihinh.list.length > 0) {
            this.listbox.ds_loaihinh.value.loaitb_id = this.listbox.ds_loaihinh.list[0].id
          } else {
            this.listbox.ds_loaihinh.value.loaitb_id = 0
          }
        }
      })
      //filler kết quả theo dịch vụ
      this.listbox.ketquatimkiem.list = this.listbox.ketquatimkiem_temp.list.filter((a) => a.dichvuvt_id.indexOf(this.listbox.ds_dichvu1.value.dichvuvt_id) >= 0)
    },
    LoaiHinhChanged() {
      //filler kết quả theo dịch vụ và loại hình
      this.listbox.ketquatimkiem.list = this.listbox.ketquatimkiem_temp.list.filter((a) => a.dichvuvt_id.indexOf(this.listbox.ds_dichvu1.value.dichvuvt_id) >= 0 && a.loaitb_id.indexOf(this.listbox.ds_loaihinh.value.loaitb_id) >= 0)
    },
    onTraCuuEnter() {
      this.btnSearchClicked()
    },
    SearchClient() {
      this.btnSearchClicked()
    },
    dgrList_SelectionChanged(data) {
      let row = data.data
      try {
        if (row) {
          this.txtSoMayAcc = row.ma_tb
          this.isSearchtxtSoMayAcc = false
          this.txtSoAo = row.ma_lt
          this.formData.thuebao_id = row.thuebao_id
          this.formData.dichvuvt_id = row.dichvuvt_id
          this.formData.ma_tb = row.ma_tb
          this.formData.thanhtoan_id = row.thanhtoan_id
          this.formData.khachhang_id = row.khachhang_id
          this.formData.phanloaikh_id = row.phanloaikh_id
          this.formData.nhanvien_id = row.nhanvien_id
          this.formData.tuyenthu_id = row.tuyenthu_id
        }
      } catch (err) {
        this.$toast.error('Lỗi: ' + err.message)
      }
    },
    //Double click row view popup thông số kỹ thuật
    ThongSoKT_DoubleClick(index, rowData) {
      this.$refs.traCuuThongSoKTModal.showModal()
      this.formData.thuebao_id = rowData.thuebao_id
      this.formData.dichvuvt_id = rowData.dichvuvt_id
      this.formData.ma_tb = rowData.ma_tb
    },
    btnSearchClicked() {
      try {
        if (!this.listbox.dk_tracuu.dk1.value) {
          this.ShowAlert('Bạn cần chọn điều kiện tra cứu theo', 1)
          return
        }
        this.loading(true)
        // var dieukien1 = "";
        // var dieukien2 = "";
        // var dieukien3 = "";
        // var dieukien = "";
        // if (this.txtTraCuu.trim()!="")
        // {
        //   dieukien1=this.listbox.ds_dktracuu1.value.dieukien.replaceAll(":values",this.txtTraCuu);
        // }
        // if (this.txtTraCuu1.trim()!="")
        // {
        //   dieukien2 = this.listbox.ds_dktracuu2.value.dieukien.replaceAll(":values",this.txtTraCuu1);
        // }
        // if (this.txtTraCuu2.trim()!="")
        // {
        //   dieukien3 = this.listbox.ds_dktracuu3.value.dieukien.replaceAll(":values",this.txtTraCuu2);
        // }
        // if (dieukien1 == "")
        // {
        //   if (dieukien2 == "")
        //   {
        //     if (dieukien3 == "")
        //     {
        //       dieukien = "";
        //     }
        //     else
        //       dieukien = dieukien3;
        //   }
        //   else
        //   {
        //     if (dieukien3 == "")
        //     {
        //       dieukien = dieukien2;
        //     }
        //     else
        //       dieukien = dieukien2 + " and " + dieukien3;
        //   }
        // }
        // else
        // {
        //   if (dieukien2 == "")
        //   {
        //     if (dieukien3 == "")
        //     {
        //       dieukien = dieukien1;
        //     }
        //     else
        //       dieukien = dieukien1 + " and " + dieukien3;
        //   }
        //   else
        //   {
        //     if (dieukien3 == "")
        //     {
        //       dieukien = dieukien1 + " and " + dieukien2;
        //     }
        //     else
        //       dieukien = dieukien1 + " and " + dieukien2 + " and " + dieukien3;
        //   }
        // }
        // if (this.isMaKH)
        // {
        //   if (dieukien == "")
        //   {
        //     dieukien = " tb.khachhang_id = '"+ this.txtMaKH +"'";
        //   }
        //   else
        //   {
        //     dieukien = dieukien + " and tb.khachhang_id = '"+ this.txtMaKH +"'";
        //   }
        // }
        // if (this.txtSoMayAcc != "")
        // {
        //   if (dieukien == "")
        //   {
        //     dieukien = " tb.ma_tb = '"+ this.txtSoMayAcc +"'";
        //   }
        //   else
        //   {
        //     dieukien = dieukien + " and tb.ma_tb = '"+ this.txtSoMayAcc +"'";
        //   }
        // }
        // if (this.listbox.ds_dktracuu1.value.kieu_tk.trim().toLowerCase()=="svlan/cvlan") //
        // {
        //   var pdata = {};
        //   pdata.cvlan = this.txtMaKH;
        //   pdata.svlan = this.txtTraCuu;
        //   SearchSubsAccountAPI.lay_ds_tb_svlan(this.axios, pdata).then((response) => {
        //   this.loading(false);
        //   if(response.data.error_code && response.data.error_code === this.success_code) {
        //       this.listbox.ketquatimkiem.list = response.data.data;
        //       this.listbox.ketquatimkiem_temp.list=this.listbox.ketquatimkiem.list;
        //       //this.listbox.ketquatimkiem.header = [{name:'',text:'Mã TT'},{name:'so_dt',text:'Số máy/Acc'},{name:'',text:'Số ảo'},{name:'',text:'Loại hình'},{name:'',text:'Tốc độ'},{name:'ten_kh',text:'Tên thuê bao'},{name:'diachi_kh',text:'Địa chỉ thuê bao'},{name:'diachi_ndd',text:'Địa chỉ lắp đặt'},{name:'ngaylap_hd',text:'Ngày SD'},{name:'',text:'Trạng thái'},{name:'',text:'Mã cước'}]
        //      }
        //   else
        //   {
        //       this.listbox.ketquatimkiem.list = [];
        //       this.listbox.ketquatimkiem_temp.list=[];
        //   }
        //     }
        //   );
        // }
        // else if (this.listbox.ds_dktracuu1.value.kieu_tk.trim().toLowerCase()=="site id")
        // {
        //   var pdata = {};
        //   pdata.site_id = this.txtTraCuu;
        //   SearchSubsAccountAPI.timkiem_theo_site_id(this.axios, pdata).then((response) => {
        //   this.loading(false);
        //   if(response.data.error_code && response.data.error_code === this.success_code) {
        //       this.listbox.ketquatimkiem.list = response.data.data;
        //       this.listbox.ketquatimkiem_temp.list=this.listbox.ketquatimkiem.list;
        //       //this.listbox.ketquatimkiem.header = [{name:'',text:'Mã TT'},{name:'so_dt',text:'Số máy/Acc'},{name:'',text:'Số ảo'},{name:'',text:'Loại hình'},{name:'',text:'Tốc độ'},{name:'ten_kh',text:'Tên thuê bao'},{name:'diachi_kh',text:'Địa chỉ thuê bao'},{name:'diachi_ndd',text:'Địa chỉ lắp đặt'},{name:'ngaylap_hd',text:'Ngày SD'},{name:'',text:'Trạng thái'},{name:'',text:'Mã cước'}]
        //      }
        //   else
        //   {
        //       this.listbox.ketquatimkiem.list = [];
        //       this.listbox.ketquatimkiem_temp.list=[];
        //   }

        //     }
        //   );
        // }
        // else if (this.listbox.ds_dktracuu1.value.kieu_tk.trim().toLowerCase()=="số ảo")
        // {
        //   var pdata = {};
        //   pdata.dichvuvt_id=0;
        //   pdata.donvi_id =0;
        //   pdata.ma_tb = this.txtTraCuu;
        //   SearchSubsAccountAPI.lay_db_theo_matb(this.axios, pdata).then((response) => {
        //   this.loading(false);
        //   if(response.data.error_code && response.data.error_code === this.success_code) {
        //       this.listbox.ketquatimkiem.list = response.data.data;
        //       this.listbox.ketquatimkiem_temp.list=this.listbox.ketquatimkiem.list;
        //       //this.listbox.ketquatimkiem.header = [{name:'',text:'Mã TT'},{name:'so_dt',text:'Số máy/Acc'},{name:'',text:'Số ảo'},{name:'',text:'Loại hình'},{name:'',text:'Tốc độ'},{name:'ten_kh',text:'Tên thuê bao'},{name:'diachi_kh',text:'Địa chỉ thuê bao'},{name:'diachi_ndd',text:'Địa chỉ lắp đặt'},{name:'ngaylap_hd',text:'Ngày SD'},{name:'',text:'Trạng thái'},{name:'',text:'Mã cước'}]
        //     }
        //   else
        //   {
        //       this.listbox.ketquatimkiem.list = [];
        //       this.listbox.ketquatimkiem_temp.list=[];
        //   }
        //     }
        //   );
        // }
        // else if (this.listbox.ds_dktracuu1.value.kieu_tk.trim().toLowerCase()=="điện thoại liên hệ bh")
        // {
        //   var pdata = {};
        //   if (this.isDichVu)
        //   {
        //     pdata.dichvuvt_id= this.listbox.ds_dichvu.value.dichvuvt_id;
        //   }
        //   else
        //   {
        //     pdata.dichvuvt_id= 0;
        //   }
        //   if (this.isDonVi)
        //   {
        //     pdata.donvi_id = this.listbox.ds_donvi.value.donvi_id;
        //   }
        //   else
        //   {
        //     pdata.donvi_id = 0;
        //   }
        // pdata.dieukien = dieukien;
        SearchSubsAccountAPI.tra_cuu_db_tb_dieukien(this.axios, this.searchCondition)
          .then((response) => {
            this.loading(false)
            if (response.data.error_code && response.data.error_code === this.success_code) {
              this.listbox.ketquatimkiem.list = response.data.data
              this.listbox.ketquatimkiem_temp.list = this.listbox.ketquatimkiem.list
              //this.listbox.ketquatimkiem.header = [{name:'',text:'Mã TT'},{name:'ma_tb',text:'Số máy/Acc'},{name:'',text:'Số ảo'},{name:'',text:'Loại hình'},{name:'',text:'Tốc độ'},{name:'ten_tb',text:'Tên thuê bao'},{name:'diachi_tb',text:'Địa chỉ thuê bao'},{name:'diachi_ld',text:'Địa chỉ lắp đặt'},{name:'ngay_sd',text:'Ngày SD'},{name:'trangthaitb_id',text:'Trạng thái'},{name:'',text:'Mã cước'}]
            } else {
              this.listbox.ketquatimkiem.list = []
              this.listbox.ketquatimkiem_temp.list = []
              this.$toast.error('Không tìm thấy thông tin theo điều kiện tìm kiếm!')
            }
          })
          .catch((e) => {
            this.$toast.error(JSON.stringify(e))
            this.loading(false)
          })
        // }
        // else
        // {
        //   var pdata = {};
        //   if (this.isDichVu)
        //   {
        //     pdata.dichvuvt_id= this.listbox.ds_dichvu.value.dichvuvt_id;
        //   }
        //   else
        //   {
        //     pdata.dichvuvt_id= 0;
        //   }
        //   if (this.isDonVi)
        //   {
        //     pdata.donvi_id = this.listbox.ds_donvi.value.donvi_id;
        //   }
        //   else
        //   {
        //     pdata.donvi_id = 0;
        //   }
        //   pdata.dieukien=dieukien;
        //   console.log(pdata);
        //   SearchSubsAccountAPI.tracuu_theo_db_tb(this.axios, pdata).then((response) => {
        //   this.loading(false);
        //   if(response.data.error_code && response.data.error_code === this.success_code) {
        //     console.log(response.data.data);
        //       this.listbox.ketquatimkiem.list = response.data.data;
        //       this.listbox.ketquatimkiem_temp.list=this.listbox.ketquatimkiem.list;
        //       //this.listbox.ketquatimkiem.header = [{name:'',text:'Mã TT'},{name:'ma_tb',text:'Số máy/Acc'},{name:'',text:'Số ảo'},{name:'',text:'Loại hình'},{name:'',text:'Tốc độ'},{name:'ten_tb',text:'Tên thuê bao'},{name:'diachi_tb',text:'Địa chỉ thuê bao'},{name:'diachi_ld',text:'Địa chỉ lắp đặt'},{name:'ngay_sd',text:'Ngày SD'},{name:'trangthaitb_id',text:'Trạng thái'},{name:'',text:'Mã cước'}]
        //     }
        //   else
        //   {
        //       this.listbox.ketquatimkiem.list = [];
        //       this.listbox.ketquatimkiem_temp.list=[];
        //   }
        //     }
        //   );
        // }
      } catch (e) {
        this.$toast.error(JSON.stringify(e))
        this.loading(false)
      }
    },

    btnShowC_DiaChi() {
      if (this.formData.thuebao_id == 0) {
        return this.$toast.warning('Hãy chọn thuê bao trước!')
      }
      this.isShow.dlg_Diachi = true
      this.header_dlgDiaChi = 'Cập nhật địa chỉ cho thuê bao: ' + this.txtSoMayAcc

      this.$refs.dlgDiaChi.show()
    },

    close_dlgDiaChi() {
      this.isShow.dlg_Diachi = false
    },

    btnShowTTLoaiNha() {
      if (this.formData.thuebao_id == 0) {
        return this.$toast.warning('Hãy chọn thuê bao trước!')
      }
      this.$refs.frmTTLoaiNha.hdtb_id = this.formData.thuebao_id
      this.$refs.frmTTLoaiNha.thuebao_id = this.formData.thuebao_id
      this.$refs.frmTTLoaiNha.type = 1
      this.$refs.frmTTLoaiNha.NVKT_BATBUOC_NHAP_LOAINHA = this.NVKT_BATBUOC_NHAP_LOAINHA
      this.$refs.frmTTLoaiNha.showDialog()
    }
  }
}
</script>
