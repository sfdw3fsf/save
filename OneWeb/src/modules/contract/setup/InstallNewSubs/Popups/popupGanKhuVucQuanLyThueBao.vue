<template>
  <div class="modal-content popup-box">
    <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>Gán khu vực quản lý thuê bao</div>
        <div class="close -ap icon-close" data-dismiss="modal" @click.prevent="closeForm">
        </div>
    </div>
    <div class="list-actions-top">
      <ul class="list" style="width:100%;">
        <li>
          <a @click="chapnhan()">
            <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp
            nhận
          </a>
        </li>
        <li v-if="dulieu.hdtb_id && dulieu.hdtb_id>0">
            <a href="#" @click.prevent="tsbtnCapNhat_Click">
                <span class="icon one-save"></span> Cập nhật
            </a>
        </li>
        <li style="position:absolute; right:0;">
            <a href="#" @click.prevent="terminateForm">
                <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát
            </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form padt8 padb8 fw6 upper">
        TÌM KIẾM THÔNG TIN KHU VỰC
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="box-form" style="min-height: 205px">
            <div class="legend-title">Thông tin địa danh</div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <vue-element labelWidth="40" label="Quận">
                  <div class="info-row">
                    <div class="value padt7 w20">
                      <div class="check-action">
                        <input
                          type="checkbox"
                          class="check"
                          v-model="isEnable.quan"
                        />
                        <span class="name"></span>
                      </div>
                    </div>
                    <select2 v-model="model.quan_id" class="select-custom" :class="{disabled:!isEnable.quan}" :options="options.quan.map(ls=>({id:ls.QUAN_ID,text:ls.TEN_QUAN}))"></select2>
                  </div>
                </vue-element>
                <vue-element labelWidth="40" label="Phố">
                  <div class="info-row">
                    <div class="value padt7 w20">
                      <div class="check-action">
                        <input
                          type="checkbox"
                          class="check"
                          v-model="isEnable.pho"
                        />
                        <span class="name"></span>
                      </div>
                    </div>
                    <select2 v-model="model.pho_id" class="select-custom" :class="{disabled:!isEnable.pho}" :options="options.pho.map(ls=>({id:ls.PHO_ID,text:ls.TEN_PHO}))"></select2>
                  </div>
                </vue-element>
                <vue-element labelWidth="40" label="Khu">
                  <div class="info-row">
                    <div class="value padt7 w20">
                      <div class="check-action">
                        <input
                          type="checkbox"
                          class="check"
                          v-model="isEnable.khu"
                        />
                        <span class="name"></span>
                      </div>
                    </div>
                    <select2 v-model="model.khu_id" class="select-custom" :class="{disabled:!isEnable.khu}" :options="options.khu.map(ls=>({id:ls.PHO_ID,text:ls.TEN_PHO}))"></select2>
                  </div>
                </vue-element>
              </div>
              <div class="col-sm-6 col-12">
                <vue-element labelWidth="70" label="Phường">
                  <div class="info-row">
                    <div class="value padt7 w20">
                      <div class="check-action">
                        <input
                          type="checkbox"
                          class="check"
                          v-model="isEnable.phuong"
                        />
                        <span class="name"></span>
                      </div>
                    </div>
                    <select2 v-model="model.phuong_id" class="select-custom" :class="{disabled:!isEnable.phuong}" :options="options.phuong.map(ls=>({id:ls.PHUONG_ID,text:ls.TEN_PHUONG}))"></select2>
                  </div>
                </vue-element>
                <vue-element labelWidth="70" label="Ấp">
                  <div class="info-row">
                    <div class="value padt7 w20">
                      <div class="check-action">
                        <input
                          type="checkbox"
                          class="check"
                          v-model="isEnable.ap"
                        />
                        <span class="name"></span>
                      </div>
                    </div>
                    <select2 v-model="model.ap_id" class="select-custom" :class="{disabled:!isEnable.ap}" :options="options.ap.map(ls=>({id:ls.PHO_ID,text:ls.TEN_PHO}))"></select2>
                  </div>
                </vue-element>
                <vue-element labelWidth="70" label="Đặc điểm">
                  <div class="info-row">
                    <div class="value padt7 w20">
                      <div class="check-action">
                        <input
                          type="checkbox"
                          class="check"
                          v-model="isEnable.dacdiem"
                        />
                        <span class="name"></span>
                      </div>
                    </div>
                    <select2 v-model="model.dacdiem_id" class="select-custom" :class="{disabled:!isEnable.dacdiem}" :options="options.dacdiem.map(ls=>({id:ls.DACDIEM_ID,text:ls.DACDIEM}))"></select2>
                  </div>
                </vue-element>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="box-form" style="min-height: 205px">
            <div class="legend-title">Thông tin khu vực</div>
            <vue-element labelWidth="80" label="Trạm TC">
              <div class="info-row">
                <div class="value padt7 w30">
                  <div class="check-action">
                    <input
                      type="checkbox"
                      class="check"
                      v-model="isEnable.tram_tc"
                    />
                    <span class="name"></span>
                  </div>
                </div>
                <select2 v-model="model.tramtc_id" class="select-custom disabled" :options="options.tram_tc.map(ls=>({id:ls.donvi_id,text:ls.ten_dv}))"></select2>
              </div>
            </vue-element>
            <vue-element labelWidth="80" label="Khu vực">
              <div class="info-row">
                <div class="value padt7 w30">
                </div>
                <ComboboxGrid  class="select-custom"
                    :columns="[
                    {fieldName: 'khuvuc_id', headerText: 'Mã khu vực', allowFiltering: true, width:'70'},
                    {fieldName: 'ten_kv', headerText: 'Tên khu vực', allowFiltering: true, width:'300'}
                    ]"
                    :dataSource="options.khuvuc" textField="ten_kv" valueField="khuvuc_id"
                    @selectedChanged="(item)=>{if(item) model.khuvuc_id=item.khuvuc_id;}">
                </ComboboxGrid>
                <!-- <select2 v-model="model.khuvuc_id" class="select-custom" :options="options.khuvuc.map(ls=>({id:ls.khuvuc_id,text:ls.ten_kv}))"></select2> -->
              </div>
            </vue-element>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title">Danh sách nhân viên trong khu vực</div>
        <DataGrid
                v-bind:columns="collumns.dsNhanvienTrongKhuvuc"
                v-bind:dataSource="options.dsNhanvienTrongKhuvuc"
                :showColumnCheckbox="false"
                :showFilter="true"
                :allowPaging="true"
                :enablePagingServer="false"
                panelDataHeight="200"
          ></DataGrid>
      </div>
      <div class="box-form">
        <div class="legend-title">
          Danh sách nhân viên địa bàn theo thuê bao
        </div>
        <DataGrid
                v-bind:columns="collumns.dsNhanvienByHDTB"
                v-bind:dataSource="options.dsNhanvienByHDTB"
                :showColumnCheckbox="false"
                :showFilter="true"
                :allowPaging="true"
                :enablePagingServer="false"
                panelDataHeight="200"
          ></DataGrid>
      </div>
    </div>
  </div>
