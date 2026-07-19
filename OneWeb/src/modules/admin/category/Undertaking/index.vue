<template>
  <div>
    <vue-bread-crumb :header="header" />
    <vue-nav>
      <ul class="list">
        <li
          @click="clickButton('nhapmoi')"
          :class="button.nhapmoi ? 'active' : 'none-active'"
        >
          <a> <span class="icon one-file-plus"></span>Nhập mới </a>
        </li>
        <li
          @click="clickButton('ghilai')"
          :class="button.ghilai ? 'active' : 'none-active'"
        >
          <a> <span class="icon one-save"></span>Ghi lại </a>
        </li>
        <li
          @click="clickButton('huybo')"
          :class="button.huybo ? 'active' : 'none-active'"
        >
          <a>
            <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Huỷ
          </a>
        </li>
        <li
          @click="clickButton('xoa')"
          :class="button.xoa ? 'active' : 'none-active'"
        >
          <a> <span class="icon one-trash"></span>Xoá </a>
        </li>
        <li
          @click="clickButton('trogiup')"
          :class="button.trogiup ? 'active' : 'none-active'"
        >
          <a> <span class="icon one-circle-question"></span>Trợ giúp </a>
        </li>
      </ul>
    </vue-nav>
    <div class="page-content">
      <div class="box-form">
        <div class="legend-title">Thông tin chủ trương</div>
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w100">Chủ trương <span class="red">(*)</span></div>
              <div class="value">
                <bss-error-marker>
                <input
                  id="chutruong"
                  v-model="txtChutruong"
                  type="text"
                  class="form-control"
                  required 

                />
                </bss-error-marker>
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Năm <span class="red">(*)</span></div>
              <div class="value">
                    <div class="select-custom">
                        <date-picker
                            :format="yearFormat"
                            :value-type="yearFormat"
                            ref="txtNam" 
                            :time-title-format="yearFormat"
                            v-model="txtNam"
                            :showTimePanel="showTimePanel"
                            :type="typeFormatYear"
                            @close="handleOpenChange" 
                            id="txtNam" 
                            placeholder="YYYY"                       
                        >
                            <template #icon-calendar>
                                <span class="icon one-calendar"></span>
                            </template>
                            <template v-slot:footer>
                                <button class="mx-btn mx-btn-text" @click="toggleTimePanel">
                                </button>
                            </template>
                        </date-picker>                      
                    </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Số công văn</div>
              <div class="value">
                <input
                  v-model="txtSocongvan"
                  type="text"
                  class="form-control"
                />
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Ký hiệu</div>
              <div class="value">
                <input v-model="txtKyhieu" type="text" class="form-control" />
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Ghi chú</div>
              <div class="value">
                <textarea
                  v-model="txtGhichu"
                  name
                  class="form-control"
                  style="height: 100px; resize: none"
                ></textarea>
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w130">Ngày quyết định <span class="red">(*)</span></div>
              <div class="value">
                <div class="input-icon-right">
                    <date-picker
                        :format="dateFormat"
                        :value-type="dateFormat"
                        ref="txtngayQD" 
                        :time-title-format="dateFormat"
                        v-model="txtNgayQD"
                        :showTimePanel="showTimePanel"
                        :type="typeFormat"
                        @close="handleOpenChange" 
                        id="txtngayQD"                        
                    >
                        <template #icon-calendar>
                            <span class="icon one-calendar"></span>
                        </template>
                        <template v-slot:footer>
                            <button class="mx-btn mx-btn-text" @click="toggleTimePanel">
                                {{ showTimePanel ? 'Chọn ngày' : 'Chọn giờ' }}
                            </button>
                        </template>
                  </date-picker>
                </div>
              </div>
            </div>

            <div class="info-row">
                <div class="key w130">File công văn</div>
                <div class="value">
                    <div class="input-more-button">
                      <label class="btn" for="upload-file" :class="{disabled:!isAddNew}" id="chonfile">
                          <span class="-ap icon-more_horiz"></span>
                          <input type="file" ref="files" class="hidden" id="upload-file" @change="UploadFile">
                      </label>   
                       
                    <div class="form-control pad10" style="height: 210px;">
                        <table class="table-content">
                          <thead></thead>
                          <body>
                            <tr v-for="(item, i) in danhsachfile" :key="i">
                              <td>
 
                                <a :href="item" download>
                                  <button class="btn btn-main padt7">                                    
                                    <span class="one-download lh20"></span>
                                  </button>
                                  </a>
                              </td>
                              <td>
                                  <button class="btn btn-main padt7" @click="removeFile(item)">                                    
                                    <span class="-ap icon-close lh20"></span>
                                  </button>                                                
                              </td>
                              <td>{{item}}</td>
                            </tr>
                          </body>
                        </table>
                    </div>
                    </div>
                </div>
            </div>

          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title">Danh sách chủ trương</div>
            <DataGrid :columns="columns_ds_chu_truong" ref="noidungchutruong" :dataSource="options.danh_sach_chu_truong" :enable-paging-server="false"
            :allowPaging="true" :showFilter="true" @selectedRowChanged="onChutruongSelected">
            </DataGrid>
      </div>
    </div>
  </div>
