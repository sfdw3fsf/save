<template>
  <div>
    <vue-bread-crumb :header="header" />
    <vue-nav>
      <ul class="list">
        <li
          @click="clickButton('timkiem')"
          :class="this.button.timkiem ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-search"></span>Tìm kiếm </a>
        </li>
        <li
          @click="clickButton('nhantin')"
          :class="this.button.nhantin ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-email"></span>Nhắn tin </a>
        </li>
        <li
          @click="clickButton('inphieuthu')"
          :class="this.button.inphieuthu ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-print"></span>In phiếu thu </a>
        </li>
        <li
          @click="clickButton('indangky')"
          :class="this.button.indangky ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-print"></span>In đăng ký </a>
        </li>
        <li
          @click="clickButton('chuyenhopdong')"
          :class="this.button.chuyenhopdong ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-file-arrow-right"></span>Chuyển HĐ </a>
        </li>
      </ul>
    </vue-nav>
    <div class="page-content">
      <div class="row">
        <div class="col-sm-12 col-lg-12">
          <div class="box-form">
            <div class="legend-title">Thông tin tìm kiếm</div>





            <div class="row">
              <div class="col-sm-6 col-lg-6">
                <div class="info-row">


                    <div class="key w80">
                      <vue-checkbox label="Đơn vị" v-model="chk_donvi"></vue-checkbox>
                    </div>
                    <div class="value">
                        <div class="select-custom">    
                          <SelectExt v-model="donvi" :options="this.options.danhsachdonvi" :disabled="!this.chk_donvi"> 
                            <option disabled value="0">Select one</option> 
                          </SelectExt>        
                        </div>         
                    </div>

                </div>
              </div>

              <div class="col-sm-3 col-lg-3">

            <div class="info-row">
                <div class="key w110">
                  <vue-checkbox label="NVQL" v-model="chk_nvql"></vue-checkbox>
                </div>
                <div class="value">
                    <div class="select-custom">    
                      <SelectExt v-model="nvql" :options="this.options.danhsachnvql" :disabled="!this.chk_nvql"> 
                        <option disabled value="0">Select one</option> 
                      </SelectExt>        
                    </div>         
                </div>
            </div>

              </div>
              <div class="col-sm-3 col-3">
                  <div class="info-row">
                      <div class="key w110">
                        <vue-checkbox label="Nhân viên TC" v-model="chk_nvtc"></vue-checkbox>
                      </div>
                      <div class="value">
                          <div class="select-custom">    
                            <SelectExt v-model="nvtc" :options="this.options.danhsachnvtc" :disabled="!this.chk_nvtc"> 
                              <option disabled value="0">Select one</option> 
                            </SelectExt>        
                          </div>         
                      </div>
                  </div>
              </div>
            </div>
          </div>
          <div class="box-form">
            <div class="legend-title">
              Danh sách đặt cọc / trả trước sắp hết hạn
            </div>


                <DataGridEdit
                  ref="grid"
                  v-bind:columns="this.options.cols"
                  v-bind:dataSource="this.options.danhsach"
                  :showFilter="true"
                  :enabledSelectFirstRow="true"
                  :editSettings="{ allowEditing: false,allowAdding: false,allowDeleting: false, mode: 'Normal' }"
                  :showColumnPointer="false"
                  :enable-paging-server="false"
                  :allowPaging="true"
                  @rowSelected="rowSelected"
                >
                </DataGridEdit>






          </div>
        </div>
      </div>
    </div>

    
    <XemBaoCao ref="popupXemBaoCao" :ds-gia-tri="xembaocao.dsGiaTri" :ma_bc="xembaocao.ma_bc"/>
    <PopupChuyenHD ref="popupPopupChuyenHD"/>




  </div>




</template>
<script>
import api from "./api";
import define from "./define";
import breadcrumb from '@/components/breadcrumb'
import DataGridEdit from './components/DataGrid'

import XemBaoCao from '@/modules/print/XemBaoCao/XemBaoCao.vue'
import PopupChuyenHD from "./PopupChuyenHD.vue";

