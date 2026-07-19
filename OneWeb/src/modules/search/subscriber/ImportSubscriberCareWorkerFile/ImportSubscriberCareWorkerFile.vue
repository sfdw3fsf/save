<template src="./ImportSubscriberCareWorkerFile.html"></template>
<style src="./ImportSubscriberCareWorkerFile.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import ImportSubscriberCareWorkerFileAPI from './ImportSubscriberCareWorkerFileAPI'
import xlsx from "xlsx";

//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
//
export default {
  components: { breadcrumb},
  name: 'ImportSubscriberCareWorkerFileAPI',
  mounted () {
  },
  destroyed () {},
  data () {
    return {
      header: {
        title: 'Import file nhân viên chăm sóc thuê bao',
        list: [
          {
            name: 'Lập hợp đồng',
            link: { name: 'Ui.buttons' }
          },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      MaGD: "",
      tenFile: "",
      DisXuatEx: true,
      DisThucThi: true,
      datamau: [
    {
        "THUEBAO_ID": 1234,
        "NHANVIEN_ID": 1,
        "LOAINV_ID": 449,
        "TYLE": 1,
        "LOAI": 1,
        "DONVI_ID": 1
    },
    {
        "THUEBAO_ID": 1235,
        "NHANVIEN_ID": 1,
        "LOAINV_ID": 449,
        "TYLE": 1,
        "LOAI": 1,
        "DONVI_ID": 1
    },
    {
        "THUEBAO_ID": 1236,
        "NHANVIEN_ID": 1,
        "LOAINV_ID": 449,
        "TYLE": 1,
        "LOAI": 1,
        "DONVI_ID": 1
    }
],
      dulieuexcel: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
          columns: [
          { fieldName: '', headerText: 'Kết quả', allowFiltering: true },
          { fieldName: 'THUEBAO_ID', headerText: 'Nhân viên ID', allowFiltering: true },
          { fieldName: 'LOAINV_ID', headerText: 'Loại NV ID', allowFiltering: true},
          { fieldName: 'TYLE', headerText: 'Tỷ lệ', allowFiltering: true },
          { fieldName: 'LOAI', headerText: 'Loại', allowFiltering: true },
          { fieldName: 'DONVI_ID', headerText: 'Đơn vị ID', allowFiltering: true},
          { fieldName: 'THUEBAO_ID', headerText: 'Thuê bao ID', allowFiltering: true },
        ],
      },
      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      from_date: new Date(),
      to_date: new Date()
    }
  },
  methods: {
    naplai(){
      this.tenFile = "";
      this.dulieuexcel.list = null;
      this.DisXuatEx = true;
      this.DisThucThi = true;
    },
    ThucThi(){
      var input = {};
      input.phanvung_id = this.$auth.getPhanVungID();
      input.p_json = JSON.stringify(this.dulieuexcel.list);
      console.log(input);
      ImportSubscriberCareWorkerFileAPI.thuc_thi(this.axios, input).then((response) => {
        if(response.data.error_code == "BSS-00000000") {
           this.$toast.success('Thêm dữ liệu thành công')
        }
      });
    },
    FileMau() {
        this.loading(true);
        try {
          var dsach = xlsx.utils.json_to_sheet(this.datamau);
          var wb = xlsx.utils.book_new();
          xlsx.utils.book_append_sheet(wb, dsach, "ds_import");
          xlsx.writeFile(wb, "ImportDsNV.xlsx");
        } catch (error) {
        } finally {
          this.loading(false);
        }
      },
      XuatExcel() {
        if (this.dulieuexcel.list.length <= 0) {
          this.$toast.error("Chưa có danh sách để xuất Excel!");
          return;
        }
        this.loading(true);
        try {
          var dsach = xlsx.utils.json_to_sheet(this.dulieuexcel.list);
          var wb = xlsx.utils.book_new();
          xlsx.utils.book_append_sheet(wb, dsach, "DS");
          xlsx.writeFile(wb, "Danhsachnhanvien.xlsx");
        } catch (error) {
        } finally {
          this.loading(false);
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
            files = this.files;
            this.dulieuexcel.list = null;
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
                            !excellist[0].hasOwnProperty("THUEBAO_ID") ||
                            !excellist[0].hasOwnProperty("NHANVIEN_ID")||
                            !excellist[0].hasOwnProperty("LOAINV_ID") ||
                            !excellist[0].hasOwnProperty("TYLE") ||
                            !excellist[0].hasOwnProperty("LOAI") ||
                            !excellist[0].hasOwnProperty("DONVI_ID")
                        ) {
                            this.$root.toastError("File không đúng định dạng, vui lòng tải file mẫu!");
                            this.DisXuatEx = true;
                            this.DisThucThi = true;
                            return;
                        }
                        this.dulieuexcel.list = excellist;
                      //  console.log(this.dulieuexcel.list);
                        //  Kiểm tra NULL
                        // for (let k = 0; k < excellist.length; k++) {
                        //     let item = excellist[k];
                        //     if (!item.SONE || item.SONE.length == 0) {
                        //         this.$root.toastError("Cột số Ne không được để trống");
                        //       // excellist = null;
                        //       this.dulieuexcel.list = null;
                        //         break;
                        //     }
                        // }
                    } else {
                        this.$root.toastError("File không có data!");
                    }
                    this.DisThucThi = false;
                    this.DisXuatEx= false;
                } catch (e) {
                    return alert("Read failure!" + e);
                }
            };
            fileReader.readAsBinaryString(files[0]);
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
