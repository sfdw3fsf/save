<template src='./frmTaoSerial.html'></template>
<script>
import moment from 'moment';
import Vue from "vue";
import breadcrumb from '@/components/breadcrumb'
import frmTaoSerialAPI from  '../api/frmTaoSerialAPI'
import select2 from '@/components/Select2.vue'
import { Freeze, Group, Page, Filter, Sort, Resize, ForeignKey} from "@syncfusion/ej2-vue-grids";
import { L10n } from '@syncfusion/ej2-base'
import XLSX from "xlsx";
import {previewPrint} from "@/utils/util"

L10n.load({
    'vi-VN': {
        grid: {
          EmptyRecord: 'Không có bản ghi nào để hiển thị'
        }
    }
})

export default {

  components: { XLSX, breadcrumb, appSelect2: select2 },
  name: "frmTaoSerial",
  mounted() {
  },
  provide: {
    grid: [ Freeze, Group, Page,Filter,Sort,Resize, ForeignKey ]
  },
  computed: {

    
  },
  data() {
    return {
        dtChitietCT: [],
        dt: [],
        dsCT_ChungTu: [],
        imgs: [],

        kieu: 0,
        sinh_serial_quytac: false,
        kytu_dau: '',
        tinh_hientai: '',
        vctct_id: '',
        sl_goc: 0,
        PageCounter: 0,

        gridThietBi: [],
        gridThietBi_Columns: [
            {fieldName: '', headerText: '', textAlign: 'Center', type:'checkbox', allowFiltering: true, width: '70', visible: true},
            {fieldName: 'LOHANG', headerText: 'Lô hàng', textAlign: 'Left', allowFiltering: true, visible: true},
            {fieldName: 'MA_VT', headerText: 'Mã vật tư', textAlign: 'Left', allowFiltering: true, visible: true},
            {fieldName: 'SERIAL', headerText: 'Serial', textAlign: 'Left', allowFiltering: true, visible: true},
            {fieldName: 'DVI_TINH', headerText: 'DVI_TINH', textAlign: 'Left', allowFiltering: true, visible: true},
            {fieldName: 'CotXoa', headerText: 'Xóa', textAlign: 'Center', allowFiltering: true, template: function () {
            return {
                template: Vue.component('CotXoaTemplate', {
                    template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" @click="CotXoa_Click">
                                  <span class="-ap icon-close"></span>
                              </button>`,
                    data: function () { return { data: {} }; },
                    methods:{
                      async CotXoa_Click(){
                        await this.$parent.$parent.$parent.CotXoa_Click(this.data)
                      }

                    }
                })
            }
        }, width: '70', visible: true},
            ],

        params: {
            p_txtSoLuongTBi: 0,
            p_txtSoLuongTem: 3,
            p_txtChieuNgang: 528,
            p_txtChieuDoc: 104,

        },

        Visible: {
            tsbtnTaoSerial: true
        },
        Enabled: {
            txtSoLuongTBi: true,
            gridViewThietBi: {
                CotXoa: true,
            }
        },
        CODE128A: 29,
        gridThietBiPage : {
            page: 0,
            maxSize: 10,
            totalElement: 0,
            totalPages: 0,
            sort: null,
            propertiesSort: null,
            currentRow: 0
        },
    };
  },
  methods: {
    
    // chuyển Date thành type string
     f_DateToString(value,format='DD/MM/YYYY'){
      return moment(value).format(format)
    },
    f_StringToDate(value,format='DD/MM/YYYY'){
      return moment(value,format).toDate();
    },
    gridThietBiPageHandler(e) {
      this.gridThietBiPage.page = e.pageIndex
      this.gridThietBiPage.maxSize = e.pageSize
    },
    async frmTaoSerial_Load(){
        let ds = []
        this.gridThietBi = []
        await frmTaoSerialAPI.getDSThamSoMucDich(this.axios, {ma: 'SINH_SERIAL_QUYTAC'})
            .then(
                (response) => {
                  ds = response.data.data || []
                }
            )
            .catch(function() {
                
            })
            .finally(function() {

            })
        if (ds.length){
            this.sinh_serial_quytac = true
            this.kytu_dau = ds[0].tenTS.toString()
        }
        if (this.kieu == 0){
            
            await frmTaoSerialAPI.getChiTietChungTu(this.axios, {chungTuId: this.vctct_id})
            .then(
                (response) => {
                  this.dtChitietCT = response.data.data || []
                }
            )
            .catch(function() {})
            .finally(function() {})

            if (this.dtChitietCT.length){
                this.params.p_txtSoLuongTBi = this.dtChitietCT[0]["SOLUONG"]
            }
            let soluong = parseInt(this.params.p_txtSoLuongTBi)
            let soluong_tt = this.dsCT_ChungTu.length
            this.sl_goc = soluong_tt
        }
        if(this.kieu==1){
            this.Visible.tsbtnTaoSerial = false
            this.params.p_txtSoLuongTBi = this.dsCT_ChungTu.length
            this.Enabled.txtSoLuongTBi = false
            this.gridThietBi_Columns.find(a=>a.fieldName == 'CotXoa').visible = false

            this.gridThietBi = this.dsCT_ChungTu
            let soluong = parseInt(this.params.p_txtSoLuongTBi)
            let soluong_tt = this.dsCT_ChungTu.length
            this.sl_goc = soluong_tt
        }
    },
    async tsbtnTaoSerial_Click(){
        await this.TaoSerial(this.kieu)
    },
    async TaoSerial(kieu){
        // try {
            if (this.kieu == 0){
                let soluong = parseInt(this.params.p_txtSoLuongTBi)
                let soluong_tt = parseInt(this.dtChitietCT[0]["SOLUONG"])
                if (soluong > soluong_tt){
                    this.$toast.error("Bạn không được chọn số lượng thiết bị vượt quá số lượng trong lô hàng!")
                    return
                }
                this.dt = []
                for (let i = 0; i < soluong; i++){
                    let r = {}
                    r["LOHANG"] = this.dtChitietCT[0]["LOHANG"].toString()
                    r["MA_VT"] = this.dtChitietCT[0]["MA_VT"].toString()
                    r["TEN_VT"] = this.dtChitietCT[0]["TEN_VT"].toString()
                    r["DVI_TINH"] = this.dtChitietCT[0]["DVI_TINH"].toString()
                    let getKey = ''
                    await frmTaoSerialAPI.getKeys(this.axios, {
                            "keyName" : "SERIAL",
                            "numBlockSize" : 1,
                            "numRetry":  10
                        })
                    .then(
                        (response) => {
                        getKey = response.data.data || []
                        }
                    )
                    .catch(function() {})
                    .finally(function() {})

                    if (this.sinh_serial_quytac){
                        r["SERIAL"] = this.kytu_dau + getKey.toString()//("000000000000");
                    }
                    else{
                        r["SERIAL"] = getKey.toString()//("000000000000");
                    }
                        
                    r["SOLUONG"] = 1;
                    r["DONGIA"] = this.dtChitietCT[0]["DONGIA"];
                    if (this.$auth.getMaTinh() == "HCM" || this.$auth.getMaTinh() == "NET"){
                        r["MATHUNG"] = "";
                    }
                        
                    this.dt.push(r);
                }
                this.gridThietBi = this.dt
                await this.TaoCode()
            }
            if(kieu==1){
                let soluong = parseInt(this.params.p_txtSoLuongTBi)
                if (soluong > this.sl_goc){
                    this.$toast.error("Bạn không được chọn số lượng thiết bị vượt quá số lượng trong lô hàng!")
                    return
                }
                this.dt = []
                if( soluong< this.sl_goc){
                    for (let i = 0; i < soluong; i++){
                       let r = {} 
                       if (this.dsCT_ChungTu[i]["ISCHECK"] == "1"){
                           r["LOHANG"] = this.dsCT_ChungTu[i]["LOHANG"].toString();
                            r["MA_VT"] = this.dsCT_ChungTu[i]["MA_VT"].toString();
                            r["TEN_VT"] = this.dsCT_ChungTu[i]["TEN_VT"].toString();
                            r["DVI_TINH"] = this.dsCT_ChungTu[i]["DVI_TINH"].toString();
                            r["SERIAL"] = this.dsCT_ChungTu[i]["SERIAL"].toString();
                            r["SOLUONG"] = 1;
                            r["DONGIA"] = this.dsCT_ChungTu[i]["DONGIA"];
                            this.dt.push(r)
                       }
                    }
                }
                if( soluong == this.sl_goc){
                    for (let i = 0; i < soluong; i++){
                        var r = {}
                        r["LOHANG"] = this.dsCT_ChungTu[i]["LOHANG"].toString();
                        r["MA_VT"] = this.dsCT_ChungTu[i]["MA_VT"].toString();
                        r["TEN_VT"] = this.dsCT_ChungTu[i]["TEN_VT"].toString();
                        r["DVI_TINH"] = this.dsCT_ChungTu[i]["DVI_TINH"].toString();
                        r["SERIAL"] = this.dsCT_ChungTu[i]["SERIAL"].toString();
                        r["SOLUONG"] = 1;
                        r["DONGIA"] = this.dsCT_ChungTu[i]["DONGIA"];
                        this.dt.push(r)
                    }
                }
                if(this.dt.length == 0){
                    this.$toast.error("Không có dữ liệu để tạo serial")
                    return
                }
                this.gridThietBi = this.dsCT_ChungTu
                this.TaoCode()

            }
        // } catch (error) {
            
        // }
    },
    async btnXuatExel_Click(){
        let data1 = XLSX.utils.json_to_sheet(this.dt);
        let wb = XLSX.utils.book_new(); // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, data1, "sheet1"); // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, "Thietbi.xlsx");
    },
    async TaoCode(){
        // lam sau
        // let w = parseInt(this.params.p_txtChieuNgang)
        // let h = parseInt(this.params.p_txtChieuDoc)
        // for (let i = 0; i < this.dt.length; i++){
        //     let img = {}
        //     img.Name = this.dt[i]["SERIAL"].toString();
        //     img.value = this.dt[i]["SERIAL"].toString();
        //     img.Image = await this.SinhBarcode(this.dt[i]["SERIAL"].ToString(), w, h, this.CODE128A);
        //     imgs.Add(img);
        // }
    },
    async toolStripButton1_Click(){
        var soluongTem = 0
        try {
            soluongTem = parseInt(this.params.p_txtSoLuongTem)
        } catch (error) {
            this.$toast.error("Hãy nhập số lượng tem")
            this.$refs.txtSoLuongTem.focus()
            return
        }
        if (this.kieu == 1){
            await this.TaoSerial(1)
        }
        var dsbarcode = []
        var dtbarcode = []
        var data = this.gridThietBi
        for (let dr of this.$refs.gridViewThietBi.getSelectedRecords()){
            for (let i = 0; i < soluongTem; i++){
                dtbarcode.push({serial: dr["SERIAL"]})
            }
        }
        if (dtbarcode.length == 0){
            this.$toast.error("Hãy tick chọn các serial cần in")
            return
        }
        await this.frmXemBaoCao(dtbarcode)

    },
    async frmXemBaoCao(dtbarcode){
      this.$root.showLoading(true);
      var res = ''
      await frmTaoSerialAPI.xemBaoCao(this.axios, dtbarcode)
            .then(
                (response) => {
                  res = response
                }
            )
            .catch(function() {
                
            })
            .finally(function() {

            })

      this.$root.showLoading(false);
      res ? previewPrint(res.data) : ''
    },
    async pd_BeginPrint(){
        this.PageCounter = 0
    },
    PrintPage(){
        // Lam sau
    },
    SinhBarcode(value, w, h, type){
        // Lam sau
    },
    async toolStripButton2_Click(){
        var dtbarcode = []
        this.$refs.gridViewThietBi.getSelectedRecords().forEach(item => {
            dtbarcode.push({serial: item["SERIAL"]})
        });
        
        await frmTaoSerialAPI.XuatFileZip(this.axios, dtbarcode)
            .then((response) => {
                var blob = new Blob([response.data], { type: "application/json" });
                var url = window.URL.createObjectURL(blob);
                var a = document.createElement("a");
                a.href = url;
                a.download = 'barcode.zip';
                a.click();
                document.body.removeChild(a);
                }
            )
            .catch(function() {
                
            })
            .finally(function() {

            })
    },
    repositoryItemCheckEdit1_CheckedChanged(){
        if (this.kieu == 0){
            return
        }
        let ds_dem = this.gridThietBi
        var q = this.$refs.gridViewThietBi.getSelectedRecords()
        this.params.p_txtSoLuongTBi = q.length != 0? q.length : this.sl_goc
    },

    // web bổ sung, C# ko thấy (quy)
    async CotXoa_Click(row){
        this.gridThietBi.splice(row.index, 1)
    },



    async Close(){
        this.$parent.hide()
    },
    async ShowDialog(){
        await this.frmTaoSerial_Load()
        this.$parent.show()
    }
  },
created: async function() {
    
  },
  watch: {
    
  },
  destroyed () {
  }
};
</script>
<style>
</style>