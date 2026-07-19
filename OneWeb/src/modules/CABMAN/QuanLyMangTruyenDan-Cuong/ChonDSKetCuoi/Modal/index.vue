import { maxLength } from 'vuelidate/lib/validators';
<template>
  <b-modal
    v-model="modalShow"
    size="lg"
    no-close-on-backdrop
    hide-footer
    hide-header
    hide-header-close >
`<div class="modal-content popup-box">
        <div class="popup-header">
            <div class="title"><span class="icon one-notepad"></span> Chọn danh sách kết cuối</div>
                <div class="close -ap icon-close" data-dismiss="modal" @click="closeModal">
            </div>
        </div>
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a href="#/cabman/chon-danhsach-ketcuoi" @click="traCuuKT()">
                        <span class="icon one-file-search"></span>Tra cứu
                    </a>
                </li>
                <li >
                    <a href="#/cabman/chon-danhsach-ketcuoi"  @click="xuatExcel()" :class="{'disabled': isDisableBtn}">
                        <span class="icon one-xlsx-import"></span>Xuất Excel
                    </a>
                </li>
                <download-excel :data="cboData.dataExcelEx" id="exportexcel"  style="display: none;">
                    <span class="icon one-xlsx"></span>Xuất Excel
                </download-excel>
            </ul>
        </div>
        <div class="popup-body">
            <div class="box-form">
                <div class="legend-title">
                    <div class="pull-left">Tìm kiếm kết cuối</div>
                    <div class="pull-right">
                        <div class="list-checks">
                            <div class="item">
                                <div class="check-action">
                                    <input name="chkTracuu" type="radio" value="rdoChinhXac" v-model="checkrdo" >
                                    <span class="name">Tìm chính xác</span>
                                </div>
                            </div>
                            <div class="item">
                                <div class="check-action">
                                    <input name="chkTracuu" type="radio" value="rdoGandung"  v-model="checkrdo" >
                                    <span class="name">Tìm gần đúng</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <div class="row">
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <div class="key w90">Loại kết cuối</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select name="" id="1" class="form-control" v-model="modelData.mLoaiKetcuoi">
                                        <option v-for="item in cboData.cboLoaiKetCuoi" :value="item.LOAIKC_ID">{{item.LOAI_KC}}</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <div class="key w90">Dung lượng</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select name="" id="2" class="form-control" v-model="modelData.mDungLuong">
                                        <option v-for="item in cboData.cboDungLuong" :value="item.DUNGLUONG_ID">{{item.DUNGLUONG}}</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w90">Trung tâm VT</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select name="" id="3" class="form-control" v-model="modelData.mTTVT" @change="NAP_DS_TOKT()">
                                        <option v-for="item in cboData.cboTTVT" :value="item.DONVI_ID">{{item.TEN_DV}}</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                </div>
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w90">Ký hiệu</div>
                            <div class="value">
                                <input type="text" class="form-control" v-model="mTenKC">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w90">Tổ quản lý</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select name="" id="4" class="form-control" v-model="modelData.mToKT" @change="NAP_DS_TRAM()">
                                        <option v-for="item in cboData.cboToKT" :value="item.DONVI_ID">{{item.TEN_DV}}</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w90">Tên kết cuối</div>
                            <div class="value" v-model="mKyHieu">
                                <input type="text" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w90">Tổng đài</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select name="" id="4" class="form-control" v-model="modelData.mTram">
                                        <option v-for="item in cboData.cboTram" :value="item.DONVI_ID">{{item.TEN_DV}}</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box-form">
                <div class="legend-title">
                    <div class="pull-left">Danh sách đối tượng</div>
                    <div class="pull-right normal">
                        Hiển thị tối đa <span class="red">200</span> đối tượng
                    </div>
                    <div class="clearfix"></div>
                </div>
                <div class="table-content">
                    <DataGrid v-bind:columns="[
                        {fieldName: 'KYHIEU', headerText: 'Ký hiệu', allowFiltering: true, allowSorting: false},
                        {fieldName: 'TEN_KC', headerText: 'Tên kết cuối', allowFiltering: true, allowSorting: false, width: 5},
                        {fieldName: 'DIACHI', headerText: 'Địa chỉ', allowFiltering: true, allowSorting: false},
                        {fieldName: 'THONGTIN_BS', headerText: 'Thông tin chi tiết', allowFiltering: true, allowSorting: false},
                        ]"
                                v-bind:dataSource="grcDSDT"
                                :showFilter="true"
                                :allowPaging = true
                                :enablePagingServer = false
                                @selectedRowChanged="onClickItem">

                    </DataGrid>
                </div>
            </div>
            <div class="info-row">
                <div class="value padl0">
                    <div class="check-action mart10">
                        <input type="checkbox" class="check">
                        <span class="name">Hiển thị thông tin chi tiết</span>
                    </div>
                </div>
                <div class="value nowrap padl10 tright">
                    
                    <button class="btn-main btn w100 marl10" :disabled="grcDSDT.length<=0">
                        <span class="nc-icon-glyph ui-1_check-simple"></span>Chọn
                    </button>
                    <button class="btn-second btn w100">
                        <span class="fa fa-times"></span>Huỷ bỏ
                    </button>
                </div>
            </div>
        </div>
    </div>`
  </b-modal>
  
