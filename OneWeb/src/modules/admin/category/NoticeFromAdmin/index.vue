<template>
  <div>
    <vue-bread-crumb :header="header" />
    <vue-nav>
      <ul class="list">        
        <li
          @click="clickButton('ghilai')"
          :class="button.ghilai ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-save"></span>Ghi lại </a>
        </li>
         <li
          @click="clickButton('thoat')"
          :class="button.thoat ? 'active' : 'non-active'"
        >
          <a>
            <span class="icon nc-icon-glyph ui-1_circle-remove" style="color:red"></span>Thoát
          </a>
        </li>
      </ul>
    </vue-nav>
    <div class="page-content">            
          <div class="box-form">                
                <div class="legend-title">                 
                    <div class="pull-left">  
                        Quản trị hệ thống - Tập đoàn
                    </div>
                     <div class="pull-right red">
                        <div class="check-action">
                            <input type="checkbox" class="check" v-model="chkFlag">
                            <span class="name">Thông báo người dùng</span>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>               
                <div class="title-bg-main">
                  <span class="title">Danh sách phiên bản</span>
                </div>
                <div class="table-content">
                  <DataGrid
                    v-bind:dataSource="options.danh_sach_phien_ban"
                    :columns="collumns.danh_sach_phien_ban"
                    :enablePagingServer="false"
                    :allowPaging="true"
                    :pageIndex="pageIndex"
                    :pageSize="pageSize"                  
                    ref="danh_sach_phien_ban"
                    :totalRecords="totalRecords"
                    @selectedRowChanged="selectedRowChanged"
                    @pageChanged="pageChanged"
                    @recordClick="setCurrent"
                    @actionComplete="actionComplete"
                  />
              </div>
          <div class="title-bg-main mart10">
                    <span class="title">Thông báo</span>
          </div>
            <textarea v-model= "txtThongBao" name="" ref="textFocus" class="form-control" style="height: 300px;resize: none;"></textarea>          
        </div>      
    </div>
  </div>
</template>
<script>

