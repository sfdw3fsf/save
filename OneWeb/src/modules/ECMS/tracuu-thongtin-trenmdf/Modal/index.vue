<template src="./index.popup.html"></template>
<script>

export default {
  components: {},
  name: 'Modal',
  props: {
    modalShow: Boolean,
    dsTonghop: Object
  },
  computed:{
    dataForTbl() {
      this.dataRow = []
      let sodong = this.dsTonghop.gridDanhSachCount;
      for (let i = 1; i < sodong; i++)
      {
        let row = this.dsTonghop.dsDando[i-1]
        if (i < sodong - 1)
        {
          let itemRow = {
            stt: i+1,
            kyhieu_kc: row.KYHIEU_KC != null ? row.KYHIEU_KC : "",
            diachi_kc: row.DIACHI_KC != null ? row.DIACHI_KC: "",
            jumper_vao: row.JUMPER_VAO != null ? row.JUMPER_VAO : "",
            capra: row.CAPRA != null ? row.CAPRA : "",
            doidayra: row.DOIDAYRA != null ? row.DOIDAYRA : "",
          }
          this.dataRow.push(itemRow) 
        }
        else if (i == sodong - 1)
        {
          let itemRow = {
            stt: i+1,
            kyhieu_kc: row.KYHIEU_KC != null ? row.KYHIEU_KC : "",
            diachi_kc: row.DIACHI_KC != null ? row.DIACHI_KC : "",
            jumper_vao: row.JUMPER_VAO != null ? row.JUMPER_VAO : "",
            capra: '',
            doidayra: '',
          }
          this.dataRow.push(itemRow)
        }
      }
    },
    dataForHeader(){
      this.dataRow1 = []
      this.dv = parseInt(this.dsTonghop.gridDanhSachCount / 4);
      this.md = parseInt(this.dsTonghop.gridDanhSachCount % 4);
      if (this.dv > 0)
      {
        for (let i = 1; i <= this.dv; i++)
        {
          for (let j = 0; j < 4; j++)
          {
            if (j === 0 && i===1)
            {
              this.dataRow1[0]=this.dsTonghop.dsTongdai[0].DOIDAYRA + '/' + this.dsTonghop.dsTongdai[0].CAPRA
            }
            else
            {
              if (this.dsTonghop.dsDando[(this.dv - 1) * 4 + j - 1].DOIDAYRA !== "0"){
                this.dataRow1[j] = this.dsTonghop.dsDando[(this.dv - 1) * 4 + j - 1].DOIDAYRA + '/' + this.dsTonghop.dsDando[(this.dv - 1) * 4 + j - 1].CAPRA
              }
            }
          }
        }
        Object.assign(this.footers, this.dataRow1);
      }

      if (this.md > 0)
      {
        if (this.dv === 0)
        {
          this.dataRow1[0]=this.dsTonghop.dsTongdai[0].DOIDAYRA + '/' + this.dsTonghop.dsTongdai[0].CAPRA

          for (let j = 1; j < this.md; j++)
          {
            if (j < this.md)
            {
              if (this.dsTonghop.dsDando[j - 1].DOIDAYRA !== "0")
              {
                this.dataRow1[j] = this.dsTonghop.dsDando[j - 1].DOIDAYRA + '/' + this.dsTonghop.dsDando[j - 1].CAPRA
              }
            }
          }
          Object.assign(this.footers, this.dataRow1);
        }
        if (this.dv > 0)
        {
          for (let j = 1; j <= this.md; j++)
          {
            if (j < this.md)
            {
              this.dataRow1[j] = this.dsTonghop.dsDando[this.dv * 4 + j - 2].DOIDAYRA + '/' + this.dsTonghop.dsDando[this.dv * 4 + j - 2].CAPRA
            }
          }
          Object.assign(this.footers, this.dataRow1);
        }
      }
      console.log('this.footers ',this.footers)
      console.log('json',JSON.stringify(this.footers))

      //return this.display;
    }
  },
  data: function () {
    return {
      dv: -1,
      md: -1,
      thongtinThem:[],
      dataRow:[],
      dataRow1:[],
      footers: {}
    };
  },
  mounted: function () {

  },
  created: function () {
  },
  methods: {
    hideModal: function () {
      this.$emit('hideModal');
    },
    printGiando: async function () {
      const prtHtml = document.getElementById('popup-body').innerHTML;

      // Get all stylesheets HTML
      let stylesHtml = '';
      for (const node of [...document.querySelectorAll('link[rel="stylesheet"], style')]) {
        stylesHtml += node.outerHTML;
      }

      // Open the print window
      const WinPrint = window.open('', '', 'left=0,top=0,width=800,height=900,toolbar=0,scrollbars=0,status=0');

      WinPrint.document.write(`<!DOCTYPE html>
          <html>
            <head>
              ${stylesHtml}
            </head>
            <body>
              ${prtHtml}
            </body>
          </html>`);

      WinPrint.document.close();
      WinPrint.focus();
      WinPrint.print();
      WinPrint.close();
    }
  },
};
</script>
<style>
#footer ul {
  margin: 0;
  padding: 0;
  list-style: none ;
}

#footer li{
     display: inline-block;   
     margin-right:150px;
     text-decoration: underline;
}


table {
  width: 100%;
  border-collapse: collapse;
}
.table td, .table th {

    border: #797979 solid thin;
    /* border-top: 1px solid #dee2e6; */
}
.table>:not(:last-child)>:last-child>* {
    border-bottom-color: #797979;
}
.table thead th {
    border-bottom: #797979 solid thin;
}
</style>

