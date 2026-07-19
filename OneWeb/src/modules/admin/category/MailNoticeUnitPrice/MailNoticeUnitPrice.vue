<template src="./MailNoticeUnitPrice.html"></template>
<style src="./MailNoticeUnitPrice.scss"></style>
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
  name: 'MailNoticeUnitPrice',
  mounted () {
    //this.listbox.kieuNQH.list.push({'ID': 0 ,'KIEU_NQH':"Tháng k+1",'GHICHU':"k - tháng chêm 2 chiều"});
    //this.listbox.kieuNQH.list.push({'ID': 1 ,'KIEU_NQH':"Tháng k+2",'GHICHU':"k - tháng chêm 2 chiều"});
    this.LoadGrid();
    if (this.listbox.ds_thulaoPTTB.list.length==0)
    {
      this.SetButton(0);
    }
    else
    {
      this.SetButton(-1);
    }
    

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

        ds_thulaoPTTB: {
          list: [],
          header: [ {fieldName: 'dongia_tbn_id', headerText: 'STT', allowFiltering: true, width:100}, 
                    {fieldName: 'dongia_tbn', headerText: 'Nội dung', allowFiltering: true}, 
                   /*  {fieldName: 'dongia', headerText: 'Đơn giá', allowFiltering: true},  */
                    {fieldName: "dongia", headerText: "Đơn giá", allowFiltering: true,allowSorting: false, type: "Number", format: "N0", width: 120 },
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
      NoiDung: "",
      DonGia: "0",
      DonGiaID: "",
      index: 0,
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
      var tempdata = [];
      tempdata = this.GetData(await API.sp_ds_dongia_tbn(this.axios, input));
      let i=0;
      for(const item of tempdata){
        i++;
        item.phanvung_id = item.dongia_tbn_id;
        item.dongia_tbn_id = i;
      }
      this.listbox.ds_thulaoPTTB.list = tempdata ; 
    },
    
    gridChanged(obj)
    {
      if(!obj) { 
        return; 
      }
      console.log(obj);
      this.NoiDung = obj.dongia_tbn;
      this.DonGia = obj.dongia;
      this.DonGiaID = obj.phanvung_id;

      this.HienThi_TYLE_NQH_Theo_LoaiNV();
      this.HienThi_TYLE_NQH_Theo_KhuVuc();
      this.HienThi_TYLE_Theo_ChuKyNo();
      this.SetButton(3);
    },

    rowClicked(i,obj)
    {
      if(!obj) { 
        return; 
      }
      this.index = i;
      
    },

    HienThi_TYLE_NQH_Theo_LoaiNV: async function ()
    {
      //Lấy dữ liệu chưa gán
      var input1 = {};
      input1.dongia_tbn_id=this.DonGiaID;
      input1.loai = 0;
      console.log(input1);
      this.listbox.ds_tylenqh_theonv_chuagan.list = this.GetData(await API.sp_ds_dongia_tbn_nhanvien(this.axios, input1));
      this.listbox.ds_tylenqh_theonv_chuagan.header = [{name:'ten',text:''}];
      //Lấy dữ liệu đã gán
      var input = {};
      input.dongia_tbn_id=this.DonGiaID;
      input.loai = 1;
      console.log(input);

      this.listbox.ds_tylenqh_theonv_dagan.list = this.GetData(await API.sp_ds_dongia_tbn_nhanvien(this.axios, input));
      this.listbox.ds_tylenqh_theonv_dagan.header = [{name:'ten',text:''}];
    },

    
    HienThi_TYLE_NQH_Theo_KhuVuc: async function ()
    {
      //Lấy dữ liệu chưa gán
      var input1 = {};
      input1.dongia_tbn_id=this.DonGiaID;
      input1.loai = 0;
      this.listbox.ds_tylenqh_theokv_chuagan.list = this.GetData(await API.sp_ds_dongia_tbn_khuvuc(this.axios, input1));
      this.listbox.ds_tylenqh_theokv_chuagan.header = [{name:'ten_kv',text:''}];
      //Lấy dữ liệu đã gán
      var input = {};
      input.dongia_tbn_id=this.DonGiaID;
      input.loai = 1;
      this.listbox.ds_tylenqh_theokv_dagan.list = this.GetData(await API.sp_ds_dongia_tbn_khuvuc(this.axios, input));
      this.listbox.ds_tylenqh_theokv_dagan.header = [{name:'ten_kv',text:''}];
    },

    HienThi_TYLE_Theo_ChuKyNo: async function ()
    {
      //Lấy dữ liệu chưa gán
      var input1 = {};
      input1.dongia_tbn_id=this.DonGiaID;
      input1.loai = 0;
      this.listbox.ds_tylenqh_theockn_chuagan.list = this.GetData(await API.sp_ds_dongia_tbn_chukyno(this.axios, input1));
      this.listbox.ds_tylenqh_theockn_chuagan.header = [{name:'chukyno',text:''}];
      //Lấy dữ liệu đã gán
      var input = {};
      input.dongia_tbn_id=this.DonGiaID;
      input.loai = 1;
      this.listbox.ds_tylenqh_theockn_dagan.list = this.GetData(await API.sp_ds_dongia_tbn_chukyno(this.axios, input));
      this.listbox.ds_tylenqh_theockn_dagan.header = [{name:'chukyno',text:''}];
    },

   
    SetButton: async function(kieu)
    {
      this.isNhapMoi = false;
      this.isGhiLai = false;
      this.isHuy = false;
      this.isXoa = false;
      if (kieu == -1)//Bat dau
      {
        this.isGhiLai = true;
        this.isHuy = true;
        this.$refs.NoiDung.focus();
      }
      if (kieu == 0)//Bat dau
      {
        this.isNhapMoi = true;
        await this.Clear();
        this.$refs.grid.setCurrentSelectedRow(this.index);
      }
      if (kieu == 1)//Them moi
      {
        this.isGhiLai = true;
        this.isHuy = true;
        await this.Clear();
        this.$refs.NoiDung.focus();
      }
      if (kieu == 2)//Huy
      {
        this.isNhapMoi = true;
        this.isXoa = true;
        await this.Clear();
        this.$refs.NoiDung.focus();
      }
      if (kieu == 3)//Edit
      {
        this.isNhapMoi = true;
        this.isXoa = true;
        this.isGhiLai = true;
        this.isHuy = true;
      }
    },

    Clear: async function()
    {
      this.NoiDung = "";
      this.DonGia = "0";
      this.DonGiaID = -1;

       //Lấy dữ liệu chưa gán
      var input1 = {};
      input1.dongia_tbn_id="";
      input1.loai = 0;
      this.listbox.ds_tylenqh_theonv_chuagan.list = this.GetData(await API.sp_ds_dongia_tbn_nhanvien(this.axios, input1));
      this.listbox.ds_tylenqh_theonv_chuagan.header = [{name:'ten',text:''}];
      //Dữ liệu đã gán
      this.listbox.ds_tylenqh_theonv_dagan.list = [];
      this.listbox.ds_tylenqh_theonv_dagan.header = [{name:'ten',text:''}];
      //Lấy dữ liệu chưa gán
      var input1 = {};
      input1.dongia_tbn_id="";
      input1.loai = 0;
      this.listbox.ds_tylenqh_theokv_chuagan.list = this.GetData(await API.sp_ds_dongia_tbn_khuvuc(this.axios, input1));
      this.listbox.ds_tylenqh_theokv_chuagan.header = [{name:'ten_kv',text:''}];
      //Lấy dữ liệu đã gán
      this.listbox.ds_tylenqh_theokv_dagan.list = [];
      this.listbox.ds_tylenqh_theokv_dagan.header = [{name:'ten_kv',text:''}];
      //Lấy dữ liệu chưa gán
      var input1 = {};
      input1.dongia_tbn_id="";
      input1.loai = 0;
      this.listbox.ds_tylenqh_theockn_chuagan.list = this.GetData(await API.sp_ds_dongia_tbn_chukyno(this.axios, input1));
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
      this.$bvModal.msgBoxConfirm('Bạn chắc chắn muốn xóa đơn giá phát thư thông báo không?', {
      title: '', size: 'sm', okTitle: 'Đồng ý',cancelTitle: 'Hủy',}).then(async v => {
      if(v) {
        //console.log(this.TYLE_NQH_ID);
        var input = {};
        input.dongia_tbn_id=this.DonGiaID;
        await API.sp_ds_dongia_tbn_xoa(this.axios, input).then(async (response) => {
          if(response.data.error_code && response.data.error_code === this.success_code) {
            this.ShowAlert("Xóa đơn giá phát thư thông báo thành công!",0);
            await this.LoadGrid();
            if (this.listbox.ds_thulaoPTTB.list.length==0)
            {
              this.SetButton(0);
            }
          }
          else
          {
            this.ShowAlert("Xóa thư thông báo không thành công !" + response.data.message,2);
          }
        }
        ).catch(function(){
          console.log('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.',2);
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
      if (this.NoiDung == "")
      {
        this.ShowAlert("Bạn chưa nhập nội dung!",2);
        this.$refs.NoiDung.focus();
        return;
      }
      if (this.DonGia == "0" || this.DonGia == "")
      {
        this.ShowAlert("Bạn chưa nhập đơn giá!",2);
        this.$refs.DonGia.focus();
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
        input.dongia_tbn_id ="";
      }
      else //update
      {
        input.dongia_tbn_id = this.DonGiaID;
      }
      input.dongia_tbn = this.NoiDung;
      input.dongia = this.DonGia;
      input.dongia_tbn_kv = tyletheokv_dagan;
      input.dongia_tbn_lnv = tyletheonv_dagan;
      input.dongia_tbn_khd = tyletheockn_dagan;
      console.log(input);
      API.sp_ds_dongia_tbn_capnhat(this.axios, input).then((response) => {
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
