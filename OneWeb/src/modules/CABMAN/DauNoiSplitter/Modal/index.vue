<template src="./index.html">
</template>

<script>
import { DropDownTreePlugin } from "@syncfusion/ej2-vue-dropdowns";
import { L10n } from '@syncfusion/ej2-base'

L10n.load({
  'vi-VN': {
    grid: {
      EmptyRecord: 'Không có bản ghi nào để hiển thị'
    }
  }
})
let thietBiNguonTemplate = function () {
  return {
    template: {
      template: `
      <div>
            <div class="pull-left" style="width: calc(100% - 25px);display: block;overflow: hidden;text-overflow: ellipsis;" :title="data.THIETBI_N ? data.THIETBI_N : ''"> {{data.THIETBI_N ? data.THIETBI_N : ""}}</div>
                <div class="pull-right" @click="onClick">
                    <button class="btn btn-second lh12 pad2" >
                        <span class="-ap icon-more_horiz"></span>
                    </button>
                </div>
            </div>`,
      data() {
        return {
          data: { }
        }
      },
      computed: {
        parent() {
          return this.$parent.$parent.$parent.$parent.$parent
        },
        id(){
          return `${this.data.CONG_VAO}`
        }
      },
      methods:{
        onClick() {
        //   this.parent.xemPopupQuanLyCap(this.data.CAPVAO_ID)
        }
      }
    }
  }
}
var columnTemplateChonNhieuPortDV = function () {
  return {
    template: {
      template: `
            <div class="check-action">
                <input
                    type="checkbox"
                    class="check"
                    v-model="parent.DVChecked"
                    :value="data.CONG_VAO"/>
                <span class="name"></span>
            </div>
        `,
      data() {
        return {
          data: { }
        }
      },
      computed: {
        parent() {
            return this.$parent.$parent.$parent.$parent.$parent
        },
      }
    }
  }
}
var columnTemplateChonNhieuPortDR = function () {
  return {
    template: {
      template: `
            <div class="check-action">
                <input
                    type="checkbox"
                    class="check"
                    v-model="parent.DRChecked"
                    :value="data.CONG_RA"/>
                <span class="name"></span>
            </div>
        `,
      data() {
        return {
          data: { }
        }
      },
      computed: {
        parent() {
          return this.$parent.$parent.$parent.$parent.$parent
        },
      }
    }
  }
}
var thietBiDichTemplate = function () {
  return {
    template: {
      template: `
        <div>
            <div class="pull-left" style="width: calc(100% - 25px);display: block;overflow: hidden;text-overflow: ellipsis;" :title="data.THIETBI_D ? data.THIETBI_D : ''"> {{data.THIETBI_D?data.THIETBI_D:""}}</div>
                <div class="pull-right" @click="onClick">
                    <button class="btn btn-second lh12 pad2" >
                        <span class="-ap icon-more_horiz"></span>
                    </button>
                </div>
        </div>`,
      data() {
        return {
          data: { }
        }
      },
      computed: {
        parent() {
          return this.$parent.$parent.$parent.$parent.$parent
        },
        id(){
          return `${this.data.CONG_RA}`
        }
      },methods:{
        onClick() {
        //   this.parent.xemPopupQuanLyCap(this.data.CAP_ID)
        }
      }
    }
  }
}
export default {
    components: {
        DropDownTreePlugin
    },
    props: {
        modalId: String,
        splitterId: Number,
        loaikcId: Number
    },
    data: function() {
        return {
            DVColumns:[
                {
                customAttributes:{hidden:true},
                columns:[
                    {
                        field: 'THIETBI_N',
                        headerText: 'Thiết bị nguồn',
                        template: thietBiNguonTemplate,
                        width: '15%',
                        textAlign: 'Center',
                        minWidth: 10
                    },
                    {
                        field: 'VITRI_N',
                        headerText: 'Port',
                        width: '10%',
                        textAlign: 'Center',
                        minWidth: 10
                    },
                ],
                },
            ],
            thongTinCongColums: [
                {
                    headerText: 'Mặt trước',
                    textAlign: 'Center',
                    columns:[
                        {
                            field: 'CHON_VAO',
                            headerText: 'Chọn',
                            template: columnTemplateChonNhieuPortDV,
                            width: '10%',
                            textAlign: 'Center',
                            minWidth: 10
                        },
                        {
                            field: 'CONG_VAO',
                            headerText: 'Vị trí vào',
                            width: '10%',
                            textAlign: 'Center',
                            minWidth: 10
                        },
                    ]
                },
                {
                    headerText: 'Mặt sau',
                    textAlign: 'Center',
                    columns:[
                        {
                            field: 'CONG_RA',
                            headerText: 'Vị trí ra',
                            width: '10%',
                            textAlign: 'Center',
                            minWidth: 10
                        },
                        {
                            field: 'CHON_RA',
                            headerText: 'Chọn',
                            template: columnTemplateChonNhieuPortDR,
                            width: '10%',
                            textAlign: 'Center',
                            minWidth: 10
                        },
                    ]
                }
            ],
            DRColumns:[
                {
                customAttributes:{hidden:true},
                columns:[
                    {
                        field: 'THIETBI_D',
                        headerText: 'Thiết bị đích',
                        template: thietBiDichTemplate,
                        width: '15%',
                        textAlign: 'Center',
                        minWidth: 10
                    },
                    {
                        field: 'VITRI_D',
                        headerText: 'Port',
                        width: '10%',
                        textAlign: 'Center',
                        minWidth: 10
                    },
                ]
                }
            ],
            
            DAUNOI_DR: {},
            DAUNOI_DV: {},
            fieldsThietBiDv3: {},
            fieldsThietBiDr3: {},
            donvi_id: null,
            lbl_TenThietBi: null,
            dsCongSoiVao: [],
            dsCongSoiRa: [],

            dsLoaiThietBi_DV2: [],
            dsLoaiThietBi_DR2: [],

            dsDauNoi: [],

            dsThietBi_DV2: [],
            dsThietBi_DV3: [],
            dsThietBi_DR2: [],
            dsThietBi_DR3: [],
            // Đấu nối check
            DVChecked:[],
            DRChecked:[],

            cbo_loaiTbiId_DV2: null,
            cbo_loaiTbiId_DR2: null,
            cbo_thietBi_DV2: null,
            cbo_thietBi_DV3: null,
            cbo_thietBi_DR2: null,
            cbo_thietBi_DR3: null,

            chk_CSVCheck: [],
            chk_CSRCheck: [],

            cellIndex:null, // column index
        }
    },
    methods: {
        getTenLoaiTBI: function(){
            switch(this.loaikcId){
                case 5: return "splitter";
                case 9: return "UGain";
                case 25: return "CEx"; 
            }

            return 'kết cuối';
        },
        onShownModal: async function() {
            try {
                this.loading(true);
                let rs = await this.$root.context.get(`web-cabman/dau-noi-splitter/thong-tin/${this.splitterId}`);
                if(rs.data.length) {
                    this.donvi_id = rs.data[0].DONVI_ID;
                    this.lbl_TenThietBi = rs.data[0].KYHIEU;
                    this.dsCongSoiVao = [];
                    this.dsCongSoiRa = [];
                    await this.napDsLoaiTbiDv2();
                    await this.napDsLoaiTbiDr2();
                    await this.napDsDauNoi();
                }
                else {
                    this.$root.toastError('Phiến dọc không tồn tại hoặc đã bị xóa');
                }
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        onHiddenModal: function() {

        },
        onchangeThietBiDv3: function() {
            let defaultObj = this.$refs.thietbiDv3Tree.ej2Instances;
            this.cbo_thietBi_DV3 = defaultObj.value.toString() || '0#0';
        },
        onchangeThietBiDr3: function() {
            let defaultObj = this.$refs.thietbiDr3Tree.ej2Instances;
            this.cbo_thietBi_DR3 = defaultObj.value.toString() || '0#0';
        },
        cellSelected: function(args) {
            this.cellIndex = args.cellIndex.cellIndex
        },
        napDsLoaiTbiDv2: async function() {
            try {
                let rs = await this.$root.context.get('web-cabman/dau-noi-splitter/ds-loai-tbi-dv2');
                this.dsLoaiThietBi_DV2 = rs.data;

                let isFirstLoad=this.cbo_loaiTbiId_DV2===null;
                this.cbo_loaiTbiId_DV2 = this.dsLoaiThietBi_DV2.length ? this.dsLoaiThietBi_DV2[0].LOAITBI_ID : 0;

                if(!isFirstLoad) 
                    this.napDsThietBiDv2()
                
            } catch (error) {
                this.$root.toastError("Có lỗi xảy ra khi tải danh sách loại thiết bị - Thiết bị - Đầu vào")
            }
        },
        napDsLoaiTbiDr2: async function() {
            try {
                let rs = await this.$root.context.get('web-cabman/dau-noi-splitter/ds-loai-tbi-dr2');
                this.dsLoaiThietBi_DR2 = rs.data;

                let isFirstLoad=this.cbo_loaiTbiId_DR2===null;
                this.cbo_loaiTbiId_DR2 = this.dsLoaiThietBi_DR2.length ? this.dsLoaiThietBi_DR2[0].LOAITBI_ID : 0;

                if(!isFirstLoad)
                    this.napDsThietBiDr2()
                
            } catch (error) {
                
            }
        },
        napDsDauNoi: async function() {
            try {
                let rs = await this.$root.context.get(`web-cabman/dau-noi-splitter/ds-ct-daunoi/${this.splitterId}`);
                this.dsDauNoi = rs.data;
            } catch (error) {
                
            }
        },
        napDsThietBiDv2: async function() {
            try {
                this.loading(true);
                let rs = await this.$root.context.post('web-cabman/dau-noi-splitter/ds-thietbi-dvr2', {
                    splitter_id: this.splitterId,
                    loaitb_id: this.cbo_loaiTbiId_DV2,
                    donvi_id: this.donvi_id
                });
                this.dsThietBi_DV2 = rs.data;
                this.cbo_thietBi_DV2 = this.dsThietBi_DV2.length ? this.dsThietBi_DV2[0].THIETBI_ID : 0;
            } catch (error) {
                this.$root.toastError("Có lỗi xảy ra khi tải danh sách thiết bị - Thiết bị - Đầu vào")
            } finally {
                this.loading(false);
            }
        },
        napDsThietBiDv3: async function() {
            try {
                this.loading(true);
                let rs = await this.$root.context.post('web-cabman/tracuu/dando/tracuu_dando/lay-ds-thietbi-con', {
                    doiTuongMapId: this.cbo_thietBi_DV2,
                    loaiTbiId: this.cbo_loaiTbiId_DV2,
                });
                this.dsThietBi_DV3 = rs.data;
                this.cbo_thietBi_DV3 = null;
                this.fieldsThietBiDv3 = {
                    dataSource: this.createTreeThietBi(this.dsThietBi_DV3),
                    value: 'KEY', 
                    text: 'TEN_TB', 
                    parentValue: 'PKEY', 
                    hasChildren: 'hasChild', 
                    imageUrl: 'image'
                }
            } catch (error) {
                this.$root.toastError("Có lỗi xảy ra khi tải danh sách thiết bị 3 - Thiết bị - Đầu vào")
            } finally {
                this.loading(false);
            }
        },
        // Tạo tree thiết bị 3
        createTreeThietBi(dsThietBi){
            let tree = []
            let pkeyArray = dsThietBi.map(item => (item['PKEY']))
            let keyArray = dsThietBi.map(item => (item['KEY']))
            dsThietBi.forEach(item => {
                //check item la parent node
                if(pkeyArray.includes(item['KEY'])){
                    //check PKEY khong co trong ds KEY thi xoa PKEY
                    if(!keyArray.includes(item['PKEY'])){
                        delete item.PKEY
                    }
                    tree.push( {...item, image:'/static/icons/icon16x16/'+item.MA_LTB + '.png', hasChild:true, expanded: true})
                }else{
                    tree.push({...item,image:'/static/icons/icon16x16/'+item.MA_LTB + '.png'})
                }
            })
            if (tree.length === 1){
                //only parent node
                tree[0].hasChild = true
                tree[0].expanded = true
                delete tree[0].PKEY
            }
            return tree
        },
        napDsCongTbiDv: async function() {
            this.chk_CSVCheck = [];
            this.dsCongSoiVao = [];

            try {
                this.loading(true);
                let data = this.cbo_thietBi_DV3.split("#");
                let rs = await this.$root.context.post('web-cabman/dau-noi-splitter/ds-cong-thietbi-dv', {
                    loaitb_id: data[0],
                    thietbi_id: data[1]
                });
                this.dsCongSoiVao = rs.data;
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        napDsThietBiDr2: async function() {
            try {
                this.loading(true);
                let rs = await this.$root.context.post('web-cabman/dau-noi-splitter/ds-thietbi-dvr2', {
                    splitter_id: this.splitterId,
                    loaitb_id: this.cbo_loaiTbiId_DR2,
                    donvi_id: this.donvi_id
                });
                this.dsThietBi_DR2 = rs.data;
                this.cbo_thietBi_DR2 = this.dsThietBi_DR2.length ? this.dsThietBi_DR2[0].THIETBI_ID : 0;
            } catch (error) {
                this.$root.toastError("Có lỗi xảy ra khi tải danh sách thiết bị - Thiết bị - Đầu ra")
            } finally {
                this.loading(false);
            }
        },
        napDsThietBiDr3: async function() {
            try {
                this.loading(true);
                let rs = await this.$root.context.post('web-cabman/tracuu/dando/tracuu_dando/lay-ds-thietbi-con', {
                    doiTuongMapId: this.cbo_thietBi_DR2,
                    loaiTbiId: this.cbo_loaiTbiId_DR2,
                });
                this.dsThietBi_DR3 = rs.data;
                this.cbo_thietBi_DR3 = null;
                this.fieldsThietBiDr3 = {
                    dataSource: this.createTreeThietBi(this.dsThietBi_DR3),
                    value: 'KEY', 
                    text: 'TEN_TB', 
                    parentValue: 'PKEY', 
                    hasChildren: 'hasChild', 
                    imageUrl: 'image'
                }
            } catch (error) {
                this.$root.toastError("Có lỗi xảy ra khi tải danh sách thiết bị 3 - Thiết bị - Đầu ra")
            } finally {
                this.loading(false);
            }
        },
        napDsCongTbiDr: async function() {
            this.chk_CSRCheck = [];
            this.dsCongSoiRa = [];

            try {
                this.loading(true);
                let data = this.cbo_thietBi_DR3.split("#");
                let rs = await this.$root.context.post('web-cabman/dau-noi-splitter/ds-cong-thietbi-dr', {
                    loaitb_id: data[0],
                    thietbi_id: data[1]
                });
                this.dsCongSoiRa = rs.data;
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        /**
         * ĐẤU NỐI ĐẦU VÀO
         */
        btnGanDauVao: function() {
            try {
                if(this.kiemTraGanDauVao(this.chk_CSVCheck, this.DVChecked)) {
                    this.ganDauVao(this.chk_CSVCheck, this.DVChecked);
                }
            } catch (error) {
                
            }
        },
        kiemTraGanDauVao: function(soivao, vitrivao) {
            if(soivao.length == 0) {
                this.$root.toastError("Vui lòng chọn cổng để gán");
                return false;
            }
            if(vitrivao.length == 0) {
                this.$root.toastError("Vui lòng chọn vị trí vào để gán");
                return false;
            }
            if(soivao.length > 1) {
                this.$root.toastError("Chỉ được phép đấu 1 cổng của thiết bị nguồn vào splitter");
                return false;
            }
            return true;
        },
        ganDauVao: async function(soivao, vitrivao) {
            try {
                this.loading(true);
                let data = this.cbo_thietBi_DV3.split("#");
                let rs = await this.$root.context.post('web-cabman/dau-noi-splitter/gan-dauvao', {
                    ds_soivaora: soivao.toString(),
                    ds_vitrivaora: vitrivao[0], //SP chỉ có 1 vị trí vào
                    splitter_id: this.splitterId,
                    donvi_id: this.donvi_id,
                    loaitb_id_dv2: this.cbo_loaiTbiId_DV2,
                    loaitb_id_dv3: data[0],
                    thietbi_id_dv2: this.cbo_thietBi_DV2,
                    thietbi_id_dv3: data[1]
                });
                if(rs.data.result) {
                    //Nếu đấu splitter vào port OLT thì chạy hàm gán toàn bộ Splitter vào port OLT
                    if(data[0] == 10) {
                        await this.autoGanSpVaoOlt();
                    }
                    this.$root.toastSuccess("Gán đầu vào thành công");
                    await this.napDsDauNoi();
                    await this.napDsCongTbiDv();
                }
            } catch (error) {
                console.log(error);
                this.$root.toastError("Có lỗi xảy ra khi gán đầu vào")
            } finally {
                this.loading(false);
            }
        },
        autoGanSpVaoOlt: async function() {
            try {
                let rs = await this.$root.context.post('web-cabman/dau-noi-splitter/lay-port-olt-theo-cap', {
                    ketcuoi_id: this.splitterId,
                    mat: 1,
                    vitri: 1,
                    kieu: 1
                });
                if(rs.data.result > 0) {
                    let portvl_id = rs.data.result;
                    let rsGanPort = await this.$root.context.post(`web-cabman/dau-noi-splitter/gan-port-olt-theo-splitter/${portvl_id}`);
                    if(rsGanPort.data.length) {
                        let tenCardGpon = rsGanPort.data[0].CARD_GP;
                        let slSP = rsGanPort.data[0].SL_SP;
                        this.$root.toastSuccess(`GPON: ${tenCardGpon} \r\nCấu hình xong: ${slSP} splitter`);
                    }
                }
                else {
                    this.$root.toastError("Splitter chưa thông về OLT");
                }
            } catch (error) {
                
            }
        },
        btnGoDauVao: function() {
            try {
                if(this.kiemTraGoDauVao(this.DVChecked)) {
                    this.goDauVao(this.DVChecked[0]);
                }
            } catch (error) {
                
            }
        },
        kiemTraGoDauVao: function(vitrivao) {
            if (vitrivao.length === 0){
                this.$root.toastError("Vui lòng chọn vị trí vào để gỡ")
                return false;
            }
            return true;
        },
        goDauVao: async function(vitrivao) {
            try {
                this.loading(true);
                let rs = await this.$root.context.post('web-cabman/dau-noi-splitter/go-dauvao', {
                    splitter_id: this.splitterId,
                    vitrivaora: vitrivao
                });
                if(rs.data.result) {
                    this.$root.toastSuccess("Gỡ đầu vào thành công")
                    await this.napDsDauNoi();
                    await this.napDsCongTbiDv();
                }
            } catch (error) {
                this.$root.toastError("Có lỗi xảy ra khi gỡ đầu vào")
            } finally {
                this.loading(false);
            }
        },
        /**
         * ĐẤU NỐI ĐẦU RA
         */
        btnGanDauRa: function() {
            try {
                if(this.kiemTraGanDauRa(this.chk_CSRCheck, this.DRChecked)) {
                    this.ganDauRa(this.chk_CSRCheck, this.DRChecked);
                }
            } catch (error) {
                
            }
        },
        kiemTraGanDauRa: function(soira, vitrira) {
            if (soira.length == 0){
                this.$root.toastError("Vui lòng chọn cổng để gán");
                return false;
            }
            if (vitrira.length == 0){
                this.$root.toastError("Vui lòng chọn vị trí ra để gán");
                return false;
            }
            if (soira.length != vitrira.length){
                this.$root.toastError("Số cổng và số vị trí ra không tương đồng");
                return false;
            }
            return true;
        },
        ganDauRa: async function(soira, vitrira) {
            try {
                this.loading(true);
                if(!this.cbo_thietBi_DV3) {
                    this.cbo_thietBi_DV3 = "0#0"
                }
                let daura = this.cbo_thietBi_DR3.split("#");
                let dauvao = this.cbo_thietBi_DV3.split("#");
                let rs = await this.$root.context.post('web-cabman/dau-noi-splitter/gan-daura', {
                    ds_soivaora: soira.toString(),
                    ds_vitrivaora: vitrira.toString(),
                    splitter_id: this.splitterId,
                    donvi_id: this.donvi_id,
                    loaitb_id_dr2: this.cbo_loaiTbiId_DR2,
                    loaitb_id_dr3: daura[0],
                    loaitb_id_dv3: dauvao[0],
                    thietbi_id_dr2: this.cbo_thietBi_DR2,
                    thietbi_id_dr3: daura[1],
                    thietbi_id_dv3: dauvao[1]
                });
                if(rs.data.result) {
                    this.$root.toastSuccess("Gán đầu ra thành công");
                    await this.napDsDauNoi();
                    await this.napDsCongTbiDr();
                }
            } catch (error) {
                this.$root.toastError(error.response.data.message_detail || "Có lỗi xảy ra khi gán đầu ra");
            } finally {
                this.loading(false);
            }
        },
        btnGoDauRa: async function() {
            try {
                if(this.kiemTraGoDauRa(this.DRChecked)) {
                    this.goDauRa(this.DRChecked);
                }
            } catch (error) {
                
            }
        },
        kiemTraGoDauRa: function(vitrira) {
            if (vitrira.length == 0){
                this.$root.toastError("Vui lòng chọn vị trí ra để gỡ")
                return false;
            }
            return true;
        },
        goDauRa: async function(vitrira) {
            try {
                this.loading(true);
                let rs = await this.$root.context.post('web-cabman/dau-noi-splitter/go-daura', {
                    splitter_id: this.splitterId,
                    vitrivaora: vitrira.toString()
                });
                if(rs.data.result) {
                    this.$root.toastSuccess("Gỡ đầu vào thành công")
                    await this.napDsDauNoi();
                    await this.napDsCongTbiDv();
                }
            } catch (error) {
                this.$root.toastError("Có lỗi xảy ra khi gỡ đầu vào")
            } finally {
                this.loading(false);
            }
        },
        btnDauNoiTuDong: function() {
            if (!this.cellIndex){
                return;
            }
            let min;
            // columns index : <4 DAUVAO, >4 DAURA
            if (this.cellIndex <= 4) { // in CAP_VAO, SOI_VAO, CONG_VAO, CHON_VAO
                this.DVChecked = [];
                this.chk_CSVCheck = [];
                min = Math.min(this.dsCongSoiVao.length, this.dsDauNoi.length);
                this.chk_CSVCheck = this.dsCongSoiVao.filter((item,index) => index < min).map(item => item.VITRI);
                for (let i = 0, j = 0; i < this.dsDauNoi.length && j < min; i++) {
                    if (this.dsDauNoi[i].SOI_VAO == null) {
                        this.DVChecked.push(this.dsDauNoi[i].CONG_VAO)
                        j++
                    }
                }
            }
            else {
                this.DRChecked = [];
                this.chk_CSRCheck = [];
                min = Math.min(this.dsCongSoiRa.length, this.dsDauNoi.length);
                this.chk_CSRCheck = this.dsCongSoiRa.filter((item,index) => index < min).map(item => item.VITRI);
                for (let i = 0, j = 0; i < this.dsDauNoi.length && j < min; i++) {
                    if (this.dsDauNoi[i].SOI_RA == null) {
                        this.DRChecked.push(this.dsDauNoi[i].CONG_RA)
                        j++
                    }
                }
            }
        },
        btnDauOLT: async function() {
            try {
                this.loading(true);
                await this.autoGanSpVaoOlt();
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        }
    },
    watch: {
        cbo_loaiTbiId_DV2: function(val) {
            this.chk_CSVCheck = [];

            this.dsThietBi_DV2 = [];
            this.dsThietBi_DV3 = [];
            this.fieldsThietBiDv3 = {
                dataSource: this.createTreeThietBi(this.dsThietBi_DV3),
                value: this.fieldsThietBiDv3.value, 
                text: this.fieldsThietBiDv3.text, 
                parentValue: this.fieldsThietBiDv3.parentValue, 
                hasChildren: this.fieldsThietBiDv3.hasChildren, 
                imageUrl: this.fieldsThietBiDv3.imageUrl
            }
            this.dsCongSoiVao = [];
            if(val) {
                this.napDsThietBiDv2()
            }
        },
        cbo_thietBi_DV2: function(val) {
            this.chk_CSVCheck = [];

            this.dsCongSoiVao = [];

            if(val) {
                this.napDsThietBiDv3();
            }
        },
        cbo_thietBi_DV3: function(val) {
            this.chk_CSVCheck = [];
            this.dsCongSoiVao = [];

            if(val) {
                this.napDsCongTbiDv();
            }
        },
        ///////////////
        cbo_loaiTbiId_DR2: function(val) {
            this.chk_CSRCheck = [];

            this.dsThietBi_DR2 = [];
            this.dsThietBi_DR3 = [];
            this.fieldsThietBiDr3 = {
                dataSource: this.createTreeThietBi(this.dsThietBi_DR3),
                value: this.fieldsThietBiDr3.value, 
                text: this.fieldsThietBiDr3.text, 
                parentValue: this.fieldsThietBiDr3.parentValue, 
                hasChildren: this.fieldsThietBiDr3.hasChildren, 
                imageUrl: this.fieldsThietBiDr3.imageUrl, 
            }
            this.dsCongSoiRa = [];

            if(val) {
                this.napDsThietBiDr2();
            }
        },
        cbo_thietBi_DR2: function(val) {
            this.chk_CSRCheck = [];
            this.dsCongSoiRa = [];

            if(val) {
                this.napDsThietBiDr3();
            }
        },
        cbo_thietBi_DR3: function(val) {
            this.chk_CSRCheck = [];
            this.dsCongSoiRa = [];

            if(val) {
                this.napDsCongTbiDr();
            }
        },
        DVChecked: function(val) {

        },
        DRChecked: function(val) {

        }
    }
}
</script>
