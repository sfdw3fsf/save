<template>
  <div class="box-col box-form">
    <div class="legend-title">Danh sách nghề nghiệp đã gán</div>
    <div class="table-content">
      <table class="table-result table-gachle">
        <thead class="center">
          <tr>
            <th class="w20"></th>
            <th class="w20">Chọn</th>
            <th>Nghành nghề</th>
          </tr>
          <tr>
            <th class="w20"></th>

            <th class="w40">
              <div class="input-icon-right">
                <input type="text" class="form-control" />
                <span class="icon nc-icon-outline ui-1_zoom"></span>
              </div>
            </th>
            <th>
              <div class="input-icon-right">
                <input type="text" class="form-control" />
                <span class="icon nc-icon-outline ui-1_zoom"></span>
              </div>
            </th>
          </tr>
        </thead>
        <tbody>
          <tr class="highlight">
            <td class="w20 center">
              <span class="fa fa-play text-main"></span>
            </td>
            <td class="w20 center">
              <div class="check-action">
                <input type="checkbox" class="check" />
                <span class="name"></span>
              </div>
            </td>
            <td class="selected highlight1"></td>
          </tr>
          <tr>
            <td class="w20 center"></td>
            <td class="w20 center">
              <div class="check-action">
                <input type="checkbox" class="check" />
                <span class="name"></span>
              </div>
            </td>
            <td></td>
          </tr>
          <tr>
            <td class="w20 center"></td>
            <td class="w20 center">
              <div class="check-action">
                <input type="checkbox" class="check" />
                <span class="name"></span>
              </div>
            </td>
            <td></td>
          </tr>
          <tr>
            <td class="w20 center"></td>
            <td class="w20 center">
              <div class="check-action">
                <input type="checkbox" class="check" />
                <span class="name"></span>
              </div>
            </td>
            <td></td>
          </tr>
          <tr>
            <td class="w20 center"></td>
            <td class="w20 center">
              <div class="check-action">
                <input type="checkbox" class="check" />
                <span class="name"></span>
              </div>
            </td>
            <td></td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="tc-pagination">
      <a href="#" class="btn one-arrow-left-forward"></a>
      <a href="#" class="btn fa fa-angle-double-left"></a>
      <a href="#" class="btn fa fa-angle-left"></a>
      <span class="text">Bản ghi</span>
      <div class="dropdown inline">
        <div class="fw6 input-number" data-toggle="dropdown">
          <span class="text-main">10</span>/1033
        </div>
        <div class="dropdown-menu">
          <a href="#" class="dropdown-item">10</a>
          <a href="#" class="dropdown-item">20</a>
        </div>
      </div>
      <a href="#" class="btn fa fa-angle-right"></a>
      <a href="#" class="btn fa fa-angle-double-right"></a>
      <a href="#" class="btn one-arrow-right-forward"></a>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TableListCareerNotMaping'
}
</script>
