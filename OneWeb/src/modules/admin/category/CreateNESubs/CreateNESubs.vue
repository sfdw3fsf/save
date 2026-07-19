<template src="./CreateNESubs.html"></template>
<style src="./CreateNESubs.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import CreateNESubsAPI from './CreateNESubsAPI'
import DocFileSoNe from './DocFileSoNe'
import xlsx from "xlsx"
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
import { log } from 'pdfmake/build/pdfmake'
//

export default {
  components: { breadcrumb, DocFileSoNe },
  name: 'CreateNESubsAPI',
  mounted () {
    //load ds đơn vị vệ tinh
    var pdata = {};
    pdata.loaidv_id = "25";
    CreateNESubsAPI.ds_don_vi(this.axios, pdata).then((response) => {
      console.log(response.data.data);
    if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.listbox.don_vi.list = response.data.data;
        }
      }
    );
   this.btnSearchClicked();
  },
  data () {
    return {
      header: {
        title: 'Tạo số NE',
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
      popupComponent: null,
      TxtSone: "",
      TxtGhichu: "",
      ifGhiLai: true,
      ifXoa: true,
      ifNhapMoi: true,
      mota: "",
      tenFile: "",
      files: "",
      activeTabs: true,
      ketquatimkiem1: {
          list: [],
          header:[],
          value: {},
          columns: [
          { fieldName: 'SONE', headerText: 'Số Ne', allowFiltering: true },
          { fieldName: 'GHICHU', headerText: 'Ghi chú', allowFiltering: true },                    
        ],
      },  
      ketquatimkiem: {
          list: [],
          header:[],
          value: {},
          columns: [
          { fieldName: 'SONE', headerText: 'Số Ne', allowFiltering: true },
          { fieldName: 'GHICHU', headerText: 'Ghi chú', allowFiltering: true },                    
        ],
      },  
      listbox: {
        don_vi:{
          list:[],
          value: 570,
          isEnabled:false,
        },
        ketquatimkiem3: {
          list:[],
          value: "",
          isEnabled:false,
        },
      },
    }
  },
  methods: {
    ShowSearchAccount() {
      this.popupComponent = SearchAccount;
      SearchAccount.data.isPopup = true;
      // this.Popup("popupComponents");
      // this.popupComponentName = "popupSearchAccount";
    },
    showModal: function () {
      this.$bvModal.show('docfile')
    },
    btnSearchClicked() {
      this.ketquatimkiem.list = null;
      this.TxtSone = "";
      this.TxtGhichu = ""
      var pdata = {};
      pdata.donvi_id = this.listbox.don_vi.value;
      CreateNESubsAPI.tracuu(this.axios, pdata).then((response) => {
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
            if(response.data.data.length == 0){
              this.$toast.error('Không tìm thấy danh sách phiếu')
            }
            this.loading(true);
            this.ketquatimkiem.list = response.data.data;
            this.loading(false);
        }
      });
    },
    selectedRowChanged(obj){
      this.ifNhapMoi = true;
      this.ifXoa = true;
      this.ketquatimkiem.value = obj;
      this.listbox.ketquatimkiem3.list = obj;
      this.TxtSone = this.listbox.ketquatimkiem3.list.SONE;
      this.TxtGhichu = this.listbox.ketquatimkiem3.list.GHICHU;
    },
    NhapMoi(){
      this.$refs.FcNe.focus();
      this.ifNhapMoi = false;
      this.ifXoa = false;
      this.TxtSone = "";
      this.TxtGhichu = ""
    },
    GhiLai(){
      if(this.ifNhapMoi == false){
        this.$bvModal.msgBoxConfirm('Bạn có thực sự muốn tạo số ne mới không?', {
                	title: '',
                	size: "md",
                	okTitle: 'Đồng ý',
                	cancelTitle: 'Hủy',
            	}).then(async v => {
                	if(!v) {
                  	return;
                	}
		            // Xử lý code ở đây
                CreateNESubsAPI.get_key(this.axios, {"keyname": "NE_ID"}).then((response) => {
                  if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
                      var pdata = {};
                      pdata.donvi_id = this.listbox.don_vi.value;
                      pdata.ghichu = this.TxtGhichu;
                      pdata.ne_id = response.data.data;
                      if (this.TxtGhichu.length>0){
                        this.TxtGhichu = this.TxtGhichu.trim();
                      }
                      if (this.TxtSone.length>0){
                        this.TxtSone = this.TxtSone.trim();
                      }
                      if(this.TxtSone == ""){
                        this.$toast.error('Bạn chưa nhập số ne!');
                      }
                      else {
                      pdata.sone = this.TxtSone;
                      console.log(pdata);
                      CreateNESubsAPI.nhap_moi(this.axios, pdata).then((response) => {
                      if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
                          this.btnSearchClicked();
                          this.$toast.success('Tạo số Ne thành công');
                        }
                      });
                    }  
                  }
                });
            	});
          }
      else {
      this.$bvModal.msgBoxConfirm('Bạn thực sự muốn cập nhật số ne này không?', {
                	title: '',
                	size: "md",
                	okTitle: 'Đồng ý',
                	cancelTitle: 'Hủy',
            	}).then(async v => {
                	if(!v) {
                  	return; 
                	}
		            // Xử lý code ở đây
                var pdata = {};
                if (this.TxtGhichu.length!=""){
                  this.TxtGhichu = this.TxtGhichu.trim();
                }
                if (this.TxtSone!=""){
                  this.TxtSone = this.TxtSone.trim();
                }
                if(this.TxtSone == ""){
                  this.$toast.error('Bạn chưa nhập số ne!');
                }
                else {
                pdata.ghichu = this.TxtGhichu;
                pdata.ne_id = this.listbox.ketquatimkiem3.list.NE_ID;
                pdata.sone = this.TxtSone;
                CreateNESubsAPI.ghi_lai(this.axios, pdata).then((response) => {
                  if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.btnSearchClicked();
                    this.$toast.success('Cập nhật số Ne thành công')
                    }
                  });
                }
            });
      }
    },
    Xoa(){
      this.$bvModal.msgBoxConfirm('Bạn có thật sự muốn xóa dữ liệu không?', {
                	title: '',
                	size: "md",
                	okTitle: 'Đồng ý',
                	cancelTitle: 'Hủy',
            	}).then(async v => {
                	if(!v) {
                  	return;
                	}
		                // Xử lý code ở đây
                    var pdata = {};
                    pdata.ne_id = this.listbox.ketquatimkiem3.list.NE_ID;
                    pdata.sone = this.listbox.ketquatimkiem3.list.SONE;
                    console.log(pdata);
                    CreateNESubsAPI.xoa(this.axios, pdata).then((response) => {
                    if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
                      this.btnSearchClicked();
                      this.$toast.success('Xóa dải số ne thành công')
                  }
           });
        });
    },
    hideModal: function () {
      this.$bvModal.hide('docfile')
    },
    ChapNhan_Click(){
        var pdata = {};
        pdata.nguoidung_id = this.$auth.getNguoiDungID();
        pdata.donvi_id = this.donvi_id;
        var json_sone = {};
        json_sone = this.ketquatimkiem1.list;
        json_sone = JSON.stringify(json_sone);
        pdata.json_sone = json_sone;
        console.log(pdata);
        if(json_sone == "[]" || json_sone == null){
            this.$toast.error('Bạn chưa tải file số Ne');
        }
        else{
        CreateNESubsAPI.chap_nhan(this.axios, pdata).then((response) => {
        if(response && response.data && response.data.data) {
          CreateNESubsAPI.lay_ds_tmp(this.axios, {"nguoidung_id": this.$auth.getNguoiDungID()}).then((response1) => {
            console.log(response1.data.data);
            var input = {};
            input.nguoidung_id = this.$auth.getNguoiDungID();
            input.donvi_id = this.listbox.don_vi.value;
            CreateNESubsAPI.cap_nhat_ds_tmp(this.axios, input).then((response2) => {
              console.log(response2.data.message);
              this.btnSearchClicked();
              this.$toast.success("Thêm danh sách file thành công");
            });
          });
          // this.ketquatimkiem.list = this.ketquatimkiem1.list;
          this.$bvModal.hide('docfile')
        }
      });
    }
    },
    async importExcel(e) {
            const files = e.target.files;
            this.files = files;
          //  console.log(files);
            if (!files.length) {
                return;
            } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
                return this.$root.toastError("File excel không đúng định dạng!");
            }
            if( files[0].name ) {
                this.tenFile = files[0].name
            }
            this.readFile(files);
        },
    readFile(files) {
      this.$bvModal.msgBoxConfirm('Bạn muốn mở file?', {
                	title: '',
                	size: "md",
                	okTitle: 'Đồng ý',
                	cancelTitle: 'Không đồng ý',
            	}).then(async v => {
                	if(!v) {
                  	return;
                	}
            files = this.files;
            const fileReader = new FileReader();
            fileReader.onload = async (ev) => {
                try {
                    // Khai báo biến read excel
                    const data = ev.target.result;
                    const XLSX = xlsx;
                    const workbook = XLSX.read(data, {
                        type: "binary",
                    });
                    const wsname = workbook.SheetNames[0];
                   // console.log(wsname);
                    const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]);
                  //  console.log(ws[0]);
                    const excellist = [];
                  //  console.log(ws.length);
                    if (ws.length > 0) {
                        for (var i = 0; i < ws.length; i++) {
                        //  console.log(ws[i]);
                          excellist.push(ws[i]);
                        }
                         this.fieldsRender = this.fields;
                        if (
                            !excellist[0].hasOwnProperty("SONE") ||
                            !excellist[0].hasOwnProperty("GHICHU") 
                        ) {
                            this.$root.toastError("Tên cột trong file không đúng định dạng: SONE, GHICHU");
                            return;
                        }
                        this.ketquatimkiem1.list = excellist;
                        //  Kiểm tra NULL
                        for (let k = 0; k < excellist.length; k++) {
                            let item = excellist[k];
                            this.ketquatimkiem1.list[k].NGUOIDUNG_ID = this.$auth.getNguoiDungID();
                            if (!item.SONE || item.SONE.length == 0) {
                                this.$root.toastError("Cột số Ne không được để trống");
                              // excellist = null;
                              this.ketquatimkiem1.list = null;
                                break;
                            }
                        }
                    } else {
                        this.$root.toastError("File không có data!");
                    }

                    // Kiểm tra dữ liệu xem có đúng form theo mẫu
                   // this.ketquatimkiem.list = excellist;
                    // Kiểm tra lặp
                    const lookup = excellist.reduce((a, e) => {
                        a[e.SONE] = ++a[e.SONE] || 0;
                        a[e.GHICHU] = ++a[e.GHICHU] || 0;
                        return a;
                    }, {});
                } catch (e) {
                    return alert("Read failure!" + e);
                }
            };
            fileReader.readAsBinaryString(files[0]);
            });
        },
        uploadFile() {
            this.$refs.fileInput.click();
        },
    created: async function () {
      this.loading(true);
      this.loadData();
      this.loading(false);
    },
  },
};
</script>