</template>

<script>

export default {
  components: {},
  name: 'Modal',
  props: {
    modalShow: Boolean,
    doiTuong: Object
  },

  computed: {
  },
  data () {
    return {
        checkrdo: 'rdoChinhXac',
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
        mKyHieu: ''
    }
  },
  computed: {

  },
  watch: {
       
  },
  methods: {
    closeModal: function () {
      this.$emit('closeModal', this.modalShow)
    },
    setDisableBtn: function (isHasData) {
      this.isDisableBtn = isHasData;
    },
    onClickItem: async function(){
        console.log('onClickItem ')
    },
    NAP_DS_LOAI_KC: async function(){
        var rs = await this.$root.context.get("/web-cabman/ketcuoidadaucap/ds-loai-kc");
       
        if (rs.data.length > 0) {
            this.cboData.cboLoaiKetCuoi = rs.data
            this.modelData.mLoaiKetcuoi = rs.data[0].LOAIKC_ID
        }
    },
    DS_DUNGLUONG_KC: async function(){
        var rs = await this.$root.context.get("/web-cabman/ketcuoidadaucap/ds-dungluong-ketcuoi");
        console.log('rs ',rs)
        if (rs.data.length > 0) {
            this.cboData.cboDungLuong = rs.data
            this.modelData.mDungLuong = rs.data[0].DUNGLUONG_ID
        }
    },
    NAP_DS_TTVT: async function(){
        let nhanvien_id = this.$root.token.getNhanVienID()
        var rs = await this.$root.context.get("/web-cabman/chon-ketcuoi/lay-ds-ttvt-theo-nv", {nhanvien_id: nhanvien_id}) //
        console.log('rs ',rs)
        if (rs.data.length > 0) {
            console.log(rs.data[0].DONVI_ID)
            this.cboData.cboTTVT = rs.data
            this.modelData.mTTVT = rs.data[0].DONVI_ID
            if(rs.data[0].DONVI_ID > 0){
                this.NAP_DS_TOKT()
            }
        }
    },
    NAP_DS_TOKT: async function(){
        let ttvt_id = this.modelData.mTTVT
        var rs = await this.$root.context.get("/web-cabman/chon-ketcuoi/lay-ds-tokt-theo-nv", {ttvt_id: ttvt_id, nhanvien_id: nhanvien_id}) //
        console.log('rs ',rs)
        if (rs.data.length > 0) {
            console.log(rs.data[0].DONVI_ID)
            this.cboData.cboToKT = rs.data
            this.modelData.mToKT = rs.data[0].DONVI_ID
            if (rs.data[0].DONVI_ID > 0) {
                this.NAP_DS_TRAM()
            }
        }
    },
    NAP_DS_TRAM: async function(){
        let ttvt_id = this.modelData.mToKT
        var rs = await this.$root.context.get("/web-cabman/chon-ketcuoi/lay-ds-tramtbi-theo-toql", {ttvt_id: ttvt_id}) //
        console.log('rs ',rs)
        if (rs.data.length > 0) {
            console.log(rs.data[0].DONVI_ID)
            this.cboData.cboTram = rs.data
            this.modelData.mTram = rs.data[0].DONVI_ID
        }
    },
    traCuuKT: async function(){
        console.log('kieu ', console.log('kieu ',this.doiTuong.kieu))
        let rs = []
        if (this.doiTuong.kieu === 0)
        {
            //Tìm kết cuối đã nối cáp
            let data = {
                timcx: this.rdoTimChinhXac === 'rdoChinhXac' ? 1 : 0,
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
                timcx: this.rdoTimChinhXac === 'rdoChinhXac' ? 1 : 0,
                loaikc_id: this.modelData.mLoaiKetcuoi,
                dungluong: this.modelData.mDungLuong,
                ten_kc: this.mTenKC,
                kyhieu: this.mKyHieu,
                ttvt_id: this.modelData.mTTVT,
                tokt_id: this.modelData.mToKT,
                tramtbi_id: this.modelData.mTram
            }
            rs = await this.$root.context.post("/web-cabman/chon-ketcuoi/lay-ds-ketcuoi-chua-noi-cap", data)
        }
        else if (this.doiTuong.kieu === 2)
        {
            //Tất cả kết cuối ko nằm trong trạm hoặc trong kết cuối khác
            let data = {
                timcx: this.rdoTimChinhXac === 'rdoChinhXac' ? 1 : 0,
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
            //this.closeBlurLoading();
            //grcDSDT.KeyFieldName = "KETCUOI_ID";
            //grcDSDT.ParentFieldName = "KETCUOI_ROOT_ID";
            this.grcDSDT = rs.data;
            console.log('rs.data ', rs.data)
            
        }
        this.setDisableBtn(rs.data !== null && rs.data.length >0)
        // tsbtnXuatExcel.Enabled = rs.Data != null && rs.Data.Rows.Count > 0;
        // btnChon.Enabled = rs.Data != null && rs.Data.Rows.Count > 0;
    },
    xuatExcel: async function(){
      try
      {
        this.dataExcelEx = Object.values(this.grcDSDT)
        document.getElementById("exportexcel").click();
      }
      catch (ex)
      {
        this.$toast.error("Có lỗi trong quá trình xuất file Excel !" +ex)
      }
    },
  },
  async created () {
    console.log('kieu ',this.grcDSDT.length)
    
    switch (this.doiTuong.kieu)
        {
            case 0: // Danh sách kết cuối đã đấu cáp
                this.NAP_DS_LOAI_KC();
                this.DS_DUNGLUONG_KC();
                this.NAP_DS_TTVT();
                break;
            case 1: // Danh sách kết cuối đã đấu cáp
                this.NAP_DS_LOAI_KC();
                this.DS_DUNGLUONG_KC();
                this.NAP_DS_TTVT();
                break;
            case 2: // Danh sách kết cuối đã đấu cáp
                this.NAP_DS_LOAI_KC();
                this.DS_DUNGLUONG_KC();
                this.NAP_DS_TTVT();
                break;
            // case 1: // Danh sách kết cuối chưa đấu cáp
            //     panTimKiemKC.Visible = true;
            //     NAP_DS_LOAI_KC();
            //     NAP_DS_DUNGLUONG_KC();
            //     NAP_DS_TTVT();
            //     Text = "Chọn danh sách kết cuối chưa đấu cáp";
            //     cboTRAM.Enabled = false;
            //     break;
            // case 2: // Danh sách kết cuối(chưa nối cáp lẫn đã nối cáp)
            //     panTimKiemKC.Visible = true;
            //     NAP_DS_LOAI_KC();
            //     NAP_DS_DUNGLUONG_KC();
            //     NAP_DS_TTVT();
            //     Text = "Chọn danh sách kết cuối";
            //     break;
        }
  }
}
</script>
<style>
a.disabled {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>