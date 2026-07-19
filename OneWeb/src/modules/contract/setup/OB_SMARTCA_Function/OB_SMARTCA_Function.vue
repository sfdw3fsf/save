<template src="./OB_SMARTCA_Function.html"></template>
<style src="./OB_SMARTCA_Function.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import api from './API'
import KTableCustom from "./components/KTableCustom.vue"
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import util from './util'
export default {
  name: 'OB_SMARTCA_Function',
  components: {
    breadcrumb,
    KTableCustom,
    KRequiredMarker,
    KDatePicker,
  },
  data() {
    return {
      isDisabled:true,
      chan_gui_Email:false,
      ob_id:0,
      nhanvien_b2a_id:0,
      khachhang_id:null,
      thuebao_id:null,
      tocdo_id:0,
      so_dt:null,
      diachi_b2a:'',
      phanvung_id:'',
      tg_hentu:'',
      tg_henden:'',
      thongtin_KH:{
        ten_kh:'',
        ma_kh:'',
        sdt_lh:'',
        email:'',
        loai_gto:'',
        so_gto:'',
        diachi_kh:'',
      },
      capnhat_KQ_OB:{
        tt_ketnoi:'',
        ketqua_ob:'',
        tg_hentu:'',
        tg_henden:'',
        chuyen_b2a:'',
        ghichu:'',
      },
      tt_ketnoi:{
        list:[],
      },
      ketqua_ob: {
        list: [],
        originalList: [], // Danh sách gốc để lưu trữ ban đầu
      },
      columns_1:[
        {
          field: 'serial',
          label: 'Serial Number',
          allowEdit: false,
          allowFilter: true,
          allowEditDate: false,
        },
        {
          field: 'ten_tb',
          label: 'Tên TB',
          allowEdit: false,
          allowFilter: true,
          allowEditDate: false,
        },
        {
          field: 'ma_tb',
          label: 'Mã TB',
          allowEdit: false,
          allowFilter: true,
          allowEditDate: false,
        },
        {
          field: 'loai_tb',
          label: 'Loại TB',
          allowEdit: false,
          allowFilter: true,
          allowEditDate: false,
        },
        {
          field: 'loai_cts',
          label: 'Loại CTS',
          allowEdit: false,
          allowFilter: true,
          allowEditDate: false,
        },
        {
          field: 'diachi_tb',
          label: 'Địa chỉ TB',
          allowEdit: false,
          allowFilter: true,
          allowEditDate: false,
        },
        {
          field: 'so_gt',
          label: 'Số giấy tờ',
          allowEdit: false,
          allowFilter: true,
          allowEditDate: false,
        },
        {
          field: 'so_dt',
          label: 'Số ĐT',
          allowEdit: false,
          allowFilter: true,
          allowEditDate: false,
        },
        {
          field: 'email',
          label: 'Email',
          allowEdit: false,
          allowFilter: true,
          allowEditDate: false,
        },
        {
          field: 'diaban',
          label: 'Địa bàn',
          allowEdit: false,
          allowFilter: true,
          allowEditDate: false,
        },
        {
          field: 'tocdo_id',
          label: 'Gói cước đang SD',
          allowEdit: false,
          allowFilter: true,
          allowEditDate: false,
        },
        {
          field: 'sothang_sd',
          label: 'Chu kỳ',
          allowEdit: false,
          allowFilter: true,
          allowEditDate: false,
        },
        {
          field: 'ngay_hethan',
          label: 'Ngày hết hạn',
          allowEdit: false,
          allowFilter: true,
          allowEditDate: false,
        },
        {
          field: 'cuoc_tg',
          label: 'Giá tiền',
          allowEdit: false,
          allowFilter: true,
          allowEditDate: false,
        },
        {
          field: 'soluong',
          label: 'SL lượt ký',
          allowEdit: false,
          allowFilter: true,
          allowEditDate: false,
        },
        {
          field: 'thao_tac',
          label: 'Gói tư vấn',
          allow_thaotac:true,
        },
    ],
    gridThongTinThueBao:[],
    columns_2: {
        headers: [
          {fieldName: 'serial', headerText: 'Serial Number', allowfiltering: true},
          {fieldName: 'ma_nv', headerText: 'Mã NV CS', allowfiltering: true},
          {fieldName: 'ten_nv', headerText: 'Tên NV CS', allowfiltering: true},
          {fieldName: 'tt_ketnoi', headerText: 'TT kết nối', allowfiltering: true},
          {fieldName: 'ketqua_ob', headerText: 'Kết quả OB', allowfiltering: true},
          {fieldName: 'ngay_cn', headerText: 'Ngày OB', allowfiltering: true,allowSorting: true},
          {fieldName: 'ngayhen', headerText: 'Ngày hẹn LL', allowfiltering: true},
          {fieldName: 'nhanvien_b2a', headerText: 'Nhân viên B2A', allowfiltering: true},
          {fieldName: 'diachi_b2a', headerText: 'Địa chỉ B2A', allowfiltering: true},
          {fieldName: 'ghi_chu', headerText: 'Ghi chú', allowfiltering: true},

        ],
        selectedIndex: 0,
        selectedItem: null,
        checked: []
      },

    gridLichSuKetQuaOB:[],
    checkCapNhat_KQ_OB:{
      checkNgayHen: false,
      checkChuyenB2A: false,
      checkEmail: false
    },
    tt_nd:[],
    selectedRow:false,
    popupComponentName: '',
    popupComponentHeader: '',
    popupComponent: null,
    popupComponentData: null,
    popupComponentAttr: {},
    popupComponentEvts: {},
    dulieu:'',
    email_form_cha_truyen:'',
    fullname_form_cha_truyen:'',
  }
},
  created: async function () {
  },
  mounted :async function() {
    this.loading(true)
    this.tt_nd = await this.$root.token.getThongTinNguoiDung()
    this.phanvung_id = this.tt_nd.phanvung_id
    console.log('Phân vùng id người dùng:',this.phanvung_id,this.tt_nd)
    await this.getdsTTKetNoi()
    // await this.getListKetQuaOB()
    const query = this.$route.query
    console.log ('query',query)
    if (query.Ref) {
      let data = util.decodeQuery(query.Ref)
      console.log('data query',data)
      this.so_dt = data.SDT_LH
      this.ob_id = data.OB_ID
      await this.laydsThueBao_KH();
    }
    // else{
    //   this.so_dt = '0912150433'
    //   await this.laydsThueBao_KH();
    // }
    this.loading(false)
  },
  methods: {
    async getdsTTKetNoi() {
      let response = await api.getdsTTKetNoi(this.axios);      
      if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          const filteredData = response.data.data.filter(item => item.ttkn_id === 1 || item.ttkn_id === 2);
          this.tt_ketnoi.list =filteredData.map((x, index) => {
              return { 
                  id: x['ttkn_id'], 
                  text: `${index + 1}. ${x['trangthai_kn']}` 
              };
          });         
          if (this.tt_ketnoi.list.length > 0) {
              this.capnhat_KQ_OB.tt_ketnoi = this.tt_ketnoi.list[0].id;
          }
      }
    },
    async getListKetQuaOB(ttkn_id) {
      const Input = {          
        vttkn_id: ttkn_id,
        vnghiepvu_id: 4
      };
      let response = await api.KetQuaOB_new(this.axios, Input);
      if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
        this.ketqua_ob.list = response.data.data.map((x) => {
          return { id: x['kqob_id'], text: x['ketqua_ob'] };
        });
        if (this.ketqua_ob.list.length > 0) {
              this.capnhat_KQ_OB.ketqua_ob = this.ketqua_ob.list[0].id;
          }
      }
    },
    async tailieuHD_Click(){
      window.open("https://smartca.vnpt.vn/help/docs/features/extendCertificate/");
    },
    async capnhatClick(){
      this.capnhat_KQ_OB.ghichu = this.capnhat_KQ_OB.ghichu.trim()
      console.log('thiện', this.capnhat_KQ_OB.ghichu.length)
      if (this.capnhat_KQ_OB.ghichu == '' || this.capnhat_KQ_OB.tt_ketnoi=='' || this.capnhat_KQ_OB.ketqua_ob==''){
        this.$toast.error('Các trường bắt buộc không được để trống')
        return
      }
      try{
        let params = {
          nhanvien_id:this.tt_nd.nhanvien_id,
          b2a:this.checkCapNhat_KQ_OB.checkChuyenB2A == true ? 1 : 0,
          nhanvien_b2a_id:this.nhanvien_b2a_id,
          diachi_b2a:this.diachi_b2a,
          thuebao_id:this.thuebao_id,
          khachhang_id: this.khachhang_id,
          nghiepvu_id: 4,
          ob_id:parseInt(this.ob_id),
          ttkn_id:this.capnhat_KQ_OB.tt_ketnoi,
          kqob_id:this.capnhat_KQ_OB.ketqua_ob,
          gui_email:this.checkCapNhat_KQ_OB.checkEmail == true ? 1 : 0,
          hen_tu:this.capnhat_KQ_OB.tg_hentu,
          hen_den:this.capnhat_KQ_OB.tg_henden,
          ghichu:this.capnhat_KQ_OB.ghichu,
        }
        const dataInput ={
          p_js_data: JSON.stringify(params)
        }
        const response = await api.capnhat_ketqua_OB(this.axios, dataInput)
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          this.$toast.success('Cập nhật dữ liệu thành công')
          await this.clearAll()
          await this.lay_lichsu_OB()

        }else{
          this.$toast.error(response.data.message)
        }
      }catch(error){
        this.$toast.error('Xảy ra lỗi khi cập nhật kết quả OB')
      }finally{
        this.loading(false);
      }
    },
    layThongtinKHG () {
      const Input = {
        p_khachhang_id: this.khachhang_id == null ? 0 : this.khachhang_id,
        }
      api.layThongtinKHG(this.axios, Input).then(response => {
        const ketqua = response.data.data 
        console.log('layThongtinKHG:',ketqua)
        this.thongtin_KH.ten_kh = ketqua[0].ten_kh
        this.thongtin_KH.ma_kh = ketqua[0].ma_kh
        this.thongtin_KH.email = ketqua[0].email
        this.thongtin_KH.diachi_kh = ketqua[0].diachi_kh
        this.thongtin_KH.so_gto = ketqua[0].so_gt
        this.thongtin_KH.loai_gto = ketqua[0].loai_gt
        this.thongtin_KH.sdt_lh = ketqua[0].so_dt

      }).finally(() => {
        this.loading(false)
      })
    },
    async laydsThueBao_KH(){
      console.log('Input',this.so_dt)
      try{
        const Input = {
          p_so_dt: this.so_dt == null ? 0 : this.so_dt,
        }
        const response = await api.laydsThueBao_KH(this.axios, Input);
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          this.gridThongTinThueBao = response.data.data
          this.$nextTick(() => {
            if (this.gridThongTinThueBao.length > 0) {
              this.isDisabled=false
              this.onSelectedRow(this.gridThongTinThueBao[0]);
            }
          });
        } 
        // else {
        //   this.$toast.error(response.data.message)
        // }
      }catch (error){
        this.$toast.error('Xảy ra lỗi khi lấy danh sách thuê bao của KH')
      }finally{
        this.loading(false);
      }
    },
    async lay_lichsu_OB(){
      try{
        const Input = {
          p_thuebao_id: this.thuebao_id == null ? 0 : this.thuebao_id,
        }
        const response = await api.lay_lichsu_OB(this.axios, Input);
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          this.gridLichSuKetQuaOB = response.data.data
          this.gridLichSuKetQuaOB.forEach(obj => {
            if (obj.ngayhen != " - "){
            let uu =this.convertDateRange(obj.ngayhen)
            obj.ngayhen = uu; }
          });
          this.gridLichSuKetQuaOB.sort((a, b) => this.parseDate(b.ngay_cn) - this.parseDate(a.ngay_cn))
        }
      }catch (error){
        this.$toast.error('Xảy ra lỗi khi lấy lịch sử OB của KH')
      }finally{
        this.loading(false);
      }
    },
    convertDateRange(dateRange) {
      let dates = dateRange.split(" - ");
      function convertDate(date) {
        let parts = date.split("-");
        let day = parts[0];
        let month = parts[1];
        let year = parts[2];
        const months = {
          "JAN": "01",
          "FEB": "02",
          "MAR": "03",
          "APR": "04",
          "MAY": "05",
          "JUN": "06",
          "JUL": "07",
          "AUG": "08",
          "SEP": "09",
          "OCT": "10",
          "NOV": "11",
          "DEC": "12"
        };
        year = "20" + year;
        return `${day}/${months[month.toUpperCase()]}/${year}`;
      }
      let startDate = convertDate(dates[0]);
      let endDate = convertDate(dates[1]);
      return `${startDate} - ${endDate}`;
    },
   
    thayDoiCheck(num) {
      if (num == 1) this.checkNgayHen = !this.checkNgayHen;
      if (num == 2) this.checkChuyenB2A = !this.checkChuyenB2A;
      if (num == 3) {
        
        this.checkEmail = !this.checkEmail;
        if(this.checkCapNhat_KQ_OB.checkEmail){
          this.showPopupguiEmail()
        }
      }
    },
    clearAll(){
      this.capnhat_KQ_OB.ghichu=''
      this.capnhat_KQ_OB.chuyen_b2a=''
      this.capnhat_KQ_OB.tg_henden=''
      this.capnhat_KQ_OB.tg_hentu=''
      this.capnhat_KQ_OB.ketqua_ob=this.ketqua_ob.list[0].id
      this.capnhat_KQ_OB.tt_ketnoi=this.tt_ketnoi.list[0].id
      this.checkCapNhat_KQ_OB.checkEmail = false
      this.checkCapNhat_KQ_OB.checkNgayHen = false
      this.checkCapNhat_KQ_OB.checkChuyenB2A = false
      this.chan_gui_Email = false      
    },
    onRowCheckChange(event, item) {
      console.log('Row check changed:', event, item);
      if (event.target.checked) {

      } else {

      }
    },
    onRowCheckChange_1(event, item) {
      console.log('Row check changed:', event, item);
      if (event.target.checked) {

      } else {

      }
    },
    checkBoxChange_1(item){
    console.log('a Khánh qq',item)
},
parseDate:function(dateString) {
      const [day, month, year, time] = dateString.split(/[/ ]/);
      return new Date(`${year}-${month}-${day}T${time}`);
    },
    async onSelectedRow(item){
      console.log('Hàng thuê bao được chọn:', item);
      this.clearAll() 
      if(item){
        this.gridLichSuKetQuaOB=[]
        this.selectedRow = true
        this.khachhang_id = item.khachhang_id
        this.tocdo_id = item.tocdo_id
        this.thuebao_id = item.thuebao_id
        this.email_form_cha_truyen = item.email
        this.fullname_form_cha_truyen = item.ten_tb
        console.log('khachhang_id',this.khachhang_id)
        await this.layThongtinKHG()
        await this.lay_lichsu_OB()
      }else{
        this.selectedRow = false
      }
    },
    onSelectedRow_1(item){
      console.log('Hàng thue bao:', item);
      if(item){
        this.selectedRow = true

      }else{
        this.selectedRow = false
      }

    },
    Click_XemGoiTuVanRow:function (index) {
      console.log(" Hàng muốn xem gói tư vấn là: ",index+1)
    },
    showPopupguiEmail : function () {
      this.popupComponent = () =>
        import(
          "@/modules/contract/setup/OB_SMARTCA_Function/popup/guiEmail.vue"
        );
      this.popupComponentName = "guiEmail";
      this.popupComponentHeader = 'Gửi email cho khách hàng';
      this.popupComponentData = {
        isPopup: true,
        email: this.email_form_cha_truyen,
        fullname: this.fullname_form_cha_truyen
      }; 
      console.log('Dữ liệu file cha truyền cho form gửi email:',this.popupComponentData)
      this.Popup("popupComponents");
    },
    showPopupgoiTuVan : function () {
      this.popupComponent = () =>
        import(
          "@/modules/contract/setup/OB_SMARTCA_Function/popup/goiTuVan.vue"
        );
      this.popupComponentName = "goiTuVan";
      this.popupComponentHeader = 'Danh sách gói cước tư vấn';
      this.popupComponentData = {
        isPopup: true,
        tocdo_id: this.tocdo_id,
        khachhang_id: this.khachhang_id
      }; 
      this.Popup("popupComponents");
    },
    showPopuptimkiemNV_CSKH : function () {
      this.popupComponent = () =>
        import(
          "@/modules/contract/setup/OB_SMARTCA_Function/popup/timkiemNV_CSKH.vue"
        );
      this.popupComponentName = "timkiemNV_CSKH";
      this.popupComponentHeader = 'Tìm kiếm thông tin NV CSKH';
      this.popupComponentData = {
        isPopup: true,
        phanvung_id:this.phanvung_id,
        diachi:this.thongtin_KH.diachi_kh
      }; 
      console.log('Dữ liệu file cha truyền:',this.popupComponentData)
      this.Popup("popupComponents");
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
   async ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
      this.popupComponent = null;
    },
    async popupClosed(val,val2,val3) {
      this.loading(true);
      try {
        switch (this.popupComponentName) {
          case "timkiemNV_CSKH": 
            console.log("Tú check, popupClosed called with:", val, val2,val3);
            this.capnhat_KQ_OB.chuyen_b2a=val
            this.nhanvien_b2a_id = val2
            this.diachi_b2a = val3
          break;
          case "guiEmail": 
            this.chan_gui_Email = true
            console.log("Tú this.chan_gui_Email,", this.chan_gui_Email)
          break;
        }
      } catch (ex) {
        if (ex.data && ex.data.message_detail) {
          this.$toast.error("" + ex.data.message_detail);
        } else {
          this.$toast.error("" + ex);
        }
      } finally {
        this.ClosePopup("popupComponents");
        this.loading(false);
      }
    },
  },
  watch: {
  'capnhat_KQ_OB.tt_ketnoi': async function(newVal) {
    console.log('Current value:', newVal);
    console.log('Type of val:', typeof newVal);
    if (newVal) {
      await this.getListKetQuaOB(newVal);
    }
  }
},

  computed: {
    formattedData() {
      return this.gridThongTinThueBao.map(item => {
        return {
          ...item,
          allow_thaotac:true,
        };
      });
    },
    hienthi_Hentu_Henden(){
      return this.capnhat_KQ_OB.ketqua_ob === '23';
    }
  },
}
</script>
