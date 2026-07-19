<template>
  <div>
    <div class="info-row">
      <div class="key w80">Nhóm file</div>
      <div class="value">
        <div class="select-custom">
          <select name="" id="" class="form-control" v-model="listbox.nhomfile.value.id" :disabled="!isThemFile">
            <option v-for="nhomfile in listbox.nhomfile.list" v-bind:value="nhomfile.ID">{{ nhomfile.NAME }}</option>
          </select>
        </div>
      </div>
    </div>
    <div class="info-row">
      <div class="key w80">Loại File</div>
      <div class="value">
        <div class="select-custom">
          <select name="" id="" class="form-control" v-model="listbox.loaifile.value.id" :disabled="!isThemFile">
            <option v-for="loaifile in listbox.loaifile.list" v-bind:value="loaifile.loaifile_id">{{ loaifile.loai_file }}</option>
          </select>
        </div>
      </div>
    </div>
    <div class="info-row">
      <div class="key w80">Đường dẫn</div>
      <div class="value">
        <div class="input-more-button -right">
          <label class="btn" for="upload-file" :disabled="!isThemFile">
            <span class="-ap icon-more_horiz"></span>
            <input type="file" ref="files" class="hidden" id="upload-file" @change="UploadFileHoSo" />
          </label>
          <input type="text" class="form-control highlight" v-model="listbox.ds_file_hs.value.url" :disabled="!isThemFile" value="http://10.70.521.164:8081/FILES/QBH/HOSO/proud.jpg" />
        </div>
      </div>
    </div>
    <div class="info-row">
      <div class="key w80">Ghi chú</div>
      <div class="value">
        <input type="text" class="form-control" v-model="ghichufile" :disabled="!isThemFile" />
      </div>
    </div>
    <div class="title-bg-main">
      <div class="pull-left">
        <span class="title mart5">Danh sách thuê bao</span>
        <a href="#" @click="btnResetClick" class="btn btn-second pad2 lh20 button-not-bg marl10">
          <span class="nc-icon-outline arrows-1_refresh-69 f20"></span>
        </a>
      </div>
      <div class="pull-right">
        <a href="#" @click="btnNhomClick" class="btn btn-second pad2 lh20 button-not-bg marl10">
          <span class="nc-icon-outline ui-2_filter f20"></span>
        </a>
      </div>
      <div class="clearfix"></div>
    </div>

    <div class="table-content" style="max-height: 300px;">
      <table class="table-result table-gachle tree-grid-2">
        <tr>
          <td>
            <div>
              <DataGrid :key="listbox.ds_thuebao.tableKey" v-bind:columns="listbox.ds_thuebao.header" v-bind:dataSource="listbox.ds_thuebao.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :showColumnCheckbox="true" @selectedItemsChanged="gridTBItemshanged"> </DataGrid>
            </div>
          </td>
        </tr>
      </table>
    </div>
  </div>
</template>
<script>
export default {}
</script>
<style></style>