import define from "./define";
export default {
  data() {
    return {
        ...define,
    };
  },
  mounted() {
    this.frmNoticeFromAdmin_Load();
  },
  watch: {    
  },
  methods: {
    setCurrent(data) {
      this.current = data.rowIndex;
    },   
    clickButton(key) {
      if (!this.button[key]) {
        return false;
      }
      if (key == "ghilai") {
        this.ghilai();
      }      
      if (key == "thoat") {
        this.thoat();
      }      
    },  
    
    async ghilai() {
      try {
        //  if(!this.KiemTra_DuLieu())
        //    return false;
        if(!this.KiemTra_DuLieu())
        {
          return false;
        }            
        this.loading(true);
        //alert(this.txtThongBao.trim());
        // private void tsbtnGhiLai_Click(object sender, EventArgs e)
        // {
        //     try
        //     {
        //         bangts.Update_Message_Admin(dgvPhienBan.CurrentRow.Cells["clphienban"].Value.ToString(), txtThongBao.Text.Trim());
        //         if (chkFlag.Checked)
        //             bangts.ExeSQL("update " + DatabaseConstants.DB1 + ".ct_tsmd set giatri = 1 where ma_ts = 'MESSAGE_ADMIN'");
        //         Message_Box.ShowTB("Cập nhật dữ liệu thành công !");
        //     }
        //     catch (Exception ex)
        //     {
        //         Message_Box.ShowError("Có lỗi : " + ex);
        //     }
        // }
        //console.log(this.rowselected);

        const pischeck = this.chkFlag? 1 : 0;
        //console.log(pischeck);        
        let rs = await this.axios.post(
          "/web-quantri/tbtuquantri/FN_CAPNHAT_THONGBAO",
          {
             "p_ischeck": pischeck,
             "p_phienban": this.rowselected.PHIENBAN,
             "p_thongbao": this.txtThongBao.trim()
          }
        );
        this.loading(false);
        this.$toast.success("Cập nhật dữ liệu thành công !");
        this.LoadGrid();
      } catch (error) {
        console.log(error);
        this.$toast.error(JSON.stringify(error));
        this.loading(false);
      }
    },  
    async thoat() {
      this.$bvModal.msgBoxConfirm('Không hiển thị thông báo nữa?', {
                	title: '',
                	size: 'sm',
                	okTitle: 'Đồng ý',
                	cancelTitle: 'Hủy',
            	}).then(async v => {
                	if(!v) {
                  	return;
                	}
                  try {            
                          //console.log("Y");        
                          let rs = await this.axios.post(
                            "/web-quantri/thamso-macdinh/capnhat_thamso_macdinh",
                            {                              
                              "vma_ts" : "TEST_CT",
                              "nguoidung_id" : 0,
                              "vgiatri" : "1"
                            }
                          );

                        } catch (error) {
                          //console.log(error);
                          this.$toast.error(JSON.stringify(error));
                          this.loading(false);
                        }
            	});

      // this.$confirm("Không hiển thị thông báo nữa?", {
      //       confirmButtonText: "Có",
      //       cancelButtonText: "Không",
      //       type: "warning",
      //   }).then( async () => {
      //     try {            
      //       //console.log("Y");        
      //       let rs = await this.axios.post(
      //         "/web-quantri/thamso-macdinh/capnhat_thamso_macdinh",
      //         {
      //           "vgiatri": '0',
      //           "vma_ts": '0',
      //           "vnguoidung_id": 0
      //         }
      //       );        
      //     } catch (error) {
      //       //console.log(error);
      //       this.$toast.error(JSON.stringify(error));
      //       this.loading(false);
      //     }
      //     });
    },   
    actionComplete(args) {
      if (args.requestType === 'filtering') { 
        // you can do your requirement here 
        this.rowselected = {};
      } 
      
    },
    selectedRowChanged(item) {   
      // console.log("selectedRowChanged");
        // "private void dgvPhienBan_SelectionChanged(object sender, EventArgs e)
        // {
        //     try
        //     {
        //         if (dgvPhienBan.SelectedRows.Count <= 0) return;
        //         txtThongBao.Text = ndfacade.Lay_ThongBao_Admin(Convert.ToInt64(dgvPhienBan.CurrentRow.Cells[""clphienban_id""].Value.ToString()));
        //     }
        //     catch (Exception ex)
        //     {
        //         new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, ex.Message, ex), true);
        //     }
        // }"
        if (item) {
        //this.chkThongBao = true;
        //console.log(item);
        this.rowselected = item;
        this.txtThongBao = item.THONGBAO;
        if (item.FLAG==null || item.FLAG==0)
        {
          this.chkFlag = false;
        }
         else{ this.chkFlag = true;}              
      }
    },
    frmNoticeFromAdmin_Load() {       
      this.LoadGrid();
    },       
    Clear() {
      this.txtThongBao = null;
      this.chkFlag = null;    
    },
    pageChanged(args) {
      this.pageSize = args.pageSize;
      this.pageIndex = args.pageIndex;
      this.current = 0
      this.LoadGrid();
    },
    async LoadGrid() {
      try {
        let rs = await this.axios.get(
          // "/web-quantri/danhmuc-chung/ADMIN_PHIENBAN", {loai_ct_id: 1}
          "/web-quantri/tbtuquantri/LAY_DS_PHIENBAN"
        );
        //console.log(rs);
        this.options.danh_sach_phien_ban = rs.data.data
        // this.options.danh_sach_phien_ban = [...this.options.danh_sach_phien_ban]
        this.$refs.danh_sach_phien_ban.setCurrentSelectedRow(this.current)
        this.$refs.danh_sach_phien_ban.flagSelectedRowIndexes = [];
        this.$refs.danh_sach_phien_ban.flagSelectedRowIndexes.push(this.current);
        if (this.options.danh_sach_phien_ban.length == 0) {
          this.Clear();
        }
      } catch (e) {
        //console.log(e);
        this.$toast.error("Đã có lỗi xảy ra");
      }
    },
     KiemTra_DuLieu() {      
      // if (!this.txtThongBao) {
      //   this.$toast.error('Bạn chưa nhập "thông báo"!');
      //   return false;
      // }
      
      if (this.rowselected.PHIENBAN == null)
      {
        this.$toast.error('"Phiên bản" chưa được chọn!');
              return false;
      }
      if (this.txtThongBao!=null)
      {
        if ((this.txtThongBao.trim() != ""))
          {
            if (this.txtThongBao.trim().length > 10000)
            {
              this.$toast.error('"Thông báo" không được quá 10000 ký tự!');
              this.$refs.textFocus.focus();
              //this.txtThongBao.focus();
              return false;
            }          
          }
      }      
      return true
     },
  },
};
</script>
<style>
.no-header .e-columnheader {
  display: none;
}
li.non-active a {
  color: #d3d3d3 !important;
}
.center {
  text-align: center !important;
}

</style>
