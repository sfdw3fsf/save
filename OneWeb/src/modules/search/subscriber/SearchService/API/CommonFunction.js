
export default{
      
    ChuanHoaTen(ten) {
        if (!ten) return "";
        var ten_new = "";
        var str_con = ten.trim();
        var str_xl = "";
        var k = 0;
        var found = true;
        if (str_con.length < 1) return;
        str_con = str_con.replaceAll(/\s+/g, " ");
        try {
          while (found) {
            k = str_con.indexOf(" ");
            if (k > 0) {
              str_xl = str_con.substring(0, k);
              str_con = str_con.substring(k + 1);
              str_xl = this.ChuyenChuHoa(str_xl);
              if (ten_new != "") ten_new += " " + str_xl;
              else ten_new = str_xl;
              found = true;
            } else found = false;
          }
          if (str_con != "") {
            str_xl = str_con;
            str_xl = this.ChuyenChuHoa(str_xl);
            if (ten_new != "") ten_new = ten_new + " " + str_xl;
            else ten_new = str_xl;
          }
          return ten_new;
        } catch (e) {
          return ten;
        }
      },  
      UpperKeyJSON(data){ 
        for(var i = 0; i < data.length; i++){ 
          for (var key in data[i]) {
            if(key.toUpperCase() !== key){
              data[i][key.toUpperCase()] = data[i][key];
              delete data[i][key];
            }
          }
        }
        return data;
      },
}


    