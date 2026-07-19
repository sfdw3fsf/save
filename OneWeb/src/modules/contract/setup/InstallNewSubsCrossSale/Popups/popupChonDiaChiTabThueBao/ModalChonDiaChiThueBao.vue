<template src="./ModalChonDiaChiThueBao.html"></template>
<script>
import ModalChonToaDo from '@/modules/CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonToaDo';
import api from './api'
export default {
  name: "ModalChonDiaChiThueBao",
  props: ["modalId", "data","isStrictMode","tinhbancheo"],
  created: async function () {
    this.dsTinh=[]          
    this.diachi.tinh_id = null
  },
  components: { ModalChonToaDo },
  computed: {
    thaydoiDiaChi: function () {
      if(this.diachi.tinh_id == 98 && this.diachi.so_nha) {
        this.diachi.diachimoi = this.diachi.so_nha; 
        return this.diachi.so_nha;
      }
      else if (this.tentinh != "") {
        this.diachi.diachimoi = this.tentinh;
        this.diachi.diachimoi = this.tenquan + ", " + this.diachi.diachimoi;
        this.diachi.diachimoi = this.tenphuong + ", " + this.diachi.diachimoi;
        if (this.isPho && this.diachi.pho_id != 0)
          this.diachi.diachimoi = this.tenpho + ", " + this.diachi.diachimoi;
        if (this.isAp && this.diachi.ap_id != 0)
          this.diachi.diachimoi = this.tenap + ", " + this.diachi.diachimoi;
        if (this.isKhu && this.diachi.khu_id != 0)
          this.diachi.diachimoi = this.tenkhu + ", " + this.diachi.diachimoi;
        if (this.diachi.so_nha != null && this.diachi.so_nha != "")
          this.diachi.diachimoi = this.diachi.so_nha + ", " + this.diachi.diachimoi;
        return this.diachi.diachimoi;
      }
      return "";
    },
    
    getPosition () {
      return {
        lat: parseFloat(this.diachi.lat),
        lng: parseFloat(this.diachi.lng)
      }
    },
  },
  watch: {
  },
  methods: {
      onModalHidden() {
          // this.dsTinh=[]
          // this.diachi.tinh_id = null   
          // this.diachi.quan_id = null 
          // this.diachi.phuong_id = null        
          // this.diachi.pho_id = null 
          // this.diachi.ap_id = null 
          // this.diachi.khu_id = null 
          // this.diachi.dacdiem_id = null 
          // this.diachi.so_nha=""
          // this.diachi.diachimoi=""
          // this.diachi.daydiachi=""
      },
      LowerCasePropertyList(obj) {
        return obj.map( function( item ){
            for(var key in item){
                var upper = key.toLowerCase();
                // check if it already wasn't uppercase
                if( upper !== key ){ 
                    item[ upper ] = item[key];
                    delete item[key];
                }
            }
            return item;
        });
      },
      async IS_CHON_DIACHI_CHITIET () {
        let isExistMaTS = async (axios, params) => axios.get('/web-cabman/danhmuc/isExistMaTS', {params: params})
        let data = this.GetData(await isExistMaTS(this.axios, {ma_ts: 'IS_CHON_DIACHI_CHITIET'}))
        if (data) {
          return data.result
        } else {
          return false
        }
      },
      GetData: function (response) {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
          return null
        } else {
          this.$root.$toast.error('Lấy dữ liệu không thành công')
        }
        return null
      },
      focusMyElement: async function () {
        await this.getTinh();

        if (await this.IS_CHON_DIACHI_CHITIET()) {
          this.isEnableThongTinThem = true
        }

        if(this.data){
          var tsdiachi = Object.assign({}, this.data);
          tsdiachi = this.LowerCasePropertyList([tsdiachi])[0];
          this.diachi.daydiachi = tsdiachi.diachi
          if (tsdiachi.tinh_id && tsdiachi.tinh_id != 0) {
          this.diachi.tinh_id = tsdiachi.tinh_id;
          if(this.diachi.tinh_id==98) this.$toast.warning("Tỉnh quốc tế chỉ được chọn để lưu địa chỉ quốc tế  hoặc địa chỉ không xác định trên biển");
          this.diachi.quan_id = tsdiachi.quan_id; 
                  // await this.getQuan();
              
          this.diachi.phuong_id=tsdiachi.phuong_id;
          if(tsdiachi.phuong_id){
          if(tsdiachi.pho_id && tsdiachi.pho_id!=0) {
            this.diachi.pho_id=tsdiachi.pho_id;
            this.isPho=true}
        else {
          this.diachi.pho_id=0;
          this.isPho=false
        }
        
          if(tsdiachi.ap_id && tsdiachi.ap_id!=0) {
              this.diachi.ap_id=tsdiachi.ap_id;
            this.isAp=true}
          else {
            this.diachi.ap_id=0;
           this.isAp=false
          }
        
          if(tsdiachi.khu_id && tsdiachi.khu_id!=0){
              this.diachi.khu_id=tsdiachi.khu_id;
            this.isKhu=true}
          else {
             this.diachi.khu_id=0;
           this.isKhu=false}
          }
          this.diachi.so_nha = tsdiachi.so_nha;
          this.diachi.daydiachi = tsdiachi.diachi;
          this.diachi.diachimoi = tsdiachi.diachi;

          this.diachi.block = this.data.block ? this.data.block : ''
          this.diachi.tang = this.data.tang ? this.data.tang : ''
          this.diachi.phong = this.data.phong ? this.data.phong : ''
          this.diachi.mota = this.data.mota ? this.data.mota : '' 
        }
      }
      if(this.dsTinh && this.dsTinh.length>0 && (!this.diachi.tinh_id || this.diachi.tinh_id==0))
        this.diachi.tinh_id = this.tinhbancheo?this.tinhbancheo:-1;//this.$root.token.getPhanVungID();//this.dsTinh[0].tinh_id;
        if(this.diachi.tinh_id==98) this.$toast.warning("Tỉnh quốc tế chỉ được chọn để lưu địa chỉ quốc tế  hoặc địa chỉ không xác định trên biển");
      // this.dsTinh = this.dsTinh.filter(x=>x.tinh_id==this.diachi.tinh_id || x.tinh_id==(this.tinhbancheo?this.tinhbancheo:-1));
      this.dsTinh = this.dsTinh.filter(x=>x.tinh_id==(this.tinhbancheo?this.tinhbancheo:-1)); //Dieu chinh theo yeu cau cua anh.nguyentuan@vnpt.vn
      //this.tentinh = this.dsTinh[0].tentinh;//this.dsTinh.filter((x) => x.tinh_id == this.diachi.tinh_id)[0].tentinh;
      if(this.$root.token.getPhanVungID()<97 || this.$root.token.getPhanVungID()>100) {
        this.dsTinh = this.dsTinh.filter(x=>x.tinh_id==this.diachi.tinh_id || x.tinh_id==(this.tinhbancheo?this.tinhbancheo:-1));
        this.tentinh = this.dsTinh[0].tentinh;//this.dsTinh.filter((x) => x.tinh_id == this.diachi.tinh_id)[0].tentinh;
      }
      else
        this.tentinh = this.dsTinh.find(x=>x.tinh_id==this.diachi.tinh_id)?this.dsTinh.find(x=>x.tinh_id==this.diachi.tinh_id).tentinh:this.dsTinh[0].tentinh;//this.dsTinh.filter((x) => x.tinh_id == this.diachi.tinh_id)[0].tentinh;
      
      await this.getQuan();
      await this.getPhuong();
      await this.getPhoApKhu(); 
    },

    getTinh: async function () {
      try {
        this.$root.showLoading(true);
     
        var rs = await this.$root.context.post(
          "/web-hopdong/bss_group1/lay_ds_tinh_thicong_combobox",{}
        //"/web-cabman/bando_tuyencot/lay_thongtin_tinh"
        );
        this.dsTinh = rs.data;
        // if(this.dsTinh && this.dsTinh.length>0 && (!this.diachi.tinh_id || this.diachi.tinh_id == 0))
        //   this.diachi.tinh_id = this.dsTinh[0].tinh_id;
      } catch (error) {
        console.log(error)
      } finally {
        this.$root.showLoading(false);
      }
    },
    getQuan: async function () {
      try {
       
        this.$root.showLoading(true);
        const rs = await api.getDanhSachQuanBanCheo(this.axios,
          {
            tinh_id: this.diachi.tinh_id,
            tinhthicong_id: this.diachi.tinh_id
          }
        )
      
        // var rs = await this.$root.context.get(
        //   "/web-cabman/bando_tuyencot/lay_ds_quan_huyen/" + this.diachi.tinh_id
        // );
        if(rs && rs.data && rs.data.error_code === "BSS-00000000"){
          this.dsQuan = rs.data.data;
        }
       
        if(this.diachi.quan_id && this.dsQuan)
         this.tenquan = this.dsQuan.filter(
           (x) => x.quan_id == this.diachi.quan_id
         )[0].ten_quan;
        else if((!this.diachi.quan_id || this.diachi.quan_id<=0) && this.dsQuan) {
          this.diachi.quan_id = this.dsQuan[0].quan_id;
          this.tenquan = this.dsQuan[0].ten_quan;
        }
        this.onChangeQuan();
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    getPhuong: async function () {
      try {
        this.$root.showLoading(true);
        const rs = await api.getDanhSachPhuongBanCheo(this.axios,
          {
            quan_id: this.diachi.quan_id,
            tinhthicong_id: this.diachi.tinh_id
          }
        )
        // var rs = await this.$root.context.get(
        //   "/web-cabman/bando_tuyencot/lay_ds_phuong_xa/" + this.diachi.quan_id
        // );
        if(rs && rs.data && rs.data.error_code === "BSS-00000000"){
          this.dsPhuong = rs.data.data;
        }
       
        if(this.diachi.phuong_id && this.dsPhuong){
          var listphuong = this.dsPhuong.filter(
          x => x.phuong_id == this.diachi.phuong_id);
          if(listphuong && listphuong.length>0)
            this.tenphuong = listphuong[0].ten_phuong;
          else if(this.dsPhuong && this.dsPhuong.length>0) {
            this.diachi.phuong_id = this.dsPhuong[0].phuong_id;
            this.tenphuong = this.dsPhuong[0].ten_phuong;
          }
        }
        else if((!this.diachi.phuong_id || this.diachi.phuong_id<=0) && this.dsPhuong) {
          this.diachi.phuong_id = this.dsPhuong[0].phuong_id;
          this.tenphuong = this.dsPhuong[0].ten_phuong;
        }
       
        this.onChangePhuong();
      } catch (error) {
        console.log(error)

      } finally {
        this.$root.showLoading(false);
      }
    },

    //nhompho_id =  1:duong pho, 2 ap, 3 khu 
    getPhoApKhu: async function () {
      try {
      
        const rsPho = await api.getDanhSachPhoApKhuBanCheo(this.axios,
          {
            phuong_id: this.diachi.phuong_id,
            tinhthicong_id: this.diachi.tinh_id,
            nhompho_id: 1
          }
        )
        this.dsPho = rsPho.data.data;
        if(this.dsPho.length){
          this.tenpho = this.dsPho[0].ten_pho
          if(!this.diachi.pho_id || !this.dsPho.find(x=>x.pho_id==this.diachi.pho_id)) this.diachi.pho_id = this.dsPho[0].pho_id
        }

        const rsAp = await api.getDanhSachPhoApKhuBanCheo(this.axios,
          {
            phuong_id: this.diachi.phuong_id,
            tinhthicong_id: this.diachi.tinh_id,
            nhompho_id: 2
          }
        )
        this.dsAp = rsAp.data.data;
          if(this.dsAp.length){
            this.diachi.ap_id = this.dsAp[0].pho_id
            this.tenap = this.dsAp[0].ten_pho
          }
       
        const rsKhu = await api.getDanhSachPhoApKhuBanCheo(this.axios,
          {
            phuong_id: this.diachi.phuong_id,
            tinhthicong_id: this.diachi.tinh_id,
            nhompho_id: 3
          }
        )
        this.dsKhu = rsKhu.data.data;
        if(this.dsKhu.length){
          this.diachi.khu_id = this.dsKhu[0].pho_id
          this.tenkhu = this.dsKhu[0].ten_pho
        }

        await this.getDacDiem();
        
        return 
        
      } catch (error) {
      } 
    },
    getDacDiem: async function () {
      try {
       
        const rs = await api.getDanhSachDaciemBanCheo(this.axios,
          {
            phuong_id: this.diachi.phuong_id,
            pho_id: this.diachi.pho_id == null ? 0 : this.diachi.pho_id,
            ap_id: this.diachi.ap_id == null ? 0 : this.diachi.ap_id,
            khu_id: this.diachi.khu_id == null ? 0 : this.diachi.khu_id,
            tinhthicong_id: this.diachi.tinh_id
          }
        )
        this.dsDacDiem = rs.data.data;
        if(this.dsDacDiem && this.dsDacDiem.length>0) {
          this.isDacDiem = true;
          if(!this.diachi.dacdiem_id || this.diachi.dacdiem_id <=0) this.diachi.dacdiem_id = this.dsDacDiem[0].dacdiem_id;
        }
        else { 
          this.isDacDiem = false;
          this.diachi.dacdiem_id = 0;
        }
      } catch (error) {
      } finally {
        
      }
    },
    saveDiachi: async function () {
      try {
        this.$root.showLoading(true);
      } catch (error) {
        this.$toast.error("Lưu ảnh thất bại");
      } finally {
        this.$root.showLoading(false);
      }
    },
    onChangeTinh: async function () {
      if(this.diachi.tinh_id==98) this.$toast.warning("Tỉnh quốc tế chỉ được chọn để lưu địa chỉ quốc tế  hoặc địa chỉ không xác định trên biển");
      await this.getQuan();
      this.tentinh = this.dsTinh.filter(
        (x) => x.tinh_id == this.diachi.tinh_id
      )[0].tentinh;
    },
    onChangeQuan: async function () {
      await this.getPhuong();
      this.tenquan = this.dsQuan.filter(
          (x) => x.quan_id == this.diachi.quan_id
        )[0].ten_quan;
    },
    onChangePhuong: async function () {
      await this.getPhoApKhu()
      var listphuong = this.dsPhuong.filter(x => x.phuong_id == this.diachi.phuong_id);
      if(listphuong && listphuong.length>0)
          this.tenphuong = listphuong[0].ten_phuong;
    },
    onChangePho: async function (){
      await this.getDacDiem();
      this.tenpho = this.dsPho.filter(
          (x) => x.pho_id == this.diachi.pho_id
       )[0].ten_pho;

      
    },
    onChangeKhu: async function () {
      await this.getDacDiem();
      this.tenkhu = this.dsKhu.filter(
          (x) => x.pho_id == this.diachi.khu_id
        )[0].ten_pho;

       
    },

    onChangeAp: async function (){
      await this.getDacDiem();
      
      this.tenap = this.dsAp.filter(
        (x) => x.pho_id == this.diachi.ap_id
      )[0].ten_pho;
    
    },
    chapnhan: async function () {
      
      if(!this.isPho) {
        this.diachi.pho_id = 0;
      } else 
        if(!this.diachi.pho_id || this.diachi.pho_id<=0) {
          this.$toast.error("Phải chọn phố hoặc bỏ tick chọn phố!");
          return;
        }
      if(!this.isAp) {
        this.diachi.ap_id = 0;
      } else 
        if(!this.diachi.ap_id || this.diachi.ap_id<=0) {
          this.$toast.error("Phải chọn ấp hoặc bỏ tick chọn ấp!");
          return;
        }
      if(!this.isKhu) {
        this.diachi.khu_id = 0;
      } else 
        if(!this.diachi.khu_id || this.diachi.khu_id<=0) {
          this.$toast.error("Phải chọn khu hoặc bỏ tick chọn khu!");
          return;
        }
      if(!this.isDacDiem) this.diachi.dacdiem_id = 0;
      if (
        this.isStrictMode && this.diachi.tinh_id != 98 &&
        ((!this.isPho && !this.isAp && !this.isKhu) ||
          (this.isPho && this.tenpho == "") ||
          (this.isAp && this.tenap == "") ||
          (this.isKhu && this.tenkhu == ""))
      ) {
        this.$toast.warning("Hãy chọn Phố / Ấp / Khu");
        return;
      }
      if(this.dsDacDiem && this.dsDacDiem.length>0) {
        if (this.isDacDiem && this.diachi.dacdiem_id<=0 && (this.dsDacDiem.length>1 || (this.dsDacDiem.length==1 && this.dsDacDiem[0].DACDIEM_ID>0))) {
          this.$toast.warning("Hãy chọn đặc điểm!");
          return;
        }
        if(!this.isDacDiem || (this.isDacDiem && this.diachi.dacdiem_id<=0)) {
          var cfr = await this.$bvModal.msgBoxConfirm(`Bạn thật sự không muốn chọn đặc điểm?`, {
            title: 'Thông báo',
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy',
          }).then(v => {
            return v;
          });
          if(!cfr) return 
        }
      }
      this.$emit("xacnhan", this.diachi);
      this.$bvModal.hide(this.modalId);
    },
    XacNhanToaDo (position) {
      this.diachi.lat = position.lat;
      this.diachi.lng = position.lng;
    },
  },
  data: function () {
    return {
      diachi: {
        diachi_id: null,
        tinh_id:null ,
        quan_id: null,
        phuong_id: null,
        pho_id: 0,
        ap_id: 0,
        khu_id:0,
        dac_diem_id: 0,
        so_nha: "",
        daydiachi: "",
        diachimoi: "",
        block: '',
        tang: '',
        phong: '',
        mota: '',
        lat:0,
        lng:0,
      },
      dsTinh: [],
      dsQuan: [],
      dsPhuong: [],
      dsPho: [],
      dsAp: [],
      dsKhu: [],
      dsDacDiem: [],
      isPho: false,
      isAp: false,
      isKhu: false,
      isDacDiem: false,
      tentinh: "",
      tenquan: "",
      tenphuong: "",
      tenpho: "",
      tenap: "",
      tenkhu: "",
      isEnableThongTinThem: false,
    };
  },
};
</script>
<style>
div#popupTCTTTB___BV_modal_outer_ {
  z-index: 10 !important;
  margin-top: 100px;
}
.dashboard-header {
  z-index: 9 !important;
}
a.disabled {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>
