import axios from "axios"
import Endpoint from "./EndPoint.js"
export default{
    LayThongTinCAHienHuu: async (serial, ten_chu_the, email, username, mst) => { 
        let chuoitimkiem = ""
        if ((serial))
            {
                chuoitimkiem = chuoitimkiem + "&serial=" + serial;
            }
            if ((ten_chu_the))
            {
                chuoitimkiem = chuoitimkiem + "&cn=" + ten_chu_the;
            }
            if ((email))
            {
                chuoitimkiem = chuoitimkiem + "&email=" + email;
            }
            if ((username))
            {
                chuoitimkiem = chuoitimkiem + "&username=" + username;
            }
            if ((mst))
            {
                chuoitimkiem = chuoitimkiem + "&username=" + mst;
            }
            chuoitimkiem = chuoitimkiem.replace("&", "?");
        axios.get(Endpoint.CALayThongTinCAHienHuuEndpoint + chuoitimkiem) 
    }

}