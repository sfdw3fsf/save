<template src="./index.html"></template>
<style src="./index.scss"></style>
<script>
import Vue from 'vue'

import { DateTimePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import { CheckBoxSelection } from '@syncfusion/ej2-vue-dropdowns'
import ThemChuDe1 from './popup/ThemMoiChuDeCap1.vue'

Vue.use(DateTimePickerPlugin)
export default {
  provide: {
    multiselect: [CheckBoxSelection]
  },
  components:{
    ThemChuDe1
  },
  data() {
    return {
      rowSelected: 0,
      btnNhapMoiEnable: true,
      btnGhiLaiEnable: true,
      btnXoaEnable: true,
      btnHuyEnable: false,
      clientInfo: null,
      
      configLoaiTB: {
        fields: { text: 'LOAIHINH_TB', value: 'LOAITB_ID' },
        mode: 'CheckBox',
        showSelectAll: true,
        showDropDownIcon: true,
        filterBarPlaceholder: 'Tìm kiếm nhanh'
      },

      DS_LOAI_KN: [],
      DS_NHOM_CHUDE: [],
      DS_CHUDE_CAP1: [],
      DS_CHUDE_CAP2: [],
      DS_DICHVU_VT: [],
      DS_LOAIHINH_TB: [],
      DS_DOITUONG_LOI: [],
      DS_TRACHNHIEM: [],
      DS_CAUHINH: [],
      dtpNgayTao: new Date(),
      currentItem: {
        LOAI_ID: 0,
        ID_NHOM: 0,
        ID_CAP1: 0,
        ID_CAP2: 0,
        NGAY_CN: new Date(),
        DS_LOAITB_ID:'',
        dsLoaiTBDaChon: [],
        NGUOI_CN: this.$root.token.getUserName()
      },
    }
  },
  
  mounted: async function () {
    //this.clientInfo = JSON.parse(localStorage.getItem("OneBSS-Client"));
    //console.log(clientInfo.ten_nv);

    this.DS_LOAI_KN = await this.getDanhMuc('LOAI_KN', 0,0);
    this.DS_DOITUONG_LOI = await this.getDanhMuc('DOITUONG_LOI', 0,0);
    if (this.DS_DOITUONG_LOI.length > 0)
      this.currentItem.DOITUONG_LOI_ID = this.DS_DOITUONG_LOI[0].ID
    this.DS_TRACHNHIEM = await this.getDanhMuc('TRACH_NHIEM', 0,0);
    if (this.DS_TRACHNHIEM.length > 0)
      this.currentItem.TRACHNHIEM_ID = this.DS_TRACHNHIEM[0].ID
    this.DS_DICHVU_VT = await this.getDichVuVT();

    // if (this.DS_DICHVU_VT.length>0)
    //     this.currentItem.DICHVUVT_ID=this.DS_DICHVU_VT[0].DICHVUVT_ID;
    // this.DS_NHOM_CHUDE=await this.getDanhMuc('NHOM_CHUDE');    
    // this.DS_CHUDE_CAP1=await this.getDanhMuc('CHUDE_CAP1');    
    // this.DS_CHUDE_CAP2=await this.getDanhMuc('CHUDE_CAP2');    
  },
  created: async function () {
    this.loadDS()
  },

  methods: {
    cleanForm() { this.currentItem = null },
    async loadDS(){
      this.DS_CAUHINH = await this.getDanhSach();
    if (this.DS_CAUHINH.length > 0) {
      this.currentItem = { ...this.DS_CAUHINH[0] }
      let arrTemp=[]
      arrTemp.push(this.currentItem.LOAITB_ID)
      this.currentItem.dsLoaiTBDaChon=arrTemp
      this.btnGhiLaiEnable = true
      
    }
    else
      this.btnGhiLaiEnable = false
    },
    getDanhMuc: async function (ten, id,id2) {
      try {
        let param = {
          tenDanhMuc: ten,
          id: id,
          id2:id2
        }
        const rs = await this.$root.context.post('/web-gqkn/tiep-nhan-khieu-nai/danhmuc-theo-ten', param);
        return rs.data;
      } catch (error) { return null }
    },
    getDichVuVT: async function () {
      try {
        const rs = await this.$root.context.get('/web-gqkn/tiep-nhan-khieu-nai/getDsDichVuVT?DS_DICHVUVT_ID=');
        return rs.data;
      } catch (error) { return null }
    },
    getLoaiHinhTB: async function (dichvuvt_id) {
      try {
        const rs = await this.$root.context.post('/web-gqkn/giaophieu-nhanvien/get-loaihinh-thuebao', { pDichVuId: dichvuvt_id });
        return rs.data;
      } catch (error) { return null }
    },
    getDanhSach: async function () {
      this.loading(true)
      try {
        const rs = await this.$root.context.get('/web-gqkn/khieunai/danhsach-cauhinh-khieunai/'+this.currentItem.DICHVUVT_ID);
        this.loading(false)
        return rs.data;

      } catch (error) {this.loading(false);
         return null }
    },

    capNhat: async function () {
      try {
        //this.currentItem.CHUDEKN_ID=this.currentItem.ID_CAP2;
        console.log('dau',this.currentItem.dsLoaiTBDaChon.join(","))
        this.currentItem.DS_LOAITB_ID=this.currentItem.dsLoaiTBDaChon.join(",")
        console.log('dít',this.currentItem.DS_LOAITB_ID)
        let param = {
          nguyenNhanId: this.btnNhapMoiEnable ? this.currentItem.NGUYENNHAN_ID : 0,
          duLieu: JSON.stringify(this.currentItem)
        }
        const rs = await this.$root.context.post('/web-gqkn/khieunai/ghilai-cauhinh-khieunai', param);
        if (rs.data[0].KETQUA == 'ok') {
          this.loading(false)
          this.$root.$toast.success('Cập nhật thành công')
        }
        else {
          this.loading(false)
          this.$root.$toast.error('Có lỗi xảy ra')
        }
      } catch (error) {
        this.$root.$toast.error(error)
        this.loading(false)

        return null
      }
    },
    xoaCauHinh: async function () {
      try {
        let param = {
          nguyenNhanId: this.currentItem.NGUYENNHAN_ID
        }
        const rs = await this.$root.context.post('/web-gqkn/khieunai/xoa-cauhinh-khieunai', param);
        if (rs.data[0].KETQUA == 'ok') {
          this.loading(false)
          this.$root.$toast.success('Xóa cấu hình thành công')
        }
        else {
          this.loading(false)
          this.$root.$toast.error('Có lỗi xảy ra')
        }
      } catch (error) {
        this.loading(false)
        return null
      }
    },
    grid_rowSelected(args) {
      console.log(args)

      this.rowSelected = args.rowIndex
      if (this.btnNhapMoiEnable) {
        this.currentItem = args
        this.currentItem.CHUDEKN_ID = this.currentItem.ID_CAP2;      

        let arrLoaiTB=[]
        arrLoaiTB.push(this.currentItem.LOAITB_ID)

        this.currentItem.dsLoaiTBDaChon=arrLoaiTB        
        this.btnGhiLaiEnable = true;
        this.btnXoaEnable = true;
        this.btnHuyEnable = false;
        console.log(this.currentItem)

      }

    },

    onChangeLoai: async function (e) {
      console.log(e)
    },
    clearForm() {
      this.currentItem.CHUDEKN_ID = null;
      this.currentItem.LOAI_ID = 1
      this.currentItem.GIAM_CUOC = false
      this.currentItem.TRANG_THAI = 1
      this.currentItem.ID_NHOM = null
      this.currentItem.DOITUONG_LOI_ID = 1
      this.currentItem.TRACHNHIEM_ID = null
      this.currentItem.ID_CAP1 = null
      this.currentItem.MA_DOANTHU_ID = 1
      this.currentItem.CHITIEU_ID = 1
      this.currentItem.CHUDE_CAP2 = '';
      this.currentItem.CHUDE_CAP2_ID = null
      this.currentItem.NGAY_TAO = new Date()
      this.currentItem.NGUOI_CN = this.$root.token.getUserName()
      this.currentItem.GHI_CHU = ''

    },
    btnNhapMoiClick: async function (e) {
      this.btnNhapMoiEnable = false
      this.btnGhiLaiEnable = true
      this.btnXoaEnable = false
      this.btnHuyEnable = true
      this.clearForm();
    },
    btnGhiLaiClick: async function (e) {

      this.$confirm(`Bạn có muốn lưu lại kết quả này không ?`,
        'Thông báo',
        {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'info'
        }).then(async () => {
          try {
            this.loading(true);
            await this.capNhat()
            this.DS_CAUHINH = await this.getDanhSach();
            this.btnGhiLaiEnable = true;
            this.btnNhapMoiEnable = true;
            this.btnXoaEnable = true;

          } catch (e) {
            console.log(e)
            this.loading(false)

            this.$root.$toast.error(e.data.message_detail)
          }
        }).catch((e) => {
          this.loading(false)
          console.log(e)
        })





    },
    

    async ThemMoiChuDe1 () {
      this.DS_CHUDE_CAP1 = await this.getDanhMuc('CHUDE_CAP1', this.currentItem.ID_NHOM,0)
      this.$refs.ModalThemChuDe1.show().then((result) => {     
        console.log(result)     
        let temp_ds=[]
          temp_ds.push({"CHUDE_CAP1_ID":result.nhom_id,"CHUDE_CAP1":result.ten_nhom})          
          this.DS_CHUDE_CAP1=[...temp_ds,...this.DS_CHUDE_CAP1];
          this.currentItem.ID_CAP1=result.nhom_id
        
      })
    },
    btnXoaClick: async function (e) {
      this.btnNhapMoiEnable = true
      this.btnGhiLaiEnable = true
      this.btnXoaEnable = true
      this.btnHuyEnable = false


      this.$confirm(`Bạn thật sự muốn xóa khiếu nại không ?`,
        'Thông báo',
        {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'info'
        }).then(async () => {
          try {
            this.loading(true);
            await this.xoaCauHinh()
            this.DS_CAUHINH = await this.getDanhSach();
          } catch (e) {
            this.loading(false)
            this.$root.$toast.error(e.message)
          }
        }).catch(() => { this.loading(false) })

    },
    btnHuyClick: async function (e) {
      this.btnNhapMoiEnable = true
      this.btnGhiLaiEnable = false
      this.btnXoaEnable = true
      this.btnHuyEnable = false
      this.loadDS();
      //this.$refs.grid.ej2Instances.selectRow(0);



      //this.DS_CAUHINH=await this.getDanhSach();
    },
    onDataBound() {
      // tự động chọn dòng đầu tiên
      // let grid = this.$refs.grid.ej2Instances;
      // grid.selectRow(0);  // index = 0 → row đầu tiên
    }
  },
  watch: {
    async 'currentItem.LOAI_ID'(val) {
      if (val) {
        this.DS_NHOM_CHUDE = await this.getDanhMuc('NHOM_CHUDE', val,0)
        if (this.DS_NHOM_CHUDE.length > 0)
          this.currentItem.ID_NHOM = this.DS_NHOM_CHUDE[0].NHOM_ID
      }

    },
    async 'currentItem.DICHVUVT_ID'(val) {
      if (val) {
        this.DS_LOAIHINH_TB = await this.getLoaiHinhTB(val)
        if (this.DS_LOAIHINH_TB.length > 0)
          this.currentItem.LOAITB_ID = this.DS_LOAIHINH_TB[0].LOAITB_ID
          this.DS_CAUHINH = await this.getDanhSach();
      }

    },
    async 'currentItem.ID_NHOM'(val) {
      if (val) {
        this.DS_CHUDE_CAP1 = await this.getDanhMuc('CHUDE_CAP1', val,0)
        if (this.DS_CHUDE_CAP1.length > 0)
          this.currentItem.ID_CAP1 = this.DS_CHUDE_CAP1[0].CHUDE_CAP1_ID
        if (this.btnNhapMoiEnable==false)
        {
          let temp_ds=[]
          temp_ds.push({"CHUDE_CAP1_ID":-1,"CHUDE_CAP1":"Thêm mới"})
          this.DS_CHUDE_CAP1=[...temp_ds,...this.DS_CHUDE_CAP1];

        }
      }

    },
    async 'currentItem.ID_CAP1'(val) {
      if (val) {
        if  (val==-1) 
          this.ThemMoiChuDe1()
        else
        {
        this.DS_CHUDE_CAP2 = await this.getDanhMuc('CHUDE_CAP2', val,0)
        if (this.DS_CHUDE_CAP2.length > 0)
          this.currentItem.ID_CAP2 = this.DS_CHUDE_CAP2[0].CHUDE_CAP2_ID
        }
      }

    },
  }
}
</script>
