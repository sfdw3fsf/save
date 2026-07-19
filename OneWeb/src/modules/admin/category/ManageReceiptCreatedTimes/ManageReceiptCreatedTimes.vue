<template src="./ManageReceiptCreatedTimes.html"></template>
<style src="./ManageReceiptCreatedTimes.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component';
import API from './API'
import { DataManager, Query, ODataAdaptor, Predicate } from '@syncfusion/ej2-data'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
//
export default {
  components: {breadcrumb,VueFlatPickr,API,DataManager, Query, ODataAdaptor, Predicate },
  name: 'ManageReceiptCreatedTimes',
  mounted () {
    this.LoadCombobox();
    this.SetButton(0);

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
        title: 'GÁN THUỘC TÍNH VẬT TƯ',
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

      isSoBL: false,
      isTuSo: false,
      isDenSo: false,

       listbox: {
          ds_khobienlai: {
          list: [],
          header: [ {fieldName: 'so_bl', headerText: 'Số biên lai', allowFiltering: true}, 
                    {fieldName: 'ten_loaibl', headerText: 'Tên loại biên lai', allowFiltering: true},
                    {fieldName: 'ma_luottao', headerText: 'Lượt tạo', allowFiltering: true},
                    {fieldName: 'ngay_tao', headerText: 'Ngày tạo', allowFiltering: true},
                    {fieldName: 'nguoi_tao', headerText: 'Người tạo', allowFiltering: true},
                    {fieldName: 'ttbl_ten', headerText: 'Trạng thái', allowFiltering: true},
                    {fieldName: 'ten_kho', headerText: 'Kho', allowFiltering: true},
                       ],
          value: {},
          checked: []
        },
        ds_luottaobl: {
          list: [],
          header: [ {fieldName: 'ma_luottao', headerText: 'Mã lượt tạo', allowFiltering: true}, 
                    {fieldName: 'tu_so', headerText: 'Từ số', allowFiltering: true},
                    {fieldName: 'den_so', headerText: 'Đến số', allowFiltering: true},
                    {fieldName: 'tong_so', headerText: 'Tổng số', allowFiltering: true},
                    {fieldName: 'kyhieu', headerText: 'Ký hiệu', allowFiltering: true},
                    {fieldName: 'ten_loaibl', headerText: 'Tên loại', allowFiltering: true},
                    {fieldName: 'ma_luottao', headerText: 'Nhóm lượt tạo', allowFiltering: true , allowHtml : true, isGroupedColumn:true},

                       ],
          value: {},
          checked: []
        },
        LoaiBienLai: {
          list: [],
          value: {},
          },
        KhoBienLai: {
          list: [],
          value: {},
          },
        ds_khobienlai_temp: [],

         
      /* 
        },
        NhomLVT: {
          list: [],
          value: {loaivt_id: "",loai_vt: ""},
        },
        LVT: {
          list: [],
          value: {loaivt_id: "",loai_vt: ""},
        },
        DonVi: {
          list: [],
          value: {DONVI_ID: "",TEN_DV: ""},
        },
        DonVi1: {
          list: [],
          value: {DONVI_ID: "",TEN_DV: ""},
        },
        
        ds_donvidagan: {
          list: [],
          header: [ {fieldName: 'ten_dv', headerText: 'Đơn vị', allowFiltering: true}, 
                    {fieldName: 'dinhmuc', headerText: 'Định mức', allowFiltering: true}, 
                       ],
          value: {},
          checked: []
        },

        ds_donvichuagan:{
           list:[],
           value:"",
           isEnabled: false,
           checked: []
        },
        fields: {
          dataSource: null,
          id: 'donvi_id',
          parentID: 'donvi_cha_id',
          text: 'ten_dv',
          hasChildren: 'hasChild'
        },
        DsKhuVuc: [],
        dsdoituong_all: [],
        ds_nvdagan: {
          list: [],
          header: [ {fieldName: 'nhanvien_id', headerText: 'Mã NV', allowFiltering: true}, 
                    {fieldName: 'ten_nv', headerText: 'Tên NV', allowFiltering: true}, 
                    {fieldName: 'dinhmuc', headerText: 'Định mức', allowFiltering: true}, 
                       ],
          value: {},
          checked: []
        },
        ds_nvchuagan: {
          list: [],
          header: [ {fieldName: 'nhanvien_id', headerText: 'Mã NV', allowFiltering: true}, 
                    {fieldName: 'ten_nv', headerText: 'Tên NV', allowFiltering: true}, 
                    {fieldName: 'dinhmuc', headerText: 'Định mức', allowFiltering: true}, 
                       ],
          value: {},
          checked: []
        },

        ds_chucdanhdagan: {
          list: [],
          header: [ {fieldName: 'vitri_id', headerText: 'Mã chức danh', allowFiltering: true}, 
                    {fieldName: 'ten_vt', headerText: 'Tên chức danh', allowFiltering: true}, 
                    {fieldName: 'dinhmuc', headerText: 'Định mức', allowFiltering: true}, 
                       ],
          value: {},
          checked: []
        },
        ds_chucdanhchuagan: {
          list: [],
          header: [ {fieldName: 'vitri_id', headerText: 'Mã chức danh', allowFiltering: true}, 
                    {fieldName: 'ten_vt', headerText: 'Tên chức danh', allowFiltering: true}, 
                    {fieldName: 'dinhmuc', headerText: 'Định mức', allowFiltering: true}, 
                       ],
          value: {},
          checked: []
        }, */
      },
      MaLuotTao: "",
      SoBL: "0",
      TuSo: "0",
      DenSo: "0",
      TongSoBienLai: "0",
      SoQuyen: "0",
       dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      success_code: "BSS-00000000",
      vluottao_id : 0,
      /*
      tungay: new Date(),
      denngay: new Date(),
      DK_tungay: new Date(),
      DK_denngay: new Date(),
      ngaytaogoi: new Date(),
      denngay_cu: new Date(),

      txtTenVatTu:"",

      ThoiDiemTT: "",
      NgayTTTu: "0",
      NgayTTDen: "0",
      TyLe: "0",
      TYLE_NQH_ID: "",
      TabIndex: 0, */
      
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
    SetSoBL: {
      get() { return this.isSoBL; },
      set(value) { this.isSoBL=value; 
      }
    }, 
    SetTuSo: {
      get() { return this.isTuSo; },
      set(value) { this.isTuSo=value; 
      }
    },
    SetDenSo: {
      get() { return this.isDenSo; },
      set(value) { this.isDenSo=value; 
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
    validateNumber: event => {
      let keyCode = event.keyCode;
      if (keyCode < 48 || keyCode > 57) {
        event.preventDefault();
      }
    },
    selectedItems_click: function (dataKeys) {
      this.listbox.ds_luottaobl.checked = dataKeys
      this.listbox.ds_luottaobl.list.forEach(function (item) {
        if (dataKeys.filter(x => x === item.luottao_id).length > 0) {
          item.ischecked = true
        } else {
          item.ischecked = false
        }
      })
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
        this.$refs.MaLuotTao.focus();
      }
      if (kieu == 0)//Bat dau
      {
        this.$refs.MaLuotTao.focus();
        this.isNhapMoi = true;
        this.Clear();
      }
      if (kieu == 1)//Them moi
      {
        this.$refs.MaLuotTao.focus();
        this.isNhapMoi = false;
        this.isGhiLai = true;
        this.isHuy = true;
        this.isSoBL = true;
        this.isTuSo = true;
        this.isDenSo = true;
        this.Clear();
        this.$refs.MaLuotTao.focus();
      }
      if (kieu == 2)//Huy
      {
        this.isNhapMoi = true;
        this.isXoa = false;
        this.isSoBL = false;
        this.isTuSo = false;
        this.isDenSo = false;
        this.Clear();
        this.$refs.MaLuotTao.focus();
      }
      if (kieu == 3)//Edit
      {
        this.$refs.MaLuotTao.focus();
        this.isNhapMoi = false;
        this.isXoa = true;
        this.isGhiLai = true;
        this.isHuy = true;
        this.isSoBL = false;
        this.isTuSo = false;
        this.isDenSo = false;
      }
    },
    Clear()
    {
      this.MaLuotTao = "";
      this.TuSo = "0";
      this.DenSo = "0";
      this.SoBL = "0";
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    LoadCombobox: async function ()
    {
      //Load loại biên lai
      var input = {};
      this.loading(true);
      this.listbox.LoaiBienLai.list = this.GetData(await API.sp_lay_ds_loai_bienlai(this.axios, input)).filter(x=>x.loaibienlai_id > 0).map(x=>({id:x.loaibienlai_id,text:x.ten_loaibl}));
      this.loading(false);
      if (this.listbox.LoaiBienLai.list.length>0)
      {
        this.listbox.LoaiBienLai.value.loaibienlai_id = this.listbox.LoaiBienLai.list[0].id;
      }
      //Load kho biên lai
      var input1 = {};
      this.loading(true);
      this.listbox.KhoBienLai.list = this.GetData(await API.sp_lay_ds_kho_bienlai(this.axios, input1)).filter(x=>x.kho_id > 0).map(x=>({id:x.kho_id,text:x.ten_kho}));
      this.loading(false);
       if (this.listbox.KhoBienLai.list.length>0)
      {
        this.listbox.KhoBienLai.value.kho_id = this.listbox.KhoBienLai.list[0].id;
      }
      //Lấy từ số-đến số
      var input2 = {};
      input2.loai_bienlai_id =1;
      this.loading(true);
      this.TuSo = this.GetData(await API.lay_tuso_from_luottao(this.axios, input2));
      this.DenSo = this.TuSo;
      this.loading(false);
    },
    
    KhoBienLaiChanged()
    {
      if (this.listbox.KhoBienLai.value.kho_id != null || this.listbox.KhoBienLai.value.kho_id !="")
      {
        this.listbox.ds_khobienlai.list = this.ds_khobienlai_temp.filter(x => x.kho_id == this.listbox.KhoBienLai.value.kho_id);
      }
    },
    LoaiBienLaiChanged: async function ()
    {
      var input = {};
      input.vloaibienlai_id = this.listbox.LoaiBienLai.value.loaibienlai_id;
      input.vluottao_id = 3575; //phải truyền vào 0, giá trị này chỉ fix để test
      console.log(input);
      this.loading(true);
      API.lay_ds_luottao_vs_bienlai(this.axios, input).then((response) => {
        this.loading(false);
        if(response.data.error_code && response.data.error_code === this.success_code) {
            this.listbox.ds_luottaobl.list = response.data.data.returnds;
            this.listbox.ds_khobienlai.list = response.data.data.returnds2;
            this.ds_khobienlai_temp = this.listbox.ds_khobienlai.list;
            this.SetButton(1);
            }
          }
      );
    },
    ItemsKhoBL_click()
    {

    },
    RowLuotTaoBL_Changed(obj)
    {
       if(!obj) { 
        return; 
      }
      this.MaLuotTao = obj.tach_ma;
      this.TuSo = obj.tu_so;
      this.DenSo =  obj.den_so;
      this.TongSoBienLai = obj.tong_so;
      this.SoBL = obj.tong_so;
      this.vluottao_id = obj.luottao_id;
      this.SetButton(3);
    },
    btnNhapMoi: async function ()
    {
      this.SetButton(1);
      this.MaLuotTao = "";
      this.vluottao_id = 0;
      //Lấy từ số-đến số
      var input2 = {};
      input2.loai_bienlai_id =1;
      this.loading(true);
      this.TuSo = this.GetData(await API.lay_tuso_from_luottao(this.axios, input2));
      this.DenSo = this.TuSo;
      this.loading(false);
    },

    onTuSo()
    {
      this.TongSoBienLai = (this.DenSo == ""? 0: parseInt(this.DenSo)) - (this.TuSo == ""? 0: parseInt(this.TuSo)) + 1;
      var SoBLQuyen = this.SoBL;
      if (SoBLQuyen == 0)
      {
        SoBLQuyen = 1;
      }
      this.SoQuyen = (parseInt(this.TongSoBienLai)/parseInt(SoBLQuyen));
    },

    onDenSo()
    {
      this.TongSoBienLai = (this.DenSo == ""? 0: parseInt(this.DenSo)) - (this.TuSo == ""? 0: parseInt(this.TuSo)) + 1;
      var SoBLQuyen = this.SoBL;
      if (SoBLQuyen == 0)
      {
        SoBLQuyen = 1;
      }
      this.SoQuyen = (parseInt(this.TongSoBienLai)/parseInt(SoBLQuyen));
    },

    refresh()
    {
      var input = {};
      input.vloaibienlai_id = this.listbox.LoaiBienLai.value.loaibienlai_id;
      input.vluottao_id = 3575; //this.vluottao_id;
      console.log(input);
      this.loading(true);
      API.lay_ds_luottao_vs_bienlai(this.axios, input).then((response) => {
        this.loading(false);
        if(response.data.error_code && response.data.error_code === this.success_code) {
          this.listbox.ds_luottaobl.list = response.data.data.returnds;
          this.listbox.ds_khobienlai.list = response.data.data.returnds2;
          this.ds_khobienlai_temp = this.listbox.ds_khobienlai.list;
          this.SetButton(3);
        }
        }
      );
    },

    btnGhiLai()
    {
      if (this.MaLuotTao == "")
      {
        this.ShowAlert("Bạn chưa nhập Mã lượt tạo !",1);
        this.$refs.MaLuotTao.focus();
        return;
      }
      if (this.TuSo == "")
      {
        this.ShowAlert("Bạn chưa nhập Số biên lai từ !",1);
        this.$refs.TuSo.focus();
        return;
      }
      if (this.DenSo == "")
      {
        this.ShowAlert("Bạn chưa nhập Số biên lai đến !",1);
        this.$refs.DenSo.focus();
        return;
      }
      if (this.SoBL == "")
      {
        this.ShowAlert("Bạn chưa nhập Số BL/Quyển !",1);
        this.$refs.SoBL.focus();
        return;
      }
      if (parseInt(this.TuSo) > parseInt(this.DenSo))
      {
        this.ShowAlert("Số biên lai đến không nhỏ hơn số biên lai từ !",1);
        this.$refs.TuSo.focus();
        return;
      }
      if (this.vluottao_id == 0) //nhập mới
      {
        var input = {};
        input.vloaibl_id = this.listbox.LoaiBienLai.value.loaibienlai_id;
        input.vma_luot = this.MaLuotTao;
        input.vtuso = this.TuSo;
        input.vso_bl_quyen = this.SoBL;
        input.vdenso = this.DenSo;
        console.log(input);
        this.loading(true);
        API.fn_insert_luottao_bienlai(this.axios, input).then((response) => {
          this.loading(false);
          if(response.data.error_code && response.data.error_code == this.success_code) {
            //Load lại dữ liệu
            console.log(response.data.error_code);
            this.refresh();
            this.ShowAlert("Thêm mới lượt biên lai thành công !",0);
            }
          else
          {
            this.loading(false);
            this.ShowAlert(response.data.data,2);
          }
          }
        );
      }
      else //update
      {
        var input = {};
        input.vloaibl_id = this.listbox.LoaiBienLai.value.loaibienlai_id;
        input.vluottao_id = this.vluottao_id;
        input.vma_luot = this.MaLuotTao;
        input.vtuso = this.TuSo;
        input.vtongso = this.TongSoBienLai;
        input.vso_bl_quyen = this.SoBL;
        input.vdenso = this.DenSo;
        console.log(input);
        this.loading(true);
        API.fn_update_luottao_bienlai(this.axios, input).then((response) => {
          this.loading(false);
          if(response.data.error_code && response.data.error_code == this.success_code) {
            this.refresh();
            this.ShowAlert("Cập nhật lượt biên lai thành công !",0);
            }
          else
          {
            this.loading(false);
            this.ShowAlert(response.data.data,2);
          }
          }
        ); 
      }
    },

    btnHuy()
    {
      this.SetButton(2);
      this.MaLuotTao = "";
      this.TuSo = "0";
      this.DenSo = "0";
      this.SoBL = "0";
    },

    btnXoa()
    {
      if (this.listbox.ds_luottaobl.checked.length <= 0)
      {
        this.ShowAlert("Bạn chưa chọn lượt biên lai để xóa",1);
        return;
      }
      this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xoá lượt biên lai này không?', {
      title: '', size: 'sm', okTitle: 'Đồng ý',cancelTitle: 'Hủy',}).then(async v => {
      if(v) {
        var sluottao = "";
        for (var item of this.listbox.ds_luottaobl.checked)
        {
          console.log(item);

          if (sluottao !="")
          {
            sluottao = sluottao + ",";
          }
          sluottao = sluottao + item.luottao_id;
        }
        var input = {};
        input.vstr_list_id = sluottao;
        console.log(input);
        this.loading(true);
        API.delete_luot_tao(this.axios, input).then((response) => {
          this.loading(false);
          if(response.data.error_code && response.data.error_code === this.success_code) {
            this.refresh();
            this.ShowAlert("Xóa lượt tạo thành công!",0);
            }
          else
          {
            this.ShowAlert(response.data.data,2);
          }
          }
        );  
      }
      });
    },

    btnSinhBienLai()
    {
      if (this.TuSo == "")
      {
        this.ShowAlert("Bạn chưa nhập Số biên lai từ !",1);
        this.$refs.TuSo.focus();
        return;
      }
      if (this.DenSo == "")
      {
        this.ShowAlert("Bạn chưa nhập Số biên lai đến !",1);
        this.$refs.DenSo.focus();
        return;
      }
      if (this.listbox.KhoBienLai.value.kho_id == null || this.listbox.KhoBienLai.value.kho_id =="")
      {
        this.ShowAlert("Hãy chọn loại Kho biên lai",1);
        return;
      }
      if (this.listbox.ds_luottaobl.checked.length <= 0)
      {
        this.ShowAlert("Chưa có lượt tạo nào được chọn để sinh Biên lai. Vui lòng chọn lượt tạo biên lai hoặc nhóm lượt!",1);
        return;
      }
      var sluottao = "";
      for (var item of this.listbox.ds_luottaobl.checked)
      {
        if (sluottao !="")
        {
          sluottao = sluottao + ",";
        }
        sluottao = sluottao + item.luottao_id;
      }
      var input = {};
      input.vds_luot_id = sluottao;
      input.vloaibienlai_id = this.listbox.LoaiBienLai.value.loaibienlai_id;
      input.vnd_giao = "";
      input.vkho_id = this.listbox.KhoBienLai.value.kho_id;

      console.log(input);
      this.loading(true);
      API.sinh_bienlai(this.axios, input).then((response) => {
        this.loading(false);
        if(response.data.error_code && response.data.error_code === this.success_code) {
          this.refresh();
          this.ShowAlert("Sinh biên lai thành công!",0);
          }
        else
        {
          this.ShowAlert(response.data.data,2);
        }
        }
      ); 




    },



  },
};

</script>
