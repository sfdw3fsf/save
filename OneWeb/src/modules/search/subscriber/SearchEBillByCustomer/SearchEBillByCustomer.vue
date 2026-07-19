<template src="./SearchEBillByCustomer.html"></template>
<style src="./SearchEBillByCustomer.scss"></style>
<script>
import SearchEBillByCustomerAPI from './SearchEBillByCustomerAPI'
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import EventBus from '@/utils/eventBus'
import Vue from 'vue'
import popupTimKiem from '@/modules/search/subscriber/SearchEBillByCustomerAdmin/Popup/TimKiemKhachHang.vue'
// import VuePdfApp from 'vue-pdf-app'
// import 'vue-pdf-app/dist/icons/main.css'

//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
//
export default {
    props: [ 'isPopup','mathuebao','ma_gd'],
    components: { breadcrumb, popupTimKiem},
    name: 'SearchEBillByCustomer',
    async created () {
        if (this.ma_gd) this.txtmagd = this.ma_gd
        EventBus.$on('onDownloadInv', this.onDownloadInvHandler);
        EventBus.$on('onDownloadPdf', this.onDownloadPdfHandler);
        EventBus.$on('onDownloadXml', this.onDownloadXmlHandler);
        EventBus.$on('onViewInv', this.onViewInvHandler);
    },

    destroyed () {
        EventBus.$off('onDownloadInv', this.onDownloadInvHandler);
        EventBus.$off('onDownloadPdf', this.onDownloadPdfHandler);
        EventBus.$off('onDownloadXml', this.onDownloadXmlHandler);
        EventBus.$off('onViewInv', this.onViewInvHandler);
    },
   async mounted () {

    console.log(this.$auth.getNhanVienID());
    await SearchEBillByCustomerAPI.lay_tham_so(this.axios, {
                "ma_ts": "VTT_HDDT_HOPDONG"
            }).then((response) => {
            response.data.data = this.checkvtt;
     });
    //  console.log(this.checkvtt);
    if(this.checkvtt != null || this.checkvtt != ""){
            var input2 = {};
            input2.ma_ts = "VTT_HDDT_HOPDONG";
            input2.nhanvien_id = this.$auth.getNhanVienID();
            await SearchEBillByCustomerAPI.check_vtt(this.axios, input2).then((response) => {
            this.thamso = response.data.data;
          });
        }

     if (this.ma_gd) this.btnSearchClicked();
  },
  data () {
    return {
      header: {
        title: 'Tra cứu hóa đơn điện tử khách hàng',
        list: [
          {
            name: 'Lập hợp đồng',
            link: { name: 'Ui.cards' }
          },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          },
        ]
      },

      // Khai báo in
      listOfParamNameValues: [],
      pdf: "base64encode",
      path: 'BIEUMAU/HOPDONGPHULUC/RP_BC_BSS_42800',
      title: 'Báo cáo',
      phanvungId: Number.parseInt(this.$root.token.getPhanVungID()),
      ThangNam: moment().format("MM/YYYY"),
      Chosefkey: "",
      chosetoken: "",
      Choseshd: "",
      ngayin: moment().format("DD") + " tháng " + moment().format("MM") + " năm " + moment().format("YYYY"),
      // end

      checkmagd: true,
      checkfkey: false,
      checktoken: false,
      magd: false,
      fkey: true,
      token: true,
      txtmagd: "",
      txtfkey: "",
      txttoken: "",
      thamso: 0,
      data: "",
      TuNgay: "",
      DenNgay: "",
      checkvtt: "",
      checkTuNgay: false,
      checkDenNgay: false,
      ketquatimkiem: {                
        list: [],
        header:[],
        value: {},
        isEnabled: true,
      },
      downloadInvTemplate: function () {
                return { template : Vue.component('columnTemplate', {
                    template: `<div><button class="btn btn-primary lh14 pad3" @click="onDownloadInv" title="Tải file .inv">
                                    <span class="-ap icon-download"></span>
                                </button></div>`,
                    data () {
                        return { data: {} };
                    },
                    methods: {
                        onDownloadInv() {
                            EventBus.$emit('onDownloadInv', this.data);
                        }
                    }})
                }
            },
            downloadPdfTemplate: function () {
                return { template : Vue.component('columnTemplate', {
                    template: `<div><button class="btn btn-primary lh14 pad3" @click="onDownloadPdf" title="Tải file .pdf">
                                    <span class="-ap icon-download"></span>
                                </button></div>`,
                    data () {
                        return { data: {} };
                    },
                    methods: {
                        onDownloadPdf() {
                            EventBus.$emit('onDownloadPdf', this.data);
                        }
                    }})
                }
            },
            viewInvTemplate: function () {
                return { template : Vue.component('columnTemplate', {
                    template: `<div><a href="javascript: void(0)" @click="onViewInv" title="Xem chi tiết hóa đơn điện tử"><span></span>Xem chi tiết</a></div>`,
                    data () {
                        return { data: {} }; 
                    },
                    methods: {
                        onViewInv() {
                            EventBus.$emit('onViewInv', this.data);
                        }
                    }})
                }
            },
            downloadXmlTemplate: function () {
                return { template : Vue.component('columnTemplate', {
                    template: `<div><button class="btn btn-primary lh14 pad3" @click="onDownloadXml" title="Tải file .xml">
                                    <span class="-ap icon-download"></span>
                                </button></div>`,                        
                    data () { 
                        return { data: {} }; 
                    },
                    methods: {
                        onDownloadXml() {                  
                            EventBus.$emit('onDownloadXml', this.data);
                        }
                    }})
                }
            },
      listbox: {
        loai_hinh:{
          list:[],
          value: 62,
          isEnabled:false,
        },
        ketquatimkiem3: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
        },
      },
       config: {
                dateConfig: {
                    value: new Date(),
                    format: "dd/MM/yyyy",
                    max: new Date()
                }
            },
    }
  },
  computed: {
    cp_ketquatimkiem: function () {
        var tmp = this.ketquatimkiem
        if (tmp.list && tmp.list.length > 0) {
            for (var i = 0; i < tmp.list.length; i++) {
                var keys = Object.keys(tmp.list[i])
                for (var j = 0; j < keys.length; j++) {
                    if (typeof tmp.list[i][keys[j]] == 'object') {
                        var value = Object.keys(tmp.list[i][keys[j]]).map(k => tmp.list[i][keys[j]][k])
                        tmp.list[i][keys[j]] = value[0]
                    }                        
                }
            }            
        } 
        return tmp
    },
  },   
  methods: {
    // click lưới
    selectedRowChanged(obj){
        console.log('selectedRowChanged', obj)
        //this.chosetoken = obj.fkey['#invToken'];
        this.chosetoken = obj.invToken?obj.invToken.toString():''
      if(this.fkey == false){
        this.Chosefkey = this.txtfkey;
      } else {        
        this.Chosefkey = obj.fkey['#text'];        
      }
      this.Choseshd = obj.invNum
    },

    // Chức năng in
        //   getReport (path) {
        //   this.pdf = null
        //   this.html = null
        //     this.listOfParamNameValues = []
        //     SearchEBillByCustomerAPI.GET_BI_PARAMS(this.axios, {}).then(response => {
        //     if (response.data.listOfParamNameValues) {
        //         this.listOfParamNameValues =
        //           response.data.listOfParamNameValues.item
        //         this.listOfParamNameValues.forEach((v, index) => {
        //         let paramName = v.name
        //         let paramLabel = v.label
        //         let paramType = v.UIType
        //           console.log('ParamName: ' + paramName + ', ParamType:' + paramType + ', ParamLable:' + paramLabel)
        //         if (paramName.includes('p_phanvung_id')) {
        //           this.$set(
        //               this.listOfParamNameValues[index],
        //               'value',
        //               this.phanvungId.toString()
        //           )
        //         } else if (paramName.includes('fkey')) {
        //             this.$set(
        //               this.listOfParamNameValues[index],
        //               'value',
        //               // "03HPG2022000927634"
        //               this.Chosefkey.toString()
        //           )
        //           }
        //           else if (paramName.includes('thangnam')) {
        //             this.$set(
        //               this.listOfParamNameValues[index],
        //               'value',
        //               this.ThangNam.toString()
        //           )
        //           }
        //           else if (paramName.includes('ngayin')) {
        //             this.$set(
        //               this.listOfParamNameValues[index],
        //               'value',
        //               this.ngayin.toString()
        //           )
        //           }
        //         // TODO check tiep param
        //       })
        //        this.viewReport('pdf')
        //     }
        //   })
        // },
        // async viewReport (type) {
        //     this.type = type
        //     let request = {
        //       report: this.path,
        //       type: type,
        //     //   file_name:
        //     //       this.convertToSlug(this.title) + '_' + this.hdkh_id + '.' + this.extension[this.type],
        //       items: this.listOfParamNameValues
        //     }
        //     console.log(request);
        //     await SearchEBillByCustomerAPI.RUN(this.axios, request)
        //         .then((response) => {
        //           console.log(response.data)
        //         if (response.data) {
        //           //  console.log(this.pdf);
        //           this.pdf = this.base64ToArrayBuffer(response.data)
        //         //  console.log(this.pdf);
        //         } else {
        //             this.$root.$toast.warning('Không lấy được dữ liệu báo cáo')
        //         }
        //       }).catch(error => {
        //           console.log(error)
        //           this.$root.$toast.error('Xảy ra lỗi khi lấy dữ liệu báo cáo' + error.data.message)
        //       })
        //   },
        //   base64ToArrayBuffer (base64) {
        //     let binaryString = window.atob(base64)
        //     let len = binaryString.length
        //     let bytes = new Uint8Array(len)
        //     for (let i = 0; i < len; i++) {
        //       bytes[i] = binaryString.charCodeAt(i)
        //     }
        //     return bytes.buffer
        //   },
        //   async openPdf (pdfApp) {
        //     if (pdfApp.pdfViewer) {
        //       await pdfApp.pdfViewer.pagesPromise
        //         pdfApp.pdfViewer.currentScale = 1
        //     }
        //   },
    // In biên nhận
    // onShowModal () {
    //    this.getReport(this.path)
 	  //    this.viewReport('pdf')
    // 	 this.$refs.dialogPreview.show()
    // },

    // mở popup tìm kiếm
    PopUpTimKiem: function () {
      this.$refs.timkiemkhachhang.show();
      // this.$bvModal.show('timkiemkhachhang')
    },
    // Download file Pdf
    async onDownloadPdfHandler(item) {
            let rs = await this.postDownloadInvPDFFkeyNoPay(item);
           // console.log(rs);
            if (rs !== null) {
                let content = Buffer.from(rs, 'base64');
                console.log(content);
                var FileSaver = require("file-saver");
                var file = new File([content], this.Choseshd, {
                    type: "application/pdf"
                });
                FileSaver.saveAs(file);
            }
        },
    async postDownloadInvPDFFkeyNoPay(item) {
        if (this.chosetoken == "" || this.chosetoken== null){
            this.chosetoken =item.pattern+";"+item.serial+";"+Number(item.invNum)
            console.log("data token 1 2",this.chosetoken)
        }
            try {
                this.loading(true);
                let apiBody = {
                    token: item && item.invToken ? item.invToken : this.chosetoken
                };
                if (this.thamso == 1){
                        let response = await SearchEBillByCustomerAPI.postDownloadInvPDFFkeyNoPay_VTT(this.axios, apiBody);
                    if (response.data.errorCode == 0) {
                        return response.data.data;
                    } else return null;
                }
                else {
                    let response = await SearchEBillByCustomerAPI.postDownloadInvPDFNoPay(this.axios, apiBody);
                    if (response.data.errorCode == 0) {
                        return response.data.data;
                    } else return null;
                }
            }
            catch (error) {
                console.log(error)
                if (error.data.message)
                    this.$toast.error(error.data.message);
                else this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
                return null;
            }
            finally {
                this.loading(false);
            }
        },

    // download inv
    async onDownloadInvHandler(item) {
            let rs = await this.postDownloadInvFkeyNoPayXml(item.fkey);
            if (rs !== null) {
                let content = rs;
                var FileSaver = require("file-saver");
                var file = new File([content], this.Choseshd + '.inv', {
                    type: "application/inv"
                });
                FileSaver.saveAs(file);
            }
        },
     async postDownloadInvFkeyNoPayXml(fkey) {
            try {
                this.loading(true);
                let apiBody = {
                    fkey: fkey ? fkey : this.txtfkey
                };
                let response = await SearchEBillByCustomerAPI.postDownloadInvFkeyNoPayXml(this.axios, apiBody);
                if (response.data.errorCode == 0) {
                    return response.data.data;
                } else return null;
            }
            catch (error) {
                console.log(error)
                if (error.data.message)
                    this.$toast.error(error.data.message);
                else this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
                return null;
            }
            finally {
                this.loading(false);
            }
        },
      // view hóa đơn
        async onViewInvHandler(item) {
            let rs = await this.getInvViewNoPay(item);
            if (rs !== null) {
                var newTab = window.open('', '_blank');
                newTab.document.body.innerHTML = rs;
            }
        },
        async getInvViewNoPay(item) {
            if (this.chosetoken == "" || this.chosetoken== null){
            this.chosetoken =item.pattern+";"+item.serial+";"+Number(item.invNum)
        }
            try {
                this.loading(true);
                let apiBody = {
                    invToken: item && item.invToken ? item.invToken : this.chosetoken
                };
               // console.log(apiBody);
               if(this.thamso == 1){
                let response = await SearchEBillByCustomerAPI.getInvViewNoPay_VTT(this.axios, apiBody);
                if (response.data.errorCode == 0) {
                    return response.data.data;
                } else return null;
               }
               else
               {
                let response = await SearchEBillByCustomerAPI.getInvViewNoPay(this.axios, apiBody);
                if (response.data.errorCode == 0) {
                    return response.data.data;
                } else return null;
            }
            }
            catch (error) {
                console.log(error)
                if (error.data.message)
                    this.$toast.error(error.data.message);
                else this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
                return null;
            }
            finally {
                this.loading(false);
            }
        },

        // dowload xml
        async onDownloadXmlHandler(item) {
            let rs = await this.postDownloadXml(item.fkey);
            if (rs !== null) {
                let content = rs;
                var FileSaver = require("file-saver");
                var file = new File([content], this.Choseshd + '.xml', {
                    type: "application/xml"
                });
                FileSaver.saveAs(file);
            }
        },
     async postDownloadXml(fkey) {
            try {
                this.loading(true);
                // var apiBody = {};
                // if(this.fkey == ""){
                //   apiBody.fkey = this.ketquatimkiem.list.fkey['#text']
                // }
                // else apiBody.fkey = this.fkey
                let apiBody = {
                    fkey: fkey ? fkey : this.txtfkey
                };
                let response = await SearchEBillByCustomerAPI.postDownloadInvFkeyNoPayXml(this.axios, apiBody);
                if (response.data.errorCode == 0) {
                    return response.data.data;
                } else return null;
            }
            catch (error) {
                console.log(error)
                if (error.data.message)
                    this.$toast.error(error.data.message);
                else this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
                return null;
            }
            finally {
                this.loading(false);
            }
        },
    btnSearchClicked() {
      this.Clear();
      // console.log(this.magd);
      // console.log(this.fkey);
      // console.log(this.token);
      if(this.magd == false) {
        if(this.txtmagd == "" || this.txtmagd == null){
          this.$toast.error('Bạn chưa nhập mã giao dịch')
        }
        else {
            // console.log(this.thamso);
            //  this.Clear();
            var pdata = {};
            pdata.cusCode = this.txtmagd.trim();
            if(this.checkTuNgay == true){
                pdata.fromDate = moment(this.TuNgay).format("DD/MM/YYYY");
            }
            else pdata.fromDate = "";
            if(this.checkDenNgay == true){
                pdata.toDate = moment(this.DenNgay).format("DD/MM/YYYY");
            }
            else pdata.toDate = "";
            // SearchEBillByCustomerAPI.lay_tham_so(this.axios, { 
            //       "ma_ts": "VTT_HDDT_HOPDONG"
            //   }).then((response) => {
            //   response.data.data = this.checkvtt;
            // });
            // console.log(this.thamso);
            if(this.thamso == 1){
                SearchEBillByCustomerAPI.tracuu_ma_gd_vtt(this.axios, pdata).then((response) => {
                    if(response.data.errorCode != 0 || response.data.data == "<Data></Data>"){
                        this.$toast.error('Không tìm thấy thông tin hóa đơn')
                    }
                    else {
                    if(response && response.data) {
                        const xmlDoc = this.parseXmlDoc(response.data.data)
                        const Json = this.xmlToJson(xmlDoc);
                        if(response.data.data.length < 550 ){
                            this.convert([Json.Data.Item]);
                            this.ketquatimkiem.list = [Json.Data.Item];
                        }
                        else {
                        this.convert(Json.Data.Item);
                        this.ketquatimkiem.list = Json.Data.Item;
                        }
                    }
                    }
                });
            }
            else {
                SearchEBillByCustomerAPI.tracuu_ma_gd(this.axios, pdata).then((response) => {
                    if(response.data.errorCode != 0 || response.data.data == "<Data></Data>"){
                        this.$toast.error('Không tìm thấy thông tin hóa đơn')
                    }
                    else {
                        if(response && response.data) {
                            const xmlDoc = this.parseXmlDoc(response.data.data)
                            const Json = this.xmlToJson(xmlDoc);
                            if(response.data.data.length < 550 ){
                                this.convert([Json.Data.Item]);
                                this.ketquatimkiem.list = [Json.Data.Item];
                            }
                            else {
                            this.convert(Json.Data.Item);
                            this.ketquatimkiem.list = Json.Data.Item;
                            }
                        }
                    }
                });
            }
        }
      }
      if(this.fkey == false){
        if(this.txtfkey == "" || this.txtfkey == null){
          this.$toast.error('Bạn chưa nhập fkey')
        }
        else {
        var pdata = {};
        pdata.key = this.txtfkey.trim();
        if(this.checkTuNgay == true){
            pdata.fromDate = this.format_date(moment(this.TuNgay).format("DD/MM/YYYY"));
        }
        else pdata.fromDate = "";
        if(this.checkDenNgay == true){
            pdata.toDate = this.format_date(moment(this.DenNgay).format("DD/MM/YYYY"));
        }
        else pdata.toDate = "";
        console.log(pdata);
        if(this.thamso == 1){
            SearchEBillByCustomerAPI.tra_cuu_fkey_vtt(this.axios, pdata).then((response) => {
          if(response.data.errorCode != 0 || response.data.data == "<Data></Data>"){
              this.$toast.error('Không tìm thấy thông tin hóa đơn')
          }
          else {
          if(response && response.data) {
                const xmlDoc = this.parseXmlDoc(response.data.data)
                const Json = this.xmlToJson(xmlDoc);
                if(response.data.data.length < 550 ){
                    this.convert([Json.Data.Item]);
                    this.ketquatimkiem.list = [Json.Data.Item];
                }
                else {
                this.convert(Json.Data.Item);
                this.ketquatimkiem.list = Json.Data.Item;
                }
            }
          }
        });
        }
        else {
        SearchEBillByCustomerAPI.tra_cuu_fkey(this.axios, pdata).then((response) => {
          if(response.data.errorCode != 0 || response.data.data == "<Data></Data>"){
              this.$toast.error('Không tìm thấy thông tin hóa đơn')
          }
          else {
          if(response && response.data) {
                const xmlDoc = this.parseXmlDoc(response.data.data)
                const Json = this.xmlToJson(xmlDoc);
                if(response.data.data.length < 550 ){
                    this.convert([Json.Data.Item]);
                    this.ketquatimkiem.list = [Json.Data.Item];
                }
                else {
                this.convert(Json.Data.Item);
                this.ketquatimkiem.list = Json.Data.Item;
                }
            }
          }
        });
         }
      }
      }
      if(this.token == false){
        if(this.txttoken == "" || this.txttoken == null){
          this.$toast.error('Bạn chưa nhập token')
        }
        else {
        var pdata = {};
        pdata.invToken = this.txttoken.trim();
        console.log(pdata);
        SearchEBillByCustomerAPI.tracuu_token(this.axios, pdata).then((response) => {
          if(response.data.errorCode == 2){
              this.$toast.error('Chuỗi token không đúng định dạng')
          }
          else if(response.data.errorCode != 0 || response.data.data == "<Data></Data>"){
              this.$toast.error('Không tìm thấy thông tin hóa đơn')
          }
          else {
            if(response && response.data) {
                const xmlDoc = this.parseXmlDoc(response.data.data)
                const Json = this.xmlToJson(xmlDoc);
                this.convert(Json);  
                console.log(Json.HDon);
                var hdon = Json.HDon ? Json.HDon : null
                if (hdon) {
                    this.txtmagd = hdon.DLHDon.NDHDon.NMua.MKHang['#text']
                    var pdata = {};
                    pdata.cusCode = this.txtmagd.trim();
                    if(this.checkTuNgay == true){
                        pdata.fromDate = moment(this.TuNgay).format("DD/MM/YYYY");
                    }
                    else pdata.fromDate = "";
                    if(this.checkDenNgay == true){
                        pdata.toDate = moment(this.DenNgay).format("DD/MM/YYYY");
                    }
                    else pdata.toDate = "";
                    // SearchEBillByCustomerAPI.lay_tham_so(this.axios, { 
                    //       "ma_ts": "VTT_HDDT_HOPDONG"
                    //   }).then((response) => {
                    //   response.data.data = this.checkvtt;
                    // });
                    // console.log(this.thamso);
                    if(this.thamso == 1){
                        SearchEBillByCustomerAPI.tracuu_ma_gd_vtt(this.axios, pdata).then((response) => {
                            if(response.data.errorCode != 0 || response.data.data == "<Data></Data>"){
                                this.$toast.error('Không tìm thấy thông tin hóa đơn')
                            }
                            else {
                            if(response && response.data) {
                                const xmlDoc = this.parseXmlDoc(response.data.data)
                                const Json = this.xmlToJson(xmlDoc);
                                if(response.data.data.length < 550 ){
                                    this.convert([Json.Data.Item]);
                                    this.ketquatimkiem.list = [Json.Data.Item];
                                }
                                else {
                                this.convert(Json.Data.Item);
                                this.ketquatimkiem.list = Json.Data.Item;
                                }
                            }
                            }
                        });
                    }
                    else {
                        SearchEBillByCustomerAPI.tracuu_ma_gd(this.axios, pdata).then((response) => {
                            if(response.data.errorCode != 0 || response.data.data == "<Data></Data>"){
                                this.$toast.error('Không tìm thấy thông tin hóa đơn')
                            }
                            else {
                                if(response && response.data) {
                                    const xmlDoc = this.parseXmlDoc(response.data.data)
                                    const Json = this.xmlToJson(xmlDoc);
                                    if(response.data.data.length < 550 ){
                                        this.convert([Json.Data.Item]);
                                        this.ketquatimkiem.list = [Json.Data.Item];
                                    }
                                    else {
                                        this.convert(Json.Data.Item);
                                        this.ketquatimkiem.list = Json.Data.Item;
                                    }
                                }
                            }
                        });
                    }
                }                
              }
            } 
          });
        }
      }      
    },
    Clear() {
          this.ketquatimkiem.list = null;
    },
    convert(a){
      for(var i=0; i<a.length; i++){
                  a[i].stt = i+1;
                  a[i].publishDate = this.format_date(a[i].publishDate['#text']);
                  a[i].thanghd = this.format_month(a[i].publishDate['#text']);
                   a[i].xemchitiet = 'Xem chi tiết';
                  if(a[i].status['#text'] == 1){
                    a[i].status = "Hóa đơn đã phát hành"
                  }
                  else if(a[i].status['#text'] == 3){
                    a[i].status = "Hóa đơn đã thay thế"
                  }
                  else if(a[i].status['#text'] == 4){
                    a[i].status = "Hóa đơn đã sửa đổi"
                  }
                  else if(a[i].status['#text'] == 5){
                    a[i].status = "Hoá đơn đã huỷ"
                  }
                //   else a[i].status['#text'] = "Hóa đơn đã hủy"
                  if(a[i].payment['#text'] == 1){
                    a[i].payment = "Đã thanh toán"
                  }
                  else a[i].payment = "Chưa thanh toán"
                  if(a[i].signStatus['#text'] == 0){
                    a[i].signStatus = "Chưa xem"
                  }
                  else a[i].signStatus = "Đã xem"
        }
    },
    parseXmlDoc (xml) {
      let xmlDoc
      if (window.DOMParser) {
        const parser = new DOMParser()
        xmlDoc = parser.parseFromString(xml.trim(), 'text/xml')
      } else {
        xmlDoc = new ActiveXObject('Microsoft.XMLDOM')
        xmlDoc.async = false
        xmlDoc.loadXML(xml.trim())
      }
      return xmlDoc
    },
    xmlToJson(xml) {
          // Create the return object
          var obj = {};

          // console.log(xml.nodeType, xml.nodeName );

          if (xml.nodeType == 1) { // element
              // do attributes
              if (xml.attributes.length > 0) {
              obj["@attributes"] = {};
                  for (var j = 0; j < xml.attributes.length; j++) {
                      var attribute = xml.attributes.item(j);
                      obj["@attributes"][attribute.nodeName] = attribute.nodeValue;
                  }
              }
          } 
          else if (xml.nodeType == 3 || 
                  xml.nodeType == 4) { // text and cdata section
              obj = xml.nodeValue
          }

          // do children
          if (xml.hasChildNodes()) {
              for(var i = 0; i < xml.childNodes.length; i++) {
                  var item = xml.childNodes.item(i);
                  var nodeName = item.nodeName;
                  if (typeof(obj[nodeName]) == "undefined") {
                      obj[nodeName] = this.xmlToJson(item);
                  } else {
                      if (typeof(obj[nodeName].length) == "undefined") {
                          var old = obj[nodeName];
                          obj[nodeName] = [];
                          obj[nodeName].push(old);
                      }
                      if (typeof(obj[nodeName]) === 'object') {
                          obj[nodeName].push(this.xmlToJson(item));
                      }
                  }
              }
          }
          return obj;
      },
    format_date(value){
         if (value) {
           return moment(String(value)).format('DD/MM/YYYY')
          }
    },
    format_month(value){
         if (value) {
           return moment(String(value)).format('MM/YYYY')
          }
    },
  },
};
</script>
