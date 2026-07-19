<template src="./CheckIT.html">
</template>
<style>

  .e-contextmenu-wrapper.e-grid-menu.e-lib.e-keyboard ul.e-menu-parent.e-ul
  {
    display: block !important;
    top:150px!important;
    overflow-y:scroll!important;
    height: 80%!important;
  }
</style>
<script>
import breadcrumb from "@/components/breadcrumb";
import API from "./API";
import Vue from "vue";
import { DropDownListPlugin } from "@syncfusion/ej2-vue-dropdowns";
import KTable from "@/components/KTable.vue";
import ActionTop from "@/components/ActionTop.vue";
import popupLayDuLieu from "./popupLayDuLieu.vue";
import searchAccount from '@/modules/search/subscriber/SearchAccount';
import popPhieuDangKy from '@/modules/contract/RegisterBusinessNumberRange/PhieuDangKy.vue';
import Const from './Const';

Vue.use(DropDownListPlugin);
export default {
  components: { breadcrumb, KTable, ActionTop,popupLayDuLieu,searchAccount,popPhieuDangKy },
  data: function () {
    return {
     header: {
        title: 'Đối soát dữ liệu VNPT – IT',
        list: [
          {
            name: 'Quản lý DB',
            link: { name: 'Ui.cards' }
          },
          {
            name: 'Đối soát dữ liệu VNPT – IT',
            link: { name: 'Ui.buttons' }
          },
        ]
      },

     dataCheckeds_ds_loaihinh:[],
     trangthai_selectted:"",
     isDisplayMenu: false,
     xuatPhieuShow: false,
      params: {
        p_loaidanhsach_option: [],
        p_loai: null,
        p_thuebao_ct_id:null,
        p_thuebao_id:null,
        p_taget:null,
        p_ghichu:null,
        p_id_cn:null,
        p_row_id:null,
        p_ma_tb_tinh:null,
        p_ma_tb:"",
        p_thuthu:null,
        p_fileid:null,
        p_origini:null,

        p_thuebao_goc_chuagan_id:null,
        p_ghichu_goc_chuagan:null,
        p_id_cn_goc_chuagan:null,


        p_thuebao_ct_chuagan_id:null,
        p_ghichu_ct_chuagan:null,


      },
      ds_donvi_option: [],
      txt_donvi:"",
      ds_cautraloi_table: [],
      ds_dulieu_thuebao_table: [],
      ds_thuebao_goc_chuagan: [],
      ds_thuebao_chitiet_chuagan: [],
      danhsach_loaihinh:[],
      ds_loaihinh_id:[],
      bancheo_id:0,
      file_id:0,
      loai_hinh_id:0,
      trangthai_id:0,
      loaihinh_id:-1,
      p_loaitb_id:0,
      p_loaitb_goc_chuagan_id:0,
      p_loaitb_ct_chuagan_id:0,
      txtMaTB:"",
      position: { X: 'center', Y: 'top' },
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: "",
      popupComponentEvents: {
        "form-close": "popupClosed",
        acceptChangeCTV: "popupClosed",
        acceptChangeNGT: "popupClosed",
      },
      dataLayDuLieu: {
            danhsach: [],
            trangthai_id: 0
      },
      button:{
          tsbtnGhepThueBao: {
            visible: false,
            enabled: true,
            type: "menu_button",
          },
          tsbtnGhepChiTiet: {
              visible: false,
              enabled: true,
              type: "menu_button",
            },

          tsbtnThemChiTiet: {
              visible: false,
              enabled: true,
              type: "menu_button",
            },

      },
       dsTBGoc: {
              visible: false
            },

      columns_ds_dulieu_tb: [
        {
          fieldName: "rowid",
          headerText: "rowid",
          allowFiltering: true,
          allowSorting: true,
          visible:false,

        },
        {
          fieldName: "thutu",
          headerText: "TT",
          allowFiltering: true,
          allowSorting: true,

        },
        {
          fieldName: "ma_tb",
          headerText: "Mã TB",
          allowFiltering: true,
          allowSorting: true,

        },
        {
            fieldName: "ma_tb_tinh",
            headerText: "Mã TB tỉnh",
            allowFiltering: true,
            allowSorting: false,
            textAlign: "Right",
            template: function () {
              return {
                template: Vue.component("ChonTemplate", {
                  template: `<div style="border:none">
                            <input v-model="data.ma_tb_tinh" type="text" style="border:none" :readonly="true"/>
                            <span class="-ap icon-more_horiz" v-on:click="handleLeftClick"></span>
                                  </div>
                      `,
                  data() {
                    return { data: {} };
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent;
                    },
                  },
                  methods: {
                    handleLeftClick() {
                     this.parent.onClickLoadAccount()
                    },

                  },
                }),
              };
            },
        },
        {
          fieldName: "ten_tb",
          headerText: "Tên TB tỉnh",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "ma_tb_ct_tinh",
          headerText: "Mã chi tiết",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "ten_tb_ct",
          headerText: "Mã chi tiết tỉnh",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "trangthai",
          headerText: "Trạng thái xử lý",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "ten_kh",
          headerText: "Tên KH",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "diachi_kh",
          headerText: "Địa chỉ KH",
          allowFiltering: true,
          allowSorting: true,
        },

        {
          fieldName: "diachi_ld",
          headerText: "Địa chỉ lắp đặt tỉnh",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "ghichu",
          headerText: "Ghi chú",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "so_dt",
          headerText: "Số ĐT",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "loaitb_id",
          headerText: "Loại hình TB",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "mst",
          headerText: "MST",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "nguoi_dd",
          headerText: "Người đại diện",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "email",
          headerText: "Email",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "domain",
          headerText: "Domain",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "ma_bhxh",
          headerText: "Mã BHXH",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "ma_csyt",
          headerText: "Mã CSYT",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "ma_truong_hoc",
          headerText: "Mã trường học",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "ma_nha_thuoc",
          headerText: "Mã nhà thuốc",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "hd_pharmacy",
          headerText: "Mã hợp đồng Pharmacy",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "serial_token",
          headerText: "Serial Token",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "parent_serial_token",
          headerText: "Parent Serial Token",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "ngay_ins",
          headerText: "Ngày sử dụng",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "ngay_ins",
          headerText: "Ngày thêm",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "nguoi_cn",
          headerText: "Người cập nhật",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "ngay_cn",
          headerText: "Ngày cập nhật",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "ngay_ins",
          headerText: "Ngày thêm",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "may_cn",
          headerText: "máy CN",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "ip_cn",
          headerText: "IP CN",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "origins",
          headerText: "Nguồn",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "file_id",
          headerText: "File ID",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "account_tao",
          headerText: "Account khởi tạo",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "acc_order",
          headerText: "Nguồn Order",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "dvi_tao",
          headerText: "Đơn vị tạo yêu cầu",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "last_active",
          headerText: "Hoạt động gần nhất",
          allowFiltering: true,
          allowSorting: true,
        },
      ],
      // menuContextItems: Const.contextMenuItems,
       columns_ds_thuebao_goc_chuagan: [
        {
          fieldName: "ma_kh",
          headerText: "Mã KH",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "ten_kh",
          headerText: "Tên KH",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "so_dt",
          headerText: "Số ĐT KH",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "email",
          headerText: "Email KH",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "so_gt",
          headerText: "Mã số thuế",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "ma_tb",
          headerText: "Mã thuê bao",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "diachi_ld",
          headerText: "Địa chỉ LĐ",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "trangthaitb_id",
          headerText: "Trạng thái TB",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "loaitb_id",
          headerText: "loaitb_id",
          allowFiltering: true,
          allowSorting: true,
          visible:false,
        },
        {
          fieldName: "thuebao_id",
          headerText: "thuebao_id",
          allowFiltering: true,
          allowSorting: true,
          visible:false,
        },
       ],
       columns_ds_thuebao_chitiet_chuagan: [
        {
          fieldName: "ten_tb",
          headerText: "Tên TB chi tiết",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "ma_tb",
          headerText: "Mã chi tiết",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "so_dt",
          headerText: "Số ĐT",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "email",
          headerText: "Email",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "diachi_ld",
          headerText: "Địa chỉ lắp đặt",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "so_gt",
          headerText: "Mã số thuế",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "thuebao_id",
          headerText: "thuebao_id",
          allowFiltering: true,
          allowSorting: true,
          visible:false

        },
        {
          fieldName: "loaitb_id",
          headerText: "loaitb_id",
          allowFiltering: true,
          allowSorting: true,
          visible:false

        },

       ],

    };

  },
  validations: {},
  computed: {
    menuContextItems() {
      let menus = [...Const.contextMenuItems];
      let data = [];
      for (let menu of menus) {
        data.push(menu);
      }

      return data;
    }

  },
  watch: {},
  created() {
      this.LoadLoaiThongKe();
      this.load_DS_DonVi();
     // this.LoadDSDonVi();
      //this.load_ds_dulieu_donvi();


  },
  async mounted() {},
  methods: {
    contextMenuClick(args) {
      let data = args.rowInfo.rowData;
      if(args.item.id === 'dstn_danhauhoanthanh') {

        if(this.params.p_thuebao_ct_id==null && this.params.p_thuebao_id==null)
        {
          this.$toast.error("Thuê bao chưa chuẩn hóa")
        }else
        {
          this.update_TrangThaiDoiSoat();

        }

      } else if (args.item.id === 'dstn_khongnhanKH') {
        this.$emit('xoa', data);
      }
    },
      async LoadLoaiThongKe() {
      let items = [];

        items = [
          { id: 1, text: "Chọn trên lưới" },
          { id: 2, text: "Danh sách chưa gán" },
          { id: 3, text: "Chi tiết thuê bao" },
        ];
      this.params.p_loaidanhsach_option = items;
      this.params.p_loai = items[0].id;
    },
     btnShowPopupLayDL()
    {
       this.$refs.lay_du_lieu.showModal();
    },
     acceptlayDuLieu(item) {

       this.trangthai_id = item.trangthai_selectted;
       this.danhsach_loaihinh = item.dataCheckeds_ds_loaihinh;
       this.bancheo_id=item.bancheo_id;

       this.load_ds_dulieu_thuebao();
    },
     getDanhSach(){
        try{
            var result=""
            if(this.danhsach_loaihinh.length>0){
                this.danhsach_loaihinh.forEach((item, index)=>{
                     var row="#1"
                     row=row.replace("#1",item.LOAITB_ID)
                    if(index==this.danhsach_loaihinh.length-1){
                        result=result.concat(row)
                    }else{
                        result=result.concat(row).concat(', ')
                    }
                })
            }
            return result
        }
         catch (error) {
            console.log(error);
        } finally {
            this.loading(false);
        }

    },
    async load_ds_dulieu_thuebao() {
       try {
            this.ds_loaihinh_id=this.getDanhSach();
            let res = await this.axios.post("/web-ccdv/doisoat_it/sp_doisoatit_readdata", {
                              "file_id": this.file_id,
                              "trangthai_xl": this.trangthai_id,
                              "loaitb_id":  this.ds_loaihinh_id,//215,35,39
                              "p_bancheo": this.bancheo_id, })

                if(res.data.error_code === "BSS-00000000" && res.data.data !== undefined  ) {
                     this.ds_dulieu_thuebao_table = res.data.data;
                }else{
                   this.$toast.error("Không có dữ liệu")
                }
         } catch (error) {
          console.log(error);
        } finally {
          this.loading(false);
        }
    },
    async load_ds_dulieu_thuebao_goc_chuagan() {
       try {

            let res = await this.axios.post("/web-ccdv/doisoat_it/sp_lay_ds_thuebao_chua_doisoat", {
                              "loaitb_id": this.p_loaitb_id, })

                if(res.data.error_code === "BSS-00000000" && res.data.data !== undefined  ) {
                     this.ds_thuebao_goc_chuagan = res.data.data;
                }else{}
         } catch (error) {
          console.log(error);
        } finally {
          this.loading(false);
        }
    },
    async load_ds_dulieu_thuebao_chitiet_chuagan() {
       try {
            let res = await this.axios.post("/web-ccdv/doisoat_it/sp_lay_ds_thuebao_theo_loaihinhtb", {
                              "loaitb_id": this.p_loaitb_id, })

                if(res.data.error_code === "BSS-00000000" && res.data.data !== undefined  ) {
                     this.ds_thuebao_chitiet_chuagan = res.data.data;
                }else{}
         } catch (error) {
          console.log(error);
        } finally {
          this.loading(false);
        }
    },
    onChangeLoaiHinh(agrs){

        if(agrs.id==1)
       {
          this.button.tsbtnGhepThueBao.visible = false;
          this.button.tsbtnGhepChiTiet.visible = false;
       }
       if(agrs.id==2)
       {
          this.button.tsbtnGhepThueBao.visible = true;
          this.button.tsbtnGhepChiTiet.visible = false;
          this.load_ds_dulieu_thuebao_goc_chuagan();
       }
       if(agrs.id==3)
       {
          this.button.tsbtnGhepThueBao.visible = true;
          this.button.tsbtnGhepChiTiet.visible = true;
          this.button.tsbtnThemChiTiet.visible = true;
          this.load_ds_dulieu_thuebao_goc_chuagan();
          this.load_ds_dulieu_thuebao_chitiet_chuagan();
       }

    },
    setDisplayDropDownMenu() {
      this.isDisplayMenu = !this.isDisplayMenu;
    },
     hideDropdownMenu(e) {
      if (e.target !== this.$refs.mainWrap) {
        this.isDisplayMenu = false;
      }
    },
    onXuatExcel() {
            if (this.ds_dulieu_thuebao_table.length <= 0)
                this.$toast.error("Chưa có danh sách để xuất excel!");
            else {
                let excelExportProperties = {
                    fileName:"ds_dulieu_thuebao_table.xlsx",
                    dataSource: this.ds_dulieu_thuebao_table
                };
                this.$refs.doisoatGrid.excelExport(excelExportProperties);
            }
        },
    async tsbtnHoanCong_Click(){
       try {
            let res = await this.axios.post("/web-ccdv/doisoat_it/fn_update_dulieu_doisoat", {
                              "loaitb_id": this.p_loaitb_id, })

                if(res.data.error_code === "BSS-00000000" && res.data.data !== undefined  ) {
                     this.ds_thuebao_chitiet_chuagan = res.data.data;
                }else{}
         } catch (error) {
          console.log(error);
        } finally {
          this.loading(false);
        }
    },
     async tsbtnGhepThueBao_Click(){
        if(this.ds_thuebao_goc_chuagan=="" || this.ds_thuebao_goc_chuagan==null)
        {
          this.$toast.error("Danh sách TB rỗng!");
        }
        else{
                   try {
                          let res = await this.axios.post("/web-ccdv/doisoat_it/fn_update_dulieu_doisoat", {
                                              "target": this.params.p_thuebao_goc_chuagan_id ? "thuebao_id" : "thuebao_ct_id",
                                              "target_id": this.params.p_thuebao_goc_chuagan_id,
                                              "row_id": this.params.p_row_id,//"AADOIuADIAADcJjAAA",//
                                              "ghichu": this.params.p_ghichu_goc_chuagan,
                                              "ip_cn": this.$root.token.getUserName()  })
                              if(res.data.error_code === "BSS-00000000" && res.data.data !== undefined  ) {
                                 this.$toast.success("Ghép thành công!");
                                 this.load_ds_dulieu_thuebao();
                              }else{
                                 this.$toast.error("Thuê bao đã được chuẩn hóa. Không thể ghép");
                                }
                      } catch (error) {
                        console.log(error);
                      } finally {
                        this.loading(false);
                      }
            }


    },
    async tsbtnGhepChiTiet_Click(){
       if(this.ds_thuebao_chitiet_chuagan=="" || this.ds_thuebao_chitiet_chuagan==null)
        {
          this.$toast.error("Danh sách TB chi tiết rỗng!");
        }
        else  {

                this.CapNhat_TheoBao_ChiTiet();
        }
    },
     async CapNhat_TheoBao_ChiTiet(){
       try {

            let res = await this.axios.post("/web-ccdv/doisoat_it/fn_update_dulieu_doisoat", {
                                "target": this.params.p_thuebao_ct_chuagan_id ? "THUEBAO_ID" : "THUEBAO_CT_ID",
                                "target_id": this.params.p_thuebao_ct_chuagan_id,
                                "row_id": this.params.p_row_id,//"AADOIuADIAADcJjAAA",//
                                "ghichu": this.params.p_ghichu_ct_chuagan,
                                "ip_cn": this.$root.token.getUserName()  })

                if(res.data.error_code === "BSS-00000000" && res.data.data !== undefined  ) {
                     this.$toast.success("Ghép thành công!");
                     this.load_ds_dulieu_thuebao();

                }else{
                   this.$toast.error("Thuê bao đã được chuẩn hóa. Không thể ghép");
                  }
         } catch (error) {
          console.log(error);
        } finally {
          this.loading(false);
        }
     },
      async Export_Excel(){
          try {
             if (this.ds_dulieu_thuebao_table.length <= 0)
                this.$toast.error("Chưa có danh sách để xuất excel!");
            else {
                let excelExportProperties = {
                    fileName:"ds_dulieu_thuebao_table.xlsx",
                    dataSource: this.ds_dulieu_thuebao_table
                };
                this.$refs.doisoatGrid.excelExport(excelExportProperties);
            }


         } catch (error) {
          console.log(error);
        } finally {
          this.loading(false);
        }
      },
     async tsbtnThongKeTheoFile_Click(){
       this.Export_Excel();
    },
     async tsbtnThongKeTheoLoaiHinh_Click(){
       this.Export_Excel();
    },
     async tsbtnThongKeTheoNgay_Click(){
      this.Export_Excel();
    },
     async tsbtnThemChiTiet_Click(){
        if(this.ds_thuebao_chitiet_chuagan=="" || this.ds_thuebao_chitiet_chuagan==null)
        {
          this.$toast.error("Danh sách TB chi tiết rỗng!");
        }
        else  {
               this.CapNhat_TheoBao();
        }

    },
    gridSelectedChiTiet: function (value) {
         this.params.p_loaitb_ct_chuagan_id = value.loaitb_id;
         this.params.p_thuebao_ct_chuagan_id = value.thuebao_id;
         this.params.p_ghichu_ct_chuagan = value.ma_tb;


    },
    gridSelectedItems(value) {
         this.p_loaitb_id=value.loaitb_id;
         this.params.p_thuebao_id = value.thuebao_id;
         this.params.p_thuebao_ct_id = value.thuebao_ct_id;
         this.params.p_ghichu = value.ten_tb_ct;
         this.params.p_row_id = value.rowid;
         this.params.p_ma_tb_tinh=value.ma_tb_tinh;
         this.params.p_ma_tb=value.ma_tb;


        this.params.p_thuthu=value.thutu;
         this.params.p_fileid=value.file_id;
         this.params.p_origini=value.origins;


    },

    gridSelected_TBGoc_ChuaGan_Items(value) {
         this.p_loaitb_goc_chuagan_id=value.loaitb_id;
         this.params.p_thuebao_goc_chuagan_id = value.thuebao_id;
         this.params.p_ghichu_goc_chuagan = value.ma_tb;

    },

    searchAccountDialogClose(data) {
      if (data != null) {
         this.$toast.error("Bạn chưa chọn thuê bao trên lưới");
      }
      this.$refs.searchAccountDialog.hide()
    },
    onClickLoadAccount() {
      this.$refs.searchAccountDialog.show()

    },
     async load_DS_DonVi() {
           let res = await this.axios.post("web-hopdong/thaydoi_thongso_megawan/post_list_v2", {
                          "p_id": "DOISOATIT_CHUYEN_DONVI_VNP",
                          "p_para1": null,
                          "p_para2": null,
                          "p_para3": null,
                          "p_para4": null,
                          "p_para5": null,
             })
              if(res.data.error_code === "BSS-00000000" && res.data.data !== undefined  ) {
                    let items = [];
                    if (res.data.data !== undefined) {
                        res.data.data.forEach(function (item) {
                            items.push({ id: item.ID, text: item.MA });
                    });
                        this.ds_donvi_option = items;
                    }
              }
    },
     async update_TrangThaiDanhDauHoanThanh() {
       try {
            let res = await this.axios.post("/web-ccdv/doisoat_it/sp_capnhat_trangthai_doisoat", {
                              "tt_xuly": this.trangthai_xl,
                              "rowids": this.params.p_row_id,
                              })

                if(res.data.error_code === "BSS-00000000" && res.data.data === "ok"  ) {
                       this.load_ds_dulieu_thuebao();
                      this.$toast.success("Đánh dấu thành công!");
                }else{

                }
         } catch (error) {
          console.log(error);
        } finally {
          this.loading(false);
        }
    },
    async update_TrangThaiDoiSoat() {
       try {
        debugger;
           var js_data = {
                    vma_tb : this.params.p_ma_tb,
                    vrowid : this.params.p_row_id,
                    vorigins :this.params.p_origini,
                    vtrangthai_xl: 6,
                    vloaitb_id: this.p_loaitb_id,
                    vthutu: this.params.p_thuthu,
                    vfile_id: this.params.p_fileid,
              };

            let res = await this.axios.post("/web-ccdv/doisoat_it/sp_capnhat_trangthai_xl_dsit", {
                              "danhsach":JSON.stringify(js_data),
                              })

                if(res.data.error_code === "BSS-00000000") {
                  this.$toast.success("Đánh dấu thành công!");
                  this.load_ds_dulieu_thuebao();

                }else{

                }
        } catch (error) {
          console.log(error);
        } finally {
          this.loading(false);
        }
    },
  },
};
</script>
