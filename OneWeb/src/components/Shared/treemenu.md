# Gridview
Author: thangdm1@vnpt.vn

## Cách sử dụng:
Ở trang vue, chèn vào sau thẻ mở script  
`import treemenu from '@/components/Shared/treemenu'`  
Sau `export default {` chèn thêm vào components:  
`components: { treemenu },`  
Ở phần template / trang html, chèn với cấu trúc sau:  
```
<treemenu 
  @treeItemClicked="treeClicked"
  @cbClicked="cbClicked"
  :nodes="tree_menu_dagan.childs" 
  :depth="0"   
  :label="tree_menu_dagan.text"
  :hsid="tree_menu_dagan.id"
  :hascheckbox="false"
  :selected="tree_menu_dagan.isSelected"></treemenu>
```

### Thuộc tính:
- `nodes`: trỏ  đến  thuộc  tính  (mảng) chứa  nhánh  con. Ở  các  nhánh  con  sẽ  bind  với  thuộc  tính  childs.
- `depth`: Độ  sâu  hiện  tại  của  nhánhh, mặc  định  0
- `label`: Text  hiển  thị  ở  node  (gốc), ở các  nhánh  con  sẽ  bind  với  thuộc  tính  text  
- `hsid`: id  / value  của  node  (gốc), ở  các  nhánh  con  sẽ  bind  với  thuộc  tính  id  
- `hascheckbox`: true  sẽ  hiện  checkbox ở  bên  trái  node 
- `selected`: Dùng  với  checkbox, true  thể  hiện  trạng  thái  đã  check. Gắn  với  thuộc  tính  isSelected.
- `cbClicked`: Sự kiện khi chọn checkbox
- `treeItemClicked`: Sự kiện khi bấm vào 1 item trên tree.