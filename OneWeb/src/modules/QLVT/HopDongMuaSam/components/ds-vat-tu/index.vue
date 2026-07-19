<template src="./index.html"></template>
<style scoped>
.modal-wrapper {
  min-height: 500px;
}
.text-line {
  width: 100%;
  border-bottom: 1px solid #cbcbcb;
  line-height: 0.1em;
  margin: 5px 0 20px;
}
.text-line span,
label {
  background: #fff;
  display: initial;
}
.text-line .noline {
  background: #fff;
}
.fw-600 {
  font-weight: 600;
}
.col-content {
  border: 1px solid #ddd;
  height: 100%;
  padding: 0.25rem;
}
.page-content {
  top: 60px !important;
}

.list-actions-top .list {
  overflow: unset;
}
</style>
<script>
import moment from "moment";
import VTCustomDropdown from "../custom-dropdown";

export default {
  name: "DSVatTu",
  components: { "vt-custom-dropdown": VTCustomDropdown },
  props: ["data"],
  testData: [],
  data: function () {
    return {
      formTK: {
        thang: new Date(),
        trangThai: 1,
        duyetTuCbk: false,
        duyetTu: new Date(),
        denNgayCbk: false,
        denNgay: new Date(),
        donViCkb: false,
        dotCkb: false,
        dot: "",
        donVi: ""
      },

      checkAllDotTH: false,    
      
      columnsCheckboxAllVT: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.checkAllDotTH" class="uncheck"/>
                                <span class="name"></span>
                            </div>
                        `,
            data: function () {
              return {};
            },            
            computed: {
              parent() {
                return this.$parent.$parent.$parent;
              },
            },
          },
        };
      },
      CheckboxDotTH: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="uncheck"                                    
                                    v-model="parent.vatTuChon"
                                    :value="data.KEY"/>
                                <span class="name"></span>
                            </div>
                        `,
            data() {
              return {
                data: {},
              };
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent;
              },
            },
          },
        };
      },
			editSettings: { allowEditing: true, mode:'Batch'},

      vatTuLst: {
        data: [],
        totalItems: 0,
      },
	  vatTuChon: [],
      dsDot: [],
      dsDvTh: [],
    };
  },
  validations: {},
  watch: {
    vatTuChon: function (value) {   
      this.vatTuChon = value;
      for (var e of this.vatTuLst.data) {
        if ( this.vatTuChon.includes(e.KEY ) && e.SOLUONG_MUA == 0 ) {
          e.SOLUONG_MUA =  e.SOLUONG_DK == null || e.SOLUONG_DK == "" ? 0 : parseInt(e.SOLUONG_DK);
        }else if(!this.vatTuChon.includes(e.KEY )){
          e.SOLUONG_MUA = 0
        }
      }
      console.log(this.vatTuLst.data)
      this.$refs.tableVT.grid.refreshColumns();  
    },
    checkAllDotTH: function (value) {
      if (value) {
        for (const e of this.vatTuLst.data) {
          if(!this.vatTuChon.includes(e.KEY)){
            this.vatTuChon.push(e.KEY);
            e.SOLUONG_MUA =  e.SOLUONG_DK == null || e.SOLUONG_DK == "" ? 0 : parseInt(e.SOLUONG_DK);
          }
          
        }
      } else {
        this.vatTuChon = [];
        for (const e of this.vatTuLst.data) {          
          e.SOLUONG_MUA =  0
        }
      }    
      
    },
  },
  created: async function () {
	this.onClickTimKiem();
    this.getDsDot();
    this.getDsDvTh();
	},
  async mounted() {},
  methods: {
    onChangeDonVi(value) {
      this.formTK.donVi = value.DONVI_ID;
      this.onClickTimKiem();
    },
    onClickTimKiem() {
      const thang = moment(this.formTK.thang).format("YYYYMM");
      this.dsVatTu(thang);
    },

		// selectedItemChangedVT(value) {
		// 	this.vatTuChon = value;
		// },
		editGridVT(args) {			
      let tableVT = this.$refs.tableVT;
      let changes = tableVT.getBatchChanges();

      if (changes.changedRecords.length == 0) {
        return
      }
      if (args.columnName == "SOLUONG_MUA") {
        if(changes.changedRecords[0].SOLUONG_MUA > changes.changedRecords[0].SOLUONG_DK){

          var SOLUONG_DK = changes.changedRecords[0].SOLUONG_DK
          changes.changedRecords[0].SOLUONG_MUA = SOLUONG_DK == "" || SOLUONG_DK == null ? 0 :parseInt(SOLUONG_DK)
          if(!this.vatTuChon.includes(changes.changedRecords[0].KEY)){
            this.vatTuChon.push(changes.changedRecords[0].KEY)
          }
          this.$toast.error("Số lượng mua phải nhỏ hơn số lượng dự kiến");

        }else if(changes.changedRecords[0].SOLUONG_MUA <= 0){

          changes.changedRecords[0].SOLUONG_MUA = 0
          this.vatTuChon = this.removeElementInArray(this.vatTuChon, changes.changedRecords[0].KEY);

        }else{
          if(!this.vatTuChon.includes(changes.changedRecords[0].KEY)){
            this.vatTuChon.push(changes.changedRecords[0].KEY)
          }
        }
        console.log(this.vatTuChon , " = vatTuChon")
        tableVT.batchUpdate(changes);
        this.$refs.tableVT.grid.refreshColumns()
      }
		},
    removeElementInArray(array, element) {
      const index = array.indexOf(element);
      if (index > -1) {
        array.splice(index, 1);
      }
      return array;
    },
		onSave() {
			if(this.validate()) {
				const dsVT = this.vatTuLst.data.filter(item=>this.vatTuChon.includes(item.KEY)).map(item=> ({
					TONGHOP_ID: item.TONGHOP_ID,
					DONVI_ID: item.DONVI_ID,
					VATTU_ID: item.VATTU_ID,
					SOLUONG: item.SOLUONG_MUA,
				}));
				const body = {
					"hdMsId": this.data.HDMS_ID,
					"dsVt": dsVT,//[{"TONGHOP_ID":367,"DONVI_ID":"882","VATTU_ID":"11167","SOLUONG":"5"}],
					"nguoiDung": this.$auth.getNguoiDungID(),// 1987,
					"ngayCn": moment(new Date()).format('DD/MM/YYYY'),//"10/05/2021",
					"nguoiCn": this.$auth.getUserName(),//"admin",
					"mayCn": "",
					"ipCn": ""
				}
				console.log(body);
				this.excuteSave(body);
			}
		},
		validate() {
			const msg = [];
			if(this.vatTuChon.length == 0) {
				this.$toast.error("Bạn chưa chọn vật tư để mua");
				return false;
			}
			this.vatTuLst.data.forEach(item => {
					if(this.vatTuChon.includes(item.KEY)) {
						if(!item.SOLUONG_MUA || Number(item.SOLUONG_MUA) == 0) {
							msg.push(`Bạn chưa nhập số lượng mua cho vật tư ${item.TEN_VT}`);
						}
					}
			});
			if(msg.length > 0) {
				this.$toast.error(msg[0]);
				return false;
			}
			return true;
		},

    dsVatTu: async function (thang) {
      try {
        this.$root.loading(true);
        var rs = await this.$root.context.get(
          `/web-qlvt/api/vat-tu-da-duyet/ds-tong-hop-vt-da-duyet/${thang}`
        );
        for(var e of rs.data){
          e.SOLUONG_MUA = e.SOLUONG_MUA == null || e.SOLUONG_MUA == ""? 0 : parseInt(e.SOLUONG_MUA)
        }
        this.vatTuLst.data = rs.data.filter(item=> {
          return (this.formTK.donViCkb && this.formTK.donVi ? item.DONVI_ID == this.formTK.donVi : true) &&
          (this.formTK.dotCkb && this.formTK.dot ? item.DOT == this.formTK.dot : true) &&
          (this.formTK.duyetTuCbk && this.formTK.duyetTu ? moment(item.NGAYDUYET, "DD-MM-YYYY HH:mm:ss").toDate() >= this.formTK.duyetTu : true) &&
          (this.formTK.denNgayCbk && this.formTK.denNgay ? moment(item.NGAYDUYET, "DD-MM-YYYY HH:mm:ss").toDate() <= this.formTK.denNgay : true)
        });
        console.log(this.vatTuLst.data , " = this.vatTuLst.data")
        this.vatTuChon = []
        this.checkAllDotTH = false
        this.vatTuLst.totalItems = rs.data.length;
        // console.log(rs);
      } catch (error) {
      } finally {
         this.$root.loading(false);
      }
    },
    getDsDot: async function () {
      try {
        this.$root.loading(true);
        var rs = await this.$root.context.get(
          `/web-qlvt/api/dang-ky-cap-phat/ds-dot-dang-ky`
        );
        this.dsDot = rs.data;
      } catch (error) {
      } finally{
        this.$root.loading(false);
      }
    },
    getDsDvTh: async function () {
      try {
        this.$root.loading(true);
        var rs = await this.$root.context.get(
          `/web-qlvt/api/vat-tu-da-duyet/ds-don-vi-thuc-hien`
        );
        this.dsDvTh = rs.data;
      } catch (error) {
      } finally{
        this.$root.loading(false);
      }
    },
	excuteSave: async function (body) {
      try {
        this.$root.loading(true);
        var rs = await this.$root.context.post(
          `/web-qlvt/api/vat-tu-da-duyet/mua-vt-tong-hop`,
					body
        );
        console.log(rs);
        if(rs.error_code ==="BSS-00000000") {
            this.$emit("addSuccess");
        }
      } catch (error) {
		this.$toast.error("Lỗi khi cập nhật dữ liệu mua sắm vật tư");
      } finally{
        this.$root.loading(false);
      }
    },
  },
};
</script>
