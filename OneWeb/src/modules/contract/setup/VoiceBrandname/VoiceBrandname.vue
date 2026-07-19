<template src="./VoiceBrandname.html"></template>
<style src="./VoiceBrandname.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import DatePicker from 'vue2-datepicker'
import VueFlatPickr from 'vue-flatpickr-component';
import API from './API'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'

import commonFn from '@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'

import moment from 'moment'
import { async, Promise, reject } from 'q';
//
export default {
  components: {
    breadcrumb,
    VueFlatPickr,
    API,DatePicker
},
  name: 'VoiceBrandname',
  mounted () {
    this.frmDangKySoThanhVien_Load()
    this.showModal()
  },
  watch : {
  },
  props: ['hdtb_id', 'thuebao_id', 'lst_hdtbct', 'lst_dbtbct', 'kieuld_id'],
  data () {
    return {
      // hdtb_id : 0,
      // thuebao_id : 0,
      // lst_hdtbct : [],
      // lst_dbtbct : [],
      tdan : {},
      // kieuld_id : 0,
      settingSelect2 : {
        language: 'vi'
      },
      cboKieuYc : {
        list : [
          // {
          //   id : 0,
          //   text : 'Khóa 1 chiều gọi đi'
          // },
          // {
          //   id : 1,
          //   text : 'Khóa 2 chiều'
          // },
          // {
          //   id : 2,
          //   text : 'Khóa 1 chiều gọi đến'
          // },
          // {
          //   id : 3,
          //   text : 'Khôi phục'
          // },
          {
            id : 4,
            text : 'Hủy'
          },
          // {
          //   id: 5,
          //   text: 'Tạm dừng'
          // }
        ],
        value : 4,
        disable: false,
      },
      grcTBDuocChon : {
        list : [],
        header : [
            {fieldName: 'SO_DT_CT', headerText: 'Số thuê bao', allowFiltering: true, width : 'auto'}, 
            {fieldName: 'TRANGTHAI_CT' , headerText: 'Trạng thái thuê bao', allowFiltering: true, width : 'auto'}, 
          // { fieldName: 'NGAY_NGHIEMTHU_CT', headerText: 'Ngày hoàn thành', allowFiltering: true, width: 'auto' },
            {fieldName: 'MA_TB_CT', headerText: 'Số chủ nhóm', allowFiltering: true, width: 'auto'}
        ],
        value : {},
        isEnabled : true,
      },
      grcDanhBaTBI : {
        list : [],
        header : [
            {fieldName: 'SO_DT_CT', headerText: 'Số thuê bao', allowFiltering: true, width : 'auto'}, 
            {fieldName: 'TRANGTHAI_CT' , headerText: 'Trạng thái thuê bao', allowFiltering: true, width : 'auto'}, 
          // { fieldName: 'NGAY_NGHIEMTHU_CT', headerText: 'Ngày hoàn thành', allowFiltering: true, width: 'auto' },
          {fieldName: 'MA_TB_CT', headerText: 'Số chủ nhóm', allowFiltering: true, width: 'auto'}
        ],
        value : {},
        isEnabled : true,
      },
    }
  },
  computed: {
    
    
  },
  methods : {
    showAlert(mesage)
    {
      this.$toast.error(mesage);
    },
    showSuccess(mesage) {
        this.$toast.success(mesage);
    },

    getData: function (response) {
      if (response.data.error === 200 || response.data.error === '200' || response.data.error === '0') {
        if (response.data.data ) {
          console.log(this.stringToJsonData(response.data.data));
          return this.stringToJsonData(response.data.data)
        }
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    stringToJsonData(str) {
      console.log(str)
      if (str == "[]") return JSON.parse(str);
      str = str.replace("[{", '').replace("}]", "");
      const jsonArray = [];

      str.split("}, {").forEach(el => {
        const jsonItem = {};
        el.split(', ').forEach(item => {
          const keyValuePairs = item.split(',');
          for (const pair of keyValuePairs) {
            const [key, value] = pair.split('=');
            jsonItem[key.toUpperCase()] = value;
          }
        });
        jsonArray.push(jsonItem);
      });
      
      var kq = JSON.stringify(jsonArray);
      console.log(kq)
      return JSON.parse(kq);
    },

    closeModal () {
      this.$bvModal.hide('voiceBrandnameModal')
    },
    showModal () {
      this.$bvModal.show('voiceBrandnameModal')
    },

    async frmDangKySoThanhVien_Load() {
      if (this.hdtb_id != 0)
      {
        let input = {
          'vhdtb_id' : this.hdtb_id
        }
        this.lst_hdtbct = this.getData(await API.lay_ds_thietbi_idc_dangky(this.axios, input))
        this.grcTBDuocChon.list = this.lst_hdtbct
      }

      if (this.thuebao_id != 0)
      {
        let input = {
          'vthuebao_id' : this.thuebao_id
        }
        this.lst_dbtbct = this.getData(await API.lay_ds_thietbi_idc_sudung(this.axios, input))
        this.grcDanhBaTBI.list = this.lst_dbtbct
      }

      this.cboKieuYc.value = 4;
  
    
      
    },

    async btnDangky_Click() {

    },

    async btnGhiLai_ItemClick() {
      try {
        let input = {
          'vhdtb_id' : this.hdtb_id,
          'vdata' : JSON.stringify(this.grcTBDuocChon.list)
        }
        let kq = (await API.capnhat_thietbi_dk_biendong_idc(this.axios, input)).data.data;
        if (kq == "ok")
        {
          this.showSuccess("Đăng ký thuê bao thành công!");
          // this.Close();
          return;
        }
        else
        {
          this.showAlert("Đăng ký thuê bao thất bại! " + kq);
        }
      } catch (error) {
        this.showAlert(error)
      }
    },

    btnDangky_Click()
    {

      let data = this.$refs.danhSachThueBaoDangHoatDong.getSelectedRecords();
      var a = this.grcDanhBaTBI.list;
      for (var i = 0; i  < data.length; i++) {
       
        if (data[i].SO_DT_CT && data[i].MA_TB_CT && data[i].SO_DT_CT.toString() == data[i].MA_TB_CT.toString())
        {
          data = a
        }
      }
      console.log(data);

      var kieuyc = this.cboKieuYc.list.find(x => x.id == this.cboKieuYc.value);
      if (kieuyc) {
        for (var i = 0; i < data.length; i++) {
          data[i].NGAY_NGHIEMTHU_CT = "";
          data[i].TRANGTHAI_CT = "Yêu cầu " + kieuyc.text.toLowerCase();
          data[i].IP_CT = kieuyc.id;
        }
      }
      this.grcTBDuocChon.list = data;
      //  foreach(DB_THUEBAO_CT item in lst_dbtbct.FindAll(x => x.chon == true))
      // {
      //           HD_THUEBAO_CT a = new HD_THUEBAO_CT();
      //   Mapper.Map < DB_THUEBAO_CT, HD_THUEBAO_CT > (item, a);
      //   if (!lst_hdtbct.Exists(x => x.SO_DT_CT == item.SO_DT_CT)) {
      //     a.TRANGTHAI_CT = "Yêu cầu " + cboKieuYc.Text;
      //     lst_hdtbct.Add(a);
      //   }
      // }
      // grcTBDuocChon.DataSource = new BindingList < HD_THUEBAO_CT > (lst_hdtbct);
    },
    btnHuy_Click() {
      let data = this.$refs.danhSachThueBaoBiBienDong.getSelectedRecords();
      this.grcTBDuocChon.list = this.grcTBDuocChon.list.filter(function (el) {
        return !data.includes(el);
      });
     
    },
    btnThoat_ItemClick() {

    }
  }
}

</script>

<style>
    .searchAccountDialogClass .e-dlg-header-content {
        display: none;
    }
</style>
