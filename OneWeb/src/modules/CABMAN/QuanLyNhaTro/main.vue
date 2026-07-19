<template src="./main.html">


</template>

<script>
import themmoi from './ThemMoiNhaTro/main'
import suanhatro from './SuaNhaTro/main'
import moment from 'moment'

var itemThaoTac= function () {
  return {
    template: {
      template: `
        <div class="table-result ghi-chu" style="background: transparent;">
          <div style="display: inline-flex;align-items: center;justify-content: space-around;" class="mb-1 ghi-chu-item">
            <div class="pull-left" style="margin-right: 3px" @click="onClickSua(this.data)">
              <button class="btn btn-second lh12 pad2">
                <span class="fa fa-edit"></span>
              </button>
            </div>
            <div style="margin-left: 3px" class="pull-right" @click="onClickXoa(this.data)">
              <button class="btn btn-second lh12 pad2" >
                <span class="-ap icon-close" style="color:red"></span>
              </button>
            </div>
          </div>
        </div>`,
      data() {
        return {
          data: { },
          textChange: ''
        }
      },
      computed: {
        parent() {
          return this.$parent.$parent.$parent
        }
      },methods:{
        onClickXoa() {
          this.parent.xoaNhaTro(this.data)
        },
        onClickSua() {
          this.parent.SuaNhaTro(this.data)
        }
      },mounted() {
      }
    }
  }
}
export default {
  name: "main",
  components: { themmoi,moment,suanhatro },
  provide: {
  },
  computed: {
  },
  data () {
    return {
      inputSearch: "",
      dsNhaTro : [],
      disableCancel:true,
      first_row :"",
      last_row :"",
      itemThaoTac :itemThaoTac
    }
  },
  mounted() {
    this.NAP_DS_NHATRO()
  },
  methods :{
   tsbtnNhapMoi_Click() {
     this.$refs.themmoi.show(false,this.inputSearch).then(result => {
       console.log("áđá",result)
       if(result.ok ==true){
         this.NAP_DS_NHATRO()
       }
     })
   },
   onSelectedRowChanged (row) {
   },async TRA_CUU(){
      await this.LAY_DS_NHATRO(1,this.inputSearch)
    },
    buttonClear (){
      this.inputSearch = ""
    },async NAP_DS_NHATRO() {
     this.inputSearch =''
     await this.LAY_DS_NHATRO(1,"")
   },async xoaNhaTro(data) {
      this.$bvModal.msgBoxConfirm("Bạn có chắc muốn xóa?",
        {
          title: "",
          centered: true,
          size: "sm",
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy',
        }).then(async (value) => {
        if (value) { await this.$root.context.post(
          `/web-cabman/quanly-nhatro/xoa-nhatro`,
          {
            rowId : data.ID,
            nhanvienid :  this.$root.token.getNhanVienID()
          }
        ).then(res =>{
          if(res.data.result == true){
            this.$root.toastSuccess("Xóa nhà trọ thành công.")
            this.LAY_DS_NHATRO(1,"")
          }
        }).catch(err=>{
          if(err.response.data.message_detail){
            this.$root.toastError(err.response.data.message_detail)
          }else {
            this.$root.toastError(err.response.data.message)
          }
        }).finally(()=>{
          this.loading(false)
        }) }
        })
    },async SuaNhaTro(data) {
     if(!data.ID || !data.THUEBAO_ID){
       return
     }
      this.$refs.suanhatro.show(true,data).then(result => {
        if(result.ok ==true){
          this.NAP_DS_NHATRO()
        }
      })
    }, btnXuatDataMain :function (){
      if(this.dsNhaTro){
        let exportProperties = {
          dataSource: this.dsNhaTro,
          fileName : "ds_nhatro.xlsx"
        }
        this.$refs.gridNhaTro.excelExport(exportProperties)
      }else {
        this.$toast.error("Không có dữ liệu để xuất Excel !");
      }

    }
    ,LAY_DS_NHATRO : async function(vmode,search){
     if( vmode == 0){
       this.loading(true)
       await this.$root.context.get(
         "/web-cabman/quanly-nhatro/lay-chitiet-nhatro?query=" + "" +"&first_row=" + this.first_row +"&last_row="+this.last_row
       ).then(res =>{

       }).catch().finally(()=>{
         this.loading(true)
       })

     }else {

     }
     this.loading(true)
     await this.$root.context.get(
       "/web-cabman/quanly-nhatro/lay-chitiet-nhatro?query=" + search +"&first_row=" + 1 +"&last_row="+ 1000000
     ).then(res =>{
       this.dsNhaTro = res.data
       this.dsNhaTro.forEach(item => {
         if(item.NGAYCAP!=null){
           item.NGAYCAP =  moment(item.NGAYCAP).format('DD/MM/YYYY')
         }
         if(item.NGAY_CN!=null){
           item.NGAY_CN =  moment(item.NGAY_CN).format('DD/MM/YYYY')
         }
         if(item.NGAY_SN!=null){
           item.NGAY_SN =  moment(item.NGAY_SN).format('DD/MM/YYYY')
         }
         if(item.sltb ==0 ){
           item.LOAI_NHATRO = "Nhà trọ tiềm năng";
         }else if(item.sltb > 5){
           item.LOAI_NHATRO = "Nhà trọ/nhà thuê từ 5 thuê bao trở lên";
         } else {
           item.LOAI_NHATRO = "Nhà trọ/nhà thuê quy mô nhỏ";
         }
       })
     }).catch(e=> {throw e}).finally(()=> this.loading(false))
   }
 },watch :{
    "inputSearch"  (val){
      if(val.length>0){
        this.disableCancel = false
      }else {
        this.disableCancel = true
      }

    }
  }
}
</script>

<style scope>
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
</style>
