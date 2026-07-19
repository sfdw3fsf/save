<template>
  <div>
    <vue-nav>
      <ul class="list">
        <li @click="btnNewClick" :class="!btnNew.Enabled ? 'non-ative' : ''">
          <a href="#">
            <span class="icon one-file-plus"></span>Nhập mới
          </a>
        </li>
        <li @click="btnSaveClick" :class="!btnNew.Enabled ? 'non-ative' : ''">
          <a href="#">
            <span class="icon one-save"></span>Ghi lại
          </a>
        </li>
        <li @click="btnCancelClick" :class="!btnNew.Enabled ? 'non-ative' : ''">
          <a href="#">
              <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy
          </a>
        </li>
        <li @click="btnDeleteClick" :class="!btnNew.Enabled ? 'non-ative' : ''">
          <a href="#">
              <span class="icon one-trash"></span>Xóa bảng
          </a>
        </li>
      </ul>
    </vue-nav>
    <div class="popup-body">
        <div class="box-form">
            <div class="legend-title">Thông tin bảng</div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key w100">DS bảng</div>
                      <div class="value">
                        <div class="select-custom" id="CboListTableId">
                          <SelectExt v-model="TableInfo.Table" ref="ListTable" dataTextField="TENBANG" v-on:change="changeItem($event)"
                            dataValueField="TENBANG" :dataSource="ListTable.DataSource">
                          </SelectExt>
                        </div>
                        <div id="TxtTable" display = 'none'>
                            <input v-model="TableInfo.Schema" type="text" class="form-control">
                        </div>
                      </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Schema</div>
                        <div class="value">
                            <input v-model="TableInfo.Schema" type="text" class="form-control">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Tên hiển thị</div>
                        <div class="value">
                            <input v-model="TableInfo.DisplayName" type="text" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-sm-3 col-12">
                    <div class="info-row">
                        <div class="key w100">Số cột</div>
                        <div class="value">
                            <input v-model="TableInfo.NumberColunm" type="text" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-sm-3 col-12">
                    <div class="info-row">
                        <div class="key w60">Sắp xếp</div>
                        <div class="value">
                            <input v-model="TableInfo.Arrange" type="text" class="form-control">
                        </div>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w100">Ghi chú</div>
                <div class="value">
                    <input v-model="TableInfo.Note" type="text" class="form-control">
                </div>
            </div>
        </div>
        <div class="box-form">
            <div class="legend-title">Thông tin trường</div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Tên trường</div>
                        <div class="value">
                            <input v-model="FieldInfo.FieldName" type="text" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Tên hiển thị</div>
                        <div class="value">
                            <input v-model="FieldInfo.DisplayName" type="text" class="form-control">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Kiểu dữ liệu</div>
                        <div class="value">
                            <!-- <input v-model="FieldInfo.DataType" type="text" class="form-control"> -->
                            <div class="select-custom">
                              <SelectExt v-model="FieldInfo.DataType" ref="DataType" dataTextField="DataTypeName"
                                dataValueField="DataTypeId" :dataSource="DataType.DataSource">
                              </SelectExt>
                            </div>
                        </div>
                        <div class="value w30 nowrap padt7">
                            <div class="check-action">
                                <input v-model="FieldInfo.Option" type="checkbox" class="check">
                                <span class="name">Option</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Thứ tự</div>
                        <div class="value">
                            <input v-model="FieldInfo.Order" type="text" class="form-control">
                        </div>
                        <div class="value w30 nowrap padt7">
                            <div class="check-action">
                                <input v-model="FieldInfo.Show" type="checkbox" class="check">
                                <span class="name">Hiển thị</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Kiểu ràng buộc</div>
                        <div class="value">
                            <div class="select-custom">
                              <SelectExt v-model="FieldInfo.Bond" ref="BondType" dataTextField="BondTypeName"
                                dataValueField="BondTypeId" :dataSource="BondType.DataSource">
                              </SelectExt>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Tên bảng cha</div>
                        <div class="value">
                            <input v-model="FieldInfo.TableParent" type="text" class="form-control">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Schema</div>
                        <div class="value">
                            <input v-model="FieldInfo.Schema" type="text" class="form-control">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Tên trường RB</div>
                        <div class="value">
                            <input v-model="FieldInfo.FieldNameRB" type="text" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Tên trường HT</div>
                        <div class="value">
                            <input v-model="FieldInfo.FieldNameHT" type="text" class="form-control">
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="box-form">
            <div class="box-form marb0">
                <div class="legend-title">Danh sách trường</div>
                <div class="table-content" style="height: 250px;">
                    <DataGrid :columns="ListFieldTable.columns" ref="ListFieldTable" :dataSource="ListFieldTable.DataSource" :enable-paging-server="false"
                    :allowPaging="false" :showFilter="true" @selectedRowChanged="ListDataFocus">
                    </DataGrid>
                </div>
            </div>
        </div>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      ListFieldTable: {
        DataSource: [],
        columns: [
          {
            fieldName: 'TENCOT',
            headerText: 'Tên trường',
            allowFiltering: true,
            allowSorting: true,
            width: '150'
          },
          {
            fieldName: 'NHAN_COT',
            headerText: 'Tên hiển thị',
            allowFiltering: true,
            allowSorting: true,
            width: '150'
          },
          {
            fieldName: 'THUTU_ID',
            headerText: 'Thứ tự',
            allowFiltering: true,
            allowSorting: true,
            width: '150'
          },
          {
            fieldName: 'HIENTHI',
            headerText: 'Hiển thị',
            allowFiltering: true,
            allowSorting: true,
            width: '150'
          }
        ]
      },
      BondType: {
        DataSource: [
          {
            BondTypeId: 0,
            BondTypeName: 'Không có'
          },
          {
            BondTypeId: 1,
            BondTypeName: 'Khóa chính'
          },
          {
            BondTypeId: 2,
            BondTypeName: 'Khóa ngoại'
          }
        ],
        Value: null
      },
      DataType: {
        DataSource: [
          {
            DataTypeId: 'NUMBER',
            DataTypeName: 'NUMBER'
          },
          {
            DataTypeId: 'VARCHAR2',
            DataTypeName: 'VARCHAR2'
          },
          {
            DataTypeId: 'DATE',
            DataTypeName: 'DATE'
          }
        ],
        Value: null
      },
      TableInfo: {
        Table: '',
        Schema: '',
        DisplayName: '',
        NumberColunm: '',
        Arrange: '',
        Note: ''
      },
      FieldInfo: {
        FieldName: '',
        DisplayName: '',
        DataType: '',
        Option: 0,
        Order: '',
        Show: 0,
        Bond: '',
        TableParent: '',
        Schema: '',
        FieldNameRB: '',
        FieldNameHT: ''
      },
      ListTable: {
        DataSource: [],
        Value: null
      },
      btnNew: {
        Enabled: true
      },
      btnSave: {
        Enabled: true
      },
      btnCancel: {
        Enabled: true
      },
      btnDelete: {
        Enabled: true
      }
    }
  },
  mounted () {
    this.GetListTable()
    this.BondType.DataSource = [...this.BondType.DataSource]
    this.DataType.DataSource = [...this.DataType.DataSource]
  },
  methods: {
    btnNewClick () {
      console.log('vao vao')
      let div = document.getElementById('CboListTableId')
      div.style.display = 'none'
      let di1v = document.getElementById('TxtTable')
      di1v.style.display = 'visible'
      this.SetButton(1)
      this.Clear('All')
    },
    btnCancelClick () {
      this.SetButton(3)
      this.FillTableInfo(this.ListTable.DataSource[0])
    },
    async btnDeleteClick () {
    //   this.$confirm(
    //     `Bạn chắc chắn muốn xóa Message không?`,
    //     'Xóa Message',
    //     {
    //       confirmButtonText: 'Có',
    //       cancelButtonText: 'Không',
    //       type: 'warning'
    //     }
    //   ).then(async () => {
    //     try {
    //       this.data = await this.axios.post('web-quantri/cauhinh_bang_tudien/fn_xoa_message',
    //         {
    //           'p_message_id': this.CurrentId
    //         })
    //       if (this.data.data.error_code === 'BSS-00000000') {
    //         this.$toast.success('Xóa Message trên dữ liệu viễn thông tỉnh thành công!')
    //         this.GetListData()
    //         this.SetButton(4)
    //       }
    //     } catch (error) {
    //       this.$toast.error('Lỗi khi xóa!')
    //     }
    //   })
    },
    async btnSaveClick () {
    //   if (this.Validate()) {
    //     let Text = 'Thêm mới'
    //     if (this.CheckInsert === 0) Text = 'Cập nhật'
    //     this.$confirm(
    //       `Bạn thật sự muốn ` + Text.toLowerCase() + ` không?`,
    //       Text + ' Message',
    //       {
    //         confirmButtonText: 'Có',
    //         cancelButtonText: 'Không',
    //         type: 'warning'
    //       }
    //     ).then(async () => {
    //       try {
    //         this.data = await this.axios.post('web-quantri/cauhinh_bang_tudien/fn_ghilai_message',
    //           {
    //             'p_chk_new': this.CheckInsert,
    //             'p_message_id': this.CurrentId,
    //             'p_ten_message': !this.Message ? '' : this.Message.trim(),
    //             'p_noidung': !this.Content ? '' : this.Content.trim(),
    //             'p_kieu': this.MessageType,
    //             'p_so_dt': !this.PhoneNumber ? '' : this.PhoneNumber.trim(),
    //             'p_email': !this.Email ? '' : this.Email.trim(),
    //             'p_tieude': !this.Title ? '' : this.Title.trim()
    //           })
    //         if (this.data.data.error_code === 'BSS-00000000') {
    //           this.$toast.success(Text + ' Message vào cơ sở dữ liệu tỉnh thành công!')
    //           this.GetListData()
    //         }
    //       } catch (error) {
    //         this.$toast.error('Lỗi khi thêm mới!')
    //       }
    //     })
    //   }
    },
    async GetListTable () {
      try {
        this.data = await this.axios.get('web-quantri/danhmuc-chung/ADMIN_DS_BANGTS')
        this.ListTable.DataSource = this.data.data.data
        // console.log('lennnn: ', this.ListTable.DataSource.length)
        if (this.ListTable.DataSource.length > 0) {
          this.FillTableInfo(this.ListTable.DataSource[0])
        } else {
          this.Clear('All')
        }
      } catch (err) {
        this.ListTable.DataSource = []
      }
    },
    changeItem (event) {
      let index = 0
      for (const element of this.ListTable.DataSource) {
        if (event === element.TENBANG) break
        index = index + 1
      }
      this.FillTableInfo(this.ListTable.DataSource[index])
    },
    async FillTableInfo (item) {
      this.TableInfo.Table = item.TENBANG
      this.TableInfo.Schema = item.TEN_SCHEMA
      this.TableInfo.DisplayName = item.TEN_HIENTHI
      this.TableInfo.NumberColunm = item.SOCOT_HT
      this.TableInfo.Arrange = item.COT_XAPXEP
      this.TableInfo.Note = item.GHICHU
      this.ListFieldTable.DataSource = await this.GetListFieldByTableName(item.TENBANG)
    //   this.TableInfo.NumberColunm = this.ListFieldTable.DataSource.length
    },
    async GetListFieldByTableName (param) {
      let Result = []
      try {
        this.data = await this.axios.get('web-quantri/danhmuc-chung/ADMIN_DS_COT')
        for (const element of this.data.data.data) {
          if (element.TENBANG === param) {
            Result.push(element)
          }
        }
      } catch (err) {
        Result = []
      }
      console.log(Result)
      return Result
    },
    async ListDataFocus () {
      this.SelectedItem = await this.$refs.ListFieldTable.getSelectedRecords()
      console.log('selected: ', this.SelectedItem)
      if (this.SelectedItem.length <= 0) {
        this.FillFieldInfo(-1)
      } else {
        this.FillFieldInfo(this.SelectedItem[0])
      }
    },
    FillFieldInfo (item) {
      if (item === -1) {
        this.Clear('Field')
      } else {
        this.FieldInfo.FieldName = item.TENCOT
        this.FieldInfo.DisplayName = item.NHAN_COT
        this.FieldInfo.DataType = item.KIEU_DL
        this.FieldInfo.Option = item.GT_TRONG
        this.FieldInfo.Order = item.THUTU_ID
        this.FieldInfo.Show = item.HIENTHI
        this.FieldInfo.Bond = item.KIEU_RB
        this.FieldInfo.TableParent = item.TENBANG_RB
        this.FieldInfo.Schema = item.SCHEMA_RB
        this.FieldInfo.FieldNameRB = item.TENCOT_RB
        this.FieldInfo.FieldNameHT = item.TENCOT_HTRB
      }
    },
    Clear (item) {
      switch (item) {
        // case 'Table':
        //   this.TableInfo.Table = this.ListTable.DataSource[0].TENBANG
        //   this.TableInfo.Schema = ''
        //   this.TableInfo.DisplayName = ''
        //   this.TableInfo.NumberColunm = ''
        //   this.TableInfo.Arrange = ''
        //   this.TableInfo.Note = ''
        //   break
        case 'Field':
          this.FieldInfo.FieldName = ''
          this.FieldInfo.DisplayName = ''
          this.FieldInfo.DataType = 'NUMBER'
          this.FieldInfo.Option = 0
          this.FieldInfo.Order = ''
          this.FieldInfo.Show = 0
          this.FieldInfo.Bond = 0
          this.FieldInfo.TableParent = ''
          this.FieldInfo.Schema = ''
          this.FieldInfo.FieldNameRB = ''
          this.FieldInfo.FieldNameHT = ''
          break
        case 'ListField':
          this.ListFieldTable.DataSource = []
          break
        case 'All':
          this.TableInfo.Table = this.ListTable.DataSource[0].TENBANG
          this.TableInfo.Schema = ''
          this.TableInfo.DisplayName = ''
          this.TableInfo.NumberColunm = ''
          this.TableInfo.Arrange = ''
          this.TableInfo.Note = ''

          this.FieldInfo.FieldName = ''
          this.FieldInfo.DisplayName = ''
          this.FieldInfo.DataType = 'NUMBER'
          this.FieldInfo.Option = 0
          this.FieldInfo.Order = ''
          this.FieldInfo.Show = 0
          this.FieldInfo.Bond = 0
          this.FieldInfo.TableParent = ''
          this.FieldInfo.Schema = ''
          this.FieldInfo.FieldNameRB = ''
          this.FieldInfo.FieldNameHT = ''

          this.ListFieldTable.DataSource = []
      }
    },
    SetButton (input) {
      this.btnNew.Enabled = false
      this.btnSave.Enabled = false
      this.btnCancel.Enabled = false
      this.btnDelete.Enabled = false
      switch (input) {
        // Click new
        case 1:
          this.btnSave.Enabled = true
          this.btnCancel.Enabled = true
          break
        // Click save
        case 2:
          this.btnNew.Enabled = true
          this.btnSave.Enabled = true
          this.btnCancel.Enabled = true
          this.btnDelete.Enabled = true
          break
        // Click cancel
        case 3:
          this.btnNew.Enabled = true
          break
        // Click delete | load form
        case 4:
          this.btnNew.Enabled = true
          this.btnSave.Enabled = true
          this.btnCancel.Enabled = true
          this.btnDelete.Enabled = true
      }
    }
  }
}
</script>
<style>
.non-ative a {
  color: #d3d3d3 !important;
}
</style>
