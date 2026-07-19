<template>
    <div>
        <breadcrumb />
        <div class="list-actions-top">
            <ul class="list">
                <li :class="{ '-disabled': !isEnableAddNew }">
                    <a @click="addNew">
                        <span class="icon one-file-plus"></span>Nhập mới
                    </a>
                </li>
                <li :class="{ '-disabled': !isEnableSave }">
                    <a @click="save">
                        <span class="icon one-save"></span>Ghi lại
                    </a>
                </li>
                <li :class="{ '-disabled': !isEnableCancel }">
                    <a @click="cancel">
                        <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy
                    </a>
                </li>
                <li :class="{ '-disabled': !isEnableDelete }">
                    <a @click="deleteTown">
                        <span class="icon one-trash"></span>Xóa
                    </a>
                </li>
            </ul>
        </div>
        <div class="page-content">
            <div class="row">
                <div class="col-sm-5 col-12">
                    <town-info ref="townInfo"></town-info>
                    <search-info></search-info>
                    <town-areas @onSelect="onSelectTownArea"></town-areas>
                </div>
                <div class="col-sm-7 col-12">
                    <town-info-belong-ward></town-info-belong-ward>
                    <wards></wards>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import api from './api/index'
import axios from 'axios'
import breadcrumb from '@/components/breadcrumb'
import {  
  TRANGTHAI_DONGBO,
} from "@/utils/Enum.js";
export default {
  components: {
    TownInfo: () => import('./parts/TownInfo.vue'),
    SearchInfo: () => import('./parts/SearchInfo.vue'),
    TownAreas: () => import('./parts/TownAreas.vue'),
    TownInfoBelongWard: () => import('./parts/TownInfoBelongWard.vue'),
    Wards: () => import('./parts/Wards.vue'),
    breadcrumb
  },
  data () {
    return {
      isEnableAddNew: true,
      isEnableDelete: true,
      isEnableSave: true,
      isEnableCancel: true,
      dongbo_capnhat_diadanh_ccbs: false
    }
  },
  computed: {
    ...mapGetters('streetCommon', [
      'getWardsAdded',
      'getNote',
      'getSelectedTownAreaId',
      'getTownIdNeo',
      'getTownName',
      'getTownId',
      'getCurrentSearchWardId',
      'getDistricts',
      'getSelectedSearchDistrictId',
    ])
  },
  created(){
    this.init() 
  },
  methods: {
    ...mapActions('streetCommon', [
      'submitNewTown',
      'submitModifyTown',
      'submitDeleteTown',
      'resetData',
      'setTownIdNeo',
      'setTownName',
      'setNote',
      'loadTownsByWard',
      'setSelectedDistrict'
    ]),
    async init(){     
      
      const { ds_tsmd } = await this.axios.post('/web-thicong/hoancongmegawan/sp_lay_ds_thamso_md', 
        {"kieu_id" : 0}
      )
      console.log(ds_tsmd)
      if (ds_tsmd && ds_tsmd.length > 0) {     
        let cnt = ds_tsmd.filter(r => r["ma_ts"] == 'DONGBO_CAPNHAT_DIADANH_CCBS' && r["ten_ts"]=="1").length
        if (cnt>0) this.dongbo_capnhat_diadanh_ccbs = true
      }
    },
    addNew () {
      this.$refs.townInfo.$refs.townIdNeo.focus()
      this.setButton(1)
    },
    async cancel () {
      const isConfirm = await this.$bvModal
        .msgBoxConfirm(
          `Bạn có thực sự muốn hủy thông tin đã nhập không?`,
          {
            title: '',
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Không đồng ý',
            centered: true
          }
        )
      if (isConfirm) {
        this.isEnableAddNew = true
        this.isEnableDelete = true
        this.isEnableSave = true
        this.isEnableCancel = true
        this.resetData()
      }
    },
    checkDataBeforeSubmit: async function (isAddNew) {
      if (!this.getTownName) {
        this.$toast.error('Tên phố không được để trống')
        this.$refs.townInfo.$refs.townName.focus()
        this.$refs.townInfo.isErrorTownName = true
        return false
      } else this.$refs.townInfo.isErrorTownName = false

      if (this.getTownName.toString().length > 50) {
        this.$toast.error('Tên phố quá số ký tự cho phép.')
        this.$refs.townInfo.$refs.townName.focus()
        this.$refs.townInfo.isErrorTownName = true
        return false
      } else this.$refs.townInfo.isErrorTownName = false

      if (this.getNote && this.getNote.toString().length > 100) {
        this.$toast.error('Ghi chú quá số ký tự cho phép.')
        this.$refs.townInfo.$refs.note.focus()
        this.$refs.townInfo.isErrorNote = true
        return false
      } else this.$refs.townInfo.isErrorNote = false

      if (isAddNew) {
        if (this.getWardsAdded.length === 0) {
          this.$toast.error('Chưa gán phường cho phố !')
          return false
        }
        if (this.getTownIdNeo) {
          // p_kieu --1: nhap moi; 2:edit, 3 xoa
          const resExistTownIdNeo = await api.checkDataBeforeSubmit(axios, {
            p_kieu: 1,
            p_nhompho_id: 0,
            p_phanvung_id: 0,
            p_pho_id: 0,
            p_pho_id_neo: this.getTownIdNeo,
            p_phuong_id: 0
          })
          if (!(resExistTownIdNeo.data && resExistTownIdNeo.data.data.toLowerCase() === 'ok')) {
            this.$toast.error(`ID NEO ${this.getTownIdNeo} đã được sử dụng. Bạn hãy kiểm tra lại!`)
            return false
          }
        }
      } else {
        // if (this.getTownIdNeo) {
        //   const resExistTownIdNeo = await api.checkDataBeforeSubmit(axios, {
        //     p_kieu: 2,
        //     p_nhompho_id: 0,
        //     p_phanvung_id: 0,
        //     p_pho_id: 0,
        //     p_pho_id_neo: this.getTownIdNeo,
        //     p_phuong_id: 0
        //   })
        //   if (!(resExistTownIdNeo.data && resExistTownIdNeo.data.data.toLowerCase() === 'ok')) {
        //     this.$toast.error(`ID NEO ${this.getTownIdNeo} đã được sử dụng. Bạn hãy kiểm tra lại!`)
        //     return false
        //   }
        // }
      }

      return true
    },
    save: async function () {
      this.$root.showLoading(true)

      try {
        if (await this.checkDataBeforeSubmit(!this.isEnableAddNew)) {
          if (!this.isEnableAddNew) {
            //gọi hàm ccbs thêm phố
            if (TRANGTHAI_DONGBO.OPEN_SYNC_VINA_TRASAU){
              if (this.dongbo_capnhat_diadanh_ccbs){
                //ts_danhmuc_insert_duongpho:  input  { "phoid" : "" ,  "tenpho" : "12123123123"}
                const data  = await this.axios.post('ccbs/executor/ts_danhmuc_insert_duongpho', 
                  { "phoid" : "" ,  "tenpho" : this.getTownName}
                )
                console.log(data)
                let PHO_ID_NEO = null
                if (data && data.data && data.data.data && data.data.message && data.data.message=='Success')
                {
                  console.log(data)
                  console.log(data.data.data.result.substring(21))
                  console.log(data.message)
                  this.setTownIdNeo(data.data.data.result.substring(21))
                }
                else {
                  this.$toast.error("Không thêm mới được phố trên VinaPhone!")
                  this.$root.showLoading(false)
                  return
                }

                //gọi hàm ccbs thêm map phố phường
                //ts_dm_diachi_phuong_pho_capnhhat:  input  { "phuong_id" : "", "pho_id" : ""}
                let tmp_phuong_id_neo =''
                console.log('wardded')
                console.log(this.getWardsAdded)
                for (let i=0;i<this.getWardsAdded.length;i++){
                  tmp_phuong_id_neo = tmp_phuong_id_neo + this.getWardsAdded[i].wardIdNeo+','
                }
                tmp_phuong_id_neo = tmp_phuong_id_neo.substring(0,tmp_phuong_id_neo.length-1)
                const data_phuongccbs  = await this.axios.post('ccbs/executor/ts_dm_diachi_phuong_pho_capnhhat', 
                  { "phuong_ids" : tmp_phuong_id_neo ,  "pho_id" : this.getTownIdNeo}
                )
                console.log(data_phuongccbs)
                if (data_phuongccbs && data_phuongccbs.data && data_phuongccbs.data.data && data_phuongccbs.data.data.result && data_phuongccbs.data.data.result=='Cap nhat thanh cong')
                {
                  console.log(data_phuongccbs)
                }
                else {
                  //xóa phố neo trên ccbs
                  //ts_danhmuc_delete_duongpho   : input  { "pho_id" : ""}
                  const data_xoa = await this.axios.post('ccbs/executor/ts_danhmuc_delete_duongpho', 
                    {"pho_id" : this.getTownIdNeo}
                  )
                  console.log(data_xoa)
                  if (data_xoa && data_xoa.data && data_xoa.data.message=='Success')
                  {
                    console.log(data_xoa)
                    this.$toast.error("Không map được phố trên VinaPhone, đã xóa phố trên VinaPhone!")
                  }   
                  this.$root.showLoading(false)           
                  return
                }
              }
            }

            await this.submitNewTown({
              ma_pho: [
                ...this.getWardsAdded.map(i => {
                  return {
                    GHICHU: this.getNote,
                    PHO_ID_NEO: this.getTownIdNeo,
                    PHUONG_ID: i.id,
                    PHUONG_ID_NEO: i.wardIdNeo
                  }
                })
              ],
              p_phanvung_id: 0,
              pho: [
                {
                  GHICHU: this.getNote,
                  NHOMPHO_ID: this.getSelectedTownAreaId,
                  PHO_ID_NEO: this.getTownIdNeo,
                  PHUONG_ID: this.getWardsAdded.length>0?this.getWardsAdded[0].id:null,
                  TEN_PHO: this.getTownName
                }
              ]
            })
          } else {
            //gọi hàm ccbs cập nhật phố
            if (TRANGTHAI_DONGBO.OPEN_SYNC_VINA_TRASAU){
              if (this.dongbo_capnhat_diadanh_ccbs){
                  //ts_dm_diachi_pho_capnhhat: input  { "pho_id" : "1", "ten_pho" : "3123123"}
                  //         //tuanlahyn
                  //         //string kq;
                  //         string kq = new CCBSFacade().ts_dm_diachi_pho_capnhhat(pho_id_neo, txtTenPho.Text, tt_nd.tentat_ccbs, tt_nd.USER_NEO);
                  //         if (kq.IndexOf("Cap nhat thanh cong") == -1)
                  //         {
                  //             Message_Box.ShowError("Không cập nhật được thông tin phố trên VinaPhone!" + kq);
                  //             return;
                  //         }
                  const data_update  = await this.axios.post('ccbs/executor/ts_dm_diachi_pho_capnhhat', 
                      {"pho_id" : this.getTownIdNeo, "ten_pho" : this.getTownName}
                  )
                  console.log(data_update)
                  if (data_update && data_update.data && data_update.data.message=='Success')
                  {
                    console.log(data_update)              
                  } 
                  else {
                    this.$toast.error("Không map được phố trên VinaPhone, đã xóa phố trên VinaPhone!")
                    this.$root.showLoading(false)
                    return
                  }

                  //         kq = new CCBSFacade().ts_danhmuc_delete_phos(pho_id_neo);
                  //         if (kq != "Success")
                  //         {
                  //             Message_Box.ShowError("Không map được phố và phường trên VinaPhone!" + kq);
                  //             return;
                  //         }
                  const  data_del_map  = await this.axios.post('ccbs/executor/ts_danhmuc_delete_phos', 
                      {"pho_id" : this.getTownIdNeo}
                  )
                  console.log(data_del_map)
                  if (data_del_map && data_del_map.data && data_del_map.data.message=='Success')
                  {
                    console.log(data_del_map)              
                  } 
                  else {
                    this.$toast.error("Không xóa được map phố và phường trên VinaPhone!")
                    this.$root.showLoading(false)
                    return
                  }
                  
                  
                  //         string tempPhuong = "";
                  //         foreach (DataRow dr in dtDaGan.Rows)
                  //         {
                  //             tempPhuong += dr["phuong_id_neo"].ToString() + ",";
                  //         }
                  //         tempPhuong = tempPhuong.Substring(0, tempPhuong.Length - 1);
                  //         kq = new CCBSFacade().ts_dm_diachi_phuong_pho_capnhhat(pho_id_neo, tempPhuong, tt_nd.tentat_ccbs, tt_nd.USER_NEO);
                  //         if (kq != "Cap nhat thanh cong")
                  //         {
                  //             Message_Box.ShowError("Không map được phố và phường trên VinaPhone!" + kq);
                  //             return;
                  //         }
                  //         Message_Box.ShowTB("Cập nhật dữ liệu phố lên VinaPhone thành công !");
                  //         //endtuanlahyn
                  let tmp_phuong_id_neo =''
                  for (let i=0;i<this.getWardsAdded.length;i++){
                    tmp_phuong_id_neo = tmp_phuong_id_neo + this.getWardsAdded[i].wardIdNeo+','
                  }
                  tmp_phuong_id_neo = tmp_phuong_id_neo.substring(0,tmp_phuong_id_neo.length-1)
                  const  data_phuongccbs  = await this.axios.post('ccbs/executor/ts_dm_diachi_phuong_pho_capnhhat', 
                    { "phuong_ids" : tmp_phuong_id_neo ,  "pho_id" : this.getTownIdNeo}
                  )
                  console.log(data_phuongccbs)
                  if (data_phuongccbs && data_phuongccbs.data && data_phuongccbs.data.data && data_phuongccbs.data.data.result && data_phuongccbs.data.data.result=='Cap nhat thanh cong')
                  {
                    console.log(data_phuongccbs)
                  }
                  else {
                    this.$toast.error("Không map được phố và phường trên VinaPhone!")
                    this.$root.showLoading(false)
                    return
                  }
                  this.$toast.success("Cập nhật dữ liệu phố lên VinaPhone thành công !");
                }
              }

            await this.submitModifyTown({
              ma_pho: [
                ...this.getWardsAdded.map(i => {
                  return {
                    GHICHU: this.getNote,
                    PHO_ID_NEO: this.getTownIdNeo,
                    PHUONG_ID: i.id,
                    PHUONG_ID_NEO: i.wardIdNeo
                  }
                })
              ],
              pho: [
                {
                  GHICHU: this.getNote,
                  NHOMPHO_ID: this.getSelectedTownAreaId,
                  PHO_ID_NEO: this.getTownIdNeo,
                  PHO_ID: this.getTownId,
                  PHUONG_ID: this.getWardsAdded.length>0?this.getWardsAdded[0].id:null,
                  TEN_PHO: this.getTownName
                }
              ],
              pho_id: this.getTownId
            })
          }

          // reload grid Phố/Ấp/Khu
          await this.loadTownsByWard({
            // 'CATEGORY_NAME': this.getSelectedTownAreaId,
            // 'CODE': null,
            // 'ID': this.getCurrentSearchWardId,
            // 'NAME': null
            "p_quan_id": this.getSelectedSearchDistrictId,
            "p_phuong_id": this.getCurrentSearchWardId
          })

          // reset danh sách phường chưa gán
          await this.setSelectedDistrict(this.getDistricts.length > 0 ? this.getDistricts[0].QUAN_ID : 0)

          this.setButton(3)
        }
      } catch (error) {
        console.log(error)
      }

      this.$root.showLoading(false)
    },
    deleteTown: async function () {
      try {
        const isConfirm = await this.$bvModal
          .msgBoxConfirm(
            `Bạn thật sự muốn xóa phố ${this.getTownName} không ?`,
            {
              title: '',
              size: 'sm',
              okTitle: 'Đồng ý',
              cancelTitle: 'Không đồng ý',
              centered: true
            }
          )

        if (isConfirm) {
          this.loading(true)
          //gọi hàm ccbs xóa map phường, phố

          //gọi hàm ccbs xóa phố
          if (TRANGTHAI_DONGBO.OPEN_SYNC_VINA_TRASAU)
          {
            const data_del_map = await this.axios.post('ccbs/executor/ts_danhmuc_delete_phos', 
                  {"pho_id" : this.getTownIdNeo,}
            )
            console.log(data_del_map)
            if (data_del_map && data_del_map.data && data_del_map.data.message=='Success')
            {
              console.log(data_del_map)              
            }  
            else {
              this.$toast.error("Không xóa được thông tin map phố và phường trên VinaPhone!") 
              this.$root.showLoading(false)
              return
            }
            const  data_del = await this.axios.post('ccbs/executor/ts_danhmuc_delete_duongpho', 
                  {"pho_id" : this.getTownIdNeo,}
            )
            console.log(data_del)
            if (data_del && data_del.data  && data_del.data.message=='Success')
            {
              console.log(data_del)              
            }  
            else {
              this.$toast.error("Không xóa được phố trên VinaPhone!") 
              this.$root.showLoading(false)
              return
            }
            
          }

          await this.submitDeleteTown({
            p_phanvung_id: 0,
            p_pho_id: this.getTownId
          })

          // reload grid Phố/Ấp/Khu
          await this.loadTownsByWard({
            // 'CATEGORY_NAME': this.getSelectedTownAreaId,
            // 'CODE': null,
            // 'ID': this.getCurrentSearchWardId,
            // 'NAME': null
            "p_quan_id": this.getSelectedSearchDistrictId,
            "p_phuong_id": this.getCurrentSearchWardId
          })

          // reset danh sách phường
          await this.setSelectedDistrict(this.getDistricts.length > 0 ? this.getDistricts[0].QUAN_ID : 0)
        }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }

      this.loading(false)
    },
    setButton: function (kieu) {
      this.isEnableAddNew = false
      this.isEnableSave = false
      this.isEnableDelete = false
      this.isEnableCancel = false

      // Bắt đầu
      if (kieu === -1) {
        this.isEnableSave = true
        this.isEnableCancel = true
      }

      // Bắt đầu
      if (kieu === 0) {
        this.isEnableAddNew = true
        this.clear()
      }

      // Thêm mới
      if (kieu === 1) {
        this.isEnableSave = true
        this.isEnableCancel = true
        this.clear()
      }

      // Hủy
      if (kieu === 2) {
        this.isEnableAddNew = true
        this.isEnableDelete = true
        this.clear()
      }

      // Edit
      if (kieu === 3) {
        this.isEnableAddNew = true
        this.isEnableSave = true
        this.isEnableDelete = true
        this.isEnableCancel = true
      }
    },
    clear: function () {
      this.$refs.townInfo.townIdNeo = null
      this.$refs.townInfo.townName = ''
      this.$refs.townInfo.selectedTownAreaId = 0
      this.$refs.townInfo.note = ''
    },
    onSelectTownArea: function (e) {
      this.setButton(3)
    }
  }
}
</script>

<style scoped>
.-disabled{
    pointer-events: none;
    cursor: default;
}
.-disabled:hover{
    background: unset;
}
.-disabled > a {
    color: gray !important;
}
.-disabled > a > span {
    color: gray;
}

a:hover{
  cursor: pointer;
}
</style>
