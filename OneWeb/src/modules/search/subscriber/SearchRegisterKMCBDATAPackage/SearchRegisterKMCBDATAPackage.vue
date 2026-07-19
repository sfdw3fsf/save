<template src="./SearchRegisterKMCBDATAPackage.html"></template>
<style src="./SearchRegisterKMCBDATAPackage.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import TraCuuAPI from "./api/TraCuuAPI";
export default {
  components: {
    breadcrumb
  },
  name: "SearchRegisterKMCBDATAPackage",
  mounted() {
  },
  destroyed() {},
  data() {
    return {
      header: {
        title: "Tra cứu, đăng ký gói cước KMCB, DATA",
        list: [
          {
            name: "Tra cứu trên CCBS/Tra cứu, đăng ký gói cước KMCB, DATA",
            link: { name: "Ui.buttons" }
          }
        ]
      },
      KieuDatCoc: "",
      SoMay: "",
      ketqua_ls:undefined,
      lists: undefined,
      listTB: undefined,
      listTK: undefined,
      listGoi: undefined,
      listGoialos: undefined,
      listGoisd: undefined,
      LOAI_TB: "",
      MA_TINH: "",
      SO_MSIN: "",
      PIN: "",
      PUK: "",
      NGAY_KH: "",
      PIN2: "",
      PUK2: "",
      NGAY_KH: "",
      GOI_DI: "",
      GOI_DEN: "",
      MaKH: "",
      TEN_KH: "",
      DCHI_KH: "",
      DIACHI_TT:"",
      DIACHI_CHUNGTU: "",
      NGAYSINH: "",
      GIOITINH:"",
      NGAYCAP: "",
      SOCMT: "",
      DOITUONG: "",
      REMAIN: "0",
      ten_goi: "",
      ten_goi_view:"",
      ten_goi_alo: "",
      ten_goi_data: "",
      GOI_SD: "",
      dsLoaiHinh: [],
      dataGrid: {
        columns: [
          {
            fieldName: "tengoi",
            headerText: "Tên gói",
            allowFiltering: true,
            allowSorting: false,
            width: "30"
          },
          {
            fieldName: "gia",
            headerText: "Giá gói",
            allowFiltering: true,
            allowSorting: false,
            width: "30"
          },
          {
            fieldName: "doi_tuong",
            headerText: "Đối tượng",
            allowFiltering: true,
            allowSorting: false,
            width: "200"
          },
          {
            fieldName: "noidung",
            headerText: "Nội dung",
            allowFiltering: true,
            allowSorting: false,
            width: "200"
          }
        ],
        dataSource: []
      },
      dataGrid_chitiet_alo: {
        columns: [
          {
            fieldName: "id",
            headerText: "Tên gói",
            allowFiltering: true,
            allowSorting: false,
            width: "70"
          },
          {
            fieldName: "package_fee",
            headerText: "Giá",
            allowFiltering: true,
            allowSorting: false,
            width: "70"
          },
          {
            fieldName: "description",
            headerText: "Mô tả",
            allowFiltering: true,
            allowSorting: false,
            width: "200"
          },
        ],
        dataSource: []
      },
      dataGrid_chitiet_data: {
        columns: [
        
          {
            fieldName: "id",
            headerText: "Tên gói",
            allowFiltering: true,
            allowSorting: false,
            width: "70"
          },
          {
            fieldName: "package_fee",
            headerText: "Giá",
            allowFiltering: true,
            allowSorting: false,
            width: "70"
          },
          {
            fieldName: "description",
            headerText: "Mô tả",
            allowFiltering: true,
            allowSorting: false,
            width: "200"
          },
        ],
        dataSource: []
      }
    };
  },

  methods: {
    async doSearch() {
        this.clear()
        this.ten_goi =''
      if (this.SoMay.trim().length !== 9) {
          this.$toast.error('Số ký tự ở ô số máy phải là 9 ký tự!')
          return false
      }
      else
      {
        try {
          await TraCuuAPI.TraCuuDangKyGoiCuocKMCBDATA(this.axios, {
                so_tb: "84" + this.SoMay.trim()
                }) 
                .then(response => {
                if (response &&
                    response.data &&
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data && 
                    response.data.data.length > 0)
                {
                  this.lists = response.data.data
                if (this.lists && Object.keys(this.lists[0]).length === 1 ) {
                    this.$toast.warning(
                      "Không tìm thấy thông tin yêu cầu !"
                    );
                  return;
                }

                if (this.lists[0].LOAI_TB === "GPhone" ) {
                    this.$toast.warning(
                      "Chức năng chỉ tra cứu với thuê bao di động !"
                    );
                  return;
                }
                  this.GOI_DI= this.lists[0].GOI_DI
                  this.GOI_DEN= this.lists[0].GOI_DEN

                  this.SO_MSIN= this.lists[0].SO_MSIN
                  this.LOAI_TB= this.lists[0].LOAI_TB
                  this.MA_TINH= this.lists[0].MA_TINH
                  this.PIN= this.lists[0].PIN
                  this.PIN2= this.lists[0].PIN2
                  this.NGAY_KH= this.lists[0].NGAY_KH
                  this.PUK= this.lists[0].PUK
                  this.PUK2= this.lists[0].PUK2

                var response_ls1 =  TraCuuAPI.TraCuuTTTB_TS(this.axios, {  
                      so_tb: "84"+ this.SoMay.trim()}) 
                      .then(resp => {
                      if (resp && resp.data &&
                        resp.data.error_code === "BSS-00000000" &&
                        resp.data.data.length > 0) {
                        this.ketqua_ls = resp.data.data
                        if (this.lists[0].TRA_SAU === 1)
                        {
                          this.NGAYSINH= this.ketqua_ls[0].NGAY_SINH
                          this.GIOITINH = this.ketqua_ls[0].PHAI
                          this.DOITUONG = this.ketqua_ls[0].DOITUONG
                          this.SOCMT =  this.ketqua_ls[0].SO_GT
                          this.NGAYCAP = this.ketqua_ls[0].NGAYCAP_GT
                          this.TEN_KH = this.ketqua_ls[0].TEN_TB
                          this.DIACHI_CHUNGTU = this.ketqua_ls[0].DIACHI_CHUNGTU
                          this.DIACHI_TT = this.ketqua_ls[0].DIACHI
                          this.DCHI_KH = this.ketqua_ls[0].DIACHI_THUONGTRU 
                        }else
                        {
                          this.NGAYSINH = this.lists[0].NGAYSINH
                          this.SOCMT = this.lists[0].SOCMT
                          this.NGAYCAP = this.lists[0].NGAYCAP
                          this.TEN_KH = this.lists[0].TEN_KH
                          this.DCHI_KH = this.lists[0].DCHI_KH //Địa chỉ giay bao cuoc
                        }
                      }

                    }
                  )
                }
                
              var  resp_alo = TraCuuAPI.TraCuuThongTinALO(this.axios,{ 
                      so_tb: "84"+ this.SoMay.trim()})
              if (resp_alo && resp_alo.data &&
                  resp_alo.data.error_code === "BSS-00000000" &&
                  resp_alo.data.data.result.length > 0)
              {
                  var bien = resp_alo.data.data.result
                  if (bien.charAt(0) === '1' ) {
                      this.ten_goi_alo = bien.split("|")[1];
                  }
              }
              else {
              this.ten_goi_alo = ""
              }

              var  resp_data = TraCuuAPI.TraCuuThongTinDATA(this.axios,{
                      so_tb: "84"+ this.SoMay.trim()})
              if ( resp_data && resp_data.data &&
                    resp_data.data.error_code === "BSS-00000000" &&
                    resp_data.data.data.result.length > 0)
              {
                  var bien_data = resp_data.data.data.result
                  if (bien_data.charAt(0) === '1' ) {
                      this.ten_goi_data = bien_data.split("|")[1];
                  }
              }
              else {
                    this.ten_goi_data = ""
              }
              // Tra cứu tiền của số máy:
              this.TraCuuGoiCuocDangSD()
              // Tra cứu danh sách gói khuyến mãi hiển thị dưới lưới
              this.TraCuuGoiCuoc()

          })
        this.loading(false)
        } 
        catch (ex) {
          console.log(ex);
          this.ten_goi =''
          this.GOI_SD=''
        }
      }
    },
    
    // Su kien enter
    onMaTBEnter() {
      this.ten_goi =''
      this.GOI_SD=''
      this.doSearch();
    },
        
    async ThongtinchitietALO(){
          this.dataGrid_chitiet_alo.dataSource = []
          await   TraCuuAPI.ThongtinchitietALO(this.axios, {
                  ds_goi: this.ten_goi_alo}) 
                .then(resp => {
              if ( resp && resp.data &&
                    resp.data.error_code === "BSS-00000000") {
                var result = resp.data.data.result
                var datas = []
                var keys = Object.keys(result)
                for (let i=0; i< keys.length;i++){
                  let key = keys[i]
                  let item = {
                              id:key,
                              package_fee: result[key]['package_fee'] ,
                              description:result[key]['description'] 
                            }
                  datas.push(item)
                }
                this.dataGrid_chitiet_alo.dataSource = datas
              }
          })
    },

    async ThongtinchitietDATA(){
          this.dataGrid_chitiet_data.dataSource = []
          await   TraCuuAPI.ThongtinchitietDATA(this.axios, {
                  ds_goi: this.ten_goi_data}) 
                .then(resp => {
              if (resp && resp.data &&
                  resp.data.error_code === "BSS-00000000") {
                var result = resp.data.data.result
                var datas = []
                var keys = Object.keys(result)
                for (let i=0; i< keys.length;i++){
                  let key = keys[i]
                  let item = {
                              id:key,
                              package_fee: result[key]['package_fee'] ,
                              description:result[key]['description'] 
                            }
                  datas.push(item)
                }
                this.dataGrid_chitiet_data.dataSource = datas
              }
          })
    },

    async TraCuuGoiCuocDangSD(){
            await   TraCuuAPI.TraCuuGoiCuocDangSD(this.axios, {  
                    ma_tinh: this.MA_TINH,so_tb: "84"+ this.SoMay 
                    }) 
                  .then(resp => {
                if (resp && resp.data &&
                    resp.data.error_code === "BSS-00000000") {
                  this.GOI_SD = resp.data.data.result
                }else
                {
                  this.GOI_SD = ""
                }
            })
    },

    async Dangkygoicuoc(){
      if (this.ten_goi ==='')
      {
        this.$toast.warning("Chưa chọn gói khuyến mại !!! ");
        return;
      }

        this.$bvModal
          .msgBoxConfirm(
            "Bạn muốn đăng ký gói "+ this.ten_goi_view +" cho số thuê bao 84"+this.SoMay.trim() +" ?",
            {
              title: "",
              size: "xm",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            }
          )
          .then(async (v) => {
            if (!v) {
              return;
            }
              await   TraCuuAPI.Dangkygoicuoc(this.axios, {
                      so_tb: "84" + this.SoMay.trim(),goi_id: this.ten_goi}) 
                    .then(resp => {
                  if (resp && resp.data &&
                    resp.data.error_code === "BSS-00000000") {
                    this.$toast.success("Đăng ký khuyến mại thành công !!!");
                    this.doSearch();
                  } else
                  {
                  this.$toast.error(resp.data.message_detail);
                  return;
                  }
              })
          });
    },

    // Lấy gói ID khi chọn trên lưới:
    Select_thuebao_danhba: function (items) {
      this.ten_goi = items.id;
      this.ten_goi_view =items.tengoi;
    },

    async TraCuuGoiCuoc(){
      this.ten_goi =''
      await TraCuuAPI.TraCuuGoiCuoc(
            this.axios,
            JSON.stringify({
              so_tb: "84" + this.SoMay.trim()
            })
          ).then(response => {
            if (
              response && response.data && response.data.data && response.data.error_code == "BSS-00000000" &&
              response.data.data.result && response.data.data.result.ds_goi && response.data.data.result.ds_goi.length > 0
            ) {
              this.dataGrid.dataSource = response.data.data.result.ds_goi.map(item => {
                return {
                  ...item,
                  text: item.ten_goi
                }
              })
            }
          })
    },

    clear()
    {
      this.LOAI_TB = "",
      this.MA_TINH = "",
      this.SO_MSIN = "",
      this.PIN = "",
      this.PUK = "",
      this.NGAY_KH = "",
      this.PIN2 = "",
      this.PUK2 = "",
      this.NGAY_KH = "",
      this.GOI_DI = "",
      this.GOI_DEN = "",
      this.MaKH = "",
      this.TEN_KH = "",
      this.DCHI_KH = "",
      this.DIACHI_TT = "",
      this.DIACHI_CHUNGTU = "",
      this.NGAYSINH = "",
      this.GIOITINH = "",
      this.NGAYCAP = "",
      this.SOCMT = "",
      this.DOITUONG = "",
      this.REMAIN = "0",
      this.ten_goi = "",
      this.GOI_SD = "",
      this.ten_goi_alo = "",
      this.ten_goi_data = "",
      this.ten_goi_view = "",
      this.dataGrid_chitiet_data.dataSource = [],
      this.dataGrid_chitiet_alo.dataSource = [],
      this.dataGrid.dataSource = []
    },

    async btnTracuuTK_Click(){
              this.loading(true)
              this.REMAIN = ''
              try
              {
                  await TraCuuAPI.tracuu_taikhoan(this.axios,
                  {
                  so_tb: "84" + this.SoMay
                  }).then(response => {
                      this.loading(false)
                      if (response && response.data && response.data.error_code === "BSS-00000000" && 
                      response.data.data && response.data.data.length>0)
                      {
                      let str_vtemp = "1";
                      let index = response.data.data.findIndex((x) => x.ID == str_vtemp);
                      if (index >= 0 && response.data.data[index].ID == "1")
                      this.REMAIN = parseInt(response.data.data[index].VOLUME).toLocaleString('vi-VN');
                      }
                  })
              }
              catch (ex)
              {
                  this.loading(false)   
                  this.REMAIN=''
                  this.$toast.error("Lỗi khi tra cứu thông tin tài khoản."+ex);
              }
          },
      async modalToggle_data(show) {
        if(this.ten_goi_data === "")
        {
        this.$toast.warning("Không có danh sách!");
        return
        }
        if (show) {
          this.$refs["modal-detail-data"].show();
          this.ThongtinchitietDATA()
        } else {
          this.$refs["modal-detail-data"].hide();
        }
      },

      async modalToggle_alo(show) {
        if(this.ten_goi_alo === "")
        {
        this.$toast.warning("Không có danh sách!");
        return
        }
        if (show) {
          this.$refs["modal-detail-alo"].show();
          this.ThongtinchitietALO()
        } else {
          this.$refs["modal-detail-alo"].hide();
        }
      }
    }
  };
</script>