import {
  Aggregate,
  CommandColumn,
  Edit,
  Filter,
  Freeze,
  Group,
  Page,
  Resize,
  Sort,
  Toolbar
} from '@syncfusion/ej2-vue-grids'
export default {
      provide: {
        grid: [
            Sort,
            Filter,
            Resize,
            Group,
            Freeze,
            CommandColumn,
            Edit,
            Aggregate,
            Toolbar,
            Page,
        ],
    },
  components: { breadcrumb, DataGridEdit,XemBaoCao,PopupChuyenHD },  
  data() {
    return {
      ...define,

      xembaocao: {
        ma_bc: '',
        dsGiaTri: []
      },

      vrkm_id: "",

      chk_donvi:false,
      chk_nvql:false,
      chk_nvtc:false,
      options: {
        danhsachnvql: [],
        danhsachnvtc: [],
        danhsachdonvi: [],
        danhsach: [],

        cols: [
          {
            type: "checkbox",
            width: 40,
          },
          {
            fieldName: "ma_tb",
            headerText: "Số máy/Acc",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "ten_tb",
            headerText: "Tên TB",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "ma_tt",
            headerText: "Mã TT",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "diachi_tt",
            headerText: "Địa chỉ TT",
            textAlign: "left",
            allowFiltering: true,
            allowSorting: true,
            allowEditing: true,
          },
          {
            fieldName: "so_dt",
            headerText: "Số ĐT",
            textAlign: "left",
            allowFiltering: true,
            allowSorting: true,
            allowEditing: true,
          },
          {
            fieldName: "manv_tc",
            headerText: "Mã NVTC",
            textAlign: "left",
            allowFiltering: true,
            allowSorting: true,
            allowEditing: true,
          },
          {
            fieldName: "ma_nv_gt",
            headerText: "Mã NV giới thiệu",
            textAlign: "left",
            allowFiltering: true,
            allowSorting: true,
            allowEditing: true,
          },
          {
            fieldName: "ten_nv_gt",
            headerText: "Tên NV giới thiệu",
            textAlign: "left",
            allowFiltering: true,
            allowSorting: true,
            allowEditing: true,
          },
          {
            fieldName: "ngay_dk",
            headerText: "Ngày ĐK",
            textAlign: "left",
            allowFiltering: true,
            allowSorting: true,
            allowEditing: true,
          },
          {
            fieldName: "thang_bd",
            headerText: "Tháng BĐ",
            textAlign: "left",
            allowFiltering: true,
            allowSorting: true,
            allowEditing: true,
          },
          {
            fieldName: "thang_kt",
            headerText: "Tháng KT",
            textAlign: "left",
            allowFiltering: true,
            allowSorting: true,
            allowEditing: true,
          },
          {
            fieldName: "datcoc_csd",
            headerText: "Đặt cọc CSD",
            type: "Number",
            format: "N",
            textAlign: "right",
            allowFiltering: true,
            allowSorting: true,
            allowEditing: true,
          },
          {
            fieldName: "tien_td",
            headerText: "Tiền trừ dần",
            type: "Number",
            format: "N",
            textAlign: "right",
            allowFiltering: true,
            allowSorting: true,
            allowEditing: true,
          },
        ],
      },
        donvi: "",
        nvql: "",
        nvtc: "",
        load_first: true
    };
  },
  created() {
    this.getDanhSachDonVi();
    this.load_first = false
  },
  watch: {
      donvi(newdata) {
      this.donvi = newdata
      if(this.load_first == false)
      {
        this.getDanhSachNVQL();        
      }
    },
     nvql(newdata) {
      this.nvql = newdata
      if(this.load_first == false)
      {
        this.getDanhSachNVTC();
      }
      
    },
  },
  props: {
    value: Object,
  },
  methods: {

  async  getDanhSachDonVi() {
      let res =  await api.getDanhSachDonVi(this.axios, {
      })
      let kq = res.data.data ? res.data.data : []
      let items = [];
      kq.forEach(function (item) {
        if(item.ten_dv != null)
        {
          items.push({ id: item.donvi_id, text: item.ten_dv  })
        }
      });
      this.options.danhsachdonvi = items;
      this.donvi = items[0].id;
    },


  async getDanhSachNVQL() {
      let res =  await api.getDanhSachNVQL(this.axios, {
          donviql_id: this.donvi,
        })
      let kq = res.data.data ? res.data.data : []
      let items = [];
      kq.forEach(function (item) {
      if(item.ghepten != null)
      {
        items.push({ id: item.nhanvien_id, text: item.ghepten });
      }
        
      });
      this.options.danhsachnvql = items;
      this.nvql = items[0].id;

    },

  async  getDanhSachNVTC() {
      var v_nvql = "-1";
      if (this.nvql != "") {
        v_nvql = this.nvql;
      }
      let res =  await api.getDanhSachNVTC(this.axios, {
          nhanvienql_id: v_nvql,
        })
      let kq = res.data.data ? res.data.data : []
      let items = [];
      kq.forEach(function (item) {
        if(item.gheptuyen != null)
        {
          items.push({ id: item.tuyenthu_id, text: item.gheptuyen });
        }
        
      });
      this.options.danhsachnvtc = items;
      this.nvtc = items[0].id;
    },





    async getDanhSach() {
      this.loading(true);
console.log("nhanvienql_id",await this.$root.token.getNhanVienID())
      const res = await  api.getDanhSach(this.axios, {
                "donviql_id": -1,
                "nhanvienql_id": await this.$root.token.getNhanVienID(),
                "tuyenthu_id": 1,
                "dagan": 3
          })
      this.options.danhsach = res.data.data ? res.data.data : [];

      console.log('this.options.danhsach', this.options.danhsach )

      if(this.options.danhsach.length < 1)
      {
        this.$toast.error('Không có dữ liệu tìm kiếm!');
      }
      this.loading(false);
    },

  async  sendSMS()
    {
      let sdt="";
      let noidung="";
      for (var i = this.options.danhsach.length - 1; i >= 0; i--) {
            sdt =""
            noidung =""
            sdt=this.options.danhsach[i].so_dt
            noidung=this.options.danhsach[i].noidung

            const res = await api.sendSMS(this.axios, {
            msisdn: sdt,
            content: noidung
            })
            if(res.data.errorCode != 0)
            {
              this.$toast.error("Lỗi: "+ sdt + " - "+ res.data.faultString);
            }

        }
    },


rowSelected(subject) {

this.vrkm_id = subject.data.rkm_id
      
},



    clickButton(key) {

      if (key == "timkiem") {
        this.getDanhSach();
      }

      if (key == "nhantin") {
        if(this.options.danhsach.length < 1)
        {
          this.$toast.error('Bạn chưa chọn dữ liệu trên lưới để cập nhật!')
          return
        }
        this.sendSMS();
      }
      if (key == "inphieuthu") {
        if(this.options.danhsach.length < 1)
        {
          this.$toast.error('Chưa có thông tin!')
          return
        }
        this.xembaocao.ma_bc = 'PHIEUTHU_GHDC'
        this.xembaocao.dsGiaTri.push(this.vrkm_id)
        this.xembaocao.dsGiaTri.push("-1")
        this.xembaocao.dsGiaTri.push("-1")
        this.xembaocao.dsGiaTri.push("4")
        this.$refs.popupXemBaoCao.XemNgay()
        return
      }
      if (key == "indangky") {
        if(this.options.danhsach.length < 1)
        {
          this.$toast.error('Chưa có thông tin!')
          return
        }
        this.xembaocao.ma_bc = 'GIAHAN_DC'
        this.xembaocao.dsGiaTri.push(this.vrkm_id)
        this.xembaocao.dsGiaTri.push("-1")
        this.xembaocao.dsGiaTri.push("-1")
        this.xembaocao.dsGiaTri.push("4")
        this.$refs.popupXemBaoCao.XemNgay()
      }
      if (key == "chuyenhopdong") {
        if(this.options.danhsach.length < 1)
        {
          this.$toast.error('Chưa có thông tin!')
          return
        }
         this.$refs.popupPopupChuyenHD.showModal()
      }
    },
  
  },
};
</script>
