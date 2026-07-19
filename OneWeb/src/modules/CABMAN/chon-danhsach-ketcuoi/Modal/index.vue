<template src="./index.html"></template>

<script>
import XLSX from "xlsx";
export default {
  components: {},
  name: 'Modal',
  props: {
    modalShow: Boolean,
    doiTuong: Object,
    id: String
  },

  computed: {
      getDS_KETCUOI () {
      if (this.XEMCHITIET === true) {
        let arr = []
        for (let index = 0; index < this.grcDSDT.length; index++) {
          const element = this.grcDSDT[index]
          arr.push({
            KETCUOI_ID: element.KETCUOI_ID,
            KYHIEU: element.KYHIEU,
            TEN_KC: element.TEN_KC,
            DIACHI: element.DIACHI,
            THONGTIN_BS: element.THONGTIN_BS !== null ? element.THONGTIN_BS.replaceAll('\r\n', '<BR/>') : null
          })
        }
        console.log('this.getDS_KETCUOI', arr)
        return arr
      } else {
        let arr = []
        for (let index = 0; index < this.grcDSDT.length; index++) {
          const element = this.grcDSDT[index]
          const THONGTIN_BS = element.THONGTIN_BS
          let T_THONGTIN_BS = null
          if (THONGTIN_BS !== null) {
            let arrTHONGTIN_BS = THONGTIN_BS.split('\r\n')
            if (arrTHONGTIN_BS.length > 0) {
              T_THONGTIN_BS = arrTHONGTIN_BS[0]
            }
          }
          arr.push({
            KETCUOI_ID: element.KETCUOI_ID,
            KYHIEU: element.KYHIEU,
            TEN_KC: element.TEN_KC,
            DIACHI: element.DIACHI,
            THONGTIN_BS: T_THONGTIN_BS
          })
        }
        return arr
      }
    }
  },
  data () {
    return {
        key: 1,
        isShown: false,
        checkrdo: '0',
        title: '',
        grcDSDT: [],
        isDisableBtn: true,
        dataExcelEx: [],
        cboData: {
            cboLoaiKetCuoi: [],
            cboDungLuong: [],
            cboTTVT: [],
            cboToKT: [],
            cboTram: [],
            
        },
        modelData: {
            mLoaiKetcuoi: 0,
            mDungLuong: 0,
            mTTVT: 0,
            mToKT: -1,
            mTram: -1
        },
        mTenKC: '',
        mKyHieu: '',
        XEMCHITIET: false,
        selectedRow: null,
        ketcuoi_id: -1
    }
  },
  watch: {
       
  },
  methods: {
    onHiddenModal() {
      this.isShown = false
      this.key++
    },
    async onShownModal() {
        this.isShown = true
        try{
            this.loading(true)
            if (this.grcDSDT != null && this.grcDSDT.length > 0) 
                this.grcDSDT = []
            this.mTenKC = ''
            this.mKyHieu = ''
            switch (this.doiTuong.kieu)
            {
                case 0: // Danh sách kết cuối đã đấu cáp
                    this.title = 'Chọn danh sách kết cuối đã đấu cáp'
                    this.NAP_DS_LOAI_KC();
                    this.DS_DUNGLUONG_KC();
                    this.NAP_DS_TTVT();
                    break;
                case 1: // Danh sách kết cuối đã đấu cáp
                    this.title = 'Chọn danh sách kết cuối chưa đấu cáp'
                    this.NAP_DS_LOAI_KC();
                    this.DS_DUNGLUONG_KC();
                    this.NAP_DS_TTVT();
                    break;
                case 2: // Danh sách kết cuối đã đấu cáp
                    this.title = 'Chọn danh sách kết cuối'
                    this.NAP_DS_LOAI_KC();
                    this.DS_DUNGLUONG_KC();
                    this.NAP_DS_TTVT();
                    break;
            }
        }catch(error){

        }finally{
            this.loading(false)
        }
    },
    grid_onCommandClicked (data) {},
    grid_onRowDoubleClicked (data) {},
    grid_onSelectedRowChanged (data) {
        if (data) {
            this.selectedRow = data
            this.ketcuoi_id = data.KETCUOI_ID
        }
      
    },
    chonDT: function(){
        if (this.ketcuoi_id == -1){
            this.$root.toastError("Chưa chọn kết cuối")
                return
        }
        this.onHiddenModal()
    },
    huyDT: async function () {
        this.$bvModal.hide('popupChonKetCuoi')
    },
    setDisableBtn: function (isHasData) {
      this.isDisableBtn = !isHasData;
    },
    onClickItem: async function(){
        console.log('onClickItem ')
    },
    NAP_DS_LOAI_KC: async function(){
        var rs = await this.$root.context.get("/web-cabman/ketcuoidadaucap/ds-loai-kc");
        console.log('NAP_DS_LOAI_KC ',rs)
        if (rs.data.length > 0) {
            this.cboData.cboLoaiKetCuoi = rs.data
            this.modelData.mLoaiKetcuoi = rs.data[0].LOAIKC_ID
        }
    },
    DS_DUNGLUONG_KC: async function(){
        var rs = await this.$root.context.get("/web-cabman/ketcuoidadaucap/ds-dungluong-ketcuoi");
        console.log('DS_DUNGLUONG_KC ',rs)
        if (rs.data.length > 0) {
            this.cboData.cboDungLuong = rs.data
            this.modelData.mDungLuong = rs.data[0].DUNGLUONG_ID
        }
    },
    NAP_DS_TTVT: async function(){
        let nhanvien_id = this.$root.token.getNhanVienID() //3970
        var rs = await this.$root.context.get("/web-cabman/chon-ketcuoi/lay-ds-ttvt-theo-nv", {nhanvien_id: nhanvien_id}) //
        console.log('NAP_DS_TTVT ',rs)
        this.cboData.cboTTVT = [{
          DONVI_ID: 0,
          TEN_DV: '--- Tất cả ---'
        }, ...rs.data]
        this.modelData.mTTVT = 0
        await this.NAP_DS_TOKT()
    },
    NAP_DS_TOKT: async function(){
        let ttvt_id = this.modelData.mTTVT
        let nhanvien_id = this.$root.token.getNhanVienID() //3970
        var rs = await this.$root.context.get("/web-cabman/chon-ketcuoi/lay-ds-tokt-theo-nv", {ttvt_id: ttvt_id, nhanvien_id: nhanvien_id}) //
        console.log('NAP_DS_TOKT ',rs)
        this.cboData.cboToKT = [{
          DONVI_ID: 0,
          TEN_DV: '--- Tất cả ---'
        }, ...rs.data]
        this.modelData.mToKT = 0
        this.NAP_DS_TRAM()
    },
    NAP_DS_TRAM: async function(){
        let ttvt_id = this.modelData.mToKT
        var rs = await this.$root.context.get("/web-cabman/chon-ketcuoi/lay-ds-tramtbi-theo-toql", {ttvt_id: ttvt_id}) //
        console.log('rs ',rs)
        this.cboData.cboTram = [{
          DONVI_ID: 0,
          TEN_DV: '--- Tất cả ---'
        }, ...rs.data]
        this.modelData.mTram = 0
    },
    traCuuKT: async function(){
        try {
            this.loading(true)
            console.log('kieu ', this.doiTuong.kieu)
            this.grcDSDT = []
            let rs = []
            if (this.doiTuong.kieu === 0)
            {
                console.log('rdoTimChinhXac ', this.checkrdo)
                //Tìm kết cuối đã nối cáp
                let data = {
                    timcx: this.checkrdo == '0' ? 1 : 0,
                    loaikc_id: this.modelData.mLoaiKetcuoi,
                    dungluong: this.modelData.mDungLuong,
                    ten_kc: this.mTenKC,
                    kyhieu: this.mKyHieu,
                    ttvt_id: this.modelData.mTTVT,
                    tokt_id: this.modelData.mToKT,
                    tramtbi_id: this.modelData.mTram
                }
                console.log('dataa ', data)
                rs = await this.$root.context.post("/web-cabman/chon-ketcuoi/lay-ds-ketcuoi-da-noi-cap", data)
            }
            else if (this.doiTuong.kieu === 1 )
            {
                //Tìm kết cuối chưa nối cáp
                let data = {
                    timcx: this.checkrdo == '0' ? 1 : 0,
                    loaikc_id: this.modelData.mLoaiKetcuoi,
                    dungluong: this.modelData.mDungLuong,
                    ten_kc: this.mTenKC,
                    kyhieu: this.mKyHieu,
                    ttvt_id: this.modelData.mTTVT,
                    tokt_id: 0,
                    tramtbi_id: this.modelData.mTram
                }
                rs = await this.$root.context.post("/web-cabman/chon-ketcuoi/lay-ds-ketcuoi-chua-noi-cap", data)
                 console.log('rsrsrsrsrs ', rs)
            }
            else if (this.doiTuong.kieu === 2)
            {
                //Tất cả kết cuối ko nằm trong trạm hoặc trong kết cuối khác
                let data = {
                    timcx: this.checkrdo == '0' ? 1 : 0,
                    loaikc_id: this.modelData.mLoaiKetcuoi,
                    dungluong: this.modelData.mDungLuong,
                    ten_kc: this.mTenKC,
                    kyhieu: this.mKyHieu,
                    ttvt_id: this.modelData.mTTVT,
                    tokt_id: this.modelData.mToKT,
                    tramtbi_id: this.modelData.mTram
                }
                rs = await this.$root.context.post("/web-cabman/chon-ketcuoi/lay-ds-ketcuoi-khac", data) 
            }
            if (rs.error_code !== 'BSS-00000000')
            {
                this.grcDSDT = []
                this.$root.toastError(rs.message_detail)
            }
            else
            {
                this.grcDSDT = rs.data;
                
            }
            this.setDisableBtn(rs.data !== null && rs.data.length >0)
        } catch (error) {
            this.$root.toastError(error.response.data.message_detail)
        }
        finally {
            this.loading(false)
        }
        
    },
    xuatExcel: async function(){
      try
      {
        var ma_thuebao = XLSX.utils.json_to_sheet(Object.values(this.grcDSDT));
        var wb = XLSX.utils.book_new(); // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, ma_thuebao, "DS_KETCUOI"); // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, "export.xlsx");
      }
      catch (ex)
      {
        this.$toast.error("Có lỗi trong quá trình xuất file Excel !" +ex)
      }
    },
  },
  async created () {
    this.grcDSDT = []
  }
}
</script>

<style>
    .chon-tram-tbi .show-detail {
        display: grid;
        overflow: hidden;
        line-height: 32px;
        padding: 0 5px;
        border-left: 0;
        border-top: 0;
    }
    .chon-tram-tbi .no-show-detail {
        white-space: normal;
    }
    a.disabled {
        pointer-events: none;
        cursor: default;
        color: #8e8f91 !important;
    }
    .table-result td, .table-result th {
        height: auto !important;
    }
    td.show-detail span {
        display: contents;
    }
</style>