let IP = "10.70.52.164:8081"
let ACTION_URL = "http://" + IP + "/FILE/"
const SERVER_HSKN = Object.freeze(
    {
        IP: IP,
        ACTION_URL: ACTION_URL,
        VIEW_URL: "http://" + IP + "/FILES/",
        URL: "" + ACTION_URL + "UploadFile.aspx?version:v2",
    })

export default SERVER_HSKN