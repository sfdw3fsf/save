<template src="./OverdueDebtPercentage.html"></template>
<style src="./OverdueDebtPercentage.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component';
import API from './API'
import gridview1 from './gridview1'
import gridview from '@/components/Shared/gridview'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
//
export default {
  components: {breadcrumb,VueFlatPickr,gridview,gridview1,API },
  name: 'OverdueDebtPercentage',
  mounted () {
    this.listbox.kieuNQH.list.push({'ID': 0 ,'KIEU_NQH':"Tháng k+1",'GHICHU':"k - tháng chêm 2 chiều"});
    this.listbox.kieuNQH.list.push({'ID': 1 ,'KIEU_NQH':"Tháng k+2",'GHICHU':"k - tháng chêm 2 chiều"});
    this.LoadGrid();
    this.SetButton(-1);

  },
  data () {
    return {
      dateconfig: {
        altFormat: 'd/m/Y H:i:S',
        altInput: true,
        dateFormat: 'd/m/Y H:i:S',
        allowInput:true,
      },
      ngay_ht: new Date(),
      header: {
        title: 'TỶ LỆ THÙ LAO THU NỢ QUÁ HẠN',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      isNhapMoi:false,
      isGhiLai:false,
      isHuy:false,
      isXoa:false,
      isCapNhat:false,
      isCachTim: false,

      listbox: {
        kieuNQH: {
          SelectValue: null,
          list: [],
          value: {ID: "",KIEU_NQH: "",GHICHU: ""},
          panelDataHeight: 'auto',
          headers: [
            {
              fieldName: 'KIEU_NQH',
              headerText: 'Kiểu NQH',
              width: 100
            },
            {
              fieldName: 'GHICHU',
              headerText: 'Ghi chú'
            }
          ],
        },

        ds_tylenqh: {
          list: [],
          header: [ {fieldName: 'tyle_nqh_id', headerText: 'STT', allowFiltering: true, width:100}, 
                    {fieldName: 'thoidiem_tt', headerText: 'Thời điểm thanh toán', allowFiltering: true}, 
                  /*   {fieldName: 'tyle_nqh', headerText: 'Tỷ lệ', allowFiltering: true},  */
                    {fieldName: "tyle_nqh", headerText: "Tỷ lệ", allowFiltering: true,allowSorting: false, type: "Number", format: "N2", width: 120 },
                       ],
          value: {},
          isEnabled: true,
        },
        ds_tylenqh_theonv_dagan: {
          list: [],
          header: [],
          value: {},
          isEnabled: true,
        },
        ds_tylenqh_theonv_chuagan: {
          list: [],
          header: [],
          value: {},
          isEnabled: true,
        },
        ds_tylenqh_theokv_dagan: {
          list: [],
          header: [],
          value: {},
          isEnabled: true,
        },
        ds_tylenqh_theokv_chuagan: {
          list: [],
          header: [],
          value: {},
          isEnabled: true,
        },
        ds_tylenqh_theockn_dagan: {
          list: [],
          header: [],
          value: {},
          isEnabled: true,
        },
        ds_tylenqh_theockn_chuagan: {
          list: [],
          header: [],
          value: {},
          isEnabled: true,
        },
      },
      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      tungay: new Date(),
      denngay: new Date(),
      DK_tungay: new Date(),
      DK_denngay: new Date(),
      ngaytaogoi: new Date(),
      denngay_cu: new Date(),

      success_code: "BSS-00000000",
      ThoiDiemTT: "",
      NgayTTTu: "0",
      NgayTTDen: "0",
      TyLe: "0",
      TYLE_NQH_ID: "",
      
    }
  },
  computed: {
    NhapMoi: {
      get() { return this.isNhapMoi; },
      set(value) { this.isNhapMoi=value; 
      }
    },
    GhiLai: {
      get() { return this.isGhiLai; },
      set(value) { this.isGhiLai=value; 
      }
    },
    Huy: {
      get() { return this.isHuy; },
      set(value) { this.isHuy=value; 
      }
    },
    Xoa: {
      get() { return this.isXoa; },
      set(value) { this.isXoa=value; 
      }
    },
    
  },
  methods: {
    ShowAlert(mesage,val)
    {
      if (val==0)
      {
        this.$toast.success(mesage);
      }
      else if (val==1)
      {
        this.$toast.warning(mesage);
      }
      else if (val==2)
      {
        this.$toast.error(mesage);
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

    LoadGrid: async function () {
      var input = {};

      this.listbox.ds_tylenqh.list = this.GetData(await API.sp_ds_tyle_nqh(this.axios, input));
      
    },

   /*  DmMucDichCap: {
        data: [],
        disabled: true,
        textField: 'mucdich',
        selectDonViQL: '',
        valueField: 'mucdich_id',
        panelDataHeight: 'auto',
        headers: [
          {
            fieldName: 'mucdich',
            headerText: 'Chọn mục đích cấp',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nhom_md',
            headerText: 'Nhóm',
            allowFiltering: true,
            allowSorting: false,
            isGroupedColumn: true
          }
        ],
        selectMucDichCap: null
      }, */
    
    gridChanged(obj)
    {
      if(!obj) { 
        return; 
      }
      console.log(obj);
      this.ThoiDiemTT = obj.thoidiem_tt;
      this.NgayTTTu = obj.ngaytt_tu;
      this.NgayTTDen = obj.ngaytt_den;
      this.TyLe = obj.tyle_nqh;
      this.listbox.kieuNQH.value.ID = obj.kieu_nqh;
      this.TYLE_NQH_ID = obj.tyle_nqh_id;

      this.HienThi_TYLE_NQH_Theo_LoaiNV();
      this.HienThi_TYLE_NQH_Theo_KhuVuc();
      this.HienThi_TYLE_Theo_ChuKyNo();
      this.SetButton(3);
    },

    HienThi_TYLE_NQH_Theo_LoaiNV: async function ()
    {
      //Lấy dữ liệu chưa gán
      var input1 = {};
      input1.tyle_nqh_id=this.TYLE_NQH_ID;
      input1.loai = 0;
      console.log
      this.listbox.ds_tylenqh_theonv_chuagan.list = this.GetData(await API.sp_ds_tyle_nqh_nhanvien(this.axios, input1));
      this.listbox.ds_tylenqh_theonv_chuagan.header = [{name:'ten',text:''}];
      //Lấy dữ liệu đã gán
      var input = {};
      input.tyle_nqh_id=this.TYLE_NQH_ID;
      input.loai = 1;

      this.listbox.ds_tylenqh_theonv_dagan.list = this.GetData(await API.sp_ds_tyle_nqh_nhanvien(this.axios, input));
      this.listbox.ds_tylenqh_theonv_dagan.header = [{name:'ten',text:''}];
    },

    
    HienThi_TYLE_NQH_Theo_KhuVuc: async function ()
    {
      //Lấy dữ liệu chưa gán
      var input1 = {};
      input1.tyle_nqh_id=this.TYLE_NQH_ID;
      input1.loai = 0;
      this.listbox.ds_tylenqh_theokv_chuagan.list = this.GetData(await API.sp_ds_tyle_nqh_khuvuc(this.axios, input1));
      this.listbox.ds_tylenqh_theokv_chuagan.header = [{name:'ten_kv',text:''}];
      //Lấy dữ liệu đã gán
      var input = {};
      input.tyle_nqh_id=this.TYLE_NQH_ID;
      input.loai = 1;
      this.listbox.ds_tylenqh_theokv_dagan.list = this.GetData(await API.sp_ds_tyle_nqh_khuvuc(this.axios, input));
      this.listbox.ds_tylenqh_theokv_dagan.header = [{name:'ten_kv',text:''}];
    },

    HienThi_TYLE_Theo_ChuKyNo: async function ()
    {
      //Lấy dữ liệu chưa gán
      var input1 = {};
      input1.tyle_nqh_id=this.TYLE_NQH_ID;
      input1.loai = 0;
      this.listbox.ds_tylenqh_theockn_chuagan.list = this.GetData(await API.sp_ds_tyle_nqh_chukyno(this.axios, input1));
      this.listbox.ds_tylenqh_theockn_chuagan.header = [{name:'chukyno',text:''}];
      //Lấy dữ liệu đã gán
      var input = {};
      input.tyle_nqh_id=this.TYLE_NQH_ID;
      input.loai = 1;
      this.listbox.ds_tylenqh_theockn_dagan.list = this.GetData(await API.sp_ds_tyle_nqh_chukyno(this.axios, input));
      this.listbox.ds_tylenqh_theockn_dagan.header = [{name:'chukyno',text:''}];
    },

   
    SetButton(kieu)
    {
      this.isNhapMoi = false;
      this.isGhiLai = false;
      this.isHuy = false;
      this.isXoa = false;
      if (kieu == -1)//Bat dau
      {
        this.isGhiLai = true;
        this.isHuy = true;
        this.$refs.ThoiDiemTT.focus();
      }
      if (kieu == 0)//Bat dau
      {
        this.isNhapMoi = true;
        this.Clear();
      }
      if (kieu == 1)//Them moi
      {
        this.isGhiLai = true;
        this.isHuy = true;
        this.Clear();
        this.$refs.ThoiDiemTT.focus();
      }
      if (kieu == 2)//Huy
      {
        this.isNhapMoi = true;
        this.isXoa = true;
        this.Clear();
        this.$refs.ThoiDiemTT.focus();
      }
      if (kieu == 3)//Edit
      {
        this.isNhapMoi = true;
        this.isXoa = true;
        this.isGhiLai = true;
        this.isHuy = true;
      }
    },

    Clear: async function ()
    {
      this.ThoiDiemTT = "";
      this.NgayTTTu = "0";
      this.NgayTTDen = "0";
      this.TyLe = "0";
      this.TYLE_NQH_ID = -1;

       //Lấy dữ liệu chưa gán
      var input1 = {};
      input1.tyle_nqh_id="";
      input1.loai = 0;
      this.listbox.ds_tylenqh_theonv_chuagan.list = this.GetData(await API.sp_ds_tyle_nqh_nhanvien(this.axios, input1));
      this.listbox.ds_tylenqh_theonv_chuagan.header = [{name:'ten',text:''}];
      //Dữ liệu đã gán
      this.listbox.ds_tylenqh_theonv_dagan.list = [];
      this.listbox.ds_tylenqh_theonv_dagan.header = [{name:'ten',text:''}];
      //Lấy dữ liệu chưa gán
      var input1 = {};
      input1.tyle_nqh_id="";
      input1.loai = 0;
      this.listbox.ds_tylenqh_theokv_chuagan.list = this.GetData(await API.sp_ds_tyle_nqh_khuvuc(this.axios, input1));
      this.listbox.ds_tylenqh_theokv_chuagan.header = [{name:'ten_kv',text:''}];
      //Lấy dữ liệu đã gán
      this.listbox.ds_tylenqh_theokv_dagan.list = [];
      this.listbox.ds_tylenqh_theokv_dagan.header = [{name:'ten_kv',text:''}];
      //Lấy dữ liệu chưa gán
      var input1 = {};
      input1.tyle_nqh_id="";
      input1.loai = 0;
      this.listbox.ds_tylenqh_theockn_chuagan.list = this.GetData(await API.sp_ds_tyle_nqh_chukyno(this.axios, input1));
      this.listbox.ds_tylenqh_theockn_chuagan.header = [{name:'chukyno',text:''}];
      //Lấy dữ liệu đã gán
      this.listbox.ds_tylenqh_theockn_dagan.list = [];
      this.listbox.ds_tylenqh_theockn_dagan.header = [{name:'chukyno',text:''}];
    },

    btnNhapMoi()
    {
      this.SetButton(1);
    },

    btnXoa: async function ()
    {
      this.$bvModal.msgBoxConfirm('Bạn chắc chắn muốn xóa tỷ lệ thù lao thu nợ quá hạn không?', {
      title: '', size: 'sm', okTitle: 'Đồng ý',cancelTitle: 'Hủy',}).then(async v => {
      if(v) {
        //console.log(this.TYLE_NQH_ID);
        var input = {};
        input.tyle_nqh_id=this.TYLE_NQH_ID;
        console.log(input)
        await API.sp_tyle_nqh_xoa(this.axios, input).then((response) => {
          if(response.data.error_code && response.data.error_code === this.success_code) {
            this.ShowAlert("Xóa tỷ lệ thù lao thu nợ quá hạn thành công!",0);
            this.listbox.ds_tylenqh_theonv_dagan.list = [];
            this.listbox.ds_tylenqh_theokv_dagan.list = [];
            this.listbox.ds_tylenqh_theockn_dagan.list = [];
            this.LoadGrid();
          }
          else
          {
            this.ShowAlert("Xóa tỷ lệ thù lao không thành công !" + response.data.message,2);
          }
        }
        ).catch(function(){
          console.log('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
        });
      }
      });
      
    },

    btnHuy()
    {
      this.SetButton(0);
    },

    btnGhiLai: async function ()
    {
     
      if (this.ThoiDiemTT == "")
      {
        this.ShowAlert("Bạn chưa nhập thời điểm thanh toán!",2);
        this.$refs.ThoiDiemTT.focus();
        return;
      }
    
      if(parseInt(this.NgayTTTu) <= 0 || parseInt(this.NgayTTTu) > 31)
      {
        this.ShowAlert("\"Ngày thanh toán từ\" phải lớn hơn 0 và nhỏ hơn 31!",2);
        this.$refs.NgayTTTu.$el.focus();
        return;
      }
      if(parseInt(this.NgayTTDen) <= 0 || parseInt(this.NgayTTDen) > 31)
      {
        this.ShowAlert("\"Ngày thanh toán đến\" phải lớn hơn 0 và nhỏ hơn 31!",2);
        this.$refs.NgayTTDen.$el.focus();
        return;
      }
      if(parseFloat(this.TyLe) <= 0)
      {
        this.ShowAlert("Tỷ lệ thù lao thu nợ quá hạn phải lớn hơn 0!",2);
        this.$refs.TyLe.$el.focus();
        return;
      }
      if(parseInt(this.NgayTTTu) > parseInt(this.NgayTTDen))
      {
        this.ShowAlert("Từ ngày phải nhỏ hơn đến ngày!",2);
        this.$refs.NgayTTDen.focus();
        return;
      }
      if (this.listbox.ds_tylenqh_theockn_dagan.list.length<=0)
      {
        this.ShowAlert("Bạn chưa gán kỳ hóa đơn!",2);
        return;
      }
      if (this.listbox.ds_tylenqh_theonv_dagan.list.length<=0)
      {
        this.ShowAlert("Bạn chưa gán loại nhân viên!",2);
        return;
      }
      if (this.listbox.ds_tylenqh_theokv_dagan.list.length<=0)
      {
        this.ShowAlert("Bạn chưa gán khu vực!",2);
        return;
      }    
      var tyletheonv_dagan = "";
      for (var item of this.listbox.ds_tylenqh_theonv_dagan.list)
      {
        if (tyletheonv_dagan != "")
        {
          tyletheonv_dagan  = tyletheonv_dagan + ",";
        }
        tyletheonv_dagan  = tyletheonv_dagan + item.loainv_id.toString();
      }
      console.log(this.listbox.ds_tylenqh_theokv_dagan.list);
      var tyletheokv_dagan = "";
      for (var item of this.listbox.ds_tylenqh_theokv_dagan.list)
      {
        if (tyletheokv_dagan != "")
        {
          tyletheokv_dagan  = tyletheokv_dagan + ",";
        }
        tyletheokv_dagan  = tyletheokv_dagan + item.khuvuc_id.toString();
      }

      var tyletheockn_dagan = "";
      for (var item of this.listbox.ds_tylenqh_theockn_dagan.list)
      {
        if (tyletheockn_dagan != "")
        {
          tyletheockn_dagan  = tyletheockn_dagan + ",";
        }
        tyletheockn_dagan  = tyletheockn_dagan + item.id.toString();
      }
      var input = {};
      if (!this.isNhapMoi) //insert
      {
        input.tyle_nqh_id ="";
      }
      else //update
      {
        input.tyle_nqh_id = this.TYLE_NQH_ID;
      }
      input.thoidiem_tt = this.ThoiDiemTT;
      input.ngaytt_tu = this.NgayTTTu;
      input.ngaytt_den = this.NgayTTDen;
      input.kieu_nqh =  this.listbox.kieuNQH.SelectValue;
      input.tyle_nqh = parseFloat(this.TyLe);
      input.tyle_nqh_kv = tyletheokv_dagan;
      input.tyle_nqh_lnv = tyletheonv_dagan;
      input.tyle_nqh_khd = tyletheockn_dagan;
      console.log(input);
      await API.sp_tyle_nqh_capnhat(this.axios, input).then((response) => {
        if(response.data.error_code && response.data.error_code === this.success_code) {
          this.ShowAlert("Cập nhật thành công",0);
          this.LoadGrid();
        }
        else
        {
          this.ShowAlert("Cập nhật không thành công !" + response.data.message,2);
        }
      }
      ).catch(function(){
        console.log('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
      });
      
    },

    btnKyHDRight()
    {
      var items = this.listbox.ds_tylenqh_theockn_dagan.list.filter(a => a.ischecked);
      if (items.length > 0) {
        for (var item of items) {
          this.listbox.ds_tylenqh_theockn_chuagan.list.push({'id': item.id, 'chukyno': item.chukyno});
        }
      }
      var temps = this.listbox.ds_tylenqh_theockn_dagan.list.filter(a => !items.includes(a));
      this.listbox.ds_tylenqh_theockn_dagan.list = [];
      this.listbox.ds_tylenqh_theockn_dagan.list = temps;
    },

    btnKyHDRightAll()
    {
      if (this.listbox.ds_tylenqh_theockn_dagan.list.length > 0) {
        for (var item of this.listbox.ds_tylenqh_theockn_dagan.list) {
          this.listbox.ds_tylenqh_theockn_chuagan.list.push({'id': item.id, 'chukyno': item.chukyno});
        }
        this.listbox.ds_tylenqh_theockn_dagan.list = [];
      }
    },

    btnKyHDLeft()
    {
      var items = this.listbox.ds_tylenqh_theockn_chuagan.list.filter(a => a.ischecked);
      if (items.length > 0) {
        for (var item of items) {
          this.listbox.ds_tylenqh_theockn_dagan.list.push({'id': item.id, 'chukyno': item.chukyno});
        }
      }
      var temps = this.listbox.ds_tylenqh_theockn_chuagan.list.filter(a => !items.includes(a));
      this.listbox.ds_tylenqh_theockn_chuagan.list = [];
      this.listbox.ds_tylenqh_theockn_chuagan.list = temps;
    },

    btnKyHDLeftAll()
    {
      if (this.listbox.ds_tylenqh_theockn_chuagan.list.length > 0) {
        for (var item of this.listbox.ds_tylenqh_theockn_chuagan.list) {
          this.listbox.ds_tylenqh_theockn_dagan.list.push({'id': item.id, 'chukyno': item.chukyno});
        }
        this.listbox.ds_tylenqh_theockn_chuagan.list = [];
      }
    },

    btnNVRight()
    {
      var items = this.listbox.ds_tylenqh_theonv_dagan.list.filter(a => a.ischecked);
      if (items.length > 0) {
        for (var item of items) {
          this.listbox.ds_tylenqh_theonv_chuagan.list.push({'loainv_id': item.loainv_id, 'ten': item.ten});
        }
      }
      var temps = this.listbox.ds_tylenqh_theonv_dagan.list.filter(a => !items.includes(a));
      this.listbox.ds_tylenqh_theonv_dagan.list = [];
      this.listbox.ds_tylenqh_theonv_dagan.list = temps;
    },

    btnNVRightAll()
    {
       if (this.listbox.ds_tylenqh_theonv_dagan.list.length > 0) {
        for (var item of this.listbox.ds_tylenqh_theonv_dagan.list) {
          this.listbox.ds_tylenqh_theonv_chuagan.list.push({'loainv_id': item.loainv_id, 'ten': item.ten});
        }
        this.listbox.ds_tylenqh_theonv_dagan.list = [];
      }
    },

    btnNVLeft()
    {
      var items = this.listbox.ds_tylenqh_theonv_chuagan.list.filter(a => a.ischecked);
      if (items.length > 0) {
        for (var item of items) {
          this.listbox.ds_tylenqh_theonv_dagan.list.push({'loainv_id': item.loainv_id, 'ten': item.ten});
        }
      }
      var temps = this.listbox.ds_tylenqh_theonv_chuagan.list.filter(a => !items.includes(a));
      this.listbox.ds_tylenqh_theonv_chuagan.list = [];
      this.listbox.ds_tylenqh_theonv_chuagan.list = temps;
    },
    
    btnNVLeftAll()
    {
      if (this.listbox.ds_tylenqh_theonv_chuagan.list.length > 0) {
        for (var item of this.listbox.ds_tylenqh_theonv_chuagan.list) {
          this.listbox.ds_tylenqh_theonv_dagan.list.push({'loainv_id': item.loainv_id, 'ten': item.ten});
        }
        this.listbox.ds_tylenqh_theonv_chuagan.list = [];
      }
    },

    btnKVRight()
    {
      var items = this.listbox.ds_tylenqh_theokv_dagan.list.filter(a => a.ischecked);
      if (items.length > 0) {
        for (var item of items) {
          this.listbox.ds_tylenqh_theokv_chuagan.list.push({'khuvuc_id': item.khuvuc_id, 'ten_kv': item.ten_kv});
        }
      }
      var temps = this.listbox.ds_tylenqh_theokv_dagan.list.filter(a => !items.includes(a));
      this.listbox.ds_tylenqh_theokv_dagan.list = [];
      this.listbox.ds_tylenqh_theokv_dagan.list = temps;
    },

    btnKVRightAll()
    {
      if (this.listbox.ds_tylenqh_theokv_dagan.list.length > 0) {
        for (var item of this.listbox.ds_tylenqh_theokv_dagan.list) {
          this.listbox.ds_tylenqh_theokv_chuagan.list.push({'khuvuc_id': item.khuvuc_id, 'ten_kv': item.ten_kv});
        }
        this.listbox.ds_tylenqh_theokv_dagan.list = [];
      }
    },

    btnKVLeft()
    {
      var items = this.listbox.ds_tylenqh_theokv_chuagan.list.filter(a => a.ischecked);
      if (items.length > 0) {
        for (var item of items) {
          this.listbox.ds_tylenqh_theokv_dagan.list.push({'khuvuc_id': item.khuvuc_id, 'ten_kv': item.ten_kv});
        }
      }
      var temps = this.listbox.ds_tylenqh_theokv_chuagan.list.filter(a => !items.includes(a));
      this.listbox.ds_tylenqh_theokv_chuagan.list = [];
      this.listbox.ds_tylenqh_theokv_chuagan.list = temps;
    },
    
    btnKVLeftAll()
    {
      if (this.listbox.ds_tylenqh_theokv_chuagan.list.length > 0) {
        for (var item of this.listbox.ds_tylenqh_theokv_chuagan.list) {
          this.listbox.ds_tylenqh_theokv_dagan.list.push({'khuvuc_id': item.khuvuc_id, 'ten_kv': item.ten_kv});
        }
        this.listbox.ds_tylenqh_theokv_chuagan.list = [];
      }
    },
  },
};

</script>
