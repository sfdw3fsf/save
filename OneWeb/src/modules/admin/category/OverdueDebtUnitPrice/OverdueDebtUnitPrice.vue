<template src="./OverdueDebtUnitPrice.html"></template>
<style src="./OverdueDebtUnitPrice.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component';
import API from './API'
import gridview1 from './gridview1'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
//
export default {
  components: {breadcrumb,VueFlatPickr,gridview1,API },
  name: 'OverdueDebtUnitPrice',
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
        title: 'ĐƠN GIÁ THÙ LAO PHÁT GIẤY BÁO CƯỚC',
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
          list: [],
          value: {ID: "",KIEU_NQH: "",GHICHU: ""},
        },

        ds_thulaoPGBC: {
          list: [],
          header: [ {fieldName: 'tyle_nqh_id', headerText: 'STT', allowFiltering: true, width:100}, 
                    {fieldName: 'thoidiem_tt', headerText: 'Nội dung', allowFiltering: true}, 
                    {fieldName: 'tyle_nqh', headerText: 'Đơn giá', allowFiltering: true}, 
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
    ShowAlert(mesage)
    {
      this.$toast.error(mesage);
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
      this.listbox.ds_thulaoPGBC.list = this.GetData(await API.sp_ds_tyle_nqh(this.axios, input));
    },
    
    gridChanged(obj)
    {
      if(!obj) { 
        return; 
      }
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
      input1.dongia_nqh_id=this.TYLE_NQH_ID;
      input1.loai = 0;
      this.listbox.ds_tylenqh_theonv_chuagan.list = this.GetData(await API.sp_ds_tyle_nqh_nhanvien(this.axios, input1));
      this.listbox.ds_tylenqh_theonv_chuagan.header = [{name:'ten',text:''}];
      //Lấy dữ liệu đã gán
      var input = {};
      input.dongia_nqh_id=this.TYLE_NQH_ID;
      input.loai = 1;
      this.listbox.ds_tylenqh_theonv_dagan.list = this.GetData(await API.sp_ds_tyle_nqh_nhanvien(this.axios, input));
      this.listbox.ds_tylenqh_theonv_dagan.header = [{name:'ten',text:''}];
    },

    
    HienThi_TYLE_NQH_Theo_KhuVuc: async function ()
    {
      //Lấy dữ liệu chưa gán
      var input1 = {};
      input1.dongia_nqh_id=this.TYLE_NQH_ID;
      input1.loai = 0;
      this.listbox.ds_tylenqh_theokv_chuagan.list = this.GetData(await API.sp_ds_tyle_nqh_khuvuc(this.axios, input1));
      this.listbox.ds_tylenqh_theokv_chuagan.header = [{name:'ten_kv',text:''}];
      //Lấy dữ liệu đã gán
      var input = {};
      input.dongia_nqh_id=this.TYLE_NQH_ID;
      input.loai = 1;
      this.listbox.ds_tylenqh_theokv_dagan.list = this.GetData(await API.sp_ds_tyle_nqh_khuvuc(this.axios, input));
      this.listbox.ds_tylenqh_theokv_dagan.header = [{name:'ten_kv',text:''}];
    },

    HienThi_TYLE_Theo_ChuKyNo: async function ()
    {
      //Lấy dữ liệu chưa gán
      var input1 = {};
      input1.dongia_nqh_id=this.TYLE_NQH_ID;
      input1.loai = 0;
      this.listbox.ds_tylenqh_theockn_chuagan.list = this.GetData(await API.sp_ds_tyle_nqh_chukyno(this.axios, input1));
      this.listbox.ds_tylenqh_theockn_chuagan.header = [{name:'chukyno',text:''}];
      //Lấy dữ liệu đã gán
      var input = {};
      input.dongia_nqh_id=this.TYLE_NQH_ID;
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

    Clear()
    {
      this.ThoiDiemTT = "";
      this.NgayTTTu = "0";
      this.NgayTTDen = "0";
      this.TyLe = "0";
      this.TYLE_NQH_ID = -1;
    },

    btnNhapMoi()
    {
      this.SetButton(1);
    },

    btnXoa()
    {
      this.$bvModal.msgBoxConfirm('Bạn chắc chắn muốn xóa tỷ lệ thù lao thu nợ quá hạn không?', {
      title: '', size: 'sm', okTitle: 'Đồng ý',cancelTitle: 'Hủy',}).then(async v => {
      if(v) {
        //console.log(this.TYLE_NQH_ID);
        var input = {};
        input.dongia_nqh_id=this.TYLE_NQH_ID;
        API.sp_tyle_nqh_xoa(this.axios, input).then((response) => {
          if(response.data.error_code && response.data.error_code === this.success_code) {
            this.ShowAlert("Xóa tỷ lệ thù lao thu nợ quá hạn thành công!");
            this.LoadGrid();
          }
          else
          {
            this.ShowAlert("Xóa tỷ lệ thù lao không thành công !" + response.data.message);
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

    btnGhiLai()
    {
      if (this.ThoiDiemTT.trim() == "")
      {
        this.ShowAlert("Bạn chưa nhập thời điểm thanh toán!");
        this.$refs.ThoiDiemTT.focus();
        return;
      }
      if (this.NgayTTTu == "")
      {
        this.ShowAlert("Bạn chưa nhập ngày thanh toán!");
        this.$refs.NgayTTTu.focus();
        return;
      }
      else if(parseInt(this.NgayTTTu) <= 0 || parseInt(this.NgayTTTu) > 31)
      {
        this.ShowAlert("Ngày thanh toán phải lớn hơn 0 và nhỏ hơn 31!");
        this.$refs.NgayTTTu.focus();
        return;
      }
      if (this.NgayTTDen == "")
      {
        this.ShowAlert("Bạn chưa nhập ngày thanh toán đến!");
        this.$refs.NgayTTDen.focus();
        return;
      }
      else if(parseInt(this.NgayTTDen) <= 0 || parseInt(this.NgayTTDen) > 31)
      {
        this.ShowAlert("Ngày thanh toán phải lớn hơn 0 và nhỏ hơn 31!");
        this.$refs.NgayTTDen.focus();
        return;
      }
      if (this.TyLe == "")
      {
        this.ShowAlert("Bạn chưa nhập tỷ lệ nợ quá hạn!");
        this.$refs.TyLe.focus();
        return;
      }
      else if(parseInt(this.TyLe) <= 0)
      {
        this.ShowAlert("Tỷ lệ thù lao thu nợ quá hạn phải lớn hơn 0!");
        this.$refs.TyLe.focus();
        return;
      }
      if(parseInt(this.NgayTTTu) > parseInt(this.NgayTTDen))
      {
        this.ShowAlert("Từ ngày phải nhỏ hơn đến ngày!");
        this.$refs.NgayTTDen.focus();
        return;
      }
           
          /*   if (clbChuKyNoDaGan.Items.Count <= 0)
            {
                Message_Box.ShowWarning("Bạn chưa gán kỳ hóa đơn!");
                return false;
            }

            if (clbKhuVucDaGan.Items.Count <= 0)
            {
                Message_Box.ShowWarning("Bạn chưa gán khu vực!");
                return false;
            }

            if (clbNhanVienDaGan.Items.Count <= 0)
            {
                Message_Box.ShowWarning("Bạn chưa gán loại nhân viên!");
                return false;
            } */
      
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
      input.kieu_nqh =  this.listbox.kieuNQH.value.ID;
      input.tyle_nqh = this.TyLe;
      input.tyle_nqh_kv = tyletheokv_dagan;
      input.tyle_nqh_lnv = tyletheonv_dagan;
      input.tyle_nqh_khd = tyletheockn_dagan;
      console.log(input);
      API.sp_tyle_nqh_capnhat(this.axios, input).then((response) => {
        if(response.data.error_code && response.data.error_code === this.success_code) {
            this.ShowAlert("Cập nhật thành công");
        }
        else
        {
          this.ShowAlert("Cập nhật không thành công !" + response.data.message);
        }
      }
      ).catch(function(){
        console.log('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
      });
      this.LoadGrid();
      
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