</template>
<style scoped>
.disabled { pointer-events: none; color:lightgray; border-color:lightgray; }
</style>
<script>
import VueSelect from '../../../../search/subscriber/components/form/VueSelect.vue';
export default {
  components: { VueSelect },
  name: "popupGanKhuVucQuanLyThueBao",
  props: ["modalId", "data","dulieu", "isStrictMode", "donvi_id", "hdtb_id"],
  watch: {
    async "model.quan_id"(val) {
      if(val)
        await this.getPhuong()
      else
        this.options.phuong = []
    },
    async "model.phuong_id"(val) {
      if(val)
        await this.getPhoApKhu()
      else {
        this.options.pho = []
        this.options.ap = []
        this.options.khu = []
      }
    },
    async "changeDiachi"(val) {
      await this.getDacDiem()
    },
    async "requiredChangeDiachi"(val) {
      if(val)
        await this.getKhuVuc()
    },
    async "model.khuvuc_id"(val) {
      if(val)
        this.getNVtheokhuvuc()
    },
    async 'dulieu.hdtb_id'(val) {
      if(val) this.getNhanvienByHDTB();
    }
  },
  data() {
    return {
      collumns: {
        dsNhanvienByHDTB: [
          {
            fieldName: "ten_dv",
            headerText: "Đơn vị",
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: "ma_nv",
            headerText: "Mã NV",
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: "ten_nv",
            headerText: "Tên nhân viên",
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: "nhiemvu",
            headerText: "Nhiệm vụ",
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: "ten_loainv",
            headerText: "Loại nhân viên",
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: "so_dt",
            headerText: "Số điện thoại",
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: "daucuoi",
            headerText: "Điểm",
            allowFiltering: true,
            allowSorting: false,
            width: 100
          }
        ],
        dsNhanvienTrongKhuvuc: [
           {
            fieldName: "ten_dv",
            headerText: "Đơn vị",
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: "ma_nv",
            headerText: "Mã NV",
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: "ten_nv",
            headerText: "Tên nhân viên",
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: "nhiemvu",
            headerText: "Nhiệm vụ",
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: "ten_loainv",
            headerText: "Loại nhân viên",
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: "so_dt",
            headerText: "Số điện thoại",
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: "ma_kv",
            headerText: "Mã khu vực",
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: "ten_kv",
            headerText: "Tên khu vực",
            allowFiltering: true,
            allowSorting: false,
            width: 100
          }
        ]
      },
      options: {
        quan: [],
        phuong: [],
        pho: [],
        ap: [],
        khu: [],
        dacdiem: [],
        khuvuc: [],
        tram_tc: [],
        dsNhanvienTrongKhuvuc: [],
        dsNhanvienByHDTB: []
      },
      model: {
        quan_id: null,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        tramtc_id: 0,
        khuvuc_id: 0,
      },
      isEnable: {
        quan: false,
        pho:false,
        khu:false,
        ap:false,
        phuong: false,
        dacdiem: false,
        khuvuc: false,
        dacdiem: false,
        tram_tc: false
      },
    };
  },
  async mounted() {


    await this.getQuan()
    await this.getTramTC()
    await this.getNhanvienByHDTB()
    if(this.dulieu && this.dulieu.diachi) {
      if(this.dulieu.diachi.QUAN_ID) {this.model.quan_id = (this.dulieu.diachi.QUAN_ID);this.isEnable.quan = true;}
      if(this.dulieu.diachi.PHUONG_ID) {this.model.phuong_id = (this.dulieu.diachi.PHUONG_ID);this.isEnable.phuong = true;}
      if(this.dulieu.diachi.PHO_ID) {this.model.pho_id = (this.dulieu.diachi.PHO_ID);this.isEnable.pho = true;}
      if(this.dulieu.diachi.KHU_ID) {this.model.khu_id = (this.dulieu.diachi.KHU_ID);this.isEnable.khu = true;}
      if(this.dulieu.diachi.AP_ID) {this.model.ap_id = (this.dulieu.diachi.AP_ID);this.isEnable.ap = true;}
      if(this.dulieu.diachi.DACDIEM_ID) {this.model.dacdiem_id = (this.dulieu.diachi.DACDIEM_ID);this.isEnable.đaciem = true;}
    }
    // if(this.dulieu && this.dulieu.hdtb_id && this.dulieu.hdtb_id>0) this.dulieu.hdtb_id = this.dulieu.hdtb_id;
    // if(this.dulieu && this.dulieu.donvi_id && this.dulieu.donvi_id>0) this.dulieu.donvi_id = this.dulieu.donvi_id;
    this.getKhuVuc();
    this.$forceUpdate()

  },
  computed: {
    changeDiachi() {
      return this.model.quan_id + this.model.phuong_id + this.model.pho_id + this.model.khu_id + this.model.ap_id
    },
    requiredChangeDiachi() {
      return this.model.quan_id + this.model.phuong_id + this.model.pho_id + this.model.dacdiem_id + this.model.tramtc_id + this.model.ap_id + this.model.khu_id + this.isEnable.quan + this.isEnable.phuong + this.isEnable.pho + this.isEnable.ap + this.isEnable.ap + this.isEnable.khu + this.isEnable.dacdiem + this.isEnable.tram_tc
    },
    thaydoiDiaChi: function () {
      if (this.tentinh != "") {
        this.diachi.diachimoi = this.tentinh;
        this.diachi.diachimoi = this.tenquan + ", " + this.diachi.diachimoi;
        this.diachi.diachimoi = this.tenphuong + ", " + this.diachi.diachimoi;
        if (this.isPho && this.diachi.pho_id != 0)
          this.diachi.diachimoi = this.tenpho + ", " + this.diachi.diachimoi;
        if (this.isAp && this.diachi.ap_id != 0)
          this.diachi.diachimoi = this.tenap + ", " + this.diachi.diachimoi;
        if (this.isKhu && this.diachi.khu_id != 0)
          this.diachi.diachimoi = this.tenkhu + ", " + this.diachi.diachimoi;
        if (this.diachi.so_nha != null && this.diachi.so_nha != "")
          this.diachi.diachimoi =
            this.diachi.so_nha + ", " + this.diachi.diachimoi;
        return this.diachi.diachimoi;
      }
      return "";
    },
  },
  methods: {
    closeForm() {
      this.$emit("form-close", null);
    },
    terminateForm() {
      this.$emit("form-close", null);
    },
    async getNVtheokhuvuc() {
      try {
        var rs = await this.$root.context.post("/web-thicong/hoinghi_truyenhinh/lay_ds_nhanvien_khuvuc",{
          "phanvung_id": this.$root.token.getPhanVungID(),
          "vkhuvuc_id": this.model.khuvuc_id
        })
        this.options.dsNhanvienTrongKhuvuc = rs.data;
      } catch (err) {

      }
    },
    async getKhuVuc() {
      try {
        var rs = await this.$root.context.post("/web-hopdong/thaydoiloaihinhtbcodinhims/sp_lay_khuvuc_theo_diadanh",{
          loaikv_id:  4,
          quan_id: this.isEnable.quan?this.model.quan_id:0,
          phuong_id:  this.isEnable.phuong?this.model.phuong_id:0,
          pho_id:  this.isEnable.pho?this.model.pho_id:0,
          ap_id:  this.isEnable.ap?this.model.ap_id:0,
          khu_id:  this.isEnable.khu?this.model.khu_id:0,
          dacdiem_id:  this.isEnable.dacdiem?this.model.dacdiem_id:0,
          tramtc_id:  this.isEnable.tram_tc?(this.dulieu.donvi_id ? this.model.tramtc_id : 0):0
        })
        if(rs.data.length == 0) {
          this.options.khuvuc = [{
            ten_kv: 'Không có khu vực',
            khuvuc_id: 0
          }]
          this.model.khuvuc_id = 0
        } else this.options.khuvuc = rs.data
        if(this.options.khuvuc && this.options.khuvuc.length>0) this.model.khuvuc_id = this.options.khuvuc[0].khuvuc_id;
      } catch (e) {

      }
    },
    getTramTC: async function () {
      try {
        var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/sp_lay_ds_donvi_tramtc",{
          "donvi_id": this.dulieu.donvi_id,
          "loaidv_id": 5,
          "phanvung_id": this.$root.token.getPhanVungID()
        })
        this.options.tram_tc = rs.data?rs.data:[]
      } catch (e) {

      }
    },
    getQuan: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-cabman/bando_tuyencot/lay_ds_quan_huyen/" + (this.dulieu.diachi.TINH_ID?this.dulieu.diachi.TINH_ID:this.$root.token.getPhanVungID())
        );
        this.options.quan = rs.data
      } catch (error) {
        console.log(error)
      } finally {
        this.$root.showLoading(false);
      }
    },
    getPhuong: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-cabman/bando_tuyencot/lay_ds_phuong_xa/" + this.model.quan_id
        );
        this.options.phuong = rs.data;
      } catch (error) {
        console.log(error);
      }
    },
    getPhoApKhu: async function () {
      try {
        this.$root.showLoading(true);
        var rs = await this.$root.context.get(
          "/web-cabman/bando_tuyencot/lay_ds_pho_ap_khu/" +
            this.model.phuong_id
        );
        if (rs.data != null) {
          this.options.pho = rs.data.dsPho;
          this.options.khu = rs.data.dsKhu;
          this.options.ap  = rs.data.dsAp;
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    getDacDiem: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-cabman/bando_tuyencot/lay_ds_dacdiem",
          {
            phuong_id: this.model.phuong_id == null ? 0 : this.model.phuong_id,
            pho_id: this.model.pho_id == null ? 0 : this.model.pho_id,
            ap_id: this.model.ap_id == null ? 0 : this.model.ap_id,
            khu_id: this.model.khu_id == null ? 0 : this.model.khu_id,
          }
        );
        this.options.dacdiem = rs.data;
      } catch (error) {
      }
    },
    async getNhanvienByHDTB() {

      try {
        var rs = await this.$root.context.post("/web-thicong/hoinghi_truyenhinh/lay_ds_nhanvien_kv_theo_hdtb_id",
          {
            vhdtb_id: this.dulieu.hdtb_id
          }
        );
        this.options.dsNhanvienByHDTB = rs.data;
      } catch (error) {
      }
    },
    saveDiachi: async function () {
      try {
        this.$root.showLoading(true);
      } catch (error) {
        this.$toast.error("Lưu ảnh thất bại");
      } finally {
        this.$root.showLoading(false);
      }
    },
    async chapnhan() {

        var data = [{
            HDTB_ID : this.dulieu.hdtb_id?this.dulieu.hdtb_id:0,
            KHUVUC_ID : this.model.khuvuc_id,
            LOAIKV_ID  : 4,
            NGAY_CN : new Date().toLocaleDateString('en-GB'),
            MAY_CN : this.$root.token.getMaNhanVien(),
            NGUOI_CN : this.$root.token.getNguoiDungID(),
            IP_CN : "xxx",
            KIEUKV_ID : 0
          }];
        if(this.dulieu.hdtb_id) {
          try {
            //var rs = await this.$root.context.post("/web-thicong/hoinghi_truyenhinh/sp_insert_hdtb_kv",
            var rs = await this.$root.context.post("/web-hopdong/hieuchinhhopdong/sp_ins_hdtb_kv_v2",
              {
                js_hdtb_kv: JSON.stringify(data)
              }
            );
            this.$toast.success("Gán khu vực thuê bao thành công")
          } catch (error) {
            this.$toast.error(error.message ? error.message : "Đã có lỗi xảy ra")
          }
        } else {
          if(!this.model.quan_id && !this.model.phuong_id) {
            this.$toast.error("Chưa có thông tin về địa chỉ lắp đặt. Bạn hãy kiểm tra lại!")
            return false
          }
        }
        if(this.options.khuvuc.filter(x=>x.khuvuc_id==this.model.khuvuc_id).length > 0) {
          this.$emit('chapnhan', {ma_kv: this.options.khuvuc.filter(x=>x.khuvuc_id==this.model.khuvuc_id)[0]['ten_kv'],hdtb_kv_data:data,khuvuc_id:this.model.khuvuc_id});
          this.$emit('form-close', {ma_kv: this.options.khuvuc.filter(x=>x.khuvuc_id==this.model.khuvuc_id)[0]['ten_kv'],hdtb_kv_data:data,khuvuc_id:this.model.khuvuc_id});
        } else {
          this.$toast.error("Chưa có thông tin về địa chỉ lắp đặt. Bạn hãy kiểm tra lại!")
        }
    },
    async tsbtnCapNhat_Click() {
      if(!this.model.khuvuc_id || this.model.khuvuc_id<=0) {
        this.$toast.error("Bạn chưa chọn khu vực quản lý thuê bao!");
        return;
      }
      var data = [{
          HDTB_ID : this.dulieu.hdtb_id?this.dulieu.hdtb_id:0,
          KHUVUC_ID : this.model.khuvuc_id,
          LOAIKV_ID  : 4,
          NGAY_CN : new Date().toLocaleDateString('en-GB'),
          MAY_CN : await this.$root.token.getMachine(),
          NGUOI_CN : this.$root.token.getNguoiDungID(),
          IP_CN : await this.$root.token.getIP(),
          KIEUKV_ID : 0
        }];
      if(this.dulieu.hdtb_id) {
        try {
          var rs = await this.$root.context.post("/web-thicong/hoinghi_truyenhinh/sp_insert_hdtb_kv",
            {
              js_hdtb_kv: data
            }
          );
          this.$toast.success("Cập nhật dữ liệu thành công!");
        } catch (error) {
          this.$toast.error(error.message ? error.message : "Đã có lỗi xảy ra")
        }
      }
    },
  },
};
</script>