</template>
<script>
import moment from "moment";
import BssErrorMarker from '@/components/BssErrorMarker';
import BssRequiredMarker from '@/components/BssRequiredMarker';
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
import define from "./define";
export default {
  prop: [],
  components: {
    moment,
    DatePicker,
    BssRequiredMarker,
    BssErrorMarker
  },
  data() {
    return {
      ...define

    };
  },
  mounted() {
      this.loadGridChuTruong();
  },

  methods: {
     
      loadGridChuTruong() {
          this.loadGrid();
          this.setButton(3);
          this.ngay_cn= moment(new Date()).format('YYYY-MM-DD HH:mm:ss');
          this.may_cn= this.$root.token.getMaNhanVien();
          this.nguoi_cn= this.$root.token.getUserName();
      },
      getCurrentYear: function () {
        return new Date().getFullYear();
      },
      setButton(kieu) {
          this.button.nhapmoi = false;
          this.button.ghilai = false;
          this.button.xoa = false;
          this.button.huybo = false;
          this.button.trogiup = false;
          this.isAddNew = false;

          if (kieu == -1) {
            // start
            this.button.ghilai = true;
            this.button.huybo = true;

          }
          if (kieu== 0) {
            // bat dau
            this.button.nhapmoi = true;
            this.Clear();
          }
          if (kieu == 1) {
            // them moi
            this.button.ghilai = true;
            this.button.huybo = true;
            this.isAddNew = false;
            this.Clear();
          }
          if (kieu == 2) {
            // huy
            this.button.nhapmoi = true;
            this.button.xoa = true;
            this.button.ghilai = true;
            this.button.huybo = true;
            this.isAddNew = true;
            // this.Clear();
          }
          if (kieu == 3) {
            // edit
            this.button.nhapmoi = true;
            this.button.xoa = true;
            this.button.ghilai = true;
            this.button.huybo = true;
            this.isAddNew = true;
          }

      },
      Clear() {
        this.txtChutruong = null;
        this.txtNam = null;
        this.txtSocongvan = null;
        this.txtKyhieu = null;
        this.txtGhichu = null;        
        this.txtNgayQD = moment().format('DD/MM/YYYY HH:mm:ss');
        this.danhsachfile = null;
      },
    async loadGrid() {
      try {
        let res = await this.axios.post("/web-quantri/chutruong/sp_lay_ds_chutruong",
        {})
        this.options.danh_sach_chu_truong = res.data.data
        if (this.options.danh_sach_chu_truong != null) {
          this.options.danh_sach_chu_truong = [...this.options.danh_sach_chu_truong]
          this.setButton(3)
        }
        if (this.options.danh_sach_chu_truong.length == 0) {
          console.log("danh sách trống")
          this.setButton(1)
        }

      } catch (er) {
        this.$toast.error("Lỗi");
      }
    },
    clickButton(key) {
      if(!this.button[key]) {
        return false;
      }
      if (key=="nhapmoi") {
        this.btnNhapMoi_Click();
      }
      if (key =="ghilai") {
        this.btnGhiLai_Click();
      }
      if (key=="huybo") {
        this.btnHuyBo_Click();
      }
      if (key =="xoa") {
        this.btnXoa_Click();
      }
      if (key =="trogiup") {
        this.btnTroGiup_Click();
      }
    },
    btnNhapMoi_Click() {
      this.setButton(1);
    },
    btnGhiLai_Click() {
      this.ghilai();
    },
    btnHuyBo_Click()   {      
      this.setButton(2);
      this.LoadPreview();
    },
    btnXoa_Click() {
      this.Xoa();
    },
    LoadPreview() {
        this.txtChutruong = this.current.chutruongtbd
        this.txtSocongvan = this.current.socongvan
        this.txtKyhieu = this.current.kyhieu
        this.txtGhichu = this.current.ghichu
        this.txtNam = this.current.nam
        this.txtNgayQD = this.current.ngayqd
        this.layDanhSachFile(this.CHU_TRUONG_ID);
    },
    onChutruongSelected(item) {
      if (item != null) {
        this.txtChutruong = item.chutruong
        this.txtSocongvan = item.so_cv
        this.txtKyhieu = item.kyhieu
        this.txtGhichu = item.ghichu
        this.txtNam = moment(String(item.nam)).format('YYYY');
        this.txtNgayQD = item.ngay_qd
        this.CHU_TRUONG_ID = item.chutruong_id
        this.current.chutruongtbd = item.chutruong
        this.current.socongvan = item.so_cv
        this.current.kyhieu = item.kyhieu
        this.current.ghichu = item.ghichu
        this.current.nam = moment(String(item.nam)).format('YYYY'); 
        this.current.ngayqd = moment(new Date(item.ngay_qd)).format('DD/MM/YYYY HH:mm:ss');
        this.layDanhSachFile(item.chutruong_id);
      }
    },

    async layDanhSachFile(id) {
      try {
        let resfile = await this.axios.post("web-quantri/chutruong/sp_lay_ds_filecv_chutruong", 
        {
          "chutruong_id": id
        })
        let data = resfile.data.data
        let filecv = data[0].file_cv
        let datafile = []
        if (filecv != null) {
          filecv = filecv.split(",");
          for (let i = 0; i < filecv.length; i ++) {
            const item = filecv[i]
            let val = item

            if (item.endsWith("|")) {
             val = item.replace("|", "");
            }
            datafile.push(val)
          }
        }
        this.danhsachfile = datafile
      } catch (err) {
        this.$toast.error("Lỗi lay danh sách file!");
      }
    }, 
    async ghilai() {
      try {
        if (!this.kiemTraDuLieu())
          return false
                  console.log('pass ktra du lieu: ');
      this.loading(true);
      let insert = 0;
      // tao du lieu
      try{      
      await  fetch('https://api.ipify.org?format=json')
        .then(x => x.json())
        .then(({ ip }) => {          
          this.ip =  ip;
        });
        
      }catch(e) {
        this.ip = "";
      }
      let id = this.CHU_TRUONG_ID;
      let files = "";
      if (!this.button.nhapmoi) {
        insert = 1;
        id = "";        
      } else {
        if (this.danhsachfile.length>0) {
          files = this.danhsachfile.join();
        }        
      }    
      let ngayQD =moment(this.txtNgayQD,'DD/MM/YYYY HH:mm:ss').format('YYYY-MM-DD HH:mm:ss')
      let json_danhsach = [
        {
          chutruong_id: id,
          chutruong: this.txtChutruong,
          nam: this.txtNam,
          so_cv: this.txtSocongvan,
          kyhieu: this.txtKyhieu,
          file_cv: files,
          ngay_cn: this.ngay_cn,
          nguoi_cn: this.nguoi_cn,
          may_cn: this.may_cn,
          ip_cn: this.ip,
          ghichu: this.txtGhichu,
          ngay_qd: ngayQD
        }
      ]
      let danhsach =  JSON.stringify(json_danhsach);
        // end tao du lieu
        console.log('insert: ', insert);
        console.log('jsnoidung: ', danhsach);

        let rsInsert = await this.axios.post(
          "/web-quantri/chutruong/sp_capnhat_chutruong",
          {
            "is_insert": insert,
            "danhsach": JSON.stringify(json_danhsach)
          }
        );
        if (rsInsert.data.error_code && rsInsert.data.error_code === this.success_code ) {
           if (!this.button.nhapmoi) {
              this.$toast.success('Thêm mới chủ trương thành công');        
            } else {
              this.$toast.success('Cập nhật chủ trương thành công');           
           }
        }
        this.loading(false);
        this.loadGrid();
        this.setButton(1);
      } catch (er) {
        this.$toast.error("Lỗi!")
        this.loading(false);
      }
    },
    kiemTraDuLieu() {
      let currentYear = this.getCurrentYear();
      if (this.txtChutruong == "" || this.txtChutruong == null) {
        this.$toast.error("Bạn chưa nhập tên chủ trương");
        $("#chutruong").focus();
        return false;
      }
      if (this.txtNam < currentYear &&  !this.button.nhapmoi) {
        this.$toast.error("Năm chủ trương không thể nhỏ hơn năm hiện tại");
         $("#txtNam").focus();
        return false;
      }
      return true;
    },

    // chọn file
     async UploadFile() {
      let formData = new FormData();
      for(var file of this.$refs.files.files) {
        console.log("file", file);
        let namefile = file.name.split('.')
        let duoifile = namefile[1]
        if (duoifile ==='exe' || duoifile ==='msi' || duoifile ==='bat') {
          this.$toast.error("File không đúng định dạng");
          return false;
        }

        formData.append('files', file);
      }
      this.loading(true);
        await this.axios.post('/web-quantri/upload',formData,{ headers: { 'Content-Type': 'multipart/form-data' } }).then((response) => {
        this.loading(false);
        if(response.data.error_code && response.data.error_code === this.success_code) {
          console.log('File upload thanh công');
          let item = response.data.data;
          this.danhsachfile.push(item[0]);
          this.capNhatChuTruong();
        }
      }).catch(function(){
        console.log('File upload failed!');
      });
    },
    /// end chọn file
    removeFile(file) {
      let files = this.danhsachfile;
      let index = files.indexOf(file);
      if (index > -1) {
        files.splice(index, 1);
        this.danhsachfile = files;
        this.capNhatChuTruong();
        //revmove file on server///
      }
    },
    toggleTimePanel() {
      this.showTimePanel = !this.showTimePanel
    },
    handleOpenChange() {
      this.showTimePanel = false
    },
    handleRangeClose() {
      this.showTimeRangePanel = false
    },

    downloadFile (url, label) {
      console.log("file: ", url);
      let url1 = 'http://10.70.521.164:8081/' + url;
      console.log("link: ", url1);
      this.axios.get(url1, { responseType: 'blob' })
      .then(response => {
        const blob = new Blob([response.data], { type: 'application/pdf' })
        const link = document.createElement('a')
        link.href = URL.createObjectURL(blob)
        link.download = label
        link.click()
        URL.revokeObjectURL(link.href)
      }).catch(console.error)
    },

    async Xoa() {
        this.$confirm(
           "Bạn chắc chắn muốn xóa thông tin chủ trương này không?", 
           {     
              confirmButtonText: "Có",
              cancelButtonText: "Không",
              type: "warning",
           }
         ).then(async () => {

           try {
             await this.axios.post("web-quantri/chutruong/sp_xoa_chutruong", 
             {
               "chutruong_id": this.CHU_TRUONG_ID
             });
             this.$toast.success("Xóa thành công!");
             this.loadGrid();
             this.setButton(3);
           } catch (e) {
             this.$$toast.error("Không thể xóa bản ghi này")
           }
         });    
    },

    async capNhatChuTruong() {
      try {
        if (!this.kiemTraDuLieu())
          return false;
        
        this.loading(true);
        // tao du lieu
      try{      
      await  fetch('https://api.ipify.org?format=json')
        .then(x => x.json())
        .then(({ ip }) => {          
          this.ip =  ip;
        });
        
      }catch(e) {
        this.ip = "";
      }
      let id = this.CHU_TRUONG_ID;
      let files = "";
      if (this.danhsachfile.length>0) {
        files = this.danhsachfile.join();
      }        

      let ngayQD =moment(this.txtNgayQD,'DD/MM/YYYY HH:mm:ss').format('YYYY-MM-DD HH:mm:ss')
      let json_danhsach = [
        {
          chutruong_id: id,
          chutruong: this.txtChutruong,
          nam: this.txtNam,
          so_cv: this.txtSocongvan,
          kyhieu: this.txtKyhieu,
          file_cv: files,
          ngay_cn: this.ngay_cn,
          nguoi_cn: this.nguoi_cn,
          may_cn: this.may_cn,
          ip_cn: this.ip,
          ghichu: this.txtGhichu,
          ngay_qd: ngayQD
        }
      ]
      let rsUpdateDS = await this.axios.post(
        "web-quantri/chutruong/sp_update_chutruong",
        {
          danhsach: JSON.stringify(json_danhsach)
        }
      );
      if(rsUpdateDS.data.error_code && rsUpdateDS.data.error_code === this.success_code) {
        console.log("cap nhat chu truong thanh cong");
        this.loading(false);
      }
      this.loading(false);                  
      } catch (e) {
        this.$toast.error("Lỗi cap nhat file chu truong!");
        this.loading(false);
      }
    }
  },

};
</script>
<style>
  li.none-active a {
    color: #d3d3d3 !important;
  }
  button.none-active a {
    color: #d3d3d3 !important;
  }
  .mx-datepicker {
    width: 100%;
  }
  .mx-icon-calendar {
    font-style: normal !important;
  }
</style>
