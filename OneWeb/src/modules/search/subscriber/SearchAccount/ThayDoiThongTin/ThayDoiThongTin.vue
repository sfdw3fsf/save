<template src="./ThayDoiThongTin.html"> </template>

<script>
import ModalChonDiaChi from '@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi'
import moment from 'moment'
const initialData = {
  ds_loaihinhtb: [],
  dt_kh: null,
  dt_tb: null,
  loaihinh_tb: null,
  dichvuvt_id: null,
  ds_dichvuvt: [],
  ds_tinh: [],
  ds_loaikenh: [],
  ds_tocdo: [],
  ma_pl_cu: '',
  ma_pl: '',
  tocdo_id_cu: null,
  diachiLD: '',
  diachiLD_cu: '',
  diachiObj: null,
  tocdo_id: null,
  loaikenh_id_cu: null,
  loaikenh_id: null,
  tinh_id_cu: null,
  tinh_id: null,
  ten_tb: '',
  ma_tb: '',
  ma_lt: '',
  ma_vtt: null,
  trangthai_tb: '',
  matb_tn: '',
  ten_kh_cu: '',
  ten_kh: '',
  diachi_ld_cu: '',
  diachi_ld: '',
  ma_crm_cu: '',
  ma_crm: '',
  ma_erp_cu: '',
  ma_erp: '',
  ds_loainode: [],
  loainode_id_cu: null,
  loainode_id: null,
  huong_kn_cu: '',
  huong_kn: ''
}
export default {
  data() {
    return initialData
  },
  methods: {
    async handleShowModal() {
      const inputTT = {
        ds_para: JSON.stringify({
          DICHVUVT_ID: this.dt_tb.dichvuvt_id * 1,
          MA_TB: this.dt_tb.ma_tb
        })
      }
      console.log(this.dt_tb)
      console.log('khach hang', this.dt_kh)
      this.loaihinh_tb = this.dt_tb.loaitb_id * 1
      this.diachiLD_cu = this.dt_tb.diachi_ld
      this.dichvuvt_id = this.dt_tb.dichvuvt_id * 1
      this.ten_tb = this.dt_tb.ten_tb
      this.ma_tb = this.dt_tb.ma_tb
      this.ma_lt = this.dt_tb.ma_lt
      this.trangthai_tb = this.dt_tb.trangthai_tb
      this.ten_kh_cu = this.dt_kh.ten_kh
      this.ten_kh = this.dt_kh.ten_kh
      // this.diachi_ld_cu = this.dt_tb.diachi_ld;
      this.diachiLD = this.dt_tb.diachi_ld
      let res = await this.$root.context.post('/web-hopdong/tracu-danhba/fn_thaydoittdb_hienthithongtin', inputTT)
      if (res.data) {
        const dt = JSON.parse(res.data)
        console.log(dt)
        this.ds_loaihinhtb = dt.DS_LOAIHINHTB.map((item) => {
          return {
            id: item.LOAITB_ID,
            text: item.LOAIHINH_TB
          }
        })
        if (this.dichvuvt_id == 9) {
          //TSL
          this.ma_crm_cu = dt.DS_DANHBA_TSL[0].MA_CRM
          this.ma_erp_cu = dt.DS_DANHBA_TSL[0].ID_ERP
          this.tinh_id_cu = dt.DS_DANHBA_TSL[0].TINH_ID
          this.tinh_id = dt.DS_DANHBA_TSL[0].TINH_ID
          this.huong_kn_cu = dt.DS_DANHBA_TSL[0].HUONG_KN
          this.ma_pl_cu = dt.DS_DANHBA_TSL[0].MA_PL
          this.ma_pl = dt.DS_DANHBA_TSL[0].MA_PL
        }
        //Megawan
        else if (this.dichvuvt_id == 7 || this.dichvuvt_id == 8) {
          this.matb_tn = dt.DS_DANHBA_MGWAN[0].MATB_TN
          this.ds_loainode = dt.DS_LOAINODE.map((item) => {
            return {
              id: item.LOAINODE_ID,
              text: item.LOAINODE
            }
          })
          this.loainode_id_cu = dt.DS_DANHBA_MGWAN[0].LOAINODE_ID
          this.ma_crm_cu = dt.DS_DANHBA_MGWAN[0].MA_CRM
          this.huong_kn_cu = dt.DS_DANHBA_MGWAN[0].HUONG_KN
          this.tinh_id_cu = dt.TINH_KN_ID * 1
          this.tinh_id = dt.TINH_KN_ID * 1
          this.loaikenh_id_cu = dt.DS_DANHBA_MGWAN[0].LOAIKENH_ID
          this.loaikenh_id = dt.DS_DANHBA_MGWAN[0].LOAIKENH_ID
          this.tocdo_id_cu = dt.DS_DANHBA_MGWAN[0].TOCDO_ID
          this.tocdo_id = dt.DS_DANHBA_MGWAN[0].TOCDO_ID
          this.ma_pl_cu = dt.DS_DANHBA_MGWAN[0].MA_PL
          this.ma_pl = dt.DS_DANHBA_MGWAN[0].MA_PL
          this.ma_erp_cu = dt.DS_DANHBA_MGWAN[0].ID_ERP
        } else {
          this.$toast.error('Không hỗ trợ dịch vụ này, vui lòng thử lại sau!')
          this.hideModal()
        }
      }

      res = await this.$root.context.get('/web-hopdong/tracu-danhba/fn_thaydoittdb_load')
      console.log(JSON.parse(res.data))
      if (res.data) {
        const dt = JSON.parse(res.data)
        this.ds_dichvuvt = dt.DS_DICHVU_VT.map((item) => {
          return {
            id: item.DICHVUVT_ID,
            text: item.TEN_DVVT
          }
        })
        this.ds_tinh = dt.DS_TINH.map((item) => {
          return {
            id: item.TINH_ID,
            text: item.TENTINH
          }
        })
        this.ds_loaikenh = dt.DS_LOAIKENH.map((item) => {
          return {
            id: item.LOAIKENH_ID,
            text: item.LOAI_KENH
          }
        })
        this.ds_tocdo = dt.DS_TOCDO_KENH.map((item) => {
          return {
            id: item.TOCDO_ID,
            text: item.TOC_DO
          }
        })
      }
    },
    async tbtnGhiLai_click() {
      if (!(!this.diachiObj || this.diachiLD == '' || this.diachiLD.trim() == this.diachiLD_cu.trim())) {
        //cập nhật địa chỉ
        const js_diachi = {
          DIACHI_ID: this.diachiObj.diachi_id * 1,
          PHO_ID: this.diachiObj.pho_id * 1,
          PHUONG_ID: this.diachiObj.phuong_id * 1,
          QUAN_ID: this.diachiObj.quan_id * 1,
          SONHA: this.diachiObj.so_nha,
          AP_ID: this.diachiObj.ap_id * 1,
          DIACHI: this.diachiObj.diachimoi,
          TINH_ID: this.diachiObj.tinh_id * 1,
          KHU_ID: this.diachiObj.khu_id * 1,
          DACDIEM_ID: this.diachiObj.dacdiem_id * 1,
          VIDO: this.diachiObj.lat,
          KINHDO: this.diachiObj.lng,
          BLOCK: this.diachiObj.block,
          TANG: this.diachiObj.tang,
          PHONG: this.diachiObj.phong,
          MOTA: this.diachiObj.mota
        }
        let res = await this.$root.context.post('/web-hopdong/tachnhapthuebao/sp_insert_diachi', {
          js_diachi: JSON.stringify(js_diachi)
        })
        if (res.data.error_code == 'BSS-00000000') {
          this.$toast.success('Cập nhật địa chỉ thành công!')
        } else {
          this.$toast.error('Cập nhật địa chỉ thất bại!')
        }
      }
      const nhanvien_id = await this.$root.token.getNhanVienID()
      const donvi_id = (await this.$root.token.getDonViID()) * 1
      const may_cn = await this.$root.token.getMachine()
      const username = await this.$root.token.getUserName()
      let res = await this.$root.context.post('/web-quantri/donvitinh/get_keys', {
          keyname: 'TDTT_DB_ID',
          numblocksize: 1,
          numretry: 10
        })
        // console.log(res)
      const tdtt_db_id = res.data;
      if (this.dichvuvt_id == 7 || this.dichvuvt_id == 8) {
        const js_tdtt_db_mgwan = {
          TDTT_DB_ID: tdtt_db_id,
          THUEBAO_ID: this.dt_tb.thuebao_id * 1,
          THUEBAO_CHA_ID: this.dt_tb.thuebao_cha_id * 1,
          HUONG_KN: this.huong_kn,
          MA_TB: this.ma_tb,
          TRANGTHAI_ID: 1,
          NHANVIEN_ID: nhanvien_id,
          DONVI_ID: donvi_id,
          MAY_CN: may_cn,
          NGAY_DK: moment().format('DD/MM/yyyy'),
          NGAY_HT: moment().format('DD/MM/yyyy'),
          NGAY_CN: moment().format('DD/MM/yyyy'),
          NGUOI_CN: username,
          LOAINODE_ID: this.loainode_id * 1,
          LOAIKENH_ID: this.loaikenh_id * 1,
          TINH_KN_ID: this.tinh_id * 1,
          TEN_KH: this.ten_kh,
          DIACHI_ID: this.diachiObj ? this.diachiObj.diachi_id * 1 : null,
          MA_CRM: this.ma_crm,
          ID_ERP: this.ma_erp,
          MA_VTT: this.ma_vtt,
          TOCDO_ID: this.tocdo_id * 1,
          MA_HD: this.dt_kh.ma_hd
        }
        res = await this.$root.context.post('/web-hopdong/tracu-danhba/sp_insert_tdtt_db_mgwan',{
          js_tdtt_db_mgwan: JSON.stringify(js_tdtt_db_mgwan)
        });
        if(res.error_code == 'BSS-00000000'){
          res = await this.$root.context.post('/web-hopdong/tracu-danhba/capnhat_dbtb_bss_mgwan',
          { vthuebao_id: this.dt_tb.thuebao_id * 1,
            vma_tb: this.ma_tb,
            vma_crm: this.ma_crm,
            vid_erp: this.ma_erp,
            vhuong_kn: this.huong_kn,
            vtinh_kn_id: this.tinh_id * 1,
            vtocdo_id: this.tocdo_id * 1,
            vma_pl: this.ma_pl,
            vdiachi_id: this.diachiObj ? this.diachiObj.diachi_id * 1 : null,
            vdiachi_ld: this.diachiLD,
            vthuebao_cha_id: this.dt_tb.thuebao_cha_id * 1,
            vtinh_tc: this.tinh_id * 1,
            vma_tb_tinh: this.ma_vtt,
            vthuebao_id_tc: this.dt_tb.thuebao_id * 1,
            vten_kh: this.ten_kh,
            vloainode_id: this.loainode_id * 1,
            vdiachi_kn: this.diachiLD,
            vloaikenh_id: this.loaikenh_id * 1
          }
          );
          if(res.error_code == 'BSS-00000000'){
            this.$emit('xacnhan')
          }
          else{
            this.$toast.error(res.message_detail)
          }
        }
        else{
          this.$toast.error(res.message_detail)
        }

        
      }
      else if(this.dichvuvt_id == 9){
        const js_tdtt_db_tsl = {
          TDTT_DB_ID: tdtt_db_id,
          THUEBAO_ID: this.dt_tb.thuebao_id * 1,
          MA_TB: this.ma_tb,
          TRANGTHAI_ID: 1,
          NHANVIEN_ID: nhanvien_id,
          DONVI_ID: donvi_id,
          NGAY_DK: moment().format('DD/MM/yyyy'),
          NGAY_HT: moment().format('DD/MM/yyyy'),
          NGAY_CN: moment().format('DD/MM/yyyy'),
          MAY_CN: may_cn,
          NGUOI_CN: username,
          TEN_KH: this.ten_kh,
          MA_VTT: this.ma_vtt,
          DIACHI_ID: this.diachiObj ? this.diachiObj.diachi_id * 1 : null,
          MA_HD: this.dt_kh.ma_hd,
          DAUCUOI_ID: 1
        }
        res = await this.$root.context.post('/web-hopdong/tracu-danhba/sp_insert_tdtt_db_tsl',{
          js_tdtt_db_tsl: JSON.stringify(js_tdtt_db_tsl)
        });
        if(res.error_code == 'BSS-00000000'){
          res = await this.$root.context.post('/web-hopdong/tracu-danhba/capnhat_dbtb_bss_tsl',
          { vthuebao_id: this.dt_tb.thuebao_id * 1,
            vma_crm: this.ma_crm,
            vid_erp: this.ma_erp,
            vma_pl: this.ma_pl,
            vdiachi_id: this.diachiObj ? this.diachiObj.diachi_id * 1 : null,
            vdiachi_ld: this.diachiLD,
            vtinh_tc: this.tinh_id * 1,
            vma_tb_tinh: this.ma_vtt,
            vthuebao_id_tc:  this.dt_tb.thuebao_id * 1,
            vten_kh: this.ten_kh,
          }
          );
          if(res.error_code == 'BSS-00000000'){
            this.$emit('xacnhan')
          }
          else{
            this.$toast.error(res.message_detail)
          }
        }
        else{
          this.$toast.error(res.message_detail)
        }
      }
    },
    getDiaChiLD(data) {
      console.log(data)
      this.diachiLD = data.diachimoi
      this.diachiObj = data
    },
    hideModal() {
      this.data = initialData
      this.$refs.ThayDoiThongTinModal.hide()
    },
    showModal() {
      this.$refs.ThayDoiThongTinModal.show()
    }
  },
  components: {
    ModalChonDiaChi
  }
}
</script>

<style lang="scss" scoped></style>
