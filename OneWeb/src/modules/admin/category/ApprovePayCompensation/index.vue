<template src="./template.html"></template>

<script>

import ChonCongTacVien from '../../../contract/setup/InstallNewSubs/Popups/popupCTV.vue'
import TTDuyetChiTraThuLao from '../ApprovePayCompensation/popups/TTDuyetChiTraThuLao.vue'

export default {
  components: {
    ChonCongTacVien,
    TTDuyetChiTraThuLao
  },
  
  data() {
    return {
      duyetTraThuLaoHopDongID: 0,
      luotChiTraType: 0,

      tableDaDuyetDataSource: []
      ,
      tableDoiDuyetDataSource: []
      ,

  
      khoanmuctt_id: 0,

    }
  },
  mounted() {
    
    this.Lay_DS_DaDuyet();
    this.Lay_DS_DoiDuyet();
   
  },
  methods: {
    
    async Lay_DS_DaDuyet() {
      try {

        let getDSParams = {
          "vkieu": 2
          ,"vnhanvien_pd_id": Number(this.$root.token.getNhanVienID())
        }

        let data = await this.axios.post("web-quantri/duyet-chitra-thulao/layds_luot_cttl_duyet", getDSParams);
        

        
        this.tableDaDuyetDataSource = data.data.data;
        
        
      } catch (err) {
        this.tableDaDuyetDataSource = []
      }
    }
    ,
    
    async Lay_DS_DoiDuyet() {
      try {

        let getDSParams = {
          "vkieu": 1
          ,"vnhanvien_pd_id": Number(this.$root.token.getNhanVienID())
        }

        let data = await this.axios.post("web-quantri/duyet-chitra-thulao/layds_luot_cttl_duyet", getDSParams);
        

        
        this.tableDoiDuyetDataSource = data.data.data;
        
        
      } catch (err) {
        this.tableDoiDuyetDataSource = []
      }
    },
    
    
    
    getColumnTemplateChuaDuyet(parent) {
     return function() {
                return { 
                    template: {
                        template: `
                            
                                <button class="btn pad4 lh14 btn-main"  >
                                  <span class="-ap icon-more_horiz" @click="parent.onClickBtnDuyetChiTra(data.luotcttl_id, 1)"></span>
                                </button>
                        `,
                        data() {
                            return { 
                                parent: parent,
                                data: { }
                            }
                        },
                        computed: {
                           
                        }
                    }
                }
            }
    },

    getColumnTemplateDaDuyet(parent) {
     return function() {
                return { 
                    template: {
                        template: `
                            
                                <button class="btn pad4 lh14 btn-main"  >
                                  <span class="-ap icon-more_horiz" @click="parent.onClickBtnDuyetChiTra(data.luotcttl_id, 2)"></span>
                                </button>
                        `,
                        data() {
                            return { 
                                parent: parent,
                                data: { }
                            }
                        },
                        computed: {
                            
                        }
                    }
                }
            }
    },

    onClickBtnDuyetChiTra(id, luotChiTraType) {

      this.duyetTraThuLaoHopDongID = id;
      this.luotChiTraType = luotChiTraType;
      this.$bvModal.show("thongTinChiTietDuyetChiTraThuLao");
      
    }

  }
}
</script>
<style>

.btn-main {
  background: #0176FF;
  color: #fff;
},
.non-ative a {
  color: #d3d3d3 !important;
}

.modal-body {
  padding: 0 !important;
}

  .e-resizable {
  resize: both;
  overflow: auto;
  border: 1px solid red;
  padding: 10px;
  height: 300px;
  min-height: 250px;
  min-width: 250px;
  }
</style>