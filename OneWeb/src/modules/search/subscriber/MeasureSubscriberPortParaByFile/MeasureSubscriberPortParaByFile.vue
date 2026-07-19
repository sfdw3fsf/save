<template src="./template.html"></template>

<script>
import breadcrumb from '@/components/breadcrumb'
import api from "../../../search/subscriber/MeasureSubscriberPortParaByFile/api/index";
import XLSX from 'xlsx'

export default {
  name: "MeasureSubscriberPortParaByFile",
  components: {
    breadcrumb
  },
  async created() {
    // this.ttnd = await this.$root.token.getThongTinNguoiDung();
    try {
      const response = await api.getTTND(this.axios)
      const data = response.data
      this.ttnd = data.data
    } catch (e) {
      console.log(e)
    } finally {
    }
    await this.frmDocFileTestPort_Load();
  },
  data() {
    return {
      header: {
        title: 'Đo thông số cổng thuê bao theo file',
        list: [
          {name: 'Lập hợp đồng', link: {name: 'Ui.cards'}},
          {name: 'Lắp đặt mới', link: {name: 'Ui.cards'}},
        ]
      },
      load: true,
      chuky: "",
      kyhoadon: null,
      khd: null,
      ma_tn: "",
      vkieu: 0,
      ds_loi: [],
      ds_load: [],
      dichvuvt_id: 0,
      loaitb_id: "",
      chieukhoa: 1,
      khoamo: 1,
      thongbao: "",
      trangthai: 1,
      tsbtnXuatExcel: true,
      tsbDoKiem: true,
      rdoOptionInsert: 0,
      grid_datasource: [],
      ttnd: null,
    }
  },
  methods: {
    async SetButton(kieu) {
      if (kieu === 4)//background đang chạy
      {
        this.tsbtnXuatExcel = false;
        this.tsbDoKiem = false;
      }

      if (kieu === 5)//background chạy xong
      {
        this.tsbtnXuatExcel = true;
        this.tsbDoKiem = true;
      }
    },
    async readFileExcel(e) {
      const files = e.target.files

      // let workbook = XLSX.read(null, {
      //   type: 'binary'
      // });

      const fileReader = new FileReader();
      fileReader.onload = async (ev) => {
        try {
          const data = ev.target.result;
          const xlsx = XLSX;
          const workbook = xlsx.read(data, {
            type: "binary",
          });
          const wsname = workbook.SheetNames[0];
          // console.log(wsname);

          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]);
          const excellist = [];

          if (ws.length > 0) {
            for (var i = 0; i < ws.length; i++) {
              excellist.push(ws[i]);
            }
          } else {
            this.$root.toastError("File không có data!");
          }
          if (!excellist[0].hasOwnProperty("MA_TB")) {
            this.$root.toastError("Tên cột trong file không đúng định dạng: MA_TB");
            return;
          }
          var p_xml_input = "<DocumentElement>";
          var _thao_tac = 2; // Chèn thêm dữ liệu

          _thao_tac = +this.rdoOptionInsert === 2 ? 2 : 1;

          for (let i of excellist) {
            p_xml_input += `<readFileExcel><MA_TB>${i.MA_TB}</MA_TB></readFileExcel>`
          }

          p_xml_input += "</DocumentElement>";

          let post = {
            "p_xml_input": p_xml_input,
            "p_type": _thao_tac,
            "p_ma_nd": this.ttnd.ma_nd
          }

          try {
            const response = await api.sp_insert_ma_tb_dokiem(this.axios, post)
            // const data = await response.data
            console.log(response)
          } catch (e) {
            this.$toast.error(e.message);
          } finally {
          }

          // console.log(excellist);

          await this.HienThiThongTin();

        } catch (e) {
          this.$root.toastError("Có lỗi : " + e);
        }
      };
      if (files.length > 0) {
        fileReader.readAsBinaryString(files[0]);
      }
    },
    async tsbtnXuatExcel_Click() {
      if (this.grid_datasource != null && this.grid_datasource.length > 0) {
        let data = XLSX.utils.json_to_sheet(this.grid_datasource)
        let wb = XLSX.utils.book_new() // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, data, 'DsThueBao') // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, 'ds_thuebao.xlsx')
      } else this.$toast.error('Không có dữ liệu!')
    },
    async tsbDoKiem_Click() {
      await this.SetButton(4);

      try {
        for (let i = 0; i < this.grid_datasource.length; i++) {
          if (this.grid_datasource[i].trang_thai.toString() === "0") {

            // this.grid_datasource[i].ten_trang_thai = "Đang thực hiện...";
            const ds = [...this.grid_datasource]
            ds[i].ten_trang_thai = "Đang thực hiện..."
            this.grid_datasource = [...ds]

            await this.fn_dokiem_theo_matb(this.grid_datasource[i].ma_tb);

            // this.grid_datasource[i].ten_trang_thai = "Đã đo kiểm";
            ds[i].ten_trang_thai = "Đã đo kiểm"
            this.grid_datasource = [...ds]
          }
        }
      } catch (e) {
        this.$toast.error(e.message);
      } finally {
        await this.HienThiThongTin();
        this.$toast.success('Đo kiểm thành công!')
      }

      await this.SetButton(5);
    },
    updateRow(grid, index, value) {
      grid[index].ten_trang_thai = value;
      return grid;
    },
    async fn_dokiem_theo_matb(ma_tb) {
      try {
        const response = await api.fn_dokiem_theo_matb(this.axios, ma_tb)
        const data = await response.data
        if (data.data.error_code.toString() === "-1") {
          let post = {
            "p_ma_tb": ma_tb,
            "p_ketqua": "-1"
          }
          await this.sp_capnhat_dokiem(post);
        } else {
          let post = {
            "linetestUser": data.data.linetestUser,
            "linetestPassword": data.data.linetestPassword,
            "input": data.data.input,
            "mac": data.data.getMac.toString()
          };
          try {
            const response_1 = await api.testPortV2(this.axios, post)
            const data = response_1.data
            // console.log(response_1)
            if (data.data !== null) {
              let ketqua = data.data.KETQUA;
              let capnhat = {
                "p_ma_tb": ma_tb,
                "p_ketqua": ketqua
              }
              await this.sp_capnhat_dokiem(capnhat);
            }
          } catch (e) {
            console.log(e)
          } finally {
          }
        }
      } catch (e) {
        console.log(e)
      } finally {
      }
    },
    parseXmlDoc(xml) {
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
    async sp_capnhat_dokiem(capnhat) {
      try {
        const response = await api.sp_capnhat_dokiem(this.axios, capnhat)
        const data = await response.data
        console.log(data);
      } catch (e) {
        console.log(e)
      } finally {
      }
    },
    async testPortV2(data) {
      try {
        const response = await api.testPortV2(this.axios, data)
        const data = await response.data

      } catch (e) {
        console.log(e)
      } finally {
      }
    },
    async tsbTaiDuLieu_Click() {
      await this.HienThiThongTin();
    },
    async HienThiThongTin() {
      console.log(this.ttnd)
      try {
        const response = await api.sp_lay_dulieu_dokiem_theofile(this.axios, {
          "ma_nd": this.ttnd.ma_nd
        })
        const data = await response.data
        this.grid_datasource = data.data;
      } catch (e) {
        this.$toast.error(e.message);
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async frmDocFileTestPort_Load() {
      this.rdoOptionInsert = 1;
    },
    async test() {
      try {
        const response = await api.sp_lay_dulieu_dokiem_theofile(this.axios, {
          "ma_nd": this.ttnd.ma_nd
        })
        const data = await response.data
        this.grid_datasource = data.data;
      } catch (e) {
        this.$toast.error(e.message);
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    }
  }
}
</script>

<style scoped>
.menu {
  background: white;
  border-bottom: 1px solid #9090;
  box-shadow: 0 0 3px #808080;
  height: 40px;
  padding-top: 5px;
  font-weight: 600;
  line-height: 1;
}

.menu .nav-link {
  border-radius: 0;
  border-right: 1px solid #A5C1D0;
}

.menu .nav-link:active {
  color: white;
  background: #007BFF;
}

.menu .nav-item:last-child .nav-link {
  border-right: 0;
}

.hide-header thead {
  display: none;
}

.form-group {
  margin-bottom: 0.333rem;
}

.e-grid.e-responsive .e-headercelldiv {
  text-overflow: clip;
}

.c-red {
  color: red;
  font-weight: bold;
}

.overflow-hidden {
  overflow: hidden;
  /*flex-wrap: none;*/
}

label, legend {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.col-xxx {
  width: calc(19.444444444% - 3px);
  flex: 0 0 calc(19.444444444% - 3px);
  max-width: calc(19.444444444% - 3px);
}
</style>
